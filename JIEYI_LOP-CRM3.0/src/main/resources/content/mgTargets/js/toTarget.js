var count =0;
$(document).ready(function(){
	$("#insDialog").datagrid({
		url:_basePath+"/target/Target!toMgTarget.action",//?CREDIT_ID=+$!param.CREDIT_ID,
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:true,
		pageSize:20,
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		columns:[[
					{field:'TR_ID',width:150,align:'center',title:'操作', formatter:function(value,rows,index){
						  return "<a href='#' onclick='view("+rows.TR_ID+")'>查看</a> || " +
						  		"<a href='#'onclick='updateData("+rows.TR_ID+")'>修改</a> " +
						  		"|| <a href='#' onclick='del("+rows.TR_ID+")'>删除</a>";
					}},
		          {field:'SUP_SHORTNAME',width:220,align:'center',title:'指标公司'}, 
				  {field:'TARGET_PRICE',width:120,align:'center',title:'指标单价'},
				  {field:'DISTRICT',width:120,align:'center',title:'区域'},
				  {field:'SATRT_DATE',width:220,align:'center',title:'起始时间'}, 
				  {field:'END_DATE',width:120,align:'center',title:'结束时间'},
				  {field:'TARGET_TOTAL',width:120,align:'center',title:'指标总数'},
				  {field:'TARGET_SY',width:120,align:'center',title:'指标剩余数'},
				  {field:'TARGET_YSY',width:120,align:'center',title:'指标使用数'}
				  
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
	var SUP_SHORTNAME = $("input[name='SUP_SHORTNAME']").val();//行业编号
	$('#insDialog').datagrid('load', {"SUP_SHORTNAME" : SUP_SHORTNAME});
}

//添加指标单价
function save(){
	
	var target = [];
	$(".detailTarget_").each(function(){
		var target_ = {};
		target_.DETAIL_MONEY = $(this).find("input[name='DETAIL_MONEY']").val();
		target_.START_TIME = $(this).find("input[name='START_TIME']").val();
		target_.END_TIME = $(this).find("input[name='END_TIME']").val();
		target.push(target_);
	});
	
	var resutlData = {
			target:target,
			SUPPLIERS_ID:$("#SUPPLIERS_ID_").combobox("getValue"),
			TARGET_PRICE:$("#TARGET_PRICE_").val(),
			DISTRICT:$("#DISTRICT_").val(),
			TARGET_TOTAL:$("#TARGET_TOTAL_").val(),
			SATRT_DATE:$("#SATRT_DATE_").datebox("getValue"),
			END_DATE:$("#END_DATE_").datebox("getValue")
	};
	
	jQuery.ajax({
		type:'post',
		url: _basePath+"/target/Target!doAddTarget.action",
		data:"resutlData="+encodeURI(JSON.stringify(resutlData)),
		dataType:"json",
		success:function(json){
			//json = $.parseJSON(json);
			if (json.flag) {
				alert("提示 ： "+ json.msg);
				$("#addPriceP").dialog('close');
				tofindData();
			} else {
				$.messager.alert("提示", json.msg);
			}
		}
	});
}

//查看指标价格
function view(TR_ID){
	jQuery.ajax({
		type:"post",
		url:_basePath+"/target/Target!toViewTarget.action?TR_ID="+TR_ID,
		dataType:"json",
		success:function(json){
			$("#viewPrivceP").dialog('open');
			$("#SUPPLIERS_ID").val(json.target.SUP_SHORTNAME);
			$("#TARGET_PRICE").val(json.target.TARGET_PRICE);
			$("#DISTRICT").val(json.target.DISTRICT);
			$("#TARGET_TOTAL").val(json.target.TARGET_TOTAL);
			$("#SATRT_DATE").val(json.target.SATRT_DATE);
			$("#END_DATE").val(json.target.END_DATE);
			$("#REMARK").text(json.target.REMARK);
			
			 var tbody = "";
			 $.each(json.detail, function (n, value) {
	               var trs = "";
	               trs += "<tr class=''><td><input type='text' name='DETAIL_MONEY' value='"+value.DETAIL_MONEY+"' readonly/></td>" +
	               		  "<td><input type='text' name='START_TIME'  value='"+value.START_TIME+"' class='easyui-datebox' readonly/></td>" +
	               		  "<td><input type='text' name='END_TIME'   value='"+value.END_TIME+"' class='easyui-datebox' readonly/></td>";
	               tbody += trs;
	         });
			 $("#dataDetail_A").append(tbody);
		}
	});
}

//修改指标价格
function updateData(TR_ID){
	jQuery.ajax({
		type:"post",
		url:_basePath+"/target/Target!toViewTarget.action?TR_ID="+TR_ID,
		dataType:"json",
		success:function(json){
			$("#upPrivceP").dialog('open');
			
			$("#SUPPLIERS_ID1 option").each(function(){
				if(json.SUP_ID==$(this).val()){
					$(this).find("option").attr("selected",true);
				}
			});
			
			$("#TR_ID").val(json.target.TR_ID);
			$("#TARGET_PRICE1").val(json.target.TARGET_PRICE);
			$("#DISTRICT1").val(json.target.DISTRICT);
			$("#TARGET_TOTAL1").val(json.target.TARGET_TOTAL);
			$("#SATRT_DATE1").datebox('setValue',json.target.SATRT_DATE);
			$("#END_DATE1").datebox('setValue',json.target.END_DATE);
			$("#REMARK1").text(json.target.REMARK);
			
			
			var tbody = "";
			 $.each(json.detail, function (n, value) {
	               var trs = "";
	               trs += "<tr class='detailTarget_e'><td><input type='hidden' name='DETAIL_ID' value='"+value.DETAIL_ID+"'/><input type='text' name='DETAIL_MONEY' value='"+value.DETAIL_MONEY+"'/></td>" +
	               		  "<td><input type='text' name='START_TIME'  value='"+value.START_TIME+"' class='easyui-datebox' id='START_TIME"+value.DETAIL_ID+"'/></td>" +
	               		  "<td><input type='text' name='END_TIME'   value='"+value.END_TIME+"' class='easyui-datebox' id='END_TIME"+value.DETAIL_ID+"'/></td>";
	               tbody += trs;
	            //   $("#START_TIME"+value.DETAIL_ID).datebox();
	           	//   $("#END_TIME"+value.DETAIL_ID).datebox();
	         });
			 $("#dataDetail_B").append(tbody);
			 $("#dataDetail_B").find("input[name=START_TIME]").datebox();
			 $("#dataDetail_B").find("input[name=END_TIME]").datebox();
		}
	});
}

//保存修改信息
function save1(){
	
	var target = [];
	$(".detailTarget_e").each(function(){
		var target_ = {};
		target_.DETAIL_ID = $(this).find("input[name='DETAIL_ID']").val();
		target_.DETAIL_MONEY = $(this).find("input[name='DETAIL_MONEY']").val();
		target_.START_TIME = $(this).find("input[name='START_TIME']").val();
		target_.END_TIME = $(this).find("input[name='END_TIME']").val();
		target.push(target_);
	});
	
	var resutlData = {
			target:target,
			FR_ID:$("#TR_ID").val(),
			SUPPLIERS_ID:$("#SUPPLIERS_ID1").combobox("getValue"),
			TARGET_PRICE:$("#TARGET_PRICE1").val(),
			DISTRICT:$("#DISTRICT1").val(),
			TARGET_TOTAL:$("#TARGET_TOTAL1").val(),
			SATRT_DATE:$("#SATRT_DATE1").datebox("getValue"),
			END_DATE:$("#END_DATE1").datebox("getValue")
	};
	//$("#upPrivce").form('submit',{
	jQuery.ajax({
		type:'post',
		url: _basePath+"/target/Target!doUpTarget.action",
		data:"resutlData="+encodeURI(JSON.stringify(resutlData)),
		dataType:"json",
		success:function(json){
			//json = $.parseJSON(json);
			if (json.flag) {
				alert("提示 ： "+ json.msg);
				$("#upPrivceP").dialog('close');
				tofindData();
			} else {
				$.messager.alert("提示", json.msg);
			}
		}
	});
	//});
}

//删除指标单价
function del(TR_ID){
	$.messager.confirm('提示', "确认删除该条数据?", function(r){
		if(r){
			jQuery.ajax({
				type:"post",
				url:_basePath+"/target/Target!doDelTarget.action?FR_ID="+TR_ID,
				dataType:"json",
				success:function(json){
					//json = $.parseJSON(json);
					if (json.flag) {
						alert("提示 ： "+ json.msg);
						tofindData();
					} else {
						$.messager.alert("提示", json.msg);
					}
				}
			});
		}
	});
}

function add(obj){
	$("#"+obj).append($("<tr class='detailTarget_'>")
			.append("<td><input type='text' name='DETAIL_MONEY'/></td>")
			.append("<td><input type='text' name='START_TIME' class='easyui-datebox' id='START_TIME"+count+"'/></td>")
			.append("<td><input type='text' name='END_TIME' class='easyui-datebox' id='END_TIME"+count+"'/></td>")
			.append("<td><a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' plain='true'  onclick='del2(this)'>删 除</a></td>"));	
	$("#START_TIME"+count).datebox();
	$("#END_TIME"+count).datebox();
	count++;
}

function add1(obj){
	$("#"+obj).append($("<tr class='detailTarget_e'>")
			.append("<td><input type='text' name='DETAIL_MONEY'/></td>")
			.append("<td><input type='text' name='START_TIME' class='easyui-datebox' id='START_TIME"+count+"'/></td>")
			.append("<td><input type='text' name='END_TIME' class='easyui-datebox' id='END_TIME"+count+"'/></td>")
			.append("<td><a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' plain='true'  onclick='del2(this)'>删 除</a></td>"));	
	$("#START_TIME"+count).datebox();
	$("#END_TIME"+count).datebox();
	count++;
}

//关闭对话框
function closeDailog(div){
	$("#"+div).dialog('close');
}