function atobcompure(value,rowData){
		if(rowData.STATUS=='2'){
			return "<a href='javascript:void(0)' onclick=updateAChangeBApply('"+rowData.PROJECT_ID+"')>修改</a>";
		}else
			return "<a href='javascript:void(0)' onclick=aChangeApply('"+rowData.PROJECT_ID+"','"+rowData.YQZJ+"')>变更申请</a>";
}
function aChangeApply(PROJECT_ID,YQZJ){
	if(YQZJ==''||YQZJ=='0'||YQZJ==undefined||YQZJ=='undefined'){
		top.addTab("变更申请",_basePath+"/achangeb/AtoB!toAtoBApplyForm.action?PROJECT_ID="+PROJECT_ID);
	}else{
		$.messager.alert("提示","该项目存在逾期，变更申请");
		return;
	}
}

function showProject(value,rowData){
	return "<a href='javascript:void(0)' onclick='projectSHow(" + JSON.stringify(rowData) + ")'>"+value+"</a>";
}

function updateAChangeBApply(PROJECT_ID){
	top.addTab("变更申请",_basePath+"/achangeb/AtoB!toUpdateAChangeBApply.action?PROJECT_ID="+PROJECT_ID);
}
function seAchangeBTable(){
	$('#achangebTable').datagrid('load', {"param":getFromData("#achangebAppForm")});
}
function clearAchangeB(){
	$("input[name=SUPPLIER_NAMES]").val('');
	$("input[name=PROJECT_CODE]").val('');
	$("#PRODUCT_NAMES").combobox('setValue','');
	$("#START_CONFIRM_DATE_START").datebox('setValue','');
	$("#START_CONFIRM_DATE_END").datebox('setValue','');
	$("input[name=COMPANY_NAMES]").val('');
	$("input[name=CLIENT_NAME]").val('');
	$("input[name=OVER_TERM]").val('');
	$("#END_DATE_START").datebox('setValue','');
	$("#END_DATE_END").datebox('setValue','');
}


function seAchangeBManager(){
	$('#achangebTableManager').datagrid('load', {"param":getFromData("#achangebMgForm")});
}
function clearAchangeBManager(){
	$("input[name=SUPPLIER_NAMES]").val('');
	$("input[name=COMPANY_NAME]").val('');
	$("input[name=PROJECT_CODE]").val('');
	$("input[name=CLIENT_NAME]").val('');
	$("#EQUIPMENINFOS").combobox('setValue','');
	$("input[name=EQ_TYPE]").val('');
	$("#APPLY_DATE_BEGIN").datebox('setValue','');
	$("#APPLY_DATE_END").datebox('setValue','');	
}
function addaTob(){
	if($("#addAtoBButton").attr("disabled")=="disabled"){
		return ;
	}
	var OPEN_BANK_ACCOUNT=$("#OPEN_ACCOUNT").val();
	var OPEN_ACCOUNT_CONFIRM=$("#OPEN_ACCOUNT_CONFIRM").val();
	if(OPEN_BANK_ACCOUNT==null||OPEN_BANK_ACCOUNT==''||OPEN_ACCOUNT_CONFIRM==null||OPEN_ACCOUNT_CONFIRM==''){
		$.messager.alert("提示","请输入银行卡号");
		return ;
	}
	if(OPEN_BANK_ACCOUNT!=OPEN_ACCOUNT_CONFIRM){
		$.messager.alert("提示","两次输入的卡号不一致");
		return ;
	}
	var SUPPLIER_ID=$("#SUPPLIER_ID").val();
	var NEW_CLIENT_ID=$("#CLIENT_ID").val();
	var CLIENT_NAME=$("#CLIENT_NAME").val();
	var CLIENT_CODE=$("#CUST_ID").val();
	var PROJECT_ID=$("#PROJECT_ID").val();
	var SUP_ID=$("#SUP_ID").val();
	var OLD_CLIENT_ID=$("#OLD_CLIENT_ID").val();
	var CLIENT_TYPE=$("#CLIENT_TYPE").val();
	var OPEN_BANK_CODE=$("#OPEN_BANK").combobox('getValue');
	var OPEN_BANK_NAME=$("#OPEN_BANK").combobox('getText');
	var OPEN_ACCOUNT_NAME=$("#OPEN_NAME").val();
	if(CLIENT_NAME==null ||CLIENT_NAME==""){
		$.messager.alert("提示","请选择B客户姓名");
		return ;
	}else if(OPEN_BANK_CODE==null || OPEN_BANK_CODE==""){
		$.messager.alert("提示","请选择开户行信息");
		return ;
	}else if(OPEN_ACCOUNT_NAME==null || OPEN_ACCOUNT_NAME==""){
		$.messager.alert("提示","请输入开户名称");
		return ;
	}
	var PAYLIST_CODE=$("#PROJECT_CODE").val()+"-1";
	$.ajax({
		type:"post",
		url:_basePath+"/achangeb/AtoB!doAddAtoB.action",
		data:"NEW_CLIENT_ID="+NEW_CLIENT_ID+"&CLIENT_NAME="+CLIENT_NAME+"&CLIENT_CODE="+CLIENT_CODE+"&PROJECT_ID="
			 +PROJECT_ID+"&SUP_ID="+SUP_ID+"&OLD_CLIENT_ID="+OLD_CLIENT_ID+"&CLIENT_TYPE="+CLIENT_TYPE+"&OPEN_BANK_CODE="
			 +OPEN_BANK_CODE+"&OPEN_BANK_NAME="+OPEN_BANK_NAME+"&OPEN_ACCOUNT_NAME="+OPEN_ACCOUNT_NAME+"&PAYLIST_CODE="
			 +PAYLIST_CODE+"&OPEN_BANK_ACCOUNT="+OPEN_BANK_ACCOUNT+"&SUPPLIER_ID="+SUPPLIER_ID,
		dataType:"json",
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","保存成功，请上传新附件资料");
				$("#addAtoBButton").linkbutton("disable");
				$("#addAtoBButton").attr("disabled","disabled");
				addNewFileTabs();
			}else{
				$.messager.alert("提示",json.msg);
			}
		},
		error:function(){
			$.messager.alert("提示","请联系网络管理员");
		}
	});
}

function addNewFileTabs(){
	var CUST_ID='';
	var CLIENT_TYPE='';
	var CLIENT_NAME='';
	CUST_ID=jQuery("#CUST_ID").val();
	CLIENT_TYPE=jQuery("#CLIENT_TYPE").val();
	CLIENT_NAME=jQuery("#CLIENT_NAME").val();
	var PRO_CODE=jQuery("#PRO_CODE").val();
	var url=_basePath+'/crm/Customer!toMgElectronicPhotoAlbum1.action?RENTER_CODE='+CUST_ID+'&CUSTOMER_TYPE='+CLIENT_TYPE+'&RENTER_NAME='+encodeURI(CLIENT_NAME)+"&PRO_CODE="+PRO_CODE+"&jbpmAtoB=jbpm";
	jQuery('#base_ifo').tabs('add',{
		  title:'新附件资料清单',
		  href:url
	});
}

function projectSHow(row){
	 if (row){
		 var PROJECT_ID=row.PROJECT_ID;
		 var PRO_CODE=row.PROJECT_CODE;
		 top.addTab(PRO_CODE+"方案",_basePath+"/project/project!projectShow.action?PROJECT_ID="+PROJECT_ID);
	}else{
		$.messager.alert("请选择一个项目!");
	}
}

function startJbpmForAtoB(){
	$("#startJbpmForAtoB").attr("disabled",true);
	$("#startJbpmForAtoB").linkbutton("disable");
	var OPEN_BANK_ACCOUNT=$("#OPEN_ACCOUNT").val();
	var OPEN_ACCOUNT_CONFIRM=$("#OPEN_ACCOUNT_CONFIRM").val();
	if(OPEN_BANK_ACCOUNT==null||OPEN_BANK_ACCOUNT==''||OPEN_ACCOUNT_CONFIRM==null||OPEN_ACCOUNT_CONFIRM==''){
		$.messager.alert("提示","请输入银行卡号");
		$("#startJbpmForAtoB").attr("disabled",false);
		$("#startJbpmForAtoB").linkbutton("enable");
		return ;
	}
	if(OPEN_BANK_ACCOUNT!=OPEN_ACCOUNT_CONFIRM){
		$.messager.alert("提示","两次输入的卡号不一致");
		$("#startJbpmForAtoB").attr("disabled",false);
		$("#startJbpmForAtoB").linkbutton("enable");
		return ;
	}
	var SUPPLIER_ID=$("#SUPPLIER_ID").val();
	var NEW_CLIENT_ID=$("#CLIENT_ID").val();
	var CLIENT_NAME=$("#CLIENT_NAME").val();
	var CLIENT_CODE=$("#CUST_ID").val();
	var PROJECT_ID=$("#PROJECT_ID").val();
	var SUP_ID=$("#SUP_ID").val();
	var OLD_CLIENT_ID=$("#OLD_CLIENT_ID").val();
	var CLIENT_TYPE=$("#CLIENT_TYPE").val();
	var OPEN_BANK_CODE=$("#OPEN_BANK").combobox('getValue');
	var OPEN_BANK_NAME=$("#OPEN_BANK").combobox('getText');
	var OPEN_ACCOUNT_NAME=$("#OPEN_NAME").val();
	if(CLIENT_NAME==null ||CLIENT_NAME==""){
		$.messager.alert("提示","请选择B客户姓名");
		$("#startJbpmForAtoB").attr("disabled",false);
		$("#startJbpmForAtoB").linkbutton("enable");
		return ;
	}else if(OPEN_BANK_CODE==null || OPEN_BANK_CODE==""){
		$.messager.alert("提示","请选择开户行信息");
		$("#startJbpmForAtoB").attr("disabled",false);
		$("#startJbpmForAtoB").linkbutton("enable");
		return ;
	}else if(OPEN_ACCOUNT_NAME==null || OPEN_ACCOUNT_NAME==""){
		$.messager.alert("提示","请输入开户名称");
		$("#startJbpmForAtoB").attr("disabled",false);
		$("#startJbpmForAtoB").linkbutton("enable");
		return ;
	}
	var PAYLIST_CODE=$("#PROJECT_CODE").val()+"-1";
	
	$.ajax({
		type:"post",
		url:_basePath+"/achangeb/AtoB!startJbpmForAtoB.action",
		data:"NEW_CLIENT_ID="+NEW_CLIENT_ID+"&CLIENT_NAME="+CLIENT_NAME+"&CLIENT_CODE="+CLIENT_CODE+"&PROJECT_ID="
			 +PROJECT_ID+"&SUP_ID="+SUP_ID+"&OLD_CLIENT_ID="+OLD_CLIENT_ID+"&CLIENT_TYPE="+CLIENT_TYPE+"&OPEN_BANK_CODE="
			 +OPEN_BANK_CODE+"&OPEN_BANK_NAME="+OPEN_BANK_NAME+"&OPEN_ACCOUNT_NAME="+OPEN_ACCOUNT_NAME+"&PAYLIST_CODE="
			 +PAYLIST_CODE+"&OPEN_BANK_ACCOUNT="+OPEN_BANK_ACCOUNT+"&SUPPLIER_ID="+SUPPLIER_ID,
		dataType:"json",
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","流程已成功发起");
			}else{
				$.messager.alert("提示",json.msg);
				$("#startJbpmForAtoB").attr("disabled",false);
				$("#startJbpmForAtoB").linkbutton("enable");
			}
		},
		error:function(){
			$.messager.alert("提示","请联系网络管理员");
			$("#startJbpmForAtoB").attr("disabled",false);
			$("#startJbpmForAtoB").linkbutton("enable");
		}
	});
}

function updateBInfo(){
	$("#updateAtoBButton").attr("disabled",true);
	$("#updateAtoBButton").linkbutton("disable");
	var OPEN_BANK_ACCOUNT=$("#OPEN_ACCOUNT").val();
	var OPEN_ACCOUNT_CONFIRM=$("#OPEN_ACCOUNT_CONFIRM").val();
	if(OPEN_BANK_ACCOUNT==null||OPEN_BANK_ACCOUNT==''||OPEN_ACCOUNT_CONFIRM==null||OPEN_ACCOUNT_CONFIRM==''){
		$.messager.alert("提示","请输入银行卡号");
		$("#updateAtoBButton").attr("disabled",false);
		$("#updateAtoBButton").linkbutton("enable");
		return ;
	}
	if(OPEN_BANK_ACCOUNT!=OPEN_ACCOUNT_CONFIRM){
		$.messager.alert("提示","两次输入的卡号不一致");
		$("#updateAtoBButton").attr("disabled",false);
		$("#updateAtoBButton").linkbutton("enable");
		return ;
	}
	var SUPPLIER_ID=$("#SUPPLIER_ID").val();
	var NEW_CLIENT_ID=$("#CLIENT_ID").val();
	var CLIENT_NAME=$("#CLIENT_NAME").val();
	var CLIENT_CODE=$("#CUST_ID").val();
	var PROJECT_ID=$("#PROJECT_ID").val();
	var SUP_ID=$("#SUP_ID").val();
	var OLD_CLIENT_ID=$("#OLD_CLIENT_ID").val();
	var CLIENT_TYPE=$("#CLIENT_TYPE").val();
	var OPEN_BANK_CODE=$("#OPEN_BANK").combobox('getValue');
	var OPEN_BANK_NAME=$("#OPEN_BANK").combobox('getText');
	var OPEN_ACCOUNT_NAME=$("#OPEN_NAME").val();
	if(CLIENT_NAME==null ||CLIENT_NAME==""){
		$.messager.alert("提示","请选择B客户姓名");
		$("#updateAtoBButton").attr("disabled",false);
		$("#updateAtoBButton").linkbutton("enable");
		return ;
	}else if(OPEN_BANK_CODE==null || OPEN_BANK_CODE==""){
		$.messager.alert("提示","请选择开户行信息");
		$("#updateAtoBButton").attr("disabled",false);
		$("#updateAtoBButton").linkbutton("enable");
		return ;
	}else if(OPEN_ACCOUNT_NAME==null || OPEN_ACCOUNT_NAME==""){
		$.messager.alert("提示","请输入开户名称");
		$("#updateAtoBButton").attr("disabled",false);
		$("#updateAtoBButton").linkbutton("enable");
		return ;
	}
	var PAYLIST_CODE=$("#PROJECT_CODE").val()+"-1";
	var B_ID=$("#B_ID").val();
	$.ajax({
		type:"post",
		url:_basePath+"/achangeb/AtoB!doUpdateAtoB.action",
		data:"B_ID="+B_ID+"&NEW_CLIENT_ID="+NEW_CLIENT_ID+"&CLIENT_NAME="+CLIENT_NAME+"&CLIENT_CODE="+CLIENT_CODE+"&PROJECT_ID="
			 +PROJECT_ID+"&SUP_ID="+SUP_ID+"&OLD_CLIENT_ID="+OLD_CLIENT_ID+"&CLIENT_TYPE="+CLIENT_TYPE+"&OPEN_BANK_CODE="
			 +OPEN_BANK_CODE+"&OPEN_BANK_NAME="+OPEN_BANK_NAME+"&OPEN_ACCOUNT_NAME="+OPEN_ACCOUNT_NAME+"&PAYLIST_CODE="
			 +PAYLIST_CODE+"&OPEN_BANK_ACCOUNT="+OPEN_BANK_ACCOUNT+"&SUPPLIER_ID="+SUPPLIER_ID,
		dataType:"json",
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","保存成功,请上传新资料");
			}else{
				$.messager.alert("提示",json.msg);
				$("#updateAtoBButton").attr("disabled",false);
				$("#updateAtoBButton").linkbutton("enable");
			}
		},
		error:function(){
			$.messager.alert("提示","请联系网络管理员");
			$("#updateAtoBButton").attr("disabled",false);
			$("#updateAtoBButton").linkbutton("enable");
		}
	});
}