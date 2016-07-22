function searchCourt() {
	$('#courtTable').datagrid('load', {});
}

var url;
function addCourt() {
	$('#courtDialog').dialog('open').dialog('setTitle', '添加法院信息');
	$('#courtAdd').form('clear');
	$('#court_CLIENT_ID').val($('#clientId').val());
	url = '../crm/CreditDossier!doSaveCourt.action';
}


function openUpdateCourt() {
	$('#courtUpdate').form('clear');
	var row = $("#courtTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示","请选择要修改的法院信息");
		return true;
	}
	$('#courtUpdateDialog').dialog('open').dialog('setTitle', '修改法院');
	$('#UPDATE_COURT_ID').val(row.ID);         
	$('#UPDATE_HAPPEN_TIME').datebox("setValue",row.HAPPEN_TIME);
	$('#UPDATE_REMARK').val(row.REMARK);
    
}


function updateCourt() {
	$('#courtUpdate').form('submit', {
		url : '../crm/CreditDossier!doUpdateCourt.action',
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#courtUpdateDialog').dialog('close'); // close the dialog
				$('#courtTable').datagrid('load', {
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



function saveCourt() {
	$('#courtAdd').form('submit', {
		url : url,
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#courtDialog').dialog('close'); // close the dialog
				$('#courtTable').datagrid('load', {});
			} else {
				$.messager.alert("提示","保存失败请重试！");
			}
		},
		error : function(e) {
			alert(e.message);
		}
	});
}
function delCourt() {
	var row = $("#courtTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示","请选择要删除的法院信息");
		return true;
	}
	if (confirm("确定要删除此记录？")) {
		jQuery.ajax( {
			url : "../crm/CreditDossier!doDeleteCourt.action",
			data : "ID=" + row.ID,
			dataType : "json",
			success : function(date) {
				if (date.flag == true) {
					$('#courtTable').datagrid('load', {});
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


function setCourtDel(val, row) {
	return "<a href='#' onclick='delCourt(" + val + ")'>删除</a>";
}