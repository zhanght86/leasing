$(document).ready(function(){

	$("#pageTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		//fitColumns:true,
		toolbar:'#pageForm',
		url:'Insurance!getinsureEquipmentList.action',
		columns:[[
          	{field:'ID',hidden:true},
          	{field:'INSU_NAME',title:'客户名称',width:100,align:'center'},
          	{field:'LEASE_CODE',title:'融资租赁合同编号',width:150,align:'center'},
          	{field:'FLAG',title:'业务类型',width:100,align:'center'},
          	{field:'COMPANY_NAME',title:'厂商',width:100,align:'center'},
          	{field:'SUPPLIERS_NAME',title:'经销商',width:100,align:'center'},
          	{field:'PRODUCT_NAME',title:'品牌名称',width:100,align:'center'},
          	{field:'SPEC_NAME',title:'品牌型号',width:100,align:'center'},
          	{field:'No',title:'车牌号',width:100,align:'center'},
          	{field:'CAR_SYMBOL',title:'车架号',width:100,align:'center'},
          	{field:'ENGINE_TYPE',title:'发动机编号',width:100,align:'center'}
         ]]
	});
});

function se(){
	var INSU_NAME= $("#pageForm input[name='INSU_NAME']").val();
	var LEASE_CODE= $("#pageForm input[name='LEASE_CODE']").val();
	var COMPANY_NAME= $("#pageForm input[name='COMPANY_NAME']").val();
	var SUPPLIERS_NAME= $("#pageForm input[name='SUPPLIERS_NAME']").val();
	var CAR_SYMBOL= $("#pageForm input[name='CAR_SYMBOL']").val();
    $('#pageTable').datagrid('load', {"INSU_NAME":INSU_NAME,"LEASE_CODE":LEASE_CODE,"COMPANY_NAME":COMPANY_NAME,"SUPPLIERS_NAME":SUPPLIERS_NAME,"CAR_SYMBOL":CAR_SYMBOL});
}

/**
 * 导出Excel
 */
function doExcel(){
	
	var INSU_NAME= $("#pageForm input[name='INSU_NAME']").val();
	var LEASE_CODE= $("#pageForm input[name='LEASE_CODE']").val();
	var COMPANY_NAME= $("#pageForm input[name='COMPANY_NAME']").val();
	var SUPPLIERS_NAME= $("#pageForm input[name='SUPPLIERS_NAME']").val();
	var CAR_SYMBOL= $("#pageForm input[name='CAR_SYMBOL']").val();
	
	var url = _basePath+"/insure/Insurance!expInsureEquipmentExcel.action?INSU_NAME=" + INSU_NAME 
			 + "&LEASE_CODE=" + LEASE_CODE 
			 + "&COMPANY_NAME=" + COMPANY_NAME
			 + "&SUPPLIERS_NAME=" + SUPPLIERS_NAME
			 + "&CAR_SYMBOL=" + CAR_SYMBOL;
	
	location.href = url;
	
}

