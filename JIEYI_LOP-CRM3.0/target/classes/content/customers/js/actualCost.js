
/**
 * 申请费用数据提交/实际放款数据提交
 */
function updateActualCost(type){
	
	var flag = true;
	
	if(type == 'loan'){
		// 遍历所有输入框，不能有数字为空
		$("input[name='askMoney']").each(function(){
			
			if(isNaN($(this).val()) || null == $(this).val() || '' == $(this).val()){
				jQuery.messager.alert("提示","申请金额只能为数字，并且不能为空！");
				flag = false;
				return false;
			}
			
		});
	}
	
	if(flag){
		
	    // 遍历input并使用val()方法获取值
	    var money =$("input[name='askMoney']").map(function()
	    		{return $(this).val()==''?0:$(this).val();}).get().join(",");
	    
	    var type_id =$("input[name='TYPE_ID']").map(function()
	    		{return $(this).val();}).get().join(",");
	    
	    //获取
		var PROJECT_ID = $("#project_id").val();
		
		var data = {};
		data.PROJECT_ID = PROJECT_ID;
		data.SIGN = type_id;
		data.SIGNVALUE = money;
		data.TYPE = type;
		
		// 申请费用时需要申请对象
		if(type == 'fee'){
			
	    	var apply_type =$("select[name='APPLY_TYPE']").map(function()
	        		{return $(this).val();}).get().join(",");
	    	
	    	data.SIGNTYPE = apply_type;
	    }else if(type == 'loan'){
	    	
	    	var APPLY_MONEY =$("input[name='APPLY_MONEY']").map(function()
	        		{return $(this).val();}).get().join(",");
	    	
	    	data.APPLYMONEY = APPLY_MONEY;
	    }
		
		var url=_basePath+"/customers/ActualCost!updateActualCost.action";
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
		
	}
	
}

/**
 * 登记实际费用
 */
function regActualCost(){
	
	var flag = true;
	
	// 处理人不能为空
	$("input[name='BALANCE_DEAL_USER']").each(function(){
		
		if(null == $(this).val() || '' == $(this).val()){
			jQuery.messager.alert("提示","处理人不能为空！");
			flag = false;
			return false;
		}
		
	});
	
	// 备注不能为空
	$("input[name='BALANCE_DEAL_REMARK']").each(function(){
		
		if(null == $(this).val() || '' == $(this).val()){
			jQuery.messager.alert("提示","备注不能为空！");
			flag = false;
			return false;
		}
		
	});
	
	if(flag){
		
	    // 遍历input并使用val()方法获取值
		var deal_is =$("select[name='BALANCE_IS_DEAL']").map(function()
	    		{return $(this).val();}).get().join(",");
	    var deal_user =$("input[name='BALANCE_DEAL_USER']").map(function()
	    		{return $(this).val();}).get().join(",");
	    
	    var deal_remark =$("input[name='BALANCE_DEAL_REMARK']").map(function()
	    		{return $(this).val();}).get().join(",");
	    
	    var apply_type =$("input[name='APPLY_TYPE']").map(function()
	    		{return $(this).val();}).get().join(",");

	    //获取
		var PROJECT_ID = $("#project_id").val();
		
		var data = {};
		data.PROJECT_ID = PROJECT_ID;
		data.DEALIS = deal_is;
		data.DEALUSER = deal_user;
		data.DEALREMARK = deal_remark;
		data.APPLYTYPE = apply_type;
		
		var url=_basePath+"/customers/ActualCost!regActualCost.action";
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
	}
	
}

/**
 * 请款数据重置
 */
function restcz(type){
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
					
					if(type == 'fee'){
						//$("#type_" + json.data[info].TYPE_ID).val(json.data[info].APPLY_TYPE==null?'':json.data[info].APPLY_TYPE);
						//$("#money_" + json.data[info].TYPE_ID).val(json.data[info].APPLY_MONEY==null?'':json.data[info].APPLY_MONEY);
						$("input[name='askMoney']").val(null);
						$("select[name='APPLY_TYPE']").val(null);
					}else{
						$("#money_" + json.data[info].TYPE_ID).val(json.data[info].ACTUAL_MONEY==null?'':json.data[info].ACTUAL_MONEY);
					}
					
				}
				
				jQuery.messager.alert("提示","重置成功!");
			}else{
				jQuery.messager.alert("提示","重置失败!");
			}
		}
	});	
	
}
