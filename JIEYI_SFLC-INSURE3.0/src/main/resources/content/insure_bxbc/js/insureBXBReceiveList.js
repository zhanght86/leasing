/**
 * 补差管理收取申请 查询  js
 * @author 韩晓龙
 */
var url = "";

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
	var APPLY_ID = $("#APPLY_ID").val();
	var STATUS = $("#STATUS").val();
	var DLD = $("#DLD").val();
	var FK_DATE_BEGIN = $("#FK_DATE_BEGIN").datebox("getValue");
	var FK_DATE_END = $("#FK_DATE_END").datebox("getValue");
	$('#dg').datagrid('load', {"APPLY_ID":APPLY_ID,"STATUS":STATUS,"DLD":DLD,"FK_DATE_BEGIN":FK_DATE_BEGIN,"FK_DATE_END":FK_DATE_END});
}

