package com.theboys.to;

import com.theboys.data.enums.CreationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HeroCreationOrderResponseTO {
    private Integer orderId;
    private String description;
    private CreationStatus status;
}
