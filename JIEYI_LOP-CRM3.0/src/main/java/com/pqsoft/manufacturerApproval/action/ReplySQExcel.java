package com.pqsoft.manufacturerApproval.action;
/**
 * 	 用于生成陕汽格式的业务审批单
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

public class ReplySQExcel extends Reply{
	private String project_id;//项目id
	private Map map = new HashMap();
	private ManufacturerApprovalService service = new ManufacturerApprovalService();
	private Map ProjInfo = new HashMap();//1.得到项目基本信息
	private Map NPcustInfo = new HashMap();//2.得到客户(自然人)信息
	private Map LPcustInfo = new HashMap();//2.得到客户(法人)信息
	private List ProductsInfo = new ArrayList();
	private Map ZCDetail = new HashMap();//8.整车 数量 总价
	private Map BGCDetail = new HashMap();//8.挂车 数量 总价
	private Map ZJLTime = new HashMap();
	private List XS_MEMO = new ArrayList();
	private List ZJL_MEMO = new ArrayList();
	private List CALL_MEMO = new ArrayList();
	
	public ReplySQExcel(String project_id) {
		this.map.put("PROJECT_ID", project_id);
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
		ZJLTime = (Map) service.selectZJLTimeByProjID(this.map);
		
		ProductsInfo = service.selectProductInfoByProjID(this.map);
		ZCDetail = (Map) service.selectZCCountByProjID(this.map);
		BGCDetail = (Map) service.selectBGCCountByProjID(this.map);
		XS_MEMO = service.selectMemoAndTimeByProjIDAndFlowName(this.map, "信审担当送审");
		ZJL_MEMO = service.selectMemoAndTimeByProjIDAndFlowName(this.map, "总经理审批");
		CALL_MEMO = service.selectMemoAndTimeByProjIDAndFlowName(this.map, "呼叫中心");
	}
	@Override
	public void exec() {
		SkyEye.getResponse().reset();
		//查询数据
		selectDetail();
		
    	try {
    		OutputStream os = SkyEye.getResponse().getOutputStream();
			SkyEye.getResponse().reset();// 清空输出流
			
 	        String filename="陕汽业务审批单.xls";
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
			String tmptitle = "陕汽业务审批单"; // sheet名
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
			wsheet.mergeCells(0, 0, 8, 0);//合并单元格
			/*
			 * 在Label()方法里面有四个参数 
			 * 第一个是代表列数, 
			 * 第二是代表行数， 
			 * 第三个代表要写入的内容
			 * 第四个是可选项，是输入这个label里面的样式
			 */
			//第1行
			wsheet.addCell(new Label(0,0, "山重融资租赁业务审批单",normalFormat));
			wsheet.setRowView(0, 1000);// 设置行高1000
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
			//第2行
			wsheet.mergeCells(0, 1, 8, 1);
			wsheet.addCell(new Label(0,1, "一、经销商系统提报信息",normalLeft));
			wsheet.setRowView(1, 500);// 设置行高500
			//第3行
			wsheet.mergeCells(1, 2, 4, 2);
			wsheet.addCell(new Label(0,2, "经销商",normalCentre));
			wsheet.addCell(new Label(1,2, getNotNullValue(ProjInfo,"DLD"),normalCentre));
			wsheet.addCell(new Label(5,2, "信审通过日期",normalCentre));
			wsheet.mergeCells(6, 2,8, 2);
			wsheet.addCell(new Label(6,2, getNotNullValue(ZJLTime,"LC_END_TIME"),normalCentre));
			//第4行
			wsheet.addCell(new Label(0,3, "厂方编号",normalCentre));
			wsheet.mergeCells(1, 3, 4, 3);
			wsheet.addCell(new Label(1,3, getNotNullValue(ProjInfo,"CS_ID"),normalCentre));
			wsheet.addCell(new Label(5,3, "项目编号",normalCentre));
			wsheet.mergeCells(6, 3,8, 3);
			wsheet.addCell(new Label(6,3, getNotNullValue(ProjInfo,"PROJ_ID"),normalCentre));
			//第5行
			wsheet.mergeCells(0, 4, 8, 4);
			wsheet.addCell(new Label(0,4, "二、租赁物明细",normalLeft));
			wsheet.setRowView(4, 500);// 设置行高500
			//第6行
			wsheet.mergeCells(0, 5, 0, 6);
			wsheet.addCell(new Label(0,5, "车型",normalCentre));
			wsheet.addCell(new Label(1,5, "整车",normalCentre));
			wsheet.addCell(new Label(2,5, getNotNullValue(ZCDetail,"ZC_TOTAL_PRICE"),normalCentre));
			wsheet.mergeCells(3, 5, 3, 6);
			wsheet.addCell(new Label(3,5, "数量(台)",normalCentre));
			wsheet.addCell(new Label(4,5, getNotNullValue(ZCDetail,"ZC_AMOUNT"),normalCentre));
			wsheet.mergeCells(5, 5, 5, 6);
			wsheet.addCell(new Label(5,5, "销售价总金额(元)项目金额",normalCentre));
			wsheet.mergeCells(6, 5, 6, 6);
			wsheet.addCell(new Label(6,5, getNotNullValue(ProjInfo,"TOTAL_AMT"),normalCentre));
			wsheet.mergeCells(7, 5, 7, 6);
			wsheet.addCell(new Label(7,5, "融资总金额(元)融资金额",normalCentre));
			wsheet.mergeCells(8, 5, 8, 6);
			wsheet.addCell(new Label(8,5, getNotNullValue(ProjInfo,"RZ_AMT"),normalCentre));
			wsheet.setRowView(5, 450);// 设置行高450
			//第7行
			wsheet.addCell(new Label(1,6, "挂车",normalCentre));
			wsheet.addCell(new Label(2,6, getNotNullValue(BGCDetail,"BGC_TOTAL_PRICE"),normalCentre));
			wsheet.addCell(new Label(4,6, getNotNullValue(BGCDetail,"BGC_AMOUNT"),normalCentre));
			wsheet.setRowView(6, 450);// 设置行高450
			//第8行
			wsheet.addCell(new Label(0,7, "起租比例",normalCentre));
			wsheet.addCell(new Label(1,7, getNotNullValue(ProjInfo,"HEAD_RATE") + "%",normalCentre));
			wsheet.addCell(new Label(2,7, "租赁期限",normalCentre));
			wsheet.mergeCells(3, 7, 4, 7);
			wsheet.addCell(new Label(3,7, getNotNullValue(ProjInfo,"LEASE_TERM") + getNotNullValue(ProjInfo,"UNIT"),normalCentre));
			wsheet.addCell(new Label(5,7, "经销商保证金比例",normalCentre));
			//wsheet.mergeCells(6, 7, 8, 7);
			//wsheet.addCell(new Label(6,7, getNotNullValue(ProjInfo,"DB_RATE") + "%",normalCentre));
			wsheet.addCell(new Label(6,7, getNotNullValue(ProjInfo,"DB_RATE") + "%",normalCentre));
			//新加客户保证金
			wsheet.addCell(new Label(7,7, "客户保证金",normalCentre));
			wsheet.addCell(new Label(8,7, getNotNullValue(ProjInfo,"KHBZJ") ,normalCentre));
			//第9行
			wsheet.mergeCells(0, 8, 1, 8);
			wsheet.addCell(new Label(0,8, "车辆抵押情况",normalCentre));
			wsheet.mergeCells(2, 8, 8, 8);
			wsheet.addCell(new Label(2,8, getNotNullValue(ProjInfo,"ON_CARD"),normalCentre));
			//第10行
			wsheet.mergeCells(0, 9, 1, 9);
			wsheet.addCell(new Label(0,9, "租赁物名称",normalCentre));
			wsheet.addCell(new Label(2,9, "型号",normalCentre));
			wsheet.mergeCells(3, 9, 4, 9);
			wsheet.addCell(new Label(3,9, "出厂编号",normalCentre));
			wsheet.mergeCells(5, 9, 6, 9);
			wsheet.addCell(new Label(5,9, "发动机号",normalCentre));
			wsheet.mergeCells(7, 9, 8, 9);
			wsheet.addCell(new Label(7,9, "车架号",normalCentre));
			//第11行
			int line_no = 10;
			Map m;
			for (int i = 0; i < ProductsInfo.size(); i++) {
				m = (Map)ProductsInfo.get(i);
				wsheet.mergeCells(0, line_no, 1, line_no);
				wsheet.addCell(new Label(0,line_no, getNotNullValue(m,"PRODUCT_NAME"),normalCentre));
				wsheet.addCell(new Label(2,line_no, getNotNullValue(m,"MODEL"),normalCentre));
				wsheet.mergeCells(3, line_no, 4, line_no);
				wsheet.addCell(new Label(3,line_no, getNotNullValue(m,"CC_CODE"),normalCentre));
				wsheet.mergeCells(5, line_no, 6, line_no);
				wsheet.addCell(new Label(5,line_no, getNotNullValue(m,"ENG_NO"),normalCentre));
				wsheet.mergeCells(7, line_no, 8, line_no);
				wsheet.addCell(new Label(7,line_no, getNotNullValue(m,"BODY_NO"),normalCentre));
				//此处遍历后,行号记录line_no
				line_no++;
			}
			//放款方式 行
			wsheet.mergeCells(0, line_no, 1, line_no);
			wsheet.addCell(new Label(0,line_no, "放款方式",normalCentre));
			wsheet.mergeCells(2, line_no, 8, line_no);
			wsheet.addCell(new Label(2,line_no, getNotNullValue(ProjInfo,"LOAN_WAY"),normalCentre));
			line_no++;
			//租赁包 行
			wsheet.mergeCells(0, line_no, 1, line_no);
			wsheet.addCell(new Label(0,line_no, "租赁包",normalCentre));
			wsheet.mergeCells(2, line_no, 8, line_no);
			wsheet.addCell(new Label(2,line_no, getNotNullValue(ProjInfo,"LEASINGBAG"),normalCentre));
			line_no++;
			//风险控制措施 行
			wsheet.mergeCells(0, line_no, 1, line_no);
			wsheet.addCell(new Label(0,line_no, "风险控制措施",normalCentre));
			wsheet.mergeCells(2, line_no, 8, line_no);
			wsheet.addCell(new Label(2,line_no, getNotNullValue(ProjInfo,"MONITOR"),normalCentre));
			line_no++;
			//经销商说明 行
			wsheet.mergeCells(0, line_no, 1, line_no);
			wsheet.addCell(new Label(0,line_no, "经销商说明",normalCentre));
			wsheet.mergeCells(2, line_no, 8, line_no);
			wsheet.addCell(new Label(2,line_no, "待定",normalCentre_red));
			line_no++;
			//加入终端客户考虑
			if("".equals(getNotNullValue(ProjInfo,"TERMINAL_KH_TYPE"))){
				if("NP".equals(getNotNullValue(ProjInfo,"KH_TYPE"))){
					//////////////////////////
					//三、承租人基本情况(自然人) 行
					wsheet.mergeCells(0, line_no, 8, line_no);
					wsheet.addCell(new Label(0,line_no, "三、承租人基本情况(自然人)",normalLeft));
					wsheet.setRowView(line_no, 500);// 设置行高500
					line_no++;
					//承租人姓名 行
					wsheet.addCell(new Label(0,line_no, "承租人姓名",normalCentre));
					wsheet.mergeCells(1, line_no, 2, line_no);
					wsheet.addCell(new Label(1,line_no, getNotNullValue(NPcustInfo,"KH_NAME"),normalCentre));
					wsheet.addCell(new Label(3,line_no, "身份证号",normalCentre));
					wsheet.mergeCells(4, line_no, 5, line_no);
					wsheet.addCell(new Label(4,line_no, getNotNullValue(NPcustInfo,"KH_CARD_NO"),normalCentre));
					wsheet.addCell(new Label(6,line_no, "手机",normalCentre));
					wsheet.mergeCells(7, line_no, 8, line_no);
					wsheet.addCell(new Label(7,line_no, getNotNullValue(NPcustInfo,"KH_PHONE"),normalCentre));
					line_no++;
					//配偶姓名 行
					wsheet.addCell(new Label(0,line_no, "配偶姓名",normalCentre));
					wsheet.mergeCells(1, line_no, 2, line_no);
					wsheet.addCell(new Label(1,line_no, getNotNullValue(NPcustInfo,"PO_NAME"),normalCentre));
					wsheet.addCell(new Label(3,line_no, "身份证号",normalCentre));
					wsheet.mergeCells(4, line_no, 5, line_no);
					wsheet.addCell(new Label(4,line_no, getNotNullValue(NPcustInfo,"PO_CARD_NO"),normalCentre));
					wsheet.addCell(new Label(6,line_no, "手机",normalCentre));
					wsheet.mergeCells(7, line_no, 8, line_no);
					wsheet.addCell(new Label(7,line_no, getNotNullValue(NPcustInfo,"PO_PHONE"),normalCentre));
					line_no++;
				}else{
					//法人
					//////////////////////////
					//三、承租人基本情况(法人) 行
					wsheet.mergeCells(0, line_no, 8, line_no);
					wsheet.addCell(new Label(0,line_no, "三、承租人基本情况(法人)",normalLeft));
					wsheet.setRowView(line_no, 500);// 设置行高500
					line_no++;
					//客户名称 行
					wsheet.addCell(new Label(0,line_no, "客户名称",normalCentre));
					wsheet.mergeCells(1, line_no, 3, line_no);
					wsheet.addCell(new Label(1,line_no, getNotNullValue(LPcustInfo,"FR_NAME"),normalCentre));
					wsheet.addCell(new Label(4,line_no, "地址",normalCentre));
					wsheet.mergeCells(5, line_no, 8, line_no);
					wsheet.addCell(new Label(5,line_no, getNotNullValue(LPcustInfo,"ZC_ADDR"),normalCentre));
					line_no++;
					//法定代表人 行
					wsheet.addCell(new Label(0,line_no, "法定代表人",normalCentre));
					wsheet.addCell(new Label(1,line_no, getNotNullValue(LPcustInfo,"FD_PRER"),normalCentre));
					wsheet.addCell(new Label(2,line_no, "注册资本",normalCentre));
					wsheet.addCell(new Label(3,line_no, getNotNullValue(LPcustInfo,"ZC_MONEY"),normalCentre));
					wsheet.addCell(new Label(4,line_no, "客户类型",normalCentre));
					wsheet.addCell(new Label(5,line_no, getNotNullValue(LPcustInfo,"CUST_NATURE"),normalCentre));
					wsheet.addCell(new Label(6,line_no, "组织机构代码",normalCentre));
					wsheet.mergeCells(7, line_no, 8, line_no);
					wsheet.addCell(new Label(7,line_no, getNotNullValue(LPcustInfo,"ORAGNIZATION_CODE"),normalCentre));
					line_no++;
					//主营业务 行
					wsheet.addCell(new Label(0,line_no, "主营业务",normalCentre));
					wsheet.mergeCells(1, line_no, 3, line_no);
					wsheet.addCell(new Label(1,line_no, getNotNullValue(LPcustInfo,"MAIN_BUSINESS"),normalCentre));
					wsheet.addCell(new Label(4,line_no, "注册日期",normalCentre));
					wsheet.addCell(new Label(5,line_no, getNotNullValue(LPcustInfo,"REGISTE_DATE"),normalCentre));
					wsheet.addCell(new Label(6,line_no, "电话",normalCentre));
					wsheet.mergeCells(7, line_no, 8, line_no);
					wsheet.addCell(new Label(7,line_no, getNotNullValue(LPcustInfo,"REGISTE_PHONE"),normalCentre));
					line_no++;
					//资产总额 行
					wsheet.addCell(new Label(0,line_no, "资产总额",normalCentre));
					wsheet.addCell(new Label(1,line_no, "需手工核算",normalCentre_red));
					wsheet.addCell(new Label(2,line_no, "负债总额",normalCentre));
					wsheet.addCell(new Label(3,line_no, "需手工核算",normalCentre_red));
					wsheet.addCell(new Label(4,line_no, "净资产",normalCentre));
					wsheet.addCell(new Label(5,line_no, "需手工核算",normalCentre_red));
					wsheet.addCell(new Label(6,line_no, "流动资产",normalCentre));
					wsheet.mergeCells(7, line_no, 8, line_no);
					wsheet.addCell(new Label(7,line_no, "需手工核算",normalCentre_red));
					line_no++;
					//固定资产 行
					wsheet.addCell(new Label(0,line_no, "固定资产",normalCentre));
					wsheet.addCell(new Label(1,line_no, "需手工核算",normalCentre_red));
					wsheet.addCell(new Label(2,line_no, "主营业务收入",normalCentre));
					wsheet.addCell(new Label(3,line_no, "需手工核算",normalCentre_red));
					wsheet.addCell(new Label(4,line_no, "净利润",normalCentre));
					wsheet.addCell(new Label(5,line_no, "需手工核算",normalCentre_red));
					wsheet.addCell(new Label(6,line_no, "上年净利润",normalCentre));
					wsheet.mergeCells(7, line_no, 8, line_no);
					wsheet.addCell(new Label(7,line_no, "需手工核算",normalCentre_red));
					line_no++;
				}
			}else{
				if("NP".equals(getNotNullValue(ProjInfo,"TERMINAL_KH_TYPE"))){
					//////////////////////////
					//三、承租人基本情况(自然人) 行
					wsheet.mergeCells(0, line_no, 8, line_no);
					wsheet.addCell(new Label(0,line_no, "三、承租人基本情况(自然人)",normalLeft));
					wsheet.setRowView(line_no, 500);// 设置行高500
					line_no++;
					//承租人姓名 行
					wsheet.addCell(new Label(0,line_no, "承租人姓名",normalCentre));
					wsheet.mergeCells(1, line_no, 2, line_no);
					wsheet.addCell(new Label(1,line_no, getNotNullValue(NPcustInfo,"KH_NAME"),normalCentre));
					wsheet.addCell(new Label(3,line_no, "身份证号",normalCentre));
					wsheet.mergeCells(4, line_no, 5, line_no);
					wsheet.addCell(new Label(4,line_no, getNotNullValue(NPcustInfo,"KH_CARD_NO"),normalCentre));
					wsheet.addCell(new Label(6,line_no, "手机",normalCentre));
					wsheet.mergeCells(7, line_no, 8, line_no);
					wsheet.addCell(new Label(7,line_no, getNotNullValue(NPcustInfo,"KH_PHONE"),normalCentre));
					line_no++;
					//配偶姓名 行
					wsheet.addCell(new Label(0,line_no, "配偶姓名",normalCentre));
					wsheet.mergeCells(1, line_no, 2, line_no);
					wsheet.addCell(new Label(1,line_no, getNotNullValue(NPcustInfo,"PO_NAME"),normalCentre));
					wsheet.addCell(new Label(3,line_no, "身份证号",normalCentre));
					wsheet.mergeCells(4, line_no, 5, line_no);
					wsheet.addCell(new Label(4,line_no, getNotNullValue(NPcustInfo,"PO_CARD_NO"),normalCentre));
					wsheet.addCell(new Label(6,line_no, "手机",normalCentre));
					wsheet.mergeCells(7, line_no, 8, line_no);
					wsheet.addCell(new Label(7,line_no, getNotNullValue(NPcustInfo,"PO_PHONE"),normalCentre));
					line_no++;
				}else{
					//法人
					//////////////////////////
					//三、承租人基本情况(法人) 行
					wsheet.mergeCells(0, line_no, 8, line_no);
					wsheet.addCell(new Label(0,line_no, "三、承租人基本情况(法人)",normalLeft));
					wsheet.setRowView(line_no, 500);// 设置行高500
					line_no++;
					//客户名称 行
					wsheet.addCell(new Label(0,line_no, "客户名称",normalCentre));
					wsheet.mergeCells(1, line_no, 3, line_no);
					wsheet.addCell(new Label(1,line_no, getNotNullValue(LPcustInfo,"FR_NAME"),normalCentre));
					wsheet.addCell(new Label(4,line_no, "地址",normalCentre));
					wsheet.mergeCells(5, line_no, 8, line_no);
					wsheet.addCell(new Label(5,line_no, getNotNullValue(LPcustInfo,"ZC_ADDR"),normalCentre));
					line_no++;
					//法定代表人 行
					wsheet.addCell(new Label(0,line_no, "法定代表人",normalCentre));
					wsheet.addCell(new Label(1,line_no, getNotNullValue(LPcustInfo,"FD_PRER"),normalCentre));
					wsheet.addCell(new Label(2,line_no, "注册资本",normalCentre));
					wsheet.addCell(new Label(3,line_no, getNotNullValue(LPcustInfo,"ZC_MONEY"),normalCentre));
					wsheet.addCell(new Label(4,line_no, "客户类型",normalCentre));
					wsheet.addCell(new Label(5,line_no, getNotNullValue(LPcustInfo,"CUST_NATURE"),normalCentre));
					wsheet.addCell(new Label(6,line_no, "组织机构代码",normalCentre));
					wsheet.mergeCells(7, line_no, 8, line_no);
					wsheet.addCell(new Label(7,line_no, getNotNullValue(LPcustInfo,"ORAGNIZATION_CODE"),normalCentre));
					line_no++;
					//主营业务 行
					wsheet.addCell(new Label(0,line_no, "主营业务",normalCentre));
					wsheet.mergeCells(1, line_no, 3, line_no);
					wsheet.addCell(new Label(1,line_no, getNotNullValue(LPcustInfo,"MAIN_BUSINESS"),normalCentre));
					wsheet.addCell(new Label(4,line_no, "注册日期",normalCentre));
					wsheet.addCell(new Label(5,line_no, getNotNullValue(LPcustInfo,"REGISTE_DATE"),normalCentre));
					wsheet.addCell(new Label(6,line_no, "电话",normalCentre));
					wsheet.mergeCells(7, line_no, 8, line_no);
					wsheet.addCell(new Label(7,line_no, getNotNullValue(LPcustInfo,"REGISTE_PHONE"),normalCentre));
					line_no++;
					//资产总额 行
					wsheet.addCell(new Label(0,line_no, "资产总额",normalCentre));
					wsheet.addCell(new Label(1,line_no, "需手工核算",normalCentre_red));
					wsheet.addCell(new Label(2,line_no, "负债总额",normalCentre));
					wsheet.addCell(new Label(3,line_no, "需手工核算",normalCentre_red));
					wsheet.addCell(new Label(4,line_no, "净资产",normalCentre));
					wsheet.addCell(new Label(5,line_no, "需手工核算",normalCentre_red));
					wsheet.addCell(new Label(6,line_no, "流动资产",normalCentre));
					wsheet.mergeCells(7, line_no, 8, line_no);
					wsheet.addCell(new Label(7,line_no, "需手工核算",normalCentre_red));
					line_no++;
					//固定资产 行
					wsheet.addCell(new Label(0,line_no, "固定资产",normalCentre));
					wsheet.addCell(new Label(1,line_no, "需手工核算",normalCentre_red));
					wsheet.addCell(new Label(2,line_no, "主营业务收入",normalCentre));
					wsheet.addCell(new Label(3,line_no, "需手工核算",normalCentre_red));
					wsheet.addCell(new Label(4,line_no, "净利润",normalCentre));
					wsheet.addCell(new Label(5,line_no, "需手工核算",normalCentre_red));
					wsheet.addCell(new Label(6,line_no, "上年净利润",normalCentre));
					wsheet.mergeCells(7, line_no, 8, line_no);
					wsheet.addCell(new Label(7,line_no, "需手工核算",normalCentre_red));
					line_no++;
				}
			}
			/////////////////////////////////////////////////////
			//四、山重信审意见 行
			wsheet.mergeCells(0, line_no, 8, line_no);
			wsheet.addCell(new Label(0,line_no, "四、山重信审意见：",normalLeft));
			wsheet.setRowView(line_no, 500);// 设置行高500
			line_no++;
			//遍历生成意见清单
			for (int i = 0; i < XS_MEMO.size(); i++) {
				m = (Map)XS_MEMO.get(i);
				if (i == 0) {
					wsheet.mergeCells(0, line_no, 8, line_no);
					wsheet.addCell(new Label(0,line_no, "信审担当送审意见：",normalLeft1));
					line_no++;
				}
				wsheet.mergeCells(0, line_no, 8, line_no);
				wsheet.addCell(new Label(0,line_no, (i+1) + "--时间：" + m.get("LC_END_TIME").toString() + "--意见：" + m.get("MEMO").toString(),normalLeft1));
				line_no++;
			}
			for (int i = 0; i < ZJL_MEMO.size(); i++) {
				if (i == 0) {
					wsheet.mergeCells(0, line_no, 8, line_no);
					wsheet.addCell(new Label(0,line_no, "总经理审批意见：",normalLeft1));
					line_no++;
				}
				m = (Map)ZJL_MEMO.get(i);
				wsheet.mergeCells(0, line_no, 8, line_no);
				wsheet.addCell(new Label(0,line_no, (i+1) + "--时间：" + m.get("LC_END_TIME").toString() + "--意见：" + m.get("MEMO").toString(),normalLeft1));
				line_no++;
			}
			//五、呼叫中心验证 行
			wsheet.mergeCells(0, line_no, 8, line_no);
			wsheet.addCell(new Label(0,line_no, "五、呼叫中心验证：",normalLeft));
			wsheet.setRowView(line_no, 500);// 设置行高500
			line_no++;
			//遍历生成意见清单
			for (int i = 0; i < CALL_MEMO.size(); i++) {
				m = (Map)CALL_MEMO.get(i);
				wsheet.mergeCells(0, line_no, 8, line_no);
				wsheet.addCell(new Label(0,line_no, (i+1) + "--时间：" + m.get("LC_END_TIME").toString() + "--意见：" + m.get("MEMO").toString(),normalLeft1));
				line_no++;
			}
			//六、备注 行
			wsheet.mergeCells(0, line_no, 8, line_no);
			wsheet.addCell(new Label(0,line_no, "六、备注",normalLeft));
			wsheet.setRowView(line_no, 500);// 设置行高500
			line_no++;
			wsheet.mergeCells(0, line_no, 8, line_no);
			wsheet.addCell(new Label(0,line_no, "",normalLeft));
			wsheet.setRowView(line_no, 700);// 设置行高500
			line_no++;
			//说明 行
			wsheet.mergeCells(0, line_no, 8, line_no);
			wsheet.addCell(new Label(0,line_no, "说明：",normalLeft2));
			line_no++;
			//特殊说明1 行
			wsheet.mergeCells(0, line_no, 8, line_no);
			wsheet.addCell(new Label(0,line_no, "1、有特殊业务需说明的填入备注栏；",normalLeft2));
			line_no++;
			//特殊说明2 行
			wsheet.mergeCells(0, line_no, 8, line_no);
			wsheet.addCell(new Label(0,line_no, "2、信审通过后，以上单子内容有变更的需反馈陕汽；",normalLeft2));
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
