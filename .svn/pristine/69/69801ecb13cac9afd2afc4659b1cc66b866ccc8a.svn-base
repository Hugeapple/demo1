package cn.itcast.erp.action.sysadmin;

import java.io.Serializable;

import cn.itcast.erp.action.BaseAction;
import cn.itcast.erp.pojo.Module;
import cn.itcast.erp.service.ModuleService;
import cn.itcast.erp.utils.Page;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 说明：
 * @author bigApple
 * @version 1.0
 * @date 2017年2月18日
 */
public class ModuleAction extends BaseAction implements ModelDriven<Module>{
	
	private Module model = new Module();
	@Override
	public Module getModel() {
		return model;
	}
	
	private ModuleService moduleService;
	public void setModel(Module model) {
		this.model = model;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}
	
	Page page = new Page();
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
	
	
	public String list() throws Exception{
		
		moduleService.findPage("from Module", page, Module.class, null);
		
		page.setUrl("moduleAction_list");
		
		this.push(page);
		
		return "list";
		
	}
public String toview() throws Exception {
		
		Module module = moduleService.get(Module.class, model.getId());
		this.push(module);
		
		return "toview";
	}
	
	public String tocreate() throws Exception {
		return "tocreate";
	}
	
	public String insert() throws Exception {
		
		moduleService.saveOrUpdate(model);
		
		return SUCCESS;
	}
	
	public String toupdate() throws Exception {
		//准备数据
		Module module = moduleService.get(Module.class, model.getId());
		this.push(module);
		
		return "toupdate";
	}
	
	public String update() throws Exception {
		
		moduleService.saveOrUpdate(model);
		
		return SUCCESS;
	}
	
	public String delete() throws Exception {
		Serializable[] ids = model.getId().split(", ");
		
		moduleService.delete(Module.class, ids);
		
		return SUCCESS;
	}
	
	
}
