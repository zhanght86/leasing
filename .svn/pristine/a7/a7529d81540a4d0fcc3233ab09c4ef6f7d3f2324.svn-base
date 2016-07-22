$(function(){
	table1 = $('#table1');
	table1.datagrid({
		url : 'Bank!doSelectDeductHistListsPageData.action',
		columns : [[
		    {field:'ID',checkbox:true,width:100},
			{field:'REQUEST_SN',title:'扣划批次',width:100},
			{field:'PAY_ACCNAME',title:'开户名',width:100},
			{field:'BANK_FLAG',title:'开户银行',width:100},
			{field:'PAY_ACCNO',title:'客户帐号',width:150},
			{field:'AMOUNT',title:'金额',width:100},
			{field:'PROJ_ID',title:'项目编号',width:100},
			{field:'RENT_LIST',title:'租金期次',width:100},
			{field:'RENT_TYPE',title:'租金类型',width:100},
			{field:'REMARK',title:'扣划状态',width:100},
			{field:'STATUS',title:'扣划结果',width:80},
			{field:'CREATE_DATE',title:'扣划日期',width:100},
		]],
		toolbar : '#toolbar',//工具条
		pagination : true ,//分页
		idField : 'ID',
		fitColumns : true,
		autoRowHeight : true,
//		singleSelect : true,
//		checkOnSelect : true,
		nowrap : true,
		rownumbers : true,
		pageSize : 20,
		pageList : [10,20,50,100,200,300]
	});
	
});
//查询
function se(){
	var searchParams = getFromData('#toolbar');
	table1.datagrid('load',{"searchParams":searchParams});
}
//清空
function clearQuery(form01){
	$('#form01').form('clear');
	//$('#toolbar input').not(':checkbox').val('');
    //$('#toolbar select').val('');
}
