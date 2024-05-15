package com.example.apisearchpracticebase.Controllers;

import com.example.apisearchpracticebase.Models.*;
import com.example.apisearchpracticebase.Repositories.PracticeBaseRepos;
import com.example.apisearchpracticebase.Repositories.PracticeManagerRepos;
import com.example.apisearchpracticebase.Repositories.VisitLogRepos;
import com.example.apisearchpracticebase.Repositories.WorkApiLogsRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("practiceBase")
public class PracticeBaseController {
    @Autowired
    VisitLogRepos visitLogRepos;

    @Autowired
    PracticeBaseRepos practiceBaseRepos;
    @Autowired
    PracticeManagerRepos practiceManagerRepos;

    @Autowired
    private ImageController imageController;

    @Autowired
    WorkApiLogsRepos workApiLogsRepos;

    @RequestMapping("/all")
    public Iterable<PracticeBase> getAll(){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);

        workApiLogsRepos.findById(1).setSuccessfulCountRequests(workApiLogsRepos.findById(1).getSuccessfulCountRequests()+1);
        return practiceBaseRepos.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<String> savePracticeBase(
            @RequestParam(value = "practiceBaseId", required = false) String id,
            @RequestParam("nameBase") String nameBase,
            @RequestParam("descriptionAboutBase") String descriptionAboutBase,
            @RequestParam("statusDialing") String statusDialing,
            @RequestParam("practiceManagerID") String practiceManagerID,
            @RequestParam(value = "photoPlace", required = false) MultipartFile photoPlace) {
        try {
            PracticeBase practiceBase = new PracticeBase();
            if (practiceBaseRepos.getPracticeBaseByManagerId(Long.valueOf(practiceManagerID)) != null){
                practiceBase = practiceBaseRepos.getPracticeBaseByManagerId(Long.valueOf(practiceManagerID));
            }
            practiceBase.setNameBase(nameBase);
            practiceBase.setDescriptionAboutBase(descriptionAboutBase);
            practiceBase.setStatusDialing(statusDialing);
            practiceBase.setManager(practiceManagerRepos.findById(Long.valueOf(practiceManagerID)).get());
            practiceBaseRepos.save(practiceBase);
            practiceBase = practiceBaseRepos.findByNameBase(practiceBase.getNameBase()).get();

            if(photoPlace != null) imageController.uploadImage(photoPlace, true, practiceBase.getId());

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @RequestMapping("getAtName/{name}")
    public Iterable<PracticeBase> getAtName(@PathVariable("name") String value){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);

        workApiLogsRepos.findById(1).setSuccessfulCountRequests(workApiLogsRepos.findById(1).getSuccessfulCountRequests()+1);
        if(value.equals("zero")){
            return practiceBaseRepos.findAll();
        }else{
            return practiceBaseRepos.getPracticeBaseByNameBaseContains(value);
        }
    }

    @PostMapping("/getAtManager")
    public ResponseEntity<PracticeBase> getAtManager(@RequestBody PracticeManager practiceManager){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);

        workApiLogsRepos.findById(1).setSuccessfulCountRequests(workApiLogsRepos.findById(1).getSuccessfulCountRequests()+1);
        PracticeBase practiceBase = practiceBaseRepos.getPracticeBaseByManagerId(practiceManager.getId());
        return ResponseEntity.ok(practiceBase);
    }

    @PostMapping("/getStudentsOnPracticeBase")
    public ResponseEntity<List<VisitLog>> getAtPracticeBase(@RequestBody PracticeBase practiceBase){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);
        List<VisitLog> returnedList;

        returnedList = visitLogRepos.findAllByPracticeBase(practiceBase);

        return ResponseEntity.ok(returnedList);
    }

    @PostMapping("/getVisitLogByStudent")
    public ResponseEntity<List<VisitLog>> getAtVisitLogStudent(@RequestBody int studentId){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);
        Iterable<VisitLog> tempList;

        tempList = visitLogRepos.findAllByStudentId(studentId);
        List<VisitLog> returnedList = new ArrayList<>();
        tempList.forEach(returnedList::add);

        return ResponseEntity.ok(returnedList);
    }

    @RequestMapping("getAtFilter/{filter}")
    public Iterable<PracticeBase> getAtFilter(@PathVariable("filter") String filter){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);

        workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()+1);
        workApiLogsRepos.save(workApiLogs);
        switch(filter) {
            case "Набор открыт":
                return practiceBaseRepos.getPracticeBaseByStatusDialingEquals("Active");
            case "Набор закрыт":
                return practiceBaseRepos.getPracticeBaseByStatusDialingEquals("Inactive");
            case "А-Я":
                return practiceBaseRepos.findAll(Sort.by(Sort.Direction.ASC, "nameBase"));
            case "Я-А":
                return practiceBaseRepos.findAll(Sort.by(Sort.Direction.DESC, "nameBase"));
            case "С номером телефона":
                return practiceBaseRepos.findByManagerContactPhoneNumberIsNot("");
            case "С электронной почтой":
                return practiceBaseRepos.findByManagerContactEmailIsNot("");
            case "С Вконтакте":
                return practiceBaseRepos.findByManagerContactVkPageDataIsNot("");
            case "С WhatsApp":
                return practiceBaseRepos.findByManagerContactWhatsAppDataIsNot("");
            case "С Telegram":
                return practiceBaseRepos.findByManagerContactTelegramDataIsNot("");
            default:
                return practiceBaseRepos.findAll();
        }
    }
}
