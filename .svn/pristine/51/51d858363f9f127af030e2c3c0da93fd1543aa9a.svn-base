$(document).ready(function(){ 
	$('#dialogDiv').dialog({
	    onClose:function(){
			$("#fromDate").form('clear');
	    }
	});
	$('#dialogDivShow').dialog({
	    onClose:function(){
			$("#fromDate1").form('clear');
	    }
	});
})

function getValue(value,rowDate,inedx){
	var f = "deleteDS('"+rowDate.NAME+"','"+value+"')";
	return "<a href='javascript:void(0)' onclick='showDS("+value+")'>查看</a>" + "&nbsp|&nbsp" +
		   "<a href='javascript:void(0)' onclick='toUpdateDS("+value+")'>修改</a>" +"&nbsp|&nbsp" +
		   "<a href='javascript:void(0)' onclick="+f+">删除</a>";
}

function showDS(ID){
	jQuery.ajax({
        url: "DataSource!getDSData.action?_dateTime="+new Date(),
        data: "ID=" + ID,
        type: "post",
        dataType: "json",
        success: function(json){
            if (json.flag) {
				$("#fromDate1 input[name='NAME']").val(json.data.NAME);
				$("#fromDate1 input[name='TYPE']").val(json.data.TYPE1);
				$("#fromDate1 textarea[name='SQL']").val(json.data.SQL);
				$("#fromDate1 textarea[name='NOTE']").val(json.data.NOTE);
				$("#dialogDiv").dialog({   
					 title:"查 看",
					 buttons: [{
			            text:'关 闭',
						iconCls:'icon-cancel',
			            handler:function(){
			               $("#dialogDivShow").dialog('close');
			            }
			        }]
				}); 
				$("#dialogDivShow").dialog('open');
            } else {
				$.messager.alert('提示','加载失败，请与管理员联系！');
            }
        }
    })
}

function toUpdateDS(ID){
	jQuery.ajax({
        url: "DataSource!getDSData.action?_dateTime="+new Date(),
        data: "ID=" + ID,
        type: "post",
        dataType: "json",
        success: function(json){
            if (json.flag) {
				$("#fromDate input[name='ID']").val(json.data.ID);
				$("#TYPE_UPDATE").combobox('select',json.data.TYPE);
				$("#fromDate input[name='NAME']").val(json.data.NAME);
				$("#fromDate textarea[name='SQL']").val(json.data.SQL);
				$("#fromDate textarea[name='NOTE']").val(json.data.NOTE);
				$("#dialogDiv").dialog({   
					 title:"修 改",
					 buttons: [{
							  id:"btnbc_update",
				            text:'保 存',
				         iconCls:'icon-save',
				         handler:function(){
						 	$('#btnbc_update').linkbutton('disable');
							jQuery.ajax({
						        url: "DataSource!doUpdateDS.action?_dateTime="+new Date(),
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
		TYPE:$("#TYPE").combobox('getValue'),
		SQL:$("#SQL").val(),
		NOTE:$("#NOTE").val()
	});
}

function clearInput(){
	$("#pageForm input").val("");
}

//删除
function deleteDS(NAME,ID){
	$.messager.confirm('提示', '确定要删除"'+NAME+'"这条数据吗？', function(r){
		if (r){
	        jQuery.ajax({
	            url: "DataSource!doDeleteDS.action?_dateTime="+new Date(),
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

function addDS(){
	$("#dialogDiv").dialog({   
		 title:"添 加",
		 buttons: [{
				  id:"btnbc",
	            text:'保 存',
	         iconCls:'icon-save',
	         handler:function(){
			 	$('#btnbc').linkbutton('disable');
				jQuery.ajax({
			        url: "DataSource!doAddDS.action?_dateTime="+new Date(),
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