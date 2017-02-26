package cn.itcast.erp.service;

import java.io.Serializable;
import java.util.List;

import cn.itcast.erp.pojo.Dept;
import cn.itcast.erp.utils.Page;

/**
 * 
 * 说明：
 * @author bigApple
 * @version 1.0
 * @date 2017年2月16日
 */
public interface DeptService {

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
	
	public abstract Page<Dept> findPAge(String hql,Page<Dept> page, Class<Dept> pojoClass, Object[] params);
	
	public  Dept get(Class<Dept> pojoClass ,Serializable id);
	
	public List<Dept> find(String hql,Class<Dept> pojoClass,Object[] params);
	
	public void saveOrUpdate(Dept pojo);
	
	public void delete(Class<Dept> pojoClass,Serializable[] ids);

	public void deleteById(Class<Dept> pojoClass, Serializable id);
}
