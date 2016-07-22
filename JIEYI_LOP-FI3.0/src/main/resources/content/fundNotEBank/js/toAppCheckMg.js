var ii = 0 ;
$(function() {
	$('#pageTable').datagrid({
		url:_basePath+"/fundNotEBank/FundNotEBank!toMgAppCheckMgData.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		fitColumns:false,
		toolbar:'#pageForm',
		onClickCell:onClickCell,
		columns:[[
		          {field:'ck',align:'center',width:30,checkbox:true},
		          {field:'HEAD_ID',align:'center',width:60,title:'付款单号'},
		          {field:'PRO_CODE',align:'center',width:120,title:'项目编号'},
		          {field:'SUPPLIER_NAME',align:'center',width:120,title:'供应商名称'},
		          {field:'FI_PAY_TYPE',align:'center',width:90,title:'付款方式'},
		          {field:'FI_REALITY_MONEY',align:'center',width:90,title:'付款金额'},
		          {field:'FI_PAY_DATE',align:'center',width:90,title:'付款日期'},
		          {field:'FI_PROJECT_NUM',align:'center',width:90,title:'项目数量'},
		          {field:'FI_ACCOUNT_DATE',align:'center',width:90,title:'到账日期'},
		          {field:'FI_REALITY_BANK',align:'center',width:90,title:'核销银行'},
		          {field:'FI_STATUS',align:'center',width:90,title:'确认状态'},
		          {field:'FI_REMARK',align:'center',width:100,editor:{type:'text'},title:'驳回原因'}
		          ]],
		view : detailview,
		detailFormatter : function(index, row) {
			return '<div id="ddv-' + index + '" style="padding:5px 0;"></div>';
		},
		onExpandRow : function(index, row) {
			$('#ddv-' + index).datagrid({
                loadMsg:'',
				height:'520',//列表展开显示错乱问题  100% 改为固定360 
				href : _basePath+"/fundNotEBank/FundNotEBank!getCheckedDetail.action?FUND_NUM="+ii+"&FUND_ID="+row.HEAD_ID, 
			    onResize:function(){
                    $('#pageTable').datagrid('fixDetailRowHeight',index);
                },
                onLoadSuccess:function(){
                    setTimeout(function(){
                        $('#pageTable').datagrid('fixDetailRowHeight',index);
                    },0);
                }
			});
			++ii;
			$('#pageTable').datagrid('fixDetailRowHeight', index);
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
	var FI_SUPPLIERS_ID = $("input[name='FI_SUPPLIERS_ID']").val();
	var FI_PAY_DATE1 = $("input[name='FI_PAY_DATE1']").val();
	var FI_PAY_DATE2 = $("input[name='FI_PAY_DATE2']").val();
	$('#pageTable').datagrid('load', {
		"HEAD_ID" : HEAD_ID,
		"FI_SUPPLIERS_ID" : FI_SUPPLIERS_ID,
		"FI_PAY_DATE1" : FI_PAY_DATE1,
		"FI_PAY_DATE2" : FI_PAY_DATE2
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


/******************************************************以下为资金扣划核销操作*********************************************************************/
/**
 * 提交
 * 用户在点击展开页面后， 填写相关数据后提交保存
 * @author yx
 * @date  2013-10-09
 * @return
 */
function toCommit(FUND_ID_APP){

//	if($("#FI_TO_THE_PAYEE").val()==null || $("#FI_TO_THE_PAYEE").val()==undefined){
//		return $.messager.alert('警告','请填写来款人');
//	}//$('#cc').combobox('setValue', '001')
//	if($("#FI_REALITY_BANK").attr('selected',true)==null || $("#FI_REALITY_BANK").attr('selected',true)==undefined){
//		return $.messager.alert('警告','请填写核销银行');
//	}
	if($("#FI_ACCOUNT_DATE"+FUND_ID_APP).datebox('getValue')==null || $("#FI_ACCOUNT_DATE"+FUND_ID_APP).datebox('getValue')==""){
		return $.messager.alert('警告','请填写核销时间');
	}
	
	
	var data = {};
	data["getBaseData"] = getBaseData(FUND_ID_APP);	
	data["getDetailData"] = getDetailData1();
	
	if($("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val()==null || $("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val()==undefined){
		return $.messager.alert('警告','请填写本次实付');
	}else{
		if(parseFloat($("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val())<parseFloat($("#fi_pay_money_"+FUND_ID_APP).val())){
			var totalMoney = parseFloat($("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val())+parseFloat($("#realityMoneyTotal").val());
			if(parseFloat(totalMoney)>parseFloat($("#fi_pay_money_"+FUND_ID_APP).val())){
				return $.messager.alert('警告','选择金额大于本次应收核销金额,请重新选择金额');
			}else if(parseFloat(totalMoney)<parseFloat($("#fi_pay_money_"+FUND_ID_APP).val())){
				return $.messager.alert('警告','选择金额应等于本次应收核销金额,请重新选择金额');
			}else{
				data["getPoolData"] = getPoolData();	
			}
		}
	}	
	
	$.ajax({		
		url:_basePath+"/fundNotEBank/FundNotEBank!doCommitHXSheet.action",
	    type:'post',
	    data:'data='+JSON.stringify(data),
	    dataType:'json',
	    success:function(json){
		 if(json.flag == true){
			 $.messager.alert("提示","保存成功！！");
			
			//刷新页面
			$('#pageTable').datagrid('load');
		 }else{
			 $.messager.alert("提示","保存失败！！");
			 
			//刷新页面
			$('#pageTable').datagrid('load');
		 }
		}
	});
}

//核销
function toCommitHexiao(FUND_ID_APP){
//	if($("#FI_TO_THE_PAYEE").val()==null || $("#FI_TO_THE_PAYEE").val()==undefined){
//		return $.messager.alert('警告','请填写来款人');
//	}//$('#cc').combobox('setValue', '001')
//	if($("#FI_REALITY_BANK").attr('selected',true)==null || $("#FI_REALITY_BANK").attr('selected',true)==undefined){
//		return $.messager.alert('警告','请填写核销银行');
//	}
	
	var head_flag = false ; 

	$.ajax( {
	    async:false,
			url : _basePath+ "/fundNotEBank/FundNotEBank!checkFirstInfoByHeadIds.action",
	       type : 'post',
		   data : 'HEAD_IDS=' + FUND_ID_APP,
	   dataType : 'json',
		success : function(json) {
			if (json.flag == true) {
				head_flag = true ;
				$.messager.alert("提示","收款单号:"+FUND_ID_APP+json.msg );
			} 		
		}
	});
	if(head_flag == true){
		return false;
	}
	
	if($("#FI_ACCOUNT_DATE"+FUND_ID_APP).datebox('getValue')==null || $("#FI_ACCOUNT_DATE"+FUND_ID_APP).datebox('getValue')==""){
		return $.messager.alert('警告','请填写核销时间!');
	}
	
	
	var data = {};
	data["getBaseData"] = getBaseData();	
	data["getDetailData"] = getDetailData1();
	if($("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val()==null || $("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val()==undefined){
		return $.messager.alert('警告','请填写本次实付');
	}else{
		if(parseFloat($("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val())<parseFloat($("#fi_pay_money_"+FUND_ID_APP).val())){
			var totalMoney = parseFloat($("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val())+parseFloat($("#realityMoneyTotal").val());
			if(parseFloat(totalMoney)>parseFloat($("#fi_pay_money_"+FUND_ID_APP).val())){
				return $.messager.alert('警告','选择金额大于本次应收核销金额请重新选择金额');
			}else if(parseFloat(totalMoney)<parseFloat($("#fi_pay_money_"+FUND_ID_APP).val())){
				return $.messager.alert('警告','选择金额应等于本次应收核销金额,请重新选择金额');
			}else{
				data["getPoolData"] = getPoolData();	
			}
		}
	}	
	
	$.ajax({		
		url:_basePath+"/fundNotEBank/FundNotEBank!doCommitHXSheetPool.action",
	    type:'post',
	    data:'data='+JSON.stringify(data),
	    dataType:'json',
	    success:function(json){
		 if(json.flag == true){
			 $.messager.alert("提示","保存成功！！");			
		 }else{
			 $.messager.alert("提示","保存失败！！");			 
		 }
		//刷新页面
		$('#pageTable').datagrid('load');
		}
	});
}

/**
 * 提交的基础数据提取
 * @author yx
 * @date  2013-10-09
 * @return
 */
function getBaseData(FUND_ID_APP){
	var getBaseData = {};
	var FI_PAY_BANK = $("#FI_PAY_BANK"+FUND_ID_APP).val();//付款银行
	var FI_ACCOUNT_DATE = $("#FI_ACCOUNT_DATE"+FUND_ID_APP).datebox('getValue');//核销时间
	var FI_REALITY_BANK = $("#FI_REALITY_BANK"+FUND_ID_APP).attr("selected",true).val();//核销银行
	var FI_TAGE = $("#FI_TAGE"+FUND_ID_APP).find("option:selected").val();//余款挂账类型
	var FI_TAGE_ID= $("#FI_TAGE_ID"+FUND_ID_APP).find("option:selected").val();//挂账人员id
	var FI_TAGE_NAME= $("#FI_TAGE_ID"+FUND_ID_APP).find("option:selected").attr("FI_TAGE_NAME");//挂账人名称
	getBaseData["FI_PAY_BANK"] = FI_PAY_BANK;
	getBaseData["FI_ACCOUNT_DATE"] = FI_ACCOUNT_DATE;
	getBaseData["FI_REALITY_BANK"] = FI_REALITY_BANK;
	getBaseData["FI_TAGE"] = FI_TAGE;
	getBaseData["FI_TAGE_ID"] = FI_TAGE_ID;
	getBaseData["FI_TAGE_NAME"] = FI_TAGE_NAME;
	getBaseData["FUND_ID"] = $("#FUND_ID"+FUND_ID_APP).val();//申请单id
	getBaseData["FI_TO_THE_PAYEE"] = $("#FI_TO_THE_PAYEE"+FUND_ID_APP).val();//来款人
	getBaseData["FI_REALITY_MONEY"] = $("#FI_REALITY_MONEY"+FUND_ID_APP).val();//实际来款金
	getBaseData["FI_REALITY_ACCOUNT"] = $("#FI_REALITY_ACCOUNT"+FUND_ID_APP).val();//实际付款金额
	getBaseData["FI_TAGE_MONEY"] = $("#FI_PAY_YUE"+FUND_ID_APP).val();//本次来款余额
	var FUND_ID = $("#FUND_ID"+FUND_ID_APP).val();
	getBaseData["MONEYORDERSAVE"] = $("#moneyOrderSave"+FUND_ID).val();
	return getBaseData;
}

/**
 * 获取资金池中数据
 * @return
 */
function getPoolData(){
	var getPoolData = [];
	$("#pool_tab tr").each(function(){
		var temp = {};
		temp.POOL_TYPE= $(this).find("input[name=POOLID]").val();//租金池类别
		temp.POOLUSERMONEY = $(this).find("input[name=POOLUSERMONEY]").val();//可用余额
		temp.dichong_money = $(this).find("input[name=dichong_money]").val();//所选冲抵款合计
		getPoolData.push(temp);
	});
	return getPoolData;
}

/**
 * 获取列表数据
 * @return
 */
function getDetailData1(){
	var getDetailData = [];
	var detailData = $("#pageTable_").datagrid('getSelections');
	for(var i = 0; i<detailData.length; i++) {
		var temp = {};
		temp.D_PROJECT_CODE = detailData[i].D_PROJECT_CODE;
		temp.D_CLIENT_CODE = detailData[i].D_CLIENT_CODE;
		temp.YS_MONEY = detailData[i].YS_MONEY;
		temp.RECEIVABLE = detailData[i].RECEIVABLE;
		//temp.D_PAY_MONEY = detailData[i].D_PAY_MONEY;
		temp.VERITABLE_MONEY = detailData[i].VERITABLE_MONEY;
		getDetailData.push(temp);
	}
	return getDetailData;//JSON.stringify(getDetailData);//encodeURI(JSON.stringify(getDetailData));
} 

/**
 * 核销
 * 
 * 用户在核销页面中选择将要核销的数据，后核销选择内容
 * 
 * @author yx
 * @date  2013-10-09
 * @return
 */
function toCommintFund(){
	if(getDetailData()==''){
		return $.messager.alert('警告','请填选择提交数据');
	}else{		
		var detailData = $("#pageTable").datagrid('getSelections');//获取选中信息
		for(var i = 0; i<detailData.length; i++) {
			var HEAD_ID = detailData[i].HEAD_ID;//付款单号
			var FI_ACCOUNT_DATE = detailData[i].FI_ACCOUNT_DATE;//到账日期
			var FI_REALITY_BANK = detailData[i].FI_REALITY_BANK;//核销银行
			var head_flag = false ; 

			$.ajax( {
			    async:false,
					url : _basePath+ "/fundNotEBank/FundNotEBank!checkFirstInfoByHeadIds.action",
			       type : 'post',
				   data : 'HEAD_IDS=' + HEAD_ID,
			   dataType : 'json',
				success : function(json) {
					if (json.flag == true) {
						head_flag = true ;
						$.messager.alert("提示","收款单号:"+HEAD_ID+json.msg );
					} 		
				}
			});
			if(head_flag == true){
				return false;
			}
			if(FI_ACCOUNT_DATE==null || FI_ACCOUNT_DATE=='undefined' || FI_ACCOUNT_DATE.length <=0)
			{
				$.messager.alert("提示","收款单号:"+HEAD_ID+"没有保存到账日期,不能核销!");
				return false;
			}
			
			if(FI_REALITY_BANK==null || FI_REALITY_BANK=='undefined' || FI_REALITY_BANK.length<=0)
			{
				$.messager.alert("提示","收款单号:"+HEAD_ID+"没有保存核销银行,不能核销!");
				return false;
			}

		}

		
		$.messager.confirm('提示','确定核销吗?',function(r) {
			if (r) {
					var data = {};
					data["getDetailData"] = getDetailData();

					$.ajax( {
							url : _basePath+ "/fundNotEBank/FundNotEBank!doCheckedFund.action",
					       type : 'post',
						   data : 'data=' + JSON.stringify(data),
					   dataType : 'json',
						success : function(json) {
							if (json.flag == true) {
								$.messager.alert("提示",
										"审核成功！！");
							} else {
								$.messager.alert("提示",
										"审核失败！！");
							}
							//刷新页面
							$('#pageTable').datagrid('load');					
							}
					});
			}
		});
		
	}
}

/**
 * 核销/驳回数据提取
 * @return
 */
function getDetailData(){
	var getDetailData = [];
	var detailData = $("#pageTable").datagrid('getSelections');//获取选中信息
	for(var i = 0; i<detailData.length; i++) {
		var temp = {};
		temp.HEAD_ID = detailData[i].HEAD_ID;//付款单号
		temp.FI_PAY_DATE = detailData[i].FI_PAY_DATE;//付款日期
		temp.FI_ACCOUNT_DATE = detailData[i].FI_ACCOUNT_DATE;//到账日期
		temp.FI_REALITY_BANK = detailData[i].FI_REALITY_BANK;//核销银行
		temp.FI_REMARK = detailData[i].FI_REMARK;//驳回原因
		getDetailData.push(temp);
	}
	return getDetailData;
}

/**
 * 本次实付在修改时， 本次实付金额要与本次应收核销进行对比。
 * 
 * 当本次实付金额大于本次应收金额时： 计算本次来款余额。 即为：本次实付金额-本次应收金额
 * 
 * 当本次实付金额小于本次应收金额时： 本次实付金额+余款池的总金额 = 本次应收金额
 * 
 * @author yx
 * @date 2013-10-10
 * @return
 */
function toChange(FUND_ID){
	var FI_REALITY_ACCOUNT = parseFloat($("#FI_REALITY_ACCOUNT"+FUND_ID).val());//本次实付
	var fi_pay_money = parseFloat($("#fi_pay_money_"+FUND_ID).val());//本次应收核销
	var unusedAmount = parseFloat(0);
	//当本次实付>本次应收金额时， 计算本次来款余额
	if(FI_REALITY_ACCOUNT>=fi_pay_money){
		unusedAmount = FI_REALITY_ACCOUNT - fi_pay_money;//本次来款余额
		$("#poolTab").hide();
		$("a[name='saveHref']").show();
		$("a[name='HeXaioFirst']").hide();
	}else if(FI_REALITY_ACCOUNT<fi_pay_money){
		//本次来款余额
		unusedAmount=0;		
		$("#poolTab").show();
		$("a[name='saveHref']").hide();
		$("a[name='HeXaioFirst']").show();
	}
	
	$("#FI_PAY_YUE"+FUND_ID).val("");
	$("#FI_PAY_YUE"+FUND_ID).val(unusedAmount);
	$("#moneyOrder"+FUND_ID).val(FI_REALITY_ACCOUNT);
}

/**
 * 计算应/实收金额总数
 * @author yx
 * @date 2013-10-10
 * @return
 */
function toChangePool(obj){
	
	//1.判断输入金额要小于等于可使用金额
	var tr=$(obj).parent().parent();
	var money1=$(tr).find("input[name='POOLUSERMONEY']").val();//可使用金额
	var money2=$(tr).find("input[name='dichong_money']").val();//想抵充金额
	if(parseFloat(money2)>money1)
	{
		$(tr).find("input[name='dichong_money']").val(money1);
	}
	
	//2.总金额累加
	var realityMoneyTotal = parseFloat(0);
	$("#pool_tab input[name=dichong_money]").each(function(){
		if(isNaN($(this).val())){
			alert("请填写正确数字！！");
			$(this).val("0.00");
		}else{
			realityMoneyTotal += parseFloat($(this).val());
		}
	});
	$("#realityMoneyTotal").val(realityMoneyTotal);
}


function updateMoneyOrder(obj){
	 $("#dlgUp"+obj).dialog("open");
}
/******************************************************以上为资金扣划核销操作*****************************************************************************************/

/******************************************************以下为资金扣划驳回操作*****************************************************************************************/
function doRegist(){
	if(getDetailData()==''){
		$.messager.alert("警告","请选择需驳回数据！！");
	}else{
		$.messager.confirm('提示','确定要驳回吗?', function(r) {
			if (r) {
				var data = {};
				data["getDetailData"] = getDetailData();
				
				$.ajax({		
					url:_basePath+"/fundNotEBank/FundNotEBank!doReject.action",
				    type:'post',
				    data:'data='+JSON.stringify(data),
				    dataType:'json',
				    success:function(json){
					 if(json.flag == true){
						 $.messager.alert("提示","驳回成功！！");				
					 }else{
						 $.messager.alert("提示","驳回失败！！");
					 }
					//刷新页面
					$('#pageTable').datagrid('load');
					}
				});
			}		
		});
	}
}
/******************************************************以上为资金扣划驳回操作*****************************************************************************************/

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
	$("#pageTable").datagrid("checkRow",editIndex);
	
	if ($('#pageTable').datagrid('validateRow', editIndex)) {		
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

function onClickCell(index, field) {
	$('#pageTable').datagrid('endEdit', editIndex);
	if (endEditing()) {
		$('#pageTable').datagrid('editCell', {index : index,field : field});
		editIndex = index;
	}
}

/**
 * 单元格修改好变更数据
 * @auth yx
 * @date 2013-09-28
 * @param rowIndex
 * @param rowData
 * @param changes
 * @return
 */
//function toChangeCell(rowIndex,rowData,changes){
//	var VERITABLE_MONEY=changes.VERITABLE_MONEY;//本次实收金额
//	var RECEIVABLE  = rowData.RECEIVABLE;//本次应收金额
//	var FI_PAY_MONEY = parseFloat($("#FI_PAY_MONEY").val());//本次实付
//	var fi_pay_money = parseFloat($("#fi_pay_money_").val());//本次应收核销
//	var unusedAmount = parseFloat(0);
//	
//	if(parseFloat(VERITABLE_MONEY)>parseFloat(RECEIVABLE)){
//		FI_PAY_MONEY = FI_PAY_MONEY - parseFloat(RECEIVABLE) + parseFloat(VERITABLE_MONEY);
//		unusedAmount = FI_PAY_MONEY - fi_pay_money;
//	}else if(parseFloat(VERITABLE_MONEY)<parseFloat(RECEIVABLE)){
//		FI_PAY_MONEY = FI_PAY_MONEY - parseFloat(RECEIVABLE) + parseFloat(VERITABLE_MONEY);
//		unusedAmount = 0;
//	}
//	
//	$("#FI_PAY_YUE").val(unusedAmount);//本次实付余额
//	$("#FI_PAY_MONEY").val(FI_PAY_MONEY);//本次实付
//	
//}
/********************************************************以上为设置单元格可修改***杨雪*****************~~**************************************/

/********************************************************通用方法****************************************************************************/
function fomatFloat(src,pos){       
    return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);       
} 

/**
 * 加法
 * @param arg1
 * @param arg2
 * @return
 */
function accAdd(arg1, arg2) {
    var r1, r2, m;
    try { r1 = arg1.toString().split(".")[1].length; } catch (e) { r1 = 0; }
    try { r2 = arg2.toString().split(".")[1].length; } catch (e) { r2 = 0; }
    m = Math.pow(10, Math.max(r1, r2));
    return (arg1 * m + arg2 * m) / m;
}