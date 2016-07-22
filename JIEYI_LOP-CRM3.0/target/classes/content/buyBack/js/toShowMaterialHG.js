$(function(){
	
	$(".checkId").each(function(){
		$(this).attr("checked","checked");
	});
	
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
});

function saveCheckedContractzc(){
	var flag=false;
	var saveRecord=new Array();
	var CLIENT_ID=$("#CLIENT_ID").val();
	var i=1;
	$(".checkId").each(function(){
		if($(this).attr("checked")=="checked"){
			flag=true;
			var temp={};
			var td=$(this).parent("td");
			var tr=$(td).parent("tr");
			temp.TPM_CODE=$(td).find("input[name=FILE_CODE]").val();
//			temp.TPM_BUSINESS_PLATE=$(td).find("input[name=TPM_BUSINESS_PLATE]").val();
			temp.TPM_TYPE=$(td).find("input[name=TPM_TYPE]").val();
			temp.TPM_CUSTOMER_TYPE=$(td).find("input[name=TPM_CUSTOMER_TYPE]").val();
//			temp.TPM_ID=$(td).find("input[name=TPM_ID]").val();
			temp.PDF_PATH=$(td).find("input[name=PDF_PATH]").val();
//			temp.NAME=$(td).find("input[name=NAME]").val();
			temp.PROJECT_ID=$(td).find("input[name=PROJECT_ID]").val();
			temp.FILE_REMARK=$(td).find("input[name=FILE_REMARK]").val();
			temp.PAYLIST_CODE=$(td).find("input[name=PAYLIST_CODE]").val();
			temp.DOSSIERTYPE=$(tr).find("input[name=DOSSIERTYPE"+i+"]:checked").val();
			temp.DOSSIER_PAGE=$(tr).find("input[name=DOSSIER_PAGE]").val();
			temp.DOSSIER_COUNT=$(tr).find("input[name=DOSSIER_COUNT]").val();
			temp.CLIENT_ID=CLIENT_ID;
			saveRecord.push(temp);
		}
		i++;
	});
	if(flag==false){
		$.messager.alert("提示","请选择需要保存的文件");
		return;
	}
	var PROJECT_CODE=$("#PROJECT_CODE").val();
	var SEND_TYPE=$("#SEND_TYPE").val();
	$.ajax({
		type:"post",
		url:_basePath+"/buyBack/BuyBack!doAddCheckedContractzc.action",
		data:"JBPM_ID="+encodeURI($("#JBPM_ID").val())+"&PROJECT_CODE="+PROJECT_CODE+"&CLIENT_ID="+CLIENT_ID+"&SEND_TYPE="+SEND_TYPE+"&FILEINFO="+JSON.stringify(saveRecord),
		dataType:"json",
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","保存成功！");
				$("#save_button").linkbutton("disable");
				$("#save_button").attr("disabled","disabled");
			}else{
				$.messager.alert("提示","保存失败"+json.msg);
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
	var CLIENT_ID=$("#CLIENT_ID").val();
	var i=1;
	$(".checkId").each(function(){
		if($(this).attr("checked")=="checked"){
			flag=true;
			var temp={};
			var td=$(this).parent("td");
			var tr=$(td).parent("tr");
			temp.TPM_CODE=$(td).find("input[name=FILE_CODE]").val();
//			temp.TPM_BUSINESS_PLATE=$(td).find("input[name=TPM_BUSINESS_PLATE]").val();
			temp.TPM_TYPE=$(td).find("input[name=TPM_TYPE]").val();
			temp.TPM_CUSTOMER_TYPE=$(td).find("input[name=TPM_CUSTOMER_TYPE]").val();
//			temp.TPM_ID=$(td).find("input[name=TPM_ID]").val();
			temp.PDF_PATH=$(td).find("input[name=PDF_PATH]").val();
//			temp.NAME=$(td).find("input[name=NAME]").val();
			temp.PROJECT_ID=$(td).find("input[name=PROJECT_ID]").val();
			temp.FILE_REMARK=$(td).find("input[name=FILE_REMARK]").val();
			temp.PAYLIST_CODE=$(td).find("input[name=PAYLIST_CODE]").val();
			temp.DOSSIERTYPE=$(tr).find("input[name=DOSSIERTYPE"+i+"]:checked").val();
			temp.DOSSIER_PAGE=$(tr).find("input[name=DOSSIER_PAGE]").val();
			temp.DOSSIER_COUNT=$(tr).find("input[name=DOSSIER_COUNT]").val();
			temp.CLIENT_ID=CLIENT_ID;
			saveRecord.push(temp);
		}
		i++;
	});
	if(flag==false){
		$.messager.alert("提示","请选择需要保存的文件");
		return;
	}
	var PROJECT_CODE=$("#PROJECT_CODE").val();
	var SEND_TYPE=$("#SEND_TYPE").val();
	$.ajax({
		type:"post",
		url:_basePath+"/buyBack/BuyBack!doAddCheckedContract.action",
		data:"JBPM_ID="+encodeURI($("#JBPM_ID").val())+"&PROJECT_CODE="+PROJECT_CODE+"&CLIENT_ID="+CLIENT_ID+"&SEND_TYPE="+SEND_TYPE+"&FILEINFO="+JSON.stringify(saveRecord),
		dataType:"json",
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","保存成功！");
				$("#save_button").linkbutton("disable");
				$("#save_button").attr("disabled","disabled");
			}else{
				$.messager.alert("提示","保存失败"+json.msg);
			}
		},
		error:function(){
			$.messager.alert("提示","网络原因，请联系管理员");
		}
	});
}