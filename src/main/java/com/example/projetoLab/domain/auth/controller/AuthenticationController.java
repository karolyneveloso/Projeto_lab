package com.example.projetoLab.domain.auth.controller;

import com.example.projetoLab.domain.auth.dto.AuthenticationDTO;
import com.example.projetoLab.domain.auth.dto.LoginResponseDTO;
import com.example.projetoLab.domain.auth.service.AuthenticationService;
import com.example.projetoLab.domain.user.dto.RegisterDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        LoginResponseDTO response = authenticationService.login(data);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data) {
        authenticationService.register(data);
        return ResponseEntity.ok().build();
    }
}