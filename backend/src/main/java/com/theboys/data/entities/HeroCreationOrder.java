package com.theboys.data.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "hero_creation_orders", schema = "theboys")
public class HeroCreationOrder {

    @Id
    @Column(name = "hero_creation_order_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer heroCreationOrderId;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "orders_skills",
            schema = "theboys",
            joinColumns = { @JoinColumn(name = "order_id")},
            inverseJoinColumns = { @JoinColumn(name = "skill_id")}
    )
    private Set<Skill> skills;


    public Integer getHeroCreationOrderId() {
        return heroCreationOrderId;
    }

    public void setHeroCreationOrderId(Integer heroCreationOrderId) {
        this.heroCreationOrderId = heroCreationOrderId;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }
}
