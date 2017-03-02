package cn.itcast.erp.service;

import java.io.Serializable;
import java.util.List;

import cn.itcast.erp.pojo.ExportProduct;
import cn.itcast.erp.utils.Page;


public interface ExportProductService {
	public abstract Page<ExportProduct> findPage(String hql, Page<ExportProduct> page, Class<ExportProduct> entityClass, Object[] params);
	
	public abstract ExportProduct get(Class<ExportProduct> entityClass, Serializable id);
	
	public List<ExportProduct> find(String hql, Class<ExportProduct> entityClass, Object[] params);
	
	public void saveOrUpdate(ExportProduct entity);
	//单条删除，按id
	public void deleteById(Class<ExportProduct> entityClass, Serializable id);
	//批量删除
	public void delete(Class<ExportProduct> entityClass, Serializable[] ids);
}
