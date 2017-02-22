package cn.itcast.erp.pojo;

import java.util.HashSet;
import java.util.Set;

public class User extends BaseEntity {
	
	private String id;
	
	private Dept dept;
	
	private String userName;
	
	private String password;
	
	private Integer state;

	private UserInfo userInfo;
	
	private Set<Role> roles = new HashSet<Role>();
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}



	@Override
	public String toString() {
		return "User [id=" + id + ", dept=" + dept + ", userName=" + userName
				+ ", password=" + password + ", state=" + state + ", userInfo="
				+ userInfo + "]";
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	
	
	
	
}
