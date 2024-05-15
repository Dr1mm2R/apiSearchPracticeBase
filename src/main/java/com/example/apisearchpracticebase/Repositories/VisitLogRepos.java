package com.example.apisearchpracticebase.Repositories;

import com.example.apisearchpracticebase.Models.PracticeBase;
import com.example.apisearchpracticebase.Models.VisitLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitLogRepos extends CrudRepository<VisitLog, Long> {

    Iterable<VisitLog> findAllByStudentId(int id);

    List<VisitLog> findAllByPracticeBase(PracticeBase practiceBase);
}