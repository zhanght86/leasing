package com.pqsoft.action;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.logicalcobwebs.proxool.ProxoolException;
import org.logicalcobwebs.proxool.ProxoolFacade;
import org.logicalcobwebs.proxool.admin.SnapshotIF;
import org.springframework.util.FileCopyUtils;

import com.pqsoft.dataDictionary.service.SysDataDictionaryService;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.CentralController;
import com.pqsoft.skyeye.Config;
import com.pqsoft.skyeye.ThreadMonitor;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyJson;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.cache.Cache;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.User;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.util.DBUtil;
import com.pqsoft.skyeye.util.LogFileUtil;
import com.pqsoft.skyeye.util.LogFileUtil.SeekRead;
import com.pqsoft.skyeye.util.UTIL;

import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.PageUtil;

public class SysAction extends Action {

	@Override
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		String dir = (String) (Config.get("static.file.path") == null ? "/temp/" : Config.get("static.file.path"));
		context.put("staticFile", "静态文件数：" + new File(dir).list().length + "");
		context.put("cached", "缓存：" + Cache.getStatus().replaceAll("\\r\\n", "<br/>") + "");
		{
			int mb = 1024 * 1024;
			context.put("sst1", Runtime.getRuntime().totalMemory() / mb);
			context.put("sst2", Runtime.getRuntime().freeMemory() / mb);
			context.put("sst3", Runtime.getRuntime().maxMemory() / mb);
			context.put("osname", System.getProperty("os.name"));
			// 获得线程总数
			ThreadGroup parentThread;
			for (parentThread = Thread.currentThread().getThreadGroup(); parentThread.getParent() != null; parentThread = parentThread.getParent());
			int totalThread = parentThread.activeCount();
			context.put("totalThread", totalThread);
			try {
				SnapshotIF snapshot = ProxoolFacade.getSnapshot(null, true);
				int curActiveCount = snapshot.getActiveConnectionCount(); // 获得活动连接数
				int availableCount = snapshot.getAvailableConnectionCount();// 获得可得到的连接数
				int maxCount = snapshot.getMaximumConnectionCount(); // 获得总连接数
				context.put("curActiveCount", curActiveCount);
				context.put("availableCount", availableCount);
				context.put("maxCount", maxCount);
			} catch (ProxoolException e) {
				e.printStackTrace();
			}

		}
		return new ReplyHtml(VM.html("sys-portal.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "系统信息" })
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply info() {
		VelocityContext context = new VelocityContext();
		context.put("info", SkyEye.getConfig());
		context.put("format", UTIL.FORMAT);
		context.put("runtime", Runtime.getRuntime());
		int mb = 1024 * 1024;
		context.put("sst1", Runtime.getRuntime().totalMemory() / mb);
		context.put("sst2", Runtime.getRuntime().freeMemory() / mb);
		context.put("sst3", Runtime.getRuntime().maxMemory() / mb);

		// 获得线程总数
		ThreadGroup parentThread;
		for (parentThread = Thread.currentThread().getThreadGroup(); parentThread.getParent() != null; parentThread = parentThread.getParent());
		int totalThread = parentThread.activeCount();
		context.put("totalThread", totalThread);
		context.put("cached", "缓存：" + Cache.getStatus() + "");
		try {
			context.put("proxool", ProxoolFacade.getSnapshot((String) SkyEye.getConfig("jdbc.type"), true));
		} catch (ProxoolException e) {
			e.printStackTrace();
		}
		return new ReplyHtml(VM.html("sys-info.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "系统信息" })
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply user() {
		VelocityContext context = new VelocityContext();
		context.put("sessions", User.getOnlineSessions());
		context.put("format", UTIL.FORMAT);
		return new ReplyHtml(VM.html("sys-user.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "系统信息" })
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply thread() {
		VelocityContext context = new VelocityContext();
		context.put("format", UTIL.FORMAT);
		context.put("list", ThreadMonitor.getThreads().values());
		context.put("now", new Date());
		return new ReplyHtml(VM.html("sys-thread.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply db() {
		VelocityContext context = new VelocityContext();
		try {
			context.put("list", ProxoolFacade.getSnapshot((String) SkyEye.getConfig("jdbc.type"), true).getConnectionInfos());
		} catch (ProxoolException e) {
			e.printStackTrace();
		}
		context.put("format", UTIL.FORMAT);
		context.put("now", new Date());
		return new ReplyHtml(VM.html("sys-db.vm", context));
	}

	@aPermission(name = { "权限管理", "系统信息", "数据备份" })
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply dbBak() {
		VelocityContext context = new VelocityContext();
		String pathDir = "/pqsoft/dbbak";
		File fdir = new File(pathDir);
		new Date(fdir.lastModified());
		if (!fdir.exists()) new File(pathDir).mkdirs();
		context.put("list", fdir.listFiles());
		context.put("format", UTIL.FORMAT);
		return new ReplyHtml(VM.html("sys-dbBak.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply doDbBak() {
		String pathDir = "/pqsoft/dbbak";
		try {
			DBUtil.dbBak((String) SkyEye.getConfig("jdbc.url"), (String) SkyEye.getConfig("jdbc.username"),
					(String) SkyEye.getConfig("jdbc.password"), pathDir + "/dbbak" + UTIL.FORMAT.date(new Date(), "yyyyMMddHHmmss") + ".dmp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ReplyAjax();
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply doDbBakDel() {
		String pathDir = "/pqsoft/dbbak/";
		try {
			File file = new File(pathDir + SkyEye.getRequest().getParameter("name"));
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ReplyAjax();
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply doDbBakDown() {
		String pathDir = "/pqsoft/dbbak/";
		try {
			File file = new File(pathDir + SkyEye.getRequest().getParameter("name"));
			return new ReplyFile(file, file.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "系统信息", "维护" })
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply killConn() {
		String id = SkyEye.getRequest().getParameter("id");
		try {
			ProxoolFacade.killConnecton((String) SkyEye.getConfig("jdbc.type"), Long.parseLong(id), false);
		} catch (Exception e) {}
		return new ReplyAjax();
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "系统信息", "维护" })
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply killThread() {
		try {
			String id = SkyEye.getRequest().getParameter("id");
			ThreadMonitor.kill(id);
		} catch (Exception e) {}
		return new ReplyAjax();
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "系统信息", "维护" })
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply killUser() {
		try {
			String id = SkyEye.getRequest().getParameter("id");
			HttpSession ss = null;
			if (id != null && !"".equals(id)) {
				for (HttpSession session : User.getOnlineSessions()) {
					if (id.equals(session.getId())) {
						ss = session;
						break;
					}
				}
				Security.cleanSession(ss);
				ss.invalidate();
			}
		} catch (Exception e) {}
		return new ReplyAjax();
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply clearCache() {
		Cache.clear();
		return new ReplyAjax();
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply clearTemp() {
		File f = new File((String) (Config.get("static.file.path") == null ? "/temp/" : Config.get("static.file.path")));
		if (f.exists() && f.isDirectory()) {
			for (File ff : f.listFiles()) {
				ff.delete();
			}
		}
		return new ReplyAjax();
	}

	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply log() {
		return new ReplyHtml(VM.html("sys-log.vm", null));
	}

	@SuppressWarnings("unchecked")
	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply getLog() {
		Map<String, Object> param = _getParameters();
		SeekRead read = new SeekRead();
		read.file = ((Map)SysDictionaryMemcached.getList("日志路径").get(0)).get("CODE").toString();
//		read.file = "/logs/pqsoft.log";
		long l = param.containsKey("point") ? Long.parseLong(param.get("point").toString()) : -1;
		read.startPoint = l;
		read.endPoint = l;
		int i = 60;
		JSONObject json = new JSONObject();
		json.put("flag", true);
		LogFileUtil.seekHtml(read);
		while (StringUtils.isEmpty(read.text)) {
			if (i-- == 0) break;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			LogFileUtil.seekHtml(read);
		}
		json.put("point", read.endPoint);
		json.put("text", read.text.replaceAll("\\n", "\r\n ").replaceAll("<p>", "").replaceAll("</p>", "\r\n "));
		return new ReplyJson(json);
	}

	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply getDevLog() {
		return new ReplyHtml(VM.html("", null));
	}

	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply getDevLogPage() {
		Map<String, Object> map = _getParameters();
		return new ReplyAjaxPage(PageUtil.getPage(map, "", ""));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "操作日志" })
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply getOpLog() {
		return new ReplyHtml(VM.html("sys-oplog.vm", null));
	}

	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name = { "权限管理", "操作记录" })
	@aDev(code = "170012", email = "wuyanfeijob@163.com", name = "武燕飞")
	public Reply getOpLogDetail() {
		return new ReplyHtml(VM.html("sys-oplog_detail.vm", null));
	}

	@aAuth(type = aAuth.aAuthType.ALL)
	@aPermission(name = { "权限管理", "操作记录" })
	@aDev(code = "170012", email = "wuyanfeijob@163.com", name = "武燕飞")
	public Reply getOpLogDetailPage() {
		Map<String, Object> map = _getParameters();
		return new ReplyAjaxPage(PageUtil.getPage(map, "sys.getDevLogDetailPage", "sys.getDevLogDetailPageCount"));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "操作日志" })
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply getOpLogPage() {
		Map<String, Object> map = _getParameters();
		return new ReplyAjaxPage(PageUtil.getPage(map, "sys.getDevLogPage", "sys.getDevLogPageCount"));
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public ReplyFile downFile() {
		Map<String, Object> param = _getParameters();
		String path = param.get("PATH").toString();
		File file = new File(path);
		return new ReplyFile(file, file.getName());
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170062", email = "wujd@pqsoft.cn", name = "吴剑东")
	public void getIndexChart() {
		Map<String, Object> map = _getParameters();
		String TYPE = map.get("TYPE") + "";
		String xmlStr = "";
		if ("1".equals(TYPE)) {
			xmlStr = getPieXml();
		} else if ("2".equals(TYPE)) {
			xmlStr = getColumnXml();
		}
		if (xmlStr.length() > 0) {
			try {
				InputStream bfis = new ByteArrayInputStream(xmlStr.getBytes("GBK"));
				byte[] b = FileCopyUtils.copyToByteArray(bfis);
				int length = b.length;
				SkyEye.getResponse().setCharacterEncoding("UTF-8");
				SkyEye.getResponse().addHeader("Content-Disposition",
						"attachment;filename=" + new String("chart.xml".getBytes("GB2312"), "ISO-8859-1"));
				SkyEye.getResponse().setContentType("application/octet-stream");
				SkyEye.getResponse().addHeader("Content-Length", "" + length);
				OutputStream toClient = new BufferedOutputStream(SkyEye.getResponse().getOutputStream());
				toClient.write(b);
				toClient.close();

			} catch (Exception e) {}
		}
	}

	public String getPieXml() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		try {

			// Map SUP_MAP = BaseUtil.getSup();
			// if (SUP_MAP != null) {
			// param.put("SUP_ID", SUP_MAP.get("SUP_ID"));
			// }
			// Map COM_MAP = BaseUtil.getCom();
			// if (COM_MAP != null) {
			// param.put("COM_ID", COM_MAP.get("COMPANY_ID"));
			// }
			context.put("ITEM", Dao.selectOne("sys.getProjectNum", param));
			// 生成字符串类型的xml格式数据
			return VM.html("charts/projectNumReport.vm", context).toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getColumnXml() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		try {

			// Map SUP_MAP = BaseUtil.getSup();
			// if (SUP_MAP != null) {
			// param.put("SUP_ID", SUP_MAP.get("SUP_ID"));
			// }
			// Map COM_MAP = BaseUtil.getCom();
			// if (COM_MAP != null) {
			// param.put("COM_ID", COM_MAP.get("COMPANY_ID"));
			// }
			context.put("ITEM", Dao.selectOne("sys.getRentOverdue", param));
			// 生成字符串类型的xml格式数据
			return VM.html("charts/fundOverdueReport.vm", context).toString();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply getReportCharts() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("param", param);
		// param.put("ISNOTFL", (BaseUtil.getSup() == null && BaseUtil.getCom()
		// == null) ? null : "ISNOTFL");
		context.put("list", Dao.selectList("sys.getContact", param));
		return new ReplyHtml(VM.html("sys-reportChats.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply getContact() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		BaseUtil.getDataAllAuth(param);
		context.put("param", param);
		// param.put("ISNOTFL", (BaseUtil.getSup() == null && BaseUtil.getCom()
		// == null) ? null : "ISNOTFL");
		context.put("list", Dao.selectList("sys.getContact", param));
		return new ReplyHtml(VM.html("sys-contact.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aPermission(name = { "权限管理", "关闭系统" })
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply stop() {
		CentralController.stop();
		try {
			SkyEye.getResponse().sendRedirect(SkyEye.getRequest().getContextPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "系统信息", "图片LOGO" })
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply pictureLogo() {
		return new ReplyHtml(VM.html("sys-pictureLogo.vm", null));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "系统信息", "上传图片" })
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply loginPicture() {
		String dir = SkyEye.getConfig("file.path");
		File file = _getFileOne();
		File file2 = new File(dir + File.separator + "sys_img_loginbg.png");
		try {
			FileCopyUtils.copy(file, file2);
			return new ReplyAjax(true, "上传成功");
		} catch (IOException e) {
			e.printStackTrace();
			return new ReplyAjax(false, "上传失败");
		}
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "系统信息", "上传图片" })
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply loginLogo() {
		String dir = SkyEye.getConfig("file.path");
		File file = _getFileOne();
		File file2 = new File(dir + File.separator + "sys_img_loginLogo.png");
		try {
			FileCopyUtils.copy(file, file2);
			return new ReplyAjax(true, "上传成功");
		} catch (IOException e) {
			e.printStackTrace();
			return new ReplyAjax(false, "上传失败");
		}
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "权限管理", "系统信息", "上传图片" })
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply homeLogo() {
		String dir = SkyEye.getConfig("file.path");
		File file = _getFileOne();
		File file2 = new File(dir + File.separator + "sys_img_homeLogo.png");
		try {
			FileCopyUtils.copy(file, file2);
			return new ReplyAjax(true, "上传成功");
		} catch (IOException e) {
			e.printStackTrace();
			return new ReplyAjax(false, "上传失败");
		}
	}

	@aAuth(type = aAuth.aAuthType.ALL)
	@aDev(code = "170026", email = "wanghl@pqsoft.cn", name = "王海龙")
	public Reply download() {
		String dir = SkyEye.getConfig("file.path");
		String fileName = SkyEye.getRequest().getParameter("fileName");
		File file = new File(dir + File.separator + fileName);
		return new ReplyFile(file, file.getName());
	}

	public static Map<String, LinkedList<String>> param = new HashMap<String, LinkedList<String>>();

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply getMsg() {
		LinkedList<String> l = param.get(Security.getUser().getCode());
		if (l == null || l.size() == 0) return null;
		synchronized (l) {
			return new ReplyHtml(l.pop());
		}
	}

	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "170043", email = "lichaohn@163.com", name = "李超")
	public Reply setMsg() {
		String code = Security.getUser().getCode();
		LinkedList<String> l = param.get(code);
		if (l == null) {
			l = new LinkedList<String>();
			param.put(code, l);
		}
		synchronized (l) {
			l.push(SkyEye.getRequest().getParameter("msg"));
			return null;
		}
	}

	public static void setMsg(String code, String msg) {
		LinkedList<String> l = param.get(code);
		if (l == null) {
			l = new LinkedList<String>();
			param.put(code, l);
		}
		synchronized (l) {
			l.push(msg);
		}
	}
}
