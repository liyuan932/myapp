package com.mycompany.mapp.dto;

/**
 * @author wb-liyuan.j
 * @date 2016-09-12
 */
public class LoginDTO extends BaseDTO {

    private String account;
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
