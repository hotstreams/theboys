package com.theboys.to;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationTO {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
