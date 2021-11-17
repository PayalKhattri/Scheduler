
package com.payal.scheduler.controllers;

import com.payal.scheduler.entities.PreferencesForm;
import com.payal.scheduler.entities.Roster;
import com.payal.scheduler.entities.User;
import com.payal.scheduler.services.CustomUserDetailsService;
import com.payal.scheduler.services.PreferencesFormService;
import com.payal.scheduler.services.RosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthController {

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private PreferencesFormService preferencesFormService;

    @Autowired
    private RosterService rosterService;
    
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView createNewUser(User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "User Already Registered");
            modelAndView.addObject("error", bindingResult.getFieldError().getDefaultMessage());
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("signup");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registerNext");

        }
        return modelAndView;
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }

    @RequestMapping(value = "/preferencesForm", method = RequestMethod.GET)
    public ModelAndView preferencesForm() {

        List<String> dates=preferencesFormService.getThisWeeksDays();

        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("dates", dates);
        modelAndView.setViewName("preferencesForm");
        return modelAndView;
    }

    @RequestMapping(value = "/preferencesForm", method = RequestMethod.POST)
    public ModelAndView preferencesForm(PreferencesForm preferencesForm, BindingResult bindingResult) {

        List<String> dates=preferencesFormService.getThisWeeksDays();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());

        ModelAndView modelAndView = new ModelAndView();
        PreferencesForm preferencesFormExists = preferencesFormService.findUserByEmail(preferencesForm.getEmail());
        System.out.println(preferencesFormExists);
        System.out.println(preferencesForm);
        System.out.println(user);
        modelAndView.addObject("dates", dates);

           if(!user.getEmail().equals(preferencesForm.getEmail())){
            bindingResult
                    .rejectValue("email", "error.invalid",
                            "Submit using registered email address");

               modelAndView.addObject("error", bindingResult.getFieldError().getDefaultMessage());
        }

        else if (preferencesFormExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "Form already submitted");

               modelAndView.addObject("error", bindingResult.getFieldError().getDefaultMessage());
        }



        if (bindingResult.hasErrors()) {
            modelAndView.addObject("user", user);
            modelAndView.setViewName("preferencesForm");
        } else {
            preferencesFormService.savePreferencesForm(preferencesForm);
            rosterService.updateRoster(preferencesForm);
            modelAndView.addObject("user", user);
            modelAndView.setViewName("dashboard");

        }
        return modelAndView;
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

    @RequestMapping(value = {"/roster"}, method = RequestMethod.GET)
    public ModelAndView roster() {


      //  List<String> slots=rosterService.getSlots();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List<Roster> rosters=rosterService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", user);
        modelAndView.addObject("rosters",rosters);
      //  modelAndView.addObject("slots",slots);
        modelAndView.setViewName("roster");
        return modelAndView;
    }

}
