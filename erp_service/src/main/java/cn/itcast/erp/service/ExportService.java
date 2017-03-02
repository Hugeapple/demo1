package cn.itcast.erp.service;

import java.io.Serializable;
import java.util.List;

import cn.itcast.erp.pojo.Export;
import cn.itcast.erp.utils.Page;


public interface ExportService {
	public abstract Page<Export> findPage(String hql, Page<Export> page, Class<Export> entityClass, Object[] params);
	
	public abstract Export get(Class<Export> entityClass, Serializable id);
	
	public List<Export> find(String hql, Class<Export> entityClass, Object[] params);
	
	public void saveOrUpdate(Export entity);
	//单条删除，按id
	public void deleteById(Class<Export> entityClass, Serializable id);
	//批量删除
	public void delete(Class<Export> entityClass, Serializable[] ids);
}
