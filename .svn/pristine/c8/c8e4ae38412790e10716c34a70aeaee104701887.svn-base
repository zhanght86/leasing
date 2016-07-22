//package com.pqsoft.taskDictionary.action;
//
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//
//import org.apache.log4j.Logger;
//import org.apache.velocity.VelocityContext;
//
//import com.pqsoft.taskDictionary.service.TaskDictionaryService;
//import com.pqsoft.skyeye.VM;
//import com.pqsoft.skyeye.api.Action;
//import com.pqsoft.skyeye.api.Page;
//import com.pqsoft.skyeye.api.Reply;
//import com.pqsoft.skyeye.api.ReplyAjax;
//import com.pqsoft.skyeye.api.ReplyAjaxPage;
//import com.pqsoft.skyeye.api.ReplyHtml;
//import com.pqsoft.skyeye.api.SkyEye;
//import com.pqsoft.skyeye.dev.aDev;
//import com.pqsoft.skyeye.rbac.OpLog;
//import com.pqsoft.skyeye.rbac.api.Security;
//import com.pqsoft.skyeye.rbac.api.aAuth;
//import com.pqsoft.skyeye.rbac.api.aPermission;
//import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
//
//public class TaskDictionaryAction extends Action {
//	@Override
//	public Reply execute() {
//		return null;
//	}
//
//	Map<String, Object> m = null;
//	private static Logger logger = Logger.getLogger(TaskDictionaryAction.class);
//	private static TaskDictionaryService taskDictionaryService = new TaskDictionaryService();
//	public VelocityContext context = new VelocityContext();
//
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	@aAuth(type = aAuthType.USER)
//	private void getPageParameter() {
//		m = new HashMap<String, Object>();
//		Enumeration<?> en = SkyEye.getRequest().getParameterNames();
//		while (en.hasMoreElements()) {
//			Object enN = en.nextElement();
//			String para = SkyEye.getRequest().getParameter(enN.toString()).trim();
//			m.put(enN.toString(), para);
//		}
//		m.put("USERNAME", Security.getUser().getName());
//		m.put("USERCODE", Security.getUser().getCode());
//	}
//
//	@aPermission(name = { "流程定义","流程节点人员配置", "列表" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	@aAuth(type = aAuthType.USER)
//	public Reply getTaskDicList() {
//		this.getPageParameter();
//
//		Map<String, Object> param = _getParameters();
//		context.put("oldTaskList", taskDictionaryService.getOldTaskId());
//		context.put("taskList", taskDictionaryService.getTaskList(param));
//		return new ReplyHtml(VM.html("taskDictionary/taskDictionaryManger.vm", context));
//	}
//
//	@aPermission(name = { "流程定义","流程节点人员配置", "数据" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	@aAuth(type = aAuthType.USER)
//	public Reply pageAjax() {
//		Map<String, Object> param = _getParameters();
//		Page page = taskDictionaryService.queryPage(param);
//		return new ReplyAjaxPage(page);
//	}
//
//	@aPermission(name = { "流程定义","流程节点人员配置", "添加页面" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	@aAuth(type = aAuthType.USER)
//	public Reply toAddTaskDic() {
//		VelocityContext context = new VelocityContext();
//		Map<String, Object> param = _getParameters();
//		context.put("userList", taskDictionaryService.getUserListByType(param));
//		context.put("taskList", taskDictionaryService.getTaskList(param));
//		context.put("SupplierList", taskDictionaryService.getSupplierList(param));
//		context.put("param", param);
//		return new ReplyHtml(VM.html("taskDictionary/taskDictionaryAdd.vm", context));
//	}
//
//	@aPermission(name = { "流程定义","流程节点人员配置", "修改页面" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	@aAuth(type = aAuthType.USER)
//	public Reply toUpdateTaskDic() {
//		VelocityContext context = new VelocityContext();
//		Map<String, Object> param = _getParameters();
//		long l1 = System.currentTimeMillis();
//		context.put("userList", taskDictionaryService.getUserListByType(param));
//		context.put("taskList", taskDictionaryService.getTaskList(param));
//		context.put("SupplierList", taskDictionaryService.getSupplierList(param));
//		context.put("param", param);
//		long l2 = System.currentTimeMillis();
//		System.out.println(l2 - l1);
//		return new ReplyHtml(VM.html("taskDictionary/taskDictionaryUpdate.vm", context));
//	}
//
//	@aPermission(name = { "流程定义","流程节点人员配置", "保存" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	@aAuth(type = aAuthType.USER)
//	public Reply doAddTaskDic() {
//		String msg = "";
//		this.getPageParameter();
//
//		//判断流程节点是否选择
//		if (m.get("NODE_NAMES") != null || m.get("NODE_NAMES") != "") {
//			String NODE_NAMES = m.get("NODE_NAMES").toString();
//			String[] noteArr = NODE_NAMES.split(",");
//			//遍历流程节点新增数据
//			for (int i = 0; i < noteArr.length; i++) {
//				if (noteArr[i] != null && !"".equals(noteArr[i].trim())) {
//					if (m.get("SUPPLIER_IDS") != null || m.get("SUPPLIER_IDS") != "") {
//						String SUPPLIER_IDS = m.get("SUPPLIER_IDS").toString();
//						String[] supArr = SUPPLIER_IDS.split(",");
//						//去除重复供应商
//						supArr = this.array_unique(supArr);
//						for (int j = 0; j < supArr.length; j++) {
//							if (supArr[j] != null && !"".equals(supArr[j].trim())) {
//								m.put("NODE_NAME", noteArr[i].trim());
//								m.put("SUPPLIER_ID", supArr[j].trim());
//								//判断供应商名称是否存在
//								if(!taskDictionaryService.checkSupName(m)){
//									continue;
//								}
//								String name = taskDictionaryService.checkNoteAndSup(m);
//								// 校验流程、节点、供应商 是否已经存在
//								System.out.println("name" + name);
//								if (name == null || "".equals(name)) {
//									taskDictionaryService.insertTaskDic(m);
//								} else {
//									String supName = taskDictionaryService.querySuppByCode(supArr[j]);
//									msg +=supArr[j] + " 已经配置给 " + name + ".   ";
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//
//		boolean flag = false;
//		if (msg.length() == 0) {
//			flag = true;
//		} else {
//			msg += "其余配置成功!";
//		}
//		return new ReplyAjax(flag, msg).addOp(new OpLog( "流程定义","流程节点人员配置", "保存" ));
//	}
//	
//	@aPermission(name = { "流程定义","流程节点人员配置", "去除重复" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	@aAuth(type = aAuthType.LOGIN)
//    public String[] array_unique(String[] a) {  
//        // array_unique  
//        List<String> list = new LinkedList<String>();  
//        String val = "";
//        for(int i = 0; i < a.length; i++) { 
//        	val = a[i].trim();
//            if(!list.contains(val)) {  
//                list.add(val);
//            }  
//        }  
//        return (String[])list.toArray(new String[list.size()]);  
//    }  
//    
//	@aPermission(name = { "流程定义","流程节点人员配置", "修改" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	@aAuth(type = aAuthType.USER)
//	public Reply doUpdateTaskDic() {
//		this.getPageParameter();
//		String msg = "";
//
//		if (m.get("NODE_NAMES") != null || m.get("NODE_NAMES") != "") {
//			String NODE_NAMES = m.get("NODE_NAMES").toString();
//			String[] noteArr = NODE_NAMES.split(",");
//			for (int i = 0; i < noteArr.length; i++) {
//				if (noteArr[i] != null && !"".equals(noteArr[i])) {
//					if (m.get("SUPPLIER_IDS") != null || m.get("SUPPLIER_IDS") != "") {
//						String SUPPLIER_IDS = m.get("SUPPLIER_IDS").toString();
//						String[] supArr = SUPPLIER_IDS.split("[，,]");
//						//去除重复供应商
//						supArr = this.array_unique(supArr);
//						m.put("NODE_NAME", noteArr[i]);
//						taskDictionaryService.deleteDictionary(m);
//						for (int j = 0; j < supArr.length; j++) {
//							if (supArr[j] != null && !"".equals(supArr[j])) {
//								m.put("SUPPLIER_ID", supArr[j]);
//								//判断供应商名称是否存在
//								if(!taskDictionaryService.checkSupName(m)){
//									continue;
//								}
//								String name = taskDictionaryService.checkNoteAndSup(m);
//								// 校验流程、节点、供应商 是否已经存在
//								System.out.println("name" + name);
//								if (name == null || "".equals(name)) {
//									taskDictionaryService.insertTaskDic(m);
//								} else {
//									String supName = taskDictionaryService.querySuppByCode(supArr[j]);
//									msg +=supArr[j] + " 已经配置给 " + name + ".   ";
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//
//		boolean flag = false;
//		if (msg.length() == 0) {
//			flag = true;
//		} else {
//			msg += "其余配置成功!";
//		}
//		return new ReplyAjax(flag, msg);
//	}
//
//	/**
//	 * 根据ID或USER_ID删除一条或多条数据(更新状态为-2)
//	 * 
//	 * @Function:
//	 *            com.pqsoft.taskDictionary.service.TaskDictionaryService.
//	 *            deleteDictionary
//	 * @param ID
//	 *            配置信息ID USER_ID 用户ID
//	 * @author: 吴剑东
//	 * @date: 2013-9-13 下午04:19:30
//	 */
//	@aPermission(name = { "流程定义","流程节点人员配置", "删除" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	@aAuth(type = aAuthType.USER)
//	public Reply doDelTaskDic() {
//		this.getPageParameter();
//		int count = (Integer) taskDictionaryService.deleteDictionary(m);
//		boolean flag = false;
//		if (count > 0) {
//			flag = true;
//		}
//		return new ReplyAjax(flag, null);
//	}
//
//	@aPermission(name = { "流程定义","流程节点人员配置", "流程变更" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	@aAuth(type = aAuthType.USER)
//	public Reply doUpdateTaskID() {
//		this.getPageParameter();
//		int count = (Integer) taskDictionaryService.doUpdateTaskID(m);
//		boolean flag = false;
//		if (count > 0) {
//			flag = true;
//		}
//		return new ReplyAjax(flag, null);
//	}
//
//	@aPermission(name = { "流程定义","流程节点人员配置", "明细列表" })
//	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
//	@aAuth(type = aAuthType.LOGIN)
//	public Reply doSelectTaskDic() {
//		Map<String, Object> param = _getParameters();
//		Page page = taskDictionaryService.getTaskDicInfo(param);
//		return new ReplyAjaxPage(page);
//	}
//}
