/**
 * TODO 
 * @author LUYANFENG @ 2015年6月8日
 */
package com.pqsoft.weixinfw.utils.weixin;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 强大的语句分析器，目前还在建设中 
 * @author LUYANFENG @ 2015年6月8日
 *
 */
public final class SentenceAnalyze {
	
	private SentenceAnalyze() {}
	/**
	 * 可能的指令集
	 */
	private static final Map<String,Opt_Finally_Type> opt_prefix_mapper = new HashMap<String,Opt_Finally_Type>();
	static {
		opt_prefix_mapper.put("grep", Opt_Finally_Type.R);
		opt_prefix_mapper.put("find", Opt_Finally_Type.R);
		opt_prefix_mapper.put("query", Opt_Finally_Type.R);
		opt_prefix_mapper.put("get", Opt_Finally_Type.R);
		opt_prefix_mapper.put("cx", Opt_Finally_Type.R);
		
		opt_prefix_mapper.put("add", Opt_Finally_Type.C);
		opt_prefix_mapper.put("insert", Opt_Finally_Type.C);
		opt_prefix_mapper.put("save", Opt_Finally_Type.C);
		
		opt_prefix_mapper.put("up", Opt_Finally_Type.U);
		opt_prefix_mapper.put("update", Opt_Finally_Type.U);
		
		opt_prefix_mapper.put("d", Opt_Finally_Type.D);
		opt_prefix_mapper.put("del", Opt_Finally_Type.D);
		opt_prefix_mapper.put("delete", Opt_Finally_Type.D);
		opt_prefix_mapper.put("remove", Opt_Finally_Type.D);
		opt_prefix_mapper.put("rm", Opt_Finally_Type.D);
		
		opt_prefix_mapper.put("bind", Opt_Finally_Type.B);
		opt_prefix_mapper.put("bd", Opt_Finally_Type.B);
		
		opt_prefix_mapper.put("rebind", Opt_Finally_Type.RB);
		opt_prefix_mapper.put("rebd", Opt_Finally_Type.RB);
		
		opt_prefix_mapper.put("yzm", Opt_Finally_Type.VC);
		opt_prefix_mapper.put("vc", Opt_Finally_Type.VC);
	}
	
	/**
	 * 指令集 对应的操作
	 * @author LUYANFENG @ 2015年7月7日
	 *
	 */
	public enum Opt_Finally_Type{
		/**
		 * 新建
		 */
		C("Create"),
		/**
		 * 查询
		 */
		R("Retrieve"),
		/**
		 * 更新
		 */
		U("Update"),
		/**
		 * 删除
		 */
		D("Delete") ,
		/**
		 * 验证码
		 */
		VC("Verify Code"),
		/**
		 * 无指令
		 */
		N("Null") , 
		/**
		 * 绑定
		 */
		B("Bind"),
		/**
		 * 重新绑定
		 */
		RB("ResetBind");
		
		private String opt = "";
		private Opt_Finally_Type(String opt) {
			this.opt = opt;
		}

		public String toString(int flag) {
			return this.opt.toString();
		}
		
	}
	/**
	 * <pre>
	 * 语句 与 系统 操作 关系映射 ： Opt_prefix to CRUD
	 * 解析出操作类型
	 * </pre> 
	 * @author LUYANFENG @ 2015年7月6日
	 *
	 */
	public static Opt_Finally_Type crud(String opt_prefix ){
		Opt_Finally_Type opt_Finally_Type = opt_prefix_mapper.get(opt_prefix);
		if(opt_Finally_Type == null){
			return Opt_Finally_Type.N;
		}
		return opt_Finally_Type;
	}
	
	/**
	 * 语句分析器
	 * @param optVal 操作关键词 ，本类找
	 * @param keyword 用户符加语句
	 * @param sentence  == optVal + keyword
	 * @author : LUYANFENG @ 2015年6月8日
	 */
	public static Map<String,Object> sentenceAnalyze(String sentence){
		Map<String,Object>  rmap = new HashMap<String,Object>();
		rmap.put("optVal", "");
		rmap.put("keyword", sentence);
		Pattern p = Pattern.compile("^(\\w+)([\\s+|:].*$)", Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(sentence);
		if(matcher.matches()){
			String optVal = matcher.group(1).trim().toLowerCase();
			String keyword = matcher.group(2).trim();
			rmap.put("optVal", optVal);
			rmap.put("keyword", keyword);
		}
		return rmap;
	}

}
