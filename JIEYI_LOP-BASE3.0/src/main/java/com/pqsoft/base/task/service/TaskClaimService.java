package com.pqsoft.base.task.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.PageUtil;

import net.sf.json.JSONArray;
import payment.tools.util.StringUtil;

public class TaskClaimService {

	public Page getPageData(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList("Task.Claim.selectTaskClaim", param));
		page.addDate(array, Dao.selectInt("Task.Claim.selectTaskClaimCount", param));
		return page;
	}

	public Page getPageData1(Map<String, Object> param) {
		Page page = new Page(param);
		JSONArray array = JSONArray.fromObject(Dao.selectList("Task.Claim.selectTaskClaim1", param));
		page.addDate(array, Dao.selectInt("Task.Claim.selectTaskClaimCount1", param));
		return page;
	}

	public List<Map<String, Object>> getNoClaim(Map<String, Object> param) {
		return Dao.selectList("Task.Claim.getNoClaim", param);
	}

	public int getTaskNum(Map<String, Object> param) {
		return Dao.selectInt("Task.Claim.getTaskNum", param);
	}

	public List<Map<String, Object>> getNoClaimAll(Map<String, Object> param) {
		Map m=Dao.selectOne("Task.Claim.getalltaskname",param);
		param.put("TASK_NAME", m.get("TASK_NAME"));
		return Dao.selectList("Task.Claim.getNoClaimAll", param);
	}

	public Map<String, Object> getTask(String USERID) {
		Map<String, Object> m = Dao.selectOne("Task.Claim.getTask", USERID);
		if (m == null) {
			m = new HashMap<String, Object>();
			m.put("TASK_NAME", "");
		}
		m.put("CLAIM_ID", USERID);
		return m;
	}

	public Map<String, Object> getTaskOther(String USERID, String ORGID) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("USERID", USERID);
		m.put("ORGID", ORGID);
		m = Dao.selectOne("Task.Claim.getTaskOther", m);
		if (m == null) {
			m = new HashMap<String, Object>();
			m.put("TASK_NAME", "");
		}
		m.put("CLAIM_ID", USERID);
		m.put("ORGID", ORGID);
		return m;
	}

	public int Claim(Map<String, Object> param) {
		return Dao.update("Task.Claim.Claim", param);
	}

	public Page getUsers(Map<String,Object> param){
		return PageUtil.getPage(param, "Task.Claim.getUsers", "Task.Claim.getUsersCount");
	}
	public void updFunction(Map<String, Object> param) {
		Map<String, Object> m = Dao.selectOne("Task.Claim.getFunction", param);
		if (m.get("TASK_NAME").toString().equals("电话催收")) {
			Dao.update("Task.Claim.updPhone", m);
		} else if (m.get("TASK_NAME").toString().equals("函件催收")) {
			Dao.update("Task.Claim.updLetters", m);
		} else if (m.get("TASK_NAME").toString().equals("法务催收")) {
			Dao.update("Task.Claim.Forensic", m);
		} else if (m.get("TASK_NAME").toString().equals("强制催收")) {
			Dao.update("Task.Claim.Compulsory", m);
		} else if ("1".equals(m.get("TYPE") + "")) {
			Dao.update("Task.Claim.updBpm", m);
		}
	}

	// TYPE:流程1数据2; TASK_NAME:任务名称;DATA_ID:数据id;DATA_NAME:描述;
	public void addClaim(Map<String, Object> param) {
		param.put("ID", Dao.getSequence("SEQ_T_SYS_TASK_CLAIM"));
		String DATA_NAME[]=param.get("DATA_NAME").toString().split(".");
		if(DATA_NAME.length>2)
		{
			param.put("DATA_NAME", DATA_NAME[0]+"."+DATA_NAME[1]);
		}
		List<Map<String, Object>> list=Dao.selectList("Task.Claim.getHistoryTask",param);
		if(list.size()>0)
		{
			Map<String, Object> HistoryTask=list.get(0);
			Map<String, Object> user_code=Dao.selectOne("Task.Claim.getJBPMUser1",HistoryTask);
			param.put("USER_CODE", user_code.get("CODE"));
			Dao.update("Task.Claim.updTaskCode",param);
		}
		if (param.containsKey("USER_CODE") && param.get("USER_CODE") != null && param.get("USER_CODE") != "") {
			Map<String, Object> CLAIM = Dao.selectOne("Task.Claim.getJBPMUser", param);
			param.put("CLAIM_ID", CLAIM.get("ID"));
			param.put("CLAIM_MAN", CLAIM.get("NAME"));
			param.put("STATE", 1);
			Dao.insert("Task.Claim.addClaim", param);
		} else {
			Map<String, Object> m = Dao.selectOne("Task.Claim.getClaim", param);
			/* start 2016年4月11日 14:46:50    吴国伟修改*/
			Map<String, Object> configure = null;
			if(null !=m){
				configure = Dao.selectOne("Task.Claim.getConfigure", m);
			}
			/* end */
			if(param.get("TASK_NAME").equals("最终审核")){
				m.put("USER_CODE", Security.getUser().getCode());
			}
			if(configure == null) 
				{
				param.put("STATE", 0);
				Dao.insert("Task.Claim.addClaim", param);
				return ;
				}else if(m == null) 
				{
				param.put("STATE", 0);
				Dao.insert("Task.Claim.addClaim", param);
				return ;
				}
			Map<String, Object> personnelMap = new HashMap<String, Object>();
			int WEIGHT = 0;
			int num = 0;
			
			// modified by xingzhili 2016.6.22 修改任务分配规则 start
            if (configure.get("ZDFP").toString().equals("0")) {
                List<Map<String, Object>> personnel = Dao.selectList("Task.Claim.getPersonnel", m);
                // 计算总任务数和总权重
                for (int i = 0; i < personnel.size(); i++) {
                    personnelMap = personnel.get(i);
                    num = num + Dao.selectInt("Task.Claim.getNum", personnelMap);
                    WEIGHT = WEIGHT + Integer.parseInt(personnelMap.get("WEIGHT").toString());
                }

                // 存放所有人的“任务比”和“权重比”之差
                List<Map<String, String>> tmpList = new ArrayList<Map<String, String>>();
                Map<String, String> tmpMap = null;
                for (int i = 0; i < personnel.size(); i++) {
                    personnelMap = personnel.get(i);
                    float a = Float.parseFloat(personnelMap.get("WEIGHT").toString()) / WEIGHT;
                    int tmp1 = Dao.selectInt("Task.Claim.getNum", personnelMap);
                    float b = Float.parseFloat(tmp1+"") / num;
                    tmpMap = new HashMap<String, String>(3);
                    tmpMap.put("ratioLog", "b-a=("+ personnelMap.get("WEIGHT").toString()+"/"+WEIGHT+")"+"-("+tmp1+"/"+num+")="+b+"-"+a+"="+(b - a));
                    tmpMap.put("ratio", (b - a)+"");
                    tmpMap.put("uid", personnelMap.get("ID").toString());
                    tmpMap.put("uname", personnelMap.get("NAME").toString());
                    tmpList.add(tmpMap);
                }

                // 获取差值最小的任务人，如果多人的差值都一样，则去最后一个人
                Map<String, String> target = new HashMap<String, String>();
                // 初始化默认值1000
                target.put("ratio", "1000");
                for (Map<String, String> it : tmpList) {
                    if (Float.valueOf(it.get("ratio")) <= Float.valueOf(target.get("ratio"))) {
                        target = it;
                    }
                }
                
                System.out.println("aliTest===task==allocation===" + tmpList.toString() + ",target===" + target.toString());
                
                if (target != null && !StringUtil.isEmpty(target.get("uid"))) {
                    param.put("CLAIM_ID", target.get("uid"));
                    param.put("CLAIM_MAN", target.get("uname"));
                    param.put("STATE", 1);
                    Dao.insert("Task.Claim.addClaim", param);
                    Dao.update("Task.Claim.Claim", param);
                    updFunction(param);
                }
            } else {
                param.put("STATE", 0);
                Dao.insert("Task.Claim.addClaim", param);
            }
            // modified by xingzhili 2016.6.22 修改任务分配规则 end
			
			/*if (configure.get("ZDFP").toString().equals("0")) {
				List<Map<String, Object>> personnel = Dao.selectList("Task.Claim.getPersonnel", m);
				for (int i = 0; i < personnel.size(); i++) {
					personnelMap = personnel.get(i);
					num = num + Dao.selectInt("Task.Claim.getNum", personnelMap);
					WEIGHT = WEIGHT + Integer.parseInt(personnelMap.get("WEIGHT").toString());
				}
				for (int i = 0; i < personnel.size(); i++) {
					personnelMap = personnel.get(i);
					float a = (float) Integer.parseInt(personnelMap.get("WEIGHT").toString()) / WEIGHT;
					float b = (float) Dao.selectInt("Task.Claim.getNum", personnelMap) / num;
					System.out.println(num);
					if (num == 0) {
						param.put("CLAIM_ID", personnelMap.get("ID"));
						param.put("CLAIM_MAN", personnelMap.get("NAME"));
						param.put("STATE", 1);
						Dao.insert("Task.Claim.addClaim", param);
						Dao.update("Task.Claim.Claim", param);
						updFunction(param);
						break;
					} else {
						if (b <= a) {
							param.put("CLAIM_ID", personnelMap.get("ID"));
							param.put("CLAIM_MAN", personnelMap.get("NAME"));
							param.put("STATE", 1);
							Dao.insert("Task.Claim.addClaim", param);
							Dao.update("Task.Claim.Claim", param);
							updFunction(param);
							break;
						}
					}
				}
			} else {
				param.put("STATE", 0);
				Dao.insert("Task.Claim.addClaim", param);
			}*/
		}
	}

	public void updClaimProcedure(String TASKID, String TYPE) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("TASKID", TASKID);
		param.put("TYPE", TYPE);
		Dao.update("Task.Claim.updClaimProcedure", param);
	}
	
	public int updTaskMan(Map<String,Object> m){
		return Dao.update("Task.Claim.updTaskMan",m);
	}
	
	/**
	 * 补证信、家访、补充资料超时查询
	 * 
	 * @return
	 * @author: xingsumin
	 * @date: 2015年12月23日15:03:25
	 */
	public List<Map<String, Object>> getTimeOutTask() {
		return Dao.selectList("Task.Claim.getTimeOutTask");
	}
	
	/**
	 *申请复议节点超时查询
	 * 
	 * @return
	 * @author: xingsumin
	 * @date: 2015年12月23日17:54:47
	 */
	public List<Map<String, Object>> getReconsiderTimeOut() {
		return Dao.selectList("Task.Claim.getReconsiderTimeOut");
	}
	
	/**
	 *查询信审评论状态
	 * 
	 * @return
	 * @author: xingsumin
	 * @date: 2015年12月23日17:54:47
	 */
	public List<Map<String, Object>> getLetterOptionByProjectId(Map<String, Object> param) {
		return Dao.selectList("Task.Claim.getLetterOptionByProjectId",param);
	}
	
	/**
	 *获取客户信息
	 * 
	 * @return
	 * @author: xingsumin
	 * @date: 2015年12月23日17:54:47
	 */
	public List<Map<String, Object>> getCustInfoByProjectId(Map<String, Object> param) {
		return Dao.selectList("Task.Claim.getCustInfoByProjectId",param);
	}
	
	/**
	 *获取projectId
	 * 
	 * @return
	 * @author: xingsumin
	 * @date: 2015年12月23日17:54:47
	 */
	public Map<String, Object> getProjectIdBytTaskId(Map<String, Object> param) {
		return Dao.selectOne("Task.Claim.getProjectIdBytTaskId",param);
	}
	
}
