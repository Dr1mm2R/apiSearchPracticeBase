package com.example.apisearchpracticebase.Controllers;

import com.example.apisearchpracticebase.Models.RequestStatus;
import com.example.apisearchpracticebase.Models.RequestSubmitted;
import com.example.apisearchpracticebase.Models.WorkApiLogs;
import com.example.apisearchpracticebase.Repositories.RequestStatusRepos;
import com.example.apisearchpracticebase.Repositories.WorkApiLogsRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("request_status")
public class RequestStatusController {
    @Autowired
    RequestStatusRepos requestStatusRepos;

    @Autowired
    WorkApiLogsRepos workApiLogsRepos;

    @RequestMapping("/all")
    public Iterable<RequestStatus> getAllRequestStatus (){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);

        workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()+1);
        workApiLogsRepos.save(workApiLogs);

        return requestStatusRepos.findAll();
    }
}
