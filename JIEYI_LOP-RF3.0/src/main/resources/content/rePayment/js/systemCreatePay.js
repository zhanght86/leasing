function calculate(){
    var _leaseTerm = getUrl_().TERM;
    var _payCountOfYear = 12/parseInt(getUrl_().PERIOD);
    var annualRate = formatNumber(parseFloat(getUrl_().INTEREST)/100,'0.000000');
    var date = $("#PAY_DATE").val();
    var pay_way = getUrl_().PAY_TYPE;
	if(pay_way==1){
		var residualPrincipal = $("#LOAN_AMOUNT").val();
	    var url_ = _basePath + "/pay/PayTask!quoteCalculateTest.action";
	    var jsonData = "_leaseTerm=" + _leaseTerm + "&_payCountOfYear=" + _payCountOfYear + "&annualRate=" + annualRate + "&date=" + date +
	    "&pay_way=" +
	    pay_way +
	    "&residualPrincipal=" +
	    residualPrincipal;
	    jQuery.ajax({
	        type: "POST",
	        dataType: "json",
	        url: url_,
	        data: jsonData,
	        success: function(msg){
	            $("#btnSaveA").linkbutton('enable');
	            $("#dataDiv").show();
	            $('#pageTable').datagrid();
	            var data = {
	                "flag": true,
	                "total": msg.data.ln.length,
	                "rows": msg.data.ln
	            }
	            $('#pageTable').datagrid("loadData", data);
	        }
	    });
	}
    
}
function btnSaveA() {
	var row = $('#pageTable').datagrid('getRows');
	var PAY_ID = $("#PAY_ID").val();
	var PROJECT_ID = $("#PROJECT_ID").val();
	jQuery.ajax({
	        type: "POST",
	        dataType: "json",
	        url: _basePath + "/rePayment/RePayment!systemCreatePaySave.action",
	        data: "PAY_ID="+PAY_ID+"&PROJECT_ID="+PROJECT_ID+"&row="+JSON.stringify(row),
	        success: function(msg){
	        	if(msg.flag=true){
	        		alert(msg.data);
	        	}
	        }
	    });

}
