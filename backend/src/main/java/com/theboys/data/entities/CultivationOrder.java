package com.theboys.data.entities;

import com.theboys.data.enums.CreationStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "hero_cultivation_orders")
@Data
public class CultivationOrder {

    @Id
    @Column(name = "hero_cultivation_order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer heroCreationOrderId;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CreationStatus status;

    @Column(name = "description")
    private String description;
}
