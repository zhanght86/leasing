$(document).ready(function(){
	$(".change").each(
			function() {
				changeMoney(this);
			});
//	LOAN_MONEY_JS();
	loadSupAccountInit();
	$("#START_PERCENT").change(function(){
		getYearRate();
		$(".change").each(
		function() {
			changeMoney(this);
		});
	});
	
	if($("#PAY_WAY").val()!=8){
		$("#P2P_ID").css("display","none");
	}
});

function changeEqumentMoney()
{
	var LEASE_TOPRIC1=$("#LEASE_TOPRIC1").val();
	var LEASE_TOPRIC=$("#LEASE_TOPRIC").val();
	var ii=-1;
	try{
		ii=$("#SCHEME_NAME1").val().indexOf("打包");//方案名称中是否包含打包二字
	}catch(Exception){};
	var LEASE_TOPRIC2=Math.round(LEASE_TOPRIC1*1.13);//打包标准方案下,实际成交价不能大于指导总价的113%
	if(LEASE_TOPRIC1 !=''){
		if(ii >= 0 && parseFloat(LEASE_TOPRIC1)<parseFloat(LEASE_TOPRIC)){
			if(parseFloat(LEASE_TOPRIC2)<parseFloat(LEASE_TOPRIC)){
				alert("实际成交价不能大于设备总价的113%");
				$("#LEASE_TOPRIC").val(LEASE_TOPRIC2);
			}
		}else if(parseFloat(LEASE_TOPRIC1)<parseFloat(LEASE_TOPRIC)){
			alert("实际成交价不能大于设备总价");
			$("#LEASE_TOPRIC").val(LEASE_TOPRIC1);
		}
	}
	$(".change").each(function() {
			changeMoney(this);
	});
	
}
//点击测算  比例不进行推算
function changeEqumentMoneyCS()
{
	var LEASE_TOPRIC1=$("#LEASE_TOPRIC1").val();
	var LEASE_TOPRIC=$("#LEASE_TOPRIC").val();
	var ii=-1;
	try{
		ii=$("#SCHEME_NAME1").val().indexOf("打包");//方案名称中是否包含打包二字
	}catch(Exception){};
	var LEASE_TOPRIC2=Math.round(LEASE_TOPRIC1*1.13);//打包标准方案下,实际成交价不能大于指导总价的113%
	if(LEASE_TOPRIC1 !=''){
		if(ii >= 0 && parseFloat(LEASE_TOPRIC1)<parseFloat(LEASE_TOPRIC)){
			if(parseFloat(LEASE_TOPRIC2)<parseFloat(LEASE_TOPRIC)){
				alert("实际成交价不能大于设备总价的113%");
				$("#LEASE_TOPRIC").val(LEASE_TOPRIC2);
			}
		}else if(parseFloat(LEASE_TOPRIC1)<parseFloat(LEASE_TOPRIC)){
			alert("实际成交价不能大于设备总价");
			$("#LEASE_TOPRIC").val(LEASE_TOPRIC1);
		}
	}
//	$(".change").each(function() {
//		var name=$(this).attr("sid");
//		if(name=="首期租金比例" || name=="客户保证金比例"){
//			alert("haha--here:"+name);
//			continue;
//		}
//		changeMoney(this);
//	});
	
}


function changefygs(obj){
	var moneyId=$(obj).attr("sid");
	$("#"+moneyId).attr("FYGS",$(obj).val());
	FIRSTPAYALL();
}

/**
 * 首期付款合计
 * @return
 *
 * @author King 2013-10-17
 */
function FIRSTPAYALL(){
	var FIRSTPAYALL=0;
	var FINANCE_TOPRIC=0;
	var LEASE_TOPRIC=$("#LEASE_TOPRIC").val();
	var START_MONEY=$("#START_MONEY").val();
	FINANCE_TOPRIC=accSub(LEASE_TOPRIC, START_MONEY);
	$(".moneygs").each(function(){
		var FYGS=$(this).attr("FYGS");
		if(FYGS=='JRSQK'){
			FIRSTPAYALL=accAdd(FIRSTPAYALL,$(this).val());
		}else if(FYGS=='JRRZE'){
			FINANCE_TOPRIC=accAdd(FINANCE_TOPRIC,$(this).val());
		}
	});
	
	var FEES_PRICE=$("#FEES_PRICE").val();
	var MANAGEMENT_FEETYPE=$("#MANAGEMENT_FEETYPE").val();
	if(MANAGEMENT_FEETYPE==null||MANAGEMENT_FEETYPE==''){
		//手续费收取方式（1：一次性收取  3：按期收取）
		MANAGEMENT_FEETYPE=1;//默认为一次性收取
	}
	
	if(MANAGEMENT_FEETYPE == '1'){
		FIRSTPAYALL=accAdd(FIRSTPAYALL,FEES_PRICE);
	}
	
	
	var PAY_WAY=$("#PAY_WAY").find("option:selected").text();
	if(PAY_WAY.indexOf("期初")>=0){
		var FIRSTMONTHPRICE=$("#FIRSTMONTHPRICE").val();
		if(FIRSTMONTHPRICE==''||FIRSTMONTHPRICE==null||FIRSTMONTHPRICE=="undefined"||FIRSTMONTHPRICE==undefined)
			FIRSTMONTHPRICE=0;
		FIRSTPAYALL=accAdd(FIRSTPAYALL,FIRSTMONTHPRICE);
	}
	$("#FIRSTPAYALL").val(FIRSTPAYALL);
	$("#FINANCE_TOPRIC").val(FINANCE_TOPRIC);
	publicMothod();
}

/**
 * 变更保险方式
 * @return
 *
 * @author King 2013-11-12
 */
function insurance(){
	var insurance=$("#INSURANCE_PERCENT");
	changeMoney(insurance);
}
function changeMoney(obj)
{
	bx_cc();
	BZJBLHJ();
	var computeName=$(obj).attr("compute");
	var CALCULATE=$(obj).attr("CALCULATE");
	var the_class = $(obj).attr("class");
	var the_name = $(obj).attr("sid");
	
	var minValue="";//最低值
	
	if($(obj).attr("minValue"))
	{
		minValue=$(obj).attr("minValue");
		if(minValue!=''&&minValue!=undefined)
		{
			if($(obj).is("input"))
			{
				var baseValue=$(obj).val();
				if(baseValue==''||baseValue==undefined)
				{
					if(the_name!="首期租金比例"){
						$(obj).val(minValue);
					}
					
				}
//				else if(minValue>baseValue)
//				{
//					$(obj).val(minValue);
//					$.messager.alert("提示","该方案的最小起租比例不能低于"+minValue);
//					return;
//				}
			}
		}
	}
	
	
	//设备款
	//var LEASE_TOPRIC=$("#LEASE_TOPRIC").val();
	var LEASE_TOPRIC = $("input[name=TOTAL]").val();
	//融资额
	var FINANCE_TOPRIC=0;
	var START_MONEY =$("input[name=START_MONEY]").val();//取租金
	if(START_MONEY==null||START_MONEY==""||START_MONEY=="undefined"||START_MONEY==undefined){
		START_MONEY=0;
	}
//	var START_MONEY=0;
	var CALCULATE_SQZJ=$("#START_PERCENT").attr("CALCULATE");
	var START_PERCENT=$("#START_PERCENT").val();
	if(START_MONEY > 0)
	{   
		//if(START_PERCENT==""||START_PERCENT==null||START_PERCENT=="undefined"||START_PERCENT==undefined)
		//	START_PERCENT=0;
		
		if(START_PERCENT==""||START_PERCENT==null || START_PERCENT=="undefined"||START_PERCENT==undefined){
			/*
			 * 2016年5月17日 15:21:28  吴国伟修改 RZZL-119 反推比例取消
			 */
			//START_PERCENT=accMul(accDiv(START_MONEY,LEASE_TOPRIC).toFixed(5),100.000);//用租金算出租金比例
			if(CALCULATE_SQZJ=="SBK"){
				START_PERCENT=accMul(accDiv(START_MONEY,LEASE_TOPRIC).toFixed(6),100.000);
			}
		}else{
			    if(the_class=="moneygs" && the_name=="首期租金"){
					if(CALCULATE_SQZJ=="SBK"){
						START_PERCENT=accMul(accDiv(START_MONEY,LEASE_TOPRIC).toFixed(6),100.000);
					}
				/*
				 * 2016年4月8日 13:59:08 吴国伟修改 只有首期租金比例按照设备款计算的才会首次计算首期租金
				 */
				}else if(the_class=="change" && the_name=="首期租金比例" && CALCULATE=="SBK"){
					/*2016年5月17日 14:14:35 吴国伟 首期租金比例为空或undefined 不计算首期租金*/
					if(!(START_PERCENT==""||START_PERCENT==null || START_PERCENT=="undefined"||START_PERCENT==undefined)){
						START_MONEY=accDiv(Math.round(accMul(LEASE_TOPRIC,START_PERCENT)),100);//用租金比例算出租金
						$("[name=START_MONEY]").val(START_MONEY);
					}
					
				}
				//else if(the_class=="change" && the_name=="首期租金比例" && CALCULATE=="RZE"){
				//	FINANCE_TOPRIC = $("#FINANCE_TOPRIC").val();
				//	START_MONEY=accDiv(Math.round(accMul(accAdd(FINANCE_TOPRIC,START_MONEY),START_PERCENT)),100);//用租金比例算出租金
				//	$("[name=START_MONEY]").val(START_MONEY);
				//}
		}
		if(!(LEASE_TOPRIC>0)){
			START_PERCENT="";
		}
//		START_PERCENT_MONEY=accDiv(Math.round(accMul(LEASE_TOPRIC,START_PERCENT)),100);
//		$("[name=START_MONEY]").val(START_MONEY);
		$("#START_PERCENT").val(START_PERCENT);
		START_MONEY=$("input[name=START_MONEY]").val();//租金
		START_PERCENT=$("#START_PERCENT").val();//租金比例
	}
	/**2016年5月13日 15:30:17 吴国伟*/
	if(START_MONEY == 0 )
	{    
		if(the_class=="change" && the_name=="首期租金比例" && CALCULATE_SQZJ=="SBK"){
			START_MONEY=accDiv(Math.round(accMul(LEASE_TOPRIC,START_PERCENT)),100);//用租金比例算出租金
			$("[name=START_MONEY]").val(START_MONEY);
		}else if(the_class=="moneygs" && the_name=="首期租金" && CALCULATE_SQZJ=="SBK"){
			START_MONEY=accDiv(Math.round(accMul(LEASE_TOPRIC,START_PERCENT)),100);//用租金比例算出租金
			$("[name=START_MONEY]").val(START_MONEY);
		}
	}
	/*******************************/
	//保证金和保证金比例  正反推算
	var DEPOSIT_MONEY =$("input[name=DEPOSIT_MONEY]").val();//取客户保证金
	if(DEPOSIT_MONEY==null||DEPOSIT_MONEY==""||DEPOSIT_MONEY=="undefined"||DEPOSIT_MONEY==undefined){
		DEPOSIT_MONEY=0;
	}
	
	if($("input[name=DEPOSIT_MONEY]").length>0){
		var DEPOSIT_PERCENT=$("#DEPOSIT_PERCENT").val();
		if(START_PERCENT=="undefined"||START_PERCENT==undefined){
			DEPOSIT_PERCENT=0;
			/*2016年5月17日 15:22:47 吴国伟 */
		}else if((DEPOSIT_PERCENT==null || DEPOSIT_PERCENT=="")){
//			DEPOSIT_PERCENT=Math.round(accMul(accDiv(DEPOSIT_MONEY,LEASE_TOPRIC),100));//用保证金算出保证金比例
			DEPOSIT_PERCENT=accMul(accDiv(DEPOSIT_MONEY,LEASE_TOPRIC).toFixed(6),100.000);//用保证金算出保证金比例
		}
		else{
			if(the_class=="moneygs" && the_name=="客户保证金"){
				DEPOSIT_PERCENT=accMul(accDiv(DEPOSIT_MONEY,LEASE_TOPRIC).toFixed(6),100.000);//用保证金算出保证金比例
			}else if(the_class=="change" && the_name=="客户保证金比例"){
				/*2016年5月17日 14:16:56 吴国伟 如果客户保证金比例为空或undefined 则不计算客户保证金*/
				if(!(DEPOSIT_PERCENT==null || DEPOSIT_PERCENT=="" || DEPOSIT_PERCENT=="undefined"||DEPOSIT_PERCENT==undefined)){
					DEPOSIT_MONEY=Math.round(accDiv(accMul(LEASE_TOPRIC,DEPOSIT_PERCENT),100));//用保证金比例算出保证金
					$("#DEPOSIT_MONEY").val(DEPOSIT_MONEY);

				}
				
			}
		}
		if(!(LEASE_TOPRIC>0)){
			DEPOSIT_PERCENT="";
		}
		$("#DEPOSIT_PERCENT").val(DEPOSIT_PERCENT);
		BZJBLHJ();//算出总保证金比例
	}	
	//尾款比例和尾款金额  正反推算
	var LAST_MONEY =$("input[name=LAST_MONEY]").val();//取尾款金额
	if(LAST_MONEY==null||LAST_MONEY==""||LAST_MONEY=="undefined"||LAST_MONEY==undefined){
		LAST_MONEY=0;
	}
	if($("input[name=LAST_MONEY]").length>0){
		var LAST_PERCENT=$("#LAST_PERCENT").val();
		if(LAST_PERCENT=="undefined"||LAST_PERCENT==undefined){
			LAST_PERCENT=0;
		}else if(LAST_PERCENT==null || LAST_PERCENT==""){
			LAST_PERCENT=accMul(accDiv(LAST_MONEY,LEASE_TOPRIC).toFixed(5),100.000);//用尾款金额算出尾款比例
		}else{
			if(the_class=="moneygs" && the_name=="尾款金额"){
				LAST_PERCENT=accMul(accDiv(LAST_MONEY,LEASE_TOPRIC).toFixed(5),100.000);//用尾款金额算出尾款比例
			}else if(the_class=="change" && the_name=="尾款比例"){
				LAST_MONEY=Math.round(accDiv(accMul(LEASE_TOPRIC,LAST_PERCENT),100));//用尾款比例算出尾款金额
				$("#LAST_MONEY").val(LAST_MONEY);
			}
		}
		if(!(LEASE_TOPRIC>0)){
			LAST_PERCENT="";
		}
		$("#LAST_PERCENT").val(LAST_PERCENT);
	}	
	
	if($("input[name=FINANCE_TOPRIC]").length>0)
	{
		//融资额
		var money=accSub(LEASE_TOPRIC,START_MONEY);
		FINANCE_TOPRIC=money;
		$("input[name=FINANCE_TOPRIC]").val(money);
	}
	var CALCULATE_MONEY=0;
	if(CALCULATE=='RZE'){
		//融资额
		CALCULATE_MONEY=FINANCE_TOPRIC;
	}else if(CALCULATE=='SBK'){
		//设备款
		CALCULATE_MONEY=$("#LEASE_TOPRIC").val();
	}else{
		$(obj).attr("CALCULATE","SBK");
		CALCULATE_MONEY=$("#LEASE_TOPRIC").val();
//		$.messager.alert("提示",$(obj).attr("SID")+"没有找到对应的计算基数");
//		return;
	}
	
	if($(obj).is("input"))
	{
		var baseValue=$(obj).val();
		//保险费计算
		var key_name_en=$(obj).attr("name");
		if(key_name_en=="INSURANCE_PERCENT"){
			var lease_term=$("input[name=lease_term]").val();//期数
			//var lease_period=$("input[name=lease_period]").val();
			var lease_period=$("#lease_period").val();
			if(lease_term==''||lease_term==undefined)
				lease_term=0;
			if(lease_period==''||lease_period==undefined)
				lease_period=0;
			var FinanceTerm  =accMul(lease_term,lease_period);//期限
			var INTN = accDiv(accMul(lease_term,lease_period),12);//期限除以12取整数
			var MODN = accMul(lease_term,lease_period)%12;//期限除以12取余数
			var InsuranceRatio =baseValue;//保险费率
			var InsuranceType=$("#INSURANCE").find("option:selected").text();//保险方式
			var EQ_COUNT=$("#EQ_COUNT").val();
			var temp_otherInsurance=$("#THREE_PARTY_INSURANCE").val();
			if(temp_otherInsurance==""||temp_otherInsurance==null||temp_otherInsurance=="undefined"||temp_otherInsurance==undefined)
				temp_otherInsurance=0;
			var OtherBail = accMul(temp_otherInsurance,EQ_COUNT);//三者险
			var HireScale = $("input[name=START_PERCENT]").val();//起租比例
			var MOD9 =  parseInt((FinanceTerm%12)/9);//Int((Val(FinanceTerm) Mod 12 )/9)//期限除于12取余再除以9
			var INTN9 =parseInt(FinanceTerm/9);//Int(Cstr(Val(FinanceTerm)/9))//期限除于9取整数
			var Insurance = 0;//定义常量为0
			if("租赁"!=InsuranceType)
			{
				Insurance = 0;
			}
			else
			{
				if( FinanceTerm>=12)
				{
					Insurance =Math.round((LEASE_TOPRIC*(1-Math.pow(0.8,INTN))/INTN  * 5 *InsuranceRatio * 0.01+ OtherBail)*( INTN+ MODN/10 - 0.05*( MODN-8)* MOD9),2);
				}
				else
				{
					Insurance = Math.round((LEASE_TOPRIC * InsuranceRatio * 0.01 +OtherBail)* (FinanceTerm/10-0.05*(FinanceTerm-8)* INTN9),2);
				}
			}
			$("[name='"+computeName+"']").val(Insurance);
			/********************保险结算结束****************************/
		}else{
//			$("[name='"+computeName+"']").val(accDiv(Math.round(accMul(CALCULATE_MONEY,baseValue)),100));
			//用比例算出金额
			//2016年4月8日 13:59:08 吴国伟修改 只有首期租金比例按照设备款计算的才会首次计算首期租金
			if(computeName=="START_MONEY"){
				if(CALCULATE=="SBK"){
					$("[name='"+computeName+"']").val(Math.round(accDiv(accMul(CALCULATE_MONEY,baseValue),100)));
				}
			}else{
				if(computeName=="DEPOSIT_MONEY" || computeName=="LAST_MONEY"){
					$("[name='"+computeName+"']").val(Math.round(accDiv(accMul(CALCULATE_MONEY,baseValue),100)));
				}else{
					$("[name='"+computeName+"']").val(accDiv(Math.round(accMul(CALCULATE_MONEY,baseValue)),100));
				}
			}
		}
	}
	else if($(obj).is("select")){
		var baseValue=$(obj).find("option:selected").val();
		//保险费计算
		var key_name_en=$(obj).attr("name");
		if(key_name_en=="INSURANCE_PERCENT"){
			var lease_term=$("input[name=lease_term]").val();//期数
			//var lease_period=$("input[name=lease_period]").val();
			var lease_period=$("#lease_period").val();
			if(lease_term==''||lease_term==undefined)
				lease_term=0;
			if(lease_period==''||lease_period==undefined)
				lease_period=0;
			var FinanceTerm  =accMul(lease_term,lease_period);//期限
			var INTN = accDiv(accMul(lease_term,lease_period),12);//期限除以12取整数
			var MODN = accMul(lease_term,lease_period)%12;//期限除以12取余数
			var InsuranceRatio =baseValue;//保险费率
//			var InsuranceRatio =accDiv(baseValue,100);//保险费率
			var InsuranceType=$.trim($("#INSURANCE").find("option:selected").text());//保险方式
			var EQ_COUNT=$("#EQ_COUNT").val();
			var temp_otherInsurance=$("#THREE_PARTY_INSURANCE").val();
			if(temp_otherInsurance==""||temp_otherInsurance==null||temp_otherInsurance=="undefined"||temp_otherInsurance==undefined)
				temp_otherInsurance=0;
			var OtherBail = accMul(temp_otherInsurance,EQ_COUNT);//三者险
			var HireScale = $("input[name=START_PERCENT]").val();//起租比例
			var MOD9 =  parseInt((FinanceTerm%12)/9);//Int((Val(FinanceTerm) Mod 12 )/9)//期限除于12取余再除以9
			var INTN9 =parseInt(FinanceTerm/9);//Int(Cstr(Val(FinanceTerm)/9))//期限除于9取整数
			var Insurance = 0;//定义常量为0
			if("租赁"!=InsuranceType)
			{
				Insurance = 0;
			}
			else
			{
				if( FinanceTerm>=12)
				{
					Insurance =Math.round((LEASE_TOPRIC*(1-Math.pow(0.8,INTN))/INTN  * 5 *InsuranceRatio * 0.01+ OtherBail)*( INTN+ MODN/10 - 0.05*( MODN-8)* MOD9),2);
				}
				else
				{
					Insurance = Math.round((LEASE_TOPRIC * InsuranceRatio * 0.01 +OtherBail)* (FinanceTerm/10-0.05*(FinanceTerm-8)* INTN9),2);
				}
			}
			$("[name='"+computeName+"']").val(Insurance);
			/********************保险结算结束****************************/
		}else{
			if(baseValue==''||baseValue==undefined)
			{
				$("[name='"+computeName+"']").val(0);
			}else{
				$("[name='"+computeName+"']").val(accDiv(Math.round(accMul(CALCULATE_MONEY,baseValue)),100));
			}
		}
	}
	
	//手续费
	if($("input[name=fees]").length>0)
	{
		try{
		var fees=$("#fees").val();
		var sbe=$("#FINANCE_TOPRIC").val();
		var money=Math.round(accDiv(accMul(sbe,fees),100),2);
		$("input[name=FEES_PRICE]").val(money);
		}catch(Exception){}
	}
	//首期付款合计及租金合计
	FIRSTPAYALL();

}

function publicMothod(){
	$("input").each(function(){
		$(this).attr({title:$.trim($(this).val())});
	});
	$("select").each(function(){
		$(this).attr({title:$.trim($(this).find("option:selected").text())});
	});
	$("input").blur(function(){
		$(this).attr({title:$.trim($(this).val())});
	});
	$("select").change(function(){
		$(this).attr({title:$.trim($(this).find("option:selected").text())});
	});
	
//	$("#UPDATE_DELIVER_DATE").datebox({onSelect: function (date) {
//		$("#DELIVER_DATE").val($("#UPDATE_DELIVER_DATE").datebox("getValue"));
//	}});
}
	
//计算融资差额
//add by lishuo 12.1.2015
function CountTotalMoney(scheme_code,FLAG){
	/*if(FLAG==2){//修改页面标志
		var AnotherMoney = $("#QITA_MONEY_MODIFY_VAL").val();//获取其他费用的值
		if(AnotherMoney == "0"){
			var Another_Money=parseInt(AnotherMoney);
		}
	}else{
		var AnotherMoney = $("#Dead_QITA_MONEY_VAL").val();//获取其他费用的值
		var Another_Money=parseInt(AnotherMoney);
	}*/
	var CarPrice =$("#LEASE_TOPRIC").val();//裸车价
	var LEASE_PERIOD=$("select[name='lease_term']").val();//期数
	var LEASE_TOPRIC = $("#LEASE_TOPRIC").val();//实际成交价
	var	START_MONEY	 = $("#START_MONEY").val();//首期租金
	var TotalMoney;
	var AnotherCost;
		var url=_basePath+"/customers/Customers!QueryTotalMoney.action";
		args={"CarPrice":CarPrice,"LEASE_PERIOD":LEASE_PERIOD,"SCHEME_CODE1":scheme_code};
		console.info(args);
		$.ajax({
			url : url,
			data:args,
			dataType:"json",
			success :function(json){	
				if(json.flag){
					var json = json.data;
					AnotherCost=parseInt(json[0].TOTALMONEY-(LEASE_TOPRIC-START_MONEY));
					//$("input[name='QITA_MONEY']").val(AnotherCost+Another_Money);
					$("input[name='QITA_MONEY']").val(AnotherCost);
					changeMoney(AnotherCost);
				}
			} 
		});
	return ;
}

function getYearRate(FLAG)
{
	changeEqumentMoney();
	var FINANCE_TOPRIC=$("#FINANCE_TOPRIC").val();//融资额
	var scheme_code =$("#scheme_code").attr("title");
	//仅供 月租产品用 12.17.2015 add by lishuo start
	var SCHEME_CODE1 = $("#SCHEME_CODE1").val();//产品名称
	if(SCHEME_CODE1 == undefined){//如果SCHEME_CODE1找不到，即进件页面
		SCHEME_CODE1 =$("#SCHEME_CODE").val();//产品名称
	}
	if(scheme_code){
		if(scheme_code.indexOf('月租')>0){
			CountTotalMoney(scheme_code);
		}
	}
	//仅供 月租产品用  12.17.2015 add by lishuo end
	
	//add by lishuo 03-38-2016 期数对应车船和保险年限联动 Strat
	var count =$("#lease_term").find("option:selected").text();
	if (count){
		$("#VEHICLE_TYPE").val(count/12);
		$("#INSURANCE_TYPE").val(count/12);
	}
	//add by lishuo 03-38-2016 期数对应车船和保险年限联动  End
	
	//add by xingsumin begin
	var SCHEME_NAME = $("#SCHEME_CODE1  option:selected").text();//产品名称
		if(SCHEME_NAME){
			if(SCHEME_NAME.indexOf('月租')>0){
				CountTotalMoney(SCHEME_NAME,FLAG);
			}else if($("input[name='QITA_MONEY']").val()!=0){//add by lishuo 03-28-2016 带出产品自配的其他费用的值
			}else{
				$("input[name='QITA_MONEY']").val(0);
			}
		}
		
	//add by xingsumin end
		
	var SCHEME_TYPE=$("#SCHEME_TYPE").val();
	if(SCHEME_TYPE=='standard'){
		var lease_term=$("#lease_term").val();
		var lease_period=$("#lease_period").find("option:selected").val();
		
		var monthAll=parseInt(accMul(lease_term,lease_period));
		var SCHEME_CODE=$("#SCHEME_CODE").val();
		
		var START_PERCENT=0;
		
		if($("#START_PERCENT").val()!='')
		{
			START_PERCENT=$("#START_PERCENT").val();
			
		}
		
		if($("#YEAR_INTEREST").length>0)
		{
			$.ajax({
				type:"post",
				url:_basePath+"/baseScheme/BaseScheme!queryYearRateBySchem.action",
				data:"SCHEME_CODE="+SCHEME_CODE+"&monthAll="+monthAll+"&START_PERCENT="+START_PERCENT,
				type:"post",
				dataType:"json",
				success:function(json){
					if(json.flag){
						$("#YEAR_INTEREST").val(json.data[0].YEAR_RATE);
						$("#GPS_MONEY").val(json.data[0].GPS_PRICE);
						$("#GPS_MONEY").attr("FYGS",json.data[0].CALCULATE);
						FIRSTPAYALL();
					}else{
						return;
					}
				}
			});
		}
		
		if($("#fees").length>0)
		{
			$("#fees").val("0");
			$("input[name=FEES_PRICE]").val(0);
			$.ajax({
				type:"post",
				url:_basePath+"/baseScheme/BaseScheme!queryFeeBySchem.action",
				data:"SCHEME_CODE="+SCHEME_CODE+"&monthAll="+monthAll,
				dataType:"json",
				success:function(json){
					$("#fees").val(json.data[0].FEE_RATE);
					var FINANCE_TOPRIC=0;
					if($("#FINANCE_TOPRIC").val().length>0)
					{
						FINANCE_TOPRIC=$("#FINANCE_TOPRIC").val();
					}
					try {
						//手续费
						var money=accDiv(accMul(FINANCE_TOPRIC,json.data[0].FEE_RATE),100);
						$("input[name=FEES_PRICE]").val(Math.round(money,2));
						$("#sxfsqfs").text(json.data[0].SQFS_NAME);
						$("#MANAGEMENT_FEETYPE").val(json.data[0].SQFS);
						$("#MANAGEMENT_FEEDSTYPE").val(json.data[0].SXFDSFS);
					} catch (e) {
					}
					FIRSTPAYALL();
				}
			});
		}
	}
	
}



function loadSupAccountInit(){
	var zhanghu=$(".zhanghu").val();
	if(zhanghu!=''&&zhanghu!=null){
		loadSupAccount($(".zhanghu"));
	}
}

function loadSupAccount(LOAN_ACCOUNT_NAME){
	var supAccount='';
	if($(LOAN_ACCOUNT_NAME).val()=='1'){
		//自有账户
		supAccount=$("input[name=supAccount]").val();
	}else if($(LOAN_ACCOUNT_NAME).val()=='2'){
		//共管账户
		supAccount=$("input[name=supAccount]").attr("sid");
	}else if($(LOAN_ACCOUNT_NAME).val()=='3'){
		//厂商账号
		supAccount=$("input[name=supAccount]").attr("companyid");
	}
	$("#LOAN_ACCOUNT").val(supAccount);
}




function onchanageFee()
{
	if($("#fees").length>0)
	{
		var FINANCE_TOPRIC=0;
		if($("#FINANCE_TOPRIC").val().length>0)
		{
			FINANCE_TOPRIC=$("#FINANCE_TOPRIC").val();
		}
		//手续费
		var fees=$("#fees").val();
		var money=Math.round(accDiv(accMul(FINANCE_TOPRIC,fees),100),2);
		$("input[name=FEES_PRICE]").val(money);
	}
}

function changefeelv(){
	
	if($("#FEES_PRICE").length>0)
	{
		var FINANCE_TOPRIC=100;
		if($("#FINANCE_TOPRIC").val().length>0)
		{
			FINANCE_TOPRIC=$("#FINANCE_TOPRIC").val();
		}
		alert("LEASE_TOPRIC:"+FINANCE_TOPRIC);
		//手续费
		var money=$("#FEES_PRICE").val();
//		var fees=Math.round(accMul(accDiv(money,FINANCE_TOPRIC),100));
		var fees=accMul(accDiv(money,FINANCE_TOPRIC).toFixed(5),100.000);
		$("#fees").val(fees);
		//首期付款合计及租金合计
		FIRSTPAYALL();
	}
}
//function changefeelv(){
//	if($("#FEES_PRICE").length>0)
//	{
//		var LEASE_TOPRIC=0;
//		if($("#LEASE_TOPRIC").val().length>0)
//		{
//			LEASE_TOPRIC=$("#LEASE_TOPRIC").val();
//		}
//		//手续费
//		var money=$("#FEES_PRICE").val();
//		var fees=accMul(accDiv(money,LEASE_TOPRIC),100);
//		$("#fees").val(fees);
//	}
//}


/**
 * 乘法
 * @param arg1
 * @param arg2
 * @return
 */
function accMul(arg1, arg2) {
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try { m += s1.split(".")[1].length; } catch (e) { }
    try { m += s2.split(".")[1].length; } catch (e) { }
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}

/**
 * 加法
 * @param arg1
 * @param arg2
 * @return
 */
function accAdd(arg1, arg2) {
    var r1, r2, m;
    try { r1 = arg1.toString().split(".")[1].length; } catch (e) { r1 = 0; }
    try { r2 = arg2.toString().split(".")[1].length; } catch (e) { r2 = 0; }
    m = Math.pow(10, Math.max(r1, r2));
    return (arg1 * m + arg2 * m) / m;
}

/**
 * 除法
 * @param arg1
 * @param arg2
 * @return
 */
function accDiv(arg1, arg2) {
    var t1 = 0, t2 = 0, r1, r2;
    try { t1 = arg1.toString().split(".")[1].length ;} catch (e) { }
    try { t2 = arg2.toString().split(".")[1].length ;} catch (e) { }
    with (Math) {
        r1 = Number(arg1.toString().replace(".", ""));
        r2 = Number(arg2.toString().replace(".", ""));
        return (r1 / r2) * pow(10, t2 - t1);
    }
}

/**
 * 减法
 * @param arg1
 * @param arg2
 * @return
 */
function accSub(arg1, arg2) {
    var r1, r2, m, n;
    try { r1 = arg1.toString().split(".")[1].length; } catch (e) { r1 = 0; }
    try { r2 = arg2.toString().split(".")[1].length; } catch (e) { r2 = 0; }
    m = Math.pow(10, Math.max(r1, r2));
    n = (r1 >= r2) ? r1 : r2;
    return ((arg1 * m - arg2 * m) / m).toFixed(n);
}

function GUARANTEE_MODEL(){
	var B_MODEL_SWITCH=$("#B_MODEL_SWITCH").val();
	var GUARANTEE_MODEL=$("#GUARANTEE_MODEL").val();
	if(B_MODEL_SWITCH=='0'&&GUARANTEE_MODEL=='B_MODE'){
		//供应商B模式开启
		$("#BAPROJECTCODEDIV").dialog('open');
	}
}
function cleanBaProjCode(){
	$("#BAPROJECTCODEDIV").dialog('close');
}

function BAPROJECTCODEBTN(){
	$("#B_A_PROJECT_CODE").val($("#BAPROJECTCODE").val());
	cleanBaProjCode();
}

/**
 * 计算放款金额
 * @return
 *
 * @author King 2013-11-21
 */
//function LOAN_MONEY_JS(){
//	//放款政策
//	var PAYMENT_STATUS=$("#PAYMENT_STATUS").val();
//	//资料后补
//	var DATE_COMPLEMENT=$("#DATE_COMPLEMENT").val();
//	//设备额
//	var LEASE_TOPRIC=$("#LEASE_TOPRIC").val();
//	//首期款合计
//	var FIRSTPAYALL=$("#FIRSTPAYALL").val();
//	//DB保证金
//	var DB_BAIL__MONEY=$("#DB_BAIL__MONEY").val();
//	var COMPANY_BAIL_MONEY=$("#COMPANY_BAIL_MONEY").val();
//	if(COMPANY_BAIL_MONEY==null || COMPANY_BAIL_MONEY=='' || COMPANY_BAIL_MONEY=='undefined' || COMPANY_BAIL_MONEY==undefined){
//		COMPANY_BAIL_MONEY=0;
//	}
//	//保证金
//	var DEPOSIT_MONEY=$("#DEPOSIT_MONEY").val();
//	var FEES_PRICE=$("#FEES_PRICE").val();
//	var LOAN_MONEY;
//	//差额
//	if(PAYMENT_STATUS=='2'){
//		//资料候补 是，70%
//		if(DATE_COMPLEMENT=='2'){
//			//(设备款-首期付款合计-DB保证金-厂商保证金)*0.7
//			//(设备款*0.7-首期付款合计-DB保证金-厂商保证金)
//			LOAN_MONEY=accSub(accSub(accSub(accMul(LEASE_TOPRIC,0.7),FIRSTPAYALL),DB_BAIL__MONEY),COMPANY_BAIL_MONEY);
////			LOAN_MONEY=accMul(accSub(accSub(accSub(LEASE_TOPRIC,FIRSTPAYALL),DB_BAIL__MONEY),COMPANY_BAIL_MONEY),0.7);
//		}else{
//			LOAN_MONEY=accSub(accSub(accSub(LEASE_TOPRIC,FIRSTPAYALL),DB_BAIL__MONEY),COMPANY_BAIL_MONEY);
//		}
//	}else if(PAYMENT_STATUS=='3'){
//		//部分差额放款
//		var START_MONEY=$("#START_MONEY").val();
//		var PURCHASE_PRICE=$("#PURCHASE_PRICE").val();
//		LOAN_MONEY=accSub(accSub(LEASE_TOPRIC,START_MONEY),PURCHASE_PRICE);
//	}else if(PAYMENT_STATUS=='4'){
//		//DB差额放款
//		LOAN_MONEY=accSub(LEASE_TOPRIC,DB_BAIL__MONEY);
//	}else{
//		LOAN_MONEY=LEASE_TOPRIC;
//	}
//	if(isNaN(LOAN_MONEY)){
//		LOAN_MONEY=0;
//	}
//	$("#LOAN_JINE").val(LOAN_MONEY);
//}

function showHelp(){
	$("#FIRSTMONEYHELP").dialog('open');
}

/**
 * 校验指标公司是否有可用的指标
 * @param param
 * @author King 2015年3月6日
 */
function checkQUOTA(param){
	var amount=0;
	try{
		$(".eq_body_tr").each(function(){
			amount=accAdd(amount,$(this).find("[name='AMOUNT']").val());
		});
	}catch(Exception){amount=1;}
	var SUPPLIERS_ID=$("#QUOTA_ID").val();
	if(SUPPLIERS_ID!=null&&SUPPLIERS_ID!=''&&SUPPLIERS_ID!=undefined&&SUPPLIERS_ID!='undefined'){
		$.ajax({
			url:_basePath+"/project/WebProject!checkQUOTA.action",
			data:"SUPPLIERS_ID="+SUPPLIERS_ID,
			dataType:'json',
			success:function(json){
				if(json.data.length>=amount){
				}else{
					alert("该指标公司没有可用的指标");
					$("#QUOTA_ID").val('');
				}
			}
		});
	}
}

/**
 * 校验是否处于区间内
 * @param param
 * @author King 2015年3月2日
 */
function checkQJ(param){
	var value=$(param).val();
	var down=$(param).attr("VALUE_DOWN");
	var up=$(param).attr("VALUE_UP");
	if(value!=''&& down!='' && up!=''){
		if(parseInt(value)-parseInt(down)<0||parseInt(value)-parseInt(up)>0){
			$.messager.alert("提示",$(param).attr("SID")+"超出取值区间【"+down+"--"+up+"】");
			$(param).val('');
			return;
		}
	}
}

/**
 * GPS安装及费用校验
 * @param param
 * @author King 2015年3月2日
 */
function checkgps(param){
	var val=$(param).val();
	if(val=="no"){
		$("#GZS_PRICE").val('0');
	}
}

/**
 * 是否需要共同承租人
 * 
 * @author King 2015年3月5日
 */
function JOINT_TENANT(){
	var JOINT_TENANT=$("#JOINT_TENANT").val();
	if(JOINT_TENANT==2||JOINT_TENANT==3){
		$("#GTCZRDIV").dialog('open');
		$("#showGtzcr").empty();
		if(JOINT_TENANT==2){
			$("#GT_CLIENT_TYPE").val("NP");
		}else{
			$("#GT_CLIENT_TYPE").val("LP");
		}
	}else{
		$("#JOINT_TENANT_ID").val('');
		$("#FINAL_CUST_NAME").val('');
	}
}

function showGTCZRD(){
	//需要共同承租人
	$.ajax({
		url:_basePath+"/project/project!queryJOINT_TENANT.action",
		data:"CLIENT_NAME="+$("#GT_CLIENT_NAME").val()+"&CLIENT_TYPE="+$("#GT_CLIENT_TYPE").val(),
		dataType:'json',
		success:function(json){
			$("#showGtzcr").empty();
			var show=$("#showGtzcr");
			for(var i=0;i<json.data.length;i++){
				show.append("<tr><td>"+json.data[i].CLIENT_TYPE+"</td><td>"+json.data[i].NAME+"</td><td><a onclick=chooseClient('"+json.data[i].FINAL_CUST_ID+"','"+json.data[i].NAME+"')>选择</a>");
			}
		}
	});
}

function chooseClient(JOINT_TENANT_ID,FINAL_CUST_NAME){
	$("#JOINT_TENANT_ID").val(JOINT_TENANT_ID);
	$("#FINAL_CUST_NAME").val(FINAL_CUST_NAME);
	$("#GTCZRDIV").dialog('close');
}

/**
 * 各保证金比例合计
 * @param param
 * @author King 2015年3月7日
 */
function BZJBLHJ(){
	try{
	var DB_BAIL_PERCENT=$("#DB_BAIL_PERCENT").val();
	var DEPOSIT_PERCENT=$("#DEPOSIT_PERCENT").val();
	if(DEPOSIT_PERCENT==''||DEPOSIT_PERCENT==null||DEPOSIT_PERCENT=='undefined'||DEPOSIT_PERCENT==undefined){
		DEPOSIT_PERCENT=0;
	}
	if(DB_BAIL_PERCENT==''||DB_BAIL_PERCENT==null||DB_BAIL_PERCENT=='undefined'||DB_BAIL_PERCENT==undefined){
		DB_BAIL_PERCENT=0;
	}
//	$("#ZBZJBL_PERCENT").val(parseInt(DB_BAIL_PERCENT)+parseInt(DEPOSIT_PERCENT));
	$("#ZBZJBL_PERCENT").val(accAdd(parseInt(DB_BAIL_PERCENT).toFixed(3),DEPOSIT_PERCENT));
	}catch(Exception){}
}

function bx_cc(){
	try{
	var bx=0,ccs=0,jqx=0;
	var amount=1;
	$(".BX").each(function(){
		var bxf=$(this).val();
		amount=$(this).parent("td").parent("tr").find("input[name='AMOUNT']").val();
		if(amount==undefined||amount=='undefined'||amount==''||amount==null)
			amount=1;
		bxf=accMul(amount,bxf);
		bx=accAdd(bx,bxf);
	});
	var INSURANCE_TYPE=$("#INSURANCE_TYPE").val();
	if(INSURANCE_TYPE==null||INSURANCE_TYPE==''||INSURANCE_TYPE=='undefined'||INSURANCE_TYPE==undefined){
		var lease_term=$("#lease_term").val();
		var LEASE_PERIOD=$("#lease_period").val();
		INSURANCE_TYPE=accDiv(accMul(lease_term,LEASE_PERIOD),12);
		//   进件保险期限自动扣减  不能超过 两年 add by xingsumin 
		/*if(INSURANCE_TYPE>2){
			INSURANCE_TYPE=INSURANCE_TYPE-1;
		}*/
		if($("#INSURANCE_TYPE") && $("#INSURANCE_TYPE").val() == ''){
			$("#INSURANCE_TYPE").val(INSURANCE_TYPE);
		}
	}
	bx=accMul(bx,INSURANCE_TYPE);
	
	$(".CCS").each(function(){
		var ccsf=$(this).val();
		amount=$(this).parent("td").parent("tr").find("input[name='AMOUNT']").val();
		if(amount==undefined||amount=='undefined'||amount==''||amount==null)
			amount=1;
		ccsf=accMul(amount,ccsf);
		ccs=accAdd(ccs,ccsf);
	});
	var VEHICLE_TYPE=$("#VEHICLE_TYPE").val();
	if(VEHICLE_TYPE==null||VEHICLE_TYPE==''||VEHICLE_TYPE=='undefined'||VEHICLE_TYPE==undefined){
		var lease_term=$("#lease_term").val();
		var LEASE_PERIOD=$("#lease_period").val();
		VEHICLE_TYPE=accDiv(accMul(lease_term,LEASE_PERIOD),12);
		//   进件保险期限自动扣减  不能超过 两年 add by xingsumin 
		/*if(VEHICLE_TYPE>2){
			VEHICLE_TYPE=VEHICLE_TYPE-1;
		}*/
		if($("#VEHICLE_TYPE") && $("#VEHICLE_TYPE").val() == ''){
			$("#VEHICLE_TYPE").val(VEHICLE_TYPE);
		}
	}
	ccs=accMul(ccs,VEHICLE_TYPE);
	
	$(".JQX").each(function(){
		var jqxf=$(this).val();
		amount=$(this).parent("td").parent("tr").find("input[name='AMOUNT']").val();
		if(amount==undefined||amount=='undefined'||amount==''||amount==null)
			amount=1;
		jqxf=accMul(amount,jqxf);
		jqx=accAdd(jqx,jqxf);
	});
	
	var VEHICLE_TYPE=$("#VEHICLE_TYPE").val();
	if(VEHICLE_TYPE==null||VEHICLE_TYPE==''||VEHICLE_TYPE=='undefined'||VEHICLE_TYPE==undefined){
		var lease_term=$("#lease_term").val();
		var LEASE_PERIOD=$("#lease_period").val();
		VEHICLE_TYPE=accDiv(accMul(lease_term,LEASE_PERIOD),12);
		//   进件保险期限自动扣减  不能超过 两年 add by xingsumin 
		/*if(VEHICLE_TYPE>2){
			VEHICLE_TYPE=VEHICLE_TYPE-1;
		}*/
	}
	jqx=accMul(jqx,VEHICLE_TYPE);
	
	$("#CCS_MONEY").val(ccs+jqx);
	$("#INSURANCE_MONEY").val(bx);
	var FINANCE_TOPRIC=$("#FINANCE_TOPRIC").val();
	var FIRSTPAYALL=$("#FIRSTPAYALL").val();
	if($("#lease_term").val()!='1'){
		if($("#INSURANCE_MONEY").next().val()=='JRRZE'){
			$("#FINANCE_TOPRIC").val(accAdd(accSub(FINANCE_TOPRIC,$("#INSURANCE_MONEY1").val()),bx));
		}else{
			$("#FIRSTPAYALL").val(accAdd(accSub(FIRSTPAYALL,$("#INSURANCE_MONEY1").val()),bx));
		}
		$("#INSURANCE_MONEY1").val(bx);
	}
	}catch(Exception){}
}

/**
 * 计算购置税
 * 
 * @author King 2015年8月21日
 */
function jsGZS_MONEY(){
//	计算购置税
//	if(车价<指导价*93%) 购置税=指导价*93%/11.7
//	if(车价>=指导价*93%) 购置税=车价/11.7
	var LEASE_TOPRIC=$("#LEASE_TOPRIC").val();
	var LEASE_TOPRIC_ZD=$("#LEASE_TOPRIC_ZD").val();
//	alert(LEASE_TOPRIC +" | " +LEASE_TOPRIC_ZD);
	var GZS_MONEY=0;
	if(accSub(accMul(LEASE_TOPRIC_ZD,0.93),LEASE_TOPRIC)>0){
		GZS_MONEY=accDiv(accMul(LEASE_TOPRIC_ZD,0.93),11.7);
	}else{
		GZS_MONEY=accDiv(LEASE_TOPRIC,11.7);
	}
	GZS_MONEY=accDiv(Math.round(accMul(GZS_MONEY,100)),100);
	try{
		$("#GZS_MONEY").val(GZS_MONEY);
	}catch(Exception){}
}
/**
 * 新增计算首期租金 吴国伟 2016年3月11日 17:25:02
 */
//标记是不是第一次操作
var Type = 1; 
function changeStartMoney(){
	//融资额
	var FINANCE_TOPRIC = $("#FINANCE_TOPRIC").val();
	//计算之后的融资额
	var FINANCE_TOPRIC_OLD = $("#FINANCE_TOPRIC_OLD").val();
	//首期租金
	var START_MONEY = $("#START_MONEY").val();
	//首期租金比例
	var START_PERCENT = $("#START_PERCENT").val();
	//首期租金计算方式
	var calculate = $("#START_PERCENT").attr("CALCULATE");
	if(calculate=='RZE'){
		  
		  //if(accSub(FINANCE_TOPRIC,FINANCE_TOPRIC_OLD)!=0 || Type == 1){
			
			$("#FINANCE_TOPRIC_OLD").val($("#FINANCE_TOPRIC").val());
			//2016年4月8日 11:26:24  修改保留2位小数
			//$("#START_MONEY").val(Math.round(accDiv(accMul(accAdd(FINANCE_TOPRIC,START_MONEY),START_PERCENT),100),2));
			
			//changeMoney($("#START_MONEY"));
			if(START_MONEY==""||START_MONEY==null||START_MONEY=="undefined"||START_MONEY==undefined){
				$("#START_MONEY").val(accDiv(Math.round(accMul(accAdd(FINANCE_TOPRIC,START_MONEY),START_PERCENT)),100));
			}
			

			if(START_PERCENT==""||START_PERCENT==null||START_PERCENT=="undefined"||START_PERCENT==undefined){
				START_PERCENT=accMul(accDiv(START_MONEY,accAdd(FINANCE_TOPRIC,START_MONEY)).toFixed(6),100.000);
				$("#START_PERCENT").val(START_PERCENT);
			}
				
			
			//++Type;
		//}
	}
}