/**
 * 催收台账js
 */
function dosearch(){
	var DLD = $("#DLD").val();
	var PROJ_ID = $("#PROJ_ID").val();
	var KHMC = $("#KHMC").val();
	var URGE_DATE_BEGIN = $("#URGE_DATE_BEGIN").datebox("getValue");
	var URGE_DATE_END = $("#URGE_DATE_END").datebox("getValue");
	$('#pageTable').datagrid('load', {"DLD":DLD,"PROJ_ID":PROJ_ID,"KHMC":KHMC,"URGE_DATE_BEGIN":URGE_DATE_BEGIN,"URGE_DATE_END":URGE_DATE_END});
}

function setOperation(val, row) {
	return "<a href='#' style='color:blue;'  onclick='showReasonRecord(" + val + ")'>催收在线 </a>";
}
/**
 * 清空按钮
 */
function emptyData(){
	//清空日期
	$("#URGE_DATE_BEGIN").datebox('clear');
	$("#URGE_DATE_END").datebox('clear');
	//清空input
	$(".paramData").each(function(){
		$(this).val("");
	});
}
//////////////////////////////以下为dlg功能/////////////////////////////////////////////////
//显示 呼叫中心-催收记录 页面
function showReasonRecord(ID){
	parent.addTab("呼叫中心-催收记录", _basePath + "/call/UrgeBook!toShowUrgeRecord.action?ID=" + ID);
}
//添加催收信息
function doAddNewMessage(ID){
	var text = $("#ID_CONTENT").attr("value");
	if(null == text || "" == $.trim(text)){
		$.messager.alert("提示","请输入内容！");
		return;
	}else{
		jQuery.ajax({
			url : _basePath + "/call/UrgeBook!doAddMessage.action",
			//data : "RECORD_ID=" + ID + "",
			data : { "RECORD_ID": ID, "M_CONTENT": text },
			dataType:'json',
			success:function(data){
				//刷新页面
				showReasonRecord(ID);
			}
		});
	}
}
//删除催收信息
function deleteMessage(ID,Record_ID){
	$.messager.confirm("删除","确定要删除此留言吗？",function(r){
		if(r){
			jQuery.ajax({
				url : _basePath + "/call/UrgeBook!doDeleteMessage.action",
				data : { "ID": ID},
				dataType:'json',
				success:function(data){
					$.messager.alert("结果","删除成功！");
					//刷新页面
					showReasonRecord(Record_ID);//这个是逾期记录的ID
				}
			});
		}
	});
}

//导出 TODO
function exportExcel(flag){
	//data
	var datagridList=$('#pageTable').datagrid('getChecked');//得到选中行
	var sqlData = [];	
	
	for(var i = 0; i < datagridList.length; i++){
		sqlData.push("'"+datagridList[i].ID+"'");
	}
	
	//params
	var searchParams = getFromData('#pageForm');
	
	//url
	var url = _basePath + "/call/UrgeBook!exportExcel.action";
	if(flag == 'all'){
		url += "?exportAll=true";
	}else{
		url += "?exportAll=false";
		if(datagridList.length == 0){
			alert("请选择要导出的数据");
			return;
		}
	}
	//submit
	$('#fm_search').form('submit',{
        url:url,
        onSubmit: function(){
			//查询参数
			if($('#searchParams').length<=0){
				$('#fm_search').append('<input name=\"searchParams\" id=\"searchParams\" type=\"hidden\" />');
			}
			$('#searchParams').val(searchParams);
			//导出标识
			if($('#sqlData').length<=0){
				$('#fm_search').append('<input name=\"sqlData\"  id=\"sqlData\" type=\"hidden\" />');
			}
			$('#sqlData').val(sqlData.join(','));
        }
    });
	//remove
	$('#sqlData').remove();
	$('#searchParams').remove();
}