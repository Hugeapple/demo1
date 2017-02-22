package cn.itcast.erp.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.itcast.erp.dao.BaseDao;
import cn.itcast.erp.pojo.Module;
import cn.itcast.erp.service.ModuleService;
import cn.itcast.erp.utils.Page;

public class ModuleServiceImpl implements ModuleService {
	
	private BaseDao baseDao;
	
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Page<Module> findPage(String hql, Page<Module> page,
			Class<Module> pojoClass, Object[] params) {
		
		return baseDao.findPage(hql, page, pojoClass, params);
	}

	@Override
	public Module get(Class<Module> pojoClass, Serializable id) {
		return baseDao.get(pojoClass, id);
	}

	@Override
	public List<Module> find(String hql, Class<Module> pojoClass,
			Object[] params) {
		return baseDao.find(hql, pojoClass, params);
	}

	@Override
	public void saveOrUpdate(Module pojo) {
		if(StringUtils.isBlank(pojo.getId())){
			//新增
			baseDao.saveOrUpdate(pojo);
		}else{
			//修改
			Module module = baseDao.get(Module.class, pojo.getId());
			
			module.setName(pojo.getName());
			module.setLayerNum(pojo.getLayerNum());
			module.setCpermission(pojo.getCpermission());
			module.setCurl(pojo.getCurl());
			
			module.setCtype(pojo.getCtype());
			module.setState(pojo.getState());
			module.setBelong(pojo.getBelong());
			module.setCwhich(pojo.getCwhich());
			
			module.setRemark(pojo.getRemark());
			module.setOrderNo(pojo.getOrderNo());
			
			baseDao.saveOrUpdate(module);
		}
		
		
	}

	@Override
	public void deleteById(Class<Module> pojoClass, Serializable id) {
		
		baseDao.deleteById(pojoClass, id);
	}

	@Override
	public void delete(Class<Module> pojoClass, Serializable[] ids) {
	for (Serializable id : ids) {
		deleteById(pojoClass, id);
	}
	}

}
