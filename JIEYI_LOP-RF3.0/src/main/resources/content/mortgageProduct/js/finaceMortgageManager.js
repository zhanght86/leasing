$(function() {
	$('#MortgageToMg').datagrid({
		url:_basePath+"/mortgageProduct/MortgageProduct!findMorInforData.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		columns:[[ 	 	 	 		 	 		
		          {field:'warm',align:'center',width:20,title:'',formatter:function(value,rowData,rowIndex){
		        	  if(rowData.REMOVE_DATE<=10&&(rowData.MLFLAGE=='0' || rowData.MLFLAGE=='1')){
		        		  return "<img src='../mortgageProduct/imag/red.png'/>";
		        	  }else if(rowData.REMOVE_DATE > 10&&(rowData.MLFLAGE=='0'|| rowData.MLFLAGE=='1')){
		        		  return "<img src='../mortgageProduct/imag/blue.png'/>";
		        	  }else {
		        		  return "<img src='../mortgageProduct/imag/hui.png'/>";
		        	  }
		          }},
		          {field:'operate',align:'center',width:40,title:'操作',formatter:function(value,rowData, rowIndex){
		        	  var url = "";
		        	  if(rowData.MLFLAGE == "0"|| rowData.MLFLAGE =="1"){
		        		  url = "<a href='javascript:void(0);' onclick='updateMorSta("+rowData.FMPID+")'>解压</a>";
		        	  }
		        	  var url1 = ""; 
		        	  if(rowData.REMARK != "1"){
		        		  url1 = "<a href='javascript:void(0);' onclick='dengji("+rowData.FMPID+")'>登记证书</a>";
		        	  }
		        	  
		        	  return url+" | <a href='javascript:void(0);' onclick='doSearchInforMor("+rowData.FMPID+")'>查看</a> | "+url1;
		          }},
		          {field:'FMPID',align:'center',width:40,title:'',checkbox:true},
		          {field:'FLAGE',align:'center',width:40,title:'合同类型',formatter:function(value,rowData, rowIndex){
		        	  if(value=="0"){
		        		  return "抵押合同";
		        	  }else{
		        		  return "质押合同";
		        	  }
		          }},
		          {field:'MORTGAGE_PLEDGEE',align:'center',width:40,title:'融资机构'},
		          {field:'MORTGAGE_DATE',align:'center',width:40,title:'签订日期'},
		          {field:'MORTGAGE_NO',align:'center',width:40,title:'抵押合同编号'},
		          {field:'MORTGAGE_REGSTER_NO',align:'center',width:40,title:'登记编号'},
		          {field:'MORTGAGE_START_TIME',align:'center',width:40,title:'抵押起始日期'},
		          {field:'MORTGAGE_END_TIME',align:'center',width:40,title:'抵押到期日'},
		          {field:'MORTGAGE_TOTLE',align:'center',width:40,title:'抵押物价值'},
		          {field:'MORTGAGE_MONEY',align:'center',width:40,title:'抵押额'},
		          {field:'MLFLAGE',align:'center',width:40,title:'抵押状态',formatter:function(value,rowData, rowIndex){
		        	  if(value=="0"){
		        		  return "已抵押 ";
		        	  }else if(value=="1"){
		        		  return "已质押";
		        	  }else {
		        		  return "已解压";
		        	  }
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
	$('#MortgageToMg').datagrid('load',{'param':getFromData("#pageForm")});
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

//解押
function updateMorSta(FMPID) {
	if(confirm('您确认对此项任务解押吗?')){
		jQuery.ajax({
        	url : _basePath+"/mortgageProduct/MortgageProduct!doRemoveMor.action",
        	type : "post",
        	data : "FMPID="+FMPID,
        	dataType:"json",
        	success : function(json) {
        		if (json.flag==true) {
        			alert("解押成功");
        			location.href=_basePath+"/mortgageProduct/MortgageProduct!findMorInfor.action";
        		} else {
    				alert("解押失败");
    				location.href=_basePath+"/mortgageProduct/MortgageProduct!findMorInfor.action";
    			}
        	}
    	});
	}
}

//登记证书  查看
function dengji(FMPID){
	window.location.href = _basePath+"/mortgageProduct/MortgageProduct!doSearchInforMor.action?tab=1&FMPID="+FMPID+"&type=0";
}

//修改登记证书
function doSearchInforMor(FMPID){
	window.location.href = _basePath+"/mortgageProduct/MortgageProduct!doSearchInforMor.action?tab=1&FMPID="+FMPID+"&type=1";
}

//查询导出
function exp(){
	var toCreateMor = [];
	$(".toCreateMor").each(function(){
		var temp={};
		temp.FLAGE = $(this).find("#FLAGE").val();
		temp.MORTGAGE_PLEDGEE = $(this).find("input[name='MORTGAGE_PLEDGEE']").val();
		temp.MORTGAGE_NO = $(this).find("input[name='MORTGAGE_NO']").val();
		temp.wind_commit_date = $(this).find("input[name='wind_commit_date']").datebox('getValue');
		temp.wind_commit_date1 = $(this).find("input[name='wind_commit_date1']").datebox('getValue');
		temp.wind_commit_date2 = $(this).find("input[name='wind_commit_date2']").datebox('getValue');
		temp.wind_commit_date3 = $(this).find("input[name='wind_commit_date3']").datebox('getValue');
		toCreateMor.push(temp);
	});
	var toCreateMor = {
		toCreateMor : toCreateMor
	};
	window.location.href= _basePath+"/mortgageProduct/MortgageProduct!excelMor.action?toCreateMor="+JSON.stringify(toCreateMor);						
}

//勾选导出
function expChecked() {	
	var dataList = [];	
	var detailData = $("#MortgageToMg").datagrid('getSelections');
	for(var i = 0; i<detailData.length; i++) {
		var temp = {};
		temp.FMPID = detailData[i].FMPID;
		dataList.push(temp);
	}
	var dataListR = {
		dataList : dataList
	}	
	window.location.href= _basePath+"/mortgageProduct/MortgageProduct!excelMor1.action?dataListR="+JSON.stringify(dataListR);	
}