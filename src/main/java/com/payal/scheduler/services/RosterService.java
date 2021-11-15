package com.payal.scheduler.services;

import com.payal.scheduler.entities.PreferencesForm;
import com.payal.scheduler.entities.Roster;
import com.payal.scheduler.repositories.RosterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RosterService {

    @Autowired
    private RosterRepository rosterRepository;

    int capacity=2;

    public void updateRoster(PreferencesForm preferencesForm){

        List<Roster> rosters=rosterRepository.findAll();
        Roster roster=new Roster();
        if(rosters.size()>0)
        roster=rosters.get(0);

        List<List<PreferencesForm>> forms=roster.getBookings();

        String day1= preferencesForm.getDay1();
        String day2= preferencesForm.getDay2();
        String day3= preferencesForm.getDay3();
        String day4= preferencesForm.getDay4();
        String day5= preferencesForm.getDay5();
        String day6= preferencesForm.getDay6();


        if(preferencesForm.getAarogyaSetu()!=null && preferencesForm.getContainmentZone()!=null && preferencesForm.getSelfQuarantined()!=null && preferencesForm.getSymptoms()!=null ) {


            if (day1 != null) {
                if (day1.equals("1") && roster.getBookings().get(0).size()<capacity)
                    forms.get(0).add(preferencesForm);
                else if(roster.getBookings().get(1).size()<capacity)
                    forms.get(1).add(preferencesForm);
            }

            if (day2 != null) {
                if (day2.equals("1")  && roster.getBookings().get(2).size()<capacity)
                    forms.get(2).add(preferencesForm);
                else if(roster.getBookings().get(3).size()<capacity)
                    forms.get(3).add(preferencesForm);
            }
            if (day3 != null) {
                if (day3.equals("1") && roster.getBookings().get(4).size()<capacity)
                    forms.get(4).add(preferencesForm);
                else if(roster.getBookings().get(5).size()<capacity)
                    forms.get(5).add(preferencesForm);
            }
            if (day4 != null) {
                if (day4.equals("1") && roster.getBookings().get(6).size()<capacity)
                    forms.get(6).add(preferencesForm);
                else if(roster.getBookings().get(7).size()<capacity)
                    forms.get(7).add(preferencesForm);
            }
            if (day5 != null) {
                if (day5.equals("1") && roster.getBookings().get(8).size()<capacity)
                    forms.get(8).add(preferencesForm);
                else if(roster.getBookings().get(9).size()<capacity)
                    forms.get(9).add(preferencesForm);
            }
            if (day6 != null) {
                if (day6.equals("1") && roster.getBookings().get(10).size()<capacity)
                    forms.get(10).add(preferencesForm);
                else if(roster.getBookings().get(11).size()<capacity)
                    forms.get(11).add(preferencesForm);
            }
        }

        rosterRepository.deleteAll();
        roster.setBookings(forms);
        rosterRepository.save(roster);

    }
}
