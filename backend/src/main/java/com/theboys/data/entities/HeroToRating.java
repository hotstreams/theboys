package com.theboys.data.entities;

public class HeroToRating {
    private int heroId;
    private double rating;

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public HeroToRating(int heroId, double rating) {
        this.heroId = heroId;
        this.rating = rating;
    }
}
