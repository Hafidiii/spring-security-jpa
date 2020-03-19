package com.example.springsec.dto;

public class JwtResponse {

    private String jwt;

    public JwtResponse(String token) {
        this.jwt = token;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
