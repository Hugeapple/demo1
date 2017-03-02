package cn.itcast.erp.service;

import java.io.Serializable;
import java.util.List;

import cn.itcast.erp.pojo.ExtEproduct;
import cn.itcast.erp.utils.Page;


public interface ExtEproductService {
	public abstract Page<ExtEproduct> findPage(String hql, Page<ExtEproduct> page, Class<ExtEproduct> entityClass, Object[] params);
	
	public abstract ExtEproduct get(Class<ExtEproduct> entityClass, Serializable id);
	
	public List<ExtEproduct> find(String hql, Class<ExtEproduct> entityClass, Object[] params);
	
	public void saveOrUpdate(ExtEproduct entity);
	//单条删除，按id
	public void deleteById(Class<ExtEproduct> entityClass, Serializable id);
	//批量删除
	public void delete(Class<ExtEproduct> entityClass, Serializable[] ids);
}
