/**
 * TODO 
 * @author LUYANFENG @ 2015年7月10日
 */
package com.pqsoft.weixinfw.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;

import com.pqsoft.skyeye.Log;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.MyNumberTool;
import com.pqsoft.weixinfw.dao.AccountDao;
import com.pqsoft.weixinfw.dao.BusinessDao;
import com.pqsoft.weixinfw.utils.IMethodReturn;
import com.pqsoft.weixinfw.utils.weixin.oauth2.OAuth2;

/**
 * TODO 
 * @author LUYANFENG @ 2015年7月10日
 *
 */
public class BizAction extends AbstractWeiXinResponseAction{

	private BusinessDao bizDao = new BusinessDao();
	private AccountDao accDao = new AccountDao();
	private MyNumberTool myNumberTool = new MyNumberTool();
//	private AuthService authService = new AuthService();
	/**
	 * 查询客户项目
	 * @return
	 * @author LUYANFENG @ 2015年7月10日
	 * weixinfw/Biz!myCreditList.action?oid=ozHVcuDLT6LYTVd18PiXJtALbs9U&k=N
	 */
	@aAuth(type=aAuthType.ALL)
	@aDev(code="170025", email="luyf@pqsoft.cn" , name="luyanfeng")
	public Reply myCreditList(){
		Map<String, Object> _getParameters = _getParameters();
		VelocityContext vc = new VelocityContext();
		vc.put("params", _getParameters);
		vc.put("title", "我的借贷");
		vc.put("actionName", "myCreditList");
		String openid = (String) _getParameters.get("oid");
		String code = (String) _getParameters.get("code");
		if(StringUtils.isBlank(openid)){
			openid = this.getOpenID(code);
			_getParameters.put("openid", openid);
		}
		String keyword = (String) _getParameters.get("k");
		List<Map<String, Object>> myCreditList = this.bizDao.getMyCreditList(openid,keyword);
		vc.put("results", myCreditList);
		vc.put("MyNumberTool", myNumberTool);
		return new ReplyHtml(VM.html("page/biz/myCreditList.vm", vc));
	}

	/**
	 * 历史借贷
	 * @return
	 * @author LUYANFENG @ 2015年8月6日
	 */
	@aAuth(type=aAuthType.ALL)
	@aDev(code="170025", email="luyf@pqsoft.cn" , name="luyanfeng")
	public Reply myHistoryCreditList(){
		VelocityContext vc = new VelocityContext();
		Map<String, Object> _getParameters = _getParameters();
		vc.put("params", _getParameters);
		vc.put("title", "历史借贷");
		vc.put("actionName", "myHistoryCreditList");
		String keyword = (String) _getParameters.get("k");
		String openid =  (String) _getParameters.get("oid");
		String code =  (String) _getParameters.get("code");
		if(StringUtils.isBlank(openid)){
			openid = this.getOpenID(code);
			_getParameters.put("openid", openid);
		}
		List<Map<String, Object>> myCreditList = this.bizDao.myHistoryCreditList(openid,keyword);
		vc.put("results", myCreditList);
		vc.put("MyNumberTool", myNumberTool);
		return new ReplyHtml(VM.html("page/biz/myCreditList.vm", vc));
	}
	
	/**
	 * 我的进行中的项目
	 * @return
	 * @author LUYANFENG @ 2015年8月6日
	 */
	@aAuth(type=aAuthType.ALL)
	@aDev(code="170025", email="luyf@pqsoft.cn" , name="luyanfeng")
	public Reply myPaying(){
		VelocityContext vc = new VelocityContext();
		Map<String, Object> _getParameters = _getParameters();
		
		String keyword = (String) _getParameters.get("k");
		String openid =  (String) _getParameters.get("oid");
		String code =  (String) _getParameters.get("code");
		if(StringUtils.isBlank(openid)){
			openid = this.getOpenID(code);
			_getParameters.put("openid", openid);
		}
		List<Map<String, Object>> myCreditList = this.bizDao.myPaying(openid,keyword);
		vc.put("results", myCreditList);
		vc.put("params", _getParameters);
		
		vc.put("title", "进行中的项目");
		vc.put("actionName", "myPaying");
		vc.put("MyNumberTool", myNumberTool);
		return new ReplyHtml(VM.html("page/biz/myCreditList.vm", vc));
	}
	
	/**
	 * 获取项目明细
	 * @return
	 * @author LUYANFENG @ 2015年7月22日
	 */
	@aAuth(type=aAuthType.ALL)
	@aDev(code="170025", email="luyf@pqsoft.cn" , name="luyanfeng")
	public Reply projectDetail(){
		VelocityContext vc = new VelocityContext();
		Map<String, Object> _getParameters = _getParameters();
		vc.put("params", _getParameters);
		String _id = (String) _getParameters.get("_id");
		String openid =  (String) _getParameters.get("oid");
		String code =  (String) _getParameters.get("code");
		if(StringUtils.isBlank(openid)){
			openid = this.getOpenID(code);
			_getParameters.put("openid", openid);
		}
		if(StringUtils.isNotBlank(_id)){
			Map<String,Object>  result = this.bizDao.getMyCreditDetail(openid, _id);
			vc.put("result", result);
		}
		vc.put("MyNumberTool", myNumberTool);
		return new ReplyHtml(VM.html("page/biz/projectDetail.vm", vc)); 
	}
	
	/**
	 * 我的 还款情况
	 * @author LUYANFENG @ 2015年8月6日
	 */
	@aAuth(type=aAuthType.ALL)
	@aDev(code="170025", email="luyf@pqsoft.cn" , name="luyanfeng")
	public Reply myPaymentInfo(){
		VelocityContext vc = new VelocityContext();
		Map<String, Object> _getParameters = _getParameters();
		String code =  (String) _getParameters.get("code");
		String openid = this.getOpenID(code);
		_getParameters.put("openid", openid);
		//TODO 
		vc.put("MyNumberTool", myNumberTool);
		return new ReplyHtml(VM.html("page/biz/myPaymentInfo.vm", vc)); 
	}
	
	/**
	 * <pre>
	 * 我的对账单
	 * <pre>
	 * @author LUYANFENG @ 2015年8月12日
	 */
	@aAuth(type=aAuthType.ALL)
	@aDev(code="170025", email="luyf@pqsoft.cn" , name="luyanfeng")
	public Reply myBills(){
		VelocityContext vc = new VelocityContext();
		Map<String, Object> _getParameters = _getParameters();
		String openid =  (String) _getParameters.get("oid");
		String code =  (String) _getParameters.get("code");
		if(StringUtils.isBlank(openid)){
			openid = this.getOpenID(code);
			_getParameters.put("openid", openid);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("openid", openid);
		Page myBills = this.bizDao.getMyBills(map);
		vc.put("page", myBills);
		vc.put("MyNumberTool", myNumberTool);
		return new ReplyHtml(VM.html("page/biz/myBills.vm", vc)); 
	}
	
	/**
	 * <pre>
	 * 对账单明细
	 * <pre>
	 * @author LUYANFENG @ 2015年8月12日
	 */
	@aAuth(type=aAuthType.ALL)
	@aDev(code="170025", email="luyf@pqsoft.cn" , name="luyanfeng")
	public Reply myBillDetail(){
		VelocityContext vc = new VelocityContext();
		Map<String, Object> _getParameters = _getParameters();
		String fund_id =  (String) _getParameters.get("fund_id");
		String code =  (String) _getParameters.get("code");
		String openid =  (String) _getParameters.get("oid");
		if(StringUtils.isBlank(openid)){
			openid = this.getOpenID(code);
			_getParameters.put("openid", openid);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("openid", openid);
		map.put("fund_id", fund_id);
		List<Map<String, Object>> results = this.bizDao.getMyBillDetail(map);
		vc.put("results", results);
		vc.put("MyNumberTool", myNumberTool);
		return new ReplyHtml(VM.html("page/biz/myBillDetail.vm", vc)); 
	}
	
	
	
	
	//------------------------------------------------------------------------------------
	
	/**
	 * 
	 * 通过OAuth2 获取openid
	 * @param params 页面传来的参数map
	 * @author LUYANFENG @ 2015年8月7日
	 */
	private String getOpenID( String oauth2Code ) {
		String openid = "";
		try {
			Log.debug("oauth2Code = " + oauth2Code);
			OAuth2 oAuth2 = new OAuth2();
			IMethodReturn methodReturn = oAuth2.getAccessToken(oauth2Code);
			Map<String, Object> msg = methodReturn.getMsg();
			
			Log.debug("msg : "+msg);
			Map<String,Object>  tokenMap = (Map<String, Object>) msg.get("data");
			openid = tokenMap.get("openid")+"";
			IMethodReturn hasBind = this.accDao.hasBind( openid);
			if(!hasBind.isOK()){
				throw new ActionException(hasBind.pollErrorMsg());
			}
		} catch (Exception e) {
			throw new ActionException("啊呀，小强跑哪去了.....");
		}
		return openid;
	}
	
}
