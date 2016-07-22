	
var table1;
var table2;
$(function(){
	table1 = $("#table1");
	table2 = $("#table2");
	table1.datagrid({
		url : 'InvoiceSearch!getInvoiceSearchPageData.action',
		columns : [[{checkbox:true},
		            {field:'ID',title:'唯一标识',width:100,align:'center'},
			        {field:'SUP_SHORTNAME',title:'经销商名称',width:150,align:'center'},
			        {field:'CUST_NAME',title:'客户名称',width:150,align:'center'},
			        {field:'PROJECT_MODEL',title:'业务类型',width:150,align:'center'},
			        {field:'LEASE_CODE',title:'融资租赁合同号',width:150,align:'center'},
			        {field:'PAYLIST_CODE',title:'支付表号',width:150,align:'center'},
			        {field:'INVOICE_TYPE',title:'发票/收据',width:100,align:'center'},
			        {field:'FUND_TYPE',title:'发票类型',width:100,align:'center'},
			        {field:'FUND_NAME',title:'发票内容',width:100,align:'center'},
			        {field:'INVOICE_CODE',title:'发票/收据号',width:100,align:'center'},
			        {field:'INVOICE_DATE',title:'发票/收据日期',width:100,align:'center'},
			        {field:'INVOICE_AMT',title:'总金额',width:100,align:'center'},
			        {field:'RENT_LIST',title:'期次',width:100,align:'center'}
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
		pageList : [20,50,100,200],
		view: detailview,
	    detailFormatter:function(index,row){
	        return '<div style="padding:2px"><table class="ddv" id="ddv-'+index+'"></table></div>';
	    },
	    onExpandRow: function(index,row){
	        var ddv = $('#ddv-'+index);
	        ddv.datagrid({
	            url:'InvoiceSearch!getInvoiceSearchFactById.action?ITEMID='+row.ID,
	            rownumbers:true,
//	    		fitColumns : true,
	    		autoRowHeight : true,
	    		singleSelect:true,
	            loadMsg:'',
	            height:'auto',
	            columns:[[
	                {field:'ID',title:'唯一标识',width:100,align:'center'},
			        {field:'INVOICE_TYPE',title:'发票/收据',width:100,align:'center'},
			        {field:'FUND_TYPE',title:'发票类型',width:100,align:'center'},
			        {field:'FUND_NAME',title:'发票内容',width:100,align:'center'},
			        {field:'INVOICE_CODE',title:'发票/收据号',width:100,align:'center'},
			        {field:'INVOICE_DATE',title:'发票/收据日期',width:100,align:'center'},
			        {field:'INVOICE_AMT',title:'总金额',width:100,align:'center'},
			        {field:'TARGET_NAME',title:'开票对象',width:100,align:'center'},
			        {field:'RENT_LIST',title:'期次',width:100,align:'center'},
			        {field:'EMS_NAME',title:'快递名称',width:100,align:'center'},
			        {field:'EMS_NUM',title:'快递编号',width:100,align:'center'},
			        {field:'EMS_DATE',title:'邮寄日期',width:100,align:'center'},
			        {field:'EMS_TO_NAME',title:'收件人',width:100,align:'center'},
			        {field:'EMS_TO_ADDR',title:'邮寄地址',width:100,align:'center'},
			        {field:'EMS_TO_CODE',title:'邮编',width:100,align:'center'}
		            ]],
	            onResize:function(){
	        		table1.datagrid('fixDetailRowHeight',index);
	            },
	            onLoadSuccess:function(){
	                setTimeout(function(){
	                	table1.datagrid('fixDetailRowHeight',index);
	                },0);
	            },
	            view: detailview,
	    	    detailFormatter:function(index,row){
	    	        return '<div style="padding:2px"><table class="ddv" id="ddv1-'+index+'"></table></div>';
	    	    },
	    	    onExpandRow: function(index,row){
	    	        var ddv1 =  $('#ddv1-'+index);;
	    	        ddv1.datagrid({
	    	            url:'InvoiceSearch!getInvoiceSearchItemById.action?ITEMID='+row.ID,
	    	            fitColumns:true,
	    	            singleSelect:true,
	    	            rownumbers:true,
	    	            loadMsg:'',
	    	            height:'auto',
	    	            columns:[[
	    	                {field:'ITEM_NAME',title:'条目名称',width:100,align:'center'},
	    	                {field:'ITEM_MODEL',title:'规格型号',width:100,align:'center'},
	    			        {field:'ITEM_UNIT_PRICE',title:'单价',width:100,align:'center'},
	    			        {field:'ITEM_NUM',title:'数量',width:100,align:'center'},
	    			        {field:'ITEM_UNIT',title:'计价单位',width:100,align:'center'},
	    			        {field:'ITEM_FACT_AMT',title:'含税金额',width:100,align:'center'},
	    			        {field:'ITEM_SUB_AMT',title:'金额',width:100,align:'center'},
	    			        {field:'ITEM_SUB_TAX_AMT',title:'税额',width:100,align:'center'},
	    			        {field:'ITEM_TAX_RATE',title:'税率',width:100,align:'center'}
	    	            ]],
	    	            onResize:function(){
	    	        		ddv.datagrid('fixDetailRowHeight',index);
		    	        	ddv1.datagrid('fixDetailRowHeight',index);
			    	        table1.datagrid('fixDetailRowHeight',index);
	    	            },
	    	            onLoadSuccess:function(){
	    	                setTimeout(function(){
		    	        		ddv.datagrid('fixDetailRowHeight',index);
	    	                	ddv1.datagrid('fixDetailRowHeight',index);
				    	        table1.datagrid('fixDetailRowHeight',index);
	    	                },0);
	    	            }
	    	        });
	        		ddv.datagrid('fixDetailRowHeight',index);
	    	        ddv1.datagrid('fixDetailRowHeight',index);
	    	        table1.datagrid('fixDetailRowHeight',index);
    	    	}
	        });
	        table1.datagrid('fixDetailRowHeight',index);
    	}
	});
	$('#form01').form({
        url:""
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


function showDetail(){
	var row = hasSelect();
	
	if(row){
		var id = row['ID'];
		var PAYLIST_CODE = row['PAYLIST_CODE'];
		var FUND_TYPE = row['FUND_TYPE'];
		var FUND_NAME = row['FUND_NAME'];
		if(id){
			init(id);
			$('#dialog1').dialog({
			    title: PAYLIST_CODE+'  ' +FUND_TYPE + '  ' +FUND_NAME,
			    width: 800,
			    height: 380,
			    closed: false,
			    cache: false,
//			    href: 'InvoiceMg!toInvoiceConfigDetailMg.action?id='+id,
			    modal: true
			});
		}
	}
}

function init(id){
	table2.datagrid({
		url : 'InvoiceSearch!getInvoiceSearchDetailPageData.action?ITEMID='+id,
		columns : [[
		            {field:'ID',title:'唯一标识',width:60,align:'center'},
		            {field:'LEASE_CODE',title:'融资租赁合同号',width:150,editor:'text',align:'center'},
		            {field:'PAYLIST_CODE',title:'支付表号',width:150,editor:'text',align:'center'},
		            {field:'RENT_LIST',title:'期次',width:50,editor:'text',align:'center'},
		            {field:'ITEM_NAME',title:'资金名称',width:100,editor:'text',align:'center'},
		            {field:'ITEM_UNIT_PRICE',title:'单价',width:100,editor:'text',align:'center'},
		            {field:'ITEM_NUM',title:'数量',width:50,editor:'text',align:'center'},
		            {field:'ITEM_FACT_AMT',title:'金额',width:100,editor:'text',align:'center'},
		            {field:'ITEM_TAX_RATE',title:'税率',width:50,editor:'text',align:'center'},
		            {field:'TARGET_TYPE',title:'对象',width:100,editor:'text',align:'center'},
		            {field:'TARGET_NAME',title:'对象名称',width:150,editor:'text',align:'center'}
		            ]],
//		toolbar : '#toolbar',//工具条
        idField : 'ID',
        fit : true ,
//		fitColumns : true,
//		autoRowHeight : true,
		singleSelect:true,
		collapsible:true,
//		    		checkOnSelect : true,
		nowrap : true,
		rownumbers : true,
	    method: 'get',
		pagination : true ,//分页
		pageSize : 20,
		pageList : [20,30,50,100]
	});
}



//导出
function exportExcel(flag){
	//url
	var url = "InvoiceSearch!doExportApplyExcel.action";
	
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
