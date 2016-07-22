$(document).ready(function(){
	 $('#uploadDialog').dialog({closed : true});
});


//非网银-创建结算单
function creatNotBankFund()
{
	var datagridList=$('#bank_C_PageTable').datagrid('getChecked');
	if(datagridList.length<=0)
	{
		alert("请先选择要数据在继续申请操作！");
		return false;
	}
	var IDS="";
	var IDS1="";
	var IDS2="";
	var IDS3="";
	var IDS4="";
	var IDS5="";
	var IDS6="";
	var hh=0;
	var aa=0;
	for(var i = 0; i < datagridList.length; i++){
		hh++;
		if(hh==1){
			if(aa==0){
				IDS=datagridList[i].ID;
			}
			else if(aa==1){
				IDS1=datagridList[i].ID;
			}
			else if(aa==2){
				IDS2=datagridList[i].ID;
			}
			else if(aa==3){
				IDS3=datagridList[i].ID;
			}
			else if(aa==4){
				IDS4=datagridList[i].ID;
			}
			else if(aa==5){
				IDS5=datagridList[i].ID;
			}
			else if(aa==6){
				IDS6=datagridList[i].ID;
			}
		}
		else{
			if(aa==0){
				IDS=IDS+","+datagridList[i].ID;
			}
			else if(aa==1){
				IDS1=IDS1+","+datagridList[i].ID;
			}
			else if(aa==2){
				IDS2=IDS2+","+datagridList[i].ID;
			}
			else if(aa==3){
				IDS3=IDS3+","+datagridList[i].ID;
			}
			else if(aa==4){
				IDS4=IDS4+","+datagridList[i].ID;
			}
			else if(aa==5){
				IDS5=IDS5+","+datagridList[i].ID;
			}
			else if(aa==6){
				IDS6=IDS6+","+datagridList[i].ID;
			}
		}
		if(hh==300){
			aa++;
			hh=0;
		}
	}
	
	$.ajax({
		url:_basePath+"/rentWrite/rentWrite!LockTypeIsF.action?IDS="+IDS,
	    type:'post',
	    dataType:'json',
	    success:function(json){
		    if(json.data == '2'){
		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
		    }
		    else if(json.data == '3'){
		    	return $.messager.alert("锁定提示","您所选择的数据已经被操作过,请刷新页面在操作！");
		    }
		    else{
		    	if(IDS1.length>0){
		    		$.ajax({
			    		url:_basePath+"/rentWrite/rentWrite!LockTypeIsF.action?IDS="+IDS1,
			    	    type:'post',
			    	    dataType:'json',
			    	    success:function(json){
			    		    if(json.data == '2'){
			    		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
			    		    }
			    		    else if(json.data == '3'){
			    		    	return $.messager.alert("锁定提示","您所选择的数据已经被操作过,请刷新页面在操作！");
			    		    }
			    		    else{
			    		    	if(IDS2.length>0){
			    		    		$.ajax({
			    			    		url:_basePath+"/rentWrite/rentWrite!LockTypeIsF.action?IDS="+IDS2,
			    			    	    type:'post',
			    			    	    dataType:'json',
			    			    	    success:function(json){
			    			    		    if(json.data == '2'){
			    			    		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
			    			    		    }
			    			    		    else if(json.data == '3'){
			    			    		    	return $.messager.alert("锁定提示","您所选择的数据已经被操作过,请刷新页面在操作！");
			    			    		    }
			    			    		    else{
			    			    		    	if(IDS3.length>0){
			    			    		    		$.ajax({
			    			    			    		url:_basePath+"/rentWrite/rentWrite!LockTypeIsF.action?IDS="+IDS3,
			    			    			    	    type:'post',
			    			    			    	    dataType:'json',
			    			    			    	    success:function(json){
			    			    			    		    if(json.data == '2'){
			    			    			    		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
			    			    			    		    }
			    			    			    		    else if(json.data == '3'){
			    			    			    		    	return $.messager.alert("锁定提示","您所选择的数据已经被操作过,请刷新页面在操作！");
			    			    			    		    }
			    			    			    		    else{
			    			    			    		    	if(IDS4.length>0){
			    			    			    		    		$.ajax({
			    			    			    			    		url:_basePath+"/rentWrite/rentWrite!LockTypeIsF.action?IDS="+IDS4,
			    			    			    			    	    type:'post',
			    			    			    			    	    dataType:'json',
			    			    			    			    	    success:function(json){
			    			    			    			    		    if(json.data == '2'){
			    			    			    			    		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
			    			    			    			    		    }
			    			    			    			    		    else if(json.data == '3'){
			    			    			    			    		    	return $.messager.alert("锁定提示","您所选择的数据已经被操作过,请刷新页面在操作！");
			    			    			    			    		    }
			    			    			    			    		    else{
			    			    			    			    		    	if(IDS5.length>0){
			    			    			    			    		    		$.ajax({
			    			    			    			    			    		url:_basePath+"/rentWrite/rentWrite!LockTypeIsF.action?IDS="+IDS5,
			    			    			    			    			    	    type:'post',
			    			    			    			    			    	    dataType:'json',
			    			    			    			    			    	    success:function(json){
			    			    			    			    			    		    if(json.data == '2'){
			    			    			    			    			    		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
			    			    			    			    			    		    }
			    			    			    			    			    		    else if(json.data == '3'){
			    			    			    			    			    		    	return $.messager.alert("锁定提示","您所选择的数据已经被操作过,请刷新页面在操作！");
			    			    			    			    			    		    }
			    			    			    			    			    		    else{
			    			    			    			    			    		    	if(IDS6.length>0){
			    			    			    			    			    		    		$.ajax({
			    			    			    			    			    			    		url:_basePath+"/rentWrite/rentWrite!LockTypeIsF.action?IDS="+IDS6,
			    			    			    			    			    			    	    type:'post',
			    			    			    			    			    			    	    dataType:'json',
			    			    			    			    			    			    	    success:function(json){
			    			    			    			    			    			    		    if(json.data == '2'){
			    			    			    			    			    			    		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
			    			    			    			    			    			    		    }
			    			    			    			    			    			    		    else if(json.data == '3'){
			    			    			    			    			    			    		    	return $.messager.alert("锁定提示","您所选择的数据已经被操作过,请刷新页面在操作！");
			    			    			    			    			    			    		    }
			    			    			    			    			    			    		    else{
			    			    			    			    			    			    		    	method();
			    			    			    			    			    			    		    }
			    			    			    			    			    			    	    }
			    			    			    			    			    			    	});
			    			    			    			    			    		    	}
			    			    			    			    			    		    	else{
			    			    			    			    			    		    		method();
			    			    			    			    			    		    	}
			    			    			    			    			    		    	
			    			    			    			    			    		    }
			    			    			    			    			    	    }
			    			    			    			    			    	});
			    			    			    			    		    	}
			    			    			    			    		    	else{
			    			    			    			    		    		method();
			    			    			    			    		    	}
			    			    			    			    		    	
			    			    			    			    		    }
			    			    			    			    	    }
			    			    			    			    	});
			    			    			    		    	}
			    			    			    		    	else{
			    			    			    		    		method();
			    			    			    		    	}
			    			    			    		    	
			    			    			    		    }
			    			    			    	    }
			    			    			    	});
			    			    		    	}
			    			    		    	else{
			    			    		    		method();
			    			    		    	}
			    			    		    	
			    			    		    }
			    			    	    }
			    			    	});
			    		    	}
			    		    	else{
			    		    		method();
			    		    	}
			    		    	
			    		    }
			    	    }
			    	});
		    	}
		    	else{
		    		method();
		    	}
		    	
		    	
		    }
	    }
	});
	
	
	
	
	
}

function method(){
	var datagridList=$('#bank_C_PageTable').datagrid('getChecked');
	
	var FI_PAY_DATE=$("input[name='FI_PAY_DATE']").val();
	if(FI_PAY_DATE==null || FI_PAY_DATE==undefined || FI_PAY_DATE.length<=0){
		return $.messager.alert('警告','请填写付款日期');
	}
	
	var FI_FAG=$("select[name='FI_FAG']").val();
	
	if(FI_FAG=='4')
	{
		var BANK_NAME = $("input[name='BANK_NAME']").val();
		var BANK_ACCOUNT = $("input[name='BANK_ACCOUNT']").val();
		 if(BANK_NAME == "" || BANK_NAME == undefined || BANK_NAME.length<=0){
			 alert("请输入持卡人！");
			 return false;
		 }
		 
		 if(BANK_ACCOUNT == "" || BANK_ACCOUNT == undefined || BANK_ACCOUNT.length<=0){
			 alert("输入持卡账号！");
			 return false;
		 }
	}
	var selectData = [];
	for(var i = 0; i < datagridList.length; i++)
	{
		var temp={};
		temp.ID=datagridList[i].ID;
		temp.PAID_MONEY=datagridList[i].PAID_MONEY;
		selectData.push(temp);
	}
	$("#seachBut").linkbutton("disable");
	$("#emptyBut").linkbutton("disable");
	$("#submitBut").linkbutton("disable");
	$("#historyBut").linkbutton("disable");
	var dataJson ={selectData:selectData};
	$("#selectDateHidden").val(JSON.stringify(dataJson));
	$("#formSerach").submit();
}

//非网银-创建结算单
function creatNotBankFundSupp()
{
	var datagridList=$('#bank_C_PageTable').datagrid('getChecked');
	if(datagridList.length<=0)
	{
		alert("请先选择要数据在继续申请操作！");
		return false;
	}
	
	var IDS="";
	var IDS1="";
	var IDS2="";
	var IDS3="";
	var IDS4="";
	var IDS5="";
	var IDS6="";
	var hh=0;
	var aa=0;
	for(var i = 0; i < datagridList.length; i++){
		hh++;
		if(hh==1){
			if(aa==0){
				IDS=datagridList[i].ID;
			}
			else if(aa==1){
				IDS1=datagridList[i].ID;
			}
			else if(aa==2){
				IDS2=datagridList[i].ID;
			}
			else if(aa==3){
				IDS3=datagridList[i].ID;
			}
			else if(aa==4){
				IDS4=datagridList[i].ID;
			}
			else if(aa==5){
				IDS5=datagridList[i].ID;
			}
			else if(aa==6){
				IDS6=datagridList[i].ID;
			}
		}
		else{
			if(aa==0){
				IDS=IDS+","+datagridList[i].ID;
			}
			else if(aa==1){
				IDS1=IDS1+","+datagridList[i].ID;
			}
			else if(aa==2){
				IDS2=IDS2+","+datagridList[i].ID;
			}
			else if(aa==3){
				IDS3=IDS3+","+datagridList[i].ID;
			}
			else if(aa==4){
				IDS4=IDS4+","+datagridList[i].ID;
			}
			else if(aa==5){
				IDS5=IDS5+","+datagridList[i].ID;
			}
			else if(aa==6){
				IDS6=IDS6+","+datagridList[i].ID;
			}
		}
		if(hh==300){
			aa++;
			hh=0;
		}
	}
	
	$.ajax({
		url:_basePath+"/rentWrite/rentWrite!LockTypeIsF.action?IDS="+IDS,
	    type:'post',
	    dataType:'json',
	    success:function(json){
		    if(json.data == '2'){
		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
		    }
		    else if(json.data == '3'){
		    	return $.messager.alert("锁定提示","您所选择的数据已经被操作过,请刷新页面在操作！");
		    }
		    else{
		    	if(IDS1.length>0){
		    		$.ajax({
			    		url:_basePath+"/rentWrite/rentWrite!LockTypeIsF.action?IDS="+IDS1,
			    	    type:'post',
			    	    dataType:'json',
			    	    success:function(json){
			    		    if(json.data == '2'){
			    		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
			    		    }
			    		    else if(json.data == '3'){
			    		    	return $.messager.alert("锁定提示","您所选择的数据已经被操作过,请刷新页面在操作！");
			    		    }
			    		    else{
			    		    	if(IDS2.length>0){
			    		    		$.ajax({
			    			    		url:_basePath+"/rentWrite/rentWrite!LockTypeIsF.action?IDS="+IDS2,
			    			    	    type:'post',
			    			    	    dataType:'json',
			    			    	    success:function(json){
			    			    		    if(json.data == '2'){
			    			    		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
			    			    		    }
			    			    		    else if(json.data == '3'){
			    			    		    	return $.messager.alert("锁定提示","您所选择的数据已经被操作过,请刷新页面在操作！");
			    			    		    }
			    			    		    else{
			    			    		    	if(IDS3.length>0){
			    			    		    		$.ajax({
			    			    			    		url:_basePath+"/rentWrite/rentWrite!LockTypeIsF.action?IDS="+IDS3,
			    			    			    	    type:'post',
			    			    			    	    dataType:'json',
			    			    			    	    success:function(json){
			    			    			    		    if(json.data == '2'){
			    			    			    		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
			    			    			    		    }
			    			    			    		    else if(json.data == '3'){
			    			    			    		    	return $.messager.alert("锁定提示","您所选择的数据已经被操作过,请刷新页面在操作！");
			    			    			    		    }
			    			    			    		    else{
			    			    			    		    	if(IDS4.length>0){
			    			    			    		    		$.ajax({
			    			    			    			    		url:_basePath+"/rentWrite/rentWrite!LockTypeIsF.action?IDS="+IDS4,
			    			    			    			    	    type:'post',
			    			    			    			    	    dataType:'json',
			    			    			    			    	    success:function(json){
			    			    			    			    		    if(json.data == '2'){
			    			    			    			    		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
			    			    			    			    		    }
			    			    			    			    		    else if(json.data == '3'){
			    			    			    			    		    	return $.messager.alert("锁定提示","您所选择的数据已经被操作过,请刷新页面在操作！");
			    			    			    			    		    }
			    			    			    			    		    else{
			    			    			    			    		    	if(IDS5.length>0){
			    			    			    			    		    		$.ajax({
			    			    			    			    			    		url:_basePath+"/rentWrite/rentWrite!LockTypeIsF.action?IDS="+IDS5,
			    			    			    			    			    	    type:'post',
			    			    			    			    			    	    dataType:'json',
			    			    			    			    			    	    success:function(json){
			    			    			    			    			    		    if(json.data == '2'){
			    			    			    			    			    		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
			    			    			    			    			    		    }
			    			    			    			    			    		    else if(json.data == '3'){
			    			    			    			    			    		    	return $.messager.alert("锁定提示","您所选择的数据已经被操作过,请刷新页面在操作！");
			    			    			    			    			    		    }
			    			    			    			    			    		    else{
			    			    			    			    			    		    	if(IDS6.length>0){
			    			    			    			    			    		    		$.ajax({
			    			    			    			    			    			    		url:_basePath+"/rentWrite/rentWrite!LockTypeIsF.action?IDS="+IDS6,
			    			    			    			    			    			    	    type:'post',
			    			    			    			    			    			    	    dataType:'json',
			    			    			    			    			    			    	    success:function(json){
			    			    			    			    			    			    		    if(json.data == '2'){
			    			    			    			    			    			    		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
			    			    			    			    			    			    		    }
			    			    			    			    			    			    		    else if(json.data == '3'){
			    			    			    			    			    			    		    	return $.messager.alert("锁定提示","您所选择的数据已经被操作过,请刷新页面在操作！");
			    			    			    			    			    			    		    }
			    			    			    			    			    			    		    else{
			    			    			    			    			    			    		    	methedSupp();
			    			    			    			    			    			    		    }
			    			    			    			    			    			    	    }
			    			    			    			    			    			    	});
			    			    			    			    			    		    	}
			    			    			    			    			    		    	else{
			    			    			    			    			    		    		methedSupp();
			    			    			    			    			    		    	}
			    			    			    			    			    		    	
			    			    			    			    			    		    }
			    			    			    			    			    	    }
			    			    			    			    			    	});
			    			    			    			    		    	}
			    			    			    			    		    	else{
			    			    			    			    		    		methedSupp();
			    			    			    			    		    	}
			    			    			    			    		    	
			    			    			    			    		    }
			    			    			    			    	    }
			    			    			    			    	});
			    			    			    		    	}
			    			    			    		    	else{
			    			    			    		    		methedSupp();
			    			    			    		    	}
			    			    			    		    	
			    			    			    		    }
			    			    			    	    }
			    			    			    	});
			    			    		    	}
			    			    		    	else{
			    			    		    		methedSupp();
			    			    		    	}
			    			    		    	
			    			    		    }
			    			    	    }
			    			    	});
			    		    	}
			    		    	else{
			    		    		methedSupp();
			    		    	}
			    		    	
			    		    }
			    	    }
			    	});
		    	}
		    	else{
		    		methedSupp();
		    	}
		    	
		    	
		    }
	    }
	});
	
}

function methedSupp(){
	var datagridList=$('#bank_C_PageTable').datagrid('getChecked');
	var FI_PAY_DATE=$("input[name='FI_PAY_DATE']").val();
	if(FI_PAY_DATE==null || FI_PAY_DATE==undefined || FI_PAY_DATE.length<=0){
		return $.messager.alert('警告','请填写付款日期');
	}
	
	var FI_FAG=$("select[name='FI_FAG']").val();
	
	if(FI_FAG=='4')
	{
		var BANK_NAME = $("input[name='BANK_NAME']").val();
		var BANK_ACCOUNT = $("input[name='BANK_ACCOUNT']").val();
		 if(BANK_NAME == "" || BANK_NAME == undefined || BANK_NAME.length<=0){
			 alert("请输入持卡人！");
			 return false;
		 }
		 
		 if(BANK_ACCOUNT == "" || BANK_ACCOUNT == undefined || BANK_ACCOUNT.length<=0){
			 alert("输入持卡账号！");
			 return false;
		 }
		 if(!confirm("请确认付款方式是山重主动网银扣划。确定点击确定，继续提交，否则请选择取消并选择非网银?")){
				return false;
			}
	}
	else{
		if(!confirm("请确认付款方式是直接打款至山重或冲抵。确定点击确定继续提交，否则请选择取消并选择网银?")){
			return false;
		}
	}
	var selectData = [];
	for(var i = 0; i < datagridList.length; i++)
	{
		var temp={};
		temp.ID=datagridList[i].ID;
		temp.PAID_MONEY=datagridList[i].PAID_MONEY;
		selectData.push(temp);
	}
	$("#seachBut").linkbutton("disable");
	$("#emptyBut").linkbutton("disable");
	$("#submitBut").linkbutton("disable");
	$("#historyBut").linkbutton("disable");
	var dataJson ={selectData:selectData};
	$("#selectDateHidden").val(JSON.stringify(dataJson));
	$("#formSerach").submit();
}


/**
 * 清空按钮
 * @return
 */
function emptyData(){
	$('#pageForm').form('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
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


function toSubmitPayment()
{
	var datagridList=$('#pageTable').datagrid('getChecked');
	if(datagridList.length<=0)
	{
		alert("请先选择要数据在继续申请操作！");
		return false;
	}
	var getDetailData = [];
	for(var i = 0; i<datagridList.length; i++) {
		var temp = {};
		temp.FUND_ID = datagridList[i].HEAD_ID;
		getDetailData.push(temp);
	}
	
	var data = {};
	data["getDetailData"] = getDetailData;
	
	$.ajax({
		url:_basePath+"/rentWrite/rentWrite!toSubmitPayment.action",
	    type:'post',
	    data:'data='+JSON.stringify(data),
	    dataType:'json',
	    success:function(json){
		    if(json.flag == true){
		    	$.messager.alert("租金提交","租金提交成功！");
		    	//页面刷新
		    	window.location.href = _basePath+"/rentWrite/rentWrite!toMgDeduct.action";
		    }else {
		    	$.messager.alert("租金提交","租金提交失败！");
		    	//页面刷新
		    	$('#pageTable').datagrid('reload');
		    	window.location.href = _basePath+"/rentWrite/rentWrite!toMgDeduct.action";
		    }
	    }
	});
}



function toAppPayment(){
	window.location.href = _basePath+"/rentWrite/rentWrite!bank_C_Manger.action";
}

//供应商不能修改核销金额
function toAppPaymentSUPP(){
	window.location.href = _basePath+"/rentWrite/rentWrite!bank_C_MangerSUPP.action";
}

function rollback(){
	var datagridList=$('#cyberBankPageTable').datagrid('getChecked');
	if(datagridList.length<=0)
	{
		alert("请先选择要数据在继续重置操作！");
		return false;
	}
	
	$.messager.confirm("提示","您确认要重置选中的数据？",function(flag){
		if(flag){
			var datagridList=$('#cyberBankPageTable').datagrid('getChecked');
			var IDS="";
			var selectData = [];
			for(var i = 0; i < datagridList.length; i++)
			{
				if(i==0)
				{
					IDS=datagridList[i].ID;
				}
				else
				{
					IDS=IDS+","+datagridList[i].ID;
				}
				
			}
			$("#IDS").val(IDS);
			$("#formRoll").submit();
		}
	});
	
}


//上传回执
function uploadExcel(){
	var datagridList=$('#cyberBankPageTable').datagrid('getRows');
	if(datagridList.length == 0)
	{
		$.messager.alert("提示","没有待确认数据!");
	}
	var url = "rentWrite!uploadExcel.action";

	var bank_type = "";
	var fromDate = "";
	var fileN="";
	
	var filename = $('#uploadFile').val();
	fileN=filename;
	$('#uploadDialog').dialog({
		title : '上传回执',
		width : 400,
		height : 170,
		closed : true,
		cache : false,
		modal : true,
		closed : false,
		buttons : 
			[{
				text : '上传',
				iconCls : 'icon-up',
				handler : function()
					{
							$('#fileUploadForm').form('submit',
								{
                                    onSubmit: function()
									{
										
										bank_type = $('#bankFlag').combobox("getValue");
										fromDate = $('#fromDate').datebox("getValue");
										if($('#uploadFile').val().indexOf('.xls') == -1 &&  bank_type =="1")
										{
											$.messager.alert('提示','建设银行请上传.xls结尾的文件!','');
											return false;
										}
										if($('#uploadFile').val().indexOf('.txt') == -1 &&  bank_type =="2")
										{
											$.messager.alert('提示','民生银行请上传.txt结尾的文件!','');
											return false;
										}
										  
                                    },
                                    url:url+"?bankFlag="+$('#bankFlag').combobox("getValue")+"&fromDate="+$('#fromDate').datebox("getValue")+"&fileN="+$('#uploadFile').val(),
                					success:function(data)
									{
                					 	var data = eval('(' + data + ')');
                						$.messager.alert('',data.msg,'');
										$('#uploadDialog').dialog('close');
                					}
                            });
                        
        			}
			},
			{
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function()
				{
					$('#uploadDialog').dialog('close');
				}
			}]
	});
}



function destroySuppUp()
{
	$("#divFrom").empty();
	var datagridList=$('#pageTable_cyber_Con').datagrid('getChecked');
	if(datagridList.length<=0)
	{
		alert("请先选择要导出的数据在继续导出操作！");
		return false;
	}
	else
	{
		$.messager.confirm("提示","您确认要重置选中的数据？",function(flag){
			if(flag){
				var datagridList=$('#pageTable_cyber_Con').datagrid('getChecked');
				var IDS="";
				for(var i = 0; i < datagridList.length; i++)
				{
					if(i==0)
					{
						IDS=datagridList[i].HEAD_ID;
					}
					else
					{
						IDS=IDS+","+datagridList[i].HEAD_ID;
					}
					
				}
				url = _basePath+"/rentWrite/rentWrite!destroySuppUp.action";
				$("#divFrom").append("<form id='formRoll' method='post' action='"+url+"'><input id='IDS' name='IDS' value='"+IDS+"'></form>");
				$("#formRoll").submit();
			}
		});
	}
}


//上传回执
function uploadExcelAll(){
	var datagridList=$('#pageTable_cyber_Con').datagrid('getRows');
	if(datagridList.length == 0)
	{
		$.messager.alert("提示","没有待确认数据!");
//		return true;
	}
	var url = "rentWrite!uploadExcel1.action";

	var bank_type = "";
	var fromDate = "";
	
	$('#uploadDialog').dialog({
		title : '上传回执',
		width : 400,
		height : 170,
		closed : true,
		cache : false,
		modal : true,
		closed : false,
		buttons : 
			[{
				text : '上传',
				id : 'fileUploadLink',
				iconCls : 'icon-up',
				handler : function()
					{
							$('#fileUploadForm').form('submit',
								{
                                    onSubmit: function()
									{
										
										var filename = $('#uploadFile').val();
										bank_type = $('#bank_type').combobox("getValue");
										fromDate = $('#fromDate').datebox("getValue");
										if(filename.indexOf('.xls') == -1 &&  bank_type =="1")
										{
											$.messager.alert('提示','建设银行请上传.xls结尾的文件!','');
											return false;
										}
										if(filename.indexOf('.txt') == -1 &&  bank_type =="")
										{
											$.messager.alert('提示','民生银行请上传.txt结尾的文件!','');
											return false;
										}
										$('#fileUploadLink').linkbutton('disable');  
                                    },
                                    url:url+"?bankFlag="+$('#bank_type').combobox("getValue")+"&fromDate="+$('#fromDate').datebox("getValue"),
                					success:function(data)
									{
                					 	var data = eval('(' + data + ')');
                						$.messager.alert('',data.msg,'');
                						$('#fileUploadLink').linkbutton('enable');
										$('#uploadDialog').dialog('close');
                					}
                            });
                        
        			}
			},
			{
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function()
				{
					$('#fileUploadLink').linkbutton('enable');
					$('#uploadDialog').dialog('close');
				}
			}]
	});
}


function toChange(obj){
	var tr=$(obj).parent().parent();
	var trP=$(obj).parent().parent().parent();
	var FI_REALITY_ACCOUNT = parseFloat($(tr).find("#FI_REALITY_ACCOUNT").val());//本次实付
	var fi_pay_money = parseFloat($(trP).find("#fi_pay_money_").val());//本次应收核销
	var unusedAmount = parseFloat(0);
	
	//当本次实付>本次应收金额时， 计算本次来款余额
	if(FI_REALITY_ACCOUNT>=fi_pay_money){
		unusedAmount = FI_REALITY_ACCOUNT - fi_pay_money;//本次来款余额
		$(trP).find("#poolTab").attr("style", "display:none");
		$(trP).find("a[name='saveHref']").attr("style", "display:block");
		$(trP).find("a[name='heXsHref']").attr("style", "display:none");
	}else if(FI_REALITY_ACCOUNT<fi_pay_money){
		//本次来款余额
		unusedAmount=0;		
		$(trP).find("#poolTab").attr("style", "display:block");
		$(trP).find("a[name='saveHref']").attr("style", "display:none");
		$(trP).find("a[name='heXsHref']").attr("style", "display:block");
	}
	
	$(tr).find("#FI_PAY_YUE").val(fomatFloat(unusedAmount,2));
}

function toChangePool(obj)
{
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


function toCommit(obj){
	var tr=$(obj).parent().parent().parent();
	if($(tr).find("#FI_TO_THE_PAYEE").val()==null || $(tr).find("#FI_TO_THE_PAYEE").val()==undefined || $(tr).find("#FI_TO_THE_PAYEE").val().length<=0){
		return $.messager.alert('警告','请填写来款人');
	}
	var FI_REALITY_BANK = $(tr).find("select[name='FI_REALITY_BANK']").find("option:selected").val();
	if(FI_REALITY_BANK.length<=0){
		return $.messager.alert('警告','请填写核销银行');
	}
	if($(tr).find("#FI_ACCOUNT_DATE").datebox('getValue')==null || $(tr).find("#FI_ACCOUNT_DATE").datebox('getValue')==undefined  || $(tr).find("#FI_ACCOUNT_DATE").datebox('getValue').length<=0){
		return $.messager.alert('警告','请填写核销时间');
	}
	var data = {};
	data["getBaseData"] = getBaseData(tr);
	
	if($(tr).find("#FI_REALITY_ACCOUNT").val()==null || $(tr).find("#FI_REALITY_ACCOUNT").val()==undefined){
		return $.messager.alert('警告','请填写本次实付！');
	}else{
		if(parseFloat($(tr).find("#FI_REALITY_ACCOUNT").val())<parseFloat($(tr).find("#fi_pay_money_").val())){
			var totalMoney = parseFloat($(tr).find("#FI_REALITY_ACCOUNT").val())+parseFloat($(tr).find("#realityMoneyTotal").val());
			if(parseFloat(totalMoney)>parseFloat($(tr).find("#fi_pay_money_").val())){
				return $.messager.alert('警告','您选择金额大于本次应收核销金额，请重新选择金额！');
			}else{
				data["getPoolData"] = getPoolData(tr);	
			}
		}
	}	
	
	$.ajax({		
		url:_basePath+"/rentWrite/rentWrite!doCommitHXSheet.action",
	    type:'post',
	    data:'data='+encodeURI(JSON.stringify(data)),
	    dataType:'json',
	    success:function(json){
		 if(json.flag == true){
			 $.messager.alert("提示","保存成功！！");	
			//页面刷新
			 window.location.href = _basePath+"/rentWrite/rentWrite!toMgAppCheckMg.action";
		 }else{
			 $.messager.alert("提示","保存失败！！");			 
		 }
		
		}
	});
}

function toCommitHexiao(obj)
{
	var tr=$(obj).parent().parent().parent();
	if($(tr).find("#FI_TO_THE_PAYEE").val()==null || $(tr).find("#FI_TO_THE_PAYEE").val()==undefined || $(tr).find("#FI_TO_THE_PAYEE").val().length<=0){
		return $.messager.alert('警告','请填写来款人');
	}
	var FI_REALITY_BANK = $(tr).find("select[name='FI_REALITY_BANK']").find("option:selected").val();
	if(FI_REALITY_BANK.length<=0){
		return $.messager.alert('警告','请填写核销银行');
	}
	if($(tr).find("#FI_ACCOUNT_DATE").datebox('getValue')==null || $(tr).find("#FI_ACCOUNT_DATE").datebox('getValue')==undefined  || $(tr).find("#FI_ACCOUNT_DATE").datebox('getValue').length<=0){
		return $.messager.alert('警告','请填写核销时间');
	}
	var data = {};
	data["getBaseData"] = getBaseData(tr);
	
	if($(tr).find("#FI_REALITY_ACCOUNT").val()==null || $(tr).find("#FI_REALITY_ACCOUNT").val()==undefined){
		return $.messager.alert('警告','请填写本次实付！');
	}else{
		if(parseFloat($(tr).find("#FI_REALITY_ACCOUNT").val())<parseFloat($(tr).find("#fi_pay_money_").val())){
			var totalMoney = parseFloat($(tr).find("#FI_REALITY_ACCOUNT").val())+parseFloat($(tr).find("#realityMoneyTotal").val());
			if(parseFloat(totalMoney)>parseFloat($(tr).find("#fi_pay_money_").val())){
				return $.messager.alert('警告','您选择金额大于本次应收核销金额，请重新选择金额！');
			}else{
				data["getPoolData"] = getPoolData(tr);	
			}
		}
	}	
	
	$.ajax({		
		url:_basePath+"/rentWrite/rentWrite!doCommitHXSheetHexiao.action",
	    type:'post',
	    data:'data='+encodeURI(JSON.stringify(data)),
	    dataType:'json',
	    success:function(json){
		 if(json.flag == true){
			 $.messager.alert("提示","核销成功！！");	
			//页面刷新
			 window.location.href = _basePath+"/rentWrite/rentWrite!toMgAppCheckMg.action";
		 }else{
			 $.messager.alert("提示","核销失败！！");			 
		 }
		
		}
	});
}


function getBaseData(obj){
	var getBaseData = {};
	var FI_ACCOUNT_DATE = $(obj).find("#FI_ACCOUNT_DATE").datebox('getValue');
	var FI_REALITY_BANK = $(obj).find("select[name='FI_REALITY_BANK']").find("option:selected").val();
	var FI_TAGE = $(obj).find("select[name='FI_TAGE']").find("option:selected").val();
	var FI_TAGE_ID=$(obj).find("select[name='FI_TAGE_ID']").find("option:selected").val();
	var FI_TAGE_NAME=$(obj).find("select[name='FI_TAGE_ID']").find("option:selected").attr("FI_TAGE_NAME");
	getBaseData["FI_ACCOUNT_DATE"] = FI_ACCOUNT_DATE;
	getBaseData["FI_REALITY_BANK"] = FI_REALITY_BANK;
	getBaseData["FI_TAGE"] = FI_TAGE;
	getBaseData["FUND_ID"] = $(obj).find("#FUND_ID").val();
	getBaseData["FI_TO_THE_PAYEE"] = $(obj).find("#FI_TO_THE_PAYEE").val();
	getBaseData["FI_REALITY_MONEY"] = $(obj).find("#FI_PAY_MONEY").val();
	getBaseData["FI_REALITY_ACCOUNT"] = $(obj).find("#FI_REALITY_ACCOUNT").val();
	getBaseData["FI_TAGE_ID"] = FI_TAGE_ID;
	getBaseData["FI_TAGE_NAME"] = FI_TAGE_NAME;
	getBaseData["FI_PAY_BANK"] = $(obj).find("#FI_PAY_BANK").val();
	getBaseData["FI_PAY_YUE"] = $(obj).find("#FI_PAY_YUE").val();
	var FUND_ID=$(obj).find("#FUND_ID").val();
	getBaseData["MONEYORDERSAVE"] = $(obj).find("#moneyOrderSave"+FUND_ID).val();

	return getBaseData;
}

function getPoolData(obj){
	var getPoolData = [];
	$(obj).find("#pool_tab tr").each(function(){
		var temp = {};
		temp.POOL_TYPE= $(this).find("input[name=POOLID]").val();//租金池类别
		temp.POOLUSERMONEY = $(this).find("input[name=POOLUSERMONEY]").val();//可用余额
		temp.dichong_money = $(this).find("input[name=dichong_money]").val();//所选冲抵款合计
		getPoolData.push(temp);
	});
	return getPoolData;
}

function toCommintFund(){
	var detailData = $("#pageTable_NotCon").datagrid('getSelections');//获取选中信息
	if(detailData.length<=0){
		return $.messager.alert('警告','请填选择提交数据');
	}else{
		for(var i = 0; i<detailData.length; i++) {
			
			var HEAD_ID = detailData[i].HEAD_ID;//付款单号
			var FI_ACCOUNT_DATE = detailData[i].FI_ACCOUNT_DATE;//到账日期
			var FI_REALITY_BANK = detailData[i].FI_REALITY_BANK;//核销银行
			if(FI_ACCOUNT_DATE==null || FI_ACCOUNT_DATE=='undefined' || FI_ACCOUNT_DATE.length <=0)
			{
				alert("收款单号:"+HEAD_ID+"没有保存到账日期，不能核销！");
				return false;
			}
			
			if(FI_REALITY_BANK==null || FI_REALITY_BANK=='undefined' || FI_REALITY_BANK.length<=0)
			{
				alert("收款单号:"+HEAD_ID+"没有保存核销银行，不能核销！");
				return false;
			}
		}
		
		$.messager.confirm('提示','确定核销吗?',function(r) {
			if (r) {
					var data = {};
					data["getDetailData"] = getDetailData();
					$("#confirBut").linkbutton("disable");
					$.ajax( {
							url : _basePath+ "/rentWrite/rentWrite!doCheckedFund.action",
					       type : 'post',
						   data : 'data=' + JSON.stringify(data),
					   dataType : 'json',
						success : function(json) {
							if (json.flag == true) {
								$.messager.alert("提示",
										"核销成功！！");
								//页面刷新
								window.location.href = _basePath
										+ "/rentWrite/rentWrite!toMgAppCheckMg.action";		
							} else {
								$.messager.alert("提示",
										"核销失败！！");
							}
										
							}
					});
			}
		});
		
	}
}

function getDetailData(){
	var getDetailData = [];
	var detailData = $("#pageTable_NotCon").datagrid('getSelections');//获取选中信息
	for(var i = 0; i<detailData.length; i++) {
		var temp = {};
		temp.HEAD_ID = detailData[i].HEAD_ID;//付款单号
		temp.FI_ACCOUNT_DATE = detailData[i].FI_ACCOUNT_DATE;//到账日期
		temp.FI_REALITY_BANK = detailData[i].FI_REALITY_BANK;//核销银行
		temp.FI_REMARK = detailData[i].FI_REMARK;//驳回原因
		getDetailData.push(temp);
	}
	return getDetailData;
}

function fomatFloat(src,pos){       
    return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);       
} 


function doRegist(){
	if(getDetailData()==''){
		$.messager.alert("警告","请选择需驳回数据！！");
	}else{
		$.messager.confirm('提示','确定要驳回吗?', function(r) {
			if (r) {
				var data = {};
				data["getDetailData"] = getDetailData();
				
				$.ajax({		
					url:_basePath+"/rentWrite/rentWrite!doReject.action",
				    type:'post',
				    data:'data='+JSON.stringify(data),
				    dataType:'json',
				    success:function(json){
					 if(json.flag == true){
						 $.messager.alert("提示","驳回成功！！");				
					 }else{
						 $.messager.alert("提示","驳回失败！！");
					 }
					//页面刷新
					window.location.href = _basePath+"/rentWrite/rentWrite!toMgAppCheckMg.action";
					}
				});
			}		
		});
	}
}

function doRegist1(){
	if(getDetailData1()==''){
		$.messager.alert("警告","请选择需驳回数据！！");
	}else{
		$.messager.confirm('提示','确定要驳回吗?', function(r) {
			if (r) {
				var data = {};
				data["getDetailData"] = getDetailData1();
				
				$.ajax({		
					url:_basePath+"/rentWrite/rentWrite!doReject.action",
				    type:'post',
				    data:'data='+JSON.stringify(data),
				    dataType:'json',
				    success:function(json){
					 if(json.flag == true){
						 $.messager.alert("提示","驳回成功！！");
						//页面刷新
							window.location.href = _basePath+"/rentWrite/rentWrite!toSupperFundMg.action";
					 }else{
						 $.messager.alert("提示","驳回失败！！");
					 }
					
					}
				});
			}		
		});
	}
}

function getDetailData1(){
	var getDetailData = [];
	var detailData = $("#pageTable_cyber_Con").datagrid('getSelections');//获取选中信息
	for(var i = 0; i<detailData.length; i++) {
		var temp = {};
		temp.HEAD_ID = detailData[i].HEAD_ID;//付款单号
		temp.FI_ACCOUNT_DATE = detailData[i].FI_ACCOUNT_DATE;//到账日期
		temp.FI_REALITY_BANK = detailData[i].FI_REALITY_BANK;//核销银行
		temp.FI_REMARK = detailData[i].FI_REMARK;//驳回原因
		getDetailData.push(temp);
	}
	return getDetailData;
}

function updateMoneyOrder(obj){
	 $("#dlgUp"+obj).dialog("open");
}

