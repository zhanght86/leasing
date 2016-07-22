package com.pqsoft.zcfl.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.pqsoft.skyeye.api.Dao;


/**
 * 打分操作
 * 
 * @author @author <a href="mailto:lichaohn@163.com">lichao</a>
 * 
 */
public class ScoreService {

	private final String mapper = "zcfl.score.";

	/**
	 * 根据选项ID获取分值
	 * 
	 * @author <a href="mailto:lichaohn@163.com">lichao</a> 2012-6-5
	 */
	public int execute(String itemId) {
		return Dao.selectInt(mapper + "getScore", itemId);
	}

	/**
	 * 根据资产ID 和 题目ID，判定分值（用于系统打分功能）
	 * 
	 * @author <a href="mailto:lichaohn@163.com">lichao</a> 2012-6-5
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int execSystem(String id, String subjectId) throws Exception {
		Map<String, Object> subject = (Map<String, Object>) Dao.selectOne(mapper + "getSubject", subjectId);

		if (subject == null)
			throw new Exception("未找到对应打分项目数据提取模版");
		String sql = (String) subject.get("SQL");
		if (StringUtils.isEmpty(sql))
			throw new Exception("未找到相关数据提取模版");
		if(sql.contains("#{id}") ){
			sql = sql.replace("#{id}", id); 
		}
		Map<String, Object> sqlMap = new HashMap<String, Object>();
		sqlMap.put("SQL", sql);
		String value = Dao.selectOne(mapper + "execSql", sqlMap);
		if (StringUtils.isEmpty(value)){
			throw new Exception("无法获取对应结果");
		}
		if ("section".equalsIgnoreCase((String) subject.get("MODER"))){ // 数字区间匹配模式
			return section(subjectId, value);
		}
		else if ("pattern".equalsIgnoreCase((String) subject.get("MODER"))) {// 正则匹配模式
			return pattern(subjectId, value);
		}
		else
			throw new Exception("无法匹配打分类型");
	}

	/**
	 * 数字区间匹配 min,100000 100000,200000 200000,max
	 * 
	 * @author <a href="mailto:lichaohn@163.com">lichao</a> 2012-6-5
	 */
	@SuppressWarnings("unchecked")
	private int section(String subjectId, String value) {
		List<?> list = Dao.selectList(mapper + "getItem", subjectId);
		BigDecimal amount = new BigDecimal(value);
		for (Object object : list) {// 循环选项
			Map<String, Object> m = (Map<String, Object>) object;
			String formula = (String) m.get("FORMULA");// 获取表达式
			if (StringUtils.isNotEmpty(formula)) {
				String[] conditions = formula.split("[,;，；]");
				BigDecimal min = "min".equals(conditions[0].trim().toLowerCase()) ? null : new BigDecimal(conditions[0].trim());
				BigDecimal max = "max".equals(conditions[1].trim().toLowerCase()) ? null : new BigDecimal(conditions[1].trim());
				if (compare(min, max, amount)) {// 比较数字
					return Integer.parseInt(m.get("SCORE") == null ? "0" : m.get("SCORE").toString());
				}
			}
		}
		return 0;
	}

	/**
	 * 比较数字
	 * 
	 * @author <a href="mailto:lichaohn@163.com">lichao</a> 2012-6-5
	 */
	private boolean compare(BigDecimal min, BigDecimal max, BigDecimal amount) {
		if (min != null && amount.compareTo(min) < 0) {
			return false;
		}
		if (max != null && amount.compareTo(max) > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 正则表达式方式匹配
	 * 
	 * @author <a href="mailto:lichaohn@163.com">lichao</a> 2012-6-5
	 */
	@SuppressWarnings("unchecked")
	private int pattern(String subjectId, String value) {
		List<?> list = Dao.selectList(mapper + "getItem", subjectId);
		for (Object object : list) {// 循环选项
			Map<String, Object> m = (Map<String, Object>) object;
			String formula = (String) m.get("FORMULA");// 获取表达式
			if (StringUtils.isNotEmpty(formula)) {
				if (Pattern.matches(formula, value)) {// 比较数字
					return Integer.parseInt(m.get("SCORE") == null ? "0" : m.get("SCORE").toString());
				}
			}
		}
		return 0;
	}
	
}
