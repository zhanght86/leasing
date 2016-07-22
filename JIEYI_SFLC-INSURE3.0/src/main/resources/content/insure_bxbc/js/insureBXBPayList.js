/**
 * 补差管理支付申请 查询  js
 * @author 韩晓龙
 */

/**
 * 点击单号转查看明细
 */
function setOperation(val, row) {
	return "<a href='#' style='color:blue' onclick='showDetail(" + val + ")'>" + val + "</a>";
}

/**
 * TODO 查看明细页面
 */
function showDetail(GLIDE_ID){
	$.messager.alert("提示","还没有明细!");
	return false;
}

/**
 * 清空按钮
 */
function emptyData(){
	$("#FK_DATE_BEGIN").datebox('clear');
	$("#FK_DATE_END").datebox('clear');
	//清空input
	$(".paramData").each(function(){
		$(this).val("");
	});
}
//TODO
function dosearch(){
	var GLIDE_ID = $("#GLIDE_ID").val();
	var STATUS = $("#STATUS").val();
	var DLD = $("#DLD").val();
	var FK_DATE_BEGIN = $("#FK_DATE_BEGIN").datebox("getValue");
	var FK_DATE_END = $("#FK_DATE_END").datebox("getValue");
	$('#dg').datagrid('load', {"GLIDE_ID":GLIDE_ID,"STATUS":STATUS,"DLD":DLD,"FK_DATE_BEGIN":FK_DATE_BEGIN,"FK_DATE_END":FK_DATE_END});
}

