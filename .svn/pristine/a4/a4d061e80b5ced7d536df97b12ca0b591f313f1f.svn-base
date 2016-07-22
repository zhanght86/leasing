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

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true}
	if ($('#pageTable').datagrid('validateRow', editIndex)){
		$('#pageTable').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickRow(index){
	if (editIndex != index){
		if (endEditing()){
			$('#pageTable').datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			editIndex = index;
		} else {
			$('#pageTable').datagrid('selectRow', editIndex);
		}
	}
}
function importEXCEL(){
	$("#dialog").show();
	$('#dialog').dialog({
		    modal:true
		});  
}
function approval(parm){
	if($("#check2")[0].checked){
		$.messager.show({
			title:'操作错误提示',
			msg:'保险确认不能数据全选',
			showType:'show'
		});
		return false;
	}
	//var data = $('#pageTable').datagrid("getSelections");
	var data = $('#pageTable').datagrid('getChanges');
	if(data.length==0){
		$.messager.show({
			title:'操作错误提示',
			msg:'请选择要操作的行',
			showType:'show'
		});
		return false;
	}
	var PRO_CODES = new Array();
	$(data).each(function(){
		if(this.INSUR_NO!=null&&this.INSUR_NO!=""){//如果选中的行保单号没有就不能起保
			var data = {
				PROJ_NO:this.PRO_CODE,
				EQUIP_SN:this.WHOLE_ENGINE_CODE,
				INSUR_NO:this.INSUR_NO,
				INSUR_DATE:this.INSUR_DATE,
				INVOICE_NO:this.INVOICE_NO,
				INVOICE_DATE:this.INVOICE_DATE,
				INSUR_START_DATE:this.DELIVER_DATE,
				INSUR_START_DATE:this.DELIVER_DATE,
				INSUR_TERM:this.TOTAL_PERIODS,
			}
			PRO_CODES.push(data);
		}
	})
	if(PRO_CODES.length==0){
		$.messager.show({
			title:'操作错误提示',
			msg:'请填写至少一行数据的保单号',
			showType:'show'
		});
		return false;
	}
	var jsonData = JSON.stringify(PRO_CODES);
	var url_ = _basePath+"/insure/Insure!insureDbAffirmApproval.action";
	jQuery.ajax({
	   type: "POST",
	   dataType:"json",
	   url: url_,
	   data: "jsonData="+jsonData,
	   success: function(msg){
		alert (msg.data);
		window.location.href=_basePath+"/insure/Insure!insureDbAffirm.action"
	   }
	});
}

function revocation(parm){
		//alert(parm);
		var data = $('#pageTable').datagrid("getSelections");
		var PRO_CODES = new Array();
		$(data).each(function(){
			PRO_CODES.push(this.ID);
		})
		//
		var jsonData = $("#queryForm").serialize();
		var jsonData = $("#queryForm").serialize()+"&PRO_CODES="+PRO_CODES.toString()+"&parm="+parm+"&status_=预约";
		var url_ = _basePath+"/insure/Insure!insureCheckApproval.action";
		jQuery.ajax({
		   type: "POST",
		   dataType:"json",
		   url: url_,
		   data: jsonData,
		   success: function(msg){
			alert (msg.data);
			window.location.href=_basePath+"/insure/Insure!insureAffirm.action"
		   }
		});
}
function se(){
	//查询之前先同步db保险数据
	jQuery.ajax({
	   type: "POST",
	   dataType:"json",
	   url: _basePath+"/insure/Insure!refreshInsureDbAffirmListing.action",
	   data: "",
	   async:false,
	   success: function(msg){
	   }
	});
	
	//当url上面的type_=2时候为审核
	var url_=_basePath+"/insure/Insure!insureDbAffirmListing.action";
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
function exportEXCEL(){
	var data = $('#pageTable').datagrid("getSelections");
	var PRO_CODES = new Array();
	$(data).each(function(){
		PRO_CODES.push(this.ID);
	})
	//
	var jsonData = $("#queryForm").serialize();
	var jsonData = $("#queryForm").serialize()+"&PRO_CODES="+PRO_CODES.toString();
	var url_ = _basePath+"/insure/Insure!insureCheckExportExcel.action?"+jsonData+"&status_=预约&OPERATION_TYPE=yes";
	window.location.href=url_;
}