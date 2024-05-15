package com.example.apisearchpracticebase.Repositories;

import com.example.apisearchpracticebase.Models.PracticeBase;
import com.example.apisearchpracticebase.Models.PracticeManager;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PracticeBaseRepos extends CrudRepository<PracticeBase, Long> {
    Iterable<PracticeBase> findAll(Sort sort);
    Iterable<PracticeBase> getPracticeBaseById(Long id);

    Optional<PracticeBase> findByNameBase(String nameBase);

    Iterable<PracticeBase> getPracticeBaseByNameBaseContains(String name);

    Iterable<PracticeBase> getPracticeBaseByStatusDialingEquals(String statusDialing);
    Iterable<PracticeBase> findByManagerContactPhoneNumberIsNot(String param);
    Iterable<PracticeBase> findByManagerContactEmailIsNot(String param);
    Iterable<PracticeBase> findByManagerContactVkPageDataIsNot(String param);
    Iterable<PracticeBase> findByManagerContactWhatsAppDataIsNot(String param);
    Iterable<PracticeBase> findByManagerContactTelegramDataIsNot(String param);

    PracticeBase getPracticeBaseByManagerId(Long id);
}