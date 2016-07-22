function toCommit(FUND_ID_APP){	
//	if($("#FI_TO_THE_PAYEE"+FUND_ID_APP).val()==null || $("#FI_TO_THE_PAYEE"+FUND_ID_APP).val()==undefined || $("#FI_TO_THE_PAYEE"+FUND_ID_APP).val().length<=0){
//		return $.messager.alert('警告','请填写来款人');
//	}
//	var FI_REALITY_BANK = $("select[name='FI_REALITY_BANK']").find("option:selected").val();
//	if(FI_REALITY_BANK.length<=0){
//		return $.messager.alert('警告','请填写核销银行');
//	}
	if($("#FI_ACCOUNT_DATE"+FUND_ID_APP).datebox('getValue')==null || $("#FI_ACCOUNT_DATE"+FUND_ID_APP).datebox('getValue')==""  || $("#FI_ACCOUNT_DATE"+FUND_ID_APP).datebox('getValue').length<=0){
		return $.messager.alert('警告','请填写核销时间');
	}
	var data = {};
	data["getBaseData"] = getBaseData(FUND_ID_APP);	
	if($("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val()==null || $("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val()==undefined){
		return $.messager.alert('警告','请填写本次实付！');
	}else{
		if(parseFloat($("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val())<parseFloat($("#fi_pay_money_"+FUND_ID_APP).val())){
			var totalMoney = parseFloat($("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val())+parseFloat($("#realityMoneyTotal"+FUND_ID_APP).val());
			if(parseFloat(totalMoney)>parseFloat($("#fi_pay_money_"+FUND_ID_APP).val())){
				return $.messager.alert('警告','选择金额大于本次应收核销金额,请重新选择金额');
			}else if(parseFloat(totalMoney)<parseFloat($("#fi_pay_money_"+FUND_ID_APP).val())){
				return $.messager.alert('警告','选择金额应等于本次应收核销金额,请重新选择金额');
			}else{
				data["getPoolData"] = getPoolData();	
			}
		}
	}	
	
	$.ajax({		
		url:_basePath+"/renterpool/CashDeposit!doCommitHXSheet.action",
	    type:'post',
	    data:'data='+JSON.stringify(data),
	    dataType:'json',
	    success:function(json){
		 if(json.flag == true){
			 $.messager.alert("提示","保存成功！！");	
			//页面刷新
			 window.location.href = _basePath+"/renterpool/CashDeposit!toMgJQDKData.action";
		 }else{
			 $.messager.alert("提示","保存失败！！");			 
		 }
		
		}
	});
}

//核销
function toCommitHexiao(FUND_ID_APP){
//	if($("#FI_TO_THE_PAYEE").val()==null || $("#FI_TO_THE_PAYEE").val()==undefined){
//		return $.messager.alert('警告','请填写来款人');
//	}//$('#cc').combobox('setValue', '001')
//	if($("#FI_REALITY_BANK").attr('selected',true)==null || $("#FI_REALITY_BANK").attr('selected',true)==undefined){
//		return $.messager.alert('警告','请填写核销银行');
//	}
	if($("#FI_ACCOUNT_DATE"+FUND_ID_APP).datebox('getValue')==null || $("#FI_ACCOUNT_DATE"+FUND_ID_APP).datebox('getValue')==""){
		return $.messager.alert('警告','请填写核销时间');
	}
	
	
	var data = {};
	data["getBaseData"] = getBaseData(FUND_ID_APP);
	if($("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val()==null || $("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val()==""){
		return $.messager.alert('警告','请填写本次实付');
	}else{
		if(parseFloat($("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val())<parseFloat($("#fi_pay_money_"+FUND_ID_APP).val())){
			var totalMoney = parseFloat($("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val())+parseFloat($("#realityMoneyTotal"+FUND_ID_APP).val());
			if(parseFloat(totalMoney)>parseFloat($("#fi_pay_money_"+FUND_ID_APP).val())){
				return $.messager.alert('警告','选择金额大于本次应收核销金额请重新选择金额');
			}else if(parseFloat(totalMoney)<parseFloat($("#fi_pay_money_"+FUND_ID_APP).val())){
				return $.messager.alert('警告','选择金额应等于本次应收核销金额,请重新选择金额');
			}else{
				data["getPoolData"] = getPoolData();	
			}
		}
	}	
	
	$.ajax({		
		url:_basePath+"/renterpool/CashDeposit!doCheckedBZJ.action",
	    type:'post',
	    data:'data='+JSON.stringify(data),
	    dataType:'json',
	    success:function(json){
		 if(json.flag == true){
			 $.messager.alert("提示","核销成功！！");
			//页面刷新
			 window.location.href = _basePath+"/renterpool/CashDeposit!toMgJQDKData.action";
		 }else{
			 $.messager.alert("提示","核销失败！！");			 
		 }
		
		}
	});
}

//获取基础数据
function getBaseData(FUND_ID_APP){
	var getBaseData = {};
	var FI_ACCOUNT_DATE = $("#FI_ACCOUNT_DATE"+FUND_ID_APP).datebox('getValue');
	var FI_REALITY_BANK = $("#FI_REALITY_BANK"+FUND_ID_APP).find("option:selected").val();
	var FI_TAGE = $("#FI_TAGE"+FUND_ID_APP).find("option:selected").val();
	var FI_TAGE_ID= $("#FI_TAGE_ID"+FUND_ID_APP).find("option:selected").val();
	var FI_TAGE_NAME= $("#FI_TAGE_ID"+FUND_ID_APP).find("option:selected").attr("FI_TAGE_NAME");
	getBaseData["FI_ACCOUNT_DATE"] = FI_ACCOUNT_DATE;
	getBaseData["FI_REALITY_BANK"] = FI_REALITY_BANK;
	getBaseData["FI_TAGE"] = FI_TAGE;
	getBaseData["FUND_ID"] = $("#FUND_ID"+FUND_ID_APP).val();
	getBaseData["FI_TO_THE_PAYEE"] = $("#FI_TO_THE_PAYEE"+FUND_ID_APP).val();
	getBaseData["FI_REALITY_MONEY"] = $("#FI_REALITY_MONEY1"+FUND_ID_APP).val();
	getBaseData["FI_REALITY_ACCOUNT"] = $("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val();
	getBaseData["FI_TAGE_ID"] = FI_TAGE_ID;
	getBaseData["FI_TAGE_NAME"] = FI_TAGE_NAME;
	getBaseData["FI_PAY_BANK"] = $("#FI_PAY_BANK"+FUND_ID_APP).val();
	getBaseData["FI_PAY_YUE"] = $("#FI_PAY_YUE"+FUND_ID_APP).val();
	var FUND_ID=$("#FUND_ID"+FUND_ID_APP).val();
	getBaseData["MONEYORDERSAVE"] = $("#moneyOrderSave"+FUND_ID).val();

	return getBaseData;
}

/**
 * 获取列表数据
 * @return
 */
function getDetailData1(){
	var getDetailData = [];
	var detailData = $("#pageTable_detail").datagrid('getRows');
	alert(detailData);
	for(var i = 0; i<detailData.length; i++) {
		var temp = {};
		temp.PRO_ID = detailData[i].PRO_ID;
		temp.PRO_CODE = detailData[i].PRO_CODE;
		temp.PAYLIST_CODE = detailData[i].PAYLIST_CODE;
		temp.CLIENT_ID = detailData[i].CLIENT_ID;
		temp.CUST_NAME = detailData[i].CUST_NAME;
		temp.WS_NUM = detailData[i].WS_NUM;
		
		temp.BJ_MONEY = detailData[i].BJ_MONEY;
		temp.LX_MONEY = detailData[i].LX_MONEY;
		temp.ZJ_MONEY = detailData[i].ZJ_MONEY;
		temp.WS_YQ = detailData[i].WS_YQ;
		temp.VALUE_STR = detailData[i].VALUE_STR;
		temp.DEDUCTION_YUE = detailData[i].DEDUCTION_YUE;
		temp.SY_BZJ = detailData[i].SY_BZJ;
		getDetailData.push(temp);
	}
	return getDetailData;//JSON.stringify(getDetailData);//encodeURI(JSON.stringify(getDetailData));
}

//获取资金池中数据
function getPoolData(){
	var getPoolData = [];
	$("#pool_tab tr").each(function(){
		var temp = {};
		temp.POOL_TYPE= $(this).find("input[name=POOLID]").val();//租金池类别
		temp.POOLUSERMONEY = $(this).find("input[name=POOLUSERMONEY]").val();//可用余额
		temp.dichong_money = $(this).find("input[name=dichong_money]").val();//所选冲抵款合计
		getPoolData.push(temp);
	});
	return getPoolData;
}

/**
 * 本次实付在修改时， 本次实付金额要与本次应收核销进行对比。
 * 
 * 当本次实付金额大于本次应收金额时： 计算本次来款余额。 即为：本次实付金额-本次应收金额
 * 
 * 当本次实付金额小于本次应收金额时： 本次实付金额+余款池的总金额 = 本次应收金额
 * 
 * @author yx
 * @date 2013-10-10
 * @return
 */
function toChange(UND_ID_APP){
	var FI_REALITY_ACCOUNT = parseFloat($("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val());//本次实付
	var fi_pay_money = parseFloat($("#fi_pay_money_"+FUND_ID_APP).val());//本次应收核销
	var unusedAmount = parseFloat(0);
	//当本次实付>本次应收金额时， 计算本次来款余额
	if(FI_REALITY_ACCOUNT>=fi_pay_money){
		unusedAmount = FI_REALITY_ACCOUNT - fi_pay_money;//本次来款余额
		$("#poolTab").hide();
		$("a[name='saveHref']").show();
		$("a[name='HeXaioFirst']").hide();
	}else if(FI_REALITY_ACCOUNT<fi_pay_money){
		//本次来款余额
		unusedAmount=0;		
		$("#poolTab").show();
		$("a[name='saveHref']").hide();
		$("a[name='HeXaioFirst']").show();
	}
	
	$("#FI_PAY_YUE"+FUND_ID_APP).val("");
	$("#FI_PAY_YUE"+FUND_ID_APP).val(unusedAmount);
}

/**
 * 计算应/实收金额总数
 * @author yx
 * @date 2013-10-10
 * @return
 */
function toChangePool(obj){
	
	//1.判断输入金额要小于等于可使用金额
	var tr=$(obj).parent().parent();
	var money1=$(tr).find("input[name='POOLUSERMONEY']").val();//可使用金额
	var money2=$(tr).find("input[name='dichong_money']").val();//想抵充金额
	if(parseFloat(money2)>money1)
	{
		$(tr).find("input[name='dichong_money']").val(money1);
	}
	
	//2.总金额累加
	var realityMoneyTotal = parseFloat(0);
	$("#pool_tab input[name=dichong_money]").each(function(){
		if(isNaN($(this).val())){
			alert("请填写正确数字！！");
			$(this).val("0.00");
		}else{
			realityMoneyTotal += parseFloat($(this).val());
		}
	});
	$("#realityMoneyTotal").val(realityMoneyTotal);
}

function updateMoneyOrder(obj){
	 $("#dlgUp"+obj).dialog("open");
}