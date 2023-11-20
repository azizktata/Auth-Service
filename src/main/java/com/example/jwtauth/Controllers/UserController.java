package com.example.jwtauth.Controllers;


import com.example.jwtauth.DTO.userDTO;
import com.example.jwtauth.DTO.userLoginDTO;
import com.example.jwtauth.Entity.User;
import com.example.jwtauth.Service.UserService;
import com.example.jwtauth.models.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostConstruct
    public void initRolesAndUser(){
        this.userService.initRolesAndUser();
    }

    @PostMapping("/registerNewUser")
    public ResponseEntity<?> registerNewUser(@RequestBody userLoginDTO userLoginDto) {
        return this.userService.createNewUser(userLoginDto);
    }

    @GetMapping({"/forAdmin"})
    @PreAuthorize("hasRole('Admin')")
    public String forAdmin(){
        return "This URL is only accessible to the admin";
    }

    @GetMapping({"/forUser"})
    @PreAuthorize("hasRole('User')")
    public String forUser(){
        return "This URL is only accessible to the user";
    }


    @GetMapping({"/forSupervisor"})
    @PreAuthorize("hasRole('Supervisor')")
    public String forSuper(){
        return "This URL is only accessible to the Supervisor";
    }

}
