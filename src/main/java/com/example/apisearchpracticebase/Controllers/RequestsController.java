package com.example.apisearchpracticebase.Controllers;

import com.example.apisearchpracticebase.Models.PracticeBase;
import com.example.apisearchpracticebase.Models.RequestStatus;
import com.example.apisearchpracticebase.Models.RequestSubmitted;
import com.example.apisearchpracticebase.Models.WorkApiLogs;
import com.example.apisearchpracticebase.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("requests")
public class RequestsController {
    @Autowired
    RequestSubmittedRepos requestSubmittedRepos;
    @Autowired
    PracticeBaseRepos practiceBaseRepos;
    @Autowired
    StudentRepos studentRepos;

    @Autowired
    RequestStatusRepos requestStatusRepos;

    @Autowired
    WorkApiLogsRepos workApiLogsRepos;

    @RequestMapping("/get")
    public Iterable<RequestSubmitted> getAllRequests (@RequestParam("id") long id, @RequestParam("sortParam") String param){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);

        Iterable<RequestStatus> allStatuses = requestStatusRepos.findAll();
        for (RequestStatus status : allStatuses) {
            if(status.getStatusName().equalsIgnoreCase(param) || status.getStatusName().contains(param)){

                workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()+1);
                workApiLogsRepos.save(workApiLogs);
                return requestSubmittedRepos.getRequestSubmittedByStatus(status);
            }
        }
        if(param.equals("all")){
            workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()+1);
            workApiLogsRepos.save(workApiLogs);
            return requestSubmittedRepos.getRequestSubmittedByStudentId(id);
        }
        if(param.equals("true")){
            workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()+1);
            workApiLogsRepos.save(workApiLogs);
            return requestSubmittedRepos.findAllByIsCanceledEquals(true);
        }
        if(param.equals("false")){
            workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()+1);
            workApiLogsRepos.save(workApiLogs);
            return requestSubmittedRepos.findAllByIsCanceledEquals(false);
        }
        workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()-1);
        workApiLogs.setErrorCountRequests(workApiLogs.getErrorCountRequests()+1);
        workApiLogsRepos.save(workApiLogs);
        return null;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveRequest(@RequestBody RequestSubmitted requestSubmitted){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);
        requestSubmitted.setStudent(studentRepos.findById(requestSubmitted.getStudent().getId()).get());
        try{
            workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()+1);
            workApiLogsRepos.save(workApiLogs);
            practiceBaseRepos.save(requestSubmitted.getPracticeBase());
            requestSubmittedRepos.save(requestSubmitted);
            return ResponseEntity.ok().body("Успешно");
        }catch (Exception e){
            workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()-1);
            workApiLogs.setErrorCountRequests(workApiLogs.getErrorCountRequests()+1);
            workApiLogsRepos.save(workApiLogs);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping("getAtNumber/{name}")
    public Iterable<RequestSubmitted> getAtName(@PathVariable("name") String value){
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);

        workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()+1);
        workApiLogsRepos.save(workApiLogs);

        if(value.equals("zero")){
            return requestSubmittedRepos.findAll();
        }else{
            return requestSubmittedRepos.getRequestSubmittedById(Integer.parseInt(value));
        }
    }
}
