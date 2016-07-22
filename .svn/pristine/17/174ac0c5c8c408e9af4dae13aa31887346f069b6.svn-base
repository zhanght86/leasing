var table1;
$(function(){
	table1 = $('#table1');
	table1.datagrid({
		url : 'BCMMg!doSelectBCMDeductPageData.action',
		columns : [[
			{field:'ID',checkbox:true,width:100},
          	{field:'ID_CARD_NO',title:'客户身份证号',width:25},
          	{field:'ACCOUNT_NAME',title:'开户名',width:35},
          	{field:'BANK_NAME',title:'开户银行',width:35},
          	{field:'BANK_ACCOUNT',title:'客户账号',width:35},
          	{field:'BEGINNING_MONEY',title:'金额',width:15},
          	{field:'PRO_CODE',title:'项目编号',width:35},
            {field:'PAYLIST_CODE',title:'还款计划',width:35}, 
          	{field:'BEGINNING_NUM',title:'期次',width:25},
          	{field:'BEGINNING_RECEIVE_DATA',title:'计划收取日期',width:35},
          	{field:'BEGINNING_NAME',title:'类别',width:35},
          	{field:'BEGINNING_FLAG',title:'扣款状态',width:35},
          	{field:'SIGN_NAME',title:'是否签约',width:35},
          	{field:'ITEM_FLAG',hidden:true},
          	{field:'CUSTNAME',hidden:true},
          	{field:'CUST_ID',hidden:true},
          	{field:'CUST_NAME',hidden:true}
		]],
		toolbar : '#toolbar',//工具条
		pagination : true ,//分页
//		idField : 'ID',
		fitColumns : true,
		autoRowHeight : true,
//		singleSelect : true,
//		checkOnSelect : true,
		nowrap : true,
//		rownumbers : true,
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

//扣划
function deduct(flag){
	//url
	var url = "BCMMg!doUpdateDeduct.action";
	
	//data
	var datagridList=table1.datagrid('getChecked');
	var sqlData = [];	
	
	for(var i = 0; i < datagridList.length; i++)
	{
		sqlData.push("'"+datagridList[i].ID+"'");
	}
	
	//params
	var searchParams = getFromData('#toolbar');
	if(flag == 'all')
	{
		url += '?exportAll=true';
		if(table1.datagrid('getRows').length <= 0){
			$.messager.alert('提示','无数据请勿操作','info',null);
			return;
		}
	}else
	{
		url += "?exportAll=false";
		if(datagridList.length == 0){
			alert("请选择要导出的数据");
			return;
		}
	}
	//submit
	alert(url);
	$('#form01').form('submit',{
      url:url,
      onSubmit: function(){
			//查询参数
//			if($('#searchParams').length<=0){
//				$('#form01').append('<input name=\"searchParams\" id=\"searchParams\" type=\"hidden\" />');
//			}
//			$('#searchParams').val(searchParams);
			//导出标识
			if($('#sqlData').length<=0){
				$('#form01').append('<input name=\"sqlData\"  id=\"sqlData\" type=\"hidden\" />');
			}
			$('#sqlData').val(sqlData.join(','));
      },
      success : function(){
//      	alert("ss");
      	se();
      }
  });
	//remove
	$('#sqlData').remove();
//	$('#searchParams').remove();
}
