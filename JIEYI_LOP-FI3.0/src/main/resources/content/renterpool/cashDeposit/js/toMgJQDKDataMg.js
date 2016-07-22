$(function() {
	$('#pageTable_DK').datagrid({
		view : detailview,
		detailFormatter : function(index, row) {
			return '<div id="ddv-' + index + '" style="padding:5px 0;"></div>';
		},
		onExpandRow : function(index, row) {
			$('#ddv-' + index).datagrid({
                loadMsg:'',
                height:'520',//列表展开显示错乱问题  100% 改为固定360
                cache:false,
                fitColumns:true,
				href : _basePath+"/renterpool/CashDeposit!getJQDKDetail.action?FUND_ID="+row.FH_ID,              	
			    onResize:function(){
                    $('#pageTable_DK').datagrid('fixDetailRowHeight',index);
                },
                onLoadSuccess:function(){
                    setTimeout(function(){
                        $('#pageTable_DK').datagrid('fixDetailRowHeight',index);
                    },0);
                }
			});
			$('#pageTable_DK').datagrid('fixDetailRowHeight', index);
		}
	});
});

/**CashDeposit
 * 清空查询数据
 * @author 杨雪
 * @return
 */
function clearQuery(){
	$("#FI_PAY_DATE").datebox('clear');
	$("#FI_PAY_DATE1").datebox('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}

/**
 * 结清抵扣申请
 * @return
 */
function toAppPayment(){
	top.addTab("结清抵扣申请",_basePath+"/renterpool/CashDeposit!toMgDeductionBZJ.action");
	//window.location.href = _basePath+"/renterpool/CashDeposit!toMgDeductionBZJ.action";
}



/**
 * 提交申请数据
 * @author yx
 * @date 2013-09-29
 * @return
 */
function toSubmiyApp(){
	$.messager.confirm('提示','确定核销申请?',function(r) {
		if(r){
			if(toCheckedCommit()){
				var data = {};
				data["getDetailData"] = getDetailData();
				
				
				var detailData = $("#pageTable_DK").datagrid('getSelections');//获取选中信息
				for(var i = 0; i<detailData.length; i++) {
					var HEAD_ID = detailData[i].FH_ID;//付款单号
					var FI_ACCOUNT_DATE = detailData[i].FI_ACCOUNT_DATE;//到账日期
					var FI_REALITY_BANK = detailData[i].FI_REALITY_BANK;//核销银行
					if(FI_ACCOUNT_DATE==null || FI_ACCOUNT_DATE=='undefined' || FI_ACCOUNT_DATE.length <=0)
					{
						alert("收款单号:"+HEAD_ID+"没有保存到账日期，不能核销！");
						return false;
					}
					
					if(FI_REALITY_BANK==null || FI_REALITY_BANK=='undefined' || FI_REALITY_BANK.length<=0)
					{
						alert("收款单号:"+HEAD_ID+"没有保存核销银行，不能核销！");
						return false;
					}
				}

				
				$.ajax({
					url:_basePath+"/renterpool/CashDeposit!doSubmitAppJQDK.action",
				    type:'post',
				    data:'data='+JSON.stringify(data),
				    dataType:'json',
				    success:function(json){
					    if(json.flag == true){
					    	$.messager.alert("承租人保证金池-结清抵扣核销",json.msg);	
					    	//页面刷新
							$('#pageTable_DK').datagrid('load');
					    }else {
					    	$.messager.alert("承租人保证金池-结清抵扣核销",json.msg);
					    	//页面刷新
							$('#pageTable_DK').datagrid('load');
					    }
					    
				    }
				});
			}else{
				$.messager.alert("承租人保证金池-结清抵扣核销","请选择未核销的数据");
			}			
		}
	});
}

/**
 * 检验选中数据-提交
 * @return
 */
function toCheckedCommit(){
	var detailData = $("#pageTable_DK").datagrid('getSelections');
	var FI_STATUS = '';
	var flag=false;
	for(var i = 0; i<detailData.length; i++) {
		FI_STATUS = detailData[i].FI_STATUS;		
		if(FI_STATUS!="未核销"){
			flag=false;
		}else{
			flag=true;
		}
	}
	return flag;
}

function doCancellation(){
	$.messager.confirm('提示','确定作废申请?',function(r) {
		if(r){
			var data = {};
			data["getDetailData"] = getDetailData();
			$.ajax({
				url:_basePath+"/renterpool/CashDeposit!doCancellation.action",
			    type:'post',
			    data:'data='+JSON.stringify(data),
			    dataType:'json',
			    success:function(json){
				    if(json.flag == true){
				    	$.messager.alert("提示","作废成功！！");
				    	//页面刷新
						$('#pageTable_DK').datagrid('load');
				    }else {
				    	$.messager.alert("提示","作废成功！！");
				    	//页面刷新
						$('#pageTable_DK').datagrid('load');
				    }
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
	var detailData = $("#pageTable_DK").datagrid('getSelections');
	var FI_STATUS = '';
	
	var flag=false;
	for(var i = 0; i<detailData.length; i++) {
		FI_STATUS = detailData[i].FI_STATUS;
		if(FI_STATUS=="已核销"||FI_STATUS=="已提交"){
			flag=false;
		}else{
			flag=true;
		}
	}
	return flag;
}




/********************************************************以下为设置单元格可修改***杨雪********************************************************************************/

$.extend($.fn.datagrid.methods, {
	editCell : function(jq, param) {
		return jq.each(function() {
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields', true).concat(
					$(this).datagrid('getColumnFields'));
			for ( var i = 0; i < fields.length; i++) {
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field) {
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for ( var i = 0; i < fields.length; i++) {
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});

var editIndex = undefined;
function endEditing() {
	if (editIndex == undefined) {
		return true;
	}
	$("#pageTable_DK").datagrid("checkRow",editIndex);
	
	if ($('#pageTable_DK').datagrid('validateRow', editIndex)) {		
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}


function onClickCell(index, field) {
	$('#pageTable_DK').datagrid('endEdit', editIndex);
	if (endEditing()) {
		$('#pageTable_DK').datagrid('editCell', {index : index,field : field});
		editIndex = index;
	}
}

/**
 * 核销/驳回数据提取
 * @return
 */
function getDetailData(){
	var getDetailData = [];
	var detailData = $("#pageTable_DK").datagrid('getSelections');//获取选中信息
	for(var i = 0; i<detailData.length; i++) {
		var temp = {};
		temp.HEAD_ID = detailData[i].FH_ID;//付款单号
		temp.FI_PAY_DATE = detailData[i].FI_PAY_DATE;//付款日期
		temp.FI_ACCOUNT_DATE = detailData[i].FI_ACCOUNT_DATE;//到账日期
		temp.FI_REALITY_BANK = detailData[i].FI_REALITY_BANK;//核销银行
		temp.FI_REMARK = detailData[i].FI_REMARK;//驳回原因
		getDetailData.push(temp);
	}
	return getDetailData;
}