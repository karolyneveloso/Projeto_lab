package com.example.projetoLab.domain.serviceOrderDocument.service;

import com.example.projetoLab.domain.serviceOrder.model.ServiceOrder;
import com.example.projetoLab.domain.serviceOrder.repository.ServiceOrderRepository;
import com.example.projetoLab.domain.serviceOrderDocument.dto.ServiceOrderDocRequestDTO;
import com.example.projetoLab.domain.serviceOrderDocument.dto.ServiceOrderDocResponseDTO;
import com.example.projetoLab.domain.serviceOrderDocument.model.ServiceOrderDocument;
import com.example.projetoLab.domain.serviceOrderDocument.repository.ServiceOrderDocRepository;
import com.example.projetoLab.domain.user.model.User;
import com.example.projetoLab.domain.user.model.UserRole;
import com.example.projetoLab.infra.exception.ForbiddenOperationException;
import com.example.projetoLab.infra.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceOrderDocService {

    private final ServiceOrderDocRepository repository;
    private final ServiceOrderRepository serviceOrderRepository;

    public ServiceOrderDocService(ServiceOrderDocRepository repository,
            ServiceOrderRepository serviceOrderRepository) {
        this.repository = repository;
        this.serviceOrderRepository = serviceOrderRepository;
    }

    @Transactional(readOnly = true)
    public List<ServiceOrderDocResponseDTO> listDocumentsByServiceOrder(Long serviceOrderId, User user) {
        ServiceOrder order = findOrderById(serviceOrderId);
        validateAccess(order, user);

        return repository.findByServiceOrderId(serviceOrderId)
                .stream()
                .map(ServiceOrderDocResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ServiceOrderDocResponseDTO findById(Long id, User user) {
        ServiceOrderDocument document = findDocumentById(id);
        validateAccess(document.getServiceOrder(), user);

        return new ServiceOrderDocResponseDTO(document);
    }

    @Transactional
    public ServiceOrderDocResponseDTO save(ServiceOrderDocRequestDTO dto, User user) {
        ServiceOrder order = findOrderById(dto.serviceOrderId());
        validateAccess(order, user);

        ServiceOrderDocument document = new ServiceOrderDocument();
        document.setFileName(dto.fileName());
        document.setContentType(dto.contentType());
        document.setFilePath(dto.filePath());
        document.setServiceOrder(order);

        ServiceOrderDocument savedDocument = repository.save(document);

        return new ServiceOrderDocResponseDTO(savedDocument);
    }

    @Transactional
    public void delete(Long id, User user) {
        ServiceOrderDocument document = findDocumentById(id);
        validateAccess(document.getServiceOrder(), user);

        repository.delete(document);
    }

    private ServiceOrder findOrderById(Long serviceOrderId) {
        return serviceOrderRepository.findById(serviceOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("Service order not found."));
    }

    private ServiceOrderDocument findDocumentById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found."));
    }

    private void validateAccess(ServiceOrder order, User user) {
        if (user.getRole() == UserRole.ADMIN) {
            return;
        }

        if (user.getRole() == UserRole.DENTIST &&
                order.getDentist() != null &&
                order.getDentist().getId().equals(user.getId())) {
            return;
        }

        throw new ForbiddenOperationException("User is not allowed to access this document.");
    }
}