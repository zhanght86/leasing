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
 * 添加功能 项目类型选择
 * @author 杨雪
 * 
 * @param val
 * @param flag
 * @return
 */
function choseTypeExp(val,flag) {
	if(flag == "project"){//项目类型
		if(val != null){
			return $("#PROJECT_TYPE").val(val);
		}else {
			return $("#PROJECT_TYPE").val('');
		}
	}
	
}

/**
 * 修改功能 项目类型选择
 * @param val
 * @param flag
 * @return
 */
function closeDailogExp_(val,flag) {
	 if(flag == "project"){
		if(val != null){
			return $("#PROJECT_TYPE2").val(val);
		}else {
			return $("#PROJECT_TYPE2").val('');
		}
	}
}

/**
 * 保存从业经历
 * @author 杨雪
 * date 2013-09-07
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
		url:_basePath+"/base/suppliersInfo/SuppMainRelation!doInsertWoekExp.action",
		data:"param="+getFromData("#addExperience_"),
//		data:from,
		type:"post",
		dataType:"json",
		success:function(data){
		  if(data.flag==false){
			  //消息弹出： 添加失败消息
			  $.messager.show({
				  title:'从业经历添加',
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
				  title:'从业经历添加',
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
* 保存从业经历修改
* @author 杨雪
* date 2013-09-07
* @return
*/
function toUpdateExperience(){
	if(!$("#upateExperience_").form('validate')){
		return false;
	}
	
	$.ajax({
		url:_basePath+"/base/suppliersInfo/SuppMainRelation!doUpdateExperence.action",
		data:"param="+getFromData("#upateExperience_"),
		type:"post",
		dataType:"json",
		success:function(data){
		  if(data.flag==false){
			  $.messager.show({
				  title:'修改从业经历',
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
				  title:'修改从业经历',
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
 * @author 杨雪
 * date 2013-09-07
 * @return
 */
function getUpdateExperience_(){
	var row = $("#Experience1").datagrid('getSelected');
	console.debug(row.ID);
	if (row){
		if(row.ID != undefined && row.ID != ""){
			$('#opDialogUp').empty();
			
			
			jQuery.get(_basePath+'/base/suppliersInfo/SuppMainRelation!getUpdateExperience.action?ID='+row.ID+"&date="+new Date(),function(html){
				$("#opDialogUp").html(html);
				$("#updateExperience_").show();
				$('#updateExperience_').dialog();
			});
			
//			$('#opDialogUp').load(_basePath+'/customers/CustMainRelation!getUpdateExperience.action?WOR_ID='+row.WOR_ID+"&date="+new Date());
		}else {
			alert("请选择一条从业经历数据");
		}
	}else{
		alert("请选择一条从业经历数据");
	}
}

/**
 * 查看 获取数据
 * @author 杨杰
 * date 2014年5月26日 13:18:44
 * @return
 */
function getLookExperience_(){
	var row = $("#Experience1").datagrid('getSelected');
	if (row.ID){
		if(row.ID != undefined && row.ID != ""){
			$('#opDialogUplook').empty();
			
			
			jQuery.get(_basePath+'/base/suppliersInfo/SuppMainRelation!getUpdateExperience.action?ID='+row.ID+"&date="+new Date(),function(html){
				$("#opDialogUplook").html("<script> $('#opDialogUplook input').attr('disabled',true);$('#opDialogUplook textarea').attr('disabled',true);$('#opDialogUplook select').attr('disabled',true);  </script>" + html);
				$("#lookExperience_").show();
				$('#lookExperience_').dialog();
			});
			
//			$('#opDialogUp').load(_basePath+'/customers/CustMainRelation!getUpdateExperience.action?WOR_ID='+row.WOR_ID+"&date="+new Date());
		}else {
			alert("请选择一条从业经历数据");
		}
	}else{
		alert("请选择一条从业经历数据");
	}
}


/**
 * 删除
 * @author 杨雪
 * date 2013-09-07
 */
function delExperience(){
	if(confirm("确认删除？")){
		var row = $("#Experience1").datagrid('getSelected');
		if (row.ID){
			jQuery.ajax({
				url: _basePath+"/base/suppliersInfo/SuppMainRelation!delWoekExp.action",
				data: "ID="+row.ID,
				dataType:"json",
				success : function(data) {
							if (data.flag == true) {
								 $.messager.show({
									  title:'从业经历删除',
									  msg:data.msg,
									  showType:'show'
								   });
								 
								  $('#Experience1').datagrid('load', {
										"param" : getFromData("#Exp")
									});
							} else {
								$.messager.show({
									  title:'从业经历删除',
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
		}else {
			alert("请选择要删除的从业经历信息");
		}
	}
}