function clearInput(){
	$("#pageForm input").val("");
}

$(document).ready(function(){
	$("#asset_MG").datagrid({
		url:"Assets!execute_ajax.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		fitColumns:true,
		pageSize:10,
		singleSelect:true,//单选模式
		toolbar:'#pageForm',
		columns:[[
					{field:'STATUS',title:'操作',width:80,align:'center',formatter:function(value,rowData,rowIndex){
							var res = "";
							switch(parseInt(value)) {
							case 1:
								res = '<fond style="color:red !important;" >评级审核中</fond>';
								break;
							case 6:
								res = '<a href="javascript:void(0);"  onclick="goon('+rowData.RESULT_TASK_ID+')"><fond style="color:red !important;" >继续评级</fond></a>';
								break;
							case 7:
								res = '<a href="javascript:void(0);" onclick="doOpenJbpmF(this)" RESULT_ID="'+rowData.RESULT_ID+'"><fond style="color:red !important;" >发起评级评审流程</fond></a>';
								break;
							default:
								if(rowData.PLATFORM==1) 
									res = '<a onclick="doOpenJbpm(this)" href="javascript:void(0);" pid="' +rowData.ZAIL_PAY_CODE+'" csjb="' +rowData.ZAIL_LEVEL1+'" NOW_LEVEL="' +rowData.CODE1+'">资产评级1</a>';
								else if(rowData.PLATFORM==2 || rowData.PLATFORM==11 || rowData.PLATFORM==3)
									res = '<a onclick="doIndirect(this)" href="javascript:void(0);" pid="' +rowData.ZAIL_PAY_CODE+'" ID_CARD_TYPE="' +rowData.PLATFORM+'" ZAIL_LEVEL="' +rowData.ZAIL_LEVEL+'" RESULT_NOW_LEVEL="' +rowData.CODE+'">资产评级2</a>';
							}
						return res;
					}
					},
		          	{field:'PLATFORM',title:'业务类型',width:80,align:'center',formatter:function(value,rowData,rowIndex){
		          			return rowData.PLATFORM1;
						}
					},
		          	{field:'RENTER_NAME',title:'客户名称',width:80,align:'center'},
		          	{field:'ZAIL_PAY_CODE',title:'支付表编号',width:80,align:'center'},
		          	{field:'ZAIL_DAYS',title:'逾期天数',width:80,align:'center'},
		          	{field:'ZAIL_LEVEL',title:'初始级别',width:80,align:'center',formatter:function(value,rowData,rowIndex){
		          			return rowData.ZAIL_LEVEL1;
						}
					},
		          	{field:'CODE',title:'当前级别',width:80,align:'center',formatter:function(value,rowData,rowIndex){
		          			return rowData.CODE1;
						}
					},
		          	
		          	{field:'T_ID',hidden:true},
		          	{field:'ZAIL_LEVEL1',hidden:true},
		          	{field:'CODE1',hidden:true},
		          	{field:'PLATFORM1',hidden:true},
		          	{field:'RESULT_TASK_ID',hidden:true},
		          	{field:'RESULT_ID',hidden:true}
		         ]]
		});
});

function doOpenJbpmF(obj) {
	jQuery.ajax({
		url : _basePath+"/zcfl/Assets!approal.action",
		data : "RESULT_ID="+$(obj).attr("RESULT_ID") ,
		dataType : "json",
		success : function(json){
			$("#PSHJB").val(json.data.CODE1);
			$("#DQJB").val(json.data.RESULT_NOW_LEVEL1);
			$("#RESULT_PAY_CODE").val(json.data.RESULT_PAY_CODE);
			$("#LB_FZ_RESULT_PAY_CODE").val(json.data.RESULT_ID);
		}
	});
	$("#FZJLC").dialog("open");
}

function diglogBut3(obj) {//非直接评级--第二次弹窗，按钮控制
	if($(obj).attr('iconCls') == 'icon-cancel') {
		$("#FZJLC").dialog('close');
	} else {
		jQuery.ajax({
			url : _basePath+"/zcfl/Assets!doStartBpmF.action",
			data : "RESULT_ID="+$("#LB_FZ_RESULT_PAY_CODE").val()+ "&RESULT_NOTE="+$("#FZJLC [name='RESULT_NOTE1']").val(),
			dataType : "json",
			success : function(json2){
				if(json2.flag){
					alert("流程发起成功！");
					$("#FZJLC").dialog("close");
					window.location=_basePath+"/zcfl/Assets!execute.action";
				}else{
					alert("流程发起失败！");
					$(this).dialog("close");
				}
			}
		});
	}
}
function doOpenJbpm(obj) {
	$("#jbpmDiv [name='ZJ_PAY_CODE']").val($(obj).attr("pid"));
	$("#jbpmDiv [name='RESULT_BEGIN_LEVEL']").val($(obj).attr("csjb"));
	$("#jbpmDiv [name='NOW_LEVEL']").val($(obj).attr("NOW_LEVEL"));
	$("#jbpmDiv").dialog("open");
}

function doIndirect(obj) {
	$("#ztt_name").empty();
	$("#doIndirectDiv [name='FZJ_PAY_CODE']").val($(obj).attr("pid"));
	$("#doIndirectDiv [name='FZJ_ID_CARD_TYPE']").val($(obj).attr("ID_CARD_TYPE"));
	$("#doIndirectDiv [name='FZJ_ZAIL_LEVEL']").val($(obj).attr("ZAIL_LEVEL"));
	$("#doIndirectDiv [name='RESULT_NOW_LEVEL']").val($(obj).attr("RESULT_NOW_LEVEL"));
	var FZJ_ID_CARD_TYPE=$("#doIndirectDiv [name='FZJ_ID_CARD_TYPE']").val();
	jQuery.ajax({
	url : "CapitalClassification!Refer2.action?ID_CARD_TYPE="+FZJ_ID_CARD_TYPE,
	dataType : "json",
	success: function(json){
				$("#ztt_name").append("<option value=''>请选择...</option>")
			for(var i=0; i<json.data.length; i++){
				$("#ztt_name").append("<option value='"+json.data[i].ID+"' >"+json.data[i].NAME+"</option>")
			}
		}
	});
	$("#doIndirectDiv").dialog("open");
}

function diglogBut1(obj) {//直接评级--弹窗，按钮控制
	if($(obj).attr('iconCls') == 'icon-cancel') {
		$("#jbpmDiv").dialog('close');
	} else {
		var RESULT_BEGIN_LEVEL = $("#jbpmDiv [name='RESULT_BEGIN_LEVEL']").val();
		var param0 = {
			'ZRD_TYPE':$("#ZRD_TYPE0").val(),
			'ZRD_LEVEL':RESULT_BEGIN_LEVEL
		}
		var param1 = {
			'ZRD_TYPE':$("#ZRD_TYPE1").val(),
			'ZRD_LEVEL':$("#ASSETS_LEVEL1").val(),
			'ZRD_NOTE':$("#ZRD_NOTE1").val()
		}
		var param2 = {
			'ZRD_TYPE':$("#ZRD_TYPE2").val(),
			'ZRD_LEVEL':$("#ASSETS_LEVEL2").val(),
			'ZRD_NOTE':$("#ZRD_NOTE2").val()
		}
		var param3 = {
			'ZRD_TYPE':$("#ZRD_TYPE3").val(),
			'ZRD_LEVEL':$("#ASSETS_LEVEL3").val(),
			'ZRD_NOTE':$("#ZRD_NOTE3").val()
		}
		var list=[];
		list.push(param0);
		list.push(param1);
		list.push(param2);
		list.push(param3);
		var data="";
		data="json="+JSON.stringify(list)+"&RESULT_BEGIN_LEVEL="+RESULT_BEGIN_LEVEL+"&RESULT_PAY_CODE="+$("#jbpmDiv [name='ZJ_PAY_CODE']").val();
	
		jQuery.ajax({
			url : _basePath+"/zcfl/Assets!doStartBpm.action",
			data : data,
			dataType : "json",
			success : function(json){
				if(json.flag){
					alert("流程发起成功！");
					window.location=_basePath+"/zcfl/Assets!execute.action";
				}else{
					alert("流程发起失败！");
					window.location=_basePath+"/zcfl/Assets!execute.action";
				}
			}
		});
	}
}

function diglogBut2(obj) {//非直接评级--第一次弹窗，按钮控制
	if($(obj).attr('iconCls') == 'icon-cancel') {
		$("#doIndirectDiv").dialog('close');
	} else {
		if(!$("#ztt_name").val() || $("#ztt_name").val() == ""){
			alert("请选择评级模板!");
			return;
		}
		jQuery.ajax({
			url : _basePath+"/zcfl/Assets!doIndirect.action",
			data : "RESULT_PAY_CODE="+$("#doIndirectDiv [name='FZJ_PAY_CODE']").val()+
					   "&ZAIL_LEVEL="+$("#doIndirectDiv [name='FZJ_ZAIL_LEVEL']").val()+
                 "&RESULT_NOW_LEVEL="+$("#doIndirectDiv [name='RESULT_NOW_LEVEL']").val()+
			               "&TTP_ID="+$("#ztt_name").val(),
			dataType : "json",
			success : function(json){
				if(json.flag){
					if(json.data.over){
						$("#doIndirectDiv").dialog("close");
						jQuery.ajax({
        					url : _basePath+"/zcfl/Assets!approal.action",
        					data : "RESULT_ID="+json.data.RESULT_ID ,
        					dataType : "json",
        					success : function(json1){
        						$("#PSHJB").val(json1.data.CODE);
        						$("#PSHJB").attr('resultId', json.data.RESULT_ID);
        						$("#DQJB").val(json1.data.RESULT_NOW_LEVEL);
        						$("#RESULT_PAY_CODE").val(json1.data.RESULT_PAY_CODE);
        						$("#RESULT_NOTE1").val("");
        					}
        				});
                		$("#FZJLC").dialog("open");
					}else{
						top.addTab("资产评级", _basePath + '/zcfl/ClassifyTask!CapitalClassificationNoStraight.action?CPT_ID='+json.data.CPT_ID+'&ID='+json.data.ID+'&RESULT_ID='+json.data.RESULT_ID+"&RESULT_PAY_CODE="+json.data.RESULT_PAY_CODE);
//						window.location=_basePath+"/zcfl/Assets!execute.action";
					}
				}else{
					alert("评级失败!");
					window.location=_basePath+"/zcfl/Assets!execute.action";
				}
			}
		});
	}
}


function syncData(){//刷数据
    jQuery.ajax({
        type: 'POST',
        url: _basePath + "/zcfl/AssetsInitialLevel!addAssetsInitialLevel.action",
        dataType: 'json',
        success: function(json){
            if (json.flag){
                alert("同步成功！");
			}else{
                alert("同步失败！");
			} 
        }
    });
}

function goon(e){
	var RESULT_TASK_ID=e;
	jQuery.ajax({
		url : _basePath+"/zcfl/Assets!goondoIndirect.action",
		data : "RESULT_TASK_ID="+RESULT_TASK_ID,
		dataType : "json",
		success : function(json){
			if(json.flag){
				top.addTab('资产评级', _basePath+'/zcfl/ClassifyTask!CapitalClassificationNoStraight.action?CPT_ID='+json.data.CPT_ID+'&ID='+json.data.ID+'&RESULT_ID='+json.data.RESULT_ID+"&RESULT_PAY_CODE="+json.data.RESULT_PAY_CODE)
			}else{
				alert("评级失败!");
				window.location="$!_basePath/zcfl/Assets!execute.action";
			}
		}
	});
}

function sjfx(){
	top.addTab("数据分析",_basePath+"/zcfl/AssetsReportTable.action?_time="+new Date());
}

function seach(){
	var params = {};
	$('#pageForm input').each(function(){
		params[$(this).attr('name')] = $(this).val();
	});
	$('#pageForm select').each(function(){
		params[$(this).attr('name')] = $(this).find('option:selected').val();
	});
	$('#asset_MG').datagrid('load', params);
}