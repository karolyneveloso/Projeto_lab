package com.example.projetoLab.domain.user.model;

public enum UserRole {
    ADMIN("ADMIN"),
    DENTIST("DENTIST");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
