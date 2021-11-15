package com.payal.scheduler.repositories;

import com.payal.scheduler.entities.PreferencesForm;
import com.payal.scheduler.entities.Roster;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RosterRepository extends MongoRepository<Roster,String> {
}
