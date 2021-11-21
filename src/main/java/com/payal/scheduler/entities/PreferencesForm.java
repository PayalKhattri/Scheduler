package com.payal.scheduler.entities;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PreferencesForm {

    @Id
    private String id;
    private String name;
    private String email;
    private String aarogyaSetu;
    private String symptoms;
    private String containmentZone;
    private String selfQuarantined;
    private String certificate;



    private String day1;
    private String day2;
    private String day3;
    private String day4;
    private String day5;
    private String day6;

}
