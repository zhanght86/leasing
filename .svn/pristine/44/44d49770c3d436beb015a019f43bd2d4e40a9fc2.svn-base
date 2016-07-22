package com.pqsoft.leeds.verify.service;

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
import com.pqsoft.util.PageUtil;

public class VerifyService {
	private static final String PATH = "custInfoInput.";

	public List<Map<String, Object>> getFilesByProId(String proId){
		String proCode = this.getProCode(proId);
		return this.getFilesByProCode(proCode);
	}
	public List<Map<String, Object>> getFilesByProCode(String proCode){
		Map<String, Object> p1 = new HashMap<String, Object>();
		p1.put("proCode", proCode);
		List<Map<String, Object>> pics = Dao.selectList(PATH+"getPics", p1);
		
		for(Map<String, Object> pic : pics) {
			pic.put("filename", pic.get("FILE_NAME")+"."+pic.get("FILE_TYPE"));
			pic.put("filepath", pic.get("ORIGINAL_PATH"));
			String[] catalogs = StringUtil.split(pic.get("ORIGINAL_PATH").toString(), '\\');
			pic.put("fileCatalog", catalogs[6]);
		}
		return pics;
	}
	
	public String getProCode(String proId) {
//		String ID = p
		String proCode = Dao.selectOne(PATH+"getProCode", proId);
		return proCode;
	}
	
	public static void main(String[] args) {
		org.springframework.core.io.Resource rs = new FileSystemResource("\\pqsoft\\file\\picture\\朱寿华_K000318\\RZZL201500386\\项目资料附件清单\\承租人配偶身份证复印件\\33bd6f47-3ee0-4758-adf1-e306faa74f3eQQ图片20150228225918.png");
		System.out.println(rs.exists());
//		try {
////			InputStream is = new FileSystemResource("\\pqsoft\\file\\picture\\朱寿华_K000318\\RZZL201500386\\项目资料附件清单\\承租人配偶身份证复印件\\33bd6f47-3ee0-4758-adf1-e306faa74f3eQQ图片20150228225918.png").getInputStream();
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////		String[] catalogs = StringUtil.split("\\pqsoft\\file\\picture\\朱寿华_K000318\\RZZL201500386\\项目资料附件清单\\承租人配偶身份证复印件\\33bd6f47-3ee0-4758-adf1-e306faa74f3eQQ图片20150228225918.png", '\\');
////		int i = 0;
	}
}
