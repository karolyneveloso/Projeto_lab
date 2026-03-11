package com.example.projetoLab.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.projetoLab.domain.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByemail(String email);
}
