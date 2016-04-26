package com.mycompany.myapp.enums.export;


import com.mycompany.myapp.utils.excel.ExcelTemplate;

import java.util.List;

public enum UserTemplate implements ExcelTemplate {
    account("账户"),password("密码"),username("用户名");

    private String title;
    UserTemplate(String title) {
        this.title = title;
    }

    public String getTitle(){
        return title;
    }



    public List<String> titles(){
        for(UserTemplate e : UserTemplate.values()){

        }

        return null;
    }
}
