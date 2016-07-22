$(document).ready(function(){

	$("#cyberBankPageTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		toolbar:'#pageForm',
		url:'rentWrite!cyberConfirm_PageAjax.action',
		columns:[[
		          	{field:'ID',checkbox:true,width:50},
		          	{field:'ID_CARD_NO',title:'客户身份证号',width:150},
		          	{field:'ACCOUNT_NAME',title:'开户名',width:100},
		          	{field:'BANK_NAME',title:'开户银行',width:100},
		          	{field:'BANK_ACCOUNT',title:'客户账号',width:100},
		          	{field:'BEGINNING_MONEY',title:'金额',width:100},
		          	{field:'PRO_CODE',title:'项目编号',width:100,formatter:function(value,rowData,rowIndex){
	                	  return "<a href='#'>"+rowData.PRO_CODE+"</a>";
	                  }},
	                  {field:'PAYLIST_CODE',title:'还款计划',width:100}, 
		          	{field:'BEGINNING_NUM',title:'期次',width:50},
		          	{field:'BEGINNING_NAME',title:'类别',width:70},
		          	{field:'BEGINNING_STATUS',title:'状态',width:100},
		          	{field:'BEGINNING_FALSE_REASON',title:'失败原因',width:170},
		          	{field:'ITEM_FLAG',hidden:true},
		          	{field:'CUSTNAME',hidden:true},
		          	{field:'CUST_ID',hidden:true},
		          	{field:'CUST_NAME',hidden:true}
		         ]]
	});
});

function resultData(){
	top.addTab("回执查询结果",_basePath+"/rentWrite/rentWrite!cyberBank_Result_Manger.action?FILE_STATUS=0");
}
