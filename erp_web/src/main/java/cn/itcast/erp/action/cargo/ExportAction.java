package cn.itcast.erp.action.cargo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import cn.itcast.erp.action.BaseAction;
import cn.itcast.erp.pojo.Contract;
import cn.itcast.erp.pojo.Export;
import cn.itcast.erp.pojo.ExportProduct;
import cn.itcast.erp.service.ContractService;
import cn.itcast.erp.service.ExportProductService;
import cn.itcast.erp.service.ExportService;
import cn.itcast.erp.utils.Page;
import cn.itcast.erp.utils.UtilFuns;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 说明：
 * 
 * @author bigApple
 * @version 1.0
 * @date 2017年2月18日
 */
public class ExportAction extends BaseAction implements ModelDriven<Export> {

	private Export model = new Export();

	@Override
	public Export getModel() {
		return model;
	}

	private ContractService contractService;

	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}

	private ExportService exportService;

	public void setModel(Export model) {
		this.model = model;
	}

	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}

	Page page = new Page();

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String list() throws Exception {

		exportService.findPage("from Export", page, Export.class, null);

		page.setUrl("ExportAction_list");

		this.push(page);

		return "list";

	}

	public String toview() throws Exception {

		Export Export = exportService.get(Export.class, model.getId());
		this.push(Export);

		return "toView";
	}

	public String tocreate() throws Exception {
		return "toCreate";
	}

	public String insert() throws Exception {

		exportService.saveOrUpdate(model);

		return SUCCESS;
	}

	public String toupdate() throws Exception {
		// 准备数据
		Export export = exportService.get(Export.class, model.getId());
		this.push(export);

		// 获取报运单下所有的货物
		Set<ExportProduct> exportProducts = export.getExportProducts();
		// 拼接字符串
		StringBuffer sb = new StringBuffer();

		for (ExportProduct ep : exportProducts) {
			sb.append("addTRRecord('mRecordTable',");
			sb.append("'" + ep.getId() + "',");
			sb.append("'" + ep.getProductNo() + "',");
			sb.append("'" + ep.getCnumber() + "',");
			sb.append("'" + (ep.getGrossWeight() == null ? "" : ep.getGrossWeight()) + "',");
			sb.append("'" + UtilFuns.convertNull(ep.getNetWeight()) + "',");
			sb.append("'" + UtilFuns.convertNull(ep.getSizeLength()) + "',");
			sb.append("'" + UtilFuns.convertNull(ep.getSizeWidth()) + "',");
			sb.append("'" + UtilFuns.convertNull(ep.getSizeHeight()) + "',");
			sb.append("'" + UtilFuns.convertNull(ep.getExPrice()) + "',");
			sb.append("'" + UtilFuns.convertNull(ep.getTax()) + "');");
		}

		// 将内容放入值栈
		this.put("mRecordData", sb.toString());

		return "toUpdate";
	}

	private String[] mr_id;
	private Integer[] mr_changed;
	private Integer[] mr_cnumber;
	private Double[] mr_grossWeight;
	private Double[] mr_netWeight;
	private Double[] mr_sizeLength;
	private Double[] mr_sizeWidth;
	private Double[] mr_sizeHeight;
	private Double[] mr_exPrice;
	private Double[] mr_tax;

	private ExportProductService exportProductService;

	public String update() throws Exception {

		// 1.2 更新属性
		Export export = exportService.get(Export.class, model.getId());
		// 报运单
		export.setCustomerContract(model.getCustomerContract());
		// 制单日期
		export.setInputDate(model.getInputDate());
		// 信用证号
		export.setLcno(model.getLcno());
		// 收货人及地址
		export.setConsignee(model.getConsignee());
		// 装运港
		export.setShipmentPort(model.getShipmentPort());
		// 目的港
		export.setDestinationPort(model.getDestinationPort());
		// 运输方式
		export.setTransportMode(model.getTransportMode());
		// 价格条件
		export.setPriceCondition(model.getPriceCondition());
		// 唛头
		export.setMarks(model.getMarks());
		// 备注
		export.setRemark(model.getRemark());

		// 2.1 获取报运单对应的所有的货物
		// Set<ExportProduct> exportProducts = export.getExportProducts();
		Set<ExportProduct> epSet = new HashSet<ExportProduct>();

		// 2.2 遍历货物，判断货物的changed==1，如果等于1，表示需要修改
		for (int i = 0; i < mr_id.length; i++) {
			ExportProduct ep = exportProductService.get(ExportProduct.class, mr_id[i]);

			if (mr_changed[i] != null && mr_changed[i] == 1) {
				ep.setCnumber(mr_cnumber[i]);
				ep.setGrossWeight(mr_grossWeight[i]);
				ep.setNetWeight(mr_netWeight[i]);
				ep.setSizeLength(mr_sizeLength[i]);
				ep.setSizeWidth(mr_sizeWidth[i]);
				ep.setSizeHeight(mr_sizeHeight[i]);
				ep.setExPrice(mr_exPrice[i]);
				ep.setTax(mr_tax[i]);
			}

			// 如果没有改变直接add
			epSet.add(ep);
		}

		// 将新的集合给报运单
		export.setExportProducts(epSet);
		// 提交更新,会级联更新吗?答：会
		exportService.saveOrUpdate(export);

		return SUCCESS;
	}

	public String delete() throws Exception {
		Serializable[] ids = model.getId().split(", ");
		
		exportService.delete(Export.class, ids);
		
		return SUCCESS;
	}

public String submit() throws Exception {
		
		String[] ids = model.getId().split(", ");
		
		for(String id:ids){
			Export export = exportService.get(Export.class, id);
			export.setState(1);
			exportService.saveOrUpdate(export);
		}
		
		return SUCCESS;
	}

	public String cancel() throws Exception {
		String[] ids = model.getId().split(", ");
		
		for (String id : ids) {
			
			Export export = exportService.get(Export.class, id);
			
			export.setState(0);
			exportService.saveOrUpdate(export);
		}

		return list();
	}
	
	
	
	
	
	
	
	
	
	
	

	public String contractList() throws Exception {

		String hql = "from Contract where state = 1";

		contractService.findPage(hql, page, Contract.class, null);

		page.setUrl("exportAction_contractList");
		this.push(page);

		return "contractList";
	}

	public void setMr_id(String[] mr_id) {
		this.mr_id = mr_id;
	}

	public void setMr_changed(Integer[] mr_changed) {
		this.mr_changed = mr_changed;
	}

	public void setMr_cnumber(Integer[] mr_cnumber) {
		this.mr_cnumber = mr_cnumber;
	}

	public void setMr_grossWeight(Double[] mr_grossWeight) {
		this.mr_grossWeight = mr_grossWeight;
	}

	public void setMr_netWeight(Double[] mr_netWeight) {
		this.mr_netWeight = mr_netWeight;
	}

	public void setMr_sizeLength(Double[] mr_sizeLength) {
		this.mr_sizeLength = mr_sizeLength;
	}

	public void setMr_sizeWidth(Double[] mr_sizeWidth) {
		this.mr_sizeWidth = mr_sizeWidth;
	}

	public void setMr_sizeHeight(Double[] mr_sizeHeight) {
		this.mr_sizeHeight = mr_sizeHeight;
	}

	public void setMr_exPrice(Double[] mr_exPrice) {
		this.mr_exPrice = mr_exPrice;
	}

	public void setMr_tax(Double[] mr_tax) {
		this.mr_tax = mr_tax;
	}

	public void setExportProductService(ExportProductService exportProductService) {
		this.exportProductService = exportProductService;
	}

}
