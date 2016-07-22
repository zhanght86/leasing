/**
 * 项目作废
 */

function setOperation(value,rowData,rowIndex){
	return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='startFlow(" + JSON.stringify(rowData) + ")'>项目作废申请</a>&nbsp;";
}


//申请  齐仔的方法
function create_FORM(row){
	var PROJECT_ID=row.PROJECT_ID;//项目ID
	var PROJECT_CODE=row.PROJ_ID;//项目编号
	var STATUS = 1;//直觉告诉我这里应该是1
	var CUST_NAME=row.KHMC;//客户名称
	var SUPPLIER_NAMES=row.DLD;//供应商名称
	var COMPANY_NAMES=row.ZZS;//厂商名称
	var CLIENT_ID=row.CLIENT_ID;//客户ID
	var SUP_ID=row.SUP_ID;
	var PLAN_STATUS=row.PLAN_STATUS;
	 top.addTab(PROJECT_CODE+"作废申请",_basePath+"/project_withdrawn/project_withdrawn!create_FORM.action?PROJECT_ID="+PROJECT_ID+"&SUP_ID="+encodeURI(SUP_ID)+"&PROJECT_CODE="+encodeURI(PROJECT_CODE)+"&STATUS="+STATUS+"&CUST_NAME="+encodeURI(CUST_NAME)+"&SUPPLIER_NAMES="+encodeURI(SUPPLIER_NAMES)+"&COMPANY_NAMES="+encodeURI(COMPANY_NAMES)+"&CLIENT_ID="+CLIENT_ID+"&PLAN_STATUS="+PLAN_STATUS);
}

//发起流程
function startFlow(row){
	$.ajax({
		type:"post",
		url:_basePath+"/projectInvalid/ProjectInvalid!projectInvalidJBPM.action",
		data:"PROJECT_ID="+row.PROJECT_ID + "&CLIENT_ID=" + row.CLIENT_ID,
		dataType:"json",
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","申请成功！",'info',function(){
						$.messager.alert("提示",json.msg+json.data,"info",function(){
							$('#dg').datagrid('reload');//刷新页面
						});
				});
			
			}else{
				$.messager.alert("提示","申请失败"+json.msg);
				$('#dg').datagrid('reload');//刷新页面
			}
		},
		error:function(){
			$.messager.alert("提示","网络原因，请联系管理员");
		}
	});
}

/**
 * 清空按钮，清空日期及可填写字段
 */
function emptyData(){
	//清空日期
	$("#QZ_DATE_BEGIN").datebox('clear');
	$("#QZ_DATE_END").datebox('clear');
	//清空input
	$(".paramData").each(function(){
		$(this).val("");
	});
}
/**
 * 查询
 */
function dosearch(){
	var PROJ_ID = $("#PROJ_ID").val();
	var DLD = $("#DLD").val();
	var ZZS = $("#ZZS").val();
	var KHMC = $("#KHMC").val();
	var ZLWLX = $("#ZLWLX").val();
	var QZ_DATE_BEGIN = $("#QZ_DATE_BEGIN").datebox("getValue");
	var QZ_DATE_END = $("#QZ_DATE_END").datebox("getValue");
	
	$('#dg').datagrid('load', {"PROJ_ID":PROJ_ID,"DLD":DLD,"ZZS":ZZS,"KHMC":KHMC,"ZLWLX":ZLWLX,"QZ_DATE_BEGIN":QZ_DATE_BEGIN,"QZ_DATE_END":QZ_DATE_END});
}