function searchReputation() {
	$('#reputationTable').datagrid('load', {});
}

var url;
function addReputation() {
	$('#reputationDialog').dialog('open').dialog('setTitle', '添加信誉');
	$('#reputationAdd').form('clear');
	$('#reputation_CLIENT_ID').val($('#clientId').val());
	url = '../crm/CreditDossier!doSaveReputation.action';
}

function openUpdateReputation() {
	$('#reputationUpdate').form('clear');
	var row = $("#reputationTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示","请选择要修改的信誉记录信息");
		return true;
	}
	$('#reputationUpdateDialog').dialog('open').dialog('setTitle', '修改信誉记录');
	
	$('#UPDATE_REPUT_ID').val(row.ID);         
	$('#UPDATE_LOAN_INFO').combobox("select",row.LOAN_ID);
	$('#UPDATE_CREDIT_INFO').combobox("select",row.CREDIT_ID);
	$('#UPDATE_PAY_STATUS').combobox("select",row.PAY_ID);
	$('#UPDATE_DEBT_INFO').combobox("select",row.DEBT_ID);
	$('#UPDATE_LEGAL_CASE').combobox("select",row.LEGAL_ID);
	$('#UPDATE_OLD_USER').combobox("select",row.OLD_ID);
	$('#UPDATE_LEVE_ID').combobox("select",row.LEVE_ID);
	$('#UPDATE_REPUT_REMARK').val(row.REMARK);
    
}


function updateReputation() {
	$('#reputationUpdate').form('submit', {
		url : '../crm/CreditDossier!doUpdateReputation.action',
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#reputationUpdateDialog').dialog('close'); // close the dialog
				$('#reputationTable').datagrid('load', {
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



function saveReputation() {
	$('#reputationAdd').form('submit', {
		url : url,
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#reputationDialog').dialog('close'); // close the dialog
				$('#reputationTable').datagrid('load', {});
			} else {
				$.messager.alert("提示","保存失败请重试！");
			}
		},
		error : function(e) {
			alert(e.message);
		}
			});
}
function delReputation() {
	var row = $("#reputationTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示","请选择要删除的信誉记录信息");
		return true;
	}
	if (confirm("确定要删除此记录？")) {
		jQuery.ajax( {
			url : "../crm/CreditDossier!doDeleteReputation.action",
			data : "ID=" + row.ID,
			dataType : "json",
			success : function(date) {
				if (date.flag == true) {
					$('#reputationTable').datagrid('load', {});
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



function setReputationrDel(val, row) {
	return "<a href='#'  onclick='delReputation(" + val + ")'>删除</a>";
}
function setTool(val, row) {
	return "<a href='#' title='"+val+"' >"+val+"</a>";
}


