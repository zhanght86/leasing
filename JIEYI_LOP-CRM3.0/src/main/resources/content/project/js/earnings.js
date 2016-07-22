
//收益分析
function updateAll(){
	//必填项
	notNullToSubmit();
	
	var this_id = $("#this_id").val();
	var client_id = $("#client_id").val();
	var line = $("#line").val();
	var distance = $("#distance").val();
	var laps_number = $("#laps_number").val();
	var cycle = $("#cycle").val();
	var month_income = $("#month_income").val();
	var month_pay = $("#month_pay").val();
	var month_net_income = $("#month_net_income").val();
	var earnings_analysis = $("#earnings_analysis").val();
	var repayment_source = $("#repayment_source").val();
	var control_measures = $("#control_measures").val();
	var data = {
			ID : this_id,
			CLIENT_ID : client_id,
			LINE : line,
			DISTANCE : distance,
			LAPS_NUMBER : laps_number,
			CYCLE : cycle,
			MONTH_INCOME : month_income,
			MONTH_PAY : month_pay,
			MONTH_NET_INCOME : month_net_income,
			EARNINGS_ANALYSIS : earnings_analysis,
			REPAYMENT_SOURCE : repayment_source,
			CONTROL_MEASURES : control_measures
	};
	var url =_basePath+"/project/Earnings!saveEarnings.action";
	if(this_id ==null || this_id ==""){
		url = _basePath+"/project/Earnings!addEarnings.action";
	}
	//alert("this_id:"+this_id+"--CLIENT_ID:"+data.CLIENT_ID+"--:"+data.CYCLE);
	//alert("url:"+url);
	jQuery.ajax({
		url:url,
		type:"post",
		dataType:"json",
		data:data,
		success : function(json){
			if(json.flag){
				$.messager.alert("提示","收益分析保存成功!");
			}
		}
	});
}

//必填项校验
function notNullToSubmit(){
	$(".warm").each(function(){
		if($(this).val() == null || $(this).val() ==""){
			$(this).addClass("error");
		}else{
			$(this).removeClass("error");
		}
	});
	
}




