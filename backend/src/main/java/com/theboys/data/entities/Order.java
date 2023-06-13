package com.theboys.data.entities;

import com.theboys.data.enums.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer orderId;

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

    public Order() {
    }

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

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getHeroDescription() {
        return heroDescription;
    }

    public void setHeroDescription(String heroDescription) {
        this.heroDescription = heroDescription;
    }
}
