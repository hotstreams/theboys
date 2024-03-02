package com.theboys.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HeroCreationOrderResponseTO {
    private Integer orderId;
    private List<String> skillNames;
}
