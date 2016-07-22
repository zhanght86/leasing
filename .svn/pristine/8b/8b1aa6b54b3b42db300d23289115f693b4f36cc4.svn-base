package com.pqsoft.base.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pqsoft.base.area.service.AreaService;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.util.StringUtils;

public class ProductService {
	private String basePath = "Product.";
	
	public List<Object> getProBig(Map<String,Object> param){
		return Dao.selectList(basePath+"getAllProduct",param);
	}
	
	public List<Object> getComPro(Map<String, Object> param){
		return Dao.selectList(basePath+"getCompanyProduct", param);
	}
	
	public Map<String,Object> getOneProBig(Map<String,Object> param){
		return Dao.selectOne(basePath+"getOneProduct",param);
	}
	
	public int addProBig(Map<String,Object> param){
		return Dao.insert(basePath+"addProductBig",param);
	}
	
	public int updateProBig(Map<String,Object> param){
		return Dao.update(basePath+"updateProductBig", param);
	}
	
	public List<Object> getProCatenaByProId(Map<String,Object> param){
		return Dao.selectList(basePath+"getProCatenaByProId", param);
	}
	
	public Map<String,Object> getOneProCatena(Map<String,Object> param){
		return Dao.selectOne(basePath+"getOneProCatena", param);
	}
	
	public int addProCatena(Map<String,Object> param ){
		return Dao.insert(basePath+"addProCatena",param);
	}
	
	public int updateProCatena(Map<String,Object> param) {
		return Dao.update(basePath+"updateProCatena", param);
	}
	
	public List<Object> getProTypeList(Map<String,Object> param){
		return Dao.selectList(basePath+"getTypeList", param);
	}
	
	public Map<String,Object> getOneProType(Map<String,Object> param){
		return Dao.selectOne(basePath+"getOneProType", param);
	}
	
	public int addProType (Map<String,Object> param){
		return Dao.insert(basePath+"addProType", param);
	}
	
	public int updateProType(Map<String,Object> param){
		return Dao.update(basePath+"updateProType", param);
	}

	public int delProType(Map<String,Object> param){
		return Dao.delete(basePath+"delProType", param);
	}
	
	public int delProCatena(Map<String,Object> param){
		return Dao.delete(basePath+"delProCatena", param);
	}
	
	public int delProduct(Map<String,Object> param){
		return Dao.delete(basePath+"delProduct", param);
	}
	
//	public int getProCatena(Map<String,Object> param){
//		return Dao.selectInt(basePath+"getProCatena", param);
//	}
	
	public int getCatenaType(Map<String,Object> param){
		return Dao.selectInt(basePath+"getCatenaType", param);
	}
	
	public List<Object> getTypeParams(Map<String, Object> param){
		return Dao.selectList(basePath+"getTypeParams", param);
	}
	
	public int addTypeParams(Map<String, Object> param){
		return Dao.insert(basePath+"addTypeParam", param);
	}
	
	public int updateTypeParams(Map<String, Object> param){
		return Dao.update(basePath+"updateTypeParameter", param);
	}
	
	public int delTypeParams(Map<String, Object> param){
		return Dao.delete(basePath+"", param);
	}
	
	public List<Object> getDeprList(Map<String, Object> param){
		return Dao.selectList(basePath+"getTypeDepr" , param);
	}
	
	public int addTypeDepr(Map<String, Object> param){
		return Dao.insert(basePath+"addTypeDepr", param);
	}
	
	public int delTypeDepr(Map<String, Object> param){
		return Dao.delete(basePath+"delTypeDepr", param);
	}
	
	public List<Object> getTypeCityPrice(Map<String, Object> param){
		return Dao.selectList(basePath+"getTypeCityPrice", param);
	}
	
	public List<Object> getCompanyProductByFlag(Map<String, Object> param){
		return Dao.selectList(basePath+"getCompanyProductByFlag", param);
	}
	
	public List<Object> getTypeListByProId(Map<String, Object> param){
		return Dao.selectList(basePath+"getTypeListByProId", param);
	}
	/**
	 * 查询公司平台下的区域
	 * @param MANAGER_ID 公司平台
	 * @param parent_id  显示层级区域的父ID
	 * @param area_leve 父ID级别  国家1 省2 市3 县 4
	 * @return
	 * @author King 2015年3月6日
	 */
	public List<Object> queryManagerArea(String MANAGER_ID,String parent_id,int area_leve){
		List<Object> lst=new ArrayList<Object>();
		AreaService aservice=new AreaService();
		Map<String,Object> mm=new HashMap<String, Object>();
		if(StringUtils.isNotBlank(parent_id)){
			mm.put("PARENT_ID", parent_id);
			lst.addAll(aservice.getSubset(mm));
		}else{
			mm.put("ID", MANAGER_ID);
			List<Map<String,Object>>list = queryArea(mm);
			for (Map<String, Object> map : list) {
				String []AREAID=null;
				if(map.containsKey("AREAID")&&StringUtils.isNotBlank(map.get("AREAID"))){
					AREAID=map.get("AREAID").toString().split(",");
					
					if(AREAID.length>=area_leve){
						map.put("ID", AREAID[area_leve-1]);
//						map.put("PARENT_ID", AREAID[area_leve-1]);
						lst.add(aservice.selectArea(map));
//						lst.addAll(aservice.getSubset(map));
					}else{
						map.put("PARENT_ID", AREAID[area_leve-2]);
						lst.addAll(aservice.getSubset(map));
					}
				}
			}
		}
		return lst;
	}
	
	public List<Map<String,Object>> queryArea(Map m){
		return Dao.selectList(basePath+"queryArea", m);
	}
	
	//add gengchangbao JZZL-171 2016年4月27日 start
	/**
	 * 删除区域价格
	 * @param param
	 * @author 耿长宝
	 * @date 2016-4-12
	 */
	public boolean deleteCityPrice(Map<String,Object> param){
		return Dao.delete(basePath+"deleteCityPrice", param) >= 1;
	}
	/**
	 * 添加区域价格
	 * @param param
	 * @author 耿长宝
	 * @date 2016-4-12
	 */
	public boolean addCityPrice(Map<String, Object> param){
		return Dao.insert(basePath+"addCityPrice", param) > 0;
	}
	//add gengchangbao JZZL-171 2016年4月27日 end
}
