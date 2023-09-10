package com.example.jwtauth.Service;


import com.example.jwtauth.DAO.RoleDAO;
import com.example.jwtauth.DAO.UserDAO;
import com.example.jwtauth.Entity.Role;
import com.example.jwtauth.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public User createNewUser(User user) {
        Role role=roleDAO.findById("User").get();
        Set<Role>Roles=new HashSet<>();
        Roles.add(role);
        user.setRoles(Roles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));
        return userDAO.save(user);
    }

    public void initRolesAndUser(){
        Role roleAdmin=new Role();
        roleAdmin.setRoleName("Admin");
        roleAdmin.setRoleDescription("Admin role");
        roleDAO.save(roleAdmin);

        Role roleUser=new Role();
        roleUser.setRoleName("User");
        roleUser.setRoleDescription("User role");
        roleDAO.save(roleUser);


        User admin=new User();
        admin.setUserName("admin123");
        admin.setUserLastName("admin");
        admin.setUserFirstName("admin");
        admin.setUserPassword(getEncodedPassword("admin@pass"));
        Set<Role> rolesAdmin=new HashSet<>();
        rolesAdmin.add(roleAdmin);
        admin.setRoles(rolesAdmin);
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
