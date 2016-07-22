$(document).ready(function(){
	$("#insDialog").datagrid({
		url:_basePath+"/analysisBySynthesis/Industry!toMgIndustry.action",//?CREDIT_ID=+$!param.CREDIT_ID,
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:true,
		pageSize:20,
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		columns:[[
		          {field:'HY_ID',width:150,align:'center',title:'操作',formatter:getValueView},
		          {field:'INDUSTRY_CODE',width:220,align:'center',title:'行业代码'}, 
				  {field:'INDUSTRY_NAME',width:120,align:'center',title:'行业名称'}
				 
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
		top.addTab("行业添加", _basePath+ "/analysisBySynthesis/Industry!toAddIndustry.action?date="+new Date().getTime());
	});
});

//数据列表中操作列： 查看， 修改， 删除
function getValueView(val, row) {
	return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' onclick='toViewIndustry("+ row.HY_ID+ ")'>查看</a> " +
	 	   "| <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' onclick='toUpdateIndustry("+ row.HY_ID+ ")'>修改</a> "+
		   "| <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' onclick='toDelIndustry("+ row.HY_ID+ ")'>删除</a> "+
		   "| <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' onclick='toViewFinancial("+ row.HY_ID+ ")'>财报查看</a> ";
} 

//页面查询按钮操作
function tofindData() {
	var INDUSTRY_CODE = $("input[name='INDUSTRY_CODE']").val();//行业编号
	var INDUSTRY_NAME = $("input[name='INDUSTRY_NAME']").val();//行业名称
	$('#insDialog').datagrid('load', {
		"INDUSTRY_CODE" : INDUSTRY_CODE,
		"INDUSTRY_NAME" : INDUSTRY_NAME
	});
}

//查看行业信息
function toViewIndustry(value ) {
	top.addTab("行业查看", _basePath+ "/analysisBySynthesis/Industry!toShowIndustry.action?HY_ID="+value+"&tab=update"+"&date="+new Date().getTime());
}

//修改行业信息
function toUpdateIndustry(hy_id) {
	top.addTab("行业修改", _basePath+ "/analysisBySynthesis/Industry!toUpdateIndustry.action?HY_ID="+hy_id+"&tab=update"+"&date="+new Date().getTime());
}

//删除行业信息  @author 杨雪
function toDelIndustry(hy_id){
	if (confirm("确认删除行业简介？")) {
		jQuery.ajax({
			url:_basePath+'/analysisBySynthesis/Industry!delINDUSTRY.action?HY_ID='+hy_id,
			type:'post',
			dataType:"json",
			success:function(json){
			    if(json.flag){
			    	alert("删除成功");
			    	tofindData();//画龙点睛之笔 2014-4-25 13:43:10
			    }else{
			    	alert("删除失败");
			    	window.location.href="../analysisBySynthesis/Industry!toMgIndustry.action";
			    }
			}
		});
	}
}

/********************************************************************************************************************
 ********************************************************************************************************************  
 ***************************以下为财务报表的相关内容  @author 杨雪   @date 2015-02-27**********************************
 ********************************************************************************************************************
 *******************************************************************************************************************/
//加载行业财报查看页面
function toViewFinancial(hy_id) {
	top.addTab("行业财报查看", _basePath+ "/analysisBySynthesis/Industry!toMgFinancialReport.action?INDUSTRY_ID="+hy_id+"&date="+new Date().getTime());
}