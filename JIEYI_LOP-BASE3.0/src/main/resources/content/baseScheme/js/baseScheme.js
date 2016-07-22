$(function(){
	$("#addYearRateDialog").dialog("close");
	$("#updateYearRateDialog").dialog("close");
	$("#addFeeRateDialog").dialog("close");
	$("#updateFeeRateDialog").dialog("close");
	
	
	
	$("#addCATENA_IDDiv").dialog("close");
	jQuery("#CATENA_ID").next().children(':first').click(function(){
		openCATENA_ID();
	});
	$("#addSpDiv").dialog("close");
	jQuery("#SP").next().children(':first').click(function(){
		openSP();
	});
	$("#addPpDiv").dialog("close");
	jQuery("#PRODUCT_ID").next().children(':first').click(function(){
		openPRODUCT_ID();
	});
	$("#addCXXDiv").dialog("close");
	jQuery("#PRODUCT_TYPE_ID").next().children(':first').click(function(){
		openPRODUCT_TYPE_ID();
	});
//	jQuery("#PRODUCT_TYPE_ID").click(function(){
//		openPRODUCT_TYPE_ID();
//	});
	$("#addJXJTDiv").dialog("close");
	jQuery("#SUPPLIER_GROUP").next().children(':first').click(function(){
		openSUPPLIER_GROUP();
	});
	$("#addjxsDiv").dialog("close");
	jQuery("#SUPPLIER_ID").next().children(':first').click(function(){
		openSUPPLIER_ID();
	});
//	jQuery("#SUPPLIER_ID").click(function(){
//		openSUPPLIER_ID();
//	});
});
function clean(){
	$("#SCHEME_NAME").val("");
	$("#COMPANY_ID").val("");
	$("#PRODUCT_ID").val("");
}
function submitForm() {
	$("#saveScheme").attr("disabled",true);
	$("#saveScheme").linkbutton("disable");
	var SCHEME_CODE=$("#SCHEME_CODE").val();
	var SCHEME_NAME=$("#SCHEME_NAME").val();
	var ALIASES=$("#ALIASES").combobox('getValue');
	if(SCHEME_NAME==""||SCHEME_NAME==" "){
		$.messager.alert("提示","请输入政策名称!");
		$("#saveScheme").attr("disabled",false);
		$("#saveScheme").linkbutton("enable");
		return false;
	}
	$.ajax({
		type:"post",
		url:_basePath+"/baseScheme/BaseScheme!doAddBaseScheme.action",
		data:"SCHEME_CODE="+SCHEME_CODE+"&SCHEME_NAME="+encodeURI(SCHEME_NAME)+"&ALIASES="+ALIASES+"&param="+getFromData1("#addSchemeForm"),
		dataType:"json",
		success:function(json){
			if(json.flag){
				$.messager.alert("消息","添加成功");
			}else{
				$.messager.alert("错误消息",json.msg);
				$("#saveScheme").attr("disabled",false);
				$("#saveScheme").linkbutton("enable");
			}
		}
	});
}

function getFromData1(str) {
	var saveRecord = new Array();
	$(str).children("tr").children(".text_right").children(".STATUS").each(
			function() {
				var temp={};
				temp.STATUS=$(this).find(":selected").val();
				var node=$(this).parent("td").next(".vtd").children();
				var item_flag=$(node).attr("ITEM_FLAG");
				if(isNaN(item_flag)){
					temp.ITEM_FLAG="";
				}else
					temp.ITEM_FLAG=item_flag;
				temp.VALUE_STATUS=$(this).parent("td").find("#VALUE_STATUS").val();
				var CALCULATE=$(this).parent("td").find("#CALCULATE").val();
				temp.CALCULATE=CALCULATE;
				var FYGS=$(this).parent("td").find("#FYGS").val();
				temp.FYGS=FYGS;
				var DSFS=$(this).parent("td").find("#DSFS").val();
				temp.DSFS=DSFS;
				var VALUE_DOWN=$(this).parent("td").find("#VALUE_DOWN").val();
				temp.VALUE_DOWN=VALUE_DOWN;
				var VALUE_UP=$(this).parent("td").find("#VALUE_UP").val();
				temp.VALUE_UP=VALUE_UP;
				
				if ($(node).is(":checkbox,:radio")) {
					if ($(node).attr("checked")) {
						temp.KEY_NAME_ZN=$(node).attr("SID");
						temp.KEY_NAME_EN=$(node).attr("name");
						temp.ROW_NUM=$(node).attr("ROW_NUM");
						temp.VALUE_STR=$(node).val();
					}
				} else {
						temp.KEY_NAME_ZN=$(node).attr("SID");
						var en=$(node).attr("name");
						if(en==null||en==''||en==undefined||en=='undefined'){
							en=$(node).attr("comboname");
						}
						temp.KEY_NAME_EN=en;
						temp.ROW_NUM=$(node).attr("ROW_NUM");
						var array=new Array();
						temp.VALUE_STR="";
						try{
							array=$("#"+en).combobox('getValues');
							for(var i=0;i<array.length;i++){
								temp.VALUE_STR+=array[i]+",";
							}
							if(temp.VALUE_STR.indexOf(",")==0){
								temp.VALUE_STR=temp.VALUE_STR.substring(1,temp.VALUE_STR.length);
							}
							if(temp.VALUE_STR.lastIndexOf(",")==temp.VALUE_STR.length-1){
								temp.VALUE_STR=temp.VALUE_STR.substring(0,temp.VALUE_STR.length-1);
							}
							
							if(temp.VALUE_STR.length<=2&&temp.VALUE_STR.indexOf(",")>=0)
								temp.VALUE_STR="";
							
							temp.VALUE_STR=temp.VALUE_STR.split(",").sort(function(a,b){return parseInt(a)<parseInt(b)?-1:1}).join(",");
						}catch(Exception ){
							if(en.indexOf("DATE")>=0){
								temp.VALUE_STR=$("#"+en).datebox('getValue');
								alert(temp.KEY_NAME_ZN+"="+temp.VALUE_STR);
							}else{
								temp.VALUE_STR=$("#"+en).val();
							}
						}
				}
				saveRecord.push(temp);
			});
	return JSON.stringify(saveRecord);
}

function scheme_name_check(){
	var SCHEME_NAME=$("#SCHEME_NAME").val();
	var ALIASES=$("#ALIASES").val();
	SCHEME_NAME=SCHEME_NAME+"-"+ALIASES;
	$.ajax({
		type:"post",
		data:"SCHEME_NAME="+encodeURI(SCHEME_NAME),
		url:_basePath+"/baseScheme/BaseScheme!doCheckBaseSchemeNameIsExist.action",
		dataType:"json",
		success:function(json){
			if(!json.flag){
				$.messager.alert("错误消息",json.msg);
				$("#SCHEME_NAME").val("");
			}
		}
	});
}
function se(){
	$('#pageTable').datagrid('load', {"param":getFromData("#pageForm")});
}
function showSchemeDetail(SCHEME_CODE){
	top.addTab("金融产品明细",_basePath+"/baseScheme/BaseScheme!toShowBaseScheme.action?SCHEME_CODE="+SCHEME_CODE);
}

function editType(SCHEME_CODE){
	top.addTab("修改金融产品",_basePath+"/baseScheme/BaseScheme!toUpdateBaseScheme.action?SCHEME_CODE="+SCHEME_CODE);
}

function updateForm(){
	var SCHEME_NAME=$("#SCHEME_NAME").val();
	var SCHEME_CODE=$("#SCHEME_CODE").val();
	var ALIASES=$("#ALIASES").combobox('getValue');
	$.ajax({
		type:"post",
		url:_basePath+"/baseScheme/BaseScheme!doUpdateBaseScheme.action",
		data:"SCHEME_NAME="+encodeURI(SCHEME_NAME)+"&ALIASES="+ALIASES+"&SCHEME_CODE="+SCHEME_CODE+"&param="+getFromData1("#addSchemeForm"),
		dataType:"json",
		success:function(json){
			if(json.flag){
				$.messager.alert("消息","修改成功");
			}else{
				$.messager.alert("错误提醒",json.msg);
			}
		}
	});
}

function destroyType(SCHEME_CODE){
		$.messager.confirm("删除","确定要删除该条数据吗？",function(r){
			if(r){
				$.ajax({
					type:"post",
					url:_basePath+"/baseScheme/BaseScheme!doDelBaseScheme.action",
					data:"SCHEME_CODE="+SCHEME_CODE,
					dataType:"json",
					success:function(json){
					if(json.flag){
						$('#pageTable').datagrid('load', {"param":getFromData("#pageForm")});
					}else
						$.messager.alert("错误提醒",json.msg);
				}
				});
			}
		});
}

function openYearRateDialog(){
	var top=$(document).scrollTop()+100;
	var left =($(window).height()-260) * 0.5;
	$("#addYearRateDialog").window("open").window('resize',{width:320,height:360,top: top,left:left});
	$("#addYearForm").form('clear');
}
function addYearRate(){
//	if(!$("#addYearForm").form('validate')){
//		return false;
//	}
	var START_PERCENT_S=$("#addYearRateDialog").find("[name=START_PERCENT_S]").val();
	var START_PERCENT_E=$("#addYearRateDialog").find("[name=START_PERCENT_E]").val();
	var LEASE_TERM_S=$("#addYearRateDialog").find("[name=LEASE_TERM_S]").val();
	var LEASE_TERM_E=$("#addYearRateDialog").find("[name=LEASE_TERM_E]").val();
	var GPS_PRICE=$("#addYearRateDialog").find("[name=GPS_PRICE]").val();
	var CALCULATE=$("#addYearRateDialog").find("#CALCULATE").combobox('getValue');
	var CALCULATE_NAME=$("#addYearRateDialog").find("#CALCULATE").combobox('getText');
	var YEAR_RATE=$("#addYearRateDialog").find("[name=YEAR_RATE]").val();
	var YEAR_RATE_UP=$("#addYearRateDialog").find("[name=YEAR_RATE_UP]").val();
	if(START_PERCENT_S==''||START_PERCENT_S==null){
		$.messager.alert("提示","请录入必填信息");
		return;
	}
	if(START_PERCENT_E==''||START_PERCENT_E==null){
		$.messager.alert("提示","请录入必填信息");
		return;
	}
	if(LEASE_TERM_S==''||LEASE_TERM_S==null){
		$.messager.alert("提示","请录入必填信息");
		return;
	}
	if(LEASE_TERM_E==''||LEASE_TERM_E==null){
		$.messager.alert("提示","请录入必填信息");
		return;
	}
	if(YEAR_RATE==''||YEAR_RATE==null){
		$.messager.alert("提示","请录入必填信息");
		return;
	}
	if(CALCULATE==''||CALCULATE==null){
		$.messager.alert("提示","请录入必填信息");
		return;
	}
	
	var SCHEME_CODE=$("#SCHEME_CODE").val();
		$.ajax({
			type:"post",
			url:_basePath+"/baseScheme/BaseScheme!doAddBaseSchemeYearRate.action",
			data:"SCHEME_CODE="+SCHEME_CODE+"&START_PERCENT_S="+START_PERCENT_S+"&START_PERCENT_E="
				 +START_PERCENT_E+"&LEASE_TERM_S="+LEASE_TERM_S+"&LEASE_TERM_E="+LEASE_TERM_E+"&GPS_PRICE="
				 +GPS_PRICE+"&YEAR_RATE="+YEAR_RATE+"&YEAR_RATE_UP="+YEAR_RATE_UP+"&CALCULATE="+CALCULATE,
			dataType:"json",
			success:function(json){
				if(!json.flag){
					$.messager.alert("错误消息","添加失败,请联系管理员！错误原因："+json.msg);
				}else{
					$("#addYearRateDialog").dialog("close");
					$("#addYearRateBody:last-child").append("<tr><td><input type='radio' name='yearRateId' class='yearRateId' value='"+json.data+"'/></td><td>"
							+START_PERCENT_S+"</td><td>"
							+START_PERCENT_E+"</td><td>"
							+LEASE_TERM_S+"</td><td>"
							+LEASE_TERM_E+"</td><td>"
							+GPS_PRICE+"</td><td><input type='hidden' name='CALCULATE_NAME' id='CALCULATE_NAME' value='"+CALCULATE_NAME+"'/>"
							+CALCULATE_NAME+"</td><td>"
							+YEAR_RATE+"</td><td>"
							+YEAR_RATE_UP+"</td></tr>");
				}
			}
		});
}

function closeAddYearRate(){
	$("#addYearRateDialog").dialog("close");
}

function delYearRate(){
	var row;
	var flag=false;
	var SCHEME_CODE=$("#SCHEME_CODE").val();
	var parent;
	$(".yearRateId").each(function(){
		if($(this).attr("checked")=='checked'||$(this).attr("checked")==true){
			row="SCHEME_CODE="+SCHEME_CODE;
			row+="&ID="+$.trim($(this).val());
			parent=$(this).parent("td").parent("tr");
			row+="&START_PERCENT_S="+$.trim($(parent).children("td:eq(1)").text());
			row+="&START_PERCENT_E="+$.trim($(parent).children("td:eq(2)").text());
			row+="&LEASE_TERM_S="+$.trim($(parent).children("td:eq(3)").text());
			row+="&LEASE_TERM_E="+$.trim($(parent).children("td:eq(4)").text());
			row+="&GPS_PRICE="+$.trim($(parent).children("td:eq(5)").text());
			row+="&YEAR_RATE="+$.trim($(parent).children("td:eq(7)").text());
			row+="&YEAR_RATE_UP="+$.trim($(parent).children("td:eq(8)").text());
			flag=true;
		}
	});
	if(flag){
		$.messager.confirm("提示","您确定要删除该条数据吗?",function(flag){
			if(flag){
				$.ajax({
					type:"post",
					url:_basePath+"/baseScheme/BaseScheme!doDelBaseSchemeYearRate.action",
					data:row,
					dataType:"json",
					success:function(json){
					if(!json.flag){
						$.messager.alert("错误","失败，原因"+json.msg);
					}else{
						$(parent).remove();
					}
				}
				});
			}
		});
	}else{
		$.messager.alert("提示","请选择需要删除的数据行");
	}
}

function openUpdateYearRate(){
	var flag=false;
	var parent;
	$(".yearRateId").each(function(){
		if($(this).attr("checked")=='checked'||$(this).attr("checked")==true){
			flag=true;
			var top=$(document).scrollTop()+100;
			var left =($(window).height()-260) * 0.5;
			$("#updateYearRateDialog").window("open").window('resize',{width:320,height:360,top: top,left:left});
			parent=$(this).parent("td").parent("tr");
			$("#updateYearForm").find("#START_PERCENT_S").numberbox('setValue',$(parent).children("td:eq(1)").text());
			$("#updateYearForm").find("#START_PERCENT_E").numberbox('setValue',$(parent).children("td:eq(2)").text());
			$("#updateYearForm").find("#LEASE_TERM_S").numberbox('setValue',$(parent).children("td:eq(3)").text());
			$("#updateYearForm").find("#LEASE_TERM_E").numberbox('setValue',$(parent).children("td:eq(4)").text());
			$("#updateYearForm").find("#GPS_PRICE").numberbox('setValue',$(parent).children("td:eq(5)").text());
			$("#updateYearForm").find("#CALCULATE").combobox('setValue',$(parent).children("td:eq(6)").text());
			$("#updateYearForm").find("#YEAR_RATE").numberbox('setValue',$(parent).children("td:eq(7)").text());
			$("#updateYearForm").find("#YEAR_RATE_UP").numberbox('setValue',$(parent).children("td:eq(8)").text());
			$("#updateYearForm").find("input[name=ID]").val($(this).val());
		}else{
		}
	});	
	if(!flag){
		$.messager.alert("提示","请选择需要修改的数据行");
	}
}

function closeUpdateYearRate(){
	$("#updateYearRateDialog").dialog("close");
}

function updateYearRate(){
	var START_PERCENT_S=$("#updateYearRateDialog").find("[name=START_PERCENT_S]").val();
	var START_PERCENT_E=$("#updateYearRateDialog").find("[name=START_PERCENT_E]").val();
	var LEASE_TERM_S=$("#updateYearRateDialog").find("[name=LEASE_TERM_S]").val();
	var LEASE_TERM_E=$("#updateYearRateDialog").find("[name=LEASE_TERM_E]").val();
	var YEAR_RATE=$("#updateYearRateDialog").find("[name=YEAR_RATE]").val();
	var YEAR_RATE_UP=$("#updateYearRateDialog").find("[name=YEAR_RATE_UP]").val();
	var GPS_PRICE=$("#updateYearRateDialog").find("[name=GPS_PRICE]").val();
	var CALCULATE=jQuery.trim($("#updateYearRateDialog").find("#CALCULATE").combobox('getValue'));
	var CALCULATE_NAME=jQuery.trim($("#updateYearRateDialog").find("#CALCULATE").combobox('getText'));
	var ID=$("#updateYearRateDialog").find("[name=ID]").val();
	var SCHEME_CODE=$("#SCHEME_CODE").val();
	
	if(START_PERCENT_S==''||START_PERCENT_S==null){
		$.messager.alert("提示","请录入必填信息");
		return;
	}
	if(START_PERCENT_E==''||START_PERCENT_E==null){
		$.messager.alert("提示","请录入必填信息");
		return;
	}
	if(LEASE_TERM_S==''||LEASE_TERM_S==null){
		$.messager.alert("提示","请录入必填信息");
		return;
	}
	if(LEASE_TERM_E==''||LEASE_TERM_E==null){
		$.messager.alert("提示","请录入必填信息");
		return;
	}
	if(YEAR_RATE==''||YEAR_RATE==null){
		$.messager.alert("提示","请录入必填信息");
		return;
	}
	if(CALCULATE==''||CALCULATE==null){
		$.messager.alert("提示","请录入必填信息");
		return;
	}
	$.ajax({
		type:"post",
		url:_basePath+"/baseScheme/BaseScheme!doUpdateBaseSchemeYearRate.action",
		data:"ID="+ID+"&SCHEME_CODE="+SCHEME_CODE+"&START_PERCENT_S="+START_PERCENT_S
			  +"&START_PERCENT_E="+START_PERCENT_E+"&LEASE_TERM_S="+LEASE_TERM_S
			  +"&LEASE_TERM_E="+LEASE_TERM_E+"&YEAR_RATE="+YEAR_RATE+"&GPS_PRICE="+GPS_PRICE
			  +"&YEAR_RATE_UP="+YEAR_RATE_UP+"&CALCULATE="+CALCULATE,
		dataType:"json",
		success:function(json){
			if(json.flag){
				$("#updateYearRateDialog").dialog("close");
				$(".yearRateId").each(function(){
					if($(this).attr("checked")=='checked'||$(this).attr("checked")==true){
						parent=$(this).parent("td").parent("tr");
						$(parent).children("td:eq(1)").replaceWith("<td>"+START_PERCENT_S+"</td>");
						$(parent).children("td:eq(2)").replaceWith("<td>"+START_PERCENT_E+"</td>");
						$(parent).children("td:eq(3)").replaceWith("<td>"+LEASE_TERM_S+"</td>");
						$(parent).children("td:eq(4)").replaceWith("<td>"+LEASE_TERM_E+"</td>");
						$(parent).children("td:eq(5)").replaceWith("<td>"+GPS_PRICE+"</td>");
						$(parent).children("td:eq(6)").replaceWith("<td><input type='hidden' name='CALCULATE_NAME' id='CALCULATE_NAME' value='"+CALCULATE_NAME+"'/>"+CALCULATE_NAME+"</td>");
						$(parent).children("td:eq(7)").replaceWith("<td>"+YEAR_RATE+"</td>");
						$(parent).children("td:eq(8)").replaceWith("<td>"+YEAR_RATE_UP+"</td>");
					}
				});
			}else{
				$.messager.alert("错误","修改失败，原因"+json.msg);
			}
		}
	});
}

function openFeeRateDialog(){
	var top=$(document).scrollTop()+100;
	var left =($(window).height()-260) * 0.5;
	$("#addFeeRateDialog").window("open").window('resize',{width:300,height:360,top: top,left:left});
	$("#addFeeRateForm").form('clear');
}
function addFeeRate(){
	var LEASE_TERM_S=$("#addFeeRateDialog").find("[name=LEASE_TERM_S]").val();
	var LEASE_TERM_E=$("#addFeeRateDialog").find("[name=LEASE_TERM_E]").val();
	var FEE_RATE=$("#addFeeRateDialog").find("[name=FEE_RATE]").val();
	var FEE_MONEY=$("#addFeeRateDialog").find("[name=FEE_MONEY]").val();
	
	var SQFS=$("#addFeeRateDialog").find("#SQFS").find("option:selected").val();
//	var SQFS=$("#addFeeRateDialog").find("#SQFS").combobox('getValue');
	var SQFSNAME=$("#addFeeRateDialog").find("#SQFS").find("option:selected").text();
//	var SQFSNAME=$("#addFeeRateDialog").find("#SQFS").combobox('getText');
	
	var SXFDSFS=$("#addFeeRateDialog").find("#SXFDSFS").find("option:selected").val();
//	var SXFDSFS=$("#addFeeRateDialog").find("#SXFDSFS").combobox('getValue');
	var SXFDSFSNAME=$("#addFeeRateDialog").find("#SXFDSFS").find("option:selected").text();
//	var SXFDSFSNAME=$("#addFeeRateDialog").find("#SXFDSFS").combobox('getText');
	var SCHEME_CODE=$("#SCHEME_CODE").val();
	
	if(LEASE_TERM_S==''||LEASE_TERM_S==null){
		$.messager.alert("提示","请录入必填信息");
		return;
	}
	if(LEASE_TERM_E==''||LEASE_TERM_E==null){
		$.messager.alert("提示","请录入必填信息");
		return;
	}
	
		$.ajax({
			type:"post",
			url:_basePath+"/baseScheme/BaseScheme!doAddBaseSchemeFeeRate.action",
			data:"SCHEME_CODE="+SCHEME_CODE+"&LEASE_TERM_S="+LEASE_TERM_S+"&LEASE_TERM_E="
				 +LEASE_TERM_E+"&FEE_RATE="+FEE_RATE+"&SQFS="+SQFS+"&FEE_MONEY="+FEE_MONEY+"&SXFDSFS="+SXFDSFS,
			dataType:"json",
			success:function(json){
				if(!json.flag){
					$.messager.alert("错误消息","添加失败,请联系管理员！错误原因："+json.msg);
				}else{
					$("#addFeeRateDialog").dialog("close");
					$("#addFeeRateBody:last-child").append("<tr><td><input type='radio' name='feeRateId' class='feeRateId' value='"+json.data+"'/> </td><td>"
							+LEASE_TERM_S+" </td><td>"
							+LEASE_TERM_E+" </td><td>"+FEE_RATE+" </td><td>"
							+FEE_MONEY+" </td><td><input type='hidden' name='SQFSNAME' id='SQFSNAME' value='"+SQFS+"'/>"+SQFSNAME+" </td>" +
									"<td><input type='hidden' name='SXFDSFSNAME' id='SXFDSFSNAME' value='"+SXFDSFS+"'/>"+SXFDSFSNAME+" </td></tr>");
				}
			}
		});
}

function closeAddFeeRate(){
	$("#addFeeRateDialog").dialog("close");
}

function delFeeRate(){
	var row;
	var f=false;
	var SCHEME_CODE=$("#SCHEME_CODE").val();
	var parent;
	$(".feeRateId").each(function(){
		if($(this).attr("checked")=='checked'||$(this).attr("checked")==true){
			row="SCHEME_CODE="+SCHEME_CODE;
			if($(this).val()!=null&&$(this).val()!=''){
				row+="&ID="+$.trim($(this).val());
			}
			parent=$(this).parent("td").parent("tr");
			row+="&LEASE_TERM_S="+$.trim($(parent).children("td:eq(1)").text());
			row+="&LEASE_TERM_E="+$.trim($(parent).children("td:eq(2)").text());
			row+="&FEE_RATE="+$.trim($(parent).children("td:eq(3)").text());
			f=true;
		}
	});
	
	if(f){
		$.messager.confirm("提示","您确认要删除该条数据吗？",function(flag){
			if(flag){
				$.ajax({
					type:"post",
					url:_basePath+"/baseScheme/BaseScheme!doDelBaseSchemeFeeRate.action",
					data:row,
					dataType:"json",
					success:function(json){
					if(!json.flag){
						$.messager.alert("错误","失败，原因"+json.msg);
					}else{
						$(parent).remove();
					}
				}
				});
			}
		});
	}else{
		$.messager.alert("提示","请选择需要删除的数据行");
	}
}

function openUpdateFeeRate(){
//	$("#updateFeeRateForm").form('clear');
	var flag=false;
	var parent;
	$(".feeRateId").each(function(){
		if($(this).attr("checked")=='checked'||$(this).attr("checked")==true){
			var top=$(document).scrollTop()+100;
			var left =($(window).height()-260) * 0.5;
			$("#updateFeeRateDialog").window("open").window('resize',{width:300,height:360,top: top,left:left});
			
			parent=$(this).parent("td").parent("tr");
			$("#updateFeeRateForm").find("#FEE_LEASE_TERM_S").numberbox('setValue',$(parent).children("td:eq(1)").text());
			$("#updateFeeRateForm").find("#FEE_LEASE_TERM_E").numberbox('setValue',$(parent).children("td:eq(2)").text());
			$("#updateFeeRateForm").find("#FEE_RATE").val($(parent).children("td:eq(3)").text());
			$("#updateFeeRateForm").find("#FEE_MONEY").val($(parent).children("td:eq(4)").text());
//			$("#updateFeeRateForm").find("#SQFS").combobox('setValue',$(parent).children("td:eq(5)").children("#SQFSNAME").val());
			var SQFS=$(parent).children("td:eq(5)").children("#SQFSNAME").val();
			$("#updateFeeRateForm").find("#SQFS").find("option[value='"+SQFS+"']").attr("selected",true);
			$("#updateFeeRateForm").find("input[name='FEE_ID']").val($(this).val());
			var SXFDSFS=$(parent).children("td:eq(6)").children("#SXFDSFSNAME").val();
//			$("#updateFeeRateForm").find("#SXFDSFS").combobox('setValue',$(parent).children("td:eq(6)").children("#SQFSNAME").val());
			$("#updateFeeRateForm").find("#SXFDSFS").find("option[value="+SXFDSFS+"]").attr("selected",true);
			flag=true;
		}
	});
	if(!flag){
		$.messager.alert("提示","请选择需要修改的数据行");
	}
}

function SQFSChange(obj){
	var aa=$(obj).val();
	if(aa != '1'){
		var parent=$(obj).parent("td").parent("tr").parent();
		$(parent).find("#SXFDSFS").find("option[value='0']").attr("selected",true);
	}
}

function DSFSChange(obj){
	var parent=$(obj).parent("td").parent("tr").parent();
	var SQFS=$(parent).find("#SQFS").val();
	if(SQFS != '1'){
		$(parent).find("#SXFDSFS").find("option[value='0']").attr("selected",true);
	}
}

function closeUpdateFeeRate(){
	$("#updateFeeRateDialog").dialog("close");
}

function updateFeeRate(){
	var LEASE_TERM_S=$("#updateFeeRateDialog").find("[name=FEE_LEASE_TERM_S]").val();
	var LEASE_TERM_E=$("#updateFeeRateDialog").find("[name=FEE_LEASE_TERM_E]").val();
	var FEE_RATE=$("#updateFeeRateDialog").find("[name=FEE_RATE]").val();
	var FEE_MONEY=$("#updateFeeRateDialog").find("[name=FEE_MONEY]").val();
	
	var SQFS=$("#updateFeeRateDialog").find("#SQFS").find("option:selected").val();
//	var SQFS=$("#updateFeeRateDialog").find("#SQFS").combobox('getValue');
	var SQFSNAME=$("#updateFeeRateDialog").find("#SQFS").find("option:selected").text();
//	var SQFSNAME=$("#updateFeeRateDialog").find("#SQFS").combobox('getText');
	
	var SXFDSFS=$("#updateFeeRateDialog").find("#SXFDSFS").find("option:selected").val();
//	var SXFDSFS=$("#updateFeeRateDialog").find("#SXFDSFS").combobox('getValue');
	var SXFDSFSNAME=$("#updateFeeRateDialog").find("#SXFDSFS").find("option:selected").text();
//	var SXFDSFSNAME=$("#updateFeeRateDialog").find("#SXFDSFS").combobox('getText');
	var ID=$("#updateFeeRateDialog").find("[name=FEE_ID]").val();
	var SCHEME_CODE=$("#SCHEME_CODE").val();
	
	if(LEASE_TERM_S==''||LEASE_TERM_S==null){
		$.messager.alert("提示","请录入必填信息");
		return;
	}
	if(LEASE_TERM_E==''||LEASE_TERM_E==null){
		$.messager.alert("提示","请录入必填信息");
		return;
	}
	
	$.ajax({
		type:"post",
		url:_basePath+"/baseScheme/BaseScheme!doUpdateBaseSchemeFeeRate.action",
		data:"ID="+ID+"&SCHEME_CODE="+SCHEME_CODE+"&LEASE_TERM_S="+
			LEASE_TERM_S+"&LEASE_TERM_E="+LEASE_TERM_E+"&FEE_RATE="+FEE_RATE+"&SQFS="+SQFS
			+"&FEE_MONEY="+FEE_MONEY+"&SXFDSFS="+SXFDSFS,
		dataType:"json",
		success:function(json){
			if(json.flag){
				$("#updateFeeRateDialog").dialog("close");
				$(".feeRateId").each(function(){
					if($(this).attr("checked")=='checked'||$(this).attr("checked")==true){
						parent=$(this).parent("td").parent("tr");
						$(parent).children("td:eq(1)").replaceWith("<td>"+LEASE_TERM_S+"</td>");
						$(parent).children("td:eq(2)").replaceWith("<td>"+LEASE_TERM_E+"</td>");
						$(parent).children("td:eq(3)").replaceWith("<td>"+FEE_RATE+"</td>");
						$(parent).children("td:eq(4)").replaceWith("<td>"+FEE_MONEY+"</td>");
						$(parent).children("td:eq(5)").replaceWith("<td><input type='hidden' name='SQFSNAME' id='SQFSNAME' value='"+SQFS+"'/>"+SQFSNAME+"</td>");
						$(parent).children("td:eq(6)").replaceWith("<td><input type='hidden' name='SXFDSFSNAME' id='SXFDSFSNAME' value='"+SXFDSFS+"'/>"+SXFDSFSNAME+"</td>");
					}
				});
			}else{
				$.messager.alert("错误","修改失败，原因"+json.msg);
			}
		}
	});
}

function updateNewSchemeElement(){
	$.ajax({
		type:"post",
		url:_basePath+"/baseScheme/BaseScheme!updateNewSchemeElement.action",
		dataType:"json",
		success:function(json){
			if(json.flag){
				$.messager.alert("结果",json.msg);
			}else{
				$.messager.alert("结果",json.msg);
			}
		}
	});
}


function schemeMeasure(ID,SCHEME_CODE)
{
	addParentTabs("测算",_basePath+"/baseScheme/BaseScheme!schemeMeasure.action?SCHEME_CODE="+SCHEME_CODE);
}


function copyScheme(scheme_code,scheme_name,ALIASES){
	$.messager.prompt("产品复制","确定复制【"+scheme_name+"】产品吗？</br>如果确认请录入新产品名称",function(r){
		if(r){
			$.ajax({
				type:"post",
				url:_basePath+"/baseScheme/BaseScheme!copyScheme.action",
				data:"OLD_SCHEME_CODE="+scheme_code+"&OLD_SCHEME_NAME="+scheme_name+"&OLD_ALIASES="+ALIASES+"&NEW_SCHEME_NAME="+r,
				dataType:"json",
				success:function(json){
					if(json.flag){
						$.messager.alert("结果",json.msg);
						$("#pageTable").datagrid('reload');
					}else{
						$.messager.alert("结果",json.msg);
					}
				}
			});
		}
	});
}

function openCATENA_ID(){
	var top = $(document.body).offset().top + 30;
	var left = $(document.body).offset().left+200;
	$("#addCATENA_IDDiv").dialog("open").window('resize',{width:'250px',height:'500px',top: top,left:left});
}
function openPRODUCT_ID(){
	var top = $(document.body).offset().top + 30;
	var left = $(document.body).offset().left+200;
	$("#addPpDiv").dialog("open").window('resize',{width:'250px',height:'500px',top: top,left:left});
}
function openSP(){
	var top = $(document.body).offset().top + 30;
	var left = $(document.body).offset().left+200;
	$("#addSpDiv").dialog("open").window('resize',{width:'250px',height:'500px',top: top,left:left});
}
function openPRODUCT_TYPE_ID(){
	var top = $(document.body).offset().top + 30;
	var left = $(document.body).offset().left+200;
	$("#addCXXDiv").dialog("open").window('resize',{width:'250px',height:'500px',top: top,left:left});
}
function openSUPPLIER_GROUP(){
	var top = $(document.body).offset().top + 30;
	var left = $(document.body).offset().left+200;
	$("#addJXJTDiv").dialog("open").window('resize',{width:'250px',height:'500px',top: top,left:left});
}
function openSUPPLIER_ID(){
	var top = $(document.body).offset().top + 30;
	var left = $(document.body).offset().left+200;
	$("#addjxsDiv").dialog("open").window('resize',{width:'250px',height:'500px',top: top,left:left});
}

function queryCATENA_IDD(){
	var BUSINESS_PLATFORM=$("#BUSINESS_PLATFORM").combobox('getValue');
	if(BUSINESS_PLATFORM==null||BUSINESS_PLATFORM==''){
		alert("请先选择业务平台");
		return;
	}
	var CX_NAME=$("#CX_NAME").val();
	var array=new Array();
	var PRODUCT_ID=',';
	var array=$("#PRODUCT_ID").combobox('getValues');
	for(var i=0;i<array.length;i++){
				PRODUCT_ID+=array[i]+",";
	}
	if(PRODUCT_ID==null||PRODUCT_ID==''||PRODUCT_ID==','){
		alert("请先选择品牌");
		return ;
	}
	var getarr=new Array();
	getarr=$("#CATENA_ID").combobox('getValues');
	$.ajax({
		url:_basePath+"/baseScheme/BaseScheme!getProCATENA1.action?PRODUCT_ID="+PRODUCT_ID+"&CATENA_NAME="+CX_NAME,
		type:'post',
		dataType:'json',
		success:function(json){
			var data1=json.data;
			var rst="";
			for(var i=0;i<data1.length;i++){
				var flag=false;
				for(var j=0;j<getarr.length;j++){
					if(data1[i].CODE==getarr[j]){
						flag=true;
					}
				}
				if(flag==true)
					rst+="<tr><td><input type='checkbox' name='CX_ID' checked onchange=chooseEle('CX_ID','CATENA_ID') class='CX_ID' id='CX_ID' value='"+json.data[i].CODE+"'/>"+json.data[i].FLAG+"</td></tr>";
				else
					rst+="<tr><td><input type='checkbox' name='CX_ID' onchange=chooseEle('CX_ID','CATENA_ID') class='CX_ID' id='CX_ID' value='"+json.data[i].CODE+"'/>"+json.data[i].FLAG+"</td></tr>";
			}
			$("#cxbody").html(rst);
		}
	});
	
}

function querypp(){
	var BUSINESS_PLATFORM=$("#BUSINESS_PLATFORM").combobox('getValue');
	if(BUSINESS_PLATFORM==null||BUSINESS_PLATFORM==''){
		alert("请先选择业务平台");
		return;
	}
	
	var getarr=new Array();
	getarr=$("#PRODUCT_ID").combobox('getValues');
	
	var PP_NAME=$("#PP_NAME").val();
	$.ajax({
		url:_basePath+"/baseScheme/BaseScheme!getProBig1.action?COMPANY_ID=&PRODUCT_NAME="+PP_NAME, 
		type:'post',
		dataType:'json',
		success:function(json){
			var data1=json.data;
			var rst="";
			for(var i=0;i<data1.length;i++){
				var flag=false;
				for(var j=0;j<getarr.length;j++){
					if(data1[i].CODE==getarr[j]){
						flag=true;
					}
				}
				if(flag==true)
				rst+="<tr><td><input type='checkbox' name='PP_ID' checked id='PP_ID' class='PP_ID' onchange=chooseEle('PP_ID','PRODUCT_ID') value='"+json.data[i].CODE+"'/>"+json.data[i].FLAG+"</td></tr>";
				else
				rst+="<tr><td><input type='checkbox' name='PP_ID' id='PP_ID' class='PP_ID' onchange=chooseEle('PP_ID','PRODUCT_ID') value='"+json.data[i].CODE+"'/>"+json.data[i].FLAG+"</td></tr>";
			}
			$("#ppbody").html(rst);
		}
	});
}

function querysp(){
	var BUSINESS_PLATFORM=$("#BUSINESS_PLATFORM").combobox('getValue');
	if(BUSINESS_PLATFORM==null||BUSINESS_PLATFORM==''){
		alert("请先选择业务平台");
		return;
	}
	
	var getarr=new Array();
	getarr=$("#SP").combobox('getValues');
	
	var SP_NAME=$("#SP_NAME").val();
	$.ajax({
		type:'post',
		url:_basePath+"/baseScheme/BaseScheme!querysp.action?SUP_TYPE=2&SUP_NAME="+SP_NAME,
		dataType:'json',
		success:function(json){
			var data1=json.data;
			var rst="";
			for(var i=0;i<data1.length;i++){
				var flag=false;
				for(var j=0;j<getarr.length;j++){
					if(data1[i].CODE==getarr[j]){
						flag=true;
					}
				}
				if(flag==true)
				rst+="<tr><td><input type='checkbox' checked name='SP_ID' class='SP_ID'  onchange=chooseEle('SP_ID','SP') id='SP_ID' value='"+json.data[i].CODE+"'/>"+json.data[i].FLAG+"</td></tr>";
				else
				rst+="<tr><td><input type='checkbox' name='SP_ID' class='SP_ID'  onchange=chooseEle('SP_ID','SP') id='SP_ID' value='"+json.data[i].CODE+"'/>"+json.data[i].FLAG+"</td></tr>";
			}
			$("#spbody").html(rst);
		}
	});
}

function queryJXJT(){
	var BUSINESS_PLATFORM=$("#BUSINESS_PLATFORM").combobox('getValue');
	if(BUSINESS_PLATFORM==null||BUSINESS_PLATFORM==''){
		alert("请先选择业务平台");
		return;
	}
	var getarr=new Array();
	getarr=$("#SUPPLIER_GROUP").combobox('getValues');
	var JXJT_NAME=$("#JXJT_NAME").val();
	$.ajax({
		type:'post',
		url:_basePath+"/baseScheme/BaseScheme!querysp.action?SUP_TYPE=4&SUP_NAME="+JXJT_NAME,
		dataType:'json',
		success:function(json){
			var data1=json.data;
			var rst="";
			for(var i=0;i<data1.length;i++){
				var flag=false;
				for(var j=0;j<getarr.length;j++){
					if(data1[i].CODE==getarr[j]){
						flag=true;
					}
				}
				if(flag==true)
				rst+="<tr><td><input type='checkbox' checked name='JXJT_ID' class='JXJT_ID'  onchange=chooseEle('JXJT_ID','SUPPLIER_GROUP') id='JXJT_ID' value='"+json.data[i].CODE+"'/>"+json.data[i].FLAG+"</td></tr>";
				else
				rst+="<tr><td><input type='checkbox' name='JXJT_ID' class='JXJT_ID'  onchange=chooseEle('JXJT_ID','SUPPLIER_GROUP') id='JXJT_ID' value='"+json.data[i].CODE+"'/>"+json.data[i].FLAG+"</td></tr>";
			}
			$("#jxjtbody").html(rst);
		}
	});
}
function queryjxs(){
	var BUSINESS_PLATFORM=$("#BUSINESS_PLATFORM").combobox('getValue');
	if(BUSINESS_PLATFORM==null||BUSINESS_PLATFORM==''){
		alert("请先选择业务平台");
		return;
	}
	var JXS_NAME=$("#JXS_NAME").val();
	var getarr=new Array();
	getarr=$("#SUPPLIER_ID").combobox('getValues');
	$.ajax({
		type:'post',
		url:_basePath+"/baseScheme/BaseScheme!querysp.action?SUP_TYPE=1&SUP_NAME="+JXS_NAME,
		dataType:'json',
		success:function(json){
			var data1=json.data;
			var rst="";
			for(var i=0;i<data1.length;i++){
				var flag=false;
				for(var j=0;j<getarr.length;j++){
					if(data1[i].CODE==getarr[j]){
						flag=true;
					}
				}
				if(flag==true)
				rst+="<tr><td><input type='checkbox'  checked name='JXS_ID' class='JXS_ID'  onchange=chooseEle('JXS_ID','SUPPLIER_ID') id='JXS_ID' value='"+json.data[i].CODE+"'/>"+json.data[i].FLAG+"</td></tr>";
				else
				rst+="<tr><td><input type='checkbox' name='JXS_ID' class='JXS_ID'  onchange=chooseEle('JXS_ID','SUPPLIER_ID') id='JXS_ID' value='"+json.data[i].CODE+"'/>"+json.data[i].FLAG+"</td></tr>";
			}
			$("#jxsbody").html(rst);
		}
	});
}
function queryCXX(){
	var BUSINESS_PLATFORM=$("#BUSINESS_PLATFORM").combobox('getValue');
	if(BUSINESS_PLATFORM==null||BUSINESS_PLATFORM==''){
		alert("请先选择业务平台");
		return;
	}
	var array=new Array();
	var CATENA_ID=',';
	var array=$("#CATENA_ID").combobox('getValues');
	for(var i=0;i<array.length;i++){
				CATENA_ID+=array[i]+",";
	}
	if(CATENA_ID==null||CATENA_ID==''||CATENA_ID==','){
		alert("请先选择车系");
		return ;
	}
	var getarr=new Array();
	getarr=$("#PRODUCT_TYPE_ID").combobox('getValues');
	var CXX_NAME=$("#CXX_NAME").val();
	$.ajax({
		type:'post',
		url:_basePath+"/baseScheme/BaseScheme!getAllProductType1.action?CATENA_ID="+CATENA_ID+"&CXX_NAME="+CXX_NAME, 
		dataType:'json',
		success:function(json){
			var data1=json.data;
			var rst="";
			for(var i=0;i<data1.length;i++){
				var flag=false;
				for(var j=0;j<getarr.length;j++){
					if(data1[i].CODE==getarr[j]){
						flag=true;
					}
				}
				if(flag==true)
				rst+="<tr><td><input type='checkbox' checked name='CXX_ID' class='CXX_ID'  onchange=chooseEle('CXX_ID','PRODUCT_TYPE_ID') id='CXX_ID' value='"+json.data[i].CODE+"'/>"+json.data[i].FLAG+"</td></tr>";
				else
				rst+="<tr><td><input type='checkbox' name='CXX_ID' class='CXX_ID'  onchange=chooseEle('CXX_ID','PRODUCT_TYPE_ID') id='CXX_ID' value='"+json.data[i].CODE+"'/>"+json.data[i].FLAG+"</td></tr>";
			}
			$("#cxxbody").html(rst);
		}
	});
}

function chooseEle(param,rst){
	var getarr=new Array();
	getarr=$("#"+rst).combobox('getValues');
	var array1=new Array();
	var a=0;
	$("."+param).each(function(){
		if($(this).attr("checked")){
			array1[a]=$(this).val();
			a++;
		}
	});
//	for(var j=0;j<getarr.length;j++){
//		var flag=true;
//		for(var m=0;m<array1.length;m++){
//			if(array1[m]==getarr[j]){
//				flag=false;
//			}
//		}
//		if(flag){
//			array1[a]=getarr[j];
//			a++;
//		}
//	}
	$("#"+rst).combobox('setValues',array1);
	
	if(rst=='PRODUCT_ID'){
		var array=new Array();
		var PRODUCT_ID=',';
		var array=$("#PRODUCT_ID").combobox('getValues');
		for(var i=0;i<array.length;i++){
					PRODUCT_ID+=array[i]+",";
		}
		//车系
		$('#CATENA_ID').combobox({ 
	    url:_basePath+"/baseScheme/BaseScheme!getProCATENA.action?PRODUCT_ID="+PRODUCT_ID, 
	    cache: false,
	    multiple:true,
	    panelHeight: 'auto',//自动高度适合
	    valueField:'CODE',   
	    textField:'FLAG'});
	}else if(rst=='CATENA_ID'){
		var array=new Array();
		var CATENA_ID=',';
		var array=$("#CATENA_ID").combobox('getValues');
		for(var i=0;i<array.length;i++){
					CATENA_ID+=array[i]+",";
		}
		//车型
		$('#PRODUCT_TYPE_ID').combobox({ 
	    url:_basePath+"/baseScheme/BaseScheme!getAllProductType.action?CATENA_ID="+CATENA_ID+"&COMPANY_ID=", 
	    cache: false,
	    multiple:true,
	    panelHeight: 'auto',//自动高度适合
	    valueField:'CODE',   
	    textField:'FLAG'});
	}
}
