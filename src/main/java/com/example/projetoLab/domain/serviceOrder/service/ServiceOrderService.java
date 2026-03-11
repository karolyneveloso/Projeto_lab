package com.example.projetoLab.domain.serviceOrder.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.projetoLab.domain.serviceOrder.dto.ServiceOrderRequestDTO;
import com.example.projetoLab.domain.serviceOrder.dto.ServiceOrderResponseDTO;
import com.example.projetoLab.domain.serviceOrder.model.ServiceOrder;
import com.example.projetoLab.domain.serviceOrder.model.ServiceStatus;
import com.example.projetoLab.domain.serviceOrder.repository.ServiceOrderRepository;
import com.example.projetoLab.domain.user.model.User;
import com.example.projetoLab.domain.user.model.UserRole;
import com.example.projetoLab.infra.exception.BusinessRuleException;
import com.example.projetoLab.infra.exception.ForbiddenOperationException;
import com.example.projetoLab.infra.exception.ResourceNotFoundException;

@Service
public class ServiceOrderService {

    private final ServiceOrderRepository serviceOrderRepository;

    private static final Map<ServiceStatus, Set<ServiceStatus>> ALLOWED_TRANSITIONS = Map.of(
            ServiceStatus.SENT, Set.of(ServiceStatus.RECEIVED, ServiceStatus.CANCELED),
            ServiceStatus.RECEIVED, Set.of(ServiceStatus.IN_PRODUCTION, ServiceStatus.CANCELED),
            ServiceStatus.IN_PRODUCTION,
            Set.of(ServiceStatus.ADJUSTMENT, ServiceStatus.FINISHED, ServiceStatus.CANCELED),
            ServiceStatus.ADJUSTMENT, Set.of(ServiceStatus.FINISHED, ServiceStatus.CANCELED),
            ServiceStatus.FINISHED, Set.of(),
            ServiceStatus.CANCELED, Set.of());

    public ServiceOrderService(ServiceOrderRepository serviceOrderRepository) {
        this.serviceOrderRepository = serviceOrderRepository;
    }

    @Transactional
    public ServiceOrderResponseDTO createOs(ServiceOrderRequestDTO dto, User user) {
        validateDentistRole(user);

        ServiceOrder order = new ServiceOrder();
        order.setPatientName(dto.patientName());
        order.setColor(dto.color());
        order.setMaterial(dto.material());
        order.setSize(dto.size());
        order.setDescription(dto.description());
        order.setDentist(user);
        order.setStatus(ServiceStatus.SENT);

        ServiceOrder savedOrder = serviceOrderRepository.save(order);
        return new ServiceOrderResponseDTO(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<ServiceOrderResponseDTO> listOs(User user) {
        if (user.getRole() == UserRole.ADMIN) {
            return serviceOrderRepository.findAll()
                    .stream()
                    .map(ServiceOrderResponseDTO::new)
                    .toList();
        }

        return serviceOrderRepository.findAllByDentist_Id(user.getId())
                .stream()
                .map(ServiceOrderResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ServiceOrderResponseDTO findById(Long id, User user) {
        return new ServiceOrderResponseDTO(getOrder(id, user));
    }

    @Transactional
    public ServiceOrderResponseDTO updateStatus(Long orderId, ServiceStatus status, User user) {
        validateAdminRole(user);

        ServiceOrder order = serviceOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Service order not found."));

        validateStatusTransition(order.getStatus(), status);

        order.setStatus(status);

        ServiceOrder savedOrder = serviceOrderRepository.save(order);
        return new ServiceOrderResponseDTO(savedOrder);
    }

    @Transactional
    public void deleteOs(Long id, User user) {
        ServiceOrder order = getOrder(id, user);
        serviceOrderRepository.delete(order);
    }

    private ServiceOrder getOrder(Long id, User user) {
        ServiceOrder order = serviceOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service order not found."));

        validateAccess(order, user);
        return order;
    }

    private void validateAccess(ServiceOrder order, User user) {
        if (user.getRole() == UserRole.DENTIST &&
                !order.getDentist().getId().equals(user.getId())) {
            throw new ForbiddenOperationException("User is not allowed to access this service order.");
        }
    }

    private void validateDentistRole(User user) {
        if (user.getRole() != UserRole.DENTIST) {
            throw new ForbiddenOperationException("Only dentists can create service orders.");
        }
    }

    private void validateAdminRole(User user) {
        if (user.getRole() != UserRole.ADMIN) {
            throw new ForbiddenOperationException("Only admins can update service order status.");
        }
    }

    private void validateStatusTransition(ServiceStatus currentStatus, ServiceStatus nextStatus) {
        Set<ServiceStatus> allowedNext = ALLOWED_TRANSITIONS.getOrDefault(currentStatus, Set.of());

        if (!allowedNext.contains(nextStatus)) {
            throw new BusinessRuleException(
                    "It is not allowed to change status from " + currentStatus + " to " + nextStatus + ".");
        }
    }
}