package cn.itcast.erp.action.cargo;

import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.erp.action.BaseAction;
import cn.itcast.erp.common.SysConstant;
import cn.itcast.erp.pojo.Contract;
import cn.itcast.erp.pojo.Module;
import cn.itcast.erp.pojo.Role;
import cn.itcast.erp.pojo.User;
import cn.itcast.erp.service.ContractService;
import cn.itcast.erp.service.ModuleService;
import cn.itcast.erp.service.RoleService;
import cn.itcast.erp.service.UserService;
import cn.itcast.erp.utils.Page;

public class ContractAction extends BaseAction implements ModelDriven<Contract>{
	
	private Contract model = new Contract();
	private ContractService contractService;
	Page page = new Page();
	
	
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}

	@Override
	public Contract getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	public void setModel(Contract model) {
		this.model = model;
	}
	
	
	public String list() throws Exception {
		
		
		String hql = "from Contract where 1=1 ";
		
		//显示多少数据，取决于用户的等级
		User user = (User) session.get(SysConstant.CURRENT_USER_INFO);//获取当前登录的用户对象
		
		//获取用户的等级
		Integer degree = user.getUserInfo().getDegree();
		
		//给hql语句加入不同的条件（条件由等级来决定）
		if(degree==4){
			//普通员工
			hql+=" and createBy='"+user.getId()+"'";
		}else if(degree == 3){
			//部门经理（管理本部门，不包括下属机构）
			hql+=" and createDept='"+user.getDept().getId()+"'";
		}else if(degree==2){
			//管理本部门及下属部门
			
		}else if(degree==1){
			//副总(跨部门跨人员)
			
		}else if(degree==0){
			//总裁  因为他可以查询所有记录，所以不加条件
			
		}
		contractService.findPage(hql, page, Contract.class, null);
		
		//设置分页组件的url
		//page.setUrl(ServletActionContext.getRequest().getContextPath()+"/sysadmin/deptAction_list");//绝对定位
		page.setUrl("contractAction_list");//相对定位
		
		//将page组件手动放入栈顶
		super.push(page);
		return "list";
		
	}
	
	
	public String toview() throws Exception{
		
		Contract contract = contractService.get(Contract.class,model.getId());
		 
		if(contract.getCrequest()!=null&&!"".equals(contract.getCrequest())){
			String s = contract.getCrequest().replaceAll("\\\\r\\\\n", "<br>");
			contract.setCrequest(s);//此处替换失败
		}
		ActionContext.getContext().getValueStack().push(contract);
		return "toView";
	}
	
	
	
	
	
	public String tocreate() throws Exception{
		
		String hql="from Contract";
		
		
		List<Contract> ContractList = contractService.find(hql, Contract.class, null);
		
		
		
		return "toCreate";
	}
	
	public String insert() throws Exception{
		
		User user = (User) session.get(SysConstant.CURRENT_USER_INFO);
			
		model.setCreateBy(user.getId());
		model.setCreateDept(user.getDept().getId());
		contractService.saveOrUpdate(model);
		
		
		return list();
	}
	
	public String toupdate()throws Exception{
		
		Contract contract = contractService.get(Contract.class, model.getId());
		
		
		this.push(contract);
		
		return "toUpdate";
	
	}
	
	public String update() throws Exception{
		
		contractService.saveOrUpdate(model);
		
		
		return SUCCESS;
	}
	public String delete() throws Exception{
		String[] ids = model.getId().split(", ");	
		contractService.delete(Contract.class, ids);
		
		return SUCCESS;
	}
	
	public String submit() throws Exception{
		
		String[] ids = model.getId().split(", ");
		
		for (String id : ids) {
			
			Contract contract = contractService.get(Contract.class, id);
			
			contract.setState(SysConstant.CONTRACT_STATE_ENABLE);
			
			contractService.saveOrUpdate(contract);
		}
		return SUCCESS;
	}
	public String cancel() throws Exception{
			
			String[] ids = model.getId().split(", ");
			
			for (String id : ids) {
				
				Contract contract = contractService.get(Contract.class, id);
				
				contract.setState(SysConstant.CONTRACT_STATE_DISABLE);
				
				contractService.saveOrUpdate(contract);
			}
			return SUCCESS;
		}
}
