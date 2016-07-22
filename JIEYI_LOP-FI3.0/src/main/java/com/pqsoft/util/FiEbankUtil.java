package com.pqsoft.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.rbac.api.Security;

/**
 * <p>
 * Title: 融资租赁信息系统平台 网银工具
 * </p>
 * <p>
 * Description: 民生、建行网银导出 解析
 * </p>
 * <p>
 * Company: 平强软件
 * </p>
 * 
 * @author 吴剑东 wujd@pqsoft.cn
 * @version 1.0
 */
public class FiEbankUtil {

    String shdm = "305440359609931";//商户代码

    DecimalFormat format = new DecimalFormat("#.00") ; 
    DecimalFormat formatZS = new DecimalFormat("#############") ; 
    String curr_date = DateUtil.getSysDate("yyyy-MM-dd");
    
    
    /**
     * 导出财务报表
     * 
     * @param params
     * @return
     * @author: 吴剑东
     * @date: 2013-9-24 上午10:44:14
     */
    public Excel exportFinanceData(Map<String, Object> params) {
        List<Map<String, Object>> dataList = (List<Map<String, Object>>) params.get("dataList");

        Excel excel = new Excel();
        excel.setAutoColumn(25);
        excel.addSheetName("sheet01");
        excel.addData(dataList);
        LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();

        // title.put("ROW_NUM", "编号");
        title.put("FGS", "子公司");
        title.put("MD", "门店");
        title.put("QYZT", "签约主体");
        title.put("LEASE_CODE", "合同编号");
        title.put("HTBB", "合同版本");
        title.put("NAME", "承租人");
        title.put("FIRSTDATE", "首次还款日");
        title.put("ENDDATE", "还款截止日");
        title.put("YZ", "月租金");
        title.put("BJ", "车辆本金");
//        title.put("GLF", "租车服务费");
//        title.put("LX", "交易服务费");
      title.put("LX", "租车服务费");
      title.put("SXF", "交易服务费");
      title.put("GLF","管理费");
        title.put("KKJE", "本次扣款金额");
        title.put("BANK_CUSTNAME", "开户人");
        title.put("BANK_ACCOUNT", "银行账号");
        title.put("BANK_NAME", "开户行");
        title.put("ID_CARD_NO", "身份证号");
        title.put("TEL_PHONE", "电话");
        title.put("RCEIVEDATE", "应还日期");
        title.put("QC", "应还期次");
        excel.addTitle(title);
        // excel.hasRownum("编号");
        excel.setHasTitle(true);
        return excel;
    }
    
    /**
     * 租金监控
     * 
     * @param params
     * @return
     * @author: 刘丽
     * @date: 2013-9-24 上午10:44:14
     */
    public Excel exportWhiteList(Map<String, Object> params) {
        List<Map<String, Object>> dataList = (List<Map<String, Object>>) params.get("dataList");

        Excel excel = new Excel();
        excel.setAutoColumn(25);
        excel.addSheetName("sheet01");
        excel.addData(dataList);
        LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
        // title.put("ROW_NUM", "编号");
        
        title.put("PLATFORM_TYPE", "机构ID");
        title.put("BANK_ACCOUNT", "账户号码");
        title.put("BANK_CUSTNAME", "账户名称");
        title.put("ZHLX", "账户类型");
        title.put("BANK_NAME", "银行名称");
        title.put("SFLX", "证件类型");
        title.put("ID_CARD_NO", "证件号码");
        title.put("TEL_PHONE", "手机号");
        title.put("MAILNAME", "邮箱");
        
        excel.addTitle(title);
        // excel.hasRownum("编号");
        excel.setHasTitle(true);
        return excel;
    }
    
    /**
     * 租金监控
     * 
     * @param params
     * @return
     * @author: 刘丽
     * @date: 2013-9-24 上午10:44:14
     */
    public Excel exportRentAccountMonitoring(Map<String, Object> params) {
        List<Map<String, Object>> dataList = (List<Map<String, Object>>) params.get("dataList");

        Excel excel = new Excel();
        excel.setAutoColumn(25);
        excel.addSheetName("sheet01");
        excel.addData(dataList);
        LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
        // title.put("ROW_NUM", "编号");
        title.put("LEASE_CODE", "合同号");
        title.put("CONTRACTSTATUS", "合同状态");
        title.put("NAME", "客户姓名");
        title.put("ID_CARD_NO", "身份证号");
        title.put("TEL_PHONE", "联系电话");
        title.put("CITYNAME", "城市");
        title.put("STORESNAME", "门店");
        title.put("SCHEME_NAME", "产品类型");
        title.put("CARNAME", "车辆名称");
        title.put("CAR_SYMBOL", "车架号");
        title.put("FINANCE_TOPRIC", "融资金额");
        title.put("FIRSTPAYALL", "首付金额");
        title.put("LEASE_TERM", "期限");
        title.put("MONTHRATE", "利率");
        title.put("START_PERCENT", "首付比例");
        title.put("DEPOSIT_MONEY", "保证金金额");
        title.put("BEGINNING_MONEY", "月租金额");
        title.put("PREPAYRENT", "预付租金");
        title.put("INTERESTREDUCTION", "利息减免");
        title.put("FIRSTMONEY", "还款金额");
        title.put("CROSSTOWNDATE", "交车日期");
        title.put("FIRSTDATE", "首次还款日");
        title.put("ENDDATE", "还款截止日");
        title.put("PAYMENTSDATE", "应还款日期");
        title.put("RECEIVEPAYMENTSMONEY", "当期应还");
        title.put("RETURNRENT", "已还租金");
        title.put("RESIDUALRENT", "剩余租金");
        title.put("CURRENTPRINCIPAL", "当期本金");
        title.put("CURRENTINTEREST", "当期利息");
        title.put("RESIDUALPRINCIPAL", "剩余本金");
        title.put("RESIDUALPERIOD", "剩余期数");
        title.put("ISYQ", "是否逾期");
        title.put("YQMONEY", "逾期金额");
        title.put("PENALTY_DAY", "逾期天数");
        title.put("PENALTY", "罚息");
        title.put("WYJ_REALITY_TIME", "实际还款时间");
        title.put("PERIOD", "当期逾期期数");
        title.put("YQQS", "累计逾期期数");
        title.put("REALITY_TIME", "提前结清时间");
        title.put("BEGINNING_PAID", "提前结清金额");
        title.put("FLAG", "业务类型");
        
        excel.addTitle(title);
        // excel.hasRownum("编号");
        excel.setHasTitle(true);
        return excel;
    }
    
    /**
     * 刘丽
     * @param params
     * @return
     */
    public Excel exportSuperTable(Map<String, Object> params) {
        List<Map<String, Object>> dataList = (List<Map<String, Object>>) params.get("dataList");

        Excel excel = new Excel();
        excel.setAutoColumn(25);
        excel.addSheetName("sheet01");
        excel.addData(dataList);
        LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();
        // title.put("ROW_NUM", "编号");
        title.put("HHZT","合同状态");
        title.put("JJSJ","进件时间");
        title.put("JJBH","进件编号");
        title.put("KHLY","客户来源");
        title.put("CPMC","产品名称");
//        title.put("ZGS","子公司");
        title.put("QYZT","签约主体");
        title.put("FB","分部");
        title.put("MD","门店");
        title.put("SQR","申请人");
        title.put("SFZH","身份证号");
        title.put("DH","电话");
        title.put("CPXH","厂牌型号");
        title.put("SCCJ","生产厂家");
        title.put("CK","款式");
        title.put("PL","排量");
        title.put("BSX","变速箱");
        title.put("CX","车型");
        title.put("ZWS","座位");
        title.put("YS","颜色");
        title.put("GZS","购置税");
        title.put("SP","上牌");
        title.put("HBBF","环保标志");
        title.put("LSPZ","临时牌照");
        title.put("GPSFY","GPS费用");
        title.put("LQF","路桥费");
        title.put("JQX","交强险");
        title.put("CCS","车船税");
        title.put("SFBL","首付比");
        title.put("QX","期限");
        title.put("BZJ","保证金");
        title.put("HTBH","合同编号");
        title.put("DBJ","打包价");
        title.put("BCEK","补差额款");
        title.put("SFK","首付款");
        title.put("RZJE","融资金额");
        title.put("YZJE","月租金额");
        title.put("YFZJ","预付租金");
        title.put("QYRQ","签约日期");
        title.put("YHKH","银行账号");
        title.put("KHH","开户行");
        title.put("HTBB","合同版本");
        title.put("HTSHRQ","合同审核日期");
        title.put("CJH","车架号");
        title.put("4SDMC","4S店名称");
        title.put("JCRQ","交车日期");
        title.put("YFQS","预付期数");
        title.put("SCHKR","首次还款日");
        title.put("HKJZR","还款截止日");
        title.put("SCHKJE","首次还款金额");
        title.put("QLL","期利率");
        title.put("WYJ","违约金");
        title.put("FJ","罚金");
        title.put("YQQS","逾期期数");
        title.put("YQTS","逾期天数");
        title.put("YQJE","逾期金额");
        title.put("TQJQSJ","提前结清时间");
        title.put("TQJQJE","提前结清金额");

        title.put("YWLX","业务类型");
        title.put("PPXH","品牌型号");
        title.put("SYX","商业险");
        title.put("SF","首付款");
        title.put("SHTGRQ","审核通过日期");
        title.put("CGJ","采购价");
        title.put("BJ","本金");
        title.put("ZCFWF","租车服务费");
        title.put("JYFWF","交易服务费");
  
        excel.addTitle(title);
        // excel.hasRownum("编号");
        excel.setHasTitle(true);
        return excel;
    }
	/**
	 * 网银导出根据类型获取流水号
	 * @param EXPORT_TYPE  导出类型  例：首付款网银
	 * @return no 流水号
	 * @author:  吴剑东
	 * @date:    2013-9-25 下午12:08:45
	 */
	public String getExportNo(String EXPORT_TYPE) {
		String no = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EXPORT_TYPE", EXPORT_TYPE);
		//导出日期 系统时间
		map.put("EXPORT_DATE", DateUtil.getSysDate("yyyy-MM-dd"));
		//流水号+1 并直接放入map中
		Dao.insert("fund.Ebank.insertExportNo", map);
		
		no = map.get("EXPORT_NO").toString();
		//不足两位补0
		if(no.length()==1){
			no="0000"+no;
		}else if(no.length()==2){
			no="000"+no;
		}else if(no.length()==3){
			no="00"+no;
		}else if(no.length()==4){
			no="0"+no;
		}else{
			no=no;
		}
		
		return no;
	}
	
	/**
	 * 网银导出根据类型获取流水号
	 * @param EXPORT_TYPE  导出类型  例：首付款网银
	 * @return no 流水号
	 * @author:  吴剑东
	 * @date:    2013-9-25 下午12:08:45
	 */
	public String getExportNoU(String EXPORT_TYPE) {
		String no = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EXPORT_TYPE", EXPORT_TYPE);
		//导出日期 系统时间
		map.put("EXPORT_DATE", DateUtil.getSysDate("yyyy-MM-dd"));
		//流水号+1 并直接放入map中
		Map mapNo=Dao.selectOne("fund.Ebank.queryExportNoU", map);
		
		
		no = mapNo.get("NO").toString();
		//不足两位补0
		if(no.length()==1){
			no="0"+no;
		}
		return no;
	}

	
	/**
	 * 解析民生银行网银扣划回执
	 * @param txtFile 回执文件
	 * @return list(map) map{bank_flag:回执成功 失败标志 1:成功  2:失败  ,  
	 * deducted_date:扣划日期  ,  bank_remark:回执成功,失败注释  ,  deducted_id:导出备注中扣划ID}
	 * @throws Exception 抛出异常或提示信息
	 * @author:  吴剑东
	 * @date:    2013-9-26 下午02:27:59
	 */
	public List<Map<String,Object>> parseMSEbank(File txtFile) throws Exception{

		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String deducted_date="";//扣划日期
		String bank_flag="";//回执成功,失败标志 1:成功  2:失败
		String bank_remark="";//回执成功,失败注释
		String deducted_id="";//导出备注中扣划ID
		String deducted_money="";//扣划金额
		
		//解析TXT文件
		List<String> contentList = new ArrayList<String>();
		//读取
		BufferedReader br = null;
		String titleStr = "";
		try {
			//解决中文乱码
			InputStreamReader read = new InputStreamReader(new FileInputStream(txtFile), "GBK");
			br=new BufferedReader(read);
			//第一行文件头
			titleStr = br.readLine();
			System.out.println("第一行文件头:"+titleStr);
			//文件体一条条内容
			String contentStr = ""; 
			//循环读取行数据并写入list
			while((contentStr=br.readLine())!=null) {
				contentList.add(contentStr);
			}
		}catch(Exception e){
		   e.printStackTrace();
		   throw new Exception("回执文件解析失败，请确认文件格式!");
		}finally{//关闭流
		   try {
				br.close();
		   } catch (IOException e) {
				e.printStackTrace();
		   }
		}
		//判断文件是否有数据
		if(contentList.size()< 1){
			throw new Exception("回执文件中没有需要核销数据的!");
		}
		String contentStr = "";
		//循环操作
		for(int i=0;i<contentList.size();i++){
			Map<String,Object> map = new HashMap<String, Object>();
			contentStr = contentList.get(i)+"";
			if( contentStr!=null && !"".equals(contentStr.trim()) ){
				//根据 "|" 分割     [3] 扣款日期    [13] 导出时备注信息    [17] 状态  1:成功  2:失败   [19] 状态说明 
				String[] strSegment = contentStr.trim().split("\\|");
				//过滤文件头
				if(strSegment.length<10){
					continue;
				}
				//备注支付表号  为空跳过
				String remarkStr=strSegment[13];
				if(remarkStr.indexOf(":")==-1){
					continue;
				}
				//扣划ID
				deducted_id = remarkStr.substring(remarkStr.indexOf(":")+1);
				bank_flag = strSegment[17];//成功标志
				bank_remark = strSegment[19];//成功标志说明
				deducted_date = strSegment[3];//扣划日期
				deducted_money = strSegment[8];//扣划金额
//				System.out.println(deducted_date+"---"+deducted_id+"---"+bank_flag+"---"+bank_remark);
				map.put("deducted_id", deducted_id);
				map.put("bank_flag", bank_flag);
				map.put("bank_remark", bank_remark);
				map.put("deducted_date", deducted_date);
				map.put("deducted_money", deducted_money);
				list.add(map);
			}
		}
		return list;
	
	}
	
	/**
	 * 解析建设银行网银扣划回执
	 * @param txtFile 回执文件
	 * @return list(map) map{bank_flag:回执成功失败标志  1:成功   2:失败  ,  
	 * deducted_money:扣划金额  ,  bank_remark:回执成功,失败注释  ,  deducted_id:导出备注1   ,  deducted_code:导出备注2}
	 * @throws Exception 抛出异常或提示信息
	 * @author:  吴剑东
	 * @date:    2013-9-26 下午02:27:59
	 */
	public List<Map<String,Object>> parseJSEbank(File txtFile) throws Exception {
		try {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

			String deducted_money="";//扣划金额
			String bank_flag="";//回执成功,失败标志 1:成功  2:失败
			String bank_remark="";//回执成功,失败注释
			String deducted_id="";//导出的备注
			String deducted_code="";//导出的备注
			
			InputStream is = new FileInputStream(txtFile);
			jxl.Workbook rwb = Workbook.getWorkbook(is);
			Sheet rs = (Sheet) rwb.getSheet(0);
			int rowNum = rs.getRows();// 得到所有行数
			for (int i = 4; i < rowNum; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				Cell c3 = (Cell) rs.getCell(3, i);
				deducted_money = ((jxl.Cell) c3).getContents();
				Cell c5 = (Cell) rs.getCell(5, i);
				bank_flag = ((jxl.Cell) c5).getContents();
				Cell c6 = (Cell) rs.getCell(6, i);
				bank_remark = ((jxl.Cell) c6).getContents();
				Cell c7 = (Cell) rs.getCell(7, i);
				deducted_id = ((jxl.Cell) c7).getContents();
				Cell c8 = (Cell) rs.getCell(8, i);
				deducted_code = ((jxl.Cell) c8).getContents();
				
				if("".equals(deducted_id) || deducted_id == ""){
					continue;
				}
				map.put("deducted_id", deducted_id);
				map.put("deducted_code", deducted_code);
				if("成功".equals(bank_flag)){
					bank_flag = "1";
				}else{
					bank_flag = "2";
				}
				map.put("bank_flag", bank_flag);
				map.put("bank_remark", bank_remark);
				map.put("deducted_money", deducted_money);

				list.add(map);
			}
			rwb.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("回执文件解析失败，请确认文件格式!");
		}
	}
	
	
	/**
	 * 导出民生银行网银扣划文件
	 * @param response 响应
	 * @param map {dataList:导出数据   ,  EXPORT_TYPE:导出类型  , FILE_HEAD:文件名标识}
	 * @author:  吴剑东
	 * @date:    2013-9-26 下午03:26:10
	 */
	public void exportMSEbank(HttpServletResponse response,Map<String, Object> map) {
		
		BufferedOutputStream buff = null;
		ServletOutputStream outSTr = null;
		StringBuffer write = new StringBuffer();
		List<Map<String,Object>> dataList = (List<Map<String, Object>>) map.get("dataList");
		try {
			//导出类型
			String EXPORT_TYPE = map.get("EXPORT_TYPE")+"";
//			//流水号
//			String no = getExportNo(EXPORT_TYPE);
			String no = getExportNo(EXPORT_TYPE);
			String fileName = map.get("FILE_HEAD")+shdm+"_"+curr_date.replaceAll("-","").substring(2)+"_"+no+".txt";
			//设置下载信息
			response.reset();
			response.setContentType("text/plain;charset=GBK");
			response.setHeader("Content-Disposition", 
					"attachment; filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ));
			outSTr = response.getOutputStream(); 
			buff = new BufferedOutputStream(outSTr);
			
			//拼写txt内容
			getMSEbankContent(dataList,write,no,EXPORT_TYPE);
			
			buff.write(write.toString().getBytes("GBK"));
			//关闭流
			buff.flush();
			buff.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buff.close();
				outSTr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	/**
	 * 导出民生银行网银扣划文件
	 * @param response 响应
	 * @param map {dataList:导出数据   ,  EXPORT_TYPE:导出类型  , FILE_HEAD:文件名标识}
	 * @author:  吴剑东
	 * @date:    2013-9-26 下午03:26:10
	 */
	public void exportMSEbankJY(HttpServletResponse response,Map<String, Object> map) {
		
		BufferedOutputStream buff = null;
		ServletOutputStream outSTr = null;
		StringBuffer write = new StringBuffer();
		List<Map<String,Object>> dataList = (List<Map<String, Object>>) map.get("dataList");
		try {
			
			String fileName = map.get("FILE_NAME").toString();
			//设置下载信息
			response.reset();
			response.setContentType("text/plain;charset=GBK");
			response.setHeader("Content-Disposition", 
					"attachment; filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ));
			outSTr = response.getOutputStream(); 
			buff = new BufferedOutputStream(outSTr);
			
			//拼写txt内容
			getMSEbankContentJY(dataList,write);
			
			buff.write(write.toString().getBytes("GBK"));
			//关闭流
			buff.flush();
			buff.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buff.close();
				outSTr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	/**
	 * 导出建设银行txt网银扣划文件
	 * @param response 响应
	 * @param map {dataList:导出数据   ,  EXPORT_TYPE:导出类型  , FILE_HEAD:文件名标识}
	 * @author:  吴剑东
	 * @date:    2013-9-26 下午03:26:10
	 */
	public void exportJSYHTXTEbank(HttpServletResponse response,Map<String, Object> map) {
		
		BufferedOutputStream buff = null;
		ServletOutputStream outSTr = null;
		StringBuffer write = new StringBuffer();
		List<Map<String,Object>> dataList = (List<Map<String, Object>>) map.get("dataList");
		try {
			//导出类型
			String EXPORT_TYPE = map.get("EXPORT_TYPE")+"";
//			//流水号
//			String no = getExportNo(EXPORT_TYPE);
			String no = getExportNo(EXPORT_TYPE);
			String fileName = map.get("FILE_HEAD")+"_"+curr_date.replaceAll("-","").substring(2)+"_"+no+".txt";
			//设置下载信息
			response.reset();
			response.setContentType("text/plain;charset=GBK");
			response.setHeader("Content-Disposition", 
					"attachment; filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ));
			outSTr = response.getOutputStream(); 
			buff = new BufferedOutputStream(outSTr);
			
			//拼写txt内容
			getJSYHTXTEbankContent(dataList,write,no,EXPORT_TYPE);
			
			buff.write(write.toString().getBytes("GBK"));
			//关闭流
			buff.flush();
			buff.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buff.close();
				outSTr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	
	/**
	 * 根据数据List生成字符串用于转换输出流
	 * @param dataList 数据
	 * @param write 字符串
	 * @param no 批次
	 * @param EXPORT_TYPE 导出类型
	 * @author:  吴剑东
	 * @date:    2013-9-25 下午03:22:06
	 */
	public void getMSEbankContent(List dataList,StringBuffer write,String no,String EXPORT_TYPE){
		try {
			String printAll="";
			String enter = "\r\n";
			
		    String bodyContent = "";
		    double moneyAll=0d;
		    //===文件体===
			String money = "0";//单笔金额
			String khyh = "";//开户银行
			String kh = "";//卡号
			String khxm = "";//姓名
			String proId = "";//主键标志字段
			String sfz = "";//身份证
			String mxxh = "";//明细序号
			String PRO_CODE="";//项目编号
			
			String noNum=getExportNoU(EXPORT_TYPE);
			int counProjNo =Integer.parseInt(noNum);
		    for (int i = 0; i < dataList.size(); i++) {
		    	counProjNo=counProjNo+1;
		    	Map<String,Object> dataMap = (Map<String,Object>)dataList.get(i);
		    	//8位数编号  不足8位前补0
		    	String projNo=counProjNo+"";
		    	while(projNo.length()<8){
		    		projNo="0"+projNo;
				}
		    	mxxh = ""+shdm.substring(3,7)+shdm.substring(11,shdm.length())+projNo;
		    	moneyAll=moneyAll+Double.parseDouble(dataMap.get("MONEY")== null?"0":dataMap.get("MONEY").toString());
		    	money = format.format(Double.parseDouble(dataMap.get("MONEY")== null?"0":dataMap.get("MONEY").toString()));
		    	khyh = dataMap.get("BANK_NAME")+"";
		    	kh = dataMap.get("BANK_ACCOUNT")+"";
		    	khxm = dataMap.get("BANK_CUSTNAME")+"";
		    	proId = dataMap.get("PROJ_ID")+"";
		    	sfz = dataMap.get("ID_CARD_NO")+"";
		    	if(dataMap!=null && dataMap.get("PAYLIST_CODE")!=null && !dataMap.get("PAYLIST_CODE").equals("")){
		    		PRO_CODE=dataMap.get("PAYLIST_CODE").toString();
		    	}
		    	bodyContent = bodyContent+mxxh+"|156|"+money+"||"+khyh+"|"+kh+"|"+khxm+"|"+EXPORT_TYPE+":"+proId+"|01|"+sfz+"|"+curr_date.replaceAll("-","").substring(2)+"||"+PRO_CODE+"|"+enter;
			    
		    }
		    getExportNo1(EXPORT_TYPE,counProjNo);
		    
		  //===文件头===
			//商户代码|批次|0||日期|总金额|总笔数||
			String titleStr = shdm+"|"+no+"|0||"+curr_date.replaceAll("-","")+"|"+format.format(moneyAll)+"|"+dataList.size()+"||"+enter;
			write.append(titleStr);
		    write.append(bodyContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据数据List生成字符串用于转换输出流
	 * @param dataList 数据
	 * @param write 字符串
	 * @param no 批次
	 * @param EXPORT_TYPE 导出类型
	 * @author:  吴剑东
	 * @date:    2013-9-25 下午03:22:06
	 */
	public void getMSEbankContentJY(List dataList,StringBuffer write){
		try {
			String printAll="";
			String enter = "\r\n";
			
		    String bodyContent = "";
		    double moneyAll=0d;
		    
		    
		    
			
		    //===文件体===
			String USER_CODE="";//用户编号
			String BANK_CODE="";//银行代码*
			String ACCOUNT_TYPE="";//账号类型
			String BANK_ACCOUNT="";//账号*
			String BANK_CUSTNAME="";//户名*
			String PROVINCE="";//省
			String CITY="";//市
			String BANK_NAME="";//开户行名称
			String BANK_ACCOUNT_TYPE="";//账户类型
			String MONEY="0";//金额*
			String CURRENCY_TYPE="";//货币类型
			String XY_CODE="";//协议号
			String XY_USER_CODE="";//协议用户编号
			String CUST_TYPE="";//开户证件类型
			String ID_CARD_NO="";//证件号
			String CUST_PHONE="";//手机号/小灵通
			String PROJ_ID="";//自定义用户号
			String PAYLIST_CODE="";//备注
			String BACK_CODE="";//返回码
			String BACK_REMARK="";//返回原因
			
		    for (int i = 0; i < dataList.size(); i++) {
		    	Map<String,Object> dataMap = (Map<String,Object>)dataList.get(i);
		    	
		    	int number=i+1;
		    	USER_CODE=(dataMap.get("USER_CODE") !=null && !dataMap.get("USER_CODE").equals("")) ? dataMap.get("USER_CODE")+"" : "";
		    	BANK_CODE=(dataMap.get("BANK_CODE") !=null && !dataMap.get("BANK_CODE").equals("")) ? dataMap.get("BANK_CODE")+"" : "";
				ACCOUNT_TYPE=(dataMap.get("ACCOUNT_TYPE") !=null && !dataMap.get("ACCOUNT_TYPE").equals("")) ? dataMap.get("ACCOUNT_TYPE")+"" : "";
				BANK_ACCOUNT=(dataMap.get("BANK_ACCOUNT") !=null && !dataMap.get("BANK_ACCOUNT").equals("")) ? dataMap.get("BANK_ACCOUNT")+"" : "";
				BANK_CUSTNAME=(dataMap.get("BANK_CUSTNAME") !=null && !dataMap.get("BANK_CUSTNAME").equals("")) ? dataMap.get("BANK_CUSTNAME")+"" : "";
				PROVINCE=(dataMap.get("PROVINCE") !=null && !dataMap.get("PROVINCE").equals("")) ? dataMap.get("PROVINCE")+"" : "";
				CITY=(dataMap.get("CITY") !=null && !dataMap.get("CITY").equals("")) ? dataMap.get("CITY")+"" : "";
				BANK_NAME=(dataMap.get("BANK_NAME") !=null && !dataMap.get("BANK_NAME").equals("")) ? dataMap.get("BANK_NAME")+"" : "";
				BANK_ACCOUNT_TYPE=(dataMap.get("BANK_ACCOUNT_TYPE") !=null && !dataMap.get("BANK_ACCOUNT_TYPE").equals("")) ? dataMap.get("BANK_ACCOUNT_TYPE")+"" : "";
				
				moneyAll=moneyAll+Double.parseDouble(dataMap.get("MONEY")== null?"0":dataMap.get("MONEY").toString());
				MONEY=formatZS.format(Double.parseDouble(dataMap.get("MONEY")== null?"0":dataMap.get("MONEY").toString()));
				
				CURRENCY_TYPE=(dataMap.get("CURRENCY_TYPE") !=null && !dataMap.get("CURRENCY_TYPE").equals("")) ? dataMap.get("CURRENCY_TYPE")+"" : "";
				XY_CODE=(dataMap.get("XY_CODE") !=null && !dataMap.get("XY_CODE").equals("")) ? dataMap.get("XY_CODE")+"" : "";
				XY_USER_CODE=(dataMap.get("XY_USER_CODE") !=null && !dataMap.get("XY_USER_CODE").equals("")) ? dataMap.get("XY_USER_CODE")+"" : "";
				CUST_TYPE=(dataMap.get("CUST_TYPE") !=null && !dataMap.get("CUST_TYPE").equals("")) ? dataMap.get("CUST_TYPE")+"" : "";
				ID_CARD_NO=(dataMap.get("ID_CARD_NO") !=null && !dataMap.get("ID_CARD_NO").equals("")) ? dataMap.get("ID_CARD_NO")+"" : "";
				CUST_PHONE=(dataMap.get("CUST_PHONE") !=null && !dataMap.get("CUST_PHONE").equals("")) ? dataMap.get("CUST_PHONE")+"" : "";
				PROJ_ID=(dataMap.get("PROJ_ID") !=null && !dataMap.get("PROJ_ID").equals("")) ? dataMap.get("PROJ_ID")+"" : "";
				PAYLIST_CODE=(dataMap.get("PAYLIST_CODE") !=null && !dataMap.get("PAYLIST_CODE").equals("")) ? dataMap.get("PAYLIST_CODE")+"" : "";
				BACK_CODE=(dataMap.get("BACK_CODE") !=null && !dataMap.get("BACK_CODE").equals("")) ? dataMap.get("BACK_CODE")+"" : "";
				BACK_REMARK=(dataMap.get("BACK_REMARK") !=null && !dataMap.get("BACK_REMARK").equals("")) ? dataMap.get("BACK_REMARK")+"" : "";
				
				bodyContent=bodyContent+number+","+USER_CODE+","+BANK_CODE+","+ACCOUNT_TYPE+","+BANK_ACCOUNT+","+BANK_CUSTNAME+","+PROVINCE+","+CITY+","+BANK_NAME+","+BANK_ACCOUNT_TYPE+","+MONEY+","+CURRENCY_TYPE+","+XY_CODE+","+XY_USER_CODE+","+CUST_TYPE+","+ID_CARD_NO+","+CUST_PHONE+","+PROJ_ID+","+PAYLIST_CODE+","+BACK_CODE+","+BACK_REMARK+enter;
			    
		    }
		    
		  //===文件头===
			//收款标志,商户ID,提交日期,总记录数,总金额,业务类型
		    String SHID=(String)Dao.selectOne("rentWriteNew.queryTlSHBH");
			String titleStr = "S,"+SHID+","+curr_date.replaceAll("-","")+","+dataList.size()+","+formatZS.format(moneyAll)+",10600"+enter;
			write.append(titleStr);
		    write.append(bodyContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据数据List生成字符串用于转换输出流(租金网银-中金支付)
	 * @param dataList 数据
	 * @param write 字符串
	 * @param no 批次
	 * @param EXPORT_TYPE 导出类型
	 * @author:  弋攀
	 * @date:    2016年1月15日 13:13:31
	 */
	public void getPayContent(List dataList,StringBuffer write){
		try {
			String printAll="";
			String enter = "\r\n";
			
		    String bodyContent = "";
		    double moneyAll=0d;
			
		    //===文件体===
		    // 流水号
		    String MONEY="0";//金额*
		    String BANK_CODE="";//银行代码*
		    String BANK_ACCOUNT_TYPE="";//账户类型
		    String BANK_CUSTNAME="";//户名*
		    String BANK_ACCOUNT="";//账号*
		    String BANK_NAME="";//开户行名称
		    String PROVINCE="";//省
		    String CITY="";//市
		    String PAYLIST_CODE="";//备注
		    String CUST_TYPE="";//开户证件类型
		    String ID_CARD_NO="";//证件号
		    String CUST_PHONE="";//手机号/小灵通
		    // 电子邮箱
		    String XY_USER_CODE="";//协议用户编号
		    // 结算标识
		    
		    
			//String USER_CODE="";//用户编号
			//String ACCOUNT_TYPE="";//账号类型
			//String CURRENCY_TYPE="";//货币类型
			//String XY_CODE="";//协议号
			//String PROJ_ID="";//自定义用户号
			//String BACK_CODE="";//返回码
			//String BACK_REMARK="";//返回原因
			
		    for (int i = 0; i < dataList.size(); i++) {
		    	Map<String,Object> dataMap = (Map<String,Object>)dataList.get(i);
		    	
		    	int number=i+1;
		    	//USER_CODE=(dataMap.get("USER_CODE") !=null && !dataMap.get("USER_CODE").equals("")) ? dataMap.get("USER_CODE")+"" : "";
		    	BANK_CODE=(dataMap.get("BANK_CODE") !=null && !dataMap.get("BANK_CODE").equals("")) ? dataMap.get("BANK_CODE")+"" : "";
				//ACCOUNT_TYPE=(dataMap.get("ACCOUNT_TYPE") !=null && !dataMap.get("ACCOUNT_TYPE").equals("")) ? dataMap.get("ACCOUNT_TYPE")+"" : "";
				BANK_ACCOUNT=(dataMap.get("BANK_ACCOUNT") !=null && !dataMap.get("BANK_ACCOUNT").equals("")) ? dataMap.get("BANK_ACCOUNT")+"" : "";
				BANK_CUSTNAME=(dataMap.get("BANK_CUSTNAME") !=null && !dataMap.get("BANK_CUSTNAME").equals("")) ? dataMap.get("BANK_CUSTNAME")+"" : "";
				PROVINCE=(dataMap.get("PROVINCE") !=null && !dataMap.get("PROVINCE").equals("")) ? dataMap.get("PROVINCE")+"" : "";
				//CITY=(dataMap.get("CITY") !=null && !dataMap.get("CITY").equals("")) ? dataMap.get("CITY")+"" : "";
				BANK_NAME=(dataMap.get("BANK_NAME") !=null && !dataMap.get("BANK_NAME").equals("")) ? dataMap.get("BANK_NAME")+"" : "";
				BANK_ACCOUNT_TYPE=(dataMap.get("BANK_ACCOUNT_TYPE") !=null && !dataMap.get("BANK_ACCOUNT_TYPE").equals("")) ? dataMap.get("BANK_ACCOUNT_TYPE")+"" : "";
				
				moneyAll=moneyAll+Double.parseDouble(dataMap.get("MONEY")== null?"0":dataMap.get("MONEY").toString());
				MONEY=formatZS.format(Double.parseDouble(dataMap.get("MONEY")== null?"0":dataMap.get("MONEY").toString()));
				
				//CURRENCY_TYPE=(dataMap.get("CURRENCY_TYPE") !=null && !dataMap.get("CURRENCY_TYPE").equals("")) ? dataMap.get("CURRENCY_TYPE")+"" : "";
				//XY_CODE=(dataMap.get("XY_CODE") !=null && !dataMap.get("XY_CODE").equals("")) ? dataMap.get("XY_CODE")+"" : "";
				XY_USER_CODE=(dataMap.get("XY_USER_CODE") !=null && !dataMap.get("XY_USER_CODE").equals("")) ? dataMap.get("XY_USER_CODE")+"" : "";
				CUST_TYPE=(dataMap.get("CUST_TYPE") !=null && !dataMap.get("CUST_TYPE").equals("")) ? dataMap.get("CUST_TYPE")+"" : "";
				ID_CARD_NO=(dataMap.get("ID_CARD_NO") !=null && !dataMap.get("ID_CARD_NO").equals("")) ? dataMap.get("ID_CARD_NO")+"" : "";
				CUST_PHONE=(dataMap.get("CUST_PHONE") !=null && !dataMap.get("CUST_PHONE").equals("")) ? dataMap.get("CUST_PHONE")+"" : "";
				//PROJ_ID=(dataMap.get("PROJ_ID") !=null && !dataMap.get("PROJ_ID").equals("")) ? dataMap.get("PROJ_ID")+"" : "";
				PAYLIST_CODE=(dataMap.get("PAYLIST_CODE") !=null && !dataMap.get("PAYLIST_CODE").equals("")) ? dataMap.get("PAYLIST_CODE")+"" : "";
				//BACK_CODE=(dataMap.get("BACK_CODE") !=null && !dataMap.get("BACK_CODE").equals("")) ? dataMap.get("BACK_CODE")+"" : "";
				//BACK_REMARK=(dataMap.get("BACK_REMARK") !=null && !dataMap.get("BACK_REMARK").equals("")) ? dataMap.get("BACK_REMARK")+"" : "";
				
				if("1".equals(BANK_ACCOUNT_TYPE)){
					BANK_ACCOUNT_TYPE = "11";
				}else{
					BANK_ACCOUNT_TYPE = "";
				}
				
				//bodyContent=bodyContent+number+","+USER_CODE+","+BANK_CODE+","+ACCOUNT_TYPE+","+BANK_ACCOUNT+","+BANK_CUSTNAME+","+PROVINCE+","+CITY+","+BANK_NAME+","+BANK_ACCOUNT_TYPE+","+MONEY+","+CURRENCY_TYPE+","+XY_CODE+","+XY_USER_CODE+","+CUST_TYPE+","+ID_CARD_NO+","+CUST_PHONE+","+PROJ_ID+","+PAYLIST_CODE+","+BACK_CODE+","+BACK_REMARK+enter;
				bodyContent=bodyContent + 
						number+"|"+						// 流水号
						MONEY+"|"+						// 金额*
					    BANK_CODE+"|"+					// 银行代码*
					    BANK_ACCOUNT_TYPE+"|"+			// 账户类型
					    BANK_CUSTNAME+"|"+				// 户名*
					    BANK_ACCOUNT+"|"+				// 账号*
					    ""+"|"+							// 开户行名称
					    ""+"|"+							// 省
					    ""+"|"+							// 市
					    ""+"|"+							// 备注
					    ""+"|"+							// 开户证件类型
					    ID_CARD_NO+"|"+					// 证件号
					    ""+"|"+							// 手机号/小灵通;
					    ""+"|"+							// 电子邮箱
					    ""+"|"+							// 协议用户编号
					    "0002"+enter;							// 结算标识
		    }
		    
		    //===文件头===
			//流水号,商户ID,提交日期,总记录数,总金额,业务类型
			String titleStr = "流水号"+"|"+"金额"+"|"+"行号"+"|"+"账户类型"+"|"+"账户名称"+"|"+"账户号码"+"|"+
					"分支行"+"|"+"省份"+"|"+"城市"+"|"+"证件类型"+"|"+"证件号码"+"|"+"手机号"+"|"+"电子邮箱"+"|"+"协议用户编号"+"|"+"结算标识" + enter;
			write.append(titleStr);
		    write.append(bodyContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 网银导出根据类型获取流水号
	 * @param EXPORT_TYPE  导出类型  例：首付款网银
	 * @return no 流水号
	 * @author:  吴剑东
	 * @date:    2013-9-25 下午12:08:45
	 */
	public void getExportNo1(String EXPORT_TYPE,int count) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EXPORT_TYPE", EXPORT_TYPE);
		//导出日期 系统时间
		map.put("EXPORT_DATE", DateUtil.getSysDate("yyyy-MM-dd"));
		map.put("EXPORT_NO", count);
		//流水号+1 并直接放入map中
		Dao.insert("fund.Ebank.insertExportNo1", map);
		Dao.commit();
	}
	
	
	
	/**
	 * 导出
	 * @param params
	 * @return
	 * @author:  吴剑东
	 * @date:    2013-9-24 上午10:44:14
	 */
	public Excel exportData(Map<String, Object> params) {
		List<Map<String,Object>> dataList = (List<Map<String, Object>>) params.get("dataList");
		
		Excel excel = new Excel();
		excel.setAutoColumn(25);
		excel.addSheetName("sheet01");
		excel.addData(dataList);
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  

//		title.put("ROW_NUM", "编号");
		title.put("BANK_ACCOUNT", "银行卡号");
		title.put("BANK_CUSTNAME", "持卡人");
		title.put("MONEY", "金额");
		title.put("PROJ_ID", "备注");
		title.put("PAYLIST_CODE", "备注1");
		excel.addTitle(title);
		excel.hasRownum("编号");
		excel.setHasTitle(false);
		return excel;
	}
	
	
	/**
	 * 导出
	 * @param params
	 * @return
	 * @author:  吴剑东
	 * @date:    2013-9-24 上午10:44:14
	 */
	public Excel exportDataGD(Map<String, Object> params) {
		List<Map<String,Object>> dataList = (List<Map<String, Object>>) params.get("dataList");
		
		Excel excel = new Excel();
		excel.setAutoColumn(25);
		excel.addSheetName("sheet01");
		excel.addData(dataList);
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  

		title.put("BANK_CUSTNAME", "姓名");
		title.put("CARD_TYPE", "卡类型");
		title.put("BANK_ACCOUNT", "卡号");
		title.put("MONEY", "金额");
		title.put("KHHSZS", "开户行所在省");
		title.put("BANK_NUMBER", "开户行代码");
		title.put("PROJ_ID", "备注");
		
		excel.addTitle(title);
		
		excel.setHeadTitles("交易渠道: 好易联");
		excel.setHeadDate(true);
		excel.setAutoHeadDate(false);
		return excel;
	}

	/**
	 * 导出租金网银-中金支付txt
	 * @param response 响应
	 * @param map {dataList:导出数据   ,  EXPORT_TYPE:导出类型  , FILE_HEAD:文件名标识}
	 * @author:  弋攀
	 * @date:    2016年1月15日 12:26:28
	 */
	public void exportDataPay(HttpServletResponse response,Map<String, Object> map) {
		
		BufferedOutputStream buff = null;
		ServletOutputStream outSTr = null;
		StringBuffer write = new StringBuffer();
		List<Map<String,Object>> dataList = (List<Map<String, Object>>) map.get("dataList");
		try {
			
			String fileName = map.get("EXPORT_TYPE").toString();
			//设置下载信息
			response.reset();
			response.setContentType("text/plain;charset=GBK");
			response.setHeader("Content-Disposition", 
					"attachment; filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ));
			outSTr = response.getOutputStream(); 
			buff = new BufferedOutputStream(outSTr);
			
			//拼写txt内容
			getPayContent(dataList,write);
			
			buff.write(write.toString().getBytes("GBK"));
			//关闭流
			buff.flush();
			buff.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buff.close();
				outSTr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	/**
	 * 导出
	 * @param params
	 * @return
	 * @author:  吴剑东
	 * @date:    2013-9-24 上午10:44:14
	 */
	public Excel exportDataJY(Map<String, Object> params) {
		List<Map<String,Object>> dataList = (List<Map<String, Object>>) params.get("dataList");
		
		Excel excel = new Excel();
		excel.setAutoColumn(25);
		excel.addSheetName("通联通标准报盘");
		excel.addData(dataList);
		LinkedHashMap<String,String>  title = new LinkedHashMap<String,String>();  

		title.put("USER_CODE", "用户编号");
		title.put("BANK_CODE", "银行代码*");
		title.put("ACCOUNT_TYPE", "账号类型");
		title.put("BANK_ACCOUNT", "账号*");
		title.put("BANK_CUSTNAME", "户名*");
		title.put("PROVINCE", "省");
		title.put("CITY", "市");
		title.put("BANK_NAME", "开户行名称");
		title.put("BANK_ACCOUNT_TYPE", "账户类型");
		title.put("MONEY", "金额*");
		title.put("CURRENCY_TYPE", "货币类型");
		title.put("XY_CODE", "协议号");
		title.put("XY_USER_CODE", "协议用户编号");
		title.put("CUST_TYPE", "开户证件类型");
		title.put("ID_CARD_NO", "证件号");
		title.put("CUST_PHONE", "手机号/小灵通");
		title.put("PROJ_ID", "自定义用户号");
		title.put("PAYLIST_CODE", "备注");
		title.put("BACK_CODE", "返回码");
		title.put("BACK_REMARK", "返回原因");
		
		excel.addTitle(title);
		excel.hasRownum("序号");
		excel.setHasTitle(true);
		excel.setHeadMoreColumn(true);
		//第一行显示多列数据
		double moneyAll=0d;
		for(int i=0;i<dataList.size();i++){
			Map<String,Object> dataMap=dataList.get(i);
			moneyAll=moneyAll+Double.parseDouble(dataMap.get("MONEY")== null?"0":dataMap.get("MONEY").toString());
		}
		
		
		String sysda=(String)Dao.selectOne("rentWriteNew.querySysdate");
		List<String> headMoreColumn=new ArrayList<String>();
		headMoreColumn.add("S");
		String SHID=(String)Dao.selectOne("rentWriteNew.queryTlSHBH");//商户ID
		headMoreColumn.add(SHID);
		headMoreColumn.add(sysda);
		headMoreColumn.add(dataList.size()+"");
		headMoreColumn.add(formatZS.format(moneyAll));
		headMoreColumn.add("10600");
		
		List<List<String>> sheetHeadMoreColumn=new ArrayList<List<String>>();
		sheetHeadMoreColumn.add(headMoreColumn);
		excel.setHeadMoreColumnTitles(sheetHeadMoreColumn);
		return excel;
	}
	
	public static void main(String[] args) throws Exception {

		System.out.println( new FiEbankUtil().parseJSEbank(new File("C://cccc.xls")));
	}
	
	
	/**
	 * 解析建设银行网银扣划回执
	 * @param txtFile 回执文件
	 * @return list(map) map{bank_flag:回执成功失败标志  1:成功   2:失败  ,  
	 * deducted_money:扣划金额  ,  bank_remark:回执成功,失败注释  ,  deducted_id:导出备注1   ,  deducted_code:导出备注2}
	 * @throws Exception 抛出异常或提示信息
	 * @author:  吴剑东
	 * @date:    2013-9-26 下午02:27:59
	 */
	public List<Map<String,Object>> parseJSEbankNew(File txtFile,Map mapDate) throws Exception {
		try {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			
			String CHECK_NAME=Security.getUser().getName();
			String CHECK_CODE=Security.getUser().getCode();
			String BANK_NAME="中国建设银行";
			Object HIRE_DATE=mapDate.get("fromDate");
			Object UPLOAD_FILE_NAME=mapDate.get("fileN");
			String FILE_ID=mapDate.get("FILE_ID").toString();

			String deducted_money="";//扣划金额
			String bank_flag="";//回执成功,失败标志 1:成功  0:失败
			String bank_remark="";//回执成功,失败注释
			String deducted_id="";//导出的备注
			String deducted_code="";//导出的备注
			
			InputStream is = new FileInputStream(txtFile);
			jxl.Workbook rwb = Workbook.getWorkbook(is);
			Sheet rs = (Sheet) rwb.getSheet(0);
			int rowNum = rs.getRows();// 得到所有行数
			for (int i = 4; i < rowNum; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				Cell c3 = (Cell) rs.getCell(3, i);
				deducted_money = ((jxl.Cell) c3).getContents();
				Cell c5 = (Cell) rs.getCell(5, i);
				bank_flag = ((jxl.Cell) c5).getContents();
				Cell c6 = (Cell) rs.getCell(6, i);
				bank_remark = ((jxl.Cell) c6).getContents();
				Cell c7 = (Cell) rs.getCell(7, i);
				deducted_id = ((jxl.Cell) c7).getContents();
				Cell c8 = (Cell) rs.getCell(8, i);
				deducted_code = ((jxl.Cell) c8).getContents();
				
				if("".equals(deducted_id) || deducted_id == ""){
					continue;
				}
				map.put("deducted_id", deducted_id);
				map.put("deducted_code", deducted_code);
				if("成功".equals(bank_flag)){
					bank_flag = "1";
				}else{
					bank_flag = "0";
				}
				
				map.put("bank_flag", bank_flag);
				map.put("bank_remark", bank_remark);
				map.put("deducted_money", deducted_money);
				map.put("CHECK_NAME", CHECK_NAME);
				map.put("CHECK_CODE", CHECK_CODE);
				map.put("BANK_NAME",BANK_NAME);
				map.put("HIRE_DATE", HIRE_DATE);
				map.put("UPLOAD_FILE_NAME", UPLOAD_FILE_NAME);
				map.put("FILE_ID", FILE_ID);
				list.add(map);
			}
			rwb.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("回执文件解析失败，请确认文件格式!");
		}
	}
	
	/**
	 * 解析通联通银行网银扣划回执
	 * @param txtFile 回执文件
	 * @return list(map) map{bank_flag:回执成功失败标志  1:成功   2:失败  ,  
	 * deducted_money:扣划金额  ,  bank_remark:回执成功,失败注释  ,  deducted_id:导出备注1   ,  deducted_code:导出备注2}
	 * @throws Exception 抛出异常或提示信息
	 * @author:  吴剑东
	 * @date:    2013-9-26 下午02:27:59
	 */
	public List<Map<String,Object>> parseJSEbankNewJY(File txtFile,Map mapDate) throws Exception {
		try {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			
			String CHECK_NAME=Security.getUser().getName();
			String CHECK_CODE=Security.getUser().getCode();
			String BANK_NAME="通联";
			Object HIRE_DATE=mapDate.get("fromDate");
			Object UPLOAD_FILE_NAME=mapDate.get("fileN");
			String FILE_ID=mapDate.get("FILE_ID").toString();

			String deducted_money="";//扣划金额
			String bank_flag="";//回执成功,失败标志 1:成功  0:失败
			String bank_remark="";//回执成功,失败注释
			String deducted_id="";//导出的备注
			String deducted_code="";//导出的备注
			
			InputStream is = new FileInputStream(txtFile);
			jxl.Workbook rwb = Workbook.getWorkbook(is);
			Sheet rs = (Sheet) rwb.getSheet(0);
			int rowNum = rs.getRows();// 得到所有行数
			for (int i = 2; i < rowNum; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				Cell c10 = (Cell) rs.getCell(10, i);
				deducted_money = ((jxl.Cell) c10).getContents();
				Cell c17 = (Cell) rs.getCell(17, i);
				deducted_id = ((jxl.Cell) c17).getContents();
				Cell c18 = (Cell) rs.getCell(18, i);
				deducted_code = ((jxl.Cell) c18).getContents();
				Cell c19 = (Cell) rs.getCell(19, i);
				bank_flag = ((jxl.Cell) c19).getContents();
				Cell c20 = (Cell) rs.getCell(20, i);
				bank_remark = ((jxl.Cell) c20).getContents();
				
				if(("4000").equals(bank_flag) || bank_flag == ""){
					continue;
				}
				
				if("".equals(deducted_id) || deducted_id == ""){
					continue;
				}
				map.put("deducted_id", deducted_id);
				map.put("deducted_code", deducted_code);
				if("0000".equals(bank_flag)){
					bank_flag = "1";
				}else{
					bank_flag = "0";
				}
				
				map.put("bank_flag", bank_flag);
				map.put("bank_remark", bank_remark);
				map.put("deducted_money", deducted_money);
				map.put("CHECK_NAME", CHECK_NAME);
				map.put("CHECK_CODE", CHECK_CODE);
				map.put("BANK_NAME",BANK_NAME);
				map.put("HIRE_DATE", HIRE_DATE);
				map.put("UPLOAD_FILE_NAME", UPLOAD_FILE_NAME);
				map.put("FILE_ID", FILE_ID);
				list.add(map);
			}
			rwb.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("回执文件解析失败，请确认文件格式!");
		}
	}
	
	
	/**
	 * 解析通联通银行网银扣划回执
	 * @param txtFile 回执文件
	 * @return list(map) map{bank_flag:回执成功失败标志  1:成功   2:失败  ,  
	 * deducted_money:扣划金额  ,  bank_remark:回执成功,失败注释  ,  deducted_id:导出备注1   ,  deducted_code:导出备注2}
	 * @throws Exception 抛出异常或提示信息
	 * @author:  吴剑东
	 * @date:    2013-9-26 下午02:27:59
	 */
	public List<Map<String,Object>> parseJSEbankNewFC(File txtFile,Map mapDate) throws Exception {
		try {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			
			String CHECK_NAME=Security.getUser().getName();
			String CHECK_CODE=Security.getUser().getCode();
			String BANK_NAME="快钱";
			Object HIRE_DATE=mapDate.get("fromDate");
			Object UPLOAD_FILE_NAME=mapDate.get("fileN");
			String FILE_ID=mapDate.get("FILE_ID").toString();

			String deducted_money="";//扣划金额
			String bank_flag="";//扣款成功,扣款失败标志 1:成功 2:失败
			String bank_remark="";//回执成功,失败注释
			String deducted_id="";//导出的备注
//			String deducted_code="";//导出的备注
			
			InputStream is = new FileInputStream(txtFile);
			jxl.Workbook rwb = Workbook.getWorkbook(is);
			Sheet rs = (Sheet) rwb.getSheet(0);
			int rowNum = rs.getRows();// 得到所有行数
			for (int i = 6; i < rowNum; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				Cell c14 = (Cell) rs.getCell(14, i);
				deducted_money = ((jxl.Cell) c14).getContents();
				
				Cell c21 = (Cell) rs.getCell(21, i);
				deducted_id = ((jxl.Cell) c21).getContents();
				
//				Cell c18 = (Cell) rs.getCell(18, i);
//				deducted_code = ((jxl.Cell) c18).getContents();
				
				Cell c23 = (Cell) rs.getCell(23, i);
				bank_flag = ((jxl.Cell) c23).getContents();
				
				Cell c25 = (Cell) rs.getCell(25, i);
				bank_remark = ((jxl.Cell) c25).getContents();
				
//				if(("4000").equals(bank_flag) || bank_flag == ""){
//					continue;
//				}
				
				if("".equals(deducted_id) || deducted_id == ""){
					continue;
				}
				map.put("deducted_id", deducted_id);
//				map.put("deducted_code", deducted_code);
				if("扣款成功".equals(bank_flag)){
					bank_flag = "1";
				}else{
					bank_flag = "0";
				}
				
				map.put("bank_flag", bank_flag);
				map.put("bank_remark", bank_remark);
				map.put("deducted_money", deducted_money);
				map.put("CHECK_NAME", CHECK_NAME);
				map.put("CHECK_CODE", CHECK_CODE);
				map.put("BANK_NAME",BANK_NAME);
				map.put("HIRE_DATE", HIRE_DATE);
				map.put("UPLOAD_FILE_NAME", UPLOAD_FILE_NAME);
				map.put("FILE_ID", FILE_ID);
				list.add(map);
			}
			rwb.close();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("回执文件解析失败，请确认文件格式!");
		}
	}
	
	
	/**
	 * 解析民生银行网银扣划回执
	 * @param txtFile 回执文件
	 * @return list(map) map{bank_flag:回执成功 失败标志 1:成功  2:失败  ,  
	 * deducted_date:扣划日期  ,  bank_remark:回执成功,失败注释  ,  deducted_id:导出备注中扣划ID}
	 * @throws Exception 抛出异常或提示信息
	 * @author:  吴剑东
	 * @date:    2013-9-26 下午02:27:59
	 */
	public List<Map<String,Object>> parseMSEbankNew(File txtFile,Map mapDate) throws Exception{

		String CHECK_NAME=Security.getUser().getName();
		String CHECK_CODE=Security.getUser().getCode();
		String BANK_NAME="中国民生银行";
		Object HIRE_DATE=mapDate.get("fromDate");
		Object UPLOAD_FILE_NAME=mapDate.get("fileN");
		String FILE_ID=mapDate.get("FILE_ID").toString();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String deducted_date="";//扣划日期
		String bank_flag="";//回执成功,失败标志 1:成功  2:失败
		String bank_remark="";//回执成功,失败注释
		String deducted_id="";//导出备注中扣划ID
		String deducted_money="";//扣划金额
		
		//解析TXT文件
		List<String> contentList = new ArrayList<String>();
		//读取
		BufferedReader br = null;
		String titleStr = "";
		try {
			//解决中文乱码
			InputStreamReader read = new InputStreamReader(new FileInputStream(txtFile), "GBK");
			br=new BufferedReader(read);
			//第一行文件头
			titleStr = br.readLine();
			System.out.println("第一行文件头:"+titleStr);
			//文件体一条条内容
			String contentStr = ""; 
			//循环读取行数据并写入list
			while((contentStr=br.readLine())!=null) {
				contentList.add(contentStr);
			}
		}catch(Exception e){
		   e.printStackTrace();
		   throw new Exception("回执文件解析失败，请确认文件格式!");
		}finally{//关闭流
		   try {
				br.close();
		   } catch (IOException e) {
				e.printStackTrace();
		   }
		}
		//判断文件是否有数据
		if(contentList.size()< 1){
			throw new Exception("回执文件中没有需要核销数据的!");
		}
		String contentStr = "";
		//循环操作
		for(int i=0;i<contentList.size();i++){
			Map<String,Object> map = new HashMap<String, Object>();
			contentStr = contentList.get(i)+"";
			if( contentStr!=null && !"".equals(contentStr.trim()) ){
				//根据 "|" 分割     [3] 扣款日期    [13] 导出时备注信息    [17] 状态  1:成功  2:失败   [19] 状态说明 
				String[] strSegment = contentStr.trim().split("\\|");
				//过滤文件头
				if(strSegment.length<10){
					continue;
				}
				//备注支付表号  为空跳过
				String remarkStr=strSegment[13];
				if(remarkStr.indexOf(":")==-1){
					continue;
				}
				//扣划ID
				deducted_id = remarkStr.substring(remarkStr.indexOf(":")+1);
				bank_flag = strSegment[17];//成功标志
				bank_remark = strSegment[19];//成功标志说明
				deducted_date = strSegment[3];//扣划日期
				deducted_money = strSegment[8];//扣划金额
//				System.out.println(deducted_date+"---"+deducted_id+"---"+bank_flag+"---"+bank_remark);
				map.put("deducted_id", deducted_id);
				if(bank_flag.equals("1")){
					map.put("bank_flag", 1);
				}
				else{
					map.put("bank_flag", 0);
				}
				
				map.put("bank_remark", bank_remark);
				map.put("deducted_date", deducted_date);
				map.put("deducted_money", deducted_money);
				map.put("CHECK_NAME", CHECK_NAME);
				map.put("CHECK_CODE", CHECK_CODE);
				map.put("BANK_NAME",BANK_NAME);
				map.put("HIRE_DATE", HIRE_DATE);
				map.put("UPLOAD_FILE_NAME", UPLOAD_FILE_NAME);
				map.put("FILE_ID", FILE_ID);
				list.add(map);
			}
		}
		return list;
	
	}
	
	/**
	 * 解析通联通网银扣划回执
	 * @param txtFile 回执文件
	 * @return list(map) map{bank_flag:回执成功 失败标志 1:成功  2:失败  ,  
	 * deducted_date:扣划日期  ,  bank_remark:回执成功,失败注释  ,  deducted_id:导出备注中扣划ID}
	 * @throws Exception 抛出异常或提示信息
	 * @author:  吴剑东
	 * @date:    2013-9-26 下午02:27:59
	 */
	public List<Map<String,Object>> parseMSEbankNewJY(File txtFile,Map mapDate) throws Exception{

		String CHECK_NAME=Security.getUser().getName();
		String CHECK_CODE=Security.getUser().getCode();
		String BANK_NAME="通联";
		Object HIRE_DATE=mapDate.get("fromDate");
		Object UPLOAD_FILE_NAME=mapDate.get("fileN");
		String FILE_ID=mapDate.get("FILE_ID").toString();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String bank_flag="";//回执成功,失败标志 1:成功  2:失败
		String bank_remark="";//回执成功,失败注释
		String deducted_id="";//导出备注中扣划ID
		String deducted_money="";//扣划金额
		
		//解析TXT文件
		List<String> contentList = new ArrayList<String>();
		//读取
		BufferedReader br = null;
		String titleStr = "";
		try {
			//解决中文乱码
			InputStreamReader read = new InputStreamReader(new FileInputStream(txtFile), "GBK");
			br=new BufferedReader(read);
			//第一行文件头
			titleStr = br.readLine();
			System.out.println("第一行文件头:"+titleStr);
			//文件体一条条内容
			String contentStr = ""; 
			//循环读取行数据并写入list
			while((contentStr=br.readLine())!=null) {
				contentList.add(contentStr);
			}
		}catch(Exception e){
		   e.printStackTrace();
		   throw new Exception("回执文件解析失败，请确认文件格式!");
		}finally{//关闭流
		   try {
				br.close();
		   } catch (IOException e) {
				e.printStackTrace();
		   }
		}
		//判断文件是否有数据
		if(contentList.size()< 1){
			throw new Exception("回执文件中没有需要核销数据的!");
		}
		String contentStr = "";
		//循环操作
		for(int i=0;i<contentList.size();i++){
			Map<String,Object> map = new HashMap<String, Object>();
			contentStr = contentList.get(i)+"";
			if( contentStr!=null && !"".equals(contentStr.trim()) ){
				//根据 "," 分割     [10] 扣款金额    [17] 导出时备注信息 [18] 导出时备注信息    [19] 状态  1:成功  2:失败   [20] 状态说明 
				String[] strSegment = contentStr.trim().split(",");
				//过滤文件头
				if(strSegment.length<10){
					continue;
				}
				//扣划ID
				deducted_money = strSegment[10];//扣划金额
				deducted_id = strSegment[17];
				bank_flag = strSegment[19];//成功标志
				bank_remark = strSegment[20];//成功标志说明
				
				if(("4000").equals(bank_flag) || bank_flag == ""){
					continue;
				}
				
				if("".equals(deducted_id) || deducted_id == ""){
					continue;
				}
				
				
				
				if("0000".equals(bank_flag)){
					bank_flag = "1";
				}else{
					bank_flag = "0";
				}
				
				map.put("bank_flag", bank_flag);
				map.put("deducted_id", deducted_id);
				map.put("bank_remark", bank_remark);
				map.put("deducted_money", deducted_money);
				map.put("CHECK_NAME", CHECK_NAME);
				map.put("CHECK_CODE", CHECK_CODE);
				map.put("BANK_NAME",BANK_NAME);
				map.put("HIRE_DATE", HIRE_DATE);
				map.put("UPLOAD_FILE_NAME", UPLOAD_FILE_NAME);
				map.put("FILE_ID", FILE_ID);
				list.add(map);
			}
		}
		return list;
	
	}
	
	
	/**
	 * 根据数据List生成字符串用于转换输出流
	 * @param dataList 数据
	 * @param write 字符串
	 * @param no 批次
	 * @param EXPORT_TYPE 导出类型
	 * @author:  吴剑东
	 * @date:    2013-9-25 下午03:22:06
	 */
	public void getJSYHTXTEbankContent(List dataList,StringBuffer write,String no,String EXPORT_TYPE){
		try {
			String printAll="";
			String enter = "\r\n";
			
		    String bodyContent = "";
		    double moneyAll=0d;
		    //===文件体===
			String money = "0";//单笔金额
			String kh = "";//卡号
			String khxm = "";//姓名
//			int num=0;
			String PRO_CODE="";//项目编号
			String YT="融资租赁收款";
			String proId = "";
			
			String noNum=getExportNoU(EXPORT_TYPE);
			int counProjNo =Integer.parseInt(noNum);
		    for (int i = 0; i < dataList.size(); i++) {
		    	Map<String,Object> dataMap = (Map<String,Object>)dataList.get(i);
//		    	num=i+1;
		    	moneyAll=moneyAll+Double.parseDouble(dataMap.get("MONEY")== null?"0":dataMap.get("MONEY").toString());
		    	money = format.format(Double.parseDouble(dataMap.get("MONEY")== null?"0":dataMap.get("MONEY").toString()));
		    	kh = dataMap.get("BANK_ACCOUNT")+"";
		    	khxm = dataMap.get("BANK_CUSTNAME")+"";
		    	proId = dataMap.get("PROJ_ID")+"";
		    	if(dataMap!=null && dataMap.get("PAYLIST_CODE")!=null && !dataMap.get("PAYLIST_CODE").equals("")){
		    		PRO_CODE=dataMap.get("PAYLIST_CODE").toString();
		    	}
//		    	bodyContent = bodyContent+num+"|"+kh+"|"+khxm+"|"+money+"|"+YT+"|"+PRO_CODE+"||"+enter;
		    	bodyContent = bodyContent+kh+"|"+khxm+"|"+money+"|"+YT+"|"+proId+"|"+PRO_CODE+"|"+enter;
		    }
		    getExportNo1(EXPORT_TYPE,counProjNo);
		    write.append(bodyContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 解析建设银行TXT网银扣划回执
	 * @param txtFile 回执文件
	 * @return list(map) map{bank_flag:回执成功 失败标志 1:成功  2:失败  ,  
	 * deducted_date:扣划日期  ,  bank_remark:回执成功,失败注释  ,  deducted_id:导出备注中扣划ID}
	 * @throws Exception 抛出异常或提示信息
	 * @author:  吴剑东
	 * @date:    2013-9-26 下午02:27:59
	 */
	public List<Map<String,Object>> parseJSYHTXTEbankNew(File txtFile,Map mapDate) throws Exception{

		String CHECK_NAME=Security.getUser().getName();
		String CHECK_CODE=Security.getUser().getCode();
		String BANK_NAME="建设银行";
		Object HIRE_DATE=mapDate.get("fromDate");
		Object UPLOAD_FILE_NAME=mapDate.get("fileN");
		String FILE_ID=mapDate.get("FILE_ID").toString();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String bank_flag="";//回执成功,失败标志 1:成功  2:失败
		String bank_remark="";//回执成功,失败注释
		String deducted_id="";//导出备注中扣划ID
		String deducted_money="";//扣划金额
		
		//解析TXT文件
		List<String> contentList = new ArrayList<String>();
		//读取
		BufferedReader br = null;
		String titleStr = "";
		try {
			//解决中文乱码
			InputStreamReader read = new InputStreamReader(new FileInputStream(txtFile), "GBK");
			br=new BufferedReader(read);
			//第一行文件头
			titleStr = br.readLine();
			System.out.println("第一行文件头:"+titleStr);
			//文件体一条条内容
			String contentStr = ""; 
			//循环读取行数据并写入list
			while((contentStr=br.readLine())!=null) {
				contentList.add(contentStr);
			}
		}catch(Exception e){
		   e.printStackTrace();
		   throw new Exception("回执文件解析失败，请确认文件格式!");
		}finally{//关闭流
		   try {
				br.close();
		   } catch (IOException e) {
				e.printStackTrace();
		   }
		}
		//判断文件是否有数据
		if(contentList.size()< 1){
			throw new Exception("回执文件中没有需要核销数据的!");
		}
		String contentStr = "";
		//循环操作
		for(int i=0;i<contentList.size();i++){
			Map<String,Object> map = new HashMap<String, Object>();
			contentStr = contentList.get(i)+"";
			if( contentStr!=null && !"".equals(contentStr.trim()) ){
				System.out.println("-----------="+contentStr);
				//根据 "|" 分割     [3] 扣款金额    [5] 状态  成功  失败   [6] 状态说明   [7] 导出时备注信息 [8] 导出时备注信息
				String[] strSegment = contentStr.trim().split("\\|");
				//过滤文件头
				if(strSegment.length<5){
					continue;
				}
				//扣划ID
				deducted_money = strSegment[3];//扣划金额
				deducted_id = strSegment[7];
				bank_flag = strSegment[5];//成功标志
				bank_remark = strSegment[6];//成功标志说明
				System.out.println("---------------deducted_money="+deducted_money+"-----------deducted_id="+deducted_id+"----------bank_flag="+bank_flag+"------------bank_remark="+bank_remark);
				
				if("".equals(deducted_id) || deducted_id == ""){
					continue;
				}
				
				
				
				if("成功".equals(bank_flag)){
					bank_flag = "1";
				}else{
					bank_flag = "0";
				}
				
				map.put("bank_flag", bank_flag);
				map.put("deducted_id", deducted_id);
				map.put("bank_remark", bank_remark);
				map.put("deducted_money", deducted_money);
				map.put("CHECK_NAME", CHECK_NAME);
				map.put("CHECK_CODE", CHECK_CODE);
				map.put("BANK_NAME",BANK_NAME);
				map.put("HIRE_DATE", HIRE_DATE);
				map.put("UPLOAD_FILE_NAME", UPLOAD_FILE_NAME);
				map.put("FILE_ID", FILE_ID);
				list.add(map);
			}
		}
		return list;
	
	}
	
	
	/**
	 * 解析民生银行网银扣划回执
	 * @param txtFile 回执文件
	 * @return list(map) map{bank_flag:回执成功 失败标志 1:成功  2:失败  ,  
	 * deducted_date:扣划日期  ,  bank_remark:回执成功,失败注释  ,  deducted_id:导出备注中扣划ID}
	 * @throws Exception 抛出异常或提示信息
	 * @author:  吴剑东
	 * @date:    2013-9-26 下午02:27:59
	 */
	public List<Map<String,Object>> parseGDEbankNew(File txtFile,Map mapDate) throws Exception{

		String CHECK_NAME=Security.getUser().getName();
		String CHECK_CODE=Security.getUser().getCode();
		String BANK_NAME="光大银行";
		Object HIRE_DATE=mapDate.get("fromDate");
		Object UPLOAD_FILE_NAME=mapDate.get("fileN");
		String FILE_ID=mapDate.get("FILE_ID").toString();
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String bank_flag="";//回执成功（AAAAAA）,失败标志 1:成功  2:失败
		String bank_remark="";//回执成功,失败注释
		String deducted_id="";//导出备注中扣划ID
		String deducted_money="";//扣划金额
		
		//解析TXT文件
		List<String> contentList = new ArrayList<String>();
		//读取
		BufferedReader br = null;
		String titleStr = "";
		try {
			//解决中文乱码
			InputStreamReader read = new InputStreamReader(new FileInputStream(txtFile), "GBK");
			br=new BufferedReader(read);
			
			//文件体一条条内容
			String contentStr = ""; 
			//循环读取行数据并写入list
			while((contentStr=br.readLine())!=null) {
				contentList.add(contentStr);
			}
		}catch(Exception e){
		   e.printStackTrace();
		   throw new Exception("回执文件解析失败，请确认文件格式!");
		}finally{//关闭流
		   try {
				br.close();
		   } catch (IOException e) {
				e.printStackTrace();
		   }
		}
		//判断文件是否有数据
		if(contentList.size()< 1){
			throw new Exception("回执文件中没有需要核销数据的!");
		}
		String contentStr = "";
		//循环操作
		for(int i=7;i<contentList.size();i++){
			Map<String,Object> map = new HashMap<String, Object>();
			contentStr = contentList.get(i)+"";
			if( contentStr!=null && !"".equals(contentStr.trim()) ){
				//根据 "," 分割     [10] 扣款金额    [17] 导出时备注信息 [18] 导出时备注信息    [19] 状态  1:成功  2:失败   [20] 状态说明 
				String[] strSegment = contentStr.trim().split("\\|");
				//过滤文件头
				if(strSegment.length<5){
					continue;
				}
				//扣划ID
				deducted_money = strSegment[2];//扣划金额
				deducted_id = strSegment[7];
				bank_flag = strSegment[4];//成功标志
				bank_remark = strSegment[5];//成功标志说明
				
				
//				if(("4000").equals(bank_flag) || bank_flag == ""){
//					continue;
//				}
				
				if("".equals(deducted_id) || deducted_id == ""){
					continue;
				}
				
				
				
				if("AAAAAA".equals(bank_flag)){
					bank_flag = "1";
				}else{
					bank_flag = "0";
				}
				
				map.put("bank_flag", bank_flag);
				map.put("deducted_id", deducted_id);
				map.put("bank_remark", bank_remark);
				map.put("deducted_money", deducted_money);
				map.put("CHECK_NAME", CHECK_NAME);
				map.put("CHECK_CODE", CHECK_CODE);
				map.put("BANK_NAME",BANK_NAME);
				map.put("HIRE_DATE", HIRE_DATE);
				map.put("UPLOAD_FILE_NAME", UPLOAD_FILE_NAME);
				map.put("FILE_ID", FILE_ID);
				list.add(map);
			}
		}
		return list;
	
	}
	
}
