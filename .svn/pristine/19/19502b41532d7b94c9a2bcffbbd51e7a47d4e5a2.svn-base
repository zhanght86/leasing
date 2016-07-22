package com.pqsoft.zhongjin.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;

import payment.tools.util.GUID;

import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.util.FIinterface;
import com.pqsoft.zhongjin.service.ZhongjinLogService;

public class ZhongjinLogAction extends Action {

	private ZhongjinLogService service = new ZhongjinLogService();
	
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "放款管理", "中金放款日志", "列表" })
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("zhongjinLog/zhongjinLog.vm", context));
	}
	
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "放款管理", "中金放款日志", "列表" })
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply getZhongjinLogPage(){
		Map<String,Object> param = _getParameters();
		return new ReplyAjaxPage(service.getPage(param));
	}
	
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "放款管理", "中金扣款日志", "列表" })
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply daikouPage() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html("zhongjinLog/daikouLog.vm", context));
	}
	
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "放款管理", "中金扣款日志", "列表" })
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply daikouPageData(){
		Map<String,Object> param = _getParameters();
		return new ReplyAjaxPage(service.daikouPageData(param));
	}
	
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "放款管理", "中金放款日志", "重新发起" })
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply Restart(){
		Boolean flag=true;
		String msg="重新发起成功";
		Map<String,Object> m = _getParameters();
		List<Map<String, Object>> bankcode = new SysDictionaryMemcached().get("中金银行代码");
		Map<String, Object> m1=new HashMap<String, Object>();
		String batchNo = GUID.getTxNo();
		m.put("BATCH_NO", batchNo);
		m.put("USE_STATE", "-1");
		for(int j=0;j<bankcode.size();j++){
			m1=bankcode.get(j);
			//匹配银行ID
			if(m.get("BANK_NAME").toString().contains(m1.get("FLAG").toString())){
				m.put("BANK_CODE", m1.get("CODE").toString());
				break;
			}else{
				m.put("BANK_CODE", "没有匹配到银行ID");
			}
		}
		service.updateZhongjin(m);
//		List<Map<String, Object>> listReturn = service.selectZhongjinLogById(m);
		List<Map<String, Object>> listReturn = Dao.selectList("rentWriteNew.queryZhongjinBatchNo", m);
		Map<String,Object> mapTitle = Dao.selectOne("rentWriteNew.selectTitleByBatchNo", m);
		
		System.out.println(listReturn+"============"+mapTitle);
		new FIinterface().daifu(listReturn, mapTitle, "1");
		return new ReplyAjax(flag, msg);
	}
	
	@aAuth(type = aAuthType.USER)
	@aPermission(name = { "放款管理", "中金扣款日志", "提交数据" })
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply submitRow(){
		Map<String,Object> param = _getParameters();
		int i = service.daikouRestart(param);
		if(i>0){
			return new ReplyAjax(true, "成功");
		}
		return new ReplyAjax(false, "失败");
	}

}
