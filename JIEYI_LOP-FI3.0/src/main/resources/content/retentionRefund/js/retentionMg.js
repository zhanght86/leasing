$(function() {
	$('#retention').datagrid({
		url:_basePath+"/retentionRefund/RetentionRefund!toMgRetentionData.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		columns:[[
		          {field:'RE_ID',align:'center',width:20,checkbox:true},
		          {field:'RE_CODE',align:'center',width:40,title:'付款单号'},
		          {field:'NAME',align:'center',width:40,title:'供应商'},
		          {field:'RE_PAYEE_UNIT',align:'center',width:40,title:'收款单位'},
		          {field:'RE_PAYEE_ADDRESS',align:'center',width:40,title:'地址'},
		          {field:'RE_PAYEE_BANK',align:'center',width:40,title:'开户行'},
		          {field:'RE_PAYEE_BANK_ADDR',align:'center',width:40,title:'开户行所在地'},
		          {field:'RE_PAYEE_ACCOUNT',align:'center',width:40,title:'付款账号'},
		          {field:'RE_MONEY',align:'center',width:40,title:'留购价'},
		          {field:'RE_DATE',align:'center',width:40,title:'付款日期'},
		          {field:'RE_STATUS',align:'center',width:40,title:'确认状态'},
		          
		          ]],
		//以下为”+“号展看显示内容。
		view : detailview,
		detailFormatter : function(index, row) {
			return '<div id="ddv-' + index + '" style="padding:5px 0;"></div>';
		},
		onExpandRow : function(index, row) {
			jQuery.ajax({
				url:_basePath+"/retentionRefund/RetentionRefund!findDetailApp.action?RE_ID="+row.RE_ID,  
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
                            {field:'ORG_NAME',align:'center',width:40,title:'供应商'},
                            {field:'PRO_CODE',align:'center',width:40,title:'项编号'},
                            {field:'CLIENT_NAME',align:'center',width:50,title:'客户名称'},
                            {field:'EQUIPMENINFOS',align:'center',width:45,title:'租赁物类型'},
                            {field:'LEASE_TOPRIC',align:'center',width:60,title:'租赁物购买价款'},
                            {field:'START_DATE',align:'center',width:50,title:'起租确认日期'},
                            {field:'ITEM_NAME',align:'center',width:50,title:'款项名称'},
                            {field:'PLAN_DATE',align:'center',width:50,title:'退款计划日期'},
                            {field:'ITEM_MONEY',align:'center',width:50,title:'款项金额'},
                            {field:'SUP_NAME',align:'center',width:50,title:'收款单位'},                            
                            {field:'PAY_DATE',align:'center',width:50,title:'租赁到期日'},
  						    {field:'FINISH_TYPE',align:'center',width:50,title:'结束方式'}
                     ]],
                     
                     onResize:function(){
                        $('#retention').datagrid('fixDetailRowHeight',index);
                    },
                    onLoadSuccess:function(){
                        setTimeout(function(){
                            $('#retention').datagrid('fixDetailRowHeight',index);
                        },0);
                    }
				});

                $('#retention').datagrid('fixDetailRowHeight',index);
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
	var RE_PAYEE_UNIT = $("input[name='RE_PAYEE_UNIT']").val();
	var SUPPLIER_NAME = $("input[name='SUPPLIER_NAME']").val();
	$('#retention').datagrid('load', {
		"RE_PAYEE_UNIT" : RE_PAYEE_UNIT,
		"SUPPLIER_NAME" : SUPPLIER_NAME
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

/**************************************************留购价退款-申请******************************************************************/
 /**
 * 申请留购价退款
 * @author 杨雪
 * @return
 */
function toAppPayment(){
	window.location.href = _basePath+"/retentionRefund/RetentionRefund!toAppRetention.action";
}



/**
 * 提交
 * @return
 */
function toCommitApp(){
	if(getDetailData==''){
		$.messager.alert("提示","请选择提交数据！");
	}else{
		if(toCheckedCommit()){
			$.messager.confirm('提示','确定提交申请?',function(r) {
				if(r){
					var data = {};
					data["getDetailData"] = getDetailData();
					$.ajax({
						url:_basePath+"/retentionRefund/RetentionRefund!doCommitApp.action",
					    type:'post',
					    data:'data='+JSON.stringify(data),
					    dataType:'json',
					    success:function(json){
						    if(json.flag == true){
						    	$.messager.alert("留购价退款提交","留购价退款提交成功！！");				    	
						    }else {
						    	$.messager.alert("留购价退款提交","留购价退款提交失败！！");				    	
						    }
						  //页面刷新
					    	$('#retention').datagrid('reload');
					    }
					});
				}
			});
		}else{
			$.messager.alert("提示","请选择未退款数据！");
		}
	}
}


/**********************************************留购价退款-作废**********************************************************************/

function doCancellation(){
	if(getDetailData==''){
		$.messager.alert("提示","请选择作废数据！");
	}else{
		if(toCheckedSelectZ()){
			$.messager.confirm('提示','确定作废申请?',function(r) {
				if(r){				
					var data = {};
					data["getDetailData"] = getDetailData();
					$.ajax({
						url:_basePath+"/retentionRefund/RetentionRefund!doDelApp.action",
					    type:'post',
					    data:'data='+JSON.stringify(data),
					    dataType:'json',
					    success:function(json){
						    if(json.flag == true){
						    	$.messager.alert("提示","作废成功！！");
						    
						    }else {
						    	$.messager.alert("提示","作废成功！！");
						    }
							//页面刷新
					    	$('#retention').datagrid('reload');
					    }
					});			
				}
			});
		}else{
			$.messager.alert("提示","请选择未退款数据！");
		}
	}	
}


/**********************************************留购价退款-通用方法**********************************************************************/

/**
 * 获取选中数据
 * @author yx
 * @date 2013-09-29
 * @return
 */
function getDetailData(){
	var getDetailData = [];
	var detailData = $("#retention").datagrid('getSelections');
	for(var i = 0; i<detailData.length; i++) {
		var temp = {};
		temp.RE_ID = detailData[i].RE_ID;		
		getDetailData.push(temp);
	}
	return getDetailData;
} 

/**
 * 检验选中数据-作废
 * @return
 */
function toCheckedCommit(){
	var detailData = $("#retention").datagrid('getSelections');
	var RE_STATUS = '';
	var flag=false;
	for(var i = 0; i<detailData.length; i++) {
		RE_STATUS = detailData[i].RE_STATUS;		
		if(RE_STATUS!="未退款"){
			flag=false;
		}else{
			flag=true;
		}
	}
	return flag;
}

/**
 * 检验选中数据-作废
 * @return
 */
function toCheckedSelectZ(){
	var detailData = $("#retention").datagrid('getSelections');
	var RE_STATUS = '';
	var flag=false;
	for(var i = 0; i<detailData.length; i++) {
		RE_STATUS = detailData[i].RE_STATUS;		
		if(RE_STATUS=="已退款"){
			flag=false;
		}else{
			flag=true;
		}
	}
	return flag;
}