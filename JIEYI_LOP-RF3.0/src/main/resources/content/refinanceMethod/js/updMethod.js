
function _sel(ele){
	var ID = $(ele).attr("ckId");
	$("#toShowReWayDetail").dialog("open");
	jQuery.get(_basePath+"/RefinanceMethod/RefinanceMethod!selMethod.action?ID="+ID+"&data="+new Date(),function(html){
		$("#openShowReWayDetail").html(html);
	});
}
function _up(ele){
	var ID = $(ele).attr("uid");
	$("#toUpReWayDetail").dialog("open");
	jQuery.get(_basePath+"/RefinanceMethod/RefinanceMethod!updateMethod.action?ID="+ID+"&data="+new Date(),function(html){
		$("#openUpReWayDetail").html(html);
	});
}

function doUpReWayDetail(){
	var TOTAL_CREDIT = parseFloat($("#TOTAL_CREDIT").val());
	var GRANT_PRICE = parseFloat($("#GRANT_PRICE").val());

	if(TOTAL_CREDIT<GRANT_PRICE){
		alert("授信金额不能超过总的授信额度");
	}
	/*if(!$("#updform").validationEngine({returnIsValid:true})){
		return false;
	}*/
	var JG_ID = $("#JG_ID").val();
	
	var data="";
	$("form").find("[name]").each(function(){
		var ele=$(this);
		if(ele.is(":text")||ele.is("textarea")||ele.is(":hidden")||ele.is("select")){
			data+="&"+ele.attr("name")+"="+ele.val();
		}
	});
	
	var g_way="";
	$(".GUARANT_WAY1").each(function(){
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
	$(".updMethod").attr("disabled","disabled");
    jQuery.ajax({
		url : _basePath+"/RefinanceMethod/RefinanceMethod!updateMethod1.action",
		data : data,
		dataType:"json",
		success : function(json){
			if(json.flag=true){
				alert("更新成功！");
				window.location.href=_basePath+"/refinanceFHFA/RefinanceFHFA!toMgFhfa.action?ID="+JG_ID;
			}else{
				alert("更新失败！");
			}
		}
	});
}