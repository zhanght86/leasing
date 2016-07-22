$.ajaxSetup ({
   		 cache: false //关闭AJAX相应的缓存
});

/**
 * 打开dialog
 * @param div
 * @return
 */
function toExperience(div){
	$("#"+div).dialog('open');
	$("#addExperience_").form('clear');
	$("#EXP_SUP_ID").val($("#sup_id").val());
}

/**
 * 关闭dialog
 * @return
 */
function closeDailogExp(div){
	$("#"+div).dialog('close');
}

/**
 * 保存三年的销售情况
 * @author 陶言民
 * date 2015-08-31
 * @return
 */
function toSaveExperience(){
	if(!$("#addExperience_").form('validate')){
		return false;
	}
	
	var param = getFromData("#addExperience_");
	var from = $("#addExperience_").serialize();
//	alert(param) ;
	$.ajax({
		url:_basePath+"/base/tysales/Tysales!doInsertTysales.action",
		data:"param="+getFromData("#addExperience_"),
//		data:from,
		type:"post",
		dataType:"json",
		success:function(data){
		  if(data.flag==false){
			  //消息弹出： 添加失败消息
			  $.messager.show({
				  title:'经销商三年的销售情况添加',
				  msg:data.msg,
				  showType:'show'
			   });
			//关闭弹出框
			  $("#Experience_").dialog('close'); 
			  //刷新页面
			  $('#Experience1').datagrid('load', {
					"param" : getFromData("#Exp")
				});
		  }else{
			  //消息弹出： 添加成功消息
			  $.messager.show({
				  title:'经销商三年的销售情况添加',
				  msg:data.msg,
				  showType:'show'
			   });
			  $("#Experience_").dialog('close');//关闭弹出框
			//刷新页面
			  $('#Experience1').datagrid('load', {
					"param" : getFromData("#Exp")
				});
		  }
		}
	});
}

/**
* 保存三年的销售情况
* @author 陶言民
* date 2015-08-31
* @return
*/
function toUpdateExperience(){
	if(!$("#upateExperience_").form('validate')){
		return false;
	}
	$.ajax({
		url:_basePath+"/base/tysales/Tysales!doUpdateTysales.action",
		data:"param="+getFromData("#upateExperience_"),
		type:"post",
		dataType:"json",
		success:function(data){
		  if(data.flag==false){
			  $.messager.show({
				  title:'修改三年的销售情况',
				  msg:data.msg,
				  showType:'show'
			   });
			  $("#updateExperience_").dialog('close');
			  $("#upateExperience_").form('clear');
			  $('#Experience1').datagrid('load', {
					"param" : getFromData("#Exp")
				});
		  }else{
			  $.messager.show({
				  title:'修改三年的销售情况',
				  msg:data.msg,
				  showType:'show'
			   });
			  $("#updateExperience_").dialog('close');
			  $("#upateExperience_").form('clear');
			  $('#Experience1').datagrid('load', {
					"param" : getFromData("#Exp")
				});
		  }
		}
	});
}


/**
 * 修改 获取数据
 * @author 陶言民
 * date 2015-08-31
 * @return
 */
function getUpdateExperience_(){
	var row = $("#Experience1").datagrid('getSelected');
	if (row){
		console.debug(row.SS_ID);
		if(row.SS_ID != undefined && row.SS_ID != ""){
			$('#opDialogUp').empty();
			
			
			jQuery.get(_basePath+'/base/tysales/Tysales!getUpdateTysales.action?SS_ID='+row.SS_ID+"&date="+new Date(),function(html){
				$("#opDialogUp").html(html);
				$("#updateExperience_").show();
				$('#updateExperience_').dialog();
			});
		}else {
			alert("请选择一条三年的销售情况数据");
		}
	}else{
		alert("请选择一条三年的销售情况数据");
	}
}

/**
 * 查看 获取数据
 * @author 陶言民
 * date 2015年8月31日 13:18:44
 * @return
 */
function getLookExperience_(){
	var row = $("#Experience1").datagrid('getSelected');
	if (row){
		if(row.SS_ID != undefined && row.SS_ID != ""){
			$('#opDialogUplook').empty();
			
			
			jQuery.get(_basePath+'/base/tysales/Tysales!getUpdateTysales.action?SS_ID='+row.SS_ID+"&date="+new Date(),function(html){
				$("#opDialogUplook").html("<script> $('#opDialogUplook input').attr('disabled',true);$('#opDialogUplook textarea').attr('disabled',true);$('#opDialogUplook select').attr('disabled',true);  </script>" + html);
				$("#lookExperience_").show();
				$('#lookExperience_').dialog();
			});
			
//			$('#opDialogUp').load(_basePath+'/customers/CustMainRelation!getUpdateExperience.action?WOR_ID='+row.WOR_ID+"&date="+new Date());
		}else {
			alert("请选择一条三年的销售情况数据");
		}
	}else{
		alert("请选择一条三年的销售情况数据");
	}
}


/**
 * 删除
 * @author 陶言民
 * date 2015-08-31
 */
function delExperience(){
		var row = $("#Experience1").datagrid('getSelected');
		if (row){
			console.debug(row.SS_ID);
			if(row.SS_ID != undefined && row.SS_ID != ""){
			if(confirm("确认删除？")){
			jQuery.ajax({
				url: _basePath+"/base/tysales/Tysales!delTysales.action",
				data: "SS_ID="+row.SS_ID,
				dataType:"json",
				success : function(data) {
							if (data.flag == true) {
								 $.messager.show({
									  title:'三年的销售情况删除',
									  msg:data.msg,
									  showType:'show'
								   });
								 
								  $('#Experience1').datagrid('load', {
										"param" : getFromData("#Exp")
									});
							} else {
								$.messager.show({
									  title:'三年的销售情况删除',
									  msg:data.msg,
									  showType:'show'
								   });
								
								  $('#Experience1').datagrid('load', {
										"param" : getFromData("#Exp")
									});
							}
						},
				error: function(e){
					alert(e.message);
				}
			});
			}
			}else {
				alert("请选择要删除的三年的销售情况信息");
			}
		}else {
			alert("请选择要删除的三年的销售情况信息");
		}
}