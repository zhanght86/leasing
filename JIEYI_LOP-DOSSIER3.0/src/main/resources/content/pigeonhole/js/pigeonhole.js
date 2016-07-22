$(function(){
	$("#checkAll").click(function(){
		var flag=$("#checkAll").attr("checked");
		if(flag=="checked"){
			$(".checkId").each(function(){
				$(this).attr("checked","checked");
			});
		}else{
			$(".checkId").each(function(){
				$(this).attr("checked",false);
			});
		}
	});
	$("#dossierApplyBtn").click(function(){
		if($("#dossierApplyBtn").attr("disabled")!="disabled"){
			dossierApply();
		}
	});
});
function chooseCabine(){
	var PORTFOLIO_HEAD=$("[name=PORTFOLIO_HEAD]").val();
	var hrows=$("input[name=PORTFOLIO_ROW]").val();
	var hline=$("input[name=PORTFOLIO_LINE]").val();
	$.ajax({
		type:"post",
		url:_basePath+"/pigeonhole/Pigeonhole!doShowCabRowLine.action",
		data:"PORTFOLIO_HEAD_1="+PORTFOLIO_HEAD,
		dataType:"json",
		success:function(json){
			if(json.flag){
				 var rows=json.data.ROW_NUM;
				 var lines=json.data.LINENUM;
				 var PORTFOLIO_ROW=$("#PORTFOLIO_ROW");
				 var PORTFOLIO_LINE=$("#PORTFOLIO_LINE");
				 PORTFOLIO_ROW.empty();
				 PORTFOLIO_LINE.empty();
				 for(var i=1;i<=rows;i++){
					var op1=$("<option>")
					op1.attr("value","00"+i);
					if(hrows=="00"+i){
						op1.attr("selected",true);
					}
					op1.text("00"+i);
					PORTFOLIO_ROW.append(op1);
				 }
				 for(var i=1;i<=lines;i++){
					var op1=$("<option>");
					op1.attr("value","00"+i);
					if(hline=="00"+i){
						op1.attr("selected",true);
					}
					op1.text("00"+i);
					PORTFOLIO_LINE.append(op1);					 
				 }
				 
			}else{
				$.messager.alert("提示","获取档案柜行列数据失败");
			}
		}
	});
}

function saveDossier(){
	if($("#save_button").attr("disabled")=="disabled"){
		return ;
	}
	var head=$("#PORTFOLIO_HEAD").val();
	var row=$("#PORTFOLIO_ROW").val();
	var line=$("#PORTFOLIO_LINE").val();
	if(head==""){
		$.messager.alert("提示","请选择档案柜编号");
		return;
	}
	var flag=false;
	var saveRecord=new Array();
	var i=1;
	$(".checkId").each(function(){
		if($(this).attr("checked")=="checked"){
			flag=true;
			var temp={};
			var tr=$(this).parent("td").parent(".detailTr");
			temp.JBPM_ID=$("#JBPM_ID").val();
			temp.FILE_REMARK=$("input[name=FILE_REMARK]").val();
			temp.CLIENT_ID=$("#CLIENT_ID_DOSSIER").val();
			temp.CLIENT_NAME=$("#DOSSIER_CLIENT_NAME").val();
			temp.PROJECT_CODE=$("input[name=PROJECT_CODE]").val();
			temp.PLATFORM_TYPE=$("input[name=PLATFORM_TYPE]").val();
			temp.TPM_BUSINESS_PLATE=$(tr).find("input[name=TPM_BUSINESS_PLATE]").val();
			temp.PORTFOLIO_NUMBER=$(tr).find("input[name=PORTFOLIO_NUMBER]").val();
			temp.CABINET_NUMBER=head+"-"+row+"-"+line;
			temp.PAYLIST_CODE=$("input[name=PAYLIST_CODE]").val();
			temp.FILE_NAME=$(tr).find("input[name=TPM_TYPE]").val();
			temp.FILE_TYPE=$("#FILE_TYPE").val();
//			if(temp.FILE_TYPE==null||temp.FILE_TYPE=='null'||temp.FILE_TYPE==''||temp.FILE_TYPE=='undefined'||temp.FILE_TYPE==undefined){
//				temp.FILE_TYPE=$("input[name=FILE_TYPE]").val();
//			}
			temp.DOSSIER_APPLY_ID=$("input[name=DOSSIER_APPLY_ID]").val();
//			temp.DOSSIER_CODE=$(tr).find("input[name=TPM_CODE]").val();
			temp.DOSSIER_COUNT=$(tr).find("input[name=DOSSIER_COUNT]").val();
			temp.DOSSIER_PAGE=$(tr).find("input[name=DOSSIER_PAGE]").val();
			temp.DOSSIER_TEMP=$(tr).find("input[name=DOSSIERTYPE"+i+"]").val();
			temp.STATUS="0";
			saveRecord.push(temp);
		}
		i++;
	});
	if(flag==false){
		$.messager.alert("提示","请选择需要入柜的文件");
		return;
	}
	$.ajax({
		type:"post",
		url:_basePath+"/pigeonhole/Pigeonhole!doAddDossierStorage.action",
		data:"param="+JSON.stringify(saveRecord),
		dataType:"json",
		success:function(json){
			if(json.flag){
				$("#save_button").attr("disabled","disabled");
				$("#save_button").linkbutton("disable");
				$.messager.alert("提示","档案入柜成功");
			}else{
				$.messager.alert("提示","档案入柜失败"+json.msg);
			}
		}
	});
}

function dossierApply(){
	var flag=false;
	var saveRecord=new Array();
	var DOSSIER_TYPE=$("#DOSSIER_TYPE").val();
	var PAYLIST_CODE;
	var PROJECT_ID;
	var i=1;
	$(".checkId").each(function(){
		if($(this).attr("checked")=="checked"){
			flag=true;
			var temp={};
			var td=$(this).parent("td");
			var tr=$(td).parent("tr");
			temp.TPM_CODE=$(td).find("input[name=TPM_CODE]").val();
			temp.TPM_BUSINESS_PLATE=$(td).find("input[name=TPM_BUSINESS_PLATE]").val();
			temp.TPM_TYPE=$(td).find("input[name=TPM_TYPE]").val();
			temp.TPM_CUSTOMER_TYPE=$(td).find("input[name=TPM_CUSTOMER_TYPE]").val();
			temp.TPM_ID=$(td).find("input[name=TPM_ID]").val();
			temp.PDF_PATH=$(td).find("input[name=PDF_PATH]").val();
			temp.NAME=$(td).find("input[name=NAME]").val();
			temp.FILE_REMARK=$(td).find("input[name=FILE_REMARK]").val();
			if(DOSSIER_TYPE=='合同档案'){
				temp.PROJECT_ID=$(td).find("input[name=PROJECT_ID]").val();
				temp.PAYLIST_CODE=$("input[name=PAYLIST_CODE]").val();
			}else{
				temp.PROJECT_ID='';
//				temp.PAYLIST_CODE=$(td).find("input[name=PAYLIST_CODE]").val();
				temp.PAYLIST_CODE='';
			}
			PROJECT_ID=temp.PROJECT_ID
			PAYLIST_CODE=temp.PAYLIST_CODE;
			temp.DOSSIERTYPE=$(tr).find("input[name=DOSSIERTYPE"+i+"]:checked").val();
			temp.DOSSIER_COUNT=$(tr).find("input[name=DOSSIER_COUNT]").val();
			temp.DOSSIER_PAGE=$(tr).find("input[name=DOSSIER_PAGE]").val();
			saveRecord.push(temp);
		}
		i++;
	});
	if(flag==false){
		$.messager.alert("提示","请选择需要申请的文件");
		return;
	}
	var PROJECT_CODE;
	if(DOSSIER_TYPE=='合同档案'){
		PROJECT_CODE=$("#PROJECT_CODE").val();
	}else{
		PROJECT_CODE="";
	}
	var CLIENT_ID=$("#CLIENT_ID").val();
	var SEND_TYPE=$("#SEND_TYPE").val();
	$.ajax({
		type:"post",
		url:_basePath+"/pigeonhole/Pigeonhole!doAddPigeonholeApply.action",
		data:"PROJECT_CODE="+PROJECT_CODE+"&PROJECT_ID="+PROJECT_ID+"&PAYLIST_CODE="+PAYLIST_CODE+"&CLIENT_ID="+CLIENT_ID+"&SEND_TYPE="+SEND_TYPE+"&FILEINFO="+JSON.stringify(saveRecord),
		dataType:"json",
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","申请成功！");
				$("#dossierApplyBtn").linkbutton("disable");
				$("#dossierApplyBtn").attr("disabled","disabled");
			}else{
				$.messager.alert("提示","申请失败"+json.msg);
			}
		},
		error:function(){
			$.messager.alert("提示","网络原因，请联系管理员");
		}
	});
}



function saveCheckedContract(){
	var flag=false;
	var saveRecord=new Array();
	var PAYLIST_CODE="";
	var i=1;
	$(".checkId").each(function(){
		if($(this).attr("checked")=="checked"){
			flag=true;
			var temp={};
			var td=$(this).parent("td");
			var tr=$(td).parent("tr");
			temp.TPM_CODE=$(td).find("input[name=FILE_CODE]").val();
			temp.TPM_TYPE=$(td).find("input[name=TPM_TYPE]").val();
			temp.TPM_CUSTOMER_TYPE=$(td).find("input[name=TPM_CUSTOMER_TYPE]").val();
			temp.PROJECT_ID=$(td).find("input[name=PROJECT_ID]").val();
			temp.FILE_REMARK=$(td).find("input[name=FILE_REMARK]").val();
			temp.PAYLIST_CODE=$(td).find("input[name=PAYLIST_CODE]").val();
			temp.DOSSIERTYPE=$(tr).find("input[name=DOSSIERTYPE"+i+"]:checked").val();
			temp.DOSSIER_PAGE=$(tr).find("input[name=DOSSIER_PAGE]").val();
			temp.DOSSIER_COUNT=$(tr).find("input[name=DOSSIER_COUNT]").val();
			temp.FILE_TYPE=$(td).find("input[name=FILE_TYPE]").val();
			temp.CLIENT_ID=$(td).find("input[name=CLIENT_ID]").val();
			temp.TPM_BUSINESS_PLATE=$(td).find("input[name=TPM_BUSINESS_PLATE]").val();
			PAYLIST_CODE=$(td).find("input[name=PAYLIST_CODE]").val();
			saveRecord.push(temp);
		}
		i++;
	});

	var APPLY_ID=$("#APPLY_ID").val();
	
	$.ajax({
		type:"post",
		url:_basePath+"/pigeonhole/Pigeonhole!doSaveCheckedContractData.action",
		data:"APPLY_ID="+APPLY_ID+"&PAYLIST_CODE="+PAYLIST_CODE+"&FILEINFO="+encodeURI(JSON.stringify(saveRecord)),
		dataType:"json",
		success:function(json){
			$("#save_button").linkbutton("disable");
			$("#save_button").attr("disabled","disabled");
			$.messager.alert("提示",json.msg);
		},
		error:function(){
			$.messager.alert("提示","网络原因，请联系管理员");
		}
	});
}


