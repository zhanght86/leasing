$(document).ready(function(){
	
	$("#payment_apply_list_PageTable").datagrid({
		url:_basePath+"/payment/PaymentApply!getPaymentApplyListPage.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		toolbar:'#pageForm',
		columns:[[
						    {field:'ID',hidden:true},
							{field:'APPLY_DATE',title:'确认日期',width:200,align:'center'},
							{field:'UNIT',title:'持卡人',width:150,align:'center'},
							{field:'LEASE_CODE',title:'合同编号',width:150,align:'center'},
							{field:'SUB_COMPANY_NAME',title:'分公司',width:150,align:'center'},
							{field:'STORE_NAME',title:'门店',width:150,align:'center'},
							{field:'CUST_NAME',title:'客户',width:100,align:'center'},
							{field:'PROJECT_CODE',title:'项目编号',width:150,align:'center'},
							{field:'APPLY_MONEY_SUM',title:'金额',width:100,align:'center'},
							{field:'BANK',title:'放款银行',width:100,align:'center'},
							{field:'ACCOUNT_TYPE',title:'账户类型',width:50,align:'center'},
							{field:'ACCOUNT',title:'银行账号',width:150,align:'center'},
							{field:'BEGGIN_DATE',title:'放款日期',width:150,align:'center'},
						{field:'STATUS',title:'是否放款',width:100,align:'center'},
		         ]]
	});
});

function seach(){
	var CUST_NAME=$("input[name='CUST_NAME']").val();
	var SUB_COMPANY_NAME=$("input[name='SUB_COMPANY_NAME']").val();
	var APPLY_DATE1=$("input[name='APPLY_DATE1']").val();
	var APPLY_DATE2=$("input[name='APPLY_DATE2']").val();
	var STATUS=$("select[name='STATUS']").find("option:selected").val();
	var STATUS1;
	var STATUS2;
	var STATUS3;
	if(STATUS==1){
		STATUS1=STATUS;
	}else if(STATUS==2){
		STATUS2=STATUS;
	}else if(STATUS==3){
		STATUS3=STATUS;
	}
	$('#payment_apply_list_PageTable').datagrid('load', {"CUST_NAME":CUST_NAME,"SUB_COMPANY_NAME":SUB_COMPANY_NAME,"APPLY_DATE1":APPLY_DATE1,"APPLY_DATE2":APPLY_DATE2,"STATUS1":STATUS1,"STATUS2":STATUS2,"STATUS3":STATUS3});
}

function exportExcel(){

	$("#divFrom").empty();
	var CUST_NAME=$("input[name='CUST_NAME']").val();
	var SUB_COMPANY_NAME=$("input[name='SUB_COMPANY_NAME']").val();
	var APPLY_DATE1=$("input[name='APPLY_DATE1']").val();
	var APPLY_DATE2=$("input[name='APPLY_DATE2']").val();
	var STATUS=$("select[name='STATUS']").find("option:selected").val();
	var STATUS1;
	var STATUS2;
	var STATUS3;
	if(STATUS==1){
		STATUS1=STATUS;
	}else if(STATUS==2){
		STATUS2=STATUS;
	}
	var url=_basePath+"/payment/PaymentApply!exportPaymentApplyList.action?CUST_NAME="+CUST_NAME+"&SUB_COMPANY_NAME="+SUB_COMPANY_NAME
	               +"&APPLY_DATE1="+APPLY_DATE1+"&APPLY_DATE2="+APPLY_DATE2+"&STATUS1="+STATUS1+"&STATUS2="+STATUS2;
	$("#divFrom").append("<form id='formSubmit' method='post' action='"+url+"'></form>");
	$("#formSubmit").submit();
}

/**
 * 清空按钮
 * @return
 */
function emptyData(){
	$('#pageForm').form('clear');
}
