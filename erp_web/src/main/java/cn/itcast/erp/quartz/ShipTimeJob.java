package cn.itcast.erp.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.itcast.erp.pojo.Contract;
import cn.itcast.erp.service.ContractService;
import cn.itcast.erp.utils.MailUtil;

public class ShipTimeJob {
	
	private ContractService contractService;
	
	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}


	/**
	 * 业务逻辑：查找今天需要上船的合同，给厂家发送电子邮件催货
	 * 1 查询数据库,查询今天需要发货的购销合同
	 * 2 发送电子邮件
	 */
	public void sendMail(){
		// 1 查询今天需要发货的合同信息
		//在java中，格式化日期类型的时候,MM代表月 ,mm代表分钟
		String time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		//oralce中，大写MM和小写的mm都代表月
		String hql = "from Contract where to_char(shipTime,'yyyy-mm-dd')= ?";
		//结果集
		List<Contract> list = contractService.find(hql, Contract.class, new String[]{time});
		
		for(final Contract c:list){
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						MailUtil.sendMail("1002552230@qq.com", "船期到了，该交货了", "主人，你好，今天合同编号为:"+c.getContractNo()+"的合同需要交货，您准备好了吗？如果没有准备好，请联系120");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}).start();
			//每次发邮件的时候都稍微慢一点
			/*try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		
		
		
	}
	
	
}
