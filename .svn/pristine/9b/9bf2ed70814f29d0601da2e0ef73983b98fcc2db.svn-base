

$(function(){  
	//默认关闭添加修改窗口
	$("#addTaskDicDialog").dialog("close");
	$("#updateTaskDicDialog").dialog("close");
	$("#updateTaskID").dialog("close");
	
	//二级联动选择流程节点
    $('#TASK_ID').combobox({  
        editable:false,  
        onSelect:function(record){  
            //重新载入节点，并清空当前输入的值  
    	$('#NODE_NAME').combobox({  
                disabled:false,  
                url:_basePath+'/bpm/Task!getCoordinatesMapKeyByName.action?taskId='+encodeURI(record.value),  
                valueField:'NAME',  
                textField:'NAME'  
            }).combobox('clear');  
        }  
    });  
	//二级联动选择流程节点
    $('#UPDATE_TASK_ID').combobox({  
        editable:false,  
        onSelect:function(record){  
            //重新载入节点，并清空当前输入的值  
    	$('#UPDATE_NODE_NAME').combobox({  
                disabled:false,  
                url:_basePath+'/bpm/Task!getCoordinatesMapKeyByName.action?taskId='+encodeURI(record.value),  
                valueField:'NAME',  
                textField:'NAME'  
            }).combobox('clear');  
        }  
    });  
    //选择操作人刷新列表
    $('#USER_ID').combobox({  
        onSelect:function(record){  
		$("#addTaskDic").datagrid({
			url:_basePath+"/taskDictionary/TaskDictionary!doSelectTaskDic.action",
			queryParams:{"USER_ID":record.value}
		});
        }  
    });  
    
    
  //选择供应商
    $('#UPDATE_SUPPLIER_ID').combobox({  
        onSelect:function(record){
    		$('#UPDATE_SUPPLIER_NAME').val($('#UPDATE_SUPPLIER_NAME').val().replace(record.text,''));
    		$('#UPDATE_SUPPLIER_NAME').val($('#UPDATE_SUPPLIER_NAME').val().replace(',,',','));
    		$('#UPDATE_SUPPLIER_NAME').val($('#UPDATE_SUPPLIER_NAME').val()+","+record.text);
        },  
        onUnselect:function(record){
    		$('#UPDATE_SUPPLIER_NAME').val($('#UPDATE_SUPPLIER_NAME').val().replace(','+record.text,''));
        }
    }); 
    $('#SUPPLIER_ID').combobox({  
        onSelect:function(record){
			$('#SUPPLIER_NAME').val($('#SUPPLIER_NAME').val().replace(record.text,''));
    		$('#SUPPLIER_NAME').val($('#SUPPLIER_NAME').val().replace(',,',','));
    		$('#SUPPLIER_NAME').val($('#SUPPLIER_NAME').val()+","+record.text);
        },  
        onUnselect:function(record){
    		$('#SUPPLIER_NAME').val($('#SUPPLIER_NAME').val().replace(','+record.text,''));
        }
    }); 
    
    
    
});  

//关闭添加窗口
function closeAddTaskDic(){
	$("#addTaskDicDialog").dialog("close");
}

//打开修改窗口
function openUpdateTaskDic(row){
	$("#updateTaskDicDialog").dialog("open");
	$("#updateTaskDicForm").form('load',row);
	$('#UPDATE_TASK_ID').combobox('select',row.TASK_ID);
	$('#UPDATE_NODE_NAME').combobox('setValue',row.NODE_NAME);
	$('#UPDATE_SUPPLIER_NAME').val(row.SUP_NAME);
	$('#updateTask').linkbutton('enable'); 
}
//关闭修改窗口
function closeUpdateTaskDic(){
	$("#updateTaskDicDialog").dialog("close");
}

//打开流程变更窗口
function openUpdateTaskID(row){
	$("#updateTaskID").dialog("open");
}
//关闭流程变更窗口
function closeUpdateTaskID(){
	$("#updateTaskID").dialog("close");
}


//执行修改操心
function updateTaskDic(el){
	var TASK_ID=$("#UPDATE_TASK_ID").combobox('getValues');
	var NODE_NAMES = $('#UPDATE_NODE_NAME').combobox('getValues');
	var SUPPLIER_IDS = $('#UPDATE_SUPPLIER_NAME').val();
	var REMARK=$("#updateTaskDicDialog").find("[name=REMARK]").val();
	var USER_ID=$("#updateTaskDicDialog").find("[name=USER_ID]").val();
	$(el).linkbutton('disable'); 
	$.ajax({
		type:"post",
		url:_basePath+"/taskDictionary/TaskDictionary!doUpdateTaskDic.action",
		data:"&TASK_ID="+TASK_ID+"&NODE_NAMES="+NODE_NAMES+"&SUPPLIER_IDS="+SUPPLIER_IDS+"&REMARK="+REMARK+"&USER_ID="+USER_ID,
		dataType:"json",
		success:function(json){
			if(json.flag){
				$("#updateTaskDicDialog").dialog("close");
		 		$("#addTaskDic").datagrid({
					url:_basePath+"/taskDictionary/TaskDictionary!doSelectTaskDic.action",
					queryParams:{"USER_ID":USER_ID}
				});
			}else{
				$.messager.alert("提示信息",json.msg);
				$("#updateTaskDicDialog").dialog("close");
		 		$("#addTaskDic").datagrid({
					url:_basePath+"/taskDictionary/TaskDictionary!doSelectTaskDic.action",
					queryParams:{"USER_ID":USER_ID}
				});
			}
		}
	});
}


//流程变更
function updateTaskID(){
	var TASK_ID = $('#UPDATE_TASK_ID').combobox('getValues');
	var OLD_TASK_ID = $('#OLD_TASK_ID').combobox('getValues');
	$.ajax({
		type:"post",
		url:_basePath+"/taskDictionary/TaskDictionary!doUpdateTaskID.action",
		data:"&TASK_ID="+TASK_ID+"&OLD_TASK_ID="+OLD_TASK_ID,
		dataType:"json",
		success:function(json){
			if(json.flag){
				$("#updateTaskID").dialog("close");
				se();
			}else{
				$.messager.alert("提示信息",json.msg);
				$("#updateTaskID").dialog("close");
				se();
			}
		}
	});
}


//清空搜索条件
function clean(){
	$("#pageForm").find("[name=USER_NAME]").val("");
	$("#pageForm").find("[name=SUP_NAME]").val("");
	$("#pageForm").find("[name=NODE_NAME]").val("");
	$("#pageForm").find("[name=TASK_ID]").val("");
}

//打开添加窗口
function openTaskDicVM(){
	top.addTab("添加流程节点人员配置",_basePath+"/taskDictionary/TaskDictionary!toAddTaskDic.action");
}

//打开添加明细窗口
function openTaskDicDialog(){
	var USER_ID=$("#addTaskDicToolBar").find("[name=USER_ID]").val();
	if(USER_ID == ""){
		$.messager.alert("提示","请先选择操作人员再添加配置！");
	}else{
		//清空缓存数据
		$('#TASK_ID').combobox('setValue',"");
		$('#NODE_NAME').combobox({disabled:true}).combobox('clear');  
		$('#SUPPLIER_ID').combobox('setValue',"");
		$('#REMARK').val("");
		$("#addTaskDicDialog").dialog("open");
		$('#SUPPLIER_NAME').val("");
		$('#saveTask').linkbutton('enable'); 
	}
}

//执行添加操作
function addTaskDicInfo(el){
	var TASK_ID=$("#addTaskDicDialog").find("[name=TASK_ID]").val();
	var NODE_NAMES = $('#NODE_NAME').combobox('getValues');
	var SUPPLIER_IDS = $('#SUPPLIER_NAME').val();
	var REMARK=$("#addTaskDicDialog").find("[name=REMARK]").val();
	var USER_ID=$("#addTaskDicToolBar").find("[name=USER_ID]").val();
	$(el).linkbutton('disable'); 
	if(USER_ID==""){
		$.messager.alert("提示","请选择操作人姓名！");
		return;
	}
		$.ajax({
			type:"post",
			url:_basePath+"/taskDictionary/TaskDictionary!doAddTaskDic.action",
			data:"TASK_ID="+TASK_ID+"&NODE_NAMES="+NODE_NAMES+"&SUPPLIER_IDS="+SUPPLIER_IDS+"&REMARK="+REMARK+"&USER_ID="+USER_ID,
			dataType:"json",
			success:function(json){
				if(!json.flag){
					$.messager.alert("提示消息",json.msg);
					$("#addTaskDicDialog").dialog("close");
					$("#addTaskDic").datagrid({
						url:_basePath+"/taskDictionary/TaskDictionary!doSelectTaskDic.action",
						queryParams:{"USER_ID":USER_ID}
					});
				}else{
					//保存成功后刷新列表
					$("#addTaskDicDialog").dialog("close");
					$("#addTaskDic").datagrid({
						url:_basePath+"/taskDictionary/TaskDictionary!doSelectTaskDic.action",
						queryParams:{"USER_ID":USER_ID}
					});
				}
			}
		});
}


function setTaskDicTool(val,row){
	return "<a href='javascript:void(0);' onclick='openUpdateTaskDic(" + JSON.stringify(row) + ")'>修改</a>  |  <a href='javascript:void(0);' onclick='delTaskDicInfo(" + JSON.stringify(row) + ",1)'>删除</a> ";
}
//删除配置信息 
function delTaskDicInfo(row,taskFlag){
	$.messager.confirm("提示","您确认要删除该条数据吗？",function(flag){
		//taskFlag=0  全部删除  =1删除单条数据
		if(flag){
			var data = {
				"ID":row.ID,
				"USER_ID":row.USER_ID,
				"TASK_ID":row.TASK_ID,
				"NODE_NAME":row.NODE_NAME
			};
			$.ajax({
				type:"post",
				url:_basePath+"/taskDictionary/TaskDictionary!doDelTaskDic.action",
				data:data,
				dataType:"json",
				success:function(json){
				if(!json.flag){
					$.messager.alert("错误","删除失败，原因"+json.msg);
				}else{
					//刷新不同列表
					if(taskFlag == "1"){
						$("#addTaskDic").datagrid({
							url:_basePath+"/taskDictionary/TaskDictionary!doSelectTaskDic.action",
							queryParams:{"USER_ID":row.USER_ID}
						});
					}else{
						se();
					}
				}
			}
			});
		}
	});
}

function editTaskDic(row){
	top.addTab(row.NAME+"流程节点人员配置",_basePath+"/taskDictionary/TaskDictionary!toUpdateTaskDic.action?USER_ID="+row.USER_ID);
}

function setTitleTool(val, row) {
	return "<a href='#' title='"+val+"' >"+val+"</a>";
}
