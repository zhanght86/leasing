package com.pqsoft.project.action;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.util.UTIL;
import com.pqsoft.util.PoiExcelUtil;

public class FundConfirmationLetter extends Reply {

	private Map<String, Object> m = new HashMap<String, Object>();
	private String path;

	public FundConfirmationLetter(Map<String, String> param) {
		Map mp = Dao.selectOne("project.get_business_money", param);
		double LEASE_TOPRIC = mp.get("LEASE_TOPRIC")==null||"".equals(mp.get("LEASE_TOPRIC"))?0:Double.parseDouble(mp.get("LEASE_TOPRIC")+"");
		double QZZJ = mp.get("QZZJ")==null||"".equals(mp.get("QZZJ"))?0:Double.parseDouble(mp.get("QZZJ")+"");
		double BZJ = mp.get("BZJ")==null||"".equals(mp.get("BZJ"))?0:Double.parseDouble(mp.get("BZJ")+"");
		double SXF = mp.get("SXF")==null||"".equals(mp.get("SXF"))?0:Double.parseDouble(mp.get("SXF")+"");
		double LGJK = mp.get("LGJK")==null||"".equals(mp.get("LGJK"))?0:Double.parseDouble(mp.get("LGJK")+"");
		double LOAN_JINE = mp.get("LOAN_JINE")==null||"".equals(mp.get("LOAN_JINE"))?0:Double.parseDouble(mp.get("LOAN_JINE")+"");
		double DB_BZJ = mp.get("DB_BZJ")==null||"".equals(mp.get("DB_BZJ"))?0:Double.parseDouble(mp.get("DB_BZJ")+"");
		double FIRST_MONEY_ALL = mp.get("FIRST_MONEY_ALL")==null||"".equals(mp.get("FIRST_MONEY_ALL"))?0:Double.parseDouble(mp.get("FIRST_MONEY_ALL")+"");
		
		//m.put("差额款项", (LEASE_TOPRIC-QZZJ-BZJ)+"");
		m.put("差额款项", LOAN_JINE+"");
		m.put("起租租金", (LEASE_TOPRIC-LOAN_JINE-DB_BZJ-LGJK)+"");
		m.put("保证金", (DB_BZJ)+"");
		m.put("项目款", (LEASE_TOPRIC)+"");
		m.put("项目编号", mp.get("PRO_CODE")+"");
		m.put("手续费", (SXF)+"");
		m.put("留购价款", (LGJK)+"");
		m.put("首期款合计", (FIRST_MONEY_ALL)+"");
		path = param.get("fileName");
	}

	@Override
	public void exec() {
		// 查询数据exportExcel
		//List list = Dao.selectList("BuyBack.exportExcel", m);
		SkyEye.getResponse().reset();
		OPCPackage pack = null;
		OutputStream os= null;
//		String newPath ="";
		try {
			os = SkyEye.getResponse().getOutputStream();
			SkyEye.getResponse().reset();// 清空输出流
			String filename = "资金结算确认函.xls" ;
			filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
			SkyEye.getResponse().setHeader("Content-disposition", "attachment; filename=" + filename);// 设定输出文件头
			SkyEye.getResponse().setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml");// 定义输出类型
//		    String uuid = UUID.randomUUID().toString();
//		    newPath = path.replaceAll("\\w+\\.\\w+$", uuid+".xls");
//		    FileUtils.copyFile(new File(path), new File(newPath));
		    PoiExcelUtil.excelTemplateExcle(UTIL.RES.getResource("classpath:file/fundConfirmationLetter/"+path).getInputStream(), os, m);
//		    pack = POIXMLDocument.openPackage(newPath); 
//            //获得XWPFDocument对象  
//            XWPFDocument doc = new XWPFDocument(pack); 
//            changeParagraphs(doc, m);
//            doc.write(os); 
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				SkyEye.getResponse().getOutputStream();
				os.flush(); 
	            os.close(); 
			} catch (IOException e) {}
		}
	}
	
	public static void changeParagraphs(XWPFDocument document,Map<String,String> label){
		List<XWPFParagraph> list = document.getParagraphs();
		// 基本内容替换
		for (int i = 0; i < list.size(); i++) {
			for (Entry<String, String> e : label.entrySet()) {
				if (list.get(i).getParagraphText().equals(e.getKey())) {
					List runs = list.get(i).getRuns();
					// 删除原来内容
					for (int j = runs.size() - 1; j >= 0; j--) {
						list.get(i).removeRun(j);
					}
					// 创建新内容
					XWPFRun paragraphRun = list.get(i).createRun();
					paragraphRun.setText(e.getValue());
					System.out.println(list.get(i).getParagraphText());
				}
			}
		}

//	Iterator<XWPFTable> it = document.getTablesIterator();  
//	//表格内容替换添加
//	   while(it.hasNext()){
//	    XWPFTable table = (XWPFTable)it.next();
//	    int rcount = table.getNumberOfRows();
//	    for(int i =0 ;i < rcount;i++){
//	     XWPFTableRow row = table.getRow(i);
//	     List cells =  row.getTableCells();
//	     for (XWPFTableCell cell : cells){
//	        for(Entry e : content.entrySet()){
//	         if (cell.getText().equals(e.getKey())){
//	   //删除原来内容
//	          cell.removeParagraph(0);
//	           //写入新内容
//	          cell.setText(e.getValue());
//	         }
//	        }
//	     }
//	    }
//	   }
	}
	
	
	
	
	
	
	
	@SuppressWarnings("unchecked")
	public static void checkParagraphs(XWPFDocument document,Map<String,String> map){
         List listRun;
         Map mapAttr = new HashMap();
         List listParagraphs = document.getParagraphs();
         for (int sa = 0; sa < listParagraphs.size(); sa++) {
			Set entrySet = map.entrySet();
             for (Entry e : map.entrySet()) {
            	 XWPFParagraph paragraph = (XWPFParagraph)listParagraphs.get(sa);
            	// System.out.println("____________________"+paragraph.getText());
                 if (paragraph.getText().indexOf(e.getKey()+"") != -1) {
                	 System.out.println("--------------------"+paragraph.getRuns());
                	 listRun = paragraph.getRuns();
                     for (int p = 0; p < listRun.size(); p++) {
                         if (listRun.get(p).toString().equals(e.getKey())) {
                             //得到占位符的文本格式
                             XWPFRun runOld = paragraph.getRuns().get(p);
                             mapAttr=getWordXWPFRunStyle(runOld); //封装该占位符文本样式到map
                             paragraph.removeRun(p);//移除占位符
                             //创建设置对应占位符的文本
                             XWPFRun runNew = paragraph.insertNewRun(p);
                             setWordXWPFRunStyle(runNew,mapAttr,e.getValue()+"",true);
                         }
                     }
                 }
             }
         }
     }
	@SuppressWarnings("unchecked")
	public static Map getWordXWPFRunStyle(XWPFRun runOld){
         Map mapAttr = new HashMap();
         mapAttr.put("Color", runOld.getColor());
         if(-1==runOld.getFontSize()){
             mapAttr.put("FontSize", 12);
         }else{
             mapAttr.put("FontSize", runOld.getFontSize());
         }
         mapAttr.put("Subscript", runOld.getSubscript());
         mapAttr.put("Underline", runOld.getUnderline());
         mapAttr.put("FontFamily",runOld.getFontFamily());
         return mapAttr;
     }
	  public static XWPFRun setWordXWPFRunStyle(XWPFRun runNew,Map mapAttr,String text,boolean flag){
          runNew.setColor((String) mapAttr.get("Color"));
          if("-1".equals(mapAttr.get("FontSize").toString())){//处理小四字号读取为-1的问题
              runNew.setFontSize(12);
          }else{
              runNew.setFontSize((Integer) mapAttr.get("FontSize"));
          }
          runNew.setBold(flag);
          runNew.setUnderline((UnderlinePatterns) mapAttr.get("Underline"));
          runNew.setText(text);
          runNew.setSubscript((VerticalAlign) mapAttr.get("Subscript"));
          runNew.setFontFamily((String) mapAttr.get("FontFamily"));
          return runNew;
      }

	/**
	 * <li>功能描述：时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return
	 *         long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date beginDate;
		java.util.Date endDate;
		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return 字符串 yyyy-mm-dd
	 */
	public static String getStringToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static String parseDate(String date) {
		return date.substring(0, 4) + "年" + date.substring(5, 7) + "月" + date.substring(8, 10) + "日";
	}
}
