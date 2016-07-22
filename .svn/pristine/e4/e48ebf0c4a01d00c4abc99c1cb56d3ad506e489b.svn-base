	
var table1;
$(function(){
	table1 = $("#table1");
	table1.datagrid({
		url : 'InvoiceMg!checkInvoiceData.action',
		columns : [[{checkbox:true},
		            {field:'SUP_ID',title:'供应商标识',width:50,align:'left'},
			        {field:'SUP_SHORTNAME',title:'供应商名称',width:50},
			        {field:'PRO_CODE',title:'项目编号',width:50},
			        {field:'INVOICE_FALSE_REASION',title:'营业税票据开具失败原因',width:200}
		            ]],
		toolbar : '#toolbar',//工具条
		idField : 'ID',
		fitColumns : true,
		autoRowHeight : true,
//		singleSelect:true,
//		collapsible:true,
		checkOnSelect : true,
//		nowrap : true,
		rownumbers : true,
	    method: 'get',
		pagination : true ,//分页
		pageSize : 20,
		pageList : [20,50,100,200]
	});
});

function seachInfo(){
	var searchParams = getFromData('#toolbar');
	table1.datagrid('load',{"searchParams":searchParams});
}

function clearQuery(){
	$('#form01').form('clear');
}


function hasSelect(){
	var sel = table1.datagrid('getSelected');
	if(!sel)
	{
		$.messager.show({
			title:'提示',
			msg:'请选中要操作的数据',
			timeout:5000,
			showType:'slide'
		});
		return false;
	}
	return sel;
}





//导出
function exportExcel(flag){
	//url
	var url = "InvoiceMg!doExportExcel.action";
	
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
		if(table1.datagrid('getRows').length <= 0){
			$.messager.alert('提示','无数据请勿点击导出','info',null);
			return;
		}
		if($('#exportAll').length<=0){
			$('#form01').append('<input name=\"exportAll\"  id=\"exportAll\" type=\"hidden\" />');
		}
		$('#exportAll').val('true');
	}else
	{
		url += "?exportAll=false";
		if(datagridList.length == 0){
			alert("请选择要导出的数据");
			return;
		}
		if($('#exportAll').length<=0){
			$('#form01').append('<input name=\"exportAll\"  id=\"exportAll\" type=\"hidden\" />');
		}
		$('#exportAll').val('false');
	}
//	alert(url);
	//submit
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
//        	alert("ss");
        }
    });
	//remove
	$('#sqlData').remove();
	$('#exportAll').remove();
//	$('#searchParams').remove();
}
