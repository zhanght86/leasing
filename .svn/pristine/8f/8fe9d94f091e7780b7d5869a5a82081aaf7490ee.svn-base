
$(function(){
	$("#pageTable_QueryM").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		fitColumns:true,
		url:_basePath+'/fiForRed/FiForRed!doShowMgFiForRedApp2.action',
		toolbar:'#pageForm',
		columns:[[
		          	{field:'ID',checkbox:true,width:1,align:'center'},
		          	{field:'HEAD_ID',title:'付款单号',width:3,align:'center'},
		          	{field:'FI_PAY_MONEY',title:'付款金额',width:3,align:'center'},
		          	{field:'FI_FUND_CODE',title:'付款类别',align:'center',width:4,formatter:function(value,rowData,rowIndex){
		          		if(value=='1'){
		          			return "租金";
		          		}else if (value=='2'){
		          			return "首付款";
		          		}
		          	}},
		          	{field:'FI_FLAG',title:'付款方式',align:'center',width:4,formatter:function(value,rowData,rowIndex){
		          		if(value=='3')
		          		{
		          			return "非网银";
		          		}
		          		else if(value=='4')
		          		{
		          			return "供应商垫付-网银";
		          		}
		          		else if(value=='5')
		          		{
		          			return "供应商垫付-非网银";
		          		}
		          		else if(value=='2')
		          		{
		          			return "网银";
		          		}
		          		else if(value=='15')
		          		{
		          			return "租金-网银-不足额";
		          		}
		          		else if(value=='16')
		          		{
		          			return "供应商垫付-网银-虚拟";
		          		}
		          		else if(value=='17')
		          		{
		          			return "供应商垫付-非网银-虚拟";
		          		}
		          		else if(value=='18')
		          		{
		          			return "退款-虚拟";
		          		}
		          		else if(value=='19')
		          		{
		          			return "退款-租金池-虚拟";
		          		}
	                  }},
	                {field:'FI_PAY_DATE',title:'计划还款日',width:3,align:'center'},
	                {field:'FI_STATUS',title:'确认状态',width:2,align:'center',formatter:function(value,rowData,rowIndex){
	                	if(value== '-1')
		          		{
		          			return "作废";
		          		}
		          		else if(value=='0')
		          		{
		          			return "未核销";
		          		}
		          		else if(value=='1')
		          		{
		          			return "虚拟核销";
		          		}
		          		else if(value=='2')
		          		{
		          			return "已核销";
		          		}
		          		else if(value=='3')
		          		{
		          			return "核销失败";
		          		}
		          		else if(value=='4')
		          		{
		          			return "已驳回";
		          		}
		          		else if(value=='5')
		          		{
		          			return "已提交";
		          		}
		          		else if(value=='6')
		          		{
		          			return "已退款";
		          		}
		          		else if(value=='7')
		          		{
		          			return "已冲红";
		          		}
	                }},
		         ]],
		         view:detailview,
			 	 detailFormatter : function(index, row) {
			 			return '<div id="ddv-' + row.HEAD_ID + '" style="padding:5px 0;"></div>';
			 	 },
				 onExpandRow : function(index, row) {
					jQuery.ajax({
						url:_basePath+"/rentWrite/rentWriteNew!getQueryFundDetailData.action?HEAD_ID="+row.HEAD_ID,  
						type:'post',
						dataType:'json',
					    success: function(json){
							var data = {flag:json.flag,total:json.data.length,rows:json.data};
							var pRowIndex = "ddv-"+row.HEAD_ID;
							$('#ddv-'+row.HEAD_ID).datagrid({
								fitColumns:true,
								columns:[[
				                            {field:'CUSTNAME',align:'center',width:15,title:'客户名称'},
				                            {field:'COMPANY_NAME',align:'center',width:15,title:'厂商'},
				                            {field:'SUP_NAME',align:'center',width:15,title:'经销商'},
				                            {field:'LEASE_CODE',align:'center',width:15,title:'融资租赁合同号'},
											{field:'PAYLIST_CODE',align:'center',width:15,title:'支付表号'},
				                            {field:'EQUIPMENINFOS',align:'center',width:15,title:'品牌'},
				                            {field:'FI_PRO_NAME',align:'center',width:10,title:'款项名称'},
				                            {field:'BEGINNING_NUM',align:'center',width:5,title:'期次'},
				                            {field:'D_RECEIVE_DATE',align:'center',width:10,title:'计划收取日期'},
				                            {field:'BEGINNING_MONEY',align:'center',width:10,title:'本次应收金额'},
				                            {field:'D_RECEIVE_MONEY',align:'center',width:10,title:'本次实收金额'}
								         ]],
								onResize:function(){
			                        $('#pageTable_QueryM').datagrid('fixDetailRowHeight',index);
			                    },
			                    onLoadSuccess:function(){
			                        setTimeout(function(){
			                            $('#pageTable_QueryM').datagrid('fixDetailRowHeight',index);
			                        },0);
			                    }
							});
							 $('#pageTable_QueryM').datagrid('fixDetailRowHeight',index);
							 $('#ddv-'+row.HEAD_ID).datagrid("loadData",data);
						}
					});
			 	}
	});
});

function FiForRedApp2(TYPE){
	var rows=$("#pageTable_QueryM").datagrid("getSelections");
	if(rows.length == 0){
		$.messager.alert("提示","请选择要申请的冲红作废数据！");
		return ;
	}else{
		if(TYPE=='1'){
			$.messager.confirm("提示","您确定要申请冲红",function(r){
				if(r){
					var array=new Array();
					for(var i=0; i<rows.length; i++){
						var temp={};
						temp.HEAD_ID = rows[i].HEAD_ID;
						temp.FI_PAY_MONEY = rows[i].FI_PAY_MONEY;
						temp.FI_FUND_CODE = rows[i].FI_FUND_CODE;
						temp.FI_FLAG = rows[i].FI_FLAG;
						temp.FI_PAY_DATE = rows[i].FI_PAY_DATE;
						temp.FI_STATUS = rows[i].FI_STATUS;
						temp.TYPE = TYPE;
						array.push(temp);
					}
					
					$.ajax({
						type:'post',
						url:_basePath+"/fiForRed/FiForRed!doAddFiForRedApp2.action",
						data:"param="+JSON.stringify(array),
						dataType:'json',
						success:function(json){
							if(json.flag){
								seFiForRedTable();
							}else{
								$.messager.alert("提示",json.msg);
							}
						},
						error:function(){
							$.messager.alert("提示","网络问题，请联系网络管理员");
						}
					});
				}
			});
		}else{
			$.messager.confirm("提示","您确定要申请作废",function(r){
				if(r){
					var array=new Array();
					for(var i=0; i<rows.length; i++){
						var temp={};
						temp.HEAD_ID = rows[i].HEAD_ID;
						temp.FI_PAY_MONEY = rows[i].FI_PAY_MONEY;
						temp.FI_FUND_CODE = rows[i].FI_FUND_CODE;
						temp.FI_FLAG = rows[i].FI_FLAG;
						temp.FI_PAY_DATE = rows[i].FI_PAY_DATE;
						temp.FI_STATUS = rows[i].FI_STATUS;
						temp.TYPE = TYPE;
						array.push(temp);
					}
					
					$.ajax({
						type:'post',
						url:_basePath+"/fiForRed/FiForRed!doAddFiForRedApp_ZF2.action",
						data:"param="+JSON.stringify(array),
						dataType:'json',
						success:function(json){
							if(json.flag){
								seFiForRedTable();
							}else{
								$.messager.alert("提示",json.msg);
							}
						},
						error:function(){
							$.messager.alert("提示","网络问题，请联系网络管理员");
						}
					});
				}
			});
		}
	}
}
function seFiForRedTable(){
	var HEAD_ID = $("input[name='HEAD_ID']").val();
	var FI_PAY_MONEY = $("input[name='FI_PAY_MONEY']").val();
	var FI_PAY_DATE1 = $("input[name='FI_PAY_DATE1']").val();
	var FI_PAY_DATE2 = $("input[name='FI_PAY_DATE2']").val();
	var FI_FLAG = $("select[name='FI_FLAG']").attr("selected",true).val();
	$('#pageTable_QueryM').datagrid('load',{
	    "HEAD_ID" : HEAD_ID,
		"FI_PAY_MONEY" : FI_PAY_MONEY,
		"FI_PAY_DATE1" : FI_PAY_DATE1,
		"FI_PAY_DATE2" : FI_PAY_DATE2,
		"FI_FLAG" : FI_FLAG
     });
}
