function getValue(value, rowDate, inedx) {
	return "<a href='javascript:void(0)' onclick='toViewGuarantorMain(" + value + "," + JSON.stringify(rowDate) + ")'>查看</a>";
}

function showCreditGuarantor(value, rowData) {
	top.addTab("查看担保人", _basePath + "/credit/CreditGuarantor!toViewGuarantorInfo.action?CLIENT_ID=" + value + "&TYPE=" + rowData.FCC_TYPE + "&tab=view" + "&date=" + new Date().getTime());
}

function toViewGuarantorMain(value, rowData){
	top.addTab("查看担保人", _basePath + "/credit/CreditGuarantor!toViewGuarantorInfoMain.action?CLIENT_ID=" + value + "&TYPE=" + rowData.FCC_TYPE + "&tab=view" + "&date=" + new Date().getTime());
}


function getStatus(value, rowDate, inedx) {
	if (value == '0') {
		return "启用中";
	} else {
		return "已禁用";
	}
}

//搜索
function conditionsSelect() {
	$('#pageTable').datagrid('load', {
		STATUS : $('#STATUS').datebox('getValue'),
		FCC_TYPE : $('#FCC_TYPE').datebox('getValue'),
		CREATE_TIME : $('#CREATE_TIME').datebox('getValue'),
		NAME : $("#NAME").val(),
		CO_NO : $("#CO_NO").val(),
	});
}

function clearInput() {
	$("#pageForm input").val("");
}