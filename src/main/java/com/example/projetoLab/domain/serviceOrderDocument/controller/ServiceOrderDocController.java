package com.example.projetoLab.domain.serviceOrderDocument.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.projetoLab.domain.serviceOrderDocument.dto.ServiceOrderDocRequestDTO;
import com.example.projetoLab.domain.serviceOrderDocument.dto.ServiceOrderDocResponseDTO;
import com.example.projetoLab.domain.serviceOrderDocument.service.ServiceOrderDocService;
import com.example.projetoLab.domain.user.model.User;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/service-order-documents")
public class ServiceOrderDocController {

    private final ServiceOrderDocService serviceOrderDocService;

    public ServiceOrderDocController(ServiceOrderDocService serviceOrderDocService) {
        this.serviceOrderDocService = serviceOrderDocService;
    }

    @GetMapping("/service-order/{serviceOrderId}")
    public List<ServiceOrderDocResponseDTO> listByServiceOrder(
            @PathVariable Long serviceOrderId,
            @AuthenticationPrincipal User user) {
        return serviceOrderDocService.listDocumentsByServiceOrder(serviceOrderId, user);
    }

    @GetMapping("/{id}")
    public ServiceOrderDocResponseDTO findById(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        return serviceOrderDocService.findById(id, user);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceOrderDocResponseDTO save(
            @RequestBody @Valid ServiceOrderDocRequestDTO dto,
            @AuthenticationPrincipal User user) {
        return serviceOrderDocService.save(dto, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        serviceOrderDocService.delete(id, user);
    }
}