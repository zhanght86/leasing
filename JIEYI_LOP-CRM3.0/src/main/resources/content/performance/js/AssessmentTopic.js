$(document).ready(function(){ 
	$('#dialogDiv').dialog({
	    onClose:function(){
			$("#fromDate").form('clear');
			$("#fromDate input[name='NAME']").removeAttr("readonly");
			$("#fromDate textarea[name='SQL']").removeAttr("readonly");
			$("#fromDate textarea[name='NOTE']").removeAttr("readonly");
	    }
	});
})

function getValue(value,rowDate,inedx){
	var f = "deleteAT('"+rowDate.NAME+"','"+value+"')";
	return "<a href='javascript:void(0)' onclick='showAT("+value+")'>查看</a>" + "&nbsp|&nbsp" +
		   "<a href='javascript:void(0)' onclick='toUpdateAT("+value+")'>修改</a>" +"&nbsp|&nbsp" +
		   "<a href='javascript:void(0)' onclick="+f+">删除</a>";
}

function showAT(ID){
	jQuery.ajax({
        url: "AssessmentTopic!getATData.action?_dateTime="+new Date(),
        data: "ID=" + ID,
        type: "post",
        dataType: "json",
        success: function(json){
            if (json.flag) {
				$("#fromDate input[name='NAME']").val(json.data.NAME);
				$("#fromDate textarea[name='SQL']").val(json.data.SQL);
				$("#fromDate textarea[name='NOTE']").val(json.data.NOTE);
				$("#fromDate input[name='NAME']").attr("readonly","true");
				$("#fromDate textarea[name='SQL']").attr("readonly","true");
				$("#fromDate textarea[name='NOTE']").attr("readonly","true");
				$("#dialogDiv").dialog({   
					 title:"查看考核项",
					 buttons: [{
			            text:'关 闭',
						iconCls:'icon-cancel',
			            handler:function(){
			               $("#dialogDiv").dialog('close');
			            }
			        }]
				}); 
				$("#dialogDiv").dialog('open');
            } else {
				$.messager.alert('提示','加载失败，请与管理员联系！');
            }
        }
    })
}

function toUpdateAT(ID){
	jQuery.ajax({
        url: "AssessmentTopic!getATData.action?_dateTime="+new Date(),
        data: "ID=" + ID,
        type: "post",
        dataType: "json",
        success: function(json){
            if (json.flag) {
				$("#fromDate input[name='ID']").val(json.data.ID);
				$("#fromDate input[name='NAME']").val(json.data.NAME);
				$("#fromDate textarea[name='SQL']").val(json.data.SQL);
				$("#fromDate textarea[name='NOTE']").val(json.data.NOTE);
				$("#dialogDiv").dialog({   
					 title:"修改考核项",
					 buttons: [{
							  id:"btnbc_update",
				            text:'保 存',
				         iconCls:'icon-save',
				         handler:function(){
						 	$('#btnbc_update').linkbutton('disable');
							jQuery.ajax({
						        url: "AssessmentTopic!doUpdateAT.action?_dateTime="+new Date(),
						        data: "param="+getFromData("#fromDate"),
						        type: "post",
						        dataType: "json",
						        success: function(json){
					                if(json.flag){
										$("#dialogDiv").dialog('close');
										$('#pageTable').datagrid('load',{ID:json.data.ID});
									}else{
										$('#btnbc_update').linkbutton('enable');
										$.messager.alert('提示','保存失败，请与管理员联系！','warning');
									}
						        }
						    })
			             }
			        },{
			            text:'关 闭',
						iconCls:'icon-cancel',
			            handler:function(){
			               $("#dialogDiv").dialog('close');
			            }
			        }]
				}); 
				$("#dialogDiv").dialog('open');
            } else {
				$.messager.alert('提示','加载失败，请与管理员联系！');
            }
        }
    })
}

//搜索
function conditionsSelect(){
	$('#pageTable').datagrid('load', {
		NAME:$("#NAME").val(),
		SQL:$("#SQL").val(),
		NOTE:$("#NOTE").val()
	});
}

function clearInput(){
	$("#pageForm input").val("");
}

//删除
function deleteAT(NAME,ID){
	$.messager.confirm('提示', '确定要删除"'+NAME+'"这条数据吗？', function(r){
		if (r){
	        jQuery.ajax({
	            url: "AssessmentTopic!doDeleteAT.action?_dateTime="+new Date(),
	            data: "ID=" + ID,
	            type: "post",
	            dataType: "json",
	            success: function(json){
	                if (json.flag) {
						$('#pageTable').datagrid('load');
	                }
	                else {
						$.messager.alert('提示','删除失败，请与管理员联系！');
	                }
	            }
	        })
		}
	});
}

function addAT(){
	$("#dialogDiv").dialog({   
		 title:"添加考核项",
		 buttons: [{
				  id:"btnbc",
	            text:'保 存',
	         iconCls:'icon-save',
	         handler:function(){
			 	$('#btnbc').linkbutton('disable');
				jQuery.ajax({
			        url: "AssessmentTopic!doAddAT.action?_dateTime="+new Date(),
			        data: "param="+getFromData("#fromDate"),
			        type: "post",
			        dataType: "json",
			        success: function(json){
		                if(json.flag){
							$("#dialogDiv").dialog('close');
							$('#pageTable').datagrid('load',{ID:json.data.ID});
						}else{
							$('#btnbc').linkbutton('enable');
							$.messager.alert('提示','保存失败，请与管理员联系！','warning');
						}
			        }
			    })
             }
        },{
            text:'关 闭',
			iconCls:'icon-cancel',
            handler:function(){
               $("#dialogDiv").dialog('close');
            }
        }]
	}); 
	$("#dialogDiv").dialog('open');
}