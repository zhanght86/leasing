/**
 * 厂商项目审批 js
 * 作者:韩晓龙
 */

/**
 * 清空按钮
 */
function emptyData(){
	//清空input
	$(".paramData").each(function(){
		$(this).val("");
	});
}
/**
 * 查询
 */
function dosearch(){
	var PROJ_ID = $("#PROJ_ID").val();
	var KHMC = $("#KHMC").val();
	var DLD = $("#DLD").val();
	var CJH = $("#CJH").val();
	var SHSTATUS = $("#SHSTATUS").val();
	$('#dg').datagrid('load', {"PROJ_ID":PROJ_ID,"KHMC":KHMC,"DLD":DLD,"CJH":CJH,"SHSTATUS":SHSTATUS});
}

/**
 * 设置操作
 */
function setOperation(val, row) {
	if('已审核'==row.SHSTATUS){
		return "<a href='#' style='color:black'>无需重复处理</a>";
	}
	/*
	if('陕重汽'==row.MANUFACTURER){
		if(row.LCNAME!=undefined && (row.LCNAME).indexOf("反馈信审")>-1){
			return "<a href='#' style='color:blue' onclick=showSQPage('" + val + "')>陕汽预览并导出审批单</a>";
		}else{
			return "<a href='#' style='color:blue' onclick=showSQConfirmPage('" + val + "')>陕汽预览并导出确认函</a>";
		}
	*/
	if('陕重汽'==row.MANUFACTURER){
			return "<a href='#' style='color:blue' onclick=showSQPage('" + val + "')>陕汽预览并导出审批单</a>||<a href='#' style='color:blue' onclick=showSQConfirmPage('" + val + "')>陕汽预览并导出确认函</a>";
	}else if('山重建机'==row.MANUFACTURER){
		return "<a href='#' style='color:blue' onclick=showJJPage('" + val + "')>建机审批处理操作</a>";
	}else if('山推股份'==row.MANUFACTURER){
		return "<a href='#' style='color:blue' onclick=showSTPage('" + val + "')>山推审批处理操作</a>";
	}else{
		return "<a href='#' style='color:black'>无可处理操作</a>";
	}
}

/**
 * 跳转陕汽审批单页面
 */
function showSQPage(PROJECT_ID){
	top.addTab("陕重汽厂商项目审批单", _basePath + "/manufacturerApproval/ManufacturerApproval!toShowSQPage.action?PROJECT_ID=" + PROJECT_ID);
}

/**
 * 跳转陕汽确认函页面
 */
function showSQConfirmPage(PROJECT_ID){
	top.addTab("陕重汽厂商项目审批确认函", _basePath + "/manufacturerApproval/ManufacturerApproval!toShowSQConfirmPage.action?PROJECT_ID=" + PROJECT_ID);
}

/**
 * 跳转山重建机审批页面
 */
function showJJPage(PROJECT_ID){
	top.addTab("山重建机厂商项目审批", _basePath + "/manufacturerApproval/ManufacturerApproval!toShowJJPage.action?PROJECT_ID=" + PROJECT_ID);
}

/**
 * 跳转山推股份审批页面
 */
function showSTPage(PROJECT_ID){
	top.addTab("山推股份厂商项目审批", _basePath + "/manufacturerApproval/ManufacturerApproval!toShowSTPage.action?PROJECT_ID=" + PROJECT_ID);
}

