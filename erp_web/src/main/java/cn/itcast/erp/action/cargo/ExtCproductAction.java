package cn.itcast.erp.action.cargo;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.erp.action.BaseAction;
import cn.itcast.erp.common.SysConstant;
import cn.itcast.erp.pojo.ExtCproduct;
import cn.itcast.erp.pojo.Factory;
import cn.itcast.erp.service.ExtCproductService;
import cn.itcast.erp.service.FactoryService;
import cn.itcast.erp.utils.Page;

public class ExtCproductAction extends BaseAction implements ModelDriven<ExtCproduct>{
	
	private ExtCproduct model = new ExtCproduct();
	private ExtCproductService extCproductService;
	private FactoryService factoryService;
	Page page = new Page();
	
	
	
	public void setFactoryService(FactoryService factoryService) {
		this.factoryService = factoryService;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public void setExtCproductService(ExtCproductService extCproductService) {
		this.extCproductService = extCproductService;
	}
	
	@Override
	public ExtCproduct getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	public void setModel(ExtCproduct model) {
		this.model = model;
	}
	
	
	public String list() throws Exception {
		
		
		
		
		
		
		
		String hql="from ExtCproduct";
		extCproductService.findPage(hql, page, ExtCproduct.class, null);
		
		page.setUrl("ExtCproductAction_list");
		
		ActionContext.getContext().getValueStack().push(page);
		
		return "list";
	}
	
	
	public String toview() throws Exception{
		
		ExtCproduct ExtCproduct = extCproductService.get(ExtCproduct.class,model.getId());
		
		ActionContext.getContext().getValueStack().push(ExtCproduct);
		return "toView";
	}
	
	public String tocreate() throws Exception{
		
		List<Factory> factoryList = factoryService.find("from Factory where ctype = '附件' and state = 1", Factory.class, null);
		
		String hql = "from ExtCproduct where contractProduct.id = ?";
		
		extCproductService.findPage(hql, page, ExtCproduct.class, new String[]{model.getContractProduct().getId()});
		
		page.setUrl("extCproductAction_tocreate");
		
		this.push(page);
		
		this.put("factoryList", factoryList);
		
		return "toCreate";
	}
	
	public String insert() throws Exception{
		
		extCproductService.saveOrUpdate(model);
		
		
		return tocreate();
	}
	
	public String toupdate()throws Exception{
		
		ExtCproduct obj = extCproductService.get(ExtCproduct.class, model.getId());
		super.push(obj);		
		// 2.得到用于生产附件的厂家列表
		List<Factory> factoryList = factoryService.find("from Factory where state=1 and ctype='附件'", Factory.class,
						null);
		// 放入值栈中
		super.put("factoryList", factoryList);

		return "toUpdate";

	
	}
	
	public String update() throws Exception{
		ExtCproduct obj = extCproductService.get(ExtCproduct.class, model.getId());

		// 2.页面修改的属性，就要更新值
		obj.setFactory(model.getFactory());
		obj.setFactoryName(model.getFactoryName());
		obj.setProductNo(model.getProductNo());
		obj.setProductImage(model.getProductImage());
		obj.setCnumber(model.getCnumber());
		obj.setPackingUnit(model.getPackingUnit());
		obj.setPrice(model.getPrice());
		obj.setOrderNo(model.getOrderNo());
		obj.setProductDesc(model.getProductDesc());
		obj.setProductRequest(model.getProductRequest());
		// 3.更新
		extCproductService.saveOrUpdate(obj);
		return tocreate();

	}
	public String delete() throws Exception{
		// 2.调用业务方法，删除记录
		extCproductService.delete(ExtCproduct.class, model);

		return tocreate();

	}
	
	
}
