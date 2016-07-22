package com.pqsoft.adjustRate.service;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.util.Util;

public class AdjustRateNoticeService {
	/********租金调整通知书***@auth: king 2014年9月23日 *************************/
	
	private String NAMESPACE = "adjustRate.";
	private AdjustRateNoticePdf pdfUtil=new AdjustRateNoticePdf();
	
	/**
	 * 查询导出的调息变动基本信息
	 * 
	 * @param map
	 * @return
	 * @author King 2014年9月25日
	 */
	public List<Map<String, Object>> queryAdjustRatePdfInfo(
			Map<String, Object> map) {
		return Dao.selectList(NAMESPACE + "queryAdjustRatePdfInfo", map);
	}

	/**
	 * 查询基准利率信息
	 * 
	 * @param map
	 * @author King 2014年9月25日
	 */
	public void queryRateInfo(Map<String, Object> map) {
		List<Map<String, Object>> rateList = Dao.selectList(NAMESPACE
				+ "queryRateInfo");
		String new_rate = null;
		String old_rate = null;
		String LEASE_TERM = map.get("LEASE_TERM").toString();
		String LEASE_PERIOD = map.get("LEASE_PERIOD").toString();
		int term_sum = Integer.parseInt(LEASE_TERM)
				* Integer.parseInt(LEASE_PERIOD);
		String rate = null;
		String rate_term = null;
		for (int i = 0; i < rateList.size(); i++) {
			Map<String, Object> map2 = rateList.get(i);
			if (term_sum > 60) {
				rate = map2.get("OVER_FIVE_YEARS").toString();
				rate_term = "5-10";
			} else if (term_sum > 36 && term_sum <= 60) {
				rate = map2.get("THREE_FIVE_YEARS").toString();
				rate_term = "3-5";
			} else if (term_sum > 12 && term_sum <= 36) {
				rate = map2.get("ONE_THREE_YEARS").toString();
				rate_term = "1-3";
			} else if (term_sum > 6 && term_sum <= 12) {
				rate = map2.get("ONE_YEAR").toString();
				rate_term = "0.5-1";
			} else {
				rate = map2.get("SIX_MONTHS").toString();
				rate_term = "0-0.5";
			}

			if (i == 0)
				new_rate = rate;
			else
				old_rate = rate;
		}
		map.put("OLD_BASE_RATE", old_rate);
		map.put("NEW_BASE_RATE", new_rate);
		map.put("RATE_TERM", rate_term);
		map.put("RATE_CHA",
				Util.formatDouble2(Double.parseDouble(new_rate) - Double.parseDouble(old_rate)));
	}

	/**
	 * 查询调息前后租金明细
	 * 
	 * @param map
	 * @author King 2014年9月25日
	 */
	public List<Map<String,Object>> queryAdjustPayDetailList(Map<String,Object> map){
		List<Map<String,Object>> list=Dao.selectList(NAMESPACE+"queryAdjustPayDetailList", map);
		double old_zujin_sum=0;
		double new_zujin_sum=0;
		for (Map<String, Object> map2 : list) {
			old_zujin_sum+=Double.parseDouble(map2.get("OLD_ZUJIN")+"");
			new_zujin_sum+=Double.parseDouble(map2.get("NEW_ZUJIN")+"");
		}
		map.put("OLD_ZUJIN", old_zujin_sum);
		map.put("NEW_ZUJIN", new_zujin_sum);
		return list;
	}
	
	/**
	 * 导出pdf
	 * @param map
	 * @param list
	 * @param response
	 * @author King 2014年9月25日
	 */
	public String expPdf(Map<String,Object> map,List<Map<String,Object>> list){
		String tempPath = SkyEye.getConfig("file.path.temp");

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// PDF名字的定义
		String pdfName = map.get("PAYLIST_CODE")+"租金调整通知书";
		String strFileName = map.get("DEPEND_TIME")+"-"+map.get("CUST_NAME").toString()+"-"+pdfName + ".pdf";
		String filepath = tempPath + File.separator + strFileName;
		map.put("filePath", filepath);
//		String file=null;
		try { 
			pdfUtil.createNoticePdf(map, list, baos);
		//删除临时文件
			File tempFile=new File(tempPath + File.separator + strFileName);
			FileCopyUtils.copy(baos.toByteArray(), tempFile);
			baos.close();
//			file=pdfUtil.getPdf(strFileName);
//			tempFile.delete();
	} catch (Exception e) {
		e.printStackTrace();
	}
//		return file;
		return filepath;
	}
}
