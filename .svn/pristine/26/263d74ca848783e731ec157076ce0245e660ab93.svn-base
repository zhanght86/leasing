var _isNotLoad = true;
$(function() {
	if (colSize == -1) {
		colSize = $("#COLSIZE").val();
	}
	// 判断字段长度小于10时自动设置宽度
	var fitFlag = false;
	if (parseInt(colSize) < 10) {
		fitFlag = true;
	}

	// 动态生成列标题
	var columns = new Array(); // 定义列集合
	
	for (var i = 1; i <= parseInt(colSize); i++) {
		// 循环放入定义数组
		columns.push( {
			field : $("#FIELD" + i).val(),
			title : $("#COLUMN" + i).val(),
			width : 100,
            align: 'center'
		});
	}
	
	$("#"+tableId).datagrid({
		url : url ,
		columns : [ columns ],
		toolbar : '#'+toolbarId,// 工具条
		pagination : true,// 分页
		rownumbers : true,
		idField : 'ID',
//		nowrap : true,
//		autoRowHeight : true,
//		checkOnSelect : true,
		fit:true,
		fitColumns : fitFlag,
		pageSize : rowSize,
		pageList : [  20, 50, 100, 200, 300 ]
	});
	// 根据用户配置 设置字段的显示隐藏
	setHideColumn();
});

function setHideColumn() {
	var COLUMN_NAMES = $('#COLUMN_NAMES').val();
	var strs = COLUMN_NAMES.split(",");
	// 如果没有配置全部显示
	if (strs == null || strs == "") {
		for ( var i = 1; i <= parseInt(colSize); i++) {
			var field = $("#FIELD" + i).val();
			$("#"+tableId).datagrid("showColumn", field);
		}
	} else {
		// 如果配置先全部隐藏再显示配置字段
		for ( var i = 1; i <= parseInt(colSize); i++) {
			var field = $("#FIELD" + i).val();
			$("#"+tableId).datagrid("hideColumn", field);
		}
		for ( var i = 0; i < strs.length; i++) {
			// 设置选中
			$('#COLUMN_NAME').combobox("select", strs[i]);
			alert(strs[i]);
			$("#"+tableId).datagrid("showColumn", strs[i]);
		}
	}
}

//查询按钮
function tofindData(url){
	var searchParams = getFromData('#'+toolbarId);
	// 修改显示字段
	setHideColumn();
	
	if (_isNotLoad) {
		$('#' + tableId).datagrid({
			url : url,
			queryParams : {
				"searchParams" : searchParams
			}
		});
		_isNotLoad = false;
	} else {
		$('#' + tableId).datagrid('load', {
			"searchParams" : searchParams
		});
	}
}

//清空按钮
function qingkong(){
	$(".paramData").each(function(){
		$(this).val("");
	});
}

//导出
function exportExcel(url){
	var searchParams = getFromData('#'+toolbarId);
	window.location.href = url +"?searchParams="+searchParams;
}