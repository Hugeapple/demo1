package cn.itcast.erp.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.itcast.erp.dao.BaseDao;
import cn.itcast.erp.pojo.Contract;
import cn.itcast.erp.pojo.ExtCproduct;
import cn.itcast.erp.service.ExtCproductService;
import cn.itcast.erp.utils.Page;
import cn.itcast.erp.utils.UtilFuns;

public class ExtCproductServiceImpl implements ExtCproductService {
	
	private BaseDao baseDao;
	
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Page<ExtCproduct> findPage(String hql, Page<ExtCproduct> page,
			Class<ExtCproduct> pojoClass, Object[] params) {
		
		return baseDao.findPage(hql, page, pojoClass, params);
	}

	@Override
	public ExtCproduct get(Class<ExtCproduct> pojoClass, Serializable id) {
		return baseDao.get(pojoClass, id);
	}

	@Override
	public List<ExtCproduct> find(String hql, Class<ExtCproduct> pojoClass,
			Object[] params) {
		return baseDao.find(hql, pojoClass, params);
	}

	@Override
	public void saveOrUpdate(ExtCproduct pojo) {

		Double amount = 0d;
		if(UtilFuns.isEmpty(pojo.getId())){
			
			
			if(UtilFuns.isNotEmpty(pojo.getCnumber())&&UtilFuns.isNotEmpty(pojo.getPrice())){
				amount = pojo.getCnumber() * pojo.getPrice();
			}
			pojo.setAmount(amount);
			
			Contract contract = baseDao.get(Contract.class, pojo.getContractProduct().getContract().getId());
			contract.setTotalAmount(contract.getTotalAmount()+amount);
			
			baseDao.saveOrUpdate(contract);
			baseDao.saveOrUpdate(pojo);
			
		}else{
			ExtCproduct extCproduct = baseDao.get(ExtCproduct.class, pojo.getId());
//			修改
			if(UtilFuns.isNotEmpty(pojo.getCnumber())&&UtilFuns.isNotEmpty(pojo.getPrice())){
				amount = pojo.getCnumber() * pojo.getPrice();
			}
			
			Contract contract = baseDao.get(Contract.class, pojo.getContractProduct().getContract().getId());
			
			contract.setTotalAmount(contract.getTotalAmount()-extCproduct.getAmount()+amount);
			
			extCproduct.setAmount(amount);
			
			baseDao.saveOrUpdate(contract);
			baseDao.saveOrUpdate(extCproduct);
		}
	
		
		
	}



	@Override
	public void delete(Class<ExtCproduct> pojoClass, ExtCproduct model) {
		ExtCproduct obj = baseDao.get(ExtCproduct.class, model.getId());
		//2.得到购销合同对象
		 Contract contract = baseDao.get(Contract.class, model.getContractProduct().getContract().getId());	
		 //3.修改购销合同总金额   = 购销合同总金额-附件金额 
		 contract.setTotalAmount(contract.getTotalAmount()-obj.getAmount()); 
		 //4.删除附件对象
		 baseDao.deleteById(ExtCproduct.class, model.getId()); 
		 //5.保存购销合同总金额
		 baseDao.saveOrUpdate(contract);

		
	}

}
