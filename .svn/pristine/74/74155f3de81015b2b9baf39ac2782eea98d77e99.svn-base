function rzsfbl(){
	$("#RZJEBL").val(accMul(accDiv($("#FINANCE_TOPRIC").val(),$("#LEASE_TOPRIC").val()),100));
	
	var sfzj=accAdd($("#START_MONEY").val(),$("#DEPOSIT_MONEY").val());
	
	if($("#GLF_PRICE").val()!='undefined'&&$("#GLF_PRICE").val()!=null&& $("#GLF_PRICE").val()!=''&&$("#GLF_PRICE").val()!=undefined){
		sfzj=accAdd(sfzj,$("#GLF_PRICE").val());
	}
	if($("#DB_BAIL__MONEY").val()!='undefined'&&$("#DB_BAIL__MONEY").val()!=null&& $("#DB_BAIL__MONEY").val()!=''&&$("#DB_BAIL__MONEY").val()!=undefined){
		sfzj=accAdd(sfzj,$("#DB_BAIL__MONEY").val());
	}
	$("#SFZJZB").val(accMul(accDiv(sfzj,$("#LEASE_TOPRIC").val()),100));
	$("#DKZB").val(Subtr(100,accMul(accDiv(sfzj,$("#LEASE_TOPRIC").val()),100)));
}

function toEstimates(PROJECT_ID, PROJECT_SCHEME_ID) {
    top.addTab("项目测算", _basePath + "/projectBudget/ProjectBudget!toShowEstimates.action?PROJECT_ID=" + PROJECT_ID + "&PROJECT_SCHEME_ID=" + PROJECT_SCHEME_ID);
}

function se() {
    $("#pageTable").datagrid('load', {
        "param": getFromData("#pageForm")
    });
}

function jsrze(){
	$("#FINANCE_TOPRIC").val(Subtr($("#LEASE_TOPRIC").val(),$("#START_MONEY").val()));
}

/**
 * 项目信息--根据比例计算金额
 * @param param
 * @author King 2014年8月22日
 */
function projectCalculateMoney(param){
	var id=$(param).attr("ID");
	id=id.substring(0,id.lastIndexOf("_PERCENT"));
	$("#"+id+"_MONEY").val(accDiv(accMul($("#LEASE_TOPRIC").val(),$(param).val()),100));
	if(id=="START"){
		$("#FINANCE_TOPRIC").val(Subtr($("#LEASE_TOPRIC").val(),accDiv(accMul($("#LEASE_TOPRIC").val(),$(param).val()),100)));
		rzsfbl();
	}
}

/**
 * 项目信息--根据金额计算比例
 * @param param
 * @author King 2014年8月22日
 */
function projectCalculateRate(param){
	var id=$(param).attr("ID");
	id=id.substring(0,id.lastIndexOf("_"));
	$("#"+id+"_PERCENT").val(accMul(accDiv($(param).val(),$("#LEASE_TOPRIC").val()),100));
	if(id=="START"){
		$("#FINANCE_TOPRIC").val(Subtr($("#LEASE_TOPRIC").val(),$(param).val()));
		rzsfbl();
	}
}

/**
 * 计算资金支付占比
 * 
 * @author King 2014年8月23日
 */
function xjzfzb(){
	$("#XJZFZB").val(Subtr(100,$("#KPZB").val()));
}

/**
 * 计算开票占比
 * 
 * @author King 2014年8月23日
 */
function KPZB(){
	$("#KPZB").val(Subtr(100,$("#XJZFZB").val()));
}

/**
 * 防洪护体税基
 * 
 * @author King 2014年8月23日
 */
function fhhdsj(){
	var fhhdsj=$("#RATE_PRICE").val();
	fhhdsj=accAdd($("#CZHGJ").val(),fhhdsj);
	if($("#GLF_PRICE").val()!='undefined'&&$("#GLF_PRICE").val()!=null&& $("#GLF_PRICE").val()!=''&&$("#GLF_PRICE").val()!=undefined){
		$("#FHHDFSJ").val(fhhdsj,$("#GLF_PRICE").val());
	}else{
		$("#FHHDFSJ").val(fhhdsj);
	}
	$("#YYSJFJSJ").val(anyDouble(accDiv($("#FHHDFSJ").val(),$("#LEASE_TERM").val()),2));
	yhsj();
}

/**
 * 印花税基
 * 
 * @author King 2014年8月23日
 */
function yhsj(){
	var yhsj=$("#MONTH_PRICE").val();
	if($("#GLF_PRICE").val()!='undefined'&&$("#GLF_PRICE").val()!=null&& $("#GLF_PRICE").val()!=''&&$("#GLF_PRICE").val()!=undefined){
		yhsj=accAdd(yhsj,$("#GLF_PRICE").val());
	}
	yhsj=accAdd(yhsj,$("#START_MONEY").val());
	yhsj=accAdd(yhsj,$("#CZHGJ").val());
	$("#YHSJ").val(yhsj);
}

/**
 * 测算收益率
 * 
 * @author King 2014年8月22日
 */
function calculate(){
	$.ajax({
		type:'post',
		url:_basePath+'/projectBudget/ProjectBudget!doCalculateIRR.action',
		data:'param='+getFromData("#paramDiv"),
		dataType:'json',
		success:function(json){
			if(json.flag){
				$("#calculateIRR").html(json.data);
			}else{
				$.messager.alert("提示",json.msg);
			}
		}
	});
}

