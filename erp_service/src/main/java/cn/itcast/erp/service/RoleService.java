package cn.itcast.erp.service;

import java.io.Serializable;
import java.util.List;

import cn.itcast.erp.pojo.Role;
import cn.itcast.erp.utils.Page;

public interface RoleService {
	
	

	public abstract Page<Role> findPage(String hql, Page<Role> page,Class<Role> pojoClass,Object[] params);
	
	public abstract Role get(Class<Role> pojoClass,Serializable id);
	
	public List<Role> find(String hql,Class<Role> pojoClass,Object[] params);

	public abstract void saveOrUpdate(Role pojo);

	public abstract void deleteById(Class<Role> pojoClass, Serializable id);

	public abstract void delete(Class<Role> pojoClass, Serializable[] ids);

}
