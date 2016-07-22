package com.pqsoft.payment.service;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.velocity.VelocityContext;
import org.jbpm.api.ProcessInstance;

import com.pqsoft.baseScheme.service.BaseSchemeService;
import com.pqsoft.bpm.JBPM;
import com.pqsoft.bpm.service.JbpmFiService;
import com.pqsoft.entity.Excel;
import com.pqsoft.skyeye.api.Dao;
import com.pqsoft.skyeye.api.Page;
import com.pqsoft.skyeye.api.SkyEye;
import com.pqsoft.skyeye.filter.ResMime;
import com.pqsoft.skyeye.rbac.api.Security;
import com.pqsoft.util.BaseUtil;
import com.pqsoft.util.DateSiteUtil;
import com.pqsoft.util.DateUtil;
import com.pqsoft.util.PageUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class paymentService {
	private final String xmlPath = "customers.";
	public void jbpmByProjectPayMent(String JBPM_ID)
	{
		String PROJECT_ID=JBPM.getVeriable(JBPM_ID).get("PROJECT_ID").toString();
		this.createPaymentData(PROJECT_ID);
	}
	
	//立项流程总经理审批通过像放款表插入放款数据
	@SuppressWarnings("unchecked")
	public void createPaymentData(String PROJECT_ID)
	{
		
		
		//TODO  放款政策配置
		String payment_type="1";//1:全额放款，2：差额放款,3:部分差额放款,4:DB差额放款
		String payment_Bank_type="1";//1：自有账号，2：共有账号 ,3:厂商账号
		String payment_Ziliao_type="1";//1：否，2：是70%，3：是100%
		String PAY_WAY="1";//还款政策
		
		
		double LEASE_TOPRIC=0d;//设备总额
		double PAY_MONEY=0d;//放款额
		double KK_MONEY=0d;//扣款金额
		double SQ_PRICE=0d;//首期款
		double DBBZ_PRICE=0d;//DB保证金
		double CSBZ_PRICE=0d;//厂商保证金
		double DBFEE_PRICE=0d;//担保费
		double OTHER_PRICE=0d;//其他费用
		double ONEMONEY=0d;//第一期租金
		double BZ_PRICE=0d;//保证金
		double SXF_PRICE=0d;//手续费
		double QZZJ_PRICE=0d;//起租租金
		double LGJ_PRICE=0d;//留购价款
		double BXF_PRICE=0d;//保险费
		
		
		//1.通过项目ID查到方案
		//2.判断放款方式是全额放款还是差额放款及是否资料后补来确定分
		//3.插入到数据库
		//------------------开始-----------------
		Map mapScheme=new HashMap();
		mapScheme.put("PROJECT_ID", PROJECT_ID);
		//如果数据库存在就将其删掉
		Dao.delete("payment.deletePayMentDetail", mapScheme);
		

		mapScheme.put("KEY_NAME_EN", "PAY_WAY");
		Map huankuan_TypeMap=Dao.selectOne("payment.query_value_scheme", mapScheme);//查询出放款方式
		if(huankuan_TypeMap!=null && huankuan_TypeMap.get("CODE")!=null && !huankuan_TypeMap.get("CODE").equals(""))
		{
			PAY_WAY=huankuan_TypeMap.get("CODE").toString();
		}

		mapScheme.put("KEY_NAME_EN", "PAYMENT_STATUS");
		Map payment_TypeMap=Dao.selectOne("payment.query_value_scheme", mapScheme);//查询出放款方式
		if(payment_TypeMap!=null && payment_TypeMap.get("CODE")!=null && !payment_TypeMap.get("CODE").equals(""))
		{
			payment_type=payment_TypeMap.get("CODE").toString();
		}
		
		mapScheme.put("KEY_NAME_EN", "LOAN_ACCOUNT_NAME");
		Map payment_Bank_typeMap=Dao.selectOne("payment.query_value_scheme", mapScheme);//查询出放款账号
		if(payment_Bank_typeMap!=null && payment_Bank_typeMap.get("CODE")!=null && !payment_Bank_typeMap.get("CODE").equals(""))
		{
			payment_Bank_type=payment_Bank_typeMap.get("CODE").toString();
		}
		
		mapScheme.put("KEY_NAME_EN", "DATE_COMPLEMENT");
		Map payment_Ziliao_typeMap=Dao.selectOne("payment.query_value_scheme", mapScheme);//查询出是否资料后补
		if(payment_Ziliao_typeMap!=null && payment_Ziliao_typeMap.get("CODE")!=null && !payment_Ziliao_typeMap.get("CODE").equals(""))
		{
			payment_Ziliao_type=payment_Ziliao_typeMap.get("CODE").toString();
		}
		
		//基础数据
		Map baseMap=Dao.selectOne("payment.query_base_map", mapScheme);
		if(baseMap==null)
		{
			baseMap=new HashMap();
		}
		

//		baseMap.put("REMARK", "DSFK");// 丢失放款数据
		baseMap.put("STATUS", 1);//核销状态(-2：作废，1：未核销，2：已核销)
		
		//账号信息
		Map suppBank=Dao.selectOne("payment.query_supp_bank", mapScheme);
		if(payment_Bank_type.equals("1"))//自有账号
		{
			baseMap.put("PAYEE_NAME",suppBank.get("LOANS_OWN_UNIT"));//收款单位
			baseMap.put("PAY_BANK_ACCOUNT",suppBank.get("LOANS_OWN_ACCOUNT"));//收款账号
			baseMap.put("PAY_BANK_ADDRESS",suppBank.get("LOANS_OWN_ADDR"));//开户行地址
			baseMap.put("PAY_BANK_NAME",suppBank.get("LOANS_OWN_BANK"));//开户行银行
		}
		else if(payment_Bank_type.equals("2"))//共有账号
		{
			baseMap.put("PAYEE_NAME",suppBank.get("LOANS_TOTAL_UNIT"));//收款单位
			baseMap.put("PAY_BANK_ACCOUNT",suppBank.get("LOANS_TOTAL_ACCOUNT"));//收款账号
			baseMap.put("PAY_BANK_ADDRESS",suppBank.get("LOANS_TOTAL_ADDDR"));//开户行地址
			baseMap.put("PAY_BANK_NAME",suppBank.get("LOANS_TOTAL_BANK"));//开户行银行
		}
		else{//厂商账号
			baseMap.put("PAYEE_NAME",suppBank.get("LOANS_COMPANY_UNIT"));//收款单位
			baseMap.put("PAY_BANK_ACCOUNT",suppBank.get("LOANS_COMPANY_ACCOUNT"));//收款账号
			baseMap.put("PAY_BANK_ADDRESS",suppBank.get("LOANS_COMPANY_ADDR"));//开户行地址
			baseMap.put("PAY_BANK_NAME",suppBank.get("LOANS_COMPANY_BANK"));//开户行银行
		}
		
		
		//设备价款
		if(baseMap.get("LEASE_TOPRIC")!=null && !baseMap.get("LEASE_TOPRIC").equals(""))
		{
			LEASE_TOPRIC=Double.parseDouble(baseMap.get("LEASE_TOPRIC").toString());
		}
		
		
		//第一期租金
		if(PAY_WAY.equals("2") || PAY_WAY.equals("4") || PAY_WAY.equals("6")){
			Map ONEMap=Dao.selectOne("payment.query_Total_ONE", mapScheme);
			if(ONEMap!=null && ONEMap.get("BEGINNING_MONEY")!=null && !"".equals(ONEMap.get("BEGINNING_MONEY")+"")){
				ONEMONEY=Double.parseDouble(ONEMap.get("BEGINNING_MONEY").toString());
			}
		}
		
		//首期款
		Map SQMap=Dao.selectOne("payment.query_Total_SQ", mapScheme);
		if(SQMap!=null && SQMap.get("PRICE")!=null && !SQMap.get("PRICE").equals(""))
		{
			SQ_PRICE=Double.parseDouble(SQMap.get("PRICE").toString());
			SQ_PRICE += ONEMONEY;
		}
		
		//DB保证金
		Map DBBZMap=Dao.selectOne("payment.query_Total_DBBZ", mapScheme);
		if(DBBZMap!=null && DBBZMap.get("PRICE")!=null && !DBBZMap.get("PRICE").equals(""))
		{
			DBBZ_PRICE=Double.parseDouble(DBBZMap.get("PRICE").toString());
		}
		
		//CS保证金
		Map CSBZMap=Dao.selectOne("payment.query_Total_CSBZ", mapScheme);
		if(CSBZMap!=null && CSBZMap.get("PRICE")!=null && !CSBZMap.get("PRICE").equals(""))
		{
			CSBZ_PRICE=Double.parseDouble(CSBZMap.get("PRICE").toString());
		}
		
		//保证金
		Map BZMap=Dao.selectOne("payment.query_Total_BZFee", mapScheme);
		System.out.println("kingBZMap="+BZMap);
		if(BZMap!=null && BZMap.get("PRICE")!=null && !BZMap.get("PRICE").equals(""))
		{
			BZ_PRICE=Double.parseDouble(BZMap.get("PRICE").toString());
		}
//		
		//手续费
		Map SXFMap=Dao.selectOne("payment.query_Total_SXF", mapScheme);
		if(SXFMap!=null && SXFMap.get("PRICE")!=null && !SXFMap.get("PRICE").equals(""))
		{
			SXF_PRICE=Double.parseDouble(SXFMap.get("PRICE").toString());
		}
		
		//起租租金
		Map QZZJMap=Dao.selectOne("payment.query_Total_QJZJ", mapScheme);
		if(QZZJMap!=null && QZZJMap.get("PRICE")!=null && !QZZJMap.get("PRICE").equals(""))
		{
			QZZJ_PRICE=Double.parseDouble(QZZJMap.get("PRICE").toString());
		}
		
		//留购价
		Map LGJMap=Dao.selectOne("payment.query_Total_LGJ", mapScheme);
		if(LGJMap!=null && LGJMap.get("PRICE")!=null && !LGJMap.get("PRICE").equals(""))
		{
			LGJ_PRICE=Double.parseDouble(LGJMap.get("PRICE").toString());
		}
		
		//保险费
		Map BXFMap=Dao.selectOne("payment.query_Total_BXF", mapScheme);
		if(BXFMap!=null && BXFMap.get("PRICE")!=null && !BXFMap.get("PRICE").equals(""))
		{
			BXF_PRICE=Double.parseDouble(BXFMap.get("PRICE").toString());
		}

		//其他费用
		Map OTHERMap=Dao.selectOne("payment.query_Total_OTHER", mapScheme);
		if(OTHERMap!=null && OTHERMap.get("PRICE")!=null && !OTHERMap.get("PRICE").equals(""))
		{
			OTHER_PRICE=Double.parseDouble(OTHERMap.get("PRICE").toString());
			
		}
		//全额放款--------放款额=设备总额
		//差额放款--------放款额=设备总额-首期款-DB保证金-其他费用(如果是期初还要减去第一期租金)
		//部分差额放款----放款额=设备总额-留购价款-起租租金
		//DB差额放款-----DB差额放款=设备总额-DB保证金
		if(payment_type.equals("1"))//全额放款
		{
			//放款额=设备总额
			PAY_MONEY=LEASE_TOPRIC;
			//放款方式
			baseMap.put("PAYMENT_TYPE",1);
		}
		else if(payment_type.equals("2")){//差额放款
			mapScheme.put("KEY_NAME_EN", "PAY_WAY");
			Map PAY_WAYMAP=Dao.selectOne("payment.query_value_scheme", mapScheme);//查询出放款方式
			if(PAY_WAYMAP!=null && PAY_WAYMAP.get("CODE")!=null && !PAY_WAYMAP.get("CODE").equals(""))
			{
				PAY_WAY=PAY_WAYMAP.get("CODE").toString();
			}

			//放款额=设备总额-首期款-DB保证金-厂商保证金-其他费用(如果是期初还要减去第一期租金)
			PAY_MONEY=LEASE_TOPRIC;
			KK_MONEY = SQ_PRICE + DBBZ_PRICE + CSBZ_PRICE + ONEMONEY + OTHER_PRICE; 
			baseMap.put("PAYMENT_TYPE",2);
		}else if(payment_type.equals("3")){//部分差额放款
			mapScheme.put("KEY_NAME_EN", "PAY_WAY");
			Map PAY_WAYMAP=Dao.selectOne("payment.query_value_scheme", mapScheme);//查询出放款方式
			if(PAY_WAYMAP!=null && PAY_WAYMAP.get("CODE")!=null && !PAY_WAYMAP.get("CODE").equals(""))
			{
				PAY_WAY=PAY_WAYMAP.get("CODE").toString();
			}
			

			//放款额=设备总额-留购价款-起租租金
			PAY_MONEY=LEASE_TOPRIC;
			KK_MONEY = LGJ_PRICE + QZZJ_PRICE ; 
			baseMap.put("PAYMENT_TYPE",3);
			
		}
		else if(payment_type.equals("4")){//DB差额放款
			mapScheme.put("KEY_NAME_EN", "PAY_WAY");
			Map PAY_WAYMAP=Dao.selectOne("payment.query_value_scheme", mapScheme);//查询出放款方式
			if(PAY_WAYMAP!=null && PAY_WAYMAP.get("CODE")!=null && !PAY_WAYMAP.get("CODE").equals(""))
			{
				PAY_WAY=PAY_WAYMAP.get("CODE").toString();
			}
			
			//DB差额放款=设备总额-DB保证金
			PAY_MONEY = LEASE_TOPRIC ;

			KK_MONEY = DBBZ_PRICE ; 
			baseMap.put("PAYMENT_TYPE",4);
		}
		
		baseMap.put("PAY_MONEY",PAY_MONEY - KK_MONEY);//放额款
		baseMap.put("LEASE_TOPRIC",LEASE_TOPRIC);//设备总额
		
		
		
		
		if(payment_type.equals("2")){
			baseMap.put("SQ_PRICE",SQ_PRICE);//首期款
			baseMap.put("LGJK",LGJ_PRICE);//留购价款
			baseMap.put("QZZJ",QZZJ_PRICE);//起租租金
			baseMap.put("BZJ",BZ_PRICE);//保证金
			baseMap.put("SXF",SXF_PRICE);//手续费
			baseMap.put("DBF",DBFEE_PRICE);//担保费
			baseMap.put("FIRST_RENT", ONEMONEY);//第一期租金
			baseMap.put("BXF", BXF_PRICE);//保险费
			baseMap.put("QTFY", OTHER_PRICE);//其他费用
			
			baseMap.put("CS_PRICE",CSBZ_PRICE);//厂商保证金
			baseMap.put("DB_PRICE",DBBZ_PRICE);//DB保证金
			
		}else if(payment_type.equals("3")){
			
			baseMap.put("SQ_PRICE",(LGJ_PRICE + QZZJ_PRICE));//首期款

			baseMap.put("LGJK",LGJ_PRICE);//留购价款
			baseMap.put("QZZJ",QZZJ_PRICE);//起租租金
			
		}if(payment_type.equals("4")){
			baseMap.put("DB_PRICE",DBBZ_PRICE);//DB保证金
		}
		
		if(payment_Ziliao_type.equals("1"))//资料后补：否
		{
			baseMap.put("COMPLEMENT_TYPE",1);//是否放款状态
			baseMap.put("PAY_TYPE",1);//款项类型
			baseMap.put("PAY_NAME", "全款");//款项名称
			baseMap.put("PAYMENT_NUM", 1);//放款顺序 
			baseMap.put("PAYMENT_ONE_SECCESS",1);
			//保存数据
			Dao.insert("payment.createPayMentDate", baseMap);
		}
		else if(payment_Ziliao_type.equals("2"))//资料后补：是，70%
		{
			baseMap.put("PAY_TYPE",1);//款项类型
			//70%可以放款
			baseMap.put("PAY_NAME", "70﹪款");//款项名称
			baseMap.put("PAYMENT_NUM", 1);//放款顺序 
			baseMap.put("PAY_MONEY",PAY_MONEY*0.7 - KK_MONEY);//放额款
			baseMap.put("PAYMENT_ONE_SECCESS",1);
			baseMap.put("COMPLEMENT_TYPE",1);//是否放款状态
			//保存数据
			Dao.insert("payment.createPayMentDate", baseMap);
			
			Map mapER=new HashMap();
			mapER.putAll(baseMap);
			//30%不能放款
			mapER.put("PAY_NAME", "30﹪款");//款项名称
			mapER.put("PAYMENT_NUM", 2);//放款顺序 
			mapER.put("PAY_MONEY",PAY_MONEY*0.3);//放额款
			mapER.put("PAYMENT_ONE_SECCESS",0);//等一次付款成功后更新为1
			mapER.put("COMPLEMENT_TYPE",0);//是否放款状态
			
			mapER.put("DB_PRICE",0);//首期款
			mapER.put("SQ_PRICE",0);//首期款
			mapER.put("LGJK",0);//留购价款
			mapER.put("QZZJ",0);//起租租金
			mapER.put("BZJ",0);//保证金
			mapER.put("SXF",0);//手续费
			mapER.put("DBF",0);//担保费
			mapER.put("FIRST_RENT", 0);//第一期租金
			mapER.put("BXF", 0);//保险费0
			mapER.put("DBBZ_PRICE",0);//DB保证金0
			
			//保存数据
			Dao.insert("payment.createPayMentDate", mapER);
		}
		else//资料后补：是，100%
		{
			baseMap.put("PAY_TYPE",1);//款项类型
			baseMap.put("PAY_NAME", "全款");//款项名称
			baseMap.put("COMPLEMENT_TYPE",1);//是否放款状态
			baseMap.put("PAYMENT_NUM", 1);//放款顺序 
			baseMap.put("PAYMENT_ONE_SECCESS",1);
			//保存数据
			Dao.insert("payment.createPayMentDate", baseMap);
		}
		
		Dao.update("payment.updatePayMentDateINVOICE",baseMap);

		//保存其他费用
		if(OTHER_PRICE>0)
		{
			
			Map mapOther=baseMap;
			mapOther.put("PAY_MONEY",OTHER_PRICE);//放额款
			mapOther.put("SQ_PRICE",0);//首期款
			mapOther.put("LGJK",0);//留购价款
			mapOther.put("QZZJ",0);//起租租金
			mapOther.put("BZJ",0);//保证金
			mapOther.put("SXF",0);//手续费
			mapOther.put("DBF",0);//担保费
			mapOther.put("FIRST_RENT", 0);//第一期租金
			mapOther.put("BXF", 0);//保险费0
			mapOther.put("DBBZ_PRICE",0);//DB保证金0
			mapOther.put("PAY_TYPE",4);//款项类型
			mapOther.put("PAY_NAME", "全款");//款项名称
			mapOther.put("COMPLEMENT_TYPE",1);//是否放款状态
			mapOther.put("PAYMENT_NUM", 1);//放款顺序 
			mapOther.put("PAYMENT_ONE_SECCESS",1);
			//保存数据
			Dao.insert("payment.createPayMentDate", mapOther);
		}
		
		//保存担保费
		Map DBFEERMap=Dao.selectOne("payment.query_Total_DBFee", mapScheme);
		if(DBFEERMap!=null && DBFEERMap.get("PRICE")!=null && !DBFEERMap.get("PRICE").equals(""))
		{
			DBFEE_PRICE=Double.parseDouble(DBFEERMap.get("PRICE").toString());
			if(DBFEE_PRICE>0)
			{
				Map mapDBFEE=baseMap;
				mapDBFEE.put("PAY_MONEY",DBFEE_PRICE);//放额款
				mapDBFEE.put("SQ_PRICE",0);//首期款
				mapDBFEE.put("LGJK",0);//留购价款
				mapDBFEE.put("QZZJ",0);//起租租金
				mapDBFEE.put("BZJ",0);//保证金
				mapDBFEE.put("SXF",0);//手续费
				mapDBFEE.put("DBF",0);//担保费
				mapDBFEE.put("FIRST_RENT", 0);//第一期租金
				mapDBFEE.put("BXF", 0);//保险费0
				mapDBFEE.put("DBBZ_PRICE",0);//DB保证金0
				
				mapDBFEE.put("PAY_TYPE",2);//款项类型
				mapDBFEE.put("PAY_NAME", "全款");//款项名称
				mapDBFEE.put("COMPLEMENT_TYPE",1);//是否放款状态
				mapDBFEE.put("PAYMENT_NUM", 1);//放款顺序 
				mapDBFEE.put("PAYMENT_ONE_SECCESS",1);
				
				//担保公司账号
//				baseMap.put("PAYEE_NAME",suppBank.get("LOANS_COMPANY_UNIT"));//收款单位
//				baseMap.put("PAY_BANK_ACCOUNT",suppBank.get("LOANS_COMPANY_ACCOUNT"));//收款账号
//				baseMap.put("PAY_BANK_ADDRESS",suppBank.get("LOANS_COMPANY_ADDR"));//开户行地址
//				baseMap.put("PAY_BANK_NAME",suppBank.get("LOANS_COMPANY_BANK"));//开户行银行
				//保存数据
				Dao.insert("payment.createPayMentDate", mapDBFEE);
			}
		}
		
	}

	
	//修改发票号和发票日期（日期都是yyyy-MM-dd），
	//参数map-包括（项目编号：PROJECT_CODE，发票类型<1:设备发票,2:担保费发票,4:其他发票>：PAY_TYPE，发票号：INVOICE_NUM，发票日期：INVOICE_DATE，担保号：GUARANTEE_NUM，担保日期：GUARANTEE_DATE）
	public void updatePMByDate(Map dateMap)
	{
		if(dateMap!=null && dateMap.get("PROJECT_CODE")!=null && !dateMap.get("PROJECT_CODE").equals(""))
		{
			if(dateMap.get("PAY_TYPE")!=null && !dateMap.get("PAY_TYPE").equals(""))
			{
				Dao.update("payment.updatePMByDate",dateMap);
			}
		}
	}
	
	//修改资料后补状态，参数String （项目编号：project_Code）
	public void updatePMByComplement(String project_Code)
	{
		if(project_Code.length()>0)
		{
			Map map=new HashMap();
			map.put("PROJECT_CODE", project_Code);
			Dao.update("payment.updatePMByComplement",map);
		}
	}
	
	//修改起租日,验收日期和预付款日（日期都是yyyy-MM-dd），参数map-包括(项目编号：PROJECT_CODE，起租日：START_DATE，预付款日期：RESERVE_DATE，验收日期：ACCEPTANCE_DATE)
	public void updatePMByStateDate(Map dateMap)
	{
		if(dateMap!=null && dateMap.get("PROJECT_CODE")!=null && !dateMap.get("PROJECT_CODE").equals(""))
		{
			Dao.update("payment.updatePMByStateDate",dateMap);
		}
	}
	
	
	//设备放款申请页面
	public List query_Eq_PayMent_C(Map map)
	{
		List listDate=new ArrayList();
		//1.能申请的必须是有起租日的
		//2.那些业务类型不需要发票(具体类型参考数据字典：业务类型)
//		String NotInvoice="1,8";//不需要发票的业务类型在这个地方已逗号隔开
		String NotInvoice="11";
		if(map==null)
		{
			map=new HashMap();
		}
		map.put("NotInvoice", NotInvoice);
		
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			map.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			map.put("COM_ID",COM_MAP.get("COMPANY_ID"));
		}
		listDate=Dao.selectList("payment.query_Eq_PayMent_C", map);
		return listDate;
	}
	
	//设备放款申请页面按照供应商统计
	public List query_EqSupp_PayMent_C(Map map)
	{
		List listDate=new ArrayList();
		//1.能申请的必须是有起租日的
		//2.那些业务类型不需要发票(具体类型参考数据字典：业务类型)
//		String NotInvoice="1,8";//不需要发票的业务类型在这个地方已逗号隔开
		String NotInvoice="11";
		if(map==null)
		{
			map=new HashMap();
		}
		map.put("NotInvoice", NotInvoice);
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			map.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			map.put("COM_ID",COM_MAP.get("COMPANY_ID"));
		}
		listDate=Dao.selectList("payment.query_EqSupp_PayMent_C", map);
		return listDate;
	}
	
	//其他费用放款申请页面
	public List query_OTHER_PayMent_C(Map map)
	{
		List listDate=new ArrayList();
		//1.能申请的必须是有起租日的
		//2.那些业务类型不需要发票(具体类型参考数据字典：业务类型)
//		String NotInvoice="1,8";//不需要发票的业务类型在这个地方已逗号隔开
		String NotInvoice="11";
		if(map==null)
		{
			map=new HashMap();
		}
		map.put("NotInvoice", NotInvoice);
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			map.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			map.put("COM_ID",COM_MAP.get("COMPANY_ID"));
		}
		listDate=Dao.selectList("payment.query_OTHER_PayMent_C", map);
		return listDate;
	}
	
	//其他费用放款申请页面按照供应商统计
	public List query_OTHERSupp_PayMent_C(Map map)
	{
		List listDate=new ArrayList();
		//1.能申请的必须是有起租日的
		//2.那些业务类型不需要发票(具体类型参考数据字典：业务类型)
//		String NotInvoice="1,8";//不需要发票的业务类型在这个地方已逗号隔开
		String NotInvoice="11";
		if(map==null)
		{
			map=new HashMap();
		}
		map.put("NotInvoice", NotInvoice);
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			map.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			map.put("COM_ID",COM_MAP.get("COMPANY_ID"));
		}
		listDate=Dao.selectList("payment.query_OTHERSupp_PayMent_C", map);
		return listDate;
	}
	
	//担保费放款申请页面
	public List query_DB_PayMent_C(Map map)
	{
		List listDate=new ArrayList();
		//1.能申请的必须是有起租日的
		//2.那些业务类型不需要发票(具体类型参考数据字典：业务类型)
//		String NotInvoice="1,8";//不需要发票的业务类型在这个地方已逗号隔开
		String NotInvoice="11";
		if(map==null)
		{
			map=new HashMap();
		}
		map.put("NotInvoice", NotInvoice);
		listDate=Dao.selectList("payment.query_DB_PayMent_C", map);
		return listDate;
	}
	
	//担保费放款申请页面按照供应商统计
	public List query_DBSupp_PayMent_C(Map map)
	{
		List listDate=new ArrayList();
		//1.能申请的必须是有起租日的
		//2.那些业务类型不需要发票(具体类型参考数据字典：业务类型)
//		String NotInvoice="1,8";//不需要发票的业务类型在这个地方已逗号隔开
		String NotInvoice="11";
		if(map==null)
		{
			map=new HashMap();
		}
		map.put("NotInvoice", NotInvoice);
		listDate=Dao.selectList("payment.query_DBSupp_PayMent_C", map);
		return listDate;
	}
	
	
	//付款但的保存
	public void payMent_C_Submit(Map mapDate)
	{
		String PAYMENT_HEAD_ID=Dao.getSequence("SEQ_FI_FUND_PAYMENT_HEAD");
		String ID="";
		JSONObject mapSelect = JSONObject.fromObject(mapDate.get("selectDateHidden"));
		List listDate=(List<Map<String, Object>>)mapSelect.get("selectData");
		for (int hh=0;hh<listDate.size();hh++) {
			Map<String, Object> m = (Map<String, Object>) listDate.get(hh);
			//将收款单的ID保存到细表中
			if(m!=null && m.get("payMent_ID")!=null && !m.get("payMent_ID").equals("") && !m.get("payMent_ID").equals("0"))
			{
				m.put("PAYMENT_HEAD_ID", PAYMENT_HEAD_ID);
				ID=m.get("payMent_ID").toString();
				Dao.update("payment.updatePaymentDetailPayID",m);
				Dao.update("payment.updateBuyContractType",m);//更新买卖合同修改状态5-14
			}
		}
		//查询第一个勾选数据的基本信息
		Map map=new HashMap();
		if(ID!=null && !ID.equals(""))
		{
			map.put("pay_ID", ID);
			map=Dao.selectOne("payment.queryPayMentDeatelByPayID", map);
		}
		mapDate.putAll(map);
		mapDate.put("PAYMENT_HEAD_ID", PAYMENT_HEAD_ID);
		//应收单编号
		Map paycodeMap=Dao.selectOne("payment.PayCodeCreate");
		mapDate.put("PAYMENT_CODE", paycodeMap.get("PAYMENT_CODE"));
		
		//当前登录人ID
		mapDate.put("USER_ID", Security.getUser().getId());
		// 组织机构应该取缓存 后面在改
		Map ORG_MAP=Dao.selectOne("payment.orgListByUserId", mapDate);
		if(ORG_MAP!=null){
			mapDate.put("ORG_ID", ORG_MAP.get("ORG_ID"));
		}
		
		Dao.insert("payment.createPayMentHead",mapDate);
	}
	
	//设备放款管理页
	public Page pay_Head_Eq_Mg(Map<String,Object> m)
	{
//		//查询丢失的放款数据  刷入放款表
//		List<Map<String,Object>> listDate=Dao.selectList("payment.selectProidForLosePayData",m);
//		for (int i = 0; i < listDate.size(); i++) {
//			Map<String,Object> mm = listDate.get(i);
//			System.out.println("proid==="+mm.get("ID")+"");
//			createPaymentData(mm.get("ID")+"");
//		}
//	
		
		String ORG_LIST=BaseUtil.getSupOrgChildren();
		m.put("ORG_LIST",ORG_LIST);
		return PageUtil.getPage(m, "payment.pay_Head_Eq_Mg", "payment.pay_Head_Eq_Mg_count");

	}
	
	//其他费用放款管理页
	public Page pay_Head_Other_Mg(Map<String,Object> m)
	{
		String ORG_LIST=BaseUtil.getSupOrgChildren();
		m.put("ORG_LIST",ORG_LIST);
		return PageUtil.getPage(m, "payment.pay_Head_Other_Mg", "payment.pay_Head_Other_Mg_count");

	}
	
	//DB担保费放款管理页
	public Page pay_Head_DB_Mg(Map<String,Object> m)
	{
		return PageUtil.getPage(m, "payment.pay_Head_DB_Mg", "payment.pay_Head_DB_count");

	}
	
	public void PayHead_Status(Map<String,Object> mapDate)
	{
		JSONObject mapSelect = JSONObject.fromObject(mapDate.get("selectDateHidden"));
		List listDate=(List<Map<String, Object>>)mapSelect.get("selectData");
		
		for (int hh=0;hh<listDate.size();hh++) {
			String ID=null;
			Map<String, Object> m = (Map<String, Object>) listDate.get(hh);
			//将收款单的ID保存到细表中
			if(m!=null && m.get("ID")!=null && !m.get("ID").equals("") && !m.get("ID").equals("0"))
			{
				m.put("STATUS", mapDate.get("STATUS"));
				if("7".equals(mapDate.get("STATUS")+"")){
					new JbpmFiService().pay_Head_Eq_Del_Supper(m);
				}
				Dao.update("payment.PayHead_Status",m);
				ID=m.get("ID")+"";
				
				//后加-通过放款日期设定起租日
				//如果已经设定不再设定起租日
				List mapPayList=Dao.selectList("payment.queryPaymentStartDayByID",m);
				for(int ii=0;ii<mapPayList.size();ii++){
					Map mapPay=(Map)mapPayList.get(ii);
					if(mapPay !=null && (mapPay.get("START_DATE")==null || mapPay.get("START_DATE").equals(""))){
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
									for(int gg=0;gg<irrList.size();gg++){
										//"zj":"11919.33","PMTzj":"12308.22","bj":"11141.55","lx":"777.78","sybj":"128858.45","qc":"1","PAY_DATE":"2015-09-15","glf":"0.0","sxf":"0","lxzzs":"0.00"
										Map mapIrr=irrList.get(gg);
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
			
			
			
			if(mapDate.get("EQ")!=null && !mapDate.get("EQ").equals("") && mapDate.get("EQ").equals("EQ"))//设备放款，存在二次放款(核销)
			{
				Dao.update("payment.updatePayDetailStatus",m);//修改细表已核销状态
				//核销额度保证金
				Dao.update("payment.updateCreditFundByHeadid",m);
				Dao.update("payment.updateCreditAccountByHeadid",m);
				//设备放款判断是否有抵扣金额，有的话向租金池里放一条抵扣金额
				this.payMentPool(m);
				
				//先查询那些是存在二次放款的一次放款（将要修改二次放款的状态）
				//List date=Dao.selectList("payment.queryOneYouTwo",m);
				//Dao.update("payment.updateTwoPayMentS",m);
//				String IDS="0";
//				for(int num=0;num<date.size();num++){
//					
//					Map dateMap=(Map)date.get(num);
//					IDS=IDS+","+dateMap.get("ID").toString();
//					
//					Map map=Dao.selectOne("payment.queryPayMentDeProHead", dateMap);
//					if(map!=null && map.get("PAY_MONEY")!=null && !map.get("PAY_MONEY").equals(""))
//					{
//						Dao.update("payment.updateProj_sub1",map);//存在二次放款没有放款，说明是一次放款修改成+70
//					}
//				}
//				
				//该放款单去除上面去除的ID放款
//				m.put("IDS", IDS);
//				
//				List dateList=Dao.selectList("payment.queryPayMentOneList",m);
//				for(int number=0;number<dateList.size();number++){
//					Map dateMap=(Map)dateList.get(number);
//					Map map=Dao.selectOne("payment.queryPayMentDeProHead", dateMap);
//					if(map!=null && map.get("PAY_MONEY")!=null && !map.get("PAY_MONEY").equals(""))
//					{
//						if(map.get("PAY_NAME")!=null && !"70﹪款".equals(map.get("PAY_NAME")+"")){ //排除存在二次放款的第一次放款
//							Dao.update("payment.updateProj_sub2",map);//如果不存在二次放款没有放款及一次放全款或者二次放款修改为100
//						}
//					}
//				}
				//核销时候查询该放款单为一次放款（不用区分有没有二次放款）并且是差额放款就抵扣首期款
				
//				List<Map<String,Object>> listSQ=Dao.selectList("payment.queryChaeShouQi",m);
//				FundNotEBankService ebankService=new FundNotEBankService();
//				if(listSQ.size()>0){
//					for (int i = 0; i < listSQ.size(); i++) {
//						Map<String,Object> paymap = listSQ.get(i);
//						String PAYMENT_STATUS = paymap.get("VALUE_STR") == null?"":paymap.get("VALUE_STR")+"";
//						if(!"".equals(PAYMENT_STATUS) && "2".equals(PAYMENT_STATUS) ){
//							//2:差额放款
//							ebankService.CHEFKhexiao(paymap);
//						}else if(!"".equals(PAYMENT_STATUS) && "3".equals(PAYMENT_STATUS) ){
//							//3:部分差额放款
//							ebankService.CHEFKhexiao_BFCE(paymap);
//						}else if(!"".equals(PAYMENT_STATUS) && "4".equals(PAYMENT_STATUS) ){
//							//4:DB保证金差额放款
//							ebankService.CHEFKhexiao_DBCE(paymap);
//						}
//					}
//				}
			}

//			if(mapDate!=null && mapDate.get("FLAG")!=null){
//				String FLAG=mapDate.get("FLAG").toString();
//				//货款支付申请
//				if(FLAG.equals("1"))//设备款发起流程
//				{
//					List<String> jbpmList=JBPM.getDeploymentListByModelName("purchasePricePaid");
//					if(!jbpmList.isEmpty()&&jbpmList.size()>0){
//						Map<String,Object> jm=new HashMap<String,Object>();
//						jm.put("PAYMENT_HEAD_ID", ID);
//						jm.put("SUPPLIER_ID", m.get("SUPPER_ID"));
//						JBPM.startProcessInstanceById(jbpmList.get(0), null, null, null, jm);
//					}
//				}
			//}
		}

	}
	
	public void clZqxPay(Map<String,Object> map){
		
			//1.查询起租日和还款日
			Map DateInfoByPay=Dao.selectOne("payment.queryDateInfoByPayId", map);
			if(DateInfoByPay !=null && DateInfoByPay.get("START_DATE") !=null && DateInfoByPay.get("REPAYMENT_DATE")!=null){
				//保存主表起租日和还款日
				//查询起租日和还款日之间利息差
				//计算起租日期和还款日期利息差
				Map mapbase=Dao.selectOne("payment.queryHeadInfoByPayId", map);
				double DateLXMoney=0d;
				double residualPrincipal=mapbase.get("FINANCE_TOPRIC")!=null ? Double.parseDouble(mapbase.get("FINANCE_TOPRIC").toString()):0d;
				double annualRate=mapbase.get("YEAR_INTEREST")!=null ? Double.parseDouble(mapbase.get("YEAR_INTEREST").toString()):0d;
				DateLXMoney=this.getDateLiXiMoney(DateInfoByPay.get("START_DATE")+"", DateInfoByPay.get("REPAYMENT_DATE")+"", residualPrincipal, annualRate);
				
				map.put("ID", map.get("PAY_ID"));
				List detailList=this.doShowRentPlanDetail(map);
				if(DateLXMoney>0 && detailList.size()>0){
					Map mapOne=(Map)detailList.get(0);
					mapOne.put("ZJ", (Double.parseDouble(mapOne.get("ZJ").toString())+DateLXMoney));
					mapOne.put("LX", (Double.parseDouble(mapOne.get("LX").toString())+DateLXMoney));
					
					//修改支付表信息
					Dao.update("payment.updatePlanDetailOneZJ",mapOne);
					Dao.update("payment.updatePlanDetailOneLX",mapOne);
					//修改应收期初表利息
					Dao.update("payment.updateBeginningDetailOneLX",mapOne);
					
					
				}
				//将租前息存入支付表头上
				map.put("ZQX_MONEY", DateLXMoney);
				Dao.update("payment.updateHeadZQX",map);
				
			}
		
		
		
	}
	
	// 计算起租日和还款日中间的利息差
		/**
		 * date:起租日 date1：还款日 residualPrincipal:融资额 annualRate：年利率
		 */
		public double getDateLiXiMoney(String date, String date1,
				double residualPrincipal, double annualRate) {
			Map map = new HashMap();
			map.put("date", date);
			map.put("date1", date1);
			map.put("residualPrincipal", residualPrincipal);
			map.put("annualRate", annualRate);
			return Dao.selectDouble("payment.getDateLiXiMoneyNew", map);
		}
	
	public List<Map<String, Object>> doShowRentPlanDetail(
			Map<String, Object> map) {
		List<Map<String, Object>> list = Dao.selectList(
				"payment.doShowRentPlanDetail", map);
		if (list.isEmpty() || list.size() < 1) {
			return list;
		}
		return list;
	}
	

	public void PayHead_Status_Submit(Map<String,Object> mapDate)
	{
		JSONObject mapSelect = JSONObject.fromObject(mapDate.get("selectDateHidden"));
		List listDate=(List<Map<String, Object>>)mapSelect.get("selectData");
		
		//将多个核销单发起一个流程
		String JBPM_ID=Dao.getSequence("SEQ_PAYMENT_HEAD_JBPM");
		String SUPPER_ID="";
		
		for (int hh=0;hh<listDate.size();hh++) {
			String ID=null;
			Map<String, Object> m = (Map<String, Object>) listDate.get(hh);
			//将收款单的ID保存到细表中
			if(m!=null && m.get("ID")!=null && !m.get("ID").equals("") && !m.get("ID").equals("0"))
			{
				m.put("STATUS", mapDate.get("STATUS"));
				m.put("JBPM_ID", JBPM_ID);
				Dao.update("payment.PayHead_Status",m);
				ID=m.get("ID")+"";
			}
			
			if(m!=null && m.get("SUPPER_ID")!=null && !m.get("SUPPER_ID").equals("") && !m.get("SUPPER_ID").equals(""))
			{
				SUPPER_ID= m.get("SUPPER_ID").toString();
			}
			
			Dao.update("payment.updatePayDetailStatusForSubmit",m);//修改细表已核销状态
		}
		
		if(mapDate!=null && mapDate.get("FLAG")!=null){
			String FLAG=mapDate.get("FLAG").toString();
			//货款支付申请
			if(FLAG.equals("1"))//设备款发起流程
			{
//				List<String> jbpmList=JBPM.getDeploymentListByModelName("purchasePricePaid");
				List<String> jbpmList = JBPM.getDeploymentListByModelName("付款审批流程","", Security.getUser().getOrg().getPlatformId());
				if(!jbpmList.isEmpty()&&jbpmList.size()>0){
					Map<String,Object> jm=new HashMap<String,Object>();
					jm.put("PAYMENT_JBPM_ID", JBPM_ID);
					jm.put("SUPPLIER_ID",SUPPER_ID);
					ProcessInstance instance = JBPM.startProcessInstanceById(jbpmList.get(0), null,null , null, jm);
					mapDate.put("JBPM_ID", instance.getId());
				}
			}
		}
	}
	
	
	public void PayHead_De(Map<String,Object> mapDate)
	{
		JSONObject mapSelect = JSONObject.fromObject(mapDate.get("selectDateHidden"));
		List listDate=(List<Map<String, Object>>)mapSelect.get("selectData");
		for (int hh=0;hh<listDate.size();hh++) {
			Map<String, Object> m = (Map<String, Object>) listDate.get(hh);
			//将收款单的ID保存到细表中
			if(m!=null && m.get("ID")!=null && !m.get("ID").equals("") && !m.get("ID").equals("0"))
			{
				m.put("STATUS", mapDate.get("STATUS"));
				Dao.update("payment.updateBuyContarctTypeStatus",m);//更新买卖合同修改状态，变可修改
				Dao.update("payment.updatePayDetail_headID",m);
				Dao.delete("payment.deletePayHead_ID",m);
			}
		}
	}
	
	
	//设备放款管理页
	public Page pay_HeadBack_Eq_Mg(Map<String,Object> m)
	{
		return PageUtil.getPage(m, "payment.pay_HeadBack_Eq_Mg", "payment.pay_HeadBack_Eq_Mg_count");

	}
	
	//其他费用放款管理页
	public Page pay_HeadBack_Other_Mg(Map<String,Object> m)
	{
		return PageUtil.getPage(m, "payment.pay_HeadBack_Other_Mg", "payment.pay_HeadBack_Other_Mg_count");

	}
	
	//DB担保费放款管理页
	public Page pay_HeadBack_DB_Mg(Map<String,Object> m)
	{
		return PageUtil.getPage(m, "payment.pay_HeadBack_DB_Mg", "payment.pay_HeadBack_DB_count");

	}
	
	
	/**
	 * 实际费用查询
	 * 
	 * @param params
	 * @return
	 * @author : gengchangbao @ 2016年3月3日
	 */
	public List<Map<String,Object>> getSlsjfyInfo(Map<String,Object> mm){
		return Dao.selectList(xmlPath+"getSlsjfyInfo",mm);
		
	}
	
	/**
	 * 查询重签合同前版【废弃】项目编号
	 * @author : gengchangbao @ 2016年3月3日
	 * @param map
	 * @return
	 */
	public List<Map<String, Object>> queryParentProjectById(Map<String,Object> param) {
		return Dao.selectList("project.queryParentProjectById", param);
	}
	
	/**
	 * 根据ProjectId得到ProjectDetailBase类型和价格
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> findReSignXmmxlist(Map<String,Object> param){
		return Dao.selectList("project.findReSignXmmxlist", param);
	}
	
	/**
	 * 根据ProjectId得到ProjectDetailBase类型和价格
	 * @author : gengchangbao @ 2016年3月3日
	 * @param param
	 * @return
	 */
	public List<Map<String,Object>> findXmmxlist(Map<String,Object> param){
		return Dao.selectList("project.findXmmxlist", param);
	}
	
	/**
	 *  查询该项目所选择的设备
	 * @param param
	 * @author : gengchangbao @ 2016年3月3日
	 * @return
	 */
	public List<Map<String,Object>> queryEqByProjectIDByScheme(Map<String,Object> param) {
		param.put("tags", "设备单位");
		return Dao.selectList("project.queryEqByProjectIDByScheme", param);
	}
	
	
//	/**
//	 * 重签项目费用明细查看
//	 * 
//	 * @param params
//	 * @return
//	 * @author : gengchangbao @ 2016年3月3日
//	 */
//	public void getReSignPayHeadDataByProId(VelocityContext context, Map<String,Object> m) {
//		
//		getReSignPayHead_feeRemarks(context,m);
//		Map schemeMoney = new HashMap();
//		schemeMoney = getReSignPayHeadDataByProId_EQUIPMENT(m);
//		context.put("schemeMoney", schemeMoney);
//		
//		Map detaillist = new HashMap();
//		detaillist = getReSignPayHeadDataByProId_FEEDETAIL(context, m, schemeMoney);
//		
//		
//	}
	
	/**
	 * 重签项目费用明细备注信息查询
	 * 
	 * @param params
	 * @return
	 * @author : gengchangbao @ 2016年3月3日
	 */
	public void getReSignPayHead_feeRemarks(VelocityContext context, Map<String,Object> m) {
		
		List<Map<String,Object>> reSignDataList = Dao.selectList("payment.getFeedetail_remarks",m);
		if (reSignDataList == null || reSignDataList.size() < 1) {
			for (int i = 1; i < 11; i++) {
				Map mapPool=new HashMap();
				mapPool.put("PROJECT_ID", m.get("PROJECT_ID"));
				mapPool.put("TYPE_ID", i);
				mapPool.put("REMARKS", "");
				Dao.insert("payment.createFeedetail_remarks",mapPool);
			}
			reSignDataList = Dao.selectList("payment.getFeedetail_remarks",m);
		}
		
		// 实付金额【废弃合同】
		Map<String, Object> remarks = new HashMap<String, Object>();
		for (Map<String, Object> map : reSignDataList) {
			remarks.put("remarks"+map.get("TYPE_ID"), map.get("REMARKS"));
		}
		context.put("remarksList",remarks);
	}
	
	/**
	 * 重签项目费用明细备注信息修改
	 * 
	 * @param params
	 * @return
	 * @author : gengchangbao @ 2016年3月3日
	 */
	public void saveReSignPayHeadUpdData(Map<String,Object> m) {
		for (int i=1; i<11; i++) {
			Map map=new HashMap();
			map.put("PROJECT_ID", m.get("PROJECT_ID"));
			map.put("TYPE_ID", i);
			map.put("REMARKS",  m.get("REMARKS"+i));
			Dao.update("payment.updateFeedetail_remarks",map);
		}
		
		
	}
	
//	/**
//	 * 重签项目费用明细查看
//	 * 
//	 * @param params
//	 * @return
//	 * @author : gengchangbao @ 2016年3月3日
//	 */
//	public Map getReSignPayHeadDataByProId_FEEDETAIL(VelocityContext context, Map<String,Object> m, Map schemeMoney) {
//		
//		List<Map<String,Object>> reSignDataList = Dao.selectList("payment.getReSignPayHeadData_FEEDETAIL",m);
//		if (reSignDataList != null && reSignDataList.size() > 0) {
//			reSignDataList = Dao.selectList("payment.findReSignXmmxlist",m);
//		}
//		
//		// 实付金额【废弃合同】
//		Map<String, Object> detail = new HashMap<String, Object>();
//		// 差价
//		Map<String, Object> difference = new HashMap<String, Object>();
//		
//		BigDecimal sfje; //作废合同实付金额
//		BigDecimal yfje; //重签合同应付金额
//		BigDecimal cj; //差价
//		Map<String, Object> initMap = new HashMap<String, Object>();
//		initMap.put("APPLY_MONEY", "0");
//		detail.put("CLSJ", initMap);	//车辆实际采购价
//		detail.put("CLGZS", initMap);	//车辆购置税
//		detail.put("SYBX", initMap);	//商业险
//		detail.put("JQX", initMap);		//交强险
//		detail.put("CCS", initMap);		//车船税
//		detail.put("LQF", initMap);		//路桥费
//		detail.put("HBFY", initMap);	//环保标费
//		detail.put("SPF", initMap);		//上牌费
//		detail.put("LPF", initMap);		//临牌费
//		detail.put("QTFY", initMap);	//其他费用
//		for (Map<String, Object> map : reSignDataList) {
//			sfje = new BigDecimal(0);
//			String TYPEID = map.get("TYPEID")==null?"":map.get("TYPEID").toString();
//			
//			if("1".equals(TYPEID)){
//				detail.put("CLSJ", map);	//车辆实际采购价
//				if (map.get("APPLY_MONEY") != null) {
//					sfje = new BigDecimal(map.get("APPLY_MONEY").toString());
//				}
//				yfje = new BigDecimal(schemeMoney.get("money1").toString());
//				cj = yfje.subtract(sfje);
//				difference.put("difference1", cj);
//			}else if("2".equals(TYPEID)){
//				detail.put("CLGZS", map);	//车辆购置税
//				if (map.get("APPLY_MONEY") != null) {
//					sfje = new BigDecimal(map.get("APPLY_MONEY").toString());
//				}
//				yfje = new BigDecimal(schemeMoney.get("money2").toString());
//				cj = yfje.subtract(sfje);
//				difference.put("difference2", cj);
//			}else if("3".equals(TYPEID)){
//				detail.put("SYBX", map);	//商业险
//				if (map.get("APPLY_MONEY") != null) {
//					sfje = new BigDecimal(map.get("APPLY_MONEY").toString());
//				}
//				yfje = new BigDecimal(schemeMoney.get("money3").toString());
//				cj = yfje.subtract(sfje);
//				difference.put("difference3", cj);
//			}else if("4".equals(TYPEID)){
//				detail.put("JQX", map);		//交强险
//				if (map.get("APPLY_MONEY") != null) {
//					sfje = new BigDecimal(map.get("APPLY_MONEY").toString());
//				}
//				yfje = new BigDecimal(schemeMoney.get("money4").toString());
//				cj = yfje.subtract(sfje);
//				difference.put("difference4", cj);
//			}else if("5".equals(TYPEID)){
//				detail.put("CCS", map);		//车船税
//				if (map.get("APPLY_MONEY") != null) {
//					sfje = new BigDecimal(map.get("APPLY_MONEY").toString());
//				}
//				yfje = new BigDecimal(schemeMoney.get("money5").toString());
//				cj = yfje.subtract(sfje);
//				difference.put("difference5", cj);
//			}else if("6".equals(TYPEID)){
//				detail.put("LQF", map);		//路桥费
//				if (map.get("APPLY_MONEY") != null) {
//					sfje = new BigDecimal(map.get("APPLY_MONEY").toString());
//				}
//				yfje = new BigDecimal(schemeMoney.get("money6").toString());
//				cj = yfje.subtract(sfje);
//				difference.put("difference6", cj);
//			}else if("7".equals(TYPEID)){
//				detail.put("HBFY", map);	//环保标费
//				if (map.get("APPLY_MONEY") != null) {
//					sfje = new BigDecimal(map.get("APPLY_MONEY").toString());
//				}
//				yfje = new BigDecimal(schemeMoney.get("money7").toString());
//				cj = yfje.subtract(sfje);
//				difference.put("difference7", cj);
//			}else if("8".equals(TYPEID)){
//				detail.put("SPF", map);		//上牌费
//				if (map.get("APPLY_MONEY") != null) {
//					sfje = new BigDecimal(map.get("APPLY_MONEY").toString());
//				}
//				yfje = new BigDecimal(schemeMoney.get("money8").toString());
//				cj = yfje.subtract(sfje);
//				difference.put("difference8", cj);
//			}else if("9".equals(TYPEID)){
//				detail.put("LPF", map);		//临牌费
//				if (map.get("APPLY_MONEY") != null) {
//					sfje = new BigDecimal(map.get("APPLY_MONEY").toString());
//				}
//				yfje = new BigDecimal(schemeMoney.get("money9").toString());
//				cj = yfje.subtract(sfje);
//				difference.put("difference9", cj);
//			}else if("10".equals(TYPEID)){
//				detail.put("QTFY", map);	//其他费用
//				if (map.get("APPLY_MONEY") != null) {
//					sfje = new BigDecimal(map.get("APPLY_MONEY").toString());
//				}
//				yfje = new BigDecimal(schemeMoney.get("money10").toString());
//				cj = yfje.subtract(sfje);
//				difference.put("difference10", cj);
//			}
//		}
//		
//		context.put("detaillist",detail);
//		context.put("differencelist",difference);
//		return detail;
//	}
	
	/**
	 * 获取合同编号
	 * 
	 * @param params
	 * @return
	 * @author : gengchangbao @ 2016年3月3日
	 */
	public Map getReSignLeaseCode(Map<String,Object> m) {
		List<Map<String,Object>> newDataList = Dao.selectList("payment.getReSignLeaseCode",m);
		Map<String,Object> mapJson = newDataList.get(0);
		return mapJson;
	}
	
//	/**
//	 * 重签项目费用明细查看
//	 * 
//	 * @param params
//	 * @return
//	 * @author : gengchangbao @ 2016年3月3日
//	 */
//	public Map getReSignPayHeadDataByProId_EQUIPMENT(Map<String,Object> m) {
//		List<Map<String,Object>> newDataList = Dao.selectList("project.findXmmxlist", m);
//		//List<Map<String,Object>> newDataList = Dao.selectList("payment.getReSignPayHeadData_EQUIPMENT",m);
//		//List eqList = service.queryEqByProjectIDByScheme(map);
//		
//		BigDecimal sfje; //作废合同实付金额
//		BigDecimal yfje; //重签合同应付金额
//		BigDecimal cj; //差价
//		Map mapJson = new HashMap();
//		if(newDataList != null && newDataList.size()>0){
//			Map obj = (Map) newDataList.get(0);
//			mapJson.put("money4", obj.get("JQX"));		 // 交强险
//			
//			mapJson.put("money5", obj.get("CCS"));		 // 车船税
//			
//			mapJson.put("money3", obj.get("BX"));		 // 商业险
//			
//			mapJson.put("money1", obj.get("UNIT_PRICE"));// 保存车辆实际采购价
//		}else{
//			mapJson.put("money1", "0");// 保存车辆实际采购价
//		}
//		BaseSchemeService bsService = new BaseSchemeService();
//		List<Map<String, Object>> schemeDetail = bsService.getSchemeClob_new(m.get("PROJECT_ID").toString());
//		for (Map<String, Object> map2 : schemeDetail) {
//			if(map2.get("KEY_NAME_EN").equals("GZS_MONEY")){
//				// 车辆购置税
//				mapJson.put("money2", map2.get("VALUE_STR"));
//				
//			}else if(map2.get("KEY_NAME_EN").equals("SHANGPAI_MONEY")){
//				// 上牌费
//				mapJson.put("money8", map2.get("VALUE_STR"));
//				
//			}else if(map2.get("KEY_NAME_EN").equals("KEEP_MONEY")){
//				// 路桥费
//				mapJson.put("money6", map2.get("VALUE_STR"));
//				
//			}else if(map2.get("KEY_NAME_EN").equals("MOUNT_MONEY")){ // modify by lishuo 12.23.2015 临牌费  原先是GPS费
//				// 临牌费
//				mapJson.put("money9", map2.get("VALUE_STR"));
//				
//			}else if(map2.get("KEY_NAME_EN").equals("YCZBF_MONEY")){// modify by lishuo 12.23.2015 环保标费  原先是延长zhibaofei费
//				// 环保标费
//				mapJson.put("money7", map2.get("VALUE_STR"));
//				
//			}else if(map2.get("KEY_NAME_EN").equals("QITA_MONEY")){
//				// 其他费用
//				mapJson.put("money10", map2.get("VALUE_STR"));
//			}
//		}
//		
//		for (int i = 1; i < 11; i++) {
//			if (mapJson.get("money"+i) == null || "".equals(mapJson.get("money"+i))) {
//				mapJson.put("money"+i, "0");	
//			}
//		}
//		
//		return mapJson;
//	}
	
	
	/**
	 * 重签一览画面数据金额计算整理
	 * 
	 * @param params
	 * @return
	 * @author : gengchangbao @ 2016年3月3日
	 */
	public Page getReSignPayHeadListData(Map<String,Object> m) {
		
		List<Map<String,Object>> dataList = Dao.selectList("payment.getReSignPayHeadListData",m);
		//List eqList = service.queryEqByProjectIDByScheme(map);
		Map obj = null;
		BigDecimal sfje; //作废合同实付金额
		BigDecimal yfje; //重签合同应付金额
		BigDecimal cj; //差价
		
		if(dataList != null && dataList.size()>0){
			Map<String, Object> projectMap = new HashMap<String, Object>();
			//BaseSchemeService bsService = new BaseSchemeService();
			Map<String, Object> schemeDetail = null;
			for (int i = 0; i < dataList.size(); i++) {
				obj = (Map) dataList.get(i);
				yfje = new BigDecimal(0);
				if (obj.get("REVOKED_MONEY") != null) {
					sfje = new BigDecimal(obj.get("REVOKED_MONEY").toString());
				} else {
					sfje = new BigDecimal(0);
				}
				projectMap.put("PROJECT_ID", obj.get("PRO_ID"));
				//List<Map<String, Object>> schemeDetail = bsService.getSchemeClob_new(obj.get("PRO_ID").toString());
				schemeDetail = Dao.selectOne("payment.getPayHeadDataMoneySum",projectMap);
				if (schemeDetail != null && schemeDetail.size() > 0) {
					if (schemeDetail.get("APP_MONEY_SUM") != null && !"".equals(schemeDetail.get("APP_MONEY_SUM"))){
						yfje = new BigDecimal(schemeDetail.get("APP_MONEY_SUM").toString());
					} 
				}
				
				cj = yfje.subtract(sfje);
				obj.put("REVOKED_MONEY", sfje);
				obj.put("RESIGN_MONEY", yfje);
				obj.put("DIFFERENCE", cj);
			}
			
		}
		
		
		Page page = new Page(m);
		JSONArray array = JSONArray.fromObject(dataList);
		
		page.addDate(array, Dao.selectInt("payment.getReSignPayHeadListData_count", m));
		return page;
	}
	
	//设备放款管理页
	public Page pay_Head_Query_Eq_Mg(Map<String,Object> m)
	{
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			m.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			m.put("COM_ID",COM_MAP.get("COMPANY_ID"));
		}
		return PageUtil.getPage(m, "payment.pay_Head_Query_Eq_Mg", "payment.pay_Head_Query_Eq_Mg_count");

	}
	
	//设备放款导出
	public List<Map<String,Object>> pay_Head_Excle_Eq_Mg(Map<String,Object> m)
	{
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			m.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			m.put("COM_ID",COM_MAP.get("COMPANY_ID"));
		}
		return Dao.selectList("pay_Head_Excle_Eq_Mg", m);
	}
	
	//设备放款导出
	public Map<String,Object> pay_Head_Excle_Eq_Sum(Map<String,Object> m)
	{
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			m.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			m.put("COM_ID",COM_MAP.get("COMPANY_ID"));
		}
		return Dao.selectOne("pay_Head_Excle_Eq_Sum", m);
	}
	
	//其他费用放款管理页
	public Page pay_Head_Query_Other_Mg(Map<String,Object> m)
	{
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			m.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			m.put("COM_ID",COM_MAP.get("COMPANY_ID"));
		}
		return PageUtil.getPage(m, "payment.pay_Head_Query_Other_Mg", "payment.pay_Head_Query_Other_Mg_count");

	}
	
	//DB担保费放款管理页
	public Page pay_Head_Query_DB_Mg(Map<String,Object> m)
	{
		return PageUtil.getPage(m, "payment.pay_Head_Query_DB_Mg", "payment.pay_Head_Query_DB_count");

	}
	
	//设备放款明细管理页
	public Page pay_Detail_Eq_Mg(Map<String,Object> m)
	{
//		String NotInvoice="1,8";//不需要发票的业务类型在这个地方已逗号隔开
		String NotInvoice="11";
		if(m==null)
		{
			m=new HashMap();
		}
		m.put("NotInvoice", NotInvoice);
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			m.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			m.put("COM_ID",COM_MAP.get("COMPANY_ID"));
		}
		return PageUtil.getPage(m, "payment.pay_Detail_Eq_Mg", "payment.pay_Detail_Eq_Mg_count");

	}
	
	//其他费用放款明细管理页
	public Page pay_Detail_Other_Mg(Map<String,Object> m)
	{
//		String NotInvoice="1,8";//不需要发票的业务类型在这个地方已逗号隔开
		String NotInvoice="11";
		if(m==null)
		{
			m=new HashMap();
		}
		m.put("NotInvoice", NotInvoice);
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			m.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			m.put("COM_ID",COM_MAP.get("COMPANY_ID"));
		}
		return PageUtil.getPage(m, "payment.pay_Detail_Other_Mg", "payment.pay_Detail_Other_Mg_count");

	}
	
	//DB担保费放款明细管理页
	public Page pay_Detail_DB_Mg(Map<String,Object> m)
	{
//		String NotInvoice="1,8";//不需要发票的业务类型在这个地方已逗号隔开
		String NotInvoice="11";
		if(m==null)
		{
			m=new HashMap();
		}
		m.put("NotInvoice", NotInvoice);
		return PageUtil.getPage(m, "payment.pay_Detail_DB_Mg", "payment.pay_Detail_DB_Mg_count");

	}
	/**
	 * 根据付款单id查询对应供应商下逾期的项目情况
	 * @param PAYMENT_HEAD_ID
	 * @return
	 * @author:King 2013-10-26
	 */
	public List<Map<String,Object>> showPayOverList(String PAYMENT_HEAD_ID){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("PAYMENT_HEAD_ID", PAYMENT_HEAD_ID);
		map.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		return Dao.selectList("fi.overdue.showPayOverList", map);
	}
	
	/**
	 * 查看一个供应商到今天为止的逾期金额及台数的逾期率
	 * @param SUPPLIERS_ID 供应商id
	 * @return
	 * @author:King 2013-10-26
	 */
	public Map<String,Object> showOverDuePercent(String SUPPLIERS_ID){
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("SUPPER_ID", SUPPLIERS_ID);
		map.put("CREATE_DATE", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		return Dao.selectOne("fi.overdue.showOverDuePercent", map);
	}
	
	//PAYMENT_HEAD_ID=18935&MONEY=14092&RATIO=10&LASTMONEY=126828
	public void jbpmByPayMentPassStatue(String PAYMENT_JBPM_ID)
	{
		this.PayMentJBPMPassStatue(PAYMENT_JBPM_ID);
	}
	
	public void PayMentPassStatue(String PAYMENT_HEAD_ID,double money,double rate,double laseMoney,double ACCBILL_PRICE,double BANKBILL_PRICE)
	{
		Map map=new HashMap();
		map.put("PAYMENT_HEAD_ID", PAYMENT_HEAD_ID);
		map.put("MONEY", money);
		map.put("RATE", rate);
		map.put("LASEMONEY", laseMoney);

		map.put("ACCBILL_PRICE", ACCBILL_PRICE);
		map.put("BANKBILL_PRICE", BANKBILL_PRICE);
		Dao.update("payment.PayMentPassStatue",map);
	}
	
	public void PayMentJBPMPassStatue(String PAYMENT_JBPM_ID)
	{
		Map map=new HashMap();
		map.put("JBPM_ID", PAYMENT_JBPM_ID);
		Dao.update("payment.PayMentJBPMPassStatue",map);
	}
	
	
	public void jbpmByPayMentNotPassStatue(String PAYMENT_JBPM_ID)
	{
		this.PayMentJBPMNotPassStatue(PAYMENT_JBPM_ID);
	}
	
	public void PayMentJBPMNotPassStatue(String PAYMENT_JBPM_ID)
	{
		Map map=new HashMap();
		map.put("JBPM_ID", PAYMENT_JBPM_ID);
		//修改放款单状态，以及将JBPMID等值置为null
		Dao.update("payment.PayMentJBPMNotPassStatue",map);
	}
	
	//设备放款判断是否有抵扣金额，有的话向租金池里放一条抵扣金额
	public void payMentPool(Map map){
		if(map!=null && map.get("ID")!=null && !map.get("ID").equals("")){
			Map mapDate=Dao.selectOne("payment.payMentPool", map);
			if(mapDate!=null){
				double money=Double.parseDouble(mapDate.get("DEDUCTION_MONEY").toString());
				if(money>0){
					Map mapPool=new HashMap();
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
	
	public int updatePayMent(Map map){
		Dao.update("payment.updatePayMentDeatilDate",map);
		
		//如果该放款单下的放款明细，如果有委托租赁，则将其买卖合同银行信息更新（委托租赁没有买卖合同，此虚拟买卖合同是核销数据时候刷新过来的）
		List list=Dao.selectList("payment.queryBuyConIdByPl7", map);//查询该放款但下委托租赁的虚拟买卖合同ID
		//修改买卖合同下的银行信息
		for(int i=0;i<list.size();i++){
			Map buyMap=(Map)list.get(i);
			buyMap.putAll(map);
			Dao.update("payment.updateBuyConBank", buyMap);
		}
		return Dao.update("payment.updatePayMent",map);
	}
	
	// 导出
	public Excel EQ_Upload(Map map) {
		String type="1";
		if(map!=null && map.get("type")!=null){
			type=map.get("type").toString();
		}
		
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			map.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}

		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			map.put("COM_ID",COM_MAP.get("COMPANY_ID"));
		}
		List<Map<String, Object>> dataList = Dao.selectList("payment.EQ_UPLOAD_DATE",map);
		Excel excel = new Excel();
		excel.addSheetName("sheet01");
		excel.addData(dataList);
		LinkedHashMap<String, String> title = new LinkedHashMap<String, String>();

		title.put("ROW_NUM", "序号");
		title.put("STATUS_FLAG", "状态");
		title.put("SUPPER_NAME", "供应商");
		title.put("PROJECT_CODE", "项目编号");
		title.put("CUST_NAME", "客户名称");
		title.put("COMP_NAME", "厂商");
		title.put("PRODUCT_NAMES", "租赁物名称");
		title.put("ENGINE_TYPES", "机型");
		title.put("WHOLE_ENGINE_CODES", "出厂编号");
		title.put("EQUIPMENT_AMOUNTS", "台量");
		title.put("DELIVERY_DATE", "交货时间");
		title.put("DELIVERY_ADDR", "交货地点");
		title.put("START_DATE", "起租确认日");
		title.put("PAY_MONEY", "实际放款金额");
		title.put("PAY_NAME", "款项名称");
		title.put("RESERVE_DATE", "预付日期");
		title.put("PAYEE_NAME", "收款单位");
		title.put("PAY_BANK_ACCOUNT", "放款账号");
		title.put("INVOICE_NUM", "发票号");
		title.put("INVOICE_DATE", "租赁方式（直租/回租）");
		title.put("PAYMENT_NAME", "放款方式");
		excel.addTitle(title);

		excel.setHasTitle(true);
		return excel;
	}
	
	//设备放款明细管理页
	public List pay_Detail_Query_Eq_Date(Map<String,Object> m)
	{
		return Dao.selectList("payment.pay_Detail_Query_Eq_Date", m);
	}

	public void pay_Head_Excle_Mg(Map<String, Object> param) {
		OutputStream os;
		try {

			
			String[] titlesName = new String[]{"序号","供应商",	"厂商",	"收款单位",	"开户行行名",	"开户行所在地",	"帐号",	"付款金额"};
			String[] titlesName2 = new String[]{"序号","供应商",	"厂商",	"收款单位",	"开户行行名",	"开户行所在地",	"帐号",	"付款金额",	"付款比例",	"实际电汇金额"};
			String[] titlesName3 = new String[]{"序号","供应商",	"厂商",	"收款单位",	"开户行行名",	"开户行所在地",	"帐号",	"应付租赁物价款金额",	"扣款比例",	"扣款金额"};
			
			
			os = SkyEye.getResponse().getOutputStream();
			XSSFWorkbook xssf_w_book = new XSSFWorkbook();
			XSSFSheet xssf_w_sheet = xssf_w_book.createSheet("租赁物购买价款支付总表");
			XSSFSheet xssf_w_sheet2 = xssf_w_book.createSheet("租赁物购买价款支付电汇明细表");
			XSSFSheet xssf_w_sheet3 = xssf_w_book.createSheet("租赁物购买价款支付扣款明细表");
			XSSFRow xssf_w_row = null;// 创建一行
			XSSFRow xssf_w_row2 = null;// 创建一行
			XSSFRow xssf_w_row3 = null;// 创建一行
			XSSFCell xssf_w_cell = null;// 创建每个单元格
			int col_count = 0;
			int col_count2 = 0;
			int col_count3 = 0;
			int row_count = 0;
			int row_count2 = 0;
			int row_count3 = 0;
			col_count = titlesName.length;
			col_count2 = titlesName2.length;
			col_count3 = titlesName3.length;
			
			XSSFDataFormat format = xssf_w_book.createDataFormat();

			// 创建标题行样式
			XSSFCellStyle head_cellStyle = xssf_w_book.createCellStyle();
			XSSFFont head_font = xssf_w_book.createFont();
			head_font.setFontName("宋体");// 设置头部字体为宋体
			head_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			head_font.setFontHeightInPoints((short) 10);
			head_font.setColor(XSSFFont.COLOR_RED);
			

			head_cellStyle.setFont(head_font);// 单元格样式使用字体
			head_cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			head_cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			head_cellStyle.setDataFormat(format.getFormat("@"));

			// 普通文本
			XSSFCellStyle cellStyle_CN = xssf_w_book.createCellStyle();

			cellStyle_CN.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_CN.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_CN.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setDataFormat(format.getFormat("@"));
			
			
			// 普通文本红色
			XSSFCellStyle cellStyle_red = xssf_w_book.createCellStyle();

			cellStyle_red.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_red.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_red.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setDataFormat(format.getFormat("@"));
			
			XSSFFont red_font = xssf_w_book.createFont();
			red_font.setColor((short)0xa);
			red_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			cellStyle_red.setFont(red_font);
			
			// 普通文本蓝色
			XSSFCellStyle cellStyle_blue = xssf_w_book.createCellStyle();

			cellStyle_blue.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_blue.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_blue.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setDataFormat(format.getFormat("@"));
			
			XSSFFont blue_font = xssf_w_book.createFont();
			blue_font.setColor((short)30F);
			blue_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			cellStyle_blue.setFont(blue_font);
			
			// 日期
			XSSFCellStyle cellStyle_date = xssf_w_book.createCellStyle();

			cellStyle_date.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中 
			cellStyle_date.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_date.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderTop(XSSFCellStyle.BORDER_THIN);
//			cellStyle_date.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy/mm/dd"));
			cellStyle_date.setDataFormat(format.getFormat("yyyy/MM/dd"));
			
			// 数字
			XSSFCellStyle cellStyle_num = xssf_w_book.createCellStyle();

			cellStyle_num.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_num.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_num.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setDataFormat(format.getFormat("#,##0.######"));
			
			// 数字红色
			XSSFCellStyle cellStyle_num_red = xssf_w_book.createCellStyle();
			cellStyle_num_red.setFont(head_font);// 单元格样式使用字体
			cellStyle_num_red.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_num_red.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_num_red.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setDataFormat(format.getFormat("#,##0.######"));
			

			int titleRows = 0; // 标题占据的行数
			int titleRows2 = 0; // 标题占据的行数
			int titleRows3 = 0; // 标题占据的行数
			xssf_w_row = xssf_w_sheet.createRow(0 + titleRows);// 第一行写入标题行
			for (int i = 0; i < col_count; i++) {
				
				xssf_w_cell = xssf_w_row.createCell((short) i);			
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(titlesName[i]);
				xssf_w_cell.setCellStyle(head_cellStyle);
				if(i == 0){
					xssf_w_sheet.setColumnWidth(i, 1500);
				}else{
					xssf_w_sheet.setColumnWidth(i, 5500);
				}
				

			}
			xssf_w_row2 = xssf_w_sheet2.createRow(0 + titleRows2);// 第一行写入标题行
			for (int i = 0; i < col_count2; i++) {
				xssf_w_cell = xssf_w_row2.createCell((short) i);			
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(titlesName2[i]);
				xssf_w_cell.setCellStyle(head_cellStyle);
				if(i == 0){
					xssf_w_sheet2.setColumnWidth(i, 1500);
				}else{
					xssf_w_sheet2.setColumnWidth(i, 5500);
				}

			}
			xssf_w_row3 = xssf_w_sheet3.createRow(0 + titleRows3);// 第一行写入标题行
			for (int i = 0; i < col_count3; i++) {
				xssf_w_cell = xssf_w_row3.createCell((short) i);			
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(titlesName3[i]);
				xssf_w_cell.setCellStyle(head_cellStyle);
				if(i == 0){
					xssf_w_sheet3.setColumnWidth(i, 1500);
				}else{
					xssf_w_sheet3.setColumnWidth(i, 5500);
				}

			}
			
			double fkhj = 0.0;
			double sfhj = 0.0;
			double kkyf = 0.0;
			double kkhj = 0.0;
			
			int jianNum = 0;
			double kkys_sum = 0.0;
			++titleRows;++titleRows2;++titleRows3;
			
			List COMPLIST=new ArrayList();
			// 供应商厂商数据权限
			Map SUP_MAP=BaseUtil.getSup();
			if(SUP_MAP!=null){
				param.put("SUP_ID",SUP_MAP.get("SUP_ID"));
			}
			Map COM_MAP=BaseUtil.getCom();
			if(COM_MAP!=null){
				param.put("COM_ID",COM_MAP.get("COMPANY_ID"));
			}

			if(param.get("COMP_ID") == null || "".equals(param.get("COMP_ID")+"")){
				COMPLIST=Dao.selectList("payment.pay_ComListAll");
			}
			else{
				COMPLIST=Dao.selectList("payment.pay_ComListByParam",param);
			}
			
			for (int i = 0; i < COMPLIST.size(); i++) {
				Map mapCom=(Map)COMPLIST.get(i);
				param.put("COMP_ID", mapCom.get("COMP_ID"));
				List<Map<String,Object>> dataList = pay_Head_Excle_Eq_Mg(param);
				
				row_count = dataList.size();                           
			
				Map<String, Object> rowMap = new HashMap<String, Object>();
				for (int j = 0; j < row_count; j++) {
					xssf_w_row = xssf_w_sheet.createRow(titleRows);
					xssf_w_row2 = xssf_w_sheet2.createRow(titleRows2);
					rowMap = dataList.get(row_count != dataList.size() ? 1 : j);
					
					Object SUPPER_NAME = rowMap.get("SUPPER_NAME") == null ? "" : rowMap.get("SUPPER_NAME");
					Object COMP_NAME = rowMap.get("COMP_NAME") == null ? "" : rowMap.get("COMP_NAME");
					Object PAYEE_NAME = rowMap.get("PAYEE_NAME") == null ? "" : rowMap.get("PAYEE_NAME");
					Object PAY_BANK_NAME = rowMap.get("PAY_BANK_NAME") == null ? "" : rowMap.get("PAY_BANK_NAME");
					Object PAY_BANK_ADDRESS = rowMap.get("PAY_BANK_ADDRESS") == null ? "" : rowMap.get("PAY_BANK_ADDRESS");
					Object PAYMENT_MONEY = rowMap.get("PAYMENT_MONEY") == null ? "" : rowMap.get("PAYMENT_MONEY");
					Object DEDUCTION_RATE = rowMap.get("DEDUCTION_RATE") == null ? "" : rowMap.get("DEDUCTION_RATE");
					Object PAYMENT_RATE = rowMap.get("PAYMENT_RATE") == null ? "" : rowMap.get("PAYMENT_RATE");
					Object DEDUCTION_MONEY = rowMap.get("DEDUCTION_MONEY") == null ? "" : rowMap.get("DEDUCTION_MONEY");
					Object LAST_MONEY = rowMap.get("LAST_MONEY") == null ? "" : rowMap.get("LAST_MONEY");
					Object PAY_BANK_ACCOUNT = rowMap.get("PAY_BANK_ACCOUNT") == null ? "" : rowMap.get("PAY_BANK_ACCOUNT");
	
					
					
					//  第一个sheet
					xssf_w_cell = xssf_w_row.createCell(0);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(titleRows-jianNum);
	
					xssf_w_cell = xssf_w_row.createCell(1);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(SUPPER_NAME.toString());
					
					xssf_w_cell = xssf_w_row.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(COMP_NAME.toString());
					
					xssf_w_cell = xssf_w_row.createCell(3);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAYEE_NAME.toString());
					
					xssf_w_cell = xssf_w_row.createCell(4);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_NAME.toString());
					
					xssf_w_cell = xssf_w_row.createCell(5);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_ADDRESS.toString());
					
					xssf_w_cell = xssf_w_row.createCell(6);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_ACCOUNT.toString());
					
					xssf_w_cell = xssf_w_row.createCell(7);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) PAYMENT_MONEY).doubleValue());
					
					//  第二个sheet
					
					xssf_w_cell = xssf_w_row2.createCell(0);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(titleRows2-jianNum);
	
					
					xssf_w_cell = xssf_w_row2.createCell(1);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(SUPPER_NAME.toString());
					
					xssf_w_cell = xssf_w_row2.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(COMP_NAME.toString());
					
					xssf_w_cell = xssf_w_row2.createCell(3);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAYEE_NAME.toString());
					
					xssf_w_cell = xssf_w_row2.createCell(4);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_NAME.toString());
					
					xssf_w_cell = xssf_w_row2.createCell(5);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_ADDRESS.toString());
					
					xssf_w_cell = xssf_w_row2.createCell(6);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_ACCOUNT.toString());
					
					xssf_w_cell = xssf_w_row2.createCell(7);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) PAYMENT_MONEY).doubleValue());
	
					xssf_w_cell = xssf_w_row2.createCell(8);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num);
					xssf_w_cell.setCellValue(PAYMENT_RATE.toString());
	
					xssf_w_cell = xssf_w_row2.createCell(9);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) LAST_MONEY).doubleValue());
	
					 
					if(Double.parseDouble((DEDUCTION_MONEY+"") == ""?"0":DEDUCTION_MONEY.toString()) > 0){
						xssf_w_row3 = xssf_w_sheet3.createRow(titleRows3);
					//  第三个sheet
						xssf_w_cell = xssf_w_row3.createCell(0);
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell.setCellStyle(cellStyle_CN);						
						xssf_w_cell.setCellValue(titleRows3-jianNum);
						
						xssf_w_cell = xssf_w_row3.createCell(1);
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell.setCellStyle(cellStyle_CN);						
						xssf_w_cell.setCellValue(SUPPER_NAME.toString());
						
						xssf_w_cell = xssf_w_row3.createCell(2);
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell.setCellStyle(cellStyle_CN);						
						xssf_w_cell.setCellValue(COMP_NAME.toString());
						
						xssf_w_cell = xssf_w_row3.createCell(3);
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell.setCellStyle(cellStyle_CN);						
						xssf_w_cell.setCellValue(PAYEE_NAME.toString());
						
						xssf_w_cell = xssf_w_row3.createCell(4);
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell.setCellStyle(cellStyle_CN);						
						xssf_w_cell.setCellValue(PAY_BANK_NAME.toString());
						
						xssf_w_cell = xssf_w_row3.createCell(5);
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell.setCellStyle(cellStyle_CN);						
						xssf_w_cell.setCellValue(PAY_BANK_ADDRESS.toString());
						
						xssf_w_cell = xssf_w_row3.createCell(6);
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell.setCellStyle(cellStyle_CN);						
						xssf_w_cell.setCellValue(PAY_BANK_ACCOUNT.toString());
						
						xssf_w_cell = xssf_w_row3.createCell(7);
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						xssf_w_cell.setCellStyle(cellStyle_num);
						xssf_w_cell.setCellValue(((java.math.BigDecimal) PAYMENT_MONEY).doubleValue());
						
						kkys_sum += ((java.math.BigDecimal) PAYMENT_MONEY).doubleValue();
	
						xssf_w_cell = xssf_w_row3.createCell(8);
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						xssf_w_cell.setCellStyle(cellStyle_num);
						xssf_w_cell.setCellValue(DEDUCTION_RATE.toString());
	
						xssf_w_cell = xssf_w_row3.createCell(9);
						xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						xssf_w_cell.setCellStyle(cellStyle_num);
						xssf_w_cell.setCellValue(((java.math.BigDecimal) DEDUCTION_MONEY).doubleValue());
						++titleRows3;
					}
					
					

					++titleRows;++titleRows2;
				}

				Map<String,Object> sumMap = pay_Head_Excle_Eq_Sum(param);
				
				if(sumMap!=null){
					
					xssf_w_row = xssf_w_sheet.createRow(titleRows);
					xssf_w_row2 = xssf_w_sheet2.createRow(titleRows2);
					xssf_w_row3 = xssf_w_sheet3.createRow(titleRows3);
					Object PAYMENT_MONEY_SUM = sumMap.get("PAYMENT_MONEY") == null ? "" : sumMap.get("PAYMENT_MONEY");
					Object DEDUCTION_MONEY_SUM = sumMap.get("DEDUCTION_MONEY") == null ? "" : sumMap.get("DEDUCTION_MONEY");
					Object LAST_MONEY_SUM = sumMap.get("LAST_MONEY") == null ? "" : sumMap.get("LAST_MONEY");
					
					fkhj += ((java.math.BigDecimal) PAYMENT_MONEY_SUM).doubleValue();
					sfhj += ((java.math.BigDecimal) LAST_MONEY_SUM).doubleValue();
					kkhj += ((java.math.BigDecimal) DEDUCTION_MONEY_SUM).doubleValue();
					kkyf += kkys_sum;
					
					Object COMP_NAME1 = sumMap.get("COMP_NAME") == null ? "" : sumMap.get("COMP_NAME");
					xssf_w_cell = xssf_w_row.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(head_cellStyle);						
					xssf_w_cell.setCellValue(COMP_NAME1.toString()+" 汇总");
					
					xssf_w_cell = xssf_w_row.createCell(7);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num_red);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) PAYMENT_MONEY_SUM).doubleValue());
					
	
					xssf_w_cell = xssf_w_row2.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(head_cellStyle);						
					xssf_w_cell.setCellValue(COMP_NAME1.toString()+" 汇总");

					xssf_w_cell = xssf_w_row2.createCell(7);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num_red);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) PAYMENT_MONEY_SUM).doubleValue());
					
					xssf_w_cell = xssf_w_row2.createCell(9);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num_red);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) LAST_MONEY_SUM).doubleValue());
					
					
					xssf_w_cell = xssf_w_row3.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(head_cellStyle);						
					xssf_w_cell.setCellValue(COMP_NAME1.toString()+" 汇总");

					xssf_w_cell = xssf_w_row3.createCell(7);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num_red);
					xssf_w_cell.setCellValue(kkys_sum);
					
					xssf_w_cell = xssf_w_row3.createCell(9);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num_red);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) DEDUCTION_MONEY_SUM).doubleValue());
					
					++jianNum;++titleRows3;
					++titleRows;++titleRows2;
					kkys_sum = 0.0 ;
					 
				}
			}
			xssf_w_row = xssf_w_sheet.createRow(titleRows);
			xssf_w_row2 = xssf_w_sheet2.createRow(titleRows2);
			xssf_w_row3 = xssf_w_sheet3.createRow(titleRows3);
			
			xssf_w_cell = xssf_w_row.createCell(2);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(head_cellStyle);						
			xssf_w_cell.setCellValue(" 合计 ");

			xssf_w_cell = xssf_w_row.createCell(7);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell.setCellStyle(cellStyle_num_red);
			xssf_w_cell.setCellValue(fkhj);
			
			xssf_w_cell = xssf_w_row2.createCell(2);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(head_cellStyle);						
			xssf_w_cell.setCellValue(" 合计 ");
			
			xssf_w_cell = xssf_w_row2.createCell(7);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell.setCellStyle(cellStyle_num_red);
			xssf_w_cell.setCellValue(fkhj);
			
			xssf_w_cell = xssf_w_row2.createCell(9);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell.setCellStyle(cellStyle_num_red);
			xssf_w_cell.setCellValue(sfhj);
			
			xssf_w_cell = xssf_w_row3.createCell(2);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(head_cellStyle);						
			xssf_w_cell.setCellValue(" 合计 ");
			
			xssf_w_cell = xssf_w_row3.createCell(7);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell.setCellStyle(cellStyle_num_red);
			xssf_w_cell.setCellValue(kkyf);
			
			xssf_w_cell = xssf_w_row3.createCell(9);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell.setCellStyle(cellStyle_num_red);
			xssf_w_cell.setCellValue(kkhj);
			
			
			SkyEye.getResponse().reset();
			SkyEye.getResponse().setCharacterEncoding("UTF-8");
			SkyEye.getResponse().setHeader(
					"Content-Disposition",
					new StringBuilder("attachment;filename=").append(
							new String(("租赁物购买价款支付表"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx").getBytes("GB2312"),
									"ISO-8859-1")).toString());
			SkyEye.getResponse().setContentType(ResMime.get("xlsx"));
			xssf_w_book.write(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void pay_Detail_Excle_Mg(Map<String, Object> param) {
		OutputStream os;
		try {

			//全额放款--------放款额=设备总额
			//差额放款--------放款额=设备总额-首期款-DB保证金-其他费用(如果是期初还要减去第一期租金)
			//部分差额放款----放款额=设备总额-留购价款-起租租金
			//DB差额放款-----DB差额放款=设备总额-DB保证金
			//全额
			String[] titlesName = new String[]{"序号","供应商","项目编号","客户名称","厂商","租赁物名称","台量","起租确认日","租赁物购买价款","实际放款金额","款项名称","预付日期","收款单位","账号"};
			//差额
			String[] titlesName2 = new String[]{"序号","供应商","项目编号","客户名称","厂商","租赁物名称","台量","起租确认日","租赁物购买价款","首期款金额","DB保证金金额","厂商保证金","其他费用","实际放款金额","款项名称","预付日期","收款单位","账号"};
			//DB保证金差额
			String[] titlesName3 = new String[]{"序号","供应商","项目编号","客户名称","厂商","租赁物名称","台量","起租确认日","租赁物购买价款","DB保证金金额","实际放款金额","款项名称","预付日期","收款单位","账号"};
			//部分差额放款
			String[] titlesName4 = new String[]{"序号","供应商","项目编号","客户名称","厂商","租赁物名称","台量","起租确认日","租赁物购买价款","起租租金","留购价款","实际放款金额","款项名称","预付日期","收款单位","账号"};
			
			os = SkyEye.getResponse().getOutputStream();
			XSSFWorkbook xssf_w_book = new XSSFWorkbook();
			XSSFSheet xssf_w_sheet = xssf_w_book.createSheet("租赁物购买价款全额放款支付明细");
			XSSFSheet xssf_w_sheet2 = xssf_w_book.createSheet("租赁物购买价款差额放款支付明细");
			XSSFSheet xssf_w_sheet3 = xssf_w_book.createSheet("租赁物购买价款DB保证金差额放款支付明细");
			XSSFSheet xssf_w_sheet4 = xssf_w_book.createSheet("租赁物购买价款部分差额放款支付明细");
			XSSFRow xssf_w_row = null;// 创建一行
			XSSFRow xssf_w_row2 = null;// 创建一行
			XSSFRow xssf_w_row3 = null;// 创建一行
			XSSFRow xssf_w_row4 = null;// 创建一行
			XSSFCell xssf_w_cell = null;// 创建每个单元格
			XSSFCell xssf_w_cell2 = null;// 创建每个单元格
			XSSFCell xssf_w_cell3 = null;// 创建每个单元格
			XSSFCell xssf_w_cell4 = null;// 创建每个单元格
			int col_count = 0;
			int col_count2 = 0;
			int col_count3 = 0;
			int col_count4 = 0;
			int row_count = 0;
			int row_count2 = 0;
			int row_count3 = 0;
			int row_count4 = 0;
			col_count = titlesName.length;
			col_count2 = titlesName2.length;
			col_count3 = titlesName3.length;
			col_count4 = titlesName4.length;
			
			XSSFDataFormat format = xssf_w_book.createDataFormat();

			// 创建标题行样式
			XSSFCellStyle head_cellStyle = xssf_w_book.createCellStyle();
			XSSFFont head_font = xssf_w_book.createFont();
			head_font.setFontName("宋体");// 设置头部字体为宋体
			head_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			head_font.setFontHeightInPoints((short) 10);
			head_font.setColor(XSSFFont.COLOR_RED);
			

			head_cellStyle.setFont(head_font);// 单元格样式使用字体
			head_cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
			head_cellStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			head_cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			head_cellStyle.setDataFormat(format.getFormat("@"));

			// 普通文本
			XSSFCellStyle cellStyle_CN = xssf_w_book.createCellStyle();

			cellStyle_CN.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_CN.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_CN.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_CN.setDataFormat(format.getFormat("@"));
			
			
			// 普通文本红色
			XSSFCellStyle cellStyle_red = xssf_w_book.createCellStyle();

			cellStyle_red.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_red.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_red.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_red.setDataFormat(format.getFormat("@"));
			
			XSSFFont red_font = xssf_w_book.createFont();
			red_font.setColor((short)0xa);
			red_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			cellStyle_red.setFont(red_font);
			
			// 普通文本蓝色
			XSSFCellStyle cellStyle_blue = xssf_w_book.createCellStyle();

			cellStyle_blue.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_blue.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_blue.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_blue.setDataFormat(format.getFormat("@"));
			
			XSSFFont blue_font = xssf_w_book.createFont();
			blue_font.setColor((short)30F);
			blue_font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			cellStyle_blue.setFont(blue_font);
			
			// 日期
			XSSFCellStyle cellStyle_date = xssf_w_book.createCellStyle();

			cellStyle_date.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中 
			cellStyle_date.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_date.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_date.setBorderTop(XSSFCellStyle.BORDER_THIN);
//			cellStyle_date.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy/mm/dd"));
			cellStyle_date.setDataFormat(format.getFormat("yyyy/MM/dd"));
			
			// 数字
			XSSFCellStyle cellStyle_num = xssf_w_book.createCellStyle();

			cellStyle_num.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_num.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_num.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_num.setDataFormat(format.getFormat("#,##0.######"));
			
			// 数字红色
			XSSFCellStyle cellStyle_num_red = xssf_w_book.createCellStyle();
			cellStyle_num_red.setFont(head_font);// 单元格样式使用字体
			cellStyle_num_red.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);// 水平居中
			cellStyle_num_red.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直居中
			cellStyle_num_red.setBorderBottom(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setBorderLeft(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setBorderRight(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setBorderTop(XSSFCellStyle.BORDER_THIN);
			cellStyle_num_red.setDataFormat(format.getFormat("#,##0.######"));
			

			int titleRows = 0; // 标题占据的行数
			int titleRows2 = 0; // 标题占据的行数
			int titleRows3 = 0; // 标题占据的行数
			int titleRows4 = 0; // 标题占据的行数
			xssf_w_row = xssf_w_sheet.createRow(0 + titleRows);// 第一行写入标题行
			for (int i = 0; i < col_count; i++) {
				
				xssf_w_cell = xssf_w_row.createCell((short) i);			
				xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell.setCellValue(titlesName[i]);
				xssf_w_cell.setCellStyle(head_cellStyle);
				if(i == 0){
					xssf_w_sheet.setColumnWidth(i, 1500);
				}else{
					xssf_w_sheet.setColumnWidth(i, 5500);
				}
				

			}
			xssf_w_row2 = xssf_w_sheet2.createRow(0 + titleRows2);// 第一行写入标题行
			for (int i = 0; i < col_count2; i++) {
				xssf_w_cell2 = xssf_w_row2.createCell((short) i);			
				xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell2.setCellValue(titlesName2[i]);
				xssf_w_cell2.setCellStyle(head_cellStyle);
				if(i == 0){
					xssf_w_sheet2.setColumnWidth(i, 1500);
				}else{
					xssf_w_sheet2.setColumnWidth(i, 5500);
				}

			}
			xssf_w_row3 = xssf_w_sheet3.createRow(0 + titleRows3);// 第一行写入标题行
			for (int i = 0; i < col_count3; i++) {
				xssf_w_cell3 = xssf_w_row3.createCell((short) i);			
				xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell3.setCellValue(titlesName3[i]);
				xssf_w_cell3.setCellStyle(head_cellStyle);
				if(i == 0){
					xssf_w_sheet3.setColumnWidth(i, 1500);
				}else{
					xssf_w_sheet3.setColumnWidth(i, 5500);
				}

			}
			
			xssf_w_row4 = xssf_w_sheet4.createRow(0 + titleRows4);// 第一行写入标题行
			for (int i = 0; i < col_count4; i++) {
				xssf_w_cell4 = xssf_w_row4.createCell((short) i);			
				xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
				xssf_w_cell4.setCellValue(titlesName4[i]);
				xssf_w_cell4.setCellStyle(head_cellStyle);
				if(i == 0){
					xssf_w_sheet4.setColumnWidth(i, 1500);
				}else{
					xssf_w_sheet4.setColumnWidth(i, 5500);
				}

			}
			//全额放款--------放款额=设备总额
			double LEASE_TOPRIC_JIS = 0.0;
			double PAY_MONEY_JIS = 0.0;
			int AMOUNT_JIS=0;
			
			//差额放款--------放款额=设备总额-首期款-DB保证金-其他费用(如果是期初还要减去第一期租金)
			double LEASE_TOPRIC_JIS2 = 0.0;
			double SQ_PRICE_JIS2 = 0.0;
			double DB_PRICE_JIS2 = 0.0;
			double FIRST_RENT_JIS2 = 0.0;
			double QTFYF_JIS2 = 0.0;
			double PAY_MONEY_JIS2 = 0.0;
			double CS_PRICEALL_JIS2=0.0;
			int AMOUNT_JIS2=0;
			
			//DB差额放款-----DB差额放款=设备总额-DB保证金
			double LEASE_TOPRIC_JIS3 = 0.0;
			double DB_PRICE_JIS3 = 0.0;
			double PAY_MONEY_JIS3 = 0.0;
			int AMOUNT_JIS3=0;
			
			//部分差额放款----放款额=设备总额-留购价款-起租租金
			double LEASE_TOPRIC_JIS4 = 0.0;
			double LGJK_JIS4 = 0.0;
			double QZZJ_JIS4 = 0.0;
			double PAY_MONEY_JIS4 = 0.0;
			int AMOUNT_JIS4=0;
			
			int jianNum = 0;
			int jianNum2 = 0;
			int jianNum3 = 0;
			int jianNum4 = 0;
			++titleRows;++titleRows2;++titleRows3;++titleRows4;
			List SUPPLIST=new ArrayList();

			// 供应商厂商数据权限
			Map SUP_MAP=BaseUtil.getSup();
			if(SUP_MAP!=null){
				param.put("SUP_ID",SUP_MAP.get("SUP_ID"));
			}
			Map COM_MAP=BaseUtil.getCom();
			if(COM_MAP!=null){
				param.put("COM_ID",COM_MAP.get("COMPANY_ID"));
			}

			
			SUPPLIST=Dao.selectList("payment.pay_SupList",param);
			
			for (int i = 0; i < SUPPLIST.size(); i++) {
				Map mapCom=(Map)SUPPLIST.get(i);
				if(mapCom == null ){
					continue;
				}
				param.put("SUP_ID", mapCom.get("SUPPER_ID"));
				Map<String, Object> rowMap = new HashMap<String, Object>();
				
				List<Map<String,Object>> dataList = pay_Detail_Excle_Eq_Mg(param);
				row_count = dataList.size();
				for (int j = 0; j < row_count; j++) {
					xssf_w_row = xssf_w_sheet.createRow(titleRows);
					rowMap = dataList.get(row_count != dataList.size() ? 1 : j);
					
					Object SUPPER_NAME = rowMap.get("SUPPER_NAME") == null ? "" : rowMap.get("SUPPER_NAME");
					Object PROJECT_CODE = rowMap.get("PROJECT_CODE") == null ? "" : rowMap.get("PROJECT_CODE");
					Object CUST_NAME = rowMap.get("CUST_NAME") == null ? "" : rowMap.get("CUST_NAME");
					Object COMP_NAME = rowMap.get("COMP_NAME") == null ? "" : rowMap.get("COMP_NAME");
					Object PRODUCT_NAME = rowMap.get("PRODUCT_NAME") == null ? "" : rowMap.get("PRODUCT_NAME");
					Object AMOUNT = rowMap.get("AMOUNT") == null ? "" : rowMap.get("AMOUNT");
					Object START_DATE = rowMap.get("START_DATE") == null ? "" : rowMap.get("START_DATE");
					Object PAY_NAME = rowMap.get("PAY_NAME") == null ? "" : rowMap.get("PAY_NAME");
					Object RESERVE_DATE = rowMap.get("RESERVE_DATE") == null ? "" : rowMap.get("RESERVE_DATE");
					Object PAYEE_NAME = rowMap.get("PAYEE_NAME") == null ? "" : rowMap.get("PAYEE_NAME");
					Object PAY_BANK_ACCOUNT = rowMap.get("PAY_BANK_ACCOUNT") == null ? "" : rowMap.get("PAY_BANK_ACCOUNT");
					Object LEASE_TOPRIC = rowMap.get("LEASE_TOPRIC") == null ? "" : rowMap.get("LEASE_TOPRIC");
					Object PAY_MONEY = rowMap.get("PAY_MONEY") == null ? "" : rowMap.get("PAY_MONEY");
					
					
					//  第一个sheet
					//供应商
					xssf_w_cell = xssf_w_row.createCell(0);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(titleRows-jianNum);
					
					//供应商
					xssf_w_cell = xssf_w_row.createCell(1);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(SUPPER_NAME.toString());
	
					//项目编号
					xssf_w_cell = xssf_w_row.createCell(2);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PROJECT_CODE.toString());
					
					//客户名称
					xssf_w_cell = xssf_w_row.createCell(3);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(CUST_NAME.toString());
					
					//厂商
					xssf_w_cell = xssf_w_row.createCell(4);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(COMP_NAME.toString());
					
					//租赁物名称
					xssf_w_cell = xssf_w_row.createCell(5);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PRODUCT_NAME.toString());
					
					//台量
					xssf_w_cell = xssf_w_row.createCell(6);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(AMOUNT.toString());
					
					//起租确认日
					xssf_w_cell = xssf_w_row.createCell(7);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(START_DATE.toString());
					
					//租赁物购买价款
					xssf_w_cell = xssf_w_row.createCell(8);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) LEASE_TOPRIC).doubleValue());
					
					//实际放款金额
					xssf_w_cell = xssf_w_row.createCell(9);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) PAY_MONEY).doubleValue());
					
					//款项名称
					xssf_w_cell = xssf_w_row.createCell(10);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_NAME.toString());
					
					//预付日期
					xssf_w_cell = xssf_w_row.createCell(11);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(RESERVE_DATE.toString());
					
					//收款单位
					xssf_w_cell = xssf_w_row.createCell(12);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAYEE_NAME.toString());
					
					//账号
					xssf_w_cell = xssf_w_row.createCell(13);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(cellStyle_CN);						
					xssf_w_cell.setCellValue(PAY_BANK_ACCOUNT.toString());
					++titleRows;
				}
				
				
				List<Map<String,Object>> dataList2 = pay_Detail_Excle_Eq_Mg2(param);
				row_count2 = dataList2.size();
				for (int j = 0; j < row_count2; j++) {
					xssf_w_row2 = xssf_w_sheet2.createRow(titleRows2);
					rowMap = dataList2.get(row_count2 != dataList2.size() ? 1 : j);
					
					Object SUPPER_NAME = rowMap.get("SUPPER_NAME") == null ? "" : rowMap.get("SUPPER_NAME");
					Object PROJECT_CODE = rowMap.get("PROJECT_CODE") == null ? "" : rowMap.get("PROJECT_CODE");
					Object CUST_NAME = rowMap.get("CUST_NAME") == null ? "" : rowMap.get("CUST_NAME");
					Object COMP_NAME = rowMap.get("COMP_NAME") == null ? "" : rowMap.get("COMP_NAME");
					Object PRODUCT_NAME = rowMap.get("PRODUCT_NAME") == null ? "" : rowMap.get("PRODUCT_NAME");
					Object AMOUNT = rowMap.get("AMOUNT") == null ? "" : rowMap.get("AMOUNT");
					Object START_DATE = rowMap.get("START_DATE") == null ? "" : rowMap.get("START_DATE");
					Object PAY_NAME = rowMap.get("PAY_NAME") == null ? "" : rowMap.get("PAY_NAME");
					Object RESERVE_DATE = rowMap.get("RESERVE_DATE") == null ? "" : rowMap.get("RESERVE_DATE");
					Object PAYEE_NAME = rowMap.get("PAYEE_NAME") == null ? "" : rowMap.get("PAYEE_NAME");
					Object PAY_BANK_ACCOUNT = rowMap.get("PAY_BANK_ACCOUNT") == null ? "" : rowMap.get("PAY_BANK_ACCOUNT");
					Object LEASE_TOPRIC = rowMap.get("LEASE_TOPRIC") == null ? "" : rowMap.get("LEASE_TOPRIC");
					Object PAY_MONEY = rowMap.get("PAY_MONEY") == null ? "" : rowMap.get("PAY_MONEY");
					Object SQ_PRICE = rowMap.get("SQ_PRICE") == null ? "" : rowMap.get("SQ_PRICE");
					Object DB_PRICE = rowMap.get("DB_PRICE") == null ? "" : rowMap.get("DB_PRICE");
					Object FIRST_RENT = rowMap.get("FIRST_RENT") == null ? "" : rowMap.get("FIRST_RENT");
					Object QTFYF = rowMap.get("QTFYF") == null ? "" : rowMap.get("QTFYF");
					Object CS_PRICE = rowMap.get("CS_PRICE") == null ? "" : rowMap.get("CS_PRICE");
	
					
					
					//  第二个sheet
					xssf_w_cell2 = xssf_w_row2.createCell(0);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(titleRows2-jianNum2);
	
					
					//供应商
					xssf_w_cell2 = xssf_w_row2.createCell(1);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(SUPPER_NAME.toString());
	
					//项目编号
					xssf_w_cell2 = xssf_w_row2.createCell(2);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(PROJECT_CODE.toString());
					
					//客户名称
					xssf_w_cell2 = xssf_w_row2.createCell(3);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(CUST_NAME.toString());
					
					//厂商
					xssf_w_cell2 = xssf_w_row2.createCell(4);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(COMP_NAME.toString());
					
					//租赁物名称
					xssf_w_cell2 = xssf_w_row2.createCell(5);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(PRODUCT_NAME.toString());
					
					//台量
					xssf_w_cell2 = xssf_w_row2.createCell(6);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(AMOUNT.toString());
					
					//起租确认日
					xssf_w_cell2 = xssf_w_row2.createCell(7);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(START_DATE.toString());
					
					//租赁物购买价款
					xssf_w_cell2 = xssf_w_row2.createCell(8);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) LEASE_TOPRIC).doubleValue());
					
					//首付款
					xssf_w_cell2 = xssf_w_row2.createCell(9);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) SQ_PRICE).doubleValue());
					
					//DB保证金
					xssf_w_cell2 = xssf_w_row2.createCell(10);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) DB_PRICE).doubleValue());
					
					//厂商保证金
					xssf_w_cell2 = xssf_w_row2.createCell(11);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) CS_PRICE).doubleValue());
					
					//其他费用
					xssf_w_cell2 = xssf_w_row2.createCell(12);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) QTFYF).doubleValue());
					
					//实际放款金额
					xssf_w_cell2 = xssf_w_row2.createCell(13);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) PAY_MONEY).doubleValue());
					
					//款项名称
					xssf_w_cell2 = xssf_w_row2.createCell(14);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(PAY_NAME.toString());
					
					//预付日期
					xssf_w_cell2 = xssf_w_row2.createCell(15);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(RESERVE_DATE.toString());
					
					//收款单位
					xssf_w_cell2 = xssf_w_row2.createCell(16);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(PAYEE_NAME.toString());
					
					//账号
					xssf_w_cell2 = xssf_w_row2.createCell(17);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(cellStyle_CN);						
					xssf_w_cell2.setCellValue(PAY_BANK_ACCOUNT.toString());
					
					++titleRows2;
				}
				
				
				
				List<Map<String,Object>> dataList3 = pay_Detail_Excle_Eq_Mg3(param);
				row_count3 = dataList3.size();
				for (int j = 0; j < row_count3; j++) {
					rowMap = dataList3.get(row_count3 != dataList3.size() ? 1 : j);
					
					Object SUPPER_NAME = rowMap.get("SUPPER_NAME") == null ? "" : rowMap.get("SUPPER_NAME");
					Object PROJECT_CODE = rowMap.get("PROJECT_CODE") == null ? "" : rowMap.get("PROJECT_CODE");
					Object CUST_NAME = rowMap.get("CUST_NAME") == null ? "" : rowMap.get("CUST_NAME");
					Object COMP_NAME = rowMap.get("COMP_NAME") == null ? "" : rowMap.get("COMP_NAME");
					Object PRODUCT_NAME = rowMap.get("PRODUCT_NAME") == null ? "" : rowMap.get("PRODUCT_NAME");
					Object AMOUNT = rowMap.get("AMOUNT") == null ? "" : rowMap.get("AMOUNT");
					Object START_DATE = rowMap.get("START_DATE") == null ? "" : rowMap.get("START_DATE");
					Object PAY_NAME = rowMap.get("PAY_NAME") == null ? "" : rowMap.get("PAY_NAME");
					Object RESERVE_DATE = rowMap.get("RESERVE_DATE") == null ? "" : rowMap.get("RESERVE_DATE");
					Object PAYEE_NAME = rowMap.get("PAYEE_NAME") == null ? "" : rowMap.get("PAYEE_NAME");
					Object PAY_BANK_ACCOUNT = rowMap.get("PAY_BANK_ACCOUNT") == null ? "" : rowMap.get("PAY_BANK_ACCOUNT");
					Object LEASE_TOPRIC = rowMap.get("LEASE_TOPRIC") == null ? "" : rowMap.get("LEASE_TOPRIC");
					Object PAY_MONEY = rowMap.get("PAY_MONEY") == null ? "" : rowMap.get("PAY_MONEY");
					Object DB_PRICE = rowMap.get("DB_PRICE") == null ? "" : rowMap.get("DB_PRICE");
					
					
						xssf_w_row3 = xssf_w_sheet3.createRow(titleRows3);
					//  第三个sheet
						xssf_w_cell3 = xssf_w_row3.createCell(0);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(titleRows3-jianNum3);
						
						//供应商
						xssf_w_cell3 = xssf_w_row3.createCell(1);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(SUPPER_NAME.toString());
		
						//项目编号
						xssf_w_cell3 = xssf_w_row3.createCell(2);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(PROJECT_CODE.toString());
						
						//客户名称
						xssf_w_cell3 = xssf_w_row3.createCell(3);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(CUST_NAME.toString());
						
						//厂商
						xssf_w_cell3 = xssf_w_row3.createCell(4);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(COMP_NAME.toString());
						
						//租赁物名称
						xssf_w_cell3 = xssf_w_row3.createCell(5);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(PRODUCT_NAME.toString());
						
						//台量
						xssf_w_cell3 = xssf_w_row3.createCell(6);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(AMOUNT.toString());
						
						//起租确认日
						xssf_w_cell3 = xssf_w_row3.createCell(7);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(START_DATE.toString());
						
						//租赁物购买价款
						xssf_w_cell3 = xssf_w_row3.createCell(8);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						xssf_w_cell3.setCellStyle(cellStyle_num);
						xssf_w_cell3.setCellValue(((java.math.BigDecimal) LEASE_TOPRIC).doubleValue());
						
						//DB保证金
						xssf_w_cell3 = xssf_w_row3.createCell(9);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						xssf_w_cell3.setCellStyle(cellStyle_num);
						xssf_w_cell3.setCellValue(((java.math.BigDecimal) DB_PRICE).doubleValue());
						
						//实际放款金额
						xssf_w_cell3 = xssf_w_row3.createCell(10);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						xssf_w_cell3.setCellStyle(cellStyle_num);
						xssf_w_cell3.setCellValue(((java.math.BigDecimal) PAY_MONEY).doubleValue());
						
						//款项名称
						xssf_w_cell3 = xssf_w_row3.createCell(11);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(PAY_NAME.toString());
						
						//预付日期
						xssf_w_cell3 = xssf_w_row3.createCell(12);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(RESERVE_DATE.toString());
						
						//收款单位
						xssf_w_cell3 = xssf_w_row3.createCell(13);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(PAYEE_NAME.toString());
						
						//账号
						xssf_w_cell3 = xssf_w_row3.createCell(14);
						xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell3.setCellStyle(cellStyle_CN);						
						xssf_w_cell3.setCellValue(PAY_BANK_ACCOUNT.toString());

					++titleRows3;
				}
				
				
				
				List<Map<String,Object>> dataList4 = pay_Detail_Excle_Eq_Mg4(param);
				row_count4 = dataList4.size();
				for (int j = 0; j < row_count4; j++) {
					rowMap = dataList4.get(row_count4 != dataList4.size() ? 1 : j);
					
					Object SUPPER_NAME = rowMap.get("SUPPER_NAME") == null ? "" : rowMap.get("SUPPER_NAME");
					Object PROJECT_CODE = rowMap.get("PROJECT_CODE") == null ? "" : rowMap.get("PROJECT_CODE");
					Object CUST_NAME = rowMap.get("CUST_NAME") == null ? "" : rowMap.get("CUST_NAME");
					Object COMP_NAME = rowMap.get("COMP_NAME") == null ? "" : rowMap.get("COMP_NAME");
					Object PRODUCT_NAME = rowMap.get("PRODUCT_NAME") == null ? "" : rowMap.get("PRODUCT_NAME");
					Object AMOUNT = rowMap.get("AMOUNT") == null ? "" : rowMap.get("AMOUNT");
					Object START_DATE = rowMap.get("START_DATE") == null ? "" : rowMap.get("START_DATE");
					Object PAY_NAME = rowMap.get("PAY_NAME") == null ? "" : rowMap.get("PAY_NAME");
					Object RESERVE_DATE = rowMap.get("RESERVE_DATE") == null ? "" : rowMap.get("RESERVE_DATE");
					Object PAYEE_NAME = rowMap.get("PAYEE_NAME") == null ? "" : rowMap.get("PAYEE_NAME");
					Object PAY_BANK_ACCOUNT = rowMap.get("PAY_BANK_ACCOUNT") == null ? "" : rowMap.get("PAY_BANK_ACCOUNT");
					Object LEASE_TOPRIC = rowMap.get("LEASE_TOPRIC") == null ? "" : rowMap.get("LEASE_TOPRIC");
					Object PAY_MONEY = rowMap.get("PAY_MONEY") == null ? "" : rowMap.get("PAY_MONEY");
					Object QZZJ = rowMap.get("QZZJ") == null ? "" : rowMap.get("QZZJ");
					Object LGJK = rowMap.get("LGJK") == null ? "" : rowMap.get("LGJK");
					
					
					xssf_w_row4 = xssf_w_sheet4.createRow(titleRows4);
					//  第四个sheet
						xssf_w_cell4 = xssf_w_row4.createCell(0);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(titleRows4-jianNum4);
		
						
						//供应商
						xssf_w_cell4 = xssf_w_row4.createCell(1);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(SUPPER_NAME.toString());
		
						//项目编号
						xssf_w_cell4 = xssf_w_row4.createCell(2);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(PROJECT_CODE.toString());
						
						//客户名称
						xssf_w_cell4 = xssf_w_row4.createCell(3);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(CUST_NAME.toString());
						
						//厂商
						xssf_w_cell4 = xssf_w_row4.createCell(4);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(COMP_NAME.toString());
						
						//租赁物名称
						xssf_w_cell4 = xssf_w_row4.createCell(5);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(PRODUCT_NAME.toString());
						
						//台量
						xssf_w_cell4 = xssf_w_row4.createCell(6);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(AMOUNT.toString());
						
						//起租确认日
						xssf_w_cell4 = xssf_w_row4.createCell(7);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(START_DATE.toString());
						
						//租赁物购买价款
						xssf_w_cell4 = xssf_w_row4.createCell(8);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						xssf_w_cell4.setCellStyle(cellStyle_num);
						xssf_w_cell4.setCellValue(((java.math.BigDecimal) LEASE_TOPRIC).doubleValue());
						
						//起租租金
						xssf_w_cell4 = xssf_w_row4.createCell(9);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						xssf_w_cell4.setCellStyle(cellStyle_num);
						xssf_w_cell4.setCellValue(((java.math.BigDecimal) QZZJ).doubleValue());
						
						//留购价款
						xssf_w_cell4 = xssf_w_row4.createCell(10);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						xssf_w_cell4.setCellStyle(cellStyle_num);
						xssf_w_cell4.setCellValue(((java.math.BigDecimal) LGJK).doubleValue());
						
						//实际放款金额
						xssf_w_cell4 = xssf_w_row4.createCell(11);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						xssf_w_cell4.setCellStyle(cellStyle_num);
						xssf_w_cell4.setCellValue(((java.math.BigDecimal) PAY_MONEY).doubleValue());
						
						//款项名称
						xssf_w_cell4 = xssf_w_row4.createCell(12);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(PAY_NAME.toString());
						
						//预付日期
						xssf_w_cell4 = xssf_w_row4.createCell(13);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(RESERVE_DATE.toString());
						
						//收款单位
						xssf_w_cell4 = xssf_w_row4.createCell(14);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(PAYEE_NAME.toString());
						
						//账号
						xssf_w_cell4 = xssf_w_row4.createCell(15);
						xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
						xssf_w_cell4.setCellStyle(cellStyle_CN);						
						xssf_w_cell4.setCellValue(PAY_BANK_ACCOUNT.toString());
					

					++titleRows4;
				}
				

				Map<String,Object> sumMap = pay_Detail_Excle_Eq_Sum(param);
				
				if(sumMap!=null){
					
					xssf_w_row = xssf_w_sheet.createRow(titleRows);
					Object LEASE_TOPRICALL = sumMap.get("LEASE_TOPRICALL") == null ? "" : sumMap.get("LEASE_TOPRICALL");
					Object PAY_MONEYALL = sumMap.get("PAY_MONEYALL") == null ? "" : sumMap.get("PAY_MONEYALL");
					Object AMOUNTALL=sumMap.get("AMOUNTALL") == null ? "" : sumMap.get("AMOUNTALL");
				   
					
					LEASE_TOPRIC_JIS += ((java.math.BigDecimal) LEASE_TOPRICALL).doubleValue();
					PAY_MONEY_JIS += ((java.math.BigDecimal) PAY_MONEYALL).doubleValue();
					AMOUNT_JIS+= ((java.math.BigDecimal) AMOUNTALL).doubleValue();
					
					
					Object SUPPER_NAME = sumMap.get("SUPPER_NAME") == null ? "" : sumMap.get("SUPPER_NAME");
					xssf_w_cell = xssf_w_row.createCell(1);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell.setCellStyle(head_cellStyle);						
					xssf_w_cell.setCellValue(SUPPER_NAME.toString()+" 汇总");
					
					xssf_w_cell = xssf_w_row.createCell(6);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num_red);
					xssf_w_cell.setCellValue(AMOUNTALL.toString());
					
					xssf_w_cell = xssf_w_row.createCell(8);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num_red);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) LEASE_TOPRICALL).doubleValue());
					
					xssf_w_cell = xssf_w_row.createCell(9);
					xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell.setCellStyle(cellStyle_num_red);
					xssf_w_cell.setCellValue(((java.math.BigDecimal) PAY_MONEYALL).doubleValue());
					
					++jianNum;
					++titleRows;
				}
				
				
				Map<String,Object> sumMap2 = pay_Detail_Excle_Eq_Sum2(param);
				if(sumMap2!=null){
					
					xssf_w_row2 = xssf_w_sheet2.createRow(titleRows2);
					Object LEASE_TOPRICALL = sumMap2.get("LEASE_TOPRICALL") == null ? "" : sumMap2.get("LEASE_TOPRICALL");
					Object SQ_PRICEALL = sumMap2.get("SQ_PRICEALL") == null ? "" : sumMap2.get("SQ_PRICEALL");
					Object DB_PRICEALL = sumMap2.get("DB_PRICEALL") == null ? "" : sumMap2.get("DB_PRICEALL");
					Object FIRST_RENTALL = sumMap2.get("FIRST_RENTALL") == null ? "" : sumMap2.get("FIRST_RENTALL");
					Object QTFYFALL = sumMap2.get("QTFYFALL") == null ? "" : sumMap2.get("QTFYFALL");
					Object CS_PRICEALL = sumMap2.get("CS_PRICEALL") == null ? "" : sumMap2.get("CS_PRICEALL");
					Object PAY_MONEYALL = sumMap2.get("PAY_MONEYALL") == null ? "" : sumMap2.get("PAY_MONEYALL");
					Object AMOUNTALL=sumMap2.get("AMOUNTALL") == null ? "" : sumMap2.get("AMOUNTALL");
				   
					Object SUPPER_NAME = sumMap2.get("SUPPER_NAME") == null ? "" : sumMap2.get("SUPPER_NAME");
					
					LEASE_TOPRIC_JIS2 += ((java.math.BigDecimal) LEASE_TOPRICALL).doubleValue();
					SQ_PRICE_JIS2 += ((java.math.BigDecimal) SQ_PRICEALL).doubleValue();
					DB_PRICE_JIS2 += ((java.math.BigDecimal) DB_PRICEALL).doubleValue();
					FIRST_RENT_JIS2 += ((java.math.BigDecimal) FIRST_RENTALL).doubleValue();
					QTFYF_JIS2 += ((java.math.BigDecimal) QTFYFALL).doubleValue();
					PAY_MONEY_JIS2 += ((java.math.BigDecimal) PAY_MONEYALL).doubleValue();
					AMOUNT_JIS2+= ((java.math.BigDecimal) AMOUNTALL).doubleValue();
					CS_PRICEALL_JIS2+= ((java.math.BigDecimal) CS_PRICEALL).doubleValue();
					
					
	
					xssf_w_cell2 = xssf_w_row2.createCell(1);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell2.setCellStyle(head_cellStyle);						
					xssf_w_cell2.setCellValue(SUPPER_NAME.toString()+" 汇总");

					xssf_w_cell2 = xssf_w_row2.createCell(6);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num_red);
					xssf_w_cell2.setCellValue(AMOUNTALL.toString());
					
					xssf_w_cell2 = xssf_w_row2.createCell(8);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num_red);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) LEASE_TOPRICALL).doubleValue());
					
					xssf_w_cell2 = xssf_w_row2.createCell(9);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num_red);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) SQ_PRICEALL).doubleValue());
					
					xssf_w_cell2 = xssf_w_row2.createCell(10);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num_red);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) DB_PRICEALL).doubleValue());
					
					xssf_w_cell2 = xssf_w_row2.createCell(11);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num_red);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) CS_PRICEALL).doubleValue());
					
					xssf_w_cell2 = xssf_w_row2.createCell(12);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num_red);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) QTFYFALL).doubleValue());
					
					xssf_w_cell2 = xssf_w_row2.createCell(13);
					xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell2.setCellStyle(cellStyle_num_red);
					xssf_w_cell2.setCellValue(((java.math.BigDecimal) PAY_MONEYALL).doubleValue());
					
					++jianNum2;
					++titleRows2;
				}
				
				Map<String,Object> sumMap3 = pay_Detail_Excle_Eq_Sum3(param);
				
				if(sumMap3!=null){
					
					xssf_w_row3 = xssf_w_sheet3.createRow(titleRows3);
					Object LEASE_TOPRICALL = sumMap3.get("LEASE_TOPRICALL") == null ? "" : sumMap3.get("LEASE_TOPRICALL");
					Object DB_PRICEALL = sumMap3.get("DB_PRICEALL") == null ? "" : sumMap3.get("DB_PRICEALL");
					Object PAY_MONEYALL = sumMap3.get("PAY_MONEYALL") == null ? "" : sumMap3.get("PAY_MONEYALL");
					Object AMOUNTALL=sumMap3.get("AMOUNTALL") == null ? "" : sumMap3.get("AMOUNTALL");
				   
					Object SUPPER_NAME = sumMap3.get("SUPPER_NAME") == null ? "" : sumMap3.get("SUPPER_NAME");
					
					LEASE_TOPRIC_JIS3 += ((java.math.BigDecimal) LEASE_TOPRICALL).doubleValue();
					DB_PRICE_JIS3 += ((java.math.BigDecimal) DB_PRICEALL).doubleValue();
					PAY_MONEY_JIS3 += ((java.math.BigDecimal) PAY_MONEYALL).doubleValue();
					AMOUNT_JIS3+= ((java.math.BigDecimal) AMOUNTALL).doubleValue();
					
					xssf_w_cell3 = xssf_w_row3.createCell(1);
					xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell3.setCellStyle(head_cellStyle);						
					xssf_w_cell3.setCellValue(SUPPER_NAME.toString()+" 汇总");

					xssf_w_cell3 = xssf_w_row3.createCell(6);
					xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell3.setCellStyle(cellStyle_num_red);
					xssf_w_cell3.setCellValue(AMOUNTALL.toString());
					
					xssf_w_cell3 = xssf_w_row2.createCell(8);
					xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell3.setCellStyle(cellStyle_num_red);
					xssf_w_cell3.setCellValue(((java.math.BigDecimal) LEASE_TOPRICALL).doubleValue());
					
					xssf_w_cell3 = xssf_w_row2.createCell(9);
					xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell3.setCellStyle(cellStyle_num_red);
					xssf_w_cell3.setCellValue(((java.math.BigDecimal) DB_PRICEALL).doubleValue());
					
					xssf_w_cell3 = xssf_w_row2.createCell(10);
					xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell3.setCellStyle(cellStyle_num_red);
					xssf_w_cell3.setCellValue(((java.math.BigDecimal) PAY_MONEYALL).doubleValue());
					
					++jianNum3;++titleRows3;
				}
				
				Map<String,Object> sumMap4 = pay_Detail_Excle_Eq_Sum4(param);
				
				if(sumMap4!=null){
					xssf_w_row4 = xssf_w_sheet4.createRow(titleRows4);
					Object LEASE_TOPRICALL = sumMap4.get("LEASE_TOPRICALL") == null ? "" : sumMap4.get("LEASE_TOPRICALL");
					Object QZZJALL = sumMap4.get("QZZJALL") == null ? "" : sumMap4.get("QZZJALL");
					Object LGJKALL = sumMap4.get("LGJKALL") == null ? "" : sumMap4.get("LGJKALL");
					Object PAY_MONEYALL = sumMap4.get("PAY_MONEYALL") == null ? "" : sumMap4.get("PAY_MONEYALL");
					Object AMOUNTALL=sumMap4.get("AMOUNTALL") == null ? "" : sumMap4.get("AMOUNTALL");
				   
					Object SUPPER_NAME = sumMap4.get("SUPPER_NAME") == null ? "" : sumMap4.get("SUPPER_NAME");
					
					LEASE_TOPRIC_JIS4 += ((java.math.BigDecimal) LEASE_TOPRICALL).doubleValue();
					QZZJ_JIS4 += ((java.math.BigDecimal) QZZJALL).doubleValue();
					LGJK_JIS4 += ((java.math.BigDecimal) LGJKALL).doubleValue();
					PAY_MONEY_JIS4 += ((java.math.BigDecimal) PAY_MONEYALL).doubleValue();
					AMOUNT_JIS4 += ((java.math.BigDecimal) AMOUNTALL).doubleValue();
					
					xssf_w_cell4 = xssf_w_row4.createCell(1);
					xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
					xssf_w_cell4.setCellStyle(head_cellStyle);						
					xssf_w_cell4.setCellValue(SUPPER_NAME.toString()+" 汇总");

					xssf_w_cell4 = xssf_w_row4.createCell(6);
					xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell4.setCellStyle(cellStyle_num_red);
					xssf_w_cell4.setCellValue(AMOUNTALL.toString());
					
					xssf_w_cell4 = xssf_w_row4.createCell(8);
					xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell4.setCellStyle(cellStyle_num_red);
					xssf_w_cell4.setCellValue(((java.math.BigDecimal) LEASE_TOPRICALL).doubleValue());
					
					xssf_w_cell4 = xssf_w_row4.createCell(9);
					xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell4.setCellStyle(cellStyle_num_red);
					xssf_w_cell4.setCellValue(((java.math.BigDecimal) QZZJALL).doubleValue());
					
					xssf_w_cell4 = xssf_w_row4.createCell(10);
					xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell4.setCellStyle(cellStyle_num_red);
					xssf_w_cell4.setCellValue(((java.math.BigDecimal) LGJKALL).doubleValue());
					
					xssf_w_cell4 = xssf_w_row4.createCell(11);
					xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					xssf_w_cell4.setCellStyle(cellStyle_num_red);
					xssf_w_cell4.setCellValue(((java.math.BigDecimal) PAY_MONEYALL).doubleValue());
					
					++jianNum4;++titleRows4;
				}
			}
			xssf_w_row = xssf_w_sheet.createRow(titleRows);
			xssf_w_row2 = xssf_w_sheet2.createRow(titleRows2);
			xssf_w_row3 = xssf_w_sheet3.createRow(titleRows3);
			xssf_w_row4 = xssf_w_sheet4.createRow(titleRows4);
			
			xssf_w_cell = xssf_w_row.createCell(1);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell.setCellStyle(head_cellStyle);						
			xssf_w_cell.setCellValue(" 合计 ");

			xssf_w_cell = xssf_w_row.createCell(6);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell.setCellStyle(cellStyle_num_red);
			xssf_w_cell.setCellValue(AMOUNT_JIS);
			
			xssf_w_cell = xssf_w_row.createCell(8);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell.setCellStyle(cellStyle_num_red);
			xssf_w_cell.setCellValue(LEASE_TOPRIC_JIS);
			
			xssf_w_cell = xssf_w_row.createCell(9);
			xssf_w_cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell.setCellStyle(cellStyle_num_red);
			xssf_w_cell.setCellValue(PAY_MONEY_JIS);
			
			xssf_w_cell2 = xssf_w_row2.createCell(1);
			xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell2.setCellStyle(head_cellStyle);						
			xssf_w_cell2.setCellValue(" 合计 ");
			
			xssf_w_cell2 = xssf_w_row2.createCell(6);
			xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell2.setCellStyle(cellStyle_num_red);
			xssf_w_cell2.setCellValue(AMOUNT_JIS2);
			
			xssf_w_cell2 = xssf_w_row2.createCell(8);
			xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell2.setCellStyle(cellStyle_num_red);
			xssf_w_cell2.setCellValue(LEASE_TOPRIC_JIS2);
			
			xssf_w_cell2 = xssf_w_row2.createCell(9);
			xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell2.setCellStyle(cellStyle_num_red);
			xssf_w_cell2.setCellValue(SQ_PRICE_JIS2);
			
			xssf_w_cell2 = xssf_w_row2.createCell(10);
			xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell2.setCellStyle(cellStyle_num_red);
			xssf_w_cell2.setCellValue(DB_PRICE_JIS2);
			
			xssf_w_cell2 = xssf_w_row2.createCell(11);
			xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell2.setCellStyle(cellStyle_num_red);
			xssf_w_cell2.setCellValue(CS_PRICEALL_JIS2);
			
			xssf_w_cell2 = xssf_w_row2.createCell(12);
			xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell2.setCellStyle(cellStyle_num_red);
			xssf_w_cell2.setCellValue(QTFYF_JIS2);
			
			xssf_w_cell2 = xssf_w_row2.createCell(13);
			xssf_w_cell2.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell2.setCellStyle(cellStyle_num_red);
			xssf_w_cell2.setCellValue(PAY_MONEY_JIS2);
			
			
			xssf_w_cell3 = xssf_w_row3.createCell(1);
			xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell3.setCellStyle(head_cellStyle);						
			xssf_w_cell3.setCellValue(" 合计 ");
			
			xssf_w_cell3 = xssf_w_row3.createCell(6);
			xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell3.setCellStyle(cellStyle_num_red);
			xssf_w_cell3.setCellValue(AMOUNT_JIS3);
			
			xssf_w_cell3 = xssf_w_row3.createCell(8);
			xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell3.setCellStyle(cellStyle_num_red);
			xssf_w_cell3.setCellValue(LEASE_TOPRIC_JIS3);
			
			xssf_w_cell3 = xssf_w_row3.createCell(9);
			xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell3.setCellStyle(cellStyle_num_red);
			xssf_w_cell3.setCellValue(DB_PRICE_JIS3);
			
			xssf_w_cell3 = xssf_w_row3.createCell(10);
			xssf_w_cell3.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell3.setCellStyle(cellStyle_num_red);
			xssf_w_cell3.setCellValue(PAY_MONEY_JIS3);
			
			xssf_w_cell4 = xssf_w_row4.createCell(1);
			xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_STRING);
			xssf_w_cell4.setCellStyle(head_cellStyle);						
			xssf_w_cell4.setCellValue(" 合计 ");
			
			xssf_w_cell4 = xssf_w_row4.createCell(6);
			xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell4.setCellStyle(cellStyle_num_red);
			xssf_w_cell4.setCellValue(AMOUNT_JIS4);
			
			xssf_w_cell4 = xssf_w_row4.createCell(8);
			xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell4.setCellStyle(cellStyle_num_red);
			xssf_w_cell4.setCellValue(LEASE_TOPRIC_JIS4);
			
			xssf_w_cell4 = xssf_w_row4.createCell(9);
			xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell4.setCellStyle(cellStyle_num_red);
			xssf_w_cell4.setCellValue(QZZJ_JIS4);
			
			xssf_w_cell4 = xssf_w_row4.createCell(10);
			xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell4.setCellStyle(cellStyle_num_red);
			xssf_w_cell4.setCellValue(LGJK_JIS4);
			
			xssf_w_cell4 = xssf_w_row4.createCell(11);
			xssf_w_cell4.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			xssf_w_cell4.setCellStyle(cellStyle_num_red);
			xssf_w_cell4.setCellValue(PAY_MONEY_JIS4);
			
			
			SkyEye.getResponse().reset();
			SkyEye.getResponse().setCharacterEncoding("UTF-8");
			SkyEye.getResponse().setHeader(
					"Content-Disposition",
					new StringBuilder("attachment;filename=").append(
							new String(("租赁物购买价款明细表"+"_"+DateUtil.getSysDate()+"_"+Math.abs(new Random(100).nextInt())+".xlsx").getBytes("GB2312"),
									"ISO-8859-1")).toString());
			SkyEye.getResponse().setContentType(ResMime.get("xlsx"));
			xssf_w_book.write(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//设备全额放款导出
	public List<Map<String,Object>> pay_Detail_Excle_Eq_Mg(Map<String,Object> m)
	{
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			m.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			m.put("COM_ID",COM_MAP.get("COMPANY_ID"));
		}
		return Dao.selectList("payment.pay_Detail_Excle_Eq_Mg", m);
	}
	
	//设备放款全额导出明细项
	public Map<String,Object> pay_Detail_Excle_Eq_Sum(Map<String,Object> m)
	{
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			m.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			m.put("COM_ID",COM_MAP.get("COMPANY_ID"));
		}
		return Dao.selectOne("payment.pay_Detail_Excle_Eq_Sum", m);
	}
	
	//设备全额放款导出
	public List<Map<String,Object>> pay_Detail_Excle_Eq_Mg2(Map<String,Object> m)
	{
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			m.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			m.put("COM_ID",COM_MAP.get("COMPANY_ID"));
		}
		return Dao.selectList("payment.pay_Detail_Excle_Eq_Mg2", m);
	}
	
	//设备放款全额导出明细项
	public Map<String,Object> pay_Detail_Excle_Eq_Sum2(Map<String,Object> m)
	{
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			m.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			m.put("COM_ID",COM_MAP.get("COMPANY_ID"));
		}
		return Dao.selectOne("payment.pay_Detail_Excle_Eq_Sum2", m);
	}
	
	//设备全额放款导出
	public List<Map<String,Object>> pay_Detail_Excle_Eq_Mg3(Map<String,Object> m)
	{
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			m.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			m.put("COM_ID",COM_MAP.get("COMPANY_ID"));
		}
		return Dao.selectList("payment.pay_Detail_Excle_Eq_Mg3", m);
	}
	
	//设备放款全额导出明细项
	public Map<String,Object> pay_Detail_Excle_Eq_Sum3(Map<String,Object> m)
	{
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			m.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			m.put("COM_ID",COM_MAP.get("COMPANY_ID"));
		}
		return Dao.selectOne("payment.pay_Detail_Excle_Eq_Sum3", m);
	}
	
	//设备全额放款导出
	public List<Map<String,Object>> pay_Detail_Excle_Eq_Mg4(Map<String,Object> m)
	{
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			m.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			m.put("COM_ID",COM_MAP.get("COMPANY_ID"));
		}
		return Dao.selectList("payment.pay_Detail_Excle_Eq_Mg4", m);
	}
	
	//设备放款全额导出明细项
	public Map<String,Object> pay_Detail_Excle_Eq_Sum4(Map<String,Object> m)
	{
		Map SUP_MAP=BaseUtil.getSup();
		if(SUP_MAP!=null){
			m.put("SUP_ID",SUP_MAP.get("SUP_ID"));
		}
		Map COM_MAP=BaseUtil.getCom();
		if(COM_MAP!=null){
			m.put("COM_ID",COM_MAP.get("COMPANY_ID"));
		}
		return Dao.selectOne("payment.pay_Detail_Excle_Eq_Sum4", m);
	}
	/**
	 * 
	* @Title: getAllPaymentContract 付款查询
	* @autor wuguowei_jing@163.com 2014-5-9 下午12:03:34
	* @Description: TODO
	* @param m
	* @return    
	* List<Map<String,Object>>    
	* @throws
	 */
	public List<Map<String,Object>> getAllPaymentContract(Map<String,Object> m){
		
		return Dao.selectList("payment.getAllPaymentContract", m);
	}
	
	//付款保存
		public void payMentSave(Map mapDate)
		{
			
			String ID="";
			JSONObject mapSelect = JSONObject.fromObject(mapDate.get("selectDateHidden"));
			List listDate=(List<Map<String, Object>>)mapSelect.get("selectData");
			for (int hh=0;hh<listDate.size();hh++) {
				Map<String, Object> m = (Map<String, Object>) listDate.get(hh);
				//将收款单的ID保存到细表中
				if(m!=null && m.get("payMent_ID")!=null && !m.get("payMent_ID").equals("") && !m.get("payMent_ID").equals("0"))
				{
					String PAYMENT_HEAD_ID=Dao.getSequence("SEQ_FI_FUND_PAYMENT_HEAD");
					m.put("PAYMENT_HEAD_ID", PAYMENT_HEAD_ID);
					ID=m.get("payMent_ID").toString();
					Dao.update("payment.updatePaymentDetailPayID",m);
					
					/************************************/
					Map map=new HashMap();
					if(ID!=null && !ID.equals(""))
					{
						map.put("pay_ID", ID);
						map=Dao.selectOne("payment.queryPayMentDeatelByPayID", map);
					}
					mapDate.putAll(map);
					mapDate.put("PAYMENT_HEAD_ID", PAYMENT_HEAD_ID);
					
					//应收单编号
					Map paycodeMap=Dao.selectOne("payment.PayCodeCreate");
					mapDate.put("PAYMENT_CODE", paycodeMap.get("PAYMENT_CODE"));
					
					//当前登录人ID
					mapDate.put("USER_ID", Security.getUser().getId());
					// 组织机构应该取缓存 后面在改
					Map ORG_MAP=Dao.selectOne("payment.orgListByUserId", mapDate);
					if(ORG_MAP!=null){
						mapDate.put("ORG_ID", ORG_MAP.get("ORG_ID"));
					}
					
					Dao.insert("payment.createPayMentHead",mapDate);
					/******************************************/
				}
			}
			//查询第一个勾选数据的基本信息
			
		}
		
		public List queryBankListByMa(Map map){
			return Dao.selectList("payment.queryBankListByMa",map);
		}
}
