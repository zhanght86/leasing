//$(document).ready(function(){

function conditionsSelect(){
	$('#pageTable').datagrid('load', {"param":getFromData("#fromDate")});
}

$(function(){
	$("#updatePdfDataConfigurationDiv").dialog("close");
	$("#fromDate").form('clear');
	$("#pageTable").datagrid({
		url:_basePath+"/pdfTemplate/PdfTemplateDataConfiguration!toMgPdfTemplateDataConfiguration.action",
        pagination:true,// 分页
        rownumbers:true,//行数
        singleSelect:true,//单选模式
		fitColumns:true,
		toolbar:'#pageForm',
        columns:[[
			{field:'compure',align:'center',width:10,title:'操作',formatter:function(value,row,rowIndex){
				return "<a href='javascript:void(0)' onclick='updatePZopen(" + JSON.stringify(row) + ")'>修改</a>|<a href='javascript:void(0)' onclick=deletePZ('"+row.ID+"')>删除</a>";
			}},
			{field:'TPM_TYPE',align:'center',width:10,title:'模版名称'},
            {field:'FORMLABEL',align:'center',width:10,title:'表单名称'},
            {field:'EXE_ORDER',align:'center',width:10,title:'执行顺序'},
            {field:'NOTE',align:'center',width:10,title:'备注'}
            
        ]]
    });
//	$("#EDITOR_TPM_BUSINESS_PLATE").combobox({   
//		onSelect:function(){
//			var TPF_ID = $(this).combobox("getValue");
//			jQuery.ajax({
//				url : _basePath+"/pdfTemplate/PdfTemplateDataConfiguration!selectExeOrder.action?TPF_ID="+encodeURI(TPF_ID)+"&_datetime="+new Date().getTime(),
//				dataType : "json",
//				success : function(json){
//					var temp = new Array();
//					var flag = true;
//					for(var i = 1; i <= 50; i++){
//						if(json.data != null && json.data.length > 0){
//							for(var j = 0; j < json.data.length; j++){
//								if(json.data[j].EXE_ORDER == i){
//									flag = false;
//								}
//							}
//							if(flag){
//								temp.push({"id":i,"text":i});
//							}
//							flag = true;
//						}else{
//							temp.push({"id":i,"text":i});
//						}
//					}
//					$('#EDITOR_EXE_ORDER').combobox({valueField:'id',textField:'text'});
//					$('#EDITOR_EXE_ORDER').combobox('loadData', temp);
//				}
//			});
//		}
//	});
	
//	$("#EDITOR_TPM_BUSINESS_PLATE1").combobox({   
//		onSelect:function(){
//			var TPF_ID = $(this).combobox("getValue");
//			jQuery.ajax({
//				url : _basePath+"/pdfTemplate/PdfTemplateDataConfiguration!selectExeOrder.action?TPF_ID="+encodeURI(TPF_ID)+"&_datetime="+new Date().getTime(),
//				dataType : "json",
//				success : function(json){
//					var temp = new Array();
//					var flag = true;
//					for(var i = 1; i <= 50; i++){
//						if(json.data != null && json.data.length > 0){
//							for(var j = 0; j < json.data.length; j++){
//								if(json.data[j].EXE_ORDER == i){
//									flag = false;
//								}
//							}
//							if(flag){
//								temp.push({"id":i,"text":i});
//							}
//							flag = true;
//						}else{
//							temp.push({"id":i,"text":i});
//						}
//					}
//					$('#EDITOR_EXE_ORDER1').combobox({valueField:'id',textField:'text'});
//					$('#EDITOR_EXE_ORDER1').combobox('loadData', temp);
//				}
//			});
//		}
//	});
	
});


function updatePZopen(param){
	$("#updatePdfDataConfigurationDiv").dialog('open');
	$("#updatePdfDataConfigurationForm").find("#EDITOR_TPM_BUSINESS_PLATE1").combobox('select',param.TPF_ID);
	$("#updatePdfDataConfigurationForm").find("#EDITOR_PDF_FORMLABEL").combobox('select',param.SQL_ID);
	$("#updatePdfDataConfigurationForm").find("input[name='UPDATE_ID']").val(param.ID);
	$("#updatePdfDataConfigurationForm").find("input[name='EDITOR_EXE_ORDER']").val(param.EXE_ORDER);
	$("#updatePdfDataConfigurationForm").find("[name='EDITOR_NOTE']").val(param.NOTE);
}

function updatePZ(){
	var EDITOR_TPM_BUSINESS_PLATE1=$("#EDITOR_TPM_BUSINESS_PLATE1").combobox('getValue');
	var EDITOR_PDF_FORMLABEL1=$("#EDITOR_PDF_FORMLABEL1").combobox('getValue');
	var EDITOR_EXE_ORDER1=$("#updatePdfDataConfigurationForm").find("[name=EDITOR_EXE_ORDER]").val();
	var EDITOR_NOTE1=$("#updatePdfDataConfigurationForm").find("[name=EDITOR_NOTE]").val();
	var ID=$("#updatePdfDataConfigurationForm").find("[name=UPDATE_ID]").val();
	$.ajax({
		type:'post',
		url:_basePath+"/pdfTemplate/PdfTemplateDataConfiguration!doUpdatePdfTemplateDataConfiguration.action",
		data:"ID="+ID+"&EDITOR_TPM_BUSINESS_PLATE1="+EDITOR_TPM_BUSINESS_PLATE1+"&EDITOR_PDF_FORMLABEL1="+
			 EDITOR_PDF_FORMLABEL1+"&EDITOR_EXE_ORDER1="+encodeURI(EDITOR_EXE_ORDER1)+"&EDITOR_NOTE1="+
			 encodeURI(EDITOR_NOTE1),
	    dataType:"json",
	    success:function(json){
			if(json.flag){
				$.messager.alert("提示","修改成功！");
				$("#updatePdfDataConfigurationDiv").dialog('close');
				$('#pageTable').datagrid('load');
			}else{
				$.messager.alert("提示","修改失败");
			}
		}
	});
}

function deletePZ(id){
	$.messager.confirm("提示","您确认要删除该条配置吗?",function(r){
		if(r){
			$.ajax({
				type:'post',
				url:_basePath+"/pdfTemplate/PdfTemplateDataConfiguration!deletePZ.action",
				data:"ID="+id,
				dataType:"json",
				success:function(json){
					if(json.flag){
						$.messager.alert("提示","删除成功!");
						$('#pageTable').datagrid('load');
					}else{
						$.messager.alert("提示","删除失败");
					}
				}
			});
		}
	});	
}



function clearSelect(formId){
	$("#"+formId).form('clear');
}

function addPdfTemplateDataConfiguration(divId,formId,type){
	$("#"+divId).show();
	$("#"+divId).dialog({   
		 title:type,
     	 modal:true,
		 resizable:true,
		 buttons: [{
			  id:"btnbc",
            text:'保 存',
            iconCls:'icon-save',
            handler:function(){
			  	$('#btnbc').linkbutton('disable');
				$('#'+formId).form({   
					url:_basePath+'/pdfTemplate/PdfTemplateDataConfiguration!doAddPdfTemplateDataConfiguration.action',  
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

function dataConfigurationView(ID,divId,formId){
	$("#"+divId).show();
	$("#"+divId).dialog({ 
		title:"配置详情",  
		modal:true,
		resizable:true,
		buttons: [{
			text:'关 闭',
			iconCls:'icon-cancel',
			handler:function(){
			$("#"+divId).dialog('close');
		}
		}]
	}); 
	$("#"+formId).form('clear');
	jQuery.ajax({
		url : _basePath+"/pdfTemplate/PdfTemplateDataConfiguration!toShowDataConfiguration.action?ID="+encodeURI(ID)+"&_datetime="+new Date().getTime(),
		dataType : "json",
		success : function(json){
			$("#SHOW_TPM_BUSINESS_PLATE").val(json.data.NAME + "—" + json.data.PDF_VERSION);
			$("#SHOW_PDF_FORMLABEL").val(json.data.FORMLABEL);
			$("#SHOW_EXE_ORDER").val(json.data.EXE_ORDER);
			$("#SHOW_NOTE").val(json.data.NOTE);
		}
	});
}

function deletePdfFormLabelDataConfiguration(ID){
	$.messager.confirm('提示框', '确定要删除吗?',function(flag){
		if(flag){
			jQuery.ajax({
				url : _basePath+"/pdfTemplate/PdfTemplateDataConfiguration!doDeletePdfTemplateDataConfiguration.action?ID="+encodeURI(ID)+"&_datetime="+new Date().getTime(),
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

function updatePdfTemplateDataConfiguration(ID,divId,formId,type){
	$("#"+divId).show();
	$("#"+divId).dialog({ 
		 title:type,  
     	 modal:true,
		 resizable:true,
		 buttons: [{
			  id:"btnbc",
            text:'保 存',
            iconCls:'icon-save',
            handler:function(){
			 	$('#btnbc').linkbutton('disable');
				$('#'+formId).form({   
					url:_basePath+'/pdfTemplate/PdfTemplateDataConfiguration!doUpdatePdfTemplateDataConfiguration.action?ID='+encodeURI(ID)+"&_datetime="+new Date().getTime(),
					success:function(json){ 
						json = jQuery.parseJSON(json);
						if(json.flag){
							alert("保存成功");
							$("#"+divId).dialog('close');
//							$('#pageTable').datagrid('load', {"param":JSON.stringify(json.data)});
							conditionsSelect();
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
	jQuery.ajax({
		url : _basePath+"/pdfTemplate/PdfTemplateDataConfiguration!toShowDataConfiguration.action?ID="+encodeURI(ID)+"&_datetime="+new Date().getTime(),
		dataType : "json",
		success : function(json){
			$('#EDITOR_TPM_BUSINESS_PLATE1').combobox('select', json.data.TPF_ID);
			$('#EDITOR_PDF_FORMLABEL1').combobox('select', json.data.SQL_ID);
//			$('#EDITOR_EXE_ORDER1').combobox('select', json.data.EXE_ORDER);
			$("#EDITOR_NOTE1").val(json.data.NOTE);
		}
	});
}