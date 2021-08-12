package com.alkemy.java.security;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtResponse implements Serializable {

    private final String token;

    public String getToken() {
        return token;
    }

    public JwtResponse(String token) {
        this.token = token;
    }

}