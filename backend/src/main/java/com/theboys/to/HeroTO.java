package com.theboys.to;

import lombok.*;

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
}
