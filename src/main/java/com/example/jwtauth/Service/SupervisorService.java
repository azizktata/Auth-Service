package com.example.jwtauth.Service;

import com.example.jwtauth.DTO.projectDTO;
import com.example.jwtauth.models.Supervisor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class SupervisorService {

    private RestTemplate restTemplate;

    public SupervisorService() {
        this.restTemplate = new RestTemplate();
    }

    public Supervisor getSupervisor(String id) {
        String URI = "http://localhost:8080/api/v1/supervisors/"+id;
//        RestTemplate restTemplate = new RestTemplate();
        Supervisor supervisor =restTemplate.getForObject(URI,Supervisor.class);
        return supervisor;
    }

    public List<Supervisor> getAllSupervisors() {
        String URI = "http://localhost:8080/api/v1/supervisors";
        ResponseEntity<List<Supervisor>> responseEntity = restTemplate.exchange(
                URI,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Supervisor>>() {
                }
        );

        // Extract the list of students from the response entity
        List<Supervisor> supervisors = responseEntity.getBody();
        return supervisors;
    }


    public String AddProject(String Id, projectDTO projectDto) {
        String apiUrl = "http://localhost:8080/api/v1/supervisors/"+Id+"/projects";
        String response = restTemplate.postForObject(apiUrl, projectDto ,String.class);
        return response;
    }
}
