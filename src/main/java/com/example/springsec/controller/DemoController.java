package com.example.springsec.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping(value = "/")
    public String home(){
        return ("<h2> Page d'accueil</h2>");
    }

    @GetMapping(value = "/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String admin(){
        return ("<h2> Page Admin</h2>");
    }

    @GetMapping(value = "/user")
    @PreAuthorize("hasRole('ROLE_USER')" + " || hasRole('ROLE_ADMIN')")
    public String user(){
        return ("<h2> Page User</h2>");
    }
}
