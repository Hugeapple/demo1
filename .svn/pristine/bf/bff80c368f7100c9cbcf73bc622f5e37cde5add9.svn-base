package cn.itcast.erp.service;

import java.io.Serializable;
import java.util.List;

import cn.itcast.erp.pojo.User;
import cn.itcast.erp.utils.Page;

public interface UserService {
	
	
	
	public abstract Page<User> findPage(String hql, Page<User> page,Class<User> pojoClass,Object[] params);
	
	public abstract User get(Class<User> pojoClass,Serializable id);
	
	public List<User> find(String hql,Class<User> pojoClass,Object[] params);

	public abstract void saveOrUpdate(User pojo);

	public abstract void deleteById(Class<User> pojoClass, Serializable id);

	public abstract void delete(Class<User> pojoClass, String[] ids);

	public abstract User finUserByName(String username);
	
}
