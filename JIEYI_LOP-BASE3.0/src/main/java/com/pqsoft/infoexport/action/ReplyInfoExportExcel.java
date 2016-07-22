package com.pqsoft.infoexport.action;
/**
 * 	 用于管理信息系统模板导出
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

import com.pqsoft.infoexport.service.InfoExportService;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.SkyEye;

public class ReplyInfoExportExcel extends Reply{
	
	private InfoExportService service = new InfoExportService();
	private List ProjectsInfo = new ArrayList();//项目信息
	Map param = new HashMap();
	
	public ReplyInfoExportExcel(Map param) {
		this.param = param;
	}
	
	public void selectDetail(Map param) {
		ProjectsInfo = service.selectProjectsInfo(param);
	}
	@Override
	public void exec() {
		SkyEye.getResponse().reset();
		//查询数据
		selectDetail(param);
			
    	try {
    		OutputStream os = SkyEye.getResponse().getOutputStream();
			SkyEye.getResponse().reset();// 清空输出流
			
 	        String filename="登记表上传导出数据.xls";
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
			String tmptitle = "登记表信息"; // sheet名
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
			int width = 6;//单位
			//设置列宽 共35列
			wsheet.setColumnView(0,   1*width);
			wsheet.setColumnView(1,   2*width);
			wsheet.setColumnView(2,   3*width);
			wsheet.setColumnView(3,   50);
			wsheet.setColumnView(4,   3*width);
			wsheet.setColumnView(5,   6*width);
			wsheet.setColumnView(6,   3*width);
			wsheet.setColumnView(7,   3*width);
			wsheet.setColumnView(8,   3*width);
			wsheet.setColumnView(9,   4*width);
			wsheet.setColumnView(10,   4*width);
			wsheet.setColumnView(11,   3*width);
			wsheet.setColumnView(12,   3*width);
			wsheet.setColumnView(13,   3*width);
			wsheet.setColumnView(14,   3*width);
			wsheet.setColumnView(15,   3*width);
			wsheet.setColumnView(16,   3*width);
			wsheet.setColumnView(17,   40);
			wsheet.setColumnView(18,   3*width);
			wsheet.setColumnView(19,   3*width);
			wsheet.setColumnView(20,   3*width);
			wsheet.setColumnView(21,   3*width);
			wsheet.setColumnView(22,   40);
			wsheet.setColumnView(23,   3*width);
			wsheet.setColumnView(24,   3*width);
			wsheet.setColumnView(25,   3*width);
			wsheet.setColumnView(26,   40);
			wsheet.setColumnView(27,   30);
			wsheet.setColumnView(28,   3*width);
			wsheet.setColumnView(29,   3*width);
			wsheet.setColumnView(30,   3*width);
			wsheet.setColumnView(31,   30);
			wsheet.setColumnView(32,   30);
			wsheet.setColumnView(33,   3*width);
			wsheet.setColumnView(34,   3*width);
			wsheet.setColumnView(35,   3*width);
			//wsheet.setRowView(line_no, 1000);// 设置行高1000
			//第1行
			int line_no = 0;
			int col_no = 0;
			//wsheet.mergeCells(0, line_no, 8, line_no);//合并单元格
			wsheet.addCell(new Label(col_no,line_no, "序号",normalCentre));//1
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "合同编号",normalCentre));//2
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "承(出)租人信息",normalCentre));//3
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "承租人名称",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "承租人类型",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "身份证号",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "法人代表",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "承租人所属行业",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "承租人企业规模",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "工商注册号",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "税务登记证号",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "租赁物基本信息",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "租赁物使用地",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "租金信息",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "融资额(万元)",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "租金总额(万元)",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "手续费(万元)",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "其他一次性支付费用(万元)",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "币种",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "担保及关联交易",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "有无担保",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "担保金额(万元)",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "担保人",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "承租人是否为关联方",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "其他信息",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "租赁方式",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "委托人/转租人/资金提供人/其他租赁人",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "合同生效日期",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "租赁期限(月)",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "补充说明:",normalCentre));
			col_no++;
			//以下是设备信息
			wsheet.addCell(new Label(col_no,line_no, "租赁物1名称",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "租赁物1类型（大类）",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "租赁物1类型（小类）",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "租赁物1价款(万元)",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "租赁物1数量（套）",normalCentre));
			col_no++;
			wsheet.addCell(new Label(col_no,line_no, "待补充",normalCentre));
			col_no++;
			
			line_no++;
			//第2行开始
			int length = 0 ;
			String[] equips = null;//租赁物名称
			String[] big_class = null;//大类
			String[] small_class = null;//小类
			String[] prices = null;//价格
			String[] amounts = null;//数量
			for (int i = 0; i < ProjectsInfo.size(); i++) {
				col_no = 0 ;//col_no置为0
				wsheet.addCell(new Label(col_no++,line_no, (i+1)+"",normalCentre));//序号
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"HTBH"),normalCentre));//合同编号
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"CZRXX"),normalCentre));//承(出)租人信息
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"KHMC"),normalCentre));//承租人名称
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"NSZZ"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"CARD_NO"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"FRDB"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"SSHY"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"QYGM"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"GSID"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"SWDJ"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"ZLWJBXX"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"ZLWYD"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"ZJXX"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"RZE"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"ZJZE"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"SXF"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"OTHERFEE"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"MONEY_TYPE"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"DBGL"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"ISSURE"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"DBJE"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"SURER"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"SFGLF"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"QTXX"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"ZLFS"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"COL1"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"SXRQ"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"ZLQX"),normalCentre));//
				wsheet.addCell(new Label(col_no++,line_no, getNotNullValue((Map)ProjectsInfo.get(i),"BCSM"),normalCentre));//
				//以下是设备的输出
				equips = getNotNullValue((Map)ProjectsInfo.get(i),"ZLWMCS").toString().split(",");//根据系统中的实际名称数个数
				length = equips.length;
				big_class = getNotNullValue((Map)ProjectsInfo.get(i),"ZLWDLS").toString().split(",");
				small_class = getNotNullValue((Map)ProjectsInfo.get(i),"ZLWXLS").toString().split(",");
				prices = getNotNullValue((Map)ProjectsInfo.get(i),"SBJES").toString().split(",");
				amounts = getNotNullValue((Map)ProjectsInfo.get(i),"AMOUNTS").toString().split(",");
				for (int j = 0; j < length; j++) {
					wsheet.addCell(new Label(col_no++,line_no, equips[j],normalCentre));
					wsheet.addCell(new Label(col_no++,line_no, big_class[j],normalCentre));
					wsheet.addCell(new Label(col_no++,line_no, small_class[j],normalCentre));
					wsheet.addCell(new Label(col_no++,line_no, prices[j],normalCentre));
					wsheet.addCell(new Label(col_no++,line_no, amounts[j],normalCentre));
				}
				line_no++;
			}
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
