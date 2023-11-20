package com.example.jwtauth.DTO;

import com.example.jwtauth.models.Team;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamDTO {
    public Team team;
    public String projectTitle;
}
