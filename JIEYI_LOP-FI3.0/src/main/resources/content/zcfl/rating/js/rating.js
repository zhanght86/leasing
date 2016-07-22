function getOperation(value,rowData,index){
	return  "<a href='javascript:void(0)' onclick='updateR("+JSON.stringify(rowData)+")'>修改</a>" +
			"&nbsp;|&nbsp;" +
			"<a href='javascript:void(0)' onclick='deleteR("+JSON.stringify(rowData)+")'>删除</a>" +
			"&nbsp;|&nbsp;" +
			"<a href='javascript:void(0)' onclick='config("+JSON.stringify(rowData)+")'>配置模版</a>" ;
}

function updateR(rowData){
	$("#ADD_NAME").val(rowData.NAME);
	$('#ID_CARD_TYPE').combobox('setValue',rowData.ID_CARD_TYPE);
    $("#dialogDivAddR").dialog({
        title: "添加模版",
		width: 290,
	    height: 170,
		closed: false,//是否自动开启
	    cache: false,//是否缓存
	    modal: true,//是否有遮罩层
		resizable:false,//是否可以变更尺寸
        buttons: [{
            text: '保 存',
            iconCls: 'icon-save',
            handler: function(){
				var NAME = $("#ADD_NAME").val();
				var ID_CARD_TYPE = $('#ID_CARD_TYPE').combobox('getValue');
				var ID = rowData.ID
                jQuery.ajax({
					async: true,
                    url: "Rating!updateRating.action",
                    data: "NAME=" + NAME + "&ID_CARD_TYPE=" + ID_CARD_TYPE + "&ID=" + ID + "&_dateTime=" + new Date(),
                    type: "post",
                    dataType: "json",
                    success: function(json){
                        if (json.flag) {
                        	$('#pageTable').datagrid('load');
                        }else{
							alert("修改失败！");
						}
		                $("#dialogDivAddR").dialog('close');
                    },
					error: function(e) { 
						alert("修改失败！");
					} 
                })
            }
        }, {
            text: '关 闭',
            iconCls: 'icon-cancel',
            handler: function(){
                $("#dialogDivAddR").dialog('close');
            }
        }],
		onClose:function(){
			$("#ADD_NAME").val('');
	    }
    });
}

function addR(){
    $("#dialogDivAddR").dialog({
        title: "添加模版",
		width: 290,
	    height: 170,
		closed: false,//是否自动开启
	    cache: false,//是否缓存
	    modal: true,//是否有遮罩层
		resizable:false,//是否可以变更尺寸
        buttons: [{
            text: '保 存',
            iconCls: 'icon-save',
            handler: function(){
				var NAME = $("#ADD_NAME").val();
				var ID_CARD_TYPE = $('#ID_CARD_TYPE').combobox('getValue');
                jQuery.ajax({
					async: true,
                    url: "Rating!saveRating.action",
                    data: "NAME=" + NAME + "&ID_CARD_TYPE=" + ID_CARD_TYPE + "&_dateTime=" + new Date(),
                    type: "post",
                    dataType: "json",
                    success: function(json){
                        if (json.flag) {
                        	$('#pageTable').datagrid('load');
                        }else{
							alert("添加失败！");
						}
		                $("#dialogDivAddR").dialog('close');
                    },
					error: function(e) { 
						alert("添加失败！");
					} 
                })
            }
        }, {
            text: '关 闭',
            iconCls: 'icon-cancel',
            handler: function(){
                $("#dialogDivAddR").dialog('close');
            }
        }],
		onClose:function(){
			$("#ADD_NAME").val('');
	    }
    });
}

function deleteR(rowData){
	$.messager.confirm('提 示', '确定删除模版【'+rowData.NAME+'】吗?', function(r){
		if(r){
			jQuery.ajax({
				async: true,
		        url: "Rating!deleteRating.action",
		        data: "ID=" + rowData.ID + "&_dateTime=" + new Date(),
		        type: "post",
		        dataType: "json",
		        success: function(json){
		            if (json.flag) {
		            	$('#pageTable').datagrid('load');
		            }else{
						alert("删除失败！");
					}
		        },
				error: function(e) { 
					alert("删除失败！");
				} 
		    })
		}
	})
}

//搜索
function conditionsSelect(){
	$('#pageTable').datagrid('load', {
		NAME:$("#NAME").val(),
		ID_CARD_TYPE:$("#ID_CARD_TYPE_S").combobox('getValue')
	});
}

function clearInput(){
	$("#pageForm input").val("");
}

//$(function(){});
$(document).ready(function(){
	
	$("#add").click(function() {//右箭头图片
		$("#select1 option:selected").appendTo("#select2");
	});
	
	$("#remove").click(function() {//左箭头图片
		$("#select2 option:selected").appendTo("#select1");
	});
	
	$("#select1").dblclick(function(){ //双击
		$("option:selected",this).appendTo("#select2"); 
	});
	
	$("#select2").dblclick(function(){//双击
	   $("option:selected",this).appendTo("#select1");
	});
	
	$("#select1").keydown(function(e) {
		if (e.keyCode == 39) {//右箭头
			$("#select1 option:selected").appendTo("#select2");
		}
	});
	
	$("#select2").keydown(function(e) {
		if(e.keyCode == 37){//左箭头
			$("#select2 option:selected").appendTo("#select1");
		}
	});
	
	$("#TITLE_NAME1").keydown(function(e) {
		if (e.keyCode == 13) {//回车
			sousuo();
		}
	});
	
});

function config(rowData){
	jQuery.ajax({
		url : "Rating!getTitleData.action",
		type : "post",
		data : "ID_CARD_TYPE=" + rowData.ID_CARD_TYPE + "&_dateTime=" + new Date(),
		dataType : "json",
		success: function(json){
			for(var i = 0;i<json.data.length;i++){
				$("#select1").append('<option value="'+json.data[i].ID+'" name="TITLE_ID" class="menber" >'+json.data[i].NAME+'</option>');
			}
//********************************************************************************************************************************************************************************************************
			jQuery.ajax({
				async: false,//同步
				url : "Rating!getConfigTitleData.action",
				type : "post",
				data : "ID=" + rowData.ID + "&_dateTime=" + new Date(),
				dataType : "json",
				success: function(json){
					for (var i = 0; i < json.data.length; i++) {
						$("#select1 .menber[value='" + json.data[i].ID + "']").appendTo('#select2');
					}
					$("#CONFIG_ID").attr("value",rowData.ID);
				}
			});
//			$("#openDialogConfig").show();
			$("#openDialogConfig").dialog({
		        title: "配置模版",
				width: 502,
			    height: 485,
				closed: false,//是否自动开启
			    cache: false,//是否缓存
			    modal: true,//是否有遮罩层
				resizable:false,//是否可以变更尺寸
		        buttons: [{
		            text: '全 选',
		            iconCls: 'icon-add',
		            handler: function(){
		               $("#select1 option").appendTo("#select2");
		            }
		        },{
		            text: '取消全选',
		            iconCls: 'icon-remove',
		            handler: function(){
		                $("#select2 option").appendTo("#select1");
		            }
		        },{
		            text: '保 存',
		            iconCls: 'icon-save',
		            handler: function(){
						var CONFIG_ID = $("#CONFIG_ID").val(); 
						var TITLE_IDS = "";
						$("#select2 [name='TITLE_ID']").each(function(){
							TITLE_IDS += "," + $(this).val();
						});
						TITLE_IDS=TITLE_IDS.substring(1,TITLE_IDS.length);
						jQuery.ajax({
							url : "Rating!saveConfigTitle.action",
							data: "CONFIG_ID=" + CONFIG_ID + "&TITLE_IDS=" + TITLE_IDS + "&_dateTime=" + new Date(),
		                    type: "post",
							dataType : "json",
							success: function(json){
								if(json.flag){
									alert("保存成功！");
									$("#openDialogConfig").dialog("close");
								}else{
									alert("保存失败！");
								}
							},
							error: function(e) { 
								alert("保存失败！");
							} 
		                })
		            }
		        }, {
		            text: '关 闭',
		            iconCls: 'icon-cancel',
		            handler: function(){
		                $("#openDialogConfig").dialog('close');
		            }
		        }],
				onClose:function(){
//					$("#openDialogConfig").hide();
					$("#TITLE_NAME1").val("");
					$("#select1").empty();
					$("#select2").empty();
			    }
			});
//********************************************************************************************************************************************************************************************************
		}
	});
}

function sousuo(){
	var rowData = $('#pageTable').datagrid('getSelected');
	var ID_CARD_TYPE = rowData.ID_CARD_TYPE;
	var TITLE_NAME=$("#TITLE_NAME1").val();
	jQuery.ajax({
		url : "Rating!getConfigTitleForName.action",
		type : "post",
		data : "ID_CARD_TYPE=" + ID_CARD_TYPE + "&TITLE_NAME=" + TITLE_NAME + "&_dataTime=" + new Date(),
		dataType : "json",
		success : function(json){
			$("#select1").empty();
			for(var i = 0;i<json.data.length;i++){
				var flag = true;
				$("#select2 option").each(function(){
					var id = $(this).val();
					if(id == json.data[i].ID){
						flag = false;
					}
				});
				if(flag){
					$("#select1").append('<option value="'+json.data[i].ID+'" name="TITLE_ID" class="menber" >'+json.data[i].NAME+'</option>');
				}
			}
		}
	});
}