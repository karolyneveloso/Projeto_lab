package com.example.projetoLab.domain.serviceOrder.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.projetoLab.domain.serviceOrder.dto.ServiceOrderRequestDTO;
import com.example.projetoLab.domain.serviceOrder.dto.ServiceOrderResponseDTO;
import com.example.projetoLab.domain.serviceOrder.model.ServiceStatus;
import com.example.projetoLab.domain.serviceOrder.service.ServiceOrderService;
import com.example.projetoLab.domain.user.model.User;

import jakarta.validation.Valid;;

@RestController
@RequestMapping("/serviceOrder")
public class ServiceOrderController {

    private final ServiceOrderService serviceOrderService;

    public ServiceOrderController(ServiceOrderService serviceOrderService) {
        this.serviceOrderService = serviceOrderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceOrderResponseDTO createOs(
            @RequestBody @Valid ServiceOrderRequestDTO dto,
            @AuthenticationPrincipal User user) {
        return serviceOrderService.createOs(dto, user);
    }

    @GetMapping
    public List<ServiceOrderResponseDTO> listOs(@AuthenticationPrincipal User user) {
        return serviceOrderService.listOs(user);
    }

    @GetMapping("/{id}")
    public ServiceOrderResponseDTO findById(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        return serviceOrderService.findById(id, user);
    }

    @PatchMapping("/{id}/status")
    public ServiceOrderResponseDTO updateStatus(
            @PathVariable Long id,
            @RequestParam ServiceStatus status,
            @AuthenticationPrincipal User user) {
        return serviceOrderService.updateStatus(id, status, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOs(@PathVariable Long id,
            @AuthenticationPrincipal User user) {
        serviceOrderService.deleteOs(id, user);
    }
}