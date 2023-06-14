package com.theboys.to;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationTO {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
