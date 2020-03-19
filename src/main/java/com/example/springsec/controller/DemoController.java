package com.example.springsec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping(value = "/")
    public String home(){
        return ("<h2> Page d'accueil</h2>");
    }

    @GetMapping(value = "/admin")
    public String admin(){
        return ("<h2> Page Admin</h2>");
    }

    @GetMapping(value = "/user")
    public String user(){
        return ("<h2> Page User</h2>");
    }
}
