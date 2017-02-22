package cn.itcast.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import cn.itcast.erp.dao.BaseDao;
import cn.itcast.erp.utils.Page;

public class BaseDaoImpl implements BaseDao{
	
	private SessionFactory sessionFactory;
	

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	//带条件查询
	@Override
	public <T> List<T> find(String hql, Class<T> pojoClass, Object[] params) {
		Query query = this.getSession().createQuery(hql);
		
		if(params!=null){
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return (List<T>) query.list();
	}
	
	
	//获取一条，根据主键id
	@Override
	public <T> T get(Class<T> pojoClass, Serializable id) {
		return (T) this.getSession().get(pojoClass, id);
		
	}
	
	
	//分页查询，查询两次，一次查询总数，一次查询分页记录
	@Override
	public <T> Page<T> findPage(String hql, Page<T> page, Class<T> pojoClass,
			Object[] params) {
		
		Query query = this.getSession().createQuery(hql);
		
		if(params!=null){
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		
		int count = query.list().size();
		page.setTotalRecord(count);
		
		query.setFirstResult((page.getPageNo()-1)*page.getPageSize());
		query.setMaxResults(page.getPageSize());
		page.setResults((List<T>) query.list());
		return page;
	}
	
	
	
	
	
	//新增和修改，hibernate根据id是否为null自动判断
	@Override
	public <T> void saveOrUpdate(T pojo) {
		this.getSession().save(pojo);
	}
	
	
	//集合保存，这时新增还是修改，就自动判断，调用时是否简洁。适合批量新增和修改时。
	@Override
	public <T> void saveOrUpdateAll(Collection<T> pojos) {
		for( T pojo :pojos){
			this.saveOrUpdate(pojo);
		}
	}
	
	
	
	//按主键id删除
	@Override
	public <T> void deleteById(Class<T> pojoClass, Serializable id) {
			this.getSession().delete(get(pojoClass, id));
	}

	//批量删除
	@Override
	public <T> void delete(Class<T> pojoClass, Serializable[] ids) {
			for(Serializable s : ids)
				deleteById(pojoClass, s);
	}

}
