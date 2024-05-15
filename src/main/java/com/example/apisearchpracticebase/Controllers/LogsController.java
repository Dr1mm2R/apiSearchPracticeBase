package com.example.apisearchpracticebase.Controllers;

import com.example.apisearchpracticebase.Models.WorkApiLogs;
import com.example.apisearchpracticebase.Repositories.WorkApiLogsRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apiLogs")
public class LogsController {
    @Autowired
    WorkApiLogsRepos workApiLogsRepos;
    @GetMapping
    public ResponseEntity<WorkApiLogs> getAllLogs(){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        return ResponseEntity.ok(workApiLogs);
    }
}
