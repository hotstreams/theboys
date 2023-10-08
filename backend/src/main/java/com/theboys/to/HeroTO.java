package com.theboys.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeroTO {
    private Integer id;
    private String name;
    private float rating;
    private String abilities;
    private String description;
}
