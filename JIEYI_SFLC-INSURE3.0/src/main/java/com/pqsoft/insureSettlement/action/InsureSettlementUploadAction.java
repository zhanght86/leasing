package com.pqsoft.insureSettlement.action;

/**
 *  保险理赔 款项上传
 *  @author hanxl
 *  涉及表 FI_INSUREPAID_FEE_UPLOAD
 */
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;

import org.springframework.util.FileCopyUtils;

import com.pqsoft.insureSettlement.service.InsureSettlementUploadService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjax;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyFile;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.util.UTIL;

public class InsureSettlementUploadAction extends Action {

	private String basePath = "insureSettlement/";
	private InsureSettlementUploadService service = new InsureSettlementUploadService();
	private int sucNo = 0;//成功条数
	private int faiNo = 0;//失败条数

	@Override
//	@aPermission(name = { "保险管理", "保险理赔", "款项上传主页面" })
//	@aAuth(type = aAuthType.USER)
//	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply execute() {
//		return new ReplyHtml(VM.html(
//				basePath + "insureSettlementUploadShow.vm", null));
		return null;
	}
	
	/**
	 * 用于分页查询
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply pageData(){
		Map<String,Object> param = _getParameters();
		Page pagedata = service.getPageData(param);
		return new ReplyAjaxPage(pagedata);
	}
	
	/**
	 *  导出模板
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongflc.com", name = "韩晓龙")
	public Reply downloadTemplate(){
		//return new ReplyFile(new File("D:\\insureTemplate\\insureSettlementTemplate.xls"),"insureSettlementTemplate.xls");
		try {
			return new ReplyFile(UTIL.RES.getResource("classpath:file/insureSettlementTemplate.xls").getInputStream(), "insureSettlementTemplate.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *  用于上传理赔来款
	 */
	@aAuth(type = aAuth.aAuthType.LOGIN)
	@aDev(code = "6196", email = "hanxl@strongsflc.com", name = "韩晓龙")
	public Reply uploadTemplate() {
		// 1.得到上传的文件
		File filetemp = _getFileOne();
		// 2.创建来款附件目录 创建insureSettlementFile目录用于存放上传的来款文件
		String filepath = "";
		filepath = "/insureSettlementFile";// 用于存放上传的来款文件
		File temp = null;
		temp = new File(filepath);
		if (!temp.exists()) {
			temp.mkdirs();
		}
		filepath += "/" + filetemp.getName();
		temp = new File(filepath);
		
		String msg = "";
		
		try {
			FileCopyUtils.copy(FileCopyUtils.copyToByteArray(filetemp), temp);
			msg = analysisFile(temp);//解析所传文件 得到成功和失败条目
		} catch (Exception e) {
			msg = "文件异常，请检查!";
		}
		// 4.删除上传的临时文件
		// filetemp.delete();
		return new ReplyAjax(true, msg);
	}

	/**
	 *  解析所传文件 并 形成款项条目
	 *  支持excel 03 格式
	 */
	private String analysisFile(File file) throws Exception {
		String filePath = file.getPath();//文件路径
		
		Workbook book = Workbook.getWorkbook(file);
		int line_no = 9;//因银行模板原因10行开始识别
		int cellNo = 0;//列号
		int flag = 0;//是否执行标志  flag = 0 则不执行插入操作, 大于0则执行
		int sucessNo = 0;//成功条数
		int failedNo = 0;//失败条数
		/**
		 *  从模板中获得的数据
		 */
		String account_date = "";//记账时间
		String trade_time = "";//交易时间
		String proof_type = "";//凭证种类
		String proof_code = "";//凭证号
		String money_lender = "";//发生额/元  贷方  （收到的金额）
		String opposite_account = "";//对方户名
		String opposite_accountno = "";//对方账号
		String summary = "";//摘要
		String remark = "";//备注
		String account_serial_num = "";//账户明细编号-交易流水号
		String enterprise_serial_num = "";//企业流水号
		///////////////////////////////////////
		//其余字段
		String creator = Security.getUser().getName();//创建者
		///////////////////////////////////////
		Map<String,Object> paramMap = new HashMap<String,Object>();
		///////////////////////////////////////
		for(Sheet sheet : book.getSheets()){
			line_no = 9;
			if(sheet!=null && sheet.getRows()>0){
					for(int j = line_no  ; j < sheet.getRows() ; j++){//因银行模板原因10行开始识别
						//先清空paramMap
						paramMap.clear();
						flag = 0;//标志置0
						cellNo = 0;//列号归零
						System.out.println("第=======" + j + "===========行");
						System.out.println("清空后的map=========" + paramMap.toString());
						//记账时间
						if(sheet.getCell(cellNo, j)!=null && !"".equals(sheet.getCell(cellNo, j).getContents().trim())){//没有则不执行
							flag++;
							account_date = sheet.getCell(cellNo, j).getContents().trim();
							paramMap.put("ACCOUNT_DATE", account_date);
						}else{
							failedNo++;//记录失败条数
							continue;
						}
						cellNo++;//移动一列
						//交易时间
						if(sheet.getCell(cellNo, j)!=null && !"".equals(sheet.getCell(cellNo, j).getContents().trim())){
							flag++;
							trade_time = sheet.getCell(cellNo, j).getContents().trim();
							paramMap.put("TRADE_TIME", trade_time);
						}else{
							//donothing
						}
						cellNo++;//移动一列
						//凭证种类
						if(sheet.getCell(cellNo, j)!=null && !"".equals(sheet.getCell(cellNo, j).getContents().trim())){
							flag++;
							proof_type = sheet.getCell(cellNo, j).getContents().trim();
							paramMap.put("PROOF_TYPE", proof_type);
						}else{
							//donothing
						}
						cellNo++;//移动一列
						//凭证号
						if(sheet.getCell(cellNo, j)!=null && !"".equals(sheet.getCell(cellNo, j).getContents().trim())){
							flag++;
							proof_code = sheet.getCell(cellNo, j).getContents().trim();
							paramMap.put("PROOF_CODE", proof_code);
						}else{
							//donothing
						}
						cellNo++;//移动一列
						cellNo++;//移动一列   //根据模板这里要移动两列
						//发生额/元  贷方  （收到的金额）
						if(sheet.getCell(cellNo, j)!=null && !"".equals(sheet.getCell(cellNo, j).getContents().trim())){
							flag++;
							money_lender = sheet.getCell(cellNo, j).getContents().trim();
							money_lender = money_lender.replaceAll(",", "");//去掉金额中的逗号
							paramMap.put("MONEY_LENDER", money_lender);
						}else{
							//donothing
						}
						cellNo++;//移动一列
						cellNo++;//移动一列
						cellNo++;//移动一列  //根据模板这里要移动三列
						//对方户名
						if(sheet.getCell(cellNo, j)!=null && !"".equals(sheet.getCell(cellNo, j).getContents().trim())){
							flag++;
							opposite_account = sheet.getCell(cellNo, j).getContents().trim();
							paramMap.put("OPPOSITE_ACCOUNT", opposite_account);
						}else{
							//donothing
						}
						cellNo++;//移动一列
						//对方账号
						if(sheet.getCell(cellNo, j)!=null && !"".equals(sheet.getCell(cellNo, j).getContents().trim())){
							flag++;
							opposite_accountno = sheet.getCell(cellNo, j).getContents().trim();
							paramMap.put("OPPOSITE_ACCOUNTNO", opposite_accountno);
						}else{
							//donothing
						}
						cellNo++;//移动一列
						//摘要
						if(sheet.getCell(cellNo, j)!=null && !"".equals(sheet.getCell(cellNo, j).getContents().trim())){
							flag++;
							summary = sheet.getCell(cellNo, j).getContents().trim();
							paramMap.put("SUMMARY", summary);
						}else{
							//donothing
						}
						cellNo++;//移动一列
						//备注
						if(sheet.getCell(cellNo, j)!=null && !"".equals(sheet.getCell(cellNo, j).getContents().trim())){
							flag++;
							remark = sheet.getCell(cellNo, j).getContents().trim();
							paramMap.put("REMARK", remark);
						}else{
							//donothing
						}
						cellNo++;//移动一列
						//账户明细编号-交易流水号
						if(sheet.getCell(cellNo, j)!=null && !"".equals(sheet.getCell(cellNo, j).getContents().trim())){
							flag++;
							account_serial_num = sheet.getCell(cellNo, j).getContents().trim();
							paramMap.put("ACCOUNT_SERIAL_NUM", account_serial_num);
						}else{
							//donothing
						}
						cellNo++;//移动一列
						//企业流水号
						if(sheet.getCell(cellNo, j)!=null && !"".equals(sheet.getCell(cellNo, j).getContents().trim())){
							flag++;
							enterprise_serial_num = sheet.getCell(cellNo, j).getContents().trim();
							paramMap.put("ENTERPRISE_SERIAL_NUM", enterprise_serial_num);
						}else{
							//donothing
						}
						/////////////数据插入//////////////
						System.out.println("flag==============" + flag);
						if(flag > 0){
							sucessNo++;//记录成功条数
							paramMap.put("CREATOR", creator);
							paramMap.put("FILE_PATH", filePath);
							System.out.println("准备数据：================" + paramMap.toString());
							//执行数据插入
							service.insertInsureSettlementFee(paramMap);
						}else{
							failedNo++;//记录失败条数
						}
					}
			}
		}
		return "上传结果：成功 " + sucessNo + "条，失败 " + failedNo + "条！";
	}
}
