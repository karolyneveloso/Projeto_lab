package com.example.projetoLab.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDTO(

                @NotBlank String nameUser,

                @NotBlank String cpf,

                @Email @NotBlank String email,

                @NotBlank String password

) {
}