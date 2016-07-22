package com.pqsoft.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import org.apache.log4j.Logger;

import com.pqsoft.finance_YY.service.finance_BaseService;

public class jdbc_YongYou {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private Connection dbcon;
	
	private String driverName = "net.sourceforge.jtds.jdbc.Driver";   //加载JDBC驱动  
	private String dbURL = "jdbc:jtds:sqlserver://192.168.150.19:1433; DatabaseName=UFDATA_002_2013";   //连接服务器和数据库sample  
	 
	private String userName = "sa";   //默认用户名  
	 
	private String userPwd = "Ufida123";   //密码  

	public static void main(String[] srg) {  
		jdbc_YongYou yu=new jdbc_YongYou();
		yu.searchTest();
	} 
	
	
	public Connection getConnection() throws SQLException {
		 try {  
			 if (dbcon != null && !dbcon.isClosed()){
					;
				}
				else{
					Class.forName(driverName);
					dbcon = DriverManager.getConnection(dbURL, userName, userPwd);
					
				}
				
		 } catch (Exception e) {  
			 e.printStackTrace();  
			 
		 }
		 finally{
			 return dbcon;
		 }
		
	}
	
	
	public void searchTest() {
		String sql = "select name from sysobjects ";
		//log.debug(sql);
		Connection con = null;
		Statement state = null;
		ResultSet rs = null;
		try {
			con = this.getConnection();
			state = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = state.executeQuery(sql);
			while (rs.next()) {
				System.out.println("---------------------aaa="+rs.getString(1));
			}
			finance_BaseService baseService=new finance_BaseService();
			baseService.test();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(rs, state, con);
		}
	}
	
	public void closeConnection(ResultSet rs, Statement state, Connection con) {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
			rs = null;
			logger.error(e);
		}
		try {
			if (state != null)
				state.close();
		} catch (Exception e) {
			state = null;
			logger.error(e);
		}
		try {
			if (con != null)
				con.close();
		} catch (Exception e) {
			con = null;
			logger.error(e);
		}
	}
}
