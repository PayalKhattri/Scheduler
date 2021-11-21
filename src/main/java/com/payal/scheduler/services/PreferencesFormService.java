package com.payal.scheduler.services;

import com.payal.scheduler.entities.PreferencesForm;
import com.payal.scheduler.entities.User;
import com.payal.scheduler.repositories.PreferencesFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.*;

@Service
public class PreferencesFormService {

    private Locale locale = new Locale("en","IN");
    // 1. create calendar
    private Calendar cal = new GregorianCalendar(locale);
    private DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);


    @Autowired
    private PreferencesFormRepository preferencesFormRepository;


    public int numberOfEntries(){
        return preferencesFormRepository.findAll().size();
    }

    public PreferencesForm findUserByEmail(String email) {
        return preferencesFormRepository.findByEmail(email);
    }

    public void savePreferencesForm(PreferencesForm preferencesForm){
         preferencesFormRepository.save(preferencesForm);
    }

    public void deletePreferencesForm(PreferencesForm preferencesForm){
        preferencesFormRepository.delete(preferencesForm);
    }

    public List<String> getThisWeeksDays() {

        List<String> dates=new ArrayList<>();


        // 2. set calendar to the current date
        cal.setTime(new Date());
        cal.setFirstDayOfWeek(Calendar.MONDAY);

        // 3. set calendars dOW field to the first dOW (last sunday)
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());

        for (int i = 0; i < 7; i++) {
            // 4. get some infomation
            String nameOfMonth = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, locale);
            String nameOfDay = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, locale);
            int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

            dates.add(df.format(cal.getTime()));

            //add date to map

            // 5. increase day field; add() will adjust the month if neccessary
            cal.add(Calendar.DAY_OF_WEEK, 1);
        }
        return dates;
    }


}
