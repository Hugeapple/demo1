package cn.itcast.erp.action.sysadmin;

import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.erp.action.BaseAction;
import cn.itcast.erp.common.SysConstant;
import cn.itcast.erp.pojo.Dept;
import cn.itcast.erp.pojo.Module;
import cn.itcast.erp.pojo.Role;
import cn.itcast.erp.service.DeptService;
import cn.itcast.erp.service.ModuleService;
import cn.itcast.erp.service.RoleService;
import cn.itcast.erp.service.UserService;
import cn.itcast.erp.utils.Page;

public class DeptAction extends BaseAction implements ModelDriven<Dept>{
	
	private Dept model = new Dept();
	private DeptService deptService;
	Page page = new Page();
	
	
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	@Override
	public Dept getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	public void setModel(Dept model) {
		this.model = model;
	}
	
	
	public String list() throws Exception {
		
		
		
		
		
		
		
		String hql="from Dept";
		deptService.findPAge(hql, page, Dept.class, null);
		
		page.setUrl("deptAction_list");
		
		ActionContext.getContext().getValueStack().push(page);
		
		return "list";
	}
	
	
	public String toview() throws Exception{
		
		Dept dept = deptService.get(Dept.class,model.getId());
		
		ActionContext.getContext().getValueStack().push(dept);
		return "toView";
	}
	
	public String tocreate() throws Exception{
		
		String hql="from Dept";
		
		
		List<Dept> deptList = deptService.find(hql, Dept.class, null);
		
		for (int i = 0; i < deptList.size(); i++) {
			if(deptList.get(i).getState()==0){
				deptList.remove(i);
			}
			
			
		}
		super.put("deptList", deptList);
		
		return "toCreate";
	}
	
	public String insert() throws Exception{
		
		deptService.saveOrUpdate(model);
		
		
		return list();
	}
	
	public String toupdate()throws Exception{
		
		String hql = "from Dept where state ="+SysConstant.DEPT_STATE_ENABLED;
		
		List<Dept> deptList = deptService.find(hql, Dept.class, null);
		
		Dept dept = deptService.get(Dept.class, model.getId());
		
		deptList.remove(dept);
		
		this.push(dept);
		
		this.put("deptList", deptList);
		
		return "toUpdate";
	
	}
	
	public String update() throws Exception{
		
		 deptService.saveOrUpdate(model);
		
		
		return SUCCESS;
	}
	public String delete() throws Exception{
		Object object = ActionContext.getContext().getSession().get(model);
		String[] ids = model.getId().split(", ");	
		deptService.delete(Dept.class, ids);
		
		return SUCCESS;
	}
	
	
}
