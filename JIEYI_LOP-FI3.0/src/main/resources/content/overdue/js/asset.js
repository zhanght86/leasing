$(document).ready(function(){
	
	$("#asset_MG").datagrid({
		url:"Overdue!query_asset_MG_AJAX.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		//fitColumns:true,
		pageSize:10,
//		singleSelect:true,//单选模式
		toolbar:'#pageForm',
		frozenColumns:[[
					{field:'AA',title:'操作',width:200,align:'center',formatter:function(value,rowData,rowIndex){
						  
						//	  if(rowData.DUN_PERIOD_COUNT>0){
								  return "<a href='Overdue!YUJINExpPDF.action?PAYLIST_CODE="+rowData.PAYLIST_CODE+"'>预警</a>&nbsp;|&nbsp;<a href='Overdue!CHUXIANExpPDF.action?PAYLIST_CODE="+rowData.PAYLIST_CODE+"' >出险 </a>&nbsp;|&nbsp;<a href='Overdue!SHIGUExpPDF.action?PAYLIST_CODE="+rowData.PAYLIST_CODE+"' >事故 </a>&nbsp;";
						//	  }
						//	  else
						//	  {
						//		  return "&nbsp;";
						//	  }
						}}
		                ]],
		columns:[[
					
		          	{field:'PAYLIST_CODE',title:'支付表号',width:130,align:'center'},
		          	{field:'PRO_CODE',title:'项目编号',width:100,align:'center'},
		          	{field:'CUST_NAME',title:'客户名称',width:150,align:'center'},
//		          	{field:'SUPPLIER_NAMES',title:'供应商',width:150},
//		          	{field:'COMPANY_NAMES',title:'厂商',width:150},
//		          	{field:'PRODUCT_NAMES',title:'租赁物名称',width:150},
//		          	{field:'EQUIPMENT_AMOUNTS',title:'台量',width:50},
//		          	{field:'ENGINE_TYPES',title:'发动机编号',width:150},
//		          	{field:'WHOLE_ENGINE_CODES',title:'出厂编号',width:150},
		          	{field:'PROJECT_DATE',title:'立项日期',width:100,align:'center'},
		          	{field:'START_DATE',title:'起租确定日期',width:100,align:'center'},
		          	{field:'LEASE_TOPRIC',title:'品牌价款',width:100,align:'center'},
		          	{field:'LEASE_TERM',title:'品牌期限',width:50,align:'center'},
		          	{field:'ITEM_MONEY_ALL',title:'租金总额',width:100,align:'center'},
		          	{field:'DUN_PERIOD_COUNT',title:'逾期期数',width:50,align:'center'},
		          	{field:'DUN_COUNT',title:'累计逾期',width:50,align:'center'},
		          	{field:'DUN_DAY_COUNT',title:'逾期天数',width:50,align:'center'},
		          	{field:'PAID_MONEY',title:'逾期租金',width:100,align:'center'},
		          	{field:'PENALTY_RECE_ALL',title:'违约金额',width:100,align:'center'},
		          	{field:'ID',hidden:true}
		          	
		         ]],
		view:detailview,
		detailFormatter:function(index,row){
		
		return '<div id="ddv-' + row.ID + '" style="padding:5px 0"></div>';
		 },
		onExpandRow: function(index,row){
//			 alert(row.PAYLIST_CODE);
			 jQuery.ajax({
					url:"Overdue!query_asset_Detail.action?PAYLIST_CODE="+encodeURI(row.PAYLIST_CODE),  
					type:'post',
					dataType:'json',
					success: function(json){
//				 alert("查询明细成功");
				 var data = {flag:json.flag,total:json.data.length,rows:json.data};
//					var pRowIndex = "ddv-"+row.ID;
					$('#ddv-'+row.ID).datagrid({
						fitColumns:true,
						rownumbers:true,//左侧自动显示行数
//						loadMsg:'加载中...',
//						height:'auto',
						columns:[[
									{field:'PRODUCT_NAME',align:'center',width:100,title:'品牌名称'},
									{field:'SPEC_NAME',align:'center',width:100,title:'型号名称'},
									
									{field:'SUP_SHORTNAME',align:'center',width:100,title:'经销商编号'},
									{field:'COMPANY_SHORTNAME',align:'center',width:100,title:'经销商编号'},
									
									{field:'CERTIFICATE_NUM',align:'center',width:100,title:'合格证编号'},
									
									{field:'ENGINE_TYPE',align:'center',width:100,title:'发动机编号'},
									{field:'CAR_SYMBOL',align:'center',width:100,title:'车架号'},
									{field:'ID',align:'center',title: '操作',width:80,formatter:function(value,rowData,rowIndex){
										return "<a target=_self href='GpsSearch!SelectForDeviceGps.action?EQUIPMENT_ID="+value+"'>GPS管理</a>";}}
//												<a href='Overdue!YUJINExpPDF.action?PAYLIST_CODE="+rowData.PAYLIST_CODE+"'>预警</a>
//									return "<a #if($gps) href="$request.ContextPath/gpsPlan/GpsSearch!SelectForDeviceGps.action?EQUIPMENT_ID=$item.ID" #else title="header=[温馨提示] body=[您无操作权限！]" #end  >#if($gps)GPS管理  #else <font style="color:gray!important;">GPS管理</font> #end&nbsp;&nbsp;</a>";}}
//									return "<a href='javascript:void(0);' onclick='GPSManage("+value+")'>GPS管理</a>&nbsp;";}}
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
						$('#ddv-'+row.ID).datagrid("loadData",data);
				 
			 }
			 });
			 
		 }
		});
	
});

//Add By YangJ 2014年5月15日18:04:02
function GPSManage(id){
	
	
}


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