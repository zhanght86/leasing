var rows = new Array() ;
$(document).ready(function(){
	$("#PAYMENT_CASE").dialog('close');
	
	$("#eq_Head_PageTable").datagrid({
		url:"payment!pay_Head_Eq_Mg_AJAX.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		toolbar:'#pageForm',
		frozenColumns:[[
		    {field:'ID',checkbox:true,width:10},
			{field:'aaa',title:'操作',width:50,align:'center',formatter:function(value,rowData,rowIndex){	
				 var ID   = rowData.ID ;
				 rows[ID] = rowData ;
				  return "<a href='javascript:void(0);' onclick=payMentUForm(" + ID + ")>修改</a>";  
			}}
		]],
		columns:[[
		          	
		          	{field:'STATUS',title:'状态',align:'center',width:100,formatter:function(value,rowData,rowIndex){
		          		if(value == '7'){
		          			 return "已驳回";
		          		}else{
	                	  return "未核销";
	                	}
	                  }},
		          	{field:'SUPPER_NAME',title:'出卖人',width:150,align:'center'},
		          	//{field:'COMP_NAME',title:'厂商',width:100},
		          	{field:'PAYMENT_CODE',title:'付款单号',width:100,align:'center'},
		          	{field:'PAYMENT_MONEY',title:'付款金额',width:100,align:'center'},
		          	{field:'ACCBILL_PRICE',title:'承兑汇票',width:100,align:'center'},
		          	{field:'BANKBILL_PRICE',title:'银行汇款',width:100,align:'center'},
		          	{field:'BEGGIN_DATE',title:'放款日期',width:100,align:'center'},
		          	{field:'PROJECT_NUM',title:'项目数量',width:80,align:'center'},
		          	{field:'PAYEE_NAME',title:'收款单位',width:200,align:'center'},
		          	{field:'PAY_BANK_NAME',title:'开户行行名',width:200,align:'center'},
		          	{field:'PAY_BANK_ADDRESS',title:'开户行所在地',width:200,align:'center'},
		          	{field:'PAY_BANK_ACCOUNT',title:'收款帐号',width:200,align:'center'},
		          	{field:'SUPPER_ID',hidden:true}
	                  
		         ]]
	});
	
	
	
	$("#eq_HeadBack_PageTable").datagrid({
		url:"payment!pay_HeadBack_Eq_Mg_AJAX.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		toolbar:'#pageForm',
		frozenColumns:[[
		                {field:'ID',checkbox:true,width:50}
		    		]],
		columns:[[
		          	{field:'NAME',title:'客户名称',width:150,align:'center'},
		          	{field:'LEASE_CODE',title:'融资租赁合同',width:150,align:'center'},
		          	{field:'SUPPER_NAME',title:'出卖人',width:100},
		          	//{field:'COMP_NAME',title:'厂商',width:100},
		          	{field:'PAYMENT_CODE',title:'付款单号',width:100},
		          	{field:'PAYMENT_MONEY',title:'付款金额',width:100},
		          	{field:'DEDUCTION_RATE',title:'抵扣比例',width:100},
		          	{field:'DEDUCTION_MONEY',title:'抵扣金额',width:100},
		          	{field:'LAST_MONEY',title:'实际放款金额',width:100},
		          	{field:'ACCBILL_PRICE',title:'承兑汇票',width:100},
		          	{field:'BANKBILL_PRICE',title:'银行汇款',width:100},
		          	{field:'BEGGIN_DATE',title:'放款日期',width:100},
		          	{field:'PROJECT_NUM',title:'项目数量',width:50},
		          	{field:'PAYEE_NAME',title:'收款单位',width:150},
		          	{field:'PAY_BANK_NAME',title:'开户行行名',width:150},
		          	{field:'PAY_BANK_ADDRESS',title:'开户行所在地',width:150},
		          	{field:'PAY_BANK_ACCOUNT',title:'付款帐号',width:150},
		          	{field:'STATUS',title:'状态',width:100,formatter:function(value,rowData,rowIndex){
	                	  return "未核销";
	                  }}
		         ]]
	});
	
	
	
	$("#db_Head_PageTable").datagrid({
		url:"payment!pay_Head_DB_Mg_AJAX.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		toolbar:'#pageForm',
		columns:[[
		          	{field:'ID',checkbox:true,width:10},
		          	{field:'PAYMENT_CODE',title:'付款单号',width:25},
		          	{field:'PAYMENT_MONEY',title:'付款金额',width:25},
		          	{field:'BEGGIN_DATE',title:'放款日期',width:25},
		          	{field:'PROJECT_NUM',title:'项目数量',width:15},
		          	{field:'STATUS',title:'状态',width:25,formatter:function(value,rowData,rowIndex){
	                	  return "未核销";
	                  }}
		         ]]
	});
	
	$("#db_Head_Query_PageTable").datagrid({
		url:"payment!pay_Head_Query_DB_Mg_AJAX.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		toolbar:'#pageForm',
		columns:[[
		          	{field:'ID',checkbox:true,width:10},
		          	{field:'PAYMENT_CODE',title:'付款单号',width:25},
		          	{field:'PAYMENT_MONEY',title:'付款金额',width:25},
		          	{field:'BEGGIN_DATE',title:'放款日期',width:25},
		          	{field:'PROJECT_NUM',title:'项目数量',width:15},
		          	{field:'FLAG',title:'状态',width:25}
		         ]]
	});
	
	$("#db_HeadBack_PageTable").datagrid({
		url:"payment!pay_HeadBack_DB_Mg_AJAX.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		toolbar:'#pageForm',
		columns:[[
		          	{field:'ID',checkbox:true,width:10},
		          	{field:'PAYMENT_CODE',title:'付款单号',width:25},
		          	{field:'PAYMENT_MONEY',title:'付款金额',width:25},
		          	{field:'BEGGIN_DATE',title:'放款日期',width:25},
		          	{field:'PROJECT_NUM',title:'项目数量',width:15},
		          	{field:'STATUS',title:'状态',width:25,formatter:function(value,rowData,rowIndex){
	                	  return "未核销";
	                  }}
		         ]]
	});
	
	
	
	$("#other_Head_PageTable").datagrid({
		url:"payment!pay_Head_Other_Mg_AJAX.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		toolbar:'#pageForm',
		columns:[[
		          	{field:'ID',checkbox:true,width:100},
		          	{field:'PAYMENT_CODE',title:'付款单号',width:25},
		          	{field:'PAYMENT_MONEY',title:'付款金额',width:25},
		          	{field:'BEGGIN_DATE',title:'放款日期',width:25},
		          	{field:'PROJECT_NUM',title:'项目数量',width:15},
		          	{field:'STATUS',title:'状态',width:25,formatter:function(value,rowData,rowIndex){
	                	  return "未核销";
	                  }}
		         ]]
	});
	
	$("#other_Head_Query_PageTable").datagrid({
		url:"payment!pay_Head_Query_Other_Mg_AJAX.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		toolbar:'#pageForm',
		columns:[[
		          	{field:'ID',checkbox:true,width:100},
		          	{field:'PAYMENT_CODE',title:'付款单号',width:25},
		          	{field:'PAYMENT_MONEY',title:'付款金额',width:25},
		          	{field:'BEGGIN_DATE',title:'放款日期',width:25},
		          	{field:'PROJECT_NUM',title:'项目数量',width:15},
		          	{field:'FLAG',title:'状态',width:25}
		         ]]
	});
	
	$("#other_HeadBack_PageTable").datagrid({
		url:"payment!pay_HeadBack_Other_Mg_AJAX.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		toolbar:'#pageForm',
		columns:[[
		          	{field:'ID',checkbox:true,width:100},
		          	{field:'PAYMENT_CODE',title:'付款单号',width:25},
		          	{field:'PAYMENT_MONEY',title:'付款金额',width:25},
		          	{field:'BEGGIN_DATE',title:'放款日期',width:25},
		          	{field:'PROJECT_NUM',title:'项目数量',width:15},
		          	{field:'STATUS',title:'状态',width:25,formatter:function(value,rowData,rowIndex){
	                	  return "未核销";
	                  }}
		         ]]
	});
	
	
});

function paymentBankForm(row){
	
	if (row){
		 var PAYMENT_CODE=row.PAYMENT_CODE;
		 var ID=row.ID;
		 var SUPPER_NAME=row.SUPPER_NAME;
		 var REALITY_DATE=row.REALITY_DATE;
		 var REALITY_BANK_NAME=row.REALITY_BANK_NAME;
		 $("#PAYMENT_CODE_U").val(PAYMENT_CODE);
		 $("#ID_U").val(ID);
		 $("#SUPPER_NAME_U").val(SUPPER_NAME);
		 $("#REALITY_DATE_U").datebox("setValue",REALITY_DATE);
		 $("#REALITY_BANK_NAME_U").val(REALITY_BANK_NAME);

		 $("#PAYMENT_CASE").dialog('open');
	}else{
		$.messager.alert("请选择一条付款单!");
	}
}


/**
 * 清空按钮
 * @return
 */
function emptyData(){
	$('#pageForm').form('clear');
}


//项目个数计算，金额求和
function payMaonyOp()
{
	var lease_name="new";
	var num=0;
	var moneyAll=0;
	$('input:checkbox[name="checkOp"]:checked').each(function(){  
		num=num+1;
		var pay_money=$(this).attr("pay_money");
		var lease_name_add=$(this).attr("lease_name");
		moneyAll=accAdd(moneyAll,pay_money);
		if(lease_name!="new"){
			if(lease_name!=lease_name_add){
				$(this).attr("checked",false);
				alert("请选择同一出卖人");
				return;
			}
		}
		lease_name = lease_name_add;
	}); 

	$("#PROJECT_NUM").val(num);
	$("#PAYMENT_MONEY").val(moneyAll);
}

/**
 * 加法
 * @param arg1
 * @param arg2
 * @return
 */
function accAdd(arg1, arg2) {
    var r1, r2, m;
    try { r1 = arg1.toString().split(".")[1].length; } catch (e) { r1 = 0; }
    try { r2 = arg2.toString().split(".")[1].length; } catch (e) { r2 = 0; }
    m = Math.pow(10, Math.max(r1, r2));
    return (arg1 * m + arg2 * m) / m;
}


//非网银-创建结算单
function creatPayMent()
{
	var PROJECT_NUM=$("#PROJECT_NUM").val();
	if(PROJECT_NUM<=0)
	{
		alert("请先选择需要保存的数据在继续保存操作！");
		return false;
	}
	
	var BEGGIN_DATE=$("input[name='BEGGIN_DATE']").val();
	if(BEGGIN_DATE == "" || BEGGIN_DATE == undefined || BEGGIN_DATE.length<=0){
		alert("请输入付款日期，在进行保存！");
		return false;
	}
	
	var selectData = [];
	$('input:checkbox[name="checkOp"]:checked').each(function(){  
		var temp={};
		temp.payMent_ID=$(this).attr("payMent_ID");
		selectData.push(temp);
	}); 
	var dataJson ={selectData:selectData};
	$("#selectDateHidden").val(JSON.stringify(dataJson));
	$("#formsubmt").submit();
}



//新增
function createPayHead(str)
{
	var url ="";
	if(str=='1')//设备放款
	{
		top.addTab("新增放款", _basePath
				+ "/payment/payment!query_Eq_PayMent_C.action?date="+new Date().getTime());

		//url = _basePath+"/payment/payment!query_Eq_PayMent_C.action";
	}
	else if(str=='2')//担保费放款
	{
		url = _basePath+"/payment/payment!query_DB_PayMent_C.action";
	}
	else//其他费用放款
	{
		url = _basePath+"/payment/payment!query_OTHER_PayMent_C.action";
	}
	//页面刷新
	// window.location.href = url;
}


//提交
function sumbitPayHead(str)
{
	$("#divFrom").empty();
	var datagridList;
	if(str=='1')
	{
		datagridList=$('#eq_Head_PageTable').datagrid('getChecked');
	}
	else if(str=='2')//担保费放款
	{
		datagridList=$('#db_Head_PageTable').datagrid('getChecked');
	}
	else//其他费用放款
	{
		datagridList=$('#other_Head_PageTable').datagrid('getChecked');
	}
	if(datagridList.length<=0)
	{
		alert("请先选择数据在继续提交操作！");
		return false;
	}
	if(str=='1')
	{
		
		for(var i = 0; i < datagridList.length; i++)
		{
			var PAYMENT_CODECO=datagridList[i].PAYMENT_CODE;
			var PAY_BANK_ACCOUNT=datagridList[i].PAY_BANK_ACCOUNT;
			var PAY_BANK_ADDRESS=datagridList[i].PAY_BANK_ADDRESS;
			var PAYEE_NAME=datagridList[i].PAYEE_NAME;
			var PAY_BANK_NAME=datagridList[i].PAY_BANK_NAME;
			var BANKBILL_PRICE=datagridList[i].BANKBILL_PRICE;
			var ACCBILL_PRICE=datagridList[i].ACCBILL_PRICE;
			if(PAY_BANK_ACCOUNT+"" =='' || PAY_BANK_ACCOUNT ==undefined || PAY_BANK_ACCOUNT =='undefined'){
				alert("请输入付款单号为"+PAYMENT_CODECO+"的收款帐号！");
				return false;
			}
			if(PAYEE_NAME+"" =='' || PAYEE_NAME ==undefined  || PAYEE_NAME =='undefined'){
				alert("请输入付款单号为"+PAYMENT_CODECO+"的收款单位！");
				return false;
			}
			
			if(PAY_BANK_ADDRESS+"" ==''  || PAY_BANK_ADDRESS ==undefined || PAY_BANK_ADDRESS =='undefined'){
				alert("请输入付款单号为"+PAYMENT_CODECO+"的开户行地址！");
				return false;
			}
			
			if(PAY_BANK_NAME+"" =='' || PAY_BANK_NAME ==undefined  || PAY_BANK_NAME =='undefined'){
				alert("请输入付款单号为"+PAYMENT_CODECO+"的开户行行名！");
				return false;
			}
			
			if(ACCBILL_PRICE+"" == '' || ACCBILL_PRICE == undefined  || ACCBILL_PRICE == 'undefined'){
				alert("请输入付款单号为"+PAYMENT_CODECO+"的承兑汇票！");
				return false;
			}
          	
          	if(BANKBILL_PRICE+"" =='' || BANKBILL_PRICE ==undefined || BANKBILL_PRICE =='undefined'){
				alert("请输入付款单号为"+PAYMENT_CODECO+"的银行汇款！");
				return false;
			}
		}
	}
	var selectData = [];
	for(var i = 0; i < datagridList.length; i++)
	{
		var temp={};
		temp.ID=datagridList[i].ID;
		
			if(str=='1')
			{
				temp.BANKBILL_PRICE=datagridList[i].BANKBILL_PRICE;
				temp.ACCBILL_PRICE=datagridList[i].ACCBILL_PRICE;
			}
		temp.SUPPER_ID=datagridList[i].SUPPER_ID;
		selectData.push(temp);
	}
	var dataJson ={selectData:selectData};
	
	var url ="";
	var flag = true;
	if(str=='1')//设备放款
	{
		flag = false;
		url = _basePath+"/payment/payment!eq_PayHead_Status.action?FLAG=1&selectDateHidden="+JSON.stringify(dataJson);
	}
	else if(str=='2')//担保费放款
	{
		url = _basePath+"/payment/payment!db_PayHead_Status.action?FLAG=2&selectDateHidden="+JSON.stringify(dataJson);
	}
	else//其他费用放款
	{
		url = _basePath+"/payment/payment!other_PayHead_Status.action?FLAG=3&selectDateHidden="+JSON.stringify(dataJson);
	}
	if(flag){
		$("#divFrom").append("<form id='formSub' method='post' action='"+url+"'></form>");
		$("#formSub").submit();
	}else{
		jQuery.ajax({
        type: "POST",
        dataType: "json",
		url:url,
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","发起流程成功！","info",function(){
					$.messager.alert("提示",json.msg+json.data,"info",function(){
						window.location=_basePath+"/payment/payment!pay_Head_Eq_Mg.action";
					});
				});	
			}
		}
	});	
	}
}

//删除
function deletePayHead(str)
{
	$("#divFrom").empty();
	var datagridList;
	if(str=='1')
	{
		datagridList=$('#eq_Head_PageTable').datagrid('getChecked');
	}
	else if(str=='2')//担保费放款
	{
		datagridList=$('#db_Head_PageTable').datagrid('getChecked');
	}
	else//其他费用放款
	{
		datagridList=$('#other_Head_PageTable').datagrid('getChecked');
	}
	if(datagridList.length<=0)
	{
		alert("请先选择数据在继续提交操作！");
		return false;
	}
	var selectData = [];
	for(var i = 0; i < datagridList.length; i++)
	{
		var temp={};
		temp.ID=datagridList[i].ID;
		selectData.push(temp);
	}
	var dataJson ={selectData:selectData};
	
	var url ="";
	if(str=='1')//设备放款
	{
		url = _basePath+"/payment/payment!eq_PayHead_De_Status.action?selectDateHidden="+JSON.stringify(dataJson);
	}
	else if(str=='2')//担保费放款
	{
		url = _basePath+"/payment/payment!db_PayHead_De_Status.action?selectDateHidden="+JSON.stringify(dataJson);
	}
	else//其他费用放款
	{
		url = _basePath+"/payment/payment!other_PayHead_De_Status.action?selectDateHidden="+JSON.stringify(dataJson);
	}
	$("#divFrom").append("<form id='formSub' method='post' action='"+url+"'></form>");
	$("#formSub").submit();
}

//核销
function hexiaoPayHead(str)
{
	$("#divFrom").empty();
	var datagridList;
	if(str=='1')
	{
		datagridList=$('#eq_HeadBack_PageTable').datagrid('getChecked');
	}
	else if(str=='2')//担保费放款
	{
		datagridList=$('#db_HeadBack_PageTable').datagrid('getChecked');
	}
	else//其他费用放款
	{
		datagridList=$('#other_HeadBack_PageTable').datagrid('getChecked');
	}
	
	if(datagridList.length<=0)
	{
		alert("请先选择数据在继续提交操作！");
		return false;
	}
	var selectData = [];
	for(var i = 0; i < datagridList.length; i++)
	{
		var temp={};
		temp.ID=datagridList[i].ID;
		if(str=='1')
		{
			var REALITY_DATE=datagridList[i].REALITY_DATE;
			var REALITY_DATE=datagridList[i].REALITY_DATE;
			var PAYMENT_CODE=datagridList[i].PAYMENT_CODE;
			if(REALITY_DATE =='' || REALITY_DATE ==undefined || REALITY_DATE =='undefined'){
				alert("请输入付款单号为"+PAYMENT_CODE+"的实付日期！");
				return false;
			}
			
			var REALITY_BANK_NAME=datagridList[i].REALITY_BANK_NAME;
			if(REALITY_BANK_NAME =='' || REALITY_BANK_NAME ==undefined || REALITY_BANK_NAME =='undefined'){
				alert("请输入付款单号为"+PAYMENT_CODE+"的核销银行！");
				return false;
			}
			
			temp.REALITY_DATE=datagridList[i].REALITY_DATE;
			temp.REALITY_BANK_NAME=datagridList[i].REALITY_BANK_NAME;
		}
		selectData.push(temp);
	}
	var dataJson ={selectData:selectData};
	
	var url ="";
	if(str=='1')//设备放款
	{
		url = _basePath+"/payment/payment!eq_PayHeadSub_Status.action?selectDateHidden="+JSON.stringify(dataJson);
	}
	else if(str=='2')//担保费放款
	{
		url = _basePath+"/payment/payment!db_PayHeadSub_Status.action?selectDateHidden="+JSON.stringify(dataJson);
	}
	else//其他费用放款
	{
		url = _basePath+"/payment/payment!other_PayHeadSub_Status.action?selectDateHidden="+JSON.stringify(dataJson);
	}
	$("#divFrom").append("<form id='formSub' method='post' action='"+url+"'></form>");
	$("#formSub").submit();
}

//驳回
function bhuiPayHead(str)
{
	$("#divFrom").empty();
	var datagridList;
	if(str=='1')
	{
		datagridList=$('#eq_HeadBack_PageTable').datagrid('getChecked');
	}
	else if(str=='2')//担保费放款
	{
		datagridList=$('#db_HeadBack_PageTable').datagrid('getChecked');
	}
	else//其他费用放款
	{
		datagridList=$('#other_HeadBack_PageTable').datagrid('getChecked');
	}
	
	if(datagridList.length<=0)
	{
		alert("请先选择数据在继续提交操作！");
		return false;
	}
	var selectData = [];
	for(var i = 0; i < datagridList.length; i++)
	{
		var temp={};
		temp.ID=datagridList[i].ID;
		if(str=='1')
		{
			temp.REALITY_DATE=datagridList[i].REALITY_DATE;
			temp.REALITY_BANK_NAME=datagridList[i].REALITY_BANK_NAME;
		}
		selectData.push(temp);
	}
	var dataJson ={selectData:selectData};
	
	var url ="";
	if(str=='1')//设备放款
	{
		url = _basePath+"/payment/payment!eq_PayHeadBack_Status.action?selectDateHidden="+JSON.stringify(dataJson);
	}
	else if(str=='2')//担保费放款
	{
		url = _basePath+"/payment/payment!db_PayHeadBack_Status.action?selectDateHidden="+JSON.stringify(dataJson);
	}
	else//其他费用放款
	{
		url = _basePath+"/payment/payment!other_PayHeadBack_Status.action?selectDateHidden="+JSON.stringify(dataJson);
	}
	$("#divFrom").append("<form id='formSub' method='post' action='"+url+"'></form>");
	$("#formSub").submit();
}



//放款核销
function confirmPaymentHead()
{
	$("#divFrom").empty();
	var datagridList = $('#eq_HeadBack_PageTable').datagrid('getChecked');
	if(datagridList.length<=0)
	{
		$.messager.alert("提示","请先选择数据在继续提交操作!");
		return false;
	}
	var selectData = [];
	var REALITY_DATE = $('#REALITY_DATE').datebox("getValue");
	var REALITY_BANK_NAME = $('#REALITY_BANK_NAME').val();
	if(REALITY_DATE == "" || REALITY_BANK_NAME == ""){
		$.messager.alert("提示","请选择实付日期和核销银行!");
		return false;
	}
	for(var i = 0; i < datagridList.length; i++)
	{
		var temp={};
		temp.ID=datagridList[i].ID;
		temp.REALITY_DATE = REALITY_DATE;
		temp.REALITY_BANK_NAME = REALITY_BANK_NAME;
		selectData.push(temp);
	}
	var dataJson ={selectData:selectData};
	if(confirm("确认核销放款单?")){
		$('#confirmId').linkbutton('disable');
		var url = _basePath+"/payment/payment!eq_PayHeadSub_Status.action?selectDateHidden="+JSON.stringify(dataJson);
		$("#divFrom").append("<form id='formSub' method='post' action='"+url+"'></form>");
		$("#formSub").submit();
	}
}

//放款
function goBackPaymentHead()
{
	$("#divFrom").empty();
	var datagridList = $('#eq_HeadBack_PageTable').datagrid('getChecked');
	if(datagridList.length<=0)
	{
		$.messager.alert("提示","请先选择数据在继续提交操作!");
		return false;
	}
	var selectData = [];
	for(var i = 0; i < datagridList.length; i++)
	{
		var temp={};
		temp.ID=datagridList[i].ID;
		selectData.push(temp);
	}
	var dataJson ={selectData:selectData};
	if(confirm("确认驳回放款单?")){
		$('#returnId').linkbutton('disable');
		var url = _basePath+"/payment/payment!eq_PayHeadBack_Status.action?selectDateHidden="+JSON.stringify(dataJson);
		$("#divFrom").append("<form id='formSub' method='post' action='"+url+"'></form>");
		$("#formSub").submit();
	}
}

function test()
{
	var url = _basePath+"/payment/payment!execute.action";
	window.location.href = url;
}

function payMentUForm(ID){
	//debugger ;
	var row = rows[ID] ;
	if (row){
		 var PAYMENT_CODE=row.PAYMENT_CODE;
		 var ID=row.ID;
		 var PAYMENT_MONEY=row.PAYMENT_MONEY;
		 var ACCBILL_PRICE=row.ACCBILL_PRICE;
		 var BANKBILL_PRICE=row.BANKBILL_PRICE;
		 var PAYEE_NAME=row.PAYEE_NAME;
		 var PAY_BANK_ACCOUNT=row.PAY_BANK_ACCOUNT;
		 var PAY_BANK_NAME=row.PAY_BANK_NAME;
		 var PAY_BANK_ADDRESS=row.PAY_BANK_ADDRESS;
		 $("#PAYMENT_CODE_U").val(PAYMENT_CODE);
		 $("#ID_U").val(ID);
		 $("#PAYMENT_MONEY_U").val(PAYMENT_MONEY);
		 $("#ACCBILL_PRICE_U").val(ACCBILL_PRICE);
		 $("#BANKBILL_PRICE_U").val(BANKBILL_PRICE);
		 $("#PAYEE_NAME_U").val(PAYEE_NAME);
		 $("#PAY_BANK_ACCOUNT_U").val(PAY_BANK_ACCOUNT);
		 $("#PAY_BANK_NAME_U").val(PAY_BANK_NAME);
		 $("#PAY_BANK_ADDRESS_U").val(PAY_BANK_ADDRESS);
		 $("#PAYMENT_CASE").dialog('open');
	}else{
		$.messager.alert("提示","请选择一条付款单!");
	}
}

function paseDouble(vale){
	var PAYMENT_MONEY=$("#PAYMENT_MONEY_U").val();
	if(vale=='ACCBILL_PRICE_U'){
		var ACCBILL_PRICE=$("#ACCBILL_PRICE_U").val();
		var BANKBILL_PRICE=jian(PAYMENT_MONEY,ACCBILL_PRICE);
		$("#BANKBILL_PRICE_U").val(BANKBILL_PRICE);
	}
	else{
		var BANKBILL_PRICE=$("#BANKBILL_PRICE_U").val();
		var ACCBILL_PRICE=jian(PAYMENT_MONEY,BANKBILL_PRICE);
		$("#ACCBILL_PRICE_U").val(ACCBILL_PRICE);
	}
}

//精确计算浮点数 网上C的 减法
function jian(arg1,arg2){
    var r1,r2,m,n;
    try{r1=arg1.toString().split(".")[1].length;}catch(e){r1=0;}
    try{r2=arg2.toString().split(".")[1].length;}catch(e){r2=0;}
    m=Math.pow(10,Math.max(r1,r2));
    //last modify by deeka
    //动态控制精度长度
    n=(r1>=r2)?r1:r2;
    return ((arg1*m-arg2*m)/m).toFixed(n);
}


function updatePayment(){
	
	var ID=$("#ID_U").val();
	var PAYMENT_CODE=$("#PAYMENT_CODE_U").val();
	
	var REALITY_DATE=$("#REALITY_DATE_U").datebox("getValue");//实付日期
	var REALITY_BANK_NAME=$("#REALITY_BANK_NAME_U").val();//核销银行 	
	if (REALITY_DATE == ''){
		alert("请选择实付日期!");
		return ;
	}
	
	if (REALITY_BANK_NAME == ''){
		alert("请选择核销银行!");
		return ;
	}
	jQuery.ajax({
		type:"post",
		url:"payment!pay_Head_Eq_Update.action?ID="+ID+"&REALITY_DATE="+REALITY_DATE+"&REALITY_BANK_NAME="+encodeURI(REALITY_BANK_NAME),
		dataType:"json",
		success:function(e){
			if(e.flag){
				alert("修改付款单："+PAYMENT_CODE+"成功！");
				$("#PAYMENT_CASE").dialog('close');
				seach();
			}else{
				alert("修改付款单失败"+json.msg);
			}
		},
	error:function(e){alert("修改付款单失败");}   
	});
}


function updatePaymentHead(){
	
	var ID=$("#ID_U").val();
	var PAYMENT_CODE=$("#PAYMENT_CODE_U").val();
	var PAYMENT_MONEY=$("#PAYMENT_MONEY_U").val();
	var ACCBILL_PRICE=$("#ACCBILL_PRICE_U").val();
	var PAYEE_NAME=$("#PAYEE_NAME_U").val();
	var PAY_BANK_ACCOUNT=$("#PAY_BANK_ACCOUNT_U").val();
	var PAY_BANK_NAME=$("#PAY_BANK_NAME_U").val();
	var PAY_BANK_ADDRESS=$("#PAY_BANK_ADDRESS_U").val();
	var BANKBILL_PRICE=$("#BANKBILL_PRICE_U").val();

	if(jian(PAYMENT_MONEY,accAdd(ACCBILL_PRICE,BANKBILL_PRICE))!=0){
		alert("请确保承兑汇票和银行汇款之和为付款金额！");
		return;
	}
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
		url:"payment!pay_Head_Eq_Update.action?ID="+ID+"&PAYMENT_MONEY="+PAYMENT_MONEY+"&ACCBILL_PRICE="+ACCBILL_PRICE+"&PAYEE_NAME="+encodeURI(PAYEE_NAME)+"&PAY_BANK_ACCOUNT="+encodeURI(PAY_BANK_ACCOUNT)+"&PAY_BANK_NAME="+encodeURI(PAY_BANK_NAME)+"&PAY_BANK_ADDRESS="+encodeURI(PAY_BANK_ADDRESS)+"&BANKBILL_PRICE="+BANKBILL_PRICE,
		dataType:"json",
		success:function(e){
			if(e.flag){
				alert("修改付款单："+PAYMENT_CODE+"成功！");
				$("#PAYMENT_CASE").dialog('close');
				seach();
			}else{
				alert("修改付款单失败"+json.msg);
			}
		},
	error:function(e){alert("修改付款单失败");}   
	});
}



function subPayment(){
	
	var ID=$("#ID_U").val();
	var PAYMENT_CODE=$("#PAYMENT_CODE_U").val();
	var PAYMENT_MONEY=$("#PAYMENT_MONEY_U").val();
	var ACCBILL_PRICE=$("#ACCBILL_PRICE_U").val();
	var PAYEE_NAME=$("#PAYEE_NAME_U").val();
	var PAY_BANK_ACCOUNT=$("#PAY_BANK_ACCOUNT_U").val();
	var PAY_BANK_NAME=$("#PAY_BANK_NAME_U").val();
	var PAY_BANK_ADDRESS=$("#PAY_BANK_ADDRESS_U").val();
	var BANKBILL_PRICE=$("#BANKBILL_PRICE_U").val();
	
	var REALITY_DATE=$("#REALITY_DATE_U").datebox("getValue");//实付日期
	var REALITY_BANK_NAME=$("#REALITY_BANK_NAME_U").val();//核销银行 	
	if(jian(PAYMENT_MONEY,accAdd(ACCBILL_PRICE,BANKBILL_PRICE))!=0){
		alert("请确保承兑汇票和银行汇款之和为付款金额！");
		return;
	}
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
		url:"payment!pay_Head_Eq_Update.action?ID="+ID+"&REALITY_DATE="+REALITY_DATE+"&REALITY_BANK_NAME="+REALITY_BANK_NAME+"&PAYMENT_MONEY="+PAYMENT_MONEY+"&ACCBILL_PRICE="+ACCBILL_PRICE+"&PAYEE_NAME="+encodeURI(PAYEE_NAME)+"&PAY_BANK_ACCOUNT="+encodeURI(PAY_BANK_ACCOUNT)+"&PAY_BANK_NAME="+encodeURI(PAY_BANK_NAME)+"&PAY_BANK_ADDRESS="+encodeURI(PAY_BANK_ADDRESS)+"&BANKBILL_PRICE="+BANKBILL_PRICE,
		dataType:"json",
		success:function(e){
			if(e.flag){
				alert("修改付款单："+PAYMENT_CODE+"成功！");
				$("#PAYMENT_CASE").dialog('close');
				seach();
			}else{
				alert("修改付款单失败"+json.msg);
			}
		},
	error:function(e){alert("修改付款单失败");}   
	});
}

//只能输入数字和小数点(全部清空)
function doubleValue1(ele)
{
	var value=ele.value;
	if(!(/[^\d{1,100}\.]/.test(value)))
	{
		ele.value=value.replace(/[^\d{1,100}\.]/,"");
	}
	else
	{
		ele.value="0";
	}
		
}



/********************************************************以下为设置单元格可修改***齐江龙********************************************************************************/
$.extend($.fn.datagrid.methods, {
	editCell : function(jq, param) {
		return jq.each(function() {
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields', true).concat(
					$(this).datagrid('getColumnFields'));
			for ( var i = 0; i < fields.length; i++) {
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field) {
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for ( var i = 0; i < fields.length; i++) {
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});

var editIndex = undefined;
function endEditing() {
	if (editIndex == undefined) {
		return true;
	}
	$("#eq_HeadBack_PageTable").datagrid("checkRow",editIndex);
	
	if ($('#eq_HeadBack_PageTable').datagrid('validateRow', editIndex)) {
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

/********************************************************以上为设置单元格可修改***齐江龙********************************************************************************/


function savePayMent()
{
	var PROJECT_NUM=$("#PROJECT_NUM").val();
	if(PROJECT_NUM<=0)
	{
		alert("请先选择需要保存的数据在继续保存操作！");
		return false;
	}
	
	var BEGGIN_DATE=$("input[name='BEGGIN_DATE']").val();
	if(BEGGIN_DATE == "" || BEGGIN_DATE == undefined || BEGGIN_DATE.length<=0){
		alert("请输入付款日期，在进行保存！");
		return false;
	}
	
	var selectData = [];
	$('input:checkbox[name="checkOp"]:checked').each(function(){  
		var temp={};
		temp.payMent_ID=$(this).attr("payMent_ID");
		selectData.push(temp);
	}); 
	var dataJson ={selectData:selectData};
	$("#selectDateHidden").val(JSON.stringify(dataJson));
	$("#formsubmt").submit();
}