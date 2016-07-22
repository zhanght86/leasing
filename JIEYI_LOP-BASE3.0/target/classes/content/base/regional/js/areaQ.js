$(document).ready(function(){ 
	$('#dialogDiv').dialog({
	    onClose:function(){
			$("#ID").val('');
			$("input[name='NAME']").val('');
			$("input[name='LESSEE_AREA']").val('');
	    }
	});
	
	window.onresize=resizeBannerImage;//当窗口改变宽度时执行此函数
});

function resizeBannerImage(){
	$('#quyu').datagrid('resize', {
		width:function(){return document.body.clientWidth;}
	});
	$('#shengshi').datagrid('resize', {
		width:function(){return document.body.clientWidth;}
	});
}
 
//查询区域所包含省市
function clickTrQuYu(index,rowDate){
	var id = rowDate.ID;
    jQuery.ajax({
        url: "Regional!selectQuYuSubset.action",
        data: "AREA_ID=" + id,
        type: "post",
        dataType: "json",
        success: function(json){
            if (json.flag) {
                var rows = [];
                for (var i = 0; i < json.data.length; i++) {
                    rows.push({
                        NAME: json.data[i].NAME,
                        LESSEE_AREA: json.data[i].LESSEE_AREA,
                        SHORT_NAME: json.data[i].SHORT_NAME,
                        ID: json.data[i].ID,
                        PARENT_ID:  json.data[i].PARENT_ID
                    });
                }
                $("#shengshi").datagrid('loadData', rows);
            } else {
				$.messager.alert('提示','加载失败！请与管理员联系！','warning');
            }
        }
    });
}

//添加页面
function dialogAdd(){
	$("#dialogDiv").dialog({title:'添加区域'});
	$("#dialogDiv").dialog('open');
	var opts = $('#quyu').datagrid(null);
}


//修改页面
function dialogUpdate(){
 	var opts = $('#quyu').datagrid('getSelected');
	if (opts) {
		$("#ID").val(opts.ID);
		$("input[name='NAME']").val(opts.NAME);
		$("input[name='LESSEE_AREA']").val(opts.LESSEE_AREA);
		//add by lishuo 01-08-2016 Start
		var AREA_ID =opts.ID;
		jQuery.ajax({
			url:"Regional!searchForModify.action",
			data:"AREA_ID=" + AREA_ID,
			type:"post",
			dataType:"json",
			success:function(data){
				if(data){
					var json =data.data;
					if(json[0]!= null){
						$("#Contract_Man").val(json[0].NAME);
						$("input[name='SELECT_MAN']").val(json[0].NAME);
					}else{
						$("#Contract_Man").val('');
						$("input[name='SELECT_MAN']").val('');
					}
				}
			}
		});
		//add by lishuo 01-08-2016 End
		$("#dialogDiv").dialog({title:'修改区域'});
		$("#dialogDiv").dialog('open');
	}else{
		$.messager.alert('提示','请选择要修改的数据！');
	}
}

//添加and修改保存
function save(){
	var url = '';
	var SELECT_MAN =$("input[name='SELECT_MAN']").val();
	var Contract_Man =$("#Contract_Man").val();
	if(Contract_Man == ""){
		$.messager.alert('提示','请录入合同审核负责人！');
		return;	
	}
	if(SELECT_MAN == ""){
		if(Contract_Man == null ||Contract_Man =="" ) {
			$.messager.alert('提示','请录入合同审核负责人！');
			return;	
		}
	}
	var opts = $('#quyu').datagrid('getSelected');
	if (opts) {
		$("#ID").val(opts.ID);
	}
	if($("#ID").val()){
		url = 'Regional!doUpdateArea.action?_dateTime='+new Date()+"&SELECT_MAN="+SELECT_MAN+"&AREA_ID="+opts.ID;
	}else{
		url = 'Regional!doAddArea.action?_dateTime='+new Date()+"&SELECT_MAN="+SELECT_MAN;
	}
	
	if($("input[name='NAME']").validatebox('isValid')){
		jQuery.ajax({
	        url: url,
	        data: "param="+getFromData("#fromDate"),
	        type: "post",
	        dataType: "json",
	        success: function(json){
                if($("#ID").val()){
                	successSaveUpdate(json);
                	if(json.data =="区域负责人只能有一个，若想添加区域请修改原先区域负责人的配置区域！"){
                		$.messager.alert('提示','区域负责人只能有一个，若想添加区域请修改原先区域负责人的配置区域！');
                		return false;
                	}
                	else if(json.msg==""&&!json.data){
						$.messager.alert('提示','修改失败,合同审核负责人：数据不合法！');	
					}else if(!json.data){
						$.messager.alert('提示','修改失败,该用户有多个组织！');	
					}else{
						$.messager.alert('提示','修改成功！');
					}
                	setTimeout("window.location.reload()",3000);
				}else{
					successSaveAdd(json);
					if(json.data =="区域负责人只能有一个，若想添加区域请修改原先区域负责人的配置区域！"){
                		$.messager.alert('提示','区域负责人只能有一个，若想添加区域请修改原先区域负责人的配置区域！');
                		setTimeout("window.location.reload()",5000);
                		return false;
                	}
					if(json.msg==""&&!json.data){
						$.messager.alert('提示','保存失败,合同审核负责人：数据不合法！');	
					}else if(!json.data){
						$.messager.alert('提示','保存失败,该用户有多个组织！');	
					}else{
						$.messager.alert('提示','保存成功！');
					}
					setTimeout("window.location.reload()",3000);
				}
	        }
	    });
	}else{
		$.messager.alert('提示','请填写必填项！','warning');
	}
}

function successSaveAdd(json){
	if(json.flag){
		$('#quyu').datagrid('insertRow',{
			index: 0,
			row: {
				NAME: json.data.NAME,
                LESSEE_AREA: json.data.LESSEE_AREA,
                SHORT_NAME: json.data.SHORT_NAME,
                ID: json.data.ID,
                PARENT_ID: json.data.PARENT_ID
			}
		});
		closeDialog();
	}else{
		$.messager.alert('提示','保存失败！请与管理员联系！');
	}
}

function successSaveUpdate(json){
	var opts = $('#quyu').datagrid('getSelected');
	var index = $('#quyu').datagrid('getRowIndex',opts);
	if(json.flag){
 		$('#quyu').datagrid('updateRow', { 
			index: index, 
			row: { 
				NAME: json.data.NAME,
				LESSEE_AREA: json.data.LESSEE_AREA, 
				SHORT_NAME: json.data.SHORT_NAME,
				ID: json.data.ID,
				PARENT_ID: json.data.PARENT_ID
			} 
		});
		closeDialog();
	}else{
		$.messager.alert('提示','保存失败！请与管理员联系！');
	}
}

//删除
function doDelete(){
	var opts = $('#quyu').datagrid('getSelected');
	var index = $('#quyu').datagrid('getRowIndex',opts);
	if(opts){
		$.messager.confirm('提示', '确定要删除"'+opts.NAME+'"这条数据吗？', function(r){
			if (r){
				var ID = opts.ID;
		        jQuery.ajax({
		            url: "Regional!doDeleteArea.action",
		            data: "ID=" + ID,
		            type: "post",
		            dataType: "json",
		            success: function(json){
		                if (json.flag > 0) {
//			                    $.messager.alert('提示','删除成功！');
							$('#quyu').datagrid('deleteRow',index);
		                } else {
							$.messager.alert('提示','删除失败！');
		                }
		            }
		        });
			}
		});
	}else{
		$.messager.alert('提示','请选择要删除的数据！');
	}
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
		$.messager.alert('提示','请选择要配置的区域！');
		return;
	}
	jQuery.ajax({
		url : "Regional!getShengFenData.action",
		type : "post",
		data : "AREA_ID=" + opts.ID + "&_dateTime=" + new Date(),
		dataType : "json",
		success: function(json){
			for(var i = 0;i<json.data.length;i++){
				var flag = true;
				$("#select2 option").each(function(){
					var id = $(this).val();
					if(id == json.data[i].ID || id == json.data[i].PARENT_ID){
						flag = false;
					}
				});
				if(flag){
					$("#select1").append('<option value="'+json.data[i].ID+'" name="SHENG_SHI_ID" class="menber" >'+json.data[i].NAME+'</option>');
				}
			}
//********************************************************************************************************************************************************************************************************
			jQuery.ajax({
				async: false,//同步
				url : "Regional!getQSFData.action",
				type : "post",
				data : "AREA_ID=" + opts.ID + "&_dateTime=" + new Date(),
				dataType : "json",
				success: function(json){
					if(json.data){
						for (var i = 0; i < json.data.length; i++) {
							$("#select1 .menber[value='" + json.data[i].ID + "']").appendTo('#select2');
						}
					}
					$("#CONFIG_ID").attr("value",opts.ID);						
				}
			});
			$("#openDialogConfig").dialog({
		        title: "配置区域",
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
						var SHENG_SHI_IDS = "";
						$("#select2 [name='SHENG_SHI_ID']").each(function(){
							SHENG_SHI_IDS += "," + $(this).val();
						});
						SHENG_SHI_IDS=SHENG_SHI_IDS.substring(1,SHENG_SHI_IDS.length);
						jQuery.ajax({
							url : "Regional!saveConfig.action",
							data: "CONFIG_ID=" + CONFIG_ID + "&SHENG_SHI_IDS=" + SHENG_SHI_IDS + "&_dateTime=" + new Date(),
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
	jQuery.ajax({
		url : "Regional!selectShengShi.action",
		type : "post",
		data : "SHENGSHI_NAME=" + SHENGSHI_NAME + "&_dataTime=" + new Date(),
		dataType : "json",
		success : function(json){
			$("#select1").empty();
			for(var i = 0;i<json.data.length;i++){
				var flag = true;
				$("#select2 option").each(function(){
					var id = $(this).val();
					if(id == json.data[i].ID || id == json.data[i].PARENT_ID){
						flag = false;
					}
				});
				if(flag){
					$("#select1").append('<option value="'+json.data[i].ID+'" name="SHENG_SHI_ID" class="menber" >'+json.data[i].NAME+'</option>');
				}
			}
		}
	});
}
/**
 * add by lishuo 01-07-2016
 * 查询输入的员工姓名
 */
function getContent(){
	var html="";
	var Contract_Man= $("#Contract_Man").val();
	$.ajax({
		url:"Regional!SerchUser.action",
		data:{"Contract_Man":Contract_Man},
		type:"post",
		dataType:"json",
		sync:true,
		success:function(data){
			if(data){
				var json =data.data;
				if(json.length > 0){
					for(var i =0; i<json.length;i++){
						html = html +"<div class='item'  style='cursor:hand' onClick='getCon(this)'>" + json[i].NAME + "</div>"
					}
					if(html != ""){
						$("#append").html(html);
					}else{
						$("#append").html("");
					}
				}else{
					$("#append").html("");
					return;
				}
			}
		}
	})
}
/**
 * add by lishuo 01-07-2016
 * 选择输入的员工姓名
 */
function getCon(obj){
	var Contract_Man =$(obj).text();
	$("#Contract_Man").val(Contract_Man);
	$("#append").html("");
	$("#SELECT_MAN").val(Contract_Man);
}