package cn.itcast.erp.service;

import java.io.Serializable;
import java.util.List;

import cn.itcast.erp.pojo.Factory;
import cn.itcast.erp.utils.Page;

/**
 * 
 * 说明：
 * @author bigApple
 * @version 1.0
 * @date 2017年2月16日
 */
public interface FactoryService {

	/**
	 * 
	 * 说明：
	 * @param hql 查询语句
	 * @param page
	 * @param pojoClass
	 * @param params
	 * @return
	 * @author bigApple
	 * @time：2017年2月16日 下午7:37:07
	 */
	
	public abstract Page<Factory> findPage(String hql,Page<Factory> page, Class<Factory> pojoClass, Object[] params);
	
	public  Factory get(Class<Factory> pojoClass ,Serializable id);
	
	public List<Factory> find(String hql,Class<Factory> pojoClass,Object[] params);
	
	public void saveOrUpdate(Factory pojo);
	
	public void delete(Class<Factory> pojoClass,Serializable[] ids);

	public void deleteById(Class<Factory> pojoClass, Serializable id);
}
