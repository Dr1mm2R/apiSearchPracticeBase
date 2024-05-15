package com.example.apisearchpracticebase.Repositories;

import com.example.apisearchpracticebase.Models.Contact;
import com.example.apisearchpracticebase.Models.ResumeStudent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeStudentRepos extends CrudRepository<ResumeStudent, Long> {

    Optional<ResumeStudent> findByContact(Contact contact);
}