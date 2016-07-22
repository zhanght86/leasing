 var tempIndex = 1;
function copyTrToAddLK() {
	var fromId=$("#eq_template_ValueToAdd").val();
	var toId=$("#eq_body_ValueToAdd").val();
	var tr=$("#"+fromId).clone();
	$(tr).removeAttr("style");
	$(tr).removeAttr("id");
	$(tr).attr("class","eq_body_tr");
	
	$(tr).find("input[name='TOTHEPEOPLE']").attr("name",'TOTHEPEOPLE_'+tempIndex);
	$(tr).find("select[name*=TOTHETYPE]").attr("name",'TOTHETYPE_'+tempIndex).attr("id",'TOTHETYPE_'+tempIndex);
	
	$(tr).find("select[name*=MONEYMODE]").attr("name",'MONEYMODE_'+tempIndex).attr("id",'MONEYMODE_'+tempIndex);
	

	$(tr).find("input[name='ABSMONEY']").attr("name",'ABSMONEY_'+tempIndex);
	
	$(tr).find("input[name='TOTHEMONEY']").attr("name",'TOTHEMONEY_'+tempIndex);
	
	$(tr).find("select[name*=STATUS]").attr("name",'STATUS_'+tempIndex).attr("id",'STATUS_'+tempIndex);
	$(tr).find("input[name='TOTHETIME']").attr("name",'TOTHETIME_'+tempIndex).attr("class","easyui-datebox");
	
	$(tr).find("input[name='VOUCHER']").attr("name",'VOUCHER_'+tempIndex).attr("id",'VOUCHER_'+tempIndex);
	
	$(tr).find("input[name='PICTNAME']").attr("name",'PICTNAME_'+tempIndex).attr("id",'PICTNAME_'+tempIndex);
	$(tr).find("input[name='PICTPATH']").attr("name",'PICTPATH_'+tempIndex).attr("id",'PICTPATH_'+tempIndex);
	
	$(tr).find("a[name='PICTNAMEURL']").attr("name",'PICTNAMEURL_'+tempIndex).attr("id",'PICTNAMEURL_'+tempIndex);
	$(tr).find("a[name='index']").attr("name",'index_'+tempIndex).attr("indexFlag",tempIndex);;
	$("#"+toId).append(tr);
	$.parser.parse(tr);
	tempIndex++;
}
function deleteTr(tbodyId) {
	$("#"+tbodyId+"> tr").each(function (){
		var box=$(this).find(":checkbox");
		if ($(box).attr("checked"))
		{
			$(this).remove();
		}
	});
	countMoney();
}

function toTheMoneyForUpd(str, obj) {
	var moneyName_from = obj.name.replace(str,'ABSMONEY');
	var moneyName_to = obj.name.replace(str,'TOTHEMONEY');
	var statusName = obj.name.replace(str,'STATUS');
	var statusFlag = $("select[name*='"+statusName+"']").val();
	if (statusFlag == 2) {
		$("input[name*='"+moneyName_to+"']").val(-$("input[name*='"+moneyName_from+"']").val());
	} else {
		$("input[name*='"+moneyName_to+"']").val($("input[name*='"+moneyName_from+"']").val());
	}
	
	countMoney();
}


function countMoney(){
	var firstPayAll=$("#FIRSTPAYALL_HID").val();
	var status = $("#STATUS").val();
	var totMoneys = new Array();
	var size = $("input[name*='TOTHEMONEY_']").length;
	totMoneys = $("input[name*='TOTHEMONEY_']");
	var countMoney = 0;
	var difference = 0;
	for (var i = 0; i < size; i++) {
		if (totMoneys[i].value != '' && parseInt(totMoneys[i].value) > 0) {
			countMoney = parseInt(countMoney) + parseInt(totMoneys[i].value);
		}
	}
	difference = parseInt(firstPayAll) - parseInt(countMoney);
	$("#countMoney").html(countMoney);
	$("#difference").html(difference);
}

//上传资料文件
function uploadPaperFile(obj) {
	$("#indexFlag").val(obj.name.replace('index',''));
	$("#uploadPaperFileDiv").show();
	$("#uploadPaperFileDiv")
			.dialog(
					{
						title : "上传资料文件",
						modal : true,
						resizable : true,
						buttons : [
								{
									id : "btnbc",
									text : '保 存',
									iconCls : 'icon-save',
									handler : function() {
										$('#uploadPaperFileForm')
												.form(
														{
															url : _basePath + '/project/project!uploadPaperFileReturn.action',
															 onSubmit: function(){
																 var check = $("#fromDate").form('validate');
																 if(check){
																	 var file =
																	 $("#file").val();
																	 if(file == null ||
																		 file == ''){
																		 alert("请选择要上传的资料文件");
																		 return false;
																	 }
																	 $('#btnbc').linkbutton('disable');
																 }
															 },
															success : function(json) {
																json = jQuery
																		.parseJSON(json);
																var ind = $("#indexFlag").val();
																if (json.flag) {
																	$("#uploadPaperFileForm").form('clear');
																	$("#uploadPaperFileDiv")
																			.dialog(
																					'close');
																					
																		var ID = $(this).attr("SID2");
																    $("#PICTNAMEURL"+ind).attr("PICTPATH",json.msg.split(',')[0]);				
																	$("#PICTNAMEURL"+ind).html(json.msg.split(',')[1]);
																	
																	$("#PICTPATH"+ind).val(json.msg.split(',')[0]);
																	$("#PICTNAME"+ind).val(json.msg.split(',')[1]);
																} else {
																	alert(json.msg);
																}
																$("#uploadPaperFileForm").form('clear');
																$("#indexFlag").val('');
															}
														});
										$('#uploadPaperFileForm').submit();
									}
								}, {
									text : '关 闭',
									iconCls : 'icon-cancel',
									handler : function() {
										$("#uploadPaperFileDiv").dialog('close');
									}
								} ]
					});
}

//下载资料文件
function downloadFile(obj) {
	var PICTPATH =  $("#PICTNAMEURL"+obj.name.replace('PICTNAMEURL','')).attr("PICTPATH");	
	window.location.href = _basePath
			+ "/project/project!downPaperFile.action?PICTPATH="
			+ PICTPATH;
}
function doDepositUpdate(){
	var paramFrom = getFromData("#editDeposit");
	jQuery.ajax({
		url:_basePath+"/project/project!doDepositUpdate.action",
		data: "params="+paramFrom,
		dataType: "json",
		type: "post",
		success: function(result){
			if (result.flag==false){
                jQuery.messager.alert("提示",result.msg);
            }else{
			   jQuery.messager.alert("提示",'保存成功！');
			} 
		}
	});
}