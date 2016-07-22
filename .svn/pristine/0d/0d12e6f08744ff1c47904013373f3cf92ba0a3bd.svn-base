package com.pqsoft.util;

/**
 * 发票工具类
 */
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.pqsoft.skyeye.api.Dao;


public class InvoiceUtil {

	public static String getInvoices(List<Object> rt) throws Exception {
		return getInvoiceXml(parseToList(rt));
	}


	private static List<Invoice> parseToList(List<Object> rs) {
		List<Invoice> invoices = new ArrayList<Invoice>();
		Invoice invoice = null;
		for (int i = 0; i < rs.size() ; i++) {
			try{
				Map<String,Object> dtl = (Map<String,Object>)rs.get(i);
				invoice = new Invoice();
				invoice.setGhdw(checkStringNull(dtl,"GHDW"));
				invoice.setSfzhm(dtl.containsKey("SFZHM") && dtl.get("SFZHM") !=null ? dtl.get("SFZHM").toString() :"");
				invoice.setScqymc(dtl.containsKey("SCQYMC") && dtl.get("SCQYMC") !=null ? dtl.get("SCQYMC").toString():"");
				invoice.setCllx(checkStringNull(dtl ,"CLLX"));
				invoice.setCpxh(checkStringNull(dtl,"CPXH"));
				invoice.setCd(checkStringNull(dtl,"CD"));
				invoice.setHgzs(checkStringNull(dtl,"HGZS"));
				invoice.setJkzmsh(dtl.containsKey("JKZMSH") && dtl.get("JKZMSH") !=null ? dtl.get("JKZMSH").toString() :"");
				invoice.setSjdh(StringUtils.nullToString(dtl.get("SJDH")));
				invoice.setFdjhm(StringUtils.nullToString(dtl.get("FDJHM")));
				invoice.setClsbdh(checkStringNull(dtl,"CLSBDH"));
				invoice.setJshj(checkStringNull(dtl,"JSHJ"));
				invoice.setXhdwmc(dtl.containsKey("XHDWMC") && dtl.get("XHDWMC") != null ? dtl.get("XHDWMC").toString():"");
				invoice.setNsrsbh(dtl.containsKey("NSRSBH") && dtl.get("NSRSBH") != null ? dtl.get("NSRSBH").toString():"");
				invoice.setDh(checkStringNull(dtl,"DH"));
				invoice.setZh(checkStringNull(dtl,"ZH"));
				invoice.setDz(checkStringNull(dtl,"DZ"));
				invoice.setKhyh(checkStringNull(dtl,"KHYH"));
				invoice.setZzssl(checkStringNull(dtl,"ZZSSL"));
				invoice.setDw(StringUtils.nullToString(dtl.get("DW")));
				invoice.setXcrs(dtl.containsKey("XCRS") && dtl.get("XCRS") != null ? dtl.get("XCRS").toString():"");
			}catch(NullPointerException e){
				rs.remove(i);
				i--;
				continue;
			}
			invoices.add(invoice);
		} 
		return invoices;
	}
	
	public static String checkStringNull(Map dtl,String str) throws NullPointerException{
		Object temp = dtl.get(str); 
		if(temp == null || temp.toString().trim().equals("")){
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("SQL_", str.toUpperCase());
			m.put("SQL_1", "不能为空");
			m.put("ID", dtl.get("PROJECT_EQUIPMENT_ID").toString());
			Dao.update("VehicleInvoice.insertVehErrMsg",m);
			throw new NullPointerException();
		}
		return temp.toString().trim();
	}

	private static String getInvoiceXml(List<Invoice> invoices) {
		Document doc = DocumentHelper.createDocument();
		if(invoices != null){
			doc.setXMLEncoding("gb2312");
			Element fpmx = doc.addElement("fpmx");
			for(int i = 0 ;i < invoices.size() ; i++){
				Element fpmx_row = fpmx.addElement("fpmx_row");
				setFpmxRow(fpmx_row,invoices.get(i));// 添加票据详情
			}
			return doc.asXML();
		}else{
			return "0";
		}
		
	}

	private static void setFpmxRow(Element fpmxRow, Invoice invoice) {
		fpmxRow.addElement("ghdw").setText(nullToString(invoice.getGhdw()));
		fpmxRow.addElement("sfzhm").setText(nullToString(invoice.getSfzhm()));
		fpmxRow.addElement("scqymc").setText(nullToString(invoice.getScqymc()));
		fpmxRow.addElement("cllx").setText(nullToString(invoice.getCllx()));
		fpmxRow.addElement("cpxh").setText(nullToString(invoice.getCpxh()));
		fpmxRow.addElement("cd").setText(nullToString(invoice.getCd()));
		fpmxRow.addElement("hgzs").setText(nullToString(invoice.getHgzs()));
		fpmxRow.addElement("jkzmsh").setText(nullToString(invoice.getJkzmsh()));
		fpmxRow.addElement("sjdh").setText(nullToString(invoice.getSjdh()));
		fpmxRow.addElement("fdjhm").setText(nullToString(invoice.getFdjhm()));
		fpmxRow.addElement("clsbdh").setText(nullToString(invoice.getClsbdh()));
		fpmxRow.addElement("jshj").setText(nullToString(invoice.getJshj()));
		fpmxRow.addElement("xhdwmc").setText(nullToString(invoice.getXhdwmc()));
		fpmxRow.addElement("nsrsbh").setText(nullToString(invoice.getNsrsbh()));
		fpmxRow.addElement("dh").setText(nullToString(invoice.getDh()));
		fpmxRow.addElement("zh").setText(nullToString(invoice.getZh()));
		fpmxRow.addElement("dz" ).setText(nullToString(invoice.getDz()));
		fpmxRow.addElement("khyh" ).setText(nullToString(invoice.getKhyh()));
		fpmxRow.addElement("zzssl").setText(nullToString(invoice.getZzssl()));
		fpmxRow.addElement("dw").setText(nullToString(invoice.getDw()));
		fpmxRow.addElement("xcrs").setText(nullToString(invoice.getXcrs()));
		
	}
	
	public static String nullToString(String str){
		if(str == null || str.trim().equals("")){
			return "";
		}
		return str;
	}
	
	/*
	 * String fpmx;
	String fpmx_row;
	String ghdw;
	String sfzhm;
	String scqymc;
	String cllx;
	String cpxh;
	String cd;
	String hgzs;
	String jkzmsh;
	String sjdh;
	String fdjhm;
	String clsbdh;
	String jshj;
	String xhdwmc;
	String nsrsbh;
	String dh;
	String zh;
	String dz;
	String khyh;
	String zzssl;
	String dw;
	String xcrs;
	
	String invoice_id;
	String invoice_date;
	String ems;
	String ems_id;
	String ems_addr;
	String ems_per;
	 */
	public static List<Invoice> parseXml(String xml) throws DocumentException{
		List<Invoice> invoices = new ArrayList<Invoice>();
		Invoice invoice = null;
		Document doc = DocumentHelper.parseText(xml);
		Element fpmx = doc.getRootElement();
		List<Element> fpmx_rows  = fpmx.elements("jdcxsfpMxxx");
		for(Element e : fpmx_rows){
			invoice  = new Invoice();
			invoice.setClsbdh(nullToString(e.elementText("clsbdh").trim()));//车架号
			invoice.setInvoice_date(nullToString(e.elementText("kprq"))+" "+nullToString(e.elementText("kpsj")));//发票日期//TODO
			invoice.setZzsse(nullToString(e.elementText("zzsse")));
			invoice.setInvoice_id(nullToString(e.elementText("fphm")));//发票号//TODO
			invoices.add(invoice);
		}
		return invoices;
	}
	
	
	public static String  getJoinStr(){
		Map<String, Object> param = new HashMap<String,Object>();;
		param.put("SQL_TYPE", "发票_项目特性");
		param.put("SQL_KEYS", "CODE,FLAG,SHORTNAME,REMARK");
		param.put("SQL_CODE", "");
		param.put("SQL_FLAG", "");
		List<Object> list = Dao.selectList("invoice.selectRulerValues", param);
		String a = "TT";
		String b = "TTT";
		String join = "";
		for(Object str : list){
			Map<String,Object> m =(Map<String,Object>)str;
			if(m.containsKey("REMARK") && m.get("REMARK")!=null && "Date".equals(m.get("REMARK").toString().trim())){
				join +=" and "+a+"."+m.get("CODE").toString()+m.get("SHORTNAME").toString()+"TO_DATE("+b+"."+m.get("CODE").toString()+",'YYYY-MM-DD')";
			}else{
			    join +=" and "+a+"."+m.get("CODE").toString()+m.get("SHORTNAME").toString()+b+"."+m.get("CODE").toString();
			}
		}
		return join;
	}

	/**
	 * 
	 * @Title: getItemNameId 
	 * @Description: 根据商品名称和规格型号返回 名称编码。不存在会自动生成
	 * @author 程龙达
	 * @return String 
	 * @throws 
	 */
	public static String getItemNameId(String name,String prod_model){
		String item_code = "";
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("SQL_NAME", name);
		m.put("PROD_MODEL", prod_model);
		List<Map<String,Object>> l = Dao.selectList("invoiceHandle.selectItemIdByName", m);
		if(l.size()>0){
			item_code = ((Map<String,Object>)(l.get(0))).get("ITEM_CODE").toString();
		}
		
		if(item_code==null||item_code.equals("")){
			l = Dao.selectList("invoiceHandle.selectMaxItemId",m);
			item_code = ((Map<String,Object>)(l.get(0))).get("ITEM_CODE").toString();
			m.put("ITEM_CODE", item_code);
			Dao.insert("invoiceHandle.insertItemId",m);
		}
		return item_code;
	}
	
	//四舍五入校验
	public static boolean testAmt(String str){
		BigDecimal hanshui = BigDecimal.valueOf(Double.valueOf(str));
		double tax_rate =0.17;//后期可调整
		BigDecimal jine = hanshui.divide(BigDecimal.valueOf(1+tax_rate),2,BigDecimal.ROUND_HALF_UP);
		BigDecimal shuie = BigDecimal.valueOf((hanshui.doubleValue()*tax_rate)/(1+tax_rate)).divide(BigDecimal.valueOf(1),2, BigDecimal.ROUND_HALF_UP);
		double flag =  Double.valueOf(jine.add(shuie).subtract(hanshui).toString());
		if(flag == 0){
			return true;
		}
		return false;
	}
	
	//校验非空信息
	public static boolean isNotNull(Map<String,Object> mess){
		Boolean flag = true ;
		Set<String> keys = mess.keySet();
		Iterator<String> its = keys.iterator();
		while (its.hasNext()) {
			String key = (String) its.next();
			if(mess.containsKey(key) && mess.get(key)!=null && !"".equals(mess)){
				flag = true;
			}else{
				flag = false;
				break ;
			}
		}
		return flag ;
	}
	
}
