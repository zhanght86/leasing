function newOne(){
	top.addTab("添加组任务", _basePath+"/base/task/TaskGroup!add.action");
}
//添加人员页面
function PersonnelAdd(){
	$("#PersonnelDiv").dialog({title:'添加人员'});
	$("#PersonnelDiv").dialog('open');
	$("#outpersonnelname").val("");
	$("#WEIGHT").val("");
}
function closePersonnelDiv(){
    $('#PersonnelDiv').dialog('close');
}
function certain()
{
		var USERID=$("#outpersonnelname option:selected").val();
		var USERNAME=$("#outpersonnelname option:selected").text();
		var ORGID=$("#outpersonnelname option:selected").attr("ORGID");
		var WEIGHT=$("#WEIGHT").val();
		if(parseInt(WEIGHT)>10)
		{
			alert("权重应小于10");
			return false;
		}
		var html="";
		var num=1;
		html="<tr class='addData'><td align='right' class='serial'>"+num+"</td><td align='center'><input type='hidden' name='USERID' value='"+USERID+"'/><input type='hidden' name='ORGID' value='"+ORGID+"'/><input type='hidden' name='USERNAME' value='"+USERNAME+"'/>"+USERNAME+"</td><td align='center'><input type='hidden' name='WEIGHT' value='"+WEIGHT+"'/>"+WEIGHT+"</td><td align='center'><a href=javascript:void(0) onclick=del1(this)>删除</a></td></tr>";
		$("#PERSONNEL0").after(html);
		$(".serial").each(function(){
			$(this).text(num);
			num=num+1;
		});
		closePersonnelDiv();
}
function del1(e)
{
	var ele=$(e);
	ele.parent().parent().remove();
	var num=1;
	$(".serial").each(function(){
		$(this).text(num);
		num=num+1;
	});
	$("#serialnumber").val(num);
}
function del2(e)
{
	var ele=$(e);
	ele.parent().parent().remove();
}
function RuleAdd()
{
	var RuleNext=$("input[name='RULEID']").val();
	if(RuleNext !=null)
	{
		alert("规则只能选中一条");
	}else{
	var TASK_NAME=$("#TASK_NAME option:selected").val();
	jQuery.ajax({
		url: _basePath+"/base/task/TaskGroup!selTaskAllication.action",
		data: "TASK_NAME="+TASK_NAME,
		dataType:"json",
		success: function(json){
			if(json.flag)
			{
				$("#RuleOut").dialog({
					autoOpen:false,
					title:'规则列表',
					reaziable:true,
					modal:true,
					width:300,
					height:200,
					cache: true,
					iconCls : "icon-group",
					buttons : "#RuleOut-buttons"
				});
				$("#RuleOut").dialog('close');
//				jQuery.get("TaskGroup!getTaskAllication.action?TASK_NAME="+TASK_NAME,function(html){
//					$("#RuleOut").html(html);	
//				});
				$("#RuleOut").dialog("open");
			}else
			{
				alert(json.msg);
			}
		}
	 });
	}
}
function selRenter(){
	var RULE_NAME = $("#RULE_NAME").val();
	var TASK_NAME=$("#TASK_NAME option:selected").val();
	jQuery.ajax({
		url : "TaskGroup!getTaskAllication.action",
		data : {RULE_NAME : RULE_NAME,TASK_NAME : TASK_NAME},
		type: 'POST',
		dataType:"json",
		success : function(json){
			if(json.flag){
				$("#RKCONTENT").html(json.data);
			}else{
				alert(json.msg);
			}
		}
	});
}
function doRuleY(e)
{
	var ele=$(e);
	var TASK_NAME=$("#TASK_NAME option:selected").val();
	var html="<tr><td align='right'>1<input type='hidden' name='RULEID' value='"+ele.parent().attr("sid")+"'/></td><td align='center'>"+TASK_NAME+"</td><td align='center'>"+ele.parent().attr("sname")+"</td><td align='center'><a href=javascript:void(0) onclick=del2(this)>删除</a></td></tr>";
	$("#RULE").after(html);
	$("#RuleOut").dialog("close");
}
function updateOne(row){
	//var row = $('#pageTable').datagrid('getSelected');
	if (row){
		top.addTab("修改组任务",_basePath+"/base/task/TaskGroup!upd.action?ID="+row.ID);
    }
}

function del(row){
	//var row = $('#pageTable').datagrid('getSelected');
	if (row){
		 if(confirm("确定要删除该规则吗？")){
			 jQuery.ajax({
				url: _basePath+"/base/task/TaskGroup!del.action",
				data: "ID="+row.ID,
				dataType:"json",
				success: function(res){
					if(res.flag==true){
						$.messager.alert("提示",res.msg);
						$('#pageTable').datagrid('reload');
				   }
				   else{
					   $.messager.alert("提示",res.msg);
					   alert("操作失败请重试！");
				   }
				}
					 
			 });
		 }
	}
}