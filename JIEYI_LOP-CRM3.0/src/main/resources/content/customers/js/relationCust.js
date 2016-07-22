/**
 * 打开对话框
 * @param div
 * @return
 */
function toaddChild(div){
	$("#"+div).dialog('open');
	$("#"+div).panel("move",{top:$(document).scrollTop()*0.7 + ($(window).height()-250) * 0.5});  
	
	$("#addLink").form('clear');
	$("#client_id_child").val($("#client_id_child_1").val());
	$("#type_child_1").val($("#type_child_12").val());
	$("#tab").val($("#tab_child").val());
}

function toaddSocial(div){
	$("#"+div).dialog('open');
	$("#"+div).panel("move",{top:$(document).scrollTop()*0.7 + ($(window).height()-250) * 0.5});  
	
	$("#RelationCust").form('clear');
	$("#client_id_society").val($("#client_id_society_1").val());
	$("#type_society").val($("#type_society_1").val());
	$("#tab_society").val($("#tab_society_1").val());
}

/*
 * 选择性别
 */
function choseSex(val){
	if(val != null){
	   return $("#LINK_SEX").val(val);
	}else {
		 return $("#LINK_SEX").val('');
	}
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
 * 添加子女
 * @author yx
 * @date 2013-09-04
 * @return
 */
function toSaveChild(){
	$("#addLink").form('submit',{
		url:_basePath+"/customers/Customers!doInsertLinl.action",
		success:function(data){
		  data = $.parseJSON(data);
		  if(data.flag==false){
			  alert("子女添加失败");
			  $("#toaddChild").dialog('close');
			
			  $('#childDialog1').datagrid('load', {
					"param" : getFromData("#tb")
				});
		  }else{
			  alert("子女添加成功");
			  $("#toaddChild").dialog('close');
			  $('#childDialog1').datagrid('load', {
					"param" : getFromData("#tb")
				});
		  }
		}
	});
}

/**
 * 社会关系
 * @return
 */
function toSaveRelation(){
	$("#RelationCust").form('submit',{
		url:_basePath+"/customers/Customers!doInsertLinl.action",
		success:function(data){
		data = $.parseJSON(data);
		  if(data.flag==false){
			  alert("社会关系添加失败");
			  $("#toaddRelation").dialog('close');
			  $('#relationToCust').datagrid('load', {
					"param" : getFromData("#relation")
				});
		  }else{
			  alert("社会关系添加成功");
			  $("#toaddRelation").dialog('close');
			  $('#relationToCust').datagrid('load', {
					"param" : getFromData("#relation")
				});
		  }
		}
	});
}

/**
 * 子女修改
 * @return
 */
function updateChild(){
	var row = $("#childDialog1").datagrid('getSelected');
	if(row){
		$.ajax({
			url:_basePath + "/customers/Customers!getCustRelation.action?LINK_ID="+row.LINK_ID+"&date="+new Date(),
			type:"post",
			dataType:"json",
			success:function(json){
				$("#LINK_NAME1").val(json.realtion.LINK_NAME);
				$("#LINK_IDCARD1").val(json.realtion.LINK_IDCARD);
				$("#LINK_SEX1u").val(json.realtion.LINK_SEX);
				$("#LINK_BIRTHDAY1u").datebox("setValue",json.realtion.LINK_BIRTHDAY);
				$("#LINK_WORK_UNITS1").val(json.realtion.LINK_WORK_UNITS);
				$("#REMARK1").val(json.realtion.REMARK);
				$("#Link_id").val(json.realtion.LINK_ID);
			}
		});
		$("#toUpdateChild").dialog('open');
		$("#toUpdateChild").panel("move",{top:$(document).scrollTop()*0.7 + ($(window).height()-250) * 0.5});  
	}else{
		alert("请选择要修改的子女信息");
	}
}

function toUpdateChild(){
	$("#UpdateChild").form('submit',{
		url:_basePath+'/customers/Customers!doCustRelation.action',
		success : function(data) {
			if (data.flag == false) {
				alert("客户子女修改失败");
				//关闭弹出框
				$("#toUpdateChild").dialog('close');
				//开户行信息刷新
				$('#childDialog1').datagrid('load', {
					"param" : getFromData("#tb")
				});
			} else {
				alert("客户子女修改成功");
				//关闭弹出框
				$("#toUpdateChild").dialog('close');
				//开户行信息刷新
				$('#childDialog1').datagrid('load', {
					"param" : getFromData("#tb")
				});
			}
		}
	});
}


/**
 * 社会关系修改
 * @return
 */
function toUpdateSocial(){
	var row = $("#relationToCust").datagrid('getSelected');
	if(row){
		$.ajax({
			url:_basePath + "/customers/Customers!getCustRelation.action?LINK_ID="+row.LINK_ID+"&date="+new Date(),
			type:"post",
			dataType:"json",
			success:function(json){
				$("#LINK_NAME11").val(json.realtion.LINK_NAME);
				$("#LINK_IDCARD11").val(json.realtion.LINK_IDCARD);
				$("#LINK_SEX11").val(json.realtion.LINK_SEX);
				$("#LINK_WORK_ADDRESS11").val(json.realtion.LINK_WORK_ADDRESS);
				$("#LINK_WORK_UNITS11").val(json.realtion.LINK_WORK_UNITS);
				$("#REMARK11").val(json.realtion.REMARK);
				$("#LINK_ID").val(json.realtion.LINK_ID);
				$("#LINK_RELATION2CUST11").val(json.realtion.LINK_RELATION2CUST);
				$("#LINK_MOBILE11").val(json.realtion.LINK_MOBILE);
				$("#LINK_PHONE11").val(json.realtion.LINK_PHONE);
			}
		});
		$("#toupdateRelation").dialog('open');
		$("#bankAdd").panel("move",{top:$(document).scrollTop()*0.7 + ($(window).height()-250) * 0.5});  
	}else{
		alert("请选择要修改的社会关系信息");
	}
}

function toUpdateRelation(){
	$("#updateRelationCust").form('submit',{
		url:_basePath+'/customers/Customers!doCustRelation.action',
		success : function(data) {
			if (data.flag == false) {
				alert("客户社会关系修改失败");
				//关闭弹出框
				$("#toupdateRelation").dialog('close');
				//开户行信息刷新
				$('#relationToCust').datagrid('load', {
					"param" : getFromData("#relation")
				});
			} else {
				alert("客户社会关系修改成功");
				//关闭弹出框
				$("#toupdateRelation").dialog('close');
				//开户行信息刷新
				$('#relationToCust').datagrid('load', {
					"param" : getFromData("#relation")
				});
			}
		}
	});
}


/**
 * 删除
 */
function delChild(){
	if(confirm("确认删除？")){
		var row = $("#childDialog1").datagrid('getSelected');
		if (row){
			jQuery.ajax({
				url: _basePath+"/customers/Customers!doDelRelation.action",
				data: "CLIENT_ID="+row.CLIENT_ID+"&LINK_ID="+row.LINK_ID,
				dataType:"json",
				success : function(data) {
							if (data.flag == true) {
								alert("删除子女成功");
								 $('#childDialog1').datagrid('load', {
										"param" : getFromData("#tb")
									});
							} else {
								alert("删除子女失败");
								 $('#childDialog1').datagrid('load', {
										"param" : getFromData("#tb")
									});
							}
						},
				error: function(e){
					alert(e.message);
				}
			});
		}
	}
}

function delSocial(){
	if(confirm("确认删除？")){
		var row = $("#relationToCust").datagrid('getSelected');
		if (row){
			jQuery.ajax({
				url: _basePath+"/customers/Customers!doDelRelation.action",
				data: "CLIENT_ID="+row.CLIENT_ID+"&LINK_ID="+row.LINK_ID,
				dataType:"json",
				success : function(data) {
							if (data.flag == true) {
								alert("社会关系删除成功");
								 $('#relationToCust').datagrid('load', {
										"param" : getFromData("#relation")
									});
							} else {
								alert("社会关系删除子女失败");
								 $('#relationToCust').datagrid('load', {
										"param" : getFromData("#relation")
									});
							}
						},
				error: function(e){
					alert(e.message);
				}
			});
		}
	}
}

/**
 * 关闭dailog
 * @return
 */
function closeDailog(div){
	$("#"+div).dialog('close');
}