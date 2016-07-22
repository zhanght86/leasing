package com.pqsoft.api.datalist.action;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.velocity.VelocityContext;
import org.springframework.core.io.Resource;

import com.pqsoft.api.datalist.service.DataListService;
import com.pqsoft.dataDictionary.service.DataDictionaryMemcached;
import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.exception.ActionException;
import com.pqsoft.skyeye.rbac.OpLog;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.util.UTIL;
import com.pqsoft.util.JsonUtil;

public class DataListAction extends Action {
	private String path = "datalist/";
	private DataListService service = new DataListService();

	@Override
	@aAuth(type = aAuth.aAuthType.USER)
	@aPermission(name = { "参数配置", "资料清单管理", "列表显示" })
	@aDev(code = "zhaofeng", email = "dreamsfeng@163.com", name = "赵峰")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		List<Map<String, Object>> listType = (List) DataDictionaryMemcached.getList("权证类别");
		List<Map<String, Object>> list = (List) DataDictionaryMemcached.getList("权证级别");
		List<Map<String, Object>> list1 = (List) DataDictionaryMemcached.getList("客户类型");
		List<Map<String, Object>> list2 = (List) DataDictionaryMemcached.getList("婚姻状况");
		// context.put("CUSNAME",service.selectCountByName(param));
		context.put("list", list);
		context.put("listType", listType);
		context.put("list1", list1);
		context.put("list2", list2);
		return new ReplyHtml(VM.html(path + "datalistMg.vm", context));
	}

	// @aPermission(name = {"资料清单管理","DataList进度管理"})
	@aAuth(type = aAuthType.LOGIN)
	@aDev(code = "zhaofeng", email = "zhaofeng1012@foxmail.com", name = "赵峰")
	public Reply getMgDataListData() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		return new ReplyAjaxPage(service.getPage(param));
	}

	@aPermission(name = { "参数配置", "资料清单管理", "查看" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "zhaofeng", email = "zhaofeng1012@foxmail.com", name = "赵峰")
	public Reply getMgDataListData1() {
		Map<String, Object> param = _getParameters();
		JSONObject json = JSONObject.fromObject(param);
		param.clear();
		param.putAll(JsonUtil.toMap(json));
		return new ReplyAjaxPage(service.getPage1(param));
	}

	// 添加
	@aPermission(name = { "参数配置", "资料清单管理", "添加" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "zhaofeng", email = "zhaofeng1012@foxmail.com", name = "赵峰")
	public Reply doAddDataList() {
		// Security.getUser().getOrg().getName();

		Map<String, Object> param = _getParameters();
		if (param.containsKey("param")) {
			JSONObject json = JSONObject.fromObject(param.get("param"));
			param.remove("param");
			param.putAll(JsonUtil.toMap(json));
		}

		boolean flag = service.doAddDataList(param);
		return new ReplyAjax(flag, param).addOp(new OpLog("DataList进度管理", "添加",
				param.toString()));
	}

	// 删除
	@aPermission(name = { "参数配置", "资料清单管理", "删除" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "zhaofeng", email = "zhaofeng1012@foxmail.com", name = "赵峰")
	public Reply doDeleteDataList() {
		Map<String, Object> param = _getParameters();
		boolean flag = service.doDeleteDataList(param);
		return new ReplyAjax(flag, param).addOp(new OpLog("DataList进度管理", "删除",
				param.toString()));
	}

	// 修改
	@aPermission(name = { "参数配置", "资料清单管理", "修改" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "zhaofeng", email = "zhaofeng1012@foxmail.com", name = "赵峰")
	public Reply doUpdateDataList() {

		Map<String, Object> param = _getParameters();
		if (param.containsKey("param")) {
			JSONObject json = JSONObject.fromObject(param.get("param"));
			param.remove("param");
			param.putAll(JsonUtil.toMap(json));
		}
		boolean flag = service.doUpdateDataList(param);
		return new ReplyAjax(flag, param).addOp(new OpLog("DataList进度管理", "修改",
				param.toString()));
	}

	// 查询
	@aPermission(name = { "参数配置", "资料清单管理", "查询" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "zhaofeng", email = "zhaofeng1012@foxmail.com", name = "赵峰")
	public Reply getDataListData() {
		Map<String, Object> param = _getParameters();
		return new ReplyAjax(service.getDataListData(param));
	}

	@aPermission(name = { "参数配置", "资料清单管理", "EXCEL导出" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "zhaofeng", email = "zhaofeng1012@foxmail.com", name = "赵峰")
	public Reply exportExcel() {

		List<Map<String, Object>> dataList = service
				.getData(new HashMap<String, Object>());
		OutputStream ouputStream = null;
		try {
			Resource resource = UTIL.RES
					.getResource("classpath:/content/template/data.xls");
			// Workbook wb = new HSSFWorkbook(new FileInputStream(new
			// File(path)));
			Workbook wb = WorkbookFactory.create(resource.getInputStream());
			Sheet sheet = wb.getSheetAt(0);
			// 循环遍历表sheet.getLastRowNum()是获取一个表最后一条记录的记录号，
			for (int j = 0; j < 1; j++) {
				Row row = sheet.getRow(j);// 行对象
				for (int i = 0; i < row.getLastCellNum(); i++) { // 行里面的各单元格

					Cell cell = row.getCell(i);// 单元格
					System.out.println(cell.getRichStringCellValue());// 印出来看看数据
				}
				System.out
						.println("=================================================");
			}

			// 主体数据
			for (int x = 0; x < dataList.size(); x++) {
				// Row row = sheet.getRow(x+4);
				Row row = sheet.createRow(x + 1);

				Map<String, Object> map = new HashMap<String, Object>();

				map = dataList.get(x);

				if (map != null) {
					int cellIndex = 0;

					// String rownum = "";
					//
					// if(map.get("rownum")!=null
					// &&!"".equals(map.get("rownum"))){
					// rownum = map.get("rownum").toString();
					// }
					row.createCell(cellIndex++).setCellValue(x + 1);
					/*** 权证名称 */
					String WARRANT_NAME = "";
					if (map.get("WARRANT_NAME") != null
							&& !"".equals(map.get("WARRANT_NAME"))) {
						WARRANT_NAME = map.get("WARRANT_NAME").toString();
					}
					row.createCell(cellIndex++).setCellValue(WARRANT_NAME);
					/*** 权证类型*/
					String WARRANT_TYPE = "";
					if (map.get("WARRANT_TYPE") != null
							&& !"".equals(map.get("WARRANT_TYPE"))) {
						WARRANT_TYPE = map.get("WARRANT_TYPE").toString();
					}
					row.createCell(cellIndex++).setCellValue(WARRANT_TYPE);
					/*** 权证级别*/
					String WARRANT_GRADE = "";
					if (map.get("WARRANT_GRADE") != null
							&& !"".equals(map.get("WARRANT_GRADE"))) {
						WARRANT_GRADE = map.get("WARRANT_GRADE").toString();
					}
					row.createCell(cellIndex++).setCellValue(WARRANT_GRADE);
					/***客户类型*/
					String CUSTOMER_TYPE = "";
					if (map.get("CUSTOMER_TYPE") != null
							&& !"".equals(map.get("CUSTOMER_TYPE"))) {
						CUSTOMER_TYPE = map.get("CUSTOMER_TYPE").toString();
					}
					row.createCell(cellIndex++).setCellValue(CUSTOMER_TYPE);
					/***婚姻情况*/
					String MARRIAGE_SITUATION = "";
					if (map.get("MARRIAGE_SITUATION") != null
							&& !"".equals(map.get("MARRIAGE_SITUATION"))) {
						MARRIAGE_SITUATION = map.get("MARRIAGE_SITUATION")
								.toString();
					}
					row.createCell(cellIndex++)
							.setCellValue(MARRIAGE_SITUATION);

				}

			}

			String fileName = "导出"
					+ new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar
							.getInstance().getTime());
			fileName = new String(fileName.getBytes(), "iso-8859-1");
			HttpServletResponse response = SkyEye.getResponse();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ fileName + ".xls");
			ouputStream = response.getOutputStream();
			wb.write(ouputStream);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (ouputStream != null) {
					ouputStream.flush();
					ouputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@aPermission(name = { "参数配置", "资料清单管理" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "zhaofeng", email = "zhaofeng1012@foxmail.com", name = "赵峰")
	public Reply toValidateName() {
		Map<String, Object> param = _getParameters();
		boolean flag = service.selectCountByName(param) > 0;
		return new ReplyAjax(flag, param).addOp(new OpLog("DataList进度管理", "校验",
				param.toString()));
	}
}
