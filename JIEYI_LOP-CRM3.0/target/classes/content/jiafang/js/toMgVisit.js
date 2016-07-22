$(document).ready(function(){
	$("#insDialog").datagrid({
		url:_basePath+"/returnVisit/ReturnVisit!toMgVisit.action",//?CREDIT_ID=+$!param.CREDIT_ID,
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:true,
		pageSize:20,
		fit:true,
//		fitColumns:true,
		toolbar:'#pageForm',
		iconCls:'icon-edit',
//		onClickCell: onClickCell,
		 frozenColumns:[[    
					{field:'TR_ID',width:200,align:'center',title:'操作', formatter:function(value,rows,index){
//						if(rows.TR_ID==undefined){
//							return "<a href='#' onclick='add("+rows.PROJECT_ID+"\,"+rows.CLIENT_ID+"\,\""+rows.CUST_ID+"\",\""+rows.CLIENT_NAME+"\")'>添加</a>  " ;
//						}else {
						if(rows.TR_ID!=undefined){
							return "<a href='#' onclick='view("+rows.TR_ID+"\,"+rows.PROJECT_ID+"\,"+rows.CLIENT_ID+"\,\""+rows.CUST_ID+"\",\""+rows.CLIENT_NAME+"\")'>查看</a> || " +
						  		"<a href='#'onclick='update("+rows.TR_ID+"\,"+rows.PROJECT_ID+"\,"+rows.CLIENT_ID+"\,\""+rows.CUST_ID+"\",\""+rows.CLIENT_NAME+"\")'>修改</a> ||" +
//						  		"<a href='#' onclick='updateQixian("+rows.TR_ID+"\,\""+rows.ZX_INVESTIGATE_DATE+"\")'>修改调查期限</a> || " +
							    "<a href='#' onclick='del("+rows.TR_ID+")'>删除</a>";
						}
					}}    
		             ]], 
		columns:[[
				  {field:'CUST_ID',width:130,align:'center',title:'客户编号'}, 
		          {field:'CLIENT_NAME',width:120,align:'center',title:'客户姓名'},
				  {field:'ZX_ACCESS_TYPE',width:120,align:'center',title:'客户分类', formatter:function(value, rows, index){
					  if(value==0){
						  return '申请人';
					  }else {
						  return "担保人";
					  }
				  }},
				  {field:'SPEC_NAME',width:220,align:'center',title:'车型'}, 
				  {field:'hh',width:120,align:'center',title:'排量'},
				  {field:'UNIT_PRICE',width:120,align:'center',title:'车价'},
				  {field:'START_PERCENT',width:120,align:'center',title:'首付比例'},
				  {field:'LEASE_TERM',width:120,align:'center',title:'期限'},
				  {field:'ITEM_MONEY',width:120,align:'center',title:'月还款'},
//				  {field:'ZX_YD_TYPE',width:300,align:'center',title:'疑点类别',formatter:function(value, rows, index){
//						if(value!=undefined){
//							var str = value.split(',');
//							var zx_yd_type = "";
//							for(i=1; i<str.length; i++){
//								var str_ = str[i];
//								for(var t=0; t<type.length; t++){
//									var type_ = type[t].CODE;
//									if(str_==type_){
//										zx_yd_type += "; "+type[t].FLAG;
//									}
//								}
//							}
//							return zx_yd_type;
//						}
//				  }},
//				  {field:'ZX_YD_NR',width:400,align:'center',title:'疑点内容',formatter:function(value, rows, index){
//					if(rows.ZX_YD_TYPE!=undefined){
//							var str = rows.ZX_YD_TYPE.split(',');
//							var zx_remark = "";
//							for(i=1; i<str.length; i++){
//								var str_ = str[i];
//								for(var t=0; t<type.length; t++){
//									var type_ = type[t].CODE;
//									if(str_==type_){
//										zx_remark += "; "+type[t].REMARK;
//									}
//								}
//							}
//							return zx_remark;
//						}
//				  }},
				  {field:'ZX_VISIT_ADDR',width:120,align:'center',title:'调查地点'},
				  {field:'ZX_INVESTIGATE_DATE',width:120,align:'center',title:'调查期限'},//, editor:'datebox'
				  {field:'ZX_REMARK',width:200,align:'center',title:'备注'}
		         ]]	
	});
	
	//页面清空按钮操作
	$("#qingkong").click(function(){
		$(".paramData").each(function(){
			$(this).val("");
		});
	});
	
	//加载行业添加页面
	$("#tianjia").click(function(){
		$("#addPriceP").dialog('open');
	});
});

function tofindData(){
	var searchParams = getFromData('#pageForm');
	$('#insDialog').datagrid('load',{"searchParams":searchParams});
}

/**
 * 添加家访记录
 * @param project_id 项目id
 * @param client_id  客户id
 * @param CUST_ID    客户编号
 * @param CLIENT_NAME  客户名称
 * @return
 */
function add(project_id, client_id,CUST_ID,CLIENT_NAME){
	top.addTab("家访添加", _basePath+ "/returnVisit/ReturnVisit!toAddVisit.action?project_id="+project_id+"&client_id="+client_id+"&CUST_ID="+CUST_ID+"&CLIENT_NAME="+CLIENT_NAME+"&date="+new Date().getTime());
}

/**
 * 保存家访记录
 * @param FLAG
 * @param TYPE
 * @return
 */
function save(FLAG,TYPE){
	$("#addVisit").form('submit',{
		type:'post',
		 url: _basePath+"/returnVisit/ReturnVisit!doAddReturnVisitSave.action",
		success:function(json){
			json = $.parseJSON(json);
			if(json.flag){
				alert("家访任务信息保存成功！");
			}else{
				alert("家访任务信息保存失败！");
			}
		}
	});			
}

function saveL(FLAG,TYPE){
		//$("#save").linkbutton('disable');
		
		$("#addVisit").form('submit',{
			type:'post',
			 url: _basePath+"/returnVisit/ReturnVisit!doAddReturnVisitSave1.action",
			success:function(json){
				json = $.parseJSON(json);
				if(json.flag){
					alert("家访任务信息保存成功！");
				}else{
					alert("家访任务信息保存失败！");
				}
			}
		});	
}

function saveL1(FLAG,TYPE){
//	var ZX_INVESTIGATE_DATE=$("#ZX_INVESTIGATE_DATE").combobox('getValue');
//	if(ZX_INVESTIGATE_DATE=='')
//	{
//		alert("请填写【调查日期】！");
//		return ;
//	}else{
//		var a = new Date(ZX_INVESTIGATE_DATE);  
//		var d = new Date();
//		if(a-d<-86400000)
//		{
//			alert("调查期限小于今天，请重新填写");
//			$("#ZX_INVESTIGATE_DATE").combobox('clear');
//			return ;
//		}
//	}
//	var CUST_ID = $("input[name='CUST_ID']:checked").val();
//	var ZX_ACCESS_TYPE = $("input[name='CUST_ID']:checked").attr("accType");
//	var ZX_YD_TYPE = "";
//	$("input[name='ZX_YD_TYPE']").each(function(){
//		if($(this).attr("checked")){
//			ZX_YD_TYPE += ","+$(this).val();
//		}
//	});
//	
//	var ZX_YD_TYPE = "";
//	$("input[name='ZX_YD_TYPE']").each(function(){
//		if($(this).attr("checked")){
//			ZX_YD_TYPE += ","+$(this).val();
//		}
//	});
//	
//	if(ZX_YD_TYPE==""||$("input[name='ZX_VISIT_ADDR']:checked").val()==undefined||$("#ZX_INVESTIGATE_DATE").datebox("getValue")==""){;
//		return alert("请选择必选项目");
//	}
	if($("input[name='ZX_VISIT_ADDR']:checked").val()==undefined||$("#ZX_REMARK").val()==""){;
		return alert("请选择必选项目");
    }else{
		//$("#save").linkbutton('disable');
		
		jQuery.ajax({
			type:'post',
			url:_basePath+"/returnVisit/ReturnVisit!doAddReturnVisitSave1.action",
//			data:"FLAG="+FLAG+"&TYPE="+TYPE+"&CUST_ID="+CUST_ID+"&ZX_YD_TYPE="+ZX_YD_TYPE+"&ZX_VISIT_ADDR="
//			     +$("input[name='ZX_VISIT_ADDR']:checked").val()+"&ZX_REMARK="+$("#ZX_REMARK").val()+"&PROJECT_ID="
//			     +$("#PROJECT_ID").val()+"&ZX_ACCESS_TYPE="+ZX_ACCESS_TYPE+"&TR_ID="+$("#TR_ID").val()+"&ZX_INVESTIGATE_DATE="
//			     +$("#ZX_INVESTIGATE_DATE").datebox("getValue"),
			data:"ZX_VISIT_ADDR="+$("input[name='ZX_VISIT_ADDR']:checked").val()+"&ZX_REMARK="+$("#ZX_REMARK").val()
			     +"&TR_ID="+$("#TR_ID").val()+"&PROJECT_ID="+$("#PROJECT_ID").val()+"&TYPE="+TYPE+"&FLAG="+FLAG,
			dataType:"json",
			success:function(json){
				if(json.flag){
					alert("家访任务信息保存成功！");
				}else{
					alert("家访任务信息保存失败！");
				}
			}
		});
	}
}
function saveL1Ask(FLAG,TYPE){
	if($("input[name='ZX_VISIT_ADDR']:checked").val()==undefined||$("#ZX_REMARK").val()==""){;
		return alert("请选择必选项目");
	}else{
		jQuery.ajax({
			type:'post',
			url:_basePath+"/returnVisit/ReturnVisit!doAddReturnVisitSave1.action",
			data:"ZX_VISIT_ADDR="+$("input[name='ZX_VISIT_ADDR']:checked").val()+"&ZX_REMARK="+$("#ZX_REMARK").val()
			+"&TR_ID="+$("#TR_ID").val()+"&PROJECT_ID="+$("#PROJECT_ID").val()+"&TYPE="+TYPE+"&FLAG="+FLAG,
			dataType:"json",
			success:function(json){
			if(json.flag){
				alert("家访任务信息保存成功！");
			}else{
				alert("家访任务信息保存失败！");
			}
		}
		});
	}
}

/**
 * 查看家访记录
 * @param tr_id   家访记录id
 * @param project_id   项目id
 * @param client_id    客户id
 * @param CUST_ID      客户编号
 * @param CLIENT_NAME  客户名称
 * @return
 */
function view(tr_id,project_id, client_id,CUST_ID,CLIENT_NAME){
	top.addTab("家访查看", _basePath+ "/returnVisit/ReturnVisit!toViewVisit.action?PROJECT_ID="+project_id+"&client_id="+client_id+"&CUST_ID="+CUST_ID+"&CLIENT_NAME="+CLIENT_NAME+"&TR_ID="+tr_id+"&date="+new Date().getTime());

}


/**
 * 修改家访记录
 * @param tr_id   家访记录id
 * @param project_id   项目id
 * @param client_id    客户id
 * @param CUST_ID      客户编号
 * @param CLIENT_NAME  客户名称
 * @return
 */
function update(tr_id,project_id, client_id,CUST_ID,CLIENT_NAME){
	top.addTab("家访修改", _basePath+ "/returnVisit/ReturnVisit!toUpdateVisit.action?PROJECT_ID="+project_id+"&client_id="+client_id+"&CUST_ID="+CUST_ID+"&CLIENT_NAME="+CLIENT_NAME+"&TR_ID="+tr_id+"&date="+new Date().getTime());

}

/**
 * 保存家访记录
 * @param FLAG
 * @param TYPE
 * @return
 */
function save1(FLAG,TYPE){	
	if(checkedData("#upVisit")>0){
		alert("请添加必选项目");
	}else {
		$("#upVisit").form('submit',{
			type:'post',
			 url: _basePath+"/returnVisit/ReturnVisit!doUpReturnVisitSave.action",
			success:function(json){
				json = $.parseJSON(json);
				if(json.flag){
					alert("家访任务信息保存成功！");
				}else{
					alert("家访任务信息保存失败！");
				}
			}
		});
	}		
}

function checkedData(obj){
	var count =0 ;
	$(obj+' [name]').each(function(){
		if($(this).val()== "ZX_REMARK"){	
			if($(this).attr("name")!=''){
			count++;
			}
		}
	});
	return count;
}

/**
 * 删除家访记录
 * 
 * @param tr_id
 * @return
 */
function del(tr_id){
	$.messager.confirm('警告','确定删除该条记录?',function(r){
	    if (r){
	       jQuery.ajax({
	    	   url:_basePath+"/returnVisit/ReturnVisit!doDelVisit.action",
	    	   type:"post",
	    	   data:"TR_ID="+tr_id,
	    	   dataType:"json",
	    	   success:function(json){
		    	//    json = $.parseJSON(json);
					if(json.flag){
						alert("删除成功");
						tofindData()
					}else{
						alert("删除失败");
					}
	       	   }
	       });
	    }
	});
}

function updateQixian(tr_id, qixian){
	$.messager.confirm('警告','确定修改该条记录?',function(r){
	    if (r){
	    	if(qixian == "undefined"){
				return alert("请选择调查期限!");
			}else{
				jQuery.ajax({
			    	   url:_basePath+"/returnVisit/ReturnVisit!doUpINVESTIGATE.action",
			    	   type:"post",
			    	   data:"TR_ID="+tr_id+"&ZX_INVESTIGATE_DATE="+qixian,
			    	   dataType:"json",
			    	   success:function(json){
				    	//    json = $.parseJSON(json);
							if(json.flag){
								alert("期限修改成功, 调查期限调整为： "+qixian);
								tofindData();
							}else{
								alert("期限修改失败");
							}
			       	   }
			       });
			}
	    }
	});
}

/******************************************************以下为页面单元格可操作设置*****************************************************************************************/
$.extend($.fn.datagrid.methods, {
	editCell: function(jq,param){
		return jq.each(function(){
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field){
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for(var i=0; i<fields.length; i++){
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});

function onClickCell(index, field){
	if (endEditing()){
		$('#insDialog').datagrid('selectRow', index)
				.datagrid('editCell', {index:index,field:field});
		editIndex = index;
	}
}

var editIndex = undefined;
function endEditing(){
	if (editIndex == undefined){return true;}
	if ($('#insDialog').datagrid('validateRow', editIndex)){
		$('#insDialog').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
/******************************************************以上为页面单元格可操作设置*****************************************************************************************/