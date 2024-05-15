package com.example.apisearchpracticebase.Repositories;

import com.example.apisearchpracticebase.Models.WorkApiLogs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkApiLogsRepos extends CrudRepository<WorkApiLogs, Long> {
    WorkApiLogs findById(int id);
}
