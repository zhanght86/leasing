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
	var data = $('#pageTable').datagrid("getSelections");
	var PRO_CODES = new Array();
	var flag = true;
	$(data).each(function(){
		PRO_CODES.push(this.PROJ_EQUIP_ID);
		if(this.STATUS!="待录保单信息"){
			flag = false;
			$.messager.show({
				title:'操作错误提示',
				msg:'您选中撤销的数据状态必须为"待录保单信息"',
				showType:'show'
			});
		}
	})
	if(!flag){
		return false;
	}
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
	   	window.location.href=_basePath+"/insure/Insure!renewalRemind.action";
	   }
	});
	
}
function se(){
	var url_=_basePath+"/insure/Insure!insureShowListing.action";
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
	var url_ = _basePath+"/insure/Insure!insureCheckExportExcel.action?"+jsonData+"&status_=起保";
	window.location.href=url_;
}