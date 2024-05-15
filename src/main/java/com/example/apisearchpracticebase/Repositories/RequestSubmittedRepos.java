package com.example.apisearchpracticebase.Repositories;

import com.example.apisearchpracticebase.Models.RequestStatus;
import com.example.apisearchpracticebase.Models.RequestSubmitted;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestSubmittedRepos extends CrudRepository<RequestSubmitted, Long> {
    Iterable<RequestSubmitted> getRequestSubmittedByStudentId(Long id);
    Iterable<RequestSubmitted> getRequestSubmittedByStatus(RequestStatus statusName);

    Iterable<RequestSubmitted> findAllByIsCanceledEquals(boolean canceled);

    Iterable<RequestSubmitted> getRequestSubmittedById(int id);
}
