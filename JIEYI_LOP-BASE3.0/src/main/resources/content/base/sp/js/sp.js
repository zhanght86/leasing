function newOne(){
	top.addTab("添加SP", _basePath+"/base/sp/Sp!addSupPage.action");
}

function update(row){
	//var row = $('#pageTable').datagrid('getSelected');
	if (row){
		top.addTab("SP信息修改",_basePath+"/base/sp/Sp!toUpdateSpMain.action?SUP_ID="+row);
    }
}

function spSuppliers(row){
	//var row = $('#pageTable').datagrid('getSelected');
	if (row){
		top.addTab("下属经销商",_basePath+"/base/sp/Sp!spSuppliers.action?SUP_ID="+row);
    }
}


function addSuppliers(row){
	if(row){
		top.addTab("添加下属经销商",_basePath+"/base/suppliers/Suppliers!addSupPage.action?SP_ID="+row);
	}
}

function del(row){
	//var row = $('#pageTable').datagrid('getSelected');
	if (row){
		jQuery.ajax({
			url: _basePath+"/base/suppliers/Suppliers!checkDel.action",
			data: "SUP_ID="+row,
			dataType: "json",
			success: function(data){
				if(!data.falg){
					$.messager.alert("提示",'关联其他数据，不可删除！');
				}else{
					$.messager.confirm("确认","确定要删除该SP吗？",function(r){
						if(r){
							jQuery.ajax({
								url: _basePath+"/base/sp/Sp!delSuppliers.action",
								data: "SUP_ID="+row,
								dataType:"json",
								success: function(res){
									if(res.flag==true){
										$.messager.alert("提示",res.msg);
										$('#pageTable').datagrid('reload');
								   }
								   else{
									   $.messager.alert("提示",res.msg);
									   alert("操作失败请重试！");
								   }
								}
									 
							 });
						}
					});
				}
			}
		});
	}
}

function updateSupp(row){
	//var row = $('#pageTable').datagrid('getSelected');
	if (row){
		top.addTab("经销商信息修改",_basePath+"/base/suppliers/Suppliers!toUpdateSuppliersMain.action?SUP_ID="+row);
    }
}

function delSupp(row){
	//var row = $('#pageTable').datagrid('getSelected');
	if (row){
		jQuery.ajax({
			url: _basePath+"/base/suppliers/Suppliers!checkDel.action",
			data: "SUP_ID="+row,
			dataType: "json",
			success: function(data){
				if(!data.falg){
					$.messager.alert("提示","此经销商关联其他数据，不可删除！");
				}else{
					console.debug(data);
					$.messager.confirm("确认","确定要删除该经销商吗？",function(r){
						if(r){
							jQuery.ajax({
								url: _basePath+"/base/suppliers/Suppliers!delSuppliers.action",
								data: "SUP_ID="+row,
								dataType:"json",
								success: function(res){
									if(res.flag==true){
										$.messager.alert("提示",res.msg);
										$('#pageTable').datagrid('reload');
									}
									else{
										$.messager.alert("提示",res.msg);
										alert("操作失败请重试！");
									}
								}
							
							});
						}
					});
					 
				}
			}
		});
		
	}
}

function select(row,sup_id){
	if(row){
		if(confirm("确定要关联此经销商吗？")){
			jQuery.ajax({
				url: _basePath+"/base/sp/Sp!relevanceSupp.action",
				data: "SUP_ID="+row+"&SP_ID="+sup_id,
				dataType: "json",
				success: function(res){
					if(res.flag==true){
						$.messager.alert("提示",res.msg);
						$('#pageTable').datagrid('reload');
					}else{
						$.messager.alert("提示",res.msg);
						alert("操作失败请重试！");
					}
				}
			
			});
		}
	}
}

function delSpSupp(row,sup_id){
	if(row){
		if(confirm("确定要取消关联此经销商吗？")){
			jQuery.ajax({
				url: _basePath+"/base/sp/Sp!delSpSupp.action",
				data: "SUP_ID="+row+"&SP_ID="+sup_id,
				dataType: "json",
				success: function(res){
					if(res.flag==true){
						$.messager.alert("提示",res.msg);
						$('#pageTable').datagrid('reload');
					}else{
						$.messager.alert("提示",res.msg);
						alert("操作失败请重试！");
					}
				}
			});
		}
	}
}

function getChildArea(val,childId1,childId2){
	jQuery.ajax({
		url: _basePath+'/base/suppliers/Suppliers!getAddress.action?PARENT_ID='+val,
		type: 'post',
		dataType: 'json',
		success: function (json){
			//将本行的输入框初始化
			$("#"+childId1).each(function (){
				//初始化
				if ($(this).is("select")){
					$(this).empty();
					$(this).append($("<option>").val("").text("--请选择--"));
					
				}
			});
			//将本行的输入框初始化
			if(childId2!=null){
				$("#"+childId2).each(function (){
					//初始化
					if ($(this).is("select")){
						$(this).empty();
						$(this).append($("<option>").val("").text("--请选择--"));
						
					}
				});
			}
			
			for(var i=0;i<json.address.length;i++){
				$("#"+childId1).append($("<option value='"+json.address[i].ID+"'>"+json.address[i].NAME+"</option>"));				
			}
		}
	});
}

function checkSupName(input,SUP_ID){
	jQuery.ajax({
		url: _basePath+'/base/sp/Sp!checkSupName.action',
		data: $(input).attr('name')+'='+$(input).val()+'&SUP_ID='+SUP_ID,
		type: 'post',
		dataType: 'json',
		success: function(data){
			if(!data.flag){
				$(input).parent().find('font').css('color','red').text(data.msg);
				$(input).val('');
			}else{
				$(input).parent().find('font').css('color','green').text('可用');
			}
		}
	});
}

