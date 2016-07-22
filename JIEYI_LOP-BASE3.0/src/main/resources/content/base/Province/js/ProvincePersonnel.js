function se(){
	var NAME = $("#NAME").val();
	$('#quyu').datagrid('load', {"NAME":NAME});
}
//查询区域所包含省市
function clickTrQuYu(index,rowDate){
	var id = rowDate.ID;
    jQuery.ajax({
        url: "ProvincePersonnel!selectpeople.action",
        data: "PROVINCE_ID=" + id,
        type: "post",
        dataType: "json",
        success: function(json){
            if (json.flag) {
                var rows = [];
                for (var i = 0; i < json.data.length; i++) {
                    rows.push({
                        NAME: json.data[i].NAME,
                        CODE: json.data[i].CODE
                    });
                }
                $("#shengshi").datagrid('loadData', rows);
            } else {
				$.messager.alert('提示','加载失败！请与管理员联系！','warning');
            }
        }
    });
}

//关闭
function closeDialog(){
    $('#dialogDiv').dialog('close');
}


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
	
	$("#SHENGSHI_NAME").keydown(function(e) {
		if (e.keyCode == 13) {//回车
			sousuo();
		}
	});
	
});

function doConfig(){
	var opts = $('#quyu').datagrid('getSelected');
	var index = $('#quyu').datagrid('getRowIndex',opts);
	if (!opts) {
		$.messager.alert('提示','请选择要配置的省份！');
		return;
	}
	jQuery.ajax({
		url : "ProvincePersonnel!selectAllpeople.action",
		type : "post",
		data : "PROVINCE_ID=" + opts.ID,
		dataType : "json",
		success: function(json){
			for(var i = 0;i<json.data.length;i++){
				$("#select1").append('<option value="'+json.data[i].ID+'" name="ID" class="menber" >'+json.data[i].NAME+'</option>');
			}
//********************************************************************************************************************************************************************************************************
			jQuery.ajax({
				async: false,//同步
				url : "ProvincePersonnel!selectpeople.action",
				type : "post",
				data : "PROVINCE_ID=" + opts.ID,
				dataType : "json",
				success: function(json){
					if(json.data){
						for (var i = 0; i < json.data.length; i++) {
							$("#select2").append('<option value="'+json.data[i].ID+'" name="ID" class="menber" >'+json.data[i].NAME+'</option>');
						}
					}
					$("#CONFIG_ID").attr("value",opts.ID);						
				}
			});
			$("#openDialogConfig").dialog({
		        title: "配置省份人员",
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
		            	var PROVINCE_ID=opts.ID;
						var SHENG_SHI_IDS = "";
						$("#select2 option").each(function(){
							SHENG_SHI_IDS += "," + $(this).val();
						});
						SHENG_SHI_IDS=SHENG_SHI_IDS.substring(1,SHENG_SHI_IDS.length);
						jQuery.ajax({
							url : "ProvincePersonnel!saveConfig.action",
							data: "PROVINCE_ID=" + PROVINCE_ID + "&USER_ID=" + SHENG_SHI_IDS,
		                    type: "post",
							dataType : "json",
							success: function(json){
								if(json.flag){
									alert("保存成功！");
									clickTrQuYu(index,opts);
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
					$("#SHENGSHI_NAME").val("");
					$("#select1").empty();
					$("#select2").empty();
			    }
			});
//********************************************************************************************************************************************************************************************************
		}
	});
}

function sousuo(){
	var SHENGSHI_NAME=$("#SHENGSHI_NAME").searchbox('getValue');
	var opts = $('#quyu').datagrid('getSelected');
	jQuery.ajax({
		url : "ProvincePersonnel!selectAllpeople.action",
		type : "post",
		data : "NAME=" + SHENGSHI_NAME + "&PROVINCE_ID=" + opts.ID,
		dataType : "json",
		success : function(json){
			$("#select1").empty();
			if(json.data){
				for(var i = 0;i<json.data.length;i++){
					$("#select1").append('<option value="'+json.data[i].ID+'" name="SHENG_SHI_ID" class="menber" >'+json.data[i].NAME+'</option>');
				}
			}	
		}
	});
}