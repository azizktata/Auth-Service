package com.example.jwtauth.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    private String name;
    private String email;
    private String role;

    public Member(String name, String email) {
        this.name = name;
        this.email = email;
        this.role = "student";
    }
}

