package com.theboys.to;

import com.theboys.security.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseTO {
    Integer id;
    String username;
    UserRole role;
    String image;
}
