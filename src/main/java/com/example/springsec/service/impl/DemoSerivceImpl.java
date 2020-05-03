package com.example.springsec.service.impl;

import com.example.springsec.model.User;
import com.example.springsec.repository.UserRepository;
import com.example.springsec.service.DemoSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DemoSerivceImpl implements DemoSerivce {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllAdmin() {
        return userRepository.findAllAdminMembers();
    }

    @Override
    public  Object getAllUser() {
        return restTemplate.getForEntity("https://jsonplaceholder.typicode.com/users", Object.class).getBody();
    }

    @Override
    public String getHome() {
        return ("<h2> Page d'accueil</h2>");
    }
}
