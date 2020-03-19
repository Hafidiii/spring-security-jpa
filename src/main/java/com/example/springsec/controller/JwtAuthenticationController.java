package com.example.springsec.controller;

import com.example.springsec.dto.JwtRequest;
import com.example.springsec.dto.JwtResponse;
import com.example.springsec.service.UserServices;
import com.example.springsec.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserServices userDetailService;


    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> getAuthenticationToken(@RequestBody JwtRequest request){

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        }catch (BadCredentialsException e){
            throw new UsernameNotFoundException("User not found");
        }

        UserDetails userDetails = userDetailService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));

    }
}
