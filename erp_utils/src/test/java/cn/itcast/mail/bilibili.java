package cn.itcast.mail;

import org.junit.Test;

public class bilibili {
	
	public static void main(String[] args) {
		String str = "hello\r\n大家好";
		
		String l = str.replaceAll("\\r\\n", "<br>");
		System.out.println(l);
	}
	
}
