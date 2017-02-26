package cn.itcast.erp.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.itcast.erp.dao.BaseDao;
import cn.itcast.erp.pojo.Factory;
import cn.itcast.erp.service.FactoryService;
import cn.itcast.erp.utils.Page;

public class FactoryServiceImpl implements FactoryService {
	
	private BaseDao baseDao;
	
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Page<Factory> findPage(String hql, Page<Factory> page,
			Class<Factory> pojoClass, Object[] params) {
		
		return baseDao.findPage(hql, page, pojoClass, params);
	}

	@Override
	public Factory get(Class<Factory> pojoClass, Serializable id) {
		return baseDao.get(pojoClass, id);
	}

	@Override
	public List<Factory> find(String hql, Class<Factory> pojoClass,
			Object[] params) {
		return baseDao.find(hql, pojoClass, params);
	}

	@Override
	public void saveOrUpdate(Factory pojo) {
		
	}

	@Override
	public void deleteById(Class<Factory> pojoClass, Serializable id) {
		
		baseDao.deleteById(pojoClass, id);
	}

	@Override
	public void delete(Class<Factory> pojoClass, Serializable[] ids) {
	for (Serializable id : ids) {
		deleteById(pojoClass, id);
	}
	}

}
