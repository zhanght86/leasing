/**
 * TODO 
 * @author LUYANFENG @ 2015年7月10日
 */
package test.comm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.junit.Test;

import com.pqsoft.skyeye.Log;
import com.pqsoft.weixinfw.service.MenuService;
import com.pqsoft.weixinfw.utils.weixin.HTTPUtils;

/**
 * TODO 
 * @author LUYANFENG @ 2015年7月10日
 *
 */
public class TestJSON {
	
	@Test
	public void test1(){
		Map<String,Object> baseMenu = new HashMap<String,Object>();
		List<Map<String,?>> menuLMap = new ArrayList<Map<String,?>>(); 
		menuLMap.add(this.leftMenu());
		menuLMap.add(this.middleMenu());
		menuLMap.add(this.rightMenu());
		baseMenu.put("button", menuLMap);
		
		String menuStr = JSONArray.fromObject(baseMenu).toString(1);
		Log.debug("################# 生成菜单响应：\n"+menuStr);
	}
	
	private  Map<String,?> leftMenu(){
		Map<String,Object> left_ = new HashMap<String,Object>();
		List<Map<String,?>> sub2 = new ArrayList<Map<String,?>>();
		left_.put("name", "我的借贷");
		left_.put("sub_button", sub2);
		
		{
			Map<String,Object> sub2_1 = new HashMap<String,Object>();
			sub2_1.put("type", "click");
			sub2_1.put("name", "还款情况");
			sub2_1.put("key", "left_sub2_1");
			sub2.add(sub2_1);
		}
		{
			Map<String,Object> sub2_1 = new HashMap<String,Object>();
			sub2_1.put("type", "click");
			sub2_1.put("name", "进行中的");
			sub2_1.put("key", "left_sub2_2");
			sub2.add(sub2_1);
		}
		{
			Map<String,Object> sub2_1 = new HashMap<String,Object>();
			sub2_1.put("type", "click");
			sub2_1.put("name", "历史借贷");
			sub2_1.put("key", "left_sub2_3");
			sub2.add(sub2_1);
		}
		
		
		return left_;
		
	}
	/**
	 * <pre>
	 * 中间菜单
	 * </pre>
	 * @return
	 */
	private Map<String,?> middleMenu(){
		Map<String,Object > middle_ = new HashMap<String,Object>();
		List<Map<String,?>> sub2 = new ArrayList<Map<String,?>>();
		middle_.put("name", "资源库");
		middle_.put("sub_button", sub2);
		{
			Map<String,Object> sub2_1 = new HashMap<String,Object>();
			sub2_1.put("type", "click");
			sub2_1.put("name", "导出表单域无数据");
			sub2_1.put("key", "middle_sub2_1");
			sub2.add(sub2_1);
		}
		
		{
			Map<String,Object> sub2_2 = new HashMap<String,Object>();
			sub2_2.put("type", "click");
			sub2_2.put("name", "罚息金额如何收取");
			sub2_2.put("key", "middle_sub2_2");
			sub2.add(sub2_2);
		}
		
		{
			Map<String,Object> sub2_3 = new HashMap<String,Object>();
			sub2_3.put("type", "click");
			sub2_3.put("name", "支付表修改/变更");
			sub2_3.put("key", "middle_sub2_3");
			sub2.add(sub2_3);
		}
		
		{
			Map<String,Object> sub2_4 = new HashMap<String,Object>();
			sub2_4.put("type", "click");
			sub2_4.put("name", "如何果看评审进度");
			sub2_4.put("key", "middle_sub2_4");
			sub2.add(sub2_4);
		}
		
		{
			Map<String,Object> sub2_5 = new HashMap<String,Object>();
			sub2_5.put("type", "click");
			sub2_5.put("name", "如何将任务移交");
			sub2_5.put("key", "middle_sub2_5");
			sub2.add(sub2_5);
		}
		
		return middle_;
		
	}
	/**
	 * <pre>
	 * 右侧菜单
	 * </pre>
	 * @return
	 */
	private Map<String,?> rightMenu(){
		Map<String,Object > right_ = new HashMap<String,Object>();
		List<Map<String,?>> sub2 = new ArrayList<Map<String,?>>();
		right_.put("name", "走进平强");
		right_.put("sub_button", sub2);
		{
			Map<String,Object> sub2_1 = new HashMap<String,Object>();
			sub2_1.put("type", "click");
			sub2_1.put("name", "企业简介");
			sub2_1.put("key", "right_sub2_1");
			sub2.add(sub2_1);
		}
		{
			Map<String,Object> sub2_2 = new HashMap<String,Object>();
			sub2_2.put("type", "click");
			sub2_2.put("name", "平强软件");
			sub2_2.put("key", "right_sub2_2");
			sub2.add(sub2_2);
		}
		
		return right_;
		
	}

}
