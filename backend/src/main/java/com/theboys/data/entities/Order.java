package com.theboys.data.entities;

import com.theboys.data.enums.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "orders", schema = "theboys")
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer order_id;

    @ManyToOne(targetEntity = Hero.class)
    @JoinColumn(name = "hero_id")
    private Hero hero;

    @ManyToOne(targetEntity = Customer.class, optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "request_description")
    private String requestDescription;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @Column(name = "hero_description")
    private String heroDescription;

    public Order(Customer customer, LocalDate date, LocalDate startDate, LocalDate endDate,
                 String requestDescription, OrderStatus status, String heroDescription) {
        this.customer = customer;
        this.date = date;
        this.startDate = startDate;
        this.endDate = endDate;
        this.requestDescription = requestDescription;
        this.status = status;
        this.heroDescription = heroDescription;
    }

    public Order(Hero hero, Customer customer, LocalDate date, LocalDate startDate, LocalDate endDate,
                 String requestDescription, OrderStatus status, String heroDescription) {
        this(customer, date, startDate, endDate, requestDescription, status, heroDescription);
        this.hero = hero;
    }


}
