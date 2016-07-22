$(function() {
	$("#REFUND_RECORD_CASE").dialog('close');
});

function dd() {

	$("#Refund_Record_PageTable").datagrid({
		pagination : true, //是否分页 true为是
		rownumbers : true, //左侧自动显示行数
		fit : true, 
		singleSelect:true,
		pageSize : 50,
		toolbar : '#pageForm',
		url : 'rentWriteVinual!refundRecordManager.action',
		onSelect : function(rowIndex, rowData) {
			onChangeSelect();
		},
		onUnselect : function(rowIndex, rowData) {
			onChangeSelect();
		},
		onSelectAll : function(rowIndex, rowData) {
			onChangeSelect();
		},
		onUnselectAll : function(rowIndex, rowData) {
			onChangeSelect();
		},
		columns : [[{
			field : 'ID',
			checkbox : false,
			width : 100,
			hidden : true
		}, 
		{field:'UPLOAD_TYPE',title:'状态',width:100,formatter:function(value,rowData,rowIndex){
		          		if(value == '0'){
		          			 return "未导出";
		          		}else{
	                	  return "已导出";
	                	}
	                  }},
		{
			field : 'LEASE_CODE',
			title : '合同编号',
			width : 180,
			formatter : function(value, rowData, rowIndex) {
				return "<a href='javascript:void(0);' onclick=refundForm(" + JSON.stringify(rowData) + ")>" + value + "</a>";
			}
		}, {
			field : 'CUST_NAME',
			title : '客户名称',
			width : 150
		}, {
			field : 'COMPANY_NAME',
			title : '厂商',
			width : 150
		}, {
			field : 'SUPPLIERS_NAME',
			title : '供应商',
			width : 200
		}, {
			field : 'PRODUCT_NAME',
			title : '租赁物',
			width : 150
		}, {
			field : 'PAYLIST_CODE',
			title : '还款计划',
			width : 150
		}, {
			field : 'BEGINNING_NAME',
			title : '款项名称',
			width : 100
		}, {
			field : 'REFUND_FLAG',
			title : '款项类型',
			width : 150,
			formatter : function(value, rowData, rowIndex) {
				return checkRefundFlag(value) ;
			}
		}, {
			field : 'BEGINNING_NUM',
			title : '期次',
			width : 50
		}, {
			field : 'BEGINNING_RECEIVE_DATA',
			title : '计划收取日期',
			width : 100
		}, {
			field : 'VINUAL_MONEY',
			title : '退款金额',
			width : 100
		}, {
			field : 'FI_ACCOUNT_DATE',
			title : '退款日期',
			width : 100
		}, {
			field : 'PAYEE_NAME',
			title : '收款单位',
			width : 200
		}, {
			field : 'PAY_BANK_NAME',
			title : '开户行行名',
			width : 200
		}, {
			field : 'PAY_BANK_ADDRESS',
			title : '开户行所在地',
			width : 200
		}, {
			field : 'PAY_BANK_ACCOUNT',
			title : '收款帐号',
			width : 200
		}, {
			field : 'ITEM_FLAG',
			hidden : true
		}, {
			field : 'CUST_ID',
			hidden : true
		}, {
			field : 'SUP_ID',
			hidden : true
		}]]
	});

}

function closeDialog() {
	$("#REFUND_RECORD_CASE").dialog('close');
}

function checkRefundFlag(value){
	var info;
	if (value == 1)
		info = "退款";
	else if (value == 2)
		info = "代收违约金退款";
	else
		info = "";
	return info ;
}

function checkValue(value) {
	if (value == null || value == undefined || typeof (value) == undefined) {
		return '';
	}
	return value;
}

function refundForm(row) {
	if (row) {
		var LEASE_CODE = checkValue(row.LEASE_CODE);
		var CUST_NAME = checkValue(row.CUST_NAME);
		var COMPANY_NAME = checkValue(row.COMPANY_NAME);
		var SUPPLIERS_NAME = checkValue(row.SUPPLIERS_NAME);
		var PRODUCT_NAME = checkValue(row.PRODUCT_NAME);
		var PAYLIST_CODE = checkValue(row.PAYLIST_CODE);
		var BEGINNING_NAME = checkValue(row.BEGINNING_NAME);
		var BEGINNING_NUM = checkValue(row.BEGINNING_NUM);
		var BEGINNING_RECEIVE_DATA = checkValue(row.BEGINNING_RECEIVE_DATA);
		var VINUAL_MONEY = checkValue(row.VINUAL_MONEY);
		var FI_ACCOUNT_DATE = checkValue(row.FI_ACCOUNT_DATE);
		var PAYEE_NAME = checkValue(row.PAYEE_NAME);
		var PAY_BANK_NAME = checkValue(row.PAY_BANK_NAME);
		var PAY_BANK_ADDRESS = checkValue(row.PAY_BANK_ADDRESS);
		var PAY_BANK_ACCOUNT = checkValue(row.PAY_BANK_ACCOUNT);
		var ITEM_FLAG = checkValue(row.ITEM_FLAG);
		var REFUND_FLAG =  checkRefundFlag(checkValue(row.REFUND_FLAG)) ;

		$("#LEASE_CODE_ID").val(LEASE_CODE);
		$("#CUST_NAME_ID").val(CUST_NAME);
		$("#COMPANY_NAME_ID").val(COMPANY_NAME);
		$("#SUPPLIERS_NAME_ID").val(SUPPLIERS_NAME);
		$("#PRODUCT_NAME_ID").val(PRODUCT_NAME);
		$("#PAYLIST_CODE_ID").val(PAYLIST_CODE);
		$("#BEGINNING_NAME_ID").val(BEGINNING_NAME);
		$("#BEGINNING_NUM_ID").val(BEGINNING_NUM);
		$("#BEGINNING_RECEIVE_DATA_ID").val(BEGINNING_RECEIVE_DATA);

		$("#VINUAL_MONEY_ID").val(VINUAL_MONEY);
		$("#FI_ACCOUNT_DATE_ID").val(FI_ACCOUNT_DATE);
		$("#PAYEE_NAME_ID").val(PAYEE_NAME);
		$("#PAY_BANK_NAME_ID").val(PAY_BANK_NAME);
		$("#PAY_BANK_ADDRESS_ID").val(PAY_BANK_ADDRESS);
		$("#PAY_BANK_ACCOUNT_ID").val(PAY_BANK_ACCOUNT);
		$("#ITEM_FLAG_ID").val(ITEM_FLAG);
		$("#REFUND_FLAG_ID").val(REFUND_FLAG) ;
		$("#REFUND_RECORD_CASE").dialog('open');
	} else {
		$.messager.alert("请选择一条付款单!");
	}
}

function onChangeSelect() {
	var datagridList = $("#fund_Back_PageTable").datagrid('getSelections');
	var pages = $(".pagination-num").val();
	var rowss = $(".pagination-page-list").val();

	var BEGINNING_MONEYAll = 0;
	var NUM = 0;

	for (var i = 0; i < datagridList.length; i++) {
		var jj = datagridList[i].ROWNO - (pages - 1) * rowss;
		if (!$("input[type='checkbox']")[jj].disabled) {
			var BEGINNING_MONEY = datagridList[i].VINUAL_MONEY;
			BEGINNING_MONEYAll = fomatFloat(accAdd(BEGINNING_MONEYAll, BEGINNING_MONEY), 2);
			NUM++;
		}
	}
	$("#FI_PROJECT_NUM").val(NUM);
	$("#FI_PAY_MONEY").val(BEGINNING_MONEYAll);
}

//驳回
function ISBack_BOHUI() {
	$("#divFrom").empty();
	var datagridList = $('#fund_Back_PageTable').datagrid('getChecked');
	if (datagridList.length <= 0) {
		alert("请先选择要数据在继续驳回操作！");
		return false;
	}

	$.messager.confirm("提示", "您确定对选中的数据驳回？", function(flag) {
		if (flag) {
			var selectData = [];
			for (var i = 0; i < datagridList.length; i++) {
				var temp = {};
				temp.PAYLIST_CODE = datagridList[i].PAYLIST_CODE;
				temp.BEGINNING_NUM = datagridList[i].BEGINNING_NUM;
				temp.ITEM_FLAG = datagridList[i].ITEM_FLAG;
				selectData.push(temp);
			}
			var dataJson = {
				selectData : selectData
			};
			var url = _basePath + "/rentWrite/rentWriteVinual!fund_Back_BoHui.action?selectDateHidden=" + JSON.stringify(dataJson);
			$("#divFrom").append("<form id='formRoll' method='post' action='" + url + "'></form>");
			$("#formRoll").submit();
		}
	});
}

//驳回
function ISBack_ChongZ() {
	$("#divFrom").empty();
	var datagridList = $('#fund_Back_PageTable').datagrid('getChecked');
	if (datagridList.length <= 0) {
		alert("请先选择要数据在继续重置操作！");
		return false;
	}

	$.messager.confirm("提示", "您确定对选中的数据重置？", function(flag) {
		if (flag) {
			var selectData = [];
			for (var i = 0; i < datagridList.length; i++) {
				var temp = {};
				temp.PAYLIST_CODE = datagridList[i].PAYLIST_CODE;
				temp.BEGINNING_NUM = datagridList[i].BEGINNING_NUM;
				temp.ITEM_FLAG = datagridList[i].ITEM_FLAG;
				selectData.push(temp);
			}
			var dataJson = {
				selectData : selectData
			};
			var url = _basePath + "/rentWrite/rentWriteVinual!fund_Back_ChongZ.action?selectDateHidden=" + JSON.stringify(dataJson);
			$("#divFrom").append("<form id='formRoll' method='post' action='" + url + "'></form>");
			$("#formRoll").submit();
		}
	});
}

function ISBack_Fund() {
	$("#divFrom").empty();
	var datagridList = $('#fund_Back_PageTable').datagrid('getChecked');
	if (datagridList.length <= 0) {
		alert("请先选择要数据在继续申请操作！");
		return false;
	}

	var FI_ACCOUNT_DATE = $("input[name='FI_ACCOUNT_DATE']").val();

	if (FI_ACCOUNT_DATE == "" || FI_ACCOUNT_DATE == undefined || FI_ACCOUNT_DATE.length <= 0) {
		alert("请输入退款日期！！");
		return false;
	}

	$.messager.confirm("提示", "您确定对选中的数据退款？", function(flag) {
		if (flag) {
			var selectData = [];
			for (var i = 0; i < datagridList.length; i++) {
				datagridList[i].FI_ACCOUNT_DATE = FI_ACCOUNT_DATE;
				var temp = {};
				temp.PAYLIST_CODE = datagridList[i].PAYLIST_CODE;
				temp.BEGINNING_NUM = datagridList[i].BEGINNING_NUM;
				temp.ITEM_FLAG = datagridList[i].ITEM_FLAG;
				selectData.push(temp);
			}
			var dataJson = {
				selectData : selectData
			};
			var url = _basePath + "/rentWrite/rentWriteVinual!fund_Back_C_Submit.action?selectDateHidden=" + JSON.stringify(dataJson) + "&FI_ACCOUNT_DATE=" + FI_ACCOUNT_DATE + "&datagridList=" + JSON.stringify(datagridList);
			$("#divFrom").append("<form id='formRoll' method='post' action='" + url + "'><input id='FI_FAG' name='FI_FAG' value='18'><input id='APP_CREATE_TYPE' name='APP_CREATE_TYPE' value='2'></form>");
			$("#formRoll").submit();
		}
	});
}

function ISBack_Fund_POOL() {
	$("#divFrom").empty();
	var datagridList = $('#fund_Back_PageTable').datagrid('getChecked');
	if (datagridList.length <= 0) {
		alert("请先选择要数据在继续申请操作！");
		return false;
	}

	var FI_ACCOUNT_DATE = $("input[name='FI_ACCOUNT_DATE']").val();

	if (FI_ACCOUNT_DATE == "" || FI_ACCOUNT_DATE == undefined || FI_ACCOUNT_DATE.length <= 0) {
		alert("请输入退款日期！！");
		return false;
	}

	$.messager.confirm("提示", "您确定对选中的数据退款到租金池？", function(flag) {
		if (flag) {
			var selectData = [];
			for (var i = 0; i < datagridList.length; i++) {
				var temp = {};
				temp.PAYLIST_CODE = datagridList[i].PAYLIST_CODE;
				temp.BEGINNING_NUM = datagridList[i].BEGINNING_NUM;
				temp.ITEM_FLAG = datagridList[i].ITEM_FLAG;
				selectData.push(temp);
			}
			var dataJson = {
				selectData : selectData
			};
			var url = _basePath + "/rentWrite/rentWriteVinual!fund_Back_C_Submit.action?selectDateHidden=" + JSON.stringify(dataJson) + "&FI_ACCOUNT_DATE=" + FI_ACCOUNT_DATE;
			$("#divFrom").append("<form id='formRoll' method='post' action='" + url + "'><input id='FI_FAG' name='FI_FAG' value='19'><input id='APP_CREATE_TYPE' name='APP_CREATE_TYPE' value='2'></form>");
			$("#formRoll").submit();
		}
	});
}

function ISBack_UPLOAD() {
	$("#divFrom").empty();
	var LEASE_CODE = $("input[name='LEASE_CODE']").val();
	var CUST_NAME = $("input[name='CUST_NAME']").val();
	var UPLOAD_TYPE = $("select[name='UPLOAD_TYPE']").find("option:selected").val();
	var COM_NAME = $("select[name='COM_NAME']").find("option:selected").val();
	var PRODUCT_NAME = $("select[name='PRODUCT_NAME']").find("option:selected").val();
	var SUPPER_NAME = $("input[name='SUPPER_NAME']").val();

	var url = _basePath + "/rentWrite/rentWriteVinual!Refund_Record_Excle.action?LEASE_CODE=" + LEASE_CODE + "&CUST_NAME=" + CUST_NAME + "&UPLOAD_TYPE=" + UPLOAD_TYPE + "&COM_NAME=" + COM_NAME + "&PRODUCT_NAME=" + PRODUCT_NAME + "&SUPPER_NAME=" + SUPPER_NAME;
	$("#divFrom").append("<form id='formSubmit' method='post' action='" + url + "'></form>");
	$("#formSubmit").submit();
}

/**
 * 加法
 * @param arg1
 * @param arg2
 * @return
 */
function accAdd(arg1, arg2) {
	var r1, r2, m;
	try {
		r1 = arg1.toString().split(".")[1].length;
	} catch (e) {
		r1 = 0;
	}
	try {
		r2 = arg2.toString().split(".")[1].length;
	} catch (e) {
		r2 = 0;
	}
	m = Math.pow(10, Math.max(r1, r2));
	return (arg1 * m + arg2 * m) / m;
}

function fomatFloat(src, pos) {
	return Math.round(src * Math.pow(10, pos)) / Math.pow(10, pos);
}