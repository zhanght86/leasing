$(document).ready(function(){

	$("#pageTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		url:'LeaseApplication!toAjaxData.action',
		columns:[[
					{field:'AA',width:15,align:'center',title:'操作',formatter:function(value,rowData,rowIndex){
							if(rowData.PLATFORM_TYPE=='11')
							{
								if(rowData.COUNTPRDX>0){
									return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='qzAppList(" + JSON.stringify(rowData) + ")'>起租申请</a> &nbsp;";
								}
								else{
									return " ";
								}
								
							}
							if(rowData.PLATFORM_TYPE=='8')//联合租赁先判断各融资租赁公司出资情况填写没
							{
								if(rowData.COUNTPRBZ>0){
									return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='qzAppListLH(" + JSON.stringify(rowData) + ")'>起租申请</a> &nbsp;";
								}
								else{
									return " ";
								}
							}
							else{
								if(rowData.COUNTPRBZ>0){
									return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='qzApp(" + JSON.stringify(rowData) + ")'>起租申请</a> &nbsp;";
								}
								else{
									return " ";
								}
							}
							
					 }},
		          	{field:'PROJECT_ID',hidden:true,align:'center'},
		          	{field:'PLATFORM_TYPE',hidden:true,align:'center'},
		          	{field:'PLATFORM_NAME',title:'业务类型',width:15,align:'center'},
		          	{field:'PRO_CODE',title:'项目编号',width:15,align:'center'},
		          	{field:'LEASE_CODE',title:'融资租赁合同号',width:15,align:'center'},
		          	{field:'LEASE_MODEL',title:'租赁模式',width:15,align:'center'},
		          	{field:'CUST_NAME',title:'客户名称',width:15,align:'center'},
		          	{field:'CUST_CODE',title:'客户编号',width:15,align:'center'},
		          	{field:'CUST_TYPE',title:'客户类型',width:15,align:'center',formatter:function(value,rowData,rowIndex){
		          		if(value=='LP')
		          		{
		          			return "法人";
		          		}
		          		else if(value=='NP')
		          		{
		          			return "个人";
		          		}
	                  }}
	                  
		         ]],
		        view:detailview,
		 		detailFormatter : function(index, row) {
		 			return '<div id="ddv-' + row.PROJECT_ID + '" style="padding:5px 0;"></div>';
		 		},
				onExpandRow : function(index, row) {
					jQuery.ajax({
						url:_basePath+"/leaseApplication/LeaseApplication!getLeasePayList.action?PROJECT_ID="+row.PROJECT_ID,  
						type:'post',
						dataType:'json',
					    success: function(json){
							var data = {flag:json.flag,total:json.data.length,rows:json.data};
							var pRowIndex = "ddv-"+row.PROJECT_ID;
							$('#ddv-'+row.PROJECT_ID).datagrid({
								fitColumns:true,
								columns:[[
								          {field:'BB',width:30,align:'center',title:'操作',formatter:function(value,rowData,rowIndex){
			    			       				return "<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='PayListView(" + JSON.stringify(rowData) + ")'>查看</a> &nbsp;";
			    			       		 }},
								          	{field:'PAYLIST_CODE',align:'center',width:10,title:'支付表号'},
				                            {field:'SUP_SHORTNAME',align:'center',width:15,title:'经销商'},
				                            {field:'COMPANY_NAME',align:'center',width:15,title:'厂商'},
				                            {field:'START_DATE',align:'center',width:10,title:'起租日'},
				                            {field:'REPAYMENT_DATE',align:'center',width:10,title:'还款日'}
				                           
								         ]],
								onResize:function(){
			                        $('#pageTable').datagrid('fixDetailRowHeight',index);
			                    },
			                    onLoadSuccess:function(){
			                        setTimeout(function(){
			                            $('#pageTable').datagrid('fixDetailRowHeight',index);
			                        },0);
			                    }
							});
							 $('#pageTable').datagrid('fixDetailRowHeight',index);
								$('#ddv-'+row.PROJECT_ID).datagrid("loadData",data);
					}
				});
		 		}
	});
});


function qzApp(row){
	if (row){
		 var PROJECT_ID=row.PROJECT_ID;
		 var LEASE_CODE=row.LEASE_CODE;
		 top.addTab(LEASE_CODE+"起租申请",_basePath+"/leaseApplication/LeaseApplication!queryEQList.action?PROJECT_ID="+PROJECT_ID);
	}else{
		$.messager.alert("请选择一个项目!");
	}
}

function qzAppList(row){
	if (row){
		 var PROJECT_ID=row.PROJECT_ID;
		 var LEASE_CODE=row.LEASE_CODE;
		 top.addTab(LEASE_CODE+"起租申请",_basePath+"/leaseApplication/LeaseApplication!queryEQListByPay.action?PROJECT_ID="+PROJECT_ID);
	}else{
		$.messager.alert("请选择一个项目!");
	}
}

function qzAppListLH(row){
	if (row){
		 var PROJECT_ID=row.PROJECT_ID;
		 var LEASE_CODE=row.LEASE_CODE;
		 top.addTab(LEASE_CODE+"起租申请",_basePath+"/leaseApplication/LeaseApplication!qzAppListLH.action?PROJECT_ID="+PROJECT_ID);
	}else{
		$.messager.alert("请选择一个项目!");
	}
}

function PayListView(row){
	if (row){
		 var PAYLIST_CODE=row.PAYLIST_CODE;
		 var PAY_ID=row.PAY_ID;
		 top.addTab(PAYLIST_CODE+"查看",_basePath+"/leaseApplication/LeaseApplication!leaseApplication_view.action?PAY_ID="+PAY_ID);
	}else{
		$.messager.alert("请选择一个支付表!");
	}
}

