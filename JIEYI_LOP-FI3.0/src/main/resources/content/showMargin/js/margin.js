
//页面加载隐藏  确认入网保证金收款  和  确认逾期保证金收款区域
$(function(){
	$("#affirmPayable").dialog('close');
	$("#affirmLate").dialog('close');
	$("#tuiKuan").dialog('close');
});

function tofindData(){
	var SUP_NAME = $("#sup_name").val();
	var PAYMENTTYPE = $("#paymentType").val();
	$('#pageTable').datagrid('load', {
		"SUP_NAME" : SUP_NAME,
		"PAYMENTTYPE" : PAYMENTTYPE
	});
}

function emptyData(){
	$("#sup_name").val("");
	$("#paymentType").val("0");
}

function payType(value, rowData){
	if(rowData.PAYMENTTYPE == "1"){
		return "一次";
	}
	if(rowData.PAYMENTTYPE == "2"){
		return "分期";
	}
	return "";
}

function closeLate(){
	$("#affirmLate").dialog('close');
}

function closeAffrim(){
	$("#affirmPayable").dialog('close');
}

function closeTuiKuan(){
	$("#tuiKuan").dialog('close');
}

// 加载灯泡图片
function icon(value, rowData){
	if(rowData.PAYABLEINITIALMARGIN == rowData.INDEEDINITIALMARGIN &&
			rowData.PAYABLELATEMARGIN == rowData.INDEEDLATEMARGIN){
		return "<img src='../showMargin/icon/green.gif'>";
	}else{
		return "<img src='../showMargin/icon/red2.gif'>";
	}
}

// 查看保证金缴纳明细
function showDetail(value, rowData){
	if(rowData.ID == undefined){
		return "";
	}
	var param = {};
	var ID = rowData.ID;
	param.T_REFOUND_MARGIN_ID = ID;
	var paramJson = JSON.stringify(param);
	var array = new Array() ;
	array.push(paramJson) ;
	return "<a href='Margin!toShowMarginDetail.action?paramJson="+paramJson+"'>查看</a>";
}

// 确认入网保证金收款
function affirmPayable(value, rowData){
	if(rowData.PAYABLEINITIALMARGIN == undefined){
		return "";
	}else if(rowData.PAYABLEINITIALMARGIN == rowData.INDEEDINITIALMARGIN){
		return "收款完成";
	}else{
		return "<a onclick=openAffirmPayable('"+rowData.ID+"','"+rowData.PAYABLEINITIALMARGIN+"','"
			+rowData.INDEEDINITIALMARGIN+"','"+rowData.SUP_ID+"')>确认收款</a>";
	}
}

// 弹出确认入网保证金收款页面
function openAffirmPayable(ID,PAYABLEINITIALMARGIN,INDEEDINITIALMARGIN,SUB_ID){
	$("#sub_id_cs").val(SUB_ID);
	$("#sscsbzj").val("");
	$("#csId").val(ID);
	if(INDEEDINITIALMARGIN != "undefined"){
		$("#indeedinitialmargin").val(INDEEDINITIALMARGIN);
	}else{
		$("#indeedinitialmargin").val("");
	}
	if(PAYABLEINITIALMARGIN != "undefined"){
		$("#payableinitial").val(PAYABLEINITIALMARGIN);
		if(INDEEDINITIALMARGIN != "undefined"){
			$("#payable").html("<="+(Number(PAYABLEINITIALMARGIN)-Number(INDEEDINITIALMARGIN)));
		}else{
			$("#payable").html("<="+PAYABLEINITIALMARGIN);
		}
		$("#payable").css('color', 'red');
	}else{
		$("#payable").html("");
		$("#payableinitial").val("");
	}
	$("#affirmPayable").dialog("open");
}

// 确认逾期保证金收款
function affirmLate(value, rowData){
	if(rowData.PAYABLELATEMARGIN == undefined){
		return "";
	}else if(rowData.PAYABLELATEMARGIN == rowData.INDEEDLATEMARGIN){
		return "收款完成";
	}else{
		return "<a onclick=openAffirmLate('"+rowData.ID+"','"+rowData.PAYABLELATEMARGIN+"','"
			+rowData.INDEEDLATEMARGIN+"','"+rowData.SUP_ID+"')>确认收款</a>";
	}
}

// 弹出确认逾期保证金收款页面
function openAffirmLate(ID,PAYABLELATEMARGIN,INDEEDLATEMARGIN,SUB_ID){
	$("#sub_id_yq").val(SUB_ID);
	$("#ssyqbzj").val("");
	$("#yqId").val(ID);
	if(PAYABLELATEMARGIN != "undefined"){
		$("#payablelatemargin").val(PAYABLELATEMARGIN);
		if(INDEEDLATEMARGIN != "undefined"){
			$("#yuqiBZJ").html("<="+(Number(PAYABLELATEMARGIN)-Number(INDEEDLATEMARGIN)));
		}else{
			$("#yuqiBZJ").html("<="+PAYABLELATEMARGIN);
		}
		$("#yuqiBZJ").css('color', 'red');
	}else{
		$("#payablelatemargin").val("");
	}
	
	if(INDEEDLATEMARGIN != undefined){
		$("#indeedlatemargin").val(INDEEDLATEMARGIN);
	}else{
		$("#indeedlatemargin").val("");
	}
	$("#affirmLate").dialog("open");
}

// 逾期保证金收款
function updateMarginYQ(){
	
	var yqId = $("#yqId").val();
	var ssyqbzj = $("#ssyqbzj").val();
	var sub_id = $("#sub_id_yq").val();
	var time_yq = $("input[name='time_yq']").val();
	var project_no_yq = $("#project_no_yq").val();
	var payment_no_yq = $("#payment_no_yq").val();
	if(ssyqbzj == null || ssyqbzj == ""){
		alert("逾期保证金金额不能为空!");
		return;
	}
	
	var payablelatemargin = $("#payablelatemargin").val();
	var indeedlatemargin = $("#indeedlatemargin").val();
	
	if(ssyqbzj != "undefined"){
		if(indeedlatemargin != "undefined"){
			if((Number(payablelatemargin)-Number(indeedlatemargin)) < Number(ssyqbzj)){
				alert("实缴逾期保证金不能大于应缴逾期保证金!");
				return;
			}
		}else{
			if(Number(payablelatemargin) < Number(ssyqbzj)){
				alert("实缴逾期保证金不能大于应缴逾期保证金!");
				return;
			}
		}
	}
	if(indeedlatemargin != null && indeedlatemargin != "" && indeedlatemargin != "undefined"){
		payablelatemargin = Number(payablelatemargin) - Number(indeedlatemargin);
	}
	jQuery.ajax({
		url: _basePath+ "/margin/Margin!updateMarginYQ.action?yqId="+yqId+"&TIME="+time_yq+"&PAYMENT_NO="+payment_no_yq
 	    	+"&ssyqbzj="+ssyqbzj+"&payablelatemargin="+payablelatemargin+"&PROJECT_NO="+project_no_yq
 	    	+"&indeedlatemargin="+indeedlatemargin+"&sup_id="+sub_id,
 	    type:"post",
 	    dataType: "json",
 	    success: function (res,msg){
				 if(res){
					 alert("操作成功!");
					 $("#affirmLate").dialog("close");
					 $("#pageTable").datagrid('reload');
				 }else{
					 alert("保存失败!");
				 }
 	    },
     	error: function (callback){
 	    	alert("保存失败!");
 	    }
	 });
}

// 入网保证金收款
function updateMargin(){
	var csId = $("#csId").val();
	var sscsbzj = $("#sscsbzj").val();
	var sub_id = $("#sub_id_cs").val();
	var time_rw = $("input[name='time_rw']").val();
	var project_no = $("#project_no_rw").val();
	if(sscsbzj == null || sscsbzj == ""){
		alert("入网保证金金额不能为空!");
		return;
	}
	
	var payableinitial = $("#payableinitial").val();
	var indeedinitialmargin = $("#indeedinitialmargin").val();
	
	if(payableinitial != "undefined"){
		if((Number(payableinitial)-Number(indeedinitialmargin)) < Number(sscsbzj)){
			alert("实缴入网保证金不能大于应缴入网保证金!");
			return;
		}
	}else{
		if(Number(payableinitial) < Number(sscsbzj)){
			alert("实缴入网保证金不能大于应缴入网保证金!");
			return;
		}
	}
	if(indeedinitialmargin != null && indeedinitialmargin != "" && indeedinitialmargin != "undefined"){
		payableinitial = Number(payableinitial) - Number(indeedinitialmargin);
	}
	jQuery.ajax({
		url: _basePath+ "/margin/Margin!updateMargin.action?csId="+csId
 	    	+"&sscsbzj="+sscsbzj
 	    	+"&indeedinitialmargin="+indeedinitialmargin+"&sup_id="+sub_id
 	    	+"&payableinitial="+payableinitial+"&TIME="+time_rw+"&PROJECT_NO="+project_no,
 	    type:"post",
 	    dataType: "json",
 	    success: function (res,msg){
				 if(res){
					 alert("操作成功!");
					 $("#affirmPayable").dialog("close");
					 $("#pageTable").datagrid('reload');
				 }else{
					 alert("保存失败!");
				 }
 	    },
     	error: function (callback){
 	    	alert("保存失败!");
 	    }
	 });
}

// 退款
function lateTuiKuan(value, rowData){
	if(rowData.INDEEDLATEMARGIN == undefined && rowData.INDEEDINITIALMARGIN == undefined){
		return "";
	}else if(rowData.INDEEDLATEMARGIN == "0" && rowData.INDEEDINITIALMARGIN == "0"){
		return "";
	}else {
		return "<a onclick=openTuiKuan('"+rowData.ID+"','"+rowData.INDEEDINITIALMARGIN+"','"+
			rowData.INDEEDLATEMARGIN+"','"+rowData.SUP_ID+"')>退款</a>";
	}
}

// 弹出退款页面
function openTuiKuan(ID,INDEEDINITIALMARGIN,INDEEDLATEMARGIN,SUP_ID){
	$("#marginID").val(ID);
	$("#indeedinitialmargint").val(INDEEDINITIALMARGIN);
	$("#indeedlatemargint").val(INDEEDLATEMARGIN);
	$("#sup_id_tk").val(SUP_ID);
	$("#tuikuan").val("");
	$("#type").val("0");
	$("#tuiKuan").dialog("open");
}

// 执行退款操作
function tuiKuan(){
	var type = $("#type").val();
	if(type == null || type == ""){
		alert("请选择退款类型!");
		return;
	}
	var jine= $("#tuikuan").val();
	if(jine == null || jine == ""){
		alert("退款金额不能为空!");
		return;
	}
	var reg = /^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
	if(!reg.test(jine)){
		alert("退款金额格式错误!");
		return;
	}
	var id = $("#marginID").val();
	var indeedinitialmargint = $("#indeedinitialmargint").val();
	var indeedlatemargint = $("#indeedlatemargint").val();
	var sup_id_tk = $("#sup_id_tk").val();
	var time_tk = $("input[name='time_tk']").val();
	var project_no_tk = $("#project_no_tk").val();
	var payment_no_tk = $("#payment_no_tk").val();
	
	if(type == "1"){
		if(indeedinitialmargint == "undefined"){
			alert("实缴入网保证金为空不能退款!");
			return;
		}else if(Number(indeedinitialmargint) <= 0){
			alert("实缴入网保证金已为0不能退款!");
			return;
		}else if(Number(indeedinitialmargint) < Number(jine)){
			alert("入网保证金退款金额不能大于实缴入网保证金金额!");
			return;
		}
	}else if(type == "2"){
		if(indeedlatemargint == "undefined"){
			alert("实缴逾期保证金为空不能退款!");
			return;
		}else if(Number(indeedlatemargint) <= 0){
			alert("实缴逾期保证金已为0不能退款");
			return;
		}else if(Number(indeedlatemargint) < Number(jine)){
			alert("逾期保证金退款金额不能大于实缴逾期保证金金额!");
			return;
		}
	}
	
	jQuery.ajax({
		url: _basePath+ "/margin/Margin!marginDetailPromargin.action?SUP_ID="+sup_id_tk+"&type="+type,
 	    type:"post",
 	    dataType: "json",
 	    success: function (json){
				 if(json.data != null && json.data != ""){
					 if(type == "1"){
						 if(Number(indeedinitialmargint)+Number(json.data) < Number(jine)){
							 alert("入网保证金最大退款金额为："+ (Number(indeedinitialmargint)+Number(json.data)));
							 return;
						 }
					 }else if(type == "2"){
						 if(Number(indeedlatemargint)+Number(json.data) < Number(jine)){
							 alert("逾期保证金最大退款金额为："+ (Number(indeedlatemargint)+Number(json.data)));
							 return;
						 }
					 }
				 }
				 jQuery.ajax({
						url: _basePath+ "/margin/Margin!updateMarginTK.action?ID="+id+"&type="+type+"&jine="+jine
							+"&sup_id_tk="+sup_id_tk+"&TIME="+time_tk+"&PROJECT_NO="+project_no_tk
							+"&PAYMENT_NO="+payment_no_tk,
				 	    type:"post",
				 	    dataType: "json",
				 	    success: function (res,msg){
								 if(res){
									 alert("操作成功!");
									 $("#tuiKuan").dialog("close");
									 $("#pageTable").datagrid('reload');
								 }else{
									 alert("保存失败!");
								 }
				 	    },
				     	error: function (callback){
				 	    	alert("保存失败!");
				 	    }
					 });
 	    }
	 });
}

