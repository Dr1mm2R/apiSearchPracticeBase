package com.example.apisearchpracticebase.Controllers;

import com.example.apisearchpracticebase.Models.Student;
import com.example.apisearchpracticebase.Models.VisitLog;
import com.example.apisearchpracticebase.Models.WorkApiLogs;
import com.example.apisearchpracticebase.Repositories.VisitLogRepos;
import com.example.apisearchpracticebase.Repositories.WorkApiLogsRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("visitLog")
public class VisitLogController {
    @Autowired
    VisitLogRepos visitLogRepos;

    @Autowired
    WorkApiLogsRepos workApiLogsRepos;

    @RequestMapping("/get")
    public Iterable<VisitLog> getLogsAtId(@RequestParam("id") int id)
    {
        return visitLogRepos.findAllByStudentId(id);
    }

    @PostMapping("/save")
    public ResponseEntity<VisitLog> saveVisitLog(@RequestBody VisitLog visitLog){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);

        try{
            workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()+1);
            workApiLogsRepos.save(workApiLogs);

            visitLogRepos.save(visitLog);
            return ResponseEntity.ok(visitLog);
        }catch (Exception e){
            workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()-1);
            workApiLogs.setErrorCountRequests(workApiLogs.getErrorCountRequests()+1);
            workApiLogsRepos.save(workApiLogs);
            return ResponseEntity.badRequest().build();
        }
    }
}
