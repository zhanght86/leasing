$(document).ready(function(){

	$("#pageTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		url:'rentWriteNew!toAjaxData.action',
		columns:[[
		          	{field:'ID',checkbox:true,width:5},
		          	{field:'HEAD_ID',title:'付款单号',width:15},
		          	{field:'FI_PAY_MONEY',title:'付款金额',width:15},
		          	{field:'FI_PROJECT_NUM',title:'项目数量',width:15},
		          	{field:'APP_CREATE',title:'申请人',width:15},
		          	{field:'FI_PAY_DATE',title:'计划还款日',width:15},
		          	{field:'FI_FLAG',title:'付款方式',width:15,formatter:function(value,rowData,rowIndex){
		          		if(value=='3')
		          		{
		          			return "租金-非网银";
		          		}
		          		else if(value=='4')
		          		{
		          			return "供应商垫付-网银";
		          		}
		          		else if(value=='5')
		          		{
		          			return "供应商垫付-非网银";
		          		}
	                	  
	                  }},
	                {field:'STATUS_FLAG',title:'确认状态',width:10},
		          	{field:'FI_STATUS',hidden:true}
		         ]],
		        view:detailview,
		 		detailFormatter : function(index, row) {
		 			return '<div id="ddv-' + row.HEAD_ID + '" style="padding:5px 0;"></div>';
		 		},
				onExpandRow : function(index, row) {
					jQuery.ajax({
						url:_basePath+"/rentWrite/rentWriteNew!getFundDetailData.action?HEAD_ID="+row.HEAD_ID,  
						type:'post',
						dataType:'json',
					    success: function(json){
							var data = {flag:json.flag,total:json.data.length,rows:json.data};
							var pRowIndex = "ddv-"+row.HEAD_ID;
							$('#ddv-'+row.HEAD_ID).datagrid({
								fitColumns:true,
								columns:[[
								          	{field:'PRO_CODE',align:'center',width:10,title:'项目编号'},
				                            {field:'CUSTNAME',align:'center',width:15,title:'客户名称'},
				                            {field:'COMPANY_NAME',align:'center',width:15,title:'厂商'},
				                            {field:'SUP_NAME',align:'center',width:15,title:'供应商'},
				                            {field:'EQUIPMENINFOS',align:'center',width:15,title:'租赁物'},
				                            {field:'FI_PRO_NAME',align:'center',width:10,title:'款项名称'},
				                            {field:'BEGINNING_NUM',align:'center',width:5,title:'期次'},
				                            {field:'D_RECEIVE_DATE',align:'center',width:10,title:'计划收取日期'},
				                            {field:'BEGINNING_MONEY',align:'center',width:10,title:'本次应收金额'}
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
								$('#ddv-'+row.HEAD_ID).datagrid("loadData",data);
					}
				});
		 		}
	});
});

function exportData(flag){
	$("#divFrom").empty();
	var type=1;
	var IDS="";
	if(flag == 'all')
	{
		uploadType="all";
	}
	else{
		type=2;
		var datagridList=$('#pageTable').datagrid('getChecked');
		if(datagridList.length<=0)
		{
			alert("请先选择要导出的数据在继续导出操作！");
			return false;
		}
		else{
			
			for(var i = 0; i < datagridList.length; i++)
			{
				if(i==0){
					IDS=datagridList[i].HEAD_ID;
				}
				else{
					IDS=IDS+","+datagridList[i].HEAD_ID;
				}
			}
		}
	}
	
	var HEAD_ID = $("input[name='HEAD_ID']").val();
	var FI_PAY_MONEY = $("input[name='FI_PAY_MONEY']").val();
	var FI_PAY_DATE1 = $("input[name='FI_PAY_DATE1']").val();
	var FI_PAY_DATE2 = $("input[name='FI_PAY_DATE2']").val();
	var FI_STATUS = $("select[name='FI_STATUS']").attr("selected",true).val();
	var url="rentWriteNew!HEAD_Upload.action?IDS="+IDS+"&HEAD_ID="+HEAD_ID+"&FI_PAY_MONEY="+FI_PAY_MONEY+"&FI_PAY_DATE1="+FI_PAY_DATE1+"&FI_PAY_DATE2="+FI_PAY_DATE2+"&FI_STATUS="+FI_STATUS+"&type="+type;
	
	$("#divFrom").append("<form id='formSubmit' method='post' action='"+url+"'></form>");
	$("#formSubmit").submit();
}

function toZuoFPayment()
{
	var datagridList=$('#pageTable').datagrid('getChecked');
	if(datagridList.length<=0)
	{
		alert("请先选择要数据在继续作废操作！");
		return false;
	}
	$.messager.confirm("提示","您确认要作废选中的数据？",function(flag){
		if(flag){
			var getDetailData = [];
			for(var i = 0; i<datagridList.length; i++) {
				var temp = {};
				temp.FUND_ID = datagridList[i].HEAD_ID;
				getDetailData.push(temp);
			}
			
			var data = {};
			data["getDetailData"] = getDetailData;
			
			$.ajax({
				url:_basePath+"/rentWrite/rentWriteNew!toRemovePayment.action",
			    type:'post',
			    data:'data='+JSON.stringify(data),
			    dataType:'json',
			    success:function(json){
				    if(json.flag == true){
				    	$.messager.alert("作废","付款单作废成功！");
				    	//页面刷新
				    	$('#pageTable').datagrid('reload');
				    }else {
				    	$.messager.alert("作废","付款单作废失败！");
				    	//页面刷新
				    	$('#pageTable').datagrid('reload');
				    }
			    }
			});
		}
	});
}

function toAppPayment(){
	window.location.href = _basePath+"/rentWrite/rentWriteNew!bank_C_Manger.action";
}

//供应商不能修改核销金额
function toAppPaymentSUPP(){
	window.location.href = _basePath+"/rentWrite/rentWriteNew!bank_C_MangerSUPP.action";
}

function toSubmitPayment()
{
	var datagridList=$('#pageTable').datagrid('getChecked');
	if(datagridList.length<=0)
	{
		alert("请先选择要数据在继续申请操作！");
		return false;
	}
	var getDetailData = [];
	for(var i = 0; i<datagridList.length; i++) {
		var temp = {};
		temp.FUND_ID = datagridList[i].HEAD_ID;
		getDetailData.push(temp);
	}
	
	var data = {};
	data["getDetailData"] = getDetailData;
	
	$.ajax({
		url:_basePath+"/rentWrite/rentWriteNew!toSubmitPayment.action",
	    type:'post',
	    data:'data='+JSON.stringify(data),
	    dataType:'json',
	    success:function(json){
		    if(json.flag == true){
		    	$.messager.alert("租金提交","租金提交成功！");
		    	//页面刷新
		    	window.location.href = _basePath+"/rentWrite/rentWriteNew!toMgDeduct.action";
		    }else {
		    	$.messager.alert("租金提交","租金提交失败！");
		    	//页面刷新
		    	$('#pageTable').datagrid('reload');
		    	window.location.href = _basePath+"/rentWrite/rentWriteNew!toMgDeduct.action";
		    }
	    }
	});
}
