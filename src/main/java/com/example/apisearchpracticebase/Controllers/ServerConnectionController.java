package com.example.apisearchpracticebase.Controllers;

import com.example.apisearchpracticebase.Models.WorkApiLogs;
import com.example.apisearchpracticebase.Repositories.WorkApiLogsRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
public class ServerConnectionController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    WorkApiLogsRepos workApiLogsRepos;

    @GetMapping("/check-Connection")
    public ResponseEntity<String> checkDBConnection() {
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);

        try {
            workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()+1);
            workApiLogsRepos.save(workApiLogs);

            jdbcTemplate.execute("SELECT 1");
            return new ResponseEntity<>("Соединение успешно установлено", HttpStatus.OK);
        } catch (Exception e) {
            workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()-1);
            workApiLogs.setErrorCountRequests(workApiLogs.getErrorCountRequests()+1);
            workApiLogsRepos.save(workApiLogs);
            return new ResponseEntity<>("Ошибка при проверке соединения", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-Error")
    public ResponseEntity<String> simulateServerError() {
        WorkApiLogs workApiLogs = workApiLogsRepos.findById(1);
        workApiLogs.setAllCountRequests(workApiLogs.getAllCountRequests()+1);

        workApiLogs.setSuccessfulCountRequests(workApiLogs.getSuccessfulCountRequests()+1);
        workApiLogsRepos.save(workApiLogs);

        throw new RuntimeException("Симуляция ошибки");
    }
}
