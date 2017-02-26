package cn.itcast.erp.action.cargo;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.erp.action.BaseAction;
import cn.itcast.erp.common.SysConstant;
import cn.itcast.erp.pojo.Factory;
import cn.itcast.erp.service.FactoryService;
import cn.itcast.erp.utils.Page;

public class FactoryAction extends BaseAction implements ModelDriven<Factory>{
	
	private Factory model = new Factory();
	private FactoryService factoryService;
	Page page = new Page();
	
	
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public void setfactoryService(FactoryService factoryService) {
		this.factoryService = factoryService;
	}

	@Override
	public Factory getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	public void setModel(Factory model) {
		this.model = model;
	}
	
	
	public String list() throws Exception {
		
		
		
		
		
		
		
		String hql="from Factory";
		factoryService.findPage(hql, page, Factory.class, null);
		
		page.setUrl("FactoryAction_list");
		
		ActionContext.getContext().getValueStack().push(page);
		
		return "list";
	}
	
	
	public String toview() throws Exception{
		
		Factory Factory = factoryService.get(Factory.class,model.getId());
		
		ActionContext.getContext().getValueStack().push(Factory);
		return "toView";
	}
	
	public String tocreate() throws Exception{
		
		String hql="from Factory";
		
		
		List<Factory> factoryList = factoryService.find(hql, Factory.class, null);
		
		for (int i = 0; i < factoryList.size(); i++) {
			if(factoryList.get(i).getState()==0){
				factoryList.remove(i);
			}
			
			
		}
		super.put("FactoryList", factoryList);
		
		return "toCreate";
	}
	
	public String insert() throws Exception{
		
		factoryService.saveOrUpdate(model);
		
		
		return list();
	}
	
	public String toupdate()throws Exception{
		
		String hql = "from Factory where state ="+SysConstant.Factory_STATE_ENABLED;
		
		List<Factory> FactoryList = factoryService.find(hql, Factory.class, null);
		
		Factory Factory = factoryService.get(Factory.class, model.getId());
		
		FactoryList.remove(Factory);
		
		this.push(Factory);
		
		this.put("FactoryList", FactoryList);
		
		return "toUpdate";
	
	}
	
	public String update() throws Exception{
		
		 factoryService.saveOrUpdate(model);
		
		
		return SUCCESS;
	}
	public String delete() throws Exception{
		Object object = ActionContext.getContext().getSession().get(model);
		String[] ids = model.getId().split(", ");	
		factoryService.delete(Factory.class, ids);
		
		return SUCCESS;
	}
	
	
}
