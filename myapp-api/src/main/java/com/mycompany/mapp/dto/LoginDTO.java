package com.mycompany.mapp.dto;

/**
 * @author wb-liyuan.j
 * @date 2016-09-12
 */
public class LoginDTO extends BaseDTO {

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
