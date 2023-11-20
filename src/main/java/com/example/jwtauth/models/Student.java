package com.example.jwtauth.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    private String id;

    private String name;
    private String email;

    private Project project;

    @JsonIgnore
    private Team team;


    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void getOutTeam (){
        this.team = new Team();
        this.project = new Project();
    }
}
