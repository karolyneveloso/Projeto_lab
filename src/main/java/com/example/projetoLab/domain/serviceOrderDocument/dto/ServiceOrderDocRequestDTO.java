package com.example.projetoLab.domain.serviceOrderDocument.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServiceOrderDocRequestDTO(

                @NotBlank String fileName,

                @NotBlank String contentType,

                @NotBlank String filePath,

                @NotNull Long serviceOrderId) {
}