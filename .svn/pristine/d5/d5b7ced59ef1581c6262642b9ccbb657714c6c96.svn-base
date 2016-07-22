var table1 = null;
$(function(){
		table1  = $('#table1').datagrid({
			url : 'BankSignMg!doSelectBankSignApplyPageData.action',
			columns : [[
				{field:'DQFLAG',width:30,align:'center',title:'', formatter:function(value,rowData,rowIndex){
						if(value == '1'){
							return "<img src='../img/red.png'>";
						}else if(value == '2'){
							return "<img src='../img/blue.png'>";
						}
				}},
				{field:'PROJECT_ID',checkbox:true,width:100,align:'center'},
				{field:'SIGN_FLAG_NAME',title:'签约状态',width:100,align:'center'},
				{field:'END_DATA',title:'项目到期日',width:120,align:'center'},
				//{field:'AGRDEADLINE',title:'签约到日期',width:120,align:'center'},
				{field:'PRO_CODE',title:'项目编号',width:200,align:'center'},
				{field:'LEASE_CODE',title:'合同编号',width:200,align:'center'},
				{field:'CUST_TYPE_NAME',title:'客户类型',width:100,align:'center'},
				{field:'CUST_NAME',title:'客户名称',width:200,align:'center'},
				{field:'BANK_CUSTNAME',title:'持卡人',width:200,align:'center'},
				{field:'BANK_NAME',title:'开户银行',width:200,align:'center'},
				{field:'BANK_ACCOUNT',title:'银行卡号',width:200,align:'center'},
				{field:'ID_CARD_NO',title:'身份证号',width:200,align:'center'}
			]],
			toolbar : '#toolbar',//工具条
			pagination : true ,//分页
			idField : 'BANK_ID',
			
			autoRowHeight : true,
			//singleSelect : true,
			checkOnSelect : true,
			nowrap : true,
			rownumbers : true,
			fit : true,
			pageSize : 20,
			pageList : [10,20,50,100,200,300]//,
			//onLoadSuccess : function(data){console.info(table1.datagrid('getRows').length);}
		});
	
	$('#form01').form({
        url:""
    });

});
//查询
function se(){
	var searchParams = getFromData('#toolbar');
	table1.datagrid('load',{"searchParams":searchParams});
}

////是否全选
//function change(obj){
//	$(obj).is(":checked") ? table1.datagrid('selectAll') : table1.datagrid('unselectAll') ;
//}

//导出
function exportExcelDC(flag){
	//url
	var url = "BankSignMg!doExportApplyExcel.action";
	
	//data
	var datagridList=table1.datagrid('getChecked');
	var sqlData = [];	
	
	for(var i = 0; i < datagridList.length; i++)
	{
		sqlData.push("'"+datagridList[i].PROJECT_ID+"'");
	}
	
	//params
	var searchParams = getFromData('#toolbar');
	
	if(flag == 'all')
	{
		url += '?exportAll=true';
		if(table1.datagrid('getRows').length <= 0){
			$.messager.alert('提示','无数据请勿点击导出','info',null);
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
	$('#form01').form('submit',{
        url:url,
        onSubmit: function(){
			//查询参数
			//导出标识
			if($('#sqlData').length<=0){
				$('#form01').append('<input name=\"sqlData\"  id=\"sqlData\" type=\"hidden\" />');
			}
			$('#sqlData').val(sqlData.join(','));
        },
        success : function(){
        	se();
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



//导出
function exportExcel(flag){
	//url
	var url = "BankSignMg!doQyApplyExcel.action";
	
	//data
	var datagridList=table1.datagrid('getChecked');
	var sqlData = [];	
	
	for(var i = 0; i < datagridList.length; i++)
	{
		sqlData.push(datagridList[i].PROJECT_ID);
	}
	
	var searchParams = getFromData('#toolbar');
		url += "?exportAll=false";
		if(datagridList.length == 0){
			alert("请选择要签约的数据");
			return;
		}
		
		var sqld=sqlData.join(',');
		
		$.ajax({
			url:_basePath+"/crm/BankSignMg!doQyApplyExcel.action?sqlData="+sqld,
			type:"post",
			dataType:"json",
			success:function (json){
				if(json.flag){
					alert("请等待签约结果");
					se();
				}else{
					se();
				}
			}
		});
}


//导出
function exportExcelNew(flag){
	//url
	var url = "BankSignMg!doExportApplyExcelNew.action";
	
	//data
	var datagridList=table1.datagrid('getChecked');
	var sqlData = [];	
	
	for(var i = 0; i < datagridList.length; i++)
	{
		sqlData.push(datagridList[i].PROJECT_ID);
	}
	alert(datagridList);
	//params
	var searchParams = getFromData('#toolbar');
	
	if(flag == 'all')
	{
		url += '?exportAll=true';
		if(table1.datagrid('getRows').length <= 0){
			$.messager.alert('提示','无数据请勿点击导出','info',null);
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
	$('#form01').form('submit',{
        url:url,
        onSubmit: function(){
			//查询参数
			//导出标识
			if($('#sqlData').length<=0){
				$('#form01').append('<input name=\"sqlData\"  id=\"sqlData\" type=\"hidden\" />');
			}
			$('#sqlData').val(sqlData.join(','));
        },
        success : function(){
        	se();
        }
    });
	$('#sqlData').remove();
}



//导出
function exportExcelNewFC(flag){
	//url
	var url = "BankSignMg!exportExcelNewFC.action";
	
	//data
	var datagridList=table1.datagrid('getChecked');
	var sqlData = [];	
	
	for(var i = 0; i < datagridList.length; i++)
	{
		sqlData.push(datagridList[i].PROJECT_ID);
	}
	//params
	var searchParams = getFromData('#toolbar');
	
	if(flag == 'all')
	{
		url += '?exportAll=true';
		if(table1.datagrid('getRows').length <= 0){
			$.messager.alert('提示','无数据请勿点击导出','info',null);
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
	$('#form01').form('submit',{
        url:url,
        onSubmit: function(){
			//查询参数
			//导出标识
			if($('#sqlData').length<=0){
				$('#form01').append('<input name=\"sqlData\"  id=\"sqlData\" type=\"hidden\" />');
			}
			$('#sqlData').val(sqlData.join(','));
        },
        success : function(){
        	se();
        }
    });
	$('#sqlData').remove();
}

