$(function(){
	$("#table1").datagrid({
		url : 'FundEbank!refreshSearch.action',
		columns : [[
			{field:'ID',checkbox:true,width:100},
			{field:'DLD',title:'供应商',width:100},
			{field:'PROJ_ID',title:'项目编号',width:100},
			{field:'CUST_NAME',title:'客户姓名',width:100},
			{field:'BANK_CUSTNAME',title:'持卡人',width:100},
			{field:'BANK_NAME',title:'开户银行',width:100},
			{field:'BANK_ACCOUNT',title:'银行卡号',width:100},
			{field:'ID_CARD_NO',title:'身份证号',width:100},
			{field:'CREATE_DATE',title:'立项日期',width:100},
			{field:'FLAG',title:'状态',width:100}
		]],
		toolbar : '#toolbar',//工具条
		pagination : true ,//分页
		idField : 'ID',
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
	$('#fileUploadForm').form({
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

//是否全选
function change(obj){
	$(obj).is(":checked") ? $('#table1').datagrid('selectAll') : $('#table1').datagrid('unselectAll') ;
}

$(function(){
	//页面全选
	$("input[name='ck_all']").click(function(){
		if($(this).attr("checked")){
			$("input[name='inverse_ck_all']").attr("checked",false);
			$("input[name='data_ck_all']").attr("checked",false);
			$('#table1').datagrid('selectAll');
		}else{
			$('#table1').datagrid('unselectAll');
		}
	});
	/*
	//反选功能
	$("input[name='inverse_ck_all']").click(function(){
			$("input[name='ck_all']").attr("checked",false);
			$("input[name='data_ck_all']").attr("checked",false);
			$("input[name='list']").each(function(){
				$(this).attr("checked",!$(this).attr("checked"));
			});
	});
	*/
	//数据全选 -- 页面配合选中
	$("input[name='data_ck_all']").click(function(){
		if($(this).attr("checked")){
			$("input[name='inverse_ck_all']").attr("checked",false);
			$("input[name='ck_all']").attr("checked",false);
			$('#table1').datagrid('selectAll');
		}else{
			$('#table1').datagrid('unselectAll');
		}
	});
});
//数据选中判断
function isNotChecked(val){
	var datagridList=$('#table1').datagrid('getChecked');
	if(datagridList.length == 0){
		alert("请选择要【"+val+"】的数据...");
		return true;
	}
	return false;
}