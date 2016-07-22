function searchEquipment() {
	$('#equipmentTable').datagrid('load', {});
}

var url;
function addEquipment() {
	//在url里面获取tab参数，如果参数值为view则是查看页面不让操作
    var param = getUrl_().tab;
    if (param != 'view') {
	    $('#equipmentDialog').dialog('open').dialog('setTitle', '添加设备');
		$('#equipmentAdd').form('clear');
		$("input[name='OWNER_NAME']").val($("#equipment_CLIENT_NAME").val());
		$('#equipmentUpdate').find("input[name='IS_SHHZ'][value='1']").attr("checked",true);
		
		$('#equipment_CLIENT_ID').val($('#clientId').val());
		url = '../crm/AssetsDossier!doSaveEquipment.action';
    }
}
function saveEquipment() {
	$('#equipmentAdd').form('submit', {
		url : url,
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#equipmentDialog').dialog('close'); // close the dialog
		$('#equipmentTable').datagrid('load', {
			"param" : getFromData("#pForm")
		});
	} else {
		$.messager.alert("提示","保存失败请重试！");
	}
},
error : function(e) {
	alert(e.message);
}
	});
}
function delEquipment() {
	var row = $("#equipmentTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示", "请选择要删除的设备信息");
		return true;
	}
	if (confirm("确定要删除此记录？")) {
		jQuery.ajax( {
			url : "../crm/AssetsDossier!doDeleteEquipment.action",
			data : "ID=" + row.ID,
			dataType : "json",
			success : function(date) {
				if (date.flag == true) {
					$('#equipmentTable').datagrid('load', {});
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


function openUpdateEquipment() {
	$('#equipmentUpdate').form('clear');

	var row = $("#equipmentTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示","请选择要修改的设备信息");
		return true;
	}
	$('#equipmentUpdateDialog').dialog('open').dialog('setTitle', '修改设备');
	
	$('#UPDATE_ID').val(row.ID);  
	$('#UPDATE_EQUIPMENT_NAME').val(row.EQUIPMENT_NAME);
	$('#UPDATE_TYPE_ID').val(row.TYPE_ID);
	$('#UPDATE_OWNER_NAME').val(row.OWNER_NAME);
	$('#UPDATE_EQUIPMENT_PRICE').val(row.EQUIPMENT_PRICE);
	$('#UPDATE_EQUIPMENT_NUMBER').val(row.EQUIPMENT_NUMBER);
	$('#UPDATE_EQUIPMENT_VALUATION').val(row.EQUIPMENT_VALUATION);
	$('#UPDATE_EQUIPMENT_PROFIT').val(row.EQUIPMENT_PROFIT);
	$('#UPDATE_BUY_TIME').datebox("setValue",row.BUY_DATE);
	$('#UPDATE_CREATE_COMPANY').val(row.CREATE_COMPANY);
	$('#UPDATE_SUP_NAME').val(row.SUP_NAME);
	$('#UPDATE_PERIOD_MONTH').val(row.PERIOD_MONTH);
	$('#UPDATE_PAY_REMARK').val(row.PAY_REMARK);
	$('#UPDATE_BILL_NUM').val(row.BILL_NUM);
	$('#UPDATE_BILL_DATE').datebox("setValue",row.BILL_DATE);
	$('#UPDATE_EQUIPMENT_REMARK').val(row.REMARK);
	$('#equipmentUpdate').find("input[name='IS_SHHZ'][value='"+row.IS_SHHZ+"']").attr("checked",true);
    
}

function updateEquipment() {
	$('#equipmentUpdate').form('submit', {
		url : '../crm/AssetsDossier!doUpdateEquipment.action',
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#equipmentUpdateDialog').dialog('close'); // close the dialog
				$('#equipmentTable').datagrid('load', {
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


function setEquipmentDel(val, row){
	//在url里面获取tab参数，如果参数值为view则是查看页面不让操作
    var param = getUrl_().tab;
    if (param != 'view') {
	    return "<a href='#' onclick='delEquipment(" + val + ")'>删除</a>";
    }else{
		return " ";
	}
}
function setCallLogTool(val, row) {
	if(val == null){
		return "<a ></a>";
	}else{
		return "<a style='color:black' title='"+val+"' >"+val+"</a>";
	}
}