package com.payal.scheduler.entities;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.text.DateFormat;
import java.util.*;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Roster {
    @Id
    private String id;
    private List<PreferencesForm> bookings;
    private String slot;

}
