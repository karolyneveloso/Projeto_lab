package com.example.projetoLab.domain.serviceOrderDocument.dto;

import com.example.projetoLab.domain.serviceOrderDocument.model.ServiceOrderDocument;

public record ServiceOrderDocResponseDTO(Long id, String fileName, String contentType, String filePath,
        Long serviceOrderId) {
    public ServiceOrderDocResponseDTO(ServiceOrderDocument document) {
        this(
                document.getId(),
                document.getFileName(),
                document.getContentType(),
                document.getFilePath(),
                document.getServiceOrder().getId());
    }
}