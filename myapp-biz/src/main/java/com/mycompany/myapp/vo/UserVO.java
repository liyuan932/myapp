package com.mycompany.myapp.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mycompany.myapp.utils.excel.FileName;
import com.mycompany.myapp.utils.excel.Title;


@FileName("用户")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class UserVO extends BaseVO {
    private Long id;

    @Title("账户")
    private String account;

    @Title("用户名")
    private String username;

    private Integer sex;

    private Integer age;

    @Title("手机号码")
    private String mobile;

    private String address;

    private Integer type;

    private Integer status;

    @Title("用户类型")
    private String typeText;

    @Title("状态")
    private String statusText;

    @Title("创建时间")
    private String gmtCreateText;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGmtCreateText() {
        return gmtCreateText;
    }

    public void setGmtCreateText(String gmtCreateText) {
        this.gmtCreateText = gmtCreateText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }


}