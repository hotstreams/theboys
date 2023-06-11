package com.theboys.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "users", schema = "theboys")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
}
