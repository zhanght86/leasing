package com.pqsoft.dunReport.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.util.PageUtil;
import com.pqsoft.util.PaymentIRRUtil;
import com.pqsoft.util.Util;

public class DunReportTableService {
	/*****逾期报表******@auth: king 2014年7月30日 *************************/
	private final String _NAMESPACE="dunReport.";
	
	//逾期旅
	public Page queryDunRatePage(Map<String, Object> m) {
		return PageUtil.getPage(m, _NAMESPACE+"showDunRateDate", _NAMESPACE+"showDunDateRate_count");
	}
	//逾期旅
	public Page queryDunFXRatePage(Map<String, Object> m) {
		return PageUtil.getPage(m, _NAMESPACE+"showDunFXRateDate", _NAMESPACE+"showDunFXDateRate_count");
	}
	
	//项目逾期率
	public Page queryRateProjectData(Map<String, Object> m) {
		return PageUtil.getPage(m, _NAMESPACE+"showDunProjectRateDate", _NAMESPACE+"showDunProjectRateDate_count");
	}
	
	//查询客户表符合条件的客户最小日期及当前日期
	public Map queryDateByCust(){
		return (Map)Dao.selectOne(_NAMESPACE+"CUSTCREATE_TIME");
	}
	
	/**
	 * 查询数据列表
	 * @param m
	 * @return
	 * @author King 2014年8月3日
	 */
	public List<Map<String,Object>> clientList(Map<String,Object> m){
		return Dao.selectList( _NAMESPACE+"showDunRateDate", m);
	}
	
	/**
	 * 定期刷历史月份的逾期数据  每月最后一天刷
	 * 
	 * @author King 2014年8月8日
	 */
	public void addFiOverDueMonth(){
		Map<String,Object> param=new HashMap<String,Object>();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getMaximum(Calendar.DAY_OF_MONTH));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		param.put("CREATE_DATE", sdf.format(calendar.getTime()));
		Dao.insert(_NAMESPACE+"addFiOverDueMonth", param);
	}
	
	/**
	 * 刷当前日期的数据
	 * 
	 * @author King 2014年8月8日
	 */
	public void addFiOverDueMonth_(){
		Map<String,Object> param=new HashMap<String,Object>();
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(new Date());
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		param.put("CREATE_DATE", sdf.format(calendar.getTime()));
		Dao.insert(_NAMESPACE+"addFiOverDueMonth", param);
	}
	
	
	public List<String> listX(Map map){
		List<Map<String,Object>> list=Dao.selectList(_NAMESPACE+"BJFX_REPORT_TYPE", map);//横坐标
		List<String> xlist=new ArrayList<String>();
		for(int i=0;i<list.size();i++)
		{
			Map<String,Object> xMap=(Map<String,Object>)list.get(i);
			if(xMap!=null && xMap.get("DATA_TYPE")!=null && !xMap.get("DATA_TYPE").equals("")){
				String DATA_TYPE=xMap.get("DATA_TYPE").toString();
				xlist.add(xMap.get("DATA_TYPE").toString());
			}
			
		}
		return xlist;
	}
	
	public List<Map<String,Object>> listY(List<String> listx,Map map){
		String REPORT_DATE=map.get("REPORT_DATE").toString();
		List listDate=new ArrayList();
		if(REPORT_DATE.equals("1")){//利润
			for(int i=0;i<listx.size();i++){
				map.put("DATA_TYPE", listx.get(i));
				Map mapDate=Dao.selectOne(_NAMESPACE+"querySYList", map);
				if(mapDate!=null && mapDate.get("DATA_VALUE")!=null && !mapDate.get("DATA_VALUE").equals("")){
					listDate.add(mapDate.get("DATA_VALUE"));
				}
				else{
					listDate.add(0);
				}
				
			}
		}else if(REPORT_DATE.equals("2")){//收益率
			
			
			for(int i=0;i<listx.size();i++){
				map.put("DATA_TYPE", listx.get(i));
				List<Map<String,Object>> rstList=Dao.selectList(_NAMESPACE+"queryIRRCashFlowList", map);
				
					
					if(rstList.size()>0){
						double []cashFlows= new double [rstList.size()];
						Date []date=new Date[rstList.size()];
						for (int h = 0; h < rstList.size(); h++) {
							cashFlows[h]=Double.parseDouble(rstList.get(h).get("MONEY")+"");
							date[h]=(Date) rstList.get(h).get("PAY_DATE");
						}
						System.out.println("-----------------"+PaymentIRRUtil.getIRR(cashFlows, date, Double.NaN,  1));
						double real_irr=0d;
						if(PaymentIRRUtil.getIRR(cashFlows, date, Double.NaN,  1) == Double.NaN){
							real_irr=0d;
						}
						else{
							real_irr=Util.formatDouble6(PaymentIRRUtil.getIRR(cashFlows, date, Double.NaN,  1));
						}
						System.out.println("-----------------"+real_irr);
						real_irr*=100;
						listDate.add(real_irr);
					}
					else{
						listDate.add(0);
					}
					
				
			}
			
			
			
			
		}else if(REPORT_DATE.equals("3")){//逾期率
			for(int i=0;i<listx.size();i++){
				map.put("DATA_TYPE", listx.get(i));
				Map mapDate=Dao.selectOne(_NAMESPACE+"queryYQLList", map);
				if(mapDate!=null && mapDate.get("DATA_VALUE")!=null && !mapDate.get("DATA_VALUE").equals("")){
					listDate.add(mapDate.get("DATA_VALUE"));
				}
				else{
					listDate.add(0);
				}
				
			}
		}else if(REPORT_DATE.equals("4")){//逾期金额
			for(int i=0;i<listx.size();i++){
				map.put("DATA_TYPE", listx.get(i));
				Map mapDate=Dao.selectOne(_NAMESPACE+"queryDunMoneyList", map);
				if(mapDate!=null && mapDate.get("DATA_VALUE")!=null && !mapDate.get("DATA_VALUE").equals("")){
					listDate.add(mapDate.get("DATA_VALUE"));
				}
				else{
					listDate.add(0);
				}
				
			}
		}
		
		
		List<Map<String,Object>> listY=new ArrayList<Map<String,Object>>();
		Map mapY=new HashMap();
		mapY.put("DATA_VALUE", listDate);
		listY.add(mapY);
		return listY;
	}
}
