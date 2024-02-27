package com.theboys.to;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostTO {
    @NotEmpty
    String title;
    String description;
}
