var _isNotLoad = true;
$(function() {
	if (colSize == -1) {
		colSize = $("#COLSIZE").val();
	}
//	var dataUrl =_basePath+"/clientReport/ClientReportTable!doTableShow.action";
	// 判断字段长度小于10时自动设置宽度
	var fitFlag = false;
	if (parseInt(colSize) < 10) {
		fitFlag = true;
	}
	// 动态生成列标题
	var columns = new Array(); // 定义列集合
	// 显示选择框
//	columns.push( {
//		field : 'ID',
//		checkbox : true,
//		width : 100
//	});
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
//		url : dataUrl,
		columns : [ columns ],
		toolbar : '#'+toolbarId,// 工具条
		pagination : true,// 分页
		idField : 'ID',
		autoRowHeight : true,
		checkOnSelect : true,
		nowrap : true,
		fitColumns : fitFlag,
		rownumbers : true,
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
			$("#"+tableId).datagrid("showColumn", strs[i]);
		}

	}
}
// 查询
function se(dataUrl,chartUrl) {
	var searchParams = getFromData('#'+toolbarId);
	// 查询前先判断字段选项是否改变
	var COLUMN_NAMES = $('#COLUMN_NAME').combobox('getValues');
	var VALUE_STR = "";
	for ( var i = 0; i < COLUMN_NAMES.length; i++) {
		VALUE_STR += COLUMN_NAMES[i] + ",";
	}
	$('#COLUMN_NAMES').val(COLUMN_NAMES);
	// 修改显示字段
	setHideColumn();
	VALUE_STR = VALUE_STR.substring(0, VALUE_STR.length - 1);
	
	 if(_isNotLoad){
	 $('#'+tableId).datagrid({
	 url : dataUrl,
	 queryParams : {
	 "searchParams" : searchParams,

	 "COLUMN_NAMES" : VALUE_STR
	 }
	 });
	 _isNotLoad = false;
	 }else{
	 $('#'+tableId).datagrid('load', {
	 "searchParams" : searchParams,

	 "COLUMN_NAMES" : VALUE_STR
	 });
	 }
	$.ajax({
		type:'post',
		url:chartUrl,
		data:"param="+searchParams,
		dataType:'json',
		success:function(json){
			if(json.flag){
				$("#chartShow").html(json.data);
			}
		}
	});
}


function se1(dataUrl,chartUrl) {
	var searchParams = getFromData('#'+toolbarId);
	// 查询前先判断字段选项是否改变
	var COLUMN_NAMES = $('#COLUMN_NAME').combobox('getValues');
	var VALUE_STR = "";
	for ( var i = 0; i < COLUMN_NAMES.length; i++) {
		VALUE_STR += COLUMN_NAMES[i] + ",";
	}
	$('#COLUMN_NAMES').val(COLUMN_NAMES);
	// 修改显示字段
	setHideColumn();
	VALUE_STR = VALUE_STR.substring(0, VALUE_STR.length - 1);
	
	 if(_isNotLoad){
	 $('#'+tableId).datagrid({
	 url : dataUrl,
	 queryParams : {
	 "searchParams" : searchParams,

	 "COLUMN_NAMES" : VALUE_STR
	 }
	 });
	 _isNotLoad = false;
	 }else{
	 $('#'+tableId).datagrid('load', {
	 "searchParams" : searchParams,

	 "COLUMN_NAMES" : VALUE_STR
	 });
	 }
	$.ajax({
		type:'post',
		url:chartUrl,
		data:"param="+searchParams,
		dataType:'json',
		success:function(json){
			if(json.flag){
				$("#chartShow").html(json.data);
			}
		}
	});
}

// 导出
function exportExcel(flag,expUrl) {
	// 获取选中行
	var datagridList = $('#'+tableId).datagrid('getChecked');
	var sqlData = [];
//	if(reportName == 'T102Report'&& $.trim($("#point_date").val()).length<=0 ){
//		alert("请选择节点日期");
//		return false;
//	}
    
    var options = $('#'+tableId).datagrid('getPager').data("pagination").options; 
    var totalRowNum = options.total;

    if(flag == 'all'&&totalRowNum>50000){
    	alert("@_@#..导出条数超出5万了，暂时屏蔽大数据导出，若急需请联系管理员！！");
    	return;
    }
    
	for ( var i = 0; i < datagridList.length; i++) {
		sqlData.push("'" + datagridList[i].ID + "'");
	}
	// 传递参数
	var searchParams = getFromData('#'+toolbarId);
//	var expUrl = reportName + "!exportExcel.action";
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
	$('#'+formid).form(
					'submit',
					{
						url : expUrl,
						onSubmit : function() {
							if ($('#searchParams').length <= 0) {
								$('#'+formid)
										.append(
												'<input name=\"searchParams\" id=\"searchParams\" type=\"hidden\" />');
							}
							$('#searchParams').val(searchParams);
							// 导出标识
							if ($('#sqlData').length <= 0) {
								$('#'+formid)
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
	$('#'+formid).form('clear');
}
function clearColumn(){
	$("#"+toolbarId+" #COLUMN_NAME").combobox("clear");
}
