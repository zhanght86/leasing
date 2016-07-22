package com.pqsoft.importAndExport.action;

import java.io.File;
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
import org.apache.velocity.VelocityContext;
import org.springframework.core.io.Resource;

import com.pqsoft.importAndExport.service.ImportAndExportService;
import com.pqsoft.payment.service.paymentService;
import com.pqsoft.skyeye.VM;
import com.pqsoft.skyeye.api.Action;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Reply;
import com.pqsoft.skyeye.api.ReplyAjaxPage;
import com.pqsoft.skyeye.api.ReplyHtml;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.dev.aDev;
import com.pqsoft.skyeye.rbac.api.aAuth;
import com.pqsoft.skyeye.rbac.api.aAuth.aAuthType;
import com.pqsoft.skyeye.rbac.api.aPermission;
import com.pqsoft.skyeye.util.UTIL;
import com.pqsoft.util.DateSiteUtil;

public class ImportAndExportAction extends Action  {
	private ImportAndExportService service = new ImportAndExportService();
	private String path = "importAndExport/";

	@aPermission(name = { "放款管理", "放款导出", "列表" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply execute() {
		VelocityContext context = new VelocityContext();
		return new ReplyHtml(VM.html(path+"importAndExport.vm", context));
	}
	
	@aPermission(name = { "放款管理", "放款导出", "列表" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply pageData(){
		Map<String,Object> param = _getParameters();
		return new ReplyAjaxPage(service.getPage(param));
	}
	
	@aPermission(name = { "放款管理", "放款导入", "列表" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply loanImport(){
		return new ReplyHtml(VM.html(path+"loanImport.vm", null));
	}
	
	@aPermission(name = { "放款管理", "放款导入", "列表" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply importData(){
		Map<String,Object> param = _getParameters();
		return new ReplyAjaxPage(service.importPage(param));
	}
	
	@aPermission(name = { "放款管理", "放款导出", "导出" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply exportExcel(){
		//总金额和总笔数
		List<Map<String,Object>> ZJE = service.getZJE();
		List<Map<String,Object>> dataList = service.getData(new HashMap<String,Object>());
		OutputStream ouputStream = null;
		try {
			Resource resource = UTIL.RES.getResource("classpath:/content/template/建元模版.xls");
//			Workbook wb = new HSSFWorkbook(new FileInputStream(new File(path)));
			Workbook wb = WorkbookFactory.create(resource.getInputStream());
			Sheet sheet = wb.getSheetAt(0);
			//循环遍历表sheet.getLastRowNum()是获取一个表最后一条记录的记录号，
			for(int j=0;j<4;j++) {
				Row row = sheet.getRow(j);//行对象
				for(int i=0;i<row.getLastCellNum();i++) { //行里面的各单元格

				Cell cell = row.getCell(i);//单元格
				System.out.println(cell.getRichStringCellValue());//印出来看看数据
				}
				System.out.println("=================================================");
			}
			//表头总金额和总笔数
			Map<String,Object> zjeMap = ZJE.get(0);
			Row row1 = sheet.getRow(1);
			Row row2 = sheet.getRow(2);
			String zje = "";
			if(zjeMap.get("ZJE")!=null&&!"".equals(zjeMap.get("ZJE"))){
				zje = zjeMap.get("ZJE").toString();
			}
			row1.createCell(1).setCellValue(zje);
			String zbs = "";
			if(zjeMap.get("ZBS")!=null&&!"".equals(zjeMap.get("ZBS"))){
				zbs = zjeMap.get("ZBS").toString();
			}
			row2.createCell(1).setCellValue(zbs);
			//主体数据
			for(int x=0;x<dataList.size();x++){
//				Row row  = sheet.getRow(x+4);
				Row row = sheet.createRow(x+4);
				
				Map<String,Object> map = new HashMap<String, Object>();
						
				map = dataList.get(x);
				
				if(map!=null){
					int cellIndex = 0 ;
					String ZDLX = "";
					if(map.get("ZDLX")!=null&&!"".equals(map.get("ZDLX"))){
						ZDLX = map.get("ZDLX").toString();
					}
					row.createCell(cellIndex++).setCellValue(ZDLX);
					String ID = "";
					if(map.get("ID")!=null &&!"".equals(map.get("ID"))){
						ID = map.get("ID").toString();
					}
					row.createCell(cellIndex++).setCellValue(ID);
					String NAME = "";
					if(map.get("NAME")!=null&&!"".equals(map.get("NAME"))){
						NAME = map.get("NAME").toString();
					}
					row.createCell(cellIndex++).setCellValue(NAME);
					String YLBZ = "";
					if(map.get("YLBZ")!=null&&!"".equals(map.get("YLBZ"))){
						YLBZ = map.get("YLBZ").toString();
					}
					row.createCell(cellIndex++).setCellValue(YLBZ);
					String REALITY_BANK_NAME = "";
					if(map.get("REALITY_BANK_NAME")!=null&&!"".equals(map.get("REALITY_BANK_NAME"))){
						REALITY_BANK_NAME = map.get("REALITY_BANK_NAME").toString();
					}
					row.createCell(cellIndex++).setCellValue(REALITY_BANK_NAME);
					String PAY_MONEY = "";
					if(map.get("PAY_MONEY")!=null&&!"".equals(map.get("PAY_MONEY"))){
						PAY_MONEY = map.get("PAY_MONEY").toString();
					}
					row.createCell(cellIndex++).setCellValue(PAY_MONEY);
					String PAY_BANK_ACCOUNT = "";
					if(map.get("PAY_BANK_ACCOUNT")!=null&&!"".equals(map.get("PAY_BANK_ACCOUNT"))){
						PAY_BANK_ACCOUNT = map.get("PAY_BANK_ACCOUNT").toString();
					}
					row.createCell(cellIndex++).setCellValue(PAY_BANK_ACCOUNT);
					String SUP_NAME = "";
					if(map.get("SUP_NAME")!=null&&!"".equals(map.get("SUP_NAME"))){
						SUP_NAME = map.get("SUP_NAME").toString();
					}
					row.createCell(cellIndex++).setCellValue(SUP_NAME);
					String ZHLX = "";
					if(map.get("ZHLX")!=null&&!"".equals(map.get("ZHLX"))){
						ZHLX = map.get("ZHLX").toString();
					}
					row.createCell(cellIndex++).setCellValue(ZHLX);
					String ZKHH = "";
					if(map.get("ZKHH")!=null&&!"".equals(map.get("ZKHH"))){
						ZKHH = map.get("ZKHH").toString();
					}
					row.createCell(cellIndex++).setCellValue(ZKHH);
					String ZFKZH = "";
					if(map.get("ZFKZH")!=null&&!"".equals(map.get("ZFKZH"))){
						ZFKZH = map.get("ZFKZH").toString();
					}
					row.createCell(cellIndex++).setCellValue(ZFKZH);
					String ZFKZHM = "";
					if(map.get("ZFKZHM")!=null&&!"".equals(map.get("ZFKZHM"))){
						ZFKZHM = map.get("ZFKZHM").toString();
					}
					row.createCell(cellIndex++).setCellValue(ZFKZHM);
					String ZFKKHH = "";
					if(map.get("ZFKKHH")!=null&&!"".equals(map.get("ZFKKHH"))){
						ZFKKHH = map.get("ZFKKHH").toString();
					}
					row.createCell(cellIndex++).setCellValue(ZFKKHH);
					String YT = "";
					if(map.get("YT")!=null&&!"".equals(map.get("YT"))){
						YT = map.get("YT").toString();
					}
					row.createCell(cellIndex++).setCellValue(YT);
					String HL = "";
					if(map.get("HL")!=null&&!"".equals(map.get("HL"))){
						HL = map.get("HL").toString();
					}
					row.createCell(cellIndex++).setCellValue(HL);
					String ISTZ = "";
					if(map.get("ISTZ")!=null&&!"".equals(map.get("ISTZ"))){
						ISTZ = map.get("ISTZ").toString();
					}
					row.createCell(cellIndex++).setCellValue(ISTZ);
					String PHONE = "";
					if(map.get("PHONE")!=null&&!"".equals(map.get("PHONE"))){
						PHONE = map.get("PHONE").toString();
					}
					row.createCell(cellIndex++).setCellValue(PHONE);
					String EMAIL = "";
					if(map.get("EMAIL")!=null&&!"".equals(map.get("EMAIL"))){
						EMAIL = map.get("EMAIL").toString();
					}
					row.createCell(cellIndex++).setCellValue(EMAIL);
					String ZFHHZFHM = "";
					if(map.get("ZFHHZFHM")!=null&&!"".equals(map.get("ZFHHZFHM"))){
						ZFHHZFHM = map.get("ZFHHZFHM").toString();
					}
					row.createCell(cellIndex++).setCellValue(ZFHHZFHM);
				}
				
				
			}
			
			String fileName = "放款导出"+new SimpleDateFormat("yyyyMMddHHmmss")
								.format(Calendar.getInstance().getTime());
			fileName = new String(fileName.getBytes(), "iso-8859-1");
			HttpServletResponse response = SkyEye.getResponse();
			response.setContentType("application/vnd.ms-excel");  
	        response.setHeader("Content-disposition", "attachment;filename="+fileName+".xls");  
	        ouputStream = response.getOutputStream();  
	        wb.write(ouputStream);  
	        
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(ouputStream!=null){
					ouputStream.flush();
					ouputStream.close();  
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@aPermission(name = { "放款管理", "放款导入", "导入" })
	@aAuth(type = aAuthType.USER)
	@aDev(code = "170026", email = "whlpqsoft@163.com", name = "王海龙")
	public Reply readExcel(){
		File file = _getFileOne();
//		File file = new File("\\JavaEE\\放款导出20150420111153.xls");
		
		Workbook wb;
		try {
//			wb = new HSSFWorkbook(new FileInputStream(file));
			wb = WorkbookFactory.create(file);
			Sheet sheet = wb.getSheetAt(0);
			//循环遍历表sheet.getLastRowNum()是获取一个表最后一条记录的记录号，
			for(int j=4;j<=sheet.getLastRowNum();j++) {
				Row row = sheet.getRow(j);//行对象
				
				Cell idCell = row.getCell(1);
				Cell statusCell = row.getCell(19);
				if(idCell!=null&&statusCell!=null&&idCell.getRichStringCellValue()!=null&&statusCell.getRichStringCellValue()!=null){
					String id = idCell.getRichStringCellValue().toString();
					String status = statusCell.getRichStringCellValue().toString();
					if("1".equals(status)){
						Map<String,Object> param = new HashMap<String,Object>();
						param.put("ID", id);
						param.put("BANKLEND_STATE", status);
						param.put("STATUS", 5);
						service.updateFund(param);
						
						Dao.update("payment.updatePayDetailStatus",param);//修改细表已核销状态
						//核销额度保证金
						Dao.update("payment.updateCreditFundByHeadid",param);
						Dao.update("payment.updateCreditAccountByHeadid",param);
						//设备放款判断是否有抵扣金额，有的话向租金池里放一条抵扣金额
						this.payMentPool(param);
						
						//后加-通过放款日期设定起租日
						//如果已经设定不再设定起租日
						List<Map<String,Object>> mapPayList=Dao.selectList("payment.queryPaymentStartDayByID",param);
						for(int ii=0;ii<mapPayList.size();ii++){
							Map<String,Object> mapPay=mapPayList.get(ii);
							if(mapPay !=null && (mapPay.get("START_DATE")==null || mapPay.get("START_DATE").equals(""))){
								paymentService paymentSer=new paymentService();
								try{
									//先查询该项目中金融产品配置的是否差额利息（SFCELX）,如果没有配置或者停用状态或者配置的为Y则计算否则不计算
									Map ISCELXMap=Dao.selectOne("PayTask.querySchemeSFCELXByPay",mapPay);
									if(ISCELXMap !=null && ISCELXMap.get("VALUE_STR") !=null && ISCELXMap.get("VALUE_STR").equals("3")){
										
										DateSiteUtil.setDateData(mapPay.get("PAY_ID").toString(), null,true);//参数PAY_ID,3固定为放款日期类型标示
										
									}else{
										
										//根据关键日期管理查询出还款日和起租日
										DateSiteUtil.setDateData(mapPay.get("PAY_ID").toString(), null,false);//参数PAY_ID,3固定为放款日期类型标示
										
										Map<String,Object> mapbase = Dao.selectOne("PayTask.getSchemeBaseInfoByProjectIdINT", mapPay);
										
										//查询起租日和还款日
										Map dataMap=Dao.selectOne("PayTask.queryPayDataByPayId", mapPay);
										
										if (mapbase != null) {
											mapbase.put("SCHEME_ID", mapbase.get("ID"));
											List<Map<String,Object>> clobList=Dao.selectList("leaseApplication.queryfil_scheme_clobForCs", mapbase);
											for (Map<String, Object> map2 : clobList) {
												mapbase.put(map2.get("KEY_NAME_EN")+"", map2.get("VALUE_STR"));
											}
											
											try{
												mapbase.put("AMOUNT", Dao.selectInt("PayTask.queryAmountCount", mapPay));
											}catch(Exception e){}
											
											
											mapbase.put("annualRate", mapbase.get("YEAR_INTEREST"));
											mapbase.put("_leaseTerm", mapbase.get("LEASE_TERM"));
											mapbase.put("residualPrincipal", mapbase.get("FINANCE_TOPRIC"));
											mapbase.put("_payCountOfYear", mapbase.get("PAYCOUNTOFYEAR"));
											mapbase.put("pay_way", mapbase.get("PAY_WAY"));
											
											mapbase.put("LXTQSQ", mapbase.get("LXTQSQ"));
											mapbase.put("DISCOUNT_MONEY", mapbase.get("DISCOUNT_MONEY"));
											mapbase.put("DISCOUNT_TYPE", mapbase.get("DISCOUNT_TYPE"));
											mapbase.put("CALCULATE_TYPE", mapbase.get("CALCULATE"));
											
											mapbase.put("START_DATE", dataMap.get("START_DATE"));
											mapbase.put("REPAYMENT_DATE", dataMap.get("REPAYMENT_DATE"));
											
											JSONObject page=null;
											Class<?> cla = Class.forName("com.pqsoft.pay.service.PayTaskService");
											page = (JSONObject) cla.getMethod("quoteCalculateTest", Map.class).invoke(cla.newInstance(), mapbase);
											List<Map<String, String>> irrList = (List<Map<String, String>>) page.get("ln");
											
											double ZJHJ = 0.00;//租金合计
											for(int hh=0;hh<irrList.size();hh++){
												//"zj":"11919.33","PMTzj":"12308.22","bj":"11141.55","lx":"777.78","sybj":"128858.45","qc":"1","PAY_DATE":"2015-09-15","glf":"0.0","sxf":"0","lxzzs":"0.00"
												Map mapIrr=irrList.get(hh);
												if(mapIrr !=null){
													mapIrr.put("PAY_ID", mapPay.get("PAY_ID"));
													
													ZJHJ = ZJHJ + Double.parseDouble((mapIrr.get("zj") !=null && mapIrr.get("zj") !="") ? mapIrr.get("zj")+"":"0");
													//更新租金
													mapIrr.put("ITEM_NAME", "租金");
													mapIrr.put("ITEM_MONEY", (mapIrr.get("zj") !=null && mapIrr.get("zj") !="") ? mapIrr.get("zj"):"0");
													Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
													//更新PMT租金
													mapIrr.put("ITEM_NAME", "PMT租金");
													mapIrr.put("ITEM_MONEY", (mapIrr.get("PMTzj") !=null && mapIrr.get("PMTzj") !="") ? mapIrr.get("PMTzj"):"0");
													Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
													//更新本金
													mapIrr.put("ITEM_NAME", "本金");
													mapIrr.put("ITEM_MONEY", (mapIrr.get("bj") !=null && mapIrr.get("bj") !="") ? mapIrr.get("bj"):"0");
													Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
													//更新利息
													mapIrr.put("ITEM_NAME", "利息");
													mapIrr.put("ITEM_MONEY", (mapIrr.get("lx") !=null && mapIrr.get("lx") !="") ? mapIrr.get("lx"):"0");
													Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
													//更新剩余本金
													mapIrr.put("ITEM_NAME", "剩余本金");
													mapIrr.put("ITEM_MONEY", (mapIrr.get("sybj") !=null && mapIrr.get("sybj") !="") ? mapIrr.get("sybj"):"0");
													Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
													//更新管理费
													mapIrr.put("ITEM_NAME", "管理费");
													mapIrr.put("ITEM_MONEY", (mapIrr.get("glf") !=null && mapIrr.get("glf") !="") ? mapIrr.get("glf"):"0");
													Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
													//更新手续费
													mapIrr.put("ITEM_NAME", "手续费");
													mapIrr.put("ITEM_MONEY", (mapIrr.get("sxf") !=null && mapIrr.get("sxf") !="") ? mapIrr.get("sxf"):"0");
													Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
													//更新利息增值税
													mapIrr.put("ITEM_NAME", "利息增值税");
													mapIrr.put("ITEM_MONEY", (mapIrr.get("lxzzs") !=null && mapIrr.get("lxzzs") !="") ? mapIrr.get("lxzzs"):"0");
													Dao.update("PayTask.updatePayDetailItemInfo",mapIrr);
												}
												
												
											}
											
											//更新还款计划主表租金合计
											Map pmap=new HashMap();
											pmap.put("ID", mapPay.get("PAY_ID"));
											pmap.put("ZJHJ", ZJHJ);
											Dao.update("PayTask.updatePayHeadMoneyAll",pmap);
											
											System.out.println("------------------------dataMap="+dataMap);
											//同步应收期初表数据
											// 同步财务数据
											Map<String, String> temp = new HashMap<String, String>();
											temp.put("PAY_ID", mapPay.get("PAY_ID")+"");
											temp.put("PAYLIST_CODE", dataMap.get("PAYLIST_CODE") + "");
											temp.put("PMT", "PMT租金");
											temp.put("ZJ", "租金");
											temp.put("SYBJ", "剩余本金");
											temp.put("D", "第");
											temp.put("QI", "期");
											// 删除财务表数据
											Dao.delete("PayTask.deleteBeginning", temp);
											Dao.insert("PayTask.synchronizationBeginning", temp);
											
											//同步中间表
											//刷单条逾期数据
											Dao.update("PayTask.doDunDateByPaylist_code",temp);
											Dao.update("PayTask.doPRC_BE_QJL_PAY_CODE",temp);
											
										}
									}
									
								}catch(Exception e){
									e.printStackTrace();
								}
								
							}
						}
					}
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return loanImport();
	}
	
	//设备放款判断是否有抵扣金额，有的话向租金池里放一条抵扣金额
	public void payMentPool(Map<String,Object> map){
		if(map!=null && map.get("ID")!=null && !map.get("ID").equals("")){
			Map<String,Object> mapDate=Dao.selectOne("payment.payMentPool", map);
			if(mapDate!=null){
				double money=Double.parseDouble(mapDate.get("DEDUCTION_MONEY").toString());
				if(money>0){
					Map<String,Object> mapPool=new HashMap<String,Object>();
					mapPool.put("OWNER_ID", mapDate.get("SUPPER_ID"));
					mapPool.put("BASE_MONEY", mapDate.get("DEDUCTION_MONEY"));
					mapPool.put("CANUSE_MONEY", mapDate.get("DEDUCTION_MONEY"));
					mapPool.put("STATUS", "1");
					mapPool.put("PAY_TIME", mapDate.get("REALITY_DATE"));
					mapPool.put("TYPE", 3);
					mapPool.put("SOURCE_ID", mapDate.get("ID"));
					Dao.insert("payment.insertPoolDate",mapPool);
				}
				
			}
			
		}
	}
}
