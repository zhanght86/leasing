package com.pqsoft.crm.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONArray;

import com.pqsoft.Code.service.CodeService;
import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.FIinterface;
import com.pqsoft.util.StringUtils;

public class BankSignMgService {
	
	/**
	 * 
	 * @Title: SelectBankSignApplyPageData 
	 * @Description: TODO(网银签约获取申请界面数据) 
	 * @param params
	 * @return 
	 * @return Page 
	 * @throws 
	 */
	public Page SelectBankSignApplyPageDataOld(Map<String, Object> params) {
		Page page = new Page(params);
		params.put("sign_flag_sql", "签约标识");
//		params.put("bank_name", "中国建设银行");
		JSONArray jsonArray = JSONArray.fromObject(Dao.selectList("BankAccountSign.selectBankSignApplyPageData",params));
		page.addDate(jsonArray, Dao.selectInt("BankAccountSign.selectBankSignApplyPageDataCount",params));
		//page.addParam(params);
		return page;
	}
	
	/**
	 * 
	 * @Title: SelectBankSignApplyPageData 
	 * @Description: TODO(网银签约获取申请界面数据) 
	 * @param params
	 * @return 
	 * @return Page 
	 * @throws 
	 */
	public Page SelectBankSignApplyPageData(Map<String, Object> params) {
		Page page = new Page(params);
		params.put("sign_flag_sql", "签约标识");
//		params.put("bank_name", "中国建设银行");
		JSONArray jsonArray = JSONArray.fromObject(Dao.selectList("BankAccountSign.selectBankSignApplyPageData",params));
		page.addDate(jsonArray, Dao.selectInt("BankAccountSign.selectBankSignApplyPageDataCount",params));
		//page.addParam(params);
		return page;
	}
	
	/**
	 * 
	 * @Title: getExportApplyExcel 
	 * @Description: (建行网银签约excel) 
	 * @param params
	 * @return 
	 * @return Excel 
	 * @throws 
	 */
	public Excel getExportApplyExcel(Map<String, Object> params) {
		//数据
//		List<Map<String,Object>> dataList = Dao.selectList("BankAccountSign.getExportApplyExcelData",params);
		List<Map<String,Object>> dataList = Dao.selectList("BankAccountSign.getExportApplyExcelDataNew",params);
		//表头
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
		title.put("bank_custname", "持卡人");
		title.put("id_card_no", "身份证号");
		title.put("bank_name", "银行");
		title.put("bank_account", "银行卡号");
		title.put("pro_code", "备注");
		//封装excel
		Excel excel = new Excel();
//		excel.setName("CardCheck"+DateUtil.getSysDate("yyyymmddss")+".xls");
//		excel.addSheetName("sheet01");
		excel.addData(dataList);
		excel.addTitle(title);
		excel.setAutoColumn(25);
//		excel.hasRownum();
//		excel.hasTitle(true);
		//业务逻辑
		params.put("bank_sql", "中国建设银行");
		Dao.insert("BankAccountSign.InsertBankSignInfo", params);
		return excel;
	}
	
	/**
	 * 
	 * @Title: SelectBankSignConfirmPageData 
	 * @Description: (查询回执上传页面数据) 
	 * @param params
	 * @return 
	 * @return Page 
	 * @throws 
	 */
	public Page SelectBankSignConfirmPageData(Map<String, Object> params) {
		
		Page page = new Page(params);
		JSONArray jsonArray = JSONArray.fromObject(Dao.selectList("BankAccountSign.selectBankSignConfirmPageData",params));
		page.addDate(jsonArray, Dao.selectInt("BankAccountSign.selectBankSignConfirmPageDataCount",params));
		//page.addParam(params);
		return page;
	}
	
	/**
	 * 
	 * @Title: resetSignAccount 
	 * @param m
	 * @return 
	 * @return int 
	 * @throws 
	 */
	public int resetBankSignApply(Map<String, Object> m) {
		return Dao.delete("BankAccountSign.resetBankSignApply",m);
	}

	/**
	 * @param m 
	 * 
	 * @Title: updateConfirmExcelInfo 
	 * @Description: (回执确认) 
	 * @param files
	 * @return 
	 * @return int 
	 * @throws 
	 */
	public int updateConfirmExcelInfo(List<File> files, Map<String, Object> m) {
		int count = 0;
		File file = files.get(0);
		List<List<String>> sheet  = Excel.read2Map(file).get(0);
		if(sheet != null && sheet.size() >0 ){
			for(int i = 1; i < sheet.size() ; i++){
				List<String> row = sheet.get(i);
				if(row.get(0).equals("持卡人")){
					continue;
				}
				m.put("bank_account", row.get(3));
//				m.put("bank_custname", row.get(0));
				m.put("sign_flag", row.get(5));
				if(StringUtils.nullToOther(row.get(5),"0").equals("1")){
					count += Dao.update("BankAccountSign.updateConfirmExcelInfo",m);
				}else{
					count += Dao.delete("BankAccountSign.deleteConfirmExcelInfo",m);
				}
			}
		}
		return count;
	}
	
	/**
	 * 
	 * @Title: selectBankSignHistPageData 
	 * @Description: (签约历史数据查询) 
	 * @param params
	 * @return 
	 * @return Page 
	 * @throws 
	 */
	public Page selectBankSignHistPageData(Map<String, Object> params) {
		Page page = new Page(params);
		params.put("sign_flag_sql", "签约标识");
		JSONArray jsonArray = JSONArray.fromObject(Dao.selectList("BankAccountSign.selectBankSignHistPageData",params));
		page.addDate(jsonArray, Dao.selectInt("BankAccountSign.selectBankSignHistPageDataCount",params));
		//page.addParam(params);
		return page;
	}
	
	public List<Map<String,Object>> sendQyInfoDc(String[] sqlDataL,String sqlData){
		for(int i=0;i<sqlDataL.length;i++){
			String PROJECT_ID=sqlDataL[i];
			if(PROJECT_ID.length()>0){
				Map map=Dao.selectOne("BankAccountSign.queryBankQyInfoIs", PROJECT_ID);
				if(map ==null){//没有数据则新增一条
					String AGREEMENTNO = CodeService.getCode("网银签约编号", "", "");
					Map mapWYQY=new HashMap();
					mapWYQY.put("PROJECT_ID", PROJECT_ID);
					mapWYQY.put("AGREEMENTNO", AGREEMENTNO);
					Dao.insert("BankAccountSign.insertWYQYInfoDc",mapWYQY);
				}
			}
		}
		
		List<Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
		if(sqlDataL.length>0 && sqlData.length()>0){
			Map mapda=new HashMap();
			mapda.put("SQLDATA", sqlData);
			dataList=Dao.selectList("BankAccountSign.queryWYQYInfoListDc",mapda);
			Dao.update("BankAccountSign.updateAGEGEADLINEDc",mapda);
			
		}
		return dataList;
	}
	
	public List<Map<String,Object>> sendQyInfoDcAll(Map<String,Object> mapP){
		List dlist=Dao.selectList("BankAccountSign.queryWYQYInfoListDcAllData",mapP);
		for(int i=0;i<dlist.size();i++){
			Map proMap=(Map)dlist.get(i);
			if(proMap !=null && proMap.get("PROJECT_ID") !=null && !proMap.get("PROJECT_ID").equals("")){
				String PROJECT_ID=proMap.get("PROJECT_ID").toString();
				if(PROJECT_ID.length()>0){
					Map map=Dao.selectOne("BankAccountSign.queryBankQyInfoIs", PROJECT_ID);
					if(map ==null){//没有数据则新增一条
						String AGREEMENTNO = CodeService.getCode("网银签约编号", "", "");
						Map mapWYQY=new HashMap();
						mapWYQY.put("PROJECT_ID", PROJECT_ID);
						mapWYQY.put("AGREEMENTNO", AGREEMENTNO);
						Dao.insert("BankAccountSign.insertWYQYInfoDc",mapWYQY);
					}
				}
			}
			
		}
		
		List<Map<String,Object>> dataList=Dao.selectList("BankAccountSign.queryWYQYInfoListDcAll", mapP);
		Dao.update("BankAccountSign.updateAGEGEADLINEDcAll",mapP);
		return dataList;
	}
	
	
	public Excel getExportApplyExcelDc(List<Map<String,Object>> dataList) {
		//表头
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  
		title.put("ACNAME", "姓名");
		title.put("PROJECT_ID", "用户id");
		title.put("MOBILE", "电话");
		title.put("BANKNAME", "开户行");
		title.put("ACCT", "账号");
		title.put("BANKPROV", "开户所在省");
		title.put("BANKCITY", "开户所在市");
		title.put("IDNO", "身份证号码");
		title.put("BUSS_CODE", "商户id");
		title.put("ADDR", "用户地址");
		title.put("REMARK", "备注");
		title.put("TYPE", "交易类型");
		//封装excel
		Excel excel = new Excel();
		excel.addData(dataList);
		excel.addTitle(title);
		excel.setAutoColumn(25);
		return excel;
	}
	
	
	public void sendQyInfo(String[] sqlDataL,String sqlData){
		for(int i=0;i<sqlDataL.length;i++){
			String PROJECT_ID=sqlDataL[i];
			if(PROJECT_ID.length()>0){
				Map map=Dao.selectOne("BankAccountSign.queryBankQyInfoIs", PROJECT_ID);
				if(map ==null){//没有数据则新增一条
					String AGREEMENTNO = CodeService.getCode("网银签约编号", "", "");
					Map mapWYQY=new HashMap();
					mapWYQY.put("PROJECT_ID", PROJECT_ID);
					mapWYQY.put("AGREEMENTNO", AGREEMENTNO);
					Dao.insert("BankAccountSign.insertWYQYInfo",mapWYQY);
				}
			}
		}
		//传输数据到接口
		if(sqlDataL.length>0 && sqlData.length()>0){
			Map mapda=new HashMap();
			mapda.put("SQLDATA", sqlData);
			List list=Dao.selectList("BankAccountSign.queryWYQYInfoList",mapda);
			Dao.update("BankAccountSign.updateAGEGEADLINE",mapda);
			System.out.println("---------------------list="+list);
		}
	}
	
	//签约回执（发送报文失败）（如果失败先判断是否有签约日期，如果有的话恢复以前状态为已签约，如果没有签约日期则为未签约）
	public void tranxSign(){
		//先查询提交失败的报文
		List listTjSb=Dao.selectList("BankAccountSign.listTjSb");
		for(int i=0;i<listTjSb.size();i++){
			Map mapTjSb=(Map)listTjSb.get(i);
			if(mapTjSb !=null && mapTjSb.get("AGREEMENTNO") !=null && !mapTjSb.get("AGREEMENTNO").equals("")){
				int num=Dao.update("BankAccountSign.signSb",mapTjSb);
				if(num>1){
					//反更该条数据已经被处理掉
					Dao.update("BankAccountSign.updatetranxsingstatus",mapTjSb);
				}
			}
		}
		
		//再查询提交成功并银行已反馈的数据进行处理
		List listTjCgBack=Dao.selectList("BankAccountSign.listTjCgBack");
		for(int i=0;i<listTjCgBack.size();i++){
			Map mapTjCgBack=(Map)listTjCgBack.get(i);
			if(mapTjCgBack !=null && mapTjCgBack.get("AGREEMENTNO") !=null && !mapTjCgBack.get("AGREEMENTNO").equals("")){
				if(mapTjCgBack.get("STATUS") !=null && !mapTjCgBack.get("STATUS").equals("")){
					String STATUS=mapTjCgBack.get("STATUS")+"";
					int num=0;
					if(STATUS.equals("2")){//签约成功
						num=Dao.update("BankAccountSign.signBackCG",mapTjCgBack);
					}else if(STATUS.equals("4") || STATUS.equals("9")){
						num=Dao.update("BankAccountSign.signSb",mapTjCgBack);
					}
					
					if(num>1){
						//反更该条数据已经被处理掉
						Dao.update("BankAccountSign.updatetranxsingstatus",mapTjCgBack);
					}
				}
			}
		}
	}
	
	
	public List<Map<String,Object>> sendQyInfoDcFC(String[] sqlDataL,String sqlData){
		for(int i=0;i<sqlDataL.length;i++){
			String PROJECT_ID=sqlDataL[i];
			if(PROJECT_ID.length()>0){
				Map map=Dao.selectOne("BankAccountSign.queryBankQyInfoIs", PROJECT_ID);
				if(map ==null){//没有数据则新增一条
//					String AGREEMENTNO = CodeService.getCode("网银签约编号", "", "");
					Map mapWYQY=new HashMap();
					mapWYQY.put("PROJECT_ID", PROJECT_ID);
//					mapWYQY.put("AGREEMENTNO", AGREEMENTNO);
					mapWYQY.put("SIGN_FLAG", "1");
					Dao.insert("BankAccountSign.insertWYQYInfoDcFC",mapWYQY);
				}
			}
		}
		
		List<Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
		if(sqlDataL.length>0 && sqlData.length()>0){
			Map mapda=new HashMap();
			mapda.put("SQLDATA", sqlData);
			dataList=Dao.selectList("BankAccountSign.queryWYQYInfoListDcFC",mapda);
			mapda.put("SIGN_FLAG", "3");
			Dao.update("BankAccountSign.updateAGEGEADLINEDc",mapda);
			
		}
		return dataList;
	}
	
	public List<Map<String,Object>> sendQyInfoDcAllFC(Map<String,Object> mapP){
		List dlist=Dao.selectList("BankAccountSign.queryWYQYInfoListDcAllData",mapP);
		for(int i=0;i<dlist.size();i++){
			Map proMap=(Map)dlist.get(i);
			if(proMap !=null && proMap.get("PROJECT_ID") !=null && !proMap.get("PROJECT_ID").equals("")){
				String PROJECT_ID=proMap.get("PROJECT_ID").toString();
				if(PROJECT_ID.length()>0){
					Map map=Dao.selectOne("BankAccountSign.queryBankQyInfoIs", PROJECT_ID);
					if(map ==null){//没有数据则新增一条
						Map mapWYQY=new HashMap();
						mapWYQY.put("PROJECT_ID", PROJECT_ID);
						mapWYQY.put("SIGN_FLAG", 1);
						Dao.insert("BankAccountSign.insertWYQYInfoDcFC",mapWYQY);
					}
				}
			}
			
		}
		
		List<Map<String,Object>> dataList=Dao.selectList("BankAccountSign.queryWYQYInfoListDcAllFC", mapP);
		mapP.put("SIGN_FLAG", "3");
		Dao.update("BankAccountSign.updateAGEGEADLINEDcAll",mapP);
		return dataList;
	}
	
	/**
	 * 
	 * @Title: SelectBankSignApplyPageData 
	 * @Description: TODO(网银签约获取申请界面数据) 
	 * @param params
	 * @return 
	 * @return Page 
	 * @throws 
	 */
	public Page SelectBankSignApplyPageDataCX(Map<String, Object> params) {
		Page page = new Page(params);
		params.put("sign_flag_sql", "签约标识");
//		params.put("bank_name", "中国建设银行");
		JSONArray jsonArray = JSONArray.fromObject(Dao.selectList("BankAccountSign.selectBankSignApplyPageDataCX",params));
		page.addDate(jsonArray, Dao.selectInt("BankAccountSign.selectBankSignApplyPageDataCountCX",params));
		//page.addParam(params);
		return page;
	}
	
	public List<Map<String,Object>> sendQyInfoDcFCJY(String[] sqlDataL,String sqlData){
		for(int i=0;i<sqlDataL.length;i++){
			String PROJECT_ID=sqlDataL[i];
			if(PROJECT_ID.length()>0){
				Map map=Dao.selectOne("BankAccountSign.queryBankQyInfoIs", PROJECT_ID);
				if(map ==null){//没有数据则新增一条
//					String AGREEMENTNO = CodeService.getCode("网银签约编号", "", "");
					Map mapWYQY=new HashMap();
					mapWYQY.put("PROJECT_ID", PROJECT_ID);
//					mapWYQY.put("AGREEMENTNO", AGREEMENTNO);
					mapWYQY.put("SIGN_FLAG", "1");
					Dao.insert("BankAccountSign.insertWYQYInfoDcFC",mapWYQY);
				}
			}
		}
		
		List<Map<String,Object>> dataList=new ArrayList<Map<String,Object>>();
		if(sqlDataL.length>0 && sqlData.length()>0){
			Map mapda=new HashMap();
			mapda.put("SQLDATA", sqlData);
			dataList=Dao.selectList("BankAccountSign.queryWYQYInfoListDcFCJY",mapda);
			mapda.put("SIGN_FLAG", "5");
			Dao.update("BankAccountSign.updateAGEGEADLINEDc",mapda);
			
		}
		return dataList;
	}
	
	public List<Map<String,Object>> sendQyInfoDcAllFCJY(Map<String,Object> mapP){
		List dlist=Dao.selectList("BankAccountSign.queryWYQYInfoListDcAllData",mapP);
		for(int i=0;i<dlist.size();i++){
			Map proMap=(Map)dlist.get(i);
			if(proMap !=null && proMap.get("PROJECT_ID") !=null && !proMap.get("PROJECT_ID").equals("")){
				String PROJECT_ID=proMap.get("PROJECT_ID").toString();
				if(PROJECT_ID.length()>0){
					Map map=Dao.selectOne("BankAccountSign.queryBankQyInfoIs", PROJECT_ID);
					if(map ==null){//没有数据则新增一条
						Map mapWYQY=new HashMap();
						mapWYQY.put("PROJECT_ID", PROJECT_ID);
						mapWYQY.put("SIGN_FLAG", 1);
						Dao.insert("BankAccountSign.insertWYQYInfoDcFC",mapWYQY);
					}
				}
			}
			
		}
		
		List<Map<String,Object>> dataList=Dao.selectList("BankAccountSign.queryWYQYInfoListDcAllFCJY", mapP);
		mapP.put("SIGN_FLAG", "5");
		Dao.update("BankAccountSign.updateAGEGEADLINEDcAllJY",mapP);
		return dataList;
	}
	
	
	
	public List<Map<String,Object>> parsebankSignExcel(File txtFile,Map mapDate) throws Exception {
		try {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

			String AGREEMENTNO="";//协议号
			String BANK_RESULT="";//回执结果：生效
			
			
			InputStream is = new FileInputStream(txtFile);
			jxl.Workbook rwb = Workbook.getWorkbook(is);
			Sheet rs = (Sheet) rwb.getSheet(0);
			int rowNum = rs.getRows();// 得到所有行数
			for (int i = 4; i < rowNum; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				Cell c0 = (Cell) rs.getCell(0, i);
				AGREEMENTNO = ((jxl.Cell) c0).getContents();
				Cell c9 = (Cell) rs.getCell(9, i);
				BANK_RESULT = ((jxl.Cell) c9).getContents();
				
				map.put("AGREEMENTNO", AGREEMENTNO);
				map.put("BANK_RESULT", BANK_RESULT);
				
				list.add(map);
			}
			rwb.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("回执文件解析失败，请确认文件格式!");
		}
	}
	
	public int updateBankSignStatus(List list,String bank_sign){
		int num=0;
		for(int i=0;i<list.size();i++){
			Map map=(Map)list.get(i);
			String BANK_RESULT="";
			if(map !=null && map.get("BANK_RESULT") !=null && !map.get("BANK_RESULT").equals("")){
				BANK_RESULT=map.get("BANK_RESULT").toString();
			}
			if(BANK_RESULT.equals("生效")){
				map.put("SIGN_FLAG",bank_sign);
				num=num+Dao.update("BankAccountSign.updateBankSignStatus",map);
			}
			
		}
		return num;
	}
	
	//网银签约接口提取数据
	//中金导出数据
	//流水号：AGREEMENTNO
	//银行名称：BANK_NAME
	//账户名称：ACNAME
	//账户号码：ACCT
	//开户证件类型：ID_CARD_TYPE_CODE
	//证件号：IDNO
	//手机号：MOBILE
	//卡类型：KLX
	public List bankSignListInfo(){
		Map<String,Object> mapP=new HashMap<String,Object>();
		List dlist=Dao.selectList("BankAccountSign.queryWYQYInfoListDcAllDataJK",mapP);
		for(int i=0;i<dlist.size();i++){
			Map proMap=(Map)dlist.get(i);
			if(proMap !=null && proMap.get("PROJECT_ID") !=null && !proMap.get("PROJECT_ID").equals("")){
				String PROJECT_ID=proMap.get("PROJECT_ID").toString();
				if(PROJECT_ID.length()>0){
					Map map=Dao.selectOne("BankAccountSign.queryBankQyInfoIs", PROJECT_ID);
					if(map ==null){//没有数据则新增一条
						Map mapWYQY=new HashMap();
						mapWYQY.put("PROJECT_ID", PROJECT_ID);
						mapWYQY.put("SIGN_FLAG", 1);
						Dao.insert("BankAccountSign.insertWYQYInfoDcFC",mapWYQY);
					}
				}
			}
			
		}
		
		List<Map<String,Object>> dataList=Dao.selectList("BankAccountSign.queryWYQYInfoListDcAllFCJK", mapP);
		mapP.put("SIGN_FLAG", "3");
		Dao.update("BankAccountSign.updateAGEGEADLINEDcAllJK",mapP);
		return dataList;
	}
	
	//网银签约接口回执
	//参数   流水号：AGREEMENTNO  回执类型：RETURN_STATUS 10绑定处理中 20绑定失败 30绑定成功 40解绑成功
	public void bankSignBackInfo(Map<String,Object> map){
		String BANK_RESULT="";
		if(map !=null && map.get("RETURN_STATUS") !=null && !map.get("RETURN_STATUS").equals("")){
			BANK_RESULT=map.get("RETURN_STATUS").toString();
		}
		map.put("AGREEMENTNO", map.get("TXSNBINDING"));
		if(BANK_RESULT.equals("20")){
			map.put("SIGN_FLAG","1");
			Dao.update("BankAccountSign.updateBankSignStatus",map);
			Dao.delete("BankAccountSign.deleteZhongjinBinding", map);
		}else if(BANK_RESULT.equals("30")){
			map.put("SIGN_FLAG","2");
			Dao.update("BankAccountSign.updateBankSignStatus",map);
		}else if(BANK_RESULT.equals("40")){
			map.put("SIGN_FLAG","6");
			Dao.update("BankAccountSign.updateBankSignStatus",map);
		}
	}
	
	/**
	 * 捷翊租赁：签约接口
	 * @author wanghl
	 * @datetime 2015年8月17日,下午1:38:38
	 */
	public void autoSigning(){
//		new Thread(new Runnable() {
//			public void run() {
				try {
					List bankList = bankSignListInfo();
					FIinterface fi = new FIinterface();
					Map<String,Object> map = new HashMap<String, Object>();
//					map.put("AGREEMENTNO", "a312433");
//					map.put("BANK_NAME", "中国银行");
//					map.put("ACNAME", "张三");
//					map.put("ACCT", "1508185215010809");
//					map.put("ID_CARD_TYPE_CODE", "0");
//					map.put("IDNO", "");
//					map.put("MOBILE", "13333333333");
//					map.put("KLX", "10");
					if(bankList!=null&&bankList.size()>0){
						for(int i=0;i<bankList.size();i++){
							map = (Map<String, Object>) bankList.get(i);
							map.put("STATUS", "-1");
							Dao.insert("BankAccountSign.insertZhongjinBinding", map);
						}
					}
					List<Map<String,Object>> bList = Dao.selectList("BankAccountSign.selectBinding");
					if(bList!=null && bList.size()>0){
						for(Map<String,Object> bMap:bList ){
							fi.Binding(bMap);
						}
					}
					Dao.commit();
				} catch (Exception e) {
					Dao.rollback();
					e.printStackTrace();
				} finally {
					Dao.close();
				}
//			}
//		}).start();
	}
	
	/**
	 * 捷翊租赁：查询签约接口
	 * @author wanghl
	 * @datetime 2015年8月17日,下午1:38:38
	 */
	public void querySigning(){
		new Thread(new Runnable() {
			public void run() {
				try {
					List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
					Map<String,Object> param = new HashMap<String, Object>();
					param.put("STATUS", "1");
					list =	Dao.selectList("BankAccountSign.queryZhongjinBinding",param);
					FIinterface fi = new FIinterface();
					Map<String,Object> map = new HashMap<String, Object>();
					for(int i=0;i<list.size();i++){
						map = list.get(i);
						fi.querySigning(map.get("TXSNBINDING").toString());
					}
					Dao.commit();
				} catch (Exception e) {
					Dao.rollback();
					e.printStackTrace();
				} finally {
					Dao.close();
				}
			}
		}).start();
	}
}
