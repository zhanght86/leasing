package com.pqsoft.checkIdCard.action;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Map;
import net.sf.json.JSONObject;
import org.springframework.util.FileCopyUtils;
import sun.misc.BASE64Decoder;
import com.pqsoft.checkIdCard.service.CheckIdCardService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.osi.api.IDCard;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.util.JsonUtil;

@SuppressWarnings("restriction")
public class CheckIdCardAction extends Action{

	private String path = "checkIdCard/";
	private CheckIdCardService service= new CheckIdCardService();
	
	@Override
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"接口管理","公安","认证"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply execute() {
		return new ReplyHtml(VM.html(path+"checkIdCard.vm", null));
	}
	
	@aAuth(type = aAuthType.USER)
	@aPermission(name ={"接口管理","公安","认证"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply doCheck() {
		Map<String, Object> param = _getParameters();
		param.put("USERID", Security.getUser().getId());
		String name = param.get("NAME").toString();
		String ID_CARD_NO = param.get("ID_CARD_NO").toString();
		boolean flag = false;
		//先查身份证认证成功通过记录表，看此证件是否已被认证
		Map<String, Object> cardMap = service.getIdCardMsg(ID_CARD_NO);
		if(cardMap == null){
			IDCard idCard = new IDCard();
			idCard.setName(name);
			idCard.setIdCard(ID_CARD_NO);
			try {
				idCard.check();
			} catch (Exception e) {
				e.printStackTrace();
				param.put("RESULT", "认证失败");
				service.addCardIdLog(param);
				return new ReplyAjax(flag, param);
			} 
			param.put("CHECK_REAL", idCard.isReal());
			param.put("RESULT", idCard.getMsg());
			param.put("NAME", idCard.getName());
			param.put("ID_CARD_NO", idCard.getIdCard());
			param.put("SEX", idCard.getResex());
			param.put("BIRTHDAY", idCard.getRebirthday());
			param.put("ADDRESS", idCard.getReyyfzd());
			param.put("LOCAL", idCard.isLocal() + "");
			BASE64Decoder decode = new BASE64Decoder(); 
			byte[] b;
			try {
				if(idCard.getPhoto() == null){
					param.put("PHOTO", null);
				}else{
					b = decode.decodeBuffer(idCard.getPhoto());
					param.put("PHOTO", b);
				}
			} catch (IOException e) {
				e.printStackTrace();
				param.put("PHOTO", "");
				param.put("RESULT", "认证失败");
				service.addCardIdLog(param);
				return new ReplyAjax(flag, param);
			} 
			//如果成功的将认证成功的信息添加到身份证认证成功通过记录表
			if(idCard.isReal()){
				service.addCheckCardIdSuccess(param);
			}
			flag = idCard.isReal();
		}else{
			if(cardMap.get("NAME").equals(param.get("NAME"))){
				param.putAll(cardMap);
				flag = true;
			}else{
				param.put("RESULT", "证件名称与证件号不匹配");
			}
		}
		//将此次认证添加到认证日志表
		service.addCardIdLog(param);
		param.put("PHOTO", "");
		return new ReplyAjax(flag, param);
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"接口管理","公安","认证"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
		public Reply downloadPictureFile(){
		Map<String, Object> param = _getParameters();
		Map<String, Object> cardMap = service.getIdCardMsg(param.get("ID_CARD_NO").toString());
		Blob blob = (Blob)cardMap.get("PHOTO");
		InputStream inStream = null;
		try {
			inStream = blob.getBinaryStream();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		byte[] file_byte = null;
		try {
			file_byte = FileCopyUtils.copyToByteArray(inStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ReplyFile(file_byte, cardMap.get("NAME")+".JPG");
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"接口管理","公安","认证查询"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply checkIdCardSuccessMg() {
		return new ReplyHtml(VM.html(path+"checkIdCardSuccessMg.vm", null));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"接口管理","公安","认证查询"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getCheckIdCardData() {
		Map<String, Object> param = _getParameters();
			JSONObject json = JSONObject.fromObject(param);
			param.clear();
			param.putAll(JsonUtil.toMap(json));
		return new ReplyAjaxPage(service.getCICPage(param));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"接口管理","公安","认证日志"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply checkIdCardLogMg() {
		return new ReplyHtml(VM.html(path+"checkIdCardLogMg.vm", null));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"接口管理","公安","认证日志"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getCheckIdCardLogData() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		return new ReplyAjaxPage(service.getCICLogPage(param));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"接口管理","公安","调用认证接口查询"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply callInterfaceMg() {
		return new ReplyHtml(VM.html(path+"callInterfaceMg.vm", null));
	}
	
	@aAuth(type=aAuth.aAuthType.USER)
	@aPermission(name ={"接口管理","公安","调用认证接口查询"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getCallInterfaceData() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		return new ReplyAjaxPage(service.getCallInterfaceData(param));
	}
	
	@aAuth(type=aAuth.aAuthType.LOGIN)
//	@aPermission(name ={"接口管理","判断身份证认证"})
	@aDev(code = "47", email = "fuyulong47@foxmail.com", name = "付玉龙")
	public Reply getIdCardState() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.getIdCariState(param));
	}

}