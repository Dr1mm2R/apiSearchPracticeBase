package com.example.apisearchpracticebase.Repositories;

import com.example.apisearchpracticebase.Models.PracticeManager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PracticeManagerRepos extends CrudRepository<PracticeManager, Long> {
    Optional<PracticeManager> findByManagerLogin(String login);
}