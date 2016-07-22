$(document).ready(function(){
	$("#FUND_CASE").dialog('close');
	
	var HEAD_ID = $("input[name='HEAD_ID']").val();
	var FI_SUPPLIERS_ID = $("input[name='FI_SUPPLIERS_ID']").val();
	var FI_PAY_DATE1 = $("input[name='FI_PAY_DATE1']").val();
	var FI_PAY_DATE2 = $("input[name='FI_PAY_DATE2']").val();
	var UPLOADTYPE = $("select[name='UPLOADTYPE']").val();
	var FI_REALITY_BANK_FLAF=$("select[name='bankFlag']").find("option:selected").val();
	var FI_REALITY_BANK="";
	if(FI_REALITY_BANK_FLAF=='1'){
		FI_REALITY_BANK=$("select[name='bankFlag']").find("option:selected").text();
	}
	
	$('#pageTable_cyber_Con').datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		toolbar:'#pageForm',
		url:'rentWrite!toSupperFundMgData.action',
		queryParams:{"HEAD_ID" : HEAD_ID,"FI_SUPPLIERS_ID" : FI_SUPPLIERS_ID,"FI_PAY_DATE1" : FI_PAY_DATE1,"FI_PAY_DATE2" : FI_PAY_DATE2,"UPLOADTYPE" : UPLOADTYPE,"FI_REALITY_BANK" : FI_REALITY_BANK},
		columns:[[
		          {field:'ck',align:'center',width:30,checkbox:true},
		          {field:'HEAD_ID',align:'center',width:100,title:'付款单号'},
		          {field:'FI_PAY_MONEY',align:'center',width:100,title:'付款金额'},
		          {field:'FI_PROJECT_NUM',align:'center',width:70,title:'项目数量'},
		          {field:'FI_PAY_DATE',align:'center',width:100,title:'核销日期'},
		          {field:'FI_REALITY_BANK',align:'center',width:150,editor:'text',title:'核销银行'},
		          {field:'FI_TO_THE_PAYEE',align:'center',width:150,editor:'text',title:'付款人'},
		          {field:'FI_TO_THE_ACCOUNT',align:'center',width:200,editor:'text',title:'付款账号'},
		          {field:'SUPPLIER_IDS',hidden:true},
		          {field:'UPLOADTYPE',align:'center',width:100,title:'是否导出',formatter:function(value,rowData,rowIndex){
		          		if(value=='0')
		          		{
		          			return "未导出";
		          		}
		          		else
		          		{
		          			return "已导出";
		          		}
	                	  
	                  }},
		          {field:'FI_STATUS',align:'center',width:100,title:'确认状态'},
		          {field:'FI_REMARK',align:'center',width:300,editor:'text',title:'驳回原因'},
		          {field:'aaa',title:'操作',width:150,formatter:function(value,rowData,rowIndex){
                	  return "<a href='javascript:void(0);' onclick=fundUForm(" + JSON.stringify(rowData) + ")>修改</a>";  
                  }}
		          ]],
		view : detailview,
		detailFormatter : function(index, row) {
			return '<div id="ddv-' + row.HEAD_ID + '" style="padding:5px 0;"></div>';
		},
		onExpandRow : function(index, row) {
			jQuery.ajax({
				url:_basePath+"/rentWrite/rentWrite!getSupperFundData.action?HEAD_ID="+row.HEAD_ID,  
				type:'post',
				dataType:'json',
			    success: function(json){
					var data = {flag:json.flag,total:json.data.length,rows:json.data};
					var pRowIndex = "ddv-"+row.HEAD_ID;
					$('#ddv-'+row.HEAD_ID).datagrid({
						fitColumns:true,
						columns:[[
						          	{field:'PRO_CODE',align:'center',width:10,title:'项目编号'},
		                            {field:'CUSTNAME',align:'center',width:15,title:'客户名称'},
		                            {field:'COMPANY_NAME',align:'center',width:15,title:'厂商'},
		                            {field:'SUP_NAME',align:'center',width:15,title:'供应商'},
		                            {field:'EQUIPMENINFOS',align:'center',width:15,title:'租赁物'},
		                            {field:'FI_PRO_NAME',align:'center',width:10,title:'款项名称'},
		                            {field:'BEGINNING_NUM',align:'center',width:5,title:'期次'},
		                            {field:'D_RECEIVE_DATE',align:'center',width:10,title:'计划收取日期'},
		                            {field:'BEGINNING_MONEY',align:'center',width:10,title:'本次应收金额'}
						         ]],
						onResize:function(){
	                        $('#pageTable_cyber_Con').datagrid('fixDetailRowHeight',index);
	                    },
	                    onLoadSuccess:function(){
	                        setTimeout(function(){
	                            $('#pageTable_cyber_Con').datagrid('fixDetailRowHeight',index);
	                        },0);
	                    }
					});
					 $('#pageTable_cyber_Con').datagrid('fixDetailRowHeight',index);
						$('#ddv-'+row.HEAD_ID).datagrid("loadData",data);
			}
		});
 		}
	});
});

function fundUForm(row){
	if (row){
		var HEAD_ID=row.HEAD_ID;
		if(row.UPLOADTYPE=='0'){
			
			 var FI_PAY_MONEY=row.FI_PAY_MONEY;
			 var FI_REALITY_BANK=row.FI_REALITY_BANK;
			 var FI_TO_THE_PAYEE=row.FI_TO_THE_PAYEE;
			 var FI_TO_THE_ACCOUNT=row.FI_TO_THE_ACCOUNT;
			
			 $("#HEAD_ID_U").val(HEAD_ID);
			 $("#FI_PAY_MONEY_U").val(FI_PAY_MONEY);
			 $("#FI_REALITY_BANK_U").val(FI_REALITY_BANK);
			 $("#FI_TO_THE_PAYEE_U").val(FI_TO_THE_PAYEE);
			 $("#FI_TO_THE_ACCOUNT_U").val(FI_TO_THE_ACCOUNT);
			 $("#FUND_CASE").dialog('open');
		}
		else{
			alert("核销单："+HEAD_ID+"已经导出，不能修改！");
		}
		 
	}else{
		$.messager.alert("请选择一条核销单!");
	}
}

function subFund(){
	
	var HEAD_ID=$("#HEAD_ID_U").val();
	var FI_REALITY_BANK=$("#FI_REALITY_BANK_U").val();
	var FI_TO_THE_PAYEE=$("#FI_TO_THE_PAYEE_U").val();
	var FI_TO_THE_ACCOUNT=$("#FI_TO_THE_ACCOUNT_U").val();
	
	
	if (FI_TO_THE_PAYEE == ''){
		alert("请输入付款人！");
		return ;
	}
	
	if (FI_TO_THE_ACCOUNT == ''){
		alert("请输入付款帐号！");
		return ;
	}
	jQuery.ajax({
		type:"post",
		url:_basePath+"/rentWrite/rentWrite!updateFundHeadBank.action?HEAD_ID="+HEAD_ID+"&FI_REALITY_BANK="+encodeURI(FI_REALITY_BANK)+"&FI_TO_THE_PAYEE="+encodeURI(FI_TO_THE_PAYEE)+"&FI_TO_THE_ACCOUNT="+encodeURI(FI_TO_THE_ACCOUNT),
		dataType:"json",
		success:function(e){
			if(e.flag){
				alert("修改核销单："+HEAD_ID+"成功！");
				$("#FUND_CASE").dialog('close');
				toSeacher();
			}else{
				alert("修改付款单失败"+json.msg);
			}
		},
	error:function(e){alert("修改付款单失败");}   
	});
}


function exportExcelNotCy(flag){
	$("#divFrom").empty();
	var uploadType="select";
	if(flag == 'all')
	{
		uploadType="all";
	}
	else{
		var datagridList=$('#pageTable_cyber_Con').datagrid('getChecked');
		if(datagridList.length<=0)
		{
			alert("请先选择要导出的数据在继续导出操作！");
			return false;
		}
		var selectData = [];
		for(var i = 0; i < datagridList.length; i++)
		{
			if(datagridList[i].UPLOADTYPE==1)
			{
				alert("付款单："+datagridList[i].HEAD_ID+"已被导出！");
				return false;
			}
			var temp={};
			temp.HEAD_ID=datagridList[i].HEAD_ID;
			temp.FI_REALITY_BANK=datagridList[i].FI_REALITY_BANK;
			temp.FI_TO_THE_PAYEE=datagridList[i].FI_TO_THE_PAYEE;
			temp.FI_TO_THE_ACCOUNT=datagridList[i].FI_TO_THE_ACCOUNT;
			temp.MONEY=datagridList[i].FI_PAY_MONEY;
			temp.SUPPLIER_IDS=datagridList[i].SUPPLIER_IDS;
			selectData.push(temp);
		}
		$("#selectDateHidden").val(JSON.stringify(selectData));
		
		uploadType="select";
	}
	
	var HEAD_ID = $("input[name='HEAD_ID']").val();
	var FI_SUPPLIERS_ID = $("input[name='FI_SUPPLIERS_ID']").val();
	var FI_PAY_DATE1 = $("input[name='FI_PAY_DATE1']").val();
	var FI_PAY_DATE2 = $("input[name='FI_PAY_DATE2']").val();
	var UPLOADTYPE2 = $("select[name='UPLOADTYPE']").val();
//	var FI_REALITY_BANK=$("select[name='bankFlag']").find("option:selected").text();
	var FI_REALITY_BANK_FLAF=$("select[name='bankFlag']").find("option:selected").val();
	var FI_REALITY_BANK="";
	if(FI_REALITY_BANK_FLAF=='1'){
		FI_REALITY_BANK=$("select[name='bankFlag']").find("option:selected").text();
	}
	var bankFlag = $("select[name='bankFlag']").find("option:selected").val();
	var url=_basePath+"/rentWrite/rentWrite!toSupper_Upload.action?uploadType1="+uploadType+"&bankFlag="+bankFlag+"&FI_REALITY_BANK="+FI_REALITY_BANK+"&HEAD_ID="+HEAD_ID+"&FI_SUPPLIERS_ID="+FI_SUPPLIERS_ID+"&FI_PAY_DATE1="+FI_PAY_DATE1+"&FI_PAY_DATE2="+FI_PAY_DATE2+"&UPLOADTYPE="+UPLOADTYPE2+"&selectDateHidden="+JSON.stringify(selectData);
	$("#divFrom").append("<form id='formSubmit' method='post' action='"+url+"'></form>");
	$("#formSubmit").submit();
	//刷新表单
	$('#pageTable_cyber_Con').datagrid('load',{"searchParams":searchParams});
}