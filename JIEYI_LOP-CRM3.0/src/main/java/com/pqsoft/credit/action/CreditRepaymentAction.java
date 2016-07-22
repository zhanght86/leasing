package com.pqsoft.credit.action;

import java.io.File;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.velocity.VelocityContext;

import com.pqsoft.credit.service.CreditRepaymentService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.Action404Exception;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

/**
 * <p>
 * Title: 融资租赁信息系统平台 资信管理 还款管理
 * </p>
 * <p>
 * Description:还款管理
 * </p>
 * <p>
 * Company: 平强软件
 * </p>
 * @version 1.0
 */
public class CreditRepaymentAction extends Action {

	private static CreditRepaymentService service = new CreditRepaymentService();
	String path = "credit/repayment/";

//	@aPermission(name = { "资信平台管理", "还款情况","列表" })
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
	public Reply raymentPage() {
		Map<String, Object> param = _getParameters();
		VelocityContext context = new VelocityContext();
		context.put("param", param);
		System.out.println(param.toString());
		return new ReplyHtml(VM.html(path+"repaymentMg.vm", context));
	}

				/**        ---------------------------------------------银行征信---------------------          **/
					
					@aAuth(type = aAuth.aAuthType.LOGIN)
//					@aPermission(name = { "资信平台管理", "还款情况", "银行征信" ,"列表"})
					@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
					public Reply toMgPayment() {

			   			Map<String, Object> param = _getParameters();
						VelocityContext context = new VelocityContext();
						System.out.println(param);
						context.put("param", param);
						return new ReplyHtml(VM.html(path+"paymentMg.vm", context));
					}

					@aAuth(type = aAuth.aAuthType.LOGIN)
					@aPermission(name = { "资信平台管理", "还款情况", "银行征信","数据" })
					@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
					public Reply doSelectPayment() {
						Map<String, Object> param = _getParameters();
						System.out.println(param);
						Page page = service.getPaymentListPage(param);
						return new ReplyAjaxPage(page);
					}

					@aAuth(type = aAuth.aAuthType.LOGIN)
					@aPermission(name = { "资信平台管理", "还款情况", "银行征信","保存" })
					@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
					public Reply doSavePayment() {
						Map<String, Object> param = _getParameters();
						Boolean flag = true;
						String msg = "";
						System.out.println(param.toString());
						int result = 0;
						try {
							result = service.savePayment(param);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (result > 0) {
							flag = true;
						} else {
							flag = false;
						}
						return new ReplyAjax(flag, msg);
					}

					@aPermission(name = { "资信平台管理", "还款情况", "银行征信", "删除" })
					@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
					@aAuth(type = aAuthType.LOGIN)
					public Reply doDeletePayment() {
						try {
							Map<String, Object> param = _getParameters();
							int count = (Integer) service.doDeletePayment(param);
							boolean flag = false;
							if (count > 0) {
								flag = true;
							}
							return new ReplyAjax(flag, null);

						} catch (Exception e) {
							throw new Action404Exception(e);
						}
					}
					@aAuth(type = aAuth.aAuthType.LOGIN)
					@aPermission(name = { "资信平台管理", "还款情况", "银行征信", "修改" })
					@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
					public Reply doUpdatePayment() {
						Map<String, Object> param = _getParameters();
						Boolean flag = true;
						String msg = "";
						int result = 0;
						try {
							result = service.updatPayment(param);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (result > 0) {
							flag = true;
							msg = "修改成功";
						} else {
							flag = false;
							msg = "修改失败";
						}
						return new ReplyAjax(flag, msg);
					}

					@Override
					public Reply execute() {
						// TODO Auto-generated method stub
						return null;
					}
/**        ---------------------------------------------法院信息---------------------          **/
					
					@aAuth(type = aAuth.aAuthType.LOGIN)
					@aPermission(name = { "资信平台管理", "还款情况", "法院信息" ,"列表"})
					@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
					public Reply toMgCourt() {

			   			Map<String, Object> param = _getParameters();
						VelocityContext context = new VelocityContext();
						System.out.println(param);
						context.put("param", param);
						return new ReplyHtml(VM.html(path+"courtMg.vm", context));
					}

					@aAuth(type = aAuth.aAuthType.LOGIN)
					@aPermission(name = { "资信平台管理", "还款情况", "法院信息","数据" })
					@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
					public Reply doSelectCourt() {
						Map<String, Object> param = _getParameters();
						System.out.println(param);
						Page page = service.getCourtListPage(param);
						return new ReplyAjaxPage(page);
					}

					@aAuth(type = aAuth.aAuthType.LOGIN)
					@aPermission(name = { "资信平台管理", "还款情况", "法院信息","保存" })
					@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
					public Reply doSaveCourt() {
						Map<String, Object> param = _getParameters();
						Boolean flag = true;
						String msg = "";
						System.out.println(param.toString());
						int result = 0;
						try {
							result = service.saveCourt(param);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (result > 0) {
							flag = true;
						} else {
							flag = false;
						}
						return new ReplyAjax(flag, msg);
					}

					@aPermission(name = { "资信平台管理", "还款情况", "法院信息", "删除" })
					@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
					@aAuth(type = aAuthType.LOGIN)
					public Reply doDeleteCourt() {
						try {
							Map<String, Object> param = _getParameters();
							System.out.println(param.toString());
							int count = (Integer) service.doDeleteCourt(param);
							boolean flag = false;
							if (count > 0) {
								flag = true;
							}
							return new ReplyAjax(flag, null);

						} catch (Exception e) {
							throw new Action404Exception(e);
						}
					}
					@aAuth(type = aAuth.aAuthType.LOGIN)
					@aPermission(name = { "资信平台管理", "还款情况", "法院信息", "修改" })
					@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
					public Reply doUpdateCourt() {
						Map<String, Object> param = _getParameters();
						Boolean flag = true;
						String msg = "";
						int result = 0;
						try {
							result = service.updatCourt(param);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (result > 0) {
							flag = true;
							msg = "修改成功";
						} else {
							flag = false;
							msg = "修改失败";
						}
						return new ReplyAjax(flag, msg);
					}
/**        ---------------------------------------------逾期信息---------------------          **/
					
					@aAuth(type = aAuth.aAuthType.LOGIN)
					@aPermission(name = { "资信平台管理", "还款情况", "逾期信息" ,"列表"})
					@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
					public Reply toMgOverdue() {

			   			Map<String, Object> param = _getParameters();
						VelocityContext context = new VelocityContext();
						System.out.println(param);
						context.put("param", param);
						return new ReplyHtml(VM.html(path+"overdueMg.vm", context));
					}

					@aAuth(type = aAuth.aAuthType.LOGIN)
					@aPermission(name = { "资信平台管理", "还款情况", "逾期信息","数据" })
					@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
					public Reply doSelectOverdue() {
						Map<String, Object> param = _getParameters();
						System.out.println(param);
						Page page = service.getOverdueListPage(param);
						return new ReplyAjaxPage(page);
					}

/**        ---------------------------------------------调查问卷信息---------------------          **/
					
					@aAuth(type = aAuth.aAuthType.LOGIN)
					@aPermission(name = { "资信平台管理", "还款情况", "调查问卷" ,"列表"})
					@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
					public Reply toMgSurvey() {

			   			Map<String, Object> param = _getParameters();
						VelocityContext context = new VelocityContext();
						System.out.println(param);
						context.put("param", param);
						context.put("survey", service.getSurveyData(param));
						System.out.println("-----------------param="+param);
						if(param.get("tab").equals("view")){
							return new ReplyHtml(VM.html(path+"surveyMgView.vm", context));
						}
						return new ReplyHtml(VM.html(path+"surveyMg.vm", context));
					}
					@aAuth(type = aAuth.aAuthType.LOGIN)
					@aPermission(name = { "资信平台管理", "还款情况", "调查问卷","保存" })
					@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
					public Reply doSaveSurvey() {
						Map<String, Object> param = _getParameters();
						Boolean flag = true;
						String msg = "";
						JSONObject  m = JSONObject.fromObject(SkyEye.getRequest().getParameter("alldata"));
						System.out.println(param.toString());
						int result = 0;
						try {
							result =service.saveSurvey(m);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (result > 0) {
							flag = true;
						} else {
							flag = false;
						}
						return new ReplyAjax(flag, msg);
					}
					@aAuth(type = aAuth.aAuthType.LOGIN)
					@aPermission(name = { "资信平台管理", "还款情况", "调查问卷","修改" })
					@aDev(code = "wuguowei", email = "wuguowei_jing@163.com", name = "吴国伟")
					public Reply doUpdateSurvey() {
						Map<String, Object> param = _getParameters();
						Boolean flag = true;
						String msg = "";
						JSONObject  m = JSONObject.fromObject(SkyEye.getRequest().getParameter("alldata"));
						System.out.println(param.toString());
						int result = 0;
						try {
							result =service.updateSurvey(m);
						} catch (Exception e) {
							e.printStackTrace();
						}
						if (result > 0) {
							flag = true;
						} else {
							flag = false;
						}
						return new ReplyAjax(flag, msg);
					}
					@aAuth(type=aAuth.aAuthType.LOGIN)
					@aDev(code = "170063", email = "wuguowei_jing@163.com", name = "吴国伟")
					public Reply uploadRecord(){
						Map<String,Object> m = _getParameters();
						Map<String,Object> obj = (Map<String,Object>)service.uploadFileForOne(SkyEye.getRequest());
						JSONArray array = JSONArray.fromObject(obj);
						return new ReplyJson(array);
					}
					@aAuth(type=aAuth.aAuthType.LOGIN)
					@aDev(code = "170063", email = "wuguowei_jing@163.com", name = "吴国伟")
					public Reply downLoadRecordFile(){
						Map<String,Object> map = _getParameters();
						return new ReplyFile(new File(map.get("FILE_URL") + ""), map.get("FILE_NAME") + "");
					}					
					
					@aAuth(type = aAuth.aAuthType.LOGIN)
//					@aPermission(name = { "客户管理", "客户资料管理","信用档案-法院信息", "数据" })
					@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
					public Reply doSelectCircles() {
						Map<String, Object> param = _getParameters();
						Page page = service.getCirclesListPage(param);
						return new ReplyAjaxPage(page);
					}
					@aAuth(type = aAuth.aAuthType.LOGIN)
//					@aPermission(name = { "客户管理", "客户资料管理","信用档案-法院信息", "数据" })
					@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
					public Reply doSelectTax() {
						Map<String, Object> param = _getParameters();
						Page page = service.getTaxListPage(param);
						return new ReplyAjaxPage(page);
					}
}
