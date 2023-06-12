package com.theboys.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "skills", schema = "theboys")
public class Skill {
    @Id
    @GeneratedValue
    @Column(name = "skill_id")
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
