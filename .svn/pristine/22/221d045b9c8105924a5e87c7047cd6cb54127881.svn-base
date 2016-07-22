function clear_() {
	$('#queryForm').form('clear');
}
function selectPage(checkbox) {
	var tt = checkbox;
	if (tt.checked) {
		$('#pageTable').datagrid("selectAll");
	} else {
		$('#pageTable').datagrid("unselectAll");
	}
}
function se() {
	var content = {};
	$("#queryForm :input").each(function() {
		if ($(this).attr("name") == undefined)
			return;
		content[$(this).attr("name")] = $(this).val();
	});
	$('#pageTable').datagrid('load', content);
}
function approval(parm) {
	if (!confirm("请确认提交违约金减免申请？"))
		return;
	var data = $('#pageTable').datagrid("getSelections");
	if (data.length == 0) {
		$.messager.show({
			title : '操作错误提示',
			msg : '请选择要操作的行',
			showType : 'show'
		});
		return false;
	}
	var ids = "";
	$(data).each(function() {
		ids += this.ID + ";";
	});
	jQuery.ajax({
		url : _basePath + "/overdue/Overdue!doExemptOverdue.action",
		data : {
			ids : ids
		},
		dataType : "json",
		success : function(json) {
			if (json.flag) {
				alert("成功");
				$('#pageTable').datagrid('reload');
				if(json.data) top.addTab("我的任务.免息"+json.data,_basePath+"/bpm/Task!toShow.action?taskId="+json.data);
			} else {
				alert(json.msg);
			}
		}
	});
}

function appAll() {
	if (!confirm("请确认申请减免所有客户的违约金？"))
		return;
	var data = {};
	$("#pageForm :input").each(function(){
		if($(this).attr("name")!=undefined)	data[$(this).attr("name")]=$(this).val();
	});
	jQuery.ajax({
		url : _basePath + "/overdue/Overdue!doExemptOverdueAll.action",
		data : data,
		dataType : "json",
		success : function(json) {
			if (json.flag) {
				alert("成功");
				$('#pageTable').datagrid('reload');
				if(json.data) top.addTab("我的任务.免息"+json.data,_basePath+"/bpm/Task!toShow.action?taskId="+json.data);
			} else {
				alert(json.msg);
			}
		}
	});
}
