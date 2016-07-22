/**
 * 打开dialog
 * @param div
 * @return
 */
function toPartnerCust(div){
	$("#"+div).dialog('open');	
	$("#addPartner").form('clear');
	$("#partner_CLIENT_ID").val($("#client_id").val());
}

/**
 * 关闭dialog
 * @return
 */
function closeDailogPartner(div){
	$("#addPartner").form('clear');
	$("#"+div).dialog('close');
}


/**
 * 添加功能
 * @author 杨雪
 * 
 * @param val
 * @param flag
 * @return
 */
function choseType(val) {
		if(val != null){
			return $("#COMPANY_PROPERTY").val(val);
		}else {
			return $("#COMPANY_PROPERTY").val('');
		}
}

function choseType_(val) {
		if(val != null){
			return $("#COMPANY_PROPERTY2").val(val);
		}else {
			return $("#COMPANY_PROPERTY2").val('');
		}
}

/**
 * 保存
 * @author 杨雪
 * date 2013-09-06
 * @return
 */
function toSave(){
	$("#addPartner").form('submit',{
		url:_basePath+"/customers/CustMainRelation!doCustPartners.action",
		success:function(data){
		  data = $.parseJSON(data);
		  if(data.flag==false){
			  $.messager.show({
				  title:'合作伙伴添加',
				  msg:'添加合作伙伴失败',
				  showType:'show'
			   });
			  $("#partnerCust").dialog('close');				
			  $("#addPartner").form('clear');
			  $('#partnerDetail').datagrid('load', {
					"param" : getFromData("#partnerDe")
				});
		  }else{
			  $.messager.show({
				  title:'合作伙伴添加',
				  msg:'添加合作伙伴成功',
				  showType:'show'
			   });
			  $("#partnerCust").dialog('close');
			  $('#partnerDetail').datagrid('load', {
					"param" : getFromData("#partnerDe")
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
function delPartnerCust(){
	if(confirm("确认删除？")){
		var row = $("#partnerDetail").datagrid('getSelected');
		if (row){
			jQuery.ajax({
				url: _basePath+"/customers/CustMainRelation!doDelPartners.action",
				data: "PAR_ID="+row.PAR_ID,
				dataType:"json",
				success : function(data) {
							if (data.flag == true) {
								 $.messager.show({
									  title:'合作伙伴删除',
									  msg:data.msg,
									  showType:'show'
								   });
								 $('#partnerDetail').datagrid('load', {
										"param" : getFromData("#partnerDe")
								  });
							} else {
								$.messager.show({
									  title:'合作伙伴删除',
									  msg:data.msg,
									  showType:'show'
								   });
								$('#partnerDetail').datagrid('load', {
									"param" : getFromData("#partnerDe")
							  });
							}
						},
				error: function(e){
					alert(e.message);
				}
			});
		}else{
			alert("请选择要删除的合作伙伴的数据");
		}
	}
}


//合作伙伴数据，根据id获取
function updatePartnerCust(){
	var row = $("#partnerDetail").datagrid('getSelected');
	if (row){
		if(row.WOR_ID != undefined || row.WOR_ID != ""){
			$("#partnerCust_").dialog('open');
			jQuery.get(_basePath+'/customers/CustMainRelation!getPartners.action?PAR_ID='+row.PAR_ID,function(html){
				$("#openPartner").html(html);
			});
		}else {
			alert("请选择一条客户合作伙伴数据");
		}
	}else{
		alert("请选择一条客户合作伙伴数据");
	}
}


/**
* 修改合作伙伴
* @author 杨雪
* date 2013-09-09
* @return
*/
function toUpdatePartnerCust_(){
	$("#upPartner").form('submit',{
		url:_basePath+"/customers/CustMainRelation!doUpdatePartners.action",
		onSubmit: function(){
			return $(this).form('validate');
		},
		success:function(data){
		  data = $.parseJSON(data);
		  if(data.flag==false){
			  $.messager.show({
				  title:'修改合作伙伴',
				  msg:data.msg,
				  showType:'show'
			   });
			  $("#partnerCust_").dialog('close');
			  $("#upPartner").form('clear');
			  $('#partnerDetail').datagrid('load', {
					"param" : getFromData("#partnerDe")
			  });
		  }else{
			  $.messager.show({
				  title:'修改合作伙伴',
				  msg:data.msg,
				  showType:'show'
			   });
			  $("#partnerCust_").dialog('close');
			  $("#upPartner").form('clear');
			  $('#partnerDetail').datagrid('load', {
					"param" : getFromData("#partnerDe")
			  });
		  }
		}
	});
}