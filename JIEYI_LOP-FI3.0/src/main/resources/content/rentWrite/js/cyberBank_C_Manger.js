$(document).ready(function(){
	var FI_REALITY_BANK_FLAF=$("select[name='bankFlag']").find("option:selected").val();
	var FI_REALITY_BANK="";
	if(FI_REALITY_BANK_FLAF=='1'){
		FI_REALITY_BANK=$("select[name='bankFlag']").find("option:selected").text();
	}
	$("#cyberBank_C_PageTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		fit:true,
		toolbar:'#pageForm',
		url:'rentWrite!cyberBank_C_PageAjax.action',
		queryParams:{"FI_REALITY_BANK" : FI_REALITY_BANK},
		columns:[[
		          	{field:'ID',checkbox:true,width:50},
		          	{field:'CUSTNAME',title:'承租人',width:150},
		          	{field:'ID_CARD_NO',title:'客户身份证号/组织架构',width:150},
		          	{field:'ACCOUNT_NAME',title:'开户名',width:150},
		          	{field:'BANK_NAME',title:'开户银行',width:150},
		          	{field:'BANK_ACCOUNT',title:'客户账号',width:150},
		          	{field:'BEGINNING_MONEY',title:'金额',width:100},
		          	{field:'PRO_CODE',title:'项目编号',width:150},
	                {field:'PAYLIST_CODE',title:'还款计划',width:150}, 
		          	{field:'BEGINNING_NUM',title:'期次',width:50},
		          	{field:'BEGINNING_RECEIVE_DATA',title:'计划收取日期',width:150},
		          	{field:'BEGINNING_NAME',title:'类别',width:70},
		          	{field:'BEGINNING_STATUS',title:'状态',width:70},
		          	{field:'BEGINNING_FALSE_REASON',title:'失败原因',width:170},
		          	{field:'SIGN_FLAG',hidden:true},
		          	{field:'SIGN_NAME',title:'是否签约',width:70,formatter:function(value,rowData,rowIndex){
		          		if(rowData.SIGN_FLAG=='0')
		          		{
		          			return value;
		          		}
		          		else if(rowData.SIGN_FLAG=='1')
		          		{
		          			return value;
		          		}
		          		else
		          		{
		          			return "未签约";
		          		}
	                	  
	                  }},
		          	{field:'ITEM_FLAG',hidden:true},
		          	{field:'CUST_ID',hidden:true},
		          	{field:'CUST_NAME',hidden:true},
		          	{field:'PROJ_ID',hidden:true}
		         ]]
	});
});



function exportExcel(flag){
	$("#divFrom").empty();
	var LOCKTYPE=$("#LOCKTYPE").val();
	if(LOCKTYPE=='2'){
		var uploadType="select";
		if(flag == 'all')
		{
			uploadType="all";
		}
		else{
			var datagridList=$('#cyberBank_C_PageTable').datagrid('getChecked');
			if(datagridList.length<=0)
			{
				alert("请先选择要导出的数据在继续导出操作！");
				return false;
			}
			var selectData = [];
			for(var i = 0; i < datagridList.length; i++)
			{
				var temp={};
				temp.ITEM_FLAG=datagridList[i].ITEM_FLAG;
				temp.PAYLIST_CODE=datagridList[i].PAYLIST_CODE;
				temp.BEGINNING_NUM=datagridList[i].BEGINNING_NUM;
				temp.MONEY=datagridList[i].BEGINNING_MONEY;
				temp.ROW_NUM=i+1;
				selectData.push(temp);
			}
			
			uploadType="select";
		}
		
		$.messager.confirm("提示","您确认要导出数据吗？",function(flag){
			if(flag){
				var url="";
				if(uploadType=="select"){
					url="rentWrite!cyberBank_C_Upload.action?bankFlag="+$("select[name='bankFlag']").val()+"&selectDateHidden="+JSON.stringify(selectData)+"&uploadType="+uploadType;
				}
				else{
					var ACCOUNT_NAME=$("input[name='ACCOUNT_NAME']").val();
					var PROJECT_CODE=$("input[name='PROJECT_CODE']").val();
					var CUST_TYPE=$("select[name='CUST_TYPE']").find("option:selected").val();
					var FEE_TYPE=$("select[name='FEE_TYPE']").find("option:selected").val();
					var SUP_NAME=$("input[name='SUP_NAME']").val();
					var BANK_NAME=$("select[name='BANK_NAME']").find("option:selected").val();
					var PLAN_START_DATE=$("input[name='PLAN_START_DATE']").val();
					var PLAN_END_DATE=$("input[name='PLAN_END_DATE']").val();
					var SIGN_FLAG=$("select[name='SIGN_FLAG']").find("option:selected").val();
					var PRODUCT_NAME=$("select[name='PRODUCT_NAME']").find("option:selected").val();
					var bankFlag=$("select[name='bankFlag']").val();
					var UPLOAD_NUMBER=$("input[name='UPLOAD_NUMBER']").val();
					var BEGINNING_FALSE_REASON=$("input[name='BEGINNING_FALSE_REASON']").val();
					var FI_REALITY_BANK_FLAF=$("select[name='bankFlag']").find("option:selected").val();
					var FI_REALITY_BANK="";
					if(FI_REALITY_BANK_FLAF=='1'){
						FI_REALITY_BANK=$("select[name='bankFlag']").find("option:selected").text();
					}
					url="rentWrite!cyberBank_C_Upload.action?ACCOUNT_NAME="+ACCOUNT_NAME+"&BEGINNING_FALSE_REASON="+BEGINNING_FALSE_REASON+"&PROJECT_CODE="+PROJECT_CODE+"&CUST_TYPE="+CUST_TYPE+"&UPLOAD_NUMBER="+UPLOAD_NUMBER+"&FEE_TYPE="+FEE_TYPE+"&SUP_NAME="+SUP_NAME+"&BANK_NAME="+BANK_NAME+"&PLAN_START_DATE="+PLAN_START_DATE+"&PLAN_END_DATE="+PLAN_END_DATE+"&SIGN_FLAG="+SIGN_FLAG+"&PRODUCT_NAME="+PRODUCT_NAME+"&bankFlag="+$("select[name='bankFlag']").val()+"&uploadType="+uploadType+"&FI_REALITY_BANK="+FI_REALITY_BANK;
				}
				$("#divFrom").append("<form id='formSubmit' method='post' action='"+url+"'></form>");
				$("#formSubmit").submit();
			}
		});
		
		
	}
	else{
		$.messager.alert("锁定提示","请先锁定数据在做导出操作！");
	}
	
}


function toRefer(){
	$.ajax({		
		url:_basePath+"/rentWrite/rentWrite!CreateJoinFundDate.action",
	    type:'post',
	    dataType:'json',
	    success:function(json){
		 if(json.flag == true){
			 $.messager.alert("提示","同步成功！！");	
			//页面刷新
		 }else{
			 $.messager.alert("提示","同步失败！！");			 
		 }
		
		}
	});
}

function LOCKTYPENO(){
	$("#LOCKTYPENO").attr("style","display:none");
	$("#LOCKTYPEYES").attr("style","");
	$.ajax({
		url:_basePath+"/rentWrite/rentWrite!updateLockType.action?LOCKTYPE=2",
	    type:'post',
	    dataType:'json',
	    success:function(json){
		    if(json.flag == true){
		    	$("#LOCKTYPE").val("2");
		    	$.messager.alert("锁定提示","已锁定！");
		    	seach();
		    }
	    }
	});
	
	
}

function LOCKTYPEYES(){
	$("#LOCKTYPEYES").attr("style","display:none");
	$("#LOCKTYPENO").attr("style","");
	$.ajax({
		url:_basePath+"/rentWrite/rentWrite!updateLockType.action?LOCKTYPE=1",
	    type:'post',
	    dataType:'json',
	    success:function(json){
		    if(json.flag == true){
		    	$("#LOCKTYPE").val("1");
		    	$.messager.alert("锁定提示","已解锁！");
		    	seach();
		    }
	    }
	});
}
