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
			updateRecordButton();
		}
	});

	$("#BORROW_OUT_BTN").click(function() {
		if ($("#BORROW_OUT_BTN").attr("disabled") != "disabled") {
			BORROW_OUT_BTN();
		}
	});
	
	$("#ReturnRegister").click(function() {
		if ($("#ReturnRegister").attr("disabled") != "disabled") {
			RETURNREGISTER();
		}
	});
});

function saveDossierBorrowApp() {
	if (!$("#borrowform").form('validate')) {
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
				i++;
			});
	if (flag == false) {
		$.messager.alert("提示", "请选择需要借阅的文件");
		return;
	}
	
	var PROJECT_ID=$("#PROJECT_ID").val();
	var BORROWNAME = $("input[name=BORROWNAME]").val();
	var BORROWIDCARD = $("#BORROWIDCARD").val();
	var BORROWPHONE = $("#BORROWPHONE").val();
	var INTENDRESTOREDATE = $("input[name=INTENDRESTOREDATE]").val();
	var POSTCODE = $("#POSTCODE").val();
	var BORROWADDRESS = $("#BORROWADDRESS").val();
	var RECIEVETYPE = $("input[name=RECIEVETYPE]:checked").val();
	var RESTOREPURPOSE = $("#RESTOREPURPOSE").val();
	var CLIENT_NAME = $("input[name=CLIENT_NAME]").val();
	var CLIENT_ID = $("input[name=CLIENT_ID]").val();
	var PROJECT_CODE = $("input[name=PROJECT_CODE]").val();
	if(PAYLIST_CODE.substring(0,PAYLIST_CODE.lastIndexOf("-"))!=PROJECT_CODE){
		PROJECT_ID="";
		PAYLIST_CODE="";
	}
	$.ajaxFileUpload( {
		type : "post",
		url : _basePath
				+ "/borrow/DossierBorrow!doAddBorrowApp.action?BORROWNAME="
				+ BORROWNAME + "&BORROWIDCOARD=" + BORROWIDCARD
				+ "&PREDICTTIME=" + INTENDRESTOREDATE + "&RESTOREPURPOSE="
				+ RESTOREPURPOSE + "&BORROWPHONE=" + BORROWPHONE
				+ "&RECIEVETYPE=" + RECIEVETYPE + "&BORROWADDRESS="
				+ BORROWADDRESS + "&POSTCODE=" + POSTCODE + "&PROJECT_CODE="
				+ PROJECT_CODE + "&CLIENT_ID=" + CLIENT_ID + "&CLIENT_NAME="
				+ CLIENT_NAME+"&PROJECT_ID="+PROJECT_ID+"&PAYLIST_CODE="+PAYLIST_CODE + "&DOSSIERINFO=" + JSON.stringify(saveRecord),
		secureuri : false,
		fileElementId : "FILEPATH",
		dataType : "json",
		success : function(json, status) {
			if (status) {
				$.messager.alert("提示", "借阅申请成功");
				$("#addRecordButton").linkbutton("disable");
				$("#addRecordButton").attr("disabled", "disabled");
			} else {
				$.messager.alert("提示", json.msg);
			}
		}
	});
}

function updateRecordButton(){
	if (!$("#borrowform").form('validate')) {
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
				i++;
			});
	if (flag == false) {
		$.messager.alert("提示", "请选择需要借阅的文件");
		return;
	}
	
//	var PROJECT_ID=$("#PROJECT_ID").val();
	var BORROWNAME = $("input[name=BORROWNAME]").val();
	var BORROWIDCARD = $("#BORROWIDCARD").val();
	var BORROWPHONE = $("#BORROWPHONE").val();
	var INTENDRESTOREDATE = $("input[name=INTENDRESTOREDATE]").val();
	var POSTCODE = $("#POSTCODE").val();
	var BORROWADDRESS = $("#BORROWADDRESS").val();
	var RECIEVETYPE = $("input[name=RECIEVETYPE]:checked").val();
	var RESTOREPURPOSE = $("#RESTOREPURPOSE").val();
	var CLIENT_NAME = $("input[name=CLIENT_NAME]").val();
	var CLIENT_ID = $("input[name=CLIENT_ID]").val();
	var PROJECT_CODE = $("input[name=PROJECT_CODE]").val();
	if(PAYLIST_CODE.substring(0,PAYLIST_CODE.lastIndexOf("-"))!=PROJECT_CODE){
//		PROJECT_ID="";
		PAYLIST_CODE="";
	}
	var BORROWID=$("#BORROWID").val();
	$.ajaxFileUpload( {
		type : "post",
		url : _basePath
				+ "/borrow/DossierBorrow!doUpdateBorrowAppSave.action?BORROWNAME="
				+ BORROWNAME + "&BORROWIDCOARD=" + BORROWIDCARD
				+ "&PREDICTTIME=" + INTENDRESTOREDATE + "&RESTOREPURPOSE="
				+ RESTOREPURPOSE + "&BORROWPHONE=" + BORROWPHONE+"&BORROWID="+BORROWID
				+ "&RECIEVETYPE=" + RECIEVETYPE + "&BORROWADDRESS="
				+ BORROWADDRESS + "&POSTCODE=" + POSTCODE + "&PROJECT_CODE="
				+ PROJECT_CODE + "&CLIENT_ID=" + CLIENT_ID + "&CLIENT_NAME="
				+ CLIENT_NAME+"&PAYLIST_CODE="+PAYLIST_CODE + "&DOSSIERINFO=" + JSON.stringify(saveRecord),
		secureuri : false,
		fileElementId : "FILEPATH",
		dataType : "json",
		success : function(json, status) {
			if (status) {
				$.messager.alert("提示", "借阅申请成功");
				$("#addRecordButton").linkbutton("disable");
				$("#addRecordButton").attr("disabled", "disabled");
			} else {
				$.messager.alert("提示", json.msg);
			}
		}
	});
}
function downFile() {
	var FILE_PATH = $("#download").attr("spath").replace("\f", "\\f");
	var FILE_PATH_NAME = $("#download").attr("sname");
	location.href = _basePath
			+ "/borrow/DossierBorrow!downFile.action?FILE_PATH=" + encodeURI(FILE_PATH)
			+ "&FILE_PATH_NAME=" + encodeURI(FILE_PATH_NAME);
}

function BORROW_OUT_BTN() {
	var flag = false;
	var saveRecord = new Array();
	$(".DOSSIERID").each(
			function() {
				if ($(this).attr("checked") == "checked") {
					flag = true;
					var temp = {};
					var td = $(this).parent("td");
					var tr = $(this).parent("td").parent("tr");
					temp.DOSSIER_STORAGE_ID = $(this).val();
					temp.DOSSIERNAME = $(td).find("input[name=DOSSIERNAME]").val();
					temp.DOSSIERCOUNT = $(td).find("input[name=DOSSIERCOUNT]")
							.val();
					temp.PAYLIST_CODE = $(td).find("input[name=PAYLIST_CODE]")
							.val();
					temp.PORTFOLIO_NUMBER = $(td).find(
							"input[name=PORTFOLIO_NUMBER]").val();
					temp.CABINET_NUMBER = $(td).find(
							"input[name=CABINET_NUMBER]").val();
					temp.DOSSIER_TEMP = $(td).find(
							"input[name=DOSSIER_TEMP]").val();
					temp.FILEPAGE = $(td).find("input[name=FILEPAGE]").val();
					temp.DOSSIER_CODE = $(td).find("input[name=DOSSIER_CODE]").val();

					temp.BORROWNAME = $("input[name=BORROWNAME]").val();
					temp.BORROWIDCARD = $("input[name=BORROWIDCARD]").val();
					temp.BORROWPHONE = $("input[name=BORROWPHONE]").val();
					temp.INTENDRESTOREDATE = $("input[name=INTENDRESTOREDATE]")
							.val();
					temp.POSTCODE = $("input[name=POSTCODE]").val();
					temp.BORROWADDRESS = $("input[name=BORROWADDRESS]").val();
					temp.RECIEVETYPE = $("input[name=RECIEVETYPE]:checked")
							.val();
					temp.RESTOREPURPOSE = $("#RESTOREPURPOSE").val();
					temp.CLIENT_NAME = $("input[name=CLIENT_NAME]").val();
					temp.CLIENT_ID = $("input[name=CLIENT_ID]").val();
					temp.PROJECT_CODE = $("input[name=PROJECT_CODE]").val();
					temp.FILEPATH = $("input[name=FILEPATH]").val();
					temp.DOSSIER_BORROWAPPID = $("input[name=DOSSIER_BORROWAPPID]").val();
					temp.DOSSIERPACKAGECODE = $("input[name=DOSSIERPACKAGECODE]").val();
					temp.DOSSIERBOXCODE = $("input[name=DOSSIERBOXCODE]").val();
					temp.FILEPATH_NAME = $("input[name=FILEPATH_NAME]").val();

					saveRecord.push(temp);
				}
			});
	if (flag == false) {
		$.messager.alert("提示", "请选择需要登记借阅的文件");
		return;
	}
	$.ajax( {
		type : "post",
		url : _basePath + "/borrow/DossierBorrow!doAddBorrowOutInfo.action",
		data : "param=" + JSON.stringify(saveRecord),
		dataType : "json",
		success : function(json) {
			if (json.flag) {
				$.messager.alert("提示", "借出登记成功");
				$("#BORROW_OUT_BTN").linkbutton("disable");
				$("#BORROW_OUT_BTN").attr("disabled", "disabled");
			} else {
				$.messager.alert("提示", json.msg);
			}
		}
	});
}

function RETURNREGISTER(){
	var flag = false;
	var saveRecord = new Array();
	$(".DOSSIERID").each(
			function() {
				if ($(this).attr("checked") == "checked") {
					flag = true;
					var temp = {};
					var td = $(this).parent("td");
					temp.DOSSIER_STORAGE_ID=$(this).val();
					temp.DOSSIER_BORROW_ID=$(td).find("input[name=DOSSIER_BORROWED_ID]").val();
					saveRecord.push(temp);
				}
			});
	if (flag == false) {
		$.messager.alert("提示", "请选择需要登记入柜的文件");
		return;
	}
	$.ajax( {
		type : "post",
		url : _basePath + "/borrow/DossierBorrow!doReturnRegistered.action",
		data : "param=" + JSON.stringify(saveRecord),
		dataType : "json",
		success : function(json) {
			if (json.flag) {
				$.messager.alert("提示", "归还入柜成功");
				$("#ReturnRegister").linkbutton("disable");
				$("#ReturnRegister").attr("disabled", "disabled");
			} else {
				$.messager.alert("提示", json.msg);
			}
		}
	});
}