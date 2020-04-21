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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

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
    public Object getAuthenticationToken(@RequestBody JwtRequest request){

        Map<String, Object> map = new HashMap<>();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        }catch (BadCredentialsException e){
            map.put("message", "Oups!!! user or password incorrect");
            map.put("status", false);

            return map;
        }

        UserDetails userDetails = userDetailService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        map.put("jwt",new JwtResponse(token).getJwt());
        map.put("message","user authenticated");
        map.put("status", true);

        return map;

    }

    @GetMapping(value = "/403")
    public Object accesssDenied(Principal user) {
        Map<String, Object> json = new HashMap();

        json.put("status", "403 Forbidden");
        if (user != null) {
            json.put("message","Hi " + user.getName() + ", you do not have permission to access this page!");
        } else {
            json.put("message",
                    "You do not have permission to access this page!");
        }
        return json;
    }
}
