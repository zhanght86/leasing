$(function(){
	$("#addConfigDialog").dialog("close");
	$("#updateConfigDialog").dialog("close");
});
function compure(value, rowData, rowIndex) {
	return "<a href='javaScript:void(0)' onClick=openEditConfig("+JSON.stringify(rowData)+")>修改</a>| <a href='javascript:void(0)' onclick=delConfig('"+rowData.ID+"')>删除</a>";
}

function clean(){
	$("#industry_type").combobox('setValue','');
	$("#PORTFOLIO_HEAD").val('');
}

function se(){
	$("#pageTable").datagrid('load',{"PORTFOLIO_HEAD":$("input[name=PORTFOLIO_HEAD]").val(),"INDUSTRY_TYPE":$("#industry_type").combobox('getValue')});
}

function openAddConfig(){
	$("#addConfigDialog").dialog("open");
	$("#addConfigForm").form('clear');
}

function addConfig(){
	if(!$("#addConfigForm").form('validate')){
		return false;
	}
	var INDUSTRY_TYPE=$("#addConfigForm").find("[name=INDUSTRY_TYPE]").val();
	var PORTFOLIO_HEAD=$("#addConfigForm").find("[name=PORTFOLIO_HEAD]").val();
	var ROW_NUM=$("#addConfigForm").find("[name=ROW_NUM]").val();
	var LINENUM=$("#addConfigForm").find("[name=LINENUM]").val();
	$.ajax({
		type:"post",
		url:_basePath+"/dossier/DossierConfig!doAddDossierConfig.action",
		data:"INDUSTRY_TYPE="+INDUSTRY_TYPE+"&PORTFOLIO_HEAD="+PORTFOLIO_HEAD+"&ROW_NUM="+ROW_NUM+"&LINENUM="+LINENUM,
		dataType:"json",
		success:function(json){
			if(json.flag){
				$("#addConfigDialog").dialog("close");
				$("#pageTable").datagrid('load');
			}else{
				$.messager.alert("错误",json.msg);
			}
		}
	});
}

function closeAddConfig(){
	$("#addConfigDialog").dialog("close");
}

function openEditConfig(row){
	$("#updateConfigDialog").dialog("open");
	$("#updateConfigForm").form('load',row);
}

function updateConfig(){
	if(!$("#updateConfigForm").form('validate')){
		return false;
	}
	var INDUSTRY_TYPE=$("#updateConfigForm").find("[name=INDUSTRY_TYPE]").val();
	var PORTFOLIO_HEAD=$("#updateConfigForm").find("[name=PORTFOLIO_HEAD]").val();
	var ROW_NUM=$("#updateConfigForm").find("[name=ROW_NUM]").val();
	var LINENUM=$("#updateConfigForm").find("[name=LINENUM]").val();
	var ID=$("#updateConfigForm").find("[name=ID]").val();
	$.ajax({
		type:"post",
		url:_basePath+"/dossier/DossierConfig!doUpdateDossierConfig.action",
		data:"INDUSTRY_TYPE="+INDUSTRY_TYPE+"&PORTFOLIO_HEAD="+PORTFOLIO_HEAD+"&ROW_NUM="+ROW_NUM+"&LINENUM="+LINENUM+"&ID="+ID,
		dataType:"json",
		success:function(json){
			if(json.flag){
				$("#updateConfigDialog").dialog("close");
				$("#pageTable").datagrid('load');
			}else{
				$.messager.alert("错误",json.msg);
			}
		}
	});
}

function closeupdateConfig(){
	$("#updateConfigDialog").dialog("close");
}

function delConfig(ID){
	$.messager.confirm("提示","您确定要删除该条数据吗？",function(flag){
		if(flag){
			$.ajax({
				type:"post",
				url:_basePath+"/dossier/DossierConfig!doDelDossierConfig.action",
				data:"ID="+ID,
				dataType:"json",
				success:function(json){
				if(json.flag){
					$("#pageTable").datagrid('load');
				}else{
					$.messager.alert("错误",json.msg);
				}
			}
			});
		}
	});
}