package com.pqsoft.qsd.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.pqsoft.dataDictionary.action.DataDictionaryAction;
import com.pqsoft.payment.service.paymentService;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.DateSiteUtil;
import com.pqsoft.util.PageUtil;

public class QsdService {
	private Logger logger = Logger.getLogger(DataDictionaryAction.class);
	public Page qsd(Map<String,Object> m)
	{
		String NotInvoice="11";
		if(m==null)
		{
			m=new HashMap();
		}
		m.put("NotInvoice", NotInvoice);
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			m.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			m.put("COM_ID",COM_MAP.get("COMPANY_ID"));
		}
		return PageUtil.getPage(m, "qsd.findqsd", "qsd.findqsd_count");

	}
	
	public boolean uploadPdfTemplate(Map<String, Object> param,List<FileItem> fileList){
		logger.info("开始上传文件");
		// 设置文件上传的大小 目前设置为30m
		final long MAX_SIZE = 300 * 1024 * 1024;
		DiskFileItemFactory df = new DiskFileItemFactory();// 磁盘目录
		df.setSizeThreshold(4096);// 设置缓冲区大小
		String path = SkyEye.getConfig("file.path");
		path+=File.separator+"picture";
		try {
			File wf = new File(path);
			if (!wf.exists()) {//检查磁盘上是否存在path路径文件
				wf.mkdirs();//在磁盘上创建路径
			}
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		ServletFileUpload suf = new ServletFileUpload(df);
		suf.setHeaderEncoding("UTF-8");
		suf.setSizeMax(MAX_SIZE);
		Iterator<?> i = fileList.iterator();
		File file = null;
		while (i.hasNext()) {
			FileItem fileitem = (FileItem) i.next();
			logger.error(fileitem.getFieldName());
			logger.error(fileitem.getName());
			if (!fileitem.isFormField()) {// 如果是文件
				logger.info("fieldName:  " + fileitem.getFieldName() + "Name: " + fileitem.getName());
				if(!fileitem.getName().isEmpty() && !"".equals(fileitem.getName())){
					String fn = Action._getFileName(fileitem.getName());
					file = new File(path + File.separator + UUID.randomUUID() + fn);
					// 判断文件扩展名
					String filename = file.getName();
					String lastname = filename.substring(filename.length() - 4);
					if (file.exists()) {
						if (!".pdf".equals(lastname)) {
							logger.error("您上传的文件格式不正确! --> " + file.getName());
							file = null;
							return false;
						}
					}
					try {
						fileitem.write(file);
					} catch (Exception e) {
						e.printStackTrace();
						return false;
					}
					param.put("FILE_NAME", fn.substring(0, fn.length()-4));
					param.put("FILE_URL", file.getPath());
					logger.info("保存文件完毕：" + file.getAbsolutePath());
				}
			} else {
				try {
					param.put(fileitem.getFieldName(), new String(fileitem.getString().getBytes("ISO-8859-1"), "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return true;
	}
	public boolean uploadPdfTemplate(Map<String, Object> param) throws Exception{	
		String ID = Dao.getSequence("SEQ_PDFTEMPLATE_FILE");
		param.put("ID", ID);

		//根据起租规则 计算日期 
		Map projectID  = Dao.selectOne("PayTask.getProjectID", param);
		List rendID = DateSiteUtil.getRend_ID(projectID.get("PROJECT_ID"+"")+"");
		String PAY_ID = "";
		for(int t = 0 ;t < rendID.size(); t++){
			PAY_ID = ((Map)rendID.get(t)).get("ID")+"";
			Map mapPay=new HashMap();
			mapPay.put("PAY_ID", PAY_ID);
			paymentService paymentSer=new paymentService();
			try{
				//先查询该项目中金融产品配置的是否差额利息（SFCELX）,如果没有配置或者停用状态或者配置的为Y则计算否则不计算
				Map ISCELXMap=Dao.selectOne("PayTask.querySchemeSFCELXByPay",mapPay);
				if(ISCELXMap !=null && ISCELXMap.get("VALUE_STR") !=null && ISCELXMap.get("VALUE_STR").equals("3")){
					
					DateSiteUtil.setDateData(mapPay.get("PAY_ID").toString(), null,true);//参数PAY_ID,3固定为放款日期类型标示
					
				}else{
					
					//根据关键日期管理查询出还款日和起租日
					DateSiteUtil.setDateData(mapPay.get("PAY_ID").toString(), null,false);//参数PAY_ID,3固定为放款日期类型标示
					
					Map<String,Object> mapbase = Dao.selectOne("PayTask.getSchemeBaseInfoByProjectIdINT", mapPay);
					
					//查询起租日和还款日
					Map dataMap=Dao.selectOne("PayTask.queryPayDataByPayId", mapPay);
					
					if (mapbase != null) {
						mapbase.put("SCHEME_ID", mapbase.get("ID"));
						List<Map<String,Object>> clobList=Dao.selectList("leaseApplication.queryfil_scheme_clobForCs", mapbase);
						for (Map<String, Object> map2 : clobList) {
							mapbase.put(map2.get("KEY_NAME_EN")+"", map2.get("VALUE_STR"));
						}
						
						try{
							mapbase.put("AMOUNT", Dao.selectInt("PayTask.queryAmountCount", mapPay));
						}catch(Exception e){}
						
						
						mapbase.put("annualRate", mapbase.get("YEAR_INTEREST"));
						mapbase.put("_leaseTerm", mapbase.get("LEASE_TERM"));
						mapbase.put("residualPrincipal", mapbase.get("FINANCE_TOPRIC"));
						mapbase.put("_payCountOfYear", mapbase.get("PAYCOUNTOFYEAR"));
						mapbase.put("pay_way", mapbase.get("PAY_WAY"));
						
						mapbase.put("LXTQSQ", mapbase.get("LXTQSQ"));
						mapbase.put("DISCOUNT_MONEY", mapbase.get("DISCOUNT_MONEY"));
						mapbase.put("DISCOUNT_TYPE", mapbase.get("DISCOUNT_TYPE"));
						mapbase.put("CALCULATE_TYPE", mapbase.get("CALCULATE"));
						
						mapbase.put("START_DATE", dataMap.get("START_DATE"));
						mapbase.put("REPAYMENT_DATE", dataMap.get("REPAYMENT_DATE"));
						
						JSONObject page=null;
						Class<?> cla = Class.forName("com.pqsoft.pay.service.PayTaskService");
						page = (JSONObject) cla.getMethod("quoteCalculateTest", Map.class).invoke(cla.newInstance(), mapbase);
						List<Map<String, String>> irrList = (List<Map<String, String>>) page.get("ln");
						
						double ZJHJ = 0.00;//租金合计
						for(int hh=0;hh<irrList.size();hh++){
							//"zj":"11919.33","PMTzj":"12308.22","bj":"11141.55","lx":"777.78","sybj":"128858.45","qc":"1","PAY_DATE":"2015-09-15","glf":"0.0","sxf":"0","lxzzs":"0.00"
							Map mapIrr=irrList.get(hh);
							if(mapIrr !=null){
								mapIrr.put("PAY_ID", mapPay.get("PAY_ID"));
								
								ZJHJ = ZJHJ + Double.parseDouble((mapIrr.get("zj") !=null && mapIrr.get("zj") !="") ? mapIrr.get("zj")+"":"0");
								//更新租金
								mapIrr.put("ITEM_NAME", "租金");
								mapIrr.put("ITEM_MONEY", (mapIrr.get("zj") !=null && mapIrr.get("zj") !="") ? mapIrr.get("zj"):"0");
								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
								//更新PMT租金
								mapIrr.put("ITEM_NAME", "PMT租金");
								mapIrr.put("ITEM_MONEY", (mapIrr.get("PMTzj") !=null && mapIrr.get("PMTzj") !="") ? mapIrr.get("PMTzj"):"0");
								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
								//更新本金
								mapIrr.put("ITEM_NAME", "本金");
								mapIrr.put("ITEM_MONEY", (mapIrr.get("bj") !=null && mapIrr.get("bj") !="") ? mapIrr.get("bj"):"0");
								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
								//更新利息
								mapIrr.put("ITEM_NAME", "利息");
								mapIrr.put("ITEM_MONEY", (mapIrr.get("lx") !=null && mapIrr.get("lx") !="") ? mapIrr.get("lx"):"0");
								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
								//更新剩余本金
								mapIrr.put("ITEM_NAME", "剩余本金");
								mapIrr.put("ITEM_MONEY", (mapIrr.get("sybj") !=null && mapIrr.get("sybj") !="") ? mapIrr.get("sybj"):"0");
								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
								//更新管理费
								mapIrr.put("ITEM_NAME", "管理费");
								mapIrr.put("ITEM_MONEY", (mapIrr.get("glf") !=null && mapIrr.get("glf") !="") ? mapIrr.get("glf"):"0");
								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
								//更新手续费
								mapIrr.put("ITEM_NAME", "手续费");
								mapIrr.put("ITEM_MONEY", (mapIrr.get("sxf") !=null && mapIrr.get("sxf") !="") ? mapIrr.get("sxf"):"0");
								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
								//更新利息增值税
								mapIrr.put("ITEM_NAME", "利息增值税");
								mapIrr.put("ITEM_MONEY", (mapIrr.get("lxzzs") !=null && mapIrr.get("lxzzs") !="") ? mapIrr.get("lxzzs"):"0");
								Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
							}
							
							
						}
						
						//更新还款计划主表租金合计
						Map pmap=new HashMap();
						pmap.put("ID", mapPay.get("PAY_ID"));
						pmap.put("ZJHJ", ZJHJ);
						Dao.update("PayTask.updatePayHeadMoneyAll",pmap);
						
						System.out.println("------------------------dataMap="+dataMap);
						//同步应收期初表数据
						// 同步财务数据
						Map<String, String> temp = new HashMap<String, String>();
						temp.put("PAY_ID", mapPay.get("PAY_ID")+"");
						temp.put("PAYLIST_CODE", dataMap.get("PAYLIST_CODE") + "");
						temp.put("PMT", "PMT租金");
						temp.put("ZJ", "租金");
						temp.put("SYBJ", "剩余本金");
						temp.put("D", "第");
						temp.put("QI", "期");
						// 删除财务表数据
						Dao.delete("PayTask.deleteBeginning", temp);
						Dao.insert("PayTask.synchronizationBeginning", temp);
						
						//同步中间表
						//刷单条逾期数据
						Dao.update("PayTask.doDunDateByPaylist_code",temp);
						Dao.update("PayTask.doPRC_BE_QJL_PAY_CODE",temp);
						
					}
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
				
			
		}
		return Dao.insert("qsd.addUploadPdfTemplate", param) >= 1 ;
	}
	public List<Map<String,Object>> getUnfoldDate(Map<String, Object> param){
		return Dao.selectList("qsd.getUnfoldDate", param);
		
	}
	public Map<String,Object> selectPdfPath(Map<String,Object> param){
		return Dao.selectOne("qsd.selectPdfPath", param);
	}
	public boolean doDeleteqsd(Map<String,Object> param){
		return Dao.delete("qsd.doDeleteqsd", param) >= 1;
	}
}
