package com.theboys.data.entities;

import com.theboys.security.User;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "managers")
@PrimaryKeyJoinColumn(name = "manager_id")
public class Manager extends User {

    @OneToMany(mappedBy = "manager")
    private List<HeroCreationOrder> orders;

    public List<HeroCreationOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<HeroCreationOrder> orders) {
        this.orders = orders;
    }
}
