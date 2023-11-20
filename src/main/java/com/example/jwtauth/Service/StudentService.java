package com.example.jwtauth.Service;

import com.example.jwtauth.models.Student;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class StudentService {

    private RestTemplate restTemplate;

    public StudentService() {
        this.restTemplate = new RestTemplate();
    }

    public Student getStudent(String id) {
        String URI = "http://localhost:8080/api/v1/students/"+id;
//        RestTemplate restTemplate = new RestTemplate();
        Student student =restTemplate.getForObject(URI,Student.class);
        return student;
    }

    public List<Student> getAllStudent() {
        String URI = "http://localhost:8080/api/v1/students";
        ResponseEntity<List<Student>> responseEntity = restTemplate.exchange(
                URI,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                }
        );

        // Extract the list of students from the response entity
        List<Student> students = responseEntity.getBody();
        return students;
    }

    public String EnrollProject(String studentId, String projectId) {
        String apiUrl = "http://localhost:8080/api/v1/students/"+studentId+"/projects/"+projectId;
        String response = restTemplate.postForObject(apiUrl,null ,String.class);
        return response;
    }
}
