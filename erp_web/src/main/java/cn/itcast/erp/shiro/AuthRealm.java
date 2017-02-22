package cn.itcast.erp.shiro;

import java.util.ArrayList;
import java.util.Set;




import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.struts2.ServletActionContext;

import cn.itcast.erp.common.SysConstant;
import cn.itcast.erp.pojo.Module;
import cn.itcast.erp.pojo.Role;
import cn.itcast.erp.pojo.User;
import cn.itcast.erp.service.UserService;

public class AuthRealm extends AuthorizingRealm {
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {

		User user = (User) principals.fromRealm(getName()).iterator().next();

		Set<Role> roles = user.getRoles();

		ArrayList<String> list = new ArrayList<String>();

		for (Role r : roles) {

			Set<Module> modules = r.getModules();

			for (Module module : modules) {
				if (module.getCtype() == 0) {
					list.add(module.getCpermission());

				}
			}
		}	
//		ServletActionContext.getRequest().getSession().setAttribute(SysConstant.CURRENT_USER_INFO, user);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		
		info.addStringPermissions(list);
		
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {

		System.out.println("认证");

		UsernamePasswordToken upToken = (UsernamePasswordToken) token;

		User user = userService.finUserByName(upToken.getUsername());

		if (user == null) {

			return null;
		} else {

			AuthenticationInfo info = new SimpleAuthenticationInfo(user,
					user.getPassword(), getName());

			return info;
		}

	}

}
