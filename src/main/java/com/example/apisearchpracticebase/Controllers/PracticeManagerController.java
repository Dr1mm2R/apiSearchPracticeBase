package com.example.apisearchpracticebase.Controllers;

import com.example.apisearchpracticebase.Models.PracticeManager;
import com.example.apisearchpracticebase.Models.WorkApiLogs;
import com.example.apisearchpracticebase.Repositories.ContactRepos;
import com.example.apisearchpracticebase.Repositories.PracticeManagerRepos;
import com.example.apisearchpracticebase.Repositories.WorkApiLogsRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("practice_Manager")
public class PracticeManagerController {
    @Autowired
    PracticeManagerRepos practiceManagerRepos;

    @Autowired
    WorkApiLogsRepos workApiLogsRepos;

    @Autowired
    ContactRepos contactRepos;

    @RequestMapping("/all")
    public Iterable<PracticeManager> getAll(){
        return practiceManagerRepos.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<PracticeManager> saveStudentInfo(@RequestBody PracticeManager practiceManager){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);

        try{

            workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()+1);
            workApiLogsRepos.save(workApiLogs);

            practiceManagerRepos.save(practiceManager);
            contactRepos.save(practiceManager.getContact());
            return ResponseEntity.ok().build();
        }catch (Exception e){
            workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()-1);
            workApiLogs.setErrorCountRequests(workApiLogs.getErrorCountRequests()+1);
            workApiLogsRepos.save(workApiLogs);
            return ResponseEntity.badRequest().build();
        }
    }
}
