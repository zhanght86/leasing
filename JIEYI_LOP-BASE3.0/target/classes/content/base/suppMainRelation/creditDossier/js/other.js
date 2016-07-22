function searchOther() {
	$('#otherTable').datagrid('load', {});
}

var url;
function addOther() {
	$('#otherDialog').dialog('open').dialog('setTitle', '添加其他资产');
	$('#otherAdd').form('clear');
	url = '../crm/AssetsDossier!doSaveOther.action';
}
function saveOther() {
	$('#otherAdd').form('submit', {
		url : url,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#otherDialog').dialog('close'); // close the dialog
		$('#otherTable').datagrid('load', {});
	} else {
		$.messager.alert("提示","保存失败请重试！");
	}
},
error : function(e) {
	alert(e.message);
}
	});
}
function delOther(ID) {
	if (confirm("确定要删除此记录？")) {
		jQuery.ajax( {
			url : "../crm/AssetsDossier!doDeleteOther.action",
			data : "ID=" + ID,
			dataType : "json",
			success : function(date) {
				if (date.flag == true) {
					$('#otherTable').datagrid('load', {});
				} else {
					$.messager.alert("提示","删除失败请重试！");
				}
			},
			error : function(e) {
				alert(e.message);
			}
		});
	}
}



function setOtherDel(val, row) {
	return "<a href='#' onclick='delOther(" + val + ")'>删除</a>";
}