package com.pqsoft.util;

import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolManager;

import com.pqsoft.skyeye.CentralController;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.exception.SystemException;
import com.pqsoft.skyeye.util.RES;

public final class VelocityUtils extends VM{
	public VelocityUtils() {
		super();
	}
	
	
	private static ToolManager ToolsManager = new ToolManager(); 
	static {
		try {
			ToolsManager.configure("velocity-tools.properties");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	
	/**
	 * 把配置文件提了出来，方便velocity的更多种应用
	 * @author : LUYANFENG @ 2015年1月20日
	 */
	public static String html(String path, Map<String,Object>... params) {
		StringWriter writer = new StringWriter();
		try {
			Locale defaultLocale = Locale.getDefault();
			
			ToolContext context =  ToolsManager.createContext() ;
			if(params != null && params.length > 0) {
				for(Map<String,Object> m : params){
					context.putAll(m);
				}
			}
			
			path = "/" + path;
			if (CentralController.getRequest() != null) {
				Cookie[] cookies = CentralController.getRequest().getCookies();
				context.put("_theme_", "blue");
				if (cookies != null)
					for (Cookie cookie : cookies) {
						if ("_theme_".equals(cookie.getName())) {
							context.put("_theme_", cookie.getValue());
						}
					}
				defaultLocale = CentralController.getRequest().getLocale();
				context.put("request", CentralController.getRequest());
				context.put("response", CentralController.getResponse());
				context.put("session", CentralController.getRequest().getSession());
				context.put("application", CentralController.getRequest().getSession().getServletContext());
				
				context.put("ctx", CentralController.getRequest().getContextPath());
				context.put("_basePath", CentralController.getRequest().getContextPath());
			}
			String suff = path.substring(path.lastIndexOf("."));
			path = path.substring(0, path.lastIndexOf(suff));
			String pathLocal = path + "_" + defaultLocale.getLanguage() + "_" + defaultLocale.getCountry();
			if (new RES().getResource("/content" + pathLocal + suff).exists())
				Velocity.mergeTemplate("content" + pathLocal + suff, "UTF-8", context, writer);
			else
				Velocity.mergeTemplate("content" + path + suff, "UTF-8", context, writer);
		} catch (Exception e) {
			throw new SystemException(e);
		}
		return writer.toString();
	}
	
	/*
	 * //TODO
	public static String generateHTML(String path){
		ToolContext createContext = ToolsManager.createContext();
		Writer writer = new StringWriter();
		createContext.get("request");
		Velocity.evaluate(createContext, writer , "generateHTML", path);
		return writer.toString();
	}*/

}
