$(function() {
	$('#morManagerMg').datagrid({
		url:_basePath+"/mortgageProduct/MortgageProduct!toMgMorgage.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:true,		
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		columns:[[ 	 	 	 		 	 		
		          {field:'CLINET_NAME',align:'center',width:40,title:'承租人'},
		          {field:'MORTGATE_STATUS',align:'center',width:40,title:'状态',formatter:function(value,rowData,rowIndex){
		        	  if(value=='0'){
		        		  return "未抵押";
		        	  }else if(value=='1'){
		        		  return "已抵押 ";
		        	  }else if(value=='2'){
		        		  return "已质押  ";
		        	  }else {
		        		  return "已解押";
		        	  }
		          }},
		          {field:'EQUIPMENINFOS',align:'center',width:40,title:'物品名称'},
		          {field:'AMOUNT',align:'center',width:40,title:'数量'},
		          {field:'zhiliang',align:'center',width:40,title:'质量及状况'},
		          {field:'LEASE_TOPRIC',align:'center',width:40,title:'评估价值（万元）'},
		          {field:'baoxiandanhao',align:'center',width:40,title:'保险单（号码）'},
		          {field:'qizhishijian',align:'center',width:40,title:'保险起止时间'},
		          {field:'MORTGAGE_PLEDGEE',align:'center',width:40,title:'权属状况 '},
		          {field:'REGISTER_OFFICE',align:'center',width:40,title:'登记机关'}
		          ]]
	});
});

/**
 * 查询模糊
 * @author 杨雪
 * @return
 */
function toSeacher() {
	var MORTGATE_STATUS = $("input[name='MORTGATE_STATUS']").val();
	var ORGAN_NAME = $("input[name='ORGAN_NAME']").val();
	var PROJECT_NAME = $("input[name='PROJECT_NAME']").val();
	$('#morManagerMg').datagrid('load', {
		"MORTGATE_STATUS" : MORTGATE_STATUS,
		"ORGAN_NAME" : ORGAN_NAME,
		"PROJECT_NAME" : PROJECT_NAME
	});
}

/**
 * 清空查询数据
 * @author 杨雪
 * @return
 */
function clearQuery(){
	$(".paramData").each(function(){
		$(this).val("");
	});
}

/**
 * 查看项目弹出框
 * @author 杨雪
 * @param divName
 * @return
 */
function getProjectName(divName) {
	$("#"+divName).dialog('open');
}

/**
 * 查看项目
 * @author 杨雪
 * @return
 */
function findProjectName() {
	var data={};
    data.PAGE_CURR=$("#PAGE_CURR").val();        	
    if(data.PAGE_CURR==undefined||data.PAGE_CURR<1 ||data.PAGE_CURR==''){
    	data.PAGE_CURR=1;
    }else{
    	data.PAGE_CURR=parseInt(data.PAGE_CURR);
    }
	data.PROJECT_NAME1=$("#PROJECT_NAME1").val();
	jQuery("#getProName").empty();
	$("#getProName").load(_basePath+"/mortgageProduct/MortgageProduct!getProjectName.action?json="+JSON.stringify(data),function(){});
}

//创建抵押
function createMor(){
	var PROJECT_NAME = $("#PROJECT_NAME").val();
	var PROJECT_ID = $("#PROJECT_ID").val();
	var MOR_STATUS = $(".MOR_STATUS").val();
	if(MOR_STATUS==1) {
	   alert("产品已经被抵押, 请选其他产品");
	} else{
		 if(PROJECT_NAME == ''){
			alert("请选择项目名称");
		}else {
			location.href=_basePath+'/mortgageProduct/MortgageProduct!doMortgage.action?PROJECT_ID='+PROJECT_ID+'&FLAGE='+0;
		}	
	}	
}

//创建质押
function createPledge(){
	var PROJECT_NAME = $("#PROJECT_NAME").val();
	var PROJECT_ID = $("#PROJECT_ID").val();
	var MOR_STATUS = $(".MOR_STATUS").val();
	if(MOR_STATUS==2) {
	   alert("产品已经被质押, 请选其他产品");
	} else{
		if(PROJECT_NAME == ''){
			alert("请选择项目名称");
		}else {
			location.href=_basePath+'/mortgageProduct/MortgageProduct!doMortgage.action?PROJECT_ID='+PROJECT_ID+'&FLAGE='+1;
		}	
	}	
}

//选择项目
function chooseCardUnit(id,dmv_name){
	$("#PROJECT_NAME").val(dmv_name);
	$("#PROJECT_ID").val(id);
	$("#getProjectName").dialog('close');
}