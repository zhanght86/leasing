

function PERIODChange(obj){
	var period=$(obj).val();
	if (period == "" || period == null || period == undefined) {
		$("input[name='NO_RENT_MEONY']").val(0);
		$("input[name='ZUJIN_AFTER']").val(0);
		TOTALMONEY();
	}
	else{
		var PAYLIST_CODE=$("input[name='PAYLIST_CODE']").val();
		$.ajax({
			url:_basePath+"/pay/PayTask!queryInfoNormalByPeriod.action?PAYLIST_CODE="+PAYLIST_CODE+"&PERIOD="+period,
			type:"post",
			dataType:"json",
			success:function (data){
				$("input[name='NO_RENT_MEONY']").val(data[0].NO_RENT_MEONY);
				$("input[name='RENT_DATE']").val(data[0].RENT_DATE);
				$("input[name='ZUJIN_AFTER']").val(data[0].ZUJIN_AFTER);
				
				TOTALMONEY();
			}
		});
	}
}

function TOTALMONEY(){
	var NO_RENT_MEONY=parseFloat($("input[name='NO_RENT_MEONY']").val());//(1+)结清前未收租金
	var PENALTY_RECE=parseFloat($("input[name='PENALTY_RECE']").val());//(2+)罚息金额
	
	var DEPOSIT=parseFloat($("input[name='DEPOSIT']").val());//(5-)保证金抵扣金额
	var LGJ=$("input[name='LGJ']").val();//(3+)留购价
	var ZUJIN_AFTER=parseFloat($("input[name='ZUJIN_AFTER']").val());//(4+结清期次后租金合计)
	if (LGJ == "" || LGJ == null || LGJ == undefined || LGJ == "NaN") {
		LGJ=parseFloat(0);
	}
//	alert("NO_RENT_MEONY="+NO_RENT_MEONY+"---PENALTY_RECE="+PENALTY_RECE+"---ZUJIN_AFTER="+ZUJIN_AFTER+"---DEPOSIT="+DEPOSIT+"---LGJ="+LGJ);
	var TOTAL_MONEY=parseFloat(NO_RENT_MEONY+PENALTY_RECE-DEPOSIT+LGJ+ZUJIN_AFTER).toFixed(2);
	$("input[name='TOTAL_MONEY']").val(TOTAL_MONEY);
}

function DKCHANGE(){
	var STATUS=$("select[name='STATUS']").val();
	if(STATUS==0){
		$("input[name='DEPOSIT']").removeAttr("readonly");
		var DEPOSIT_CUST=$("input[name='DEPOSIT_CUST']").val();
		if (DEPOSIT_CUST == "" || DEPOSIT_CUST == null || DEPOSIT_CUST == undefined) {
			DEPOSIT_CUST=0;
		}
		$("input[name='DEPOSIT']").val(DEPOSIT_CUST);
	}
	else{
		$("input[name='DEPOSIT']").val(0);
		$("input[name='DEPOSIT']").attr("readonly","readonly");
	}
	TOTALMONEY();
}

function changeMoney(obj){
	var DEPOSIT=$(obj).attr("DEPOSIT");
	if (DEPOSIT == "" || DEPOSIT == null || DEPOSIT == undefined) {
		DEPOSIT=0;
	}
	
	var DEPOSIT_CH=$(obj).val();
	if (DEPOSIT_CH == "" || DEPOSIT_CH == null || DEPOSIT_CH == undefined) {
		$(obj).val(0);
		DEPOSIT_CH=0;
	}
	
	if(parseFloat(DEPOSIT_CH)>parseFloat(DEPOSIT)){
		$(obj).val(DEPOSIT);
	}
	
	if(parseFloat(DEPOSIT_CH)<0){
		$(obj).val(0);
	}
	
	TOTALMONEY();
}


function changeDunMoney(obj){
	var JM=parseFloat($(obj).val());
	var YS=parseFloat($("input[name='PENALTY_RECE_YS']").val());
	if(JM>YS){
		$(obj).val(0);
		$("input[name='PENALTY_RECE']").val(YS);
	}
	else{
		$("input[name='PENALTY_RECE']").val(YS-JM);
	}
	TOTALMONEY();
}

//点击下一步（保存测试结果）
function nex_New() {
//	var JQ_PERIOD=$("select[name='JQ_PERIOD']").val();
//	if (JQ_PERIOD == "" || JQ_PERIOD == null || JQ_PERIOD == undefined) {
//		alert("请选择结清期次!") ;
//		return ;
//	}
	
	var TOTAL_MONEY=$("input[name='TOTAL_MONEY']").val();
	if(parseFloat(TOTAL_MONEY)<0){
		alert("请修改保证金抵扣金额，合计不能小于零!") ;
		return false;
	}
	
	var CURRDATE=$("#CURRDATE").val();
	var RENT_DATE=$("input[name='RENT_DATE']").val();
	if(RENT_DATE<CURRDATE){
		alert("请选择未来某一天日期，如果需用已有来款核销，需先核销后在做正常结清!");
		return false;
	}
	
	var myData = $('#pageTable').datagrid('getRows');
	var settleInfo = getFormParamFormat("changePay");
	var data_ = "myData=" + JSON.stringify(myData) + "&PAYLIST_CODE="
			+ $("#PAYLIST_CODE").val() + "&code_=" + $("#code_").val()
			+ "&status_=" + $("#status_").val() + "&settleInfo="
			+ JSON.stringify(settleInfo);
	jQuery.ajax( {
		type : "POST",
		dataType : "json",
		async : false,
		url : _basePath + "/pay/PayTask!settleNormalSave.action",
		data : data_,
		success : function(json) {
			$.messager.alert("提示","发起流程成功！",'info',function(){
						$.messager.alert("提示",json.msg+json.data,"info",function(){
						//点击保存后下一步和测算按钮不可用
						$('#nex_').linkbutton('disable');
						$('#calculate_').linkbutton('disable');
						window.location.href = _basePath
								+ "/pay/PayTask!earlySettlementManage.action";
						});
					});		
		}
	});
}

