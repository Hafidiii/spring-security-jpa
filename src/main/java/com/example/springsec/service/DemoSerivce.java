package com.example.springsec.service;

import com.example.springsec.model.User;

import java.util.List;

public interface DemoSerivce {
    List<User> getAllAdmin();
    Object getAllUser();
    String getHome();
}
