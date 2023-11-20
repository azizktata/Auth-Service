package com.example.jwtauth.Service;


import com.example.jwtauth.DAO.RoleDAO;
import com.example.jwtauth.DAO.UserDAO;
import com.example.jwtauth.DTO.userDTO;
import com.example.jwtauth.DTO.userLoginDTO;
import com.example.jwtauth.Entity.Role;
import com.example.jwtauth.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void CreateUserPDS(userLoginDTO userLoginDto) {
        userDTO userDto = new userDTO();
        userDto.setEmail(userLoginDto.userEmail);
        userDto.setName(userLoginDto.userName);

        if (userLoginDto.userRole.equals("Student")){
            String apiUrl = "http://localhost:8080/api/v1/students";
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.postForObject(apiUrl, userDto, String.class);
            System.out.println(response);
        }
        else if (userLoginDto.userRole.equals("Supervisor")){
            String apiUrl = "http://localhost:8080/api/v1/supervisors";
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.postForObject(apiUrl, userDto, String.class);
            System.out.println(response);

        }
    }


    public ResponseEntity<?> createNewUser(userLoginDTO userLoginDto) {
        // Check if the username  is already taken
        if (userDAO.findByUserName(userLoginDto.userName) != null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Username is already taken.");
        }
        User user = new User();
        Role role = roleDAO.findById(userLoginDto.userRole).get();
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRole(roles);

        // Add Supervisor or Student in mongo
        CreateUserPDS(userLoginDto);


        user.setUserPassword(getEncodedPassword(userLoginDto.userPassword));
        user.setUserName(userLoginDto.userName);
        user.setUserEmail(userLoginDto.userEmail);
        User savedUser = userDAO.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser);
    }


    public void initRolesAndUser(){
        Role roleAdmin=new Role();
        roleAdmin.setRoleName("Admin");
        roleAdmin.setRoleDescription("Admin role");
        roleDAO.save(roleAdmin);

        Role roleUser=new Role();
        roleUser.setRoleName("Student");
        roleUser.setRoleDescription("Student role");
        roleDAO.save(roleUser);

        Role roleUser2=new Role();
        roleUser2.setRoleName("Supervisor");
        roleUser2.setRoleDescription("Supervisor role");
        roleDAO.save(roleUser2);


        User admin=new User();
        admin.setUserName("admin123");

        admin.setUserPassword(getEncodedPassword("admin@pass"));
        Set<Role> rolesAdmin=new HashSet<>();
        rolesAdmin.add(roleAdmin);
        admin.setRole(rolesAdmin);
        userDAO.save(admin);

//        User user=new User();
//        user.setUserName("user123");
//        user.setUserLastName("user");
//        user.setUserFirstName("user");
//        user.setUserPassword(getEncodedPassword("user@pass"));
//        Set<Role> rolesUser=new HashSet<>();
//        rolesUser.add(roleUser);
//        user.setRoles(rolesUser);
//        userDAO.save(user);

    }

    public String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }


}
