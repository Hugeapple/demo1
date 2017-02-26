package cn.itcast.erp.action.cargo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import cn.itcast.erp.action.BaseAction;
import cn.itcast.erp.pojo.ContractProduct;
import cn.itcast.erp.service.ContractProductService;
import cn.itcast.erp.utils.DownloadUtil;
import cn.itcast.erp.utils.UtilFuns;

public class OutProductAction extends BaseAction {
	
	private String inputDate;
	private ContractProductService contractProductService;
	
	
	public void setContractProductService(
			ContractProductService contractProductService) {
		this.contractProductService = contractProductService;
	}
	
	
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}


	public String toedit() throws Exception{
		
		
		return "toedit";
		
	}
	public String printWithTemplete() throws Exception {
		//获取模板对象，通过流去读取模板对象
		// 获取服务器的地址
		String path = ServletActionContext.getRequest().getRealPath("/");
		
		System.out.println("path："+path);
		
		//组装path
		path+="/make/xlsprint/tOUTPRODUCT.xlsx";
		
		//创建流
		FileInputStream inputStream = new FileInputStream(new File(path));
		
		//0 工作表的创建
//		Workbook wb = new HSSFWorkbook(inputStream);
		Workbook wb = new XSSFWorkbook(inputStream);
		
		// 获取sheet
		Sheet sheet = wb.getSheetAt(0);
		
		//定义一些公共变量
		Row nRow=null;
		Cell nCell =null;
		
		int rowNo=0;//行号
		int cellNo=1;//列号
		
	
		
		/***1大标题****/
		nRow = sheet.getRow(rowNo);
		nCell = nRow.getCell(cellNo);
		
		
		/**
		 * 设置内容
		 * 2015-07   2015-7   2015年7月份出货表
		 * 2015-11   2015年11月份出货表
		 * 
		 */
		//方式一
//		String value = inputDate.replace("-0", "-").replace("-", "年");
		//方式二
		String value = inputDate.replace("-0", "年").replace("-", "年");
		nCell.setCellValue(value+"月份出货表");
		//设置文本样式:不需要设置文本样式了，因为模板中已经有样式了
//		nCell.setCellStyle(bigTitle(wb));
		
		/***2小标题***/
		//行号变化
		rowNo++;
		
		/*****3文本*****/
		rowNo++;
		/**
		 * 1 获取样式
		 * 2 第三行重新创建
		 * 3 循环输出数据进入xls表格
		 */
		// 1.1 获取行对象
		nRow = sheet.getRow(rowNo);
		// 1.2 获取单元格样式
		//客户
		CellStyle customerCellStyle = nRow.getCell(cellNo++).getCellStyle();
		// 订单号
		CellStyle contractNoCellStyle = nRow.getCell(cellNo++).getCellStyle();
		//货号
		CellStyle productNoCellStyle = nRow.getCell(cellNo++).getCellStyle();
		//数量
		CellStyle cnumberCellStyle = nRow.getCell(cellNo++).getCellStyle();
		//工厂名字
		CellStyle factoryNameCellStyle = nRow.getCell(cellNo++).getCellStyle();
		//交期
		CellStyle deliveryPeriodCellStyle = nRow.getCell(cellNo++).getCellStyle();
		//船期
		CellStyle shipTimeCellStyle = nRow.getCell(cellNo++).getCellStyle();
		//贸易条款
		CellStyle tradeTermsCellStyle = nRow.getCell(cellNo++).getCellStyle();
		
		
		//查找数据
//		String hql = "from ContractProduct where contract.shipTime like '%"+inputDate+"%'";//这种mysql支持，oracle 不支持
		//pl/sql函数：tochar,oracle的pl/sql函数可以直接写在HQL语句中
//		String hql = "from ContractProduct where to_char(contract.shipTime,'yyyy-mm') = '"+inputDate+"'";
		String hql = "from ContractProduct where to_char(contract.shipTime,'yyyy-mm') = ?";
		List<ContractProduct> list = contractProductService.find(hql, ContractProduct.class, new String[]{inputDate});
		
		
		
		//遍历数据，创建行对象，在行中创建列对象
		for(ContractProduct cp:list){
			//列号归1
			cellNo=1;
			nRow = sheet.createRow(rowNo++);
			
			//客户
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getContract().getCustomName());
			nCell.setCellStyle(customerCellStyle);
			
			//订单号
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getContract().getContractNo());
			nCell.setCellStyle(contractNoCellStyle);
			
			//货号
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getProductNo());
			nCell.setCellStyle(productNoCellStyle);
			//数量
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getCnumber());
			nCell.setCellStyle(cnumberCellStyle);
			//工厂
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getFactoryName());		
			nCell.setCellStyle(factoryNameCellStyle);
			//交期
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getDeliveryPeriod()));		
			nCell.setCellStyle(deliveryPeriodCellStyle);
			
			//船期
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getShipTime()));		
			nCell.setCellStyle(shipTimeCellStyle);
			
			//贸易条款
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getContract().getTradeTerms());		
			nCell.setCellStyle(tradeTermsCellStyle);
			
			
		}
		
		
		/*****4下载*****/
		
		DownloadUtil downloadUtil = new DownloadUtil();
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		wb.write(byteArrayOutputStream);
		
		//获取response
		HttpServletResponse response = ServletActionContext.getResponse();
		
		/**
		 * 第一个参数：输出流
		 * 第二个参数：response
		 * 第三个参数：返回的name
		 */
		downloadUtil.download(byteArrayOutputStream, response, "出货表.xlsx");
		
		
		return NONE;
	}
	
	/**
	 * 不适用模板打印
	 * 
	 * @return
	 * @throws Exception
	 */
	public String print() throws Exception {
		//0 工作表的创建
//		Workbook wb = new HSSFWorkbook();
		Workbook wb = new SXSSFWorkbook();
		// 创建sheet
		Sheet sheet = wb.createSheet();
		
		//定义一些公共变量
		Row nRow=null;
		Cell nCell =null;
		
		int rowNo=0;//行号
		int cellNo=1;//列号
		
		//设置列宽
		sheet.setColumnWidth(0, 6*256);
		sheet.setColumnWidth(1, 26*256);
		sheet.setColumnWidth(2, 12*256);
		sheet.setColumnWidth(3, 30*256);
		sheet.setColumnWidth(4, 12*256);
		sheet.setColumnWidth(5, 15*256);
		sheet.setColumnWidth(6, 10*256);
		sheet.setColumnWidth(7, 10*256);
		sheet.setColumnWidth(8, 10*256);
		
		/***1大标题****/
		nRow = sheet.createRow(rowNo);
		nCell = nRow.createCell(cellNo);
		
		//设置合并单元格
		sheet.addMergedRegion(new CellRangeAddress(0,0,1,8));
		//设置行高
		nRow.setHeightInPoints(36f);
		
		/**
		 * 设置内容
		 * 2015-07   2015-7   2015年7月份出货表
		 * 2015-11   2015年11月份出货表
		 * 
		 */
		//方式一
//		String value = inputDate.replace("-0", "-").replace("-", "年");
		//方式二
		String value = inputDate.replace("-0", "年").replace("-", "年");
		nCell.setCellValue(value+"月份出货表");
		//设置文本样式
		nCell.setCellStyle(bigTitle(wb));
		
		/***2小标题***/
		//行号变化
		rowNo++;
		//创建行对象
		nRow = sheet.createRow(rowNo);
//		设置行高
		nRow.setHeightInPoints(27f);
		String[] titles = {"客户","订单号","货号","数量","工厂","工厂交期","船期","贸易条款"};
		
		for(String title:titles){
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(title);
			nCell.setCellStyle(title(wb));
		}
		
		/*****3文本*****/
		//查找数据
//		String hql = "from ContractProduct where contract.shipTime like '%"+inputDate+"%'";//这种mysql支持，oracle 不支持
		//pl/sql函数：tochar,oracle的pl/sql函数可以直接写在HQL语句中
//		String hql = "from ContractProduct where to_char(contract.shipTime,'yyyy-mm') = '"+inputDate+"'";
		String hql = "from ContractProduct where to_char(contract.shipTime,'yyyy-mm') = ?";
		List<ContractProduct> list = contractProductService.find(hql, ContractProduct.class, new String[]{inputDate});
		
		
		
		//遍历数据，创建行对象，在行中创建列对象
		for(ContractProduct cp:list){
			//列号归1
			cellNo=1;
			nRow = sheet.createRow(++rowNo);
			
			//客户
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getContract().getCustomName());
			nCell.setCellStyle(text(wb));
			
			//订单号
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getContract().getContractNo());
			nCell.setCellStyle(text(wb));
			
			//货号
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getProductNo());
			nCell.setCellStyle(text(wb));
			//数量
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getCnumber());
			nCell.setCellStyle(text(wb));
			//工厂
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getFactoryName());		
			nCell.setCellStyle(text(wb));
			//交期
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getDeliveryPeriod()));		
			nCell.setCellStyle(text(wb));
			
			//船期
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(UtilFuns.dateTimeFormat(cp.getContract().getShipTime()));		
			nCell.setCellStyle(text(wb));
			
			//贸易条款
			nCell = nRow.createCell(cellNo++);
			nCell.setCellValue(cp.getContract().getTradeTerms());		
			nCell.setCellStyle(text(wb));
			
		}
		
		
		/*****4下载*****/
		
		DownloadUtil downloadUtil = new DownloadUtil();
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		wb.write(byteArrayOutputStream);
		
		//获取response
		HttpServletResponse response = ServletActionContext.getResponse();
		
		/**
		 * 第一个参数：输出流
		 * 第二个参数：response
		 * 第三个参数：返回的name
		 */
		downloadUtil.download(byteArrayOutputStream, response, "出货表.xlsx");
		
		
		return NONE;
	}
	
	//大标题的样式
	public CellStyle bigTitle(Workbook wb){
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short)16);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);					//字体加粗
		
		style.setFont(font);
		
		style.setAlignment(CellStyle.ALIGN_CENTER);					//横向居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
		
		return style;
	}
	//小标题的样式
	public CellStyle title(Workbook wb){
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short)12);
		
		style.setFont(font);
		
		style.setAlignment(CellStyle.ALIGN_CENTER);					//横向居中
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
		
		style.setBorderTop(CellStyle.BORDER_THIN);					//上细线
		style.setBorderBottom(CellStyle.BORDER_THIN);				//下细线
		style.setBorderLeft(CellStyle.BORDER_THIN);					//左细线
		style.setBorderRight(CellStyle.BORDER_THIN);				//右细线
		
		return style;
	}
	
	//文字样式
	public CellStyle text(Workbook wb){
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("Times New Roman");
		font.setFontHeightInPoints((short)10);
		
		style.setFont(font);
		
		style.setAlignment(CellStyle.ALIGN_LEFT);					//横向居左
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);		//纵向居中
		
		style.setBorderTop(CellStyle.BORDER_THIN);					//上细线
		style.setBorderBottom(CellStyle.BORDER_THIN);				//下细线
		style.setBorderLeft(CellStyle.BORDER_THIN);					//左细线
		style.setBorderRight(CellStyle.BORDER_THIN);				//右细线
		
		return style;
	}
	
	
	
	
	
	

}
