package com.pqsoft.pictureSynchronous.service;

import java.util.List;
import java.util.Map;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.SkyEye;

public class PictureSynchronousService {
	
	String XmlPath = "picture.synchronous.";
	
	public List<Map<String,Object>> selectPictureRecord(Map<String,Object> param){
		return Dao.selectList(XmlPath + "selectPictureRecord", param);
	}
	
	public String getConfig(){
		return  SkyEye.getConfig("file.path.picture");
	}

}
