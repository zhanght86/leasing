package com.pqsoft.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.FileCopyUtils;

import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.util.FileCatalogUtil;

public class MoveDataAccessoryAction extends Action {
	
	@Override
	public Reply execute() {
		return null;
	}

	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "170017", email = "lichaohn@163.com", name = "韩建")
	public Reply moveDataAccessory_pro(){
		
		Map m = new HashMap();
		m.put("PAGE_BEGIN", 1);
		m.put("PAGE_END", 1000);
		int temp =1;
		List list = Dao.selectList("MoveDataAccessory.test_pro", m);
		while(list!=null&&!list.isEmpty()){
			for (int i = 0; i < list.size(); i++) {
				Map mp = (Map)list.get(i);
				FileCatalogUtil u = new FileCatalogUtil();
				String CATALOG_ID = u.getCatalogIdByPath(mp.get("CUST_NAME")+"", mp.get("CUST_ID")+"", mp.get("ATTACHMENT_TYPE")+"/"+mp.get("FILE_NAME2"));
				Map tm = new HashMap();
				tm.put("FILE_NAME", this.regex(mp.get("FILE_NAME")+"", "^[^\\.]+"));
				tm.put("FILE_TYPE", this.regex(mp.get("FILE_NAME")+"", "[^\\.]+$"));
				tm.put("CATALOG_ID", CATALOG_ID);
				tm.put("CREATE_DATE", mp.get("CREATE_DATE"));
				String ORIGINAL_PATH = File.separator+"fileservice"+File.separator+
						"stleasing"+File.separator+"sflcgood"+File.separator+"picture"+File.separator+
						mp.get("CUST_NAME")+"_"+mp.get("CUST_ID")
						+File.separator+mp.get("PROJ_ID")+File.separator+mp.get("ATTACHMENT_TYPE")+File.separator+mp.get("FILE_NAME2")
						+File.separator+mp.get("FILEPATH");
				tm.put("ORIGINAL_PATH", ORIGINAL_PATH);
				Dao.insert("MoveDataAccessory.insert", tm);
				File in = new File(File.separator+"fileservice"+File.separator+"stleasing"+File.separator+"materials"+File.separator+mp.get("FILEPATH"));
				File out = new File(ORIGINAL_PATH);
				File parent = out.getParentFile(); 
				if(parent!=null&&!parent.exists()){
					parent.mkdirs();
				} 
				try {
					FileCopyUtils.copy(in, out);
				} catch (IOException e) {
					e.printStackTrace();
					//抛异常的时候往数据迁移临时表里面插入记录
					Map mm = new HashMap();
					mm.put("PROJECT_ID", mp.get("PROJ_ID"));
					mm.put("FILE_NAME", mp.get("FILEPATH"));
					mm.put("REMARK", ORIGINAL_PATH);
					Dao.insert("MoveDataAccessory.insertMoveDataAccessory",mm);
					System.out.println("--------------------------------文件出错----------------------------"+ORIGINAL_PATH);
				}
				System.out.println("-----------------------------------------------------------------------------------???>图像采集迁移进度"+((temp-1)*1000+i+1));
				System.out.println(ORIGINAL_PATH);
			}
			Dao.commit();
			Dao.close();
			m.put("PAGE_BEGIN", 1000*temp+1);
			m.put("PAGE_END", 1000*(temp+1));
			temp++;
			list = Dao.selectList("MoveDataAccessory.test", m);
		}
		
		
		System.out.println("-----------------???>图像采集迁移进度------------>完毕！！！！！！");
		return new ReplyHtml("<script type=\"text/javascript\">alert(\"操作成功！\")</script>");
	}
	
	
	/**
	 * 
	 * @Title: moveVoucher 
	 * @Description: TODO() 
	 * @return 
	 * @return Reply 
	 * @throws 
	 */
	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "110", email = "chengld@strongflc.com", name = "程龙达")
	public Reply moveVoucher(){
		Map m = new HashMap();
		List list = Dao.selectList("MoveDataAccessory.moveVoucher", m);
//		whiled(list!=null ){
			for (int i = 0; i < list.size(); i++) {
				Map mm = (Map) list.get(i);
				String proj_id = mm.get("PROJECT_ID").toString();
				String GUARAN_NAME = mm.get("GUARAN_NAME").toString();
				if(GUARAN_NAME.split(",").length>1){
					String s[] = GUARAN_NAME.split(",");
					System.out.println(s);
					for(int j = 0 ; j < s.length;j++){
						Map temp = new HashMap();
						temp.put("PROJECT_ID", proj_id);
						temp.put("GUARAN_NAME",s[j]);
						Dao.insert("MoveDataAccessory.insertVoucher", temp);
					}
				}else{
					Map temp = new HashMap();
					temp.put("PROJECT_ID", proj_id);
					temp.put("GUARAN_NAME",GUARAN_NAME);
					Dao.insert("MoveDataAccessory.insertVoucher", temp);
				}
				Dao.commit();
			}
//		}
		
		return new ReplyHtml("<script type=\"text/javascript\">alert(\"操作成功！\")</script>");
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "170017", email = "lichaohn@163.com", name = "韩建")
	public Reply moveDataAccessory(){
		
		Map m = new HashMap();
		m.put("PAGE_BEGIN", 1);
		m.put("PAGE_END", 1000);
		int temp =1;
		List list = Dao.selectList("MoveDataAccessory.test", m);
		while(list!=null&&!list.isEmpty()){
			for (int i = 0; i < list.size(); i++) {
				Map mp = (Map)list.get(i);
				FileCatalogUtil u = new FileCatalogUtil();
				String CATALOG_ID = u.getCatalogIdByPath(mp.get("CUST_NAME")+"", mp.get("CUST_ID")+"", mp.get("ATTACHMENT_TYPE")+"/"+mp.get("FILE_NAME2"));
				Map tm = new HashMap();
				tm.put("FILE_NAME", this.regex(mp.get("FILE_NAME")+"", "^[^\\.]+"));
				tm.put("FILE_TYPE", this.regex(mp.get("FILE_NAME")+"", "[^\\.]+$"));
				tm.put("CATALOG_ID", CATALOG_ID);
				tm.put("CREATE_DATE", mp.get("CREATE_DATE"));
				String ORIGINAL_PATH = File.separator+"fileservice"+File.separator+
						"stleasing"+File.separator+"sflcgood"+File.separator+"picture"+File.separator+
						mp.get("CUST_NAME")+"_"+mp.get("CUST_ID")
						+File.separator+mp.get("PROJ_ID")+File.separator+mp.get("ATTACHMENT_TYPE")+File.separator+mp.get("FILE_NAME2")
						+File.separator+mp.get("FILEPATH");
				tm.put("ORIGINAL_PATH", ORIGINAL_PATH);
				Dao.insert("MoveDataAccessory.insert", tm);
				File in = new File(File.separator+"fileservice"+File.separator+"stleasing"+File.separator+"materials"+File.separator+mp.get("FILEPATH"));
				File out = new File(ORIGINAL_PATH);
				File parent = out.getParentFile(); 
				if(parent!=null&&!parent.exists()){
					parent.mkdirs();
				} 
				try {
					FileCopyUtils.copy(in, out);
				} catch (IOException e) {
					e.printStackTrace();
					//抛异常的时候往数据迁移临时表里面插入记录
					Map mm = new HashMap();
					mm.put("PROJECT_ID", mp.get("PROJ_ID"));
					mm.put("FILE_NAME", mp.get("FILEPATH"));
					mm.put("REMARK", ORIGINAL_PATH);
					Dao.insert("MoveDataAccessory.insertMoveDataAccessory",mm);
					System.out.println("--------------------------------文件出错----------------------------"+ORIGINAL_PATH);
				}
				System.out.println("-----------------------------------------------------------------------------------???>图像采集迁移进度"+((temp-1)*1000+i+1));
				System.out.println(ORIGINAL_PATH);
			}
			Dao.commit();
			Dao.close();
			m.put("PAGE_BEGIN", 1000*temp+1);
			m.put("PAGE_END", 1000*(temp+1));
			temp++;
			list = Dao.selectList("MoveDataAccessory.test", m);
		}
		
		
		System.out.println("-----------------???>图像采集迁移进度------------>完毕！！！！！！");
		return new ReplyHtml("<script type=\"text/javascript\">alert(\"操作成功！\")</script>");
	}
	public String regex(String str,String reg){
		 Pattern pattern = Pattern.compile(reg);  
		 Matcher mat= pattern.matcher(str);  
		 String res = "";
		 while (mat.find()) {  
			 res+=mat.group();
		 }  
		return res;
	}
	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "170017", email = "lichaohn@163.com", name = "韩建")
	public Reply fil_project_file(){
		Dao.insert("MoveDataAccessory.OLD_BASE_FILE_DICT");
		Dao.insert("MoveDataAccessory.OLD_CUST_EWLEP_INFO");
		Dao.insert("MoveDataAccessory.OLD_CUST_INFO");
		Dao.insert("MoveDataAccessory.OLD_FILE_UPLOAD_INFO");
		Dao.insert("MoveDataAccessory.OLD_FILE_UPLOAD_INFO_DETAIL");
		Dao.insert("MoveDataAccessory.OLD_JB_CSXX");
		Dao.insert("MoveDataAccessory.OLD_PROJ_INFO");
		Dao.selectOne("MoveDataAccessory.fil_project_file");
		System.out.println("-----------------fil_project_file操作成功！------------------------------->");
		return new ReplyHtml("<script type=\"text/javascript\">alert(\"操作成功！\")</script>");
	}
	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "170017", email = "lichaohn@163.com", name = "韩建")
	public Reply FIL_PROJECT_SCHEME2(){
		List<Object> list = Dao.selectList("MoveDataAccessory.FIL_PROJECT_SCHEME2");
		int t = list.size();
		for (int i = 0; i < t; i++) {
			try {
				Map m= (Map)list.get(i);
				Map<Object,Object> map = this.resStrArray(m);
				for(Map.Entry<Object, Object> entry: map.entrySet()) {
					Map rm = new HashMap();
					String key = (String)entry.getKey();
					if(!"PROJ_ID".equals(key)){
						if(key.startsWith("P")&&key.length()<4){
							List rl = (List)entry.getValue();
							rm.put("KEY_NAME_ZN", rl.get(0));
							rm.put("KEY_NAME_EN", rl.get(1));
							rm.put("VALUE_STR", rl.get(2));
						}
						rm.put("PROJECT_ID", m.get("PROJ_ID"));
						Dao.insert("MoveDataAccessory.INSERT_FIL_PROJECT_SCHEME",rm);
					}
				}
				Dao.commit();
				Dao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("-------------项目方案插入成功-------------------->>>>"+i);
		}
		System.out.println("-----------------FIL_PROJECT_SCHEME操作成功！------------------------------->");
		return new ReplyHtml("<script type=\"text/javascript\">alert(\"操作成功！\")</script>");
	}
	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "170017", email = "lichaohn@163.com", name = "韩建")
	public Reply FIL_PROJECT_SCHEME3(){
		List<Object> list = Dao.selectList("MoveDataAccessory.FIL_PROJECT_SCHEME3");
		int t = list.size();
		for (int i = 0; i < t; i++) {
			try {
				Map m= (Map)list.get(i);
				Map<Object,Object> map = this.resStrArray(m);
				for(Map.Entry<Object, Object> entry: map.entrySet()) {
					Map rm = new HashMap();
					String key = (String)entry.getKey();
					if(!"PROJ_ID".equals(key)){
						if(key.startsWith("P")&&key.length()<4){
							List rl = (List)entry.getValue();
							rm.put("KEY_NAME_ZN", rl.get(0));
							rm.put("KEY_NAME_EN", rl.get(1));
							rm.put("VALUE_STR", rl.get(2));
						}
						rm.put("PROJECT_ID", m.get("PROJ_ID"));
						Dao.insert("MoveDataAccessory.INSERT_FIL_PROJECT_SCHEME",rm);
					}
				}
				Dao.commit();
				Dao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("-------------项目方案插入成功-------------------->>>>"+i);
		}
		System.out.println("-----------------FIL_PROJECT_SCHEME操作成功！------------------------------->");
		return new ReplyHtml("<script type=\"text/javascript\">alert(\"操作成功！\")</script>");
	}
	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "170017", email = "lichaohn@163.com", name = "韩建")
	public Reply FIL_PROJECT_SCHEME1(){
		List<Object> list = Dao.selectList("MoveDataAccessory.FIL_PROJECT_SCHEME1");
		int t = list.size();
		for (int i = 0; i < t; i++) {
			try {
				Map m= (Map)list.get(i);
				Map<Object,Object> map = this.resStrArray(m);
				for(Map.Entry<Object, Object> entry: map.entrySet()) {
					Map rm = new HashMap();
					String key = (String)entry.getKey();
					if(!"PROJ_ID".equals(key)){
						if(key.startsWith("P")&&key.length()<4){
							List rl = (List)entry.getValue();
							rm.put("KEY_NAME_ZN", rl.get(0));
							rm.put("KEY_NAME_EN", rl.get(1));
							rm.put("VALUE_STR", rl.get(2));
						}
						rm.put("PROJECT_ID", m.get("PROJ_ID"));
						Dao.insert("MoveDataAccessory.INSERT_FIL_PROJECT_SCHEME",rm);
					}
				}
				Dao.commit();
				Dao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("-------------项目方案插入成功-------------------->>>>"+i);
		}
		System.out.println("-----------------FIL_PROJECT_SCHEME操作成功！------------------------------->");
		return new ReplyHtml("<script type=\"text/javascript\">alert(\"操作成功！\")</script>");
	}
	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "170017", email = "lichaohn@163.com", name = "韩建")
	public Reply FIL_PROJECT_SCHEME(){
		List<Object> list = Dao.selectList("MoveDataAccessory.FIL_PROJECT_SCHEME");
		int t = list.size();
		for (int i = 0; i < t; i++) {
			try {
				Map m= (Map)list.get(i);
				Map<Object,Object> map = this.resStrArray(m);
				for(Map.Entry<Object, Object> entry: map.entrySet()) {
					Map rm = new HashMap();
					String key = (String)entry.getKey();
					if(!"PROJ_ID".equals(key)){
						if(key.startsWith("P")&&key.length()<4){
							List rl = (List)entry.getValue();
							rm.put("KEY_NAME_ZN", rl.get(0));
							rm.put("KEY_NAME_EN", rl.get(1));
							rm.put("VALUE_STR", rl.get(2));
						}
						rm.put("PROJECT_ID", m.get("PROJ_ID"));
						Dao.insert("MoveDataAccessory.INSERT_FIL_PROJECT_SCHEME",rm);
					}
				}
					Dao.commit();
					Dao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("-------------项目方案插入成功-------------------->>>>"+i);
		}
		System.out.println("-----------------FIL_PROJECT_SCHEME操作成功！------------------------------->");
		return new ReplyHtml("<script type=\"text/javascript\">alert(\"操作成功！\")</script>");
	}
	
	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "170017", email = "lichaohn@163.com", name = "韩建")
	public Reply update_seq(){
		List<Map<String, String>> list = MoveDataAccessoryUtil.getList();
		for (int i=0;i<list.size();i++) {
			Map<String, String> map = list.get(i);
			try {
				Integer start = Dao.selectInt("MoveDataAccessory.select_seq_max_value", map);
				if(start!=null&&start!=0){
					Dao.delete("MoveDataAccessory.delete_seq", map);
					map.put("start", start+1+"");
					Dao.insert("MoveDataAccessory.update_seq", map);
					System.out.println("-----------------更新序列-------------"+map.get("seq_name")+"------------------>"+i);
				}
			} catch (Exception e) {
				
				System.out.println("------失败-----------更新序列-------------"+map.get("seq_name")+"------------------>"+i);
			} 
			
			
		}
		System.out.println("-----------------更新序列操作成功！------------------------------->");
		return new ReplyHtml("<script type=\"text/javascript\">alert(\"操作成功！\")</script>");
	}
	
	
	@SuppressWarnings("unused")
	public Map resStrArray(Map<Object, Object> m) throws Exception{
		for(Map.Entry<Object, Object> entry: m.entrySet()) {
			 //System.out.print(entry.getKey() + ":" + entry.getValue() + "\t");
			String key = entry.getKey()+"";
			if(key.startsWith("P")&&key.length()<4){
				String [] temp = (String [])this.getClass().getDeclaredField(key).get(this);
				List list1 = Arrays.asList(temp);
				List list = new ArrayList(list1);
				list.add(entry.getValue());
				m.put(key, list);
			}
		}
		return m;
	}
	String [] P1 = {"租赁包","RENTAL_PACKAGE"};
	String [] P3 = {"牌抵挂","PLEDGE_STATUS"};
	String [] P4 = {"放款政策","PAYMENT_STATUS"};
	String [] P5 = {"销售模式","SALE_MODEL"};
	String [] P6 = {"还款政策","PAY_WAY"};
	String [] P7 = {"动力类型","POWER"};
	String [] P8 = {"商务政策使用范围","APPLIY_SCOPE"};
	String [] P9 = {"留购价款","PURCHASE_PRICE"};
	String [] P10 = {"首期付款方式","FIRST_PAYMENT_TYPE"};
	String [] P11 = {"租金付款方式","RENT_PAYMENT_TYPE"};
	String [] P12 = {"盗抢险","PILFER"};
	String [] P13 = {"管理服务费","MANAGEMENT_SERVICE_PRICE"};
	String [] P14 = {"担保方式","GUARANTEE"};
	String [] P15 = {"保险方式","INSURANCE"};
	String [] P16 = {"起租比例","START_PERCENT"};
	String [] P17 = {"起租租金","START_MONEY"};
	String [] P18 = {"客户保证金比例","DEPOSIT_PERCENT"};
	String [] P19 = {"保证金","DEPOSIT_MONEY"};
	String [] P20 = {"保险费率","INSURANCE_PERCENT"};
	String [] P21 = {"保险费","INSURANCE_MONEY"};
	String [] P22 = {"担保费比例","GUARANTEE_PERCENT"};
	String [] P23 = {"担保费","GUARANTEE_MONEY"};
	String [] P24 = {"DB保证金比例","DB_BAIL__PERCENT"};
	String [] P25 = {"DB保证金","DB_BAIL__MONEY"};
	String [] P26 = {"三者险年保费","THREE_PARTY_INSURANCEE"};
	String [] P27 = {"首付款来源","SOURCE_DOWN_PAYMENT"};
	String [] P28 = {"是否再融租","REFINSNCING_RENT"};
	String [] P29 = {"担保模式","GUARANTEE_MODEL"};
	String [] P30 = {"施工类型","TYPE_CONSTRUCTION"};
	String [] P31 = {"工程名称","PROJECT_NAME"};
	String [] P32 = {"工程性质","ENGINEERING_PROPERTIES"};
	String [] P33 = {"承包性质","NATURE_CONTRACT"};
	String [] P34 = {"年营业收入(万)","ANNUAL_REVENUE"};
	String [] P35 = {"台班费/月(万)","MACHINE-SHIFT_COST"};
	String [] P37 = {"资料后补","DATE_COMPLEMENT"};
	String [] P38 = {"还租依赖工程款","RENT_DEPEND_PROJECT"};
	String [] P39 = {"租金开票方式","RENT_WAY_INVOICE"};
	String [] P40 = {"第三方担保","THIRD_PARTY_GUARANTEES"};
	String [] P41 = {"实物抵押","FAUSTPFAND"};
	String [] P42 = {"放款账户","LOAN_ACCOUNT_NAME"};
	String [] P43 = {"匹配项目编号","MATCH_ITEM_NO"};
	String [] P44 = {"是否异地施工","WHETHER_DIFFERENT_CONSTRUCTION"};
	String [] P45 = {"施工规模(万)","SIZE_CONSTRUCTION"};
	String [] P46 = {"开工日期","STARTING_DATE_PROJECT"};
	String [] P47 = {"每月债务(万)","MONTHLY_PAYMENT"};
	String [] P48 = {"是否异地租赁","WHETHER_DIFFERENT_LEASE"};
	String [] P50 = {"SFLC费用支付","SFLC_PAYMENT"};
	String [] P51 = {"监控设备","MONITORING_EQUIPMENT"};
	String [] P53 = {"抵押物","GUARANTEE_ENTITY"};
	String [] P54 = {"所属行业","INDUSTRY_INVOLVED"};
	String [] P55 = {"施工地点","JOB_LOCATION"};
	String [] P56 = {"工程期限(月)","PERIOD_CONSTRUCTION"};
	String [] P57 = {"每月收入(万)","MONTHLY_INCOME"};
	String [] P58 = {"挂靠公司","AFFILIATED_COMPANY"};

}
