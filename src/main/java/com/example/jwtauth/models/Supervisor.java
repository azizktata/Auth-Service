package com.example.jwtauth.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Supervisor {

    @Id
    private String id;

    private String name;
    private String email;

    private List<Project> projects;


    public Supervisor(String name, String email) {
        this.name = name;
        this.email = email;
        this.projects = new ArrayList<>();
    }

    public boolean checkProjectId(String Id){
        for(Project project : this.projects){
            if (project.getId().equals(Id))
                return true;
        }
        return false;
    }
    public void removeProject(String Id){
        this.projects.removeIf(task -> task.getId().equals(Id));
    }

    public boolean checkForStage(String stageId){
        for(Project project : this.projects){
            for (Stage stage : project.getStages()){
                if (stage.getId().equals(stageId))
                    return true;
            }
        }
        return false;
    }
}
