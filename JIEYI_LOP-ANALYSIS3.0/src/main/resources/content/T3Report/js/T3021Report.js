
function searchPage() {
	$('#pageTable').datagrid('load', {});
}

var url;
function addPage() {
	$('#pageDialog').dialog('open').dialog('setTitle', '添加'+reportName);
	$("#pageDialog").panel("move",{top:$(document).scrollTop() + ($(window).height()-250) * 0.5,left:$(document).scrollTop() + ($(window).height()-250) * 0.5}); 
	$('#pageAdd').form('clear');
	$('#page_CLIENT_ID').val($('#clientId').val());
	url = '../fusionChart/ReportCredit!doSaveData.action?TABLE_NAME='+tableName+'&TYPE='+tableType;
	
}
function savePage() {
	var DATA_TIME_YEAR = $("#DATA_TIME_YEAR").val();
	var DATA_TIME_MONTH = $("#DATA_TIME_MONTH").val();
	
	  if(DATA_TIME_YEAR != null && DATA_TIME_YEAR != "" && DATA_TIME_MONTH != null && DATA_TIME_MONTH != ""){
		  $("#DATA_TIME").val(DATA_TIME_YEAR+"-"+DATA_TIME_MONTH);
	  }else{
		  $.messager.alert("提示","请选择数据时点!");
		  return;
	  }
	$('#pageAdd').form('submit', {
		url : url,
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#pageDialog').dialog('close'); // close the dialog
				$('#pageTable').datagrid('load', {});
			} else {
				$.messager.alert("提示","保存失败请重试！");
			}
		},
		error : function(e) {
			$.messager.alert(e.message);
		}
	});
}
function delPage() {

	var row = $("#pageTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示", "请选择要删除的"+reportName);
		return true;
	}
	if (confirm("确定要删除此记录？")) {
		jQuery.ajax( {
			url : "../fusionChart/ReportCredit!doDeleteData.action?TABLE_NAME="+tableName,
			data : "ID=" + row.ID,
			dataType : "json",
			success : function(date) {
				if (date.flag == true) {
					$('#pageTable').datagrid('load', {});
				} else {
					$.messager.alert("提示","删除失败请重试！");
				}
			},
			error : function(e) {
				$.messager.alert(e.message);
			}
		});
	}
}



function openUpdatePage() {
	$('#pageUpdate').form('clear');
	var row = $("#pageTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示","请选择要修改的"+reportName);
		return true;
	}
	$('#pageUpdateDialog').dialog('open').dialog('setTitle', '修改'+reportName);
	$("#pageUpdateDialog").panel("move",{top:$(document).scrollTop() + ($(window).height()-250) * 0.5,left:$(document).scrollTop() + ($(window).height()-250) * 0.5}); 
	
	$('#UP_ID').val(row.ID);
	var DATA_TIME = row.DATA_TM;
	var times = DATA_TIME.split("-");
	$('#UP_DATA_TIME_YEAR').val(times[0]);
	$('#UP_DATA_TIME_MONTH').val(times[1]);
	$('#UP_COLUMN1').val(row.COLUMN1);  
	$('#UP_COLUMN2').val(row.COLUMN2);
	$('#UP_COLUMN3').val(row.COLUMN3);  
	$('#UP_COLUMN4').val(row.COLUMN4);
	$('#UP_COLUMN5').val(row.COLUMN5);
	$('#UP_COLUMN6').val(row.COLUMN6);
	$('#UP_COLUMN7').val(row.COLUMN7);
	$('#UP_COLUMN8').val(row.COLUMN8);
	$('#UP_COLUMN9').val(row.COLUMN9);
	$('#UP_COLUMN10').val(row.COLUMN10);
    
}

function updatePage() {
	var UP_DATA_TIME_YEAR = $("#UP_DATA_TIME_YEAR").val();
	var UP_DATA_TIME_MONTH = $("#UP_DATA_TIME_MONTH").val();
	
	  if(UP_DATA_TIME_YEAR != null && UP_DATA_TIME_YEAR != "" && UP_DATA_TIME_MONTH != null && UP_DATA_TIME_MONTH != ""){
		  $("#UP_DATA_TIME").val(UP_DATA_TIME_YEAR+"-"+UP_DATA_TIME_MONTH);
	  }else{
		  $.messager.alert("提示","请选择数据时点!");
		  return;
	  }
	$('#pageUpdate').form('submit', {
		url : '../fusionChart/ReportCredit!doUpdateData.action?TABLE_NAME='+tableName,
		success : function(date) {
			date = $.parseJSON(date);
			if (date.flag) {
				$('#pageUpdateDialog').dialog('close'); // close the dialog
				$('#pageTable').datagrid('load', {
					"param" : getFromData("#pForm")
				});
			} else {
				$.messager.alert("提示","修改失败请重试！");
			}
		},
		error : function(e) {
			alert(e.message);
		}
	});
}

