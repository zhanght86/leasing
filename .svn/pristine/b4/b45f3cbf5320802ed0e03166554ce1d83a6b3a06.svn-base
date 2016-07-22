/**
 * 厂商项目审批查询 js
 * 作者:韩晓龙
 */

/**
 * 清空按钮
 */
function emptyData() {
	// 清空input
	$(".paramData").each(function() {
		$(this).val("");
	});
}
/**
 * 查询
 */
function dosearch() {
	var PROJ_ID = $("#PROJ_ID").val();
	var DLD = $("#DLD").val();
	$('#dg').datagrid('load', {
		"PROJ_ID" : PROJ_ID,
		"DLD" : DLD
	});
}
/**
 * 设置操作
 */
function setOperation(val, row) {
	if('是'==row.IFCAN){//如果不是历史意见则可申请重新审批
		return "<a href='#' style='color:blue' onclick=spReset('" + val + "')>审批重置</a>";
	}else{
		return "<a href='#' style='color:black'>已起租或历史意见无法重置</a>";
	}
}
//重置操作
function spReset(val){
	var ID = val;
	var url = "";
	$.messager.confirm("删除","确定要对该项目的审批重置吗?重置后请及时告知厂家！",function(r){
		if(r){
			jQuery.ajax({
				url : _basePath + "/manufacturerApproval/ManufacturerSearch!spReset.action",
				data : { "ID": ID},
				dataType:'json',
				success:function(data){
					//刷新页面
					showPageAgain();
				}
			});
		}
	});
}
//刷新页面
function showPageAgain(ID){
	top.addTab("审批查询", _basePath + "/manufacturerApproval/ManufacturerSearch.action");
}