
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
	// 显示选择框
	columns.push( {
		field : 'ck',
		checkbox : true,
		width : 100
	});
	for (var i = 1; i <= parseInt(colSize); i++) {
		// 循环放入定义数组
		columns.push( {
			field : $("#FIELD" + i).val(),
			title : $("#COLUMN" + i).val(),
			width : 100,
            align: 'center'
		});
	}
	$("#"+tableId).datagrid( {
		url : dataUrl,
		columns : [ columns ],
		toolbar : '#'+toolbarId,// 工具条
		pagination : true,// 分页
		singleSelect:false,
		checkOnSelect : true,
		rownumbers : true,
		autoRowHeight : true,
		nowrap : true,
		fitColumns : fitFlag,
		pageSize : rowSize,
		pageList : [20, 50, 100, 200, 300 ]
	});
	
	  
	
});

