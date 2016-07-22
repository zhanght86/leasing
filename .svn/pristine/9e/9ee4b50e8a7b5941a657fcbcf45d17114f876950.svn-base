function searchOther() {
	$('#otherTable').datagrid('load', {});
}

var url;
function addOther() {
	//在url里面获取tab参数，如果参数值为view则是查看页面不让操作
    var param = getUrl_().tab;
    if (param != 'view') {
		$('#otherDialog').dialog('open').dialog('setTitle', '添加其他资产');
		$('#otherAdd').form('clear');
		$('#other_SUP_ID').val($('#supId').val());
		url = _basePath+'/base/sp/AssetsDossier!doSaveOther.action';
	}
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
	var row = $("#otherTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示", "请选择要删除的设备信息");
		return true;
	}
	if (confirm("确定要删除此记录？")) {
		jQuery.ajax( {
			url : _basePath+"/base/sp/AssetsDossier!doDeleteOther.action",
			data : "ID=" + row.ID,
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



function openUpdateOther() {
	$('#otherUpdate').form('clear');
	var row = $("#otherTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示","请选择要修改的设备信息");
		return true;
	}
	$('#otherUpdateDialog').dialog('open').dialog('setTitle', '修改其他资产');
	$('#UPDATE_OTHER_ID').val(row.ID);
	$('#UPDATE_OTHER_TYPEID').val(row.OTHER_TYPEID);  
	$('#UPDATE_OTHER_NAME').val(row.OTHER_NAME);
	$('#UPDATE_OTHER_PRESENT').val(row.OTHER_PRESENT);  
	$('#UPDATE_OTHER_PROFIT').val(row.OTHER_PROFIT);  
	$('#OTHER_OWNER_NAME').val(row.OWNER_NAME);  
	$('#OTHER_BUY_TIME').datebox("setValue",row.BUY_TIME);
	$('#OTHER_REMARK').val(row.REMARK);
    
}

function updateOther() {
	$('#otherUpdate').form('submit', {
		url : _basePath+'/base/sp/AssetsDossier!doUpdateOther.action',
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#otherUpdateDialog').dialog('close'); // close the dialog
				$('#otherTable').datagrid('load', {
					"param" : getFromData("#pForm")
				});
			} else {
				$.messager.alert("提示","修改失败请重试！");
			}
		},
		error : function(e) {
			alert(e.message);
		}
	});
}
