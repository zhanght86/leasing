function testButton(){
	var  IN_DATE1 = $("#ANALYZE_DATE1").datebox("getValue");
	var  IN_DATE2 = $("#ANALYZE_DATE2").datebox("getValue");
	var  IN_DATE3 = $("#ANALYZE_DATE3").datebox("getValue");
	var CLIENT_ID = $("#CLIENT_ID").val();
	jQuery.ajax({
		type:"post",
		 url:_basePath+"/customers/FinancialStatistics!testFinancialIndex.action",
	    data:"IN_DATE="+IN_DATE1+"&IN_DATE1="+IN_DATE2+"&IN_DATE2="+IN_DATE3+"&CLIENT_ID="+CLIENT_ID,
	dataType:"json",
	 success:function(json){
		/** 
		* 盈利能力
		 */
		if(json.profitability!=undefined){
			for(var i=0;i<json.profitability.length;i++){
				var k = i+1;
				$("tr[name='SELL_GROSS_PROFIT']").find("input[name='SELL_GROSS_PROFIT"+k+"']").val(json.profitability[i].MAIN_BUSINESS);
				$("tr[name='SELL_NET_PROFIT']").find("input[name='SELL_NET_PROFIT"+k+"']").val(json.profitability[i].JING_RATE);
				$("tr[name='ACCET_RATE_OF_RETURN']").find("input[name='ACCET_RATE_OF_RETURN"+k+"']").val(json.profitability[i].JINGLIRUN);
			}
		}
		
		
		/**
		 * 偿债能力
		 */
		if(json.debtAbility!=undefined){
			for(var i=0;i<json.debtAbility.length;i++){
				var k = i+1;
				$("tr[name='FLOW_RATE']").find("input[name='FLOW_RATE"+k+"']").val(json.debtAbility[i].FLOW_RATE);
				$("tr[name='QUICK_FREEZE_RATE']").find("input[name='QUICK_FREEZE_RATE"+k+"']").val(json.debtAbility[i].QUICK_FREEZE_RATE);
				$("tr[name='ASSET_LIABILITIES_RATE']").find("input[name='ASSET_LIABILITIES_RATE"+k+"']").val(json.debtAbility[i].ZCFZ_RATE);
				$("tr[name='LEVER_RATE']").find("input[name='LEVER_RATE"+k+"']").val(json.debtAbility[i].GANGGAN_RATE);
			}
		}
		
		/**
		 * EBITDA
		 */

		if(json.EBITDA!=undefined){
			for(var i=0;i<json.EBITDA.length;i++){
				var k = i+1;
				$("tr[name='EBITDA']").find("input[name='EBITDA"+k+"']").val(json.debtAbility[i].EBITDA);
			}
		}
	
		
		/**
		 * 经营现金流分析
		 */
	
		if(json.jyxjFlowAnalyze!=undefined){
			for(var i=0;i<json.jyxjFlowAnalyze.length;i++){
				var k = i+1;
				$("tr[name='CASH_FLOW_DEBT']").find("input[name='CASH_FLOW_DEBT"+k+"']").val(json.jyxjFlowAnalyze[i].XJ_LD_FZ_RATE);
				$("tr[name='XJFZZE_RATE']").find("input[name='XJFZZE_RATE"+k+"']").val(json.jyxjFlowAnalyze[i].XJ_ZW_ZE_RATE);
				$("tr[name='SELL_CASH_RATE']").find("input[name='SELL_CASH_RATE"+k+"']").val(json.jyxjFlowAnalyze[i].XSH_XJ_RATE);
				$("tr[name='YYXJHL_RATE']").find("input[name='YYXJHL_RATE"+k+"']").val(json.jyxjFlowAnalyze[i].YY_XJ_HL_RATE);
			}
		}

		
		/**
		 * 发展能力--销售收入增长率
		 */
	
		if(json.salesGrowthRate!=undefined){
			for(var i=0;i<json.salesGrowthRate.length;i++){
				var k = i+1;
				$("tr[name='SELL_INCOME_INCREASE']").find("input[name='SELL_INCOME_INCREASE"+k+"']").val(json.salesGrowthRate[i].sellincomeRate);
			}
		}
		
		
		/**
		 * 发展能力--总资产增长率
		 */
	
		if(json.AssetsGrowthRate!=undefined){
			for(var i=0;i<json.AssetsGrowthRate.length;i++){
				var k = i+1;
				$("tr[name='ASSET_INCOME_INCREASE']").find("input[name='ASSET_INCOME_INCREASE"+k+"']").val(json.AssetsGrowthRate[i].assetRate);
			}
		}
	
		
		/**
		 * 运营能力
		 */
	
		if(json.servicePower!=undefined){
			for(var i=0;i<json.servicePower.length&&json.servicePower!=undefined;i++){
				var k = i+1;
				$("tr[name='YSZKZE_DAYS']").find("input[name='YSZKZE_DAYS"+k+"']").val(json.servicePower[i].YS_ZK_TSH);
				$("tr[name='CHZHZH_DAYS']").find("input[name='CHZHZH_DAYS"+k+"']").val(json.servicePower[i].CH_ZK_TSH);
				$("tr[name='TOTAL_ASSET_DAYS']").find("input[name='TOTAL_ASSET_DAYS"+k+"']").val(json.servicePower[i].ZZC_ZK_TSH);
			}
		}
		
	 }
});
	
}
function checkInDate(){
	var indates = $("tr[name='ANALYZE_DATE']").find("input");
	for(var i=0;i<indates.length;i++){
		var value=$(indates[i]).datebox("getValue");
		if(value==null || value=='' || value==undefined){
			 $.messager.alert("操作提示","请先完成财务报表中前面选项卡中的内容再进行财务指标操作!","error") ;
			return false;
		}			
	}		
	return true ;
}

function saveButton(){
	//alert("bankstatistics");
	if(!$("#validForm").form("validate")){
		$.messager.alert("操作提示","请先完成财务报表中前面选项卡中的内容再进行财务指标操作!","error") ;
		return;
	} 
	var formData = [];
	var dataMap0 = {};
	var dataMap1 = {};
	var dataMap2 = {};
	var inputs = $("td[name='td_data0']").find("input");
	for ( var i = 0; i < inputs.length; i++) {
		
		var name = $(inputs[i]).parent().parent().attr("name");
		if(name=='ANALYZE_DATE'){
			dataMap0[name] = $(inputs[i]).datebox("getValue");
		}else {
			dataMap0[name] = $(inputs[i]).val();
		}
	}
	formData.push(dataMap0);
	var debt1 = [];
	var inputs1 = $("td[name='td_data1']").find("input");
	for ( var i = 0; i < inputs1.length; i++) {
		
		var name = $(inputs1[i]).parent().parent().attr("name");
		if(name=='ANALYZE_DATE'){
			dataMap1[name] = $(inputs1[i]).datebox("getValue");
		}else {
			dataMap1[name] = $(inputs1[i]).val();
		}
		
		
	}
	formData.push(dataMap1);
	var debt2 = [];
	var inputs2 = $("td[name='td_data2']").find("input");
	for ( var i = 0; i < inputs2.length; i++) {
		
		var name = $(inputs2[i]).parent().parent().attr("name");
		if(name=='ANALYZE_DATE'){
			dataMap2[name] = $(inputs2[i]).datebox("getValue");
		}else {
			dataMap2[name] = $(inputs2[i]).val();
		}
		
	}
	formData.push(dataMap2);
	var debt_ = [];


	var dataMap2 = {
		FORMDATA : formData,
		CLIENT_ID : $("input[name='CLIENT_ID']").val(),
		UNIT_NAME : $("input[name='UNIT_NAME']").val(),
		CHECK_PEOPLE : $("input[name='CHECK_PEOPLE']").val(),
		CHECK_DATE : $("#CHECK_DATE").datebox("getValue"),
		INDEX_CODE : $("input[name='INDEX_CODE']").val(),
		BELONG_DATE : $("input[name='BELONG_DATE']").val(),
		REVIEW_PEOPLE : $("input[name='REVIEW_PEOPLE']").val(),
		REVIEW_DATE : $("#REVIEW_DATE").datebox("getValue")
	};
	$("#ChangeViewData").val(JSON.stringify(dataMap2));
	//$("#formView").submit();
	$('#formView').form('submit',{
		 success:function(data){
			 data = $.parseJSON(data);
			 if(data.flag==true){
				 $.messager.alert('提示', "保存成功", "保存成功");
				 $('#testButton').linkbutton('disable');
				 $('#subButton').linkbutton('disable');
			 }else {
				 $.messager.alert('提示', "保存失败", "保存失败");
			 }
		 }
	});
}
//修改
function EditButton(){
	if(!$("#validForm").form("validate")){
		 $.messager.alert("操作提示","请先完成财务报表中前面选项卡中的内容再进行财务指标操作!","error") ;
		return;
	} 
	var formData = [];
	debugger;
	var dataMap0 = {};
	var dataMap1 = {};
	var dataMap2 = {};
	var inputs = $("td[name='td_data0']").find("input");
	for ( var i = 0; i < inputs.length; i++) {
		var name = $(inputs[i]).parent().parent().attr("name");
		if(name=='ANALYZE_DATE'){
			dataMap0[name] = $(inputs[i]).datebox("getValue");
		}else {
			dataMap0[name] = $(inputs[i]).val();
		}
		
		
	}
	formData.push(dataMap0);
	var debt1 = [];
	var inputs1 = $("td[name='td_data1']").find("input");
	for ( var i = 0; i < inputs1.length; i++) {
		var name = $(inputs1[i]).parent().parent().attr("name");
		if(name=='ANALYZE_DATE'){
			dataMap1[name] = $(inputs1[i]).datebox("getValue");
		}else {
			dataMap1[name] = $(inputs1[i]).val();
		}
		
		
	}
	formData.push(dataMap1);
	var debt2 = [];
	var inputs2 = $("td[name='td_data2']").find("input");
	for ( var i = 0; i < inputs2.length; i++) {
		var name = $(inputs2[i]).parent().parent().attr("name");
		if(name=='ANALYZE_DATE'){
			dataMap2[name] = $(inputs2[i]).datebox("getValue");
		}else {
			dataMap2[name] = $(inputs2[i]).val();
		}
		
	}
	formData.push(dataMap2);
	var debt_ = [];
	
	var dataMap2 = {
		FORMDATA : formData,
		CLIENT_ID : $("input[name='CLIENT_ID']").val(),
		UNIT_NAME : $("input[name='UNIT_NAME']").val(),
		CHECK_PEOPLE : $("input[name='CHECK_PEOPLE']").val(),
		CHECK_DATE : $("#CHECK_DATE").datebox("getValue"),
		INDEX_CODE : $("input[name='INDEX_CODE']").val(),
		BELONG_DATE : $("input[name='BELONG_DATE']").val(),
		REVIEW_PEOPLE : $("input[name='REVIEW_PEOPLE']").val(),
		REVIEW_DATE : $("#REVIEW_DATE").datebox("getValue")
	};
	$("#ChangeViewData").val(JSON.stringify(dataMap2));
	//$("#formView").submit();
	$('#formView').form('submit',{
		 success:function(data){
			 data = $.parseJSON(data);
			 if(data.flag==true){
				 $.messager.alert('提示', "保存成功", "保存成功");
			 }else {
				 $.messager.alert('提示', "保存失败", "保存失败");
			 }
		 }
	});
}