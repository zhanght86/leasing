/**
 * 打开dialog
 * @param div
 * @return
 */
function toInversInfo(div){
	$("#"+div).dialog('open');
	$("#addInversInfo_").form('clear');
	$("#inversInfo_CLIENT_ID").val($("#client_id").val());
}

/**
 * 关闭dialog
 * @return
 */
function closeDailogInvers(div){
	$("#"+div).dialog('close');
}


/**
 * 添加功能
 * @author 杨雪
 * @date 2013-09-10
 * @param val
 * @param flag
 * @return
 */
function choseTypeInv(val,flag) {
	if(flag=="com_type"){//单位属性
		if(val != null){
			return $("#COMPANY_PROPERTY_").val(val);
		}else {
			return $("#COMPANY_PROPERTY").val('');
		}
	}else if(flag=="nation"){//民族
		if(val != null){
			return $("#NATION").val(val);
		}else {
			return $("#NATION").val('');
		}
	}else if(flag=="sex"){//性别
		if(val != null){
			return $("#SEX").val(val);
		}else {
			return $("#SEX").val('');
		}
	}
}

function choseTypeInv_(val,flag) {
	if(flag=="com_type"){
		if(val != null){
			return $("#COMPANY_PROPERTY2").val(val);
		}else {
			return $("#COMPANY_PROPERTY2").val('');
		}
	}else if(flag=="nation"){
		if(val != null){
			return $("#NATION2").val(val);
		}else {
			return $("#NATION2").val('');
		}
	}else if(flag=="sex"){
		if(val != null){
			return $("#SEX2").val(val);
		}else {
			return $("#SEX2").val('');
		}
	}
}

/**
 * 保存投资情况
 * @author 杨雪
 * date 2013-09-06
 * @return
 */
function toSaveInv(){
	$("#addInversInfo_").form('submit',{
		url:_basePath+"/customers/CustMainRelation!doInsertInvestInfo.action",
		onSubmit: function(){
			return $(this).form('validate');
		},
		success:function(data){
		  data = $.parseJSON(data);
		  if(data.flag==false){
			  $.messager.show({
				  title:'投资情况添加',
				  msg:'添加投资情况失败',
				  showType:'show'
			   });
			  $("#inversInfo_").dialog('close');
			  $('#inversInfoDetail').datagrid('load', {
					"param" : getFromData("#inversInfo")
				});
		  }else{
			  $.messager.show({
				  title:'投资情况添加',
				  msg:'添加投资情况成功',
				  showType:'show'
			   });
			  $("#inversInfo_").dialog('close');
			  $('#inversInfoDetail').datagrid('load', {
					"param" : getFromData("#inversInfo")
				});
		  }
		}
	});
}

/**
 * 修改 获取投资情况数据
 * @author 杨雪
 * date 2013-09-07
 * @return
 */
function getInversInfo(){
	var row = $("#inversInfoDetail").datagrid('getSelected');
	if (row){
		if(row.WOR_ID != undefined || row.WOR_ID != ""){
			$("#updateinversInfo_").dialog('open');
			jQuery.get(_basePath+'/customers/CustMainRelation!getInvest.action?INFO_ID='+row.INFO_ID+"&date="+new Date(),function(html){
				$("#openDialog").html(html);
			});
		}else {
			alert("请选择一条客户投资情况数据");
		}
	}else{
		alert("请选择一条客户投资情况数据");
	}
}


/**
* 保存从业经历修改
* @author 杨雪
* date 2013-09-07
* @return
*/
function toUpdateInversInfo_(){
	$("#updateInversInfo_").form('submit',{
		url:_basePath+"/customers/CustMainRelation!doUpdateInvest.action",
		onSubmit: function(){
			return $(this).form('validate');
		},
		success:function(data){
		  data = $.parseJSON(data);
		  if(data.flag==false){
			  $.messager.show({
				  title:'修改投资情况',
				  msg:'修改投资情况失败',
				  showType:'show'
			   });
			  $("#updateinversInfo_").dialog('close');
			  $("#updateInversInfo_").form('clear');
			  $('#inversInfoDetail').datagrid('load', {
					"param" : getFromData("#inversInfo")
				});
		  }else{
			  $.messager.show({
				  title:'修改投资情况',
				  msg:'修改投资情况成功',
				  showType:'show'
			   });
			  $("#updateinversInfo_").dialog('close');
			  $("#updateInversInfo_").form('clear');
			  $('#inversInfoDetail').datagrid('load', {
					"param" : getFromData("#inversInfo")
				});
		  }
		}
	});
}

/**
 * 删除
 * @author 杨雪
 * date 2013-09-06
 */
function delInversInfo(){
	if(confirm("确认删除？")){
		var row = $("#inversInfoDetail").datagrid('getSelected');
		if (row){
			jQuery.ajax({
				url: _basePath+"/customers/CustMainRelation!doDelInvest.action",
				data: "INFO_ID="+row.INFO_ID,
				dataType:"json",
				success : function(data) {
							if (data.flag == true) {
								 $.messager.show({
									  title:'投资情况删除',
									  msg:data.msg,
									  showType:'show'
								   });
								 $('#inversInfoDetail').datagrid('load', {
										"param" : getFromData("#inversInfo")
									});
							} else {
								$.messager.show({
									  title:'投资情况删除',
									  msg:data.msg,
									  showType:'show'
								   });
								$('#inversInfoDetail').datagrid('load', {
									"param" : getFromData("#inversInfo")
								});
							}
						},
				error: function(e){
					alert(e.message);
				}
			});
		}else {
			alert("请选择要删除的投资信息");
		}
	}
}