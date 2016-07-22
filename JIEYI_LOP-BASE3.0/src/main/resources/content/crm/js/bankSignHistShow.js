$(function(){
	$("#table1").datagrid({
		url : 'BankSignMg!doSelectBankSignHistPageData.action',
		columns : [[
//			{field:'ID',checkbox:true,width:100},
			{field:'SUP_SHORTNAME',title:'经销商',width:100,align:'center'},
			{field:'PRO_CODE',title:'项目编号',width:100,align:'center'},
			{field:'CUST_TYPE',title:'客户类型',width:100,align:'center'},
			{field:'CUST_NAME',title:'客户姓名',width:100,align:'center'},
			{field:'BANK_CUSTNAME',title:'持卡人',width:100,align:'center'},
			{field:'BANK_NAME',title:'开户银行',width:100,align:'center'},
			{field:'BANK_ACCOUNT',title:'银行卡号',width:100,align:'center'},
			{field:'ID_CARD_NO',title:'身份证号',width:100,align:'center'},
			{field:'CREATE_TIME',title:'立项日期',width:100,align:'center'},
			{field:'FLAG',title:'状态',width:100,align:'center'}
		]],
		toolbar : '#toolbar',//工具条
		pagination : true ,//分页
		idField : 'BANK_ID',
		fitColumns : true,
		autoRowHeight : true,
		//singleSelect : true,
		checkOnSelect : true,
		nowrap : true,
		rownumbers : true,
		
		pageSize : 20,
		pageList : [10,20,50,100,200,300]
	});
	
	$('#form01').form({
        url:""
    });
    $('#fileDialog').dialog({closed : true});
});
//查询
function se(){
	var searchParams = getFromData('#toolbar');
	$('#table1').datagrid('load',{"searchParams":searchParams});
}


//清空
function clearQuery(form01){
	$('#form01').form('clear');
	//$('#toolbar input').not(':checkbox').val('');
    //$('#toolbar select').val('');
}

