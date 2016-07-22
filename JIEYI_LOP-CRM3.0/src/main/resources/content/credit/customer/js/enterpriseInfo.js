/**
 * 打开dialog
 * @param div
 * @return
 */
function toRelation(div){
	$("#"+div).dialog('open');
	$("#enRelation").form('clear');
	$("#relation_client_id").val($("#client_id").val());
	$("#TYPE").val("2");
	$("#tab").val("2");
}

/**
 * 关闭dialog
 * @return
 */
function closeDailogRe(div){
	$("#"+div).dialog('close');
}


/**
 * 与客户关系选择
 * @param val
 * @return
 */
function choseRelation(val){
	if(val != null){
		return $("#LINK_RELATION2CUST").val(val);
	}else {
		return $("#LINK_RELATION2CUST").val('');
	}
}

/**
 * 修改 与客户关系
 * @param val
 * @return
 */
function choseRelation_(val){
	if(val != null){
		return $("#LINK_RELATION2CUST1").val(val);
	}else {
		return $("#LINK_RELATION2CUST1").val('');
	}
}

/**
 * 保存 	企业关联
 * @return
 */
function toSave(){
	$("#enRelation").form('submit',{
		url:_basePath+"/customers/Customers!doInsertLinl.action",
		success:function(data){
		  data = $.parseJSON(data);
		  if(data.flag==false){
			  //提示框： 添加企业关联失败
			  $.messager.show({
				  title:'添加企业关联',
				  msg:"企业关 联添加失败",
				  showType:'show'
			   });
			  //关闭弹出框
			  $("#relation").dialog('close');
			  //刷新页面
			  $("#businessRel").datagrid('load',{
				  "param":getFromData("#er")
			  });
		  }else{
			  //提示框： 添加企业关联成功
			  $.messager.show({
				  title:'添加企业关联',
				  msg:"企业关 联添加成功",
				  showType:'show'
			   });
			  $("#relation").dialog('close');
			  $("#businessRel").datagrid('load',{
				  "param":getFromData("#er")
			  });
		  }
		}
	});
}

/**
 * 修改 获取企业关联数据
 * @author 杨雪
 * date 2013-09-07
 * @return
 */
function updateRelation(){
	var row = $("#businessRel").datagrid('getSelected');
	if (row){		
		if(row.LINK_ID != undefined || row.LINK_ID != ""){
			$("#updateRelation").dialog('open');
			$.ajax({
				url:_basePath+'/customers/CustMainRelation!getCustRelation.action',
				data:"LINK_ID="+row.LINK_ID+"&date="+new Date(),
				type:"post",
				dataType:"json",
				success:function(json){
					if(json.flag){
						$("#openBusiness").html(json.data);
					}
				}
			});
		}else {
			alert("请选择企业关系数据");
		}
	}else {
		alert("请选择企业关系数据");
	}
}

/**
* 修改企业关联
* @author 杨雪
* date 2013-09-09
* @return
*/
function toUpdateBusiness(){
	$("#upRelation1").form('submit',{
		url:_basePath+"/customers/CustMainRelation!doUpdateBusiness.action",
		onSubmit: function(){
			return $(this).form('validate');
		},
		success:function(data){
		  data = $.parseJSON(data);
		  if(data.flag==false){
			  //提示框： 修改企业关联失败
			  $.messager.show({
				  title:'修改企业关联',
				  msg:data.msg,
				  showType:'show'
			   });
			  //关闭弹出框
			  $("#updateRelation").dialog('close');
			  //清空弹出框数据
			  $("#upRelation1").form('clear');
			  //重新加载
			  $("#businessRel").datagrid('load',{
				  "param":getFromData("#er")
			  });
		  }else{
			 //提示框： 修改企业关联成功
			  $.messager.show({
				  title:'修改企业关联',
				  msg:data.msg,
				  showType:'show'
			   });
			  //关闭弹出框
			  $("#updateRelation").dialog('close');
			 //清空弹出框数据
			  $("#upRelation1").form('clear');
			//重新加载
			  $("#businessRel").datagrid('load',{
				  "param":getFromData("#er")
			  });
		  }
		}
	});
}

/**
 * 删除 企业关联
 */
function delRelation(){
	if(confirm("确认删除？")){
		var row = $("#businessRel").datagrid('getSelected');
		if (row){
			jQuery.ajax({
				url: _basePath+"/customers/Customers!doDelRelation.action",
				data: "CLIENT_ID="+row.CLIENT_ID+"&LINK_ID="+row.LINK_ID,
				dataType:"json",
				success : function(data) {
							if (data.flag == true) {
								//提示框： 企业关联删除成功
								$.messager.show({ 
									  title:'删除企业关联',
									  msg:"删除企业关联成功",
									  showType:'show'
								   });
								//刷新页面
								 $("#businessRel").datagrid('load',{
									  "param":getFromData("#er")
								  });
							} else {
								//提示框： 企业关联删除失败
								$.messager.show({
									  title:'删除企业关联',
									  msg:"删除企业关联失败",
									  showType:'show'
								   });
								//刷新页面
								 $("#businessRel").datagrid('load',{
									  "param":getFromData("#er")
								  });
							}
						},
				error: function(e){
					alert(e.message);
				}
			});
		}else{
			alert("请选择要删除的企业团队信息");
		}
	}
}