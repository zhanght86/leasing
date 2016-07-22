package com.pqsoft.finance_YY.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.jdbc_YongYou;

public class finance_BaseService {
	
	String xmlPath = "finance_Base.";//xml路径
	
	//从oracle数据库读数据写入到sqlserver数据库
	public void test(){
		List<fil_finance_BusInTable_entity> list=Dao.selectList(xmlPath+"getFil_Finance_BusInTable");
		
		Connection con = null;
		Statement state = null;
		ResultSet rs = null;
		
		jdbc_YongYou yy=new jdbc_YongYou();
		
		try {
			con = yy.getConnection();
			state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for(int i=0;i<list.size();i++){
				fil_finance_BusInTable_entity entity=list.get(i);
				String sql="insert into GL_BusInTable (iBussid,cYwbh,cYwName,ddrdatetime) values ('"+entity.getIBUSSID()+"','"+entity.getCYWBH()+"','"+entity.getCYWNAME()+"',getdate())";
				System.out.println("---------------------------sql="+sql);
				state.execute(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		
		
		
	}

}
