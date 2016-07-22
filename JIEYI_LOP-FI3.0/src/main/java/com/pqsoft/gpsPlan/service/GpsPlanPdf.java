package com.pqsoft.gpsPlan.service;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.pqsoft.util.CurrencyConverter;

public class GpsPlanPdf {
	private final Logger logger = Logger.getLogger(this.getClass());

	public void gpsPlanPdfView(HttpServletResponse response,VelocityContext context) throws UnsupportedEncodingException
	{
		ByteArrayOutputStream baos =  new ByteArrayOutputStream();
		// PDF名字的定义
		String pdfName = "GPS申请单 " ;
		pdfName=new String(pdfName.getBytes("UTF-8"),"ISO8859-1");
		try{
			//设置数据
			Map content = new HashMap();
			
			Map map1=new HashMap();
			if(context!=null)
			{
				if(context.get("gpsInfo")!=null)
				{
					map1=(Map)context.get("gpsInfo");
				}
				if(context.get("credit")!=null)
				{
					content=(Map)context.get("credit");
				}
			}
			//取出数据
			//调用模型
			contentPdf(map1,content,baos) ;
			//
	   	    String strFileName = pdfName+".pdf";
	   	    response.setContentType("application/pdf");
	   	    response.setCharacterEncoding("UTF-8");
	   	    response.setHeader("Pragma", "public");
	   	    response.setHeader("Cache-Control",
	   		    "must-revalidate, post-check=0, pre-check=0");
	   	    response.setDateHeader("Expires", 0);
	   	    response.setHeader("Content-Disposition",
	   		    "attachment; filename=" + strFileName);
	   	    ServletOutputStream o = response.getOutputStream();
	   	    baos.writeTo(o);
	   	    o.flush();
	   		o.close() ;
			}catch(Exception e){
				logger.error(e);
			}
	}
	
	
	public static void contentPdf(Map<String,Object> map1,Map<String,Object> map,OutputStream baos) throws Exception 
	{
		
		//基本信息
		Map jibenData=new HashMap();
		
		//设备列表
		List equipmentList=new ArrayList();
		
		if(map!=null)
		{
			if(map.get("head")!=null)
			{
				jibenData=(Map)map.get("head");
			}
			if(map.get("equipmentList")!=null)
			{
				equipmentList=(List)map.get("equipmentList");
			}
		}
		
		
		
		
		
		//定义Cell边框粗细   顺序是：上下左右
	 	float[] borderStart = {0.5f,0,0.5f,0} ;
	 	float[] borderEnd = {0.5f,0,0.5f,0.5f} ;
	 	float[] borderStart1 = {0.5f,0.5f,0.5f,0} ;
	 	float[] borderEnd1 = {0.5f,0.5f,0.5f,0.5f} ;
	 	float[] borderStart2 = {0,0,0.5f,0} ;
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
	 	PdfPTable t1 = new PdfPTable(24);
	 	//标题行
	   t1.addCell(makeCell(bfChinese,"GPS申请单",new int[]{11,Font.BOLD}, new float[]{10f,10f,-1f,-1f}, new float[]{0f,0f,0f,0f}, alignCenter, 24)) ;
	
	   	//第一行    支付表号：        融资租赁合同号：     承租人：
	   t1.addCell(makeCell(bfChinese, "基本信息" , fontDefault, paddingDefault, borderEnd,fontDefault, 24)) ;
	  
	   
	   //第二行   租赁物清单    租赁物名称  型号    整机编号  单价    数量   留购价    买卖合同编号
	   String PROJECT_NAME="";
	   if(jibenData.get("PROJECT_NAME")!=null && !jibenData.get("PROJECT_NAME").equals(""))
	   {
		   PROJECT_NAME=jibenData.get("PROJECT_NAME").toString();
	   }
	   t1.addCell(makeCell(bfChinese, "项目名称："+PROJECT_NAME , fontDefault, paddingDefault, borderStart,alignCenter, 8)) ;
	   String CLERK_NAME="";
	   if(jibenData.get("CLERK_NAME")!=null && !jibenData.get("CLERK_NAME").equals(""))
	   {
		   CLERK_NAME=jibenData.get("CLERK_NAME").toString();
	   }
	   t1.addCell(makeCell(bfChinese, "客户经理："+CLERK_NAME , fontDefault, paddingDefault, borderStart,alignCenter, 8)) ;
	   String PLATEFORM="";
	   if(jibenData.get("PLATEFORM")!=null && !jibenData.get("PLATEFORM").equals(""))
	   {
		   PLATEFORM=jibenData.get("PLATEFORM").toString();
	   }
	   t1.addCell(makeCell(bfChinese, "业务类型："+PLATEFORM , fontDefault, paddingDefault, borderEnd,alignCenter, 8)) ;
	   
	   
	   
	   String CR_BECR_Flag="";
	   if(jibenData.get("CR_BECR_TYPE")!=null && !jibenData.get("CR_BECR_TYPE").equals(""))
	   {
		  String CR_BECR_TYPE=jibenData.get("CR_BECR_TYPE").toString();
		  if(CR_BECR_TYPE.equals("1"))
		  {
			  CR_BECR_Flag="个人";
		  }
		  else
		  {
			  CR_BECR_Flag="法人";
		  }
	   }
	   
	   String RENTER_NAME="";
	   if(jibenData.get("RENTER_NAME")!=null && !jibenData.get("RENTER_NAME").equals(""))
	   {
		   RENTER_NAME=jibenData.get("RENTER_NAME").toString();
	   }
	   
	   String RENTER_TYPE="";
	   if(jibenData.get("RENTER_TYPE")!=null && !jibenData.get("RENTER_TYPE").equals(""))
	   {
		   RENTER_TYPE=jibenData.get("RENTER_TYPE").toString();
	   }
	   
	   t1.addCell(makeCell(bfChinese, "承租人："+RENTER_NAME, fontDefault, paddingDefault, borderStart,alignCenter, 8)) ;
	   
	   t1.addCell(makeCell(bfChinese, "客户类型："+CR_BECR_Flag , fontDefault, paddingDefault, borderStart,alignCenter, 8)) ;
	  
	   t1.addCell(makeCell(bfChinese, "客户分类："+RENTER_TYPE , fontDefault, paddingDefault, borderEnd,alignCenter, 8)) ;
	   
	   
	   
	   t1.addCell(makeCell(bfChinese, "设备信息" , fontDefault, paddingDefault, borderEnd,fontDefault, 24)) ;
	   
	   
	   t1.addCell(makeCell(bfChinese, "厂商" , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
	   t1.addCell(makeCell(bfChinese, "供应商" , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
	   t1.addCell(makeCell(bfChinese, "设备名称" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, "设备型号" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, "发票编号" , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
	   t1.addCell(makeCell(bfChinese, "合格证编号" , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
	   t1.addCell(makeCell(bfChinese, "留购价（元）" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, "单价" , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
	   t1.addCell(makeCell(bfChinese, "数量" , fontDefault, paddingDefault, borderStart,alignCenter, 1)) ;
	   t1.addCell(makeCell(bfChinese, "单位" , fontDefault, paddingDefault, borderStart,alignCenter, 1)) ;
	   t1.addCell(makeCell(bfChinese, "小计" , fontDefault, paddingDefault, borderEnd,alignCenter, 2)) ;
	   
	   String TOTAL="0";
	   for(int i=0;i<equipmentList.size();i++)
	   {
		   Map equ=(Map)equipmentList.get(i);
		   
		   String COMPANY_NAME="";
		   if(equ.get("COMPANY_NAME")!=null && !equ.get("COMPANY_NAME").equals(""))
		   {
			   COMPANY_NAME=equ.get("COMPANY_NAME").toString();
		   }
		   t1.addCell(makeCell(bfChinese, ""+COMPANY_NAME  , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
		   
		   String SUPPLIERS_NAME="";
		   if(equ.get("SUPPLIERS_NAME")!=null && !equ.get("SUPPLIERS_NAME").equals(""))
		   {
			   SUPPLIERS_NAME=equ.get("SUPPLIERS_NAME").toString();
		   }
		   t1.addCell(makeCell(bfChinese, ""+SUPPLIERS_NAME , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
		   
		   String THING_NAME="";
		   if(equ.get("THING_NAME")!=null && !equ.get("THING_NAME").equals(""))
		   {
			   THING_NAME=equ.get("THING_NAME").toString();
		   }
		   t1.addCell(makeCell(bfChinese, ""+THING_NAME , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   
		   String MODEL_SPEC="";
		   if(equ.get("MODEL_SPEC")!=null && !equ.get("MODEL_SPEC").equals(""))
		   {
			   THING_NAME=equ.get("MODEL_SPEC").toString();
		   }
		   t1.addCell(makeCell(bfChinese, ""+MODEL_SPEC , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   
		   String RECEIPT_NUMBER="";
		   if(equ.get("RECEIPT_NUMBER")!=null && !equ.get("RECEIPT_NUMBER").equals(""))
		   {
			   RECEIPT_NUMBER=equ.get("RECEIPT_NUMBER").toString();
		   }
		   t1.addCell(makeCell(bfChinese, RECEIPT_NUMBER , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
		   
		   String CERTIFICATE_NO="";
		   if(equ.get("CERTIFICATE_NO")!=null && !equ.get("CERTIFICATE_NO").equals(""))
		   {
			   CERTIFICATE_NO=equ.get("CERTIFICATE_NO").toString();
		   }
		   t1.addCell(makeCell(bfChinese, CERTIFICATE_NO , fontDefault, paddingDefault, borderStart,alignCenter, 3)) ;
		   
		   String STAYBUY_PRICE="";
		   if(equ.get("STAYBUY_PRICE")!=null && !equ.get("STAYBUY_PRICE").equals(""))
		   {
			   STAYBUY_PRICE=equ.get("STAYBUY_PRICE").toString();
		   }
		   t1.addCell(makeCell(bfChinese, ""+STAYBUY_PRICE , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   
		   String UNIT_PRICE="";
		   if(equ.get("UNIT_PRICE")!=null && !equ.get("UNIT_PRICE").equals(""))
		   {
			   UNIT_PRICE=equ.get("UNIT_PRICE").toString();
		   }
		   t1.addCell(makeCell(bfChinese, ""+UNIT_PRICE , fontDefault, paddingDefault, borderStart,alignCenter, 2)) ;
		   
		   String AMOUNT="";
		   if(equ.get("AMOUNT")!=null && !equ.get("AMOUNT").equals(""))
		   {
			   AMOUNT=equ.get("AMOUNT").toString();
		   }
		   t1.addCell(makeCell(bfChinese, ""+AMOUNT , fontDefault, paddingDefault, borderStart,alignCenter, 1)) ;
		   
		   String UNIT="";
		   if(equ.get("UNIT")!=null && !equ.get("UNIT").equals(""))
		   {
			   UNIT=equ.get("UNIT").toString();
		   }
		   t1.addCell(makeCell(bfChinese, ""+UNIT , fontDefault, paddingDefault, borderStart,alignCenter, 1)) ;
		  
		  
		   if(equ.get("TOTAL")!=null && !equ.get("TOTAL").equals(""))
		   {
			   TOTAL=equ.get("TOTAL").toString();
		   }
		   t1.addCell(makeCell(bfChinese, ""+TOTAL , fontDefault, paddingDefault, borderEnd,alignCenter, 2)) ;
		   
	   }
	   
	   t1.addCell(makeCell(bfChinese, "合计（大写）："+CurrencyConverter.toUpper(TOTAL) , fontDefault, paddingDefault, borderStart,alignRight, 16)) ;
	   t1.addCell(makeCell(bfChinese,"（小写：￥） "+TOTAL , fontDefault, paddingDefault, borderEnd,alignCenter, 8)) ;
	   
	   t1.addCell(makeCell(bfChinese, "授权方案" , fontDefault, paddingDefault, borderEnd,fontDefault, 24)) ;
	   
	   
	   t1.addCell(makeCell(bfChinese, "目前设备状态" , fontDefault, paddingDefault, borderStart,alignRight, 4)) ;
	   String type="";
	   if(map1.get("TYPE")!=null)
	   {
		   if(map1.get("TYPE").equals("0"))
		   {
			   type="停机";
		   }
		   else
		   {
			   type="开机";
		   }
	   }
	   t1.addCell(makeCell(bfChinese, type , fontDefault, paddingDefault, borderStart,alignCenter, 4)) ;
	   
	   
	   t1.addCell(makeCell(bfChinese, "运行时间（小时）" , fontDefault, paddingDefault, borderStart,alignRight, 4)) ;
	   
	   String EQMT_RUNHOUR="";
	   if(map1.get("EQMT_RUNHOUR")!=null && !map1.get("EQMT_RUNHOUR").equals(""))
	   {
		   EQMT_RUNHOUR=map1.get("EQMT_RUNHOUR").toString();
	   }
	   t1.addCell(makeCell(bfChinese, ""+EQMT_RUNHOUR, fontDefault, paddingDefault, borderStart,alignCenter, 4)) ;
	   t1.addCell(makeCell(bfChinese, "施工环境" , fontDefault, paddingDefault, borderStart,alignRight, 4)) ;
	   String FLAG="";
	   if(map1.get("FLAG")!=null && !map1.get("FLAG").equals(""))
	   {
		   FLAG=map1.get("FLAG").toString();
	   }
	   t1.addCell(makeCell(bfChinese, FLAG, fontDefault, paddingDefault, borderEnd,alignCenter, 4)) ;
	   
	   
	   t1.addCell(makeCell(bfChinese, "GPS定位" , fontDefault, paddingDefault, borderStart,alignRight, 4)) ;
	   String EQMT_LONGITUDE="";
	   if(map1.get("EQMT_LONGITUDE")!=null && !map1.get("EQMT_LONGITUDE").equals(""))
	   {
		   EQMT_LONGITUDE=map1.get("EQMT_LONGITUDE").toString();
	   }
	   
	   String EQMT_LATITUDE="";
	   if(map1.get("EQMT_LATITUDE")!=null && !map1.get("EQMT_LATITUDE").equals(""))
	   {
		   EQMT_LATITUDE=map1.get("EQMT_LATITUDE").toString();
	   }
	   t1.addCell(makeCell(bfChinese, "经度: "+EQMT_LONGITUDE+"  纬度:"+EQMT_LATITUDE , fontDefault, paddingDefault, borderStart,alignCenter, 4)) ;
	   t1.addCell(makeCell(bfChinese, "设备位置" , fontDefault, paddingDefault, borderStart,alignRight, 4)) ;
	   String EQMT_ADDRESS="";
	   if(map1.get("EQMT_ADDRESS")!=null && !map1.get("EQMT_ADDRESS").equals(""))
	   {
		   EQMT_ADDRESS=map1.get("EQMT_ADDRESS").toString();
	   }
	   t1.addCell(makeCell(bfChinese, ""+EQMT_ADDRESS, fontDefault, paddingDefault, borderEnd,alignCenter, 12)) ;
	   
	   t1.addCell(makeCell(bfChinese, "申请人" , fontDefault, paddingDefault, borderStart,alignRight, 4)) ;
	   String CUST_NAME="";
	   if(map1.get("CUST_NAME")!=null && !map1.get("CUST_NAME").equals(""))
	   {
		   CUST_NAME=map1.get("CUST_NAME").toString();
	   }
	   t1.addCell(makeCell(bfChinese, ""+CUST_NAME , fontDefault, paddingDefault, borderStart,alignCenter, 4)) ;
	   t1.addCell(makeCell(bfChinese, "申请时间" , fontDefault, paddingDefault, borderStart,alignRight, 4)) ;
	   String APPLY_DATE="";
	   if(map1.get("APPLY_DATE")!=null && !map1.get("APPLY_DATE").equals(""))
	   {
		   APPLY_DATE=map1.get("APPLY_DATE").toString();
	   }
	   t1.addCell(makeCell(bfChinese, ""+APPLY_DATE, fontDefault, paddingDefault, borderStart,alignCenter, 4)) ;
	   String type1="";
	   if(map1.get("TYPE")!=null)
	   {
		   if(map1.get("TYPE").equals("1"))
		   {
			   type1="停";
		   }
		   else
		   {
			   type1="开";
		   }
	   }
	   t1.addCell(makeCell(bfChinese, "申请"+type1+"机时间" , fontDefault, paddingDefault, borderStart,alignRight, 4)) ;
	   String PLAN_TIME="";
	   if(map1.get("PLAN_TIME")!=null && !map1.get("PLAN_TIME").equals(""))
	   {
		   PLAN_TIME=map1.get("PLAN_TIME").toString();
	   }
	   t1.addCell(makeCell(bfChinese, ""+PLAN_TIME, fontDefault, paddingDefault, borderEnd,alignCenter, 4)) ;
	   
	   
	   t1.addCell(makeCell(bfChinese, type1+"机原因" , fontDefault, paddingDefault, borderStart,alignRight, 4)) ;
	   String SERVE_STATE="";
	   if(map1.get("SERVE_STATE")!=null)
	   {
		   if(map1.get("SERVE_STATE").equals("1"))
		   {
			   SERVE_STATE="逾期";
		   }
		   else
		   {
			   SERVE_STATE="非逾期";
		   }
	   }
	   t1.addCell(makeCell(bfChinese, SERVE_STATE, fontDefault, paddingDefault, borderEnd,alignCenter, 20)) ;
	   
	   t1.addCell(makeCell(bfChinese, "原因说明" , fontDefault, paddingDefault, borderStart,alignRight, 4)) ;
	   String SERVE_MEMO="";
	   if(map1.get("SERVE_MEMO")!=null && !map1.get("SERVE_MEMO").equals(""))
	   {
		   SERVE_MEMO=map1.get("SERVE_MEMO").toString();
	   }
	   t1.addCell(makeCell(bfChinese, ""+SERVE_MEMO, fontDefault, paddingDefault, borderEnd,alignCenter, 20)) ;
	   
	   t1.addCell(makeCell(bfChinese, "是否通知客户" , fontDefault, paddingDefault, borderStart,alignRight, 4)) ;
	   String WARN_STATE="";
	   if(map1.get("WARN_STATE")!=null)
	   {
		   if(map1.get("WARN_STATE").equals("1"))
		   {
			   WARN_STATE="是";
		   }
		   else
		   {
			   WARN_STATE="否";
		   }
	   }
	   t1.addCell(makeCell(bfChinese, WARN_STATE , fontDefault, paddingDefault, borderStart,alignCenter, 4)) ;
	   t1.addCell(makeCell(bfChinese, "通知时间" , fontDefault, paddingDefault, borderStart,alignRight, 4)) ;
	   String WARN_DATE="";
	   if(map1.get("WARN_DATE")!=null && !map1.get("WARN_DATE").equals(""))
	   {
		   WARN_DATE=map1.get("WARN_DATE").toString();
	   }
	   t1.addCell(makeCell(bfChinese, ""+WARN_DATE , fontDefault, paddingDefault, borderEnd,alignCenter, 12)) ;
	   
	   t1.addCell(makeCell(bfChinese, "通知客户说明" , fontDefault, paddingDefault, borderStart1,alignRight, 4)) ;
	   String WARM_MEMO="";
	   if(map1.get("WARM_MEMO")!=null && !map1.get("WARM_MEMO").equals(""))
	   {
		   WARM_MEMO=map1.get("WARM_MEMO").toString();
	   }
	   t1.addCell(makeCell(bfChinese, ""+WARM_MEMO, fontDefault, paddingDefault, borderEnd1,alignCenter, 20)) ;
	   
	   document.add(t1);
		document.close();
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
