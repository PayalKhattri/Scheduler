package com.payal.scheduler.entities;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Roster {
    @Id
    private String id;
    private List<List<PreferencesForm>> bookings;

    public Roster() {
        bookings=new ArrayList<>(12);
        for(int i = 0; i < 12; ++i) {
            bookings.add(new ArrayList<>());
        }
    }
}
