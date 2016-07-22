/**
 * TODO 
 * @author LUYANFENG @ 2015年7月7日
 */
package com.pqsoft.weixinfw.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.pqsoft.skyeye.Log;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.util.StringUtils;
import com.pqsoft.weixinfw.utils.DefaultMethodReturn;
import com.pqsoft.weixinfw.utils.IMethodReturn;
import com.pqsoft.weixinfw.utils.PQsoftStringUtil;
import com.pqsoft.weixinfw.utils.PQsoftStringUtil.Variable;
import com.pqsoft.weixinfw.utils.weixin.WeiXinMsgCryptUtils;

/**
 * TODO 
 * @author LUYANFENG @ 2015年7月7日
 *
 */
public class AccountDao {
	
//	private SmsService smsServ = new SmsService();
	private IMethodReturn iMethodReturn = new DefaultMethodReturn();
	/**
	 * @param openID
	 * @param unionid
	 * @param subscribe_time
	 * @return 系统用户map. 没找到反回空的map.
	 * @author : LUYANFENG @ 2015年6月15日
	 */
	public Map<String, Object> findWXUser(String openid, Object unionid, String subscribe_time) {
		//TODO 加个关联表？ 是自动新增用户还是手动关联一个已有的用户？
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("openid", openid);
		Map<String, Object> userMap = Dao.selectOne("fil_wx_account.findWXUser", paramMap );
		return userMap;
	}

	/**
	 * 
	 * <pre>
	 * @see findWXUser(String openid, Object unionid, String subscribe_time) 
	 * <pre>
	 * @param openid
	 * @author LUYANFENG @ 2015年9月2日
	 */
	public Map<String, Object> findWXUser(String openid){
		return this.findWXUser(openid, null, null);
	}
			

	/**
	 * 添加微信用户
	 * @param userInfoMap
	 * @return
	 */
	public boolean addWXUser(Map<String, Object> userInfoMap) {
		try {
			if(userInfoMap == null || userInfoMap.isEmpty()) return false ;
			int ok = Dao.insert("fil_wx_account.addWXUser", userInfoMap);
			return ok == 1;
		} catch (Exception e) {
			e.printStackTrace();
			Dao.rollback();
			return false;
		}
	}

	/**
	 * 绑定微信到客户
	 * @param openid
	 * @param client_no 自然人是生身证等号，企业是营业执照
	 * @author LUYANFENG @ 2015年7月7日
	 * @return  false : 可能用户不存在或出现多个
	 */
	public IMethodReturn bindAccount(String openid, String client_no) {
		long valid_time = 10 ;// 验证码有效分钟
		// 完成第一步提示
		String step1 = "已完成第一步：客户【%1$s】\n第二步：输入【yzm 手机验证码】 \n我们已将手机验证码下发至您的手机，请注意查收。";
		/*// 完成第二步提示
		String step2 = "";*/
		// 已绑定过了提示
		String rebind =  "该微信已绑定到【%1$s】上了，如需重新绑定请输入【rebind/rebd 身份证或营业执照】";
		
		// 没有找到客户微信账号提示
		String noClientWX = "没有找到该客户的微信账号";
		
		// 没有找到系统中对应的客户信息提示
		String noClient = "没有找到要绑定的账号";
		
		// 验证码有效时间提示
		String verify_tip = "绑定微信账号验证码：%1$s，有效期%2$s分钟";
		
		
		this.iMethodReturn.addErrorMsg("default", "未知错误");
		try {
			
			if(StringUtils.isBlank(openid) || StringUtils.isBlank(client_no)) return iMethodReturn.addErrorMsg("1","client_no 为空");
			//1 查看是否已有绑定文件，验证过没有
			Map<String,Object> user = Dao.selectOne("fil_wx_account_mapper.getMapperByOpenid", openid);
			if(user != null && !user.isEmpty()){
				String hasbind = user.get("HASBIND").toString();
				if( "0".equals(hasbind)){
					return iMethodReturn.addErrorMsg("unbind", String.format(step1, user.get("NAME")));
				}
				return iMethodReturn.clearError().addMsg("userMap",user ).addMsg("info", String.format(rebind, user.get("NAME")));
			}
			//2 查看微信用户信息是否在系统中存在
			Map<String, Object> findWXUser = this.findWXUser(openid, "", "");
			if(findWXUser == null || findWXUser.isEmpty()){
				return iMethodReturn.addErrorMsg("2", noClientWX);
			}
			
			//3 查看要绑定到的系统客户是否存在
			List<Map<String,Object>> clientLMap = Dao.selectList("fil_wx_account_mapper.findClientByNo", client_no);
			if(clientLMap == null || clientLMap.isEmpty()){
				return iMethodReturn.addErrorMsg("3", noClient);
			}
			if( clientLMap.size() == 1){
				user = clientLMap.get(0);
				int isNP = Integer.valueOf(user.get("ISNP").toString());
				String tel_phone = (String) user.get("TEL_PHONE");
				//send msg 
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("PHONE", tel_phone);
				//TODO 生成验证码
				String verify_code = PQsoftStringUtil.verify_code();
				param.put("CONTENT", String.format(verify_tip, verify_code, valid_time));
				// 把验证码放入session
				HttpSession session = SkyEye.getRequest().getSession();
				session.setAttribute("verify_code__", verify_code);
				session.setAttribute("valid_time__", valid_time);
				
				// test sms
//				Map<String, Object> send = this.smsServ.doManualSendMessage(param);
//				boolean sendOK = (Boolean) send.get("FLAG");
				boolean sendOK  = true;
				if(sendOK){

					Map<String,Object> datamap = new HashMap<String,Object>();
					datamap.put("openid", openid);
					datamap.put("client_id", user.get("ID").toString());
					datamap.put("client_type", isNP == 1? "NP" :"LP");
					datamap.put("bind_time", new Date());
					datamap.put("bind_appid", WeiXinMsgCryptUtils.getAppId());
					datamap.put("verify_code", verify_code);
					datamap.put("verify_time", valid_time);
					datamap.put("hasbind", "0");
					
					int ok = Dao.insert("fil_wx_account_mapper.addMapper", datamap);
					return ok == 1 ? iMethodReturn.clearError().addMsg("userMap", user).addMsg("info", String.format(step1, user.get("NAME"))) 
									: iMethodReturn.addErrorMsg("4", "账号绑定失败");
				}else{
					return iMethodReturn.addErrorMsg("5", "联系人手机验证码发送失败");
				}
				
			}else {
				return iMethodReturn.addErrorMsg("6", "无法绑定，存在多个相同号码的客户");
			}
		} catch ( Exception e) {
			e.printStackTrace();
			Dao.rollback();
			return iMethodReturn.addErrorMsg("server", e.getMessage());
		}
	}

	/**
	 * 重新绑定微信到客户
	 * @param openid
	 * @param client_no
	 * @return
	 * @author LUYANFENG @ 2015年7月7日
	 */
	public IMethodReturn rebindAccount(String openid, String client_no) {
		int del = Dao.delete("fil_wx_account_mapper.delMapperByOpenid", openid);
		IMethodReturn result = this.bindAccount(openid, client_no);
		return result;
	}

	/**
	 * 操作前要判断是否已绑定
	 * @param openid
	 * @return
	 * @author LUYANFENG @ 2015年7月7日
	 */
	public IMethodReturn hasBind(String openid) {
		this.iMethodReturn.addErrorMsg("default","未知错误");
		try {
			if(StringUtils.isBlank(openid) ) return this.iMethodReturn.addErrorMsg("openid", "openid无效");
			Map<String,Object> userMap = Dao.selectOne("fil_wx_account_mapper.getMapperByOpenid", openid);
			if(userMap == null || userMap.isEmpty()){
				return  this.iMethodReturn.addErrorMsg("nomap", "您还没有完成绑定第一步，请输入【bind/bd 身份证或营业执照】开始绑定吧");
			}
			String hasbind = userMap.get("HASBIND").toString();
			if("1".equals(hasbind)){
				SkyEye.getRequest().getSession().setAttribute(Variable.__openid__.toString(), openid);
			}
			
			Log.debug("当前用户的openid : "+openid);
			
			return   "0".equals(hasbind) 
					? this.iMethodReturn.addErrorMsg("step2", "已完成第一步：客户【"+ userMap.get("NAME") +"】\n还需完成第二步：输入【yzm 手机验证码】")
					: this.iMethodReturn.clearError().addMsg("userMap", userMap);
		} catch (Exception e) {
			e.printStackTrace();
			return this.iMethodReturn.addErrorMsg("server", e.getMessage());
		}
	}

	
	/**
	 * 核实验证码
	 * @param openid
	 * @param keyword
	 * @return
	 * @author LUYANFENG @ 2015年7月8日
	 */
	public IMethodReturn checkVerifyCode(String openid, String keyword) {
		
		Map<String,Object> user = Dao.selectOne("fil_wx_account_mapper.getMapperByOpenid", openid);
		if(user != null && !user.isEmpty()){
			String verify_code = (String) user.get("VERIFY_CODE");
			String hasbind = user.get("HASBIND").toString();
			Log.debug("o == hasbind : "+hasbind +("0".equals(hasbind)));
			if( "0".equals(hasbind)){
				try {
					String verify_time =  user.get("VERIFY_TIME").toString(); // 分钟
					Date bind_time  = (Date) user.get("BIND_TIME");
					Log.debug("new Date().getTime() - bind_time.getTime() = "+(new Date().getTime() - bind_time.getTime() ));
					Log.debug("Long.valueOf(verify_time) * 60 * 1000  ="+(Long.valueOf(verify_time) * 60 * 1000));
					if(new Date().getTime() - bind_time.getTime() > (Long.valueOf(verify_time) * 60 * 1000)){
						Dao.update("fil_wx_account_mapper.resetBindTime", openid);
						iMethodReturn.addErrorMsg("timeout", "验证码已超时，您需要重新绑定");
					}else{
						if(verify_code.equals(keyword ) ){
							Map<String,Object> updateMap = new HashMap<String,Object>();
							updateMap.put("openid", openid);
							updateMap.put("hasbind", "1");
							Dao.update("fil_wx_account_mapper.updateMapperByOpenid", updateMap);
							iMethodReturn.clearError();
						}else{
							iMethodReturn.addErrorMsg("ne", "输入的验证码不匹配");
						}
					}
					return this.iMethodReturn;
				} catch (Exception e) {
					e.printStackTrace();
					return iMethodReturn.addErrorMsg("server", e.getMessage());
				}
			}
		}
		
		return this.iMethodReturn.addErrorMsg("nomap", "您还没有完成绑定第一步，请输入【bind/bd 身份证或营业执照】开始绑定吧");
	}
}
