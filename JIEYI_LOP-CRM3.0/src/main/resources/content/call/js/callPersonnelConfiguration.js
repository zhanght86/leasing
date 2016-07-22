function addPdfTemplateFormLabel(divId,formId,type){
	$("#"+divId).show();
	$("#"+divId).dialog({   
		 title:type,
     	 modal:true,
		 resizable:true,
		 buttons: [{
            text:'保 存',
            iconCls:'icon-save',
            handler:function(){
				$('#'+formId).form({   
					url:_basePath+"/call/PersonnelConfiguration!doAddPersonnelConfiguration.action?_datetime="+new Date().getTime(),
					success:function(json){ 
						json = jQuery.parseJSON(json);
						if(json.flag){
							alert("保存成功");
							$("#"+divId).dialog('close');
							$('#pageTable').datagrid('load', {"param":JSON.stringify(json.data)});
						}else{
							alert(json.msg);
						}
					}   
				});   
				$('#'+formId).submit(); 
            }
        },{
            text:'关 闭',
			iconCls:'icon-cancel',
            handler:function(){
               $("#"+divId).dialog('close');
            }
        }]
	}); 
	$("#"+formId).form('clear');
}

function updatePdfTemplateFormLabel(ID,divId,formId,type){
	$("#"+divId).show();
	$("#"+divId).dialog({ 
		 title:type,  
     	 modal:true,
		 resizable:true,
		 buttons: [{
            text:'保 存',
            iconCls:'icon-save',
            handler:function(){
				$('#'+formId).form('submit', {  
					url:_basePath+'/call/PersonnelConfiguration!doUpdatePersonnelConfiguration.action?ID='+ID+"&_datetime="+new Date().getTime(),
					success:function(json){ 
						json = jQuery.parseJSON(json);
						if(json.flag){
							alert("修改成功");
							$("#"+divId).dialog('close');
							$('#pageTable').datagrid('load', {"param":JSON.stringify(json.data)});
						}else{
							alert(json.msg);
						}
					}   
				});   
            }
        },{
            text:'关 闭',
			iconCls:'icon-cancel',
            handler:function(){
               $("#"+divId).dialog('close');
            }
        }]
	}); 
	$("#"+formId).form('clear');
	jQuery.ajax({
    		url : _basePath+"/call/PersonnelConfiguration!toUpdatePersonnelConfiguration.action?ID="+ID+"&_datetime="+new Date().getTime(),
    		dataType : "json",
    		success : function(json){
				$("#EDITOR_BINDING_PHONE1").val(json.data.BINDING_PHONE);
				$("#EDITOR_SEAT_NUMBER1").val(json.data.SEAT_NUMBER);
				$("#EDITOR_USER_NAME1").val(json.data.USER_NAME);
				$("#EDITOR_USER_PASSWORD1").val(json.data.USER_PASSWORD);
				$("#EDITOR_USER_CODE1").val(json.data.USER_CODE);
//				校验时启用
//				$("#"+formId).form('validate');
    		}
    	});
}

function viewDetails(ID,divId,formId){
	$("#"+divId).show();
	$("#"+divId).dialog({ 
		title:"详情",  
		modal:true,
		resizable:true,
		buttons: [{
			text:'关 闭',
			iconCls:'icon-cancel',
			handler:function(){
			$("#PdfFormLabelShow").dialog('close');
		}
		}]
	}); 
	$("#"+formId).form('clear');
	jQuery.ajax({
		url : _basePath+"/call/PersonnelConfiguration!toShowPersonnelConfigurationDetails.action?ID="+ID+"&_datetime="+new Date().getTime(),
		dataType : "json",
		success : function(json){
			$("#SHOW_BINDING_PHONE").val(json.data.BINDING_PHONE);
			$("#SHOW_USER_CODE").val(json.data.USER_CODE);
			$("#SHOW_SEAT_NUMBER").val(json.data.SEAT_NUMBER);
			$("#SHOW_USER_NAME").val(json.data.USER_NAME);
			$("#SHOW_USER_PASSWORD").val(json.data.USER_PASSWORD);
		}
	});
}

function conditionsSelect(){
	$('#pageTable').datagrid('load', {"param":getFromData("#fromDate")});
}

function clearSelect(formId){
	$("#"+formId).form('clear');
}

function deletePdfFormLabel(ID,rowIndex){
	$.messager.confirm('提示框', '确定要删除吗?',function(flag){
		if(flag){
			jQuery.ajax({
				url : _basePath+"/call/PersonnelConfiguration!doDeletePersonnelConfiguration.action?ID="+ID+"&_datetime="+new Date().getTime(),
					dataType : "json",
					success : function(json){
					if(json.flag){
						$('#pageTable').datagrid('load');
					}else{
						alert(json.msg);
					}	
				}
			});
		}
	});
}

$(document).ready(function(){
	$("#fromDate").form('clear');
	$("#pageTable").datagrid({
		url:_basePath+"/call/PersonnelConfiguration!toMgPersonnelConfiguration.action",
        pagination:true,// 分页
        rownumbers:true,//行数
        singleSelect:true,//单选模式
		fitColumns:true,
		toolbar:'#pageForm',
        columns:[[
	        {field:'USER_NAME',align:'center',width:30,title:'用户名称'},
	        {field:'USER_CODE',align:'center',width:30,title:'用户编号'},
			{field:'BINDING_PHONE',align:'center',width:30,title:'绑定电话'},
            {field:'SEAT_NUMBER',align:'center',width:30,title:'席位号'},
            {field:'USER_PASSWORD',align:'center',width:30,title:'密码'},
			{field:'ID',align:'center',title: '操作',width:30,formatter:function(value,rowData,rowIndex){
                    return "<a href='javascript:void(0);' onclick='viewDetails("+rowData.ID+",\"PdfFormLabelShow\",\"showPdfFormLabel\")'>查看</a>&nbsp;|&nbsp;"+
						   "<a href='javascript:void(0);' onclick='updatePdfTemplateFormLabel("+rowData.ID+",\"PdfFormLabel1\",\"editorPdfFormLabel1\",\"修改\")'>修改</a>&nbsp;|&nbsp;"+ 
						   "<a href='javascript:void(0);' onclick='deletePdfFormLabel("+rowData.ID+","+rowIndex+")'>删除</a>";
            	}
			}
        ]]
    });
});
