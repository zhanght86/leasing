package com.pqsoft.fiForRed.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.pqsoft.overdue.service.OverdueService;
import com.pqsoft.rentWrite.service.rentWriteNewService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.StringUtils;

/**
 * 资金冲红作废
 * 
 * @author King 2013-11-17
 */
public class FiForRedService {
	private String namespace = "fiForRed.";

	/**
	 * 查询冲红申请列表数据
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-11-17
	 */
	public Page doShowMgFiForRedApp(Map<String, Object> map) {
		return PageUtil.getPage(map, namespace + "doShowMgFiForRedApp", namespace + "doShowMgFiForRedAppCount");
	}
	public Page doShowMgFiForRedApp2(Map<String, Object> m) {
		m.put("FI_STATUS", 2);
		return PageUtil.getPage(m, namespace+"query_FI_FUND_HEAD", namespace+"query_FIFUND_HEAD_count");
	}

	/**
	 * 插入冲红或作废申请数据
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-11-17
	 */
	public int doAddFiForRedApp(Map<String, Object> map) {
		map.put("CREATE_CODE", Security.getUser().getCode());
		map.put("RED_STATUS", "1");
		map.put("STATUS", "未处理");
		Dao.update(namespace + "updateFundDetailRedStatus", map);
		return Dao.insert(namespace + "doAddFiForRedApp", map);
	}

	/**
	 * 查询冲红或作废审核列表
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-11-17
	 */
	public Page doShowMgFiForRedConfirm(Map<String, Object> map) {
		return PageUtil.getPage(map, namespace + "doShowMgFiForRedConfirm", namespace + "doShowMgFiForRedConfirmCount");
	}

	/**
	 * 冲红审批驳回
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-11-18
	 */
	public int doUpdatefiForRedConfirmNotPass(Map<String, Object> map) {
		map.put("RED_STATUS", '3');
		Dao.update(namespace + "updateFundDetailRedStatus", map);
		return Dao.update(namespace + "fiForRedConfirmNotPass", map);
	}

	/**
	 * 冲红作废确认
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-11-18
	 */         
	public void doUpdateFiForRedConfirmPass(String IDS) {
		String ids[] = IDS.split(",");
		OverdueService oService = new OverdueService();
		for (String FR_ID : ids) {
			String F_FUND_ID = Dao.getSequence("SEQ_FUND_HEAD");
			System.out.println(F_FUND_ID+"-------F_FUND_ID---------");
			// 更新begining实收字段
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("FR_ID", FR_ID);//冲红作废记录表ID
			boolean wyjflag = true;
			Map<String, Object> moneyMap = (Map<String, Object>) (null == Dao.selectOne(namespace + "doQueryMoney", m) ? new HashMap<String, Object>()
					: Dao.selectOne(namespace + "doQueryMoney", m));
			if (!moneyMap.isEmpty() && moneyMap.size() > 0 && !StringUtils.isBlank(moneyMap.get("D_BEGING_ID"))) {
				// 虚拟核销判断 D_BEGING_ID 指的是应收表ID
				Object FI_FLAG = moneyMap.get("FI_FLAG");
				// 虚拟 
				if ("16".equals(FI_FLAG) || "17".equals(FI_FLAG)) {
					Dao.update(namespace + "doUpdateBegining_xn", m);
				} else {
					// 利息 本金已核销
					Dao.update(namespace + "doUpdateBegining", m);
				}
				moneyMap.put("ZJ", "ZJ");
				// 更新违约金表  D_PAY_CODE--支付表   PERIOD--期次
				if (!StringUtils.isBlank(moneyMap.get("D_PAY_CODE")) && !StringUtils.isBlank(moneyMap.get("PERIOD")))
					Dao.update(namespace + "updateOverdue", moneyMap);
			} else {
				// 违约金已经收取 减掉冲红作废的部分
				if (!StringUtils.isBlank(moneyMap.get("D_PAY_CODE")) && !StringUtils.isBlank(moneyMap.get("PERIOD")))
					Dao.update(namespace + "updateOverdue", moneyMap);
				wyjflag = false;
			}
			//回写资金到上传来款
			if(!moneyMap.isEmpty() && moneyMap.size() > 0 && !StringUtils.isBlank(moneyMap.get("FUND_ID")) && !StringUtils.isBlank(moneyMap.get("D_RECEIVE_MONEY"))) {
				
				double D_RECEIVE_MONEY=Double.parseDouble(moneyMap.get("D_RECEIVE_MONEY").toString());
				Map mapFund=new HashMap();
				mapFund.put("D_RECEIVE_MONEY", moneyMap.get("D_RECEIVE_MONEY"));
				mapFund.put("FUND_ID", moneyMap.get("FUND_ID"));
				mapFund.put("FUND_FUNDCODE", Dao.selectOne("fi.fund.getFundCode"));
				//将原资金复制下来修改金额即可
				Dao.insert(namespace+"insertFundRed",mapFund);
			}
			
			
			// Dao.update(namespace+"doUpdateBegining", m);
			m.put("F_FUND_ID", F_FUND_ID);
			// 插入冲出来的负数金额到资金主表中 FI_FUND_HEAD
			m.put("APP_CREATE", "'冲红作废'");
			Dao.insert(namespace + "addRedFundHead", m);
			// fi_fund_detail表插入冲红负数据
			Dao.insert(namespace + "doAddFiForRedConfirm", m);

			Map<String, Object> mm = Dao.selectOne(namespace + "queryClientAndSupId", m);
			String FI_FLAG = mm.get("FI_FLAG") + "";
			String TYPE = null;
			if (FI_FLAG.equals("1") || FI_FLAG.equals("4") || FI_FLAG.equals("5") || FI_FLAG.equals("8")) {
				// 供应商
				// 2-供应商垫汇余款池
				TYPE = "2";
				mm.put("OWNER_ID", mm.get("SUP_ID"));
			} else if (FI_FLAG.equals("2") || FI_FLAG.equals("3") || FI_FLAG.equals("6")) {
				// 5-承租人余款池
				TYPE = "5";
				mm.put("OWNER_ID", mm.get("CLIENT_ID"));
			} else {
				// 7-待处理资金池
				TYPE = "7";
			}
			mm.put("TYPE", TYPE);
			mm.put("BASE_MONEY", mm.get("D_RECEIVE_MONEY"));
			mm.put("CANUSE_MONEY", mm.get("D_RECEIVE_MONEY"));
			mm.put("PAYER", mm.get("SYSTEM"));
			mm.put("SOURCE_ID", mm.get("FUND_ID"));
			Dao.insert(namespace + "doAddRedToPool", mm);
			m.put("STATUS", "已冲红");
			Dao.update(namespace + "updateRedStatus", m);

			if (wyjflag) {
				// 删除违约金  PAYLIST_CODE---租金计划编号
				oService.deleteByPayEAFDate(mm.get("PAYLIST_CODE") + "", moneyMap.get("D_REALITY_DATE") + "");
			} else {
				oService.deleteByPayAFDate(mm.get("PAYLIST_CODE") + "", moneyMap.get("D_REALITY_DATE") + "");
			}
			// 刷违约金
			String PAYLIST_CODE = mm.get("PAYLIST_CODE") + "";
			mm.put("PAY_CODE", PAYLIST_CODE);
			Dao.update("fi.overdue.upDueOne", mm);

			// 向核销中间表更新数据
			String PERIOD = null == mm.get("PERIOD") ? "0" : mm.get("PERIOD") + "";
			Map<String, Object> mapDate = new HashMap<String, Object>();
			mapDate.put("PAYLIST_CODE", PAYLIST_CODE);
			mapDate.put("BEGINNING_NUM", PERIOD);
			if (Integer.parseInt(PERIOD) >= 1) {
				// 如果是租金 则向中间表更新
				Dao.update("rentWriteNew.doPRC_BE_QJL_ONE",mapDate);

			}
			System.out.println(mm.get("D_RECEIVE_MONEY"));
			updateBeginning(PAYLIST_CODE, PERIOD);
			
			//查询该核销单下面所以本金判断是否为循环授信，是的话进行处理
			rentWriteNewService rentService=new rentWriteNewService();
			rentService.queryPaylistBJSX(F_FUND_ID);//金额为付的
			
			//反更支付表主表的已还期次和未款金额
			rentService.queryPaylistHG(F_FUND_ID);
			
			//查询FI_FUND_CODE=1 租金  =2 首付款
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("FR_ID",FR_ID);
			System.out.println(FR_ID+"---------p判断-----------");
			int FI_FUND_CODE = Dao.selectInt(namespace+"select_FI_FUND_CODE", map);
			System.out.println(FI_FUND_CODE+"-------FI_FUND_CODE--333--------");
			map.put("FUND_TYPE", FI_FUND_CODE);
			if(FI_FUND_CODE==1){
				//FI_FUND_CODE=1 租金  更新fi_fund 
				map.put("FUND_TYPE", 2);
				System.out.println("------FUND_ID---------");
				int FUND_ID = Dao.selectInt("fi.fund.seq_fi_fund_id", null);
				System.out.println(FUND_ID+"------FUND_ID-------");
				map.put("FUND_ID", FUND_ID);
				//修改fi_fund表的摘要 - 冲红  和 认款人- 操作人员  和 来款类型
				String userName = Security.getUser().getName();
				map.put("FUND_PIDENTIFY_PERSON",userName);
				map.put("FUND_DOCKET","冲红");
				Dao.update("fi.fund.update_fund_type", map);
			}else if(FI_FUND_CODE==2){
				//FI_FUND_CODE=2 首付款  更新fi_fund
				map.put("FUND_TYPE", 1);
				System.out.println("------FUND_ID---------");
				int FUND_ID = Dao.selectInt("fi.fund.seq_fi_fund_id", null);
				System.out.println(FUND_ID+"------FUND_ID-------");
				map.put("FUND_ID", FUND_ID);
				//修改fi_fund表的摘要 - 冲红  和 认款人- 操作人员  和 来款类型
				String userName = Security.getUser().getName();
				map.put("FUND_PIDENTIFY_PERSON",userName);
				map.put("FUND_DOCKET","还款资金冲红");
				Dao.update("fi.fund.update_fund_type", map);
			}
			
		}
		// return 1;
	}

	/**
	 * 非网银作废
	 * 
	 * @param IDS
	 * @author:King 2013-12-22
	 */
	public void doUpdateFiForRedConfirmPass_ZF2(String IDS) {
		String ids2[] = IDS.split(",");
		String ids[] = new String[ids2.length];//冲红作废记录表-ID集合
		String s1[] = new String[ids2.length];//冲红作废记录表-付款金额集合
		String s2[] = new String[ids2.length];//资金主表-id集合
		for(int i=0;i<ids2.length;i++){
			ids[i] = ids2[i].split("-")[0];
			s1[i] = ids2[i].split("-")[1].split("=")[0];
			s2[i] = ids2[i].split("-")[1].split("=")[1];
		}
		System.out.println(IDS+"-----"+ids[0]+"-----"+s1[0]+"-----"+s2[0]);
		System.out.println(s1.length+"--"+s2.length+"--"+ids2.length+"--"+ids.length+"--");
		StringBuffer sb = new StringBuffer();
		for(int a=0;a<s2.length;a++){
			for(int b=a+1;b<s2.length;b++){
				if(s2[a].equals(s2[b])){
					sb.append(b).append("-");
				}
			}
			sb.append(",");
		}
		String str = sb.substring(0, sb.length() - 1);
		System.out.println(sb.toString()+"---11----");
		System.out.println(sb.toString()+"-----sb------"+str+"---"+str.trim());
		String[] ss = str.split(",");
		StringBuffer sb2 = new StringBuffer();
		for(int c=0;c<ss.length;c++){
			if(ss[c].trim()!=null){
				String[] aa = ss[c].split("-");
				for(int d=0;d<aa.length;d++){
					sb2.append(aa[d]).append(",");
				}
			}
		}
		System.out.println(sb2.toString()+"------"+sb2+"==");
		String str2 = "";
		if(sb2.toString()!=null & sb2.toString()!=""){
			System.out.println(sb2.toString()+"==="+sb2.substring(0, sb2.length()-1));
			str2 = sb2.substring(0, sb2.length() - 1);
		}else{
			str2 = null;
		}
		System.out.println(str2+"-------str2>>>---------");
		OverdueService oService = new OverdueService();
		Map<String,Object> map_tem = new HashMap<String,Object>();
		//for (String FR_ID : ids) {
		for (int n=0;n<ids.length;n++) {
			// 更新begining实收字段
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("FR_ID", ids[n]);
			
			//当核销完成后根据核销单查询出该核销单有本金的所有支付表编号和期次判断是否为循环授信，如果是则资金回笼
			List list=Dao.selectList(namespace+"doQueryPaylistBJBack",m);
			for(int i=0;i<list.size();i++){
				Map mapDate=(Map)list.get(i);
				if(mapDate!=null && mapDate.get("PAYLIST_CODE") !=null && !mapDate.get("PAYLIST_CODE").equals("")){
					Dao.update(namespace+"updatSXBack",mapDate);
				}
			}
			
			boolean wyjflag = true;
			Map<String, Object> moneyMap = (Map<String, Object>) (null == Dao.selectOne(namespace + "doQueryMoney", m) ? new HashMap<String, Object>()
					: Dao.selectOne(namespace + "doQueryMoney", m));
			if (!moneyMap.isEmpty() && moneyMap.size() > 0 && !StringUtils.isBlank(moneyMap.get("D_BEGING_ID"))) {
				// 虚拟核销判断
				Object FI_FLAG = moneyMap.get("FI_FLAG");
				// 虚拟
				if ("16".equals(FI_FLAG) || "17".equals(FI_FLAG)) {
					Dao.update(namespace + "doUpdateBegining_xn", m);
				} else {
					// 利息 本金已核销
					Dao.update(namespace + "doUpdateBegining", m);
				}
				moneyMap.put("ZJ", "ZJ");
				// 更新违约金表
				if (!StringUtils.isBlank(moneyMap.get("D_PAY_CODE")) && !StringUtils.isBlank(moneyMap.get("PERIOD")))
					Dao.update(namespace + "updateOverdue", moneyMap);
			} else {
				// 违约金已经收取 清掉
				if (!StringUtils.isBlank(moneyMap.get("D_PAY_CODE")) && !StringUtils.isBlank(moneyMap.get("PERIOD")))
					Dao.update(namespace + "updateOverdue", moneyMap);
				wyjflag = false;
			}
			
			//回写资金到上传来款
			if(!moneyMap.isEmpty() && moneyMap.size() > 0 && !StringUtils.isBlank(moneyMap.get("FUND_ID")) && !StringUtils.isBlank(moneyMap.get("D_RECEIVE_MONEY"))) {
				
				double D_RECEIVE_MONEY=Double.parseDouble(moneyMap.get("D_RECEIVE_MONEY").toString());
				Map mapFund=new HashMap();
				mapFund.put("D_RECEIVE_MONEY", moneyMap.get("D_RECEIVE_MONEY"));
				mapFund.put("FUND_ID", moneyMap.get("FUND_ID"));
				mapFund.put("FUND_FUNDCODE", Dao.selectOne("fi.fund.getFundCode"));
				if(str2!=null){
					String[] bb = str2.split(",");
					int count = 0;
					for(int j=0;j<bb.length;j++){
						if((n+"").equals(bb[j])){
							count++;
						}
					}
					System.out.println(count+"------count------");
					
					if(count>0){
						mapFund.put("FUND_ID", map_tem.get(s2[n]));
						mapFund.put("D_RECEIVE_MONEY", s1[n]);
						System.out.println(s2[n]+"----s2n----"+map_tem.get(s2[n])+"--"+s1[n]);
						//更新资金主表
						Dao.update(namespace+"updateFundRed",mapFund);
					}else{
						//将原资金复制下来修改金额即可
						Dao.insert(namespace+"insertFundRed",mapFund);
						System.out.println(s2[n]+"-------s2-----");
						int FUND_ID = Dao.selectInt(namespace+"selectFund_ID", null);
						map_tem.put(s2[n], FUND_ID);
						System.out.println(s2[n]+"-------s2-----"+FUND_ID);
					}
				}else{
					Dao.insert(namespace+"insertFundRed",mapFund);
				}
				
			}
			

			// FI_FLAG=8表示保证金抵扣
			String FI_FLAG = moneyMap.get("FI_FLAG") + "";
			if (FI_FLAG.equals("8")) {
				// 保证金抵扣
				if (!StringUtils.isBlank(moneyMap.get("FA_POOL_ID")) && !StringUtils.isBlank(moneyMap.get("DETAIL_ID"))) {
					// 回更保证金池可用金额
					Dao.update(namespace + "updateBZJCHI", moneyMap);
					String FUND_ID = Dao.selectOne(namespace + "queryFUZUJIN", moneyMap);
					if (!StringUtils.isBlank(FUND_ID)) {
						// 删除冲红的数据
						Dao.delete(namespace + "delFUZIJIN_ACCOUNT", FUND_ID);
						Dao.delete(namespace + "delFUZIJIN_DETAIL", FUND_ID);
						Dao.delete(namespace + "delFUZIJIN", moneyMap);
					}
				}
			} else {
				// 删除池子中对应的资金
				Dao.delete(namespace + "doDelFundPool", m);
			}
			// 删除FI_FUND_ACCOUNT对应的数据
			Dao.delete(namespace + "doDelFundAccount", m);
			// 删除fi_fund_detail表中对应的数据
			Dao.delete(namespace + "doDelFundDetail", m);
			// 修改付款申请主表FI_FUND_HEAD的数据值
			if (!moneyMap.isEmpty() && moneyMap.size() > 0 && !StringUtils.isBlank(moneyMap.get("D_FUND_ID"))) {
				Dao.update(namespace + "updateFundHead", moneyMap);
				//查询FI_FUND_HEAD 的FI_REALITY_MONEY 实际付款金额
				int FI_REALITY_MONEY = Dao.selectInt(namespace + "selectFI_REALITY_MONEY", moneyMap);
				System.out.println(FI_REALITY_MONEY+"-------金额---------");
				//如果实际付款金额为0，查询明细项是否为空，若为空删除该记录
				if(FI_REALITY_MONEY==0){
					int count = Dao.selectInt(namespace+"selectDetailCount", moneyMap);
					if(count==0){
						Dao.delete(namespace+"delFundHeadById", moneyMap);
					}
				}
			}
			// 删除项目数量为0的申请单FI_FUND_HEAD
//			Dao.delete(namespace + "delFundHeadNUMForZERO");

			String PAYLIST_CODE = moneyMap.get("PAYLIST_CODE") + "";
			Map<String, Object> mm = new HashMap<String, Object>();
			mm.put("PAY_CODE", PAYLIST_CODE);

			if (wyjflag) {
				// 删除违约金
				oService.deleteByPayEAFDate(mm.get("PAYLIST_CODE") + "", moneyMap.get("D_REALITY_DATE") + "");
			} else {
				oService.deleteByPayAFDate(mm.get("PAYLIST_CODE") + "", moneyMap.get("D_REALITY_DATE") + "");
			}
			// 刷违约金
			Dao.update("fi.overdue.upDueOne", mm);

			// 向核销中间表更新数据
			String PERIOD = null == moneyMap.get("PERIOD") ? "0" : moneyMap.get("PERIOD") + "";
			Map<String, Object> mapDate = new HashMap<String, Object>();
			mapDate.put("PAYLIST_CODE", PAYLIST_CODE);
			mapDate.put("BEGINNING_NUM", PERIOD);
			if (Integer.parseInt(PERIOD) >= 1) {
				// 如果是租金 则向中间表更新
				Dao.update("rentWriteNew.doPRC_BE_QJL_ONE",mapDate);

			}
			m.put("STATUS", "已作废");
			Dao.update(namespace + "updateRedStatus", m);
			updateBeginning(PAYLIST_CODE, PERIOD);
			

			//查询FI_FUND_CODE=1 租金  =2 首付款
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("FR_ID",ids[n]);
			System.out.println(ids[n]+"---------p判断-----------");
			int FI_FUND_CODE = Dao.selectInt(namespace+"select_FI_FUND_CODE", map);
			System.out.println(FI_FUND_CODE+"-------FI_FUND_CODE--333--------");
			map.put("FUND_TYPE", FI_FUND_CODE);
			if(FI_FUND_CODE==1){
				//FI_FUND_CODE=1 租金  更新fi_fund 
				map.put("FUND_TYPE", 2);
				System.out.println("------FUND_ID---------");
				int FUND_ID = Dao.selectInt("fi.fund.seq_fi_fund_id", null);
				System.out.println(FUND_ID+"------FUND_ID-------");
				map.put("FUND_ID", FUND_ID);
				//修改fi_fund表的摘要 - 冲红  和 认款人- 操作人员  和 来款类型
				String userName = Security.getUser().getName();
				System.out.println(userName+"------------");
				map.put("FUND_PIDENTIFY_PERSON",userName);
				map.put("FUND_DOCKET","作废");
				Dao.update("fi.fund.update_fund_type", map);
			}else if(FI_FUND_CODE==2){
				//FI_FUND_CODE=2 首付款  更新fi_fund
				map.put("FUND_TYPE", 1);
				System.out.println("------FUND_ID---------");
				int FUND_ID = Dao.selectInt("fi.fund.seq_fi_fund_id", null);
				System.out.println(FUND_ID+"------FUND_ID-------");
				map.put("FUND_ID", FUND_ID);
				//修改fi_fund表的摘要 - 冲红  和 认款人- 操作人员  和 来款类型
				String userName = Security.getUser().getName();
				System.out.println(userName+"--------------");
				map.put("FUND_PIDENTIFY_PERSON",userName);
				map.put("FUND_DOCKET","还款资金作废");
				Dao.update("fi.fund.update_fund_type", map);
			}
			
		}
	}
	public void doUpdateFiForRedConfirmPass_ZF(List<Map<String,Object>> list_map) {
		OverdueService oService = new OverdueService();
		List<Map<String,Object>> list_tem = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map_elem : list_map){
			// 更新begining实收字段
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("FR_ID", map_elem.get("ID"));
			System.out.println(map_elem.get("ID")+"--"+map_elem.get("RECEIVE_MONEY")+"--"+map_elem.get("FI_FUND_ID"));
			//当核销完成后根据核销单查询出该核销单有本金的所有支付表编号和期次判断是否为循环授信，如果是则资金回笼
			List list=Dao.selectList(namespace+"doQueryPaylistBJBack",m);
			for(int i=0;i<list.size();i++){
				Map mapDate=(Map)list.get(i);
				if(mapDate!=null && mapDate.get("PAYLIST_CODE") !=null && !mapDate.get("PAYLIST_CODE").equals("")){
					Dao.update(namespace+"updatSXBack",mapDate);
				}
			}
			
			boolean wyjflag = true;
			Map<String, Object> moneyMap = (Map<String, Object>) (null == Dao.selectOne(namespace + "doQueryMoney", m) ? new HashMap<String, Object>()
					: Dao.selectOne(namespace + "doQueryMoney", m));
			if (!moneyMap.isEmpty() && moneyMap.size() > 0 && !StringUtils.isBlank(moneyMap.get("D_BEGING_ID"))) {
				// 虚拟核销判断
				Object FI_FLAG = moneyMap.get("FI_FLAG");
				// 虚拟
				if ("16".equals(FI_FLAG) || "17".equals(FI_FLAG)) {
					Dao.update(namespace + "doUpdateBegining_xn", m);
				} else {
					// 利息 本金已核销
					Dao.update(namespace + "doUpdateBegining", m);
				}
				moneyMap.put("ZJ", "ZJ");
				// 更新违约金表
				if (!StringUtils.isBlank(moneyMap.get("D_PAY_CODE")) && !StringUtils.isBlank(moneyMap.get("PERIOD")))
					Dao.update(namespace + "updateOverdue", moneyMap);
			} else {
				// 违约金已经收取 清掉
				if (!StringUtils.isBlank(moneyMap.get("D_PAY_CODE")) && !StringUtils.isBlank(moneyMap.get("PERIOD")))
					Dao.update(namespace + "updateOverdue", moneyMap);
				wyjflag = false;
			}
			
			//回写资金到上传来款
			if(!moneyMap.isEmpty() && moneyMap.size() > 0 && !StringUtils.isBlank(moneyMap.get("FUND_ID")) && !StringUtils.isBlank(moneyMap.get("D_RECEIVE_MONEY"))) {
				
				double D_RECEIVE_MONEY=Double.parseDouble(moneyMap.get("D_RECEIVE_MONEY").toString());
				Map mapFund=new HashMap();
				mapFund.put("D_RECEIVE_MONEY", moneyMap.get("D_RECEIVE_MONEY"));
				mapFund.put("FUND_ID", moneyMap.get("FUND_ID"));
				mapFund.put("FUND_FUNDCODE", Dao.selectOne("fi.fund.getFundCode"));
				if(list_tem!=null){
					int count = 0;
					for(Map<String,Object> map_list : list_tem){
						if(map_elem.get("FI_FUND_ID").equals(map_list.get("FI_FUND_ID"))){
							mapFund.put("FUND_ID", map_list.get("FUND_ID"));
							mapFund.put("D_RECEIVE_MONEY", map_elem.get("RECEIVE_MONEY"));
							count++;
							break;
						}
					}
					if(count>0){
						System.out.println(mapFund.get("FUND_ID")+"--"+mapFund.get("D_RECEIVE_MONEY")+"--"+map_elem.get("RECEIVE_MONEY"));
						//更新资金主表
						Dao.update(namespace+"updateFundRed",mapFund);
					}else{
						Dao.insert(namespace+"insertFundRed",mapFund);
						Map<String,Object> map_tem = new HashMap<String, Object>();
						int FUND_ID = Dao.selectInt(namespace+"selectFund_ID", null);
						//map_tem中放入资金表fund_id
						map_tem.put("FUND_ID", FUND_ID);
						System.out.println(map_tem.get("FUND_ID")+"------");
						//map_tem中放入资金主表id
						map_tem.put("FI_FUND_ID",map_elem.get("FI_FUND_ID"));
						list_tem.add(map_tem);
					}
				}else{
					Dao.insert(namespace+"insertFundRed",mapFund);
					Map<String,Object> map_tem = new HashMap<String, Object>();
					int FUND_ID = Dao.selectInt(namespace+"selectFund_ID", null);
					//map_tem中放入资金表fund_id
					map_tem.put("FUND_ID", FUND_ID);
					//map_tem中放入资金主表id
					map_tem.put("FI_FUND_ID",map_elem.get("FI_FUND_ID"));
					list_tem.add(map_tem);
					System.out.println(FUND_ID+"------FUND_ID------");
				}
				
			}
			
			
			// FI_FLAG=8表示保证金抵扣
			String FI_FLAG = moneyMap.get("FI_FLAG") + "";
			if (FI_FLAG.equals("8")) {
				// 保证金抵扣
				if (!StringUtils.isBlank(moneyMap.get("FA_POOL_ID")) && !StringUtils.isBlank(moneyMap.get("DETAIL_ID"))) {
					// 回更保证金池可用金额
					Dao.update(namespace + "updateBZJCHI", moneyMap);
					String FUND_ID = Dao.selectOne(namespace + "queryFUZUJIN", moneyMap);
					if (!StringUtils.isBlank(FUND_ID)) {
						// 删除冲红的数据
						Dao.delete(namespace + "delFUZIJIN_ACCOUNT", FUND_ID);
						Dao.delete(namespace + "delFUZIJIN_DETAIL", FUND_ID);
						Dao.delete(namespace + "delFUZIJIN", moneyMap);
					}
				}
			} else {
				// 删除池子中对应的资金
				Dao.delete(namespace + "doDelFundPool", m);
			}
			// 删除FI_FUND_ACCOUNT对应的数据
			Dao.delete(namespace + "doDelFundAccount", m);
			// 删除fi_fund_detail表中对应的数据
			Dao.delete(namespace + "doDelFundDetail", m);
			// 修改付款申请主表FI_FUND_HEAD的数据值
			if (!moneyMap.isEmpty() && moneyMap.size() > 0 && !StringUtils.isBlank(moneyMap.get("D_FUND_ID"))) {
				Dao.update(namespace + "updateFundHead", moneyMap);
				//查询FI_FUND_HEAD 的FI_REALITY_MONEY 实际付款金额
				int FI_REALITY_MONEY = Dao.selectInt(namespace + "selectFI_REALITY_MONEY", moneyMap);
				System.out.println(FI_REALITY_MONEY+"-------金额---------");
				//如果实际付款金额为0，查询明细项是否为空，若为空删除该记录
				if(FI_REALITY_MONEY==0){
					int count = Dao.selectInt(namespace+"selectDetailCount", moneyMap);
					if(count==0){
						Dao.delete(namespace+"delFundHeadById", moneyMap);
					}
				}
			}
			// 删除项目数量为0的申请单FI_FUND_HEAD
//			Dao.delete(namespace + "delFundHeadNUMForZERO");
			
			String PAYLIST_CODE = moneyMap.get("PAYLIST_CODE") + "";
			Map<String, Object> mm = new HashMap<String, Object>();
			mm.put("PAY_CODE", PAYLIST_CODE);
			
			if (wyjflag) {
				// 删除违约金
				oService.deleteByPayEAFDate(mm.get("PAYLIST_CODE") + "", moneyMap.get("D_REALITY_DATE") + "");
			} else {
				oService.deleteByPayAFDate(mm.get("PAYLIST_CODE") + "", moneyMap.get("D_REALITY_DATE") + "");
			}
			// 刷违约金
			Dao.update("fi.overdue.upDueOne", mm);
			
			// 向核销中间表更新数据
			String PERIOD = null == moneyMap.get("PERIOD") ? "0" : moneyMap.get("PERIOD") + "";
			Map<String, Object> mapDate = new HashMap<String, Object>();
			mapDate.put("PAYLIST_CODE", PAYLIST_CODE);
			mapDate.put("BEGINNING_NUM", PERIOD);
			if (Integer.parseInt(PERIOD) >= 1) {
				// 如果是租金 则向中间表更新
				Dao.update("rentWriteNew.doPRC_BE_QJL_ONE",mapDate);
				
			}
			m.put("STATUS", "已作废");
			Dao.update(namespace + "updateRedStatus", m);
			updateBeginning(PAYLIST_CODE, PERIOD);
			
			
			//查询FI_FUND_CODE=1 租金  =2 首付款
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("FR_ID",map_elem.get("ID"));
			System.out.println(map_elem.get("ID")+"---------p判断-----------");
			int FI_FUND_CODE = Dao.selectInt(namespace+"select_FI_FUND_CODE", map);
			System.out.println(FI_FUND_CODE+"-------FI_FUND_CODE--333--------");
			map.put("FUND_TYPE", FI_FUND_CODE);
			if(FI_FUND_CODE==1){
				//FI_FUND_CODE=1 租金  更新fi_fund 
				map.put("FUND_TYPE", 2);
				System.out.println("------FUND_ID---------");
				int FUND_ID = Dao.selectInt("fi.fund.seq_fi_fund_id", null);
				System.out.println(FUND_ID+"------FUND_ID-------");
				map.put("FUND_ID", FUND_ID);
				//修改fi_fund表的摘要 - 冲红  和 认款人- 操作人员  和 来款类型
				String userName = Security.getUser().getName();
				System.out.println(userName+"------------");
				map.put("FUND_PIDENTIFY_PERSON",userName);
				map.put("FUND_DOCKET","作废");
				Dao.update("fi.fund.update_fund_type", map);
			}else if(FI_FUND_CODE==2){
				//FI_FUND_CODE=2 首付款  更新fi_fund
				map.put("FUND_TYPE", 1);
				System.out.println("------FUND_ID---------");
				int FUND_ID = Dao.selectInt("fi.fund.seq_fi_fund_id", null);
				System.out.println(FUND_ID+"------FUND_ID-------");
				map.put("FUND_ID", FUND_ID);
				//修改fi_fund表的摘要 - 冲红  和 认款人- 操作人员  和 来款类型
				String userName = Security.getUser().getName();
				System.out.println(userName+"--------------");
				map.put("FUND_PIDENTIFY_PERSON",userName);
				map.put("FUND_DOCKET","还款资金作废");
				Dao.update("fi.fund.update_fund_type", map);
			}
		}
	}

	/**
	 * 冲红网银冲红确认
	 * 
	 * @param map
	 * @return
	 * @author:吴剑东 2013-12-08
	 */
	public void doUpdateFiForRedConfirmPassWY(String IDS) {
		String ids[] = IDS.split(",");
		OverdueService oService = new OverdueService();
		for (String FR_ID : ids) {
			String F_FUND_ID = Dao.getSequence("SEQ_FUND_HEAD");
			// 更新begining实收字段
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("FR_ID", FR_ID);
			boolean wyjflag = true;
			Map<String, Object> moneyMap = (Map<String, Object>) (null == Dao.selectOne(namespace + "queryPayCodeAndPeriod", m) ? new HashMap<String, Object>()
					: Dao.selectOne(namespace + "queryPayCodeAndPeriod", m));
			if (!moneyMap.isEmpty() && moneyMap.size() > 0 && !StringUtils.isBlank(moneyMap.get("D_BEGING_ID"))) {
				// 虚拟核销判断
				// Object BEGINNING_FLAG=moneyMap.get("BEGINNING_FLAG");
				// Object VINUAL_FLAG=moneyMap.get("VINUAL_FLAG");
				Object FI_FLAG = moneyMap.get("FI_FLAG");
				// 虚拟
				if ("16".equals(FI_FLAG) || "17".equals(FI_FLAG)) {
					Dao.update(namespace + "doUpdateBeginingForWY_xn", m);
				} else {
					// 利息 本金已核销
					Dao.update(namespace + "doUpdateBeginingForWY", m);
				}
				// if("3".equals(BEGINNING_FLAG)){
				// //虚拟核销
				// Dao.update(namespace+"doUpdateBeginingForWY_xn",m);
				// }else
				// if("1".equals(BEGINNING_FLAG)&&"1".equals(VINUAL_FLAG)){
				// //虚拟和实际都存在
				// Dao.update(namespace+"doUpdateBeginingForWY_xn",m);
				// Dao.update(namespace + "doUpdateBeginingForWY", m);
				// }else{
				// }
				moneyMap.put("ZJ", "ZJ");
				// 更新违约金表
				if (!StringUtils.isBlank(moneyMap.get("D_PAY_CODE")) && !StringUtils.isBlank(moneyMap.get("PERIOD")))
					Dao.update(namespace + "updateOverdue", moneyMap);
			} else {
				// 违约金已经收取 清掉
				if (!StringUtils.isBlank(moneyMap.get("D_PAY_CODE")) && !StringUtils.isBlank(moneyMap.get("PERIOD")))
					Dao.update(namespace + "updateOverdue", moneyMap);
				wyjflag = false;
			}

			Dao.update(namespace + "doUpdateBeginingForWY", m);
			m.put("FR_ID", FR_ID);
			m.put("F_FUND_ID", F_FUND_ID);
			// 插入冲出来的负数金额到资金主表中 FI_FUND_HEAD
			m.put("APP_CREATE", "'冲红作废'");
			Dao.insert(namespace + "addRedFundHeadWY", m);
			// fi_fund_detail表插入冲红负数据
			Dao.insert(namespace + "doAddFiForRedConfirmWY", m);

			Map<String, Object> mm = Dao.selectOne(namespace + "queryClientAndSupIdWY", m);
			String FI_FLAG = mm.get("FI_FLAG") + "";
			String TYPE = null;
			if (FI_FLAG.equals("1") || FI_FLAG.equals("4") || FI_FLAG.equals("5") || FI_FLAG.equals("8")) {
				// 供应商
				// 2-供应商垫汇余款池
				TYPE = "2";
				mm.put("OWNER_ID", mm.get("SUP_ID"));
			} else if (FI_FLAG.equals("2") || FI_FLAG.equals("3") || FI_FLAG.equals("6")) {
				// 5-承租人余款池
				TYPE = "5";
				mm.put("OWNER_ID", mm.get("CLIENT_ID"));
			} else {
				// 7-待处理资金池
				TYPE = "7";
			}
			mm.put("TYPE", TYPE);
			mm.put("BASE_MONEY", mm.get("D_RECEIVE_MONEY"));
			mm.put("CANUSE_MONEY", mm.get("D_RECEIVE_MONEY"));
			mm.put("PAYER", mm.get("SYSTEM"));
			mm.put("SOURCE_ID", mm.get("FUND_ID"));
			Dao.insert(namespace + "doAddRedToPool", mm);

			if (wyjflag) {
				// 删除违约金
				oService.deleteByPayEAFDate(mm.get("PAYLIST_CODE") + "", moneyMap.get("D_REALITY_DATE") + "");
			} else {
				oService.deleteByPayAFDate(mm.get("PAYLIST_CODE") + "", moneyMap.get("D_REALITY_DATE") + "");
			}

			// 刷违约金
			String PAYLIST_CODE = moneyMap.get("PAYLIST_CODE") + "";
			mm.put("PAY_CODE", PAYLIST_CODE);
			Dao.update("fi.overdue.upDueOne", mm);

			// 向核销中间表更新数据
			String PERIOD = null == moneyMap.get("PERIOD") ? "0" : moneyMap.get("PERIOD") + "";
			Map<String, Object> mapDate = new HashMap<String, Object>();
			mapDate.put("PAYLIST_CODE", PAYLIST_CODE);
			mapDate.put("BEGINNING_NUM", PERIOD);
			if (Integer.parseInt(PERIOD) >= 1 && !StringUtils.isBlank(PAYLIST_CODE)) {
				// 如果是租金 则向中间表更新
				Dao.delete("rentWriteNew.deleteJoinDate", mapDate);
				Dao.insert("rentWriteNew.insertJoinDate", mapDate);

			}
			updateBeginning(PAYLIST_CODE, PERIOD);
		}

	}

	/**
	 * 网银作废
	 * 
	 * @param IDS
	 * @author:King 2013-12-24
	 */
	public void doUpdateFiForRedConfirmPassWY_ZF(String IDS) {
		String ids[] = IDS.split(",");
		OverdueService oService = new OverdueService();
		for (String FR_ID : ids) {
			// 更新begining实收字段
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("FR_ID", FR_ID);
			Map<String, Object> moneyMap = (Map<String, Object>) (null == Dao.selectOne(namespace + "queryPayCodeAndPeriod", m) ? new HashMap<String, Object>()
					: Dao.selectOne(namespace + "queryPayCodeAndPeriod", m));
			boolean wyjflag = true;
			if (!moneyMap.isEmpty() && moneyMap.size() > 0 && !StringUtils.isBlank(moneyMap.get("D_BEGING_ID"))) {
				// 虚拟核销判断
				// Object BEGINNING_FLAG=moneyMap.get("BEGINNING_FLAG");
				// Object VINUAL_FLAG=moneyMap.get("VINUAL_FLAG");
				Object FI_FLAG = moneyMap.get("FI_FLAG");
				// 虚拟
				if ("16".equals(FI_FLAG) || "17".equals(FI_FLAG)) {
					Dao.update(namespace + "doUpdateBeginingForWY_xn", m);
				} else {
					// 利息 本金已核销
					Dao.update(namespace + "doUpdateBeginingForWY", m);
				}
				moneyMap.put("ZJ", "ZJ");
				// 更新违约金表
				if (!StringUtils.isBlank(moneyMap.get("D_PAY_CODE")) && !StringUtils.isBlank(moneyMap.get("PERIOD")))
					Dao.update(namespace + "updateOverdue", moneyMap);
			} else {
				// 违约金已经收取 清掉
				if (!StringUtils.isBlank(moneyMap.get("D_PAY_CODE")) && !StringUtils.isBlank(moneyMap.get("PERIOD")))
					Dao.update(namespace + "updateOverdue", moneyMap);
				wyjflag = false;
			}

			// FI_FLAG=8表示保证金抵扣
			String FI_FLAG = moneyMap.get("FI_FLAG") + "";
			if (FI_FLAG.equals("8")) {
				// 保证金抵扣
				if (!StringUtils.isBlank(moneyMap.get("FA_POOL_ID")) && !StringUtils.isBlank(moneyMap.get("DETAIL_ID"))) {
					// 回更保证金池可用金额
					Dao.update(namespace + "updateBZJCHI", moneyMap);
					String FUND_ID = Dao.selectOne(namespace + "queryFUZUJIN", moneyMap);
					if (!StringUtils.isBlank(FUND_ID)) {
						// 删除冲红的数据
						Dao.delete(namespace + "delFUZIJIN_ACCOUNT", FUND_ID);
						Dao.delete(namespace + "delFUZIJIN_DETAIL", FUND_ID);
						Dao.delete(namespace + "delFUZIJIN", moneyMap);
					}
				}
			}

			// 删除FI_FUND_ACCOUNT对应的数据
			Dao.delete(namespace + "doDelFundAccountWY", moneyMap);
			// 删除fi_fund_detail表中对应的数据
			Dao.delete(namespace + "doDelFundDetailWY", moneyMap);
			// 修改付款申请主表FI_FUND_HEAD的数据值
			if (!moneyMap.isEmpty() && moneyMap.size() > 0) {
				Dao.update(namespace + "updateFundHeadWY", moneyMap);
			}
			// 删除项目数量为0的申请单FI_FUND_HEAD
			Dao.delete(namespace + "delFundHeadNUMForZERO");
			if (wyjflag) {
				// 删除违约金
				oService.deleteByPayEAFDate(moneyMap.get("PAYLIST_CODE") + "", moneyMap.get("D_REALITY_DATE") + "");
			} else {
				oService.deleteByPayAFDate(moneyMap.get("PAYLIST_CODE") + "", moneyMap.get("D_REALITY_DATE") + "");
			}
			// 刷违约金
			String PAYLIST_CODE = moneyMap.get("PAYLIST_CODE") + "";
			Map<String, Object> mm = new HashMap<String, Object>();
			mm.put("PAY_CODE", PAYLIST_CODE);
			Dao.update("fi.overdue.upDueOne", mm);

			// 向核销中间表更新数据
			String PERIOD = null == moneyMap.get("PERIOD") ? "0" : moneyMap.get("PERIOD") + "";
			Map<String, Object> mapDate = new HashMap<String, Object>();
			mapDate.put("PAYLIST_CODE", PAYLIST_CODE);
			mapDate.put("BEGINNING_NUM", PERIOD);
			if (Integer.parseInt(PERIOD) >= 1 && !StringUtils.isBlank(PAYLIST_CODE)) {
				// 如果是租金 则向中间表更新
				Dao.delete("rentWriteNew.deleteJoinDate", mapDate);
				Dao.insert("rentWriteNew.insertJoinDate", mapDate);

			}
			m.put("STATUS", "已作废");
			Dao.update(namespace + "updateRedStatus", m);
			updateBeginning(PAYLIST_CODE, PERIOD);
		}
	}

	/**
	 * 查询网银冲红作废申请
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-11-18
	 */
	public Page doShowMgFiForRedWebApp(Map<String, Object> map) {
		return PageUtil.getPage(map, namespace + "doShowMgFiForRedWebApp", namespace + "doShowMgFiForRedWebAppCount");
	}

	/**
	 * 添加网银申请
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-11-22
	 */
	public int doAddFiForRedWebApp(Map<String, Object> map) {
		map.put("CREATE_CODE", Security.getUser().getCode());
		return Dao.insert(namespace + "doAddFiForRedWebApp", map);
	}

	/**
	 * 提交网银冲红作废申请
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-11-24
	 */
	public int doUpdateFiForRedWebAppSubmit(Map<String, Object> map) {
		return Dao.insert(namespace + "doUpdateFiForRedWebAppSubmit", map);
	}

	/**
	 * 删除网银作废申请
	 * 
	 * @param ID
	 * @return
	 * @author:King 2013-11-24
	 */
	public int doDeleteFiForRedWebApp(String ID) {
		return Dao.delete(namespace + "doDeleteFiForRedWebApp", ID);
	}

	/**
	 * 查询需要核销确认的冲红作废数据
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-11-24
	 */
	public Page doMgFiForRedWebConfirm(Map<String, Object> map) {
		return PageUtil.getPage(map, namespace + "doMgFiForRedWebConfirm", namespace + "doMgFiForRedWebConfirmCount");
	}

	/**
	 * 根据还款计划编号、期初、类型查询付款单id
	 * 
	 * @param map
	 * @return 付款单id
	 * @author:吴剑东 2013-12-08
	 */
	public String doSelectHeadIDByCodes(Map<String, Object> map) {
		return Dao.selectOne(namespace + "doSelectHeadIDByCodes", map);
	}

	/**
	 * 查询需要核销确认的冲红作废数据
	 * 
	 * @param map
	 * @return
	 * @author:吴剑东 2013-12-08
	 */
	public String doSelectHeadIDByProCode(Map<String, Object> map) {
		return Dao.selectOne(namespace + "doSelectHeadIDByProCode", map);
	}

	/**
	 * 监控应收表 为了数据统计刷数据
	 * 
	 * @param PAYLIST_CODE
	 * @param PERIOD
	 * @author:King 2013-12-27
	 * @param object
	 */
	public void updateBeginning(String PAYLIST_CODE, String PERIOD) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (!StringUtils.isBlank(PAYLIST_CODE)) {
			map.put("PAYLIST_CODE", PAYLIST_CODE);
			map.put("PERIOD", null == PERIOD ? "0" : PERIOD);
			Dao.update(namespace + "updateBeginning", map);
		}
	}


	/**
	 * 判断当期是否有违约金核销，但却未申请冲红作废
	 * 
	 * @param map
	 * @return
	 * @author:King 2014-1-9
	 */
	public boolean checkDunHX(Object PAY_CODE, Object PERIOD) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isBlank(PERIOD) || StringUtils.isBlank(PAY_CODE)) {
			return true;
		} else {
			map.put("PAY_CODE", PAY_CODE);
			map.put("PERIOD", PERIOD);
			int i = Dao.selectInt(namespace + "checkDunHX", map);
			if (i >= 1) {
				return true;
			} else return false;
		}
	}
	/**
	 * 插入冲红或作废申请数据 --》FI_RED_HEAD
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-11-17
	 */
	public int doAddFiForRedApp2(Map<String, Object> map) {
		map.put("CREATE_CODE", Security.getUser().getCode());
		map.put("STATUS", "未处理");
		return Dao.insert(namespace + "doAddFiRedHead", map);
	}
	/**
	 * 查询冲红或作废审核列表
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-11-17
	 */
	public Page doShowMgFiForRedConfirm2(Map<String, Object> map) {
		return PageUtil.getPage(map, namespace + "doShowMgFiRedHeadConfirm", namespace + "doShowMgFiRedHeadConfirmCount");
	}
	/**
	 * 冲红作废确认
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-11-18
	 */
	public void doUpdateFiForRedConfirmPassNew(List<Map<String, Object>> list) {
		OverdueService oService = new OverdueService();
		for (Map<String,Object> map: list) {
			//fi_fund_head表的id
			String F_FUND_ID = Dao.getSequence("SEQ_FUND_HEAD");
			//获取明细项
			List list_FD = Dao.selectList(namespace+"selectFundDetail", map);
			for(int i=0;i<list_FD.size();i++){
				// 更新begining实收字段
				Map<String, Object> m = new HashMap<String, Object>();
			//	m.put("FR_ID", map.get("ID"));//冲红作废记录表ID
				m.put("FD_ID", list_FD.get(i));//FI_FUND_DETAIL表 ID
				boolean wyjflag = true;
				Map<String, Object> moneyMap = (Map<String, Object>) (null == Dao.selectOne(namespace + "doQueryMoney2", m) ? new HashMap<String, Object>()
						: Dao.selectOne(namespace + "doQueryMoney2", m));
				if (!moneyMap.isEmpty() && moneyMap.size() > 0 && !StringUtils.isBlank(moneyMap.get("D_BEGING_ID"))) {
					if ("16".equals(map.get("FI_FLAG")) || "17".equals(map.get("FI_FLAG"))) {
						Dao.update(namespace + "doUpdateBegining_xn2", m);
					} else {
						// 利息 本金已核销
						Dao.update(namespace + "doUpdateBegining2", m);
					}
					moneyMap.put("ZJ", "ZJ");
					// 更新违约金表
					if (!StringUtils.isBlank(moneyMap.get("D_PAY_CODE")) && !StringUtils.isBlank(moneyMap.get("PERIOD")))
						Dao.update(namespace + "updateOverdue", moneyMap);
				}else {
					// 违约金已经收取 减掉冲红作废的部分
					if (!StringUtils.isBlank(moneyMap.get("D_PAY_CODE")) && !StringUtils.isBlank(moneyMap.get("PERIOD")))
						Dao.update(namespace + "updateOverdue", moneyMap);
					wyjflag = false;
				}
				Map<String, Object> mm = Dao.selectOne(namespace + "queryClientAndSupIdNew", m);
				if (wyjflag) {
					// 删除违约金  PAYLIST_CODE---租金计划编号
					oService.deleteByPayEAFDate(mm.get("PAYLIST_CODE") + "", moneyMap.get("D_REALITY_DATE") + "");
				} else {
					oService.deleteByPayAFDate(mm.get("PAYLIST_CODE") + "", moneyMap.get("D_REALITY_DATE") + "");
				}
				// 刷违约金
				String PAYLIST_CODE = mm.get("PAYLIST_CODE") + "";
				mm.put("PAY_CODE", PAYLIST_CODE);
				Dao.update("fi.overdue.upDueOne", mm);
				
				// 向核销中间表更新数据
				String PERIOD = null == mm.get("PERIOD") ? "0" : mm.get("PERIOD") + "";
				Map<String, Object> mapDate = new HashMap<String, Object>();
				mapDate.put("PAYLIST_CODE", PAYLIST_CODE);
				mapDate.put("BEGINNING_NUM", PERIOD);
				if (Integer.parseInt(PERIOD) >= 1) {
					// 如果是租金 则向中间表更新
					Dao.update("rentWriteNew.doPRC_BE_QJL_ONE",mapDate);

				}
				System.out.println(mm.get("D_RECEIVE_MONEY"));
				updateBeginning(PAYLIST_CODE, PERIOD);
				// fi_fund_detail表插入冲红负数据
				//m.put("D_FUND_ID",map.get("HEAD_ID"));
				m.put("RED_STATUS", 2);
				m.put("D_FUND_ID", F_FUND_ID);
				if((1+"").equals(map.get("FI_FUND_CODE"))){
					m.put("D_STATUS", 1);
				}else{
					m.put("D_STATUS", 0);
				}
				Dao.insert(namespace + "doAddFiForRedConfirmNew", m);
			}
			int FUND_ID = 0;
			//回写资金到上传来款
			if(!map.isEmpty() && map.size() > 0 && !StringUtils.isBlank(map.get("HEAD_ID")) && !StringUtils.isBlank(map.get("FI_PAY_MONEY"))) {
				double D_RECEIVE_MONEY=Double.parseDouble(map.get("FI_PAY_MONEY").toString());
				Map mapFund=new HashMap();
				//Integer a = (Integer)map.get("FI_PAY_MONEY");
				//System.out.println(a+"---------a----------");
				mapFund.put("D_RECEIVE_MONEY", map.get("FI_PAY_MONEY"));
				mapFund.put("FUND_STATUS",4);
				mapFund.put("FUND_RED_STATUS", 2);//FUND_NOTDECO_STATE
				mapFund.put("FUND_NOTDECO_STATE", 8);
				mapFund.put("FUND_ID", Dao.selectOne(namespace+"selectFundId", map));
				mapFund.put("FUND_FUNDCODE", Dao.selectOne("fi.fund.getFundCode"));
				//插入fi_fund表冲红负数据
				Dao.insert(namespace+"insertFundRedNewT",mapFund);
				
				mapFund.put("D_RECEIVE_MONEY", map.get("FI_PAY_MONEY"));
				mapFund.put("FUND_STATUS",0);
				mapFund.put("FUND_RED_STATUS", 0);
				mapFund.put("FUND_NOTDECO_STATE", 1);
				//插入数据到fi_fund表
				Dao.insert(namespace+"insertFundRedNew",mapFund);
				
				//查询FI_FUND_CODE=1 租金  =2 首付款
				Map<String,Object> map2 = new HashMap<String, Object>();
				int FI_FUND_CODE = Dao.selectInt(namespace+"select_FundCode", map);
				map2.put("FUND_TYPE", FI_FUND_CODE);
				if(FI_FUND_CODE==1){
					//FI_FUND_CODE=1 租金  更新fi_fund 
					map2.put("FUND_TYPE", 2);
					FUND_ID = Dao.selectInt("fi.fund.seq_fi_fund_id", null);
					map2.put("FUND_ID", FUND_ID);
					//修改fi_fund表的摘要 - 冲红  和 认款人- 操作人员  和 来款类型
					String userName = Security.getUser().getName();
					map2.put("FUND_PIDENTIFY_PERSON",userName);
					Dao.update("fi.fund.update_fund_type", map2);
				}else if(FI_FUND_CODE==2){
					//FI_FUND_CODE=2 首付款  更新fi_fund
					map2.put("FUND_TYPE", 1);
					FUND_ID = Dao.selectInt("fi.fund.seq_fi_fund_id", null);
					map2.put("FUND_ID", FUND_ID);
					//修改fi_fund表的摘要 - 冲红  和 认款人- 操作人员  和 来款类型
					String userName = Security.getUser().getName();
					map2.put("FUND_PIDENTIFY_PERSON",userName);
					Dao.update("fi.fund.update_fund_type", map2);
				}
			}
			//更新FI_RED_HEAD
			map.put("STATUS", "已冲红");
			Dao.update(namespace + "updateRedHeadStatus", map);
			// Dao.update(namespace+"doUpdateBegining", m);
			map.put("F_FUND_ID", F_FUND_ID);
			// 插入冲出来的负数金额到资金主表中 FI_FUND_HEAD
			map.put("APP_CREATE", "'冲红作废'");
			map.put("FUND_ID", FUND_ID-1);
			map.put("FI_STATUS", 7);
			Dao.insert(namespace + "addRedFundHeadNew", map);

			//查询该核销单下面所以本金判断是否为循环授信，是的话进行处理
			rentWriteNewService rentService=new rentWriteNewService();
			rentService.queryPaylistBJSX(F_FUND_ID);//金额为付的
			
			
			//反更支付表主表的已还期次和未款金额
			rentService.queryPaylistHG(F_FUND_ID);
		}
		// return 1;
	}
	public void doUpdateFiForRedConfirmPass_ZFNew(List<Map<String,Object>> list) {
		OverdueService oService = new OverdueService();
		for (Map<String,Object> map: list) {
			//fi_fund_head表的id
			String F_FUND_ID = Dao.getSequence("SEQ_FUND_HEAD");
			List list_FD = Dao.selectList(namespace+"selectFundDetail", map);
			for(int i=0;i<list_FD.size();i++){
				// 更新begining实收字段
				Map<String, Object> m = new HashMap<String, Object>();
			//	m.put("FR_ID", map.get("ID"));//冲红作废记录表ID
				m.put("FD_ID", list_FD.get(i));//FI_FUND_DETAIL表 ID
				boolean wyjflag = true;
				Map<String, Object> moneyMap = (Map<String, Object>) (null == Dao.selectOne(namespace + "doQueryMoney2", m) ? new HashMap<String, Object>()
						: Dao.selectOne(namespace + "doQueryMoney2", m));
				if (!moneyMap.isEmpty() && moneyMap.size() > 0 && !StringUtils.isBlank(moneyMap.get("D_BEGING_ID"))) {
					if ("16".equals(map.get("FI_FLAG")) || "17".equals(map.get("FI_FLAG"))) {
						Dao.update(namespace + "doUpdateBegining_xn2", m);
					} else {
						// 利息 本金已核销
						Dao.update(namespace + "doUpdateBegining2", m);
					}
					moneyMap.put("ZJ", "ZJ");
					// 更新违约金表
					if (!StringUtils.isBlank(moneyMap.get("D_PAY_CODE")) && !StringUtils.isBlank(moneyMap.get("PERIOD")))
						Dao.update(namespace + "updateOverdue", moneyMap);
				}else {
					// 违约金已经收取 减掉冲红作废的部分
					if (!StringUtils.isBlank(moneyMap.get("D_PAY_CODE")) && !StringUtils.isBlank(moneyMap.get("PERIOD")))
						Dao.update(namespace + "updateOverdue", moneyMap);
					wyjflag = false;
				}
				Map<String, Object> mm = Dao.selectOne(namespace + "queryClientAndSupIdNew", m);
				if (wyjflag) {
					// 删除违约金  PAYLIST_CODE---租金计划编号
					oService.deleteByPayEAFDate(mm.get("PAYLIST_CODE") + "", moneyMap.get("D_REALITY_DATE") + "");
				} else {
					oService.deleteByPayAFDate(mm.get("PAYLIST_CODE") + "", moneyMap.get("D_REALITY_DATE") + "");
				}
				// 刷违约金
				String PAYLIST_CODE = mm.get("PAYLIST_CODE") + "";
				mm.put("PAY_CODE", PAYLIST_CODE);
				Dao.update("fi.overdue.upDueOne", mm);
				
				// 向核销中间表更新数据
				String PERIOD = null == mm.get("PERIOD") ? "0" : mm.get("PERIOD") + "";
				Map<String, Object> mapDate = new HashMap<String, Object>();
				mapDate.put("PAYLIST_CODE", PAYLIST_CODE);
				mapDate.put("BEGINNING_NUM", PERIOD);
				if (Integer.parseInt(PERIOD) >= 1) {
					// 如果是租金 则向中间表更新
					Dao.update("rentWriteNew.doPRC_BE_QJL_ONE",mapDate);

				}
				updateBeginning(PAYLIST_CODE, PERIOD);
				
			}
			
			//回写资金到上传来款
			if(!map.isEmpty() && map.size() > 0 && !StringUtils.isBlank(map.get("HEAD_ID")) && !StringUtils.isBlank(map.get("FI_PAY_MONEY"))) {
				double D_RECEIVE_MONEY=Double.parseDouble(map.get("FI_PAY_MONEY").toString());
				Map mapFund=new HashMap();
				mapFund.put("D_RECEIVE_MONEY", map.get("FI_PAY_MONEY"));
				mapFund.put("FUND_STATUS",0);
				mapFund.put("FUND_RED_STATUS", 0);//FUND_NOTDECO_STATE
				mapFund.put("FUND_NOTDECO_STATE", 1);
				mapFund.put("FUND_ID", Dao.selectOne(namespace+"selectFundId", map));
				mapFund.put("FUND_FUNDCODE", Dao.selectOne("fi.fund.getFundCode"));
				//将原资金复制下来修改金额即可
				Dao.insert(namespace+"insertFundRedNew",mapFund);
				//删除原有作废数据
				Dao.delete(namespace+"delFundRedHeadNew",mapFund);

				//查询FI_FUND_CODE=1 租金  =2 首付款
				Map<String,Object> map2 = new HashMap<String, Object>();
				int FI_FUND_CODE = Dao.selectInt(namespace+"select_FundCode", map);
				map2.put("FUND_TYPE", FI_FUND_CODE);
				if(FI_FUND_CODE==1){
					//FI_FUND_CODE=1 租金  更新fi_fund 
					map2.put("FUND_TYPE", 2);
					int FUND_ID = Dao.selectInt("fi.fund.seq_fi_fund_id", null);
					map2.put("FUND_ID", FUND_ID);
					//修改fi_fund表的摘要 - 冲红  和 认款人- 操作人员  和 来款类型
					String userName = Security.getUser().getName();
					map2.put("FUND_PIDENTIFY_PERSON",userName);
					Dao.update("fi.fund.update_fund_type", map2);
				}else if(FI_FUND_CODE==2){
					//FI_FUND_CODE=2 首付款  更新fi_fund
					map2.put("FUND_TYPE", 1);
					int FUND_ID = Dao.selectInt("fi.fund.seq_fi_fund_id", null);
					map2.put("FUND_ID", FUND_ID);
					//修改fi_fund表的摘要 - 冲红  和 认款人- 操作人员  和 来款类型
					String userName = Security.getUser().getName();
					map2.put("FUND_PIDENTIFY_PERSON",userName);
					Dao.update("fi.fund.update_fund_type", map2);
				}
			}
			//更新FI_RED_HEAD
			map.put("STATUS", "已作废");
			Dao.update(namespace + "updateRedHeadStatus", map);
			// Dao.update(namespace+"doUpdateBegining", m);
			// FI_FUND_HEAD表删除作废数据 
			Dao.delete(namespace + "delRedFundHeadNew", map);
			// fi_fund_detail表删除作废数据
			Dao.insert(namespace + "delFiForRedHeadConfirmNew", map);
			//查询该核销单下面所以本金判断是否为循环授信，是的话进行处理
			rentWriteNewService rentService=new rentWriteNewService();
			rentService.queryPaylistBJSX(F_FUND_ID);//金额为付的
			
			//反更支付表主表的已还期次和未款金额
			rentService.queryPaylistHG(F_FUND_ID);
		
		}
	}
	/**
	 * 冲红审批驳回
	 * 
	 * @param map
	 * @return
	 * @author:King 2013-11-18
	 */
	public int doUpdatefiForRedHeadConfirmNotPass(Map<String, Object> map) {
		map.put("FI_STATUS", '4');
		Dao.update(namespace + "updateFundHeadRedStatus", map);
		return Dao.update(namespace + "fiForRedHeadConfirmNotPass", map);
	}
}
