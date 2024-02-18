package com.theboys.data.entities;

import java.io.Serializable;

public class SubscriptionId implements Serializable {
    private int userId;
    private int heroId;

    public SubscriptionId(int userId, int heroId) {
        this.userId = userId;
        this.heroId = heroId;
    }
}
