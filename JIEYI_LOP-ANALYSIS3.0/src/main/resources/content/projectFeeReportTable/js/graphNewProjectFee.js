function REPORT_QJ(obj){
	var REPORT_DATE=$(obj).find("option:selected").val();
	if(REPORT_DATE=='6'){
		$("#"+toolbarId+" #START_TIME").datebox("enable");
		$("#"+toolbarId+" #END_TIME").datebox("enable");
	}
	else{
		$("#"+toolbarId+" #START_TIME").datebox("disable");
		$("#"+toolbarId+" #END_TIME").datebox("disable");
		$("#"+toolbarId+" #START_TIME").datebox("setValue",'');
		$("#"+toolbarId+" #END_TIME").datebox("setValue",'');
	}
}