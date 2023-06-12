package com.theboys.data.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "managers", schema = "theboys")
public class Manager {

    @Id
    @Column(name = "manager_id")
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
