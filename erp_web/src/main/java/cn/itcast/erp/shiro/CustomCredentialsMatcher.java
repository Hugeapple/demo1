package cn.itcast.erp.shiro;

import org.apache.poi.EncryptedDocumentException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import cn.itcast.erp.utils.Encrypt;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher{

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) {
		
			UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		
			String oldPwd = String.valueOf(upToken.getPassword());
			
			String newPwd = Encrypt.md5(oldPwd, upToken.getUsername());
			
			Object dbPwd = info.getCredentials();
			
			return equals(newPwd,dbPwd);
		
		
	}
	
	
	
	
	
	
	
	
	
}
