package com.pqsoft.log;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONBuilder;

import org.apache.tools.ant.util.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.JsonUtil;
import com.pqsoft.util.StringUtils;
import com.sun.istack.logging.Logger;

public class LogUtil {

	/**
	 * 在删除操作中插入操作记录
	 * 
	 * @param tableName
	 *            表名
	 * @param opBefore
	 *            操作前MAP
	 * @param opBehind
	 *            操作后MAP
	 * @param otherParam
	 *            存放其他所需参数
	 *@param extendList
	 *            存放扩展的 中英文对照
	 *            Map<String,String> m 存放的统一为    KEY 表字段 VALUE 表字段名称 KEY_F 格式化表字段   
	 */
	public static void insertLogForDelete(String fileName,
			Map<String, Object> opBefore, Map<String, Object> opBehind,
			Map<String, Object> otherParam,List<Map<String,String>> extendList) {
		if (!(opBehind != null && opBehind.size() > 0)) {

			// 存放中英文对照
			List<Map<String, String>> ognl = parseConfig(fileName);

			Map<String, Object> m1 = new HashMap<String, Object>();
			// 存放操作记录中操作前
			Map<String, Object> m1_f = new HashMap<String, Object>();

			for (Map<String, String> s : ognl) {

				String key = s.get("KEY");
				String key_f = s.get("KEY_F");
				String value = s.get("VALUE");
				Object v = opBefore.get(key);
				if (v != null && StringUtils.isNotBlank(v)) {
					m1.put(key, opBefore.get(key));
					if(StringUtils.isNotBlank(key_f)&&StringUtils.isNotNull(key)){
						Object bf = opBefore.get(key_f) ==null?"":opBefore.get(key_f);		
						m1_f.put(value, bf);
					}
				
				}
			}

			Map<String, Object> r = new HashMap<String, Object>();
			// 操作人
			String opName = Security.getUser().getName();

			// 操作人CODE
			String opCode = Security.getUser().getCode();

			// 操作人ID
			String opId = Security.getUser().getId();

			// 操作IP
			String opIp = Security.getUser().getIp();
			// 操作前
			String opBeforeO =m1.size()==0?"": JSONObject.fromObject(m1).toString();
			String opBeforeO_f =m1_f.size()==0?"": JSONObject.fromObject(m1_f).toString();

			r.put("OP_CODE", opCode);
			r.put("OP_NAME", opName);
			r.put("OP_ID", opId);
			r.put("OP_IP", opIp);
			r.put("OP_BEFORE_DATA", opBeforeO);
			r.put("OP_BEFORE_DATA_F", opBeforeO_f);
			r.put("OP_FLAG", 2);
			if (otherParam != null && otherParam.size() > 0)
				r.putAll(otherParam);
			Dao.insert("t_sys_op.insertLog", r);
		}

	}

	/**
	 * 在更新操作中插入日志
	 * 
	 * @param tableName
	 *            表名
	 * @param opBefore
	 *            操作前MAP
	 * @param opBehind
	 *            操作后MAP
	 * @param otherParam
	 *            存放其他所需参数
	 * @param extendList
	 *            存放扩展的 中英文对照
	 *            Map<String,String> m 存放的统一为    KEY 表字段 VALUE 表字段名称 KEY_F 格式化表字段         
	 */
	public static void insertLogForUpdate(String fileName,
			Map<String, Object> opBefore, Map<String, Object> opBehind,
			Map<String, Object> otherParam,List<Map<String,String>> extendList,String PROJECT_ID) {

		// 存放中英文对照
		List<Map<String, String>> ognl = parseConfig(fileName);
		
		if(ognl==null)
			return  ;
		if(extendList!=null && extendList.size()>0)
			ognl.addAll(extendList);
		// 存放操作记录中操作前
		Map<String, Object> m1 = new HashMap<String, Object>();//修改

		// 存放操作记录中操作后
		Map<String, Object> m2 = new HashMap<String, Object>();

		// 存放操作记录中操作前
		Map<String, Object> m1_f = new HashMap<String, Object>();//修改

		// 存放操作记录中操作后
		Map<String, Object> m2_f = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer() ;
		sb.append("<table class='table'><tr><td >操作前</td><td>操作后</td></tr>") ;
		System.out.println("opBefore......"+opBefore);
		System.out.println("opBehind......"+opBehind);
		for (Map<String, String> s : ognl) {
		
			String key = s.get("KEY");
			String key_f = s.get("KEY_F");
			String value = s.get("VALUE");
			
			Object o1 = opBefore.get(key) == null ? "" : opBefore.get(key).toString();
			Object o2 = opBehind.get(key) == null ? "" : opBehind.get(key).toString();
			if (o2 instanceof String) {
				String o1f = "";
				if (o1 instanceof String) {
					o1f = (String) o1;
				} else if (o1 instanceof Date) {
					o1f = new DateTime(((Date) o1).getTime())
							.toString("yyyy-MM-dd");
				}
				System.out.println("key_f......"+key_f);
				System.out.println("value......"+value);
				if (!o2.equals(o1f)) {
					m1.put(key, o1);
					m2.put(key, o2);
					if(StringUtils.isNotBlank(key_f)&&StringUtils.isNotNull(key)){
						Object bf = opBefore.get(key_f) ==null?"":opBefore.get(key_f);
						Object bh = opBehind.get(key_f) ==null ?"":opBehind.get(key_f) ;
						System.out.println("bf......"+bf);
						System.out.println("bh......"+bh);
						sb.append("<tr><td>"+value+": "+ bf+"</td><td>"+ value +": "+ bh+"</tr>") ;
						m1_f.put(value, bf);
						m2_f.put(value, bh);
					}
					
				}
			} else {
				if (o2 != o1) {
					m1.put(key, o1);//修改
					m2.put(key, o2);
					if(StringUtils.isNotBlank(key_f)&&StringUtils.isNotNull(key)){
						Object bf = opBefore.get(key_f) ==null?"":opBefore.get(key_f);
						Object bh = opBehind.get(key_f) ==null ?"":opBehind.get(key_f) ;
						String bfhtml = StringUtils.isBlank(bf)?"空值":bf.toString() ;
						String bhhtml = StringUtils.isBlank(bf)?"空值":bh.toString() ;
						sb.append("<tr><td>"+value+": "+ bf+"</td><td>"+ value +": "+ bh+"</tr>") ;
						
						m1_f.put(value, bf);//修改
						m2_f.put(value, bh);
					}
				}
			}
			
		}
		sb.append("</table>") ;
		System.out.println("sb......"+sb);
		if (m1 != null && m1.size() > 0) {//修改
			Map<String, Object> r = new HashMap<String, Object>();
			// 操作人
			String opName = Security.getUser().getName();

			// 操作人CODE
			String opCode = Security.getUser().getCode();

			// 操作人ID
			String opId = Security.getUser().getId();

			// 操作IP
			String opIp = Security.getUser().getIp();
			// 操作前
			String opBeforeO = m1.size()==0?null:JSONObject.fromObject(m1).toString();//修改

			// 操作后
			String opBehindO =  m2.size()==0?null:JSONObject.fromObject(m2).toString();

			// 操作前
			String opBeforeO_f =m1_f.size()==0?null: JSONObject.fromObject(m1_f).toString();//修改

			// 操作后
			String opBehindO_f =m2_f.size()==0?null: JSONObject.fromObject(m2_f).toString();

			r.put("OP_CODE", opCode);
			r.put("OP_NAME", opName);
			r.put("OP_ID", opId);
			r.put("OP_IP", opIp);
			r.put("OP_BEFORE_DATA", opBeforeO);//修改
			r.put("OP_BEHIND_DATA", opBehindO);
			r.put("OP_BEFORE_DATA_F", opBeforeO_f);//修改
			r.put("OP_BEHIND_DATA_F", opBehindO_f);
			r.put("OP_SHOW", sb.toString()) ;
			r.put("OP_FLAG", 1);
			r.put("PROJECT_ID", PROJECT_ID);
			if (otherParam != null && otherParam.size() > 0)
				r.putAll(otherParam);
			Dao.insert("t_sys_op.insertLog", r);
		}//修改

	}

	private static List<Map<String, String>> parseConfig(String fileName) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		SAXReader reader = new SAXReader();
		try {
			
			ClassPathResource cc = new ClassPathResource("file/"+fileName) ; 
			System.out.println(cc.exists());
			Document document = reader.read(cc.getURL())  ;
					
			//reader.read(LogUtil.class.getResourceAsStream(fileName));
			
			
			Element root = document.getRootElement();
			Iterator iter = root.elementIterator("map");
			while (iter.hasNext()) {
				
				Element recordEle = (Element) iter.next();
				// 拿到head节点下的子节点title值
				String key = recordEle.elementTextTrim("key");

				String key_f = recordEle.elementTextTrim("key_f");
				String key_f_ignore = recordEle.element("key_f").attributeValue(
						"ignore");
				String key_ignore = recordEle.element("key").attributeValue(
						"ignore");
				String value = recordEle.elementTextTrim("value");
				if(!(key_ignore!=null&&key_ignore.equals("true"))){
					
					Map<String, String> m = new HashMap<String, String>();
					System.out.println("key:" + key + " key_f:" + key_f
							+ " ignore:" + key_f_ignore + " value:" + value);
					m.put("KEY", key);
					if (StringUtils.isBlank(key_f_ignore) || key_f_ignore.equals("false")) {
						m.put("KEY_F", key_f);
					}
					m.put("VALUE", value);
					result.add(m);
				}
				
			}
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Map<String, String>>();
	}

	
	//为生成必填项维护解析配置文件
	public static List<Map<String, String>> parseConfig2(String fileName) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		SAXReader reader = new SAXReader();
		try {

			Document document = reader.read(LogUtil.class
					.getResourceAsStream(fileName));
			Element root = document.getRootElement();
			Iterator iter = root.elementIterator("map");
			while (iter.hasNext()) {			
				Element recordEle = (Element) iter.next();
				// 拿到head节点下的子节点title值
				String key = recordEle.elementTextTrim("key");
				String value = recordEle.elementTextTrim("value");
				Map<String, String> m = new HashMap<String, String>();
				m.put("KEY", key);
				m.put("VALUE", value);
				result.add(m);
			}
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<Map<String, String>>();
	}
	
	public static void main(String[] args) {

		System.out.println( new ClassPathResource("file/fil_cust_client_np.xml") .exists());
		
		SAXReader reader = new SAXReader();
		try {
			String p = new ClassPathResource("logConfig/fil_cust_client.xml")
					.getPath();
			System.out.println(p);
			String p2 = LogUtil.class.getResource("fil_cust_client.xml")
					.getPath();
			System.out.println(p2);
			File f = new File(p2);
			System.out.println(f.exists());
			Document document = reader.read(f);
			Element root = document.getRootElement();
			System.out.println(root.getName());
			Iterator iter = root.elementIterator("map");

			while (iter.hasNext()) {
				Element recordEle = (Element) iter.next();
				// 拿到head节点下的子节点title值
				String key = recordEle.elementTextTrim("key");
				String value = recordEle.elementTextTrim("value");
				System.out.println("key:" + key + " value:" + value);
			}

		} catch (DocumentException e) {
			System.out.println(1);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("name", "王三");
		m1.put("age", 20);

		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("name", "李四");
		m2.put("age", 25);

		Iterator<String> iter_m2 = m1.keySet().iterator();
		Map<String, Object> m3 = new HashMap<String, Object>();
		while (iter_m2.hasNext()) {
			String key = iter_m2.next();
			Object o1 = m1.get(key);
			Object o2 = m2.get(key);
			if (o2 instanceof String) {
				if (!o2.equals(o1)) {
					String s = o1 + "修改为" + o2;
					m3.put(key, s);
				}
			} else {
				if (o2 != o1) {
					String s = o1 + "修改为" + o2;
					m3.put(key, s);
				}
			}
		}
		System.out.println(m3.toString());
	}
}
