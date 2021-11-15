package com.payal.scheduler.services;

import com.payal.scheduler.entities.PreferencesForm;
import com.payal.scheduler.entities.User;
import com.payal.scheduler.repositories.PreferencesFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreferencesFormService {
    @Autowired
    private PreferencesFormRepository preferencesFormRepository;

    public PreferencesForm findUserByEmail(String email) {
        return preferencesFormRepository.findByEmail(email);
    }

    public void savePreferencesForm(PreferencesForm preferencesForm){
         preferencesFormRepository.save(preferencesForm);
    }

}
