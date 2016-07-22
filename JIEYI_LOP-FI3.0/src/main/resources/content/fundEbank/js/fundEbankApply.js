$(function(){
	$("#table1").datagrid({
		url : 'FundEbank!refreshAccounts.action',
		columns : [[
			{field:'ID',checkbox:true,width:100},
			{field:'CUST_NAME',title:'客户名称',width:100},
			{field:'ID_CARD_NO',title:'身份证号',width:100},
			{field:'BANK_CUSTNAME',title:'开户名',width:100},
			{field:'BANK_NAME',title:'开户银行',width:100},
			{field:'BANK_ACCOUNT',title:'客户帐号',width:100},
			{field:'DLD',title:'供应商',width:100},
			{field:'FIRST_MONEY',title:'首付款',width:100},
			{field:'OTHER_MONEY',title:'其他费用',width:100},
			{field:'MONEY',title:'应收金额',width:100},
			{field:'PROJ_ID',title:'备注(项目编号)',width:100},
			{field:'PAYMENT_STATUS',title:'放款方式',width:100}
//			{field:'CREATE_DATE',title:'立项日期',width:100}
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
	
	//选择银行模版刷新列表数据
    $('#bankFlag').combobox({  
        editable:false,  
        onSelect:function(record){
    		if(record.text == '民生银行'){
        		$('#bank_name').combobox('select','');
    		}else{
    			$('#bank_name').combobox('select',record.text);
    		}
	    	var searchParams = getFromData('#toolbar');
	    	$('#table1').datagrid('load',{"searchParams":searchParams});
        }  
    });  
   
});
//查询
function se(){
	var searchParams = getFromData('#toolbar');
	$('#table1').datagrid('load',{"searchParams":searchParams});
}

//是否全选
function change(obj){
	$(obj).is(":checked") ? $('#table1').datagrid('selectAll') : $('#table1').datagrid('unselectAll') ;
}

//导出
function exportExcel(flag){
	
	//data
	var datagridList = $('#table1').datagrid('getChecked');
	var sqlData = [];	
	for(var i = 0; i < datagridList.length; i++)
	{
		sqlData.push("'"+datagridList[i].ID+"'");
	}
	for(var i = datagridList.length-1; i >= 0; i--)
	{
		var index = $('#table1').datagrid('getRowIndex',datagridList[i]);//获取某行的行号
		$('#table1').datagrid('deleteRow',index);	//通过行号移除该行
	}
	
	//params
	var searchParams = getFromData('#toolbar');
	
	//url
	var url = "FundEbank!exportExcel.action";
	if(flag == 'all')
	{
		url += "?exportAll=true";
	}else
	{
		url += "?exportAll=false";
		if(sqlData.length == 0){
			$.messager.alert("提示","请选择要导出的数据");
			return;
		}
	}
	//submit
	$('#form01').form('submit',{
        url:url,
        
        onSubmit: function(){

			//导出标识
			if($('#sqlData').length<=0){
				$('#form01').append('<input name=\"sqlData\"  id=\"sqlData\" type=\"hidden\" />');
			}
			$('#sqlData').val(sqlData.join(','));
        }
    });

	$('#sqlData').remove();
}
//清空
function clearQuery(form01){
	$('#form01').form('clear');
	//$('#toolbar input').not(':checkbox').val('');
    //$('#toolbar select').val('');
}

