$(document).ready(function(){
	$("#pageTable").datagrid({
		url:_basePath+"/PaymentTerm/PaymentMould!toMgPaymentMouldData.action",
		rownumbers:true,//左侧自动显示行数
		toolbar:'#pageForm',
		pageList:[20,50,100,200],
		pagination:true,
        singleSelect:true,//单选模式
		fitColumns:true,
		columns:[[  
		          	{field:'ID',title:'操作',width:100,align:'center',formatter:setOperate},
		          	{field:'NAME',title:'放款模版名称',align:'center',width:100},
		          	{field:'FLAG',title:'行业类型',align:'center',width:100},
		          	{field:'CODE',hidden:true}
		          	
		         ]], view:detailview,
			 		detailFormatter : function(index, row) {
			 			return '<div id="ddv-' + row.ID + '" style="padding:5px 0;"></div>';
			 		},
					onExpandRow : function(index, row) {
						jQuery.ajax({
							url:_basePath+"/PaymentTerm/PaymentMould!toPaymentMouldDetail.action?NAME="+encodeURI(row.NAME),  
							type:'post',
							dataType:'json',
						    success: function(json){
								var data = {flag:json.flag,total:json.data.length,rows:json.data};
								var pRowIndex = "ddv-"+row.ID;
								$('#ddv-'+row.ID).datagrid({
									fitColumns:true,
									rownumbers:true,//左侧自动显示行数
									columns:[[
					                            {field:'NORM_NAME',align:'center',width:200,title:'客户名称'}
									         ]],
									onResize:function(){
				                        $('#pageTable').datagrid('fixDetailRowHeight',index);
				                    },
				                    onLoadSuccess:function(){
				                        setTimeout(function(){
				                            $('#pageTable').datagrid('fixDetailRowHeight',index);
				                        },0);
				                    }
								});
								 $('#pageTable').datagrid('fixDetailRowHeight',index);
									$('#ddv-'+row.ID).datagrid("loadData",data);
						}
					});
			 		}	
	})
});
//删除模版
function doDelete(item) {
	var val = item.NAME;
	if(confirm("确认是否删除放款模版数据？")){
		if(val != null && val != '' && val !='undefind'){
			jQuery.ajax({
				url : _basePath+"/PaymentTerm/PaymentMould!deletedByName.action?NAME="+encodeURI(val),
				dataType : "json",
				success : function(json){
					if(json.flag){
						seach();
					}else{
						alert("删除失败！");
					}
				}
			});
		}
	}
}
//查询刷新
function seach() {
	var param = getFromData('#pageForm');
	$('#pageTable').datagrid('load', {
		"param" : param
	});
}
// 清空
function clearQuery() {
	$('#pageForm').form('clear');
}
function setOperate(val, row) {
	var str = "<a href='javascript:void(0)' onclick ='doDelete("+JSON.stringify(row)+")'>删除</a>";
	str += " | <a href='javascript:void(0);' onclick='updatePaymentTerm("+JSON.stringify(row)+",\"PaymentTerm1\",\"editorPaymentTerm1\",\"修改放款模版\")'>修改</a>";
	return str;
}

jQuery(function($) {
	// 全选  添加窗口
	$("#btn1").click(function() {
		$("input[name='checkbox']").attr("checked", "true");
	});
	// 取消全选
	$("#btn2").click(function() {
		$("input[name='checkbox']").removeAttr("checked");
	});
	// 反选
	$("#btn4").click(function() {
		$("input[name='checkbox']").each(function() {
			if ($(this).attr("checked")) {
				$(this).removeAttr("checked");
			} else {
				$(this).attr("checked", "true");
			}
		});
	});
	// 全选  修改窗口
	$("#UP_btn1").click(function() {
		$("input[name='UP_checkbox']").attr("checked", "true");
	});
	// 取消全选
	$("#UP_btn2").click(function() {
		$("input[name='UP_checkbox']").removeAttr("checked");
	});
	// 反选
	$("#UP_btn4").click(function() {
		$("input[name='UP_checkbox']").each(function() {
			if ($(this).attr("checked")) {
				$(this).removeAttr("checked");
			} else {
				$(this).attr("checked", "true");
			}
		});
	});
});
//添加窗口 及 保存操作
function addPaymentTerm(divId,formId,type){
	$("#"+divId).show();
	$("#"+divId).dialog({   
		 title:type,
     	 modal:true,
		 resizable:true,
		 buttons: [{
			  id:"btnbc",
            text:'保 存',
            iconCls:'icon-save',
            handler:function(){
			 	$('#btnbc').linkbutton('disable');
				$('#'+formId).form({   
					url:_basePath+'/PaymentTerm/PaymentMould!add.action',
//					校验时启用
					onSubmit: function(){  
						var ADD_NAME = $("#ADD_NAME").val();
						if(ADD_NAME == null || ADD_NAME == '' || ADD_NAME =='undefind'){
							alert("请填写放款模版名称！");
							$('#btnbc').linkbutton('enable');
							return false;
						}
						var ADD_CODE = $("#ADD_CODE").val();
						if(ADD_CODE == null || ADD_CODE == '' || ADD_CODE =='undefind'){
							alert("请选择行业类型！");
							$('#btnbc').linkbutton('enable');
							return false;
						}
						var TORM_IDS = "";
						$("input[name='checkbox']:checkbox:checked").each(function() {
							TORM_IDS += $(this).attr("NORM_ID")+"-";
						});
						if(TORM_IDS == null || TORM_IDS == '' || TORM_IDS =='undefind'){
							alert("请选择放款条件！");
							$('#btnbc').linkbutton('enable');
							return false;
						}
						$("#TORM_IDS").val(TORM_IDS);
						return $("#"+formId).form('validate');
					},   
					success:function(json){ 
						json = jQuery.parseJSON(json);
						if(json.flag){
							alert("保存成功");
							//按钮可用
							$('#btnbc').linkbutton('enable');
							$("#"+formId).form('clear');
							$("#"+divId).dialog('close');
							seach();
						}else{
							$('#btnbc').linkbutton('enable');
							alert(json.msg);
						}
					}   
				});   
				$('#'+formId).submit(); 
            }
        },{
            text:'关 闭',
			iconCls:'icon-cancel',
            handler:function(){
               $("#"+divId).dialog('close');
            }
        }]
	}); 
	$("#"+formId).form('clear');
}

// 校验模版名称是否存在
function checkMouldName(id){
	var val = $("#"+id).val();
	if(val != null && val != '' && val !='undefind'){
		jQuery.ajax({
			url : _basePath+"/PaymentTerm/PaymentMould!checkMouldNameCount.action?NAME="+encodeURI(val),
			dataType : "json",
			success : function(json){
				if(json.flag){
					
				}else{
					alert("放款模版名称已存在！");
					$("#"+id).val('');
				}
			}
		});
	}
}

//修改窗口 及 保存操作
function updatePaymentTerm(row,divId,formId,type){
	$("#"+divId).show();
	$("#"+divId).dialog({ 
		 title:type,  
     	 modal:true,
		 resizable:true,
		 buttons: [{
			  id:"btnbc",
            text:'保 存',
            iconCls:'icon-save',
            handler:function(){
			 	var UP_NAME = $("#UP_NAME").val();
				if(UP_NAME == null || UP_NAME == '' || UP_NAME =='undefind'){
					alert("请填写放款模版名称！");
					$('#btnbc').linkbutton('enable');
					return false;
				}
				var UP_CODE = $("#UP_CODE").val();
				if(UP_CODE == null || UP_CODE == '' || UP_CODE =='undefind'){
					alert("请选择行业类型！");
					$('#btnbc').linkbutton('enable');
					return false;
				}
				var TORM_IDS = "";
				$("input[name='UP_checkbox']:checkbox:checked").each(function() {
					TORM_IDS += $(this).attr("NORM_ID")+"-";
				});
				if(TORM_IDS == null || TORM_IDS == '' || TORM_IDS =='undefind'){
					alert("请选择放款条件！");
					$('#btnbc').linkbutton('enable');
					return false;
				}
				$("#UP_TORM_IDS").val(TORM_IDS);
				
			 	$('#btnbc').linkbutton('disable');
			 	var jsonData = $("#"+formId).serialize();
				jQuery.ajax({
				   type: "POST",
				   dataType:"json",
				   url:_basePath+'/PaymentTerm/PaymentMould!update.action',
				   data: jsonData,
				   success:function(json){ 
						if(json.flag){
							alert("修改成功");
							$("#"+divId).dialog('close');
							seach();
						}else{
							alert("修改失败，请重新操作！");
						}
					} 
				}); 
            }
        },{
            text:'关 闭',
			iconCls:'icon-cancel',
            handler:function(){
               $("#"+divId).dialog('close');
            }
        }]
	}); 
	$("#"+formId).form('clear');
	jQuery.ajax({
    		url : _basePath+"/PaymentTerm/PaymentMould!toPaymentMouldDetail.action?NAME="+encodeURI(row.NAME),
    		dataType : "json",
    		success : function(json){
				$("#UP_NAME").val(row.NAME);
				$("#UP_CODE").val(row.CODE);
				for(var i=0;i<json.data.length;i++){
					$("input[name='UP_checkbox']").each(function() {
						var NORM_NAME = $(this).attr("NORM_NAME");
						if(NORM_NAME == json.data[i].NORM_NAME){
							$(this).attr("checked", "true");
						}
					});
				}
    		}
    	});
}