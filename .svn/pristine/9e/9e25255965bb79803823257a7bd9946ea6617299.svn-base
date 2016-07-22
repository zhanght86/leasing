$(document).ready(function(){
	
	// 实际费用明细(全选及取消)
	$("#chk_all").click(function() {
		if ($("#chk_all").attr("checked") == "checked") {
			$("input[name='chk_list']").attr("checked", "checked");
		} else {
			$("input[name='chk_list']").removeAttr("checked", "checked");
		}
		changeSumMoney();
	});
	
	// 方案金额明细(全选及取消)
	$("#chk_all_scheme").click(function() {
		if ($("#chk_all_scheme").attr("checked") == "checked") {
			$("input[name='chk_list_scheme']").attr("checked", "checked");
		} else {
			$("input[name='chk_list_scheme']").removeAttr("checked", "checked");
		}
		changeSumMoney();
	});
	
	
	
	// 根据多选框实时更新请款合计(依次累加)
	$("input[name='chk_list']").change(function(){
		changeSumMoney();
	});
	
	// 根据多选框实时更新请款合计(依次递减)
	$("input[name='chk_list_scheme']").change(function(){
		changeSumMoney();
	});
	
	changeSumMoney();
});

/**
 * 请款数据提交
 */
function saveOrUpdate(){
	
	var sumMoney =  $("#sumMoney").val();
	if(!isNaN(sumMoney) && sumMoney <= 0){
		alert("请款合计不能小于等于0！");
		return false;
	}
	
	obj = document.getElementsByName("chk_list");
	var check_val = [];
	for(var k=obj.length-1;k>=0;k--){
		
		if(obj[k].checked){
			check_val.push(obj[k].value);
		}
	}
	
	// 得到没有被锁定的输入框的值
	var ipts = $(":checkbox:checked").parents("tr").find("input:text[disabled!='disabled']");  

    // 遍历input并使用val()方法获取值

    str =ipts.map(function()
    		{return $(this).val();}).get().join(",");

	if(null != str && '' != str){
		//获取
		var PROJECT_ID = $("#project_id").val();
		var redureSumMoney = $("#redureSumMoney").val();
		var waitSumMoney = $("#waitSumMoney").val();
		
		var data = {};
		data.PROJECT_ID=PROJECT_ID;
		data.REDURE_SUM_MONEY=redureSumMoney;
		data.WAIT_SUM_MONEY=waitSumMoney;
		
		var url=_basePath+"/project/project!saveSjmx.action?SIGN="+check_val+"&SIGNVALUE="+str;
		jQuery.ajax({
		    type: "POST",
		    dataType: "json",
			url:url,
			data:data,
			success:function(json){
				if(json.flag){
					jQuery.messager.alert("提示","提交成功!");
				}else{
					jQuery.messager.alert("提示","提交失败!");
				}
			}
		});	
	}else{
		jQuery.messager.alert("提示","请选择想要提交的费用款项!");
	}
	
}

/**
 * 请款数据重置
 */
function restcz(){
	var PROJECT_ID = $("#project_id").val();
	var SCHEME_ROW_NUM = $("#SCHEME_ROW_NUM").val();
	var data = {};
	data.PROJECT_ID=PROJECT_ID;
	data.SCHEME_ROW_NUM=SCHEME_ROW_NUM;
	var url=_basePath+"/project/project!toGetQkrest.action";
	jQuery.ajax({
	    type: "POST",
	    dataType: "json",
		url:url,
		data:data,
		success:function(json){
			if(json.flag){
				
				for(var info in json.data){
					
					$("#money_" + json.data[info].TYPE_ID).val(json.data[info].APPLY_MONEY==null?'':json.data[info].APPLY_MONEY);
					
				}
				
				changeSumMoney();
				jQuery.messager.alert("提示","重置成功!");
			}else{
				jQuery.messager.alert("提示","重置失败!");
			}
		}
	});	
	
}

/**
 * 请款合计
 */
function changeSumMoney(){
	 
	 var sumMoney = 0.0;		//请款合计
	 var redureSumMoney = 0.0;	//扣减总额
	 var waitSumMoney = 0.0;	//待请款金额
	 var reqMoney;
	 
	 // 循环所有的金额，依次累加
	 $("input:checkbox[name='chk_list']:checked").each(function(i, n) {
		
		 reqMoney = $(this).parent().parent().find("input[name='askMoney']").val();
		 
		 // 如果是数字
		 if(/^-?\d+\.?\d{0,2}$/.test(reqMoney)){
			 sumMoney += parseFloat(reqMoney);
			 waitSumMoney = sumMoney;
		 }
		 
		 // 金额不能为空，若为空，则默认为0
		 if(null == reqMoney || "" == reqMoney){
			 $(this).parent().parent().find("input[name='askMoney']").val(0);
		 }
		
	 });
	 
	 // 循环所有的金额，依次递减
	 $("input:checkbox[name='chk_list_scheme']:checked").each(function(i, n) {
		
		 reqMoney = $(this).parent().parent().find("input[name='scheme_money']").val();
		 
		 // 如果是数字
		 if(/^-?\d+\.?\d{0,2}$/.test(reqMoney)){
			 sumMoney -= parseFloat(reqMoney);
			 redureSumMoney += parseFloat(reqMoney);
		 }
		
	 });
	 
	 
	 $("#sumMoney").val(sumMoney.toFixed(2));
	 $("#redureSumMoney").val(redureSumMoney.toFixed(2));
	 $("#waitSumMoney").val(waitSumMoney.toFixed(2));
	 
	 $("#REDURE_SUM_MONEY").val(redureSumMoney.toFixed(2));
	 $("#WAIT_SUM_MONEY").val(waitSumMoney.toFixed(2));
}
