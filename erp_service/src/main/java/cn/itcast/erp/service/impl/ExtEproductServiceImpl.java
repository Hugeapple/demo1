package cn.itcast.erp.service.impl;

import java.io.Serializable;
import java.util.List;
import cn.itcast.erp.dao.BaseDao;
import cn.itcast.erp.pojo.ExtEproduct;
import cn.itcast.erp.service.ExtEproductService;
import cn.itcast.erp.utils.Page;

public class ExtEproductServiceImpl implements ExtEproductService{
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	@Override
	public Page<ExtEproduct> findPage(String hql, Page<ExtEproduct> page,
			Class<ExtEproduct> entityClass, Object[] params) {
		return baseDao.findPage(hql, page, entityClass, params);
	}
	public ExtEproduct get(Class<ExtEproduct> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	@Override
	public List<ExtEproduct> find(String hql, Class<ExtEproduct> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	/**
	 * 新增出口报运单的业务逻辑： (进入这个页面，我们拥有知识合同的id集合)
	 * 1 保存报运单信息
	 * 2 保存报运货物信息和报运货物对应的附件信息
	 * 3 改变合同的状态
	 */
	public void saveOrUpdate(ExtEproduct entity) {
		
		
	}

	@Override
	public void deleteById(Class<ExtEproduct> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<ExtEproduct> entityClass, Serializable[] ids) {
		for(Serializable id:ids){
			deleteById(entityClass, id);
		}
	}

}
