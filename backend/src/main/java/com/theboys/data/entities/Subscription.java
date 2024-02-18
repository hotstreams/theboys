package com.theboys.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "user_hero_subscriptions", schema = "theboys")
@IdClass(SubscriptionId.class)
public class Subscription {
    @Id
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Id
    @Column(name = "hero_id", nullable = false)
    private int heroId;

    public Subscription() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }
}
