package com.example.apisearchpracticebase.Repositories;

import com.example.apisearchpracticebase.Models.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepos extends CrudRepository<Student, Long> {

    Optional<Student> findByStudentLogin(String loginText);

    Iterable<Student> findAllByIsInternship(boolean inInternship);
}