$(function(){
	$("#table1").datagrid({
		url : 'LeasePay!getApplyDate.action',
		columns : [[
			{field:'ID',checkbox:true,width:100},
			{field:'SUP_SHORTNAME',title:'供应商',width:100},
			{field:'COMPANY_SHORTNAME',title:'厂商',width:100},
			{field:'PAYMENT_ID',title:'付款单号',width:100},
			{field:'PLAN_MONEY',title:'放款金额',width:100},
			{field:'PAY_DATE',title:'放款日期',width:100},
			{field:'PROJ_NUMBER',title:'项目数量',width:100},
			{field:'PAYEE',title:'收款单位',width:100},
			{field:'PAYEE_BANK',title:'收款银行',width:100},
			{field:'PAYEE_ACCOUNT',title:'收款账号',width:100},
			{field:'PAYEE_LOCATION',title:'收款账号所在地',width:100},
			{field:'FLOW_STATUS',title:'状态',width:100,
				formatter: function(value,row,index){
				   if (value==0){
					    return "已申请";
				   } else if(value==1) {
					    return "已提交";
				   }else if(value==2) {
					    return "已结束";
				   }else if(value==3) {
					    return "已驳回";
				   }else {
					   return "状态错误";
				   }
			    }
			},
			{field:'FLAG',title:'放款标识',width:100},
			{field:'PAY_STATUS',title:'放款类型',width:100}
			
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
	var datagridList=$('#table1').datagrid('getChecked');
	var sqlData = [];	
	
	for(var i = 0; i < datagridList.length; i++)
	{
		sqlData.push("'"+datagridList[i].ID+"'");
	}
	
	//params
	var searchParams = getFromData('#toolbar');
	
	//url
	var url = "LeaseVerify!exportExcel.action";
	if(flag == 'all')
	{
		url += "?exportAll=true";
	}else
	{
		url += "?exportAll=false";
		if(datagridList.length == 0){
			alert("请选择要导出的数据");
			return;
		}
	}
	//submit
	$('#form01').form('submit',{
        url:url,
        onSubmit: function(){
			//查询参数
			if($('#searchParams').length<=0){
				$('#form01').append('<input name=\"searchParams\" id=\"searchParams\" type=\"hidden\" />');
			}
			$('#searchParams').val(searchParams);
			//导出标识
			if($('#sqlData').length<=0){
				$('#form01').append('<input name=\"sqlData\"  id=\"sqlData\" type=\"hidden\" />');
			}
			$('#sqlData').val(sqlData.join(','));
        }
    });
	//remove
	$('#sqlData').remove();
	$('#searchParams').remove();
}
//清空
function clearQuery(form01){
	$('#form01').form('clear');
	//$('#toolbar input').not(':checkbox').val('');
    //$('#toolbar select').val('');
}

