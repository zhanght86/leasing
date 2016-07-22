$(document)
		.ready(
				function() {
					$("#cyberBank_C_PageTable")
							.datagrid(
									{
										pagination : true,// 是否分页 true为是
										rownumbers : true,// 左侧自动显示行数
										fit : true,
										pageSize : 20,
										toolbar : '#pageForm',
										url : 'whiteList!query.action',
										columns : [ [
												{
													field : 'ID',
													title : 'HID',
													checkbox : "true",
													width : 150
												},
												{
													field : 'PLATFORM_TYPE',
													title : '机构ID',
													width : 150
												},
												{
													field : 'YWLX',
													title : '业务类型',
													width : 150
												},
												{
													field : 'BANK_ACCOUNT',
													title : '账户号码',
													width : 150
												},
												{
													field : 'BANK_CUSTNAME',
													title : '账户名称',
													width : 150
												},
												{
													field : 'ZHLX',
													title : '账户类型',
													width : 150
												},
												{
													field : 'BANK_NAME',
													title : '银行名称',
													width : 150
												},
												{
													field : 'SFLX',
													title : '证件类型',
													width : 150
												},
												{
													field : 'ID_CARD_NO',
													title : '证件号码',
													width : 150
												},
												{
													field : 'TEL_PHONE',
													title : '手机号',
													width : 150
												},
												{
													field : 'MAILNAME',
													title : '邮箱',
													width : 150
												},
												{
													field : 'PDF_PATH',
													title : '路径',
													width : 150,
													formatter : function(value,
															rowData, rowIndex) {
														// 图片保真图，大图显示时使用
														var picStr = _basePath
																+ "/leeds/cust_info_input/CustInfoInput!readPic.action?path="
																+ rowData.PDF_PATH;
														// param1.name = name;
														// param1.statu = statu;
//														return "<a href='javascript:void(0);' onclick=showPic('"
//																+ picStr
//																+ "')>"
//																+ rowData.PDF_PATH
//																+ "</a>";
														
//														return '<a href="http://localhost:8888/jieyi-project/newRentWrite/js/1.png" class="lightbox" title=""><img src="http://localhost:8888/jieyi-project/newRentWrite/js/1.png" width="100" height="40" alt="" /></a>';
														
														return '<a href="'+picStr+'" class="lightbox" title=""><img src="'+picStr+'" width="100" height="40" alt="" /></a>';
														
													}
												}/*, {
													field : 'EXCELLOADSTATUS',
													title : '是已导出',
													width : 150
												} */] ],
												//zheba
												onLoadSuccess: function (data) {
													$(".lightbox").lightbox({
														fitToScreen: true,
														imageClickClose: false
													});
												}

									
									});
				});


// 下载
function DBXZ() {

	$("#divFrom1").empty();

	var ZJ_TYPE = $("select[name='ZJ_TYPE']").find("option:selected").val();// 开户行
	var NAME = $("input[name='NAME']").val();
	var ID_CAR_NO = $("input[name='ID_CAR_NO']").val();
	var ISDOWN = $("select[name='ISDOWN']").find("option:selected").val();// 客户来源
	 var STARTDATE=$("input[name='STARTDATE']").val();
	
	var strUrl="";
	var exportType ="";
	var datagridList = $('#cyberBank_C_PageTable').datagrid('getChecked');
	if (datagridList.length <= 0) {
		exportType ="all";
		strUrl = _basePath
		+ "/newRentWrite/whiteList!DBXZ.action?exportType="
		+ exportType + "&ZJ_TYPE=" + ZJ_TYPE + "&NAME=" + NAME
		+ "&ID_CAR_NO=" + ID_CAR_NO + "&ISDOWN=" + ISDOWN+"&STARTDATE="+STARTDATE;
	} else {
		exportType ="select";
		var selectData = [];
		for (var i = 0; i < datagridList.length; i++) {
			if (i == 0) {
				IDS = datagridList[i].ID;
			} else {
				IDS = IDS + "," + datagridList[i].ID;
			}
		}
		strUrl = _basePath
				+ "/newRentWrite/whiteList!DBXZ.action?exportType="
				+ exportType + "&ZJ_TYPE=" + ZJ_TYPE + "&NAME=" + NAME
				+ "&ID_CAR_NO=" + ID_CAR_NO + "&ISDOWN=" + ISDOWN + "&IDS="
				+ IDS+"&STARTDATE="+STARTDATE;
	}
	$.messager.confirm("提示", "确认下载扣款说明吗？", function(a) {
		if (a) {
			$("#divFrom1").append(
					"<form id='formSubmit1' method='post' action='"+strUrl+"'></form>");
			$("#formSubmit1").submit();
		}
	});
}
// 导出
function exportData() {
	$("#divFrom").empty();

	var ZJ_TYPE = $("select[name='ZJ_TYPE']").find("option:selected").val();// 开户行
	var NAME = $("input[name='NAME']").val();
	var ID_CAR_NO = $("input[name='ID_CAR_NO']").val();
	var ISDOWN = $("select[name='ISDOWN']").find("option:selected").val();// 客户来源
	 var STARTDATE=$("input[name='STARTDATE']").val();
	// if(PLAN_START_DATE==null || PLAN_START_DATE ==""){
	// alert("应还日期必须选择");
	// return false;
	// }
	var exportType = "";
	var IDS = "";
	var IDS1 = "";
	var zuJin = "";
	var weiYueJin = "";
	var strUrl = "";
	var datagridList = $('#cyberBank_C_PageTable').datagrid('getChecked');
	if (datagridList.length <= 0) {
		exportType = "all";
		strUrl = _basePath
				+ "/newRentWrite/whiteList!excelUpload.action?exportType="
				+ exportType + "&ZJ_TYPE=" + ZJ_TYPE + "&NAME=" + NAME
				+ "&ID_CAR_NO=" + ID_CAR_NO + "&ISDOWN=" + ISDOWN+"&STARTDATE="+STARTDATE;

	} else {
		exportType = "select";
		var selectData = [];
		for (var i = 0; i < datagridList.length; i++) {
			if (i == 0) {
				IDS = datagridList[i].ID;
			} else {
				IDS = IDS + "," + datagridList[i].ID;
			}
		}
		strUrl = _basePath
				+ "/newRentWrite/whiteList!excelUpload.action?exportType="
				+ exportType + "&ZJ_TYPE=" + ZJ_TYPE + "&NAME=" + NAME
				+ "&ID_CAR_NO=" + ID_CAR_NO + "&ISDOWN=" + ISDOWN + "&IDS="
				+ IDS+"&STARTDATE="+STARTDATE;
	}
	$.messager.confirm("提示", "您确认要导出数据吗？", function(a) {
		if (a) {
			$("#divFrom").append(
					"<form id='formSubmit' method='post' action='" + strUrl
							+ "'></form>");
			$("#formSubmit").submit();
		}
	});

}
