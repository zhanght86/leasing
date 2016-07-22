function addTS(){
	$("#dialogDiv").dialog({   
		 title:"添 加",
		 buttons: [{
				  id:"btnbc",
	            text:'保 存',
	         iconCls:'icon-save',
	         handler:function(){
			 	$('#btnbc').linkbutton('disable');
				jQuery.ajax({
			        url: "TitleStandard!doAddTS.action?_dateTime="+new Date(),
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

//搜索
function conditionsSelect(){
	$('#pageTable').datagrid('load', {
		NAME:$("#NAME").val(),
		TYPE:$("#TYPE").combobox('getValue'),
		ID_CARD_TYPE:$("#ID_CARD_TYPE").combobox('getValue'),
		TEMPLATE_TYPE:$("#TEMPLATE_TYPE").combobox('getValue')
	});
}

function getOperation(value,rowDate,index){
	return "<a href='javascript:void(0)' onclick='toUpdateTS("+value+")'>修改</a>" + "&nbsp|&nbsp" +
	       "<a href='javascript:void(0)' onclick='deleteTC("+value+","+index+")'>删除</a>" + "&nbsp|&nbsp" +
		   "<a href='TitleStandard!toSetTAddAndUpdate.action?ID="+value+"' >设置题目</a>" + "&nbsp|&nbsp" +
		   "<a href='TitleStandard!toSetCAddAndUpdate.action?ID="+value+"' >设置标准</a>";
}

function toUpdateTS(ID){
	jQuery.ajax({
        url: "TitleStandard!getTSData.action?_dateTime="+new Date(),
        data: "ID=" + ID,
        type: "post",
        dataType: "json",
        success: function(json){
            if (json.flag) {
				$("#fromDate input[name='ID']").val(json.data.ID);
				$("#fromDate input[name='NAME']").val(json.data.NAME);
				$("#ID_CARD_TYPE_UPDATE").combobox('select',json.data.ID_CARD_TYPE);
				$("#TEMPLATE_TYPE_UPDATE").combobox('select',json.data.TEMPLATE_TYPE+'');
				$("#TYPE_UPDATE").combobox('select',json.data.TYPE);
				$("#dialogDiv").dialog({   
					 title:"修 改",
					 buttons: [{
							  id:"btnbc_update",
				            text:'保 存',
				         iconCls:'icon-save',
				         handler:function(){
						 	$('#btnbc_update').linkbutton('disable');
							jQuery.ajax({
						        url: "TitleStandard!doUpdateTS.action?_dateTime="+new Date(),
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

function clearInput(){
	$("#pageForm input").val("");
}

$(document).ready(function(){ 
	$('#dialogDiv').dialog({
	    onClose:function(){
			$("#fromDate").form('clear');
	    }
	});
})

//添加Table
function addTable(){
	$("#updateTable").append($(".hiddenClassTable").clone().removeClass("templete hiddenClassTable").attr('id',Math.uuidFast()));
}

//删除Table
function deleteTable(e){
	$(e).parents("table").remove();
	$(e).parents("br").remove();
}

//添加Tr
function addTr(e){
	var tableId = $(e).parent().parent().parent().parent().attr("id");
	var num = $("#" + tableId + " tr").length;
	$("#" + tableId).append($("#hiddenTr").clone().removeAttr("id"));
}

//删除Tr
function deleteTr(e){
	$(e).parents("tr").remove();
}

//提交保存-题目
function saveT(){
	$('#btnbc').linkbutton('disable');
	var  saveRecord = new Array();
	$(".addData:not(.templete)").each(function(){
  		var temp={};
		//题目名称
		temp.TITLE=$(this).find("[name=TITLE]").val();
		//评分模式
		temp.MODER=$(this).find("[name=MODER]").val();
		//数据源
		temp.SQLPT_ID=$(this).find("[name=SQLPT_ID]").val();
		var  saveRecord1 = new Array();
		$(this).find(".hiddenTrClass").each(function(){
			var temp1={};
			//选项
			temp1.NAME=$(this).find("[name=NAME]").val();
			//评分标准
			temp1.FORMULA=$(this).find("[name=FORMULA]").val();
			//分值
			temp1.SCORE=$(this).find("[name=SCORE]").val();
			saveRecord1.push(temp1);
		});
		temp.LIST = saveRecord1;
		saveRecord.push(temp);
    });
	var addData ={ 
		TS_ID:$("#TS_ID").val(),//模版ID
		DATA_LIST:saveRecord
	};
	var alldata = JSON.stringify(addData);
	jQuery.ajax({
		url: _basePath+"/zcfl/TitleStandard!doSaveT.action",
		type:"post",
		data: { "alldata":alldata },
		dataType: "json",
		success: function(json){
			if(json.flag){
				$.messager.alert('提示', '保存成功！');
				$('#btnbc').linkbutton('enable');
			}else{
				$.messager.alert('提示','保存失败，请与管理员联系！');
				$('#btnbc').linkbutton('enable');
			}
		}
	}); 
}

//提交保存-标准
function saveC(){
	$('#btnbc').linkbutton('disable');
	var  saveRecord = new Array();
	$(".addData:not(.templete)").each(function(){
  		var temp={};
		//最小值
		temp.SCORE_MIN=$(this).find("[name=SCORE_MIN]").val();
		//最大值
		temp.SCORE_MAX=$(this).find("[name=SCORE_MAX]").val();
		var  saveRecord1 = new Array();
		$(this).find(".hiddenTrClass").each(function(){
			var temp1={};
			//最小级别
			temp1.MIN_LEVEL=$(this).find("[name=MIN_LEVEL]").val();
			//最大级别
			temp1.MAX_LEVEL=$(this).find("[name=MAX_LEVEL]").val();
			//调整类型
			temp1.RESULT_TYPE=$(this).find("[name=RESULT_TYPE]").val();
			//调整级数
			temp1.CHANGE_LEVEL=$(this).find("[name=CHANGE_LEVEL]").val();
			saveRecord1.push(temp1);
		});
		temp.LIST = saveRecord1;
		saveRecord.push(temp);
    });
	var addData ={ 
		TS_ID:$("#TS_ID").val(),//模版ID
		DATA_LIST:saveRecord
	};
	var alldata = JSON.stringify(addData);
	jQuery.ajax({
		url: _basePath+"/zcfl/TitleStandard!doSaveC.action",
		type:"post",
		data: { "alldata":alldata },
		dataType: "json",
		success: function(json){
			if(json.flag){
				$.messager.alert('提示', '保存成功！');
				$('#btnbc').linkbutton('enable');
			}else{
				$.messager.alert('提示','保存失败，请与管理员联系！');
				$('#btnbc').linkbutton('enable');
			}
		}
	}); 
}

function deleteTC(ID,index){
	 $('#pageTable').datagrid('selectRow',index);
	var opts = $('#pageTable').datagrid('getSelected');
	jQuery.messager.confirm('提示', '确定删除模版"'+opts.NAME+'"吗？', function(r){
		jQuery.ajax({
	        url: "TitleStandard!doDeleteTC.action?TS_ID="+ID+"&_dateTime="+new Date(),
	        type: "post",
	        dataType: "json",
	        success: function(json){
	            if(json.flag){
					$('#pageTable').datagrid('load');
				}else{
					$.messager.alert('提示','删除失败，请与管理员联系！','warning');
				}
	        }
	    })
	})
}
