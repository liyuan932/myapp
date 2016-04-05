package com.mycompany.myapp.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by wb-liyuan.j on 2016/1/26.
 */
public class UserModel {
    @NotNull
    @Length(min=5, max=20,message="{user.username.Length}")
    @Pattern(regexp = "^[a-zA-Z_]\\w{4,19}$")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
