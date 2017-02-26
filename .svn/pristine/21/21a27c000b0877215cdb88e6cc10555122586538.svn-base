package cn.itcast.erp.action.sysadmin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;









import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.erp.action.BaseAction;
import cn.itcast.erp.common.SysConstant;
import cn.itcast.erp.pojo.Dept;
import cn.itcast.erp.pojo.Role;
import cn.itcast.erp.pojo.User;
import cn.itcast.erp.service.DeptService;
import cn.itcast.erp.service.RoleService;
import cn.itcast.erp.service.UserService;
import cn.itcast.erp.utils.Page;

public class UserAction extends BaseAction implements ModelDriven<User> {

	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	private DeptService deptService;
	
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}
	private RoleService roleService;
	
	
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	public void setModel(User model) {
		this.model = model;
	}
	private User model = new User();
	
	@Override
	public User getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	private String roleIds;
	
	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}


	Page page = new Page();

	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
	public String list() throws Exception{
		
		String hql="from User";
		
		userService.findPage(hql, page, User.class, null);
		
		page.setUrl("userAction_list");
		
		this.push(page);
		
		return "list";
	}
	
	public String toview() throws Exception{
		
		User user = userService.get(User.class, model.getId());
		
		this.push(user);
		
		return "toview";
		
	}
	
	
	
	public String tocreate() throws Exception{
	
		List<Dept> deptList = deptService.find("from Dept where state = 1", Dept.class, null);
		
		List<User> userList = userService.find("from User where state = 1", User.class, null);
		
		this.put("deptList", deptList);
		this.put("userList", userList);
		return "tocreate";
	}
	
	public String insert() throws Exception{
		
		userService.saveOrUpdate(model);	
		
		return SUCCESS;
	}
	
	public String toupdate() throws Exception{
		
		
		List<Dept> deptList = deptService.find("from Dept where state = 1", Dept.class, null);
		
		User user = userService.get(User.class, model.getId());
		
		this.put("deptList", deptList);
		this.push(user);
		
		return "toupdate";
		
	}
	
	public String update() throws Exception{
		
		userService.saveOrUpdate(model);
		
		return SUCCESS;
	}
	
	
	public String delete() throws Exception{
		
		String[] ids = model.getId().split(", ");
		userService.delete(User.class,ids);
		
		return SUCCESS;
	}
	
	public String torole() throws Exception{
		
		
		User user = userService.get(User.class, model.getId());
		
		this.push(user);
//		User user = (User) session.get(SysConstant.CURRENT_USER_INFO);
		super.push(user);
		List<Role> roleList = roleService.find("from Role", Role.class, null);
		
		super.put("roleList", roleList);
		
		Set<Role> roles = user.getRoles();
		
		StringBuffer sb = new StringBuffer();
			
			for (Role role : roles) {
				sb.append(role.getName()+",");
			}
		
		super.put("userRoleStr", sb.toString());
		
		return "torole";
	}
	public String role() throws Exception{
		
		
		User user = userService.get(User.class, model.getId());
		
		
		String[] ids = roleIds.split(", ");
		
		Set<Role> set = new HashSet<Role>();
		
		for (	String id: ids) {
			Role role = roleService.get(Role.class,id);
			
			set.add(role);
		}
		user.setRoles(set);
		userService.saveOrUpdate(user);
		
		return list();
	}
		
	
}
