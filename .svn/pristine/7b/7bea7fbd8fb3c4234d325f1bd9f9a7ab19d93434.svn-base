//保存还款计划书
$(function(){
	$("#btnSaveA").click(function(){
		var PAYMENT=[];	//还款计划书子表
		var MAINPAYMENT=[];//主表
		$("#payment tr").each(function(){
			var t=$(this);
			PAYMENT[PAYMENT.length]={
				PERIOD_NUM:t.find("input[name=PERIOD_NUM]").val(),
				PAY_DATE:t.find("input[name=PAY_DATE]").val(),
				REN_PAY_TIME:t.find("input[name=REN_PAY_TIME]").val(),
				OWN_PRICE:t.find("input[name=OWN_PRICE]").val(),
				REN_PRICE:t.find("input[name=REN_PRICE]").val(),
				MONTH_PRICE:t.find("input[name=MONTH_PRICE]").val(),
				PROJECT_ID:$("#PROJECT_ID").val()
			};
		});
		MAINPAYMENT[0]={PAY_ID:$("#PAY_ID").val(),PROJECT_ID:$("#PROJECT_ID").val(),LOAN_AMOUNT:$("#LOAN_AMOUNT").val()};
		  $("#btnSaveA").attr("disabled","true");
    		jQuery.ajax({
    			url:"RePaymentDetail!addPayMentDetail.action",	
    			data:"json1="+JSON.stringify(PAYMENT).replace(/\%/g,"％")+"&json2="+JSON.stringify(MAINPAYMENT).replace(/\%/g,"％"),
    			type:"post",
    			dateType:"json",
				success:function(text){
        		 	if(text=="success"){
	 		    		alert("保存成功!");
						window.location.href="RePayment!queryPaymentList.action";
    	 		    }else{
						$("#btnSaveA").attr("disabled","false");
    	 		    	alert("保存失败，请联系管理员");
    	 		    }
        		},
        		error:function(e){
					$("#btnSaveA").attr("disabled","false");
        			alert("网络连接可能有问题");
        		}
    		});
	});
});