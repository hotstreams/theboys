package com.theboys.to;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostTO {
    @NotEmpty
    String title;
    String description;
    OffsetDateTime at;
    String heroName;
}
