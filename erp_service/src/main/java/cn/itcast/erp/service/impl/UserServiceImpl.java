package cn.itcast.erp.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import cn.itcast.erp.common.SysConstant;
import cn.itcast.erp.dao.BaseDao;
import cn.itcast.erp.pojo.User;
import cn.itcast.erp.service.UserService;
import cn.itcast.erp.utils.Encrypt;
import cn.itcast.erp.utils.MailUtil;
import cn.itcast.erp.utils.Page;

public class UserServiceImpl  implements UserService{
	
	private BaseDao baseDao;
	
	
	
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	private SimpleMailMessage  mailMessage;
	
	private JavaMailSenderImpl mailSender;
	public void setMailMessage(SimpleMailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}


	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}


	/**
	 * 分页显示
	 */

	@Override
	public Page<User> findPage(String hql, Page<User> page,
			Class<User> pojoClass, Object[] params) {
		
		return baseDao.findPage(hql, page, pojoClass, params);
	}


	@Override
	public User get(Class<User> pojoClass, Serializable id) {
		return baseDao.get(pojoClass, id);
		
	}


	@Override
	public List<User> find(String hql, Class<User> pojoClass, Object[] params) {
		return baseDao.find(hql, pojoClass, params);
	}


	@Override
	public void saveOrUpdate( final User model) {
		
		if(StringUtils.isBlank(model.getId())){
			//新增
			String id = UUID.randomUUID().toString();
			model.setId(id);
			model.getUserInfo().setId(id);
			final String randomPwd = randomPwd();
			
			//设置新增用户的密码
			String pwd = Encrypt.md5(randomPwd, model.getUserName());
			model.setPassword(pwd);
//			model.setCreateTime(new Date());
//			model.getUserInfo().setCreateTime(new Date());
			baseDao.saveOrUpdate(model);
			
			
			new Thread(new Runnable(){

				@Override
				public void run() {
					mailMessage.setTo(model.getUserInfo().getEmail());
					
					mailMessage.setSubject("欢迎加入杰信商贸集团");
					
					mailMessage.setText("员工入职通知，您的账号："+model.getUserName()+",密码"+randomPwd);
					
					mailSender.send(mailMessage);
				}
				
			}).start();
			/*//发送邮件，将用户的用户名和密码发出去
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						MailUtil.sendMail(model.getUserInfo().getEmail(), 
								"欢迎加入百合网", "您的账号是："+model.getUserName()+
								",密码是:"+randomPwd);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
			*/
			
			
			
		}else{
//			修改
			User user = baseDao.get(User.class, model.getId());
			user.setDept(model.getDept());
			user.setUserName(model.getUserName());
			user.setState(model.getState());
			
			baseDao.saveOrUpdate(user);
			
		}

	}

	@Override
	public void deleteById(Class<User> pojoClass, Serializable id) {
		
		User user = baseDao.get(pojoClass, id);
		
		user.setState(SysConstant.DEPT_STATE_DISABLED);
		
		baseDao.saveOrUpdate(user);
		
		
	}


	@Override
	public void delete(Class<User> pojoClass, String[] ids) {
			for (String id : ids) {
				deleteById(pojoClass, id);
			}
	}


	@Override
	public User finUserByName(String username) {
		String hql="from User where userName=?";
		List<User> list = baseDao.find(hql, User.class, new String[]{username});
		
		return list.size()==0?null:list.get(0);
	}
	
	public String randomPwd(){
		String values = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+";
		Random random = new Random();
		String pwd = "";
		for(int i =0;i<8;i++){
//			随机截去字符
			char c = values.charAt(random.nextInt(values.length()));
			pwd+=c;
		}
		
		return pwd;
	}


}
