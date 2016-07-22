function openDialog(id){
	clear();
	$("#"+id).dialog("open") ;
}

function closeDialog(id){
	$("#"+id).dialog("close") ;
}

function clear(){
	$("#REMARK_ID").val('');
	$("#uploadFileId").val('');
}

function saveFile(){
	$("#PROJECT_TABLE_ID").val($("#PROJECT_TABLE_ID_HIDDEN").val());
	$("#addUploadFile_").form('submit',{
		url:_basePath+"/project/project!uploadFile.action",
		onSubmit: function(){
			return $(this).form('validate');
		},
		success:function(data){
		  data = $.parseJSON(data);
		  if(data.flag==false){
			  $.messager.show({
				  title:'提示信息',
				  msg:'添加附件失败',
				  showType:'show'
			   });
			  $("#uploadFile_").dialog('close');
			  $('#uploadFileGrid').datagrid('load');
		  }else{
			  $.messager.show({
				  title:'提示信息',
				  msg:'添加附件成功',
				  showType:'show'
			   });
			  $("#uploadFile_").dialog('close');
			  $('#uploadFileGrid').datagrid('load');
		  }
		}
	});
}	


function deleteFile(){
	var rows = $("#uploadFileGrid").datagrid('getSelections');
	if(rows.length>0){
		$.messager.confirm('提示信息','你确定要删除这些文件?',function(r){
				if (r){
				$.ajax({
					url:_basePath+"/project/project!deleteFile.action?ROWS="+ JSON.stringify(rows) ,
					dataType:"json" ,
					success:function(data){
						if(data.flag){
							$.messager.alert("提示信息","删除成功!","info",function(){
								$('#uploadFileGrid').datagrid('load');
							}) ;
							
						}else{
							$.messager.alert("提示信息","删除失败!") ;
						}
					}
				});
			}
		});
		 
	}else{
		 $.messager.alert('提示','请至少选择一条下载文件!');
	}	
}


function downloadFile(){
	var rows = $("#uploadFileGrid").datagrid('getSelections');
	if(rows.length>0){
		 window.location.href=_basePath+"/project/project!downloadFile.action?ROWS="+ JSON.stringify(rows) ;
	}else{
		 $.messager.alert('提示','请至少选择一条下载文件!');
	}	
}
