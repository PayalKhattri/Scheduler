package com.payal.scheduler.repositories;

import com.payal.scheduler.entities.PreferencesForm;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreferencesFormRepository extends MongoRepository<PreferencesForm,String> {

    PreferencesForm findByEmail(String email);
}
