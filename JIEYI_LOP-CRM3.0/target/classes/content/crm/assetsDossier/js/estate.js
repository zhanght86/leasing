function searchEstate() {
	$('#estateTable').datagrid('load', {});
}

var url;
function addEstate() {
	//在url里面获取tab参数，如果参数值为view则是查看页面不让操作
    var param = getUrl_().tab;
    if (param != 'view') {
		$('#estateDialog').dialog('open').dialog('setTitle', '添加土地');
		$('#estateAdd').form('clear');
		$('#estate_CLIENT_ID').val($('#clientId').val());
		url = '../crm/AssetsDossier!doSaveEstate.action';
	}
}
function saveEstate() {
	$('#estateAdd').form('submit', {
		url : url,
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#estateDialog').dialog('close'); // close the dialog
		$('#estateTable').datagrid('load', {});
	} else {
		$.messager.alert("提示","保存失败请重试！");
	}
},
error : function(e) {
	alert(e.message);
}
	});
}
function delEstate(ID) {
	var row = $("#estateTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示", "请选择要删除的设备信息");
		return true;
	}
	if (confirm("确定要删除此记录？")) {
		jQuery.ajax( {
			url : "../crm/AssetsDossier!doDeleteEstate.action",
			data : "ID=" + row.ID,
			dataType : "json",
			success : function(date) {
				if (date.flag == true) {
					$('#estateTable').datagrid('load', {});
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



function openUpdateEstate() {
	$('#estateUpdate').form('clear');
	var row = $("#estateTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示","请选择要修改的设备信息");
		return true;
	}
	$('#estateUpdateDialog').dialog('open').dialog('setTitle', '修改土地');
	
	$('#UPDATE_ESTATE_ID').val(row.ID);
	$('#UPDATE_ESTATE_AREA').val(row.ESTATE_AREA);  
	$('#UPDATE_ESTATE_ADDR').val(row.ESTATE_ADDR);
	$('#UPDATE_ESTATE_VALUATION').val(row.ESTATE_VALUATION);  
	$('#UPDATE_ESTATE_PROFIT').val(row.ESTATE_PROFIT);  
	$('#ESTATE_OWNER_NAME').val(row.OWNER_NAME);  
	$('#ESTATE_BUY_TIME').datebox("setValue",row.BUY_TIME);
	$('#ESTATE_REMARK').val(row.REMARK);
    
}

function updateEstate() {
	$('#estateUpdate').form('submit', {
		url : '../crm/AssetsDossier!doUpdateEstate.action',
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#estateUpdateDialog').dialog('close'); // close the dialog
				$('#estateTable').datagrid('load', {
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

