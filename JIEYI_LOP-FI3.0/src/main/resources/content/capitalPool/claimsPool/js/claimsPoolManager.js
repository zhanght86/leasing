$(document).ready(function(){
	$("#PAYMENT_CASE").dialog('close');
});

function dd(){
$("#claims_PageTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		fit:true,
		pageSize:50,
		toolbar:'#pageForm',
		url:'ClaimsPool!claimsPool_C_PageAjax.action',
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
		    		    {field:'POOL_ID',checkbox:true,width:10},
		    			{field:'aaa',title:'操作',width:50,formatter:function(value,rowData,rowIndex){
		    				  return "<a href='javascript:void(0);' onclick=payMentUForm(" + JSON.stringify(rowData) + ")>修改</a>";  
		    			}}
		    		]],
		columns:[[
		          	{field:'STATUS_NAME',title:'项目状态',width:100},
		          	{field:'PRO_CODE',title:'项目编号',width:100},
	                {field:'CUSTNAME',title:'客户名称',width:150},
		          	{field:'COMPANY_NAME',title:'厂商',width:150},
		          	{field:'SUP_SHORTNAME',title:'供应商',width:150},
		          	{field:'PRODUCT_NAME',title:'租赁物',width:150},
		          	{field:'DUNCOUNT',title:'是否逾期',width:70,formatter:function(value,rowData,rowIndex){
		        	  	if(value>0)
		          		{
		          			return "是";
		          		}
		        	  	else
		          		{
		          			return "否";
		          		}
	                  }},
		          	{field:'BASE_MONEY',title:'理赔款',width:150},
		          	{field:'CANUSE_MONEY',title:'剩余理赔款',width:100},
		          	{field:'PAYEE_NAME',title:'收款人',width:100},
		          	{field:'PAY_BANK_NAME',title:'收款方开户行',width:100},
		          	{field:'PAY_BANK_ADDRESS',title:'收款方开户行地址',width:100},
		          	{field:'PAY_BANK_ACCOUNT',title:'收款方开户行卡号',width:100},
		          	{field:'CUST_ID',hidden:true},
		          	{field:'PROJECT_STATUS',hidden:true},
		          	{field:'COMPANY_ID',hidden:true},
		          	{field:'SUP_ID',hidden:true}
		         ]]
	});

}

function onChangeSelect()
{
	var datagridList=$("#claims_PageTable").datagrid('getSelections');
	var pages=$(".pagination-num").val();
	var rowss=$(".pagination-page-list").val();
	
	var PAID_MONEYAll=0;
	var NUM=0;
	
	for(var i = 0; i < datagridList.length; i++)
	{
		var jj=datagridList[i].ROWNO-(pages-1)*rowss;
		if(!$("input[type='checkbox']")[jj].disabled){
			var CANUSE_MONEY=datagridList[i].CANUSE_MONEY;
			PAID_MONEYAll=fomatFloat(accAdd(PAID_MONEYAll,CANUSE_MONEY),2);
			NUM++;
		}
	}
	
	$("#PROJECT_MONEY").val(PAID_MONEYAll);
	$("#PROJECT_NUM").val(NUM);
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
		 var PROJECT_CODE=row.PRO_CODE;
		 var POOL_ID=row.POOL_ID;
		 var CUST_NAME=row.CUSTNAME;
		 var PAYEE_NAME=row.PAYEE_NAME;
		 var PAY_BANK_ACCOUNT=row.PAY_BANK_ACCOUNT;
		 var PAY_BANK_NAME=row.PAY_BANK_NAME;
		 var PAY_BANK_ADDRESS=row.PAY_BANK_ADDRESS;
		 $("#PROJECT_CODE_U").val(PROJECT_CODE);
		 $("#POOL_ID_U").val(POOL_ID);
		 $("#CUST_NAME_U").val(CUST_NAME);
		 $("#PAYEE_NAME_U").val(PAYEE_NAME);
		 $("#PAY_BANK_ACCOUNT_U").val(PAY_BANK_ACCOUNT);
		 $("#PAY_BANK_NAME_U").val(PAY_BANK_NAME);
		 $("#PAY_BANK_ADDRESS_U").val(PAY_BANK_ADDRESS);
		 $("#PAYMENT_CASE").dialog('open');
	}else{
		$.messager.alert("请选择一条保险理赔数据!");
	}
}


function updatePaymentHead(){
	
	var POOL_ID=$("#POOL_ID_U").val();
	var PROJECT_CODE=$("#PROJECT_CODE_U").val();
	var CUST_NAME=$("#CUST_NAME_U").val();
	var PAYEE_NAME=$("#PAYEE_NAME_U").val();
	var PAY_BANK_ACCOUNT=$("#PAY_BANK_ACCOUNT_U").val();
	var PAY_BANK_NAME=$("#PAY_BANK_NAME_U").val();
	var PAY_BANK_ADDRESS=$("#PAY_BANK_ADDRESS_U").val();

	
	if (PAYEE_NAME == ''){
		alert("请输入收款人！");
		return ;
	}
	
	if (PAY_BANK_ACCOUNT == ''){
		alert("请输入收款方开户行卡号！");
		return ;
	}
	
	if (PAY_BANK_NAME == ''){
		alert("请输入收款方开户行！");
		return ;
	}
	
	if (PAY_BANK_ADDRESS == ''){
		alert("请输入收款方开户行地址！");
		return ;
	}
	jQuery.ajax({
		type:"post",
		url:"ClaimsPool!claims_Info_Update.action?POOL_ID="+POOL_ID+"&PAYEE_NAME="+encodeURI(PAYEE_NAME)+"&PAY_BANK_ACCOUNT="+encodeURI(PAY_BANK_ACCOUNT)+"&PAY_BANK_NAME="+encodeURI(PAY_BANK_NAME)+"&PAY_BANK_ADDRESS="+encodeURI(PAY_BANK_ADDRESS),
		dataType:"json",
		success:function(e){
			if(e.flag){
				alert("修改项目："+PROJECT_CODE+"信息成功！");
				$("#PAYMENT_CASE").dialog('close');
				seach();
			}else{
				alert("修改项目："+PROJECT_CODE+"信息失败"+json.msg);
			}
		},
	error:function(e){alert("修改项目："+PROJECT_CODE+"信息失败");}
	});
}

function IS_Claims_Submit(){
	var datagridList=$("#claims_PageTable").datagrid('getSelections');
	var SUP_ID='';
	var SUP_NAME='';
	var COMP_ID='';
	var COMP_NAME='';
	
	var IDS="";
	
	for(var i = 0; i < datagridList.length; i++)
	{
		if(i==0){
			SUP_ID=datagridList[i].SUP_ID;
			IDS=datagridList[i].POOL_ID;
		}
		else{
			var SUPP_ID=datagridList[i].SUP_ID;
			if(SUP_ID != SUPP_ID){
				alert("请选择同一供应商的项目进行操作！");
				return;
			}
			IDS=IDS+","+datagridList[i].POOL_ID;
		}
		
		var PAYEE_NAME=datagridList[i].PAYEE_NAME;
		var PAY_BANK_ACCOUNT=datagridList[i].PAY_BANK_ACCOUNT;
		var PAY_BANK_NAME=datagridList[i].PAY_BANK_NAME;
		var PAY_BANK_ADDRESS=datagridList[i].PAY_BANK_ADDRESS;
		var PRO_CODE=datagridList[i].PRO_CODE;
		
		if (PAYEE_NAME == "" || PAYEE_NAME == undefined || PAYEE_NAME.length<=0){
			alert("请输入项目:"+PRO_CODE+"的收款人！");
			return ;
		}
		
		if (PAY_BANK_ACCOUNT == "" || PAY_BANK_ACCOUNT == undefined || PAY_BANK_ACCOUNT.length<=0){
			alert("请输入项目:"+PRO_CODE+"的收款方开户行卡号！");
			return ;
		}
		
		if (PAY_BANK_NAME == "" || PAY_BANK_NAME == undefined || PAY_BANK_NAME.length<=0){
			alert("请输入项目:"+PRO_CODE+"的收款方开户行！");
			return ;
		}
		
		if (PAY_BANK_ADDRESS == "" || PAY_BANK_ADDRESS == undefined || PAY_BANK_ADDRESS.length<=0){
			alert("请输入项目:"+PRO_CODE+"的收款方开户行地址！");
			return ;
		}
		
		var IS_FLAG=false;
		var DUNCOUNT=datagridList[i].DUNCOUNT;
		var PROJECT_STATUS=datagridList[i].PROJECT_STATUS;
		if(DUNCOUNT==0 || PROJECT_STATUS>20){
			;
		}
		else{
			if(DUNCOUNT>0){
				alert("项目:"+PRO_CODE+"已逾期，不能退款！");
				return;
			}
		}
		
		SUP_NAME=datagridList[i].SUP_SHORTNAME;
		COMP_ID=datagridList[i].COMPANY_ID;
		COMP_NAME=datagridList[i].COMPANY_NAME;
		
	}
	
	$("#IDS_S").val("");
	$("#SUP_NAME_S").val("");
	$("#SUP_ID_S").val("");
	$("#COMP_ID_S").val("");
	$("#COMP_NAME_S").val("");
	
	$("#IDS_S").val(IDS);
	$("#SUP_NAME_S").val(SUP_NAME);
	$("#SUP_ID_S").val(SUP_ID);
	$("#COMP_ID_S").val(COMP_ID);
	$("#COMP_NAME_S").val(COMP_NAME);
	
	$("#formSub").submit();
}