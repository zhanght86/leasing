/**
 * @author yx
 * @date 2013-10-14
 */
$(function(){
	$("body").click(function(e){
		var ele=$(e.target);
		if(ele.is(".qxsx")){
			_QxSx(ele);
		}
		/**
		if(ele.is(".xgSx")){
			_XgSx(ele);
		}**/
		if(ele.is(".ckSx")){
			_CkSx(ele);
		}
		if(ele.is(".qycg")){
			_QySx(ele);
		}
	});
});



/**
 * 关闭对话款
 * @author yx
 * @date 2013-10-14
 * @param div 弹出层id
 * @return
 */
function closeReCredit(div){
	$("#"+div).dialog('close');
}

/**
 * 必要信息验证
 * @author yx
 * @date 2013-10-14
 * @return
 */
function subNum(){
	var num=/^\d+(\.\d{1,3})?$/;
	var GRANT_PRICE=$("#GRANT_PRICE").val();
	var LAST_PRICE=$("#LAST_PRICE").val();
	var CUGP_CODE=$("#CUGP_CODE").val();
	var START_DATE=$("#START_DATE").val();
	var END_DATE=$("#END_DATE").val();
	if(CUGP_CODE==""){
		alert("协议编号不能为空");
		return false;
	} else if(GRANT_PRICE==""){
		alert("授信金额不能为空");
		return false;
	}else if(!num.test(GRANT_PRICE)){
			alert("授信额度只能为数字类型");
			return false;
	}else if(START_DATE==""){
		alert("授信日期不能为空");
		return false;
	}else if(END_DATE==""){
		alert("授信日期不能为空");
		return false;
	}
	return true;
}

/**
 * 提交授信信息
 * @author yx
 * @date 2013-10-14
 * @return
 */
function toAddReCredit(){
	 $('#formaddupd').form('submit',{
		 dataType:"json",
		 success:function(json){
		 	var obj = eval('('+json+')');
		 	if(obj.flag==true){
		 		alert("融资机构授信添加成功");
		 		window.location.href=_basePath+"/refinanceFHFA/RefinanceFHFA!toMgFhfa.action";
		 	}else {
		 		alert("融资机构授信添加失败");
		 		window.location.href=_basePath+"/refinanceFHFA/RefinanceFHFA!toMgFhfa.action";
		 	}
	 	 }
	 });
	 
	 
}

/**
 * 授信查看
 * @author yx
 * @date 2013-10-14
 * @param FHFA_ID
 * @return
 */
function toCreditGranting(FHFA_ID){
	$("#toShowReCredit").dialog('open');
	jQuery.get(_basePath+'/reCreditManagement/ReCreditManagement!queryGrantplanList.action?FK_ID='+FHFA_ID+"&data="+new Date(),function(html){
		$("#openShowReCredit").html(html);
	});
}

/**
 * 取消授信
 * @author yx
 * @date 2013-10-14
 * @param ele
 * @return
 */
function _QxSx(ele){
	var IDD = $(ele).attr("sxFk");
	var ID = $(ele).attr("sxId");
	jQuery.ajax({
		url:_basePath+"/reCreditManagement/ReCreditManagement!creditCancel.action",
		data:"FK_ID="+IDD+"&STATUS=1"+"&ID="+ID,
		dataType:"json",
		success:function(json){
			if(json.flag == true){
				alert("取消成功");				
				$("#toShowReCredit").dialog('close');
				window.location.href=_basePath+"/refinanceFHFA/RefinanceFHFA!toMgFhfa.action?ID="+IDD;
			}else{
				alert("取消失败");
			}
			
		}
	});	
}

/***
 * 授信启动
 * @author yx
 * @date 2013-10-14
 * @param ele
 * @return
 */
function _QySx(ele){
	var IDD = $(ele).attr("sxFk");
	var ID = $(ele).attr("qycg");
	jQuery.ajax({
		url:_basePath+"/reCreditManagement/ReCreditManagement!startCredit.action",
		data:"FK_ID="+IDD+"&STATUS=0"+"&ID="+ID,
		dataType:"json",
		success:function(json){
			if(json.flag == true){
				alert("启用成功");
				$("#toShowReCredit").dialog('close');
				window.location.href=_basePath+"/refinanceFHFA/RefinanceFHFA!toMgFhfa.action?ID="+IDD;
			}else{
				alert("启用失败");
			}
		}
	});		
}

/**
 * 授信详细查看
 * @author yx
 * @date 2013-10-14
 * @param FHFA_ID
 * @return
 */
function _CkSx(ele){
	var ID = $(ele).attr("sxId");
	$("#toShowReCreditDetail").dialog('open');
	jQuery.get(_basePath+'/reCreditManagement/ReCreditManagement!selGrantplan.action?ID='+ID+"&data="+new Date(),function(html){
		$("#openShowReCreditDetail").html(html);
	});
}

function downloadFile(path){
	if(path.length>=1)
		location.href=_basePath+"/reCreditManagement/ReCreditManagement!download.action?path="+path;
}

/**
 * 授信详细修改
 * @author yx
 * @date 2013-10-14
 * @param ele
 * @return
 */
function _XgSx(ele){
	var ID = $(ele).attr("sxId");
	$("#toUpdateReCreditDetail").dialog('open');
	jQuery.get(_basePath+'/reCreditManagement/ReCreditManagement!GrantplanUpd.action?ID='+ID+"&data="+new Date(),function(html){
		$("#openUpdateReCreditDetail").html(html);
	});
}

/**
 * 授信详细修改操作
 */
function toUpdateReCreditDetail(){
	//var grantprice=substr($("#GRANT_PRICE1").val(),$("#GRANT1").val());
	var grantprice=$("#GRANT_PRICE1").val();
	var data="";
	var id = $("#FK_ID").val();
	$("#reupdateform").find("[name]").each(function(){
		var ele=$(this);
		if(ele.is(":text")||ele.is("textarea")||ele.is(":hidden")){
			data+="&"+ele.attr("name")+"="+ele.val();
		}
		if(ele.is(":radio:checked")){
			data+="&"+ele.attr("name")+"="+ele.val();
		}
	});
	data+="&GRANT_PRICE="+grantprice;
	data=data.substring(1,data.length);
    jQuery.ajax({
		url : _basePath+"/reCreditManagement/ReCreditManagement!GrantplanAjaxUpd.action",
		data : encodeURI(data),	 
		async:false,
		dataType:"json",
		success : function(json){
			if(json.flag == true){
				alert("更新成功！");
				window.location.href=_basePath+"/refinanceFHFA/RefinanceFHFA!toMgFhfa.action?ID="+id;
			}else{
				alert("更新失败！");
			}
		}
	});
}