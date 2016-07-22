package com.pqsoft.crm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.pqsoft.skyeye.api.Dao;

public class SubService {
	public static List<String> list = new ArrayList<String>();
	static {
		init();
	}

	public static void init() {
		list.clear();
		list.add("#8C0044");
		list.add("#BB5500");
		list.add("#008800");
		list.add("#668800");
		list.add("#008866");
		list.add("#888800");
		list.add("#A42D00");
		list.add("#227700");
		list.add("#886600");
		list.add("#003377");
		list.add("#880000");
		list.add("#008844");
		list.add("#007799");
		list.add("#008888");

	}

	/**
	 * 添加主体
	 * 
	 * @param name
	 * @param code
	 * @param mobile
	 * @param memo
	 * @throws Exception
	 */
	public static String add(String name, String code, String mobile, String memo) throws Exception {
		if (StringUtils.isEmpty(name)) throw new Exception();
		if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile)) throw new Exception();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("", Dao.getSequence(""));
		m.put("NAME", name);
		m.put("CODE", code);
		m.put("MOBILE", mobile);
		m.put("MEMO", memo);
		Dao.insert("crm.sub.addSub", m);
		return (String) m.get("ID");
	}

	/**
	 * 获取主体ID
	 * 
	 * @param name
	 * @param code
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public String id(String name, String code, String mobile) throws Exception {
		if (StringUtils.isEmpty(name)) throw new Exception();
		if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile)) throw new Exception();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("NAME", name);
		if (!StringUtils.isEmpty(code)) {
			m.put("CODE", code);
		} else {
			m.put("MOBILE", mobile);
		}
		String id = Dao.selectOne("crm.sub.getId", m);
		if (id == null) {
			id = add(name, code, mobile, null);
		}
		return id;
	}

	public void addSubRel(String srcId, String tarId, String type, String weight, String memo) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("SOURCE_ID", srcId);
		m.put("TARGET_ID", tarId);
		m.put("TYPE", type);
		m.put("MEMO", memo);
		m.put("WEIGHT", weight);
		Dao.insert("crm.sub.addSubRel", m);
	}

	public JSONObject data(String id) {
		init();
		Map<String, Object> data = Dao.selectOne("crm.sub.getSub", id);
		List<Map<String, Object>> rel = Dao.selectList("crm.sub.getSubRel", id);
		JSONObject json = new JSONObject();
		{
			JSONObject title = new JSONObject();
			title.put("text", "人物关系:" + data.get("NAME"));
			title.put("subtext", "数据来自TYLOO");
			json.put("title", title);
		}

		Map<String, Integer> ces = new HashMap<String, Integer>();
		{// categories

			JSONArray categories = new JSONArray();
			{
				JSONObject j = new JSONObject();
				j.put("COLOR", "#880000");
				j.put("NAME", "人物");
				categories.add(j);
			}
			int i = 1;
			for (Map<String, Object> m : rel) {
				if (ces.containsKey(m.get("TYPE") + "")) continue;
				JSONObject j = new JSONObject();
				j.put("NAME", m.get("TYPE") + "");
				j.put("COLOR", list.get(i));
				categories.add(j);
				ces.put(m.get("TYPE") + "", i);
				i++;
			}
			json.put("categories", categories);

		}
		Map<String, Integer> sid = new HashMap<String, Integer>();
		{
			JSONArray nodes = new JSONArray();
			{
				JSONObject j = new JSONObject();
				j.put("CATEGORY", 0);
				j.put("NAME", data.get("NAME"));
				j.put("ACTIVITY", data.get("ACTIVITY"));
				j.put("ID", data.get("ID") + "");
				nodes.add(j);
			}
			int i = 1;

			for (Map<String, Object> m : rel) {
				if (sid.containsKey(m.get("ID") + "")) continue;
				JSONObject j = new JSONObject();
				j.put("CATEGORY", ces.get(m.get("TYPE") + ""));
				j.put("NAME", m.get("NAME"));
				j.put("ACTIVITY", m.get("ACTIVITY"));
				j.put("ID", m.get("ID") + "");
				nodes.add(j);
				sid.put(m.get("ID") + "", i);
				i++;
			}
			json.put("nodes", nodes);
		}
		{
			JSONArray rels = new JSONArray();
			for (Map<String, Object> m : rel) {
				JSONObject j = new JSONObject();
				if (id.equals(m.get("SOURCE_ID") + "")) {
					j.put("SOURCE", 0);
				} else {
					j.put("SOURCE", sid.get(m.get("SOURCE_ID") + ""));
				}
				if (id.equals(m.get("TARGET_ID") + "")) {
					j.put("TARGET", 0);
				} else {
					j.put("TARGET", sid.get(m.get("TARGET_ID") + ""));
				}
				j.put("WEIGHT", m.get("WEIGHT"));
				j.put("MEMO", m.get("MEMO") + "");
				rels.add(j);
			}
			json.put("rels", rels);
		}

		// $!{item.SOURCE}, target : $!{item.TARGET}, weight : $!{item.WEIGHT}}
		return json;

		// title : {
		// text: '人物关系：${NAME}',
		// subtext: '数据来自TYLOO',
		// x:'right',
		// y:'bottom'
		// },
		// tooltip : {
		// trigger: 'item',
		// formatter: '{a} : {b}'
		// },
		// legend: {
		// x: 'left',
		// data:['家人','朋友']
		// },
		// series : [
		// {
		// type:'force',
		// name : "关系",
		// categories : [
		// {
		// name: '人物',
		// itemStyle: {
		// normal: {
		// color : '#ff7f50'
		// }
		// }
		// },
		// {
		// name: '家人',
		// itemStyle: {
		// normal: {
		// color : '#87cdfa'
		// }
		// }
		// },
		// {
		// name:'朋友',
		// itemStyle: {
		// normal: {
		// color : '#9acd32'
		// }
		// }
		// }
		// ],
		// itemStyle: {
		// normal: {
		// label: {
		// show: true,
		// textStyle: {
		// color: '#800080'
		// }
		// },
		// nodeStyle : {
		// brushType : 'both',
		// strokeColor : 'rgba(255,215,0,0.4)',
		// lineWidth : 8
		// }
		// },
		// emphasis: {
		// label: {
		// show: false
		// // textStyle: null // 默认使用全局文本样式，详见TEXTSTYLE
		// },
		// nodeStyle : {
		// r: 30
		// },
		// linkStyle : {}
		// }
		// },
		// minRadius : 15,
		// maxRadius : 25,
		// density : 0.05,
		// attractiveness: 1.2,
		// nodes:[
		// {category:0, name: '乔布斯', value : 10},
		// {category:1, name: '丽萨-乔布斯',value : 2},
		// {category:1, name: '保罗-乔布斯',value : 3},
		// {category:1, name: '克拉拉-乔布斯',value : 3},
		// {category:1, name: '劳伦-鲍威尔',value : 7},
		// {category:2, name: '史蒂夫-沃兹尼艾克',value : 5},
		// {category:2, name: '奥巴马',value : 8},
		// {category:2, name: '比尔-盖茨',value : 9},
		// {category:2, name: '乔纳森-艾夫',value : 4},
		// {category:2, name: '蒂姆-库克',value : 4},
		// {category:2, name: '龙-韦恩',value : 1},
		// ],
		// links : [
		// {source : 1, target : 0, weight : 1},
		// {source : 2, target : 0, weight : 2},
		// {source : 3, target : 0, weight : 1},
		// {source : 4, target : 0, weight : 2},
		// {source : 5, target : 0, weight : 3},
		// {source : 6, target : 0, weight : 6},
		// {source : 7, target : 0, weight : 6},
		// {source : 8, target : 0, weight : 1},
		// {source : 9, target : 0, weight : 1},
		// {source : 10, target : 0, weight : 1},
		// {source : 3, target : 2, weight : 1},
		// {source : 6, target : 2, weight : 1},
		// {source : 6, target : 3, weight : 1},
		// {source : 6, target : 4, weight : 1},
		// {source : 6, target : 5, weight : 1},
		// {source : 7, target : 6, weight : 6},
		// {source : 7, target : 3, weight : 1},
		// {source : 9, target : 6, weight : 1}
		// ]
		// }
		// ]
	}
}
