package com.theboys.to;

import com.theboys.security.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseTO {
    Integer id;
    String username;
    UserRole role;
    String image;
}
