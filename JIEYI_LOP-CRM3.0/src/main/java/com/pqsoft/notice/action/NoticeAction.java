package com.pqsoft.notice.action;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

import org.apache.commons.fileupload.FileItem;
import org.apache.velocity.VelocityContext;

import com.pqsoft.notice.service.NoticeService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.ReplyMobile;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.User;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;

public class NoticeAction extends Action {
	private static final String basePath = "notice/";
	NoticeService service = new NoticeService();

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "公告管理" })
	@aDev(code = "170015", email = "yangwn@pqsoft.cn", name = "杨卫宁")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		context.put("PContext", param);
		return new ReplyHtml(VM.html(basePath + "noticeManager.vm", context));
		
		
		
	}

	@aAuth(type = aAuthType.ALL)
	@aDev(code = "170015", email = "yangwn@pqsoft.cn", name = "杨卫宁")
	public Reply getNotice() {
		try {
			Map<String, Object> map = _getParameters();

			List<Map<String, Object>> list = Dao.selectList("notice.queryNoticeList", map);

			return new ReplyMobile(list);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReplyMobile(ReplyMobile.ERROR_505, "获取数据失败");
		}
	}

	@aAuth(type = aAuthType.ALL)
	@aDev(code = "170015", email = "yangwn@pqsoft.cn", name = "杨卫宁")
	public Reply getNoticeInfo() {
		try {
			Map<String, Object> map = _getParameters();

			Map<String, Object> data = Dao.selectOne("notice.selectNoticeyId", map);
			data.put("CONTENT", ClobToString((CLOB) data.get("CONTENT")));
			return new ReplyMobile(data);
		} catch (Exception e) {
			e.printStackTrace();
			return new ReplyMobile(ReplyMobile.ERROR_505, "获取数据失败");
		}
	}

	@aAuth(type = aAuthType.ALL)
	@aDev(code = "170015", email = "yangwn@pqsoft.cn", name = "杨卫宁")
	public Reply downloadPictrueM() {
		Map<String, Object> param = _getParameters();
		try {
			Map<String, Object> map = Dao.selectOne("notice.selectImageById", param);
			BLOB blob = (BLOB) map.get("IMAGE");
			SkyEye.getResponse().setContentLength(blob.getBufferSize());
			return new ReplyFile(blob.getBinaryStream(), "a.png");
		} catch (Exception e) {
			SkyEye.getResponse().setContentLength(0);
			e.printStackTrace();
		}
		return null;

	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "公告管理", "查询" })
	@aDev(code = "170015", email = "yangwn@pqsoft.cn", name = "杨卫宁")
	public Reply pageData() {
		Map<String, Object> param = _getParameters();
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "公告管理", "添加" })
	@aDev(code = "170015", email = "yangwn@pqsoft.cn", name = "杨卫宁")
	public Reply toAdd() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html(basePath + "noticeAdd.vm", context));
	}

	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "公告管理", "添加" })
	@aDev(code = "170015", email = "yangwn@pqsoft.cn", name = "杨卫宁")
	public Reply doAdd() {
		VelocityContext context = new VelocityContext();
		try {
			List<FileItem> fileList = _getFileItem();
			Map<String, Object> param = _getParameters();
			param.put("ID", service.selectSeq());
			User user = (User) SkyEye.getRequest().getSession().getAttribute("_USER_");
			param.put("AUTHOR", user == null ? "" : user.getName());
			Iterator<?> i = fileList.iterator();
			File file = null;
			while (i.hasNext()) {
				FileItem fileitem = (FileItem) i.next();
				if (!fileitem.isFormField()) {// 如果是文件
					if (!fileitem.getName().isEmpty() && !"".equals(fileitem.getName())) {
						byte[] buffer = fileitem.get();
						param.put("IMAGE", buffer);
					}
				} else {
					param.put(fileitem.getFieldName(), new String(fileitem.getString().getBytes("ISO-8859-1"), "UTF-8"));
				}
			}
			if (1 == service.saveNotice(param)) {
				return new ReplyAjax(true, "添加成功");
			} else {
				return new ReplyAjax(false, "添加失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReplyAjax(false, "添加失败");
		}
	}

	@aPermission(name = { "参数配置", "公告管理", "修改" })
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170015", email = "yangwn@pqsoft.cn", name = "杨卫宁")
	public Reply toUpdate() {
		VelocityContext context = new VelocityContext();
		Map<String, Object> param = _getParameters();
		Map<String, Object> map = service.selectNoticeyId(param);
		map.put("CONTENT", ClobToString((CLOB) map.get("CONTENT")));
		context.put("notice", map);
		return new ReplyHtml(VM.html(basePath + "noticeEdit.vm", context));

	}

	@aPermission(name = { "参数配置", "公告管理", "修改" })
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170015", email = "yangwn@pqsoft.cn", name = "杨卫宁")
	public Reply doUpdate() {
		VelocityContext context = new VelocityContext();
		try {
			List<FileItem> fileList = _getFileItem();
			Map<String, Object> param = _getParameters();
			User user = (User) SkyEye.getRequest().getSession().getAttribute("_USER_");
			param.put("AUTHOR", user == null ? "" : user.getName());
			Iterator<?> i = fileList.iterator();
			File file = null;
			while (i.hasNext()) {
				FileItem fileitem = (FileItem) i.next();
				if (!fileitem.isFormField()) {// 如果是文件
					if (!fileitem.getName().isEmpty() && !"".equals(fileitem.getName())) {
						byte[] buffer = fileitem.get();
						param.put("IMAGE", buffer);
					} else {
						param.put("IMAGE", null);
					}
				} else {
					param.put(fileitem.getFieldName(), new String(fileitem.getString().getBytes("ISO-8859-1"), "UTF-8"));
				}
			}
			if (1 == service.updateNoticeById(param)) {
				return new ReplyAjax(true, "保存成功");
			} else {
				return new ReplyAjax(false, "保存失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ReplyAjax(false, "保存失败");
		}

	}

	@aPermission(name = { "参数配置", "公告管理", "修改" })
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170015", email = "yangwn@pqsoft.cn", name = "杨卫宁")
	public Reply downloadPictrue() {
		Map<String, Object> param = _getParameters();
		try {
			Map<String, Object> map = service.selectImageById(param);
			BLOB blob = (BLOB) map.get("IMAGE");
			SkyEye.getResponse().setContentType("image/png");
			InputStream in = new BufferedInputStream(blob.getBinaryStream());
			byte[] buf = new byte[1024];
			int hasRead = 0;
			while ((hasRead = in.read(buf)) > 0) {
				SkyEye.getResponse().getOutputStream().write(buf, 0, hasRead);
			}
			SkyEye.getResponse().getOutputStream().flush();
			SkyEye.getResponse().getOutputStream().close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@aPermission(name = { "参数配置", "公告管理", "删除" })
	@aAuth(type = aAuth.aAuthType.USER)
	@aDev(code = "170015", email = "yangwn@pqsoft.cn", name = "杨卫宁")
	public Reply delNotice() {
		VelocityContext context = new VelocityContext();

		Map<String, Object> param = _getParameters();

		if (1 == service.delNoticeById(param)) {
			return new ReplyAjax(true, "删除成功");
		} else {
			return new ReplyAjax(false, "删除失败");
		}

	}

	public static String ClobToString(CLOB clob) {
		String reString = "";
		try {
			Reader is = clob.getCharacterStream();// 得到流
			BufferedReader br = new BufferedReader(is);
			String s = br.readLine();
			StringBuffer sb = new StringBuffer();
			// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
			while (s != null) {
				sb.append(s);
				s = br.readLine();
			}
			reString = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reString;
	}
}
