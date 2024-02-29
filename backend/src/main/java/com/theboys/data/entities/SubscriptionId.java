package com.theboys.data.entities;

import java.io.Serializable;

public class SubscriptionId implements Serializable {
    private int userId;
    private int heroId;

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
