package com.mouridedev.security_token.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @GetMapping("/hello")
    public ResponseEntity<String> helloUser() {
        return ResponseEntity.ok("Hello User");
    }
}
