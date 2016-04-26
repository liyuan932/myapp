package com.mycompany.myapp.vo;

import com.mycompany.myapp.utils.excel.FileName;
import com.mycompany.myapp.utils.excel.Title;
import org.apache.poi.ss.usermodel.Cell;

import java.util.Date;

@FileName("user")
public class UserVO extends BaseVO{
	private Long id;

	@Title("账户")
    private String account;

	@Title("用户名")
    private String username;

	@Title("性别")
    private Integer sex;

	@Title(value = "年龄",cellType = Cell.CELL_TYPE_NUMERIC)
    private Integer age;

    private String mobile;

    private Integer type;

    private String typeText;

    private Integer status;

    private String statusText;

	@Title("创建时间")
	private Date gmtCreate;

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
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