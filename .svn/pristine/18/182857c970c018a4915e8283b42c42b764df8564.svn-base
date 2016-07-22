$(document).ready(function(){
	$("#insDialog").datagrid({
		url:_basePath+"/managereport/ManageReport!toMgManageReport.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:true,
		pageSize:20,
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		columns:[[
		          {field:'CUST_ID',width:120,align:'center',title:'区域'}, 
		          {field:'CLIENT_NAME',width:120,align:'center',title:'类别'},
				  {field:'ZX_ACCESS_TYPE',width:120,align:'center',title:'产品名称'},
				  {field:'SPEC_NAME',width:120,align:'center',title:'合同数'}, 
				  {field:'hh',width:120,align:'center',title:'累计融资额'},
				  {field:'UNIT_PRICE',width:120,align:'center',title:'融资余额'},
				  {field:'START_PERCENT',width:120,align:'center',title:'内部收益率'},
				  {field:'LEASE_TERM',width:120,align:'center',title:'利润'},
				  {field:'ITEM_MONEY',width:120,align:'center',title:'坏账损失金额'},
				  {field:'ZX_VISIT_ADDR',width:120,align:'center',title:'坏账损失比例'}
		         ]]	
	});
	
	//页面清空按钮操作
	$("#qingkong").click(function(){
		$(".paramData").each(function(){
			$(this).val("");
		});
	});
});

function tofindData(){
	var searchParams = getFromData('#pageForm');
	$('#insDialog').datagrid('load',{"searchParams":searchParams});
}