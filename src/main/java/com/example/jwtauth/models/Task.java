package com.example.jwtauth.models;

import com.example.jwtauth.Enum.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Task {

    private String Id;
    private String title;
    private String description;
    private TaskStatus status;
    private String createdby;

    public Task (){
        this.Id = UUID.randomUUID().toString();
    }

    public Task(String title, String description, String createdby) {
        this.Id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.createdby = createdby;
        this.status = TaskStatus.pending;
    }
}
