$(document).ready(function(){

	$("#pageTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:true,		
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		url:'LeaseApplication!toDeliverAjaxData.action',
		columns:[[
					{field:'AA',width:10,align:'center',title:'发货申请单',formatter:function(value,rowData,rowIndex){
							return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='FHVIEWAPP(" + JSON.stringify(rowData) + ")'>查看</a> &nbsp;";
						}},
					{field:'CC',width:15,align:'center',title:'发货通知单',formatter:function(value,rowData,rowIndex){
						return "<a href='javascript:void(0)'  onclick='downFile("+JSON.stringify(rowData)+")'>导出</a> &nbsp;";
					}},
					{field:'BB',width:10,align:'center',title:'验收单',formatter:function(value,rowData,rowIndex){
						return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='YSVIEWAPP(" + JSON.stringify(rowData) + ")'>查看</a> &nbsp;";
					}},
		          	{field:'SENDNOTICEID',hidden:true},
		          	{field:'PAY_ID',hidden:true},
		          	{field:'URL',hidden:true},
		          	{field:'CUST_NAME',title:'客户名称',width:20,align:'center'},
		          	{field:'PRO_CODE',title:'项目编号',width:15,align:'center'},
		          	{field:'LEASE_CODE',title:'融资租赁合同号',width:15,align:'center'},
		          	{field:'PAYLIST_CODE',title:'支付表编号',width:20,align:'center'},
		          	{field:'SEND_LEASE_CODE',title:'发货单编号',width:15,align:'center'},
		          	{field:'RECEIVE_DATE',title:'实际收货时间',width:15,align:'center'},
		          	{field:'COUNTEQ',title:'品牌数量',width:10,align:'center'},
		          	{field:'EQTOTALPRICE',title:'品牌金额',width:10,align:'center'}
		          	
		         ]]
	});
});


function downFile(row){	
	var filPath = row.URL;
	var payId = row.PAY_ID ;
	if(filPath==null || filPath==undefined || typeof(filePath)==undefined){
			$.messager.alert('提示','发货通知单文件不存在');
			return;
	}
	
	$.ajax({
	   url: "LeaseApplication!downReceiptFileMsg.action?filPath="+filPath+"&PAY_ID="+payId,
	   dataType :"json",
	   success: function(json){
	   
	   	//alert("flag = " + json.flag +" data = " + json.data ) ;
	     if(!json.flag){
	     	$.messager.alert('提示',json.msg);
	     }
	   }
	});
	//window.location.href = "LeaseApplication!downReceiptFileMsg.action?filPath="+filPath+"&PAY_ID="+payId;
}

function FHVIEWAPP(row){
	var PAY_ID=row.PAY_ID;
	var SEND_LEASE_CODE=row.SEND_LEASE_CODE;
	top.addTab(SEND_LEASE_CODE+"查看",_basePath+"/leaseApplication/LeaseApplication!doViewDeliveryProductMsg.action?PAY_ID="+PAY_ID);
}

function YSVIEWAPP(row){
	var PAY_ID=row.PAY_ID;
	var SEND_LEASE_CODE=row.SEND_LEASE_CODE;
	top.addTab(SEND_LEASE_CODE+"验收单查看",_basePath+"/leaseApplication/LeaseApplication!receiptProductMsg.action?PAY_ID="+PAY_ID);
}