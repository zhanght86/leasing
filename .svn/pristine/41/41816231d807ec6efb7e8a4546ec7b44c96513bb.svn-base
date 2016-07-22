$(function() {
	$('#pageTable').datagrid({
		url:_basePath+"/fundNotEBank/FundNotEBank!toMgDeductData.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		columns:[[
		          {field:'FI_APP_NAME',align:'center',width:20,checkbox:true},
		          {field:'HEAD_ID',align:'center',width:40,title:'付款单号'},
		          {field:'FI_REALITY_MONEY',align:'center',width:40,title:'付款金额'},
		          {field:'FI_PAY_DATE',align:'center',width:40,title:'付款日期'},
		          {field:'FI_STATUS',align:'center',width:40,title:'确认状态'},
		          {field:'FI_REMARK',align:'center',width:40,title:'驳回原因'}
		          ]],

		//以下为”+“号展看显示内容。
		view : detailview,
		detailFormatter : function(index, row) {
			return '<div id="ddv-' + index + '" style="padding:5px 0;"></div>';
		},
		onExpandRow : function(index, row) {
			jQuery.ajax({
				url:_basePath+"/fundNotEBank/FundNotEBank!getFundDetailData.action?FUND_ID="+row.HEAD_ID,  
				type:'post',
				dataType:'json',
			    success: function(json){
				var data = {flag:json.flag,total:json.data.length,rows:json.data};
				var pRowIndex = "ddv-"+index;
				$('#ddv-'+index).datagrid({
					fitColumns:true,
                    singleSelect:true,
					rownumbers:true,
                    loadMsg:'加载中...',
                    height:'auto',
                    columns:[[                     
                            {field:'D_PROJECT_CODE',align:'center',width:40,title:'项编号'},
  						    {field:'D_CLIENT_CODE',align:'center',hidden:true,width:50},
                            {field:'D_CLIENT_NAME',align:'center',width:50,title:'客户名称'},
                            {field:'PAYMENT_STATUS',align:'center',width:35,title:'放款方式',formatter:function(value,row,index){
                            	if(value=="1"){
                            		return "全额放款";
                            	}else if(value=="3"){
                            		return "部分差额放款";
                            	}else if(value=="4"){
                            		return "DB差额放款";
                            	}
                            }},
                            {field:'PRODUCT_NAME',align:'center',width:45,title:'租赁物类型'},
                            {field:'COMPANY_NAME',align:'center',width:50,title:'厂商'},
                            
                            {field:'LEASE_TOPRIC',align:'center',width:60,title:'租赁物购买价款'},
  						    {field:'D_PAY_CODE',align:'center',width:50,hidden:true},
                            {field:'FIRST_MONEY',align:'center',width:50,title:'首期款合计'},
                            {field:'OTHER_MONEY',align:'center',width:50,title:'其他费用合计'},
                            {field:'DB_MONEY',align:'center',width:50,title:'DB保证金'},
                            {field:'CS_MONEY',align:'center',width:50,title:'厂商保证金'},
                            {field:'YS_MONEY',align:'center',width:50,title:'应收金额'},
                            
                            {field:'RECEIVABLE',align:'center',width:50,hidden:true,title:'本次应收金额'},
  						  {field:'VERITABLE_MONEY',align:'center',width:50,hidden:true,title:'本次实收金额'}
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
				$('#ddv-'+index).datagrid("loadData",data);
				}
			});
		}
	});
});

/**
 * 查询模糊
 * @author 杨雪
 * @return
 */
function toSeacher() {
	var HEAD_ID = $("input[name='HEAD_ID']").val();
	var FI_PAY_MONEY = $("input[name='FI_PAY_MONEY']").val();
	var FI_PAY_DATE1 = $("input[name='FI_PAY_DATE1']").val();
	var FI_PAY_DATE2 = $("input[name='FI_PAY_DATE2']").val();
	var FI_STATUS = $("select[name='FI_STATUS']").attr("selected",true).val();
	$('#pageTable').datagrid('load', {
		"HEAD_ID" : HEAD_ID,
		"FI_PAY_MONEY" : FI_PAY_MONEY,
		"FI_PAY_DATE1" : FI_PAY_DATE1,
		"FI_PAY_DATE2" : FI_PAY_DATE2,
		"FI_STATUS" : FI_STATUS
	});
}

/**
 * 清空查询数据
 * @author 杨雪
 * @return
 */
function clearQuery(){
	$("#FI_PAY_DATE1").datebox('clear');
	$("#FI_PAY_DATE2").datebox('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}
/**************************************************资金扣划非网银-申请******************************************************************/
/**
 * 申请资金扣划
 * @author 杨雪
 * @return
 */
function toAppPayment(){
	window.location.href = _basePath+"/fundNotEBank/FundNotEBank!toAppPaymentMg.action";
}

/**
 * 提交申请数据
 * @author yx
 * @date 2013-09-29
 * @return
 */
function toSubmiyApp(){
	$.messager.confirm('提示','确定提交申请?',function(r) {
		if(r){
			if(toCheckedCommit()){
				var data = {};
				data["getDetailData"] = getDetailData();
				$.ajax({
					url:_basePath+"/fundNotEBank/FundNotEBank!doSubmitApp.action",
				    type:'post',
				    data:'data='+JSON.stringify(data),
				    dataType:'json',
				    success:function(json){
					    if(json.flag == true){
					    	$.messager.alert("首期款付款提交","首期款付款提交成功！！");	
					    	//页面刷新
							$('#pageTable').datagrid('load');
					    }else {
					    	$.messager.alert("首期款付款提交","首期款付款提交失败！！");
					    	//页面刷新
							$('#pageTable').datagrid('load');
					    }
					    
				    }
				});
			}else{
				$.messager.alert("首期款付款提交","请选择未提交的数据");
			}			
		}
	});
}

/**
 * 检验选中数据-提交
 * @return
 */
function toCheckedCommit(){
	var detailData = $("#pageTable").datagrid('getSelections');
	var FI_STATUS = '';
	var flag=false;
	for(var i = 0; i<detailData.length; i++) {
		FI_STATUS = detailData[i].FI_STATUS;		
		if(FI_STATUS!="未提交" &&  FI_STATUS!="已驳回"){
			flag=false;
		}else{
			flag=true;
		}
	}
	return flag;
}



/**********************************************资金扣划非网银-作废**********************************************************************/

function doCancellation(){
	var datagridList = $('#pageTable').datagrid('getChecked');
	var sqlData = [];	
	for(var i = 0; i < datagridList.length; i++)
	{
		sqlData.push(datagridList[i].HEAD_ID);
	}
	if(sqlData.length == 0){
		$.messager.alert("提示","请选择要作废的数据!");
		return;
	}
	
	$.messager.confirm('提示','确定作废?',function(r) {
		if(r){
			var data = {};
			data["getDetailData"] = getDetailData();
			$.ajax({
				url:_basePath+"/fundNotEBank/FundNotEBank!doCancellation.action",
			    type:'post',
			    data:'data='+sqlData.join(','),
			    dataType:'json',
			    success:function(json){
					//页面刷新
			    	$('#pageTable').datagrid('reload');
			    }
			});
		}
	});
}

/**
 * 检验选中数据-作废
 * @return
 */
function toCheckedReturn(){
	var detailData = $("#pageTable").datagrid('getSelections');
	var FI_STATUS = '';	
	var flag=false;
	for(var i = 0; i<detailData.length; i++) {
		FI_STATUS = detailData[i].FI_STATUS;
		if(FI_STATUS=="已核销"||FI_STATUS=="未核销"){
			flag=false;
		}else{
			flag=true;
		}
	}
	return flag;
}

/**********************************************通用方法，获取选中数据*****************************************************************/

/**
 * 获取选中数据
 * @author yx
 * @date 2013-09-29
 * @return
 */
function getDetailData(){
	var getDetailData = [];
	var detailData = $("#pageTable").datagrid('getSelections');
	for(var i = 0; i<detailData.length; i++) {
		var temp = {};
		temp.FUND_ID = detailData[i].HEAD_ID;		
		getDetailData.push(temp);
	}
	return getDetailData;
} 