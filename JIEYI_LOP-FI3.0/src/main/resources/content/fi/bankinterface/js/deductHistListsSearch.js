$(function(){
	table1 = $('#table1');
	table1.datagrid({
		url : 'Bank!doSelectDeductListsPageData.action',
		columns : [[
			{field:'SEND_FLAG',title:'上传发送标志',width:100},
			{field:'RESPONSE_FLAG',title:'上传响应标志',width:100},
			{field:'SUCCESS_FLAG',title:'上传处理成功标志',width:150},
			{field:'DEDUCT_SEND_FLAG',title:'批扣发送标志',width:100},
			{field:'DEDUCT_RESPONSE_FLAG',title:'批扣响应标志',width:100},
			{field:'DEDUCT_SUCCESS_FLAG',title:'批扣处理成功标志',width:100},
			{field:'SEARCH_FLAG',title:'扣划结果查询次数',width:100},
			{field:'SEARCH_SUCCESS_FLAG',title:'扣划结果查询成功标志',width:100},
			{field:'SUM_NUM',title:'总笔数',width:80},
			{field:'SUM_AMT',title:'总金额',width:100},
			{field:'SUCCESS_NUM_SEARCH',title:'成功总笔数',width:100},
			{field:'SUCCESS_AMOUNT_SEARCH',title:'成功总金额',width:100},
			{field:'FAIL_NUM',title:'失败总笔数',width:100},
			{field:'FAIL_AMOUNT',title:'失败总金额',width:100},
			{field:'F_MSG',title:'处理状态',width:100},
			{field:'SEARCH_DESCRIPT',title:'查询结果',width:100},
			{field:'CREATE_DATE',title:'创建日期',width:100},
			{field:'FILE_NAME',title:'文件名',width:100}
		]],
		frozenColumns : [[
            {field:'DEDUCT_REQUEST_SN',title:'批扣单号',width:100},
  			{field:'BANK_FLAG',title:'扣划银行',width:100}
		]],
		toolbar : '#toolbar',//工具条
		pagination : true ,//分页
		idField : 'DEDUCT_REQUEST_SN',
//		fitColumns : true,
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
