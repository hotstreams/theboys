package com.theboys.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HeroTO {
    private Integer id;
    private String name;
    private double rating;
    private String abilities;
    private String description;
    private Boolean alreadyRatedByUser;
    private Boolean subscribed;
}
