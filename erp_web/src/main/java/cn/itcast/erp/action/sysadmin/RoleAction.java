package cn.itcast.erp.action.sysadmin;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.erp.action.BaseAction;
import cn.itcast.erp.pojo.Module;
import cn.itcast.erp.pojo.Role;
import cn.itcast.erp.service.ModuleService;
import cn.itcast.erp.service.RoleService;
import cn.itcast.erp.utils.Page;

public class RoleAction extends BaseAction implements ModelDriven<Role>{
	private Role model = new Role();
	
	@Override
	public Role getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	
	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	private Page page = new Page();

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	private ModuleService moduleService;
	
	public void setModel(Role model) {
		this.model = model;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public String list() throws Exception{
		
		roleService.findPage("from Role", page, Role.class, null);
			
		
		page.setUrl("roleAction_list");
		
		this.push(page);
		return "list";
	}
	
	
	public String toview() throws Exception{
		
		Role role = roleService.get(Role.class, model.getId());
		
		this.push(role);
		
		return "toview";
		
		
	}
	
	public String tocreate() throws Exception{
		
		return "tocreate";
	}
	
	
	public String insert(){
		roleService.saveOrUpdate(model);
		return SUCCESS;
	}
	public String toupdate() throws Exception {
		Role role = roleService.get(Role.class, model.getId());
		this.push(role);
		
		return "toupdate";
	}
	
	public String update() throws Exception{
		
		roleService.saveOrUpdate(model);
		
		return SUCCESS;
		
	}
	
	
	public String delete() throws Exception {
		Serializable[] ids = model.getId().split(", ");
		
		roleService.delete(Role.class, ids);
		
		return SUCCESS;
	}
	
	public String tomodule() throws Exception{
		
		Role role = roleService.get(Role.class, model.getId());
		
		
		super.push(role);
		
		return "tomodule";
		
	}
	
	public String roleModuleJsonStr() throws Exception{
		
		
		List<Module> module = moduleService.find("from Module where state=1", Module.class, null);
		
		Role role = roleService.get(Role.class,model.getId());
		
		Set<Module> modules = role.getModules();
		
		int size=module.size();
		//组织数据json
		
		StringBuffer json = new StringBuffer();
		
	
		json.append("[");
		
		
		for (Module m : module) {
			
			size--;
			json.append("{");
			json.append("id:'"+m.getId()+"',");
			json.append("pId:'"+m.getParentId()+"',");
			json.append("name:'"+m.getName()+"',");
			
			
			boolean flag = true;
			
			if(!modules.contains(m)){
				flag =false;
			}
			json.append("checked:'"+flag+"'");
			json.append("}");
			if(size > 0){
				json.append(",");
			}
		}

		json.append("]");
		
		System.out.println(json.toString());
		
		HttpServletResponse response = ServletActionContext.getResponse();
		
		response.setContentType("application/json;charset = UTF-8");
		
		response.setHeader("cache-control", "no-cache");
		
		response.getWriter().write(json.toString());
		
		return NONE;
	}
	
	
	private String moduleIds;

	public void setModuleIds(String moduleIds) {
		this.moduleIds = moduleIds;
	}
	
	public String module() throws Exception{
			
		String[] ids = moduleIds.split(", ");
		
		Role role = roleService.get(Role.class, model.getId());
		
		Set<Module> modules = new HashSet<Module>();
		
		for (String id : ids) {
			modules.add(moduleService.get(Module.class,id));
		}
		role.setModules(modules);
		
		roleService.saveOrUpdate(role);
		
		return SUCCESS;
			
			
	}
	
}
