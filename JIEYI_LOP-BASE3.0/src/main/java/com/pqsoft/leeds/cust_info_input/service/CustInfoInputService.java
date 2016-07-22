package com.pqsoft.leeds.cust_info_input.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ws.security.util.StringUtil;
import org.springframework.core.io.FileSystemResource;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;

public class CustInfoInputService {
	private static final String PATH = "custInfoInput.";

	public String getProId(String proCode) {
//		String ID = p
		String proId = Dao.selectOne(PATH+"getProId", proCode);
		return proId;
	}
	public String getProCode(String proId) {
//		String ID = p
		String proCode = Dao.selectOne(PATH+"getProCode", proId);
		return proCode;
	}
	
	public int updatePictureStatus(Map<String, Object> params) {
		params.put("CHECK_NAME", Security.getUser().getName()) ;
		
		return Dao.update(PATH+"updatePictureStatus2",params) ;
 	}
	
	//附件资料批量审核通过
	public int updatePictureStatusPiLiang(Map<String, Object> params) {
		params.put("CHECK_NAME", Security.getUser().getName()) ;
		
		return Dao.update(PATH+"updatePictureStatus3",params) ;
 	}
	
	public static void main(String[] args) {
		
	}
	
}
