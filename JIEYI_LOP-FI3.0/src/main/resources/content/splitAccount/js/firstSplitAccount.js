$(function(){
	
	var colSize = $("#COLSIZE").val();
	

	var fitFlag = false;
	if (parseInt(colSize) < 10) {
		fitFlag = true;
	}
	//动态生成列标题
	
	var columns = new Array(); // 定义列集合
	// 显示选择框
	columns.push( {
		field : 'ID',
		checkbox : true,
		width : 100
	});
	//alert(parseInt(colSize));
	for (i = 1; i <= parseInt(colSize); i++) {
		// 循环放入定义数组
		columns.push( {
			field : $("#NAME" + i).val(),
			title : $("#COLUMN" + i).val(),
			width : 100
		});
	}
	$("#table1").datagrid( {
		columns : [ columns ],
		toolbar : '#toolbar',// 工具条
		pagination : true ,//分页
		idField : 'ID',
		autoRowHeight : true,
		checkOnSelect : true,
		nowrap : true,
		fitColumns : fitFlag,
		rownumbers : true,
		pageSize : 20,
		pageList : [10,20,50,100,200,300]
	});
	

	$('#form01').form({
        url:""
    });
   
});


//查询
var _isNotLoad = true;
function se(){
	var searchParams = getFromData('#toolbar');
	if(_isNotLoad){
		$('#table1').datagrid({
			url : 'SplitAccount!getFirstAccountDate.action',
			queryParams : {
				"searchParams" : searchParams
				
			}
		});
		_isNotLoad = false;
	}else{
		$('#table1').datagrid('load', {
			"searchParams" : searchParams
			
		});
	}
}

//是否全选
function change(obj){
	$(obj).is(":checked") ? $('#table1').datagrid('selectAll') : $('#table1').datagrid('unselectAll') ;
}

//导出
function exportExcel(flag){
	
	//data
	var datagridList=$('#table1').datagrid('getChecked');
	var sqlData = [];	
	
	for(var i = 0; i < datagridList.length; i++)
	{
		sqlData.push("'"+datagridList[i].ID+"'");
	}
	
	//params
	var searchParams = getFromData('#toolbar');
	
	//url
	var url = "SplitAccount!exportFirstAcountExcel.action";
	if(flag == 'all')
	{
		url += "?exportAll=true";
	}else
	{
		url += "?exportAll=false";
		if(datagridList.length == 0){
			alert("请选择要导出的数据");
			return;
		}
	}
	//submit
	$('#form01').form('submit',{
        url:url,
        onSubmit: function(){
			//查询参数
			if($('#searchParams').length<=0){
				$('#form01').append('<input name=\"searchParams\" id=\"searchParams\" type=\"hidden\" />');
			}
			$('#searchParams').val(searchParams);
			//导出标识
			if($('#sqlData').length<=0){
				$('#form01').append('<input name=\"sqlData\"  id=\"sqlData\" type=\"hidden\" />');
			}
			$('#sqlData').val(sqlData.join(','));
        }
    });
	//remove
	$('#sqlData').remove();
	$('#searchParams').remove();
}
//清空
function clearQuery(form01){
	$('#form01').form('clear');
	//$('#toolbar input').not(':checkbox').val('');
    //$('#toolbar select').val('');
}

