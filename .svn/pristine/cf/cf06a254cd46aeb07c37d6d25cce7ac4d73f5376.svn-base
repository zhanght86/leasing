/**
 * 打开dialog
 * @param div
 * @return
 */
function toRelationTeam(div){
	$("#"+div).dialog('open');
	$("#holderForm").form('clear');
	$("#holder_client_id").val($("#client_id").val());
}

/**
 * 关闭dialog
 * @return
 */
function closeDailogHolder(div){
	$("#"+div).dialog('close');
}


/**
 * 保存 企业股东及份额
 * @return
 */
function toSaveHolder(){
	$("#holderForm").form('submit',{
		url:_basePath+"/customers/CustMainRelation!doInsertHolder.action",
		success:function(data){
		  data = $.parseJSON(data);
		  if(data.flag==false){;
			  //提示框： 添加企业股东及份额
			  $.messager.show({
				  title:'添加企业股东及份额',
				  msg:"企业股东及份额添加失败",
				  showType:'show'
			   });
			  $("#holderL").dialog('close');
			  $("#holderLegal").datagrid('load',{
				  "param":getFromData("#holder")
			  });
		  }else{
			  //提示框： 添加企业股东及份额
			  $.messager.show({
				  title:'添加企业股东及份额',
				  msg:"企业股东及份额添加成功",
				  showType:'show'
			   });
			  $("#holderL").dialog('close');
			  $("#holderLegal").datagrid('load',{
				  "param":getFromData("#holder")
			  });
		  }
		}
	});
}

/**
 * 删除
 */
function doDelHolder(){
	if(confirm("确认删除？")){
		var row = $("#holderLegal").datagrid('getSelected');
		if (row){
			jQuery.ajax({
				url: _basePath+"/customers/CustMainRelation!doDelHolder.action",
				data: "CLIENT_ID="+row.CLIENT_ID+"&HOLDER_ID="+row.HOLDER_ID,
				dataType:"json",
				success : function(data) {
					if (data.flag == true) {
						 //提示框： 添加企业股东及份额
						  $.messager.show({
							  title:'删除企业股东及份额',
							  msg:"企业股东及份额删除成功",
							  showType:'show'
						   });
						  
						  $("#holderLegal").datagrid('load',{
							  "param":getFromData("#holder")
						  });
					} else {
						 //提示框： 添加企业股东及份额
						  $.messager.show({
							  title:'添加企业股东及份额',
							  msg:"企业股东及份额添加失败",
							  showType:'show'
						   });
						  
						  $("#holderLegal").datagrid('load',{
							  "param":getFromData("#holder")
						  });
					}
				},
				error: function(e){
					alert(e.message);
				}
			});
		}else {
			alert("请选择要删除的企业股东及份额数据");
		}
	}
}

/**
 * 修改 获取公司股东及份额
 * @author 杨雪
 * date 2013-09-07
 * @return
 */
function updateHolder(){
	var row = $("#holderLegal").datagrid('getSelected');
	if (row){
		if(row.HOLDER_ID != undefined || row.HOLDER_ID != ""){
			$("#holderL_").dialog('open');
			jQuery.get(_basePath+'/customers/CustMainRelation!getHolder.action?HOLDER_ID='+row.HOLDER_ID+"&date="+new Date(),function(html){
				$("#holderOpen").html(html);
			});
		}else {
			alert("请选择公司股东及份");
		}
	}else {
		alert("请选择公司股东及份");
	}
}

/**
* 修改公司股东及份额
* @author 杨雪
* date 2013-09-09
* @return
*/
function toUpdateHolder(){
	$("#holderUpdate").form('submit',{
		url:_basePath+"/customers/CustMainRelation!doUpdateHolder.action",
		onSubmit: function(){
			return $(this).form('validate');
		},
		success:function(data){
		  data = $.parseJSON(data);
		  if(data.flag==false){
			  $.messager.show({
				  title:'修改企业团队',
				  msg:'修改企业股东及份额失败',
				  showType:'show'
			   });
			  $("#holderL_").dialog('close');
			  $("#holderUpdate").form('clear');
			  $("#holderLegal").datagrid('load',{
				  "param":getFromData("#holder")
			  });
		  }else{
			  $.messager.show({
				  title:'修改企业团队',
				  msg:'修改企业股东及份额成功',
				  showType:'show'
			   });
			  $("#holderL_").dialog('close');
			  $("#holderUpdate").form('clear');
			  $("#holderLegal").datagrid('load',{
				  "param":getFromData("#holder")
			  });
		  }
		}
	});
}