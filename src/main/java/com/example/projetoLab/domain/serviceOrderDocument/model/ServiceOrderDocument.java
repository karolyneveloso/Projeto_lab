package com.example.projetoLab.domain.serviceOrderDocument.model;

import com.example.projetoLab.domain.serviceOrder.model.ServiceOrder;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "service_order_doc")
public class ServiceOrderDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String contentType;

    private String filePath;

    @ManyToOne
    @JoinColumn(name = "service_order_id", nullable = false)
    private ServiceOrder serviceOrder;

    public ServiceOrderDocument() {
    }

    public ServiceOrderDocument(String fileName, String contentType, String filePath, ServiceOrder serviceOrder) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.filePath = filePath;
        this.serviceOrder = serviceOrder;
    }

}