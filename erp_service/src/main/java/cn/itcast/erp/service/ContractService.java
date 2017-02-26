package cn.itcast.erp.service;

import java.io.Serializable;
import java.util.List;

import cn.itcast.erp.pojo.Contract;
import cn.itcast.erp.utils.Page;

/**
 * 
 * 说明：
 * @author bigApple
 * @version 1.0
 * @date 2017年2月16日
 */
public interface ContractService {

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
	
	public abstract Page<Contract> findPage(String hql,Page<Contract> page, Class<Contract> pojoClass, Object[] params);
	
	public  Contract get(Class<Contract> pojoClass ,Serializable id);
	
	public List<Contract> find(String hql,Class<Contract> pojoClass,Object[] params);
	
	public void saveOrUpdate(Contract pojo);
	
	public void delete(Class<Contract> pojoClass,Serializable[] ids);

	public void deleteById(Class<Contract> pojoClass, Serializable id);
}
