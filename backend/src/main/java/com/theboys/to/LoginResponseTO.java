package com.theboys.to;

import com.theboys.security.UserRole;
import lombok.*;

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
