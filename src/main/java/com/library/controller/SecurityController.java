package com.library.controller;

import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/v1/library")
@RestController
public class SecurityController {

    @PostMapping("/login")
    public String login(@RequestBody String username, String password) throws Exception {
        return "Sign in";
    }


    @PostMapping("/auth")
    public String auth(@RequestBody String token) throws Exception {
        return "Authorize";
    }


    @PostMapping("/register")
    public String register(@RequestBody User user) throws Exception {
        return" Register";
    }
}
