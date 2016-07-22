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
					url:_basePath+'/PaymentTerm/PaymentTerm!doAddPaymentTerm.action',
//					校验时启用
					onSubmit: function(){  
						return $("#"+formId).form('validate');
					},   
					success:function(json){ 
						json = jQuery.parseJSON(json);
						if(json.flag){
							alert("保存成功");
							$("#"+divId).dialog('close');
							$('#pageTable').datagrid('load', {"param":JSON.stringify(json.data)});
						}else{
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

function updatePaymentTerm(ID,divId,formId,type){
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
			 	var jsonData = $("#"+formId).serialize();
				jQuery.ajax({
				   type: "POST",
				   dataType:"json",
				   url:_basePath+'/PaymentTerm/PaymentTerm!doUpdatePaymentTerm.action?ID='+ID+"&_datetime="+new Date().getTime(),
				   data: jsonData,
				   success:function(json){ 
						if(json.flag){
							alert("修改成功");
							$("#"+divId).dialog('close');
							conditionsSelect();
						}else{
							alert(json.msg);
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
    		url : _basePath+"/PaymentTerm/PaymentTerm!toUpdatePaymentTerm.action?ID="+ID+"&_datetime="+new Date().getTime(),
    		dataType : "json",
    		success : function(json){
				$("#EDITOR_FORMLABEL1").val(json.data.FORMLABEL);
//				$("#EDITOR_SQL_FIELD1").val(json.data.SQL_FIELD);
//				$("#EDITOR_PDF_FIELD1").val(json.data.PDF_FIELD);
				$("#EDITOR_SQL1").val(json.data.SQL);
				$("#EDITOR_NOTE1").val(json.data.NOTE);
//				校验时启用
//				$("#"+formId).form('validate');
    		}
    	});
}

function viewDetails(ID,divId,formId){
	$("#"+divId).show();
	$("#"+divId).dialog({ 
		title:"付款条件详情",  
		modal:true,
		resizable:true,
		buttons: [{
			text:'关 闭',
			iconCls:'icon-cancel',
			handler:function(){
			$("#PaymentTermShow").dialog('close');
		}
		}]
	}); 
	$("#"+formId).form('clear');
	jQuery.ajax({
		url : _basePath+"/PaymentTerm/PaymentTerm!toShowPaymentTermDetails.action?ID="+ID+"&_datetime="+new Date().getTime(),
		dataType : "json",
		success : function(json){
			$("#SHOW_FORMLABEL").val(json.data.FORMLABEL);
			$("#SHOW_SQL").val(json.data.SQL);
			$("#SHOW_NOTE").val(json.data.NOTE);
//			$("#SHOW_SQL_FIELD").val(json.data.SQL_FIELD);
//			$("#SHOW_PDF_FIELD").val(json.data.PDF_FIELD);
		}
	});
}

function conditionsSelect(){
	$('#pageTable').datagrid('load', {"param":getFromData("#pageForm")});
}

function clearSelect(formId){
	$("#"+formId).form('clear');
}

function deletePaymentTerm(ID,rowIndex){
	$.messager.confirm('提示框', '确定要删除吗?',function(flag){
		if(flag){
			jQuery.ajax({
				url : _basePath+"/PaymentTerm/PaymentTerm!doDeletePaymentTerm.action?ID="+ID+"&_datetime="+new Date().getTime(),
					dataType : "json",
					success : function(json){
					if(json.flag){
						$('#pageTable').datagrid('load');
					}else{
						alert(json.msg);
					}	
				}
			});
		}
	});
}

$(document).ready(function(){
	$("#pageForm").form('clear');
	$("#pageTable").datagrid({
		url:_basePath+"/PaymentTerm/PaymentTerm!toMgPaymentTerm.action",
        pagination:true,// 分页
        rownumbers:true,//行数
        singleSelect:true,//单选模式
		fitColumns:true,
		toolbar:'#pageForm',
        columns:[[
			{field:'ID',align:'center',title: '操作',width:30,formatter:function(value,rowData,rowIndex){
			    return "<a href='javascript:void(0);' onclick='viewDetails("+rowData.ID+",\"PaymentTermShow\",\"showPaymentTerm\")'>查看</a>&nbsp;|&nbsp;"+
					   "<a href='javascript:void(0);' onclick='updatePaymentTerm("+rowData.ID+",\"PaymentTerm1\",\"editorPaymentTerm1\",\"修改\")'>修改</a>&nbsp;|&nbsp;"+ 
					   "<a href='javascript:void(0);' onclick='deletePaymentTerm("+rowData.ID+","+rowIndex+")'>删除</a>";
			}
			},
            {field:'FORMLABEL',align:'center',width:30,title:'付款条件名称'},
            {field:'SQL',align:'center',width:30,title:'SQL语句'},
            {field:'NOTE',align:'center',width:30,title:'备注'}
			
        ]]
    });
});

function checkSql(thisLabel,inputId1,inputId2,inputId3){
	var sql = $("#"+thisLabel).val();
	if(sql != null && sql != ''){
		jQuery.ajax({
			url : _basePath+"/PaymentTerm/PaymentTerm!checkSql.action?sql="+encodeURI(sql),
			dataType : "json",
			success : function(json){
				if(json.flag){
					
				}else{
					alert(json.msg);
					$("#"+thisLabel).val('');
				}
			}
		});
	}
}

function checkTermName(id){
	var val = $("#"+id).val();
	if(val != null && val != '' && val !='undefind'){
		jQuery.ajax({
			url : _basePath+"/PaymentTerm/PaymentTerm!checkTermName.action?TermName="+val,
			dataType : "json",
			success : function(json){
				if(json.flag){
					
				}else{
					alert("付款条件名称已存在！");
					$("#"+id).val('');
				}
			}
		});
	}
}
