function btnSaveA(){
	var PAYMENT = []; // 还款计划书子表
	var MAINPAYMENT = [];// 主表
	var result = as();
	if (result) {
		$("#payment tr").each(function() {
			var t = $(this);
			PAYMENT[PAYMENT.length] = {
				PERIOD_NUM : t.find("input[name=PERIOD_NUM]").val(),
				PAY_DATE : t.find("input[name=PAY_DATE]").val(),
				REN_PAY_TIME : t.find("input[name=REN_PAY_TIME]").val(),
				OWN_PRICE : t.find("input[name=OWN_PRICE]").val(),
				REN_PRICE : t.find("input[name=REN_PRICE]").val(),
				MONTH_PRICE : t.find("input[name=MONTH_PRICE]").val(),
				PROJECT_ID : $("#PROJECT_ID").val()
			};
		});
		MAINPAYMENT[0] = {
			PAY_ID : $("#PAY_ID").val(),
			PROJECT_ID : $("#PROJECT_ID").val(),
			LOAN_AMOUNT : $("#LOAN_AMOUNT").val(),
			holiday_pay_type : $("#holiday_pay_type").val(),
			PAY_TYPE : $("#PAY_TYPE").val()
		};
		$("#btnSaveA").attr("disabled", "true");
		jQuery.ajax( {
					url : _basePath
							+ "/rePayment/RePaymentDetail!addPayMentDetail.action",
					data : "json1="
							+ JSON.stringify(PAYMENT).replace(/\%/g, "％")
							+ "&json2="
							+ JSON.stringify(MAINPAYMENT).replace(/\%/g, "％"),
					type : "post",
					dataType : "json",
					success : function(json) {
						if (json.flag == true) {
							alert("保存成功!");
							$("#btnSaveA").attr("disabled", "false");
						} else {
							$("#btnSaveA").attr("disabled", "false");
							alert("保存失败，请联系管理员");
						}
					}
				});
	}
}

//不能为空
function as() {
	var a = 0;
	$("#payment tr").each(function() {
		var t = $(this);
		PERIOD_NUM = t.find("input[name=PERIOD_NUM]").val();
		PAY_DATE = t.find("input[name=PAY_DATE]").val();
		REN_PAY_TIME = t.find("input[name=REN_PAY_TIME]").val();
		OWN_PRICE = t.find("input[name=OWN_PRICE]").val();
		REN_PRICE = t.find("input[name=REN_PRICE]").val();
		MONTH_PRICE = t.find("input[name=MONTH_PRICE]").val();
		PROJECT_ID = $("#PROJECT_ID").val();
		if (PAY_DATE == null || PAY_DATE == "") {
			alert(PERIOD_NUM + "期还款日不能为空");
			t.find("input[name=PAY_DATE]").focus();
			a = a + 1;
			return false;
		}
		if (OWN_PRICE == null || OWN_PRICE == "") {
			alert(PERIOD_NUM + "期本金不能为空");
			t.find("input[name=OWN_PRICE]").focus();
			a = a + 1;
			return false;
		}
		if (REN_PRICE == null || REN_PRICE == "") {
			alert(PERIOD_NUM + "期利息不能为空");
			t.find("input[name=REN_PRICE]").focus();
			a = a + 1;
			return false;
		}
		if (MONTH_PRICE == null || MONTH_PRICE == "") {
			alert(PERIOD_NUM + "期租金不能为空");
			t.find("input[name=PAY_DATE]").focus();
			a = a + 1;
			return false;
		}
	});
	if (a != 0) {
		return false;
	} else {
		return true;
	}
}