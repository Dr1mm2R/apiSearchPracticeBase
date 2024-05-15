package com.example.apisearchpracticebase.Repositories;

import com.example.apisearchpracticebase.Models.RequestStatus;
import com.example.apisearchpracticebase.Models.RequestSubmitted;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestStatusRepos extends CrudRepository<RequestStatus, Long> {
}
