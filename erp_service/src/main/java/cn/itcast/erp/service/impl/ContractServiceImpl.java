package cn.itcast.erp.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.itcast.erp.dao.BaseDao;
import cn.itcast.erp.pojo.Contract;
import cn.itcast.erp.service.ContractService;
import cn.itcast.erp.utils.Page;
import cn.itcast.erp.utils.UtilFuns;

public class ContractServiceImpl implements ContractService {
	
	private BaseDao baseDao;
	
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Page<Contract> findPage(String hql, Page<Contract> page,
			Class<Contract> pojoClass, Object[] params) {
		
		return baseDao.findPage(hql, page, pojoClass, params);
	}

	@Override
	public Contract get(Class<Contract> pojoClass, Serializable id) {
		return baseDao.get(pojoClass, id);
	}

	@Override
	public List<Contract> find(String hql, Class<Contract> pojoClass,
			Object[] params) {
		return baseDao.find(hql, pojoClass, params);
	}

	@Override
	public void saveOrUpdate(Contract pojo) {
	
		if(UtilFuns.isEmpty(pojo.getId())){
			pojo.setTotalAmount(0d);
			
			pojo.setState(0);
			
			baseDao.saveOrUpdate(pojo);
		}else{
			Contract contract = baseDao.get(Contract.class, pojo.getId());
			//客户名称
			contract.setCustomName(pojo.getCustomName());
			//打印板式
			contract.setPrintStyle(pojo.getPrintStyle());
			//合同号
			contract.setContractNo(pojo.getContractNo());
			//收购方
			contract.setOfferor(pojo.getOfferor());
			//制单人
			contract.setInputBy(pojo.getInputBy());
			//审单人
			contract.setCheckBy(pojo.getCheckBy());
			//验货员
			contract.setInspector(pojo.getInspector());
			//签期
			contract.setSigningDate(pojo.getSigningDate());
			//重要程度
			contract.setImportNum(pojo.getImportNum());
			//船期
			contract.setShipTime(pojo.getShipTime());
			// 贸易条款
			contract.setTradeTerms(pojo.getTradeTerms());
			//交期
			contract.setDeliveryPeriod(pojo.getDeliveryPeriod());
			//要求
			contract.setCrequest(pojo.getCrequest());
//			/说明
			contract.setRemark(pojo.getRemark());
			
			baseDao.saveOrUpdate(contract);
		}
		
	}

	@Override
	public void deleteById(Class<Contract> pojoClass, Serializable id) {
		
		baseDao.deleteById(pojoClass, id);
	}

	@Override
	public void delete(Class<Contract> pojoClass, Serializable[] ids) {
	for (Serializable id : ids) {
		deleteById(pojoClass, id);
	}
	}

}
