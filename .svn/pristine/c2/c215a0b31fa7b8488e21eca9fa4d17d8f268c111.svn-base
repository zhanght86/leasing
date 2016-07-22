package com.pqsoft.fusionChart.service;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.activemq.util.ByteArrayInputStream;
import org.apache.velocity.VelocityContext;
import org.springframework.util.FileCopyUtils;

import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.SkyEye;


/**
 * <p>
 * Title: 融资租赁信息系统平台 报表中心
 * </p>
 * <p>
 * Description: 报表中心-项目分析service；
 * </p>
 * <p>
 * Company: 平强软件
 * </p>
 * @author 吴剑东  wujiandongit@163.com 
 * @version 1.0
 */
public class ReportCreditService {

	/** Velocity上下文 */
	private VelocityContext context = new VelocityContext();
	
	/**
	 * 根据模版-类型,查询数据发布版本
	 * @author 吴剑东 2013-5-20
	 */
	public List<Map<String,Object>> selectDataRelByModCard(Map<String,Object> map){
		return Dao.selectList("reportProject.selectDataRelByModCard",map);
	}

	public void doStringXmlExp(String xmlStr){
		try {
			InputStream bfis = new ByteArrayInputStream(xmlStr.getBytes("GBK"));
			
		    byte[] b = FileCopyUtils.copyToByteArray(bfis);
			
		    int length =  b.length;
//		    ReportCredit
			SkyEye.getResponse().setCharacterEncoding("UTF-8");
			SkyEye.getResponse().addHeader("Content-Disposition", "attachment;filename=" + new String("chart.xml".getBytes("GB2312"), "ISO-8859-1"));
			SkyEye.getResponse().setContentType("application/octet-stream");
		    SkyEye.getResponse().addHeader("Content-Length", ""+length);
		    OutputStream toClient  =   new  BufferedOutputStream(SkyEye.getResponse().getOutputStream());
		    toClient.write(b);
	        //关闭流
	        toClient.close();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * 根据类型,生成字符串xml数据
	 * @author 吴剑东 2013-12-30
	 */
	@SuppressWarnings("unchecked")
	public String getChangjiaXml(Map<String, Object> map) {
		try {
			//获取月份
			List<Map<String,Object>> monthList = Dao.selectList("reportCredit.selectMonthsByType",map);
			context.put("monthList", monthList);
			//获取最新数据
			List<Map<String,Object>> dataList = Dao.selectList("reportCredit.selectDatasByType",map);
			List STGF_List = new ArrayList(); // 山推股份
			List STCT_List = new ArrayList(); // 山推楚天
			List STHJ_List = new ArrayList(); // 山推合计
			List STJX_List = new ArrayList(); // 山推机械
			List SZJJ_List = new ArrayList(); // 山重建机
			List JJBK_List = new ArrayList(); // 建机板块
			List SYC_List = new ArrayList(); // 商用车
			List SHCP_List = new ArrayList(); // 社会产品
			List LDCC_List = new ArrayList(); // 林德叉车
			List HJ_List = new ArrayList(); // 合计
			for (int i = 0; i < dataList.size(); i++) {
				// 遍历放入明细集合中
				Map<String, Object> m = (Map<String, Object>) dataList.get(i);
				STGF_List.add(m.get("COLUMN1"));
				STCT_List.add(m.get("COLUMN2"));
				STHJ_List.add(m.get("COLUMN3"));
				STJX_List.add(m.get("COLUMN4"));
				SZJJ_List.add(m.get("COLUMN5"));
				JJBK_List.add(m.get("COLUMN6"));
				SYC_List.add(m.get("COLUMN7"));
				SHCP_List.add(m.get("COLUMN8"));
				LDCC_List.add(m.get("COLUMN9"));
				HJ_List.add(m.get("COLUMN10"));
			}
			
			context.put("STGF_List", STGF_List);
			context.put("STCT_List", STCT_List);
			context.put("STHJ_List", STHJ_List);
			context.put("STJX_List", STJX_List);
			context.put("SZJJ_List", SZJJ_List);
			context.put("JJBK_List", JJBK_List);
			context.put("SYC_List", SYC_List);
			context.put("SHCP_List", SHCP_List);
			context.put("LDCC_List", LDCC_List);
			context.put("HJ_List", HJ_List);
			// 生成字符串类型的xml格式数据
			return VM.html("analysis/biReport/xml/HYMonthProceeds.vm", context).toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	

	/**
	 * 根据类型,生成字符串xml数据
	 * @author 吴剑东 2013-12-30
	 */
	@SuppressWarnings("unchecked")
	public String getBankuanXml(Map<String, Object> map) {
		try {
			//获取最新数据
			List<Map<String,Object>> dataList = Dao.selectList("reportCredit.selectDatasByType",map);
			List list = new ArrayList(); // 
			for (int i = 0; i < dataList.size(); i++) {
				Map<String, Object> m = new HashMap<String, Object>();
				// 遍历放入明细集合中
				Map<String, Object> data = (Map<String, Object>) dataList.get(i);
				List yearList = new ArrayList(); // 
				for(int j = 1; j <= 12; j++){
					yearList.add(data.get("COLUMN"+j)+"");
				}
				m.put("FLAG",data.get("DATA_TM"));
				m.put("yearList", yearList);
				list.add(m);
			}
			
			context.put("list", list);
			context.put("NAME", map.get("NAME"));
			// 生成字符串类型的xml格式数据
			return VM.html("analysis/biReport/xml/yearBankuanOverdue.vm", context).toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 根据类型,生成字符串xml数据
	 * @author 吴剑东 2013-12-30
	 */
	@SuppressWarnings("unchecked")
	public String getChanPinXml(Map<String, Object> map) {
		try {
			//获取月份
			List<Map<String,Object>> monthList = Dao.selectList("reportCredit.selectMonthsByType",map);
			context.put("monthList", monthList);
			//获取最新数据
			List<Map<String,Object>> dataList = Dao.selectList("reportCredit.selectDatasByType",map);
			List STGF_List = new ArrayList(); // 
			List STCT_List = new ArrayList(); // 
			List STHJ_List = new ArrayList(); // 
			List STJX_List = new ArrayList(); // 
			List SZJJ_List = new ArrayList(); // 
			List JJBK_List = new ArrayList(); // 
			List SYC_List = new ArrayList(); // 
			List SHCP_List = new ArrayList(); // 
			List LDCC_List = new ArrayList(); // 
			List HJ_List = new ArrayList(); // 
			for (int i = 0; i < dataList.size(); i++) {
				// 遍历放入明细集合中
				Map<String, Object> m = (Map<String, Object>) dataList.get(i);
				STGF_List.add(m.get("COLUMN1"));
				STCT_List.add(m.get("COLUMN2"));
				STHJ_List.add(m.get("COLUMN3"));
				STJX_List.add(m.get("COLUMN4"));
				SZJJ_List.add(m.get("COLUMN5"));
				JJBK_List.add(m.get("COLUMN6"));
				SYC_List.add(m.get("COLUMN7"));
				SHCP_List.add(m.get("COLUMN8"));
				LDCC_List.add(m.get("COLUMN9"));
				HJ_List.add(m.get("COLUMN10"));
			}
			
			context.put("STGF_List", STGF_List);
			context.put("STCT_List", STCT_List);
			context.put("STHJ_List", STHJ_List);
			context.put("STJX_List", STJX_List);
			context.put("SZJJ_List", SZJJ_List);
			context.put("JJBK_List", JJBK_List);
			context.put("SYC_List", SYC_List);
			context.put("SHCP_List", SHCP_List);
			context.put("LDCC_List", LDCC_List);
			context.put("HJ_List", HJ_List);
			// 生成字符串类型的xml格式数据
			return VM.html("analysis/biReport/xml/chanpinMonthProceeds.vm", context).toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
	 * 根据类型,生成字符串xml数据
	 * @author 吴剑东 2013-12-30
	 */
	@SuppressWarnings("unchecked")
	public String getQizuXml(Map<String, Object> map) {
		try {
			//获取月份
			List<Map<String,Object>> monthList = Dao.selectList("reportCredit.selectMonthsByType",map);
			context.put("monthList", monthList);
			//获取最新数据
			List<Map<String,Object>> dataList = Dao.selectList("reportCredit.selectDatasByType",map);
			List STGF_List = new ArrayList(); // 
			List STCT_List = new ArrayList(); // 
			List STHJ_List = new ArrayList(); // 
			List STJX_List = new ArrayList(); // 
			List SZJJ_List = new ArrayList(); // 
			List JJBK_List = new ArrayList(); // 
			for (int i = 0; i < dataList.size(); i++) {
				// 遍历放入明细集合中
				Map<String, Object> m = (Map<String, Object>) dataList.get(i);
				STGF_List.add(m.get("COLUMN1"));
				STCT_List.add(m.get("COLUMN2"));
				STHJ_List.add(m.get("COLUMN3"));
				STJX_List.add(m.get("COLUMN4"));
				SZJJ_List.add(m.get("COLUMN5"));
				JJBK_List.add(m.get("COLUMN6"));
			}
			
			context.put("STGF_List", STGF_List);
			context.put("STCT_List", STCT_List);
			context.put("STHJ_List", STHJ_List);
			context.put("STJX_List", STJX_List);
			context.put("SZJJ_List", SZJJ_List);
			context.put("JJBK_List", JJBK_List);
			// 生成字符串类型的xml格式数据
			return VM.html("analysis/biReport/xml/qizuMonthOverdue.vm", context).toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	

	/**
	 * 根据类型,生成字符串xml数据
	 * @author 吴剑东 2013-12-30
	 */
	@SuppressWarnings("unchecked")
	public String getDunweiTailiangXml(Map<String, Object> map) {
		try {
			//获取月份
			List<Map<String,Object>> monthList = Dao.selectList("reportCredit.selectMonthsByType",map);
			context.put("monthList", monthList);
			//获取最新数据
			List<Map<String,Object>> dataList = Dao.selectList("reportCredit.selectDatasByType",map);
			List STGF_List = new ArrayList(); // 
			List STCT_List = new ArrayList(); // 
			List STHJ_List = new ArrayList(); // 
			List STJX_List = new ArrayList(); // 
			List SZJJ_List = new ArrayList(); // 
			for (int i = 0; i < dataList.size(); i++) {
				// 遍历放入明细集合中
				Map<String, Object> m = (Map<String, Object>) dataList.get(i);
				STGF_List.add(m.get("COLUMN1"));
				STCT_List.add(m.get("COLUMN2"));
				STHJ_List.add(m.get("COLUMN3"));
				STJX_List.add(m.get("COLUMN4"));
				SZJJ_List.add(m.get("COLUMN5"));
			}
			
			context.put("STGF_List", STGF_List);
			context.put("STCT_List", STCT_List);
			context.put("STHJ_List", STHJ_List);
			context.put("STJX_List", STJX_List);
			context.put("SZJJ_List", SZJJ_List);
			// 生成字符串类型的xml格式数据
			return VM.html("analysis/biReport/xml/dunweiTailiangMonthOverdue.vm", context).toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
	 * 根据类型,生成字符串xml数据
	 * @author 吴剑东 2013-12-30
	 */
	@SuppressWarnings("unchecked")
	public String getJianjiXml(Map<String, Object> map) {
		try {
			//获取月份
			List<Map<String,Object>> monthList = Dao.selectList("reportCredit.selectMonthsByType",map);
			context.put("monthList", monthList);
			//获取最新数据
			List<Map<String,Object>> dataList = Dao.selectList("reportCredit.selectDatasByType",map);
			List STGF_List = new ArrayList(); // 
			List STCT_List = new ArrayList(); // 
			List STHJ_List = new ArrayList(); // 
			List STJX_List = new ArrayList(); // 
			List SZJJ_List = new ArrayList(); // 
			for (int i = 0; i < dataList.size(); i++) {
				// 遍历放入明细集合中
				Map<String, Object> m = (Map<String, Object>) dataList.get(i);
				STGF_List.add(m.get("COLUMN1"));
				STCT_List.add(m.get("COLUMN2"));
				STHJ_List.add(m.get("COLUMN3"));
				STJX_List.add(m.get("COLUMN4"));
				SZJJ_List.add(m.get("COLUMN5"));
			}
			
			context.put("STGF_List", STGF_List);
			context.put("STCT_List", STCT_List);
			context.put("STHJ_List", STHJ_List);
			context.put("STJX_List", STJX_List);
			context.put("SZJJ_List", SZJJ_List);
			context.put("caption", map.get("caption"));
			// 生成字符串类型的xml格式数据
			return VM.html("analysis/biReport/xml/jianjiMonthOverdue.vm", context).toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	

	/**
	 * 根据类型,生成字符串xml数据
	 * @author 吴剑东 2013-12-30
	 */
	@SuppressWarnings("unchecked")
	public String getJianjiZujinXml(Map<String, Object> map) {
		try {
			//获取月份
			List<Map<String,Object>> monthList = Dao.selectList("reportCredit.selectMonthsByType",map);
			context.put("monthList", monthList);
			//获取最新数据
			List<Map<String,Object>> dataList = Dao.selectList("reportCredit.selectDatasByType",map);
			List STGF_List = new ArrayList(); // 
			List STCT_List = new ArrayList(); // 
			List STHJ_List = new ArrayList(); // 
			List STJX_List = new ArrayList(); // 
			List SZJJ_List = new ArrayList(); // 
			List JJBK_List = new ArrayList(); // 
			for (int i = 0; i < dataList.size(); i++) {
				// 遍历放入明细集合中
				Map<String, Object> m = (Map<String, Object>) dataList.get(i);
				STGF_List.add(m.get("COLUMN1"));
				STCT_List.add(m.get("COLUMN2"));
				STHJ_List.add(m.get("COLUMN3"));
				STJX_List.add(m.get("COLUMN4"));
				SZJJ_List.add(m.get("COLUMN5"));
				JJBK_List.add(m.get("COLUMN6"));
			}
			
			context.put("STGF_List", STGF_List);
			context.put("STCT_List", STCT_List);
			context.put("STHJ_List", STHJ_List);
			context.put("STJX_List", STJX_List);
			context.put("SZJJ_List", SZJJ_List);
			context.put("JJBK_List", JJBK_List);
			// 生成字符串类型的xml格式数据
			return VM.html("analysis/biReport/xml/jianjiZujinMonthOverdue.vm", context).toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public void updateByTypeDate(Map<String, Object> map) {
		// 删除历史数据
		deleteByTypeDate(map);
		
		// 插入最新数据
		Dao.insert("reportCredit.insert101ByTypeDate",map);
	}
	
	
	public void deleteByTypeDate(Map<String, Object> map) {
		Dao.delete("reportCredit.deleteByTypeDate",map);
	}

	public Page goReportData(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList("reportCredit.goReportData",param));
		page.addDate(array, 0);
		return page;
	}

	public int doDeleteData(Map<String,Object> m) {
		return Dao.delete("reportCredit.deleteData", m);
	}
	public int saveData(Map<String,Object> param){
		return Dao.insert("reportCredit.saveData",param);
	}
	public int updateData(Map<String,Object> param){
		return Dao.update("reportCredit.updateData",param);
	}
}
