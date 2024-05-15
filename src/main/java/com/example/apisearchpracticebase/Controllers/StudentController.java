package com.example.apisearchpracticebase.Controllers;

import com.example.apisearchpracticebase.Models.Contact;
import com.example.apisearchpracticebase.Models.ResumeStudent;
import com.example.apisearchpracticebase.Models.Student;
import com.example.apisearchpracticebase.Models.WorkApiLogs;
import com.example.apisearchpracticebase.Repositories.ContactRepos;
import com.example.apisearchpracticebase.Repositories.ResumeStudentRepos;
import com.example.apisearchpracticebase.Repositories.StudentRepos;
import com.example.apisearchpracticebase.Repositories.WorkApiLogsRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentRepos studentRepos;

    @Autowired
    ResumeStudentRepos resumeStudentRepos;

    @Autowired
    ContactRepos contactRepos;

    @Autowired
    WorkApiLogsRepos workApiLogsRepos;

    @GetMapping("/all")
    public Iterable<Student> getAll(){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);

        workApiLogsRepos.findById(1).setSuccessfulCountRequests(workApiLogsRepos.findById(1).getSuccessfulCountRequests()+1);

        return studentRepos.findAll();
    }

    @GetMapping("/get")
    public Optional<Student> getByLogin(@RequestParam String loginText){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);

        workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()+1);
        workApiLogsRepos.save(workApiLogs);

        return studentRepos.findByStudentLogin(loginText);
    }

    @GetMapping("/reg")
    public ResponseEntity<Student> registration(@RequestParam String studentLogin){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);

        workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()+1);
        workApiLogsRepos.save(workApiLogs);

        String uniqueKey = "tokenUnique";
        Contact emptyContact = new Contact();
        emptyContact.setAddress(uniqueKey);
        emptyContact.setEmail("");
        emptyContact.setPhoneNumber("");
        emptyContact.setTelegramData("");
        emptyContact.setVkPageData("");
        emptyContact.setWhatsAppData("");
        contactRepos.save(emptyContact);
        emptyContact = contactRepos.findByAddress(uniqueKey).get();
        emptyContact.setAddress("");
        contactRepos.save(emptyContact);

        ResumeStudent emptyResume = new ResumeStudent();
        emptyResume.setContact(emptyContact);
        emptyResume.setPurposeInternship("");
        emptyResume.setPersonalQualities("");
        emptyResume.setPreferredLanguages("");
        emptyResume.setProfessionalSkills("");
        emptyResume.setEducation("");
        emptyResume.setPhotoStudent("");
        resumeStudentRepos.save(emptyResume);

        Student student = new Student();
        student.setFirstName("Имя");
        student.setLastName("Фамилия");
        student.setMiddleName("Отчество");
        student.setStudentLogin(studentLogin);
        student.setResume(resumeStudentRepos.findByContact(emptyContact).get());
        studentRepos.save(student);

        return ResponseEntity.ok(student);
    }

    @PostMapping("/save")
    public ResponseEntity<Student> saveStudentInfo(@RequestBody Student student){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);

        try{
            workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()+1);
            workApiLogsRepos.save(workApiLogs);

            student.getResume().setPhotoStudent(studentRepos.findByStudentLogin(student.getStudentLogin()).get().getResume().getPhotoStudent());
            studentRepos.save(student);
            resumeStudentRepos.save(student.getResume());
            contactRepos.save(student.getResume().getContact());
            return ResponseEntity.ok(student);
        }catch (Exception e){
            workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()-1);
            workApiLogs.setErrorCountRequests(workApiLogs.getErrorCountRequests()+1);
            workApiLogsRepos.save(workApiLogs);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/not-in-internship")
    public Iterable<Student> getListStudent(){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);

        workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()+1);
        workApiLogsRepos.save(workApiLogs);
        return studentRepos.findAllByIsInternship(false);
    }
}
