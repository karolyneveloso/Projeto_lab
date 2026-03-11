package com.example.projetoLab.domain.auth.service;

import com.example.projetoLab.domain.auth.dto.AuthenticationDTO;
import com.example.projetoLab.domain.auth.dto.LoginResponseDTO;
import com.example.projetoLab.domain.user.dto.RegisterDTO;
import com.example.projetoLab.domain.user.model.User;
import com.example.projetoLab.domain.user.model.UserRole;
import com.example.projetoLab.domain.user.repository.UserRepository;
import com.example.projetoLab.infra.security.TokenService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthenticationManager authenticationManager,
            UserRepository userRepository,
            TokenService tokenService,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponseDTO login(AuthenticationDTO data) {

        try {

            UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
                    data.email(),
                    data.password());

            var auth = authenticationManager.authenticate(usernamePassword);

            String token = tokenService.generateToken((User) auth.getPrincipal());

            return new LoginResponseDTO(token);

        } catch (BadCredentialsException err) {
            throw new RuntimeException("Invalid email or password");
        } catch (Exception err) {
            throw new RuntimeException("Error when logging in", err);
        }
    }

    public void register(RegisterDTO data) {

        try {

            if (userRepository.findByemail(data.email()) != null) {
                throw new RuntimeException("Email já cadastrado");
            }

            String encryptedPassword = passwordEncoder.encode(data.password());

            User newUser = new User(
                    data.nameUser(),
                    data.cpf(),
                    data.email(),
                    encryptedPassword,
                    UserRole.DENTIST);

            userRepository.save(newUser);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao registrar usuário", e);
        }
    }
}