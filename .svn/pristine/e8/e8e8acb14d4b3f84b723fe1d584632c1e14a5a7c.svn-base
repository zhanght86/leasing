$(document).ready(function(){
	$("#insDialog").datagrid({
		url:_basePath+"/mortgage/Mortgage!toMgMortgageDDY.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:true,
		pageSize:20,
		fit:true,
		//fitColumns:true,//设置为 true，则会自动扩大或缩小列的尺寸以适应网格的宽度并且防止水平滚动。
		toolbar:'#pageForm',
		//onClickCell:onClickRow,//当用户点击单元格式调用
		frozenColumns:[[{field:'TR_ID',width:150,align:'center',title:'操作', formatter:function(value,rows,index){
						  return "<a href='#' onclick='toAdd("+rows.FPE_ID+"\,\""+rows.CLIENT_NAME+"\")'>抵押</a> ";
					}}]],
		columns:[[
//		          {field:'M_STATUS',width:90,align:'center',title:'抵押状态',formatter:function(value,rows,index){
//		        	  if(value=="0"||value==undefined){
//		        		  return "未抵押";
//		        	  }else if(value=="1"){
//		        		  return "已抵押";
//		        	  }else {
//		        		  return "已解押";
//		        	  }
//		          }},
		          {field:'LEASE_CODE',width:200,align:'center',title:'融资租赁合同号',formatter:function(value,rows,index){
		        	  return "<a href='#' onclick='onClickRow("+rows.PROJECT_ID+"\,\""+rows.CUST_TYPE+"\")'>"+value+"</a> ";
		          }}, 
				  {field:'CLIENT_NAME',width:200,align:'center',title:'客户名称'},
				  {field:'PRODUCT_NAME',width:200,align:'center',title:'品牌名称'},
				  {field:'CAR_SYMBOL',width:180,align:'center',title:'合格证号'}, 
				  {field:'SPEC_NAME',width:200,align:'center',title:'品牌型号'},
//				  {field:'SHANGPAIDATE',width:130,align:'center',title:'上牌时间'},
//				  {field:'FS_ADDRESS',width:130,align:'center',title:'上牌地点'},
//				  {field:'FS_DANWEI',width:130,align:'center',title:'上牌单位'},
//				  {field:'FACHEDATE',width:130,align:'center',title:'发车时间'},
//				  {field:'CARNUM',width:130,align:'center',title:'车牌号'},
//				  {field:'MORTGAGE_TIME',width:130,align:'center',title:'抵押时间'},
//				  {field:'MORTGAGE_PER',width:130,align:'center',title:'抵押权人'}
//				  {field:'EXTRACT_TIME',width:130,align:'center',title:'解压时间'}
		         ]]	
	});
	
	//页面清空按钮操作
	$("#qingkong").click(function(){
		$(".paramData").each(function(){
			$(this).val("");
		});
	});
});

function onClickRow(PROJECT_ID, CUST_TYPE){
	$('#tt1').tabs('close',"抵押资料");
	
	$('#tt1').tabs('add',{
	    title:'抵押资料'+PROJECT_ID,
	    cache: true,
	    closable:true,
	    selected : true,//leeds/cust_info_input/CustInfoInput!seeImg.action
	    content: '<iframe width="100%" id="_iframe1" height="100%" frameborder=0 scrolling=auto src="'+_basePath+'/crm/Customer!toMgElectronicPhotoAlbum11.action?PROJECT_ID='+PROJECT_ID+'&CUST_TYPE='+CUST_TYPE+'&PHASE=放款&taskStatue=1"></iframe>',
	    tools:[{
	        iconCls:'icon-mini-refresh',
	        handler:function(){
		    	var pp = $('#tt1').tabs('getSelected');    
				pp.tabs('refresh');
	        }
	    }]
	});
}

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