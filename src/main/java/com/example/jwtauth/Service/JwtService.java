package com.example.jwtauth.Service;


import com.example.jwtauth.DAO.UserDAO;
import com.example.jwtauth.Entity.JwtRequest;
import com.example.jwtauth.Entity.JwtResponse;
import com.example.jwtauth.Entity.User;
import com.example.jwtauth.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {


    @Autowired
    private UserDAO userDAO;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception{
        String username=jwtRequest.getUsername();
        String userpassword=jwtRequest.getUserPassword();
        authenticate(username,userpassword);
        final UserDetails userDetails=loadUserByUsername(username);
        String newGenratedToken= jwtUtil.generateToken(userDetails);
        User user=userDAO.findById(username).get();
        return new JwtResponse(user,newGenratedToken);

    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findById(username).get();
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(), user.getUserPassword(), getAuthorities(user));
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }


    private Set getAuthorities(User user) {
        Set authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return authorities;
    }



    private void authenticate(String username,String userpassword) throws Exception{
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,userpassword));

        }
        catch (DisabledException e){
            throw new Exception("user is disabled");
        }
        catch (BadCredentialsException e){
            throw new Exception("invalid credentials");
        }
    }
}
