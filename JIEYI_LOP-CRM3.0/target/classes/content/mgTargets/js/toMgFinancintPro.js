$(document).ready(function(){
	$("#insDialog").datagrid({
		url:_basePath+"/target/Target!toMgFinancintPro.action",//?CREDIT_ID=+$!param.CREDIT_ID,
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:true,
		pageSize:20,
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		columns:[[
		          {field:'PRO_NAME',width:220,align:'center',title:'项目名称'}, 
				  {field:'PRO_CODE',width:120,align:'center',title:'项目编号'},
				  {field:'CLIENT_NAME',width:120,align:'center',title:'客户名称'},
				  {field:'CLERK_NAME',width:220,align:'center',title:'客户经理'}, 
				  {field:'SIGNED_DATE',width:120,align:'center',title:'客户签订日期'},
				  {field:'PRODUCT_NAME',width:120,align:'center',title:'品牌名称'},
				  {field:'CAR_SYMBOL',width:120,align:'center',title:'车架号'},
				  {field:'COMPANY_NAME',width:120,align:'center',title:'品牌'},
				  {field:'ZBGS_ID',width:120,align:'center',title:'是否融指标',formatter:function(value,rows,index){
					  if(value==undefined){
						  return "不融指标";
					  }else {
						  return "融指标";
					  }
				  }},
				  {field:'ZB_NAME',width:120,align:'center',title:'指标公司'}
		         ]]	
	});
	
	//页面清空按钮操作
	$("#qingkong").click(function(){
		$(".paramData").each(function(){
			$(this).val("");
		});
	});
});

function tofindData(){
	var CAR_SYMBOL = $("input[name='CAR_SYMBOL']").val();
	var PRO_CODE = $("input[name='PRO_CODE']").val();
	var PRODUCT_NAME = $("input[name='PRODUCT_NAME']").val();
	var CLIENT_NAME = $("input[name='CLIENT_NAME']").val();
	
	$('#insDialog').datagrid('load', {"CLIENT_NAME" : CLIENT_NAME,"PRODUCT_NAME":PRODUCT_NAME,"PRO_CODE":PRO_CODE,"CAR_SYMBOL":CAR_SYMBOL});
}