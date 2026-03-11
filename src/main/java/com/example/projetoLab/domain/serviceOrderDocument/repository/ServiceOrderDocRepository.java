package com.example.projetoLab.domain.serviceOrderDocument.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projetoLab.domain.serviceOrderDocument.model.ServiceOrderDocument;

public interface ServiceOrderDocRepository extends JpaRepository<ServiceOrderDocument, Long> {

    List<ServiceOrderDocument> findByServiceOrderId(Long serviceOrderId);
}
