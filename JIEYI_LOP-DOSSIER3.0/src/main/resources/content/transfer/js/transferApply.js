$(function() {
	$("#checkAll").click(function() {
		var flag = $("#checkAll").attr("checked");
		if (flag == "checked") {
			$(".DOSSIERID").each(function() {
				if ($(this).attr("disabled") != "disabled") {
					$(this).attr("checked", true);
				}
			});
		} else {
			$(".DOSSIERID").each(function() {
				$(this).attr("checked", false);
			});
		}
	});

	$("#addRecordButton").click(function() {
		if ($("#addRecordButton").attr("disabled") != "disabled") {
			saveDossierBorrowApp();
		}
	});
	$("#updateRecordButton").click(function() {
		if ($("#updateRecordButton").attr("disabled") != "disabled") {
			updateDossierBorrowApp();
		}
	});
	$("#addDesignButton").click(function() {
		if ($("#addDesignButton").attr("disabled") != "disabled") {
			saveDossierDesign();
		}
	});
});

function saveDossierBorrowApp() {
	if (!$("#transferform").form('validate')) {
		return false;
	}
	var flag = false;
	var saveRecord = new Array();
	var PAYLIST_CODE;
	var i=1;
	$(".DOSSIERID").each(
			function() {
				if ($(this).attr("checked") == "checked") {
					flag = true;
					var temp = {};
					var td = $(this).parent("td");
					var tr = $(this).parent("td").parent("tr");
					temp.DOSSIERID = $(this).val();
					temp.FILE_NAME = $(td).find("input[name=FILE_NAME]").val();
					temp.DOSSIER_CODE = $(td).find("input[name=DOSSIER_CODE]")
							.val();
					temp.PAYLIST_CODE = $(td).find("input[name=PAYLIST_CODE]")
							.val();
					PAYLIST_CODE=temp.PAYLIST_CODE;
					temp.PORTFOLIO_NUMBER = $(td).find(
							"input[name=PORTFOLIO_NUMBER]").val();
					temp.CABINET_NUMBER = $(td).find(
							"input[name=CABINET_NUMBER]").val();
					temp.DOSSIER_TEMP = $(tr).find(
							"input[name=DOSSIER_TEMP"+i+"]:checked").val();
					temp.DOSSIER_COUNT = $(tr)
							.find("input[name=DOSSIER_COUNT]").val();
					temp.FILEPAGE = $(tr).find("input[name=FILEPAGE]").val();
					temp.DOSSIER_STATUS = $(tr).find(
							"input[name=DOSSIER_STATUS]").val();
					saveRecord.push(temp);
				}
			});
	if (flag == false) {
		$.messager.alert("提示", "请选择需要移交的文件");
		return;
	}
	
	var PROJECT_ID=$("#PROJECT_ID").val();
	var RECIEVEMAN = $("input[name=RECIEVEMAN]").val();
	var RECIEVECODE = $("#RECIEVECODE").val();
	var ACCEPTPHONE = $("#ACCEPTPHONE").val();
	var POSTWAY = $("input[name=POSTWAY]:checked").val();
	var ACCEPTADDRESS = $("input[name=ACCEPTADDRESS]").val();
	var REMARKS = $("#REMARKS").val();
	var ACCEPTPOSTCODE = $("#ACCEPTPOSTCODE").val();
	var CLIENT_NAME = $("input[name=CLIENT_NAME]").val();
	var PROJECT_CODE = $("input[name=PROJECT_CODE]").val();
	var CLIENT_ID=$("input[name=CLIENT_ID]").val();
	if(PAYLIST_CODE.substring(0,PAYLIST_CODE.lastIndexOf("-"))!=PROJECT_CODE){
		PAYLIST_CODE="";
		PROJECT_ID="";
	}
	$.ajax( {
		type : "post",
		url : _basePath
				+ "/Transfer/DossierTransferApp!doAddTransferApp.action",
		data:"RECIEVEMAN="
				+ RECIEVEMAN + "&RECIEVECODE=" + RECIEVECODE
				+ "&ACCEPTPHONE=" + ACCEPTPHONE + "&POSTWAY="
				+ POSTWAY + "&ACCEPTADDRESS=" + ACCEPTADDRESS
				+ "&REMARKS=" + REMARKS + "&ACCEPTPOSTCODE="
				+ ACCEPTPOSTCODE + "&CLIENT_NAME=" + CLIENT_NAME + "&PROJECT_CODE="
				+ PROJECT_CODE +"&CLIENT_ID="+CLIENT_ID+"&PAYLIST_CODE="+PAYLIST_CODE+"&PROJECT_ID="+PROJECT_ID
				+"&FILEINFO=" + JSON.stringify(saveRecord),
		dataType : "json",
		success : function(json) {
			if (json.flag) {
				$.messager.alert("提示", "移交申请成功");
				$("#addRecordButton").linkbutton("disable");
				$("#addRecordButton").attr("disabled", "disabled");
			} else {
				$.messager.alert("提示", json.msg);
			}
		}
	});
}

function updateDossierBorrowApp(){
	if (!$("#transferform").form('validate')) {
		return false;
	}
	var flag = false;
	var saveRecord = new Array();
	var PAYLIST_CODE;
	var i=1;
	$(".DOSSIERID").each(
			function() {
				if ($(this).attr("checked") == "checked") {
					flag = true;
					var temp = {};
					var td = $(this).parent("td");
					var tr = $(this).parent("td").parent("tr");
					temp.DOSSIERID = $(this).val();
					temp.FILE_NAME = $(td).find("input[name=FILE_NAME]").val();
					temp.DOSSIER_CODE = $(td).find("input[name=DOSSIER_CODE]")
							.val();
					temp.PAYLIST_CODE = $(td).find("input[name=PAYLIST_CODE]")
							.val();
					PAYLIST_CODE=temp.PAYLIST_CODE;
					temp.PORTFOLIO_NUMBER = $(td).find(
							"input[name=PORTFOLIO_NUMBER]").val();
					temp.CABINET_NUMBER = $(td).find(
							"input[name=CABINET_NUMBER]").val();
					temp.DOSSIER_TEMP = $(tr).find(
							"input[name=DOSSIER_TEMP"+i+"]:checked").val();
					temp.DOSSIER_COUNT = $(tr)
							.find("input[name=DOSSIER_COUNT]").val();
					temp.FILEPAGE = $(tr).find("input[name=FILEPAGE]").val();
					temp.DOSSIER_STATUS = $(tr).find(
							"input[name=DOSSIER_STATUS]").val();
					saveRecord.push(temp);
				}
			});
	if (flag == false) {
		$.messager.alert("提示", "请选择需要移交的文件");
		return;
	}
	
	var RECIEVEMAN = $("input[name=RECIEVEMAN]").val();
	var RECIEVECODE = $("#RECIEVECODE").val();
	var ACCEPTPHONE = $("#ACCEPTPHONE").val();
	var POSTWAY = $("input[name=POSTWAY]:checked").val();
	var ACCEPTADDRESS = $("input[name=ACCEPTADDRESS]").val();
	var REMARKS = $("#REMARKS").val();
	var ACCEPTPOSTCODE = $("#ACCEPTPOSTCODE").val();
	var CLIENT_NAME = $("input[name=CLIENT_NAME]").val();
	var PROJECT_CODE = $("input[name=PROJECT_CODE]").val();
	var TRANSFERAPPID=$("input[name=TRANSFERAPPID]").val();
	if(PAYLIST_CODE.substring(0,PAYLIST_CODE.lastIndexOf("-"))!=PROJECT_CODE){
		PAYLIST_CODE="";
	}
	$.ajax( {
		type : "post",
		url : _basePath
				+ "/Transfer/DossierTransferApp!doUpdateTransferApp.action",
		data:"RECIEVEMAN="
				+ RECIEVEMAN + "&RECIEVECODE=" + RECIEVECODE
				+ "&ACCEPTPHONE=" + ACCEPTPHONE + "&POSTWAY="
				+ POSTWAY + "&ACCEPTADDRESS=" + ACCEPTADDRESS
				+ "&REMARKS=" + REMARKS + "&ACCEPTPOSTCODE="
				+ ACCEPTPOSTCODE + "&CLIENT_NAME=" + CLIENT_NAME + "&PROJECT_CODE="
				+ PROJECT_CODE +"&PAYLIST_CODE="+PAYLIST_CODE+"&TRANSFERAPPID="+TRANSFERAPPID
				+"&FILEINFO=" + JSON.stringify(saveRecord),
		dataType : "json",
		success : function(json) {
			if (json.flag) {
				$.messager.alert("提示", "移交申请成功");
				$("#addRecordButton").linkbutton("disable");
				$("#addRecordButton").attr("disabled", "disabled");
			} else {
				$.messager.alert("提示", json.msg);
			}
		}
	});
}

function saveDossierDesign(){
	if (!$("#transferform").form('validate')) {
		return false;
	}
	var flag = false;
	var saveRecord = new Array();
	var i=1;
	$(".DOSSIERID").each(
			function() {
				if ($(this).attr("checked") == "checked") {
					flag = true;
					var temp = {};
					temp.RECIEVEMAN=$("#RECIEVEMAN").val();
					temp.RECIEVECODE=$("#RECIEVECODE").val();
					temp.ACCEPTPHONE=$("#ACCEPTPHONE").val();
					temp.POSTWAY=$("input[name=POSTWAY]:checked").val();
					temp.ACCEPTADDRESS=$("#ACCEPTADDRESS").val();
					temp.ACCEPTPOSTCODE=$("#ACCEPTPOSTCODE").val();
					temp.REMARKS=$("#REMARKS").val();
					temp.CLIENT_NAME=$("#CLIENT_NAME").val();
					temp.PROJECT_CODE=$("#PROJECT_CODE").val();
					var td = $(this).parent("td");
					var tr = $(this).parent("td").parent("tr");
					temp.DOSSIERID = $(this).val();
					temp.FILE_NAME = $(td).find("input[name=FILE_NAME]").val();
					temp.DOSSIER_CODE = $(td).find("input[name=DOSSIER_CODE]")
							.val();
					temp.PAYLIST_CODE = $(td).find("input[name=PAYLIST_CODE]")
							.val();
					temp.DOSSIERBAGNUMBER = $(td).find(
							"input[name=PORTFOLIO_NUMBER]").val();
					temp.CABINET_NUMBER = $(td).find(
							"input[name=CABINET_NUMBER]").val();
					temp.DOSSIER_TEMP = $(tr).find(
							"input[name=DOSSIER_TEMP"+i+"]:checked").val();
					temp.DOSSIER_COUNT = $(tr)
							.find("input[name=DOSSIER_COUNT]").val();
					temp.FILEPAGE = $(tr).find("input[name=FILEPAGE]").val();
					temp.TRANSFER_APP_ID = $("input[name=TRANSFER_APP_ID]").val();
					saveRecord.push(temp);
				}
				i++;
			});
	if (flag == false) {
		$.messager.alert("提示", "请选择需要借阅的文件");
		return;
	}

	var RECIEVEMAN = $("input[name=RECIEVEMAN]").val();
	var RECIEVECODE = $("#RECIEVECODE").val();
	var ACCEPTPHONE = $("#ACCEPTPHONE").val();
	var POSTWAY = $("input[name=POSTWAY]:checked").val();
	var ACCEPTADDRESS = $("input[name=ACCEPTADDRESS]").val();
	var REMARKS = $("#REMARKS").val();
	var ACCEPTPOSTCODE = $("#ACCEPTPOSTCODE").val();
	var CLIENT_NAME = $("input[name=CLIENT_NAME]").val();
	var PROJECT_CODE = $("input[name=PROJECT_CODE]").val();
	$.ajax( {
		type : "post",
		url : _basePath
				+ "/Transfer/DossierTransferApp!doAddTransferDesign.action",
		data:"param=" + JSON.stringify(saveRecord),
		dataType : "json",
		success : function(json) {
			if (json.flag) {
				$.messager.alert("提示", "移交登记成功");
				$("#addDesignButton").linkbutton("disable");
				$("#addDesignButton").attr("disabled", "disabled");
			} else {
				$.messager.alert("提示", json.msg);
			}
		}
	});
}

function downloadFile(){
	//TODO 所有权转移证书导出
	alert("等待完善所有权转移证书导出");
}