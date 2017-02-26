package cn.itcast.erp.service;

import java.io.Serializable;
import java.util.List;

import cn.itcast.erp.pojo.ContractProduct;
import cn.itcast.erp.utils.Page;

/**
 * 
 * 说明：
 * @author bigApple
 * @version 1.0
 * @date 2017年2月16日
 */
public interface ContractProductService {

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
	
	public abstract Page<ContractProduct> findPage(String hql,Page<ContractProduct> page, Class<ContractProduct> pojoClass, Object[] params);
	
	public  ContractProduct get(Class<ContractProduct> pojoClass ,Serializable id);
	
	public List<ContractProduct> find(String hql,Class<ContractProduct> pojoClass,Object[] params);
	
	public void saveOrUpdate(ContractProduct pojo);
	

	public void deleteById(Class<ContractProduct> pojoClass, Serializable id);

}
