package cn.itcast.erp.common;

import org.apache.log4j.Logger;

/**
 * @Description:
 * @Author:		传智播客 java学院	传智.袁新奇
 * @Company:	http://java.itcast.cn
 * @CreateDate:	2014年10月31日
 */
/*
 * 系统全局常量配置类
 */
public class SysConstant {
	//	订单提交状态
	public static final Integer CONTRACT_STATE_DISABLE = 0;
	public static final Integer CONTRACT_STATE_ENABLE = 1;

	public static final String ContractProduct_STATE_ENABLED = null;

	public static final String ExtCproduct_STATE_ENABLED = null;

	public static final String Factory_STATE_ENABLED = null;
	
	
	
	
	

	private static Logger log = Logger.getLogger(SysConstant.class);

	public static String CURRENT_USER_INFO = "_CURRENT_USER";	//当前用户session name
	public static String USE_DATABASE = "Oracle";				//使用的数据库 Oracle/SQLServer
	public static String USE_DATABASE_VER = "oracle11ORCL";				//使用的数据库版本 10g/2000

	public static String DEFAULT_PASS = "123456";				//默认密码
	public static Integer PAGE_SIZE =6;	
	//分页时一页显示多少条记录
	public static Integer DEPT_STATE_ENABLED=1;
	
	public static Integer DEPT_STATE_DISABLED=0;
}
