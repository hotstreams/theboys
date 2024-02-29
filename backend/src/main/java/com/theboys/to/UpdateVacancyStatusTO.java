package com.theboys.to;

import com.theboys.data.enums.VacancyStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UpdateVacancyStatusTO {

    @NotNull
    private VacancyStatus vacancyStatus;
}
