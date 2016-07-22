package com.pqsoft.holiday.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.velocity.VelocityContext;

import com.pqsoft.holiday.service.HolidayMainService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class HolidayMainAction extends Action{
	//页面路径
	private final String pagePath="holiday/";
	private final HolidayMainService service = new HolidayMainService();//定义service

	@Override
	public Reply execute() {
		return null;
	}
	
	/***********************************************************************************************************************************
	 **********                                           以下为节假日管理列表                             　　　　　　　　　　　　　　　                            **********
	 ****************                                                                                                   ****************
	 ***********************************************************************************************************************************/
	
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "参数配置", "节假日管理","列表显示"})
	public Reply toMgHoliday() {
		Map<String,Object> map = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", map);
		return new ReplyHtml(VM.html(pagePath+"toMgHoliday.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = { "参数配置", "节假日管理","列表"})
	public Reply toMgHolidayData() {
		//获取页面参数
		Map<String,Object> map = _getParameters();
		return new ReplyAjaxPage(service.toMgHoliday(map));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "参数配置", "节假日管理","添加"})
	public Reply toAddHoliday() {
		return new ReplyHtml(VM.html(pagePath+"task-add.vm", null));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = { "参数配置", "节假日管理","添加"})
	public Reply toSaveTaskByYear(){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String,Object> taskMap = new HashMap<String, Object>();//保存任务要的参数map
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		String year = SkyEye.getRequest().getParameter("saveByYear");
		String create_time = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		map.put("year", year);
		taskMap.put("CREATE_YEAR", year);
		taskMap.put("CREATER_NAME", Security.getUser().getName());//用户名称
		taskMap.put("CREATER_ID", Security.getUser().getId());//用户id
		taskMap.put("CREATE_TIME", create_time);
		list1 = service.queryYear(map);
		//System.out.println(list1.size()+"===================");
		int result = 0;
		if (list1.size()== 0) {
			int task_Id = service.insertTask(taskMap);//节假日表的外键（任务表Id）
			// 循环保存12个月
			for (int i = 1; i <= 12; i++) {
				map.put("month", "" + i);
				try {
					list = service.toGetDateByYear(map);
				} catch (Exception e) {
					e.printStackTrace();
				}// 根据年月查询日期和对应的星期
				for (int j = 1; j <= list.size(); j++) {// 循环保存某月的天数
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("year", year);
					m.put("month", "" + i);
					if (list.get(j - 1).get("wk") == "星期日"
							|| list.get(j - 1).get("wk") == "星期六") {
						m.put("d", "1," + j);
					} else {
						m.put("d", "0," + j);
					}
					m.put("TASK_ID", task_Id);
					try {
						result = service.insertHoliday(m);
					} catch (Exception e) {
						e.printStackTrace();
					}// 将日期插入到数据库
					//如果有一条数据插入错误，则数据全部回滚
					if(result<=0){
						logger.info("数据插入异常，数据回滚");
					}
					
				}
			}
		} else {
			logger.info("数据库已存在");
		}
		
		boolean flag = false;
		if (result != 0) {
			flag = true;
			logger.info("保存成功！");
		} else {
			flag = false;
			logger.info("保存失败！");
		}
		return new ReplyAjax(flag, null).addOp(new OpLog("节假日管理","添加节假日",create_time));
	}
	
	/**
	 * 进入查处节假日的页面
	 * toqueryHoliday
	 * @date 2013-10-18 下午06:06:39
	 * @author 杨雪
	 * @return 返回值类型
	 * @throws Exception
	 */
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "参数配置", "节假日管理", "查看备注"})
	public Reply toqueryHoliday(){
		VelocityContext context = new VelocityContext();
		String year = SkyEye.getRequest().getParameter("saveByYear");
		String statusPage = SkyEye.getRequest().getParameter("statusPage");
		context.put("year", year);
		context.put("statusPage", statusPage);
		System.out.println("=====year"+year+"=======statusPage");
		service.isHolidayByDate(SkyEye.getRequest().getParameter("time"));
		return new ReplyHtml(VM.html("holiday/holiday-query.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "参数配置", "节假日管理", "查看备注"})
	public Reply toqueryHoliday2() {
		Map<String, Object> map = new HashMap<String, Object>();
		VelocityContext context = new VelocityContext();
		String year = SkyEye.getRequest().getParameter("year");
		String month = SkyEye.getRequest().getParameter("month");
		String yearMonth = year+"-"+month;
		HttpSession session = SkyEye.getRequest().getSession();
		session.setAttribute("yearMonth", yearMonth);
		// 用来查询标号FDATE
		map.put("COMMON_DATE", yearMonth);
		// 在页面回显标号yearMonth
		map.put("year", year);
		map.put("month", month);
		map.put("IS_HOLIDAY", 1);
		List<Object> list = new ArrayList<Object>();
		if (year != null && !"".equals(year) && month != null && !"".equals(month)) {
			// 查询节假日
			try {
				list = service.queryHoliday(map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			context.put("holidayList", list);
		}

		return new ReplyHtml(VM.html(pagePath+"queryholidayMenu.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "参数配置", "节假日管理", "查看备注"})
	public Reply toqueryHoliday3(){
		Map<String, Object> map = new HashMap<String, Object>();
		VelocityContext context = new VelocityContext();
		String year = SkyEye.getRequest().getParameter("year");
		String month = SkyEye.getRequest().getParameter("month");
		String yearMonth = year+"-"+month;
		HttpSession session = SkyEye.getRequest().getSession();
		session.setAttribute("yearMonth", yearMonth);
		// 用来查询标号FDATE
		map.put("COMMON_DATE", yearMonth);
		// 在页面回显标号yearMonth
		map.put("year", year);
		map.put("month", month);
		map.put("IS_HOLIDAY", 1);
		List<Object> list = new ArrayList<Object>();
		if (year != null && !"".equals(year) && month != null && !"".equals(month)) {
			// 查询节假日
			try {
				list = service.queryHoliday(map);
			} catch (Exception e) {
				e.printStackTrace();
			}
			context.put("holidayList", list);
		}
		return new ReplyHtml(VM.html(pagePath+"holiday-menuQuery.vm", context));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "参数配置", "节假日管理", "修改"})
	public Reply openBtnUpdate() {
		return new ReplyHtml(VM.html(pagePath+"queryholidayMenu-js.vm",null));
	}
	
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "参数配置", "节假日管理", "修改"})
	public Reply doSetHolidayByDate(){
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<?> en = SkyEye.getRequest().getParameterNames();
		while (en.hasMoreElements()) {
			Object enN = en.nextElement();
			map.put(enN.toString(), SkyEye.getRequest().getParameter(enN.toString()).trim());
		}
		int i = service.doSetHolidayByDate(map);
		boolean flag = false;
		if(i>0){
			flag = false;
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("节假日管理","修改","AJX控制"));
	}
	
	/***********************************************************************************************************************************
	 **********                                           以下为备注管理                          　　　　　　　　　　　　　　　                            **********
	 ****************                                                                                                   ****************
	 ***********************************************************************************************************************************/
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
	@aPermission(name = { "参数配置", "节假日管理", "添加备注"})
	public Reply doAddPbComment(){
		Map<String, Object> m = _getParameters();
		String COMMENT_TIME = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		m.put("COMMENT_TIME", COMMENT_TIME);
		m.put("COMMENTER_NAME", Security.getUser().getName());
		m.put("COMMENTER_ID", Security.getUser().getId());
		int i = service.doAddPbComment(m);
		boolean flag = false;
		if(i>0){
			flag = true;
		}
		return new ReplyAjax(flag,null).addOp(new OpLog("节假日管理","添加备注",COMMENT_TIME));
	}
	
	@SuppressWarnings("unchecked")
	@aAuth(type=aAuth.aAuthType.LOGIN)
	@aDev(code = "170039", email = "yangxue@pqsoft.cn", name = "杨雪")
//	@aPermission(name = { "参数配置", "节假日管理", "查看备注"})
	public Object queryPbComment(){
		 StringBuffer commentStr = new StringBuffer("");
		 List<Object> list = new ArrayList<Object>();
		 Map<String, Object> m = _getParameters();
		 list = service.queryPbComment(m);

		for(int i=0;i<list.size();i++){
				commentStr.append("备注人："+((Map<String,Object>)list.get(i)).get("COMMENTER_NAME"));
				commentStr.append("  时间："+((Map<String,Object>)list.get(i)).get("COMMENT_TIME"));
				commentStr.append("  内容："+((Map<String,Object>)list.get(i)).get("COMMENT_CONTENT")+"\n");
		}
		return new ReplyAjax(true, commentStr.toString(), null);
	 }
}
