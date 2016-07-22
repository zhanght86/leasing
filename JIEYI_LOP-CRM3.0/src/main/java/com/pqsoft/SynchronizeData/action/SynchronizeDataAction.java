package com.pqsoft.SynchronizeData.action;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.util.ajax.JSON;

import com.pqsoft.SynchronizeData.service.SynchronizeDataService;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;

import jxl.common.Logger;
import net.sf.json.JSONObject;

/**
 * 与车采数据同步接口
 * @author 王华英  2016年7月10日18:02:33
 */
public class SynchronizeDataAction extends Action {
	private static SynchronizeDataService synchronizeDataService =new SynchronizeDataService() ;
	private Logger logger =Logger.getLogger(SynchronizeDataAction.class);
	@Override
	public Reply execute() {
		return null;
	}
	
	@aAuth(type=aAuth.aAuthType.ALL)
	@aPermission(name ={"接口管理","与车采数据同步","数据同步"})
	@aDev(code = "47", email = "huayingwang@huashenghaoche.com", name = "王华英")
	public void toSynchronizeData() {
		//http://localhost:8080/jieyi-project/SynchronizeData/SynchronizeData!toSynchronizeData.action		
		String paramString;
		String strDatas;
		String strData;
		JSONObject o = new JSONObject();
		try {
			paramString = IOUtils.toString(SkyEye.getRequest().getInputStream(), Charset.forName("utf-8"));
			strData=URLDecoder.decode(paramString, "UTF-8");
			logger.info("车采数据："+strData);
			System.out.println("++++++++"+strData);
			strDatas = strData.substring(5);
			@SuppressWarnings("unchecked")
			Map<String,String> mapJson=(Map<String,String>)JSON.parse(strDatas);
	    	synchronizeDataService.synchronizeData(mapJson);
			o.put("isSuccess", true);
			o.put("returnMsg", "更新成功");
			logger.debug("车采数据更新成功");
		} catch (Exception e ) {
			o.put("isSuccess", false);
			o.put("returnMsg", "更新失败");
			logger.debug("车采数据更新失败");
		} finally {
			try {
				SkyEye.getResponse().setContentType("application/x-javascript;charset=UTF-8");
				SkyEye.getResponse().getWriter().write(o.toString());
				SkyEye.getResponse().getWriter().flush();
				SkyEye.getResponse().getWriter().close();
			} catch (IOException localIOException3) {
				
			}
		}
    }
	
}
