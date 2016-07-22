package com.pqsoft.advert.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class AdvertService {
	 private String basepath = "advert.";
		/**
		 * 分页显示
		 * @param param
		 * @return
		 */
		public Page getPageData(Map<String,Object> param){
			Page page = new Page(param);
			JSONArray array = JSONArray.fromObject(Dao.selectList(basepath+"queryAdList",param));
			page.addDate(array, Dao.selectInt(basepath+"selectAdCount", param));
			return page;
		}
		
		public int saveAd(Map<String,Object> param){
			return Dao.insert(basepath+"saveAd", param);
		}
		public int saveAdImage(Map<String,Object> param){
			return Dao.insert(basepath+"saveAdImage", param);
		}
		/**
		 * 查询序列
		 * 
		 * @param m
		 */
		public int selectSeq() {
			int i = Integer.parseInt(((Map<String,Object>)Dao.selectOne(basepath+"selectSeq")).get("SEQ").toString());
			return i;
		}
		
		public Map<String,Object> selectAdById(Map<String,Object> param){
			return Dao.selectOne(basepath+"selectAdById", param);
		}
		
		public List<Map<String,Object>> selectImageByPro_Id(Map<String,Object> param){
			return Dao.selectList(basepath+"selectImageByPro_Id", param);
		}

		public Map<String, Object> selectImageById(Map<String, Object> param) {
			// TODO Auto-generated method stub
			return Dao.selectOne(basepath+"selectImageById", param);
		}
		
		public int updateAdById(Map<String, Object> param) {
			// TODO Auto-generated method stub
			return Dao.update(basepath+"updateAdById", param);
		}
		
		public int delImageById(Map<String, Object> param) {
			// TODO Auto-generated method stub
			return Dao.delete(basepath+"delImageById", param);
		}
		
		public int delAdById(Map<String, Object> param) {
			// TODO Auto-generated method stub
			return Dao.delete(basepath+"delAdById", param);
		}
		public int delImageByProId(Map<String, Object> param) {
			// TODO Auto-generated method stub
			return Dao.delete(basepath+"delImageByProId", param);
		}
		
		
}
