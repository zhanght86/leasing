package com.pqsoft.buyBack.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.bpm.JBPM;
import com.pqsoft.pigeonhole.service.PigeonholeService;
import com.pqsoft.project.service.ProjectContractManagerService;
import com.pqsoft.rentWrite.service.rentWriteService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.StringUtils;
import com.pqsoft.util.Util;

public class BuyBackCheckService {
	
	
	//修改回购单
	public int doUpdateEnd_status(Map<String,Object> map){
		return Dao.update("rentDk.doUpdateEnd_status", map);
	}

	/****************************************************************************************
	 ************************************添加冲红申请单**************Auth=杨雪****************
	 ****************************************************************************************/

	/**
	 * 核销申请首先冲红保证金, DB保证金, 留购价
	 * toCheckedRent
	 * @date 2013-11-21 下午07:29:58
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object toRedFinance(Map<String,Object> map){
		int i = 0;			
		
		//是否做承租人保证金抵扣
		String rent_bzj = map.get("BZJ")!=null&&!"".equals(map.get("BZJ"))?map.get("BZJ").toString():"";

		if("0".equals(rent_bzj)){//当选择保证金抵扣时 ， 将保证金冲抵
			
			//首先删除已生成的冲红申请单
			if(map.get("BZJ_HEAD")!=null){
				map.put("fund_id", map.get("BZJ_HEAD"));
				
				map.put("FUND_ID", map.get("BZJ_HEAD"));
				
				List<Map<String,Object>> deList = Dao.selectList("rentWrite.queryDetailByFundId", map);
				if(deList != null){
					for(int a=0; a<deList.size(); a++){
						Map<String,Object> mmm = deList.get(a);
						Dao.update("rentDk.toUpdateBegining1", mmm);
					}
				}
				
				Dao.delete("rentDk.delFundAccount", map);
				Dao.delete("rentDk.delFundDetail", map);
				Dao.delete("rentDk.delFundHead", map);
				
				map.put("TYPE", 4);
				Dao.update("rentDk.toUpdatePool",map);
			}
			
			//查看项目所在资金池
			map.put("TYPE", "4");
			//添加冲红申请单
			map.put("D_PAY_PROJECT", "保证金");
			i = this.toAddFund(map);
			
			if(i>0){
				map.put("BZJ_HEAD", i);
			}
		}
		
		//是否做DB保证金抵扣
		String db_bzj = map.get("DBBZJ")!=null&&!"".equals(map.get("DBBZJ"))?map.get("DBBZJ").toString():"";
		int db_bzj_ = 0;
		if("0".equals(db_bzj)){//当选择保证金抵扣时 ， 将DB保证金冲抵
			
			//首先删除已生成的冲红申请单
			if(map.get("DBBZJ_HEAD")!=null){
				map.put("fund_id", map.get("DBBZJ_HEAD"));
				
				map.put("FUND_ID", map.get("DBBZJ_HEAD"));
				
				List<Map<String,Object>> deList = Dao.selectList("rentWrite.queryDetailByFundId", map);
				if(deList != null){
					for(int a=0; a<deList.size(); a++){
						Map<String,Object> mmm = deList.get(a);
						Dao.update("rentDk.toUpdateBegining1", mmm);
					}
				}
				
				Dao.delete("rentDk.delFundAccount", map);
				Dao.delete("rentDk.delFundDetail", map);
				Dao.delete("rentDk.delFundHead", map);
				
				map.put("TYPE", 1);
				Dao.update("rentDk.toUpdatePool",map);
			}
			
			//查看项目所在资金池
			map.put("TYPE", "1");
			//添加冲红申请单
			map.put("D_PAY_PROJECT", "DB保证金");
			db_bzj_ = this.toAddFund(map);
			if(db_bzj_>0){
				map.put("DBBZJ_HEAD", db_bzj_);
			}
		}
		
		
		//是否做留购价抵扣
		String lgj_dk = map.get("isLiugou")!=null&&!"".equals(map.get("isLiugou"))?map.get("isLiugou").toString():"";
		if("0".equals(lgj_dk)){//当选择留购价抵扣时 ， 将留购价冲抵
			
			//首先删除已生成的冲红申请单
			if(map.get("LGJ_HEAD")!=null){
				map.put("fund_id", map.get("LGJ_HEAD"));				
				
				map.put("FUND_ID", map.get("LGJ_HEAD"));
				
				List<Map<String,Object>> deList = Dao.selectList("rentWrite.queryDetailByFundId", map);
				if(deList != null){
					for(int a=0; a<deList.size(); a++){
						Map<String,Object> mmm = deList.get(a);
						Dao.update("rentDk.toUpdateBegining1", mmm);
					}
				}
				
				Dao.delete("rentDk.delFundAccount", map);
				Dao.delete("rentDk.delFundDetail", map);
				Dao.delete("rentDk.delFundHead", map);
			}
			
			
			//付款单id
			String HEAD_ID = Dao.getSequence("SEQ_FUND_HEAD"); 
			
			//查看留購價
			map.put("LGJ", "留购价款");
			map.put("PAYLIST_CODE", map.get("PAYLIST_CODE"));
			System.out.println("--------yx----------"+map);
			List<Map<String,Object>> lgj = Dao.selectList("rentDk.getLGJData",map);
			//父类付款单id
			String FI_SUPERCLASS_ID = "";
			double CANUSE_MONEY = 0.00; 
			if(lgj!=null){
				for(int a=0; a<lgj.size(); a++){
					Map<String,Object> m = lgj.get(a);
				    FI_SUPERCLASS_ID += m.get("D_FUND_ID")!=null&&!"".equals(m.get("D_FUND_ID"))?m.get("D_FUND_ID").toString()+",":"";
				    CANUSE_MONEY += Double.parseDouble(m.get("D_PAY_MONEY")!=null&&!"".equals(m.get("D_PAY_MONEY"))? m.get("D_PAY_MONEY").toString():"");
				}
				
				//插入资金池流出的数据金额： 保证金结清抵扣之前， 首先出入插入一条负的保证金， 该保证金虚记录为融资公司冲红所用的保证金
				Map<String,Object> CH = new HashMap<String,Object>();
				CH.put("HEAD_ID", HEAD_ID);//付款单号
				CH.put("FI_PAY_MONEY", -CANUSE_MONEY);//应收金额
				CH.put("FI_PROJECT_NUM", 1);//项目数量
				CH.put("USERNAME", Security.getUser().getName());//申请人
				CH.put("USERCODE", Security.getUser().getCode());//申请人编号
				CH.put("FI_PAY_DATE", map.get("FI_ACCOUNT_DATE"));//应付款时间				
				CH.put("FI_SUPERCLASS_ID", FI_SUPERCLASS_ID);//父类资金付款单
				//插入核销银行
				CH.put("FI_REALITY_BANK", map.get("FI_REALITY_BANK"));
				CH.put("FI_STATUS", 7);
				CH.put("FI_ORG_ID",  map.get("ORG_ID"));
				//插入金额为负的保证金。
				int a  = Dao.insert("rentDk.doInsertFundHead",CH);
				int b  = 0;
				if(a>0){
					Map<String,Object> red = new HashMap<String,Object>();
					red.put("D_FUND_ID", HEAD_ID);//付款单id
					Map<String,Object> cust = Dao.selectOne("rentDk.getCustName",map);
					red.put("D_CLIENT_CODE", cust.get("CLIENT_ID").toString());//客户编号id
					red.put("D_CLIENT_NAME", cust.get("CUST_NAME").toString());//客户名称
					red.put("D_PAY_PROJECT", "留购价款");
					red.put("RED_STATUS", "2");
					red.put("D_PAY_MONEY", -CANUSE_MONEY);//保证金
					red.put("D_PAY_CODE",map.get("PAYLIST_CODE"));
					red.put("D_PROJECT_CODE", map.get("PRO_CODE"));
					String ID = Dao.getSequence("SEQ_FUND_DETAIL");
					red.put("ID", ID);
					
				    b = Dao.insert("rentDk.doInsertAppDetail", red);
				    
				    Dao.insert("rentWrite.doInsertAccountByDetail", red);
				}
				
				if(a>0&&b>0){
					map.put("LGJ_HEAD", HEAD_ID);
				}
			}
			
		}
		
		return   map;
	}
	
	/**
	 * 添加冲红申请单: 承租人 保证金 和DB保证金
	 * toAddFund
	 * @param  1  项目id     PROJECT_ID
	 *         2 项目编号         PRO_CODE
	 *         3 支付表编号    PAYLIST_CODE
	 *         4 付款时间         ACCOUNT_DATE
	 * @date 2013-11-21 下午07:20:00
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public int toAddFund(Map<String,Object> map){
		
		DecimalFormat df = new DecimalFormat(".00");//保留两位小数处理
		
		//付款单id
		String HEAD_ID = Dao.getSequence("SEQ_FUND_HEAD"); 
		
		
		//查看资金池中保证金/DB保证金
		List<Map<String,Object>> head = Dao.selectList("rentDk.getPoolData",map);
		
		//如果查询保证金为空则退出不做操作
		if(head==null||head.isEmpty()){
			return 0;
		}
		//父类付款单id
		String FI_SUPERCLASS_ID = "";
		double CANUSE_MONEY = 0.00; 
		int i = 0;
		int n = 0;
		int t = 0;
		if(head!=null){
			for(int a=0; a<head.size(); a++){
				Map<String,Object> m = head.get(a);
			    FI_SUPERCLASS_ID += m.get("SOURCE_ID")!=null&&!"".equals(m.get("SOURCE_ID"))?m.get("SOURCE_ID").toString()+",":"";
			    CANUSE_MONEY += Double.parseDouble(m.get("CANUSE_MONEY")!=null&&!"".equals(m.get("CANUSE_MONEY"))?m.get("CANUSE_MONEY").toString():"");
			}
			
			//插入资金池流出的数据金额： 保证金结清抵扣之前， 首先出入插入一条负的保证金， 该保证金虚记录为融资公司冲红所用的保证金
			Map<String,Object> CH = new HashMap<String,Object>();
			CH.put("HEAD_ID", HEAD_ID);//付款单号
			CH.put("FI_PAY_MONEY",  df.format(-CANUSE_MONEY));//应收金额
			CH.put("FI_PROJECT_NUM", 1);//项目数量
			CH.put("USERNAME", Security.getUser().getName());//申请人
			CH.put("USERCODE", Security.getUser().getCode());//申请人编号
			CH.put("FI_PAY_DATE", map.get("FI_ACCOUNT_DATE"));//应付款时间
			//CH.put("FI_FLAG", 9);//付款类型
			//插入核销银行
			CH.put("FI_REALITY_BANK", map.get("FI_REALITY_BANK"));
			CH.put("FI_SUPERCLASS_ID", FI_SUPERCLASS_ID);//父类资金付款单
			CH.put("FI_STATUS", 7);
			//插入金额为负的保证金。
			i = Dao.insert("rentDk.doInsertFundHead",CH);
			if(i>0){
				Map<String,Object> red = new HashMap<String,Object>();
				String ID = Dao.getSequence("SEQ_FUND_DETAIL");
				red.put("ID", ID);//付款单id
				red.put("D_FUND_ID", HEAD_ID);//付款单id
				Map<String,Object> cust = Dao.selectOne("rentDk.getCustName",map);
				red.put("D_CLIENT_CODE", cust.get("CLIENT_ID").toString());//客户编号id
				red.put("D_CLIENT_NAME", cust.get("CUST_NAME").toString());//客户名称
				red.put("D_PAY_PROJECT", map.get("D_PAY_PROJECT"));
				red.put("D_PAY_MONEY", df.format(-CANUSE_MONEY));//保证金
				red.put("RED_STATUS", "2");
				red.put("D_PAY_CODE",map.get("PAYLIST_CODE"));
				red.put("D_PROJECT_CODE", map.get("PRO_CODE"));
				
				n = Dao.insert("rentDk.doInsertAppDetail", red);
				if(n>0){
					t = Dao.insert("rentWrite.doInsertAccountByDetail", red);
				}
			}
		}
		
		if(i>0&&n>0&&t>0){
			return Integer.parseInt(HEAD_ID);
		}
		
		return 0;
	}
	
	
	
	
	/****************************************************************************************
	 ************************************添加申请单**保证金留购价申请********Auth=杨雪*********
	 ****************************************************************************************/
	/**
	 * 保证金留购价抵扣
	 */
	public Object doAddBZJandLgj(Map<String,Object> map){
		
		DecimalFormat df = new DecimalFormat(".00");//保留两位小数处理	
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		
		//首先删除已生成的冲红申请单
		if(map.get("BZJandLGJ")!=null){
			map.put("fund_id", map.get("BZJandLGJ"));
			
			map.put("FUND_ID", map.get("BZJandLGJ"));
			
			List<Map<String,Object>> deList = Dao.selectList("rentWrite.queryDetailByFundId", map);
			if(deList != null){
				for(int a=0; a<deList.size(); a++){
					Map<String,Object> mmm = deList.get(a);
					Dao.update("rentDk.toUpdateBegining1", mmm);
				}
			}
			
			Dao.delete("rentDk.delFundAccount", map);
			Dao.delete("rentDk.delFundDetail", map);
			Dao.delete("rentDk.delFundHead", map);
			
			map.put("TYPE", 4);
			Dao.update("rentDk.toUpdatePool",map);
		}
		
		// 组织机构应该取缓存 后面在改
		map.put("USER_ID", Security.getUser().getId());
		Map<String,Object> ORG_MAP=Dao.selectOne("payment.orgListByUserId", map);
		
		if(ORG_MAP!=null){
			map.put("ORG_ID", ORG_MAP.get("ORG_ID"));
		}
		
		//插入租金冲抵申请当
		String HEAD_ID = Dao.getSequence("SEQ_FUND_HEAD"); 
		resultMap.put("BZJandLGJ", HEAD_ID);
		Map<String,Object> fk_bill = new HashMap<String,Object>();
		fk_bill.put("HEAD_ID", HEAD_ID);//用户编号
		fk_bill.put("USERCODE", Security.getUser().getCode());//用户编号
		fk_bill.put("USERNAME", Security.getUser().getName());//用户名称
		fk_bill.put("FI_PAY_DATE", map.get("FI_ACCOUNT_DATE"));//付款时间
		fk_bill.put("FI_ACCOUNT_DATE", map.get("FI_ACCOUNT_DATE"));
		fk_bill.put("FI_PROJECT_NUM", 1);//项目数量
		fk_bill.put("FI_STATUS", 2);
		if(ORG_MAP!=null) fk_bill.put("FI_ORG_ID",  ORG_MAP.get("ORG_ID"));
		fk_bill.put("FI_FLAG", 10);//设备回购-保证金留购价款抵扣
		
		//保证金
		//如果选择了保证不抵扣 
		int is_deposit = Dao.selectInt("rentDk.is_deposit", map);
		double is_bzj = 0;
		if(is_deposit<=0){
			is_bzj = map.get("BZJDk")!=null && !"".equals(map.get("BZJDk"))?Double.parseDouble(map.get("BZJDk").toString()):0d;
		}
		
		
		//留购价
		double is_lgj = map.get("LG_DKJE")!=null && !"".equals(map.get("LG_DKJE"))?Double.parseDouble(map.get("LG_DKJE").toString()):0d;
		
		//应收款金额
		double FI_PAY_MONEY = is_bzj + is_lgj;
		
		resultMap.put("FI_REALITY_ACCOUNT",FI_PAY_MONEY);
		
		fk_bill.put("FI_PAY_MONEY", df.format(FI_PAY_MONEY));	
		fk_bill.put("FI_REALITY_MONEY", df.format(FI_PAY_MONEY));	
		fk_bill.put("FI_REALITY_ACCOUNT", df.format(FI_PAY_MONEY));	
		//插入核销银行
		fk_bill.put("FI_REALITY_BANK", map.get("FI_REALITY_BANK"));
		//插入付款申请单。
		Dao.insert("rentDk.doInsertFundHead",fk_bill);
		
		
		//通过项目CODE查询出发票开具对象
		Map<String,Object> InvoiceMap=Dao.selectOne("rentWrite.queryFundInvoice", map);
		
		if(FI_PAY_MONEY>0){
			
			//double WS_YQ_ = map.get("WS_YQ")!=null&&!"".equals(map.get("WS_YQ"))?Double.parseDouble(map.get("WS_YQ").toString()):0d;
			//TODO查询违约金
			double WS_YQ_ = 0;
			
			//首先核销截止到结算日的违约金
//			if(map.get("WS_YQ")!=null&&!"".equals(map.get("WS_YQ"))&&Double.parseDouble(map.get("WS_YQ").toString())>0){
				
				List<Map<String,Object>> over = Dao.selectList("rentDk.getOverDueData", map);
				if(over != null){
				//计算利息总额
				for (int i = 0; i < over.size(); i++) {
					WS_YQ_ += over.get(i).get("D_PAY_MONEY")!=null&&!"".equals(over.get(i).get("D_PAY_MONEY"))?Double.parseDouble(over.get(i).get("D_PAY_MONEY")+""):0;
				}
					for(int i=0; i<over.size(); i++){
						Map<String,Object> overdue = over.get(i);
						String ID = Dao.getSequence("SEQ_FUND_DETAIL");
						overdue.put("D_FUND_ID", HEAD_ID);
						overdue.put("ID", ID);				
						overdue.put("D_RECEIVE_DATE", map.get("FI_ACCOUNT_DATE"));
						overdue.put("D_PROJECT_CODE", map.get("PRO_CODE"));
						overdue.put("D_STATUS", 2);
						overdue.put("D_CLIENT_NAME", map.get("D_CLIENT_NAME"));
						overdue.put("D_CLIENT_CODE", map.get("D_CLIENT_CODE"));
						overdue.put("D_PAYEE", Security.getUser().getName());
						overdue.put("D_TO_THE_PAYEE", map.get("D_CLIENT_CODE"));
						//overdue.put("D_BEGING_ID", Dao.selectOne("rentDk.getPERIOD", overdue));
					
						
						if(InvoiceMap != null){
							overdue.put("INVOICE_TARGET_TYPE", InvoiceMap.get("INVOICE_TARGET_TYPE"));
							overdue.put("INVOICE_TARGET_ID", InvoiceMap.get("INVOICE_TARGET_ID"));
						}
						
						if(Double.parseDouble(overdue.get("D_PAY_MONEY").toString()) <= FI_PAY_MONEY){
							overdue.put("D_PAY_MONEY", overdue.get("D_PAY_MONEY"));
							Dao.insert("rentDk.doInsertAppDetail",overdue);
							FI_PAY_MONEY -= Double.parseDouble(overdue.get("D_PAY_MONEY").toString());
							//更新罚息表的实收
							Dao.update("rentDk.upateAppDetail",overdue);
							if(FI_PAY_MONEY<=0){
								break;
							}
							//WS_YQ_ = 0d;
						}else {
							overdue.put("D_PAY_MONEY", FI_PAY_MONEY);
							Dao.insert("rentDk.doInsertAppDetail",overdue);
							overdue.put("D_PAY_MONEY", FI_PAY_MONEY);
							Dao.update("rentDk.upateAppDetail",overdue);
							//WS_YQ_ -= FI_PAY_MONEY;
							FI_PAY_MONEY = 0d;
							break;
							//resultMap.put("WS_YQ_", WS_YQ_);//剩余违约金
						}
					}
				}
//			}
			
			//查看租金
			map.put("BENJIN", "本金");
			map.put("LIXI", "利息");
			List<Map<String,Object>> rentList = Dao.selectList("rentDk.getBeginingDetail", map);
			double sy_money_ = 0d;
			if(rentList!=null&&FI_PAY_MONEY>0){
				for(int k=0; k<rentList.size(); k++){
					
					Map<String,Object> rentList_ = rentList.get(k);
					
					rentList_.put("D_CLIENT_NAME", map.get("D_CLIENT_NAME"));
					rentList_.put("D_CLIENT_CODE", map.get("D_CLIENT_CODE"));
					rentList_.put("D_PAYEE", Security.getUser().getName());
					rentList_.put("D_TO_THE_PAYEE", map.get("D_CLIENT_CODE"));
					
					if(InvoiceMap != null){
						rentList_.put("INVOICE_TARGET_TYPE", InvoiceMap.get("INVOICE_TARGET_TYPE"));
						rentList_.put("INVOICE_TARGET_ID", InvoiceMap.get("INVOICE_TARGET_ID"));
					}
					
					String ID = Dao.getSequence("SEQ_FUND_DETAIL");
					rentList_.put("D_FUND_ID", HEAD_ID);
					rentList_.put("ID", ID);	
					rentList_.put("D_RECEIVE_DATE", map.get("FI_ACCOUNT_DATE"));
					rentList_.put("D_PROJECT_CODE", map.get("PRO_CODE"));
					rentList_.put("D_STATUS", 2);
					
					sy_money_ = Double.parseDouble(rentList_.get("D_PAY_MONEY").toString()); 
					
					if(FI_PAY_MONEY >= Double.parseDouble(rentList_.get("D_PAY_MONEY").toString())){
						
						//插入应收明细
						Dao.insert("rentDk.doInsertAppDetail",rentList_);	
						//剩余应收
						FI_PAY_MONEY -= Double.parseDouble(rentList_.get("D_PAY_MONEY").toString());
						sy_money_ = 0;
					}else {
						rentList_.put("D_PAY_MONEY", FI_PAY_MONEY);
						//插入应收明细
						Dao.insert("rentDk.doInsertAppDetail",rentList_);
						sy_money_ -= FI_PAY_MONEY;
						FI_PAY_MONEY = 0d;
						resultMap.put("sy_money_",sy_money_);
						
						//本更应收起初表,申请状态
						//Dao.update("rentDk.toUpdateBegining", rentList_);
					}
					
					if(FI_PAY_MONEY == 0){
						break;
					}
				}
			}
		}
		
		return resultMap;
	}
	
	/****************************************************************************************
	 ************************************添加申请单**DB保证金申请********Auth=杨雪************
	 ****************************************************************************************/
	public Object toAddDBDk(Map<String,Object> map){
		
		DecimalFormat df = new DecimalFormat(".00");//保留两位小数处理	
		Map<String,Object> resultMap = new HashMap<String,Object>();
		
		//首先删除已生成的冲红申请单
		if(map.get("dbBZJdk")!=null){
			map.put("fund_id", map.get("dbBZJdk"));
			
			map.put("FUND_ID", map.get("dbBZJdk"));
			
			List<Map<String,Object>> deList = Dao.selectList("rentWrite.queryDetailByFundId", map);
			if(deList != null){
				for(int a=0; a<deList.size(); a++){
					Map<String,Object> mmm = deList.get(a);
					Dao.update("rentDk.toUpdateBegining1", mmm);
				}
			}
			
			Dao.delete("rentDk.delFundAccount", map);
			Dao.delete("rentDk.delFundDetail", map);
			Dao.delete("rentDk.delFundHead", map);
			
			map.put("TYPE", 1);
			Dao.update("rentDk.toUpdatePool",map);
		}
		
		// 组织机构应该取缓存 后面在改
		map.put("USER_ID", Security.getUser().getId());
		Map<String,Object> ORG_MAP=Dao.selectOne("payment.orgListByUserId", map);
		
		if(ORG_MAP!=null){
			map.put("ORG_ID", ORG_MAP.get("ORG_ID"));
		}
		
		//插入租金冲抵申请当
		String HEAD_ID = Dao.getSequence("SEQ_FUND_HEAD"); 
		resultMap.put("dbBZJdk", HEAD_ID);
		Map<String,Object> fk_bill = new HashMap<String,Object>();
		fk_bill.put("HEAD_ID", HEAD_ID);//用户编号
		fk_bill.put("USERCODE", Security.getUser().getCode());//用户编号
		fk_bill.put("USERNAME", Security.getUser().getName());//用户名称
		fk_bill.put("FI_PAY_DATE", map.get("FI_ACCOUNT_DATE"));//付款时间
		fk_bill.put("FI_ACCOUNT_DATE", map.get("FI_ACCOUNT_DATE"));
		fk_bill.put("FI_PROJECT_NUM", 1);//项目数量
		fk_bill.put("FI_STATUS", 2);
		fk_bill.put("FI_ORG_ID",  ORG_MAP.get("ORG_ID"));
		fk_bill.put("FI_FLAG", 11);//设备回购-保证金留购价款抵扣
		
		//db保证金
		double is_dbbzj = map.get("dbBZJDk")!=null && !"".equals(map.get("dbBZJDk"))?Double.parseDouble(map.get("dbBZJDk").toString()):0d;
		resultMap.put("dbBZJDk", map.get("dbBZJDk"));
		//应收款金额
		double FI_PAY_MONEY = is_dbbzj;		
		
		if (FI_PAY_MONEY>0){
			
		
			fk_bill.put("FI_PAY_MONEY", df.format(FI_PAY_MONEY));	
			fk_bill.put("FI_REALITY_MONEY", df.format(FI_PAY_MONEY));				
			fk_bill.put("FI_REALITY_ACCOUNT", df.format(FI_PAY_MONEY));				
			//插入付款申请单。
			Dao.insert("rentDk.doInsertFundHead",fk_bill);
			
			//查看发票开具对象
			Map<String,Object> InvoiceMap=Dao.selectOne("rentDK.getFPDX", map);
			double yq_money = 0d; //剩余逾期金额
			if(map.get("WS_YQ_")!=null&&!"".equals(map.get("WS_YQ_"))&&Double.parseDouble(map.get("WS_YQ_").toString())>0){
				List<Map<String,Object>> over = Dao.selectList("rentDk.getOverDueData", map);
				if(over != null){
					for(int i=0; i<over.size(); i++){
						Map<String,Object> overdue = over.get(i);
						String ID = Dao.getSequence("SEQ_FUND_DETAIL");
						overdue.put("D_FUND_ID", HEAD_ID);
						overdue.put("ID", ID);				
						overdue.put("D_RECEIVE_DATE", map.get("ACCOUNT_DATE"));
						overdue.put("D_PROJECT_CODE", map.get("PRO_CODE"));
						overdue.put("D_STATUS", 2);
						overdue.put("D_BEGING_ID", Dao.selectOne("rentDk.getPERIOD", overdue));
						overdue.put("D_CLIENT_NAME", map.get("D_CLIENT_NAME"));
						overdue.put("D_CLIENT_CODE", map.get("D_CLIENT_CODE"));
						overdue.put("D_PAYEE", Security.getUser().getName());
						overdue.put("D_TO_THE_PAYEE", map.get("D_CLIENT_CODE"));
						if(InvoiceMap != null){
							overdue.put("INVOICE_TARGET_TYPE", InvoiceMap.get("INVOICE_TARGET_TYPE"));
							overdue.put("INVOICE_TARGET_ID", InvoiceMap.get("INVOICE_TARGET_ID"));
						}
						
						if(Double.parseDouble(map.get("D_PAY_MONEY").toString()) <= FI_PAY_MONEY){
							overdue.put("D_PAY_MONEY", map.get("D_PAY_MONEY"));
							Dao.insert("rentDk.doInsertAppDetail",overdue);
							FI_PAY_MONEY -= Double.parseDouble(map.get("D_PAY_MONEY").toString());
							yq_money = 0d;
						}else {
							overdue.put("D_PAY_MONEY", FI_PAY_MONEY);
							Dao.insert("rentDk.doInsertAppDetail",overdue);
							FI_PAY_MONEY = 0d;
							yq_money -= FI_PAY_MONEY;
							resultMap.put("WS_YQ_", yq_money);//剩余违约金
						}
					}
				}
			}
			
			map.put("BENJIN", "本金");
			map.put("LIXI", "利息");
			List<Map<String,Object>> rentList = Dao.selectList("rentDk.getBeginingDetail", map);
			double sy_money_ = 0d;
			if(rentList!=null&&FI_PAY_MONEY>0){
				for(int k=0; k<rentList.size(); k++){
				
					Map<String,Object> rentList_ = rentList.get(k);
					
					rentList_.put("D_CLIENT_NAME", map.get("D_CLIENT_NAME"));
					rentList_.put("D_CLIENT_CODE", map.get("D_CLIENT_CODE"));
					rentList_.put("D_PAYEE", Security.getUser().getName());
					rentList_.put("D_TO_THE_PAYEE", map.get("D_CLIENT_CODE"));
					
					if(InvoiceMap != null){
						rentList_.put("INVOICE_TARGET_TYPE", 3);
						rentList_.put("INVOICE_TARGET_ID", InvoiceMap.get("SUP_ID"));
					}
					
					String ID = Dao.getSequence("SEQ_FUND_DETAIL");
					rentList_.put("D_FUND_ID", HEAD_ID);
					rentList_.put("ID", ID);
					rentList_.put("D_RECEIVE_DATE", map.get("FI_ACCOUNT_DATE"));
					rentList_.put("D_PROJECT_CODE", map.get("PRO_CODE"));
					rentList_.put("D_STATUS", 2);
					sy_money_ = Double.parseDouble(rentList_.get("D_PAY_MONEY").toString()); 
					
					if(FI_PAY_MONEY >= Double.parseDouble(rentList_.get("D_PAY_MONEY").toString())){
						
						//插入应收明细
						Dao.insert("rentDk.doInsertAppDetail",rentList_);	
						//剩余应收
						FI_PAY_MONEY -= Double.parseDouble(rentList_.get("D_PAY_MONEY").toString());
						sy_money_ = 0;
					}else {
						rentList_.put("D_PAY_MONEY", FI_PAY_MONEY);
						//插入应收明细
						Dao.insert("rentDk.doInsertAppDetail",rentList_);
						
						FI_PAY_MONEY = 0d;
						sy_money_ -= FI_PAY_MONEY;
						resultMap.put("sy_money_",sy_money_);
						
						//本更应收起初表,申请状态
						Dao.update("rentDk.toUpdateBegining", rentList_);
					}
					
					if(FI_PAY_MONEY == 0){
						break;
					}
				}
			}
			
		}
		return resultMap;
	}
	
	
	/****************************************************************************************
	 ************************************添加申请单**正常回购********Auth=杨雪****************
	 ****************************************************************************************/
	
	/**
	 * 核销申请
	 * toAppRentCheck
	 * @date 2013-11-21 下午08:52:23
	 * @author 杨雪	 * @return 返回值类型
	 * @throws Exception
	 */
	public Object toAppRentCheck(Map<String,Object> map){
		
		//首先删除已生成的冲红申请单
		if(map.get("ZC_HEAD")!=null){
			map.put("fund_id", map.get("dbBZJdk"));
			Dao.delete("rentDk.delFundAccount", map);
			Dao.delete("rentDk.delFundDetail", map);
			Dao.delete("rentDk.delFundHead", map);
			
			//删除逾期数据
			Map<String,Object> overDue = Dao.selectOne("rentDk.getOverBuyBack", map);
			if(overDue!=null){
				Dao.delete("rentDk.delOverDue", overDue);
			}
		}
		
		// 组织机构应该取缓存 后面在改
		map.put("USER_ID", Security.getUser().getId());
		Map<String,Object> ORG_MAP=Dao.selectOne("payment.orgListByUserId", map);
		Map<String,Object> fk_bill = new HashMap<String,Object>();
		if(ORG_MAP!=null){
			map.put("ORG_ID", ORG_MAP.get("ORG_ID"));
			fk_bill.put("FI_ORG_ID", ORG_MAP.get("ORG_ID"));
		}
		
		//付款单id
		String HEAD_ID = Dao.getSequence("SEQ_FUND_HEAD"); 
		
		int i = 0;
		fk_bill.put("HEAD_ID", HEAD_ID);// 用户编号
		fk_bill.put("USERCODE", Security.getUser().getCode());// 用户编号
		fk_bill.put("USERNAME", Security.getUser().getName());// 用户名称
		fk_bill.put("FI_PAY_DATE", map.get("FI_ACCOUNT_DATE"));// 付款时间
		fk_bill.put("FI_PAY_MONEY", map.get("FI_REALITY_ACCOUNT"));// 付款金额
		fk_bill.put("FI_PROJECT_NUM", 1);// 项目数量
		fk_bill.put("FI_STATUS", 2);
		
		
		fk_bill.put("FI_REALITY_BANK", map.get("FI_REALITY_BANK"));// 设备回购
		fk_bill.put("FI_FLAG", 9);// 设备回购
		fk_bill.put("FI_REALITY_MONEY", map.get("FI_REALITY_ACCOUNT"));
		fk_bill.put("FI_ACCOUNT_DATE", map.get("FI_ACCOUNT_DATE"));
		fk_bill.put("FI_REALITY_ACCOUNT", map.get("FI_REALITY_ACCOUNT"));
		// 插入付款申请单。
		fk_bill.put("FI_REALITY_BANK", map.get("FI_REALITY_BANK"));
		i = Dao.insert("rentDk.doInsertFundHead", fk_bill);
		List<Map<String, Object>> begining = new ArrayList<Map<String, Object>>();
		if (i > 0) {
			// 查看begining表中应收起初表明细
			map.put("BENJIN", "本金");
			map.put("LIXI", "利息");
			map.put("QTFY", "其他费用");

			// 通过项目CODE查询出发票开具对象
			Map<String, Object> InvoiceMap = Dao.selectOne("rentDk.getFPDX",
					map);

			begining = Dao.selectList("rentDk.getBeginingDetail", map);

			if (begining != null) {
				for (int n = 0; n < begining.size(); n++) {// 逐条插入申请细单
					Map<String, Object> detail = begining.get(n);

					if (InvoiceMap != null) {
						detail.put("INVOICE_TARGET_TYPE", 1);
						detail.put("INVOICE_TARGET_ID", InvoiceMap
								.get("SUP_ID"));
					}

					String ID = Dao.getSequence("SEQ_FUND_DETAIL");
					detail.put("D_FUND_ID", HEAD_ID);
					detail.put("ID", ID);
					detail.put("D_STATUS", 2);
					detail.put("D_RECEIVE_DATE", map.get("FI_ACCOUNT_DATE"));
					detail.put("D_PROJECT_CODE", map.get("PRO_CODE"));

					detail.put("D_CLIENT_NAME", map.get("D_CLIENT_NAME"));
					detail.put("D_CLIENT_CODE", map.get("D_CLIENT_CODE"));
					detail.put("D_PAYEE", Security.getUser().getName());
					detail.put("D_TO_THE_PAYEE", map.get("D_CLIENT_CODE"));

					// 添加应收明细
					i = Dao.insert("rentDk.doInsertAppDetail", detail);

					// 添加实收明细
					detail.put("FA_FUND_ID", HEAD_ID);
					detail.put("FA_BEGING_ID", detail.get("D_BEGING_ID"));
					detail.put("FA_DETAIL_ID", ID);
					detail.put("FA_ACCOINT_MONEY", detail.get("D_PAY_MONEY"));
					detail.put("FA_ACCOINT_MONEY", detail.get("D_PAY_MONEY"));
					Dao.insert("fundNotEBank.doInsertAccount", detail);

					// 更行应收起初表
					detail.put("BEGINNING_PAID", detail.get("D_PAY_MONEY"));
					detail.put("BEGINNING_FLAG", 1);
					detail.put("BEGINNING_ID", detail.get("D_BEGING_ID"));
					detail.put("REALITY_TIME", map.get("FI_ACCOUNT_DATE"));
					Dao.update("fundNotEBank.doUpdateBeginning", detail);
				}
			}
			double WS_YQ_ = 0;
			List<Map<String,Object>> over1 = Dao.selectList("rentDk.getOverDueData", map);
			if(over1 != null){
				//计算利息总额
				for (int a = 0; a < over1.size(); a++) {
					WS_YQ_ += over1.get(a).get("D_PAY_MONEY")!=null&&!"".equals(over1.get(a).get("D_PAY_MONEY"))?Double.parseDouble(over1.get(a).get("D_PAY_MONEY")+""):0;
				}
				map.put("WS_YQ", WS_YQ_);
			}
			// 查看逾期记录
			if (map.get("WS_YQ") != null && !"".equals(map.get("WS_YQ"))
					&& Double.parseDouble(map.get("WS_YQ").toString()) > 0) {
				List<Map<String, Object>> over = Dao.selectList(
						"rentDk.getOverDueData", map);
				if (over != null) {
					for (int a = 0; a < over.size(); a++) {
						Map<String, Object> overdue = over.get(a);
						String ID = Dao.getSequence("SEQ_FUND_DETAIL");
						overdue.put("D_FUND_ID", HEAD_ID);
						overdue.put("ID", ID);
						overdue.put("D_RECEIVE_DATE", map
								.get("FI_ACCOUNT_DATE"));
						overdue.put("D_PROJECT_CODE", map.get("PRO_CODE"));
						overdue.put("D_PAY_CODE", map.get("PAYLIST_CODE"));
						overdue.put("D_STATUS", 2);
						//overdue.put("D_BEGING_ID", Dao.selectOne("rentDk.getPERIOD", overdue));

						overdue.put("D_CLIENT_NAME", map.get("D_CLIENT_NAME"));
						overdue.put("D_CLIENT_CODE", map.get("D_CLIENT_CODE"));
						overdue.put("D_PAYEE", Security.getUser().getName());
						overdue.put("D_TO_THE_PAYEE", map.get("D_CLIENT_CODE"));

						if (InvoiceMap != null) {
							overdue.put("INVOICE_TARGET_TYPE", 1);
							overdue.put("INVOICE_TARGET_ID", InvoiceMap
									.get("SUP_ID"));
						}

						// 插入应收表
						//overdue.put("D_PAY_MONEY", map.get("D_PAY_MONEY"));
						Dao.insert("rentDk.doInsertAppDetail", overdue);
						
						// 添加实收明细
						overdue.put("FA_FUND_ID", HEAD_ID);
						//overdue.put("FA_BEGING_ID", overdue.get("D_BEGING_ID"));
						overdue.put("FA_DETAIL_ID", ID);
						overdue.put("FA_ACCOINT_MONEY", overdue
								.get("D_PAY_MONEY"));
						overdue.put("FA_ACCOINT_MONEY", overdue
								.get("D_PAY_MONEY"));
						Dao.insert("fundNotEBank.doInsertAccount", overdue);
						//更新罚息表的实收
						Dao.update("rentDk.upateAppDetail",overdue);
					}
				}
			}
		}
		
		
		//更新回购主表
		Dao.update("rentDk.doUpdateBuyStatus",map);
		
		//以下为计算回购款逾期金额
		Map<String,Object> date = new HashMap<String,Object>();
		date.put("START_DATE",map.get("FI_ACCOUNT_DATE"));
		date.put("END_DATE",map.get("FI_ACCOUNT_DATE"));
		int days = 0;
		try{
			days = Util.TimeDay(date);
			if(days>0){
				double ys_money = map.get("FI_REALITY_MONEY")!=null&&!"".equals(map.get("FI_REALITY_MONEY"))?Double.parseDouble(map.get("FI_REALITY_MONEY").toString()):0d;
				
				double yq_money = ys_money * days * 0.0003;
				
				Map<String,Object> overDue = new HashMap<String,Object>();
				
				String maxPerid = Dao.selectOne("rentDk.getOverDueMax", map);			
				int maxPerid_1 = Integer.parseInt(maxPerid) + 1;
				overDue.put("PERIOD",maxPerid_1);
				
				overDue.put("PENALTY_RECE",yq_money);
				overDue.put("PENALTY_DAY",1);
				overDue.put("DUE_STATUS",1);
				overDue.put("RENT_RECE",map.get("FI_PAY_MONEY"));
				overDue.put("PROJECT_ID",map.get("PROJECT_ID"));
				overDue.put("PAYLIST_CODE",map.get("PAYLIST_CODE"));			
				overDue.put("RENT_DATE", map.get("RENT_DATE"));
				overDue.put("OVER_TYPE", "设备回购罚息");
				Dao.insert("BuyBack.insert_penaltyaInfo", overDue);
			}			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return HEAD_ID;
	}
	
	/****************************************************************************************
	 ************************************添加申请单**分期回购********Auth=杨雪****************
	 ****************************************************************************************/
	public Object toAppRentCheckFQ(Map<String,Object> map){	
		
		DecimalFormat df = new DecimalFormat(".00");//保留两位小数处理	
		
		//首先删除已生成的分期回购
		if(map.get("FQ_HEAD")!=null){
			map.put("fund_id", map.get("FQ_HEAD"));
			
			map.put("FUND_ID", map.get("FQ_HEAD"));
			
			List<Map<String,Object>> deList = Dao.selectList("rentWrite.queryDetailByFundId", map);
			if(deList != null){
				for(int a=0; a<deList.size(); a++){
					Map<String,Object> mmm = deList.get(a);
					Dao.update("rentDk.toUpdateBegining1", mmm);
				}
			}
			
			
			Dao.delete("rentDk.delFundAccount", map);
			Dao.delete("rentDk.delFundDetail", map);
			Dao.delete("rentDk.delFundHead", map);
			
			//删除逾期数据
			Map<String,Object> overDue = Dao.selectOne("rentDk.getOverBuyBack", map);
			if(overDue!=null){
				Dao.delete("rentDk.delOverDue", overDue);
			}
		}
		
		// 组织机构应该取缓存 后面在改
		map.put("USER_ID", Security.getUser().getId());
		Map<String,Object> ORG_MAP=Dao.selectOne("payment.orgListByUserId", map);
		
		if(ORG_MAP!=null){
			map.put("ORG_ID", ORG_MAP.get("ORG_ID"));
		}
		
		//插入租金冲抵申请当
		String HEAD_ID = Dao.getSequence("SEQ_FUND_HEAD"); 
		Map<String,Object> fk_bill = new HashMap<String,Object>();
		fk_bill.put("HEAD_ID", HEAD_ID);//用户编号
		fk_bill.put("USERCODE", Security.getUser().getCode());//用户编号
		fk_bill.put("USERNAME", Security.getUser().getName());//用户名称
		fk_bill.put("FI_PAY_DATE", map.get("FI_ACCOUNT_DATE"));//付款时间
		fk_bill.put("FI_ACCOUNT_DATE", map.get("FI_ACCOUNT_DATE"));
		fk_bill.put("FI_PROJECT_NUM", 1);//项目数量
		fk_bill.put("FI_STATUS", 2);
		fk_bill.put("FI_FLAG", 9);//设备回购
		
		//应收首期款
		double FI_PAY_MONEY = map.get("FI_REALITY_ACCOUNT")!=null&&!"".equals(map.get("FI_REALITY_ACCOUNT").toString())?Double.parseDouble(map.get("FI_REALITY_ACCOUNT").toString()):0d;
		
		fk_bill.put("FI_PAY_MONEY", df.format(FI_PAY_MONEY));	
		fk_bill.put("FI_REALITY_ACCOUNT", df.format(FI_PAY_MONEY));	
		fk_bill.put("FI_PAY_MONEY", df.format(FI_PAY_MONEY));
		
		//插入付款申请单。
		int a = Dao.insert("rentDk.doInsertFundHead",fk_bill); 
		int b = 0;
		int c = 0;
		int e = 0;
		//通过项目CODE查询出发票开具对象
		Map<String,Object> InvoiceMap=Dao.selectOne("rentDk.getFPDX", map);
		
		if(FI_PAY_MONEY>0){
			Map<String,Object> detail = new HashMap<String,Object>();
			detail.put("ITEM_FLAG", 4);
			detail.put("PAYLIST_CODE", map.get("PAYLIST_CODE"));
			List<Map<String,Object>> detailL = Dao.selectList("rentDk.getRentData1", detail);
			if(detailL!=null){
				for(int i=0; i<detailL.size(); i++){
				
					Map<String, Object> d = detailL.get(i);
					
					d.put("D_STATUS", 1);
					String ID = Dao.getSequence("SEQ_FUND_DETAIL");
					d.put("D_FUND_ID", HEAD_ID);
					d.put("ID", ID);				
					d.put("D_RECEIVE_DATE", map.get("FI_ACCOUNT_DATE"));
					d.put("D_PROJECT_CODE", map.get("PRO_CODE"));
					d.put("D_PAY_CODE", map.get("PAY_CODE"));
					d.put("D_PAY_CODE", map.get("PAY_CODE"));
					d.put("D_STATUS", 2);
					
					d.put("D_CLIENT_NAME", map.get("D_CLIENT_NAME"));
					d.put("D_CLIENT_CODE", map.get("D_CLIENT_CODE"));
					d.put("D_PAYEE", Security.getUser().getName());
					d.put("D_TO_THE_PAYEE", map.get("D_CLIENT_CODE"));
					if(InvoiceMap != null){
						d.put("INVOICE_TARGET_TYPE", 3);
						d.put("INVOICE_TARGET_ID", InvoiceMap.get("SUP_ID"));
					}
					
					//插入应收明细
					 b = Dao.insert("rentDk.doInsertAppDetail",d);	
					
					//插入实收明细
					d.put("FA_FUND_ID", HEAD_ID);
					d.put("FA_BEGING_ID", d.get("D_BEGING_ID"));
					d.put("FA_DETAIL_ID", ID);
					d.put("FA_ACCOINT_MONEY", d.get("D_PAY_MONEY"));
					d.put("FA_ACCOINT_MONEY", d.get("D_PAY_MONEY"));
					c = Dao.insert("fundNotEBank.doInsertAccount",d);
					
					//更行应收起初表
					d.put("BEGINNING_PAID", d.get("D_PAY_MONEY"));
					d.put("BEGINNING_FLAG", 1);
					d.put("BEGINNING_ID", d.get("D_BEGING_ID"));
					d.put("REALITY_TIME", map.get("FI_ACCOUNT_DATE"));
					
					
					e = Dao.update("fundNotEBank.doUpdateBeginning",d);
					
					FI_PAY_MONEY -= Double.parseDouble(d.get("D_PAY_MONEY").toString());
				}
			}
		}
		
		//更新回购主表
		int f = Dao.update("rentDk.doUpdateBuyStatus",map);
		
		//以下为计算回购款逾期金额
		Map<String,Object> date = new HashMap<String,Object>();
		date.put("START_DATE",map.get("FI_ACCOUNT_DATE"));
		date.put("END_DATE",map.get("FI_ACCOUNT_DATE"));
		int days = 0;
		try{
			days = Util.TimeDay(date);
			if(days>0){
				double ys_money = FI_PAY_MONEY;
				
				double yq_money = ys_money * days * 0.0003;
				
				Map<String,Object> overDue = new HashMap<String,Object>();
				
				String maxPerid = Dao.selectOne("rentDk.getOverDueMax", map);			
				int maxPerid_1 = Integer.parseInt(maxPerid) + 1;
				overDue.put("PERIOD",maxPerid_1);
				
				overDue.put("PENALTY_RECE",yq_money);
				overDue.put("PENALTY_DAY",1);
				overDue.put("DUE_STATUS",1);
				overDue.put("RENT_RECE",map.get("FI_PAY_MONEY"));
				overDue.put("PROJECT_ID",map.get("PROJECT_ID"));
				overDue.put("PAYLIST_CODE",map.get("PAYLIST_CODE"));			
				overDue.put("RENT_DATE", map.get("RENT_DATE"));
				overDue.put("OVER_TYPE", "设备回购罚息");
				Dao.insert("BuyBack.insert_penaltyaInfo", overDue);
			}		
			
		}catch(Exception ex){
			ex.printStackTrace();
			return "核销失败！！";
		}
		
		if(a>0&&b>0&&c>0&&e>0&&f>0){
			return HEAD_ID;
		}
		return  "";
	}

	
	
	/****************************************************************************************
	 ************************************核销申请单******************Auth=杨雪****************
	 ****************************************************************************************/
	
	/**
	 * 添加account实收明细表, 保证金留购价抵扣
	 * doHeXiaoRent
	 * @date 2013-11-23 下午02:48:40
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public void doCheckedBZJandLGJ(Map<String,Object> map){
		
		String FUND_ID=map.get("BZJandLGJ").toString();//申请单id
		
		map = Dao.selectOne("rentDk.getBuyBackData", map);
		
		//保证金
		double is_bzj = (map.get("CAUTION_DK")!=null&&!"".equals(map.get("CAUTION_DK")))?Double.parseDouble(map.get("CAUTION_DK").toString()):0d;
	
		//留购价
		double is_lgj = (map.get("NOMINALPRICE_DK")!=null&&!"".equals(map.get("NOMINALPRICE_DK")))?Double.parseDouble(map.get("NOMINALPRICE_DK").toString()):0d;		
		
		//本次实付
		double FI_REALITY_ACCOUNT = is_lgj + is_bzj;
		
		//本次实付递减
		double FI_REALITY_ACCOUNT_YU = FI_REALITY_ACCOUNT;
		
		double CANUSE_MONEY = 0d;
		
		//来款单子表
		map.put("FUND_ID",FUND_ID);
		List<Map<String,Object>> listDe=Dao.selectList("rentWrite.queryDetailByFundId",map);
		
		for(int d=0;d<listDe.size();d++){		
			Map<String,Object> mapDK=(Map<String,Object>)listDe.get(d);
			
			//剩余应收
			double D_PAY_MONEY_ = Double.parseDouble(mapDK.get("D_PAY_MONEY")+"");
			if(is_bzj>0){
				map.put("TYPE","4");
				List<Map<String,Object>> pool_bzj = Dao.selectList("rentDk.getPoolData", map);
				for(int n=0; n<pool_bzj.size(); n++){
					Map<String,Object> bzj = pool_bzj.get(n);				
					
					if(Double.parseDouble(bzj.get("CANUSE_MONEY").toString()) <= Double.parseDouble(mapDK.get("D_PAY_MONEY").toString())){
						mapDK.put("FA_POOL_ID", bzj.get("POOL_ID"));
						mapDK.put("D_PAY_MONEY",bzj.get("CANUSE_MONEY"));
						//插入實收明細
						Dao.insert("rentWrite.doInsertAccountByDetail", mapDK);
						
						D_PAY_MONEY_ = Double.parseDouble(mapDK.get("D_PAY_MONEY").toString()) - Double.parseDouble(bzj.get("CANUSE_MONEY").toString());
						FI_REALITY_ACCOUNT_YU -= Double.parseDouble(bzj.get("CANUSE_MONEY").toString());
					}else {
						mapDK.put("D_PAY_MONEY",mapDK.get("D_PAY_MONEY"));
						//插入實收明細
						Dao.insert("rentWrite.doInsertAccountByDetail", mapDK);
						D_PAY_MONEY_ = 0;
						FI_REALITY_ACCOUNT_YU -=  Double.parseDouble(mapDK.get("D_PAY_MONEY").toString());
					}
					
					
					
					//資金池剩餘保證金
					CANUSE_MONEY = Double.parseDouble(bzj.get("BASE_MONEY").toString()) - Double.parseDouble(mapDK.get("D_PAY_MONEY").toString());
					
					//更新資金池數據
					mapDK.put("CANUSE_MONEY", CANUSE_MONEY);
					mapDK.put("FA_POOL_ID", bzj.get("POOL_ID"));
					System.out.println("CANUSE_MONEY ==="+CANUSE_MONEY);
					Dao.update("rentWrite.updatePoolStateByfundId",mapDK);
					System.out.println("CANUSE_MONEY ==="+CANUSE_MONEY);
					is_bzj -= Double.parseDouble(mapDK.get("D_PAY_MONEY").toString());
					
					if(CANUSE_MONEY==0){//剩余保证金
						break;
					}
				}
			}
			
			if(is_lgj>0&&D_PAY_MONEY_>0){//如果留購價大於零， 調用留購價
				
				map.put("LGJ", "留购价款");
				//查看留購價
				List<Map<String,Object>> lgj = Dao.selectList("rentDk.getLGJData",map);
				for(int m=0; m<lgj.size(); m++){
					Map<String,Object> lgj_ = lgj.get(m);
					if(Double.parseDouble(lgj_.get("D_PAY_MONEY").toString()) <= Double.parseDouble(mapDK.get("D_PAY_MONEY").toString())){
						mapDK.put("D_PAY_MONEY",lgj_.get("D_PAY_MONEY"));
						
						
						if(D_PAY_MONEY_ > 0){
							mapDK.put("FA_POOL_ID", lgj_.get(""));
							mapDK.put("D_PAY_MONEY",D_PAY_MONEY_);
							//插入實收明細
							Dao.insert("rentWrite.doInsertAccountByDetail", mapDK);
							FI_REALITY_ACCOUNT_YU -= D_PAY_MONEY_;
						}
						
						D_PAY_MONEY_ = Double.parseDouble(mapDK.get("D_PAY_MONEY").toString()) - Double.parseDouble(lgj_.get("D_PAY_MONEY").toString());
					}else {
						mapDK.put("D_PAY_MONEY",mapDK.get("D_PAY_MONEY"));
						//插入實收明細
						Dao.insert("rentWrite.doInsertAccountByDetail", mapDK);					
						is_lgj -= Double.parseDouble(mapDK.get("D_PAY_MONEY").toString());
						FI_REALITY_ACCOUNT_YU -=  Double.parseDouble(mapDK.get("D_PAY_MONEY").toString());
					}
					
					if(is_lgj == 0){
						break;
					}
				}
			}
		}
	}
	
	/**
	 * 添加account实收明细表, DB保证金抵扣
	 * doCheckedDBbzj
	 * @date 2013-12-1 下午06:26:23
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	public void doCheckedDBbzj(Map<String,Object> map){
		String FUND_ID=map.get("dbBZJdk").toString();//申请单id
		
		
		map = Dao.selectOne("rentDk.getBuyBackData", map);
		
		//DB保证金
		double is_dbbzj = (map.get("DBDEPOSIT_DK")!=null&&!"".equals(map.get("DBDEPOSIT_DK")))?Double.parseDouble(map.get("DBDEPOSIT_DK").toString()):0d;
	
		//本次实付
		double FI_REALITY_ACCOUNT=(map.get("FI_REALITY_ACCOUNT")!=null && !map.get("FI_REALITY_ACCOUNT").equals(""))?Double.parseDouble(map.get("FI_REALITY_ACCOUNT").toString()):0d;
		
		//本次实付递减
		double FI_REALITY_ACCOUNT_YU=FI_REALITY_ACCOUNT;
		
		double CANUSE_MONEY = 0d;
		
		map.put("FUND_ID",FUND_ID);
		
		//来款单子表
		List<Map<String,Object>> listDe=Dao.selectList("rentWrite.queryDetailByFundId",map);
		
		for(int d=0;d<listDe.size();d++){		
			Map<String,Object> mapDK=(Map<String,Object>)listDe.get(d);
			
			//剩余应收
			double D_PAY_MONEY_ = Double.parseDouble(mapDK.get("D_PAY_MONEY").toString());
			if(is_dbbzj>0){
				map.put("TYPE","4");
				List<Map<String,Object>> pool_bzj = Dao.selectList("rentDk.getPoolData", map);
				for(int n=0; n<pool_bzj.size(); n++){
					Map<String,Object> bzj = pool_bzj.get(n);				
					
					if(Double.parseDouble(bzj.get("CANUSE_MONEY").toString()) <= Double.parseDouble(mapDK.get("D_PAY_MONEY").toString())){
						mapDK.put("FA_POOL_ID", bzj.get("POOL_ID"));
						mapDK.put("D_PAY_MONEY",bzj.get("CANUSE_MONEY"));
						//插入實收明細
						Dao.insert("rentWrite.doInsertAccountByDetail", mapDK);

						D_PAY_MONEY_ = Double.parseDouble(mapDK.get("D_PAY_MONEY").toString()) - Double.parseDouble(bzj.get("CANUSE_MONEY").toString());
						FI_REALITY_ACCOUNT_YU -= Double.parseDouble(mapDK.get("D_PAY_MONEY").toString());
					}else {
						mapDK.put("D_PAY_MONEY",mapDK.get("D_PAY_MONEY"));
						//插入實收明細
						Dao.insert("rentWrite.doInsertAccountByDetail", mapDK);
						D_PAY_MONEY_ = 0;
					}
					
					//資金池剩餘保證金
					CANUSE_MONEY = Double.parseDouble(bzj.get("BASE_MONEY").toString()) - Double.parseDouble(mapDK.get("D_PAY_MONEY").toString());
					
					//更新資金池數據
					mapDK.put("CANUSE_MONEY", CANUSE_MONEY);
					mapDK.put("FA_POOL_ID", bzj.get("POOL_ID"));
					System.out.println("CANUSE_MONEY ==="+CANUSE_MONEY);
					Dao.update("rentWrite.updatePoolStateByfundId",mapDK);
					System.out.println("CANUSE_MONEY ==="+CANUSE_MONEY);
					is_dbbzj -= Double.parseDouble(mapDK.get("D_PAY_MONEY").toString());
					
					if(CANUSE_MONEY==0){//剩余保证金
						break;
					}
				}
			}
		}
	}
	
	
	/****************************************************************************************
	 ************************************核销申请单**分期回购********Auth=杨雪****************
	 ****************************************************************************************/
	public int doCheckedFund1(Map<String,Object> map){		
		rentWriteService rentWS = new rentWriteService();
		map.put("FI_STATUS", 2);
		map.put("USERNAME",Security.getUser().getName());
		map.put("USERCODE",Security.getUser().getCode());
		int i = Dao.update("rentWrite.toUpdateFHead", map);
		Dao.update("rentWrite.doUpdateDetail",map);
		
		//查询核销时间，操作罚息
		Map mapTime=Dao.selectOne("rentWrite.queryFundTime", map);
		String FI_ACCOUNT_DATE=(mapTime!=null && mapTime.get("FI_ACCOUNT_DATE")!=null && !mapTime.get("FI_ACCOUNT_DATE").equals(""))?mapTime.get("FI_ACCOUNT_DATE").toString():"";
		if (i > 0) {				
				// 根据申请单id查看应收金额和实收总金额
				List<Map<String, Object>> detialL = Dao.selectList("rentWrite.getDetailData", map);
				// 当不为空时跟新应收明细
				if (detialL != null) {
					for (int k = 0; k < detialL.size(); k++) {
						Map<String, Object> realMoney = detialL.get(k);
						float beginning_money = Float.parseFloat(realMoney.get("BEGINNING_MONEY").toString());// 应收金额
						float real_money = Float.parseFloat(realMoney.get("REAL_MONEY").toString());// 实收金额
						realMoney.put("BEGINNING_PAID", real_money);
						if(realMoney.get("FA_BEGING_ID")!=null&&!"".equals(realMoney.get("FA_BEGING_ID"))){
							realMoney.put("BEGINNING_ID", realMoney.get("FA_BEGING_ID"));
						}
						
						if (beginning_money == real_money) {// 当应收金额==实收金额时，应收明细中的核销状态变更为核销已完成(1);
							realMoney.put("BEGINNING_FLAG", 1);
							realMoney.put("REALITY_TIME", FI_ACCOUNT_DATE);
						} else {// 当应收金额!=实收金额时，应收明细中的核销状态为核销未完成(0)
							realMoney.put("BEGINNING_FLAG", 0);
						}
						
						realMoney.put("REALITY_TIME", FI_ACCOUNT_DATE);
						
						//查询之前保证金和留购价抵扣的金额
						Map selectUpdateBeginning = Dao.selectOne("rentWrite.selectUpdateBeginning", realMoney);
						double d_receive_money = selectUpdateBeginning.get("D_RECEIVE_MONEY")==null||"".equals(selectUpdateBeginning.get("D_RECEIVE_MONEY"))?0:Double.parseDouble(selectUpdateBeginning.get("D_RECEIVE_MONEY")+"");
						//realMoney.put("BEGINNING_MONEY", realMoney.get("realMoney")==null||"".equals(realMoney.get("realMoney"))?d_receive_money:Double.parseDouble(realMoney.get("realMoney")+""));
						if(d_receive_money>0){
							realMoney.put("BEGINNING_MONEY",d_receive_money);
						}
						i = Dao.update("rentWrite.doUpdateBeginning", realMoney);
						
						realMoney.put("TIME", FI_ACCOUNT_DATE);
						if(realMoney.get("FA_BEGING_ID")!=null&&!"".equals(realMoney.get("FA_BEGING_ID"))){
							rentWS.dunUpdateStatus(realMoney);
						}
					}
				}
				
				if (i > 0) {
					return i;
				} else {
					return 0;
				}
		}
		return 0;
	}
	
	/****************************************************************************************
	 ************************************正常回购档案管理**************Auth=杨雪**************
	 ****************************************************************************************/
	public void doAddProjectContractList(String jbpmId) {
		Map<String, Object> param = JBPM.getVeriable(jbpmId);
		String PROJECT_ID = null;
		if (!StringUtils.isBlank(param.get("PROJECT_ID"))) {
			PROJECT_ID = param.get("PROJECT_ID") + "";
			ProjectContractManagerService psms = new ProjectContractManagerService();
			param.putAll(psms.doShowClientTypeByProjectId(PROJECT_ID));
			// 确认流程匹配模块
			String JBPMID=jbpmId.substring(0,jbpmId.indexOf("."));
			param.put("_TYPE", "流程定义模块");
			param.put("JBPMID",JBPMID);
			List<String> jbpmList=Dao.selectList("rentDk.queryJbpmModel", param);
			String JBPM_MODEL = null;
			if(jbpmList.size()>0)
				JBPM_MODEL =jbpmList.get(0); 
			else
				JBPM_MODEL=JBPMID;
			
			List<Map<String, Object>> list = psms.doShowDossierFileConfigFromDataDictionary(param.get("CLIENT_TYPE") + "", JBPM_MODEL, null);
			/***************************** 准备数据 ********************/
			String CLIENT_ID = param.get("CLIENTID") + "";
			List<Map<String, Object>> rstList = new ArrayList<Map<String, Object>>();
			Map<String, Object> mm = new HashMap<String, Object>();
			for (Map<String, Object> map : list) {
				Map<String, Object> rstMap = new HashMap<String, Object>();
				rstMap.put("TPM_CODE", map.get("CODE"));
				rstMap.put("TPM_TYPE", map.get("FILE_NAME"));
				rstMap.put("TPM_CUSTOMER_TYPE", map.get("CLIENT_TYPE_NAME"));
				rstMap.put("PROJECT_ID", PROJECT_ID);
				rstMap.put("FILE_REMARK", map.get("REMARK"));
				rstMap.put("PAYLIST_CODE", param.get("PAYLIST_CODE"));
				rstMap.put("DOSSIERTYPE", "1");
				rstMap.put("DOSSIER_PAGE", map.get("FILE_PAGE"));
				rstMap.put("DOSSIER_COUNT", map.get("FILE_COUNT"));
				rstMap.put("FILE_TYPE", map.get("FILE_TYPE"));
				rstMap.put("CLIENT_ID", CLIENT_ID);
				String REMARK=map.get("REMARK")+"";
				if(REMARK.contains(JBPM_MODEL)){
					REMARK=JBPM_MODEL;
				}
				rstMap.put("TPM_BUSINESS_PLATE", REMARK);
				rstList.add(rstMap);
			}
			mm.put("PROJECT_CODE", param.get("PROJECT_CODE"));
			mm.put("CLIENT_ID", CLIENT_ID);
			mm.put("SEND_TYPE", "1");
			mm.put("FILEINFO", JSONArray.fromObject(rstList)+"");

			/********************* 更新保存数据并做归档处理 ****************************/
			PigeonholeService pService = new PigeonholeService();
			pService.doDelPigeonholeApplyInfo(mm);
			Map<String, Object> m = new HashMap<String, Object>();
			try {
				String APPLY_ID = pService.doAddPigeonholeApplyInfo(mm);
				m.put("DOSSIER_APPLY_ID", APPLY_ID);
				JBPM.setExecutionVariable(jbpmId, m);
			} catch (Exception e) {
				throw new ActionException("归档申请失败", e);
			}
			if (rstList.size() > 0) {
				int i = 0;
				psms.doDelContractByProjectId(rstList.get(0));
				for (Map<String, Object> map : rstList) {
					i += psms.doAddCheckedContract(map);
				}
			} else {
				throw new ActionException("没有索引到需要的数据");
			}
		} else {
			throw new ActionException("没有找到匹配的项目,请联系管理员！");
		}
	}
}
