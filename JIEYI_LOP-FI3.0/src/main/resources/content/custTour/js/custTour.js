/**
 * 客户巡视功能js
 * @author 韩晓龙
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
 * 设置巡视操作
 */
//设备巡视
function setEquipOperation(val, row) {
	return "<a href='#' style='color:blue' onclick=showEquipDetail('" + row.PROJ_ID + "')>查看巡视记录</a>&nbsp;&nbsp;&nbsp;<a href='#' style='color:blue' onclick=addEquipRecord('" + row.PROJ_ID + "')>添加巡视记录</a>";
}
//显示添加设备巡视记录页面
function addEquipRecord(PROJ_ID){
	top.addTab("添加设备巡视记录", _basePath + "/custTour/CustTour!toShowAddEquipRecord.action?PROJ_ID=" + PROJ_ID);
}
//查看设备巡视记录
function showEquipDetail(PROJ_ID){
	//显示
	top.addTab("查看设备巡视记录", _basePath + "/custTour/CustTour!toShowDetailEquipRecord.action?PROJ_ID=" + PROJ_ID);
}
//交往巡视
function setContactOperation(val, row) {
	return "<a href='#' style='color:blue' onclick=showContactDetail('" + row.PROJ_ID + "')>查看巡视记录</a>&nbsp;&nbsp;&nbsp;<a href='#' style='color:blue' onclick=addContactRecord('" + row.PROJ_ID + "')>添加巡视记录</a>";
}
//显示添加交往巡视记录页面
function addContactRecord(PROJ_ID){
	top.addTab("添加交往巡视记录", _basePath + "/custTour/CustTour!toShowAddContactRecord.action?PROJ_ID=" + PROJ_ID);
}
//查看交往巡视记录
function showContactDetail(PROJ_ID){
	//显示
	top.addTab("查看交往巡视记录", _basePath + "/custTour/CustTour!toShowDetailContactRecord.action?PROJ_ID=" + PROJ_ID);
}
//理赔巡视
function setClaimOperation(val, row) {
	return "<a href='#' style='color:blue' onclick=showClaimDetail('" + row.PROJ_ID + "')>查看巡视记录</a>&nbsp;&nbsp;&nbsp;<a href='#' style='color:blue' onclick=addClaimRecord('" + row.PROJ_ID + "')>添加巡视记录</a>";
}
//显示添加理赔巡视记录页面
function addClaimRecord(PROJ_ID){
	top.addTab("添加理赔巡视记录", _basePath + "/custTour/CustTour!toShowAddClaimsRecord.action?PROJ_ID=" + PROJ_ID);
}
//查看理赔巡视记录
function showClaimDetail(PROJ_ID){
	//显示
	top.addTab("查看理赔巡视记录", _basePath + "/custTour/CustTour!toShowDetailClaimsRecord.action?PROJ_ID=" + PROJ_ID);
}

/**
 * TODO查询
 */
function dosearch(){
	var PROJ_ID = $("#PROJ_ID").val();
	var KHMC = $("#KHMC").val();
	var DLD = $("#DLD").val();
	$('#dg').datagrid('load', {"PROJ_ID":PROJ_ID,"KHMC":KHMC,"DLD":DLD});
}

