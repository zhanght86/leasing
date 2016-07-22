function conditionsSelect(){ 
	$('#pageTable').datagrid('load', {"param":getFromData("#my_form")});
}

function clearSelect() {
	$("#my_form").form('clear');
}

function lightRemind(val,row,index) {
	if(index==0) $("#timeLimit").html('<font color="red">'+row.REMINDER_DAYS+'</font>');
	//起租未抵押 ---红灯//到期后未解押-----红灯
	if(row.QZAPP_STATUS==1&&!row.ID || row.REMINDER_GAP_DAYS<=0&&row.STATE!=1) {
		return "<div class='icon-red'>&nbsp;</div>";
	//到期前若干天未解押---黄灯
	} else if(row.REMINDER_GAP_DAYS<=row.REMINDER_DAYS&&row.STATE!=1) {
		return "<img src='../img/yellow_light.gif'/>";
	} else {
		return "<div class='icon-green'>&nbsp;</div>";
	}
}

//初始化”任务人“弹窗
function taskMan(row) {
	$("#task input[name=ID]").val(row.ID);
	$("#task input[name=EQU_ID]").val(row.EQU_ID);
	$("#task input[name=TASK_MAN_ID]").val(row.TASK_MAN);
	$("#task").dialog("open");
	$("#taskTable").datagrid({
		url:_basePath+"/lmrm/LeaseMortgage!loadTaskMans.action",
		rownumbers:true,//左侧自动显示行数
		singleSelect:true,		
		fitColumns:true,
		columns:[[
		          	{field:'TASK_MAN',align:'center',width:80, formatter:fmtTask1},
		          	{field:'TASK_MAN_NAME',align:'center',title:'任务人',width:80}
		         ]],
		onClickRow: function(rowIndex, rowData) {
			$("#taskForm :radio:eq("+rowIndex+")").attr("checked", true);
		}
	});
}
//任务人弹窗、table、第一列formatter
function fmtTask1(val,row) {
	if($("#task input[name=TASK_MAN_ID]").val()==val)
		return "<input type='radio' name='TASK_MAN' checked/>";
	else return "<input type='radio' name='TASK_MAN'/>";
}
//任务人弹窗，确定按钮
function taskOk() {
	var data = $("#taskForm").serialize();
	var row = $("#taskTable").datagrid("getSelected");
	if(!row) return;
	$.ajax({
		url: _basePath+'/lmrm/LeaseMortgage!assignTaskMan.action',
		data: data+'&row='+JSON.stringify(row),
		dataType: 'json',
		success: function(json) {
			if(json.flag) {
				$("#task").dialog("close");
				$.messager.show({
				  title:'提示信息',
				  msg:'成功分配任务',
				  showType:'show'
			   });
				$("#pageTable").datagrid('reload');
			}
		}
	});
}

function state(val,row) {
	if( val == '0'){
		return "已抵押";
	}else if(val == '1'){
		return "已解押";
	}else{
		return "未抵押";
	}
}

function setHouserDel(val,row) {
	var str = '';
	if(row.STATE == '2'){
		str += "<a href='#' onclick='addMort(" + JSON.stringify(row) + ")'>抵押</a> ";
	}else if(row.STATE == '0'){
		str += "<a href='#' onclick='updateMort(" + JSON.stringify(row) + ")'>修改抵押</a> " +
				"| <a href='#' onclick='showMort(" + JSON.stringify(row) + ")'>查看抵押</a>" +
				"| <a href='#' onclick='demort(" + JSON.stringify(row) + ")'>解押</a>";
	} else if(row.STATE == '1') {
		str += "<a href='#' onclick='showMort(" + JSON.stringify(row) + ")'>查看抵押</a>" +
				"| <a href='#' onclick='showDemort(" + JSON.stringify(row) + ")'>查看解押</a>";
	}
	str += "| <a href='#' onclick='taskMan(" + JSON.stringify(row) + ")'>任务人</a>";
	return str;
}

function addMort(row){
	$("#mortButtons a:contains('保存')").attr('onclick', 'toSaveMort()')
	$("#mortForm input[name=EQU_ID]").val(row.EQU_ID);
	$("#downDiv").hide();
	$("#mort").dialog('open');
}

function demort(row){
	$("#demortForm input[name=EQU_ID]").val(row.EQU_ID);
	$("#demortForm input[name=ID]").val(row.ID);
	$("#downDiv1").hide();
	$("#demort").dialog('open');
}

function toSaveDemort() {
	$("#demortForm").form('submit',{
		url:_basePath+"/lmrm/LeaseMortgage!saveDemort.action",
		success:function(data){
		  data = $.parseJSON(data);
		  if(data.flag==false){
			  $.messager.show({
				  title:'提示信息',
				  msg:'解押失败',
				  showType:'show'
			   });
		  }else{
			  $.messager.show({
				  title:'提示信息',
				  msg:'解押成功',
				  showType:'show'
			   });
		  }
		  $("#demort").dialog('close');
		  $('#pageTable').datagrid('reload');
		}
	});
}

function toSaveMort() {
	$("#mortForm").form('submit',{
		url:_basePath+"/lmrm/LeaseMortgage!saveMort.action",
		success:function(data){
		  data = $.parseJSON(data);
		  if(data.flag==false){
			  $.messager.show({
				  title:'提示信息',
				  msg:'添加抵押失败',
				  showType:'show'
			   });
		  }else{
			  $.messager.show({
				  title:'提示信息',
				  msg:'添加抵押成功',
				  showType:'show'
			   });
		  }
		   $("#mort").dialog('close');
		   $('#pageTable').datagrid('reload');
		}
	});
}

function closeDailogExp(id) {
	$("#"+id).dialog("close");
}

function updateMort(row){
	$("#mortButtons a:contains('保存')").attr('onclick', 'toUpdateMort()');
	
	jQuery.ajax({
	   type: "POST",
	   dataType:"json",
	   url: _basePath+"/lmrm/LeaseMortgage!showMort.action?ID="+row.ID,
	   success: function(json){
	   		$("#mortForm input[name=ID]").val(row.ID);
	   		$("#mortForm input[name=EQU_ID]").val(json.data.FPE_ID);
	   		$("#mortForm .easyui-datebox:first").datebox('setValue', json.data.MORTGAGE_START_DATE);
	   		$("#mortForm .easyui-datebox:eq(1)").datebox('setValue', json.data.OVER_DATE);
	   		var path = json.data.MORT_FILE_PATH;
	   		if(!path)
	   			$("#downDiv").hide();
	   		else {
	   			$("#downDiv").show();
	   			$("#downDiv a").attr('filePath',path);
	   			$("#downSpan").html(path.substr(path.lastIndexOf("/")+1));
	   		}
	   		$("#mortForm input[name=TRANSACTOR]").val(json.data.TRANSACTOR);
	   		$("#mort").dialog("open");
	   }
	});
}

$(function(){
	$("#mort").dialog({
		onClose: function() {
			$("#mortForm").form('clear');
			$("#downDiv").hide();
			$("#mortButtons a:contains('保存')").show();
			$("#mortForm :file").show();
			$("#mortForm :input").attr('disabled', false);
			$("#mortForm .easyui-datebox").datebox({disabled: false});
		}
	});
	$("#demort").dialog({
		onClose: function() {
			$("#demortForm").form('clear');
			$("#downDiv1").hide();
			$("#demortButtons a:contains('保存')").show();
			$("#demortForm :file").show();
			$("#demortForm :input").attr('disabled', false);
			$("#demortForm .easyui-datebox").datebox({disabled: false});
		}
	});
});

function download1(obj) {
	var filePath = $(obj).attr("filePath");
	window.location.href=_basePath+"/lmrm/LeaseMortgage!downloadFile.action?filePath="+filePath;
}

function toUpdateMort() {
	var url = _basePath+"/lmrm/LeaseMortgage!updateMort.action";
	if(!$("#mortForm :file").val()) {
		jQuery.ajax({
			type: 'POST',
			url: url,
			data: $("#mortForm").serialize(),
			dataType: 'json',
			success: function(json) {
				updateMortCb(json);
			}
		});
	} else {
		$("#mortForm").form('submit',{
		url: url,
		success: function(data) {
			var json = $.parseJSON(data);
			updateMortCb(json);
		}
	});
	}
}

function updateMortCb(json) {
		  if(json.flag==false){
			  $.messager.show({
				  title:'提示信息',
				  msg:'修改抵押失败',
				  showType:'show'
			   });
		  }else{
			  $.messager.show({
				  title:'提示信息',
				  msg:'修改抵押成功',
				  showType:'show'
			   });
		  }
		  $("#mort").dialog('close');
		  $('#pageTable').datagrid('reload');
}

function showMort(row) {
	$("#mortButtons a:contains('保存')").hide();
	$("#mortForm :file").hide();
	$("#mortForm :input").attr('disabled', true);
	jQuery.ajax({
	   type: "POST",
	   dataType:"json",
	   url: _basePath+"/lmrm/LeaseMortgage!showMort.action?ID="+row.ID,
	   success: function(json){
	   		$("#mortForm .easyui-datebox:first").datebox('setValue', json.data.MORTGAGE_START_DATE);
	   		$("#mortForm .easyui-datebox:eq(1)").datebox('setValue', json.data.OVER_DATE);
	   		$("#mortForm .easyui-datebox").datebox({
	   			disabled: true
	   		});
	   		var path = json.data.MORT_FILE_PATH;
	   		if(path) {
	   			$("#downDiv").show();
	   			$("#downDiv a").attr('filePath',path);
	   			$("#downSpan").html(path.substr(path.lastIndexOf("/")+1));
	   		}
	   		$("#mortForm input[name=TRANSACTOR]").val(json.data.TRANSACTOR);
	   		$("#mort").dialog("open");
	   }
	});
}


function showDemort(row) {
	$("#demortButtons a:contains('保存')").hide();
	$("#demortForm :file").hide();
	$("#demortForm :input").attr('disabled', true);
	jQuery.ajax({
	   type: "POST",
	   dataType:"json",
	   url: _basePath+"/lmrm/LeaseMortgage!showMort.action?ID="+row.ID,
	   success: function(json){
	   		$("#demortForm .easyui-datebox").datebox('setValue', json.data.MORTGAGE_END_DATE);
	   		$("#demortForm .easyui-datebox").datebox({
	   			disabled: true
	   		});
	   		var path = json.data.DEMORT_FILE_PATH;
	   		if(path) {
	   			$("#downDiv1").show();
	   			$("#downDiv1 a").attr('filePath',path);
	   			$("#downSpan1").html(path.substr(path.lastIndexOf("/")+1));
	   		}
	   		$("#demort").dialog("open");
	   }
	});
}
