package cn.itcast.erp.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.itcast.erp.common.SysConstant;
import cn.itcast.erp.dao.BaseDao;
import cn.itcast.erp.pojo.Dept;
import cn.itcast.erp.service.DeptService;
import cn.itcast.erp.utils.Page;

public class DeptServiceImpl implements DeptService {
	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Page<Dept> findPAge(String hql, Page<Dept> page,
			Class<Dept> pojoClass, Object[] params) {

		return baseDao.findPage(hql, page, pojoClass, params);
	}

	// 根据id查找用户
	@Override
	public Dept get(Class<Dept> pojoClass, Serializable id) {

		return baseDao.get(pojoClass, id);
	}

	@Override
	public List<Dept> find(String hql, Class<Dept> pojoClass, Object[] params) {
		return baseDao.find(hql, pojoClass, params);
	}

	@Override
	public void saveOrUpdate(Dept pojo) {

		/*
		 * if((pojo.getId()).equals(pojo.getParent().getId())){
		 * pojo.setParent(null); }
		 */

		if (StringUtils.isBlank(pojo.getId())) {

			pojo.setState(SysConstant.DEPT_STATE_ENABLED);
			baseDao.saveOrUpdate(pojo);

		} else {

			Dept dept = baseDao.get(Dept.class, pojo.getId());

			dept.setDeptName(pojo.getDeptName());
			dept.setParent(pojo.getParent());
			dept.setState(pojo.getState());

			baseDao.saveOrUpdate(dept);

		}

	}

	@Override
	public void deleteById(Class<Dept> pojoClass, Serializable id) {
		List<Dept> childList = baseDao.find("from Dept where parent.id=?", pojoClass,
				new Serializable[] { id });
		
		if(childList!=null&&childList.size()!=0){
			
			for (Dept dept : childList) {
				deleteById(pojoClass, dept.getId());
			}
		}
//		baseDao.deleteById(pojoClass, id);
		Dept dept = baseDao.get(pojoClass, id);
		
		dept.setState(SysConstant.DEPT_STATE_DISABLED);
		
		baseDao.saveOrUpdate(dept);
	}

	@Override
	public void delete(Class<Dept> pojoClass, Serializable[] ids) {
		for(Serializable id:ids){
			deleteById(pojoClass, id);
		}

	}
}
