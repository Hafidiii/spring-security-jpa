package com.example.springsec.controller;

import com.example.springsec.service.DemoSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DemoController {

    @Autowired
    DemoSerivce demoSerivce;

    @GetMapping(value = "/")
    public String home(){
        return demoSerivce.getHome();
    }

    @GetMapping(value = "/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Object getAllAdmin(){
        Map<String, Object> map = new HashMap<>();
        map.put("admins", demoSerivce.getAllAdmin());
        map.put("total", demoSerivce.getAllAdmin().size());
        return map;
    }

    @GetMapping(value = "/user")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public Object getAllUser(){
        Map<String, Object> map = new HashMap<>();
        map.put("users", demoSerivce.getAllUser());
        return map;
    }
}
