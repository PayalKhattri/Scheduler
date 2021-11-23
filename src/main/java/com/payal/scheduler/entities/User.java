package com.payal.scheduler.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Set;

@Data
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    String id;
    private String name;
    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    private String email;
    private String accountType;
    private String roll;
    private String branch;
    private String position;
    private String department;
    private String phone;
    private String address;
    private String zip;
    private String city;
    private String state;
    private String password;

    private String certificate;

    private boolean enabled;
    @DBRef
    private Set<Role> roles;


}
