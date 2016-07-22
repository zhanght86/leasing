jQuery(function(){
	jQuery("#fiForRedWebAppDiv").dialog("close");
});

function setTool(value,rowData,rowIndex){
	return "<a href='javascript:void(0);' onclick='delRedForm("+rowData.ID+")'>删除</a>";
}

function setFile(val,rowData,rowIndex){
	var fil = rowData.FILE_PATH;
	 var cc = "";
	 if (fil != null && fil != "") {
		 var aa=fil.split("\\");
		 if(aa.length > 0){
			 cc=aa[aa.length-1];
		 }
     }
	return "<a href='FiForRed!doDownFile.action?FILE_PATH="+rowData.FILE_PATH+"&FILE_NAME="+cc+"' >"+cc+"</a>";;
}

function openAppForm(){
	$("#fiForRedWebAppDiv").dialog("open");
}

function FiForRedWebApp(){
	$("#FiForRedWebApp").linkbutton("disable");
	var APPLY_NAME=$("#APP_NAME").val();
	var BANK_NAME=$("select[name='BANK_NAME']").val();
	var APPLY_DATE=$("input[name='APPLY_DATE']").val();
	var REMARK=$("#REMARK").val();
	$.ajaxFileUpload( {
		type : "post",
		url : _basePath
				+ "/fiForRed/FiForRed!doAddFiForRedWebApp.action?APPLY_NAME="
				+ encodeURI(APPLY_NAME) + "&BANK_NAME=" + encodeURI(BANK_NAME)
				+ "&APPLY_DATE=" + encodeURI(APPLY_DATE) + "&REMARK="
				+ encodeURI(REMARK),
		secureuri : false,
		fileElementId : "FILE_NAME",
		dataType : "json",
		success : function(json, status) {
			if (status) {
				$('#fiForRedWebAppDiv').dialog('close');
				seFiForRedTableWY();
				$("#FiForRedWebApp").linkbutton("enable");
			} else {
				$.messager.alert("提示", json.msg);
			}
		}
	});
}

function addFiRedForm(TYPE){
	var rows=$("#fiForRedWebTable").datagrid("getSelections");
	if(rows.length == 0){
		$.messager.alert("提示","请选择要提交的申请！");
		return ;
	}
	var array=new Array();
	
	for(var i=0; i<rows.length; i++){
		var temp={};
		temp.ID=rows[i].ID;
		temp.STATUS='1';
		array.push(temp);
	}
	if(TYPE=='1'){
		$.messager.confirm("提示","您确定要做冲红吗？",function(r){
			if(r){
				$.ajax({
					type:'post',
					url:_basePath+"/fiForRed/FiForRed!doUpdateFiForRedWebAppSubmit.action",
					data:"TYPE="+encodeURI('冲红')+"&param="+JSON.stringify(array),
					dataType:'json',
					success:function(json){
					if(json.flag){
						seFiForRedTableWY();
					}else{
						$.messager.alert("提示",json.msg);
					}
				},
				error:function(){
					$.messager.alert("提示","网络问题，请联系网络管理员");
				}
				});	
			}
		});
	}else{
		$.messager.confirm("提示","您确定要做作废吗？",function(r){
			if(r){
				$.ajax({
					type:'post',
					url:_basePath+"/fiForRed/FiForRed!doUpdateFiForRedWebAppSubmit.action",
					data:"TYPE="+encodeURI('作废')+"&param="+JSON.stringify(array),
					dataType:'json',
					success:function(json){
					if(json.flag){
						seFiForRedTableWY();
					}else{
						$.messager.alert("提示",json.msg);
					}
				},
				error:function(){
					$.messager.alert("提示","网络问题，请联系网络管理员");
				}
				});	
			}
		});
	}
}

function delRedForm(RED_ID){
	$.messager.confirm("提示","确认要删除该批数据申请数据吗？",function(r){
		if(r){
			var array=new Array();
			var temp={};
			temp.ID= RED_ID;
			array.push(temp);
			$.ajax({
				type:'post',
				url:_basePath+"/fiForRed/FiForRed!doDeleteFiForRedWebApp.action",
				data:"param="+JSON.stringify(array),
				dataType:'json',
				success:function(json){
					if(json.flag){
						seFiForRedTableWY();
					}else{
						$.messager.alert("提示",json.msg);
					}
				},
				error:function(){
					$.messager.alert("提示","网络问题，请联系网络管理员");
				}
			});
		}
	});
}

function toRedFormApp(){
	var rows=$("#fiForRedWebConfirmTable").datagrid("getSelections");
	if(rows.length == 0){
		$.messager.alert("提示","请选择要驳回的申请！");
		return ;
	}
	$.messager.confirm("提示","确定要驳回该批数据吗",function(r){
		if(r){
			var rows=$("#fiForRedWebConfirmTable").datagrid("getSelections");
			var array=new Array();
			for(var i=0;i<rows.length;i++){
				var temp={};
				temp.ID=rows[i].ID;
				array.push(temp);
			}
			$.ajax({
				type:'post',
				url:_basePath+"/fiForRed/FiForRed!doUpdateRedWebFormApp.action",
				data:"param="+JSON.stringify(array),
				dataType:'json',
				success:function(json){
					if(json.flag){
						seFiForRedTableConfirmWY();
					}else{
						$.messager.alert("提示",json.msg);
					}
				},
				error:function(){
					$.messager.alert("提示","网络问题，请联系网络管理员");
				}
			});
		}
	});
}

function FiRedCheckPass(){
	$("#divFrom").empty();
	var rows=$("#fiForRedWebConfirmTable").datagrid("getSelections");
	if(rows.length<=0)
	{
		$.messager.alert("提示","请先选择要冲红作废的数据在操作");
		return ;
	}
	var selectData = [];
	for(var i = 0; i < rows.length; i++)
	{
		var temp={};
		temp.FILE_NAME=rows[i].FILE_PATH;
		temp.ID=rows[i].ID;
		temp.TYPE=rows[i].TYPE;
		selectData.push(temp);
	}
	
	var url="FiForRed!doUpdateFiRedCheckPass.action?selectData="+encodeURI(JSON.stringify(selectData));
	
	$("#divFrom").append("<form id='formSubmit' method='post' action='"+url+"'></form>");
	$("#formSubmit").submit();
}

function seFiForRedTableWY(){
	$("#fiForRedWebTable").datagrid('load',{"param":getFromData("#fiForRedWebForm")});
}
function cleanFiForRedTableWY(){
	$("#APPLY_NAME").val('');
	$("#APPLY_DATE_BEGIN").datebox('setValue','');
	$("#APPLY_DATE_END").datebox('setValue','');
	$("#FILE_PATH").val('');
}

function seFiForRedTableConfirmWY(){
	$("#fiForRedWebConfirmTable").datagrid('load',{"param":getFromData("#fiForRedWebForm")});
}
function disableButtom(){
	var val = $("#STATUS").val();
	if(val == 1){
		$("#redUndo").linkbutton("enable");
		$("#redSave").linkbutton("enable");
	}else{
		$("#redUndo").linkbutton("disable");
		$("#redSave").linkbutton("disable");
	}
	seFiForRedTableConfirmWY();
}