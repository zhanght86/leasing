$(document).ready(function(){

	var FILE_STATUS=$("input[name='FILE_STATUS']").val();
	$("#resultMangerTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		toolbar:'#pageForm',
		url:"rentWrite!cyberBank_Result_PageAjax.action",
		queryParams:{"FILE_STATUS":FILE_STATUS},
		columns:[[
		          	{field:'FILE_NAME',title:'上传文件名称',width:200},
		          	{field:'CREATE_NAME',title:'上传操作人',width:150},
		          	{field:'CREATE_TIME',title:'上传时间',width:150},
		          	{field:'FUND_DATE',title:'核销时间',width:150},
		          	{field:'SUCCESS_NUM',title:'成功条数',width:100},
		          	{field:'ERROR_BANK_NUM',title:'失败条数',width:100}, 
		          	{field:'ERROR_NUM',title:'异常条数',width:100}
		         ]]
	});
});


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


function seach(){
	var FILE_NAME=$("input[name='FILE_NAME']").val();
	var CREATE_TIME1=$("input[name='CREATE_TIME1']").val();
	var CREATE_TIME2=$("input[name='CREATE_TIME2']").val();
	var FUND_DATE1=$("input[name='FUND_DATE1']").val();
	var FUND_DATE2=$("input[name='FUND_DATE2']").val();
	var FILE_STATUS=$("input[name='FILE_STATUS']").val();
	$('#resultMangerTable').datagrid('load', {"FILE_NAME":FILE_NAME,"CREATE_TIME1":CREATE_TIME1,"CREATE_TIME2":CREATE_TIME2,"FUND_DATE1":FUND_DATE1,"FUND_DATE2":FUND_DATE2,"FILE_STATUS":FILE_STATUS});
}


