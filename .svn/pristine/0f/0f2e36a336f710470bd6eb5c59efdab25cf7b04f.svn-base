$(document).ready(function(){
	$("#insDialog").datagrid({
		url:_basePath+"/mortgage/Mortgage!toMgMortgagePage.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:true,
		pageSize:20,
		fit:true,
		//fitColumns:true,//设置为 true，则会自动扩大或缩小列的尺寸以适应网格的宽度并且防止水平滚动。
		toolbar:'#pageForm',
		//onClickCell:onClickRow,//当用户点击单元格式调用
		frozenColumns:[[{field:'TR_ID',width:150,align:'center',title:'操作', formatter:function(value,rows,index){
//						  if((rows.M_STATUS=="0"||rows.M_STATUS==undefined)&&rows.PAYMENT_HEAD_ID!=undefined){
//							  return "<a href='#' onclick='toAdd("+rows.FPE_ID+"\,\""+rows.CLIENT_NAME+"\")'>抵押</a> ";
//						  }
//						  else if(rows.M_STATUS=="1"){
//							  return  "<a href='#'onclick='view1("+rows.M_ID+"\,\""+rows.FPE_ID+"\")'>查看</a> " +
//						  		"|| <a href='#'onclick='updateData("+rows.M_ID+"\,\""+rows.FPE_ID+"\")'>修改</a> " ;
//						  		"|| <a href='#'onclick='jieya("+rows.M_ID+"\,"+rows.STATUS+")'>解押</a> ";
//						  }
						  if(rows.M_STATUS=="2"||rows.M_STATUS=="1"){
							  return "<a href='#'onclick='view2("+rows.M_ID+"\,\""+rows.FPE_ID+"\")'>查看</a>";
						  }
					}}]],
		columns:[[
		          {field:'M_STATUS',width:90,align:'center',title:'抵押状态',formatter:function(value,rows,index){
		        	  if(value=="0"||value==undefined){
		        		  return "未抵押";
		        	  }else if(value=="1"){
		        		  return "已抵押";
		        	  }else {
		        		  return "已解押";
		        	  }
		          }},
		          {field:'LEASE_CODE',width:180,align:'center',title:'融资租赁合同号'}, 
				  {field:'CLIENT_NAME',width:200,align:'center',title:'客户名称'},
				  {field:'PRODUCT_NAME',width:120,align:'center',title:'品牌名称'},
				  {field:'CAR_SYMBOL',width:130,align:'center',title:'合格证号'}, 
				  {field:'SPEC_NAME',width:130,align:'center',title:'品牌型号'},
				  {field:'SHANGPAIDATE',width:130,align:'center',title:'上牌时间'},
				  {field:'FS_ADDRESS',width:130,align:'center',title:'上牌地点'},
				  {field:'FS_DANWEI',width:130,align:'center',title:'上牌单位'},
//				  {field:'FACHEDATE',width:130,align:'center',title:'发车时间'},
				  {field:'CARNUM',width:130,align:'center',title:'车牌号'},
				  {field:'MORTGAGE_TIME',width:130,align:'center',title:'抵押时间'},
				  {field:'MORTGAGE_PER',width:130,align:'center',title:'抵押权人'},
				  {field:'EXTRACT_TIME',width:130,align:'center',title:'解压时间'}
		         ]]	
	});
	
	//页面清空按钮操作
	$("#qingkong").click(function(){
		$(".paramData").each(function(){
			$(this).val("");
		});
	});
});

//页面查询
function tofindData(){
	var LEASE_CODE = $("#LEASE_CODE").val();
	var CLIENT_NAME = $("#CLIENT_NAME").val();
	var PRODUCT_NAME = $("#PRODUCT_NAME").val();
	var CAR_SYMBOL = $("#CAR_SYMBOL").val();
	$('#insDialog').datagrid('load', {"LEASE_CODE":LEASE_CODE,"CLIENT_NAME":CLIENT_NAME,"CAR_SYMBOL":CAR_SYMBOL,"PRODUCT_NAME":PRODUCT_NAME});
}

//关闭对话框
function closeDailog(div){
	$("#"+div).dialog('close');
}

/**
 * 添加抵押对话款
 * @param fe_id
 * @return
 */
function toAdd(fe_id,CLIENT_NAME){	
	$("#FE_ID_add").val(fe_id);
	$("#ID_add").val(fe_id);
	$("#CUST_NAME").val(CLIENT_NAME);
	$("#addMortgageP").dialog('open');
}

//保存抵押信息
function save(){
	$("#addMortgage").form('submit',{
		type:'post',
		 url: _basePath+"/mortgage/Mortgage!doAddMortgage.action",
		success:function(json){
			json = $.parseJSON(json);
			if (json.flag) {
				$.messager.alert("提示 ： ",json.msg);
				$("#addMortgageP").dialog('close');
				tofindData();
			} else {
				$.messager.alert("提示", json.msg);
			}
		}
	});
}

//抵押流程页面
function view(PAYMENT_HEAD_ID){
	top.addTab("抵押", _basePath+ "/mortgage/Mortgage!toMgMortgageEqu.action?PAYMENT_HEAD_ID="+PAYMENT_HEAD_ID+"&date="+new Date().getTime());
}

//查看
function view1(M_ID,FPE_ID){
	jQuery.ajax({
		type:"post",
		url:_basePath+"/mortgage/Mortgage!toViewMortgage.action?M_ID="+M_ID+"&ID="+FPE_ID,
		dataType:"json",
		success:function(json){
			$("#viewMortgageP").dialog('open');
			$("#MORTGAGE_TIME").val(json.mortgage.MORTGAGE_TIME);
			$("#MORTGAGE_PER").val(json.mortgage.MORTGAGE_PER);
			$("#TRANSACT_PER").val(json.mortgage.TRANSACT_PER);
			
			$("#SHANGPAIDATE").val(json.shangpai.SHANGPAIDATE);
			$("#ADDRESS").val(json.shangpai.ADDRESS);
			$("#DANWEI").val(json.shangpai.DANWEI);
			
			$("#FACHEDATE").val(json.shangpai.FACHEDATE);
			$("#CARNUM").val(json.shangpai.CARNUM);
			$("#BEIZHU").val(json.shangpai.BEIZHU);
		},
		error:function(){
			alert("错误! 请联系管理员");
		}
	});
}

//修改查看
function updateData(M_ID,FPE_ID){
	jQuery.ajax({
		type:"post",
		url:_basePath+"/mortgage/Mortgage!toUpMortgage.action?M_ID="+M_ID+"&ID="+FPE_ID ,
		dataType:"json",
		success:function(json){
			$("#upMortgageP").dialog('open');
			$("#M_ID1").val(M_ID);
			$("#MORTGAGE_TIME1").datebox("setValue",json.mortgage.MORTGAGE_TIME);
			$("#MORTGAGE_PER1").val(json.mortgage.MORTGAGE_PER);
			$("#TRANSACT_PER1").val(json.mortgage.TRANSACT_PER);
			
			$("#ID_1").val(FPE_ID);
			$("#SHANGPAIDATE1").datebox("setValue",json.shangpai.SHANGPAIDATE);
			$("#ADDRESS1").val(json.shangpai.ADDRESS);
			$("#DANWEI1").val(json.shangpai.DANWEI);
			
			$("#FACHEDATE1").datebox("setValue",json.shangpai.FACHEDATE);
			$("#CARNUM1").val(json.shangpai.CARNUM);
			$("#BEIZHU1").val(json.shangpai.BEIZHU);
		},
		error:function(){
			alert("错误! 请联系管理员");
		}
	});
}

//保存抵押修改信息
function save1(){
	$("#upMortgage").form('submit',{
		type:'post',
		 url: _basePath+"/mortgage/Mortgage!doUpMortgage.action",
		success:function(json){
			json = $.parseJSON(json);
			if (json.flag) {
				$.messager.alert("提示 ： ",json.msg);
				$("#upMortgageP").dialog('close');
				tofindData();
			} else {
				$.messager.alert("提示", json.msg);
			}
		}
	});
}

//解压界面
function jieya(M_ID,STATUS){
	if(STATUS==0||STATUS==3||STATUS==6){
		$("#M_ID2").val(M_ID);
		$("#jieyaU").dialog('open');
	}else {
		return alert("请选择项目为正常, 正常结清， 提请结清的项目进行解压");
	}
}

//保存抵押修改信息
function save2Jie(){
	
	$("#jieya").form('submit',{
		type:'post',
		 url: _basePath+"/mortgage/Mortgage!doUpMortgageJ.action",
		success:function(json){
			json = $.parseJSON(json);
			if (json.flag) {
				$.messager.alert("提示 ： ",json.msg);
				$("#jieyaU").dialog('close');
				tofindData();
			} else {
				$.messager.alert("提示", json.msg);
			}
		}
	});
}

//解压后查看
function view2(M_ID,FPE_ID){
	jQuery.ajax({
		type:"post",
		url:_basePath+"/mortgage/Mortgage!toViewMortgage.action?M_ID="+M_ID+"&ID="+FPE_ID,
		dataType:"json",
		success:function(json){
			$("#view1MortgageP").dialog('open');
			$("#1MORTGAGE_TIME").val(json.mortgage.MORTGAGE_TIME);
			$("#1MORTGAGE_PER").val(json.mortgage.MORTGAGE_PER);
			$("#1TRANSACT_PER").val(json.mortgage.TRANSACT_PER);
			
			$("#1ASSIGNEE").val(json.mortgage.ASSIGNEE);
			$("#1ASSIGN_TIME").val(json.mortgage.ASSIGN_TIME);
			$("#1EXTRACT_TIME").val(json.mortgage.EXTRACT_TIME);
		},
		error:function(){
			alert("错误! 请联系管理员");
		}
	});
}

