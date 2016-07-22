/**
 * 款项查询
 * 
 * @author hanxl
 */

/**
 * 清空按钮，清空日期及可填写字段
 */
function emptyData(){
	//清空日期
	$("#LKSJ").datebox('clear');
	//清空input
	$(".paramData").each(function(){
		$(this).val("");
	});
}
/**
 * 查询
 */
function dosearch(){
	var DLD = $("#DLD").val();
	var FNNAME = $("#FNNAME").val();
	var KHMC = $("#KHMC").val();
	var LKSJ = $("#LKSJ").datebox("getValue");
	var PROJ_ID = $("#PROJ_ID").val();
	
	$('#dg').datagrid('load', {"DLD":DLD,"FNNAME":FNNAME,"KHMC":KHMC,"LKSJ":LKSJ,"PROJ_ID":PROJ_ID});
}