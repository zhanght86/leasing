function changemoney(){
	var PAY_MONEY=$("#PAY_MONEY").val();
	var RATIO=$("#RATIO").val();
	var MONEY=accDiv(accMul(PAY_MONEY,RATIO),100);
	var LASTMONEY=accSub(PAY_MONEY,MONEY);
	$("#MONEY").val(MONEY);
	$("#LASTMONEY").val(LASTMONEY);
}

function changeRatio(){
	var PAY_MONEY=$("#PAY_MONEY").val();
	var MONEY=$("#MONEY").val();
//	var RATIO=accMul(accDiv(MONEY,PAY_MONEY),100);
	var LASTMONEY=accSub(PAY_MONEY,MONEY);
//	$("#RATIO").val(RATIO);
	$("#RATIO").val("");
	$("#LASTMONEY").val(LASTMONEY);	
}

function save_Deductible(){
	var JBPM_ID=$("#JBPM_ID").val();
	var LASTMONEY=$("#LASTMONEY").val();
	var MONEY=$("#MONEY").val();
	var RATIO=$("#RATIO").val();
	$.ajax({
		type:"post",
		url:_basePath+"/bpm/JbpmFi!save_Deductible.action",
		data:"JBPM_ID="+encodeURI(JBPM_ID)+"&LASTMONEY="+LASTMONEY+"&MONEY="+MONEY+"&RATIO="+RATIO,
		dataType:"json",
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","保存成功");
				$("#save_Deductible").linkbutton("disable");
				$("#save_Deductible").attr("disabled", "disabled");
			}else{
				$.messager.alert("提示","保存失败");
			}
		},
		error:function(){
			$.messager.alert("提示","请联系管理员");
		}
	});
}

function exp_Eq_Invoice(payment_head_id){
	$.messager.alert("提示","等待设备发票导出功能完善，付款单id为="+payment_head_id);
}

function exp_Pay_Detail(payment_head_id){
	$.messager.alert("提示","导出放款明细等待功能，付款单id为="+payment_head_id);
}
//修改承兑汇票
function changeBankBill(){
	var ACCBILL_PRICE=$("#ACCBILL_PRICE").val().replace(/\D/g,''); // 承兑金额
	$("#ACCBILL_PRICE").val(ACCBILL_PRICE);
	
	var LASTMONEY=$("#LASTMONEY").val();  // 剩余放款金额
	var BANKBILL_PRICE=accSub(LASTMONEY,ACCBILL_PRICE);
//	if(BANKBILL_PRICE < 0){
//		$.messager.alert("提示","承兑汇票金额不能大于剩余放款金额!");
//		$("#ACCBILL_PRICE").val(0);
//		$("#BANKBILL_PRICE").val(LASTMONEY);
//		return;
//	}
	$("#BANKBILL_PRICE").val(BANKBILL_PRICE);
}

function save_BankBill(){
	var JBPM_ID=$("#JBPM_ID").val();
	var ACCBILL_PRICE=$("#ACCBILL_PRICE").val();
	var BANKBILL_PRICE=$("#BANKBILL_PRICE").val();
	$.ajax({
		type:"post",
		url:_basePath+"/bpm/JbpmFi!save_BankBill.action",
		data:"JBPM_ID="+encodeURI(JBPM_ID)+"&ACCBILL_PRICE="+ACCBILL_PRICE+"&BANKBILL_PRICE="+BANKBILL_PRICE,
		dataType:"json",
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","保存成功");
				$("#save_Deductible").linkbutton("disable");
				$("#save_Deductible").attr("disabled", "disabled");
			}else{
				$.messager.alert("提示","保存失败");
			}
		},
		error:function(){
			$.messager.alert("提示","请联系管理员");
		}
	});
}
