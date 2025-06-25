package com.example.smartcook.controller;

import com.example.smartcook.dto.Modaldto;
import com.example.smartcook.service.ModalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000") // local host of react
@RestController
@RequestMapping("/api/modal")
public class ModalController {

    @Autowired
    private ModalService modalService;

    @PostMapping("/login")
    public String login(@RequestBody Modaldto user) {
        boolean success = modalService.login(user.getEmail(), user.getPassword());
        return success ? "Login Successful ✅" : "Invalid credentials or not signed up ❌";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody Modaldto user) {
        boolean registered = modalService.signup(user.getEmail(), user.getPassword());
        return registered ? "Signup Successful ✅" : "User already exists ❌";
    }
}