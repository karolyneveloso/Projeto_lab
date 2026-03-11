package com.example.projetoLab.domain.serviceOrder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.projetoLab.domain.serviceOrder.model.ServiceOrder;

import java.util.List;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
    List<ServiceOrder> findAllByDentist_Id(Long dentistId);
}