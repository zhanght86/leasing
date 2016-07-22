function searchHouse() {
	$('#houseTable').datagrid('load', {});
}

var url;
function addHouse() {
	//在url里面获取tab参数，如果参数值为view则是查看页面不让操作
    var param = getUrl_().tab;
    if (param != 'view') {
		$('#houseDialog').dialog('open').dialog('setTitle', '添加房产');
		$('#houseAdd').form('clear');
		$('#house_SUP_ID').val($('#supId').val());
		url = _basePath+'/base/suppliersInfo/AssetsDossier!doSaveHouse.action';
	}
}
function saveHouse() {
	$('#houseAdd').form('submit', {
		url : url,
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#houseDialog').dialog('close'); // close the dialog
				$('#houseTable').datagrid('load', {});
			} else {
				$.messager.alert("提示","保存失败请重试！");
			}
		},
		error : function(e) {
			$.messager.alert(e.message);
		}
	});
}
function delHouse() {

	var row = $("#houseTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示", "请选择要删除的设备信息");
		return true;
	}
	if (confirm("确定要删除此记录？")) {
		jQuery.ajax( {
			url : _basePath+"/base/suppliersInfo/AssetsDossier!doDeleteHouse.action",
			data : "ID=" + row.ID,
			dataType : "json",
			success : function(date) {
				if (date.flag == true) {
					$('#houseTable').datagrid('load', {});
				} else {
					$.messager.alert("提示","删除失败请重试！");
				}
			},
			error : function(e) {
				$.messager.alert(e.message);
			}
		});
	}
}



function openUpdateHouse() {
	$('#houseUpdate').form('clear');
	var row = $("#houseTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示","请选择要修改的设备信息");
		return true;
	}
	$('#houseUpdateDialog').dialog('open').dialog('setTitle', '修改房产');
	
	$('#UPDATE_HOUSE_ID').val(row.ID);
	$('#HOUSE_OWNER_NAME').val(row.OWNER_NAME);  
	$('#UPDATE_HOUSE_AREA').val(row.HOUSE_AREA);  
	$('#UPDATE_HOUSE_ADDR').val(row.HOUSE_ADDR);
	$('#UPDATE_HOUSE_VALUATION').val(row.HOUSE_VALUATION);  
	$('#UPDATE_HOUSE_PROFIT').val(row.HOUSE_PROFIT);  
	$('#HOUSE_BUY_TIME').datebox("setValue",row.BUY_TIME);
	$('#HOUSE_REMARK').val(row.REMARK);
    
}

function updateHouse() {
	$('#houseUpdate').form('submit', {
		url : _basePath+'/base/suppliersInfo/AssetsDossier!doUpdateHouse.action',
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#houseUpdateDialog').dialog('close'); // close the dialog
				$('#houseTable').datagrid('load', {
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


