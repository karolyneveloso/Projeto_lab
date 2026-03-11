package com.example.projetoLab.domain.serviceOrder.dto;

import com.example.projetoLab.domain.serviceOrder.model.ServiceOrder;
import com.example.projetoLab.domain.serviceOrder.model.ServiceStatus;

public record ServiceOrderResponseDTO(Long id, String patientName, String color, String material, String size,
        String description, ServiceStatus status) {

    public ServiceOrderResponseDTO(ServiceOrder serviceorder) {
        this(serviceorder.getId(), serviceorder.getPatientName(), serviceorder.getColor(),
                serviceorder.getMaterial(), serviceorder.getSize(), serviceorder.getDescription(),
                serviceorder.getStatus());

    }
}
