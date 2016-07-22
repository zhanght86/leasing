$(function(){
	//新建业务申请必填项判断
	$(".Required").blur(function(){
		if($(this).val()==''||$(this).val()==null){
			alert("请维护必填项信息:["+$(this).parent("td").prev().text()+"]");
			return ;
		}
	});
	
	$("#SALE_NAME").change(function(){
		if("其他" == $(this).val()){
			$("#otherName").show();
		}else{
			$("#otherName").hide();
		}
	});
});

/**
 * 根据客户名称创建项目名称
 * 
 * @author King 2015年3月3日
 */
function createProjectName(){
	$("#PROJECT_NAME").val($("#CLIENT_NAME").val()+$("#PROJECT_NAME_TIME").val());
}

/**
 * 验证手机号码
 * 
 * @author King 2015年3月5日
 */
function checkMobile(){
	//$.match(/^0?(13[0-9]|15[012356789]|18[0236789]|14[57])[0-9]{8}$/);
	if ($("#CLIENT_MOBILE").val().length!=11) {
		alert("手机号码格式不正确！");
		$("#CLIENT_MOBILE").val("")
		return ;
	} 
}

/**
 * 必选项
 * 
 * @author caizhongyang
 */
function checkBySelect(){
	/**经销商 | 厂商 | 品牌 | 系列 | 型号 必选项begin**/
	var arrayTitle=["经销商","厂商","品牌","系列","型号"];
	
	$("#eq_body").find("tr").each(function(tindex){
//		alert(tindex);
		$(this).find("select").each(function(index){
			if(index<5){
			if(0==$(this).get(0).selectedIndex){
				$.messager.alert("提示","请选择第"+(tindex+1)+"行"+arrayTitle[index]);
				return false;
			};
		}
		});
		
	});
	/**经销商 | 厂商 | 品牌 | 系列 | 型号 必选项end**/
}
/**
 * add by lishuo 12-30-2015
 * 捷众必填，捷翊不必填
 */
function CheckChoseType(){
	var CarColor,CarType;
    CarColor = $("#PLATFORM_TYPE").find("option:selected").text().indexOf('捷翊') > -1? '捷翊':'捷众';
    CarType  = $("#PLATFORM_TYPE").find("option:selected").text().indexOf('捷翊') > -1? '捷翊':'捷众';
	if( CarColor=='捷翊'&&CarType=='捷翊'){
		$(".hidden").hide();
	}
    if( CarColor=='捷众'&&CarType=='捷众'){
		$(".hidden").show();
	}
}
/**
 * 新建申请报价测算
 * 
 * @author King 2015年3月5日
 */
function webCalculate(){
	/*2016年4月15日 11:32:39添加*/
	//FIRSTPAYALL();
	
	changeStartMoney();
	//add by lishuo 01-26-2016 固定月租产品超5.6W提醒 Start
	var UNIT_PRICE = $("input[name='UNIT_PRICE']").val();
	var scheme_code =$("#scheme_code").attr("title");
	if(scheme_code !=null && scheme_code!= '' && scheme_code.indexOf('月租') > 0 ){
		if(UNIT_PRICE<=40000 || UNIT_PRICE>=56000){
			$.messager.alert("提示","五菱固定月租方案限于车价4万元~5.6万元范围内使用!");
		}
	}
	//add by lishuo 01-26-2016 固定月租产品超5.6W提醒 End
	checkBySelect();
	var LEASE_TOPRIC_SY=$("#LEASE_TOPRIC_SY").val();
	if(LEASE_TOPRIC_SY!=''){
		var LEASE_TOPRIC=$("input[name='LEASE_TOPRIC']").val();//设备总额
		if(parseFloat(LEASE_TOPRIC)>parseFloat(LEASE_TOPRIC_SY)){
			alert("实际成交价大于项目剩余金额");
			return;
		}
		//add by lishuo 12.10.2015
		/*var LEASE_TOPRIC_ =$("#LEASE_TOPRIC").val();
		var CODE="JJXE";
		var url=_basePath+"/project/project!CheckTotalPrice.action";
		var args={"CODE":CODE};
		var QueryPrice;
		$.ajax({
			url:url,
			data:args,
			dataType:"json",
			type:"post",
			success:function(data){
				if(data){
					var json = data.data;
					QueryPrice=json[0].FLAG;
					if(LEASE_TOPRIC_ - QueryPrice >0){
						$.messager.alert("提示","车款融资金额超出，请修改！");
					}
				}
			}
		});*/
	}
	var flag=true;
	$("#submitAddScheme_1").removeAttr("disabled");
	//新建业务申请必填项判断
	$(".Required").each(function(){
		if(flag)
			if($(this).val()==''||$(this).val()==null){
				alert("请维护必填项信息:["+$(this).parent("td").prev().text()+"]");
				flag=false;
				return;
			}
	});
	
	if($("#SALE_NAME").val()=='' || ($("#SALE_NAME").val()=='其他' && $("#otherName").val()=='')){
		alert("请维护必填项信息:[销售人员]");
		return;
	}
	
	//add by lishuo 12-29-2015 Start
	CarColor = $("#PLATFORM_TYPE").find("option:selected").text().indexOf('捷翊') > -1? '捷翊':'捷众';
    CarType  = $("#PLATFORM_TYPE").find("option:selected").text().indexOf('捷翊') > -1? '捷翊':'捷众';
	
	if( CarColor=='捷众'&&$("#CAR_COLOR").val()==''){
		alert("请维护必填项信息:[颜色]");
		return;
	}
	
	if( CarType=='捷众'&&$("#XH_PARAM").val()==''){
		alert("请维护必填项信息:[型号参数]");
		return;
	}
	//add by lishuo 12-29-2015 End
	
	if($("#PROJECT_AREA").val()==''){
		alert("请维护必填项信息:[所属区域省]");
		return;
	}
	if($("#PROJECT_CITY").val()==''){
		alert("请维护必填项信息:[所属区域市]");
		return;
	}
	if($("#PLATFORM_TYPE").val()==''){
		alert("请维护必填项信息:[业务类型]");
		return;
	}
	/*2016年4月14日 09:54:17  吴国伟 P2P金融新算法涉及到的字段  start*/
	var PAY_WAY=$("#PAY_WAY").val();
	var SCHEME_SYL_BZ_VALUE = $("#SCHEME_SYL_BZ_VALUE").val();
	/*----------------------------------------------------end*/
	if(flag==false){
		return;
	}else {
		if($("#LEASE_TOPRIC_ZD").val()!='0'&&$("#LEASE_TOPRIC_ZD").val()!=null){
//			checkMobile();
			/*2016年4月13日 15:31:43 吴国伟修改 加P2P金融算法*/
			if(PAY_WAY==8){
				if(!(SCHEME_SYL_BZ_VALUE!=null && SCHEME_SYL_BZ_VALUE !='0' && SCHEME_SYL_BZ_VALUE !='')){
					alert("请先录入收益率标准");
					return;
				}
				p2p_calculate();
				
			}else{
				calculate();
			}
			
		}else
			alert("请先录入租赁物信息");
	}
}
function changeCalculate(){
	//首付款融资融计算
	FIRSTPAYALL();
	/*计算融资额*/
	changeStartMoney();
	var PAY_WAY=$("#PAY_WAY").val();
	var SCHEME_SYL_BZ_VALUE = $("#SCHEME_SYL_BZ_VALUE").val();
			/*2016年4月13日 15:31:43 吴国伟修改 加P2P金融算法*/
			if(PAY_WAY==8){
				if(!(SCHEME_SYL_BZ_VALUE!=null && SCHEME_SYL_BZ_VALUE !='0' && SCHEME_SYL_BZ_VALUE !='')){
					alert("请先录入收益率标准");
					return;
				}
				p2p_calculate();
				
			}else{
				calculate();
			}
}


/**
 * 新建申请报价保存
 * @param _TYPE
 * @author King 2015年3月5日
 */
function websubmitAddScheme(_TYPE)
{
	/**经销商 | 厂商 | 品牌 | 系列 | 型号 必选项begin**/
	var arrayTitle=["经销商","厂商","品牌","系列","型号"];
	var eq_bodyflag = 1;
	$("#eq_body").find("tr").each(function(tindex){
//		alert(tindex);
		$(this).find("select").each(function(index){
			if(index<5){
			if(0==$(this).get(0).selectedIndex){
				$.messager.alert("提示","请选择第"+(tindex+1)+"行"+arrayTitle[index]);
				eq_bodyflag =2 ;
				return false;
			};
		}
		});
		
	});
	if (eq_bodyflag == 2) {
	 return;	
	}
	/**经销商 | 厂商 | 品牌 | 系列 | 型号 必选项end**/
	
	var test1 = $('#lease_term').val()-1;
	var last_zj = $('.datagrid-body [field="zj"]:eq('+test1+')').text();
	$("#websubmitAddScheme").attr("disabled",true);
	$("#websubmitAddScheme").linkbutton("disable");
	
	var MONEYALL=$("#MONEYALL").val();
	if(MONEYALL==null||MONEYALL==''){
		$.messager.alert("提示","请先进行测算");
		if(_TYPE!='1'){
			$("#websubmitAddScheme").attr("disabled",false);
			$("#websubmitAddScheme").linkbutton("enable");
		}
		return;
	}
	var JBPMFLAG=$("#JBPMFLAG").val();
	if(_TYPE=='1'&&JBPMFLAG=='0'){
		$.messager.alert("提示","请先保存项目方案信息");
		if(_TYPE!='1'){
			$("#websubmitAddScheme").attr("disabled",false);
			$("#websubmitAddScheme").linkbutton("enable");
		}
		return ;
	}
	//add gengchangbao JZZL-182 2016年5月11日10:03:00 start
	var CC_PRICE=$("input[name='CC_PRICE']").val();//车采价格  
	var REAL_PRICE=$("input[name='REAL_PRICE']").val();//真实价格
	//add gengchangbao JZZL-182 2016年5月11日10:03:00 end
	var LEASE_TOPRIC=$("input[name='LEASE_TOPRIC']").val();//设备总额
	var FINANCE_TOPRIC=$("input[name='FINANCE_TOPRIC']").val();//融资额
	var MONEYALL=$("input[name='MONEYALL']").val();//租金合计
	MONEYALL=accSub(MONEYALL,accSub(LEASE_TOPRIC,FINANCE_TOPRIC));//租金合计-起租租金
	
	var SCHEME_ROW_NUM=$("#SCHEME_ROW_NUM").val();
	
	var ISWITHHOLDING=$("input[name='ISWITHHOLDING']:checked").val();
	var PLATFORM_TYPE=$("#PLATFORM_TYPE").val();
	
	var LEASE_TERM=$("#lease_term").val();
	if (LEASE_TERM == "" || LEASE_TERM == undefined || LEASE_TERM<=0) {
		flag = false;
		$("#lease_term").focus();
		$.messager.alert("提示","请输入期限");
		if(_TYPE!='1'){
			$("#websubmitAddScheme").attr("disabled",false);
			$("#websubmitAddScheme").linkbutton("enable");
		}
		return;
	};
	
	var lease_period=0;
	if($("#lease_period").length > 0 )
	{
		lease_period=$("#lease_period").find("option:selected").val();
	}
	else
	{
		$.messager.alert("提示","请输入周期!");
		if(_TYPE!='1'){
			$("#websubmitAddScheme").attr("disabled",false);
			$("#websubmitAddScheme").linkbutton("enable");
		}
		return;
	}
	var _payCountOfYear=accDiv(12,lease_period);
	
	var LEASE_PERIOD=$("#lease_period").find("option:selected").val();
	var YEAR_INTEREST=0;
	if($("#YEAR_INTEREST").length > 0 )
	{
		YEAR_INTEREST=$("#YEAR_INTEREST").val();
	}
	
	if(PLATFORM_TYPE !='7'){
		if (YEAR_INTEREST == "" || YEAR_INTEREST == undefined || YEAR_INTEREST<=0) {
			flag = false;
			$("#YEAR_INTEREST").focus();
			$.messager.alert("提示","请维护商务政策，确保存在年利率！");
			if(_TYPE!='1'){
				$("#websubmitAddScheme").attr("disabled",false);
				$("#websubmitAddScheme").linkbutton("enable");
			}
			return;
		};
	}
	
	
	var YEAR_INTEREST_Pay=formatNumber(accDiv(YEAR_INTEREST,100),"0.0000");
	
	var PAY_WAY_="";
	if($("select[name='PAY_WAY']").length > 0 )
	{
		PAY_WAY_=$("select[name='PAY_WAY']").find("option:selected").val();
	}
	else
	{
		$.messager.alert("提示","请输入支付方式!");
		if(_TYPE!='1'){
			$("#websubmitAddScheme").attr("disabled",false);
			$("#websubmitAddScheme").linkbutton("enable");
		}
		return;
	}
	var FIRSTPAYDATE=$("#FIRSTPAYDATE").datebox('getValue');
//	if(FIRSTPAYDATE==null||FIRSTPAYDATE==''){
//		$.messager.alert("提示","请输入首期付款日期！");
//		$("#websubmitAddScheme").attr("disabled",false);
//		$("#websubmitAddScheme").linkbutton("enable");
//		return ;
//	}
	
	var FEES='0';
	if($("#fees").length > 0 ){
		FEES=$("#fees").val();
	}
	
	var FEES_PRICE='0';
	if($("#FEES_PRICE").length > 0 ){
		FEES_PRICE=$("#FEES_PRICE").val();
	}
//	var guaranList=[];
//	 $(".guaran_body_tr").each(function(){
//				var temp={};
//				temp.GUARAN_TYPE = $(this).find("select[name='GUARAN_TYPE']").find("option:selected").val();
//				temp.GUARAN_NAME = $(this).find("[name='GUARAN_NAME']").val();
//				temp.ORGANIZATION_CODE = $(this).find("[name='ORGANIZATION_CODE']").val();
//				temp.GUARAN_PHONE = $(this).find("[name='GUARAN_PHONE']").val();
//				temp.FAUSTPFAND=$(this).find("[name='FAUSTPFAND']").val();
//				temp.GUARANTEE_ENTITY=$(this).find("[name='GUARANTEE_ENTITY']").val();
//				guaranList.push(temp);
//	 });
	//---------汽车颜色------start--
	var carcolor = $('#CAR_COLOR').val();
	var xhparam = $('#XH_PARAM').val();
	var bx = $('#BX').val();
	var ccs = $('#CCS').val();
	var pro_remark = $('#PRO_REMARK').val();
	//交强险
	var jqx = $('#JQX').val();
	
	//---------汽车颜色------end--
	var REMARK=$("#REMARK").val();
	var PRO_CODE=$("#PRO_CODE").val();
	var CLIENT_ID=$("#CLIENT_ID").val();
	var FINAL_CUST_ID=$("#FINAL_CUST_ID").val();
	var INVOICE_TARGET_TYPE = $("select[name='INVOICE_TARGET_TYPE']").find("option:selected").val();
	var SUPPLIERS_ID = $("select[name='SUPPLIERS_ID']").find("option:selected").val();
	//----------------准备还款计划数据--------开始
	var Rows = $('#pageTable').datagrid('getRows');
	var data1 = "&annualRate="+YEAR_INTEREST_Pay+"&_leaseTerm="+LEASE_TERM+"&&residualPrincipal="+FINANCE_TOPRIC+"&_payCountOfYear="+_payCountOfYear+"&pay_way="+PAY_WAY_+"&project_code="+PRO_CODE+"&rows="+JSON.stringify(Rows);
	//----------------准备还款计划数据--------结束
	var data_="PLATFORM_TYPE="+PLATFORM_TYPE+"&FIRSTPAYDATE="+$("#FIRSTPAYDATE").datebox('getValue')
				+"&PRO_CODE="+PRO_CODE+"&LEASE_TOPRIC="
				+$("#LEASE_TOPRIC").val()+"&SCHEME_ROW_NUM="+SCHEME_ROW_NUM+"&CLIENT_NAME="+$("#CLIENT_NAME").val()+"&ISWITHHOLDING="+ISWITHHOLDING+"&FINAL_CUST_ID="+FINAL_CUST_ID
				+"&CLIENT_MOBILE="+$("#CLIENT_MOBILE").val()+"&INVOICE_TARGET_TYPE="+INVOICE_TARGET_TYPE
				+"&CLIENT_TYPE="+$("#CLIENT_TYPE").val()+"&LEASE_TERM="+LEASE_TERM+"&LEASE_PERIOD="+LEASE_PERIOD
				+"&FINANCE_TOPRIC="+FINANCE_TOPRIC+"&YEAR_INTEREST="+YEAR_INTEREST+"&REMARK="+REMARK
				+"&FEES="+FEES+"&FEES_PRICE="+FEES_PRICE+"&CC_PRICE="+CC_PRICE +"&REAL_PRICE="+REAL_PRICE
				+"&projectScheme="+getProjectSchemeBaseInfo("#addSchemeForm")+"&MONEYALL="+$("input[name='MONEYALL']").val();
	data_ = data_ + data1;
	data_=data_+"&_TYPE="+_TYPE;
	var FIRST_MONEY_ALL=$("#FIRSTPAYALL").val();
	data_=data_+"&FIRST_MONEY_ALL="+FIRST_MONEY_ALL;
	var RENT_WAY_INVOICE=$("#RENT_WAY_INVOICE").val();
	if(RENT_WAY_INVOICE!=''&&RENT_WAY_INVOICE!=null&&RENT_WAY_INVOICE!=undefined&&RENT_WAY_INVOICE!="undefined"){
		data_=data_+"&RENT_WAY_INVOICE="+RENT_WAY_INVOICE;
	}
	//如果选择其他的时候SALE_NAME取输入框 的值
	var SALE_NAME = $("#SALE_NAME").val();
	if(SALE_NAME==="其他"){
		SALE_NAME = $("#otherName").val();
	}
	var TOTHEMONEY = $("#TOTHEMONEY").val();
		data_=data_+"&PRO_NAME="+$("#PROJECT_NAME").val()+"&SUPPLIERS_ID="+SUPPLIERS_ID+"&STAUS=0&AREA_ID="+jQuery.trim($("#PROJECT_AREA").find("option:selected").val())
				+"&CUSTOMER_TYPE="+$("#CUSTOMER_TYPE").val()+"&POB_ID="+$("#SCHEME_CODE").val()
				+"&PROVINCE_NAME="+jQuery.trim($("#PROJECT_AREA").find("option:selected").text())
				+"&CITY_ID="+jQuery.trim($("#PROJECT_CITY").find("option:selected").val())
				+"&CITY_NAME"+jQuery.trim($("#PROJECT_CITY").find("option:selected").text())
				+"&INDUSTRY_FICATION="+jQuery.trim($("#INDUSTRY_FICATION").find("option:selected").val())
				+"&EqList="+getEqList()+"&GUARANTEE="+$("#ISGUARANTOR").val()+"&JOINT_TENANT="+$("#JOINT_TENANT").val()
				+"&THING_KIND="+$("#THING_KIND").val()+"&KEQUN="+$("#KEQUN").val()+"&JOINT_TENANT_ID="+$("#JOINT_TENANT_ID").val();
		data_ += "&MONTH_RENT="+last_zj+"&CAR_COLOR="+carcolor+"&XH_PARAM="+xhparam+"&BX="+bx+"&CCS="+ccs+"&PRO_REMARK="+pro_remark+"&JQX="+jqx+"&SALE_NAME="+SALE_NAME+"&MARK="+$("#MARK").val()+"&YSZJQC="+$("#YSZJQC").val()
				+"&TOTHEMONEY="+TOTHEMONEY;
		var url=_basePath+"/project/WebProject!addSchemeForProject.action";
	jQuery.ajax({
        type: "POST",
        dataType: "json",
		url:url,
		data:data_,
		success:function(json){
			if(json.flag){
//					window.location.href=_basePath+"/project/project!projectUpdate.action?PROJECT_ID="+json.data;
					top.addTab("项目修改",_basePath+"/project/project!projectUpdate.action?PROJECT_ID="+json.data);
					top.closeTab("项目立项");
			}else{
			
				if(_TYPE!='1'){
					$("#websubmitAddScheme").attr("disabled",false);
					$("#websubmitAddScheme").linkbutton("enable");
				}
			}
		}
	});		
}