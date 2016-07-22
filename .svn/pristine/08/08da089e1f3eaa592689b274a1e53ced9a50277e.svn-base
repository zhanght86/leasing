$(function() {
	$('#pageTable').datagrid({
		url:_basePath+"/pendingpool/PendingPool!toMgPendingPool.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		columns:[[
		          {field:'INC_NAME',align:'center',width:40,title:'来款人'},
		          {field:'INC_BANK_OPEN',align:'center',width:40,title:'开户行'},
		          {field:'INC_BANK_ACCOUNT',align:'center',width:40,title:'来款账号'},
		          {field:'INC_TYPE',align:'center',width:40,title:'来款方式'},
		          {field:'INC_MONEY',align:'center',width:40,title:'来款金额'},	   
		          {field:'CANUSE_MONEY',align:'center',width:40,title:'可用额度'},
		          {field:'USE_MONEY',align:'center',width:40,title:'已使用额度'},
		          {field:'INC_TIME',align:'center',width:40,title:'来款日期'},
		          {field:'AFFIRM_TYPE',align:'center',width:40,title:'归类集类型',formatter:function(value,row,index){
		        	  if(value==undefined){
		        		  return "";
		        	  }else if(value=="1"){
		        		  return "供应商";
		        	  }else if(value=="2"){
		        		  return "承租人";
		        	  }
		          }},
		          {field:'AFFIRM_NAME',align:'center',width:40,title:'归类集对象名称',formatter:function(value,row,index){
		        	  if(value==undefined){
		        		  return "<a href='javascript:void(0);' onclick='toAffirmPer("+row.INCOME_ID+")'>未认款</a>";
		        	  }else{
		        		  return value;
		        	  }
		          }},
		          {field:'STATUS',align:'center',width:40,title:'状态'},
		          {field:'operation',align:'center',width:40,title:'操作',formatter:function(value,row,index){
		        	  return "<a href='#' onclick='toUpdate("+value+",\""+row.STATUS+"\")'>修改</a> | <a href='#' onclick='toCancellation("+value+",\""+row.STATUS+"\")'>作废</a>";
		          }}
		          ]]
	});
});

/**
 * 查询模糊
 * @author 杨雪
 * @return
 */
function toSeacher() {
	$('#pageTable').datagrid('load', {"param":getFromData("#pageForm")});
}

/**
 * 清空查询数据
 * @author 杨雪
 * @return
 */
function clearQuery(){
	$("#INC_TIME").datebox('clear');
	$("#INC_TIME1").datebox('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}

//提交添加来款
function toCommitData(){
	if(checked()){
		$("#doAddPending").form('submit',{
			dataType:'json',
			success:function(json){
				var obj = eval('('+json+')');
				if(obj.flag==true){
					alert("来款添加成功");
					$("#toAddPending").dialog("close");
					window.location.href = _basePath+"/pendingpool/PendingPool.action";
				}else{
					alert("来款添加失败");
					$("#toAddPending").dialog("close");
					window.location.href = _basePath+"/pendingpool/PendingPool.action";
				}
			}
		});
	}
}

//修改查询数据
function toUpdate(val,val0){
	if(val0 == "已核销"){
		alert("该数据为已核销数据， 请选择未核销的数据进行修改");
	}else{
		$.ajax({
			url:_basePath+"/pendingpool/PendingPool!toShowPending.action?INCOME_ID="+val,
		   type:"post",
		   dataType:"json",
		   success:function(json){
				if(json.flag==true){
					$("#INCOME_ID1_").val(json.data.INCOME_ID);
					$("#DEPOSIT_ID_1").val(json.data.DEPOSIT_ID);
					$("#INC_NAME1").val(json.data.INC_NAME);
					//$("#INC_TYPE1").val(json.data.INC_TYPE);
					$("#INC_TYPE1 option").each(function(){
						if($(this).val() == json.data.INC_TYPE){
							$("#INC_TYPE1 option").attr("selected",true);
						}
					});
					$("#INC_BANK_OPEN1").val(json.data.INC_BANK_OPEN);
					$("#INC_BANK_ACCOUNT1").val(json.data.INC_BANK_ACCOUNT);
					$("#INC_MONEY1").val(json.data.INC_MONEY);
					$("#INC_TIME3").datebox('setValue',json.data.INC_TIME);
					$("#toUpdatePending").dialog("open");
				}else{
					alert("查询数据有误， 请联系系统管理员");
				}
		   }
		});
	}
}

//修改操作
function toUpdateData(){
	if(checked1()){
		$("#doUpdateData").form('submit',{
			dataType:'json',
			success:function(json){
				var obj = eval('('+json+')');
				if(obj.flag==true){
					alert("来款修改成功");
					$("#toUpdatePending").dialog("close");
					window.location.href = _basePath+"/pendingpool/PendingPool.action";
				}else{
					alert("来款修改失败");
					$("#toUpdatePending").dialog("close");
					window.location.href = _basePath+"/pendingpool/PendingPool.action";
				}
			}
		});
	}
}

//来款资金作废
function toCancellation(val1, val2){
	if(val2 == "已核销"){
		alert("该数据为已核销数据， 请选择未核销的数据");
	}else{
		$.ajax({
			url:_basePath+"/pendingpool/PendingPool!doZFfunds.action?INCOME_ID="+val1,
			type:"post",
			dataType:"json",
			success:function(json){
				if(json.flag==true){
					alert("来款资金作废成功");
					window.location.href = _basePath+"/pendingpool/PendingPool.action";
				}else{
					alert("来款资金作废失败， 请联系系统管理员");
				}
			}
		});
	}
}

/**
 * 选择认款人
 * @param  val: 来款id
 */
function toAffirmPer(val){
	$("#INCOME_ID_1").val(val);
	$("#toChosePer").dialog('open');
}

//选择人搜索
function toChosePerson(){
	var AFFIRM_TYPE = $("#AFFIRM_TYPE_1").attr("selected",true).val();
	var AFFIRM_NAME = $("#AFFIRM_NAME_1").val();
	jQuery.get(_basePath+"/pendingpool/PendingPool!toChosePer.action?AFFIRM_TYPE="+encodeURI(AFFIRM_TYPE)+"&AFFIRM_NAME="+encodeURI(AFFIRM_NAME),function(html){
		$("#getPerson").html(html);
	});
}

/**
 * 认款
 * @param val0: 认款人id
 * @param val1：认款人名称
 */
function toAllocationPer(val0, val1){
	var AFFIRM_TYPE = $("#AFFIRM_TYPE_1").attr("selected",true).val();
	$.ajax({
		url:_basePath+"/pendingpool/PendingPool!doAllocationPer.action",
		type:"post",
		data:"AFFIRM_ID="+val0+"&AFFIRM_NAME="+val1+"&AFFIRM_TYPE="+AFFIRM_TYPE+"&INCOME_ID="+$("#INCOME_ID_1").val(),
		dataType:"json",
		success:function(json){
			if(json.flag==true){
				alert("归类集对象名称确认成功");
				window.location.href = _basePath+"/pendingpool/PendingPool.action";
				$("#toChosePer").dialog('colse');
			}else{
				alert("归类集对象名称确认失败");	
				$("#toChosePer").dialog('colse');
			}
		}
	});
}

//添加弹出框
function toAddPending(div){
	$("#"+div).dialog('open');
}

//关闭弹出框
function toClostDialog(div){
	$("#"+div).dialog("close");
}

/**
 * 页面提交数据校验
 * @author 杨雪
 * @date 2013-09-29
 * @return
 */
function checked(){
	var flag = true;
	$(".warm").each(function(){
		if($(this).val() != null || $(this).datebox('getValue')!=null || $(this).attr("selected",true).val() != null){
			$(this).removeClass("red");
		}else{
			$(this).addClass("red");
			flag = false;
		}
	});
	return flag;
}

/**
 * 页面提交数据校验-修改
 * @author 杨雪
 * @date 2013-09-29
 * @return
 */
function checked1(){
	var flag = true;
	$(".warm1").each(function(){
		if($(this).val() != null || $(this).datebox('getValue')!=null || $(this).attr("selected",true).val() != null){
			$(this).removeClass("red");
		}else{
			$(this).addClass("red");
			flag = false;
		}
	});
	return flag;
}