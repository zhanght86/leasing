package com.pqsoft.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.velocity.VelocityContext;
import org.springframework.util.FileCopyUtils;

import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.SessAttr;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyMobile;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Menu;
import com.pqsoft.skyeye.rbac.api.MenuIcon;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.User;
import com.pqsoft.skyeye.rbac.api.User.Org;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.util.UTIL;

public class IndexAction extends Action {

	@Override
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		context.put("user", Security.getUser());
		
		/*start 2016年4月7日 14:52:23 首页切换功能添加分公司和分店 吴国伟*/
		//当前登录的org_id
		String currentOrgId = Security.getUser().getOrg().getId();
		//所在的所有组织架构
		Map<String, Org> mapOrg = Security.getUser().getOrgMap();
		Iterator it = mapOrg.keySet().iterator();
		Map<String,Object> m;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> currentOrgMap = new HashMap<String,Object>();;
		while (it.hasNext()) {
			String itNext = (String) it.next();
			m = new  HashMap<String,Object>();
			m.put("ORG_ID", itNext);
			m.put("NAME", mapOrg.get(itNext).getName());
			m.put("PLATFORM", mapOrg.get(itNext).getPlatform());
			m.put("ORG_NAME", "门店");
			//查询门店信息
			String shopInfo = Dao.selectOne("sys.getOrgCompanyMd", m);
			m.put("ORG_NAME", "分公司");
			//查询分公司信息
			String branch = Dao.selectOne("sys.getOrgCompanyMd", m);
			m.put("ORG_BRANCH_INFO", branch);
			m.put("ORG_SHOP_INFO", shopInfo);
			list.add(m);
			//获取当前登录的组织机构ID信息 根据ORG_ID匹配
			if(currentOrgId.equals(itNext)){
				currentOrgMap.putAll(m);
			}
		}
		context.put("listOrg", list);
		context.put("currentOrgMap", currentOrgMap);
		/*end*/
		context.put("onlineCount", User.onlineCount());
		String USER_CODE = Security.getUser().getCode();
		Map<String, Object> user = Dao.selectOne("sys.queryUserME", USER_CODE);
		context.put("userME", user);
		boolean userflag = true;
		boolean changeflag = false;
		Map<String, Object> psdMap = (Map<String, Object>) new SysDictionaryMemcached().get("强制更改密码开关").get(0);
		if (!"god".equals(USER_CODE) && "0".equals(psdMap.get("CODE").toString().trim())) {// 开关状态为开
			String MODIFY_DATE = null;
			Calendar nowC = Calendar.getInstance();
			Calendar modifyC = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (user != null && !user.isEmpty() && user.size() > 0) {
				MODIFY_DATE = user.get("MODIFY_DATE") + "";
				if (MODIFY_DATE != null && !MODIFY_DATE.equals("null")) {
					try {
						modifyC.setTime(sdf.parse(MODIFY_DATE));
						String day = null == psdMap.get("SHORTNAME") ? "30" : psdMap.get("SHORTNAME").toString().trim();
						modifyC.add(Calendar.DAY_OF_YEAR, Integer.parseInt(day));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else {
					changeflag = true;
				}
			}
			// String date=sdf.format(new Date());
			nowC.setTime(new Date());
			// if(date.substring(date.lastIndexOf("-")+1,date.length()).equals("01")&&!date.equals(MODIFY_DATE)){
			// changeflag=true;//需要在每月的1号 进行密码修改
			// }else
			if (MODIFY_DATE == null) {
				changeflag = true;// 需要在每月的1号 进行密码修改
			} else if (modifyC.before(nowC)) {
				changeflag = true;// 需要在每月的1号 进行密码修改
			}

			// 密码重置后登录需要修改密码
			if (Security.getUser().getPassword().equals("CD598281A946ACC7035C7D2C615CC6FB")) {
				changeflag = true;
			}
		}
		context.put("userflag", userflag);
		context.put("changeflag", changeflag);
		// String menuData = (String)
		// SkyEye.getRequest().getSession().getAttribute("vm_menu_accordion");
		// if (StringUtils.isNull(menuData)) {
		// Map<String,Object> m = new HashMap<String,Object>();
		// getPageParameter(m);
		// menuData = TreeUtil.parseToHtml(new MenuService().getMenus(m));
		// SkyEye.getRequest().getSession().setAttribute("vm_menu_accordion",
		// menuData);
		// }
		// context.put("vm_menu_accordion", menuData);
		context.put("time", UTIL.FORMAT.date(new Date(), "yyyy-MM-dd EEEE"));
		// JSONArray menuData = (JSONArray)
		// SkyEye.getRequest().getSession().getAttribute("menuData");
		// if (StringUtils.isNull(menuData)) {
		// Map<String, Object> param = _getParameters();
		// param.put("USERCODE", Security.getUser().getCode());
		// menuData = TreeUtil.parseToJSONArray(new
		// MenuService().getMenus(param));
		// SkyEye.getRequest().getSession().setAttribute("menuData", menuData);
		// }
		// context.put("menu", menuData);
		return new ReplyHtml(VM.html("index-.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply desktop() {
		VelocityContext context = new VelocityContext();
		User user = Security.getUser();
		if (user.getMenu() == null) {
			user.setMenu(getMenu());
			SessAttr.setAttribute(Security.USER, user);
		}
		context.put("user", Security.getUser());
		return new ReplyHtml(VM.html("index-desktop.vm", context));
	}

	private static Menu getMenu() {
		List<Menu> menus = null;
		Menu menu = new Menu();
		menu.setId("0");
		if ("system".equals(Security.getUser().getCode()) || "admin".equals(Security.getUser().getCode())) {
			menus = Dao.selectList("rbac.menu.getAllMenu", Security.getUser().getCode());
			{
				Map<String, String> map = new HashMap<String, String>();
				for (Menu m : menus) {
					if (m.getLevel() == 3) map.put(m.getId(), m.getParentId());
				}
				for (Menu m : menus) {
					if (m.getLevel() == 4) m.setParentId(map.get(m.getParentId()));
				}
			}
			for (Menu m : menus) {
				if (m.getParentId() == null || "".equals(m.getParentId())) continue;
				addMenu(m, menu);
			}
		} else {
			menus = Dao.selectList("rbac.menu.getMenuByCode", Security.getUser().getId());
			Map<String, Menu> m = new HashMap<String, Menu>();
			for (Menu me : menus) {
				m.put(me.getId(), me);
			}
			for (Menu me : menus) {
				if ("0".equals(me.getParentId())) {
					menu.getChildren().add(me);
				} else {
					Menu temp = m.get(me.getParentId());
					while (!"0".equals(temp.getParentId())) {
						if (temp.getParentId() == null || "".equals(temp.getParentId())) break;
						temp = m.get(temp.getParentId());
					}
					temp.getChildren().add(me);
				}
			}
		}
		return menu;
	}

	private static void addMenu(Menu src, Menu target) {
		if (src == null || target == null) return;
		if (src.getParentId().equals(target.getId())) {
			target.getChildren().add(src);
		} else {
			for (Menu menu : target.getChildren()) {
				addMenu(src, menu);
			}
		}
	}

	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply icon() {
		byte[] b = null;
		try {
			b = MenuIcon.getInstences().get(SkyEye.getRequest().getParameter("id"));
		} catch (Exception e) {}
		if (b == null || b.length == 0) {
			InputStream is = this.getClass().getResourceAsStream("/content/img/18.png");
			if (is != null) try {
				return new ReplyFile(FileCopyUtils.copyToByteArray(is), "icon.png");
			} catch (IOException e) {}
		}
		return new ReplyFile(b, "icon.png");
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply portal() {
		VelocityContext context = new VelocityContext();
		context.put("protals", Dao.selectList("portal.getAll"));
		return new ReplyHtml(VM.html("index-portal.vm", context));
	}

	/**
	 * 下载手机APK
	 * 
	 * @return
	 * @author:King 2014-1-10
	 */
	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply mobileApk() {
		return new ReplyFile(new File("/mobileApk" + File.separator + "SZMobileApp.apk"), "SZMobileApp.apk");
	}

	/**
	 * 下载手机APK配置文件
	 * 
	 * @return
	 * @author:King 2014-1-10
	 */
	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "170053", email = "jinfu@pqsoft.cn", name = "King")
	public Reply mobileApkVersion() {
		return new ReplyFile(new File("/mobileApk" + File.separator + "version.xml"), "version.xml");
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichao@pqsoft.cn", name = "李超")
	public Reply loadRole() {
		String orgId = SkyEye.getRequest().getParameter("id");
		User user = Security.getUser();
		if (user.getOrgMap().containsKey(orgId)) {
			Security.setOrg(orgId);
		}
		try {
			if (Security.isMobile()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("_AUTH_NAME_", user.getName());
				map.put("_AUTH_USER", Security.getUser().getOrgMap());
				// boolean flag=null==BaseUtil.getSup()?false:true;
				// map.put("isSup", flag);
				return new ReplyMobile(map).addOp(new OpLog("系统", "登录", "手机端：应用服务器时间:" + UTIL.FORMAT.date(new Date())));
			} else {
				SkyEye.getResponse().sendRedirect(SkyEye.getRequest().getContextPath());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
