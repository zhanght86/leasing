$(document).ready(function(){
	$('#uploadDialog').dialog({closed : true});
	var ID=$("#ID").val();
	$("#ERROR_PAGETABLE").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		toolbar:'#pageForm',
		url:'rentWriteNew!ERROR_APP_PageAjax.action',
		queryParams:{"ID":ID},
		columns:[[
		          	{field:'ID',title:'标示',width:150},
		          	{field:'ID_CARD_NO',title:'客户身份证号',width:150},
		          	{field:'ACCOUNT_NAME',title:'开户名',width:100},
		          	{field:'BANK_NAME',title:'开户银行',width:100},
		          	{field:'BANK_ACCOUNT',title:'客户账号',width:100},
		          	{field:'BEGINNING_MONEY',title:'金额',width:100},
		          	{field:'PRO_CODE',title:'项目编号',width:100},
	                {field:'PAYLIST_CODE',title:'还款计划',width:150}, 
		          	{field:'BEGINNING_NUM',title:'期次',width:50},
		          	{field:'BEGINNING_RECEIVE_DATA',title:'计划收取日',width:150},
		          	{field:'BEGINNING_NAME',title:'类别',width:70},
		          	{field:'BEGINNING_STATUS',title:'状态',width:100},
		          	{field:'BEGINNING_FALSE_REASON',title:'失败原因',width:170}
		         ]]
	});
});

function emptyData(){
	$('#pageForm').form('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}