
$(function() {

	if (colSize == -1) {
		colSize = $("#COLSIZE").val();
	}
//	 alert(colSize);

	var dataUrl = reportName + '!doTableShow.action';
	// 判断字段长度小于10时自动设置宽度
	var fitFlag = false;
	if (parseInt(colSize) < 10) {
		fitFlag = true;
	}
	// 动态生成列标题
	var columns = new Array(); // 定义列集合
	// 显示选择框
	columns.push( {
		field : 'ID',
		checkbox : true,
		width : 100
	});

	for (i = 1; i <= parseInt(colSize); i++) {
		// 循环放入定义数组
		columns.push( {
			field : "COLUMN" + i.toString(),
			title : $("#COLUMN" + i).val(),
			width : 100,
            align: 'right'
		});
	}

	$("#table1").datagrid( {
		url : dataUrl,
		columns : [ columns ],
		toolbar : '#toolbar',// 工具条
		pagination : true,// 分页
		idField : 'ID',
		autoRowHeight : true,
		checkOnSelect : true,
		nowrap : true,
		fitColumns : fitFlag,
		rownumbers : true,
		pageSize : rowSize,
		pageList : [ 10, 20, 50, 100, 200, 300 ]
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
			var field = "COLUMN" + i.toString();
			$("#table1").datagrid("showColumn", field);
		}
	} else {
		// 如果配置先全部隐藏再显示配置字段
		for ( var i = 1; i <= parseInt(colSize); i++) {
			var field = "COLUMN" + i.toString();
			$("#table1").datagrid("hideColumn", field);
		}
		for ( var i = 0; i < strs.length; i++) {
			// 设置选中
			$('#COLUMN_NAME').combobox("select", strs[i]);
			$("#table1").datagrid("showColumn", strs[i]);
		}

	}
}
// 查询
function se() {

	var dataUrl = reportName + '!doTableShow.action';
	
	var searchParams = getFromData('#toolbar');
	
	// 查询前先判断字段选项是否改变
	var COLUMN_NAMES = $('#COLUMN_NAME').combobox('getValues');
//	alert(COLUMN_NAMES);
	var VALUE_STR = "";
	for ( var i = 0; i < COLUMN_NAMES.length; i++) {
		VALUE_STR += COLUMN_NAMES[i] + ",";
	}
	$('#COLUMN_NAMES').val(COLUMN_NAMES);
	// 修改显示字段
	setHideColumn();
	VALUE_STR = VALUE_STR.substring(0, VALUE_STR.length - 1);
	
	if(reportName == 'T102Report'&& $.trim($('#point_date').val()).length<=0 ){
		alert("请选择节点日期");
		return false;
	}

//	 alert(dataUrl);
	 $('#table1').datagrid('load', {
	 "searchParams" : searchParams,

	 "COLUMN_NAMES" : VALUE_STR
	 });
	 



}

// 导出
function exportExcel(flag) {
	// 获取选中行
	var datagridList = $('#table1').datagrid('getChecked');
	var sqlData = [];
	if(reportName == 'T102Report'&& $.trim($("#point_date").val()).length<=0 ){
		alert("请选择节点日期");
		return false;
	}

	for ( var i = 0; i < datagridList.length; i++) {
		sqlData.push("'" + datagridList[i].ID + "'");
	}
	// 传递参数
	var searchParams = getFromData('#toolbar');
//	alert(searchParams);
	var expUrl = reportName + "!exportExcel.action";
	if (flag == 'all') {
		expUrl += "?exportAll=true";
	} else {
		expUrl += "?exportAll=false";
		if (datagridList.length == 0) {
			$.messager.alert("提示", "请选择要导出的数据");
			return;
		}
	}
	// 提交form表单
	$('#form01').form(
					'submit',
					{
						url : expUrl,
						onSubmit : function() {
							if ($('#searchParams').length <= 0) {
								$('#form01')
										.append(
												'<input name=\"searchParams\" id=\"searchParams\" type=\"hidden\" />');
							}
							$('#searchParams').val(searchParams);
							// 导出标识
							if ($('#sqlData').length <= 0) {
								$('#form01')
										.append(
												'<input name=\"sqlData\"  id=\"sqlData\" type=\"hidden\" />');
							}
							$('#sqlData').val(sqlData.join(','));
						
						}
					});
	$('#sqlData').remove();

}
// 清空
function clearQuery() {
	$('#form01').form('clear');
	// 清空日期
//	$('.easyui-datebox').datebox('clear');
//	var form01 = document.getElementById("form01");
//	form01.reset();
	// 选中显示字段
	// $('#COLUMN_NAME').combobox("select", "COLUMN100");
	// $('#COLUMN_NAME').combobox("unselect", "COLUMN100");
}
function clearColumn(){

	$('#COLUMN_NAME').combobox("clear");
//	var COLUMN_NAMES = $('#COLUMN_NAME').combobox('getValues');
//	for ( var i = 0; i < COLUMN_NAMES.length; i++) {
//		$('#COLUMN_NAME').combobox("unselect", COLUMN_NAMES[i]);
//		
//	}

}