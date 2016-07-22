var _isNotLoad = true;
//列的总数量
var colSize = 1;

//报表名称
var reportName = 'SuperTable';

var rowSize = 20;
$(function() {
	// alert(colSize);
	
		colSize = $("#COLSIZE").val();
	
	// alert(colSize);

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
		
		if(i==5){
			columns.push({
                field: "COLUMN" + i.toString(),
                title: $("#COLUMN" + i).val(),
                width: 100,
                align: 'center',
                formatter: rowformater
            });
		}else{
		columns.push( {
			field : "COLUMN" + i.toString(),
			title : $("#COLUMN" + i).val(),
			width : 100,
            align: 'right'
		});
		}
	}
	$("#table1").datagrid( {
//		url : dataUrl,
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

function rowformater(value,row,index){
	return "<a  href='javascript:void(0)' class='easyui-linkbutton' onclick='sechemSHow("+row.ID+")'>"+value+"</a>";
}
function lookJbpmList(PROJECT_ID){
	top.addTab("流程查看",_basePath+"/project/project!doShowProjectJbpmList.action?PROJECT_ID="+PROJECT_ID);
}
function sechemSHow(PROJECT_ID)
{

		
		 top.addTab("项目方案",_basePath+"/project/project!projectShow.action?PROJECT_ID="+PROJECT_ID);
	
}


// 查询
function se() {

	var dataUrl = reportName + '!doTableShow.action';
	var searchParams = getFromData('#toolbar');
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
	 $('#table1').datagrid({
	 url : dataUrl,
	 queryParams : {
	 "searchParams" : searchParams,

	 "COLUMN_NAMES" : VALUE_STR
	 }
	 });
	 _isNotLoad = false;
	 }else{
	 $('#table1').datagrid('load', {
	 "searchParams" : searchParams,

	 "COLUMN_NAMES" : VALUE_STR
	 });
	 }

//	$('#table1').datagrid('load', {
//		"searchParams" : searchParams,
//		"COLUMN_NAMES" : VALUE_STR,
//		"COLUMN_NAMES" : VALUE_STR
//	});

}

// 导出
function exportExcel(flag) {
	// 获取选中行
	var datagridList = $('#table1').datagrid('getChecked');
	var sqlData = [];

	for ( var i = 0; i < datagridList.length; i++) {
		sqlData.push("'" + datagridList[i].ID + "'");
	}
	var  columnsize=0;
	var COLUMN_NAMES = $('#COLUMN_NAMES').val();
	var strs = COLUMN_NAMES.split(",");
	// 如果没有配置全部显示
	if (strs == null || strs == "") {
		columnsize=$("#COLSIZE").val();
	}else{
		columnsize=strs.length;
	}
		
    var options = $('#table1').datagrid('getPager').data("pagination").options; 
    var totalRowNum = options.total;

//    alert(totalRowNum * columnsize);
  
//    if(flag == 'all'&&totalRowNum * columnsize >200000 ){
//    	alert("@_@#..导出   条数*字段    超出100万了，建议选择必要的字段和过滤条件再导出，若急需全导出请联系管理员！！");
//    	return ;
//    }	
//    
	// 传递参数
	var searchParams = getFromData('#toolbar');
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

}
function clearColumn(){

	$('#COLUMN_NAME').combobox("clear");
//	var COLUMN_NAMES = $('#COLUMN_NAME').combobox('getValues');
//	for ( var i = 0; i < COLUMN_NAMES.length; i++) {
//		$('#COLUMN_NAME').combobox("unselect", COLUMN_NAMES[i]);
//		
//	}

}