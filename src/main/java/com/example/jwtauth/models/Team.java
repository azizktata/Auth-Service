package com.example.jwtauth.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {

    private String name;

//    @DBRef
//    private Supervisor supervisorId;

    private Set<Member> members = new HashSet<>();

    public Team(String name) {
        this.name = name;
    }

    public void removeMember(String email){
        this.members.removeIf(member -> member.getEmail().equals(email));
    }
}