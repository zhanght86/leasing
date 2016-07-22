package com.pqsoft.util;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;

public class PageUtil {

	/**
	 * FIXME  BUG : 查询字段不能有clob类型的，不然jsonobject 会处理不了。<br />
	 * 列表页分页含带数据权限
	 * sql上配置这个条件
		<if test="SYS_ORGIDS_ !=null and SYS_ORGIDS_ !=''">AND FPH.ORD_ID IN ${SYS_ORGIDS_} </if>				
	 	<if test="SYS_CLERK_ID_ !=null and SYS_CLERK_ID_ !=''">AND FPH.CLERK_ID = #{SYS_CLERK_ID_} </if>
	 * @param map
	 * @param listMapper
	 * @param countMapper
	 * @return
	 * @author King 2014年8月21日
	 */
	public static Page getPage(Map<String, Object> map, String listMapper, String countMapper) {
		BaseUtil.getDataAllAuth(map);
		Page page = new Page(map);
		List<Object> selectList = Dao.selectList(listMapper.trim(), map);
		page.addDate(JSONArray.fromObject(selectList ), Dao.selectInt(countMapper.trim(), map));
		return page;
	}
	public static Page getPage(Map<String, Object> map, String listMapper, String countMapper,String footerMapper) {
		// 分公司授权
		BaseUtil.getDataAllAuth(map);
		Page page = new Page(map);
		page.addDate(JSONArray.fromObject(Dao.selectList(listMapper, map)), Dao.selectInt(countMapper, map),JSONArray.fromObject(Dao.selectList(footerMapper, map)));
		return page;
	}
	
	
	public static void clob2JSON(List<Map<String,Object>> result, String clobField ) {
		try {
			for(Map<String,Object> m_ : result){
				clob2JSON(m_, clobField);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void clob2JSON(Map<String,Object> dataMap, String clobField ) throws SQLException, IOException {
		if(dataMap.get(clobField) != null && dataMap.get(clobField) instanceof Clob){
			Clob context = (Clob) dataMap.get(clobField);
			Reader reader = context.getCharacterStream();
			char[] c = new char[(int) context.length()];
			reader.read(c);
			
			dataMap.put(clobField, JSONSerializer.toJSON(new String(c)));
			reader.close();
		}
	}


}
