
//保存意见表信息
function feedBackFormSave(){
	var project_id = $("#project_id").val();
	var matter_name = $("#matter_name").val();
	var useful_life = $("#useful_life").val();
	var property_clear =$("#property_clear").val();
	var market_value =$("#market_value").val();
	var is_lease =$("#is_lease").val();
	var funds_use =$("#funds_use").val();
	var matter_legal =$("#matter_legal").val();
	var capital_purposes =$("#capital_purposes").val();
	var repayment_ability =$("#repayment_ability").val();
	var measures =$("#measures").val();
	var reason =$("#reason").val();
	var clerk_name =$("#clerk_name").val();
	var filing_date =$("#filing_date").datebox('getValue'); 
	var approval_comments =$("#approval_comments").val();
	var principal =$("#principal").val();
	var signature_date =$("#signature_date").datebox('getValue'); 
	
	//var data="PROJECT_ID="+project_id+"&MATTER_NAME="+matter_name+"&USEFUL_LIFE="+useful_life;
	var data = {
			PROJECT_ID : project_id,
			MATTER_NAME : matter_name,
			USEFUL_LIFE : useful_life,
			PROPERTY_CLEAR : property_clear,
			MARKET_VALUE : market_value,
			IS_LEASE : is_lease,
			FUNDS_USE : funds_use,
			MATTER_LEGAL : matter_legal,
			CAPITAL_PURPOSES : capital_purposes,
			REPAYMENT_ABILITY : repayment_ability,
			MEASURES : measures,
			REASON : reason,
			CLERK_NAME : clerk_name,
			FILING_DATE : filing_date,
			APPROVAL_COMMENTS : approval_comments,
			PRINCIPAL : principal,
			SIGNATURE_DATE : signature_date
	};
	/*var data1="PROJECT_ID="+project_id+"&MATTER_NAME="+matter_name+"&USEFUL_LIFE="+useful_life
				+"&PROPERTY_CLEAR="+property_clear+"&MARKET_VALUE="+market_value+"&IS_LEASE="+is_lease
				+"&FUNDS_USE="+funds_use+"&MATTER_LEGAL="+matter_legal+"&CAPITAL_PURPOSES="+capital_purposes
				+"&REASON="+reason+"&CLERK_NAME="+clerk_name+"&FILING_DATE="+filing_date
				+"&APPROVAL_COMMENTS="+approval_comments+"&PRINCIPAL="+principal+"&SIGNATURE_DATE="+signature_date;
	alert("data"+data);
	alert("data1"+data1);*/
	jQuery.ajax({
		url:_basePath+"/consider/Consider!feedBackSave.action",
		type:"post",
		dataType:'json',
		data:data,
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","租赁项目立项审议意见表保存成功!");
			}
		}
	});
}
//删除意见表信息
function deleteFeedBack(){
	var project_id=$("#PROJECT_ID").val();
	var data ="PROJECT_ID="+project_id;
	jQuery.ajax({
		url:_basePath+"/consider/Consider!deleteFeedBack.action",
		type:"post",
		dataType:'json',
		data:data,
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","删除成功!");
			}
		}
	});
	
}
//修改申请书信息
function updateApp(){
	var project_id=$("#PROJECT_ID").val();
	var word_contents = UE.getEditor('container').getContent();
	var data = {PROJECT_ID:project_id,WORD_CONTENTS:word_contents};//"="+project_id+"&="+word_contents;
	jQuery.ajax({
		url:_basePath+"/consider/Consider!appSave.action",
		type:"post",
		dataType:'json',
		data:data,
		success:function(json){
		if(json.flag){
			$.messager.alert("提示","租赁项目评审申请书保存成功!");
		}
	}
	});
	
}
//删除申请书信息
function deleteApp(){
	var project_id=$("#PROJECT_ID").val();
	var data ="PROJECT_ID="+project_id;
	jQuery.ajax({
		url:_basePath+"/consider/Consider!deleteApp.action",
		type:"post",
		dataType:'json',
		data:data,
		success:function(json){
		if(json.flag){
			$.messager.alert("提示","删除成功!");
		}
	}
	});
	
}

function showIrr(){
	var showIrrSpan=$("#showIrrSpan").html();
	if(showIrrSpan==null||showIrrSpan==''){
		$.messager.alert("消息","暂无收益率可查看");
		return;
	}
	$.ajax({
		type:'post',
		url:_basePath + "/pay/PayTask!showIrr.action",
		dataType:'json',
		success:function(json){
			if(json.flag)
				$.messager.alert("查看","租金收益率为"+$("#showIrrSpan").html()+"%");
			else{
				alert(json.msg);
			}
		}
	});
	
}

//租赁投放审查审批表
function placeReviewSave(){
	var ID =$("#THIS_ID").val();
	var PROJECT_ID=$("#PROJECT_ID").val();
	var CLIENT_ID=$("#CLIENT_ID").val();
	var data="PROJECT_ID="+PROJECT_ID+"&CLIENT_ID="+CLIENT_ID;
	//意见内容和经理等字段如(BUS_CONTENTS\BUS_MANAGER || LAW_CONTENTS\LAW_MANAGER)
	//特殊例子:页面字段有规律的名称,可以循环获取值并拼成数据
	var list = new Array("BUS","LAW","ASSETS","DEPUTY","GENERAL","PLAN","FINANCE","HANDLE");
	var _list= new Array("_CONTENTS","_MANAGER","_PRINCIPAL","_DATE");
	for(var i=0; i<list.length; i++){
		var str = list[i];//字段前缀
		for(var j=0;j<_list.length;j++){
			var _str=_list[j];//字段后缀
			if(_str=="_PRINCIPAL" && (str=="DEPUTY" || str=="GENERAL" || str=="HANDLE")){
				continue;//跳出页面不存在的字段
			}
			var name=str+_str;//字段名称
			var value=$("#"+str+_str).val();//属性值
			if(_str=="_DATE"){//easyui 取日期值
				value=$("#"+str+_str).datebox('getValue');
			}
//			alert("name:"+name+"--value:"+value);
			data+="&"+name+"="+value;//拼成页面提交数据
		}
//		alert("str:"+str+"--data:"+data);
	}
	var url=_basePath+"/consider/Consider!placeReviewSave.action";
	if(ID==null || ID==""){
		url=_basePath+"/consider/Consider!placeReviewAdd.action";
	}
	jQuery.ajax({
		type:"post",
		url:url,
		dataType:"json",
		data:data,
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","租赁投放审查审批表保存成功");
			}
		}
	});
}

