package com.example.projetoLab.domain.serviceOrder.model;

import java.util.ArrayList;
import java.util.List;

import com.example.projetoLab.domain.serviceOrderDocument.model.ServiceOrderDocument;
import com.example.projetoLab.domain.user.model.User;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "service_order")
@Getter
@Setter
public class ServiceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_name", nullable = false, length = 120)
    private String patientName;

    @Column(nullable = false, length = 50)
    private String color;

    @Column(nullable = false, length = 80)
    private String material;

    @Column(nullable = false, length = 50)
    private String size;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ServiceStatus status = ServiceStatus.SENT;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "dentist_id", nullable = false)
    private User dentist;

    @OneToMany(mappedBy = "serviceOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceOrderDocument> documents = new ArrayList<>();

    public ServiceOrder() {
    }

    public ServiceOrder(String patientName, String color, String material, String size, String description,
            User dentist) {
        this.patientName = patientName;
        this.color = color;
        this.material = material;
        this.size = size;
        this.description = description;
        this.dentist = dentist;
        this.status = ServiceStatus.SENT;
    }
}