package com.theboys.data.entities;

import com.theboys.security.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "customers")
@PrimaryKeyJoinColumn(name = "customer_id")
public class Customer extends User {

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
