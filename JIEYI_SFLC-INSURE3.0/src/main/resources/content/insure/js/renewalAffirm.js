function clear_(){
	 $('#queryForm').form('clear');
}
function selectPage(checkbox){
	var tt = checkbox;
	if(tt.checked){
		$('#pageTable').datagrid("selectAll");
	}else{
		$('#pageTable').datagrid("unselectAll");
	}
}
function approval(parm){
	var data = $('#pageTable').datagrid("getSelections");
	var PRO_CODES = new Array();
	$(data).each(function(){
		PRO_CODES.push(this.ID);
	})
	//
	var jsonData = $("#queryForm").serialize();
	var jsonData = $("#queryForm").serialize()+"&PRO_CODES="+PRO_CODES.toString();
	jQuery.ajax({
	   type: "POST",
	   dataType:"json",
	   async:false,
	   url: _basePath+"/insure/Insure!updateStatus.action",
	   data: jsonData+"&status_s=已续&status_e=已录入待确认",
	   success: function(msg){
		   	jQuery.ajax({
			   type: "POST",
			   dataType:"json",
			   async:false,
			   url: _basePath+"/insure/Insure!updateStatus_renewal.action",
			   data: jsonData,
			   success: function(msg){
			   	alert(msg.data);
				window.location.href=_basePath+"/insure/Insure!renewalAffirm.action";
			   }
			});
	   }
	});
	
}

function revocation(parm){
	var data = $('#pageTable').datagrid("getSelections");
	var PRO_CODES = new Array();
	$(data).each(function(){
		PRO_CODES.push(this.ID);
	})
	//
	var jsonData = $("#queryForm").serialize();
	var jsonData = $("#queryForm").serialize()+"&PRO_CODES="+PRO_CODES.toString();
	//保险录入的时候点击取消时候修改状态
	jQuery.ajax({
	   type: "POST",
	   dataType:"json",
	   async:false,
	   url: _basePath+"/insure/Insure!updateStatus.action",
	   data: jsonData+"&status_e=已录入待确认&status_s=待录保单信息",
	   success: function(msg){
	   	alert(msg.data);
	   	window.location.href=_basePath+"/insure/Insure!renewalAffirm.action";
	   }
	});
	
}
function se(){
	$("#exportEXCEL").linkbutton('enable');
	var url_=_basePath+"/insure/Insure!renewalAffirmListing.action";
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