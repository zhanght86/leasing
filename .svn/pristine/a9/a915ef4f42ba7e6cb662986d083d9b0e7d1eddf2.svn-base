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
	   data: jsonData+"&status_s=已录入待确认&status_e=待录保单信息",
	   success: function(msg){
	   	alert(msg.data);
		window.location.href=_basePath+"/insure/Insure!renewalEntering.action";
	   }
	});
}

function revocation(parm){
	var data = $('#pageTable').datagrid("getSelections");
	var PRO_CODES = new Array();
	$(data).each(function(){
		PRO_CODES.push(this.PROJ_EQUIP_ID);
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
	   data: jsonData+"&status_e=待录保单信息&status_s=续保",
	   success: function(msg){
	   	window.location.href=_basePath+"/insure/Insure!renewalEntering.action";
	   }
	});
	
}
function se(){
	$("#exportEXCEL").linkbutton('enable');
	var url_=_basePath+"/insure/Insure!renewalEnteringListing.action";
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
function compure(value,rowData,rowIndex){
	return "<a href='javascript:void(0)' onclick=informationEntry_show('"+rowIndex+"')>维护续保单</a>";
}
function informationEntry_show(rowIndex){
	var row  = $('#pageTable').datagrid('getData');
	row = row.rows[rowIndex];
	//初始化参数
	$("#ID_").val(row.ID);
	$("#PROJ_NO_").val(row.PROJ_NO);
	$("input[name='INSUR_NO']").val(row.INSUR_NO);
	//$("input[name='INSUR_DATE']").val(row.INSUR_DATE)
	$("#INSUR_DATE").datebox('setValue',row.INSUR_DATE);
	$("input[name='INVOICE_NO']").val(row.INVOICE_NO);
	$("#INVOICE_DATE").datebox('setValue',row.INVOICE_DATE);
	$("#INSUR_START_DATE").datebox('setValue',row.INSUR_START_DATE);
	$("input[name='INSUR_TERM']").val(row.INSUR_TERM);
	$("#dialog").show();
	$('#dialog').dialog({
		modal:true
	});  
}

function informationEntry(){
	var INSUR_DATE = $("input[name='INSUR_DATE']").val();
	var INVOICE_DATE = $("input[name='INVOICE_DATE']").val();
	var INSUR_START_DATE = $("input[name='INSUR_START_DATE']").val();
	var parm = $("#informationEntry").serialize()+"&INSUR_DATE="+INSUR_DATE+"&INVOICE_DATE="+INVOICE_DATE+"&INSUR_START_DATE="+INSUR_START_DATE;
	$('#informationEntryFile').form('submit', {   
	    url:_basePath+"/insure/Insure!informationEntry.action?"+parm,   
	    onSubmit: function(){   
	    },   
	    success:function(msg){   
			msg = eval('(' + msg + ')');
	      	alert(msg.data);
			$('#dialog').dialog('close');
	    }   
	},'clear');  


}
