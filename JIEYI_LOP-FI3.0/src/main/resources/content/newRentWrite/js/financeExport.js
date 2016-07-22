$(document).ready(function(){
	$("#cyberBank_C_PageTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		fit:true,
		toolbar:'#pageForm',
		url:'financeExport!query.action',
		columns:[[
		    
		          	{field:'FGS',title:'子公司',width:150},
		          	{field:'MD',title:'门店',width:150},
		          	{field:'QYZT',title:'签约主体',width:150},
		          	{field:'LEASE_CODE',title:'合同编号',width:150},
		          	{field:'HTBB',title:'合同版本',width:150},
		          	{field:'NAME',title:'承租人',width:150},
		          	{field:'FIRSTDATE',title:'首次还款日',width:150},
		          	{field:'ENDDATE',title:'还款截止日',width:150},
		          	{field:'YZ',title:'月租金',width:150},
		          	{field:'BJ',title:'车辆本金',width:150},
//		          	{field:'GLF',title:'租车服务费',width:150},
//		          	{field:'LX',title:'交易服务费',width:150},
		          	{field:'LX',title:'租车服务费',width:150},
		          	{field:'SXF',title:'交易服务费',width:150},
		          	{field:'GLF',title:'管理费',width:150},
		          	{field:'KKJE',title:'本次扣款金额',width:150},
		          	{field:'BANK_CUSTNAME',title:'开户人',width:150},
		          	{field:'BANK_ACCOUNT',title:'银行账号',width:150},
		          	{field:'BANK_NAME',title:'开户行',width:150},
		          	{field:'ID_CARD_NO',title:'身份证号',width:100},
		          	{field:'TEL_PHONE',title:'电话',width:100},
		          	{field:'RCEIVEDATE',title:'应还日期',width:100},
		          	{field:'QC',title:'应还期次',width:100},
		        	{field:'PLATFORM_TYPE',title:'业务类型',width:100},
		         ]]
	});
});
//导出


function exportData(){
	$("#divFrom").empty();
	var PLAN_START_DATE=$("input[name='PLAN_START_DATE']").val();
	var CUST_NAME=$("input[name='CUST_NAME']").val();
	var LEASE_CODE=$("input[name='LEASE_CODE']").val();
	var YW_TYPE=$("select[name='YW_TYPE']").find("option:selected").val();//开户行
	
	if(PLAN_START_DATE==null || PLAN_START_DATE ==""){
		alert("应还日期必须选择");
		return false;
	}
	var exportType="";
	var IDS="";
	var IDS1="";
	var zuJin="";
	var weiYueJin="";
	var strUrl="";
	var datagridList=$('#cyberBank_C_PageTable').datagrid('getChecked');
		if(datagridList.length<=0)
		{
			exportType="all";
			strUrl=_basePath+"/newRentWrite/financeExport!excelUpload.action?exportType="+exportType+"&PLAN_START_DATE="+PLAN_START_DATE+"&CUST_NAME="+CUST_NAME+"&LEASE_CODE="+LEASE_CODE+"&YW_TYPE="+YW_TYPE;

		}else{
//			exportType="select";
//			var selectData = [];
//			for(var i = 0; i < datagridList.length; i++)
//			{
//				if(i==0){
//					IDS=datagridList[i].ID;
//				}
//				else{
//					IDS=IDS+","+datagridList[i].ID;
//				}
//			}
//			strUrl=_basePath+"/newRentWrite/financeExport!query.action?UPLOAD_NUMBER="+UPLOAD_NUMBER+"&exportType="+exportType+"&IDS="+IDS+"&PLAN_START_DATE="+PLAN_START_DATE;
		}
		$.messager.confirm("提示","您确认要导出数据吗？",function(a){
			if(a){
				$("#divFrom").append("<form id='formSubmit' method='post' action='"+strUrl+"'></form>");
				$("#formSubmit").submit();
			}
		});
	
}



