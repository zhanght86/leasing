//债务调查
function searchDebt() {
	$('#debtTable').datagrid('load', {});
}

var url;
function addDebt() {
	$('#debtDialog').dialog('open').dialog('setTitle', '添加债务');
	
	$('#debtAdd').form('clear');
	$('#SUP_ID').val($('#supId').val());
	url = _basePath+'/base/sp/CreditDossier!doSaveDebt.action';
}
function openUpdateDebt() {
	$('#debtUpdate').form('clear');
	var row = $("#debtTable").datagrid('getSelected');
	if(!row.ID){
		$.messager.alert("提示","请选择要修改的债务调查信息");
		return true;
	}
	$('#debtUpdateDialog').dialog('open').dialog('setTitle', '修改债务');
	
	$('#ID_UPDATE').val(row.ID);
	$('#DEBT_INFO_UPDATE').combobox("select",row.DATA_ID);
	$('#DEBT_MONEY_UPDATE').val(row.DEBT_MONEY);
	$('#DEBT_NAME_UPDATE').val(row.DEBT_NAME);
	$('#DEBT_TIME_UPDATE').datebox("setValue",row.DEBT_TIME);
	$('#MATURITY_TIME_UPDATE').val(row.MATURITY_TIME);
	$('#REMARK_UPDATE').val(row.REMARK);
	$('#LOAN_AMOUNT_UPDATE').val(row.LOAN_AMOUNT);
    
}

function updateDebt() {
	$('#debtUpdate').form('submit', {
		url : _basePath+'/base/sp/CreditDossier!doUpdateDebt.action',
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#debtUpdateDialog').dialog('close'); // close the dialog
				$('#debtTable').datagrid('load', {
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

function saveDebt() {
	$('#debtAdd').form('submit', {
		url : url,
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#debtDialog').dialog('close'); // close the dialog
				$('#debtTable').datagrid('load', {
					"param" : getFromData("#pForm")
				});
			} else {
				$.messager.alert("提示", "保存失败请重试！");
			}
		},
		error : function(e) {
			alert(e.message);
		}
	});
}

function delDebt() {
	var row = $("#debtTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示", "请选择要删除的债务调查信息");
		return true;
	}
	if (confirm("确定要删除此记录？")) {
		jQuery.ajax( {
			url : _basePath+"/base/sp/CreditDossier!doDeleteDebt.action",
			data : "ID=" + row.ID,
			dataType : "json",
			success : function(date) {
				if (date.flag == true) {
					$('#debtTable').datagrid('load', {});
				} else {
					$.messager.alert("提示", "操作失败请重试！");
				}
			},
			error : function(e) {
				alert(e.message);
			}
		});
	}
}


function setDebtDel(val, row) {
	return "<a href='#' onclick='delDebt(" + val + ")'>删除</a>";
}