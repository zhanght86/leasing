package com.pqsoft.overdue.action;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.core.io.Resource;
import com.lowagie.text.Document; 
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element; 
import com.lowagie.text.Font; 
import com.lowagie.text.PageSize; 
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase; 
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont; 
import com.lowagie.text.pdf.PdfPCell; 
import com.lowagie.text.pdf.PdfPTable; 
import com.lowagie.text.pdf.PdfWriter; 
import com.pqsoft.overdue.service.OverdueService;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.util.UTIL;
import com.pqsoft.util.CnUpperCaser;

 
public class PDFReportAction extends Action{ 
     
    private static Font headfont ;// 设置字体大小 
    private static Font keyfont;// 设置字体大小 
    private static Font textfont;// 设置字体大小 
	OverdueService service = new OverdueService();

 
     
    static{ 
        BaseFont bfChinese; 
        try { 
            bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED); 
            headfont = new Font(bfChinese, 16, Font.BOLD);// 设置字体大小 
            keyfont = new Font(bfChinese, 12, Font.BOLD);// 设置字体大小 
            textfont = new Font(bfChinese, 10, Font.NORMAL);// 设置字体大小 
        } catch (Exception e) {          
            e.printStackTrace(); 
        }  
    } 
     
     

    static int maxWidth = 520; 
     


     

      
   
	@aAuth(type = aAuth.aAuthType.LOGIN)
 	@aPermission(name = { "催收管理", "导出", "" })
 	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪") 
     public void generatePDFs(){ 
		 Map<String, Object> param = _getParameters();
		 System.out.println(param);
		 ByteArrayOutputStream baos =  new ByteArrayOutputStream();
		 HttpServletResponse response = SkyEye.getResponse();
		 
		 //根据PAY_ID获取数据
		 Map<String, Object>  eqlist = service.queryEqByOverdueID(param);

         //定义Cell边框粗细   顺序是：上下左右
 	 	 float[] borderStart = {0.5f,0,0.5f,0} ;
 	 	 float[] borderEnd = {0.5f,0,0.5f,0.5f} ;
 	
	 	//定义默认边距   顺序是：上下左右
	 	 float[] paddingDefault = {-2f,-2f,-2f,-2f};
	 	//定义默认位置    水平，垂直
	 	 float[] paddingDefault1 = {0f,0f,-2f,-2f};
	 	 float[] paddingDefault2 = {0f,0f,0f,0f};
	 	 
	 	 float[] borderStart4 = {0f,0.5f,0.5f,0.5f} ;
	 	 float[] borderStart5 = {0.5f,0,0.5f,0.5f} ;
	 	 
	 	 
	 	 
	 	 
	 	 
         try {
        	
          String fileName = "逾期催收导出"+ new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar
						.getInstance().getTime());
		  String strFileName=fileName+".pdf";
		  response.setContentType("application/pdf");
   	      response.setCharacterEncoding("UTF-8");
   	      response.setHeader("Pragma", "public");
   	      response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
   	      response.setDateHeader("Expires", 0);
   	      response.setHeader("Content-Disposition", "attachment; filename=" + new String( strFileName.getBytes("gb2312"), "ISO8859-1" )); 
        	 
        	 
          NumberFormat nfFSNum = new DecimalFormat("###,###,###,###.00");
          nfFSNum.setGroupingUsed(true);
          nfFSNum.setMaximumFractionDigits(2);
                // 页面设置
          Rectangle rectPageSize = new Rectangle(PageSize.A4); // 定义A4页面大小              
          Document document = new Document(rectPageSize, 50, 50,50, 50); // 其余4个参数，设置了页面的4个边距
          PdfWriter.getInstance(document, baos);
          // 打开文档
          document.open();
			 
             
             
             
             
			Resource resource = UTIL.RES.getResource("classpath:/file/overdue_pdf.xlsx");			
	        Workbook wb = WorkbookFactory.create(resource.getInputStream());
	        Sheet sheet = wb.getSheetAt(0);
            System.out.println("param:"+param);
            System.out.println("resource:"+resource);
            
    
        	/*****从页面传入的数值转换格式**********************/
            
            String cust_name="";   //客户
			if(eqlist.get("CUST_NAME")!=null&&!"".equals(eqlist.get("CUST_NAME"))){
				cust_name=eqlist.get("CUST_NAME").toString();
			}else{
				cust_name="张三a";  //固定
			}
			/*相关的日期************/
			Date now = new Date(); 
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
	
			String CREATE_DATE="";  //创建时间
			if(eqlist.get("CREATE_DATE")!=null&&!"".equals(eqlist.get("CREATE_DATE"))){
		        String[] arr = eqlist.get("CREATE_DATE").toString().split("-");	        
		        CREATE_DATE=arr[0]+"年"+arr[1]+"月"+arr[2]+"日";
		        System.out.println("CREATE_DATE:"+CREATE_DATE);
			}else{
				 String[] arr = df.format(now).split("-");	  //固定当前系统日期
				 CREATE_DATE=arr[0]+"年"+arr[1]+"月"+arr[2]+"日";
			}

			String PENALTY_DATE="";  //实际付款日期
			if(eqlist.get("PENALTY_DATE")!=null&&!"".equals(eqlist.get("PENALTY_DATE"))){
		        String[] arr = eqlist.get("PENALTY_DATE").toString().split("-");	        
		        PENALTY_DATE=arr[0]+"年"+arr[1]+"月"+arr[2]+"日";
			}else{
				PENALTY_DATE=0000+"年"+00+"月"+00+"日";
			}
			
			 String PAY_DATE="";   //应付计划最早那一月逾期日期
			  if(eqlist.get("PAY_DATE")!=null&&!"".equals(eqlist.get("PAY_DATE"))){
				  String[] arr=eqlist.get("PAY_DATE").toString().split("-");
				  PAY_DATE=arr[0]+"年"+arr[1]+"月"+arr[2]+"日";
			 }else{
				 PAY_DATE=0000+"年"+00+"月"+00+"日";
			 }
			 
			  String RENT_DATE="";  //应付日期：逾期日
			  if(eqlist.get("RENT_DATE")!=null&&!"".equals(eqlist.get("RENT_DATE"))){
				  String[] arr=eqlist.get("RENT_DATE").toString().split("-");
				  RENT_DATE=arr[0]+"年"+arr[1]+"月"+arr[2]+"日";
			 }else{
				 RENT_DATE=0000+"年"+00+"月"+00+"日";
			 }
			  
			  
			  
			  String PENALTY_DAY="";  //实际还款天数
	          if(eqlist.get("PENALTY_DAY")!=null&&!"".equals(eqlist.get("PENALTY_DAY"))){
	        	      PENALTY_DAY = eqlist.get("PENALTY_DAY").toString();	                  	  
	             }else{
	            	 PENALTY_DAY="0";
	           }
				
	          
			 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");		
			 Date date1=sdf.parse(eqlist.get("CREATE_DATE").toString());
			 Date date2=sdf.parse(eqlist.get("RENT_DATE").toString());
			 String Day=String.valueOf(daysBetween(date1,date2));
			 System.out.println("Day:"+Day);
		
			 String OveDay="自 "+ RENT_DATE+"(逾期日)至 "+CREATE_DATE+"(实际还款日天数)"+PENALTY_DAY+"天";
			
			 /*相关的金额************/
			 
			String TOPRIC_SUBFIRENT="";//融资额
			if(eqlist.get("TOPRIC_SUBFIRENT")!=null&&!"".equals(eqlist.get("TOPRIC_SUBFIRENT"))){
				TOPRIC_SUBFIRENT=String.valueOf(Double.valueOf(eqlist.get("TOPRIC_SUBFIRENT").toString()));
			}else{
				TOPRIC_SUBFIRENT="100000.00";
			}
			
			String RENT_RECE_ALL="";//逾期金额
			if(eqlist.get("RENT_RECE_ALL")!=null&&!"".equals(eqlist.get("RENT_RECE_ALL"))){
				RENT_RECE_ALL=eqlist.get("RENT_RECE_ALL").toString();
			}else{
				RENT_RECE_ALL="0.00";
			}
			
			String PENALTY_RECE_ALL="";//罚息金额
			if(eqlist.get("PENALTY_RECE_ALL")!=null&&!"".equals(eqlist.get("PENALTY_RECE_ALL"))){
				PENALTY_RECE_ALL=eqlist.get("PENALTY_RECE_ALL").toString();
			}else{
				PENALTY_RECE_ALL="0.00";
			}
			
			String RECE_ALL="";    //逾期金额+罚息金额
			if(eqlist.get("RECE_ALL")!=null&&!"".equals(eqlist.get("RECE_ALL"))){	
				RECE_ALL=eqlist.get("RECE_ALL").toString();
			}else{
				RECE_ALL="0.00";
			}
			
           
    	
			 
	
			
			/*****把一些相关的数据写入到xls文件的空白页面当中**********************/           
            
			String cp=new CnUpperCaser(TOPRIC_SUBFIRENT).getCnString();
			String sx=new CnUpperCaser(RECE_ALL).getCnString();
			
			String yuan="元";
			String bigFont="，大写：";
			String kongGeB="          ";
			String kongGeS="     ";
			
            setCellValues(sheet,1,2,cust_name,wb);
            setCellValues(sheet,3,1,TOPRIC_SUBFIRENT+yuan+bigFont+cp,wb);	//yuan+bigFont
            setCellValues(sheet,4,2,CREATE_DATE,wb);	
            setCellValues(sheet,6,1,RENT_RECE_ALL,wb);	
            setCellValues(sheet,7,1,PENALTY_RECE_ALL,wb);
            setCellValues(sheet,8,1,OveDay,wb);
            setCellValues(sheet,9,1,CREATE_DATE+getCellVal_1(sheet,9,2,wb)+" "+RECE_ALL+yuan+bigFont,wb);      
            setCellValues(sheet,10,0,sx,wb); //合大写           
            setCellValues(sheet,11,1,CREATE_DATE,wb); //合大写  
            setCellValues(sheet,15,1,CREATE_DATE,wb);	


            
            /*****把文件中的数据获取出来**********************/
            
            String fileText4 =" "; String fileText5 =" ";String fileText3 =" ";
            String fileText11 =" "; String fileText12 =" "; 
            String fileText9=""; String fileText10=""; String fileText4s="";

            for(int cols=0;cols<3;cols++){   //列数
            	
            	String value4=getCellVal_1(sheet,4,cols,wb);
            	String value5=getCellVal_1(sheet,5,cols,wb);
            	String value11=getCellVal_1(sheet,11,cols,wb);
            	String value12=getCellVal_1(sheet,12,cols,wb);
            	String value10=getCellVal_1(sheet,10,cols,wb);
            	
            	if(cols<2){
           		 String value9=getCellVal_1(sheet,9,cols,wb);
           		 fileText9=fileText9+value9;
           		 fileText4s=fileText4s+value4;
           	    } 
            	
   
                fileText4=getCellVal_1(sheet,4,2,wb);
                fileText5=fileText5+value5;
                fileText11=fileText11+value11;
                fileText12=fileText12+value12;
                fileText10=fileText10+value10;
             
            }
            
            fileText3 =kongGeB+getCellVal_1(sheet,3,0,wb)+getCellVal_1(sheet,3,1,wb)+"，"+fileText4s;
            
            
            System.out.println("sfds:"+fileText10+"\t"); 
       
            
            /****把数文件添加到pdf中******************/
          	int colNumber=6;
        	PdfPTable objTable = createTable(colNumber); 
        	objTable.setTotalWidth(450);

        	
        	objTable.addCell(createCell_1(getCellVal_1(sheet,0,0,wb),headfont,paddingDefault,Element.ALIGN_CENTER, borderStart,colNumber,false)); 
        	objTable.addCell(createCell_1("", textfont,paddingDefault,Element.ALIGN_LEFT, borderStart,colNumber,false)); 
        	objTable.addCell(createCell_1(getCellVal_1(sheet,1,0,wb)+getCellVal_1(sheet,1,1,wb), textfont,paddingDefault,Element.ALIGN_LEFT, borderStart,colNumber,false)); 
        	objTable.addCell(createCell_1(kongGeB+getCellVal_1(sheet,2,0,wb), textfont,paddingDefault,Element.ALIGN_LEFT, borderStart,colNumber,false)); 

        	objTable.addCell(createCell_1(fileText3, textfont,paddingDefault1,Element.ALIGN_LEFT, borderStart,colNumber,false));        	
        	objTable.addCell(createCell_1(fileText4, textfont,paddingDefault,Element.ALIGN_LEFT, borderStart,colNumber,false)); 
        	objTable.addCell(createCell_1(kongGeB+fileText5, textfont,paddingDefault,Element.ALIGN_LEFT, borderStart,colNumber,false));  		
 		    
        	objTable.addCell(createCell_1(getCellVal_1(sheet,6,0,wb), textfont,paddingDefault2, Element.ALIGN_CENTER,borderStart,1,true));
        	objTable.addCell(createCell_1(kongGeS+getCellVal_1(sheet,6,1,wb)+yuan,textfont,paddingDefault2, Element.ALIGN_LEFT,borderEnd,colNumber-1,true));
        	objTable.addCell(createCell_1(getCellVal_1(sheet,7,0,wb), textfont,paddingDefault2, Element.ALIGN_CENTER,borderStart,1,true)); 
        	objTable.addCell(createCell_1(kongGeS+getCellVal_1(sheet,7,1,wb)+yuan+"  ("+ getCellVal_1(sheet,7,2,wb)+")", textfont,paddingDefault2, Element.ALIGN_LEFT,borderEnd,colNumber-1,true)); 
        	objTable.addCell(createCell_1(getCellVal_1(sheet,8,0,wb), textfont, paddingDefault2,Element.ALIGN_CENTER,borderStart,1,true)); 
        	objTable.addCell(createCell_1(kongGeS+getCellVal_1(sheet,8,1,wb), textfont, paddingDefault2,Element.ALIGN_LEFT,borderEnd,colNumber-1,true)); 	
	       
        	objTable.addCell(createCell_1(kongGeB+fileText9, textfont, paddingDefault2, Element.ALIGN_LEFT, borderStart5,colNumber,true)); 
        	objTable.addCell(createCell_1(fileText10, textfont, paddingDefault2, Element.ALIGN_LEFT,borderStart4,colNumber,true)); 

        	objTable.addCell(createCell_1(fileText11, textfont,paddingDefault,Element.ALIGN_LEFT, borderStart,colNumber,false)); 
        	objTable.addCell(createCell_1(kongGeB+fileText12, textfont,paddingDefault,Element.ALIGN_LEFT, borderStart,colNumber,false)); 
        	
        	objTable.addCell(createCell_1(getCellVal_1(sheet,13,0,wb), textfont,paddingDefault,Element.ALIGN_LEFT, borderStart,colNumber,false)); 
        	objTable.addCell(createCell_1(getCellVal_1(sheet,14,0,wb), textfont,paddingDefault,Element.ALIGN_RIGHT, borderStart,colNumber,false)); 
        	objTable.addCell(createCell_1(kongGeB+getCellVal_1(sheet,15,1,wb), textfont,paddingDefault,Element.ALIGN_LEFT, borderStart,colNumber,false)); 
    		
		
 		    document.add(objTable);         
 	        document.close(); 
 	        
 	        
 	        ServletOutputStream o=response.getOutputStream();
	   	    baos.writeTo(o);
	   	    o.flush();
	   		o.close();
	   		


		} catch (Exception e) {
			e.printStackTrace();
		}
	
  
    }
     
	
	/***以下是批量*********************************/
	
	
	@aAuth(type = aAuth.aAuthType.LOGIN)
 	@aPermission(name = { "催收管理", "批量导出", "" })
 	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪") 
	public void exportLotsOfPdf(){
		
		 Map<String, Object> param = _getParameters();
		 System.out.println("param :"+param );
	     
		 HttpServletResponse response = SkyEye.getResponse();
		 
		 
		 
		 /*******求出要写入xml的数据***********************/
		 JSONObject json = JSONObject.fromObject(param.get("exportData"));
		 List<Map<String, Object>> list = json.getJSONArray("exportData");
		 
		 Map<String, Object>  eqlists = service.queryEqByOverdueID(param);

		 
		 
		 double RENT_RECE_ALL=0; double PENALTY_RECE_ALL=0; double TOPRIC_SUBFIRENT=0; 
		 double RECE_ALL=0;
		 String CUST_NAME=""; String PAY_START=""; String CREATE_DATE="";

		

		 for(int i=0;i<list.size();i++){
			 ByteArrayOutputStream[] baos =  new ByteArrayOutputStream[list.size()];
			 Map<String,Object> m = list.get(i);

		   if( m.get("CUST_NAME")!=""&& m.get("CUST_NAME")!=null){
			      CUST_NAME= m.get("CUST_NAME").toString();
		    }
		   
		   if( m.get("CREATE_DATE")!=null&&!"".equals( m.get("CREATE_DATE"))){
			   String[] arr = m.get("CREATE_DATE").toString().split("-");	        
			   CREATE_DATE=arr[0]+"年"+arr[1]+"月"+arr[2]+"日";
		   }
		   
		  // Map<String,Object> map=new HashMap<String,Object>();		   
		  // map.put("_time"+i+"", m.get("PAY_START"));
		  // Collection<Object> c = map.values();
		  // Object[] obj = c.toArray();
		  //  Arrays.sort(obj);
		   
		   if(m.get("PAY_START")!=null&&!"".equals(m.get("PAY_START"))){
				    String[] arr = m.get("PAY_START").toString().split("-");	        
				    PAY_START=arr[0]+"年"+arr[1]+"月"+arr[2]+"日";
		   }
		   try{
		
			   if(m.get("RENT_RECE_ALL")!=null&&!"".equals(m.get("RENT_RECE_ALL"))){
				   RENT_RECE_ALL= Double.parseDouble(m.get("RENT_RECE_ALL").toString().trim());	
			   }
	
			   if(m.get("PENALTY_RECE_ALL")!=null&&!"".equals(m.get("PENALTY_RECE_ALL"))){
				   PENALTY_RECE_ALL= Double.parseDouble(m.get("PENALTY_RECE_ALL").toString());
			   }

			   if(m.get("TOPRIC_SUBFIRENT")!=null&&!"".equals(m.get("TOPRIC_SUBFIRENT"))){
				   TOPRIC_SUBFIRENT= Double.parseDouble(m.get("TOPRIC_SUBFIRENT").toString());
			   }
	
			   if(m.get("RECE_ALL")!=null&&!"".equals(m.get("RECE_ALL"))){
				   RECE_ALL= Double.parseDouble(m.get("RECE_ALL").toString());
			   }
				 
			   
		   }catch(NumberFormatException r){
			   r.printStackTrace();
		   }
		   

		   
			String OveDay="自 "+CREATE_DATE+"（逾期日）至 "+PAY_START+"（实际还款日天数）";
		
			
		     //定义Cell边框粗细   顺序是：上下左右
	 	 	 float[] borderStart = {0.5f,0,0.5f,0} ;
	 	 	 float[] borderEnd = {0.5f,0,0.5f,0.5f} ;
	 	 	 float[] borderStart1 = {0.5f,0.5f,0.5f,0} ;
	 	 	 float[] borderEnd1 = {0.5f,0.5f,0.5f,0.5f} ;
	 	 	 float[] borderStart2 = {0,0,0.5f,0} ;
	 	 	 float[] borderEnd2 = {0,0,0,0} ;
	 	 	 float[] borderStart3 = {0,0,0,0} ;
	 		 int[] fontDefault = {-1,-1} ;
		 	//定义默认边距   顺序是：上下左右
		 	 float[] paddingDefault = {-2f,-2f,-2f,-2f};
		 	//定义默认位置    水平，垂直
		 	 int [] alignDefault = {-1,-1} ;//靠左
		 	 int [] alignCenter = {PdfPCell.ALIGN_CENTER,PdfPCell.ALIGN_CENTER} ;//居中
		 	 int [] alignRight = {PdfPCell.ALIGN_RIGHT,PdfPCell.ALIGN_CENTER} ;//靠右
		 	 int [] alignLift = {PdfPCell.ALIGN_LEFT,PdfPCell.ALIGN_CENTER} ;//靠左
		 	 float[] paddingDefault1 = {0f,0f,-2f,-2f};
		 	 float[] borderStart4 = {0f,0.5f,0.5f,0.5f} ;
		 	 float[] borderStart5 = {0.5f,0,0.5f,0.5f} ;
		 	 
		 
		 	 
		 	 
		 	 try {
		 		  String[] fileName = new String[list.size()];
		 			
		          fileName[i] = "逾期催收导出"+ new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar
		 						.getInstance().getTime());		  		 		  
		 		  response.setContentType("application/pdf");
		    	  response.setCharacterEncoding("UTF-8");
		    	  response.setHeader("Pragma", "public");
		    	  response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		    	  response.setDateHeader("Expires", 0);
		    	  response.setHeader("Content-Disposition", "attachment; filename=" + new String( (fileName[i]+".pdf").getBytes("gb2312"), "ISO8859-1" )); 
		         	 
		         	 
		           NumberFormat nfFSNum = new DecimalFormat("###,###,###,###.00");
		           nfFSNum.setGroupingUsed(true);
		           nfFSNum.setMaximumFractionDigits(2);
		                 // 页面设置
		           Rectangle[] rectPageSize = new Rectangle[list.size()];
		           rectPageSize[i]=new Rectangle(PageSize.A4); // 定义A4页面大小         (PageSize.A4)   
		           Document[] document = new Document[list.size()];
		           document[i] = new Document(rectPageSize[i], 50, 50,50, 50); // 其余4个参数，设置了页面的4个边距
		           PdfWriter[] pdf=new PdfWriter[list.size()];
		           pdf[i].getInstance(document[i], baos[i]);
		           // 打开文档
		           document[i].open();
		           
		           
		           Resource resource = UTIL.RES.getResource("classpath:/file/overdue_pdf.xlsx");	
				   Workbook wb= WorkbookFactory.create(resource.getInputStream());
			       Sheet sheet = wb.getSheetAt(0);
                  System.out.println("............................resource:"+resource);
		 			 
		           /*****把一些相关的数据写入到xls文件的空白页面当中**********************/           
					String cp=new CnUpperCaser(String.valueOf(TOPRIC_SUBFIRENT)).getCnString();		
					String sx=new CnUpperCaser(String.valueOf(RECE_ALL)).getCnString();
					
					String yuan="元";
					String bigFont="，大写：";
					String kongGeB="          ";
					String kongGeS="     ";
					
		            setCellValues(sheet,1,2,CUST_NAME,wb);
		            setCellValues(sheet,3,1,TOPRIC_SUBFIRENT+yuan+bigFont+cp,wb);	//yuan+bigFont
		            setCellValues(sheet,4,2,CREATE_DATE,wb);	
		            setCellValues(sheet,6,1,String.valueOf(RENT_RECE_ALL),wb);	
		            setCellValues(sheet,7,1,String.valueOf(PENALTY_RECE_ALL),wb);
		            setCellValues(sheet,8,1,OveDay,wb);
		            setCellValues(sheet,9,1,CREATE_DATE+getCellVal_1(sheet,9,2,wb)+" "+RECE_ALL+yuan+bigFont,wb);      
		            setCellValues(sheet,10,0,sx,wb); //合大写           
		            setCellValues(sheet,15,1,CREATE_DATE,wb);
		            
		            /*****把文件中的数据获取出来**********************/
		            
		            String fileText4 =" "; String fileText5 =" ";String fileText3 =" ";
		            String fileText11 =" "; String fileText12 =" "; 
		            String fileText9=""; String fileText10=""; String fileText4s="";

		            for(int cols=0;cols<3;cols++){   //列数
		            	
		            	String value4=getCellVal_1(sheet,4,cols,wb);
		            	String value5=getCellVal_1(sheet,5,cols,wb);
		            	String value11=getCellVal_1(sheet,11,cols,wb);
		            	String value12=getCellVal_1(sheet,12,cols,wb);
		            	String value10=getCellVal_1(sheet,10,cols,wb);
		            	
		            	if(cols<2){
		           		 String value9=getCellVal_1(sheet,9,cols,wb);
		           		 fileText9=fileText9+value9;
		           		 fileText4s=fileText4s+value4;
		           	    } 
		            	
		   
		                fileText4=getCellVal_1(sheet,4,2,wb);
		                fileText5=fileText5+value5;
		                fileText11=fileText11+value11;
		                fileText12=fileText12+value12;
		                fileText10=fileText10+value10;
		             
		            }
		            
		            fileText3 =kongGeB+getCellVal_1(sheet,3,0,wb)+getCellVal_1(sheet,3,1,wb)+"，"+fileText4s;
		            
		            
		            
		            /*************写入pdf***********************/
		            PdfPTable objTable = createTable(11); 
		            objTable.setTotalWidth(450);
		            
		            objTable.addCell(createCell_1(getCellVal_1(sheet,0,0,wb),headfont,paddingDefault,Element.ALIGN_CENTER, borderStart,11,false)); 
		        	objTable.addCell(createCell_1("", textfont,paddingDefault,Element.ALIGN_LEFT, borderStart,11,false)); 
		        	objTable.addCell(createCell_1(getCellVal_1(sheet,1,0,wb)+getCellVal_1(sheet,1,1,wb), textfont,paddingDefault,Element.ALIGN_LEFT, borderStart,11,false)); 
		        	objTable.addCell(createCell_1(kongGeB+getCellVal_1(sheet,2,0,wb), textfont,paddingDefault,Element.ALIGN_LEFT, borderStart,11,false)); 
		        	objTable.addCell(createCell_1(fileText3, textfont,paddingDefault1,Element.ALIGN_LEFT, borderStart,11,false));        	
		        	//objTable.addCell(createCell_1(fileText4, textfont,paddingDefault,Element.ALIGN_LEFT, borderStart,11,false)); 
		        	objTable.addCell(createCell_1(kongGeB+fileText5, textfont,paddingDefault,Element.ALIGN_LEFT, borderStart,11,false));  		
		 		    
		        
			       
		        	objTable.addCell(createCell_1(fileText11, textfont,paddingDefault,Element.ALIGN_LEFT, borderStart,11,false)); 
		        	objTable.addCell(createCell_1(kongGeB+fileText12, textfont,paddingDefault,Element.ALIGN_LEFT, borderStart,11,false)); 
		        	
		        	objTable.addCell(createCell_1(getCellVal_1(sheet,13,0,wb), textfont,paddingDefault,Element.ALIGN_LEFT, borderStart,11,false)); 
		        	objTable.addCell(createCell_1(getCellVal_1(sheet,14,0,wb), textfont,paddingDefault,Element.ALIGN_RIGHT, borderStart,11,false)); 
		        	objTable.addCell(createCell_1(kongGeB+getCellVal_1(sheet,15,1,wb), textfont,paddingDefault,Element.ALIGN_LEFT, borderStart,11,false)); 
		    		
		           
		           
		           
		           document[i].add(objTable);         
		           document[i].close(); 
		 	        
		           ServletOutputStream[] o=new  ServletOutputStream[list.size()] ;
		 	       o[i]=response.getOutputStream();
			   	   baos[i].writeTo(o[i]);
			   	   o[i].flush();
			   	   o[i].close();
		   

				} catch (Exception e) {
						e.printStackTrace();
				}
		 }
		 
	}
	



	
    /*****从xls中获取查询出来单元格中的值*********************/
	public  String getCellVal_1(Sheet sheet, int iRowStr, int iColStr,Workbook wb){
    	Row objRow = sheet.getRow((short)iRowStr);
        Cell objCell = objRow.getCell((short)iColStr);
    	System.out.println("-----"+objCell.getStringCellValue()+"------");
		return  objCell.getStringCellValue();
	
    }
     
	 /*****向xls中单元格写入数值********************/
	public void setCellValues(Sheet sheet, int iRowStr, int iColStr,String val,Workbook wb){	    	
    	 Row objRow = sheet.getRow((short)iRowStr);
    	 Cell objCell = objRow.getCell((short)iColStr); 
    	 objRow.createCell(1).setCellValue(val);
    	 System.out.println("........"+objCell+"........");
   }
	
	 /*****批量向xls中单元格写入数值********************/
	public void setCellValue_2(int length,int i,Sheet sheet, int iRowStr, int iColStr,String val,Workbook wb){	    	
		 Row[] objRow =new Row[length];
		 objRow[i] = sheet.getRow((short)iRowStr);
		 Cell[] objCell=new Cell[length]; 
    	 objCell[i] = objRow[i].getCell((short)iColStr); 
    	 objRow[i].createCell(1).setCellValue(val);
    	 System.out.println("........"+objCell+"........");
   }
	
	
	
	
	@Override
	public Reply execute() {
		// TODO Auto-generated method stub
		return null;
	} 
	

    


    


    
    
    //可以通过boderFlag隐藏表格的边框
   public static PdfPCell createCell_1(String value,com.lowagie.text.Font font,float[] paddingType,int align,float[] borderType,int colspan,boolean boderFlag)throws DocumentException{ 
   	Font FontStyleDe=null;
   	Phrase objPhase = new Phrase(value, FontStyleDe);
   	Paragraph paragraph = new Paragraph(value, FontStyleDe);
   	paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
   	paragraph.add(objPhase);
   	PdfPCell objCell = new PdfPCell(paragraph);
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
     
   	 objCell.setVerticalAlignment(Element.ALIGN_JUSTIFIED); 
   	 objCell.setHorizontalAlignment(align);   
   	 objCell.setPhrase(new Phrase(value,font));
   	
   	 objCell.setUseAscender(true);
   	 objCell.setUseDescender(true);
   	 objCell.setColspan(colspan); 
   	 objCell.setPhrase(new Phrase(value,font)); 
   	 if(boderFlag==true){
        	objCell.setPadding(3.0f); 
        	objCell.setPaddingTop(6.0f);
           	objCell.setPaddingBottom(6.0f);
     }else if(boderFlag==false){ 
        	objCell.setBorder(0); 
        	objCell.setPaddingTop(7.0f); 
        	objCell.setPaddingBottom(7.0f); 
        	
     } 
       return objCell; 
   } 
   
   
   
   
    //创建的列数
    public static PdfPTable createTable(int colNumber){ 
       PdfPTable table = new PdfPTable(colNumber); 
       try{ 
           table.setTotalWidth(maxWidth); 
           table.setLockedWidth(true); 
           table.setHorizontalAlignment(Element.ALIGN_CENTER);      
           table.getDefaultCell().setBorder(1); 
       }catch(Exception e){ 
           e.printStackTrace(); 
       } 
       return table; 
    } 
    
    
    /**  
     * 计算两个日期之间相差的天数  
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException  
     */  
  public static int daysBetween(Date smdate,Date bdate)throws ParseException{
	  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
	  smdate=sdf.parse(sdf.format(smdate));
	  bdate=sdf.parse(sdf.format(bdate));
	  Calendar cal = Calendar.getInstance(); 
	  cal.setTime(smdate);
	  long time1 = cal.getTimeInMillis();                 
      cal.setTime(bdate);    
      long time2 = cal.getTimeInMillis();         
      long between_days=(time2-time1)/(1000*3600*24);  
      
	  return Integer.parseInt(String.valueOf(between_days));
  }  
    
    
  
  
  
  
  
  
} 

