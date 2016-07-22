function searchCourt() {
	$('#courtTable').datagrid('load', {});
}

var url;
function addCourt() {
	//在url里面获取tab参数，如果参数值为view则是查看页面不让操作
    var param = getUrl_().tab;
    if (param != 'view') {
		$('#courtDialog').dialog('open').dialog('setTitle', '添加法院信息');
		$('#courtAdd').form('clear');
		$('#court_CREDIT_ID').val($('#creditId').val());
		$('#court_CLIENT_ID').val($('#clientId').val());
		url = '../credit/CreditRepayment!doSaveCourt.action';
	}
}
function saveCourt() {
	$('#courtAdd').form('submit', {
		url : url,
		onSubmit : function() {
			return $(this).form('validate');
		},
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
		$.messager.alert("提示", "请选择要删除的信息");
		return true;
	}
	if (confirm("确定要删除此记录？")) {
		jQuery.ajax( {
			url : "../credit/CreditRepayment!doDeleteCourt.action",
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



function openUpdateCourt() {
	$('#courtTable').form('clear');
	var row = $("#courtTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示","请选择要修改的产权信息");
		return true;
	}
	$('#courtUpdateDialog').dialog('open').dialog('setTitle', '修改法院信息');
	$('#UPDATE_COURT_ID').val(row.ID);
	$('#UPDATE_COURT_DATE').datebox("setValue",row.COURT_DATE);
	$('#UPDATE_COURT_REMARK').val(row.REMARK); 
}

function updateCourt() {
	$('#courtUpdate').form('submit', {
		url : '../credit/CreditRepayment!doUpdateCourt.action',
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
