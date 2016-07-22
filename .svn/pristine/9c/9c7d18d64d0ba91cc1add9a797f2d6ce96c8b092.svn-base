function se() {
	$('#pageTable').datagrid('load', {
		"param" : getFromData("#pageForm")
	});
}

$(function() {
	$("#addCreditCoreFileDiv").dialog('close');
	$("#updateCreditCoreFileDiv").dialog('close');
	$("#exportCoreFileBtn")
			.click(
					function() {
						var RENTERTYPE = $("#SEARCHRENTERTYPE").val();
						var FILETYPE = $("#FILETYPE").val();
						var DATAID = $("#DATAID").val();
						var FLAG = $("#FLAG").val();
						location.href = _basePath
								+ "/creditlist/CreditCoreFileInfo!exportCreditCoreFile.action?RENTERTYPE="
								+ RENTERTYPE + "&FILETYPE=" + FILETYPE
								+ "&DATAID=" + DATAID + "&FLAG=" + FLAG;
					});
	$("#addCoreFileOkBtn").click(function() {
		addCreditCoreFile();
	});
	$("#UPDATECOREOKBTN").click(function() {
		UPDATECreditCoreFile();
	});

	/**
	 * 点击添加
	 */
	$("#addCoreFileBtn").click(function() {
		$("#addCreditCoreFileDiv").dialog("open");
	});

});

/**
 * 点击修改
 */
function updateCoreFileBtn(row) {
	$("#UPDATE_FILENAME").val(row.FILE_NAME);
	$("#UPDATE_FILECOUNT").val(row.FILECOUNT);
	$("#UPDATE_ID").val(row.ID);
	$("#UPDATE_FLAG").val(row.FLAG);
	$("#UPDATE_RENTERTYPE").val(row.RENTERTYPE);
	$("#UPDATE_FILETYPE").val(row.FILETYPE);
	$("#UPDATE_ISDBR").val(row.ISDBR);
	$("#UPDATE_ISMARRY").val(row.ISMARRY);
	$("#updateCreditCoreFileDiv").dialog("open");
}

/**
 * 点击删除
 */
function deleteCoreFileBtn(row) {
	var ID = row.ID;
	var url = _basePath
			+ "/creditlist/CreditCoreFileInfo!deleteCreditCoreFileInfo.action?ID="
			+ ID;
	$.ajax( {
		url : url,
		dataType : 'json',
		success : function(json) {
			if (json.flag) {
				$.messager.alert("提示", "删除成功！");
				location.reload();
			} else {
				$.messager.alert("提示", "删除失败！");
			}
		}
	});
}

/**
 * 添加校验
 * 
 * @return
 * 
 * @author King 2012-2-3
 */
function checkAddCreditCoreFile() {
	if ($("#ADD_FILENAME").val() == "") {
		alert("请填写新文件名称");
		return;
	}
	var fileCount = $.trim($("#ADD_FILECOUNT").val());
	if (isNaN(fileCount) || fileCount == "") {
		alert("请填写正确的文件数量");
		return;
	}

	if ($("#ADD_FLAG").val() == "") {
		alert("请选择文件类型");
		return;
	}

	if ($("#ADD_RENTERTYPE").val() == "") {
		alert("请选择承租人类型");
		return;
	}

	return true;
}

/**
 * 添加提交
 * 
 * @param DATAID
 * @param FILECOUNT
 * @param FLAG
 * @return
 * 
 * @author King 2012-2-3
 */
function addCreditCoreFile() {

	var FILE_NAME = $("#ADD_FILENAME").val();
	var FILECOUNT = $("#ADD_FILECOUNT").val();
	var FLAG = $("#ADD_FLAG").val();
	var RENTERTYPE = $("#ADD_RENTERTYPE").val();
	var ADD_FILETYPE = $("#ADD_FILETYPE").val();
	var ISMARRY = $("#ADD_ISMARRY").val();
	var TYPE = $("#ADD_TYPE").val();
	var ISDBR = $("#ADD_ISDBR").val();
	if (checkAddCreditCoreFile()) {
		var url = _basePath
				+ "/creditlist/CreditCoreFileInfo!addCreditCoreFileInfo.action?FILE_NAME="
				+ encodeURI(FILE_NAME) + "&FILECOUNT=" + encodeURI(FILECOUNT)
				+ "&FLAG=" + encodeURI(FLAG) + "&RENTERTYPE="
				+ encodeURI(RENTERTYPE) + "&FILETYPE="
				+ encodeURI(ADD_FILETYPE) + "&ISMARRY=" + encodeURI(ISMARRY)
				+ "&ISDBR=" + encodeURI(ISDBR) + "&TYPE=" + encodeURI(TYPE);
		$.ajax( {
			dataType : 'json',
			url : url,
			success : function(json) {
				if (json.flag) {
					$.messager.alert("提示", "添加成功");
					$("#addCreditCoreFileDiv").dialog('close');
					$("#pageTable").datagrid("reload");
				} else {
					$.messager.alert("提示", "该配置项已经存在");
				}
			}
		});
	}
}

/**
 * 修改校验
 * 
 * @return
 * 
 * @author King 2012-2-3
 */
function checkUPDATECreditCoreFile() {
	var fileCount = $.trim($("#UPDATE_FILECOUNT").val());
	if (isNaN(fileCount) || fileCount == "") {
		$.messgaer.alert("提示", "请输入正确的数量");
		return false;
	}

	if ($("#UPDATE_RENTERTYPE").val() == "") {
		alert("请选择人员类型");
		return false;
	}

	if ($("#UPDATE_FILENAME").val() == "") {
		alert("请选择文件名称");
		return false;
	}

	if ($("#UPDATE_FLAG").val() == "") {
		alert("请选择文件类型");
		return false;
	}
	return true;
}

/**
 * 修改提交
 * 
 * @param DATAID
 * @param FILECOUNT
 * @param FLAG
 * @param ID
 * @return
 * 
 * @author King 2012-2-3
 */
function UPDATECreditCoreFile() {
	var FILE_NAME = $("#UPDATE_FILENAME").val();
	var FILECOUNT = $("#UPDATE_FILECOUNT").val();
	var FLAG = $("#UPDATE_FLAG").val();
	var ID = $("#UPDATE_ID").val();
	var RENTERTYPE = $("#UPDATE_RENTERTYPE").val();
	var UPDATE_FILETYPE = $("#UPDATE_FILETYPE").val();
	var UPDATE_TYPE = $("#UPDATE_TYPE").val();
	var ISDBR = $("#UPDATE_ISDBR").val();
	var ISMARRY = $("#UPDATE_ISMARRY").val();
	if (checkUPDATECreditCoreFile()) {
		var url = _basePath
				+ "/creditlist/CreditCoreFileInfo!updateCreditCoreFileInfo.action?FILE_NAME="
				+ encodeURI(FILE_NAME) + "&FILECOUNT=" + encodeURI(FILECOUNT)
				+ "&FLAG=" + encodeURI(FLAG) + "&ID=" + ID + "&RENTERTYPE="
				+ encodeURI(RENTERTYPE) + "&FILETYPE="
				+ encodeURI(UPDATE_FILETYPE) + "&ISDBR=" + encodeURI(ISDBR)
				+ "&ISMARRY=" + encodeURI(ISMARRY) + "&TYPE="
				+ encodeURI(UPDATE_TYPE);
		$.ajax( {
			url : url,
			dataType : 'json',
			success : function(json) {
				if (json.flag == true) {
					$.messager.alert("提示", "修改成功！");
					location.reload();
				} else {
					$.messager.alert("提示", "修改失败！");
				}
			}
		});
	}
}
function recieveproj(ID) {
	window.location.href = _basePath
			+ "/creditlist/CreditCoreFileInfo!manageProjectChuShen.action?PROJECT_ID="
			+ ID;
}