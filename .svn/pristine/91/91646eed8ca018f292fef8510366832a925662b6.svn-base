package com.pqsoft.fi.payin.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.PageUtil;

public class FundUploadService {

	public Map<String, Object> parseUploadFile(File file, String type) throws Exception {
		IAnalysisXLS parser;
		if ("dk".equals(type)) {// 银行代扣回执
			parser = new CftXLS(file.getAbsolutePath());
		} else if (file.getName().endsWith("xlsx")) {
			parser = new AnalysisXLSX(file.getAbsolutePath());
		} else {
			parser = new AnalysisXLS(file.getAbsolutePath());
		}
		Map<String, Object> re = new HashMap<String, Object>();
		List<Map<String, Object>> list = parser.read();
		List<Map<String, Object>> list2 = new ArrayList<Map<String,Object>>();
		re.put("ITEMS", list);
		boolean flag = true;
		for (Map<String, Object> map : list) {
			if(map.get("FUND_RECEIVE_MONEY")!=null){
				list2.add(map);
				map.put("FUND_IMPORT_FILE", file.getName());
				// 标志位：validate格式错误 check数据不全 calcmemo原因
				boolean validate = true;
				// if (StringUtils.isEmpty((String) map.get("FUND_COMPANY_NAME"))) {
				// validate = false;
				// re.put("ERRORINFO", "上传文件中收款帐号为空，无法进行后续处理，请注意excel格式！");
				// }
				// if (StringUtils.isEmpty((String)
				// map.get("FUND_COMPANY_ACCOUNT"))) {
				// re.put("ERRORINFO", "上传文件中收款帐号为空，无法进行后续处理，请注意excel格式！");
				// validate = false;
				// }
				// if (StringUtils.isEmpty((String) map.get("FUND_ACCEPT_DATE"))) {
				// re.put("ERRORINFO", "上传文件中收款帐号为空，无法进行后续处理，请注意excel格式！");
				// validate = false;
				// }
				if (StringUtils.isEmpty((String) map.get("FUND_COMENAME"))) {
					re.put("ERRORINFO", "上传文件中收款帐号为空，无法进行后续处理，请注意excel格式！");
					validate = false;
				}
				if (StringUtils.isEmpty((String) map.get("FUND_COMECODE"))) {
					re.put("ERRORINFO", "上传文件中收款帐号为空，无法进行后续处理，请注意excel格式！");
					validate = false;
				}
				if (StringUtils.isEmpty((String) map.get("FUND_RECEIVE_MONEY"))) {
					re.put("ERRORINFO", "上传文件中收款帐号为空，无法进行后续处理，请注意excel格式！");
					validate = false;
				}
				if ("银行代扣回执单".equals(type)) {
					if (!"8".equals(map.get("FLAG"))) {
						validate = false;
					}
					if (map.get("IDCODE") == null || "".equals(map.get("IDCODE"))) {
						validate = false;
					}
				}
				if (StringUtils.isEmpty((String) map.get("FUND_TYPE_FLAG"))) {
					re.put("ERRORINFO", "上传文件中资金来源为空，无法进行后续处理，请注意excel格式！");
					validate = false;
				}
				else{
					String FUND_TYPE_FLAG=map.get("FUND_TYPE_FLAG").toString();
					if(FUND_TYPE_FLAG.equals("首期款") || FUND_TYPE_FLAG.equals("租金")){
						if(FUND_TYPE_FLAG.equals("首期款")){
							map.put("FUND_TYPE", "1");
						}
						else{
							map.put("FUND_TYPE", "2");
						}
					}else{
						re.put("ERRORINFO", "上传文件中资金来源填写不规范，无法进行后续处理，请注意excel格式！");
						validate = false;
					}
				}
				
				{
					Pattern pattern = Pattern
							.compile("(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-((((0?)[13578]|1[02])-((0?)[1-9]|[12][0-9]|3[01]))|(((0?)[469]|11)-((0?)[1-9]|[12][0-9]|30))|(02-((0?)[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})((0?)[48]|[2468][048]|[13579][26])|(((0?)[48]|[2468][048]|[3579][26])00))-(0?)2-29)");
					Pattern pattern1 = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}");
					if ("银行代扣回执单".equals(type)) {} else {
						if (map.get("FUND_ACCEPT_DATE").toString().indexOf("-") > 0) {
							// map.put("FUND_ACCEPT_DATE", );
							Matcher matcher = pattern.matcher(map.get("FUND_ACCEPT_DATE").toString());
							validate = validate && matcher.matches();
							matcher = pattern1.matcher(map.get("FUND_ACCEPT_DATE").toString());
							validate = validate && matcher.matches();
						} else {
							re.put("ERRORINFO", "上传文件中日期格式不对，无法进行后续处理，请注意excel格式！");
							validate = false;
							// StringBuilder bf = new
							// StringBuilder(map.get("FUND_ACCEPT_DATE").toString());
							// bf.insert(4, "-");
							// bf.insert(7, "-");
							// Matcher matcher = pattern.matcher(bf.toString());
							// validate = validate && matcher.matches();
						}
					}
				}
				{
					// 检查来款金额
					Pattern pattern = Pattern.compile("^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
					Matcher matcher = pattern.matcher(map.get("FUND_RECEIVE_MONEY").toString());
					if (!matcher.matches()) {
						re.put("ERRORINFO", "上传文件中金额格式不对，无法进行后续处理，请注意excel格式！");
						validate = false;
					}
				}
				map.put("validate", validate);
				flag = flag && validate;
			}
			
		}
		re.put("ITEMS", list2);
		re.put("flag", flag);
		return re;
	}

	public boolean saveViewData(Map<String, Object> output,String HouZhui) throws Exception {
		List<Map<String, Object>> list = (List<Map<String, Object>>) output.get("ITEMS");
		for (Map<String, Object> map : list) {
			if (!"8".equals(map.get("FLAG").toString())) {
				continue;
			}
			Map<String, Object> client = null;
//			if (client == null && map.get("IDCODE") != "") {
//				try {
//					client = Dao.selectOne("fundupload.getClientId2", map);
//					map.put("CLIENT_NAME", client.get("CR_BECR_NAME"));
//					map.put("FUND_CLIENT_ID", client.get("CLIENT_ID"));
//				} catch (Exception e) {}
//				map.put("FUND_ACCEPT_DATE", UTIL.FORMAT.date(new Date()));
//			}
//			if (client == null) {
//				client = Dao.selectOne("fundupload.getClientId", map);
//			}
//			if (client != null) {
//				map.put("CLIENT_NAME", client.get("CR_BECR_NAME"));
//				map.put("FUND_CLIENT_ID", client.get("CLIENT_ID"));
//				map.put("FUND_MATCHTIME", UTIL.FORMAT.date(new Date()));
//			}
			map.put("FUND_ACCEPT_NAME", map.get("FUND_COMPANY_NAME"));
			map.put("FUND_ACCEPT_CODE", map.get("FUND_COMPANY_ACCOUNT"));
			map.put("FUND_ID", Dao.selectOne("fi.fund.getFundId"));
			map.put("FUND_FUNDCODE", Dao.selectOne("fi.fund.getFundCode"));
			map.put("FUND_NOTDECO_STATE", "0");
			map.put("FUND_STATUS", "0");
			map.put("FUND_ISCONGEAL", "0");
			map.put("FUND_RED_STATUS", "0");
			map.put("FUND_PRANT_ID", "0");
			map.put("FUND_IMPORT_PERSON", Security.getUser().getName());
			map.put("FUND_IMPORT_PERSON_ID", Security.getUser().getId());
			map.put("FUND_SY_MONEY", map.get("FUND_RECEIVE_MONEY"));
			//客户表中，客户名称相同
			List<Map<String,Object>> list_Id = Dao.selectList("fi.fund.selectClientIds", map);
			System.out.println(list_Id+"-------3333-------");
			int CLIENT_ID = 0;
			if(list_Id.size()!=0){
				//匹配银行账户
				for(int i=0;i<list_Id.size();i++){
					Map<String,Object> map_tem = list_Id.get(i);
					//根据客户id查询银行账号
					List<Map<String,Object>> list2 = Dao.selectList("fi.fund.selectFundComeCode", map_tem);
					System.out.println(list2+"--------list2-------------");
					for(int j=0;j<list2.size();j++){
						Map<String,Object> map_tem2 =list2.get(j);
						if(map.get("FUND_COMECODE").equals(map_tem2.get("BANK_ACCOUNT"))){
							System.out.println(map.get("FUND_COMECODE")+"--"+map_tem2.get("BANK_ACCOUNT"));
							//根据银行账户 查询对应的id
							CLIENT_ID = Dao.selectInt("fi.fund.selectClientId", map_tem2);
							break;
						}
					}
				}
				if(CLIENT_ID!=0){
					map.put("FUND_CLIENT_NAME", map.get("FUND_COMENAME"));
					map.put("FUND_PIDENTIFY_PERSON", Security.getUser().getName());
					map.put("FUND_PIDENTIFY_PERSON_ID", Security.getUser().getId());
					map.put("FUND_CLIENT_ID", CLIENT_ID);
					if(HouZhui.equals("txt")){
						String FUND_RESULT="";
						if(map.get("FUND_RESULT") !=null && !map.get("FUND_RESULT").equals("")){
							FUND_RESULT=map.get("FUND_RESULT")+"";
						}
						if(FUND_RESULT.equals("成功")){
							if (0 == Dao.insert("fi.fund.add", map)) { throw new Exception("保存资金失败"); }
						}
					}else{
						if (0 == Dao.insert("fi.fund.add", map)) { throw new Exception("保存资金失败"); }
					}
				}else{
					if(HouZhui.equals("txt")){
						String FUND_RESULT="";
						if(map.get("FUND_RESULT") !=null && !map.get("FUND_RESULT").equals("")){
							FUND_RESULT=map.get("FUND_RESULT")+"";
						}
						if(FUND_RESULT.equals("成功")){
							if (0 == Dao.insert("fi.fund.add", map)) { throw new Exception("保存资金失败"); }
						}
					}else{
						if (0 == Dao.insert("fi.fund.add", map)) { throw new Exception("保存资金失败"); }
					}
				}
			}else{
				if(HouZhui.equals("txt")){
					String FUND_RESULT="";
					if(map.get("FUND_RESULT") !=null && !map.get("FUND_RESULT").equals("")){
						FUND_RESULT=map.get("FUND_RESULT")+"";
					}
					if(FUND_RESULT.equals("成功")){
						if (0 == Dao.insert("fi.fund.add", map)) { throw new Exception("保存资金失败"); }
					}
				}else{
					if (0 == Dao.insert("fi.fund.add", map)) { throw new Exception("保存资金失败"); }
				}
			}
				
		}
		return true;
	}

	public void addUploadFile(Map<String, Object> param) {
		Dao.insert("fi.fundupload.add", param);
	}

	public Map<String, Object> getFile(String id ){
		return Dao.selectOne("fi.fundupload.getFile", id);
	}
	
	
	public Map<String, Object> parseUploadFileTXT(File file, String type) throws Exception {
		//读取
		BufferedReader br = null;
		//解析TXT文件
		List<String> contentList = new ArrayList<String>();
		List<Map<String,Object>> contentListData = new ArrayList<Map<String,Object>>();
		String DA=DateUtil.getSysDate("yyyy-MM-dd");
		try {
			//解决中文乱码
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), "GBK");
			br=new BufferedReader(read);
			
			//文件体一条条内容
			String contentStr = ""; 
			//循环读取行数据并写入list
			while((contentStr=br.readLine())!=null) {
				contentList.add(contentStr);
				String[] strSegment = contentStr.trim().split("\\|");
				System.out.println("-----------strSegment[6]="+strSegment.length);
				
				if(strSegment.length<5){
					continue;
				}else{
					Map<String,Object> mapData=new HashMap<String,Object>();
					mapData.put("FUND_COMECODE", strSegment[0]);
					mapData.put("FUND_COMENAME", strSegment[1]);
					mapData.put("FUND_RECEIVE_MONEY", strSegment[2]);
					mapData.put("FUND_DOCKET", strSegment[3]);
					mapData.put("FUND_RESULT", strSegment[6]);
					mapData.put("FUND_TYPE_FLAG", "租金");
					mapData.put("FUND_ACCEPT_DATE", DA);
					mapData.put("FLAG", "8");
					contentListData.add(mapData);
				}
				
			}
		}catch(Exception e){
		   e.printStackTrace();
		   throw new Exception("上传文件解析失败，请确认文件格式!");
		}finally{//关闭流
		   try {
				br.close();
		   } catch (IOException e) {
				e.printStackTrace();
		   }
		}
		
		Map<String, Object> re = new HashMap<String, Object>();
		re.put("ITEMS", contentListData);
		boolean flag = true;
		String contentStr = "";
		for(int i=0;i<contentListData.size();i++){
			boolean validate = true;
			Map<String,Object> map = contentListData.get(i);
			
			if (StringUtils.isEmpty((String) map.get("FUND_COMECODE"))) {
				re.put("ERRORINFO", "上传文件中帐号为空，无法进行后续处理，请注意txt格式！");
				validate = false;
			}
			
			if (StringUtils.isEmpty((String) map.get("FUND_COMENAME"))) {
				re.put("ERRORINFO", "上传文件中户名为空，无法进行后续处理，请注意txt格式！");
				validate = false;
			}
			
			// 检查来款金额
			String FUND_RECEIVE_MONEY="";
			if(map.get("FUND_RECEIVE_MONEY") !=null && !map.get("FUND_RECEIVE_MONEY").equals("")){
				FUND_RECEIVE_MONEY=map.get("FUND_RECEIVE_MONEY")+"";
			}
			if (FUND_RECEIVE_MONEY ==null || FUND_RECEIVE_MONEY.equals("")) {
				re.put("ERRORINFO", "上传文件中金额为空，无法进行后续处理，请注意txt格式！");
				validate = false;
			}else{
				Pattern pattern = Pattern.compile("^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$");
				Matcher matcher = pattern.matcher(FUND_RECEIVE_MONEY);
				if (!matcher.matches()) {
					re.put("ERRORINFO", "上传文件中金额格式不对，无法进行后续处理，请注意excel格式！");
					validate = false;
				}
			}
			
			String FUND_RESULT="";
			if(map.get("FUND_RESULT") !=null && !map.get("FUND_RESULT").equals("")){
				FUND_RESULT=map.get("FUND_RESULT")+"";
			}
			if (FUND_RESULT ==null || FUND_RESULT.equals("")) {
				re.put("ERRORINFO", "上传文件中结果为空，无法进行后续处理，请注意txt格式！");
				validate = false;
			}else if(!FUND_RESULT.equals("成功")){
				re.put("ERRORINFO", "上传文件中结果不为成功！");
				validate = false;
			}
			map.put("validate", validate);
			
			flag = flag && validate;
			
		}
		re.put("flag", flag);
		return re;
	}
	
	public void insertUploadFilePathCount(Map<String, Object> map){
		Dao.insert("fi.fundupload.addUploadFilePath",map);
	}
	//查询上传附件路径和还款总额
	public Map<String, Object> searchUploadFilePath(Map<String, Object> map) {
		Map<String,Object> re_map = Dao.selectOne("fi.fundupload.searchUploadFilePath", map);
		//re_map.put("FI_R_UPLOAD_PATH_COUNT", Dao.selectOne("fi.fundupload.searchUploadFilePath", map));
		return re_map;
	}
	//查询总的还款金额excel
	public Map<String, Object> searchMoneyCount(Map<String, Object> map) {
		Map<String, Object> map2 = new HashMap<String, Object>();
		//return (Map<String, Object>) map2.put("FUND_COUNT_MONEY",Dao.selectOne("fi.fundupload.fi_count", map);
		map2.put("FUND_COUNT_MONEY",Dao.selectOne("fi.fundupload.fi_count", map));
		return map2;
	}
	//查询总的还款金额txt
	public Map<String, Object> searchMoneyCountTXT(Map<String, Object> map) {
		Map<String, Object> map2 = new HashMap<String, Object>();
		//return (Map<String, Object>) map2.put("FUND_COUNT_MONEY",Dao.selectOne("fi.fundupload.fi_count", map);
		map2.put("FUND_COUNT_MONEY",Dao.selectOne("fi.fundupload.i_count_txt", map));
		System.out.println(map2.get("FUND_COUNT_MONEY")+"----------");
		return map2;
	}
	
	public Page uploadFileAll_Path(Map<String, Object> map) {
		System.out.println("show-----------");
		return PageUtil.getPage(map, "fi.fundupload.search_uploadfile_all","fi.fundupload.search_uploadfile_number");
	}
	public Map<String,Object> searchSquence(){
		Map<String, Object> seq_map = new HashMap<String, Object>();
		//去附件表中查询序列值
		seq_map.put("FUND_UPLOAD_ID", Dao.selectOne("fi.fundupload.search_seq"));
		return seq_map;
	}
	public void addUpload(Map<String, Object> map){
		Dao.insert("fi.fundupload.addUpload", map);
	}
	//修改附件表中的status字段
	public void updateStatus(Map<String, Object> map) {
		// TODO Auto-generated method stub
		Dao.update("fi.fundupload.updateStatus",map);
	}

	public Excel exportData(Map<String, Object> param) {
		System.out.println("------3-------");
		// 数据
		String listMap = "fi.fundupload.search_uploadfile_all";
		List<Map<String, Object>> dataList = Dao.selectList(listMap.trim(), param);
		System.out.println("--------4--------");
		System.out.println("dataList-----------"+dataList);
		// 表头
		String columnString = "还款编号,附件路径,上传时间,资金汇总";
		String columnName = "FUND_UPLOAD_ID,FUND_FILE_PATH,FUND_UPLOAD_DATE,FUND_COUNT_MONEY";
		String[] columns = columnString.split(",");
		String[] columnNames = columnName.split(",");
		LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
		for (int i = 0; i < columnNames.length; i++) {
			title.put(columnNames[i], columns[i]);
		}
		// 封装excel
		Excel excel = new Excel();
		excel.addTitle(title);
		excel.setName("workFlow" + DateUtil.getSysDate("yyyymmddss") + ".xls");
		excel.addSheetName("sheet01");
		excel.addData(dataList);

		return excel;
	}

	public List fundRepayment(Map<String, Object> param) {
		return Dao.selectList("fi.fundupload.search_fundRepayment", param);
	}

	public Map<String,Object> searchById(Map<String, Object> map) {
		Map<String,Object> map2 = new HashMap<String, Object>();
		map2.put("FI_R_UPLOAD_PATH_COUNT", Dao.selectList("fi.fundupload.searchF_byId", map));
		map2.put("FI_R_UPLOAD_FILE_JOIN", Dao.selectList("fi.fundupload.searchCH_byId", map));
		return map2;
	}
	
	public Page checkAll(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return PageUtil.getPage(param, "fi.fundupload.getSubList", "fi.fundupload.getSubCount");
	}

	public int addSubject(Map<String, Object> param) {
		return Dao.insert("fi.fundupload.addSubject", param);
		
	}
	public int updateSubject(Map<String, Object> param){
		return Dao.update("fi.fundupload.updateSubject", param);
	}
    public int delSubject(Map<String, Object> param){
    	
    	return Dao.delete("fi.fundupload.delSubject", param);
    }
}
