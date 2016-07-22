var rows = new Array() ;
$(document).ready(function(){
	$("#PAYMENT_CASE").dialog('close');
	
	$("#fl_PageTable").datagrid({
		url:"payMentFL!pay_FL_Mg_AJAX.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		toolbar:'#pageForm',
		frozenColumns:[[
		    {field:'ID',checkbox:true,width:10},
			{field:'aaa',title:'操作',align:'center',width:50,formatter:function(value,rowData,rowIndex){	
				 var ID   = rowData.ID ;
				 rows[ID] = rowData ;
				  return "<a href='javascript:void(0);' onclick=payMentUForm(" + rowData.ID + ")>修改</a>";  
			}}
		]],
		columns:[[
		          	
		          	{field:'STATUS',title:'状态',align:'center',width:100,formatter:function(value,rowData,rowIndex){
		          		if(value == '3'){
		          			 return "已驳回";
		          		}else{
	                	  return "未申请";
	                	}
	                  }},
		          	{field:'CUST_NAME',title:'客户名称',width:150,align:'center'},
		          	{field:'PRO_CODE',title:'项目编号',width:100,align:'center'},
		          	{field:'LEASE_CODE',title:'融资租赁合同号',width:100,align:'center'},
		          	{field:'PAYLIST_CODE',title:'支付表号',width:100,align:'center'},
		          	{field:'ITEM_NAME',title:'款项名称',width:100,align:'center'},
		          	{field:'RESERVE_DATE',title:'预付日期',width:100,align:'center'},
		          	{field:'PAY_MONEY',title:'款项金额',width:80,align:'center'},
		          	{field:'PAYEE_NAME',title:'收款单位',width:200,align:'center'},
		          	{field:'PAY_BANK_NAME',title:'开户行行名',width:200,align:'center'},
		          	{field:'PAY_BANK_ADDRESS',title:'开户行所在地',width:200,align:'center'},
		          	{field:'PAY_BANK_ACCOUNT',title:'收款帐号',width:200,align:'center'},
		          	{field:'JOIN_ID',hidden:true}
		         ]]
	});
	
	$("#fl_COM_PageTable").datagrid({
		url:"payMentFL!pay_FL_COM_Mg_AJAX.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		toolbar:'#pageForm',
		frozenColumns:[[
		    {field:'ID',checkbox:true,width:10},
		]],
		columns:[[
		          	
		          	{field:'STATUS',title:'状态',align:'center',width:100,formatter:function(value,rowData,rowIndex){
		          		if(value == '1'){
		          			 return "已提交";
		          		}
	                  }},
		          	{field:'CUST_NAME',title:'客户名称',width:150,align:'center'},
		          	{field:'PRO_CODE',title:'项目编号',width:100,align:'center'},
		          	{field:'LEASE_CODE',title:'融资租赁合同号',width:100,align:'center'},
		          	{field:'PAYLIST_CODE',title:'支付表号',width:100,align:'center'},
		          	{field:'ITEM_NAME',title:'款项名称',width:100,align:'center'},
		          	{field:'RESERVE_DATE',title:'预付日期',width:100,align:'center'},
		          	{field:'PAY_MONEY',title:'款项金额',width:80,align:'center'},
		          	{field:'PAYEE_NAME',title:'收款单位',width:200,align:'center'},
		          	{field:'PAY_BANK_NAME',title:'开户行行名',width:200,align:'center'},
		          	{field:'PAY_BANK_ADDRESS',title:'开户行所在地',width:200,align:'center'},
		          	{field:'PAY_BANK_ACCOUNT',title:'收款帐号',width:200,align:'center'},
		          	{field:'JOIN_ID',hidden:true}
		         ]]
	});
	
	
	$("#fl_JL_PageTable").datagrid({
		url:"payMentFL!pay_FL_JL_Mg_AJAX.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		toolbar:'#pageForm',
		columns:[[
		          	
		          	{field:'STATUS',title:'状态',width:100,align:'center',formatter:function(value,rowData,rowIndex){
		          		if(value == '2'){
		          			 return "已付款";
		          		}
	                  }},
		          	{field:'CUST_NAME',title:'客户名称',width:150,align:'center'},
		          	{field:'PRO_CODE',title:'项目编号',width:100,align:'center'},
		          	{field:'LEASE_CODE',title:'融资租赁合同号',width:100,align:'center'},
		          	{field:'PAYLIST_CODE',title:'支付表号',width:100,align:'center'},
		          	{field:'ITEM_NAME',title:'款项名称',width:100,align:'center'},
		          	{field:'RESERVE_DATE',title:'预付日期',width:100,align:'center'},
		          	{field:'PAY_MONEY',title:'款项金额',width:80,align:'center'},
		          	{field:'REALITY_BANK_NAME',title:'付款银行',width:200,align:'center'},
		          	{field:'REALITY_DATE',title:'付款时间',width:100,align:'center'},
		          	{field:'PAYEE_NAME',title:'收款单位',width:200,align:'center'},
		          	{field:'PAY_BANK_NAME',title:'开户行行名',width:200,align:'center'},
		          	{field:'PAY_BANK_ADDRESS',title:'开户行所在地',width:200,align:'center'},
		          	{field:'PAY_BANK_ACCOUNT',title:'收款帐号',width:200,align:'center'},
		         ]]
	});
});


/**
 * 清空按钮
 * @return
 */
function emptyData(){
	$('#pageForm').form('clear');
}


function payMentUForm(ID){
	//debugger ;
	var row = rows[ID] ;
	if (row){
		 var PAYLIST_CODE=row.PAYLIST_CODE;
		 var LEASE_CODE=row.LEASE_CODE;
		 var ID=row.ID;
		 var PAY_MONEY=row.PAY_MONEY;
		 var ITEM_NAME=row.ITEM_NAME;
		 var PAYEE_NAME=row.PAYEE_NAME;
		 var PAY_BANK_ACCOUNT=row.PAY_BANK_ACCOUNT;
		 var PAY_BANK_NAME=row.PAY_BANK_NAME;
		 var PAY_BANK_ADDRESS=row.PAY_BANK_ADDRESS;
		 $("#PAYLIST_CODE_U").val(PAYLIST_CODE);
		 $("#ID_U").val(ID);
		 $("#LEASE_CODE_U").val(LEASE_CODE);
		 $("#PAY_MONEY_U").val(PAY_MONEY);
		 $("#ITEM_NAME_U").val(ITEM_NAME);
		 
		
		 $("#PAYEE_NAME_U").val(PAYEE_NAME);
		 $("#PAY_BANK_ACCOUNT_U").val(PAY_BANK_ACCOUNT);
		 $("#PAY_BANK_NAME_U").val(PAY_BANK_NAME);
		 $("#PAY_BANK_ADDRESS_U").val(PAY_BANK_ADDRESS);
		 $("#PAYMENT_CASE").dialog('open');
	}else{
		$.messager.alert("提示","请选择一条付款数据!");
	}
}


function updatePaymentHead(){
	
	var ID=$("#ID_U").val();
	var PAYLIST_CODE=$("#PAYLIST_CODE_U").val();
	var ITEM_NAME=$("#ITEM_NAME_U").val();
	var PAYEE_NAME=$("#PAYEE_NAME_U").val();
	var PAY_BANK_ACCOUNT=$("#PAY_BANK_ACCOUNT_U").val();
	var PAY_BANK_NAME=$("#PAY_BANK_NAME_U").val();
	var PAY_BANK_ADDRESS=$("#PAY_BANK_ADDRESS_U").val();
	

	
	if (PAYEE_NAME == ''){
		alert("请输入收款单位！");
		return ;
	}
	
	if (PAY_BANK_ACCOUNT == ''){
		alert("请输入收款帐号！");
		return ;
	}
	jQuery.ajax({
		type:"post",
		url:"payMentFL!pay_FL_Update.action?ID="+ID+"&PAYLIST_CODE="+PAYLIST_CODE+"&PAYEE_NAME="+encodeURI(PAYEE_NAME)+"&PAY_BANK_ACCOUNT="+encodeURI(PAY_BANK_ACCOUNT)+"&PAY_BANK_NAME="+encodeURI(PAY_BANK_NAME)+"&PAY_BANK_ADDRESS="+encodeURI(PAY_BANK_ADDRESS),
		dataType:"json",
		success:function(e){
			if(e.flag){
				alert("修改还款计划："+PAYLIST_CODE+"的"+ITEM_NAME+"成功！");
				$("#PAYMENT_CASE").dialog('close');
				seach();
			}else{
				alert("修改失败"+json.msg);
			}
		},
	error:function(e){alert("修改失败");}   
	});
}


//提交
function sumbitPayHead()
{
	$("#divFrom").empty();
	var datagridList=$('#fl_PageTable').datagrid('getChecked');
	if(datagridList.length<=0)
	{
		alert("请先选择数据在继续提交操作！");
		return false;
	}
	
		
		for(var i = 0; i < datagridList.length; i++)
		{
			var PAYLIST_CODE=datagridList[i].PAYLIST_CODE;
			var ITEM_NAME=datagridList[i].ITEM_NAME;
			var PAY_BANK_ACCOUNT=datagridList[i].PAY_BANK_ACCOUNT;
			var PAY_BANK_ADDRESS=datagridList[i].PAY_BANK_ADDRESS;
			var PAYEE_NAME=datagridList[i].PAYEE_NAME;
			var PAY_BANK_NAME=datagridList[i].PAY_BANK_NAME;
			if(PAY_BANK_ACCOUNT+"" =='' || PAY_BANK_ACCOUNT ==undefined || PAY_BANK_ACCOUNT =='undefined'){
				alert("请输入还款计划编号为"+PAYLIST_CODE+ITEM_NAME+"的收款帐号！");
				return false;
			}
			if(PAYEE_NAME+"" =='' || PAYEE_NAME ==undefined  || PAYEE_NAME =='undefined'){
				alert("请输入还款计划编号为"+PAYLIST_CODE+ITEM_NAME+"的收款单位！");
				return false;
			}
			
			if(PAY_BANK_ADDRESS+"" ==''  || PAY_BANK_ADDRESS ==undefined || PAY_BANK_ADDRESS =='undefined'){
				alert("请输入还款计划编号为"+PAYLIST_CODE+ITEM_NAME+"的开户行地址！");
				return false;
			}
			
			if(PAY_BANK_NAME+"" =='' || PAY_BANK_NAME ==undefined  || PAY_BANK_NAME =='undefined'){
				alert("请输入还款计划编号为"+PAYLIST_CODE+ITEM_NAME+"的开户行行名！");
				return false;
			}
			
		}
	
	var IDS="";
	for(var i = 0; i < datagridList.length; i++)
	{
		if(i==0){
			IDS=datagridList[i].ID;
		}
		else{
			IDS=IDS+','+datagridList[i].ID;
		}
	}
	
	if(confirm("确认提交?")){
		var url = _basePath+"/payment/payMentFL!fl_PayHead_Status.action?IDS="+encodeURI(IDS);
		
		$("#divFrom").append("<form id='formSub' method='post' action='"+url+"'></form>");
		$("#formSub").submit();
	}
}


//驳回
function backPayHead()
{
	$("#divFrom").empty();
	var datagridList=$('#fl_COM_PageTable').datagrid('getChecked');
	
	if(datagridList.length<=0)
	{
		alert("请先选择数据在继续提交操作！");
		return false;
	}
	
	var IDS="";
	for(var i = 0; i < datagridList.length; i++)
	{
		if(i==0){
			IDS=datagridList[i].ID;
		}
		else{
			IDS=IDS+','+datagridList[i].ID;
		}
	}
	if(confirm("确认驳回?")){
		var url = _basePath+"/payment/payMentFL!fl_PayHead_StatusBack.action?IDS="+encodeURI(IDS);
		
		$("#divFrom").append("<form id='formSub' method='post' action='"+url+"'></form>");
		$("#formSub").submit();
	}
}

//通过
function passPayHead()
{
	$("#divFrom").empty();
	var datagridList=$('#fl_COM_PageTable').datagrid('getChecked');
	
	if(datagridList.length<=0)
	{
		alert("请先选择数据在继续提交操作！");
		return false;
	}
	
	var IDS="";
	for(var i = 0; i < datagridList.length; i++)
	{
		if(i==0){
			IDS=datagridList[i].ID;
		}
		else{
			IDS=IDS+','+datagridList[i].ID;
		}
	}
	
	
	var REALITY_DATE = $('#REALITY_DATE').datebox("getValue");
	var REALITY_BANK_NAME = $('#REALITY_BANK_NAME').val();
	if(REALITY_DATE == "" || REALITY_BANK_NAME == ""){
		$.messager.alert("提示","请选择实付日期和核销银行!");
		return false;
	}
	
	if(confirm("确认审核通过?")){
		var url = _basePath+"/payment/payMentFL!fl_PayHead_StatusPass.action?IDS="+encodeURI(IDS)+"&REALITY_DATE="+REALITY_DATE+"&REALITY_BANK_NAME="+encodeURI(REALITY_BANK_NAME);
		
		$("#divFrom").append("<form id='formSub' method='post' action='"+url+"'></form>");
		$("#formSub").submit();
	}
}