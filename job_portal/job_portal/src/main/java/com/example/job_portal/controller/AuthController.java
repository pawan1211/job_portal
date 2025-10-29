package com.example.job_portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.job_portal.dto.AuthRequests.LoginRequest;
import com.example.job_portal.dto.AuthRequests.RegisterCandidate;
import com.example.job_portal.dto.AuthRequests.RegisterRecruiter;
import com.example.job_portal.sevices.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;


    @Autowired
  
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register/candidate")
    public ResponseEntity<?> registerCandidate(@RequestBody RegisterCandidate req) {
        return ResponseEntity.ok(authService.registerCandidate(req));
    }

    @PostMapping("/register/recruiter")
    public ResponseEntity<?> registerRecruiter(@RequestBody RegisterRecruiter req) {
        return ResponseEntity.ok(authService.registerRecruiter(req));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }
}
