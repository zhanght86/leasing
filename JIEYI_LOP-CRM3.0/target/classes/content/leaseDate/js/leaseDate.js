function clear_() {
	$('#queryForm').form('clear');
}
function se() {
	var content = {};
	
	$("#queryForm :input").each(
			function() {
				if ($(this).is(":checkbox,:radio")) {
					if ($(this).attr("checked")) {
						content[$(this).attr("name")] = $(this).val();
					}
				} else {
					if ($(this).is("select")) {
						content[$(this).attr("name")] = $(this).find(":selected").val();
					} else {
						content[$(this).attr("name")] = $.trim($(this).val());
					}
				}
			});	
	$('#pageTable').datagrid('load', content);

}

function toViewCust(row) {
	var value=row.CLIENT_ID;
	var type=row.CUST_TYPE;
	top.addTab("查看客户明细", _basePath
			+ "/customers/Customers!toViewCustInfoMain.action?CLIENT_ID=" + value
			+ "&TYPE=" + type + "&tab=view");

}

function showDetail(ID,PROJECT_ID,PAYLIST_CODE){
	top.addTab(PAYLIST_CODE+"还款计划明细",_basePath+"/pay/PayTask!toMgshowDetail.action?ID="+ID+"&PROJECT_ID="+PROJECT_ID);
}
