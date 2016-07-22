function surveyAdd() {
	$('#surveyAddId').linkbutton({'disabled': true});
	surveySubmit();
	$('#suverySave').form('submit', {
		url :'../credit/CreditRepayment!doSaveSurvey.action',
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$.messager.alert("提示","保存成功！");
			   //$('#courtDialog').dialog('close'); // close the dialog
		       //$('#courtTable').datagrid('load', {});
        	} else {
        		$.messager.alert("提示","保存失败请重试！");
        		$('#surveyAddId').linkbutton({'disabled': false});
        	}
        },
        error : function(e) {
        	alert(e.message);
        }
     });
  }
function surveyUpdate() {
	surveySubmit();
	$('#suverySave').form('submit', {
		url :'../credit/CreditRepayment!doUpdateSurvey.action',
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$.messager.alert("提示","保存成功！");
			   //$('#courtDialog').dialog('close'); // close the dialog
		       //$('#courtTable').datagrid('load', {});
        	} else {
        		$.messager.alert("提示","保存失败请重试！");
        	}
        },
        error : function(e) {
        	alert(e.message);
        }
     });
  }
function surveySubmit(){
	var FINANCING_PURP_CHECK='0';
	if($("#FINANCING_PURP_CHECK").is(":checked")==true){
		FINANCING_PURP_CHECK='1';
	}
	var FALSE_INFORM_CHECK='0';
	if($("#FALSE_INFORM_CHECK").is(":checked")==true){
		FALSE_INFORM_CHECK='1';
	}
	var PATROL_CHECK='0';
	if($("#PATROL_CHECK").is(":checked")==true){
		PATROL_CHECK='1';
	}
	var INSURANCE_CHECK='0';
	if($("#INSURANCE_CHECK").is(":checked")==true){
		INSURANCE_CHECK='1';
	}
	var INSTALL_GPS_CHECK='0';
	if($("#INSTALL_GPS_CHECK").is(":checked")==true){
		INSTALL_GPS_CHECK='1';
	}
	var REPAYMENT_CHECK='0';
	if($("#REPAYMENT_CHECK").is(":checked")==true){
		REPAYMENT_CHECK='1';
	}
	
	var dataJson ={
		CREDIT_ID:$("#suvery_CREDIT_ID").val(),
		ID:$("#suvery_ID").val(),
		
		FINANCING_PURP_CHECK:FINANCING_PURP_CHECK,
		FINANCING_PURP_TYPE :$("input:radio[name=FINANCING_PURP_TYPE][checked]").val(),
		FINANCING_PURP:$("#FINANCING_PURP").val(),
		
		FALSE_INFORM_CHECK:FALSE_INFORM_CHECK,
		FALSE_INFORM_TYPE :$("input:radio[name=FALSE_INFORM_TYPE][checked]").val(),
		FALSE_INFORM:$("#FALSE_INFORM").val(),
		
		PATROL_CHECK:PATROL_CHECK,
		PATROL_TYPE :$("input:radio[name=PATROL_TYPE][checked]").val(),
		PATROL:$("#PATROL").val(),
		
		INSURANCE_CHECK:INSURANCE_CHECK,
		INSURANCE_TYPE :$("input:radio[name=INSURANCE_TYPE][checked]").val(),
		INSURANCE:$("#INSURANCE").val(),
		
		INSTALL_GPS_CHECK:INSTALL_GPS_CHECK,
		INSTALL_GPS_TYPE :$("input:radio[name=INSTALL_GPS_TYPE][checked]").val(),
		INSTALL_GPS:$("#INSTALL_GPS").val(),
		
		REPAYMENT_CHECK:REPAYMENT_CHECK,
		REPAYMENT_TYPE :$("input:radio[name=REPAYMENT_TYPE][checked]").val(),
		REPAYMENT:$("#REPAYMENT").val()
		
	};
	$("#alldata").val(JSON.stringify(dataJson));
	
}