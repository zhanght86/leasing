$(document).ready(function(){	
	$("#BAILOUTWAY_NAME").change(function(){
		var BAILOUTWAY_NAME=$("#BAILOUTWAY_NAME").val();
		if(BAILOUTWAY_NAME=='1'||BAILOUTWAY_NAME=='2'){
			$(".fs").hide();
		}else{
			$(".fs").show();
		}	
	});
});

/**
 * 关闭对话款
 * @param div
 * @return
 */
function closeRefinaceWay(div){
	$("#"+div).dialog('close');
}

/**
 * 协议编号
 * @return
 */
function changeCugpCode(){
	var LAST_PRICE=$("#CUGP_CODE1").val();
	var CUGP_CODE1=$("#CUGP_CODE1").find("option:selected").text();
	$("#GRANT_PRICE").val(LAST_PRICE);
	$("#LAST_PRICE").val(LAST_PRICE);
	$("#CUGP_CODE").val(CUGP_CODE1);
}


/**
 * 
 * @return
 */
function doAddRefinaceWay(){
	var GRANT_PRICE=$("#GRANT_PRICE").val();
	var LAST_PRICE=$("#LAST_PRICE").val();
	if(isNaN($("#GRANT_PRICE").val())){
		alert("请填入正确的授信金额");
		return false;
	}else if($(".REPEAT_CREDIT").val()==''||$(".REPEAT_CREDIT").val()==null||$(".REPEAT_CREDIT").val()==undefined){
		alert("请确定是否是循环授信！");return false;
	}else if(parseFloat(GRANT_PRICE)>parseFloat(LAST_PRICE)){
		alert("授信余额不能大于协议授信余额"); 
		return false;
	}
	
//	if(!$("#addform").validationEngine({returnIsValid:true})){
//		return false;
//	}
	var ID = $("#JG_ID").val();
	
	var data="";
	$("form").find("[name]").each(function(){
		var ele=$(this);
		if(ele.is(":text")||ele.is("textarea")||ele.is(":hidden")||ele.is("select")){
			data+="&"+ele.attr("name")+"="+ele.val();
		}
	});
	var g_way="";
	$(".GUARANT_WAY").each(function(){	
		if($(this).attr("checked")=="checked"){
			g_way+=$(this).val()+",";
		}
	});
	
	
	var ra = "";
	$("input[name=REPEAT_CREDIT]").each(function(){
		if($(this).attr("checked")=="checked"){
			ra = $(this).val();
		}
	});
	data+="&GUARANT_WAY="+g_way+"&REPEAT_CREDIT="+ra;
	data=data.substring(1,data.length);
	$(".addMethod").attr("disabled","disabled");
    jQuery.ajax({
		url : _basePath+"/RefinanceMethod/RefinanceMethod!addMethod1.action",
		data : data,	 
		dataType:"json",
		success : function(json){
			if(json.flag==true){
				alert("添加成功！");
				window.location.href=_basePath+"/refinanceFHFA/RefinanceFHFA!toMgFhfa.action?ID="+ID;
			}else{
				alert("添加失败！");
			}
		}
	});
}