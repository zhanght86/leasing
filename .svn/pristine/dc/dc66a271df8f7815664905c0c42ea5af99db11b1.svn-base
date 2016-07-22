$(function() {
	// 添加定量打分项信息

	$(".addR").click(function() {
		var tempTR = $(".templete").clone();
		$("#addTbody").append(tempTR.removeClass("hidden templete"));
	});

	$("#addTbody").click(function(e) {
		if ($(e.target).is(".del")) {
			$(e.target).parents("tr").remove();
		}
	});

	/*$("#RECORD_NAME").change(function() {
		check();
	});*/

	// 定量打分项配置操作
	$(".updateData")
			.click(function() {
				//debugger;
					var TYPE = $(this).attr("TYPE");
					var MAIN_TYPE = $(this).attr("MAIN_TYPE");
					window.location.href = "EvaluateDictionary!toDataTypeInfo.action?TYPE="
							+ TYPE;

				});

	// 作废与启动类型
	$(".invalidStatus")
			.click(
					function() {
						var DATA_ID = $(this).attr("DATA_ID");
						var STATUS = $(this).attr("STATUS");
						var TYPE = $(this).attr("TYPES");
						var DESCRIBE = $(this).attr("DESCRIBE");
						var MAIN_TYPE = $(this).attr("MAIN_TYPE");
						var TRADE_TYPE = $(this).attr("TRADE_TYPE");

						jQuery
								.ajax( {
									url : "EvaluateDictionary!invalidDataType.action",
									data : "DATA_ID=" + DATA_ID + "&STATUS="
											+ STATUS,
									dataType : "json",
									success : function(date) {
										if (date.flag == true) {
											alert("操作成功");
											//debugger;
											window.location = "EvaluateDictionary!toDataTypeInfo.action?TYPE="
													+ TYPE
													+ "&DESCRIBE="
													+ DESCRIBE
													+ "&MAIN_TYPE="
													+ MAIN_TYPE
													+ "&TRADE_TYPE="
													+ TRADE_TYPE;
										} else {
											alert("操作失败请重试！");
										}
									},
									error : function(e) {
										alert(e.message);
									}
								});
					});
});

function del(obj) {
	$(obj).parents("tr").remove();
}

function addType() {
	window.location.href = "EvaluateDictionary!toDataType.action";
};

function editType(row) {
	if (row) {
		top
				.addTab(
						"定量打分项修改",
						_basePath
								+ '/secuEvaluate/EvaluateDictionary!toDataTypeInfo.action?TYPE='
								+ encodeURI(row.TYPE) + '&DESCRIBE='
								+ row.DESCRIBE + '&MAIN_TYPE=' + row.MAIN_TYPE
								+ '&TRADE_TYPE=' + row.TRADE_TYPE);
	}
}

// 删除定量打分项信息
function destroyType(row) {
	if (row) {
		jQuery.ajax( {
			url : "EvaluateDictionary!deleteDictionary.action",
			data : "TYPE=" + encodeURI(row.TYPE) + "&MAIN_TYPE="
					+ encodeURI(row.MAIN_TYPE) + "&TRADE_TYPE="
					+ encodeURI(row.TRADE_TYPE),
			dataType : "json",
			success : function(date) {
				if (date.flag == true) {
					alert("操作成功");
					window.location = "EvaluateDictionary!getDataList.action";
				} else {
					alert("操作失败请重试！");
				}
			},
			error : function(e) {
				alert(e.message);
			}
		});
	}
}
// 删除定量打分项信息 Add By YangJ 2014年6月3日 15:29:55
function deleteType(row) {
	if (row) {
		jQuery.ajax( {
			url : "EvaluateDictionary!delDictionary.action",
			data : "TYPE=" + encodeURI(row.TYPE) + "&TRADE_TYPE="
					+ encodeURI(row.TRADE_TYPE),
			dataType : "json",
			success : function(date) {
				if (date.flag == true) {
					alert("操作成功");
					window.location = "EvaluateDictionary!getDataList.action";
				} else {
					alert("操作失败请重试！");
				}
			},
			error : function(e) {
				alert(e.message);
			}
		});
	}
}

function test() {

	var obj = $("#CR_TRADE_TYPE").combobox('getValues');
	$("#test").val(obj);
}

function addRecordButton() {

	$("#CR_TRADE_TYPES").val($("#CR_TRADE_TYPE").combobox('getValues'));
	var CR_TRADE_TYPES = $("#CR_TRADE_TYPES").val();
	if (!check()) {

		return;
	}

	// 保存定量打分项信息
	if (!checkNull()) {
		return;
	}
	if (!checkFlag()) {
		return;
	}

	var saveRecord = new Array();
	$(".addData:not(.templete)").each(function() {
		var temp = {};
		// 名称
			var FLAG = $(this).find("[name=FLAG]").val();
			temp.FLAG = FLAG;
			if ($("#DESCRIBE").val() == "1") {
				temp.START_NUM = FLAG.split("~")[0];
				temp.END_NUM = FLAG.split("~")[1];
			}
			if ($("#DESCRIBE").val() == "2") {
				if (FLAG.indexOf("&&") > 0)
					FLAG = FLAG.replace("&&", "and");
				temp.EXPRESSION = FLAG;
			}
			// 简称
			temp.FLAG_INTNA = $(this).find("[name=FLAG_INTNA]").val();
			// 级别
			temp.LEVEL_NUM = $(this).find("[name=FIFC_ORDER1]").val();
			// 备注
			temp.REMARK = $(this).find("[name=REMARK]").val();
			saveRecord.push(temp);
		});
	var addData = {
		RECORD_NAME : $("#RECORD_NAME").val(),
		DESCRIBE : $("#DESCRIBE").val(),
		MAIN_TYPE : $("#MAIN_TYPE").val(),
		TRADE_TYPE : CR_TRADE_TYPES,
		// 15:36:59
		RECORD_LIST : saveRecord
	};
	var alldata = JSON.stringify(addData);
	jQuery.ajax( {
		url : "EvaluateDictionary!addEvaluateDictionary.action",
		type : "post",
		data : "alldata=" + encodeURI(alldata),
		dataType : "JSON",
		success : function(data) {
			if (data.flag == true) {
				alert("添加成功");
				window.location = "EvaluateDictionary!getDataList.action";
			} else {
				alert("添加失败 ");
			}
		},
		error : function(i) {
			alert("网络连接失败，请重试");
		}
	});
}

// 修改定量打分项
function updateRecordButton() {

	if (!checkForChange()) {
		return;
	}

	if (!checkNull()) {
		return;
	}
	if (!checkFlag()) {
		return;
	}
	$("#CR_TRADE_TYPES").val($("#CR_TRADE_TYPE").combobox('getValues'));
	var CR_TRADE_TYPES = $("#CR_TRADE_TYPES").val();
	var updateRecord = new Array();
	$(".addData:not(.templete)").each(function() {
		var temp = {};
		// ID
			temp.DATA_ID = $(this).find("[name=DATA_ID]").val();
			// 名称
			var FLAG = $(this).find("[name=FLAG]").val();
			temp.FLAG = FLAG;
			if ($("#DESCRIBE").val() == "1") {
				temp.START_NUM = FLAG.split("~")[0];
				temp.END_NUM = FLAG.split("~")[1];
			}
			if ($("#DESCRIBE").val() == "2") {
				if (FLAG.indexOf("&&") > 0)
					FLAG = FLAG.replace("&&", "and");
				temp.EXPRESSION = FLAG;
			}
			// 分值
			temp.FLAG_INTNA = $(this).find("[name=FLAG_INTNA]").val();
			// 级别
			temp.LEVEL_NUM = $(this).find("[name=FIFC_ORDER1]").val();
			// 备注
			temp.REMARK = $(this).find("[name=REMARK]").val().replace(/\%/g,
					"％");
			// 状态
			if ($(this).find("[name=USTATUS]").attr("STATUS") == 0) {
				temp.STATUS = 1;
			}
			if ($(this).find("[name=USTATUS]").attr("STATUS") == 1) {
				temp.STATUS = 0;
			}
			updateRecord.push(temp);
		});

	var addData = {
		RECORD_NAME : $("#RECORD_NAME").val(),
		DESCRIBE : $("#DESCRIBE").val(),
		MAIN_TYPE : $("#MAIN_TYPE").val(),
		TRADE_TYPE : CR_TRADE_TYPES,
		// 17:06:48
		RECORD_LIST : updateRecord
	};
	var alldata = JSON.stringify(addData);
	jQuery.ajax( {
		url : "EvaluateDictionary!updateEvaluateDictionary.action",
		type : "post",
		data : "alldata=" + encodeURI(alldata),
		dataType : "json",
		success : function(date) {
			if (date.flag == true) {
				//debugger;
		alert("修改成功");
		top.updateEvaluateDictionary();
		top.closeTab("定量打分项修改");
	} else {
		alert("修改失败 ");
	}
},
error : function(i) {
	alert("网络连接失败，请重试");
}
	});
}

// 判断填写是否有重复的名称 FLAG

function checkFlag() {
	var fl = $(".addData:not(.templete)").find("[name=FLAG]");
	for ( var i = 0; i < fl.length; i++) {
		for ( var j = i + 1; j < fl.length; j++) {
			if ($(fl[i]).val() == $(fl[j]).val()) {
				alert("名称不能相同！！");
				return false;
			}
		}

		if ($("#DESCRIBE").val() == "1") {
			var FLAG = $(fl[i]).val();
			var START_NUM = FLAG.split("~")[0];
			var END_NUM = FLAG.split("~")[1];
			if (isNaN(START_NUM) || isNaN(END_NUM)) {
				alert("请填写正确的区间数值！例：10~20");
				return false;
			}
		}

	}
	return true;
}

function checkNumber(temp) {
	if (isNaN($(temp).val()) && $("#DESCRIBE").val() != "2") {
		alert("请填写正确的对应分值！");
		$(temp).val("0");
	}
}

function checkForChange() {
	var TRADE_TYPE1 = $("#CR_TRADE_TYPE").combobox('getValues');
	var TRADE_TYPE2 = $("#CR_TRADE_TYPES").val();
	var TRADE_TYPE3 = "";
	for ( var i = 0; i < TRADE_TYPE1.length; i++) {
		if (TRADE_TYPE2.indexOf(TRADE_TYPE1[i]) < 0) {
			TRADE_TYPE3 = TRADE_TYPE3 + TRADE_TYPE1[i] + ",";
		}
	}

	var TYPE = $("#RECORD_NAME").val();
	var TRADE_TYPE = TRADE_TYPE3;
	if (TYPE == "") {
		alert("请填写类型!");
		return false;
	}

	if (TRADE_TYPE == "")
		return true;

	return checkTypeAjax(TYPE, TRADE_TYPE);
}

function checkTypeAjax(TYPE, TRADE_TYPE) {
	var flag = false;
	jQuery.ajax( {
		url : "EvaluateDictionary!checkType.action",
		type : "post",
		data : {
			TYPE : TYPE,
			TRADE_TYPE : TRADE_TYPE
		},
		dataType : "json",
		async:false,
		success : function(data) {
			if (data.flag == true) {
				alert(data.msg);
			} else {
				flag = true;
			}
		},
		error : function(i) {
			alert("网络连接失败，请重试");
		}
	});
	return flag;
}

// 判断类型名称是否已存在
function check() {
	var TYPE = $("#RECORD_NAME").val();
	var TRADE_TYPE = $("#CR_TRADE_TYPES").val();
	if (TYPE == "") {
		alert("请填写类型!");
		return false;
	}

	return checkTypeAjax(TYPE, TRADE_TYPE);
}

// 判断填写是否合法
function checkNull() {
	var sub = true;
	$(".addData:not(.templete)").each(function() {
		if ($.trim($(this).find("[name=FLAG]").val()).length == 0) {
			alert("选项不能为空!! 如果有空行请删除！！ ");
			sub = false;
			return false;
		}

		if ($.trim($(this).find("[name=FLAG_INTNA]").val()).length == 0) {
			alert("对应分值不能为空!! 如果有空行请删除！！ ");
			sub = false;
			return false;
		}
	});
	if (!sub) {
		return false;
	}

	$(".addData:not(.templete)").each(function() {
		if ($.trim($(this).find("[name=FIFC_ORDER1]").val()) == 0) {
			alert("级别不能为空!!")
			sub = false;
			return false;
		}
	})
	if (!sub) {
		return false;
	}

	return true;
}