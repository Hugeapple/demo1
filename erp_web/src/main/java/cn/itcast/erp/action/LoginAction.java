package cn.itcast.erp.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.hibernate.Hibernate;

import cn.itcast.erp.common.SysConstant;
import cn.itcast.erp.pojo.User;


/**
 * 
 * @Description:
 * @author:     传智播客 java学院    传智.袁新奇
 * @version:    1.0
 * @Company:    http://java.itcast.cn 
 * @date:       2016年9月17日
 */
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;



	//SSH传统登录方式
	public String login() throws Exception {
		
		if(StringUtils.isBlank(username)){
			return LOGIN;
		}
		
		
		try{
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);
			
			User principal = (User) subject.getPrincipal();
			
			session.put(SysConstant.CURRENT_USER_INFO, principal);
		}catch(Exception e){
			
			e.printStackTrace();
			
			request.put("errorInfo", "对不起，登录失败");
			
			return LOGIN;
		}
		
		return SUCCESS;
		
	}
	
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

