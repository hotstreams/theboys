package com.theboys.data.entities;

import jakarta.persistence.*;

@Entity
@IdClass(RateId.class)
@Table(name = "user_hero_rates")
public class UserHeroRate {
    @Column(name = "hero_id")
    @Id
    int heroId;

    @Column(name = "user_id")
    @Id
    int userId;

    @Column(name = "rate")
    int rate;
}
