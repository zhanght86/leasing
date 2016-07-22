/**
 * 查询模糊
 * @author 杨雪
 * @return
 */
function toSeacher() {
	var HEAD_ID = $("input[name='HEAD_ID']").val();
	var FI_PAY_MONEY = $("input[name='FI_PAY_MONEY']").val();
	var FI_PAY_DATE1 = $("input[name='FI_PAY_DATE1']").val();
	var FI_PAY_DATE2 = $("input[name='FI_PAY_DATE2']").val();
	var FI_STATUS = $("select[name='FI_STATUS']").attr("selected",true).val();
	$('#pageTable').datagrid('load', {
		"HEAD_ID" : HEAD_ID,
		"FI_PAY_MONEY" : FI_PAY_MONEY,
		"FI_PAY_DATE1" : FI_PAY_DATE1,
		"FI_PAY_DATE2" : FI_PAY_DATE2,
		"FI_STATUS" : FI_STATUS
	});
}

/**
 * 清空查询数据
 * @author 杨雪
 * @return
 */
function clearQuery(){
	$("#FI_PAY_DATE1").datebox('clear');
	$("#FI_PAY_DATE2").datebox('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}

/**
 * 申请资金扣划
 * @author 杨雪
 * @return
 */
function toAppPayment(){
	window.location.href = _basePath+"/refundFirst/RefundFirst!toAppPaymentMg.action";
}