function addConfiguration(id, keyName, type){
	var data = "PDID=" + id + "&NAME=" + keyName + "&TYPE=" + type;
	jQuery.ajax({
		type:"post",
		data:data,
		dataType:"json",
		url:"Deployment!doSelectJbpmConfigurationVM.action",
		success: function(json) {
		  	if(json.flag) {
				var param = json.data;
				if(param.TYPE == 'task'){
					$('#configurationParam').panel({title:'任务节点参数'});
					$("#PDID").val(param.PDID);
					$("#NAME").val(param.NAME);
					$("#TYPE").val(param.TYPE);
					$("#FORM").val(param.FORM);
					$("#CYY").val(param.CYY);
					$('#ASSIGN_TYPE').combobox('select', param.ASSIGN_TYPE);
					$("#ASSIGN_VALUE").val(param.ASSIGN_VALUE);
					$("#CY").val(param.CY);
					if(param.ENTRUST_FLAG=="nnnn")$("[name='ENTRUST_FLAG'][value='nnnn']").click();
					else $("[name='ENTRUST_FLAG'][value='yyyy']").click();
					if(param.MEMO_FLAG=="nnnn")$("[name='MEMO_FLAG'][value='nnnn']").click();
					else $("[name='MEMO_FLAG'][value='yyyy']").click();
					if(param.CYY_FLAG=="nnnn")$("[name='CYY_FLAG'][value='nnnn']").click();
					else $("[name='CYY_FLAG'][value='yyyy']").click();
					if(param.SMS=="nnnn")$("[name='SMS'][value='nnnn']").click();
					else $("[name='SMS'][value='yyyy']").click();
					if(param.WX=="nnnn")$("[name='WX'][value='nnnn']").click();
					else $("[name='WX'][value='yyyy']").click();
					if(param.EMAIL=="nnnn")$("[name='EMAIL'][value='nnnn']").click();
					else $("[name='EMAIL'][value='yyyy']").click();
					$("#startVM").hide();
					$("#joinVM").hide();
					$("#decisionVM").hide();
    				$("#taskVM").show();
					$("#javaVM").hide();
				}
				if(param.TYPE == 'start'){
					$('#configurationParam').panel({title:'开始节点参数'});
					$("#PDID1").val(param.PDID);
					$("#NAME1").val(param.NAME);
					$("#TYPE1").val(param.TYPE);
					$("#joinVM").hide();
					$("#decisionVM").hide();
    				$("#taskVM").hide();
					$("#startVM").show();
					$("#javaVM").hide();
				}
				if(param.TYPE == 'join'){
					$('#configurationParam').panel({title:'会签节点参数'});
					$("#PDID2").val(param.PDID);
					$("#NAME2").val(param.NAME);
					$("#TYPE2").val(param.TYPE);
					$("#ASSIGN_VALUE_JOIN").val(param.ASSIGN_VALUE);
					$("#joinVM").show();
					$("#decisionVM").hide();
    				$("#taskVM").hide();
					$("#startVM").hide();
					$("#javaVM").hide();
				}
//				if(param.TYPE == 'decision'){
//					$('#configurationParam').panel({title:'判断节点参数'});
//					$("#PDID3").val(param.PDID);
//					$("#NAME3").val(param.NAME);
//					$("#TYPE3").val(param.TYPE);
//					$("#ASSIGN_VALUE_DECISION").val(param.ASSIGN_VALUE);
//					$("#joinVM").hide();
//					$("#decisionVM").show();
//    				$("#taskVM").hide();
//					$("#startVM").hide();
//					$("#javaVM").hide();
//				}
//				if(param.TYPE == 'java'){
//					$('#configurationParam').panel({title:'JAVA节点参数'});
//					$("#PDID4").val(param.PDID);
//					$("#NAME4").val(param.NAME);
//					$("#TYPE4").val(param.TYPE);
//					$("#JAVA_CLASS").combobox('select',param.JAVA_NAME);
//					$("#joinVM").hide();
//					$("#javaVM").show();
//    				$("#taskVM").hide();
//					$("#startVM").hide();
//					$("#decisionVM").hide();
//				}
			} else {
				alert(json.msg);
   			}  
		}	
	});
}

function submitStart(){
	$("#submitForm1").form('submit');
	var PDID = $("#PDID1").val();
	var NAME = $("#NAME1").val();
	var TYPE = $("#TYPE1").val();
	var INIT_OP = $("#INIT_OP1").val();
	var param = {
			'PDID': PDID,
			'NAME': NAME,
			'TYPE': TYPE
	};
	var data = "param="+JSON.stringify(param);
	jQuery.ajax({
		url : "Deployment!doUpdateJbpmConfiguration.action",
		data : data,
		dataType : "json",
		success : function(json){
			if(json.flag){
				alert(json.msg);
			}else{
				alert(json.msg);
			}	
		}
	});
}

function submitJoin(){
	$("#submitFormJoin").form('submit');
	var PDID = $("#PDID2").val();
	var NAME = $("#NAME2").val();
	var TYPE = $("#TYPE2").val();
	var ASSIGN_VALUE_JOIN = $("#ASSIGN_VALUE_JOIN").val();
	var param = {
			'PDID': PDID,
			'NAME': NAME,
			'TYPE': TYPE,
	'ASSIGN_VALUE_JOIN': ASSIGN_VALUE_JOIN
	};
	var data = "param="+JSON.stringify(param);
	jQuery.ajax({
		url : "Deployment!doUpdateJbpmConfiguration.action",
		data : data,
		dataType : "json",
		success : function(json){
		if(json.flag){
			alert(json.msg);
		}else{
			alert(json.msg);
		}	
	}
	});
}

function submitDecision(){
	$("#submitFormDecision").form('submit');
	var PDID = $("#PDID3").val();
	var NAME = $("#NAME3").val();
	var TYPE = $("#TYPE3").val();
	var ASSIGN_VALUE_DECISION = $("#ASSIGN_VALUE_DECISION").val();
	var param = {
			'PDID': PDID,
			'NAME': NAME,
			'TYPE': TYPE,
			'ASSIGN_VALUE_DECISION': ASSIGN_VALUE_DECISION
	};
	var data = "param="+JSON.stringify(param);
	jQuery.ajax({
		url : "Deployment!doUpdateJbpmConfiguration.action",
		data : data,
		dataType : "json",
		success : function(json){
		if(json.flag){
			alert(json.msg);
		}else{
			alert(json.msg);
		}	
	}
	});
}

function submitTask(){
	//$("#submitForm").form('submit');
	var PDID = $("#PDID").val();
	var NAME = $("#NAME").val();
	var TYPE = $("#TYPE").val();
	var FORM = $("#FORM").val();
	var ASSIGN_TYPE = $("#ASSIGN_TYPE").combobox("getValue");
	var ASSIGN_VALUE = $("#ASSIGN_VALUE").val();
	var MEMO_FLAG = $("[name='MEMO_FLAG']:checked").val();
	var ENTRUST_FLAG = $("[name='ENTRUST_FLAG']:checked").val();
	var CY = $("[name='CY']").val();
	var CYY = $("[name='CYY']").val();
	var EMAIL = $("[name='EMAIL']:checked").val();
	var SMS = $("[name='SMS']:checked").val();
	var WX = $("[name='WX']:checked").val();
	var CYY_FLAG = $("[name='CYY_FLAG']:checked").val();
	var param = {
                'PDID': PDID,
                'NAME': NAME,
                'TYPE': TYPE,
                'FORM': FORM,
		'ASSIGN_TYPE' : ASSIGN_TYPE,
	   'ASSIGN_VALUE' : ASSIGN_VALUE,
	   	  'MEMO_FLAG' : MEMO_FLAG,
	   	  		 'CY' : CY,
	   	  		 'CYY' : CYY,
	   	  		 'CYY_FLAG' : CYY_FLAG,
	   	  		 'SMS' : SMS,
	   	  		 'EMAIL' : EMAIL,
	   	  		 'WX' : WX,
	   'ENTRUST_FLAG' : ENTRUST_FLAG
	};
	var data = {param:JSON.stringify(param)};
	jQuery.ajax({
		url : "Deployment!doUpdateJbpmConfiguration.action",
		type :"post",
		data : data,
		dataType : "json",
		success : function(json){
			if(json.flag){
				alert(json.msg);
			}else{
				alert(json.msg);
			}	
		}
	});
}

function submitJava(){
	var PDID = $("#PDID4").val();
	var NAME = $("#NAME4").val();
	var TYPE = $("#TYPE4").val();
	var ASSIGN_VALUE_CLASS = $("#ASSIGN_VALUE_CLASS").val();
	var ASSIGN_VALUE_METHOD = $("#ASSIGN_VALUE_METHOD").val();
	var ASSIGN_VALUE_PARAM = $("#ASSIGN_VALUE_PARAM").val();
	var JAVA_CLASS = $("#JAVA_CLASS").combobox("getValue");
	var param = {
                'PDID': PDID,
                'NAME': NAME,
                'TYPE': TYPE,
		'ASSIGN_VALUE_CLASS' : ASSIGN_VALUE_CLASS,
	   'ASSIGN_VALUE_METHOD' : ASSIGN_VALUE_METHOD,
	   'FLAG' : JAVA_CLASS,
	   'ASSIGN_VALUE_PARAM' : ASSIGN_VALUE_PARAM
	};
	var data = "param="+encodeURI(JSON.stringify(param));
	jQuery.ajax({
		url : "Deployment!doUpdateJbpmConfiguration.action",
		type :"post",
		data : data,
		async : false,
		dataType : "json",
		success : function(json){
			if(json.flag){
				alert(json.msg);
			}else{
				alert(json.msg);
			}	
		}
	});
}

function clearTask(){
    $('#FORM').val('');
    $('#ASSIGN_TYPE').combobox('select', '');
    $('#ASSIGN_VALUE').val('');
}

function clearStart(){
}

function clearJoin(){
	$('#ASSIGN_VALUE_JOIN').val('');
}

function clearDecision(){
	$('#ASSIGN_VALUE_DECISION').val('');
}

function clearJava(){
	$('#ASSIGN_VALUE_METHOD').val('');
	$('#ASSIGN_VALUE_CLASS').val('');
	$('#ASSIGN_VALUE_PARAM').val('');
}