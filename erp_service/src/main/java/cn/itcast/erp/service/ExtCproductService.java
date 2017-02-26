package cn.itcast.erp.service;

import java.io.Serializable;
import java.util.List;

import cn.itcast.erp.pojo.ExtCproduct;
import cn.itcast.erp.utils.Page;

/**
 * 
 * 说明：
 * @author bigApple
 * @version 1.0
 * @date 2017年2月16日
 */
public interface ExtCproductService {

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
	
	public abstract Page<ExtCproduct> findPage(String hql,Page<ExtCproduct> page, Class<ExtCproduct> pojoClass, Object[] params);
	
	public  ExtCproduct get(Class<ExtCproduct> pojoClass ,Serializable id);
	
	public List<ExtCproduct> find(String hql,Class<ExtCproduct> pojoClass,Object[] params);
	
	public void saveOrUpdate(ExtCproduct pojo);

	public abstract void delete(Class<ExtCproduct> pojoClass, ExtCproduct model);
}
