function newOne(){
	top.addTab("添加经销商", _basePath+"/base/suppliers/Suppliers!addSupPage.action");
}

function update(row){
	//var row = $('#pageTable').datagrid('getSelected');
	if (row){
		top.addTab("经销商信息修改",_basePath+"/base/suppliers/Suppliers!toUpdateSuppliersMain.action?SUP_ID="+row);
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

function show(row){
	if(row){
		top.addTab("下属经销商",_basePath+"/base/suppliers/Suppliers!toUnderSuppliers.action?SUP_ID="+row);
	}
}

function start(sup_id){
	if(confirm("确定要发起申请吗？")){
		jQuery.ajax({
			url: _basePath+'/base/suppliers/Suppliers!startSuppliersByJbpm.action',
			data: 'SUP_ID='+sup_id,
			dataType:'json',
			success: function(data){
				alert(data.msg);
			}
		});
	}
}

/**
 * 根据父节点获取子节点信息
 * @param val
 * @return
 */
function getChildArea(val,childId){
	
	jQuery.ajax({
		url:_basePath+"/base/suppliers/Suppliers!getAddress.action?PARENT_ID="+val,
		type:"post",
		dataType:"json",
		success:function(json){
				//将本行的输入框初始化
				$("#"+childId).each(function (){
					//初始化
					if ($(this).is("select")){
						$(this).empty();
						$(this).append($("<option>").val("").text("--请选择--"));
						
					}
				});
				for(var i=0;i<json.address.length;i++){
					$("#"+childId).append($("<option value='"+json.address[i].ID+"'>"+json.address[i].NAME+"</option>"));				
				}
		}
	});
}

/**
 * 根据父节点获取子节点信息
 * @param val
 * @return
 */
function getChildArea2(val,childId1,childId2){
	
	jQuery.ajax({
		url:_basePath+"/base/suppliers/Suppliers!getAddress.action?PARENT_ID="+val,
		type:"post",
		dataType:"json",
		success:function(json){
				
				//将本行的输入框初始化
				$("#"+childId1).each(function (){
					//初始化
					if ($(this).is("select")){
						$(this).empty();
						$(this).append($("<option>").val("").text("--请选择--"));
						
					}
				});
				//将本行的输入框初始化
				$("#"+childId2).each(function (){
					//初始化
					if ($(this).is("select")){
						$(this).empty();
						$(this).append($("<option>").val("").text("--请选择--"));
						
					}
				});
				for(var i=0;i<json.address.length;i++){
					$("#"+childId1).append($("<option value='"+json.address[i].ID+"'>"+json.address[i].NAME+"</option>"));				
				}
		}
	});
}

function checkSupName(input,SUP_ID){
	jQuery.ajax({
		url: _basePath+'/base/suppliers/Suppliers!checkSupName.action',
		data: $(input).attr('name')+'='+$(input).val()+'&SUP_ID='+SUP_ID,
		type: 'post',
		dataType: 'json',
		success: function(data){
			if(!data.flag){
				$(input).parent().find('font').css('color','red').text(data.msg);
//				$.messager.alert('警告',data.msg);
				$(input).val('');
			}else{
				$(input).parent().find('font').css('color','green').text('可用');
			}
		}
	});
}

function checkRequired(){
	var flag = true;
	$('[title]:input').each(function(){
		if($(this).val().trim()==''||$(this).val().trim()==null){
			var title = $(this).attr('title');
			alert('【'+title+'】不能为空！');
			$(this).focus();
			flag = false;
			return flag;
		}
	});
	return flag;
}

function setAreaManagerId(){
	var a_id = $('#AREA_MANAGER option:selected').attr('_id');
	$('#AREA_MANAGER_ID').val(a_id);
}

function selectProv1(b){
	var a = $(b).find("option:selected").attr('val');
    var tr=$(b).parent().parent();
    console.info(tr);
	jQuery.ajax({
		url:_basePath+"/base/suppliers/Suppliers!getCityMess.action?PARENT_ID="+a,
		type:"post", 
		dataType:"json",
		success:function(data){
			//将本行的输入框初始化
			$(tr).find(".cityMess").each(function (){
				//初始化
				if ($(this).is("select"))
				{
					$(this).empty();
					$(this).append($("<option>").val("").text("--请选择--"));
				}
			});
			var CITY_ID = $(tr).find("select[name='BRANCH_BANK_CITY']");
			console.info(CITY_ID);
			for (var i=0; i<data.provs.length;i++) {
				$(CITY_ID).append($("<option>").val(data.provs[i].ID).text(data.provs[i].NAME));
			}
		}
	});
}
