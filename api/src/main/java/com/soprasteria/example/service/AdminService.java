package com.soprasteria.example.service;

import org.springframework.stereotype.Service;

@Service
public class AdminService {

    public String getStatus() {
        return "Service is running";
    }

}
