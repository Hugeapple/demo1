package cn.itcast.erp.action.stat;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import cn.itcast.erp.action.BaseAction;
import cn.itcast.erp.dao.common.SqlDao;
import cn.itcast.erp.utils.file.FileUtil;

public class StatChartAction extends BaseAction {

	private SqlDao sqlDao;



	public void setSqlDao(SqlDao sqlDao) {
		this.sqlDao = sqlDao;
	}



	public String factorysale() throws Exception {

		String sql = "select factory_name,sum(amount) samount from contract_product_c "
				+ "group by factory_name order by samount desc";

		List<String> list = sqlDao.executeSQL(sql);

		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version='1.0' encoding='UTF-8'?>");

		sb.append("<pie>");

		for (int i = 0; i < list.size(); i++) {

			sb.append("<slice title='" + list.get(i) + "'>" + list.get(++i) + "</slice>");

		}

		sb.append("</pie>");

		String sPath = ServletActionContext.getRequest().getRealPath("/") + "stat\\chart\\factorysale";

		FileUtil fileUtil = new FileUtil();
		/**
		 * 第一个参数：服务器存放data.xml的路径 第二个参数：文件名 第三个参数：文件中的内容 第四个参数：编码方式
		 */
		fileUtil.createTxt(sPath, "data.xml", sb.toString(), "UTF-8");

		return "factorysale";

	}
	
	/**
	 * 产品销量排行 
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	public String productsale() throws Exception {
		
		String sql ="select * from (select product_no,sum(cnumber) samount "
				+ "from contract_product_c group by product_no order by samount desc)"
				+ " where rownum < 16";
		
		List<String> list = sqlDao.executeSQL(sql);
		StringBuffer sb = new StringBuffer();
		
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<chart>");
		/********打印第一块********/
		sb.append("<series>");
		for(int i =0,j=0;j<list.size();i++,j+=2){
			sb.append("<value xid='"+i+"'>"+list.get(j)+"</value>");
		}
		sb.append("</series>");
		
		
		/********打印第二块********/
		sb.append("<graphs><graph gid='30' color='#FFCC00' gradient_fill_colors='#111111, #1A897C'>");
		for(int i=0,j=1;j<list.size();i++,j+=2){
			sb.append("<value xid='"+i+"' description='' url=''>"+list.get(j)+"</value>");
		}
		sb.append("</graph></graphs>");
		
		/********打印第三块********/
		sb.append("<labels><label lid='0'><x>0</x><y>20</y><rotate></rotate><width></width>");
		sb.append("<align>center</align><text_color></text_color><text_size></text_size>");
		sb.append("<text><![CDATA[<b>产品销量排行榜</b>]]></text></label></labels>");
		sb.append("</chart>");
//		/写
		String sPath = ServletActionContext.getRequest().getRealPath("/")+"stat/chart/productsale";
		FileUtil fileUtil = new FileUtil();
		fileUtil.createTxt(sPath, "data.xml", sb.toString(), "UTF-8");
		return "productsale";
	}
	public String onlineinfo() throws Exception {
		// 1 查找数据
		String sql = "select a.*,nvl(b.c,0) from online_info_t a "
				+ "left join (select to_char(login_time,'hh24') a1,count(*) c "
				+ "from login_log_p group by to_char(login_time,'hh24')) b "
				+ "on a.a1 = b.a1 order by a.a1 asc";
		//2  执行sql，获取数据
		List<String> list = sqlDao.executeSQL(sql);
		
		//3 拼装数据
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<chart>");
		/*******第一部分*******/
		sb.append("<series>");
		for(int i = 0,j=0;i<list.size();i+=2,j++){
			sb.append("<value xid='"+j+"'>"+list.get(i)+"</value>");
		}
		sb.append("</series>");
		
		/*******第二部分******/
		sb.append("<graphs>");
		sb.append("<graph color='#00CC00' title=''>");
		for(int i=1,j=0;i<list.size();i+=2,j++){
			sb.append("<value xid='"+j+"'>"+list.get(i)+"</value>");
		}
		sb.append("</graph>");
		sb.append("</graphs>");
		
		sb.append("</chart>");
		// 将内容写到服务器
		String sPath = ServletActionContext.getRequest().getRealPath("/")+"stat/chart/onlineinfo";
		
		FileUtil fileUtil = new FileUtil();
		fileUtil.createTxt(sPath, "data.xml", sb.toString(), "UTF-8");
		return "onlineinfo";
	}

	public String productsaleJson() throws Exception {
		// 1 查询数据
		//1  编写sql
		String sql = "select * from (select product_no ,sum(cnumber) amount "
				+ "from contract_product_c group by product_no "
				+ "order by amount desc) where rownum<16";
		// 2 执行sql
		List<String> list = sqlDao.executeSQL(sql);
		
		// 3 拼装数据
		String[] colors ={"#FF0F00","#FF6600","#FF9E01","#FCD202","#F8FF01","#B0DE09","#04D215","#0D8ECF","#0D52D1",
				"#2A0CD0","#8A0CCF","#CD0D74","#754DEB","#DDDDDD","#333333","#000000"};
		
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		/**
		 * {
            "country": "USA",
            "visits": 4025,
            "color": "#FF0F00"
           }
		 */
		for(int i = 0,j=0;i<list.size();i++,j++){
			sb.append("{");
			sb.append("'product_no':'"+list.get(i)+"',");
			sb.append("'amount':"+list.get(++i)+",");
			sb.append("'color':'"+colors[j]+"'");
			sb.append("},");
		}
		//去除最好的,
		sb.delete(sb.length()-1, sb.length());
		
		sb.append("]");
		
		//
		this.put("chartData", sb.toString());
		
		
		return "productsaleJson";
	}
	


}
