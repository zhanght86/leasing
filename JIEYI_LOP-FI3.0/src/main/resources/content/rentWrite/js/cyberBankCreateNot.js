$(document).ready(function(){

	$("#cyberBankCreateNotPageTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		toolbar:'#pageForm',
		url:'rentNotWrite!cyberCreateNot_PageAjax.action',
		columns:[[
		          	{field:'ID',checkbox:true,width:50},
		          	{field:'ID_CARD_NO',title:'客户身份证号',width:150},
		          	{field:'ACCOUNT_NAME',title:'开户名',width:100},
		          	{field:'BANK_NAME',title:'开户银行',width:100},
		          	{field:'BANK_ACCOUNT',title:'客户账号',width:100},
		          	{field:'BEGINNING_MONEY',title:'金额',width:100},
		          	{field:'PRO_CODE',title:'项目编号',width:100,formatter:function(value,rowData,rowIndex){
	                	  return "<a href='#'>"+rowData.PRO_CODE+"</a>";
	                  }},
	                  {field:'PAYLIST_CODE',title:'还款计划',width:110}, 
		          	{field:'BEGINNING_NUM',title:'期次',width:50},
		          	{field:'BEGINNING_RECEIVE_DATA',title:'计划还款日',width:100},
		          	{field:'BEGINNING_NAME',title:'类别',width:70},
		          	{field:'SUPPLIERS_NAME',title:'供应商',width:150},
		          	{field:'BEGINNING_STATUS',title:'状态',width:100},
		          	{field:'BEGINNING_FALSE_REASON',title:'失败原因',width:170},
		          	{field:'ITEM_FLAG',hidden:true},
		          	{field:'CUSTNAME',hidden:true},
		          	{field:'CUST_ID',hidden:true},
		          	{field:'CUST_NAME',hidden:true}
		         ]]
	});
});

function toZuoFeiPayment()
{
	var datagridList=$('#cyberBankCreateNotPageTable').datagrid('getChecked');
	if(datagridList.length<=0)
	{
		alert("请先选择要数据在继续作废操作！");
		return false;
	}
	$.messager.confirm("提示","您确认要作废选中的数据？",function(flag){
		if(flag){
			var getDetailData = [];
			for(var i = 0; i<datagridList.length; i++) {
				var temp = {};
				temp.FUND_ID = datagridList[i].ID;
				getDetailData.push(temp);
			}
			
			var data = {};
			data["getDetailData"] = getDetailData;
			
			$.ajax({
				url:_basePath+"/rentWrite/rentWrite!toRemovePayment.action",
			    type:'post',
			    data:'data='+JSON.stringify(data),
			    dataType:'json',
			    success:function(json){
				    if(json.flag == true){
				    	$.messager.alert("作废","付款单作废成功！");
				    	//页面刷新
				    	$('#cyberBankCreateNotPageTable').datagrid('reload');
				    }else {
				    	$.messager.alert("作废","付款单作废失败！");
				    	//页面刷新
				    	$('#cyberBankCreateNotPageTable').datagrid('reload');
				    }
			    }
			});
		}
	});
}


function exportNotExcel(flag){
	$("#uploadtest").empty();
		var uploadType="select";
		if(flag == 'all')
		{
			uploadType="all";
		}
		else{
			var datagridList=$('#cyberBankCreateNotPageTable').datagrid('getChecked');
			if(datagridList.length<=0)
			{
				alert("请先选择要导出的数据在继续导出操作！");
				return false;
			}
			var selectData = [];
			for(var i = 0; i < datagridList.length; i++)
			{
				var temp={};
				temp.ID=datagridList[i].ID;
				temp.ROW_NUM=i+1;
				selectData.push(temp);
			}
			
			uploadType="select";
		}
		
		$.messager.confirm("提示","您确认要导出数据吗？",function(flag1){
			if(flag1){
				var url="";
				if(uploadType=="select"){
					url=_basePath+"/rentWrite/rentNotWrite!cyberBankNot_C_Upload.action?bankFlag="+$("select[name='bankFlag']").val()+"&selectDateHidden="+JSON.stringify(selectData)+"&uploadType="+uploadType;
				}
				else{
					var ACCOUNT_NAME=$("input[name='ACCOUNT_NAME']").val();
					var PROJECT_CODE=$("input[name='PROJECT_CODE']").val();
					var CUST_TYPE=$("select[name='CUST_TYPE']").find("option:selected").val();
					var SUP_NAME=$("input[name='SUP_NAME']").val();
					var BANK_NAME=$("select[name='BANK_NAME']").find("option:selected").val();
					var PLAN_START_DATE=$("input[name='PLAN_START_DATE']").val();
					var PLAN_END_DATE=$("input[name='PLAN_END_DATE']").val();
					var PRODUCT_NAME=$("select[name='PRODUCT_NAME']").find("option:selected").val();
					var bankFlag=$("select[name='bankFlag']").val();
					var UPLOAD_NUMBER=$("input[name='UPLOAD_NUMBER']").val();
					var BEGINNING_FALSE_REASON=$("input[name='BEGINNING_FALSE_REASON']").val();
					var FI_REALITY_BANK_FLAF=$("select[name='bankFlag']").find("option:selected").val();
					var FI_REALITY_BANK="";
					if(FI_REALITY_BANK_FLAF=='1'){
						FI_REALITY_BANK=$("select[name='bankFlag']").find("option:selected").text();
					}
					url=_basePath+"/rentWrite/rentNotWrite!cyberBankNot_C_Upload.action?ACCOUNT_NAME="+ACCOUNT_NAME+"&BEGINNING_FALSE_REASON="+BEGINNING_FALSE_REASON+"&PROJECT_CODE="+PROJECT_CODE+"&CUST_TYPE="+CUST_TYPE+"&UPLOAD_NUMBER="+UPLOAD_NUMBER+"&SUP_NAME="+SUP_NAME+"&BANK_NAME="+BANK_NAME+"&PLAN_START_DATE="+PLAN_START_DATE+"&PLAN_END_DATE="+PLAN_END_DATE+"&PRODUCT_NAME="+PRODUCT_NAME+"&bankFlag="+$("select[name='bankFlag']").val()+"&uploadType="+uploadType+"&FI_REALITY_BANK="+FI_REALITY_BANK;
				}
				$("#uploadtest").append("<form id='formSubmit' method='post' action='"+url+"'></form>");
				$("#formSubmit").submit();
			}
		});
}
