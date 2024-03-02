package com.theboys.to;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HeroCreationOrderTO {
    @NotNull
    private Integer orderId;
    @NotNull
    @NotEmpty
    private List<String> skillNames;
}
