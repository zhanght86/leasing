$(document).ready(function(){
	$("#PAYMENT_CASE").dialog('close');
});

function dd(){
$("#fund_Back_PageTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		fit:true,
		//fitColumns:true,
		pageSize:50,
		toolbar:'#pageForm',
		url:'rentWriteVinual!query_Back_DUN_SUPP_Page.action',
		onSelect: function(rowIndex, rowData){
			onChangeSelect();
		},
		onUnselect: function(rowIndex, rowData){
			onChangeSelect();
		},
		onSelectAll: function(rowIndex, rowData){
			onChangeSelect();
		},
		onUnselectAll: function(rowIndex, rowData){
			onChangeSelect();
		},
		frozenColumns:[[
		    		    {field:'ID',checkbox:true,width:10},
		    			{field:'aaa',title:'操作',width:50,align:'center',formatter:function(value,rowData,rowIndex){
		    				  return "<a href='javascript:void(0);' onclick=payMentUForm(" + JSON.stringify(rowData) + ")>修改</a>";  
		    			}}
		    		]],
		columns:[[
		          	{field:'LEASE_CODE',title:'融资租赁合同号',width:100,align:'center'},
	                {field:'CUSTNAME',title:'客户名称',width:150,align:'center'},
		          	{field:'COMPANY_NAME',title:'厂商',width:150,align:'center'},
		          	{field:'SUPPLIERS_NAME',title:'经销商',width:150,align:'center'},
		          	{field:'PRODUCT_NAME',title:'品牌',width:150,align:'center'},
		          	{field:'PAYLIST_CODE',title:'支付表号',width:150,align:'center'},
		          	{field:'BEGINNING_NAME',title:'款项名称',width:100,align:'center'},
		          	{field:'BEGINNING_NUM',title:'期次',width:50,align:'center'},
		          	{field:'BEGINNING_RECEIVE_DATA',title:'计划收取日期',width:100,align:'center'},
		       	  	{field:'VINUAL_RECE',title:'应收违约金',width:100,align:'center'},
		          	{field:'PENALTY_PAID',title:'实收违约金',width:100,align:'center'},
		          	{field:'BACK_MONEY_RECE',title:'应退款金额',width:100,align:'center'},
		          	{field:'VINUAL_BACK_MONEY',title:'实退款金额',width:100,align:'center'},
		          	{field:'PAYEE_NAME',title:'收款单位',width:200,align:'center'},
		          	{field:'PAY_BANK_NAME',title:'开户行行名',width:200,align:'center'},
		          	{field:'PAY_BANK_ADDRESS',title:'开户行所在地',width:200,align:'center'},
		          	{field:'PAY_BANK_ACCOUNT',title:'收款帐号',width:200,align:'center'},
		          	{field:'ITEM_FLAG',hidden:true},
		          	{field:'CUST_ID',hidden:true},
		          	{field:'SUP_ID',hidden:true},
		          	{field:'OVERDUE_ID',hidden:true}
		         ]]
	});

}

function onChangeSelect()
{
	var datagridList=$("#fund_Back_PageTable").datagrid('getSelections');
	var pages=$(".pagination-num").val();
	var rowss=$(".pagination-page-list").val();
	
	var BEGINNING_MONEYAll=0;
	var NUM=0;
	
	for(var i = 0; i < datagridList.length; i++)
	{
		var jj=datagridList[i].ROWNO-(pages-1)*rowss;
		if(!$("input[type='checkbox']")[jj].disabled){
			var BEGINNING_MONEY=datagridList[i].VINUAL_BACK_MONEY;
			BEGINNING_MONEYAll=fomatFloat(accAdd(BEGINNING_MONEYAll,BEGINNING_MONEY),2);
			NUM++;
		}
	}
	$("#FI_PROJECT_NUM").val(NUM);
	$("#FI_PAY_MONEY").val(BEGINNING_MONEYAll);
}

function ISBack_Submit(){
	$("#divFrom").empty();
	var datagridList=$('#fund_Back_PageTable').datagrid('getChecked');
	if(datagridList.length<=0)
	{
		alert("请先选择数据在继续申请操作！");
		return false;
	}
	
	for(var i = 0; i < datagridList.length; i++)
	{
		var PAYLIST_CODE=datagridList[i].PAYLIST_CODE;
		var BEGINNING_NUM=datagridList[i].BEGINNING_NUM;
		var PAY_BANK_ACCOUNT=datagridList[i].PAY_BANK_ACCOUNT;
		var PAY_BANK_ADDRESS=datagridList[i].PAY_BANK_ADDRESS;
		var PAYEE_NAME=datagridList[i].PAYEE_NAME;
		var PAY_BANK_NAME=datagridList[i].PAY_BANK_NAME;
		var VINUAL_BACK_MONEY=datagridList[i].VINUAL_BACK_MONEY;
		
		
		if(PAY_BANK_ACCOUNT+"" =='' || PAY_BANK_ACCOUNT ==undefined || PAY_BANK_ACCOUNT =='undefined'){
			alert("请输入"+PAYLIST_CODE+"第"+BEGINNING_NUM+"期的收款帐号！");
			return false;
		}
		if(PAYEE_NAME+"" =='' || PAYEE_NAME ==undefined  || PAYEE_NAME =='undefined'){
			alert("请输入"+PAYLIST_CODE+"第"+BEGINNING_NUM+"期的收款单位！");
			return false;
		}
		
		if(PAY_BANK_ADDRESS+"" ==''  || PAY_BANK_ADDRESS ==undefined || PAY_BANK_ADDRESS =='undefined'){
			alert("请输入"+PAYLIST_CODE+"第"+BEGINNING_NUM+"期的开户行地址！");
			return false;
		}
		
		if(PAY_BANK_NAME+"" =='' || PAY_BANK_NAME ==undefined  || PAY_BANK_NAME =='undefined'){
			alert("请输入"+PAYLIST_CODE+"第"+BEGINNING_NUM+"期的开户行行名！");
			return false;
		}
		
		if(VINUAL_BACK_MONEY+"" =='' || VINUAL_BACK_MONEY ==undefined  || VINUAL_BACK_MONEY =='undefined'){
			alert("请输入"+PAYLIST_CODE+"第"+BEGINNING_NUM+"期的实退金额！");
			return false;
		}
		
		if(parseFloat(VINUAL_BACK_MONEY)<=0){
			return alert("请输入"+PAYLIST_CODE+"第"+BEGINNING_NUM+"期的实退金额！");
		}
	}
	
	$.messager.confirm("提示","您确定提交选中的数据？",function(flag){
		if(flag){
			var selectData = [];
			for(var i = 0; i < datagridList.length; i++)
			{
				var temp={};
				temp.OVERDUE_ID=datagridList[i].OVERDUE_ID;
				selectData.push(temp);
			}
			var dataJson ={selectData:selectData};
			var url = _basePath+"/rentWrite/rentWriteVinual!fund_Back_DUN_Submit_SUPP.action?selectDateHidden="+JSON.stringify(dataJson);
			$("#divFrom").append("<form id='formRoll' method='post' action='"+url+"'></form>");
			$("#formRoll").submit();
		}
	});
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

function fomatFloat(src,pos){       
    return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);   
}

function payMentUForm(row){
	if (row){
		 var PAYLIST_CODE=row.PAYLIST_CODE;
		 var SUPP_ID=row.SUP_ID;
		 var BEGINNING_NUM=row.BEGINNING_NUM;
		 
		 var BACK_MONEY_RECE=row.BACK_MONEY_RECE;
		 var VINUAL_BACK_MONEY=row.VINUAL_BACK_MONEY;
		 var BANKBILL_PRICE=row.BANKBILL_PRICE;
		 var PAYEE_NAME=row.PAYEE_NAME;
		 var PAY_BANK_ACCOUNT=row.PAY_BANK_ACCOUNT;
		 var PAY_BANK_NAME=row.PAY_BANK_NAME;
		 var PAY_BANK_ADDRESS=row.PAY_BANK_ADDRESS;
		 var OVERDUE_ID=row.OVERDUE_ID;
		 
		 $("#PAYLIST_CODE_U").val(PAYLIST_CODE);
		 $("#SUPP_ID_U").val(SUPP_ID);
		 $("#OVERDUE_ID_U").val(OVERDUE_ID);
		 $("#BEGINNING_NUM_U").val(BEGINNING_NUM);
		 $("#BACK_MONEY_RECE_U").val(BACK_MONEY_RECE);
		 $("#VINUAL_BACK_MONEY_U").val(VINUAL_BACK_MONEY);
		 
		 $("#PAYEE_NAME_U").val(PAYEE_NAME);
		 $("#PAY_BANK_ACCOUNT_U").val(PAY_BANK_ACCOUNT);
		 $("#PAY_BANK_NAME_U").val(PAY_BANK_NAME);
		 $("#PAY_BANK_ADDRESS_U").val(PAY_BANK_ADDRESS);
		 $("#PAYMENT_CASE").dialog('open');
	}else{
		$.messager.alert("请选择一条数据!");
	}
}

function Back_Money_Com(){
	var BACK_MONEY_RECE=$("#BACK_MONEY_RECE_U").val();
	var VINUAL_BACK_MONEY=$.trim($("#VINUAL_BACK_MONEY_U").val());
	if(VINUAL_BACK_MONEY.length<=0 || VINUAL_BACK_MONEY==null || VINUAL_BACK_MONEY==undefined){
		$("#VINUAL_BACK_MONEY_U").val("0");
		return $.messager.alert('警告','请填写实际退款金额！');
	}
	
	if(parseFloat(VINUAL_BACK_MONEY)<0){
		$("#VINUAL_BACK_MONEY_U").val("0");
		return $.messager.alert('警告','您输入的金额不能小于0，请重新输入金额！');
	}
	
	if(parseFloat(VINUAL_BACK_MONEY)>parseFloat(BACK_MONEY_RECE)){
		$("#VINUAL_BACK_MONEY_U").val("0");
		return $.messager.alert('警告','您输入的金额应该<='+BACK_MONEY_RECE+'应退款金额，请重新输入金额！');
	}
}

function fomatFloat(src,pos){       
    return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);       
} 

function updatePaymentHead(){
	
	var SUPP_ID=$("#SUPP_ID_U").val();
	var PAYEE_NAME=$("#PAYEE_NAME_U").val();
	var PAY_BANK_ACCOUNT=$("#PAY_BANK_ACCOUNT_U").val();
	var PAY_BANK_NAME=$("#PAY_BANK_NAME_U").val();
	var PAY_BANK_ADDRESS=$("#PAY_BANK_ADDRESS_U").val();
	var BANKBILL_PRICE=$("#BANKBILL_PRICE_U").val();
	var OVERDUE_ID=$("#OVERDUE_ID_U").val();
	var VINUAL_BACK_MONEY=$("#VINUAL_BACK_MONEY_U").val();
	
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
		url:_basePath+"/rentWrite/rentWriteVinual!supp_Bank_DUN_UP.action?OVERDUE_ID="+OVERDUE_ID+"&VINUAL_BACK_MONEY="+VINUAL_BACK_MONEY+"&SUPP_ID="+SUPP_ID+"&PAYEE_NAME="+encodeURI(PAYEE_NAME)+"&PAY_BANK_ACCOUNT="+encodeURI(PAY_BANK_ACCOUNT)+"&PAY_BANK_NAME="+encodeURI(PAY_BANK_NAME)+"&PAY_BANK_ADDRESS="+encodeURI(PAY_BANK_ADDRESS),
		dataType:"json",
		success:function(e){
			if(e.flag){
				alert("修改成功！");
				$("#PAYMENT_CASE").dialog('close');
				seach();
			}else{
				alert("修改失败"+json.msg);
			}
		},
	error:function(e){alert("修改失败");}
	});
}