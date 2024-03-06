package com.theboys.to;

import com.theboys.data.enums.CreationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class HeroCreationStatusTO {
    @NotNull
    private CreationStatus status;
}
