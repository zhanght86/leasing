package com.pqsoft.fi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.binding.corba.wsdl.Array;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.DateUtil;

public class StatementService {

	/**
	 * 明细
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> getList(Map<String, Object> param) {
		return Dao.selectList("fi.statement.getPaymentList", param);
	}
	public List getFundDetail(Map<String, Object> param) {
		return Dao.selectList("fi.statement.getFundDetail", param);
	}
	public Excel exportData(Map<String,Object> map) {
		// 表头
		String columnString = "来款类型,来款时间,来款金额,剩余金额,支付表编号,合同编号,应收款项名称,应收金额,实分款项名称,实分金额";
		String columnName = "FUND_NOTDECO_STATE,FUND_ACCEPT_DATE,FUND_RECEIVE_MONEY,FUND_SY_MONEY,"
			+"D_PAY_CODE,LEASE_CODE,PERIOD,D_PAY_MONEY,D_PAY_PROJECT,D_RECEIVE_MONEY"; 
		String[] columns = columnString.split(",");
		String[] columnNames = columnName.split(",");
		LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
		for (int i = 0; i < columnNames.length; i++) {
			title.put(columnNames[i], columns[i]);
		}
		// 数据
		List<Map<String,Object>> list_Data = new ArrayList<Map<String,Object>>(); 
		System.out.println(map+"-----------------------");
		List<Map<String,Object>> list = Dao.selectList("fi.statement.getPageFundNew2", map);
		for(int i=0;i<list.size();i++){
			System.out.println(list.get(i)+"-------------item--------------");
			Map<String,Object> map_item = list.get(i);
			List<Map<String,Object>> list_Detail = Dao.selectList("fi.statement.getFundDetail", map_item);
			for(int j=0;j<list_Detail.size();j++){
				Map<String,Object> map_each = list_Detail.get(j);
				if(j==0){
					if(((String)map_item.get("FUND_NOTDECO_STATE")).equals(0+"")){
						map_each.put("FUND_NOTDECO_STATE","源资金");
					}else if(((String)map_item.get("FUND_NOTDECO_STATE")).equals(1+"")){
						map_each.put("FUND_NOTDECO_STATE","待分解");
					}else if(((String)map_item.get("FUND_NOTDECO_STATE")).equals(8+"")){
						map_each.put("FUND_NOTDECO_STATE","冲红作废");
					}
					map_each.put("FUND_ACCEPT_DATE", map_item.get("FUND_ACCEPT_DATE"));
					map_each.put("FUND_RECEIVE_MONEY", map_item.get("FUND_RECEIVE_MONEY"));
					map_each.put("FUND_SY_MONEY", map_item.get("FUND_SY_MONEY"));
					System.out.println(map_each.get("PERIOD")+"--"+map_each.get("D_PAY_PROJECT"));
					if(map_each.get("PERIOD")!=null){
						String mm = "第"+map_each.get("PERIOD")+"期"+map_each.get("D_PAY_PROJECT");
						map_each.put("PERIOD", mm);
						map_each.put("D_PAY_PROJECT",mm);
					}else{
						map_each.put("PERIOD", map_each.get("D_PAY_PROJECT"));
					}
					list_Data.add(map_each);
				}else{
					map_each.put("FUND_NOTDECO_STATE", null);
					map_each.put("FUND_ACCEPT_DATE", null);
					map_each.put("FUND_RECEIVE_MONEY", null);
					map_each.put("FUND_SY_MONEY", null);
					if(map_each.get("PERIOD")!=null){
						String mm = "第"+map_each.get("PERIOD")+"期"+map_each.get("D_PAY_PROJECT");
						map_each.put("PERIOD", mm);
						map_each.put("D_PAY_PROJECT",mm);
					}else{
						map_each.put("PERIOD", map_each.get("D_PAY_PROJECT"));
					}
					list_Data.add(map_each);
				}
			}
			System.out.println(list_Data+"------------list_Data---------------");
		}
		// 封装excel
		Excel excel = new Excel();
		excel.setAutoColumn(15);
		excel.addTitle(title);
		excel.setName("workFlow" + DateUtil.getSysDate("yyyyMMddHHmmss") + ".xls");
		excel.addSheetName("sheet01");
		excel.addData(list_Data);
		excel.setHeadDate(true);
		excel.setHeadTitles("客户名："+map.get("CLIENT_NAME"));
		return excel;
	}
}
