package com.theboys.to;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

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
