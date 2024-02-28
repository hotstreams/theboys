package com.theboys.data.entities;

import com.theboys.security.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "managers")
public class Manager {

    @Id
    @Column(name = "manager_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer managerId;

    @OneToMany(mappedBy = "manager")
    private List<HeroCreationOrder> orders;

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public List<HeroCreationOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<HeroCreationOrder> orders) {
        this.orders = orders;
    }
}
