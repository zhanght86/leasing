function clear_(){
	 $('#queryForm').form('clear');
}
function se(){
	$("#exportEXCEL").linkbutton('enable');
	var url_=_basePath+"/buyBack/BuyBack!buyBackWarningListing.action";
	var jsonData = $("#queryForm").serialize();
	var jsonData = "?page="+$(".pagination-num").val()+"&rows="+$(".pagination-page-list  option:selected").val()+"&"+$("#queryForm").serialize();
	var parserUrl = _parserUrl(jsonData);
	jQuery.ajax({
	   type: "POST",
	   dataType:"json",
	   url: url_,
	   data: jsonData,
	   success: function(msg){
	     //alert( "Data Saved: ");
		 $('#pageTable').datagrid("loadData",msg);
	   }
	});
}

function getStatus(value,rowData,rowIndex){
	if(rowData.BUY_BACK_STATUS==0){
		return '预警';
	}else if(rowData.BUY_BACK_STATUS==null||rowData.BUY_BACK_STATUS==""){
		return '正常';
	}
}
