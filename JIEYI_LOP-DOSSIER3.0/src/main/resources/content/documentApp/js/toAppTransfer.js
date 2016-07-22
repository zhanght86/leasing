$(function() {
	$("#DATA_ID_ALL").click(function() {
		var flag = $("#DATA_ID_ALL").attr("checked");
		if (flag == "checked") {
			$(".dataid").each(function() {
				if ($(this).attr("disabled") != "disabled") {
					$(this).attr("checked", true);
				}
			});
		} else {
			$(".dataid").each(function() {
				$(this).attr("checked", false);
			});
		}
	});
});

/**
 * 当邮寄方式发生变化的时候调用
 * @auther yangxue 
 * @date 2015-06-05
 */
function toChangeType(val){
	if(val=="0"){
		$(".sendByPost").attr("disabled", true);
	}else {
		$(".sendByPost").attr("disabled", false);
	}
}


/**
 * 申请归档, 保存(多合同归档)
 * @auther yangxue 
 * @date 2015-06-16
 * @returns
 */
function save1(){
    $("#save1").attr("disabled",true);
	
	var HANDIN_DATE = $("#HANDIN_DATE").datebox("getValue");//提交时间
	var HANDIN_PERSON = $("#HANDIN_PERSON").val();//呈报人
	var HANDIN_PHONE = $("#HANDIN_PHONE").val(); //呈报人电话
	var RECEIVE_PER = $("#RECEIVE_PER").val(); //接受人
	var RECEIVE_IDCARD =$("#RECEIVE_IDCARD").val(); //接受人身份证
	var RECEIVE_PHONE = $("#RECEIVE_PHONE").val(); //接受人电话
	var TRANSFER_TYPE = $("input[name=TRANSFER_TYPE]:checked").val(); //邮寄类型
	var MAILING_ADDRESS = $("#MAILING_ADDRESS").val(); //邮寄地址
	var ZIP_CODE = $("#ZIP_CODE").val(); //邮编
	var REMARK =  $("#REMARK").val();//备注
	
	if($("#HANDIN_DATE").datebox("getValue")==""){
		return alert("请填写提交时间");
	}
	if($("#HANDIN_PERSON").val()==""){
		return alert("请填写呈报人");
	}
	
	if($("#HANDIN_PHONE").val()==""){
		return alert("请填写呈报人电话");
	}
	
	if($("#RECEIVE_PER").val()==""){
		return alert("请填写接收人名称");
	}
	
	if($("#RECEIVE_IDCARD").val()==""){
		return alert("请填写接受人身份证号");
	}
	
	if($("#RECEIVE_PHONE").val()==""){
		return alert("请填写接受人电话");
	}	
	
	if($("input[name='TRANSFER_TYPE']:checked").val()=="2"){
		if($("#MAILING_ADDRESS").val()==""){
			return alert("请填写邮寄地址");
		}else{
			SEND_NUM = $("#MAILING_ADDRESS").val();
		}
		
		if($("#ZIP_CODE").val()==""){
			return alert("请填写邮编");
		}else{
			RECIPIENT = $("#ZIP_CODE").val();
		}
	}
	
	var saveRecord = [];
	$(".dataid").each(
			function(k) {
				if ($(this).attr("checked") == "checked") {
					flag = true;
					var temp = {};
					var td = $(this).parent("td").parent("tr");;
					temp.STORAGE_ID = $(this).val();
					temp.LEASE_CODE = $(this).attr("lease");
					temp.TRANSFER_NUMBER = $("input[name=TRANSFER_NUMBER]").val();
					saveRecord.push(temp);
				}
			});
	var data = {
			saveRecord:saveRecord,
			HANDIN_DATE:HANDIN_DATE,
			HANDIN_PERSON:HANDIN_PERSON,
			HANDIN_PHONE:HANDIN_PHONE,
			RECEIVE_PER:RECEIVE_PER,
			RECEIVE_IDCARD:RECEIVE_IDCARD,
			RECEIVE_PHONE:RECEIVE_PHONE,
			TRANSFER_TYPE:TRANSFER_TYPE,
			MAILING_ADDRESS:MAILING_ADDRESS,
			ZIP_CODE:ZIP_CODE,
			REMARK:REMARK
	}
	
	jQuery.ajax({
		url:_basePath+"/documentApp/ApplyTransfer!doAddTransferApp.action",
		type:'post',
		data:"datas="+ encodeURI(JSON.stringify(data)),
		dataType:"json",
		success:function(json){
			if(json.flag){
				$.messager.alert("提示", json.msg+", 下一节点的操作人为:"+json.data);
			}else{
				$.messager.alert("提示", "申请归档失败, "+json.msg);
			}
		}
	
	});
}