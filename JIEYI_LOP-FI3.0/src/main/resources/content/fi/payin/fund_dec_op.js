$(function() {
	$(".baseItem").first().removeAttr("disabled");
	$(".baseItem").click(function() {
		nextItemClick(this);
		dfx();
	});
	$("[name='r_arard']").change(
		function() {
			var maxvalue = anyDouble($(this).attr("maxvalue"));
			var val = anyDouble($(this).val());
			var rm = r_arard();
			if (rm > maxMoney) {
				val = val - (rm - maxMoney);
				$(this).val(val);
			}
			if (val >= maxvalue) {
				$(this).val(maxvalue);
				$(this).parent().parent().next().find(".baseItem")
						.removeAttr("disabled");
			} else {
				var nextItem = $(this).parent().parent().next().find(
						".baseItem");
				if (nextItem.attr("checked") == "checked") {
					nextItem.removeAttr("checked");
					nextItemClick(nextItem);
				}
				nextItem.attr("disabled", "disabled");
			}
			dfx();
		});
});
function nextItemClick(e) {
	var mText = $(e).parent().parent().find("[name='r_arard']");
	if ($(e).attr("checked") == "checked") {
		var maxvalue = mText.attr("maxvalue");
		mText.removeAttr("disabled");
		var vv = anyDouble($("#txtLaveMoney").val());
		if (vv > maxvalue) {
			mText.val(maxvalue).change();
		} else {
			mText.val(vv).change();
		}
	} else {
		mText.val(0);
		mText.attr("disabled", "disabled");
		if($(e).attr("iszj")=="iszj"){
			var nextItem = $(e).parent().parent().next().find(".baseItem");
			if (nextItem.attr("checked") == "checked") {
				nextItem.removeAttr("checked");
				nextItemClick(nextItem);
			}
			nextItem.attr("disabled", "disabled");
		}
	}
}

function autoClick(e){
	if($(e).attr("checked") == "checked"){
		var txtLaveMoney = anyDouble($("#txtLaveMoney").val());
		var nextItem = $(e);
		while(txtLaveMoney>0){
			nextItem = $(nextItem).parent().parent().next().find(".baseItem");
			if(nextItem.size()==0)break;
			if (nextItem.attr("disabled") != "disabled") {
				if(nextItem.attr("checked") != "checked"){
					nextItem.attr("checked","checked");
					nextItemClick(nextItem);
				}
			}else{
				break;
			}
		}
	}else{
		var nextItem = $(e);
		while(true){
			nextItem = $(nextItem).parent().parent().next().find(".baseItem");
			if(nextItem.size()==0)break;
			if (nextItem.attr("disabled") == "disabled") {
				break;
			}
			if(nextItem.attr("checked") == "checked"){
				nextItem.removeAttr("checked");
				nextItemClick(nextItem);
			}
		}
	}
}
function r_arard() {
	var m = 0;
	$("[name='r_arard']").each(function() {
		m += parseFloat($(this).val());
	});
	return anyDouble(m,2);
}
function dfx() {
	$("#txtLaveMoney").val(anyDouble(maxMoney - r_arard(),2));
}

function getSubParam() {
	var json = {};
	var laveMoney = anyDouble($("#txtLaveMoney").val());
	json.FUND_ID = FUND_ID;
	json.selectData = [];
	json.FUND_DFJ = laveMoney;
	var currMoney = 0;
	var a = 0;
	$("#decList input:checkbox:checked").each(function() {
		a = a + 1;
	});
	if (a == 0) {
		alert("请选择要分解的项目！");
		return false;
	}
	
	var IDS="";
	var aa=0;
	$(".baseItem:checked").each(function (){
			if(aa == 0){
				IDS=$(this).attr("sid");
			}else{
				IDS=IDS +","+ $(this).attr("sid");
			}
		}
			
	);
	
	//先判断是不是解锁，如果锁定则不能核销
	$.ajax({
		url:_basePath+"/rentWrite/rentWriteNew!LockTypeIsF.action?IDS="+IDS,
	    type:'post',
	    dataType:'json',
	    success:function(jsonId){
		    if(jsonId.data == '2'){
		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
		    }
		    else if(jsonId.data == '3'){
		    	return $.messager.alert("锁定提示","您所选择的数据已经被操作过,请刷新页面在操作！");
		    }else{
		    	$(".baseItem:checked").each(
		    			function() {
		    				var m = anyDouble($(this).parents("tr")
		    						.find("[name='r_arard']").val());
		    				if(m<=0) return;
		    				currMoney += m;
		    				json.selectData[json.selectData.length] = {
		    					PAID_MONEY : m,
		    					ID : $(this).attr("sid"),
		    					IS_BUY_PENALTY : (jQuery.trim(
		    							$(this).parents("tr").find("td:nth-child(4)")
		    									.text()).indexOf("违约金") > 0) ? 0 : 1
		    				// FUND_PAY_CODE : jQuery.trim($(this).parents("tr").find(
		    				// "td:nth-child(2)").text()),
		    				// FUND_PAY_PROJECT : jQuery.trim($(this).parents("tr").find(
		    				// "td:nth-child(4)").text()),
		    				// FUND_PAY_DATE : jQuery.trim($(this).parents("tr").find(
		    				// "td:nth-child(5)").text()),
		    				// FUND_PAY_COPE : jQuery.trim(
		    				// $(this).parents("tr").find("td:nth-child(8)")
		    				// .text()).replace(/[￥,]/g, '')
		    				};
		    			});
		    	if (Math.round(currMoney * 100) != Math.round((maxMoney - laveMoney) * 100)) {
		    		alert("当前分解金额与资金到账金额不一致，请检查分解明细！");
		    		return;
		    	}
		    	if (!confirm("确认保存"))
		    		return;
		    	// var t = $(this);
		    	// t.hide();
		    	// $("#decList input,#decList select").attr("disabled", true);
		    	jQuery.ajax({
		    		url : _basePath + "/fi/payin/FundDec!doOp.action",
		    		data : {
		    			FUND_ID : FUND_ID,
		    			DFJ : laveMoney,
		    			APP_CREATE_TYPE : 2,
		    			BANK_ACCOUNT : "",
		    			BANK_NAME : "",
		    			FI_FAG : FI_FAG,
		    			FI_PAY_DATE : $("#FUND_ACCEPT_DATE").val(),
		    			FI_PAY_MONEY : currMoney,
		    			FI_TO_THE_PAYEE : $("#FI_TO_THE_PAYEE").val(),
		    			FI_TO_THE_ACCOUNT : $("#FI_TO_THE_ACCOUNT").val(),
		    			FI_PAY_TYPE : 1,
		    			FI_PROJECT_NUM : a,
		    			FI_REALITY_BANK : '中国建设银行',
		    			FI_REALITY_MONEY : currMoney,
		    			selectDateHidden : JSON.stringify(json)
		    		},
		    		type : "post",
		    		dataType : "json",
		    		success : function(json) {
		    			if (json.flag) {
		    				$.messager.alert("提示","发起流程成功！",'info',function(){
		    					$.messager.alert("提示",json.msg+json.data,"info",function(){
		    						;
		    					});
		    				});
		    			} else {
		    				$.messager.alert("提示","发起流程失败！",'info',function(){
		    					
		    				});
		    			}
		    			// document.getElementById('frmDeco').submit();
		    		},
		    		error : function(r, e) {
		    			alert(e.message);
		    		}
		    	});
		    }
	    }
	});

	
}
