package com.pqsoft.cashDeposit.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.bpm.service.SystemMail;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.Util;

public class CashDepositService {

	private String xmlPath="cashDepositK.";
	/**
	 * 客户保证金管理页面数据查询(分页显示)
	 * @author yx
	 * @date 2015-03-01
	 * @param map
	 * @return
	 */
	public Page toMgCashDeposit(Map<String,Object> map){
		map.put("BZJ", "客户保证金");
		return PageUtil.getPage(map, xmlPath+"toMgCashDeposit",xmlPath+"toMgCashDepositC");
	} 
	
	/**
	 * 获取项目方案客户名称和开户行
	 * @author yx
	 * @date 2015-03-01
	 * @param map
	 * @return
	 */
	public Object toFindProjectScheme(Map<String,Object> map){
		return Dao.selectOne(xmlPath+"toFindProjectScheme", map);
	}
	
	/**
	 * 查看项目方案
	 * @param map
	 * @return
	 */
	public Object toFindScheme(Map<String,Object> map){
		Map<String,Object> proScheme = new HashMap<String,Object>();
		map.put("KHBZJ","客户保证金");
		Map<String,Object> m = Dao.selectOne(xmlPath+"toFindScheme", map);//查看报价方案
		Map<String,Object> scheme = Dao.selectOne(xmlPath+"toFindScheme1",m);//根据方案编号查看方案中客户保证金和供应商保证金的计算方式， 即判断是按融资额计算还是按设备额计算		
//		Dao.getClobTypeInfo2(m, "SCHEME_CLOB");
//		JSONArray SCHEME_CLOB=JSONArray.fromObject(m.get("SCHEME_CLOB"));
//		for(Object obj : SCHEME_CLOB){
//			Map<String,Object> scheme_ = (Map<String, Object>) obj;
//			//proScheme.put("EarningBase", scheme.get("KEY_NAME_ZN"))
//			System.out.println(scheme.get("CALCULATE")+"+============"+scheme+"=yx==========+"+scheme.get("KEY_NAME_ZN"));
//		}
		
		double bzjqsz = 0.0;//保证金起始收益
		double bzjsy = 0.0; //保证金收益
		double bzjze = Double.parseDouble(map.get("BAOZHENGJIN").toString()); //保证金总额
		if(scheme!=null){
			if(scheme.get("BZJSYQSBL")!=null){//当保证金起始收益率不为空时，计算保证金起始收益率
				//保证金起始收益=设备金额*保证金起始收益率
				bzjqsz = Double.parseDouble(m.get("LEASE_TOPRIC").toString())*Double.parseDouble(scheme.get("BZJSYQSBL").toString());
				//保证金收益=（保证金-保证金起始收益）*保证金收益率
				bzjsy  = (Double.parseDouble(map.get("BAOZHENGJIN").toString()) - bzjqsz) * Double.parseDouble(scheme.get("BZJSYL").toString());
				//保证金总额=保证金+收益
				bzjze +=bzjsy;
			}
			proScheme.put("BZJSYQSBL", Util.formatDouble2(Double.parseDouble(scheme.get("BZJSYQSBL")==null?"0":scheme.get("BZJSYQSBL").toString())));//保金金起始收益率
			proScheme.put("BZJSYQSBL", Util.formatDouble2(Double.parseDouble(scheme.get("BZJSYQSBL")==null?"0":scheme.get("BZJSYL").toString())));//保金金收益率
		}
		proScheme.put("bzjqsz", Util.formatDouble2(bzjqsz));//保证金起始收益
		proScheme.put("bzjsy", Util.formatDouble2(bzjsy));//保证金收益
		proScheme.put("bzjze", Util.formatDouble2(bzjze));//保证金总额
		
		return proScheme;
	}
	
	/**
	 * 保证金退款申请对支付表状态进行调整
	 * @author yx
	 * @date 2015-03-02 
	 * @param map
	 * @return
	 */
	public int doUpRentStatus(Map<String,Object> map){
		return Dao.update(xmlPath+"doUpRentStatus", map);
	}
	
	/**
	 * 保存退款申请
	 * @author yx
	 * @date 2015-03-02
	 * @param map
	 * @return
	 */
	public int doSaveReturn(Map<String,Object> map){
		return Dao.insert(xmlPath+"doAddReturnApp", map);
	}
	 
	/**
	 * 修改退款表中的流程id
	 *  @author yx
	 * @date 2015-03-02
	 * @param map
	 * @return
	 */
	public int doUpReturnApp(Map<String,Object> map){
		return Dao.update(xmlPath+"doUpReturnApp", map);
	}
	
	/**
	 * 查看退款申请表
	 * @param map
	 * @return
	 */
	public Object toViewReturnApp(Map<String,Object> map){
		return Dao.selectOne(xmlPath+"toViewReturnApp", map);
	}

	/*
	 * 查询客户保证金数据
	 */
	public Page search_BzjCD_All(Map<String, Object> map) {
		
		return PageUtil.getPage(map, xmlPath+"search_BzjCD_All_List", xmlPath+"search_BzjCD_All_Count");
	}
	
	public List<Map<String,Object>> getBzjSYRecord(Map<String, Object> map) {
		return Dao.selectList(xmlPath+"getBzjSYRecordList", map);
	}

	public List<Map<String, Object>> getBzjDetailData(Map<String, Object> map) {
		
		return Dao.selectList(xmlPath+"selectWSBzjDetail", map);
	}

	public 	List getApplayPageData(Map<String, Object> map) {
		
		return Dao.selectList(xmlPath+"getApplayPageData", map);
	}

	public List<Map<String, Object>> getDataByPaylist_Code(
			Map<String, Object> mapTem) {
		// TODO Auto-generated method stub
		return Dao.selectList(xmlPath+"getDataByPaylist_Code", mapTem);
	}
	
	/*
	 * 发送邮件到可以做期末冲抵的客户CashDepositJobBean
	 */
	public void sendEmailToClient(){
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map<String,Object>> list = Dao.selectList(xmlPath+"search_BzjCD_List", map);;
		for(int i=0;i<list.size();i++){
			Map<String,Object> map_tem = list.get(i);
			String address = map_tem.get("EMAIL").toString();
			String manager_name = map_tem.get("MANAGER_NAME").toString();//客户经理
			String client_name = map_tem.get("NAME").toString();//客户名
			String content = "通知："+manager_name+",你的客户-"+client_name+"可以做期末冲抵了，请您注意提醒";
			String title = "客户保证金期末冲抵";
			try {
				SystemMail.sendMail(address, title, content);
				//修改 fil_rent_plan_head表的 TX_STATUS 为 2 已发送邮箱状态
				System.out.println("----dd---");
				Dao.update("cashDepositK.update_frph_TX_STATUS", map_tem);
				//往fi_bzj_record表插入一条数据
				map_tem.put("REMARK", "已发邮件到"+manager_name);
				map_tem.put("TYPE", "提醒");
				Dao.insert("cashDepositK.insert_fbr_record", map_tem);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
