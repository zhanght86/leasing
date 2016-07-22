function graphTJ(url){
	$.ajax({
		type:'post',
		url:url,
		data:"param="+getFromData('#'+formId),
		dataType:'json',
		success:function(json){
			if(json.flag){
				$("#chartShow").html(json.data);
			}
			else{
				$("#chartShow").html(json.msg);
			}
		}
	});
}


//function graphTJ(){
//	alert(formId);
//	$("#"+formId).submit();
//}

function dataShow(title,url){
	top.addTab(title,url+"?param="+$('#'+formId).serialize());
}
function dataShow1(title,url,type){
	top.addTab(title,url+"?TYPE="+type);
}

function dataShow2(title,url){
	var INDICATORS_TYPE=$("input[name='INDICATORS_TYPE']").val();
	var TIMETYPE=$("select[name='TIMETYPE']").val();
	var TYPE=$("select[name='TYPE']").val();
	top.addTab(title,url+"?INDICATORS_TYPE="+INDICATORS_TYPE+"&TIMETYPE="+TIMETYPE+"&TYPE="+TYPE);
}


function REPORT_QJ(obj){
	var REPORT_DATE=$(obj).find("option:selected").val();
	if(REPORT_DATE=='6'){
		$("#"+formId+" #START_TIME").datebox("enable");
		$("#"+formId+" #END_TIME").datebox("enable");
	}
	else{
		$("#"+formId+" #START_TIME").datebox("disable");
		$("#"+formId+" #END_TIME").datebox("disable");
		$("#"+formId+" #START_TIME").datebox("setValue",'');
		$("#"+formId+" #END_TIME").datebox("setValue",'');
	}
}