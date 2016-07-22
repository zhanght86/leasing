/**
 * 补差管理支付申请 明细查询  js
 * @author 韩晓龙
 */
var url = "";

/**
 * 清空按钮
 */
function emptyData(){
	$("#PLAN_DATE_BEGIN").datebox('clear');
	$("#PLAN_DATE_END").datebox('clear');
	$("#DZ_DATE_BEGIN").datebox('clear');
	$("#DZ_DATE_END").datebox('clear');
	//清空input
	$(".paramData").each(function(){
		$(this).val("");
	});
}
//TODO
function dosearch(){
	var DLD = $("#DLD").val();
	var PROJ_ID = $("#PROJ_ID").val();
	var KHMC = $("#KHMC").val();
	var PLAN_DATE_BEGIN = $("#PLAN_DATE_BEGIN").datebox("getValue");
	var PLAN_DATE_END = $("#PLAN_DATE_END").datebox("getValue");
	var ZZS = $("#ZZS").val();
	var PRODUCT_NAME = $("#PRODUCT_NAME").val();
	var DZ_DATE_BEGIN = $("#DZ_DATE_BEGIN").datebox("getValue");
	var DZ_DATE_END = $("#DZ_DATE_END").datebox("getValue");
	$('#dg').datagrid('load', {"DLD":DLD,"PROJ_ID":PROJ_ID,"KHMC":KHMC,"PLAN_DATE_BEGIN":PLAN_DATE_BEGIN,"PLAN_DATE_END":PLAN_DATE_END,"ZZS":ZZS,"PRODUCT_NAME":PRODUCT_NAME,"DZ_DATE_BEGIN":DZ_DATE_BEGIN,"DZ_DATE_END":DZ_DATE_END});
}

