package com.example.apisearchpracticebase.Controllers;

import com.example.apisearchpracticebase.Models.*;
import com.example.apisearchpracticebase.Repositories.*;
import com.example.apisearchpracticebase.Security.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/authentication")
public class AuthController {

    String uniqueKey = "tokenUnique";

    private final JWTProvider jwtProvider;

    public AuthController(JWTProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Autowired
    StudentRepos studentRepos;
    @Autowired
    PracticeManagerRepos practiceManagerRepos;
    @Autowired
    ResumeStudentRepos resumeStudentRepos;
    @Autowired
    ContactRepos contactRepos;

    @Autowired
    WorkApiLogsRepos workApiLogsRepos;

    @PostMapping("/site/authentication")
    public ResponseEntity<Map<String, Object>> authenticationUserFromSite(@RequestBody String emailValue){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);

        Map<String, Object> map = new HashMap<>();
        if(practiceManagerRepos.findByManagerLogin(emailValue).isPresent()) {
            map.put("object", practiceManagerRepos.findByManagerLogin(emailValue).get());
            map.put("role", "practiceManager");
            map.put("token", jwtProvider.createToken(practiceManagerRepos.findByManagerLogin(emailValue).get().getManagerLogin(), List.of("PRACTICEMANAGER")));
        }

        workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()+1);
        workApiLogsRepos.save(workApiLogs);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/site/registration")
    public ResponseEntity<Map<String, Object>> registrationUserFromSite(@RequestBody Map<String, String> map){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);

        PracticeManager practiceManager = new PracticeManager();
        practiceManager.setManagerLogin(map.get("email"));
        practiceManager.setPostManager(map.get("position"));
        practiceManager.setWorkDirection(map.get("direction"));
        practiceManager.setWorkExperience(map.get("experience"));
        practiceManager.setSecondName(map.get("lastName"));
        practiceManager.setFirstName(map.get("firstName"));
        if(map.containsKey("middleName")) practiceManager.setMiddleName(map.get("middleName"));
        practiceManager.setRole(map.get("role"));
        Contact emptyContact = getEmptyContact(practiceManager.getManagerLogin());
        practiceManager.setContact(emptyContact);
        practiceManagerRepos.save(practiceManager);

        workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()+1);
        workApiLogsRepos.save(workApiLogs);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/student")
    public ResponseEntity<Map<String, Object>> authentication(@RequestBody Map<String, String> requestData){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);


        workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()+1);
        workApiLogsRepos.save(workApiLogs);
        String email = requestData.get("email");
        String first = "";
        String second = "";
        String middle = "";

        try{
            first = requestData.get("first");
            second = requestData.get("second");
            middle = requestData.get("middle");
        }catch (Exception e){
            workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()-1);
            workApiLogs.setErrorCountRequests(workApiLogs.getErrorCountRequests()+1);
            workApiLogsRepos.save(workApiLogs);
        }

        Student studentTemp = new Student();
        if(studentRepos.findByStudentLogin(email).isEmpty()){
            studentTemp.setStudentLogin(email);
            studentTemp.setFirstName(first);
            studentTemp.setLastName(second);
            studentTemp.setMiddleName(middle);

            Contact emptyContact = getEmptyContact(email);
            ResumeStudent emptyResume = new ResumeStudent();
            emptyResume.setContact(emptyContact);
            emptyResume.setPurposeInternship("");
            emptyResume.setPersonalQualities("");
            emptyResume.setPreferredLanguages("");
            emptyResume.setProfessionalSkills("");
            emptyResume.setEducation("");
            emptyResume.setPhotoStudent("");
            resumeStudentRepos.save(emptyResume);

            studentTemp.setResume(resumeStudentRepos.findByContact(emptyContact).get());
            studentRepos.save(studentTemp);
        }else{
            studentTemp = studentRepos.findByStudentLogin(email).get();
        }

        Map<String, Object> map = new HashMap<>();
        map.put("student", studentTemp);
        map.put("token", jwtProvider.createToken(studentTemp.getStudentLogin(), List.of("STUDENT")));

        return ResponseEntity.ok(map);
    }

    private Contact getEmptyContact(String email){
        Contact emptyContact = new Contact();
        emptyContact.setAddress(uniqueKey);
        emptyContact.setEmail(email);
        emptyContact.setPhoneNumber("");
        emptyContact.setTelegramData("");
        emptyContact.setVkPageData("");
        emptyContact.setWhatsAppData("");
        contactRepos.save(emptyContact);
        emptyContact = contactRepos.findByAddress(uniqueKey).get();
        emptyContact.setAddress("");
        contactRepos.save(emptyContact);
        return emptyContact;
    }
}
