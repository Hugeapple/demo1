package cn.itcast.erp.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.itcast.erp.dao.BaseDao;
import cn.itcast.erp.pojo.Role;
import cn.itcast.erp.service.RoleService;
import cn.itcast.erp.utils.Page;

public class RoleServiceImpl implements RoleService{
	
	
	private BaseDao baseDao;
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Page<Role> findPage(String hql, Page<Role> page,
			Class<Role> pojoClass, Object[] params) {
		return baseDao.findPage(hql, page, pojoClass, params);
	}

	@Override
	public Role get(Class<Role> pojoClass, Serializable id) {
		return baseDao.get(pojoClass, id);
	}

	@Override
	public List<Role> find(String hql, Class<Role> pojoClass, Object[] params) {
		return baseDao.find(hql, pojoClass, params);
	}

	@Override
	public void saveOrUpdate(Role pojo) {
		
		if(StringUtils.isBlank(pojo.getId())){
			
			baseDao.saveOrUpdate(pojo);
			
		}else{
			
			Role role = baseDao.get(Role.class, pojo.getId());
			
			role.setName(pojo.getName());
			role.setRemark(pojo.getRemark());
			
			baseDao.saveOrUpdate(pojo);
			
			
		}
		
		
		
		
		
	}

	
	
	
	@Override
	public void deleteById(Class<Role> pojoClass, Serializable id) {
		
		
		baseDao.deleteById(pojoClass, id);
	}

	@Override
	public void delete(Class<Role> pojoClass, Serializable[] ids) {

		for (Serializable id : ids) {
			
			deleteById(pojoClass, id);
		}
		
	}

}
