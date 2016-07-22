package com.pqsoft.manufacturerApproval.action;
/**
 * 	 用于生成陕汽格式的业务确认函
 *  @author 韩晓龙
 */
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.pqsoft.manufacturerApproval.service.ManufacturerApprovalService;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.SkyEye;

public class ReplySQConfirmExcel extends Reply{
	private String project_id;//项目id
	private Map map = new HashMap();
	private ManufacturerApprovalService service = new ManufacturerApprovalService();
	private Map ProjInfo = new HashMap();//1.得到项目基本信息
	private Map NPcustInfo = new HashMap();//2.得到客户(自然人)信息
	private Map LPcustInfo = new HashMap();//2.得到客户(法人)信息
	private List ProductsInfo = new ArrayList();
	private Map ZCDetail = new HashMap();//8.整车 数量 总价
	private Map BGCDetail = new HashMap();//8.挂车 数量 总价
	
	private String CAR_SALE_DATE;//车辆已售日期
	private String ADVICE;//厂家审批意见
	
	public ReplySQConfirmExcel(String project_id,String car_sale_date,String advice) {
		this.map.put("PROJECT_ID", project_id);
		this.CAR_SALE_DATE = car_sale_date;
		this.ADVICE = advice;
	}
	
	public void selectDetail() {
		ProjInfo = (Map) service.selectProjInfo(this.map);
		//加入终端客户的考虑
		if("".equals(getNotNullValue(ProjInfo,"TERMINAL_KH_TYPE"))){
			if("NP".equals(getNotNullValue(ProjInfo,"KH_TYPE"))){
				NPcustInfo = (Map) service.selectNPCustInfo(this.map);
			}else{
				LPcustInfo = (Map) service.selectLPCustInfo(this.map);
			}
		}else{
			if("NP".equals(getNotNullValue(ProjInfo,"TERMINAL_KH_TYPE"))){
				NPcustInfo = (Map) service.selectTerminalNPCustInfo(this.map);
			}else{
				LPcustInfo = (Map) service.selectTerminalLPCustInfo(this.map);
			}
		}
		ProductsInfo = service.selectProductInfoByProjID(this.map);
		ZCDetail = (Map) service.selectZCCountByProjID(this.map);
		BGCDetail = (Map) service.selectBGCCountByProjID(this.map);
	}
	@Override
	public void exec() {
		SkyEye.getResponse().reset();
		//查询数据
		selectDetail();
		
    	try {
    		OutputStream os = SkyEye.getResponse().getOutputStream();
			SkyEye.getResponse().reset();// 清空输出流
			
 	        String filename="陕汽业务确认函.xls";
 	        filename = new String(filename.getBytes("UTF-8"),"ISO8859-1");
 	        SkyEye.getResponse().setHeader("Content-disposition", "attachment; filename="+filename);// 设定输出文件头  
 	        SkyEye.getResponse().setContentType("application/msexcel");// 定义输出类型
 	        
 	        WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件  
 	        //////////////////////////////////////////////////////////////////////////////
 	        int charNormal = 18;
 		    WritableFont oneFont = new WritableFont(WritableFont.createFont("宋体"), charNormal);
 		    WritableFont twoFont = new WritableFont(WritableFont.createFont("宋体"), 12);
 		    oneFont.setBoldStyle(WritableFont.BOLD);
 		    twoFont.setBoldStyle(WritableFont.BOLD);
 		    //normalFormat格式 水平垂直居中
 		  	WritableCellFormat normalFormat = new WritableCellFormat(oneFont);
 		  	normalFormat.setAlignment(Alignment.CENTRE);
 		  	normalFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
 		  	normalFormat.setWrap(true); // 是否换行
 		  	normalFormat.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); 
 		  	//normalFormat3格式 水平垂直居中
 			//数据格式
	  		WritableCellFormat normalFormat3 = new WritableCellFormat();
	  		normalFormat3.setAlignment(Alignment.CENTRE);
	  		normalFormat3.setVerticalAlignment(VerticalAlignment.CENTRE);
	  		normalFormat3.setWrap(true); // 是否换行
	  		normalFormat3.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); 
	  		//字体
	  		WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.BLACK);  
	  		//居左 含边框 字体加粗
	  		WritableCellFormat normalLeft = new WritableCellFormat(twoFont);
	  		normalLeft.setAlignment(Alignment.LEFT);
	  		normalLeft.setVerticalAlignment(VerticalAlignment.CENTRE);
	  		normalLeft.setWrap(true); // 是否换行
	  		normalLeft.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); 
	  		//居左 含边框
	  		WritableCellFormat normalLeft1 = new WritableCellFormat();
	  		normalLeft1.setAlignment(Alignment.LEFT);
	  		normalLeft1.setVerticalAlignment(VerticalAlignment.CENTRE);
	  		normalLeft1.setWrap(true); // 是否换行
	  		normalLeft1.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); 
	  		//居左 不含边框
	  		WritableCellFormat normalLeft2 = new WritableCellFormat();
	  		normalLeft2.setAlignment(Alignment.LEFT);
	  		normalLeft2.setVerticalAlignment(VerticalAlignment.CENTRE);
	  		normalLeft2.setWrap(true); // 是否换行
	  		//居中
	  		WritableCellFormat normalCentre = new WritableCellFormat();
	  		normalCentre.setAlignment(Alignment.CENTRE);
	  		normalCentre.setVerticalAlignment(VerticalAlignment.CENTRE);
	  		normalCentre.setWrap(true); // 是否换行
	  		normalCentre.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN); 
	  		//居中 红色
	  		WritableFont wfont_red = new WritableFont(WritableFont.ARIAL, 10,WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE,Colour.RED);  
	  		WritableCellFormat normalCentre_red = new WritableCellFormat(wfont_red);
	  		normalCentre_red.setAlignment(Alignment.CENTRE);
	  		normalCentre_red.setVerticalAlignment(VerticalAlignment.CENTRE);
	  		normalCentre_red.setWrap(true); // 是否换行
	  		normalCentre_red.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
	  		//////////////////////////////////////////////////////////////////////////////
			String tmptitle = "陕汽业务确认函"; // sheet名
			WritableSheet wsheet = wbook.createSheet(tmptitle, 0); // sheet名称

			WritableCellFormat wcfFC = new WritableCellFormat(wfont);//?
			wcfFC.setBackground(Colour.AQUA);//?
			/*
			 * mergeCells(a,b,c,d) 单元格合并函数
			 * a 单元格的列号
			 * b 单元格的行号
			 * c 从单元格[a,b]起，向右合并到的列数
			 * d从单元格[a,b]起，向下合并到的行数
			 */
			/*
			 * 在Label()方法里面有四个参数 
			 * 第一个是代表列数, 
			 * 第二是代表行数， 
			 * 第三个代表要写入的内容
			 * 第四个是可选项，是输入这个label里面的样式
			 */
			//第1行
			int line_no = 0;
			wsheet.mergeCells(0, line_no, 8, line_no);//合并单元格
			wsheet.addCell(new Label(0,line_no, "业务确认函",normalFormat));
			wsheet.setRowView(line_no, 1000);// 设置行高1000
			int width = 5;//单位
			//设置列宽 共9列
			wsheet.setColumnView(0,   2*width);
			wsheet.setColumnView(1,   2*width);
			wsheet.setColumnView(2,   2*width);
			wsheet.setColumnView(3,   1*width);
			wsheet.setColumnView(4,   1*width);
			wsheet.setColumnView(5,   3*width);
			wsheet.setColumnView(6,   2*width);
			wsheet.setColumnView(7,   2*width);
			wsheet.setColumnView(8,   2*width);
			line_no++;
			//第2行
			wsheet.mergeCells(1, line_no, 4, line_no);
			wsheet.addCell(new Label(0,line_no, "经销商",normalCentre));
			wsheet.addCell(new Label(1,line_no, getNotNullValue(ProjInfo,"DLD"),normalCentre));
			wsheet.addCell(new Label(5,line_no, "承租人",normalCentre));
			wsheet.mergeCells(6, line_no,8, line_no);
			if("".equals(getNotNullValue(ProjInfo,"TERMINAL_KH_TYPE"))){
				if("NP".equals(getNotNullValue(ProjInfo,"KH_TYPE"))){
					wsheet.addCell(new Label(6,line_no, getNotNullValue(NPcustInfo,"KH_NAME"),normalCentre));
				}else{
					//法人
					wsheet.addCell(new Label(6,line_no, getNotNullValue(LPcustInfo,"FR_NAME"),normalCentre));
				}
			}else{
				if("NP".equals(getNotNullValue(ProjInfo,"TERMINAL_KH_TYPE"))){
					wsheet.addCell(new Label(6,line_no, getNotNullValue(NPcustInfo,"KH_NAME"),normalCentre));
				}else{
					//法人
					wsheet.addCell(new Label(6,line_no, getNotNullValue(LPcustInfo,"FR_NAME"),normalCentre));
				}
			}
			/////////////////////
			line_no++;
			//第3行
			wsheet.addCell(new Label(0,line_no, "厂方编号",normalCentre));
			wsheet.mergeCells(1, line_no, 4, line_no);
			wsheet.addCell(new Label(1,line_no, getNotNullValue(ProjInfo,"CS_ID"),normalCentre));
			wsheet.addCell(new Label(5,line_no, "项目编号",normalCentre));
			wsheet.mergeCells(6, line_no,8, line_no);
			wsheet.addCell(new Label(6,line_no, getNotNullValue(ProjInfo,"PROJ_ID"),normalCentre));
			line_no++;
			//第4行
			wsheet.mergeCells(0, line_no, 0, line_no+1);
			wsheet.addCell(new Label(0,line_no, "车型",normalCentre));
			wsheet.addCell(new Label(1,line_no, "整车",normalCentre));
			wsheet.addCell(new Label(2,line_no, getNotNullValue(ZCDetail,"ZC_TOTAL_PRICE"),normalCentre));
			wsheet.mergeCells(3, line_no, 3, line_no+1);
			wsheet.addCell(new Label(3,line_no, "数量(台)",normalCentre));
			wsheet.addCell(new Label(4,line_no, getNotNullValue(ZCDetail,"ZC_AMOUNT"),normalCentre));
			wsheet.mergeCells(5, line_no, 5, line_no+1);
			wsheet.addCell(new Label(5,line_no, "销售价总金额(元)项目金额",normalCentre));
			wsheet.mergeCells(6, line_no, 6, line_no+1);
			wsheet.addCell(new Label(6,line_no, getNotNullValue(ProjInfo,"TOTAL_AMT"),normalCentre));
			wsheet.mergeCells(7, line_no, 7, line_no+1);
			wsheet.addCell(new Label(7,line_no, "融资总金额(元)融资金额",normalCentre));
			wsheet.mergeCells(8, line_no, 8, line_no+1);
			wsheet.addCell(new Label(8,line_no, getNotNullValue(ProjInfo,"RZ_AMT"),normalCentre));
			wsheet.setRowView(line_no, 450);// 设置行高450
			line_no++;
			//第5行
			wsheet.addCell(new Label(1,line_no, "挂车",normalCentre));
			wsheet.addCell(new Label(2,line_no, getNotNullValue(BGCDetail,"BGC_TOTAL_PRICE"),normalCentre));
			wsheet.addCell(new Label(4,line_no, getNotNullValue(BGCDetail,"BGC_AMOUNT"),normalCentre));
			wsheet.setRowView(line_no, 450);// 设置行高450
			line_no++;
			//第6行
			wsheet.addCell(new Label(0,line_no, "起租比例",normalCentre));
			wsheet.addCell(new Label(1,line_no, getNotNullValue(ProjInfo,"HEAD_RATE") + "%",normalCentre));
			wsheet.addCell(new Label(2,line_no, "租赁期限",normalCentre));
			wsheet.mergeCells(3, line_no, 4, line_no);
			wsheet.addCell(new Label(3,line_no, getNotNullValue(ProjInfo,"LEASE_TERM") + getNotNullValue(ProjInfo,"UNIT"),normalCentre));
			wsheet.addCell(new Label(5,line_no, "经销商保证金比例",normalCentre));
			//wsheet.mergeCells(6, line_no, 8, line_no);
			//wsheet.addCell(new Label(6,line_no, getNotNullValue(ProjInfo,"DB_RATE") + "%",normalCentre));
			wsheet.addCell(new Label(6,line_no, getNotNullValue(ProjInfo,"DB_RATE") + "%",normalCentre));
			//新加客户保证金
			wsheet.addCell(new Label(7,line_no, "客户保证金",normalCentre));
			wsheet.addCell(new Label(8,line_no, getNotNullValue(ProjInfo,"KHBZJ") ,normalCentre));
			line_no++;
			//第7行
			wsheet.mergeCells(0, line_no, 1, line_no);
			wsheet.addCell(new Label(0,line_no, "车辆抵押情况",normalCentre));
			wsheet.mergeCells(2, line_no, 8, line_no);
			wsheet.addCell(new Label(2,line_no, getNotNullValue(ProjInfo,"ON_CARD"),normalCentre));
			line_no++;
			//第8行
			wsheet.mergeCells(0, line_no, 1, line_no);
			wsheet.addCell(new Label(0,line_no, "放款金额",normalCentre));
			wsheet.mergeCells(2, line_no, 4, line_no);
			wsheet.addCell(new Label(2,line_no, getNotNullValue(ProjInfo,"FK_AMT"),normalCentre));
			wsheet.addCell(new Label(5,line_no, "放款方式",normalCentre));
			wsheet.mergeCells(6, line_no, 8, line_no);
			wsheet.addCell(new Label(6,line_no, getNotNullValue(ProjInfo,"LOAN_WAY"),normalCentre));
			line_no++;
			//第9行
			if(ProductsInfo.size()>0){
				wsheet.mergeCells(0, line_no,  0, line_no + ProductsInfo.size() - 1);//合并个数
				wsheet.addCell(new Label(0,line_no, "确认租赁物明细信息",normalCentre));
				Map m;
				for (int i = 0; i < ProductsInfo.size(); i++) {
					m = (Map)ProductsInfo.get(i);
					wsheet.mergeCells(1, line_no, 8, line_no);
					wsheet.addCell(new Label(1,line_no, getNotNullValue(m,"BODY_NO"),normalCentre));
					line_no++;
				}
			}else {
				wsheet.addCell(new Label(0,line_no, "确认租赁物明细信息",normalCentre));
				wsheet.mergeCells(1, line_no, 8, line_no);
				wsheet.addCell(new Label(1,line_no, "暂无数据",normalCentre));
				line_no++;
			}
			//确认放款账户 行
			wsheet.mergeCells(0, line_no, 1, line_no);
			wsheet.addCell(new Label(0,line_no, "确认放款账户",normalCentre));
			wsheet.mergeCells(2, line_no, 8, line_no);
			wsheet.addCell(new Label(2,line_no, getNotNullValue(ProjInfo,"FK_ACCOUNT") + " " + getNotNullValue(ProjInfo,"FK_NO"),normalCentre));
			line_no++;
			//车辆已售日期 行
			wsheet.mergeCells(0, line_no, 1, line_no);
			wsheet.addCell(new Label(0,line_no, "车辆已售日期",normalCentre));
			wsheet.mergeCells(2, line_no, 8, line_no);
			wsheet.addCell(new Label(2,line_no, CAR_SALE_DATE,normalCentre));
			line_no++;
			//厂家审批意见 行
			wsheet.mergeCells(0, line_no, 1, line_no);
			wsheet.addCell(new Label(0,line_no, "厂家审批意见",normalCentre));
			wsheet.mergeCells(2, line_no, 8, line_no);
			wsheet.addCell(new Label(2,line_no, ADVICE,normalCentre));
			line_no++;
			
 	        wbook.write(); // 写入文件  
 	    	wbook.close();  
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				SkyEye.getResponse().getOutputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 *  解决无值 返回 空字符串的问题
	 */
	private String getNotNullValue(Map map,String key){
		 return map.containsKey(key) ? map.get(key).toString() : "";
	}
}
