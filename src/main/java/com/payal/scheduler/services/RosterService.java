package com.payal.scheduler.services;

import com.payal.scheduler.entities.PreferencesForm;
import com.payal.scheduler.entities.Roster;
import com.payal.scheduler.repositories.RosterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.*;

@Service
public class RosterService {

    @Autowired
    private RosterRepository rosterRepository;

    int capacity=2;


    static boolean execute=false;
    List<String> slots=new ArrayList<>();


    public List<String> getThisWeeksDays() {

        Locale locale = new Locale("en","IN");
        // 1. create calendar
        Calendar cal = new GregorianCalendar(locale);
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);

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

    public void initialize(){

        if(!execute) {
            List<String> dates = getThisWeeksDays();

            for (int i = 0; i < 7; i++) {
                String s = dates.get(i) + " Slot 1";
                Roster r = new Roster();
                r.setSlot(s);
                rosterRepository.save(r);
                slots.add(s);

                s = dates.get(i) + " Slot 2";
                r = new Roster();
                r.setSlot(s);
                rosterRepository.save(r);
                slots.add(s);

            }
            execute = true;
        }

    }


    public List<String> getSlots(){
        return slots;
    }


    public void helper(PreferencesForm preferencesForm,int index){
        Roster r=rosterRepository.findBySlot(slots.get(index));
        List<PreferencesForm> l=r.getBookings();
        if(l==null)
            l=new ArrayList<>();
        l.add(preferencesForm);
        r.setBookings(l);
//        rosterRepository.delete(rosterRepository.findBySlot(slots.get(index)));
        rosterRepository.save(r);
    }


    public Roster findBySlot(String slot){
        return rosterRepository.findBySlot(slot);
    }

    public void updateRoster(PreferencesForm preferencesForm){
        initialize();

        String day1= preferencesForm.getDay1();
        String day2= preferencesForm.getDay2();
        String day3= preferencesForm.getDay3();
        String day4= preferencesForm.getDay4();
        String day5= preferencesForm.getDay5();
        String day6= preferencesForm.getDay6();


        if(day1!=null){
            if(day1.equals("1")){
               helper(preferencesForm,0);
            }
            else{
                helper(preferencesForm,1);
            }
        }

        if(day2!=null){
            if(day2.equals("1")){
                helper(preferencesForm,2);
            }
            else{
                helper(preferencesForm,3);
            }
        }

        if(day3!=null){
            if(day3.equals("1")){
                helper(preferencesForm,4);
            }
            else{
                helper(preferencesForm,5);
            }
        }

        if(day4!=null){
            if(day4.equals("1")){
                helper(preferencesForm,6);
            }
            else{
                helper(preferencesForm,7);
            }
        }

        if(day5!=null){
            if(day5.equals("1")){
                helper(preferencesForm,8);
            }
            else{
                helper(preferencesForm,9);
            }
        }

        if(day6!=null){
            if(day6.equals("1")){
                helper(preferencesForm,10);
            }
            else{
                helper(preferencesForm,11);
            }
        }


//        List<Roster> rosters=rosterRepository.findAll();
//        Roster roster=new Roster();
//        if(rosters.size()>0)
//        roster=rosters.get(0);
//
//        List<List<PreferencesForm>> forms=roster.getBookings();
//
//        String day1= preferencesForm.getDay1();
//        String day2= preferencesForm.getDay2();
//        String day3= preferencesForm.getDay3();
//        String day4= preferencesForm.getDay4();
//        String day5= preferencesForm.getDay5();
//        String day6= preferencesForm.getDay6();
//
//
//        if(preferencesForm.getAarogyaSetu()!=null && preferencesForm.getContainmentZone()!=null && preferencesForm.getSelfQuarantined()!=null && preferencesForm.getSymptoms()!=null ) {
//
//
//            if (day1 != null) {
//                if (day1.equals("1") && roster.getBookings().get(0).size()<capacity)
//                    forms.get(0).add(preferencesForm);
//                else if(roster.getBookings().get(1).size()<capacity)
//                    forms.get(1).add(preferencesForm);
//            }
//
//            if (day2 != null) {
//                if (day2.equals("1")  && roster.getBookings().get(2).size()<capacity)
//                    forms.get(2).add(preferencesForm);
//                else if(roster.getBookings().get(3).size()<capacity)
//                    forms.get(3).add(preferencesForm);
//            }
//            if (day3 != null) {
//                if (day3.equals("1") && roster.getBookings().get(4).size()<capacity)
//                    forms.get(4).add(preferencesForm);
//                else if(roster.getBookings().get(5).size()<capacity)
//                    forms.get(5).add(preferencesForm);
//            }
//            if (day4 != null) {
//                if (day4.equals("1") && roster.getBookings().get(6).size()<capacity)
//                    forms.get(6).add(preferencesForm);
//                else if(roster.getBookings().get(7).size()<capacity)
//                    forms.get(7).add(preferencesForm);
//            }
//            if (day5 != null) {
//                if (day5.equals("1") && roster.getBookings().get(8).size()<capacity)
//                    forms.get(8).add(preferencesForm);
//                else if(roster.getBookings().get(9).size()<capacity)
//                    forms.get(9).add(preferencesForm);
//            }
//            if (day6 != null) {
//                if (day6.equals("1") && roster.getBookings().get(10).size()<capacity)
//                    forms.get(10).add(preferencesForm);
//                else if(roster.getBookings().get(11).size()<capacity)
//                    forms.get(11).add(preferencesForm);
//            }
//        }
//
//        rosterRepository.deleteAll();
//
//        roster.setBookings(forms);
//        rosterRepository.save(roster);

    }

    public List<Roster>  findAll(){
        return rosterRepository.findAll();
    }

}
