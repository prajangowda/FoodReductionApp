package com.prajan.FoodReduction.DTO;

import com.prajan.FoodReduction.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;

    private Role role;

    private Boolean profilecompletd;

    public LoginResponse(String token, Boolean profilecompletd) {
        this.token = token;
        this.profilecompletd = profilecompletd;
    }

    public LoginResponse(String token, Role role) {
        this.token = token;
        this.role = role;
    }

    public void setToken(String token, Role role) {
        this.token = token;
        this.role=role;
    }


}
