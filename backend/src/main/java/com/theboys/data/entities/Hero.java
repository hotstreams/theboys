package com.theboys.data.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "heroes")
public class Hero {

    @Id
    @Column(name = "hero_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer heroId;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinTable(
            name = "heroes_skills",
            joinColumns = { @JoinColumn(name = "hero_id") },
            inverseJoinColumns = { @JoinColumn(name = "skill_id") }
    )
    private Set<Skill> skills;

    @OneToMany(mappedBy = "hero")
    private List<Order> orders;

    public Integer getHeroId() {
        return heroId;
    }

    public void setHeroId(Integer heroId) {
        this.heroId = heroId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
