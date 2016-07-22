function updateXs(obj) {
	$("#updateXsDialog").dialog('open');
	var params = "FE_ID="+$(obj).attr("eq_id");
	var url = _basePath+"/returnVisit/ReturnVisit!updateReturnVisit.action";
	jQuery.ajax({
		type: 'POST',
		url: url,
		data: params,
		dataType: 'json',
		success: function(json) {
			if(json.data.length == 0) {
				$("#updateXsDialog table").append(
						"<tr>" +
							"<td colspan='2' style='color:red'>无巡视记录</td>" +
						"</tr>");
			}
			jQuery.each(json.data, function(i, n) {
				$("#updateXsDialog table").append(
						"<tr>" +
							"<td>"+n.P_TIME+"</td>" +
							"<td><a target=_self href='ReturnVisit!addReturnVisit.action?FE_ID"+n.FE_ID+"=&P_TIME="+n.P_TIME+"&TYPE=UPDATE'>更新</a></td>" +
						"</tr>");
			});
		}
	});
}
function showXs(obj) {
	$("#showXsDialog").dialog('open');
	var params = "FE_ID="+$(obj).attr("eq_id");
	var url = _basePath+"/returnVisit/ReturnVisit!showReturnVisit.action";
	jQuery.ajax({
		type: 'POST',
		url: url,
		data: params,
		dataType: 'json',
		success: function(json) {
			if(json.data.length == 0) {
				$("#showXsDialog table").append(
						"<tr>" +
							"<td colspan='2' style='color:red'>无巡视记录</td>" +
						"</tr>");
			}
			jQuery.each(json.data, function(i, n) {
				$("#showXsDialog table").append(
						"<tr>" +
							"<td>"+n.P_TIME+"</td>" +
							"<td><a target=_self href='ReturnVisit!addReturnVisit.action?FE_ID"+n.FE_ID+"=&P_TIME="+n.P_TIME+"&TYPE=SHOW'>查看</a></td>" +
						"</tr>");
			});
		}
	});
}

$(document).ready(function(){
	$("#asset_MG").datagrid({
		url:"ReturnVisit!newInsuranceManage_AJAX.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		fitColumns:true,
		pageSize:10,
//		singleSelect:true,//单选模式
		toolbar:'#pageForm',
		columns:[[
					{field:'T_ID',align:'center',title: '操作',width:80,formatter:function(value,rowData,rowIndex){
						return "<a target=_self href='ReturnVisit!addReturnVisit.action?CUST_ID="+value+"&CUST_TYPE="+rowData.TYPE+"'>新建</a>&nbsp;";
					}
						
						},
		          	{field:'RENTER_CODE',align:'center',title:'客户编号',width:130},
		          	{field:'RENTER_NAME',align:'center',title:'客户名称',width:130},
		          	{field:'TYPE',width:50,title:'客户类型',align:'center',formatter:function(value,rowData,rowIndex){
                  	  if(value == 'LP'){
                  		 return "法人";
                  	  }else {
                  		 return "个人";
                  	  }
                    }},
		          	{field:'DOCUMENT_NUMBER',align:'center',title:'身份证/组织机构代码证号',width:130}
		          	
		         ]],
		view:detailview,
		detailFormatter:function(index,row){
		
		return '<div id="ddv-' + row.T_ID + '" style="padding:5px 0"></div>';
		 },
		onExpandRow: function(index,row){
//			 alert(row.PAYLIST_CODE);
			 jQuery.ajax({
					url:"ReturnVisit!getEmpByRectId.action?CUST_ID="+row.T_ID,  
					type:'post',
					dataType:'json',
					success: function(json){
//				 alert("查询明细成功");
				 var data = {flag:json.flag,total:json.data.length,rows:json.data};
//					var pRowIndex = "ddv-"+row.ID;
					$('#ddv-'+row.T_ID).datagrid({
						fitColumns:true,
						rownumbers:true,//左侧自动显示行数
//						loadMsg:'加载中...',
//						height:'auto',
						columns:[[
//									{field:'EQUIPMENT_ADDRESS',align:'center',width:100,title:'车辆存放地'},
//									{field:'PRO_CODE',align:'center',width:100,title:'项目编号'},
									{field:'LEASE_CODE',align:'center',width:100,title:'合同编号'},
									{field:'PAYLIST_CODE',align:'center',width:100,title:'支付表号'},
									
									{field:'COMPANY_NAME',align:'center',width:100,title:'厂商'},
									{field:'THING_NAME',align:'center',width:100,title:'产品名称'},
									
									{field:'MODEL_SPEC',align:'center',width:100,title:'产品型号'},
									
									{field:'CERTIFICATE_NUM',align:'center',width:100,title:'合格证编号'},
									{field:'SUPPLIERS_NAME',align:'center',width:100,title:'经销商'},
									{field:'UNIT_PRICE',align:'right',width:100,title:'单价'}
									
								 ]],
						onResize:function(){
							$('#asset_MG').datagrid('fixDetailRowHeight',index);
						},
						onLoadSuccess:function(){
							setTimeout(function(){
								$('#asset_MG').datagrid('fixDetailRowHeight',index);
							},0);
						}
					});
					 $('#asset_MG').datagrid('fixDetailRowHeight',index);
						$('#ddv-'+row.T_ID).datagrid("loadData",data);
				 
			 }
			 });
			 
		 }
		});
	
});



/**
 * 清空按钮
 * @return
 */
function emptyData(){
	$('#pageForm').form('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}