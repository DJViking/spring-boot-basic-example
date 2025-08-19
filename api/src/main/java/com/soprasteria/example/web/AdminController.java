package com.soprasteria.example.web;

import com.soprasteria.example.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @RequestMapping("/status")
    public String status() {
        return adminService.getStatus();
    }

}
