package cn.itcast.erp.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import cn.itcast.erp.dao.BaseDao;
import cn.itcast.erp.pojo.Contract;
import cn.itcast.erp.pojo.ContractProduct;
import cn.itcast.erp.pojo.ExtCproduct;
import cn.itcast.erp.service.ContractProductService;
import cn.itcast.erp.utils.Page;
import cn.itcast.erp.utils.UtilFuns;

public class ContractProductServiceImpl implements ContractProductService {

	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Page<ContractProduct> findPage(String hql, Page<ContractProduct> page, Class<ContractProduct> pojoClass,
			Object[] params) {

		return baseDao.findPage(hql, page, pojoClass, params);
	}

	@Override
	public ContractProduct get(Class<ContractProduct> pojoClass, Serializable id) {
		return baseDao.get(pojoClass, id);
	}

	@Override
	public List<ContractProduct> find(String hql, Class<ContractProduct> pojoClass, Object[] params) {
		return baseDao.find(hql, pojoClass, params);
	}

	@Override
	public void saveOrUpdate(ContractProduct pojo) {
		Double amount = 0d;
		if (UtilFuns.isEmpty(pojo.getId())) {

			if (UtilFuns.isNotEmpty(pojo.getPrice()) && UtilFuns.isNotEmpty(pojo.getCnumber())) {

				amount = pojo.getPrice() * pojo.getCnumber();
			}
			pojo.setAmount(amount);

			Contract contract = baseDao.get(Contract.class, pojo.getContract().getId());

			contract.setTotalAmount(contract.getTotalAmount() + amount);

			baseDao.saveOrUpdate(contract);

			baseDao.saveOrUpdate(pojo);
		} else {
			ContractProduct contractProduct = baseDao.get(ContractProduct.class, pojo.getId());
			
			if (UtilFuns.isNotEmpty(contractProduct.getPrice()) && UtilFuns.isNotEmpty(contractProduct.getCnumber())) {

				amount = contractProduct.getPrice() * contractProduct.getCnumber();
			}
			
			Contract contract = contractProduct.getContract();
			
			contract.setTotalAmount(contract.getTotalAmount()-contractProduct.getAmount()+amount);
			
			contractProduct.setAmount(amount);
			
			baseDao.saveOrUpdate(contract);
			baseDao.saveOrUpdate(contractProduct);

		}

	}

	@Override
	public void deleteById(Class<ContractProduct> pojoClass, Serializable id) {
		
		
		ContractProduct contractProduct = baseDao.get(pojoClass, id);
		
		
		Contract contract = contractProduct.getContract();		
		
		Set<ExtCproduct> extCproducts = contractProduct.getExtCproducts();
		
		contract.setTotalAmount(contract.getTotalAmount()-contractProduct.getAmount());
		
		for (ExtCproduct extCproduct : extCproducts) {
			
			contract.setTotalAmount(contract.getTotalAmount()-extCproduct.getAmount());
		}
		
		
		baseDao.saveOrUpdate(contract);
		
		baseDao.deleteById(pojoClass, id);
		
		
		
		
	}

	
}
