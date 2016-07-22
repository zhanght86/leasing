/**
 * 打开dialog
 * @param div
 * @return
 */
function toRelationTeam(div){
	$("#"+div).dialog('open');
	$("#teamCompany").form('clear');
	$("#team_client_id").val($("#client_id").val());
}

function toDescComTeam(){
	$("#teamDesc").dialog('open');
	$.ajax({
		url: _basePath+'/customers/CustMainRelation!toTeamDesc.action',
		data: 'CLIENT_ID='+$("#client_id").val(),
		dataType: 'html',
		success: function(html) {
			$("#openTeamDesc").html(html);
		}
	});
}

function doAddTeamDesc() {
	$("#teamDescForm").form('submit',{
		url:_basePath+"/customers/CustMainRelation!doTeamDesc.action",
		onSubmit: function(param) {
			param.CLIENT_ID = $("#client_id").val();
		},
		success:function(data){
		  data = $.parseJSON(data);
		  if(data.flag==false){
			  $.messager.show({
				  title:'提示',
				  msg:'修改团队描述失败',
				  showType:'show'
			   });
			  $("#teamDesc").dialog('close');
			  
		  }else{
			  $.messager.show({
				  title:'提示',
				  msg:'修改团队描述成功',
				  showType:'show'
			   });
			  $("#teamDesc").dialog('close');
			  
		  }
		}
	});
}

/**
 * 关闭dialog
 * @return
 */
function closeDailogTeam(div){
	$("#"+div).dialog('close');
}

/***
 * 添加 企业团队信息选择
 * @param val
 * @param tag
 * @return
 */
function choseRelation1(val, tag){
	if(tag == "duty"){
		if(val != null){
			return $("#TYPE_1").val(val);
		}else {
			return $("#TYPE_1").val('');
		}
	}else if(tag == "card_type"){
		if(val != null){
			return $("#ID_CARD_TYPE").val(val);
		}else {
			return $("#ID_CARD_TYPE").val('');
		}
	}else if(tag == "edu"){
		if(val != null){
			return $("#DEGREE_EDU").val(val);
		}else {
			return $("#DEGREE_EDU").val('');
		}
	}else if(tag == "nation"){
		if(val != null){
			return $("#NATION").val(val);
		}else {
			return $("#NATION").val('');
		}
	}else if(tag == "illegal"){
		if(val != null){
			return $("#IS_ILLEGAL").val(val);
		}else {
			return $("#IS_ILLEGAL").val('');
		}
	}
}

/***
 * 添加 企业团队信息选择
 * @param val
 * @param tag
 * @return
 */
function choseRelation1_(val, tag){
	if(tag == "duty"){
		if(val != null){
			return $("#TYPE2_").val(val);
		}else {
			return $("#TYPE2_").val('');
		}
	}else if(tag == "card_type"){
		if(val != null){
			return $("#ID_CARD_TYPE2").val(val);
		}else {
			return $("#ID_CARD_TYPE2").val('');
		}
	}else if(tag == "edu"){
		if(val != null){
			return $("#DEGREE_EDU2").val(val);
		}else {
			return $("#DEGREE_EDU2").val('');
		}
	}else if(tag == "nation"){
		if(val != null){
			return $("#NATION2").val(val);
		}else {
			return $("#NATION2").val('');
		}
	}else if(tag == "illegal"){
		if(val != null){
			return $("#IS_ILLEGAL2").val(val);
		}else {
			return $("#IS_ILLEGAL2").val('');
		}
	}
}



/**
 * 保存 企业团队
 * @return
 */
function toSaveTeam(){
	$("#teamCompany").form('submit',{
		url:_basePath+"/customers/CustMainRelation!doInsertCpmpanyTeam.action",
		success:function(data){
		  data = $.parseJSON(data);
		  if(data.flag==false){
			  //提示框： 添加企业团队失败
			  $.messager.show({
				  title:'添加企业团队',
				  msg:"企业团队添加失败",
				  showType:'show'
			   });
			  $("#toAddEn").dialog('close');
			  $("#comteam").datagrid('load',{
				  "param":getFromData("#team")
			  });
		  }else{
			  //提示框： 添加企业团队成功
			  $.messager.show({
				  title:'添加企业团队',
				  msg:"企业团队添加成功",
				  showType:'show'
			   });
			  $("#toAddEn").dialog('close');
			  $("#comteam").datagrid('load',{
				  "param":getFromData("#team")
			  });
		  }
		}
	});
}

/**
 * 修改 获取企业团队
 * @author 杨雪
 * date 2013-09-07
 * @return
 */
function getUpdateComTeam(){
	var row = $("#comteam").datagrid('getSelected');
	if (row){
		if(row.COMTEAM_ID != undefined || row.COMTEAM_ID != ""){
			$("#toUpdateComteam").dialog('open');
			jQuery.get(_basePath+'/customers/CustMainRelation!getComTeam.action?COMTEAM_ID='+row.COMTEAM_ID+"&date="+new Date(),function(html){
				$("#openComTeam").html(html);
			});
		}else {
			alert("请选择企业团队数据");
		}
	}else {
		alert("请选择企业团队数据");
	}
}


/**
* 修改企业团队
* @author 杨雪
* date 2013-09-09
* @return
*/
function toUpdateTeam(){
	$("#updateTeamCompany").form('submit',{
		url:_basePath+"/customers/CustMainRelation!doUpdateComTeam.action",
		onSubmit: function(){
			return $(this).form('validate');
		},
		success:function(data){
		  data = $.parseJSON(data);
		  if(data.flag==false){
			  $.messager.show({
				  title:'修改企业团队',
				  msg:'修改企业团队失败',
				  showType:'show'
			   });
			  $("#toUpdateComteam").dialog('close');
			  $("#updateTeamCompany").form('clear');
			  $("#comteam").datagrid('load',{
				  "param":getFromData("#team")
			  });
		  }else{
			  $.messager.show({
				  title:'修改企业团队',
				  msg:'修改企业团队成功',
				  showType:'show'
			   });
			  $("#toUpdateComteam").dialog('close');
			  $("#updateTeamCompany").form('clear');
			  $("#comteam").datagrid('load',{
				  "param":getFromData("#team")
			  });
		  }
		}
	});
}

/**
 * 删除
 */
function doDelComTeam(){
	if(confirm("确认删除？")){
		var row = $("#comteam").datagrid('getSelected');
		if (row){
			jQuery.ajax({
				url: _basePath+"/customers/CustMainRelation!doDelComTeam.action",
				data: "CLIENT_ID="+row.CLIENT_ID+"&COMTEAM_ID="+row.COMTEAM_ID,
				dataType:"json",
				success : function(data) {
							if (data.flag == true) {
								 $.messager.show({
									  title:'删除企业团队',
									  msg:"删除企业团队成功",
									  showType:'show'
								   });
								 $("#comteam").datagrid('load',{
									  "param":getFromData("#team")
								  });
							} else {
								$.messager.show({
									  title:'删除企业团队',
									  msg:"删除企业团队失败",
									  showType:'show'
								   });
								$("#comteam").datagrid('load',{
									  "param":getFromData("#team")
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

function download1(obj) {
	var filePath = $(obj).attr("filePath");
	window.location.href=_basePath+"/lmrm/LeaseMortgage!downloadFile.action?filePath="+filePath;
}