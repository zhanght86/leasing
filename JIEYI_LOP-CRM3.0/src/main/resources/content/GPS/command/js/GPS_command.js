$(document).ready(function(){
	$('#pageTable').datagrid({
		url:_basePath+"/GPS/GPSCommand/GPSCommand!showMgGpsCommand.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:true,
		fit : true,
		pageSize:20,
		toolbar:'#pageForm',
		 frozenColumns:[[
		       {field:'aaa',title:'操作',width:200,align:'center',formatter:function compure(value, rowData) {
//		    	   var approval = "<a href='javascript:void(0);' onclick=commandSet('"+rowData.GPS_CODE+"')>发送命令</a>";
//		    	   if(rowData.CODE_ == 1 || rowData.CODE_ == 2 || rowData.CODE_ == 3 || rowData.CODE_ == 4 ){
//		    		   approval = approval + " | <a href='javascript:void(0);' onclick='removeGPS("+rowData.GPS_ID+")'>解除GPS</a>&nbsp;";
//		    	   }else{
//		    		   approval = approval + " | <a href='javascript:void(0);' onclick='installGPS("+rowData.GPS_ID+","+rowData.EQUIPMENT_ID+",\"installGPSDiv\",\"installGPSForm\")'>安装GPS</a>&nbsp;";
//		    	   }
//		    	   return approval;
		    	}
		       }
		 ]],
		 columns:[[
		         {field:'COMMAND_STATUS_NAME',title:'GPS命令结果',width:120},
		         {field:'GPS_TYPE_NAME',title:'GPS设备类型',width:100}, 
		         {field:'GPS_CODE',title:'GPS型号',width:80}, 
		         {field:'COMMAND_MODEL_NAME',title:'GPS命令模式',width:80}, 
		         {field:'WORK_TIME',title:'工作时间',width:100}, 
		         {field:'INTERVAL_TIME',title:'回传数据频率',width:100,align:'center'},
		         {field:'GPS_FIRST_DATE',title:'第一次回传时间',width:120,align:'center'},
		         {field:'COMMAND_TYPE_NAME',title:'GPS命令类型',width:100,align:'center'}, 
		         {field:'LOCK_LEVEL_NAME',title:'GPS锁车级别',width:100}, 
		          
		 ]]
	});
});
/**
 * 搜索
 * @return
 */
function seach(){
	var COMMAND_STATUS=$("#COMMAND_STATUS").val();
	var GPS_TYPE=$("#GPS_TYPE").val();
	var GPS_CODE=$("#GPS_CODE").val();
	var COMMAND_MODEL=$("#COMMAND_MODEL").val();
	var COMMAND_TYPE=$("#COMMAND_TYPE").val();
	var LOCK_LEVEL=$("#LOCK_LEVEL").val();
	
	$('#pageTable').datagrid('load', {"COMMAND_STATUS":COMMAND_STATUS,"GPS_TYPE":GPS_TYPE,"GPS_CODE":GPS_CODE,"COMMAND_MODEL":COMMAND_MODEL,"COMMAND_TYPE":COMMAND_TYPE,"LOCK_LEVEL":LOCK_LEVEL});
}
/**
 * 清空按钮
 * @return
 */
function emptyData(){
	$('#pageForm').form('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}

