package com.example.apisearchpracticebase.Repositories;

import com.example.apisearchpracticebase.Models.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepos extends CrudRepository<Contact, Long> {

    Optional<Contact> findByAddress(String address);
}