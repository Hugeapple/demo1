package cn.itcast.erp.quartz;

import java.util.Date;

/**
 * 类名随意
 * @Description:
 * @author:     传智播客 java学院    传智.袁新奇
 * @version:    1.0
 * @Company:    http://java.itcast.cn 
 * @date:       2017年3月1日
 */
public class JobTest {
	
//	写方法，方法名也是随意
	public void execute(){
		System.out.println("执行了调度:"+new Date());
	}
	
}
