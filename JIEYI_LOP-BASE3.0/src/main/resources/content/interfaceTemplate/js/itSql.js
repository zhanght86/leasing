	function clearInput(){
		$("#pageForm [name='NAME']").val('');
		$("#pageForm [name='NOTE']").val('');
		$("#pageForm [name='TYPE']").val('');
		$("#TYPE1").combobox('select','');
	}
	
	function getOperation(value,rowData,index){
		return  "<a href='javascript:void(0)' onclick='updateSql("+index+")'>修改</a>" +
				"&nbsp;|&nbsp;" +
				"<a href='javascript:void(0)' onclick='deleteSql("+index+")'>删除</a>" ;
	}
	
	//搜索
	function conditionsSelect(){
		$('#pageTable').datagrid('load', {
			NAME:$("#pageForm [name='NAME']").val(),
			NOTE:$("#pageForm [name='NOTE']").val(),
			TYPE:$("#pageForm [name='TYPE']").val()
		});
	}
	
	//添加页面
	function addSQL(){
		$("#dialogDiv").show();
		$("#dialogDiv").dialog({title:'添 加'});
		$("#dialogDiv").dialog('open');
	}
	
	//修改页面
	function updateSql(rowIndex){
		$('#pageTable').datagrid('selectRow',rowIndex);
		var rowData = $('#pageTable').datagrid('getSelected');
		$("#fromDate [name='ID']").val(rowData.ID);
		$("#fromDate [name='OLDNAME']").val(rowData.NAME);
		$("#fromDate [name='NAME']").val(rowData.NAME);
		$("#fromDate [name='NOTE']").val(rowData.NOTE);
		$("#fromDate [name='SQL']").val(rowData.SQL);
		$("#TYPE").combobox('select',rowData.TYPE);
		$("#dialogDiv").show();
		$("#dialogDiv").dialog({title:'修 改'});
		$("#dialogDiv").dialog('open');
	}
	
	//关闭
	function closeDialog(){
	    $('#dialogDiv').dialog('close');
	}
	
	$(document).ready(function(){ 
		$('#dialogDiv').dialog({
		    onClose:function(){
				$("#fromDate [name='NAME']").val('');
				$("#fromDate [name='ID']").val('');
				$("#fromDate [name='NOTE']").val('');
				$("#fromDate [name='SQL']").val('');
				$("#fromDate [name='OLDNAME']").val('');
				$("#TYPE").combobox('select','2');
		    }
		});
	});
	
	//添加and修改保存
	function save(){
		$('#btnbc_save').linkbutton('disable');
		var url = '';
		if($("#fromDate [name='ID']").val()){
			url = 'InterfaceTemplateSql!doUpdateSql.action?_dateTime='+new Date();
		}else{
			url = 'InterfaceTemplateSql!doAddSql.action?_dateTime='+new Date();
		}
		if($("#fromDate [name='NAME']").validatebox('isValid')){
			var NAME = $("#fromDate [name='NAME']").val();
			var url1 = '';
			var OLDNAME = '';
			if($("#fromDate [name='ID']").val()){
				url1 = 'InterfaceTemplateSql!checkNameUpdate.action?_dateTime='+new Date();
				OLDNAME = $("#fromDate [name='OLDNAME']").val();
			}else{
				url1 = 'InterfaceTemplateSql!checkNameAdd.action?_dateTime='+new Date();
			}
			jQuery.ajax({
		        url: url1,
		        data: "NAME="+NAME+"&OLDNAME="+OLDNAME,
		        type: "post",
		        dataType: "json",
		        success: function(json){
	            	if(json.flag){
						$.messager.alert('提示','该名称已被占用！','warning');
						$('#btnbc_save').linkbutton('enable');
					}else{
						jQuery.ajax({
					        url: url,
					        data: "param="+getFromData("#fromDate"),
					        type: "post",
					        dataType: "json",
					        success: function(json){
			                	if(json.flag){
									$('#pageTable').datagrid('load',{ID:json.data.ID});
									closeDialog();
								}else{
									$.messager.alert('提示','保存失败，请与管理员联系！','warning');
								}
								$('#btnbc_save').linkbutton('enable');
					        }
					    });
					}
		        }
		    });
		}else{
			$.messager.alert('提示','请填写必填项！','warning');
			$('#btnbc_save').linkbutton('enable');
		}
	}
	
	function deleteSql(rowIndex){
		$('#pageTable').datagrid('selectRow',rowIndex);
		var rowData = $('#pageTable').datagrid('getSelected');
		$.messager.confirm('提 示', '确定删除【'+rowData.NAME+'】吗?', function(r){
			if(r){
				jQuery.ajax({
					async: true,
			        url: "InterfaceTemplateSql!deleteSql.action",
			        data: "ID=" + rowData.ID + "&NAME=" + rowData.NAME + "&_dateTime=" + new Date(),
			        type: "post",
			        dataType: "json",
			        success: function(json){
			            if (json.flag) {
			            	$('#pageTable').datagrid('load');
			            }else{
							$.messager.alert('提示','删除失败！','warning');
						}
			        },
					error: function(e) { 
						$.messager.alert('提示','删除失败！','warning');
					} 
			    })
			}
		})
	}
