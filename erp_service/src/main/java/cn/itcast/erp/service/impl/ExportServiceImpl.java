package cn.itcast.erp.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import cn.itcast.erp.dao.BaseDao;
import cn.itcast.erp.pojo.Contract;
import cn.itcast.erp.pojo.ContractProduct;
import cn.itcast.erp.pojo.Export;
import cn.itcast.erp.pojo.ExportProduct;
import cn.itcast.erp.pojo.ExtCproduct;
import cn.itcast.erp.pojo.ExtEproduct;
import cn.itcast.erp.service.ExportService;
import cn.itcast.erp.utils.Page;
public class ExportServiceImpl implements ExportService{
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Override
	public Page<Export> findPage(String hql, Page<Export> page,
			Class<Export> pojoClass, Object[] params) {
		return baseDao.findPage(hql, page, pojoClass, params);
	}
	public Export get(Class<Export> pojoClass, Serializable id) {
		return baseDao.get(pojoClass, id);
	}

	@Override
	public List<Export> find(String hql, Class<Export> pojoClass, Object[] params) {
		return baseDao.find(hql, pojoClass, params);
	}

	/**
	 * 新增出口报运单的业务逻辑： (进入这个页面，我们拥有知识合同的id集合)
	 * 1 保存报运单信息
	 * 2 保存报运货物信息和报运货物对应的附件信息
	 * 3 改变合同的状态
	 */
	public void saveOrUpdate(Export pojo) {
		if(StringUtils.isBlank(pojo.getId())){
			//新增
			//1 合同和确认书号
			String[] contractIds = pojo.getContractIds().split(", ");
			StringBuffer sb = new StringBuffer();
			
			for(String id:contractIds){
				//查找
				Contract contract = baseDao.get(Contract.class, id);
				sb.append(contract.getContractNo()).append(" ");
				//3 改变合同状态
				contract.setState(2);
				baseDao.saveOrUpdate(contract);
			}
			//2 将合同和确认书号给Export
			pojo.setCustomerContract(sb.toString());
			
			//将合同下对应的货物给报运单下的货物
			//将附件给 报运的附件
			//3.4 定义一个集合装货物
			Set<ExportProduct> eps = new HashSet<ExportProduct>();
			// 3.1 遍历集合
			for(String id:contractIds){
				//3.2 获取合同 
				Contract contract = baseDao.get(Contract.class, id);
				//3.3 获取contract下面的货物
				Set<ContractProduct> cps = contract.getContractProducts();
				
				// 3.5 遍历货物
				for(ContractProduct cp:cps){
					// 3.6 实例化ExportProduct，来接收CP的值
					ExportProduct ep = new ExportProduct();
					//工厂
					ep.setFactory(cp.getFactory());
					//货号
					ep.setProductNo(cp.getProductNo());
					//包装单位
					ep.setPackingUnit(cp.getPackingUnit());
					//数量
					ep.setCnumber(cp.getCnumber());
					//单价
					ep.setPrice(cp.getPrice());
					//排序
					ep.setOrderNo(cp.getOrderNo());
					
					
					// 3.6 获取附件
					Set<ExtCproduct> extCproducts = cp.getExtCproducts();
					Set<ExtEproduct> extEproducts = new HashSet<ExtEproduct>();
					
					for(ExtCproduct extc:extCproducts){
						//创建报运附件
						ExtEproduct exte = new ExtEproduct();
						//厂家
						exte.setFactory(extc.getFactory());
						//货号
						exte.setProductNo(extc.getProductNo());
						//照片
						exte.setProductImage(extc.getProductImage());
						//货描
						exte.setProductDesc(extc.getProductDesc());
						//包装单位
						exte.setPackingUnit(extc.getPackingUnit());
						//数量
						exte.setCnumber(extc.getCnumber());
						//单价
						exte.setPrice(extc.getPrice());
						//总金额
						exte.setAmount(extc.getAmount());
						//要求
						exte.setProductRequest(extc.getProductRequest());
						//排序号
						exte.setOrderNo(extc.getOrderNo());
						
						//直接复制属性:将对象中具有同名的属性机进行自动复制
						//将extc中的同名的属性的值自动赋给exte中
//						BeanUtils.copyProperties(extc, exte);
						exte.setId(null);
						System.out.println(exte);
						//装入集合
						extEproducts.add(exte);
						exte.setExportProduct(ep);
					}
					//将extEproducts集合给货物
					ep.setExtEproducts(extEproducts);
					//将货物添加至货物集合
					eps.add(ep);
					ep.setExport(pojo);
				}
				
				//3.7 将货物集合给报运单
				pojo.setExportProducts(eps);
			}
			//状态
			pojo.setState(0);//0 草稿 1 已上报 2 已装箱 3 委托 4 发票  5 财务
			pojo.setInputDate(new Date());
			
			//保存当前的报运实体
			baseDao.saveOrUpdate(pojo);
		}else{
			//修改
			Export export = baseDao.get(Export.class, pojo.getId());
			
			
			
			baseDao.saveOrUpdate(export);
		}
		
	}

	@Override
	public void deleteById(Class<Export> pojoClass, Serializable id) {
		baseDao.deleteById(pojoClass, id);
	}

	@Override
	public void delete(Class<Export> pojoClass, Serializable[] ids) {
		for(Serializable id:ids){
			deleteById(pojoClass, id);
		}
	}

}
