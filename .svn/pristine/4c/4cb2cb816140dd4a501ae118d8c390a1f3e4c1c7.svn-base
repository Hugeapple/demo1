package cn.itcast.erp.pojo;

import java.util.HashSet;
import java.util.Set;

public class Dept {
	
	//编号
	private String id;
	
	//名字
	private String deptName;
	
	//父部门：自关联
	private Dept parent;
	
	//状态
	private Integer state;
	
	//关联用户
	private Set<User> users = new HashSet<User>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Dept getParent() {
		return parent;
	}

	public void setParent(Dept parent) {
		this.parent = parent;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Dept [id=" + id + ", deptName=" + deptName + ", parent="
				+ parent + ", state=" + state + "]";
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	
	

}
