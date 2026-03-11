package com.example.projetoLab.domain.serviceOrder.dto;

import jakarta.validation.constraints.NotBlank;

public record ServiceOrderRequestDTO(

        @NotBlank String patientName,
        @NotBlank String color,
        @NotBlank String material,
        @NotBlank String size,
        String description) {
}
