package com.theboys.data.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "customers", schema = "theboys")
public class Customer {

    @Id
    @GeneratedValue
    @Column(name = "customer_id")
    private Integer customerId;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}