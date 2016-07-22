//指标公司操作列
function addConfig(value,row,index){
	var html = "&nbsp;&nbsp;";
	console.debug(row);
    return html +"<a href=javascript:void(0) onclick=update('"+value+"')>修改</a>" +
    		"&nbsp;|&nbsp;<a href=javascript:void(0) onclick=del('"+value+"')>删除</a>";
}

//添加指标公司
function toAddCompany(){
	top.addTab("添加指标公司", _basePath+"/target/TargetCompany!toAddCompany.action");
}

function update(row){
	if (row){
		top.addTab("指标公司信息修改",_basePath+"/target/TargetCompany!toUpTargetMain.action?SUP_ID="+row);
    }
}

function del(row){
	if (row){
		 if(confirm("确定要删除该指标公司吗？")){
			 jQuery.ajax({
				url: _basePath+"/target/TargetCompany!delSuppliers.action",
				data: "SUP_ID="+row,
				dataType:"json",
				success: function(res){
					if(res.flag==true){
						$.messager.alert("提示",res.msg);
						se();
				   }
				   else{
					   $.messager.alert("提示",res.msg);
					   alert("操作失败请重试！");
				   }
				} 
			 });
		 }
	}
}

function show(row){
	if(row){
		top.addTab("下属经销商",_basePath+"/base/suppliers/Suppliers!toUnderSuppliers.action?SUP_ID="+row);
	}
}

function se(){
	var SUP_SHORTNAME=$("#SUP_SHORTNAME").val();
	var CREATE_TIME=$("#CREATE_TIME").datebox("getValue");
	var BUSINESS_SECTOR = $("#BUSINESS_SECTOR").val();
	$('#pageTable').datagrid('load',{"SUP_SHORTNAME":SUP_SHORTNAME,"CREATE_TIME":CREATE_TIME,"BUSINESS_SECTOR":BUSINESS_SECTOR});
}


function SCOREFUNCTION(value,row,index){
	var SUP_ID=row.SUP_ID;
	var SUP_SHORTNAME=row.SUP_SHORTNAME;
	return "<a href=javascript:void(0) onclick=scoreAddTab('"+SUP_ID+"','"+SUP_SHORTNAME+"')>"+value+"</a>";
}

function scoreAddTab(SUP_ID,SUP_SHORTNAME){
	top.addTab(SUP_SHORTNAME+"打分",_basePath+"/secuEvaluate/SecuEvaluate!doGradeScoreSupp.action?SUP_ID="+SUP_ID+"&TYPE=DLS&SUP_SHORTNAME="+SUP_SHORTNAME);
}

function turnSwitch (SUP_ID,STATUS,TYPE){
    var param = "";
    if(TYPE==1){
	   param +="SUP_ID="+SUP_ID+"&SUP_SWITCH="+STATUS;
	}else if(TYPE==2){
	   param +="SUP_ID="+SUP_ID+"&BALANCE_LOAN_SWITCH="+STATUS;
	}else if(TYPE==3){
	   param +="SUP_ID="+SUP_ID+"&IRREGULAR_REPAYMENT_SWITCH="+STATUS;
	}else if(TYPE==4){
	   param +="SUP_ID="+SUP_ID+"&B_MODEL_SWITCH="+STATUS;
	}else if(TYPE==5){
	   param +="SUP_ID="+SUP_ID+"&SMS_SWITCH="+STATUS;
	}else if(TYPE==6){
	   param +="SUP_ID="+SUP_ID+"&CREDIT_SWITCH="+STATUS;
	}else if(TYPE==7){
	   param +="SUP_ID="+SUP_ID+"&SCAN_SWITCH="+STATUS;
	}else if(TYPE==8){
	   param +="SUP_ID="+SUP_ID+"&DATAFILL_SWITCH="+STATUS;
	}else if(TYPE==9){
	   param +="SUP_ID="+SUP_ID+"&YINGYE_STATUS="+STATUS;
	}
    jQuery.ajax({
		url: "$_basePath/base/suppliers/Suppliers!supTurnSwitch.action",
		data: param,
		dataType:"json",
		success: function(res){
			if(res.flag==true){
				//jQuery.messager.alert("提示",res.msg);
				$('#pageTable').datagrid('reload');
		    }
		    else{
			   jQuery.messager.alert("提示",res.msg);
		    }
		}
	 });
}

