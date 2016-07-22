package com.pqsoft.project.service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * <p>
 * Title: 融资租赁信息系统平台 项目管理 导出PDF
 * </p>
 * <p>
 * Description: 
 * </p>
 * <p>
 * Company: 平强软件
 * </p>
 * 
 * @author 吴剑东 wujd@pqsoft.cn
 * @version 1.0
 */
public class ProjectManagerPdf {

	
	private final  Logger logger = Logger.getLogger(this.getClass());
	public void fileManagerPdfJoin1(HttpServletResponse response,Map<String,Object> map)
	{
		ByteArrayOutputStream baos =  new ByteArrayOutputStream();
		// PDF名字的定义
		String pdfName = "山重融资租赁业务审批单" ;
		try{
			//设置数据
			Map content = new HashMap();
			//取出数据
			//调用模型
			contentPdf1(map,baos) ;
			//
	   	    String strFileName = pdfName+".pdf";
	   	    response.setContentType("application/pdf");
	   	    response.setCharacterEncoding("UTF-8");
	   	    response.setHeader("Pragma", "public");
	   	    response.setHeader("Cache-Control",
	   		    "must-revalidate, post-check=0, pre-check=0");
	   	    response.setDateHeader("Expires", 0);
	   	    response.setHeader("Content-Disposition",
	   		    "attachment; filename=" + new String( strFileName.getBytes("gb2312"), "ISO8859-1" ));
	   	    ServletOutputStream o = response.getOutputStream();
	   	    baos.writeTo(o);
	   	    o.flush();
	   		o.close() ;
			}catch(Exception e){
				logger.error(e);
			}
	}
	public void confirmationPdfJoin(HttpServletResponse response,Map<String,Object> map)
	{
		ByteArrayOutputStream baos =  new ByteArrayOutputStream();
		// PDF名字的定义
		String pdfName = "业务确认函" ;
		try{
			//设置数据
			Map content = new HashMap();
			//取出数据
			//调用模型
			confirPdf(map,baos) ;
			//
	   	    String strFileName = pdfName+".pdf";
	   	    response.setContentType("application/pdf");
	   	    response.setCharacterEncoding("UTF-8");
	   	    response.setHeader("Pragma", "public");
	   	    response.setHeader("Cache-Control",
	   		    "must-revalidate, post-check=0, pre-check=0");
	   	    response.setDateHeader("Expires", 0);
	   	    response.setHeader("Content-Disposition",
	   		    "attachment; filename=" +  new String( strFileName.getBytes("gb2312"), "ISO8859-1" ));
	   	    ServletOutputStream o = response.getOutputStream();
	   	    baos.writeTo(o);
	   	    o.flush();
	   		o.close() ;
			}catch(Exception e){
				logger.error(e);
			}
	}
	
	/**
	 * 判断对象是否为空 不为空转换为String类型
	 * @Function: com.pqsoft.project.service.ProjectManagerPdf.objectToString
	 * @param obj 要判断对象
	 * @return String 字符串
	 * @author:  吴剑东
	 * @date:    2013-9-17 下午07:24:20
	 */
	public static String objectToString(Object obj){
		String str = "";
		str = obj==null?"":obj.toString();
		return str;
	}
	
	public static void contentPdf1(Map<String,Object> map,OutputStream baos) throws Exception 
	{
		try {
			
//		//基础信息
		Map param = (Map)map.get("param");
		

		Map custInfo = (Map)map.get("custInfo");
		Map custLinkInfo = (Map)map.get("custLinkInfo");
		
		//经销商
		String SUPPLIERS_NAME = "";
		
		//设备信息
		List eqList=(List)map.get("eqList");
		for(int i=0;i<eqList.size();i++){
			Map item = (Map)eqList.get(0); 
			SUPPLIERS_NAME = item.get("SUPPLIERS_NAME")==null?"":item.get("SUPPLIERS_NAME").toString();
		}

		List SCHEMEDETAIL=(List)map.get("SCHEMEDETAIL");
		
		String qizuValue = "";
		String baozhengjinValue = "";
		String paiguaiValue = "";
		String fangkuanValue = "";
		String zulinbaoValue = "";
		
		for(int i=0;i<SCHEMEDETAIL.size();i++){
			Map item = (Map)SCHEMEDETAIL.get(i); 
			if("最低起租比例".equals(objectToString(item.get("KEY_NAME_ZN")))){
				qizuValue = objectToString(item.get("VALUE_STR"));
				continue;
			}
			if("保证金比例".equals(objectToString(item.get("KEY_NAME_ZN")))){
				baozhengjinValue = objectToString(item.get("VALUE_STR"));
				continue;
			}
			if("牌抵挂".equals(objectToString(item.get("KEY_NAME_ZN")))){
				paiguaiValue = objectToString(item.get("VALUE_STR"));
				continue;
			}
			if("放款政策".equals(objectToString(item.get("KEY_NAME_ZN")))){
				fangkuanValue = objectToString(item.get("VALUE_STR"));
				continue;
			}
			if("租赁包".equals(objectToString(item.get("KEY_NAME_ZN")))){
				zulinbaoValue = objectToString(item.get("VALUE_STR"));
				continue;
			}
			
		}
		
		
		//定义Cell边框粗细   顺序是：上下左右
	 	float[] borderStart = {0.5f,0,0.5f,0} ;
	 	float[] borderEnd = {0.5f,0,0.5f,0.5f} ;
	 	float[] borderStart1 = {0.5f,0.5f,0.5f,0} ;
	 	float[] borderEnd1 = {0.5f,0.5f,0.5f,0.5f} ;
	 	float[] borderStart2 = {0,0,0.5f,0} ;
	 	float[] borderEnd2 = {0,0,0,0} ;
	 	float[] borderStart3 = {0,0,0,0} ;
	 	//定义默认字体
	 	int[] fontDefault = {-1,-1} ;
	 	//定义默认边距   顺序是：上下左右
	 	float[] paddingDefault = {2f,2f,2f,2f};
	 	//定义默认位置    水平，垂直
	 	int [] alignDefault = {-1,-1} ;//靠左
	 	int [] alignCenter = {PdfPCell.ALIGN_CENTER,PdfPCell.ALIGN_CENTER} ;//居中
	 	int [] alignRight = {PdfPCell.ALIGN_RIGHT,PdfPCell.ALIGN_CENTER} ;//靠右
	 	int [] alignLift = {PdfPCell.ALIGN_LEFT,PdfPCell.ALIGN_CENTER} ;//靠左
		//pdf名字
	 	
	 	
 		BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.EMBEDDED);
	 
 		// 数字格式
        NumberFormat nfFSNum = new DecimalFormat("###,###,###,###.00");
        nfFSNum.setGroupingUsed(true);
        nfFSNum.setMaximumFractionDigits(2);
        // 页面设置
        Rectangle rectPageSize = new Rectangle(PageSize.A4); // 定义A4页面大小
        
        Document document = new Document(rectPageSize, 2, 2,10, 10); // 其余4个参数，设置了页面的4个边距
        
    
        PdfWriter.getInstance(document, baos);
        // 打开文档
        document.open();
        //写入标题
        //t1 承租人部分
	 	PdfPTable t1 = new PdfPTable(11);
	 	
	 	
	 	//标题行
	   t1.addCell(makeCell(bfChinese,"山重融资租赁业务审批单",new int[]{12,Font.BOLD}, new float[]{10f,10f,-1f,-1f}, new float[]{0f,0f,0f,0f}, alignCenter, 11)) ;
	
	   //一、经销商系统提报信息    
	   t1.addCell(makeCell(bfChinese, "一、经销商系统提报信息  " , new int[]{11,Font.BOLD}, paddingDefault, borderEnd, alignLift, 11)) ;
	   
	   //
	   t1.addCell(makeCell(bfChinese, "经销商" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, SUPPLIERS_NAME , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
	   t1.addCell(makeCell(bfChinese, "信审通过日期" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, "待续----------------" , fontDefault, paddingDefault, borderEnd,alignCenter, 4)) ;

	   //
	   t1.addCell(makeCell(bfChinese, "厂方编号" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, "待续----------------" , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
	   t1.addCell(makeCell(bfChinese, "项目编号" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, objectToString(param.get("PRO_CODE")) , fontDefault, paddingDefault, borderEnd,alignCenter, 4)) ;

	   //二、租赁物明细    
	   t1.addCell(makeCell(bfChinese, "二、租赁物明细  " , new int[]{11,Font.BOLD}, paddingDefault, borderEnd, alignLift, 11)) ;
	   
	   //
	   t1.addCell(makeCell(bfChinese, "车 型" , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
	   //新建table放入单元格中  显示出拆分单元格效果
	   PdfPTable iTable = new PdfPTable(2);
	   for(int i=0;i<eqList.size();i++){
			Map item = (Map)eqList.get(i); 
			iTable.addCell(makeCell(bfChinese, objectToString(item.get("PRODUCT_NAME")) , fontDefault, paddingDefault, new float[]{0f,0.5f,0f,0.5f},alignCenter, -1)) ;
			iTable.addCell(makeCell(bfChinese, objectToString(item.get("UNIT_PRICE")) , fontDefault, paddingDefault, new float[]{0f,0.5f,0f,0f},alignCenter, -1)) ;
			
		}
	   PdfPCell iCell = new PdfPCell(iTable);
	   iCell.setColspan(2);
	   t1.addCell(iCell);
	   t1.addCell(makeCell(bfChinese, "数 量(台)" , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
	   
	   PdfPTable sumTable = new PdfPTable(1);
	   for(int i=0;i<eqList.size();i++){
		   Map item = (Map)eqList.get(i); 
		   sumTable.addCell(makeCell(bfChinese, objectToString(item.get("AMOUNT")) , fontDefault, paddingDefault, new float[]{0f,0.5f,0f,0.5f},alignCenter, -1)) ;
	   }
	   PdfPCell sumCell = new PdfPCell(sumTable);
	   t1.addCell(sumCell);
	   
	   t1.addCell(makeCell(bfChinese, "销售价总金额(万元)" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, objectToString(param.get("LEASE_TOPRIC"))  , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
	   t1.addCell(makeCell(bfChinese, "融资总金额(万元)" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, "待续----------------" , fontDefault, paddingDefault, borderEnd,alignCenter, -1)) ;
	   
	   t1.addCell(makeCell(bfChinese, "起租比例" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, qizuValue+"  %" , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
	   t1.addCell(makeCell(bfChinese, "租赁期限" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, objectToString(param.get("LEASE_TERM"))+" 个月"  , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
	   t1.addCell(makeCell(bfChinese, "经销商保证金比例" , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
	   t1.addCell(makeCell(bfChinese, baozhengjinValue+"  %", fontDefault, paddingDefault, borderEnd,alignCenter, 2)) ;
	   
	   t1.addCell(makeCell(bfChinese, "车辆抵押情况" , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
	   t1.addCell(makeCell(bfChinese, paiguaiValue , fontDefault, paddingDefault, borderEnd,alignCenter, 8)) ;
	   
	   t1.addCell(makeCell(bfChinese, "租赁物名称" , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
	   t1.addCell(makeCell(bfChinese, "型号" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, "出厂编号" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, "发动机号" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, "车架号" , fontDefault, paddingDefault, borderEnd,alignCenter, 2)) ;
	   
	   
	   for (int i = 0; i < eqList.size(); i++) {
		   Map item = (Map)eqList.get(i);
		   t1.addCell(makeCell(bfChinese, objectToString(item.get("PRODUCT_NAME")) , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
		   t1.addCell(makeCell(bfChinese, objectToString(item.get("SPEC_NAME")) , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, objectToString(item.get("WHOLE_ENGINE_CODE")) , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, objectToString(item.get("ENGINE_TYPE")) , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, objectToString(item.get("CAR_SYMBOL")) , fontDefault, paddingDefault, borderEnd,alignCenter, 2)) ;
	   }
	   
	   t1.addCell(makeCell(bfChinese, "放款方式" , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
	   t1.addCell(makeCell(bfChinese, fangkuanValue , fontDefault, paddingDefault, borderEnd,alignCenter, 8)) ;
	   

	   t1.addCell(makeCell(bfChinese, "租赁包" , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
	   t1.addCell(makeCell(bfChinese, zulinbaoValue , fontDefault, paddingDefault, borderEnd,alignCenter, 8)) ;
	   

	   t1.addCell(makeCell(bfChinese, "风险控制措施" , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
	   t1.addCell(makeCell(bfChinese, "待续----------------" , fontDefault, paddingDefault, borderEnd,alignCenter, 8)) ;
	   

	   t1.addCell(makeCell(bfChinese, "经销商说明" , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
	   t1.addCell(makeCell(bfChinese, "待续----------------" , fontDefault, paddingDefault, borderEnd,alignCenter, 8)) ;
	   
	   if(("NP").equals(objectToString(custInfo.get("TYPE")))){
		   
	   
		   //三、承租人基本情况(自然人)
		   t1.addCell(makeCell(bfChinese, "三、承租人基本情况(个人) " , new int[]{11,Font.BOLD}, paddingDefault, borderEnd, alignLift, 11)) ;
	
		   t1.addCell(makeCell(bfChinese, "承租人姓名" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, objectToString(custInfo.get("NAME")) , fontDefault, paddingDefault, borderStart,alignCenter, 1)) ;
		   t1.addCell(makeCell(bfChinese, "身份证号" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, objectToString(custInfo.get("ID_CARD_NO")) , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, "手机" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, objectToString(custInfo.get("TEL_PHONE")) , fontDefault, paddingDefault, borderEnd,alignCenter, 2)) ;
		   
		   t1.addCell(makeCell(bfChinese, "配偶姓名" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, objectToString(custLinkInfo.get("NAME")) , fontDefault, paddingDefault, borderStart,alignCenter, 1)) ;
		   t1.addCell(makeCell(bfChinese, "身份证号" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, objectToString(custLinkInfo.get("ID_CARD_NO")) , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, "手机" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, objectToString(custLinkInfo.get("TEL_PHONE")) , fontDefault, paddingDefault, borderEnd,alignCenter, 2)) ;

	   }else{
			   
		   //三、承租人基本情况(法人)
		   t1.addCell(makeCell(bfChinese, "三、承租人基本情况(法人) " , new int[]{11,Font.BOLD}, paddingDefault, borderEnd, alignLift, 11)) ;
		   
		   t1.addCell(makeCell(bfChinese, "客户名称" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, objectToString(custInfo.get("NAME")) , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
		   t1.addCell(makeCell(bfChinese, "地址" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, objectToString(custInfo.get("WORK_ADDRESS")) , fontDefault, paddingDefault, borderEnd,alignCenter, 4)) ;
		   
	
		   t1.addCell(makeCell(bfChinese, "法定代表人" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, objectToString(custInfo.get("EGAL_PERSON")) , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
		   t1.addCell(makeCell(bfChinese, "注册资本" , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
		   t1.addCell(makeCell(bfChinese, objectToString(custInfo.get("REGISTE_CAPITAL")) , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
		   t1.addCell(makeCell(bfChinese, "客户类型" , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
		   t1.addCell(makeCell(bfChinese, objectToString(custInfo.get("TYPE_NAME")) , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
		   
		   t1.addCell(makeCell(bfChinese, "组织机构代码" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, objectToString(custInfo.get("ORAGNIZATION_CODE")) , fontDefault, paddingDefault, borderEnd,alignCenter, 2)) ;
		   
	
		   t1.addCell(makeCell(bfChinese, "主营业务" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, objectToString(custInfo.get("MAIN_BUSINESS"))  , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, "注册日期" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, objectToString(custInfo.get("REGISTE_DATE"))   , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, "电话" , fontDefault, paddingDefault, borderStart,alignCenter, 1)) ;
		   t1.addCell(makeCell(bfChinese, objectToString(custInfo.get("REGISTE_PHONE")) , fontDefault, paddingDefault, borderEnd,alignCenter, 2)) ;
		   
	
		   t1.addCell(makeCell(bfChinese, "资产总额" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, "testx" , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
		   t1.addCell(makeCell(bfChinese, "负债总额" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, "testx" , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
		   t1.addCell(makeCell(bfChinese, "净资产" , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
		   t1.addCell(makeCell(bfChinese, "testx" , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
		   t1.addCell(makeCell(bfChinese, "流动资产" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, "123456" , fontDefault, paddingDefault, borderEnd,alignCenter, -1)) ;
		   
	
		   t1.addCell(makeCell(bfChinese, "固定资产" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, "testx" , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
		   t1.addCell(makeCell(bfChinese, "主营业务收入" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, "testx" , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
		   t1.addCell(makeCell(bfChinese, "净利润" , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
		   t1.addCell(makeCell(bfChinese, "testx" , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
		   t1.addCell(makeCell(bfChinese, "上年净利润" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   t1.addCell(makeCell(bfChinese, "1234567" , fontDefault, paddingDefault, borderEnd,alignCenter, -1)) ;
		   

	   }
	   //四、信审担当意见
	   t1.addCell(makeCell(bfChinese, "四、信审担当意见" , new int[]{11,Font.BOLD}, paddingDefault, borderEnd, alignLift, 11)) ;
	   
	   t1.addCell(makeCell(bfChinese, "信审担当意见及总经理审批意见" , fontDefault, paddingDefault, borderEnd, alignLift, 11)) ;
	   
	   
	   //五、呼叫中心验证
	   t1.addCell(makeCell(bfChinese, "五、呼叫中心验证" , new int[]{11,Font.BOLD}, paddingDefault, borderEnd, alignLift, 11)) ;
	   t1.addCell(makeCell(bfChinese, "呼叫中心意见（截取承租人身份验证和租赁物信息验证的信息）" , fontDefault, paddingDefault, borderEnd, alignLift, 11)) ;
		
	   
	   //六、备注
	   t1.addCell(makeCell(bfChinese, "六、备注" , new int[]{11,Font.BOLD}, paddingDefault, borderEnd, alignLift, 11)) ;
	   t1.addCell(makeCell(bfChinese, "备注" , fontDefault, paddingDefault, borderEnd1, alignLift, 11)) ;
		
	   
	   
	   
	   document.add(t1);
		document.close();
		

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void confirPdf(Map<String,Object> map,OutputStream baos) throws Exception 
	{
		try {
			
//		//基础信息
		Map param = (Map)map.get("param");
		

		Map custInfo = (Map)map.get("custInfo");
		Map custLinkInfo = (Map)map.get("custLinkInfo");
		
		//经销商
		String SUPPLIERS_NAME = "";
		
		//设备信息
		List eqList=(List)map.get("eqList");
		for(int i=0;i<eqList.size();i++){
			Map item = (Map)eqList.get(0); 
			SUPPLIERS_NAME = item.get("SUPPLIERS_NAME")==null?"":item.get("SUPPLIERS_NAME").toString();
		}

		List SCHEMEDETAIL=(List)map.get("SCHEMEDETAIL");
		
		String qizuValue = "";
		String baozhengjinValue = "";
		String paiguaiValue = "";
		String fangkuanValue = "";
		String zulinbaoValue = "";
		
		for(int i=0;i<SCHEMEDETAIL.size();i++){
			Map item = (Map)SCHEMEDETAIL.get(i); 
			if("最低起租比例".equals(objectToString(item.get("KEY_NAME_ZN")))){
				qizuValue = objectToString(item.get("VALUE_STR"));
				continue;
			}
			if("保证金比例".equals(objectToString(item.get("KEY_NAME_ZN")))){
				baozhengjinValue = objectToString(item.get("VALUE_STR"));
				continue;
			}
			if("牌抵挂".equals(objectToString(item.get("KEY_NAME_ZN")))){
				paiguaiValue = objectToString(item.get("VALUE_STR"));
				continue;
			}
			if("放款政策".equals(objectToString(item.get("KEY_NAME_ZN")))){
				fangkuanValue = objectToString(item.get("VALUE_STR"));
				continue;
			}
			if("租赁包".equals(objectToString(item.get("KEY_NAME_ZN")))){
				zulinbaoValue = objectToString(item.get("VALUE_STR"));
				continue;
			}
			
		}
		
		
		//定义Cell边框粗细   顺序是：上下左右
	 	float[] borderStart = {0.5f,0,0.5f,0} ;
	 	float[] borderEnd = {0.5f,0,0.5f,0.5f} ;
	 	float[] borderStart1 = {0.5f,0.5f,0.5f,0} ;
	 	float[] borderEnd1 = {0.5f,0.5f,0.5f,0.5f} ;
	 	float[] borderStart2 = {0,0,0.5f,0} ;
	 	float[] borderEnd2 = {0,0,0,0} ;
	 	float[] borderStart3 = {0,0,0,0} ;
	 	//定义默认字体
	 	int[] fontDefault = {-1,-1} ;
	 	//定义默认边距   顺序是：上下左右
	 	float[] paddingDefault = {2f,2f,2f,2f};
	 	//定义默认位置    水平，垂直
	 	int [] alignDefault = {-1,-1} ;//靠左
	 	int [] alignCenter = {PdfPCell.ALIGN_CENTER,PdfPCell.ALIGN_CENTER} ;//居中
	 	int [] alignRight = {PdfPCell.ALIGN_RIGHT,PdfPCell.ALIGN_CENTER} ;//靠右
	 	int [] alignLift = {PdfPCell.ALIGN_LEFT,PdfPCell.ALIGN_CENTER} ;//靠左
		//pdf名字
	 	
	 	
 		BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.EMBEDDED);
	 
 		// 数字格式
        NumberFormat nfFSNum = new DecimalFormat("###,###,###,###.00");
        nfFSNum.setGroupingUsed(true);
        nfFSNum.setMaximumFractionDigits(2);
        // 页面设置
        Rectangle rectPageSize = new Rectangle(PageSize.A4); // 定义A4页面大小
        
        Document document = new Document(rectPageSize, 2, 2,10, 10); // 其余4个参数，设置了页面的4个边距
        
    
        PdfWriter.getInstance(document, baos);
        // 打开文档
        document.open();
        //写入标题
        //t1 承租人部分
	 	PdfPTable t1 = new PdfPTable(11);
	 	
	 	
	 	//标题行
	   t1.addCell(makeCell(bfChinese,"业务确认函",new int[]{12,Font.BOLD}, new float[]{10f,10f,-1f,-1f}, new float[]{0f,0f,0f,0f}, alignCenter, 11)) ;
	   //
	   t1.addCell(makeCell(bfChinese, "经销商" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, SUPPLIERS_NAME , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
	   t1.addCell(makeCell(bfChinese, "承租人" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, objectToString(param.get("CUST_NAME")) , fontDefault, paddingDefault, borderEnd,alignCenter, 4)) ;

	   //
	   t1.addCell(makeCell(bfChinese, "厂方编号" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, "待续----------------" , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
	   t1.addCell(makeCell(bfChinese, "项目编号" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, objectToString(param.get("PRO_CODE")) , fontDefault, paddingDefault, borderEnd,alignCenter, 4)) ;
	   //
	   t1.addCell(makeCell(bfChinese, "车 型" , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
	   //新建table放入单元格中  显示出拆分单元格效果
	   PdfPTable iTable = new PdfPTable(2);
	   for(int i=0;i<eqList.size();i++){
			Map item = (Map)eqList.get(i); 
			iTable.addCell(makeCell(bfChinese, objectToString(item.get("PRODUCT_NAME")) , fontDefault, paddingDefault, new float[]{0f,0.5f,0f,0.5f},alignCenter, -1)) ;
			iTable.addCell(makeCell(bfChinese, objectToString(item.get("UNIT_PRICE")) , fontDefault, paddingDefault, new float[]{0f,0.5f,0f,0f},alignCenter, -1)) ;
			
		}
	   PdfPCell iCell = new PdfPCell(iTable);
	   iCell.setColspan(2);
	   t1.addCell(iCell);
	   t1.addCell(makeCell(bfChinese, "数 量(台)" , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
	   
	   PdfPTable sumTable = new PdfPTable(1);
	   for(int i=0;i<eqList.size();i++){
		   Map item = (Map)eqList.get(i); 
		   sumTable.addCell(makeCell(bfChinese, objectToString(item.get("AMOUNT")) , fontDefault, paddingDefault, new float[]{0f,0.5f,0f,0.5f},alignCenter, -1)) ;
	   }
	   PdfPCell sumCell = new PdfPCell(sumTable);
	   t1.addCell(sumCell);
	   
	   t1.addCell(makeCell(bfChinese, "销售价总金额(万元)" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, objectToString(param.get("LEASE_TOPRIC"))  , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
	   t1.addCell(makeCell(bfChinese, "融资总金额(万元)" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, "待续----------------" , fontDefault, paddingDefault, borderEnd,alignCenter, -1)) ;
	   
	   t1.addCell(makeCell(bfChinese, "起租比例" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, qizuValue+"  %" , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
	   t1.addCell(makeCell(bfChinese, "租赁期限" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, objectToString(param.get("LEASE_TERM"))+" 个月"  , fontDefault, paddingDefault, borderStart,alignCenter, -1)) ;
	   t1.addCell(makeCell(bfChinese, "经销商保证金比例" , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
	   t1.addCell(makeCell(bfChinese, baozhengjinValue+"  %", fontDefault, paddingDefault, borderEnd,alignCenter, 2)) ;
	   
	   t1.addCell(makeCell(bfChinese, "车辆抵押情况" , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
	   t1.addCell(makeCell(bfChinese, paiguaiValue , fontDefault, paddingDefault, borderEnd,alignCenter, 8)) ;
	   
	   t1.addCell(makeCell(bfChinese, "放款方式" , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
	   t1.addCell(makeCell(bfChinese, fangkuanValue , fontDefault, paddingDefault, borderEnd,alignCenter, 8)) ;
	   

	   t1.addCell(makeCell(bfChinese, "确认租赁物明细信息" , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
	   t1.addCell(makeCell(bfChinese, "待续----------------" , fontDefault, paddingDefault, borderEnd,alignCenter, 8)) ;
	   

	   t1.addCell(makeCell(bfChinese, "确认放款账户" , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
	   t1.addCell(makeCell(bfChinese, "待续----------------" , fontDefault, paddingDefault, borderEnd,alignCenter, 8)) ;
	   

	   t1.addCell(makeCell(bfChinese, "车辆已售日期" , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
	   t1.addCell(makeCell(bfChinese, "待续----------------" , fontDefault, paddingDefault, borderEnd,alignCenter, 8)) ;

	   t1.addCell(makeCell(bfChinese, "厂家审批意见" , fontDefault, paddingDefault, borderEnd1,alignCenter, 3)) ;
	   t1.addCell(makeCell(bfChinese, "待续----------------" , fontDefault, paddingDefault, borderEnd1, alignLift, 8)) ;
		
	   
	   
	   
	   document.add(t1);
		document.close();
		

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private static PdfPCell makeCell(BaseFont bfChinese,String content,int[] fontType,float[] paddingType,
			float[] borderType,int[] alignType,int colspan) {
	 //BaseFont bfChinese //字体设置
 	//字体自定义
	//int fontType[0]=0;//字体大小(一般设置成11,默认为11（标记-1）)
	//int fontType[1]=0;//字体（用系统函数Font下的参数（例如：Font.BOLD）,默认为Font.BOLD（标记-1））

	//字体位置
	 //float paddingTopF=0f;//离上边距距离paddingType[0]
	 //float paddingBottomF=0f;//离下边距距离paddingType [1]
	 //float paddingLeftF=0f;//离左边距距离paddingType [2]
	 //float paddingRightF=0f;//离右边距距离 paddingType[3]
 
 	
 	//int alignHorizontal alignType[0]//水平位置（用系统函数PdfPCell的参数（居中：PdfPCell.ALIGN_CENTER,靠左：PdfPCell.ALIGN_LEFT或PdfPCell.LEFT,
 							//靠右PdfPCell.ALIGN_RIGHT或PdfPCell.RIGHT,靠上PdfPCell.ALIGN_TOP或PdfPCell.TOP，靠下PdfPCell.ALIGN_BOTTOM或PdfPCell.BOTTOM）
 								//默认为不设置（标记为-1））
 	
 	//int alignVertical   alignType[1]//垂直位置(同水平位置设定)默认为不设置（标记为-1）
 
 	//float borderTopF=0f;//上边框粗细
 	//float borderBottomF=0f;//下边框粗细
 	//float borderLeftF=0f;//左边框粗细
 	//float borderRightF=0f;//右边框粗细
 
 
 	//int colspan=0;合并单元格,默认为不设置（标记为-1）
 	
 	Font FontStyleDe=null;
 	if(fontType[0]<=0f)
 	{
 		if(fontType[1]==-1f)
 		{
 			FontStyleDe = new Font(bfChinese, 8f, Font.BOLD);
 		}
 		else
 		{
 			FontStyleDe = new Font(bfChinese, 8f, fontType[1]);
 		}
 		
 	}
 	else
 	{
 		if(fontType[1]==-1f)
 		{
 			FontStyleDe = new Font(bfChinese, fontType[0], Font.BOLD);
 		}
 		else
 		{
 			FontStyleDe = new Font(bfChinese, fontType[0], fontType[1]);
 		}
 	} 
 	
	Phrase objPhase = new Phrase(content, FontStyleDe);
	PdfPCell objCell = new PdfPCell(objPhase);
	
	
	if(paddingType[0]!=-1)
	{
		objCell.setPaddingTop(paddingType[0]);
	}
	if(paddingType[1]!=-1)
	{
		objCell.setPaddingBottom(paddingType[1]);
	}
	
	if(paddingType[2]!=-1)
	{
		objCell.setPaddingLeft(paddingType[2]);
	}
	if(paddingType[3]!=-1)
	{
		objCell.setPaddingRight(paddingType[3]);
	}
	
	objCell.setBorderWidthTop(borderType[0]);
	objCell.setBorderWidthBottom(borderType[1]);
	objCell.setBorderWidthLeft(borderType[2]);
	objCell.setBorderWidthRight(borderType[3]);
	
	if(alignType[0]!=-1)
	{
		objCell.setHorizontalAlignment(alignType[0]);
	}
	if(alignType[1]!=-1)
	{
		objCell.setVerticalAlignment(alignType[1]);
	}
	
	if(colspan!=-1)
	{
		objCell.setColspan(colspan);
	}

	return objCell;
}
	
}
