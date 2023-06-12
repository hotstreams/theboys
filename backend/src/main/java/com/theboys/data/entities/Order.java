package com.theboys.data.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "orders", schema = "theboys")
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Integer order_id;

    @ManyToOne(targetEntity = Hero.class)
    @JoinColumn(name = "hero_id")
    private Hero hero;

    @ManyToOne(targetEntity = Customer.class, optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "cost")
    private Integer cost;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @Column(name = "hero_description")
    private String heroDescription;


}
