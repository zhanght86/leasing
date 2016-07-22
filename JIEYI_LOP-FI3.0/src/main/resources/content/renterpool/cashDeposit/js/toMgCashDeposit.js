//查詢
function se() {
	var CUST_NAME = $("#CUST_NAME").val();
	var SUP_NAME = $("#SUP_NAME").val();
	var STATUS1 = $("#STATUS1").val();

	var PRO_CODE = $("#PRO_CODE").val();
	var LEASE_CODE = $("#LEASE_CODE").val();
	var BASE_MONEY = $("#BASE_MONEY").val();
	var CANUSE_MONEY = $("#CANUSE_MONEY").val();
	$('#pageTable').datagrid('load', {
		"CUST_NAME" : CUST_NAME,
		"SUP_NAME" : SUP_NAME,
		"STATUS" : STATUS1,
		"PRO_CODE" : PRO_CODE,
		"LEASE_CODE" : LEASE_CODE,
		"BASE_MONEY" : BASE_MONEY,
		"CANUSE_MONEY" : CANUSE_MONEY
	});
}

/**
 * CashDeposit 清空查询数据
 * 
 * @author 杨雪
 * @return
 */
function clearQuery() {
	$(".paramData").each(function() {
		$(this).val("");
	});
}

// //操作
// function operate(){
// return "<a
// href='#'onclick='returnMoney("+indext+","+JSON.stringify(row)+")'>退款申请</a>";
// }

// 退款
function returnMoney() {
	if ($("#SUP_NAME").val() != "") {
		// 获取选中的退款信息
		var checkedItems = $('#pageTable').datagrid('getSelections');
		var refundMoney = 0;
		var NUM = 0;
		var POOL_ID_ITEMS;
		if (checkedItems.length > 0) {
			if (toCheckedData()) {
				$.ajax( {
					url : _basePath+ "/renterpool/CashDeposit!getSuppliers.action",
					type : "post",
					data : "SUP_NAME=" + $("#SUP_NAME").val(),
					dataType : "json",
					success : function(json) {
						if (json.data != null) {
							$("#RE_PAYEE_ACCOUNT").val(
									json.data.LOANS_OWN_ACCOUNT);
							$("#RE_PAYEE_BANK").val(json.data.LOANS_OWN_BANK);
							$("#RE_PAYEE_BANK_ADDR").val(
									json.data.LOANS_OWN_ADDR);
						}
						for ( var i = 0; i < checkedItems.length; i++) {
							var CANUSE_MONEY = checkedItems[i].CANUSE_MONEY;
							refundMoney = accAdd(refundMoney, CANUSE_MONEY);
							POOL_ID_ITEMS = checkedItems[i].POOL_ID + ",";
							NUM++;
						}

						$("#POOL_ID_ITEMS").val(POOL_ID_ITEMS);
						// $("#CUST_NAME_1").text($("#SUP_NAME").val());
						$("#PAY_TIME").datebox("setValue", "");
						$("#RE_PAYEE_UNIT").val($("#SUP_NAME").val());
						$("#returnMoney input[name='REFUND_MONEY']").val(
								refundMoney);
						$("#returnMoney input[name='PROJECT_COUNT']").val(NUM);
						$('#returnMoney').dialog('open').dialog('setTitle',
								'新建承租人余款池退款单');
					}
				});
			}else{
				jQuery.messager.alert("提示", "请选择已解冻的数据进行退款！");
			}
		} else {
			jQuery.messager.alert("提示", "请选中要退款项！");
		}
	} else {
		jQuery.messager.alert("提示", "请选请选择供应商！");
	}
}

// 保存
function saveRefund() {
	// var CUST_NAME = $("#CUST_NAME_1").text();

	var RE_PAYEE_TYPE = $("#returnMoney input[name='RE_PAYEE_TYPE']").val();
	var REFUND_MONEY = $("#returnMoney input[name='REFUND_MONEY']").val();
	var PROJECT_COUNT = $("#returnMoney input[name='PROJECT_COUNT']").val();
	var POOL_ID_ITEMS = $("#returnMoney input[name='POOL_ID_ITEMS']").val();
	var RE_PAYEE_UNIT = $("#returnMoney input[name='RE_PAYEE_UNIT']").val();
	var RE_PAYEE_ACCOUNT = $("#returnMoney input[name='RE_PAYEE_ACCOUNT']")
			.val();
	var RE_PAYEE_BANK = $("#returnMoney input[name='RE_PAYEE_BANK']").val();
	var RE_PAYEE_BANK_ADDR = $("#returnMoney input[name='RE_PAYEE_BANK_ADDR']")
			.val();
	var PAY_TIME = $("#PAY_TIME").datebox("getValue");
	var PRO_CODES = $("#PRO_CODES").val();
	if (PAY_TIME == '') {
		jQuery.messager.alert("提示", "请填写退款时间!");
	} else {
		jQuery.ajax( {
			url : _basePath + "/renterpool/CashDeposit!refundCDApply.action",
			type : "post",
			data : "REFUND_MONEY=" + REFUND_MONEY + "&PROJECT_COUNT="
					+ PROJECT_COUNT + "&POOL_ID_ITEMS=" + POOL_ID_ITEMS
					+ "&PAY_TIME=" + PAY_TIME + "&PRO_CODES=" + PRO_CODES
					+ "&RE_PAYEE_UNIT=" + RE_PAYEE_UNIT + "&RE_PAYEE_ACCOUNT="
					+ RE_PAYEE_ACCOUNT + "&RE_PAYEE_BANK=" + RE_PAYEE_BANK
					+ "&RE_PAYEE_BANK_ADDR=" + RE_PAYEE_BANK_ADDR
					+ "&RE_PAYEE_TYPE=" + RE_PAYEE_TYPE,
			dataType : "json",
			success : function(res) {
				if (res.flag == true) {
					jQuery.messager.alert("提示", res.msg);
					$('#returnMoney').dialog('close');
					$('#pageTable').datagrid('reload');
				} else {
					jQuery.messager.alert("提示", res.msg);
				}
			}

		});
	}
}

/**
 * 退款数据验证
 * 
 * @return
 */
function toCheckedData() {
	var detailData = $("#pageTable").datagrid('getSelections');
	var STATUS = '';
	var flag = false;
	for ( var i = 0; i < detailData.length; i++) {
		STATUS = detailData[i].STATUS;
		if (STATUS != "解冻") {
			flag = false;
		} else {
			flag = true;
		}
	}
	return flag;
}