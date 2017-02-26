package cn.itcast.erp.action.cargo;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.erp.action.BaseAction;
import cn.itcast.erp.common.SysConstant;
import cn.itcast.erp.pojo.ContractProduct;
import cn.itcast.erp.pojo.Factory;
import cn.itcast.erp.service.ContractProductService;
import cn.itcast.erp.service.FactoryService;
import cn.itcast.erp.utils.Page;

public class ContractProductAction extends BaseAction implements ModelDriven<ContractProduct>{
	
	private ContractProduct model = new ContractProduct();
	private ContractProductService contractProductService;
	Page page = new Page();
	
	
	
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public void setContractProductService(ContractProductService contractProductService) {
		this.contractProductService = contractProductService;
	}

	@Override
	public ContractProduct getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	public void setModel(ContractProduct model) {
		this.model = model;
	}
	
	public FactoryService factoryService;
	
	public void setFactoryService(FactoryService factoryService) {
		this.factoryService = factoryService;
	}

	public String list() throws Exception {
		
		
		
		
		
		
		
		String hql="from ContractProduct";
		contractProductService.findPage(hql, page, ContractProduct.class, null);
		
		page.setUrl("ContractProductAction_list");
		
		ActionContext.getContext().getValueStack().push(page);
		
		return "list";
	}
	
	
	public String toview() throws Exception{
		
		ContractProduct ContractProduct = contractProductService.get(ContractProduct.class,model.getId());
		
		ActionContext.getContext().getValueStack().push(ContractProduct);
		return "toView";
	}
	
	public String tocreate() throws Exception{
		
		List<Factory> factoryList = factoryService.find("from Factory where state = 1 and ctype ='货物'", Factory.class, null);
		
		this.put("factoryList", factoryList);
		
		contractProductService.findPage("from ContractProduct where contract.id=?", page,
				ContractProduct.class, new String[]{model.getContract().getId()});
		page.setUrl("contractProductAction_tocreate");
		this.push(page);
		
		return "toCreate";
	}
	
	public String insert() throws Exception{
		
		
		
		contractProductService.saveOrUpdate(model);
		
		
		return tocreate();
	}
	
	public String toupdate()throws Exception{
		
		ContractProduct contractProduct = contractProductService.get(ContractProduct.class,model.getId());
		
		super.push(contractProduct);
		
		List<Factory> factoryList = factoryService.find("from Factory where state = 1 and ctype ='货物'", Factory.class, null);
		
		this.put("factoryList", factoryList);
		return "toUpdate";
	
	}
	
	public String update() throws Exception{
		
		ContractProduct contractProduct = contractProductService.get(ContractProduct.class, model.getId());
		// 2 将修改的属性赋值给contractProduct
		//生产厂家
		contractProduct.setFactory(model.getFactory());
		//货物照片
		contractProduct.setProductImage(model.getProductImage());
		//数量
		contractProduct.setCnumber(model.getCnumber());
		//装率
		contractProduct.setLoadingRate(model.getLoadingRate());
		//单价
		contractProduct.setPrice(model.getPrice());
		//货描
		contractProduct.setProductDesc(model.getProductDesc());
		//货号
		contractProduct.setProductNo(model.getProductNo());
		//包装单位
		contractProduct.setPackingUnit(model.getPackingUnit());
		//箱数
		contractProduct.setBoxNum(model.getBoxNum());
		//排序号
		contractProduct.setOrderNo(model.getOrderNo());
		//要求
		contractProduct.setProductRequest(model.getProductRequest());
		
		
		contractProductService.saveOrUpdate(contractProduct);
		
		return SUCCESS;

		
	}
	
	public String delete() throws Exception{
		contractProductService.deleteById(ContractProduct.class, model.getId());
		
		return tocreate();
	}
	
	
}
