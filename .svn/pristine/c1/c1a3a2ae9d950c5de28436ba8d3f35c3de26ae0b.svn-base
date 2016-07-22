$(document).ready(function(){
	$("#overdue_Supper_MG").datagrid({
		url:"Overdue!query_Supp_OverDue_Mg_AJAX.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		pageSize:50,
		toolbar:'#pageForm',
		columns:[[
		          	{field:'AREA_NAME',title:'区域',width:25},
		          	{field:'SUP_NAME',title:'供应商',width:35},
		          	{field:'COMPANY_NAME',title:'厂商',width:35},
		          	{field:'AMOUNTALL',title:'在租台量',width:20},
		          	{field:'AMOUNTOVERDUE',title:'逾期台量',width:20},
		          	{field:'EQOVERDUE',title:'逾期率 %',width:20},
		          	{field:'PAID_MONEY_ALL',title:'租金金额',width:30},
		          	{field:'OVERDUE_MONEY_ALL',title:'逾期租金金额',width:30},
	                {field:'PRICEOVERDUE',title:'租金逾期率 %',width:20}
		         ]]
	});
	
});


/**
 * 清空按钮
 * @return
 */
function emptyData(){
	$(".paramData").each(function(){
		$(this).val("");
	});
}

function exportData(){
	$("#divFrom").empty();
	if(confirm("是否导出为excel？")){
		var url="Overdue!overDueUpload.action";
		
		$("#divFrom").append("<form id='formSubmit' method='post' action='"+url+"'></form>");
		$("#formSubmit").submit();
	}
}