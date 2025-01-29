package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
public class LoginDto {
    private String usernameOrEamil;
    private String password;

    public String getUsernameOrEamil() {
        return usernameOrEamil;
    }

    public void setUsernameOrEamil(String usernameOrEamil) {
        this.usernameOrEamil = usernameOrEamil;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
