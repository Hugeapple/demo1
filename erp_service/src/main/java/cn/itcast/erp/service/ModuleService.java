package cn.itcast.erp.service;

import java.io.Serializable;
import java.util.List;

import cn.itcast.erp.pojo.Module;
import cn.itcast.erp.utils.Page;

public interface ModuleService {


	public abstract Page<Module> findPage(String hql, Page<Module> page,Class<Module> pojoClass,Object[] params);
	
	public abstract Module get(Class<Module> pojoClass,Serializable id);
	
	public List<Module> find(String hql,Class<Module> pojoClass,Object[] params);

	public abstract void saveOrUpdate(Module pojo);

	public abstract void deleteById(Class<Module> pojoClass, Serializable id);

	public abstract void delete(Class<Module> pojoClass, Serializable[] ids);

}
