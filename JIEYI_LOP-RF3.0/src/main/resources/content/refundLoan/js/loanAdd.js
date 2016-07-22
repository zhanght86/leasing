//校验必填项不能为空
function checkImport(){
	var COST_TOTAL = $("#COST_TOTAL").val();
	var EXPECT_TIME = $("#EXPECT_TIME").datebox('getValue');
	var DEDUCT_ACCOUNT = $("#DEDUCT_ACCOUNT").attr("selected",true).val();
	if(COST_TOTAL==null||COST_TOTAL==""){
		alert("费用金额不可为空");
		$("#COST_TOTAL").focus();
		return false;
	}
	if(EXPECT_TIME==null||EXPECT_TIME==""){
		alert("预计支付费用时间不能为空");
		$("#EXPECT_TIME").focus();
		return false;
	}
	if(DEDUCT_ACCOUNT == null || DEDUCT_ACCOUNT==""){
		alert("银行账号不能为空");
		$("#DEDUCT_ACCOUNT").focus();
		return false;
	}
	$("#mainForm").form('submit',{
		dataType:'json',
		success:function(json){
			var obj = eval('('+json+')');
			if(obj.flag==true){
				alert("保存成功");
//				window.location.href = _basePath+"/refundLoan/RefundLoan!toMgLoan.action?PROJECT_ID="+$("#PROJECT_ID").val();
			}else{
				alert("保存失败");
//				window.location.href = _basePath+"/refundLoan/RefundLoan!toMgLoan.action?PROJECT_ID="+$("#PROJECT_ID").val();
			}
		}
	});
}

//返回
function goBack(){
	var pid = $("#PROJECT_ID").val();
	window.location.href="RefundLoan!toMgLoan.action?PROJECT_ID="+pid;
}