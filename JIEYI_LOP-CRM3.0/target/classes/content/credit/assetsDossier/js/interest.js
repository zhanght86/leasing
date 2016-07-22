function searchInterest() {
	$('#interestTable').datagrid('load', {});
}

var url;
function addInterest() {
	//在url里面获取tab参数，如果参数值为view则是查看页面不让操作
    var param = getUrl_().tab;
    if (param != 'view') {
		$('#interestDialog').dialog('open').dialog('setTitle', '添加知识产权');
		$('#interestAdd').form('clear');
		$('#interest_CLIENT_ID').val($('#clientId').val());
		$('#interest_CREDIT_ID').val($('#creditId').val());
		url = '../credit/CreditAssetsDossier!doSaveInterest.action';
	}
}

function saveInterest() {
	$('#interestAdd').form('submit', {
		url : url,
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#interestDialog').dialog('close'); // close the dialog
		$('#interestTable').datagrid('load', {});
	} else {
		$.messager.alert("提示","保存失败请重试！");
	}
},
error : function(e) {
	alert(e.message);
}
	});
}
function delInterest(ID) {
	var row = $("#interestTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示", "请选择要删除的设备信息");
		return true;
	}
	if (confirm("确定要删除此记录？")) {
		jQuery.ajax( {
			url : "../credit/CreditAssetsDossier!doDeleteInterest.action",
			data : "ID=" + row.ID,
			dataType : "json",
			success : function(date) {
				if (date.flag == true) {
					$('#interestTable').datagrid('load', {});
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



function openUpdateInterest() {
	$('#interestUpdate').form('clear');
	var row = $("#interestTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示","请选择要修改的产权信息");
		return true;
	}
	$('#interestUpdateDialog').dialog('open').dialog('setTitle', '修改知识产权');
	$('#UPDATE_INTEREST_ID').val(row.ID);
	$('#UPDATE_INTEREST_NAME').val(row.INTEREST_NAME);
	$('#UPDATE_INTEREST_HUMAN').val(row.INTEREST_HUMAN);  
	$('#UPDATE_INTEREST_DATE').datebox("setValue",row.INTEREST_DATE);
	$('#UPDATE_VALIDITY_DATE').datebox("setValue",row.VALIDITY_DATE);
	$('#UPDATE_INTEREST_TYPE').val(row.INTEREST_TYPE_CODE);  
    
}

function updateInterest() {
	$('#interestUpdate').form('submit', {
		url : '../credit/CreditAssetsDossier!doUpdateInterest.action',
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#interestUpdateDialog').dialog('close'); // close the dialog
				$('#interestTable').datagrid('load', {
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
