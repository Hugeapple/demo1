package cn.itcast.erp.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

public class Encrypt {
	
	
	
	public static String md5(String password,String salt){
		
		return new Md5Hash(password,salt,2).toString();
		
		
	}
	
}
