$(function(){ 
	var type =  $("#TYPE").attr("selected",true).val();
	if(type == "LP"){
		$.ajax({
			url:_basePath+"/customers/custFinance!toViewFinance.action?CLIENT_ID="+$("#CLIENT_ID").val(),
			type:"post",
			dataType:"json",
			success:function(json){
				if(json.data != null){
					$("#KJHJ_MONEY").val(json.data.KJHJ_MONEY);
					$("#DWDB_MONEY").val(json.data.DWDB_MONEY);
					$("#ZCZE_MONEY").val(json.data.ZCZE_MONEY);
					$("#LDZC_MONEY").val(json.data.LDZC_MONEY);
					$("#GDZC_MONEY").val(json.data.GDZC_MONEY);
					$("#FZ_MONEY").val(json.data.FZ_MONEY);
					$("#LDFZ_MONEY").val(json.data.LDFZ_MONEY);
					$("#CQFZ_MONEY").val(json.data.CQFZ_MONEY);
					$("#ZCFZ_RATE").val(json.data.ZCFZ_RATE);
					$("#LDBL_RATE").val(json.data.LDBL_RATE);
					$("#JZCSYL").val(json.data.JZCSYL);
					$("#SNYYSR").val(json.data.SNYYSR);
					$("#SNYYLR").val(json.data.SNYYLR);
					$("#YYLRL").val(json.data.YYLRL);
					
					$("#YYSRZZL").val(json.data.YYSRZZL);
					$("#YYLRZZL").val(json.data.YYLRZZL);
					$("#ZZCBCL").val(json.data.ZZCBCL);
					$("#FINANCE_ID").val(json.data.FINANACE_ID);
				}
			}
		});
	}
	//初始化页面时 直辖市则改为二级选择框 例如北京市
//	var city=$("#HOUSE_ADDRESS_CITY").attr("selected",true).val();
//	if(city=="200000143"){
//		$("#HOUSE_ADDRESS_CITY").attr("class","");
//		$("#HOUSE_ADDRESS_CITY").hide();
//	}
});

/**
 * 证件号码验证
 * @param cardId
 * @return
 */
function checkCardNo(cardId){
	var cardNo = $("#"+cardId).val().toUpperCase();
	var id_card_type = $("#ID_CARD_TYPE").attr("selected",true).val();
	var CUST_TYPE=$("#CUST_TYPE").val();
	if(id_card_type == "1" || id_card_type == "4"){
		if(isCardNo(cardId,id_card_type)){			
			$.ajax({
				type:"post",
				url:_basePath + "/customers/Customers!checkIdCardNoNew.action?ID_CARD_NO="+cardNo+"&id_card_type="+id_card_type+"&CUST_TYPE="+CUST_TYPE,
				dataType:"json",
				success:function(json){
				var PROJECT_ID=$("#PROJECT_ID").val();
					if(PROJECT_ID !=null && PROJECT_ID !=''){
						if(json.flag){
							// upd gengchangbao JZZL-109 2016年3月7日18:21:26 start
//							$.messager.confirm("询问","身份证号码已存在，请确认是否选择已有身份信息？",function(r){
//								if(r){
							alert("证号码已存在,将自动导入身份信息！");
							// upd gengchangbao JZZL-109 2016年3月7日18:21:26 end
									var OLD_CLIENT_ID=$("#CLIENT_ID").val();
									var CLIENT_ID=json.data[0].ID;
									var TYPE=json.data[0].TYPE;
									var now_age =$("#AGE").val();
										//替换客户
										$.ajax({
											type:"post",
											url:_basePath + "/customers/Customers!toUpdateCustInfoNew.action?CLIENT_ID="+CLIENT_ID+"&PROJECT_ID="+PROJECT_ID+"&OLD_CLIENT_ID="+OLD_CLIENT_ID+"&TYPE=NP",
											dataType:"json",
											success:function(rtjson){
												if(rtjson){
													window.location.reload();
//													$('#base_ifo').tabs('getSelected').panel('refresh', _basePath+"/customers/Customers!toUpdateCustInfo1.action?CLIENT_ID="+CLIENT_ID+"&TYPE="+TYPE+"&tab=update&JD=JBPM&PROJECT_ID="+PROJECT_ID);
													//算一遍年龄
													rtjson.date[0].AGE=now_age;
												}
											}
										});
									
									
									
//								}else{
//									//$("#"+cardId).val("");
//									$("#"+cardId).focus(); 
//								}
//								
//							});
						}
					}else{
						if(json.flag){
							alert("证件号已存在，请重新填写！");
							//$("#"+cardId).val("");
							$("#"+cardId).focus(); 
							
						}
					}
					
				}
			});
		}else{
			$("#"+cardId).focus(); 
		}
	}else {
		if(isCardNo(cardId,id_card_type)){
			jQuery.ajax({
				type:"post",
				url:_basePath + "/customers/Customers!checkIdCardNoNew.action?ID_CARD_NO="+cardNo+"&id_card_type="+id_card_type+"&CUST_TYPE="+CUST_TYPE,
				dataType:"json",
				success:function(json){
				var PROJECT_ID=$("#PROJECT_ID").val();
					if(PROJECT_ID !=null && PROJECT_ID !=''){
						if(json.flag){
							// upd gengchangbao JZZL-109 2016年3月7日18:21:26 start
//							$.messager.confirm("询问","身份证号码已存在，请确认是否选择已有身份信息？",function(r){
//								if(r){
							alert("证号码已存在,将自动导入身份信息！");
							// upd gengchangbao JZZL-109 2016年3月7日18:21:26 end
									var OLD_CLIENT_ID=$("#CLIENT_ID").val();
									var CLIENT_ID=json.data[0].ID;
									var TYPE=json.data[0].TYPE;
									
										//替换客户
										$.ajax({
											type:"post",
											url:_basePath + "/customers/Customers!toUpdateCustInfoNew.action?CLIENT_ID="+CLIENT_ID+"&PROJECT_ID="+PROJECT_ID+"&OLD_CLIENT_ID="+OLD_CLIENT_ID+"&TYPE=NP",
											dataType:"json",
											success:function(rtjson){
												if(rtjson){
													window.location.reload();
	//												$('#base_ifo').tabs('getSelected').panel('refresh', _basePath+"/customers/Customers!toUpdateCustInfo1.action?CLIENT_ID="+CLIENT_ID+"&TYPE="+TYPE+"&tab=update&JD=JBPM&PROJECT_ID="+PROJECT_ID);
												}
											}
										});
									
//								}else{
//									//$("#"+cardId).val("");
//									$("#"+cardId).focus(); 
//								}
//								
//							});
						}
					}else{
						if(json.flag){
							alert("证件号已存在，请重新填写！");
							//$("#"+cardId).val("");
							$("#"+cardId).focus(); 
							
						}
					}
					
				}
			});
		}
	}
}


/**
 * 证件号码验证
 * @param cardId
 * @return
 */
function checkGtczrCardNo(cardId){
	var cardNo = $("#"+cardId).val().toUpperCase();
	var id_card_type = $("#ID_CARD_TYPE").attr("selected",true).val();
	var CUST_TYPE=$("#CUST_TYPE").val();
	if(id_card_type == "1" || id_card_type == "4"){
		if(isCardNo(cardId,id_card_type)){			
			$.ajax({
				type:"post",
				url:_basePath + "/customers/Customers!checkIdCardNoNew.action?ID_CARD_NO="+cardNo+"&id_card_type="+id_card_type+"&CUST_TYPE="+CUST_TYPE,
				dataType:"json",
				success:function(json){
				var PROJECT_ID=$("#PROJECT_ID").val();
					if(PROJECT_ID !=null && PROJECT_ID !=''){
						if(json.flag){
							// upd gengchangbao JZZL-109 2016年3月7日18:21:26 start
//							$.messager.confirm("询问","身份证号码已存在，请确认是否选择已有身份信息？",function(r){
//								if(r){
							alert("证号码已存在,将自动导入身份信息！");
							// upd gengchangbao JZZL-109 2016年3月7日18:21:26 end
									var OLD_CLIENT_ID=$("#CLIENT_ID").val();
									var CLIENT_ID=json.data[0].ID;
									var TYPE=json.data[0].TYPE;
									
										//替换客户的共同承租人
										$.ajax({
											type:"post",
											url:_basePath + "/customers/Customers!toUpdateGtczrCustInfoNew.action?CLIENT_ID="+CLIENT_ID+"&PROJECT_ID="+PROJECT_ID,
											dataType:"json",
											success:function(rtjson){
												if(rtjson){
													window.location.reload();
//													$('#base_ifo').tabs('getSelected').panel('refresh', _basePath+"/customers/Customers!toUpdateCustInfo1.action?CLIENT_ID="+CLIENT_ID+"&TYPE="+TYPE+"&tab=update&JD=JBPM&PROJECT_ID="+PROJECT_ID);
												}
											}
										});
									
									
									
//								}else{
//									//$("#"+cardId).val("");
//									$("#"+cardId).focus(); 
//								}
//								
//							});
						}
					}else{
						if(json.flag){
							alert("证件号已存在，请重新填写！");
							//$("#"+cardId).val("");
							$("#"+cardId).focus(); 
							
						}
					}
					
				}
			});
		}
	}else {
		jQuery.ajax({
			type:"post",
			url:_basePath + "/customers/Customers!checkIdCardNoNew.action?ID_CARD_NO="+cardNo+"&id_card_type="+id_card_type+"&CUST_TYPE="+CUST_TYPE,
			dataType:"json",
			success:function(json){
			var PROJECT_ID=$("#PROJECT_ID").val();
				if(PROJECT_ID !=null && PROJECT_ID !=''){
					if(json.flag){
						// upd gengchangbao JZZL-109 2016年3月7日18:21:26 start
//						$.messager.confirm("询问","身份证号码已存在，请确认是否选择已有身份信息？",function(r){
//							if(r){
						alert("证号码已存在,将自动导入身份信息！");
						// upd gengchangbao JZZL-109 2016年3月7日18:21:26 end
								var OLD_CLIENT_ID=$("#CLIENT_ID").val();
								var CLIENT_ID=json.data[0].ID;
								var TYPE=json.data[0].TYPE;
								
									//替换客户
									$.ajax({
										type:"post",
										url:_basePath + "/customers/Customers!updateProjectGtczrCust.action?CLIENT_ID="+CLIENT_ID+"&PROJECT_ID="+PROJECT_ID,
										dataType:"json",
										success:function(rtjson){
											if(rtjson){
												window.location.reload();
//												$('#base_ifo').tabs('getSelected').panel('refresh', _basePath+"/customers/Customers!toUpdateCustInfo1.action?CLIENT_ID="+CLIENT_ID+"&TYPE="+TYPE+"&tab=update&JD=JBPM&PROJECT_ID="+PROJECT_ID);
											}
										}
									});
								
//							}else{
//								//$("#"+cardId).val("");
//								$("#"+cardId).focus(); 
//							}
//							
//						});
					}
				}else{
					if(json.flag){
						alert("证件号已存在，请重新填写！");
						//$("#"+cardId).val("");
						$("#"+cardId).focus(); 
						
					}
				}
				
			}
		});
	}
}

/**
 * 证件号码验证
 * @param cardId
 * @return
 */
function checkDBRCardNo(cardId){
	var cardNo = $("#"+cardId).val().toUpperCase();
	var id_card_type = $("#ID_CARD_TYPE").attr("selected",true).val();
	var CUST_TYPE=$("#CUST_TYPE").val();
	if(id_card_type == "1" || id_card_type == "4"){
		if(isCardNo(cardId,id_card_type)){			
			$.ajax({
				type:"post",
				url:_basePath + "/customers/Customers!checkIdCardNoNew.action?ID_CARD_NO="+cardNo+"&id_card_type="+id_card_type+"&CUST_TYPE="+CUST_TYPE,
				dataType:"json",
				success:function(json){
				var PROJECT_ID=$("#PROJECT_ID").val();
					if(PROJECT_ID !=null && PROJECT_ID !=''){
						if(json.flag){
							// upd gengchangbao JZZL-109 2016年3月7日18:21:26 start
//							$.messager.confirm("询问","身份证号码已存在，请确认是否选择已有身份信息？",function(r){
//								if(r){
							alert("证号码已存在,将自动导入身份信息！");
							// upd gengchangbao JZZL-109 2016年3月7日18:21:26 end
									var OLD_CLIENT_ID=$("#CLIENT_ID").val();
									var CLIENT_ID=json.data[0].ID;
									var TYPE=json.data[0].TYPE;
									
										//替换客户
										$.ajax({
											type:"post",
											url:_basePath + "/customers/Customers!toUpdateDbrCustInfoNew.action?CLIENT_ID="+CLIENT_ID+"&PROJECT_ID="+PROJECT_ID,
											dataType:"json",
											success:function(rtjson){
												if(rtjson){
													window.location.reload();
//													$('#base_ifo').tabs('getSelected').panel('refresh', _basePath+"/customers/Customers!toUpdateCustInfo1.action?CLIENT_ID="+CLIENT_ID+"&TYPE="+TYPE+"&tab=update&JD=JBPM&PROJECT_ID="+PROJECT_ID);
												}
											}
										});
									
									
									
//								}else{
//									//$("#"+cardId).val("");
//									$("#"+cardId).focus(); 
//								}
//								
//							});
						}
					}else{
						if(json.flag){
							alert("证件号已存在，请重新填写！");
							//$("#"+cardId).val("");
							$("#"+cardId).focus(); 
							
						}
					}
					
				}
			});
		}
	}else {
		if(isCardNo(cardId,id_card_type)){
			jQuery.ajax({
				type:"post",
				url:_basePath + "/customers/Customers!checkIdCardNoNew.action?ID_CARD_NO="+cardNo+"&id_card_type="+id_card_type+"&CUST_TYPE="+CUST_TYPE,
				dataType:"json",
				success:function(json){
				var PROJECT_ID=$("#PROJECT_ID").val();
					if(PROJECT_ID !=null && PROJECT_ID !=''){
						if(json.flag){
							// upd gengchangbao JZZL-109 2016年3月7日18:21:26 start
//							$.messager.confirm("询问","身份证号码已存在，请确认是否选择已有身份信息？",function(r){
//								if(r){
							alert("证号码已存在,将自动导入身份信息！");
							// upd gengchangbao JZZL-109 2016年3月7日18:21:26 end
									var OLD_CLIENT_ID=$("#CLIENT_ID").val();
									var CLIENT_ID=json.data[0].ID;
									var TYPE=json.data[0].TYPE;
									
										//替换客户
										$.ajax({
											type:"post",
											url:_basePath + "/customers/Customers!toUpdateDbrCustInfoNew.action?CLIENT_ID="+CLIENT_ID+"&PROJECT_ID="+PROJECT_ID,
											dataType:"json",
											success:function(rtjson){
												if(rtjson){
													window.location.reload();
//													$('#base_ifo').tabs('getSelected').panel('refresh', _basePath+"/customers/Customers!toUpdateCustInfo1.action?CLIENT_ID="+CLIENT_ID+"&TYPE="+TYPE+"&tab=update&JD=JBPM&PROJECT_ID="+PROJECT_ID);
												}
											}
										});
									
//								}else{
//									//$("#"+cardId).val("");
//									$("#"+cardId).focus(); 
//								}
//								
//							});
						}
					}else{
						if(json.flag){
							alert("证件号已存在，请重新填写！");
							//$("#"+cardId).val("");
							$("#"+cardId).focus(); 
							
						}
					}
					
				}
			});
		}
		
	}
}


/**
 * 证件号码验证
 * @param cardId
 * @return
 */
function checkSexAgeCardNo(cardId){
	var cardNo = $("#"+cardId).val().toUpperCase();
	var id_card_type = $("#ID_CARD_TYPE").attr("selected",true).val();
	isCardNo(cardId,id_card_type);
}

/**
 * 组织机构代码证验证
 * @param oragnizationCodeId
 * @return
 */
function checkOragnizationCode(oragnizationCodeId){
	var ORAGNIZATION_CODE = $("#"+oragnizationCodeId).val();
	var CUST_TYPE=$("#CUST_TYPE").val();
	jQuery.ajax({
		type:"post",
		url:_basePath + "/customers/Customers!oragnizationCode.action?ORAGNIZATION_CODE="+ORAGNIZATION_CODE+"&CUST_TYPE="+CUST_TYPE,
		dataType:"json",
		success:function(json){
			var PROJECT_ID=$("#PROJECT_ID").val();
			if(PROJECT_ID !=null && PROJECT_ID !=''){
				if(json.flag){
					$.messager.confirm("询问","组织机构代码证号已存在，确认选择已有身份信息？",function(r){
						if(r){
							var OLD_CLIENT_ID=$("#CLIENT_ID").val();
							var CLIENT_ID=json.data[0].ID;
							var TYPE=json.data[0].TYPE;
							
								//替换客户
								$.ajax({
									type:"post",
									url:_basePath + "/customers/Customers!toUpdateCustInfoNew.action?CLIENT_ID="+CLIENT_ID+"&PROJECT_ID="+PROJECT_ID+"&OLD_CLIENT_ID="+OLD_CLIENT_ID+"&TYPE=LP",
									dataType:"json",
									success:function(rtjson){
										if(rtjson){
											window.location.reload();
//											$('#base_ifo').tabs('getSelected').panel('refresh', _basePath+"/customers/Customers!toUpdateCustInfo1.action?CLIENT_ID="+CLIENT_ID+"&TYPE="+TYPE+"&tab=update&JD=JBPM&PROJECT_ID="+PROJECT_ID);
										}
									}
								});
							
							
							
						}else{
//							$("#"+oragnizationCodeId).val("");
							$("#"+oragnizationCodeId).focus(); 
						}
						
					});
				}
			}else{
				if(json.flag){
					alert("组织机构代码证号已存在，请重新填写！");
//					$("#"+oragnizationCodeId).val("");
					$("#"+oragnizationCodeId).focus();
					
				}
			}
			
		}
	});	
}

/**
 * 组织机构代码证验证
 * @param oragnizationCodeId
 * @return
 */
function checkDbrOragnizationCode(oragnizationCodeId){
	var ORAGNIZATION_CODE = $("#"+oragnizationCodeId).val();
	var CUST_TYPE=$("#CUST_TYPE").val();
	jQuery.ajax({
		type:"post",
		url:_basePath + "/customers/Customers!oragnizationCode.action?ORAGNIZATION_CODE="+ORAGNIZATION_CODE+"&CUST_TYPE="+CUST_TYPE,
		dataType:"json",
		success:function(json){
			var PROJECT_ID=$("#PROJECT_ID").val();
			if(PROJECT_ID !=null && PROJECT_ID !=''){
				if(json.flag){
					$.messager.confirm("询问","组织机构代码证号已存在，确认选择已有身份信息？",function(r){
						if(r){
							var CLIENT_ID=json.data[0].ID;
							var TYPE=json.data[0].TYPE;
							
								//替换客户
								$.ajax({
									type:"post",
									url:_basePath + "/customers/Customers!toUpdateDbrCustInfoNew.action?CLIENT_ID="+CLIENT_ID+"&PROJECT_ID="+PROJECT_ID,
									dataType:"json",
									success:function(rtjson){
										if(rtjson){
											window.location.reload();
//											$('#base_ifo').tabs('getSelected').panel('refresh', _basePath+"/customers/Customers!toUpdateCustInfo1.action?CLIENT_ID="+CLIENT_ID+"&TYPE="+TYPE+"&tab=update&JD=JBPM&PROJECT_ID="+PROJECT_ID);
										}
									}
								});
							
							
							
						}else{
//							$("#"+oragnizationCodeId).val("");
							$("#"+oragnizationCodeId).focus();
						}
						
					});
				}
			}else{
				if(json.flag){
					alert("组织机构代码证号已存在，请重新填写！");
//					$("#"+oragnizationCodeId).val("");
					$("#"+oragnizationCodeId).focus();
					
				}
			}
			
		}
	});	
}
/**
 * 更改客户类型
 * @param val
 * @return
 */
function choseCustType(val){
	var CREDIT_ID = $("#CREDIT_ID").val();
	var PROJECT_ID = $("#CREDIT_ID").val();
	var GLS_CODE=$("#GLS_CODE_C").val();
	window.location.href=_basePath+"/customers/Customers!toAddCust.action?CUST_TYPE="+val+"&CREDIT_ID="+CREDIT_ID+"&PROJECT_ID="+PROJECT_ID+"&GLS_CODE="+GLS_CODE;
}

function winBack(){
	top.removeTab('添加担保人');
}

/**
 * 根据省的id获取市
 * @param val
 * @return
 */
function getCity(val){
	jQuery.ajax({
		url:_basePath+"/customers/Customers!getCity.action?PARENT_ID="+val,
		type:"post",
		dataType:"json",
		success:function(json){
				//将本行的输入框初始化
				$("#PROJECT_CITY").each(function (){
					//初始化
					if ($(this).is("select")){
						$(this).empty();
						$(this).append($("<option>").val("").text("--请选择--"));
						
					}
				});
				for(var i=0;i<json.length;i++){
					$("#PROJECT_CITY").append($("<option value='"+json[i].ID+"'>"+json[i].NAME+"</option>"));				
				}
		}
	});
}
/**
 * 根据父节点获取子节点信息
 * @param val
 * @return
 */
function getChildArea(obj,val,childId){
	var tr=$(obj).parent().parent();
	jQuery.ajax({
		url:_basePath+"/customers/Customers!getChildArea.action?PARENT_ID="+val,
		type:"post",
		dataType:"json",
		success:function(json){
				//将本行的输入框初始化
				$(tr).find("#"+childId).each(function (){
					//初始化
					if ($(this).is("select")){
						$(this).empty();
						$(this).append($("<option>").val("").text("--请选择--"));
						
					}
				});
				for(var i=0;i<json.length;i++){
					$(tr).find("#"+childId).append($("<option value='"+json[i].ID+"'>"+json[i].NAME+"</option>"));				
				}
		}
	});
}

/**
 * 根据父节点获取子节点信息
 * @param val
 * @return
 */
function getChildArea2(obj,val,childId1,childId2){
	var tr=$(obj).parent().parent();
	jQuery.ajax({
		url:_basePath+"/customers/Customers!getChildArea.action?PARENT_ID="+val,
		type:"post",
		dataType:"json",
		success:function(json){
				
				//将本行的输入框初始化
				$(tr).find("#"+childId1).each(function (){
					//初始化
					if ($(this).is("select")){
						$(this).empty();
						$(this).append($("<option>").val("").text("--请选择--"));
						
					}
				});
				//将本行的输入框初始化
				$(tr).find("#"+childId2).each(function (){
					//初始化
					if ($(this).is("select")){
						$(this).empty();
						$(this).append($("<option>").val("").text("--请选择--"));
						
					}
				});
				for(var i=0;i<json.length;i++){
					$(tr).find("#"+childId1).append($("<option value='"+json[i].ID+"'>"+json[i].NAME+"</option>"));				
				}
				//如果是直辖市,则两级选择框
//				if(val=="100009998" || val=="100010003" || val=="100439000" || val=="100246608"){
//					if(json.length==1){//二级市为一个选项
//						getChildArea(obj,json[0].ID,childId2);
//						$(tr).find("option[value="+json[0].ID+"]").attr("selected",true);
//					}else{
//						getChildArea(obj,val,childId2);
//					}	
//					$(tr).find("#"+childId1).attr("class","");
//					$(tr).find("#"+childId1).hide();
//				}else{
//					$(tr).find("#"+childId1).attr("class","warm");
//					$(tr).find("#"+childId1).show();
//				}
		}
	});
}


/**
 * 根据父节点获取子节点信息
 * @param val
 * @return
 */
function getChildAreaById(_this){
	if( $(_this).val()==null ||  $(_this).val()=='') return ;
	var val = $(_this).val() ;
	var text = $(_this).find("option:selected").text(); 
	
	if($(_this).length<=1){
		if(val!=null){
		jQuery.ajax({
		url:_basePath+"/customers/Customers!getChildAreaById.action?ID="+val,
		type:"post",
		dataType:"json",
		success:function(json){
				
				
				var length1 =  $(_this).find("option[value!='']").length ;
				
				if(length1!=json.length){
					$(_this).empty() ;
					$(_this).append($("<option>").val(val).text(text));
					for(var i=0;i<json.length;i++){
		
						if(json[i].ID!=val){
							$(_this).append($("<option value='"+json[i].ID+"'>"+json[i].NAME+"</option>"));		
						}
				
					}	
					
				}
					
				}
			});
		}
	}	
}

function getChildAreaBank(_this){
	if( $(_this).val()==null ||  $(_this).val()=='') return ;
	var val = $(_this).val() ;
	var text = $(_this).find("option:selected").text(); 
	
	if($(_this).length<=1){
		if(val!=null){
		jQuery.ajax({
		url:_basePath+"/customers/Customers!getCityAreaById.action?ID="+val,
		type:"post",
		dataType:"json",
		success:function(json){
				$(_this).parent().parent().next().find("select").empty();
				$(_this).parent().parent().next().find("select").append("<option value=''>--请选择--</option>");
				for(var i=0;i<json.length;i++){
					$(_this).parent().parent().next().find("select").append($("<option value='"+json[i].ID+"'>"+json[i].NAME+"</option>"));		
				}	
					
				}
			});
		}
	}	
}
/* 保存客户信息
 * 
 * @return
 */
function submitForm() {	

	if (checking2()) {
		var type =$("#TYPE").attr("selected", true).val();
		//客户为企业 -LP,进行验证
		if(type=="LP"){
			//注册时间校验
			if(!dateComparison()){
				return false;
			}
			//营业执照有效期校验
			if(!changeDataYz()){
				return false;
			}
		}
		
		var ID_CARD_NO = "";
		if ($("#ID_CARD_NO").val() != undefined) {
			ID_CARD_NO = $("#ID_CARD_NO").val();
		}
		var CORP_BUSINESS_LICENSE = "";
		if ($("#CORP_BUSINESS_LICENSE").val() != undefined) {
				CORP_BUSINESS_LICENSE = $("#CORP_BUSINESS_LICENSE").val();
		}
			jQuery.ajax({
				url: "../customers/Customers!toJudgeIsCust.action?CUST_NAME=" +
				encodeURI($("#CUST_NAME").val()) +
				"&ID_CARD_NO=" +
				encodeURI(ID_CARD_NO) +
				"&CORP_BUSINESS_LICENSE=" +
				encodeURI(CORP_BUSINESS_LICENSE),
				type: 'post',
				dataType: "json",
				success: function(data){
					if (data.flag == false) {
						return alert("该客户已存在！！");
					}
					else {
						$("#doSaveCust").attr("disabled", true);
						jQuery.ajax({
							url: _basePath + '/customers/Customers!doAddCust.action',
							data: "param=" +
							getCustData('custSave'),
							dataType: "json",
							success: function(data){
								var CREDIT_ID = $("#CREDIT_ID").val();
								if (data.flag == false) {
									alert("保存失败");
									if(CREDIT_ID){
										var PROJECT_ID = $("#CREDIT_ID").val();
										winBack() ;
										//window.location.href=_basePath+"/credit/Credit!updateCreditPage.action?PROJECT_ID="+PROJECT_ID+"&CLIENT_ID="+CLIENT_ID+"&tab=update"+"&date="+new Date().getTime();
									}else{
										window.location.href = _basePath + '/customers/Customers!findCustomersPage.action';
									}
								}
								else {
									alert("保存成功");
									if(CREDIT_ID){
										$("#doSaveCust").attr("disabled",true);
										$("#doSaveCust").linkbutton("disable");
										winBack() ;
									}else{
										top.closeTab("添加客户明细");
										top.addTab("修改客户信息", _basePath
												+ "/customers/Customers!toUpdateCustInfoMain.action?CLIENT_ID="
												+ data.msg + "&TYPE=" + $("#TYPE").attr("selected", true).val() + "&tab=update"+"&date="+new Date().getTime());
									}
								}
							}
						});
					}
				}
			});
	} else {
		//alert("请填写必填项目");
	}
}



/**
 * 修改客户
 * @author yx
 * @date 2013-09-03
 * @return
 */
function updateFormNu(CLIENT_ID,ISZC_TYPE,PAGE_TYPE,project_id) {
	
 //alert(project_id);
//	$("#updateCust"+CLIENT_ID).find("#updateSaveNu").linkbutton("disable");
	isNotNullClass();
	if(ISZC_TYPE =='1'){
		jQuery.ajax({
			type:"post",
			url : _basePath + "/customers/Customers!toUpdateCustInfo.action",
			data : "param=" + getCustData('updateCust'+CLIENT_ID) + "&CLIENT_ID="
			+ CLIENT_ID+"&ISZC_TYPE=1&PAGE_TYPE="+PAGE_TYPE,
			dataType : "json",
			success : function(result) {
				if (result.flag == false) {
					
					alert("暂存失败");
				} else {
					alert("暂存成功！请尽快完善资料！");
				}
			}
		});
	}else{
		if (isNotNullSubmit()){
			jQuery.ajax({
				type:"post",
				url : _basePath + "/customers/Customers!toUpdateCustInfo.action",
				data : "param=" + getCustData('updateCust'+CLIENT_ID) +"&PROJECT_ID="+project_id+ "&CLIENT_ID="
				+ CLIENT_ID+"&ISZC_TYPE=2&PAGE_TYPE="+PAGE_TYPE,
				dataType : "json",
				success : function(result) {
					if (result.flag == false) {
						alert("保存失败");
					} else {
						alert("保存成功");
					}
				}
			});
		}else {
//			alert("请填写必填写必填选项！");
//			$("#updateCust"+CLIENT_ID).find("#updateSaveNu").linkbutton("enable");
		}
	}
	
}


/**
 * 修改客户
 * @author yx
 * @date 2013-09-03
 * @return
 */
function updateForm(CLIENT_ID,ISZC_TYPE,PAGE_TYPE) {
//	$("#updateCust"+CLIENT_ID).find("#updateSave").linkbutton("disable");
	if(!changeDataYz()){
		return false;
	}
	//验证组织机构代码证\税务登记证号
	if(!checkAnyToOragnization()){
		return false;
	}
	if(!checkAnyToTax()){
		return false;
	}
	
	//注册时间校验
	if(!dateComparison()){
		return false;
	}
	isNotNullClass();
	if(ISZC_TYPE == '1'){
		jQuery.ajax( {
			url : _basePath + '/customers/Customers!toUpdateCustInfo.action',
			data : "param=" + getCustData('updateCust'+CLIENT_ID) + "&CLIENT_ID="
					+ CLIENT_ID+"&ISZC_TYPE=1&PAGE_TYPE="+PAGE_TYPE,
			dataType : "json",
			success : function(result) {
				if (result.flag == false) {
					alert("暂存失败");
				} else {
					alert("暂存成功！请尽快完善资料！");
//					alert("修改客户成功");
				}
			}
		});
	}else{
		if (isNotNullSubmit()) {
//			$("#updateCust"+CLIENT_ID).find("#doSaveCust").attr("disabled",true);
			jQuery.ajax( {
				url : _basePath + '/customers/Customers!toUpdateCustInfo.action',
				data : "param=" + getCustData('updateCust'+CLIENT_ID) + "&CLIENT_ID="
						+ CLIENT_ID+"&ISZC_TYPE=2&PAGE_TYPE="+PAGE_TYPE,
				dataType : "json",
				success : function(result) {
					if (result.flag == false) {
						alert("保存失败");
					} else {
						alert("保存成功");
//						alert("修改客户成功");
					}
				}
			});
			
		}else {
			//alert("请填写必填写必填选项！");
//			$("#updateCust"+CLIENT_ID).find("#updateSave").linkbutton("enable");
		}
	}
	
}

/**
 * 判断配偶情况
 * 
 * @param val
 * @return
 */
function choustSpoust(val) {
	if (val == "1Marriage" || val == "4Marriage" || val == "4" || val == "1") {
		$("#marriage_type").attr("display", "");
		$("#marriage_type").attr("style", "diaplay:block");
	} else {
		$("#marriage_type").attr("style", "display:none");
	}
}

/**
 * 必填项校验 个人 杨雪 2013-08-30
 * @return
 */
function checking2() {
	
	
	var type = $("#TYPE").attr("selected", true).val();
	var flag = true;
	
		$(".warm").each(function() {
			var required = $(this).attr("requiredF")  ;
			var title =  $(this).attr("title") ;
			//必选项验证,parseInt(required)==2 这个不知道是啥规则,暂时去掉
			//if(parseInt(required)==2 && ($(this).val() == '' || $(this).val() == null)){
			if($(this).val() == '' || $(this).val() == null){
				alert("请输入"+title) ;
				$(this).focus();
				flag=false ;
				return false ;
			}
			
		});
		
		$(".warmlegalDate").each(function() {
			var required = $(this).attr("requiredF")  ;
			var title =  $(this).attr("title") ;
			//必选项验证,parseInt(required)==2 这个不知道是啥规则,暂时去掉
			//if (parseInt(required)==2 && ($(this).datebox('getValue') == '' || $(this).datebox('getValue') == null )) {
			if ($(this).datebox('getValue') == '' || $(this).datebox('getValue') == null ) {
				alert("请输入"+title) ;
				$(this).focus();
				flag=false ;
				return false ;
			}
			
		});
		
	
		$(".checkB").each(function() {
		
			
			var title = $(this).attr("title")  ;
			var required = $(this).attr("requiredF")  ;
			name= $(this).attr("name")  ;
			
			var test=$("input[name='"+name+"'][checked]").length;
			var checkLength = $('input[type="checkbox"][name="'+name+'"]:checked').length ;
			//debugger ;
			//alert(test);
			//alert(checkLength+"name: " + name ); 
			//var checked =  $(this).prop("checked");
			if (parseInt(required)==2 && checkLength<=0){
				alert("请至少选中一项"+title) ;
				flag = false ;
				return false ;
			}

		});
		//配偶信息必填项校验
		if(!checkingMarry()){
			flag=false;
			return flag;
		}

	return  flag ;
}

/**
 * 必填项校验 个人 杨雪 2013-08-30
 * @return
 */
function checking() {
	
	
	var type = $("#TYPE").attr("selected", true).val();
	var flag = true;
	if (type == "NP") {
		$(".warm").each(function() {
			if (($(this).val() == '' || $(this).val() == null)) {
				$(this).addClass("red");
				flag = false;
			} else {
				$(this).removeClass("red");
			}
		});
		
		$(".warmlegalDate").each(function() {
			if ($(this).datebox('getValue') == '' || $(this).datebox('getValue') == null ) {
				$(this).addClass("red");
				flag = false;
			}else {
				$(this).removeClass("red");
			}
			
		});
	} else {
		$(".warmlegal").each(function() {
			if ($(this).val() == '' || $(this).val() == null ) {
				$(this).addClass("red");
				flag = false;
			} else {
				$(this).removeClass("red");
			}
		});
		$(".warmlegalDate").each(function() {
			if ($(this).datebox('getValue') == '' || $(this).datebox('getValue') == null ) {
				$(this).addClass("red");
				flag = false;
			}else {
				$(this).removeClass("red");
			}
			
		});
	}
	
	return  flag ;
}

/**
 * 获取页面参数
 * @author 杨雪
 * @date 2013-09-03
 * @param save
 * @return
 */
function getCustData(save) {
	var getCustData = new Array();
	var TYPE = $("#"+save).find("select[name=TYPE]").attr("selected","selected").val();
	var IS_MARRY = $("#"+save).find("select[name=IS_MARRY]").attr("selected","selected").val();
	var INDUSTRY_FICATION = $("#"+save).find("select[name=INDUSTRY_FICATION]").attr("selected","selected").val();
	
	$("#"+save).each(function(){
	
		var temp = {};
		temp.CUST_NAME = $(this).find($("input[name=CUST_NAME]")).val();
		temp.CUST_TYPE = $(this).find($("input[name=CUST_TYPE]")).val();
		temp.CREDIT_ID = $(this).find($("input[name=CREDIT_ID]")).val();
		temp.MAIL_ADDRESS=$(this).find($("input[name=MAIL_ADDRESS]")).val();
		temp.TYPE = TYPE;
		temp.INDUSTRY_FICATION = INDUSTRY_FICATION ;
		if(TYPE == "NP"){
			var xqah_list = [];
			var xg_list = [];
			$("[name = XQAH]:checkbox").each(function(){
				if($(this).is(":checked")){
					var temp1={};
					temp1.CODE=$(this).attr("value");
					temp1.TYPE='XQAH';
					xqah_list.push(temp1);
				}
			});
			$("[name = XG]:checkbox").each(function(){
				if($(this).is(":checked")){
					var temp2={};
					temp2.CODE=$(this).attr("value");
					temp2.TYPE='XG';
					xg_list.push(temp2);
				}
			});
		temp.xqah_list = xqah_list;
		temp.xg_list = xg_list;
		temp.ID_CARD_TYPE = $(this).find($("select[name=ID_CARD_TYPE]")).attr("selected",true).val();
		var ID_CARD_TYPECO= $(this).find($("select[name=ID_CARD_TYPE]")).attr("selected",true).val()
		if(ID_CARD_TYPECO =='1' || ID_CARD_TYPECO =='4'){
			temp.ID_CARD_NO = $(this).find($("input[name=ID_CARD_NO]")).val().toUpperCase();
		}else{
			temp.ID_CARD_NO = $(this).find($("input[name=ID_CARD_NO]")).val();
		}
		
		temp.BIRTHDAY = $(this).find($("input[name=BIRTHDAY]")).val();
		temp.TEL_PHONE = $(this).find($("input[name=TEL_PHONE]")).val();
		temp.POST = $(this).find($("input[name=POST]")).val();
		temp.PHONE = $(this).find($("input[name=PHONE]")).val();
		temp.SEX = $(this).find($("select[name=SEX]")).attr("selected",true).val();
		temp.AGE = $(this).find($("input[name=AGE]")).val();
		temp.DEGREE_EDU = $(this).find($("select[name=DEGREE_EDU]")).attr("selected",true).val();
		temp.IS_MARRY = $(this).find($("select[name=IS_MARRY]")).attr("selected",true).val();
		temp.NATION = $(this).find($("select[name=NATION]")).attr("selected",true).val();
		temp.IS_CHILDRED = $(this).find($("select[name=IS_CHILDRED]")).attr("selected",true).val();
		temp.PROVINCE = $(this).find($("select[name=PROVINCE]")).attr("selected",true).val();
		
		temp.SCALE_SOURCE = $(this).find($("select[name=SCALE_SOURCE]")).attr("selected",true).val();
		
		temp.CUST_DW_DH=$(this).find($("input[name=CUST_DW_DH]")).val();
		temp.CUST_PJ_SY=$(this).find($("input[name=CUST_PJ_SY]")).val();
		temp.CUST_RELA=$(this).find($("select[name=CUST_RELA]")).attr("selected",true).val();
		
		//增加涉及到地址的数据都追加省市  start
		
		temp.COMPANY_ADDR_PROVINCE = $(this).find($("select[name=COMPANY_ADDR_PROVINCE]")).attr("selected",true).val();
		temp.COMPANY_ADDR_CITY = $(this).find($("select[name=COMPANY_ADDR_CITY]")).attr("selected",true).val();
		temp.COMPANY_ADDR_COUNTY = $(this).find($("select[name=COMPANY_ADDR_COUNTY]")).attr("selected",true).val();
		temp.HOUSE_ADDRESS_PROVINCE = $(this).find($("select[name=HOUSE_ADDRESS_PROVINCE]")).attr("selected",true).val();
		temp.HOUSE_ADDRESS_CITY = $(this).find($("select[name=HOUSE_ADDRESS_CITY]")).attr("selected",true).val();
		temp.HOUSE_ADDRESS_COUNTY = $(this).find($("select[name=HOUSE_ADDRESS_COUNTY]")).attr("selected",true).val();
		temp.MAIL_ADDRESS_PROVINCE = $(this).find($("select[name=MAIL_ADDRESS_PROVINCE]")).attr("selected",true).val();
		temp.MAIL_ADDRESS_CITY = $(this).find($("select[name=MAIL_ADDRESS_CITY]")).attr("selected",true).val();
		temp.MAIL_ADDRESS_COUNTY = $(this).find($("select[name=MAIL_ADDRESS_COUNTY]")).attr("selected",true).val();	
		
		
		
		//增加涉及到地址的数据都追加省市  end
		
		var c = $(this).find($("select[name=PROJECT_CITY]")).attr("selected",true).val();
		if(c==null||c==undefined || c=="null") c="" ;
		temp.CITY = c ;
		temp.WORK_UNIT = $(this).find($("input[name=WORK_UNIT]")).val();
		temp.COMPANY_PROPERTY = $(this).find($("select[name=COMPANY_PROPERTY]")).attr("selected",true).val();
		temp.ENTRY_TIME = $(this).find($("input[name=ENTRY_TIME]")).attr("selected",true).val();
		temp.POSITION = $(this).find($("input[name=POSITION]")).val();
		temp.FAX = $(this).find($("input[name=FAX]")).val();
		temp.COMPANY_ADDR = $(this).find($("input[name=COMPANY_ADDR]")).val();
		temp.HOUSE_ADDRESS = $(this).find($("input[name=HOUSE_ADDRESS]")).val();
		temp.HOUSR_RE_ADDRESS = $(this).find($("input[name=HOUSR_RE_ADDRESS]")).val();
		temp.TAX_QUALIFICATION = $(this).find($("select[name=TAX_QUALIFICATION]")).attr("selected",true).val();
		temp.REMARK = $(this).find($("textarea[name=REMARK]")).val();
		temp.overMethod = $(this).find($("input[name=overMethod]")).val();
		try{
			temp.CUST_STATUS=$(this).find($("input[name=CUST_STATUS]")).val();
		}catch(Exception){}
		
		temp.USER_YEAR = $(this).find($("input[name=USER_YEAR]")).val();
		temp.PHYSICAL_STATE = $(this).find($("select[name=PHYSICAL_STATE]")).attr("selected",true).val();
		
		if(IS_MARRY == "1Marriage" || IS_MARRY == "4Marriage" || IS_MARRY == "1" || IS_MARRY == "4"){
			temp.SPOUST_NAME = $(this).find($("input[name=SPOUST_NAME]")).val();
			temp.SPOUST_SEX = $(this).find($("select[name=SPOUST_SEX]")).attr("selected",true).val();
			temp.SPOUST_BIRTHDAY = $(this).find($("input[name=SPOUST_BIRTHDAY]")).val();
			temp.SPOUST_NATION = $(this).find($("select[name=SPOUST_NATION]")).val();
			temp.SPOUDT_ID_CARD_NO = $(this).find($("input[name=SPOUDT_ID_CARD_NO]")).val().toUpperCase();
			temp.SPOUDT_TEL_PHONE = $(this).find($("input[name=SPOUDT_TEL_PHONE]")).val();
			temp.SPOUST_HOUSR_RE_ADDRESS = $(this).find($("input[name=SPOUST_HOUSR_RE_ADDRESS]")).val();
			temp.SPOUST_WORK_UNIT = $(this).find($("input[name=SPOUST_WORK_UNIT]")).val();
			temp.SPOUST_COMPANY_PROPERTY = $(this).find($("select[name=SPOUST_COMPANY_PROPERTY]")).attr("selected",true).val();
			temp.SPOUST_POSITION = $(this).find($("input[name=SPOUST_POSITION]")).val();
			temp.SPOUST_WORK_PHONE = $(this).find($("input[name=SPOUST_WORK_PHONE]")).val();
			temp.SPOUST_FAX = $(this).find($("input[name=SPOUST_FAX]")).val();
			temp.SPOUST_COMPANY_ADDR = $(this).find($("input[name=SPOUST_COMPANY_ADDR]")).val();
			temp.SPOUST_MAILING_ADDR = $(this).find($("input[name=SPOUST_MAILING_ADDR]")).val();
			temp.SPOUST_DEGREE_EDU = $(this).find($("select[name=SPOUST_DEGREE_EDU]")).attr("selected",true).val();		
			temp.SPOUST_PHYSICAL_STATE = $(this).find($("select[name=SPOUST_PHYSICAL_STATE]")).attr("selected",true).val();	
	
			temp.SPOUST_COMPANY_ADDR_PROVINCE = $(this).find($("select[name=SPOUST_COMPANY_ADDR_PROVINCE]")).attr("selected",true).val();	
			temp.SPOUST_COMPANY_ADDR_CITY = $(this).find($("select[name=SPOUST_COMPANY_ADDR_CITY]")).attr("selected",true).val();	
			temp.SPOUST_COMPANY_ADDR_COUNTY = $(this).find($("select[name=SPOUST_COMPANY_ADDR_COUNTY]")).attr("selected",true).val();	
		}
		
		}else{
			temp.BUSINESS_TYPE = $(this).find($("select[name=BUSINESS_TYPE]")).attr("selected",true).val();
			temp.CORP_BUSINESS_LICENSE = $(this).find($("input[name=CORP_BUSINESS_LICENSE]")).val();
			temp.TAX_CODE = $(this).find($("input[name=TAX_CODE]")).val();
			temp.ORAGNIZATION_CODE = $(this).find($("input[name=ORAGNIZATION_CODE]")).val();
			temp.SETUP_DATE = $(this).find($("input[name=SETUP_DATE]")).val();
			temp.PERIOD_OF_VALIDITY = $(this).find($("input[name=PERIOD_OF_VALIDITY]")).val();
			temp.MAIL_ADDRESS=$(this).find($("input[name=MAIL_ADDRESS]")).val();
			temp.REGISTE_DATE = $(this).find($("input[name=REGISTE_DATE]")).val();
			temp.REGISTE_CAPITAL = $(this).find($("input[name=REGISTE_CAPITAL]")).val();
			temp.POST = $(this).find($("input[name=POST]")).val();
			temp.REGISTE_PHONE = $(this).find($("input[name=REGISTE_PHONE]")).val();
			temp.FAX = $(this).find($("input[name=FAX]")).val();
			temp.NUMBER_PER = $(this).find($("input[name=NUMBER_PER]")).val();
			temp.TAX_QUALIFICATION = $(this).find($("select[name=TAX_QUALIFICATION]")).attr("selected",true).val();
			temp.RATEPAYING = $(this).find($("select[name=RATEPAYING]")).attr("selected",true).val();
			temp.REGISTE_ADDRESS = $(this).find($("input[name=REGISTE_ADDRESS]")).val();
			temp.WORK_ADDRESS = $(this).find($("input[name=WORK_ADDRESS]")).val();
			temp.IS_GUARANTEE = $(this).find($("select[name=IS_GUARANTEE]")).attr("selected",true).val();
			temp.PROVINCE = $(this).find($("select[name=PROVINCE]")).attr("selected",true).val();
			var c = $(this).find($("select[name=PROJECT_CITY]")).attr("selected",true).val();
			if(c==null||c==undefined || c=="null") c="" ;
			temp.CITY = c ;
			temp.CUST_STATUS=$(this).find($("input[name=CUST_STATUS]")).val();
			temp.MAIN_BUSINESS = $(this).find($("input[name=MAIN_BUSINESS]")).val();
			temp.LEGAL_PERSON = $(this).find($("input[name=LEGAL_PERSON]")).val();
			var corporate =$(this).find($("input[name=ID_CARD_CORPORATE_NO]")).val();
			if(corporate==null || corporate==undefined ||corporate=="null"){
				corporate="";
			}
			temp.ID_CARD_CORPORATE_NO = corporate.toUpperCase();
			temp.LEGAL_PERSON_PHONE = $(this).find($("input[name=LEGAL_PERSON_PHONE]")).val();
			temp.REMARK = $(this).find($("textarea[name=REMARK]")).val();
			temp.SCALE_SOURCE = $(this).find($("select[name=SCALE_SOURCE]")).attr("selected",true).val();
			temp.SCALE_ENTERPRISE = $(this).find($("select[name=SCALE_ENTERPRISE]")).attr("selected",true).val();
			temp.INDUSTRY_FICATION = $(this).find($("select[name=INDUSTRY_FICATION]")).attr("selected",true).val();
			temp.RC_CURRENCY_TYPE = $(this).find($("select[name=RC_CURRENCY_TYPE]")).attr("selected",true).val() ;
			
			temp.REGISTE_ADDRESS_PROVINCE= $(this).find($("select[name=REGISTE_ADDRESS_PROVINCE]")).attr("selected",true).val();	
			temp.REGISTE_ADDRESS_CITY= $(this).find($("select[name=REGISTE_ADDRESS_CITY]")).attr("selected",true).val();	
			temp.REGISTE_ADDRESS_COUNTY= $(this).find($("select[name=REGISTE_ADDRESS_COUNTY]")).attr("selected",true).val();	
			temp.WORK_ADDRESS_PROVINCE= $(this).find($("select[name=WORK_ADDRESS_PROVINCE]")).attr("selected",true).val();	
			temp.WORK_ADDRESS_CITY= $(this).find($("select[name=WORK_ADDRESS_CITY]")).attr("selected",true).val();	
			temp.WORK_ADDRESS_COUNTY= $(this).find($("select[name=WORK_ADDRESS_COUNTY]")).attr("selected",true).val();	
			
			temp.CUST_RELA=$(this).find($("select[name=CUST_RELA]")).attr("selected",true).val();
		}
		//备注说明
		temp.GUARANTEE_CAPACITY = $(this).find($("textarea[name=GUARANTEE_CAPACITY]")).val();
		temp.RELATION_EXPLAIN = $(this).find($("textarea[name=RELATION_EXPLAIN]")).val();
		
		//自定义start
		var zdy_array = {} ;
		$("#"+save).find(".ZDY_INFO").find("[name^='ZDY_']").each(function(i){
			var name = $(this).attr("name") ;
			var s = name.split("ZDY_")[1] ;
			zdy_array[s]= $(this).val();
//			alert("name:"+s[1] +" value: "+ $(this).val()) ;
		}); 
		$("#"+save).find(".ZDY_INFO").find("[comboname^='ZDY_']").each(function(i){//easyui-datebox日期框获取值
			var name = $(this).attr("comboname") ;
			var s = name.split("ZDY_")[1] ;
			zdy_array[s]= $("[comboname="+name+"]").datebox('getValue');
//			alert("comboname:"+name +" value: "+ zdy_array[s]) ;
		}); 
		
		//alert($("input[name^='ZDY_']"));
		zdy_array.SUN_TABLE_KEY_PAGE = $(this).find($("input[name=SUN_TABLE_KEY_PAGE]")).val();
		zdy_array.SUN_FIELD_KEY_PAGE = $(this).find($("input[name=SUN_FIELD_KEY_PAGE]")).val();
		zdy_array.SUN_FIELD_VALUE_PAGE = $(this).find($("input[name=SUN_FIELD_VALUE_PAGE]")).val();
			
		temp.ZDY_INFO = zdy_array;
		//自定义end
		
		//紧急联系人 start
		var emergencyInfo = new Array() ;
		
		var i = 1 ;
		$("#"+save).find("input[name^='EMERGENCY_NAME']").each(function(){
			var t = {}  ;
			
			var EMERGENCY_NAME = $(this).attr("name").split("_"+i)[0] ;
			t.EMERGENCY_NAME = $(this).val();
			var EMERGENCY_TYPE = $("#"+save).find("select[name='EMERGENCY_TYPE_"+i+"']").find("option:selected").val();
			t.EMERGENCY_TYPE = EMERGENCY_TYPE ;
			var EMERGENCY_PHONE = $("#"+save).find("input[name='EMERGENCY_PHONE_"+i+"']").val();
			t.EMERGENCY_PHONE = EMERGENCY_PHONE ;
			var EMERGENCY_ADDR = $("#"+save).find("input[name='EMERGENCY_ADDR_"+i+"']").val();
			t.EMERGENCY_ADDR = EMERGENCY_ADDR ;
			var EMERGENCY_UNIT = $("#"+save).find("input[name='EMERGENCY_UNIT_"+i+"']").val();
			t.EMERGENCY_UNIT = EMERGENCY_UNIT ;
			var EMERGENCY_RELATIONSHIP = $("#"+save).find("select[name='EMERGENCY_RELATIONSHIP_"+i+"']").find("option:selected").val();
			t.EMERGENCY_RELATIONSHIP = EMERGENCY_RELATIONSHIP ;
			
			var ADDR_PROVINCE = $("#"+save).find("select[name='ADDR_PROVINCE_"+i+"']").find("option:selected").val();
			t.ADDR_PROVINCE = ADDR_PROVINCE ;
			
			var ADDR_CITY = $("#"+save).find("select[name='ADDR_CITY_"+i+"']").find("option:selected").val();
			t.ADDR_CITY = ADDR_CITY ;
			
			var ADDR_COUNTY = $("#"+save).find("select[name='ADDR_COUNTY_"+i+"']").find("option:selected").val();
			t.ADDR_COUNTY = ADDR_COUNTY ;
			
			emergencyInfo.push(t) ;
			i++ ;	
		}) ;
	
		
		//紧急联系人 end
		temp.EMERGENCY_INFO=emergencyInfo ;
		getCustData.push(temp);
		
	});
	return encodeURI(JSON.stringify(getCustData));
}

/**********************************************客户开户行*************************************************************************/
function toAddBank(){
	/*
	 * /获取浏览器显示区域的高度   
		$(window).height();   
		//获取浏览器显示区域的宽度   
		$(window).width();   
		  
		//获取页面的文档高度   
		$(document.body).height();   
		//获取页面的文档宽度   
		$(document.body).width();   
		  
		//获取滚动条到顶部的垂直高度   
		$(document).scrollTop();   
		//获取滚动条到左边的垂直宽度   
		$(document).scrollLeft();   
	 */
	
	$("#bankAdd").dialog('open',{top:500,left:200});
	$("#bankAdd").panel("move",{top:$(document).scrollTop()*0.7 + ($(window).height()-250) * 0.5});  
	$(".window-mask").css({ height: $(document).height()}); 
	
//	$("#doAddBank").form('clear');
//	$("input[name='BANK_CUSTNAME']").val($("#CUST_NAME").val());
	$("input[name='BANK_ACCOUNT']").val("");
//	$("select[name='BANK_NAME']").attr("selected","");
	$("input[name='HEAD_OFFICE']").val("");
	//$("#BNAK_CLIENT_ID").val($("#client_id").val());
	$("#PROJECT_ID_ADD").val($("#PROJECT_ID_BANK").val());
}
//
//function toSaveBank() {
//	jQuery.ajax({
//		type:"post",
//		url:_basePath+'/customers/Customers!isAccountHave.action',
//		data: "CLIENT_ID="+$("#BNAK_CLIENT_ID").val()+"&BANK_NAME="+$("#BANK_NAME_1").combobox('getValue')+"&BANK_ACCOUNT="+$("#BANK_ACCOUNT_1").val(),
//		dataType:"json",
//		success:function(json){
//			if(json.flag==true){
//				toSaveBank_();
//			}else {
//				alert("该银行帐号已存在，请重新填写");
//			}
//		}
//	});
//		
//}

function toSaveBank(){
	var BANK_CUSTNAME=jQuery.trim($("#doAddBank").find("input[name='BANK_CUSTNAME']").val());
	if(BANK_CUSTNAME == '' || BANK_CUSTNAME == null){
		alert("请输入持卡人！");
		return false;
	}
	
	var BANK_ACCOUNT=jQuery.trim($("#doAddBank").find("input[name='BANK_ACCOUNT']").val());
	if(BANK_ACCOUNT == '' || BANK_ACCOUNT == null){
		alert("请输入银行帐号！");
		return false;
	}
	
	$("#doAddBank").form('submit',{
		url:_basePath+'/customers/Customers!doAddBank.action',
		success : function(data) {
			if (data.flag == false) {
				alert("银行信息添加失败");
				//关闭弹出框
				$("#bankAdd").dialog('close');
				//开户行信息刷新
				$('#bankOpen').datagrid('load', {
					"param" : getFromData("#bank")
				});
			} else {
				alert("银行信息添加成功");
				//关闭弹出框
				$("#bankAdd").dialog('close');
				//开户行信息刷新
				$('#bankOpen').datagrid('load', {
					"param" : getFromData("#bank")
				});
			}
		}
	});
}

function toUpdateBank(){
	var row = $("#bankOpen").datagrid('getSelected');
	if(row){
		$.ajax({
			url:_basePath + "/customers/Customers!findBankDetailByid.action?CO_ID="+row.CO_ID+"&date="+new Date().getTime(),
			type:"post",
			dataType:"json",
			success:function(json){
				var json = eval(json.bank);
				for(var i=0; i<json.length; i++){
					$("#BANK_NAME1").val(json[i].BANK_NAME);
//					$("#BANK_NAME1").combobox('select',json[i].BANK_NAME);
					$("#BANK_ACCOUNT1").val(json[i].BANK_ACCOUNT);
					$("#BANK_ADDRESS1").val(json[i].BANK_ADDRESS);
					$("#BANK_CUSTNAME1").val(json[i].BANK_CUSTNAME);
					$("#HEAD_BRANCH1").val(json[i].BRANCH);
					$("#HEAD_PROVINCE1").val(json[i].PROVINCE_ID);
					if(json[i].CITY_ID==undefined||json[i].CITY_NAME==undefined){
						$("#HEAD_CITY1").append("<option value=''>--请选择--</option>");
					}else{
						$("#HEAD_CITY1").append("<option value='"+json[i].CITY_ID+"'>"+json[i].CITY_NAME+"</option>");
					}	 
					$("#HEAD_OFFICE1").val(json[i].HEAD_OFFICE);
					$("#FLAG1").combobox('select',json[i].FLAG);
					$("#REMARK2").val(json[i].REMARK);
					$("#CO_ID").val(json[i].CO_ID);
				}
			}
		});
		$("#bankUpdate").dialog('open');
		$("#bankUpdate").panel("move",{top:$(document).scrollTop()*0.7 + ($(window).height()-250) * 0.5});  
		
	}else{
		alert("请选择要修改的银行信息");
	}
}

function toSaveUpdateBank(){
	var PROJECT_ID=$("#PROJECT_ID").val();
	var BANK_CUSTNAME=jQuery.trim($("#doUpdateBank").find("input[name='BANK_CUSTNAME']").val());
	if(BANK_CUSTNAME == '' || BANK_CUSTNAME == null){
		alert("请输入持卡人！");
		return false;
	}
	
	var BANK_ACCOUNT=jQuery.trim($("#doUpdateBank").find("input[name='BANK_ACCOUNT']").val());
	if(BANK_ACCOUNT == '' || BANK_ACCOUNT == null){
		alert("请输入银行帐号！");
		return false;
	}
	$("#doUpdateBank").form('submit',{
		url:_basePath+'/customers/Customers!doUpdageLeagBank.action?PROJECT_ID='+PROJECT_ID,
		success : function(data) {
			var json=$.parseJSON(data);
			if (json.flag == false) {
//				alert("客户开户行修改失败");
				$.messager.alert("提示",json.msg);
				//关闭弹出框
				$("#bankUpdate").dialog('close');
				//开户行信息刷新
				$('#bankOpen').datagrid('load', {
					"param" : getFromData("#bank")
				});
			} else {
//				alert("客户开户行修改成功");
				$.messager.alert("提示",json.msg);
				//关闭弹出框
				$("#bankUpdate").dialog('close');
				//开户行信息刷新
				$('#bankOpen').datagrid('load', {
					"param" : getFromData("#bank")
				});
			}
		}
	});
}


/**
 * 删除开户行
 * @return
 */
function removeit() {
	var PROJECT_ID=$("#PROJECT_ID").val();
	if (confirm("确认删除？")) {
		var row = $("#bankOpen").datagrid('getSelected');
		if (row) {
			jQuery.ajax( {
				url : _basePath + "/customers/Customers!delOpenBank.action",
				data : "CO_ID=" + row.CO_ID + "&CLIENT_ID=" + row.CLIENT_ID+"&PROJECT_ID="+PROJECT_ID,
				dataType : "json",
				success : function(data) {
					$.messager.alert("提示",data.msg);

					$('#bankOpen').datagrid('load', {
						"param" : getFromData("#bank")
					});
				},
				error : function(e) {
					alert(e.message);
				}
			});
		}else{
			alert("请选择要删除的银行信息");
		}
	}
}


/**
 * 启用开户行
 * @return
 */
function qyBankit() {
	
		var row = $("#bankOpen").datagrid('getSelected');
		var PROJECT_ID=$("#PROJECT_ID_BANK").val();
		if (row) {
			if (confirm("确认将选中的银行设为代扣卡？")) {
				jQuery.ajax( {
					url : _basePath + "/customers/Customers!updateBankProject.action",
					data : "CO_ID=" + row.CO_ID + "&PROJECT_ID=" + PROJECT_ID,
					dataType : "json",
					success : function(data) {
						if (data.flag == true) {
							alert("项目银行账户设定代扣成功");
	
							$('#bankOpen').datagrid('load', {
								"param" : getFromData("#bank")
							});
						} else {
							alert("项目银行账户设定代扣失败");
	
							$('#bankOpen').datagrid('load', {
								"param" : getFromData("#bank")
							});
						}
					},
					error : function(e) {
						alert(e.message);
					}
				});
			}
		}else{
			alert("请选择要代扣的银行信息");
		}
	
}


function closeDailog(div){
	$("#"+div).dialog('close');
}


//从厂商供应商中同步客户信息
function syncCustInfo(ORAGNIZATION_CODE){
	var ORAGNIZATION_CODE = $("#ORAGNIZATION_CODE").val() ;	
	jQuery.ajax( {
		url : _basePath + "/customers/Customers!syncCustInfo.action",
		data :{
			ORAGNIZATION_CODE:ORAGNIZATION_CODE 
		},
		dataType : "json",
		success : function(data) {
			var flag = data.data ;
			if(flag){
				$.messager.confirm('提示信息','在厂商或供应商中存在该法人信息，是否进行同步操作?',function(r){
				    if (r){
				        var result = data.data ;
				        
				        //取到JSON对象的key值
				        for(var key in result){  
			                //alert(key);  
			                syncInput(key,result[key]) ;
			                syncEasyUiDate(key,result[key]) ;    
			            }  
				        
				    }
				});
			}
		},
		error : function(e) {
			alert(e.message);
		}
	});
	
}

function syncInput(name,val){
	$(".warm").each(function() {
		var name1 = $(this).attr("name") ;
		if(name1==name){
			$(this).val(val) ;
		}	
	});
}

function syncEasyUiDate(name,val){
	$(".warmlegalDate").each(function() {
		var name1 = $(this).attr("name")  ;
		if(name1==name){
			$(this).datebox('setValue', val);	
		}	
		
	});
}

function checkBankAccount(bank_account){
	jQuery.ajax({
		url: _basePath+'/customers/Customers!checkBank.action',
		data: 'BANK_ACCOUNT='+bank_account,
		dataType: 'json',
		success : function (data){
			if(data.flag==false){
				alert('银行账号重复！');
				$('#BANK_ACCOUNT').val('');
				$('#BANK_ACCOUNT1').val('');
			}
		}
	});
}



/**********************************************客户法人财报*************************************************************************/

function saveFinance(){
	if($("#FINANCE_ID").val()==""){
		var url  = _basePath+"/customers/custFinance!toAddFinance.action";
		$("#saveFinance").attr("action",url);
		$("#saveFinance").form("submit",{
			dataType:"json",
			success:function(json){
				json = jQuery.parseJSON(json);
				if(json.flag == true){
					$.messager.alert("提示", json.msg);
					window.location.href = _basePath+ "/customers/Customers!toUpdateCustInfoMain.action?CLIENT_ID="+ $("#CLIENT_ID").val() + "&TYPE=" + $("#TYPE").attr("selected",true).val() + "&tab=update";
				}else {
					$.messager.alert("提示", json.msg);
				}
			}
		});
	}else {
		var url  = _basePath+"/customers/custFinance!toUpdateFinance.action";
		$("#saveFinance").attr("action",url);
		$("#saveFinance").form("submit",{
			dataType:"json",
			success:function(json){
				json = jQuery.parseJSON(json);
				if(json.flag == true){
					$.messager.alert("提示", json.msg);
					window.location.href = _basePath+ "/customers/Customers!toUpdateCustInfoMain.action?CLIENT_ID="+ $("#CLIENT_ID").val() + "&TYPE=" + $("#TYPE").attr("selected",true).val() + "&tab=update";
				}else {
					$.messager.alert("提示", json.msg);
				}
			}
		});
	}
}
// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


//追加紧急联系人
function appendEmergencyContact(){
	var QUYUOPTION=$("#QUYUOPTION").val();
	var contact_type =$("#CONTACT_TYPE").val();
	var relationship =$("#RELATIONSHIP").val();
	var  emergencyTitle= $("#emergencyTitle") ;
	var rowspan = emergencyTitle.attr("rowCounts") ;
	var nextRowSpan = parseInt(rowspan)+5 ;
	var emergencyEndTr= $(".tr"+parseInt(rowspan)/5) ;
	if(emergencyEndTr!=null && emergencyEndTr!=undefined){
		emergencyTitle.attr("rowCounts",nextRowSpan) ;
		var emergencyEndTrMax = emergencyEndTr.get(emergencyEndTr.length-1) ;
		var next = parseInt(nextRowSpan)/5 ;
		var removeSpan = next-1;
		$("#span"+removeSpan).remove() ;
		//onchange="isMobilephone('EMERGENCY_PHONE_2')" 
		
		var appendTr = "<tr class='tr"+next+"'>"+
					"<td  rowspan='5'>联系人<span id='span"+next+"'>(<a href='javascript:void(0)' onclick='removeEmergencyContact("+next+")'>移除</a>)</span></td>"+
				 "</tr>"+
				 "<tr class='tr"+next+"'>"+
				 	"<td >联系人类型:</td>"+
				 	"<td >"+
				 	"<select name='EMERGENCY_TYPE_"+next+"' class='warm' id='EMERGENCY_TYPE_"+next+"' style='width:135px;' onchange='' title='联系人类型'><option value=''>--请选择--</option> "+contact_type+"	</select>" +
				 	"</td>"+
				 	"<td >姓名:</td>"+
				 	"<td >"+
				 		"<input type='text' id='EMERGENCY_NAME_"+next+"' class='warm' title='联系人姓名' style='width:135px;' name='EMERGENCY_NAME_"+next+"' onkeyup='noNumber(this)'/>"+
				 	"</td>"+
				 	"</tr>"+
				 	"<tr class='tr"+ next +"'>"+
				 	"<td >手机号码:</td>"+
				 	"<td >"+
				 		"<input type='text' id='EMERGENCY_PHONE_"+next+"' title='联系人手机号码' style='width:135px;' name='EMERGENCY_PHONE_"+next+"' class='warm validate[required,custom[phone]]' onchange='isMobilephone(\"EMERGENCY_PHONE_"+next+"\")'/>"+
				 	"</td>"+
				 "<td >与申请人关系:</td>	"+
				 	"<td >"+
//				 		"<input type='text' class='warm' id='EMERGENCY_RELATIONSHIP_"+next+"' title='联系人与申请人关系' style='width:135px;' name='EMERGENCY_RELATIONSHIP_"+next+"'/>"+
				 		"<select name='EMERGENCY_RELATIONSHIP_"+next+"' class='warm' id='EMERGENCY_RELATIONSHIP_"+next+"' style='width:135px;' title='与申请人关系'><option value=''>--请选择--</option> "+relationship+"	</select>" +
				 	"</td>"+
				 	"</tr>"+
				 	"<tr class='tr"+ next +"'>"+
				 	"<td >单位名称:</td>"+
				 	"<td >"+
				 	"<input type='text' id='EMERGENCY_UNIT_"+next+"' title='单位名称' style='width:135px;' name='EMERGENCY_UNIT_"+next+"' class='' />"+
				 	"</td>"+
				 	"<td ></td>	"+
				 	"<td >"+
				 	"</td>"+
				 	"</tr><tr class='tr"+ next +"'>"+
				 	"<td >现居住地址:</td><td >" +
				 	"<select name='ADDR_PROVINCE_"+next+"'  id='ADDR_PROVINCE_"+next+"' style='width:100px;' onchange='getChildArea2(this,this.value,\"ADDR_CITY_"+next+"\",\"ADDR_COUNTY_"+next+"\")''><option value=''>--请选择--</option>	"+QUYUOPTION+"</select>" +

			"<select name='ADDR_CITY_"+next+"'  id='ADDR_CITY_"+next+"' style='width:100px;'  onchange='getChildArea(this,this.value,\"ADDR_COUNTY_"+next+"\")'' ><option value=''>--请选择--</option>	</select>"+
			
			"<select name='ADDR_COUNTY_"+next+"' class='' id='ADDR_COUNTY_"+next+"' style='width:100px;' title='区/县(联系人现居住地址)' ><option value=''>--请选择--</option>	</select>"+
				
				 	
				 	"</td>"+
				 "<td colspan='2'><input type='text' id='EMERGENCY_ADDR_"+next+"'  style='width:415px;' name='EMERGENCY_ADDR_"+next+"'/></td></tr>" ;			
		$(emergencyEndTrMax).after(appendTr) ;	
		
	}
		
		
}

//移除紧急联系人
function removeEmergencyContact(next){
	var QUYUOPTION=$("#QUYUOPTION").val();
	$(".tr"+next).remove() ;
	var  emergencyTitle= $("#emergencyTitle") ;
	var rowspan = emergencyTitle.attr("rowCounts") ;
	var preRowSpan = parseInt(rowspan)-5 ;
	emergencyTitle.attr("rowCounts",preRowSpan) ;
	var pre = preRowSpan/5 ;
	
	var EMERGENCY_NAME = $("input[name='EMERGENCY_NAME_"+pre+"']").val();
	
	var EMERGENCY_PHONE = $("input[name='EMERGENCY_PHONE_"+pre+"']").val();
	
	var EMERGENCY_ADDR = $("input[name='EMERGENCY_ADDR_"+pre+"']").val();
	
	var EMERGENCY_RELATIONSHIP = $("input[name='EMERGENCY_RELATIONSHIP_"+pre+"']").val();
	
	var appendTr = "<tr class='tr"+pre+"'>"+
					"<td  rowspan='4'>紧急联系人<span id='span"+pre+"'>(<a href='javascript:void(0)' onclick='removeEmergencyContact("+pre+")'>移除</a>)</span></td>"+
				 "</tr>"+
				 "<tr class='tr"+pre+"'>"+
				 	"<td >姓名:</td>"+
				 	"<td >"+
				 		"<input type='text' id='EMERGENCY_NAME_"+pre+"' class='warm' title='联系人姓名' style='width:135px;' name='EMERGENCY_NAME_"+pre+"' onkeyup='noNumber(this)'/>"+
				 	"</td>"+
				 	"<td >移动电话:</td>"+
				 	"<td >"+
				 		"<input type='text' id='EMERGENCY_PHONE_"+pre+"' style='width:135px;' title='联系人移动电话' name='EMERGENCY_PHONE_"+pre+"' class='warm validate[required,custom[phone]]'/>"+
				 	"</td>"+
				 "</tr>"+
				 "<tr class='tr"+ pre +"'>"+
				 "<td >与申请人关系:</td>	"+
				 	"<td >"+
				 		"<input type='text' class='warm' id='EMERGENCY_RELATIONSHIP_"+pre+"' title='联系人与申请人关系' style='width:135px;' name='EMERGENCY_RELATIONSHIP_"+pre+"'/>"+
				 	"</td>"+
				 	"<td >现居住地址:</td><td >" +
				 	"<select name='ADDR_PROVINCE_"+pre+"' class='warm' id='ADDR_PROVINCE_"+pre+"' style='width:100px;' onchange='getChildArea2(this,this.value,\"ADDR_CITY_"+pre+"\",\"ADDR_COUNTY_"+pre+"\")' title='省(联系人现居住地址)'><option value=''>--请选择--</option>"+QUYUOPTION+"	</select>" +

					"<select name='ADDR_CITY_"+pre+"' class='warm' id='ADDR_CITY_"+pre+"' style='width:100px;'  onchange='getChildArea(this,this.value,\"ADDR_COUNTY_"+pre+"\")' title='市(联系人现居住地址)' ><option value=''>--请选择--</option>	</select>"+
					
					"<select name='ADDR_COUNTY_"+pre+"' class='' id='ADDR_COUNTY_"+pre+"' style='width:100px;' title='区/县(联系人现居住地址)' ><option value=''>--请选择--</option>	</select>"+
						
						 	
						 	"</td>"+
				 	
				 	
				 "</tr><tr class='tr"+ pre +"'><td> </td><td colspan='3'><input type='text' class='warm' id='EMERGENCY_ADDR_"+pre+"' title='街道(联系人现居住地址)'  style='width:415px;' name='EMERGENCY_ADDR_"+pre+"'/></td></tr>" ;			
	if(	pre>1 ){		
		$(".tr"+pre).remove() ;
		var index = pre ;
		pre = pre-1 ;
		var v =$(".tr"+pre).get($(".tr"+pre).length-1) ;
		$(v).after(appendTr) ;	
		$("input[name='EMERGENCY_NAME_"+index+"']").val(EMERGENCY_NAME);
		$("input[name='EMERGENCY_PHONE_"+index+"']").val(EMERGENCY_PHONE);
		$("input[name='EMERGENCY_ADDR_"+index+"']").val(EMERGENCY_ADDR);
		$("input[name='EMERGENCY_RELATIONSHIP_"+index+"']").val(EMERGENCY_RELATIONSHIP);
	}
	
}

function isHasSpouse(){
	
	var v = $("select[name=SEX]").attr("selected","selected").val();
	var IS_MARRY = $("select[name=IS_MARRY]").attr("selected","selected").val();
	
	if(IS_MARRY == "1Marriage" || IS_MARRY == "4Marriage" || IS_MARRY == "1" || IS_MARRY == "4"){
		
		var v1 = 0 ;
		
		if(v==v1){
			v1=1 ;
		}
		$("select[name=SPOUST_SEX]").val(v1);
	}
	
}


//提交校验
function isNotNullSubmit(){
	var flag = true;
	// 联系人现居住地址不需要填写
	$(".warm").each(function(){
		if($(this).val() == null || $(this).val() ==''){
			var title = $(this).attr('title');
			alert('【'+title+'】不能为空！');
			$(this).focus();
			flag = false;
			return flag;
		}
	});
	
	if(!flag){
		;
	}else{
		$(".warmlegalDate").each(function() {
			if ($(this).datebox('getValue') == '' || $(this).datebox('getValue') == null ) {
				var title = $(this).attr('title');
				alert('【'+title+'】不能为空！');
				$(this).focus();
				flag = false;
				return flag;
			}
			
		});
	}
	//法人客户-LP,保存时企业团队必填一人信息
//	if(flag && $("#TYPE").val()=="LP"){
//		//验证企业团队人员信息
//		var legal_person = $("#LEGAL_PERSON").val();//法人信息
//		var team = $("#EnterpriseTeam").val();
//		if(team !=undefined){
//			var rows=$("#comteam").datagrid('getData').rows.length;
//			if(rows<1 && (legal_person!=null && legal_person!="")){
//				$.messager.alert("提示","企业团队一栏至少要有一个人信息,请填写后保存!");
//				$("#EnterpriseTeam").focus();
//				flag = false;
//				return flag;
//			}
//			return flag;
//		}
//		return flag;
//	}
	//配偶信息
	if(flag && !isNotNullSubmitMarry()){
		flag = false;
		return flag;
	}
	
	return flag;
}

//提交提示
function isNotNullClass(){
	// 联系人现居住地址不需要填写
	$(".warm").each(function(){
		if($(this).val() == null || $(this).val() ==''){
			$(this).addClass('error');
		}else{
			$(this).removeClass('error');
		}
	});
	
	$(".warmlegalDate").each(function() {
		if ($(this).datebox('getValue') == '' || $(this).datebox('getValue') == null ) {
			$(this).parent().find("input").addClass('error');
		}else{
			$(this).parent().find("input").removeClass('error');
		}
		
	});
	//验证配偶信息必输项
	isNotNullClassMarry();
}


function changeDataYz(){
	var PERIOD_OF_VALIDITY=$("input[name='PERIOD_OF_VALIDITY']").val();
	var REGISTE_DATE=$("input[name='REGISTE_DATE']").val();
	
	if(PERIOD_OF_VALIDITY != '' && PERIOD_OF_VALIDITY !=null && REGISTE_DATE != '' && REGISTE_DATE !=null){
		if(PERIOD_OF_VALIDITY > REGISTE_DATE){
			return true;
		}else{
			alert("营业执照有效期必须大于注册时间！");
			return false;
		}
	}
	return true;
}

//暂存/保存  进行校验组织机构代码证
function checkAnyToOragnization(){
	//验证组织机构代码证
	var orag =$("#ORAGNIZATION_CODE").val();
	if(orag!=null && orag!=''){
		if(!isOragnizationCode('ORAGNIZATION_CODE')){
			return false;
		}
		return true;
	}
	return true;
}
//暂存/保存  进行校验税务登记证号
function checkAnyToTax(){
	//验证税务登记证号
	var tax =$("input[name='TAX_CODE']").val();
	if(tax!=null && tax!=''){
		if(!isTaxCode('TAX_CODE')){
			return false;
		}
		return true;
	}
	return true;
}

//注册时间  比较
function dateComparison(){
	var nowDate =new Date();
	var regDate =$("input[name='REGISTE_DATE']").val();
	var regDateNew = new Date(regDate.replace(/-/g,"/"));
	if(regDate!=null && regDate!=''){
		if(regDateNew > nowDate){
			alert("注册时间不能大于当前时间!");
			return false;
		}
		return true;
	}
	return true;
}

//法人身份证号验证
function toCheckFRCard(ele){
	var card = $("#"+ele).val();
	// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X 
	if(!isIdCardNo(card)){
		$("#"+ele)[0].focus(); 
        return  false;  
	}
	return true;
}

/**
 * 配偶信息必填项校验 保存时 有配偶则控制必输
 * @return
 */
function checkingMarry() {
	
	var type = $("#TYPE").attr("selected", true).val();
	var flagM = true;
	var IS_MARRY = $("select[name=IS_MARRY]").attr("selected","selected").val();
	if(IS_MARRY == "1Marriage" || IS_MARRY == "4Marriage" || IS_MARRY == "1" || IS_MARRY == "4"){
		$(".warmMarry").each(function() {
			var required = $(this).attr("requiredF")  ;
			var title =  $(this).attr("title") ;
			if($(this).val() == '' || $(this).val() == null){
				alert("请输入"+title) ;
				$(this).focus();
				flagM=false ;
				return false ;
			}
		});
		
		$(".warmlegalDateMarry").each(function() {
			var required = $(this).attr("requiredF")  ;
			var title =  $(this).attr("title") ;
			if ($(this).datebox('getValue') == '' || $(this).datebox('getValue') == null ) {
				alert("请输入"+title) ;
				$(this).focus();
				flagM=false ;
				return false ;
			}
		});
		return  flagM ;
	}
	
	return  flagM ;
}
//配偶信息	提交提示,有则提示
function isNotNullClassMarry(){
	var IS_MARRY = $("select[name=IS_MARRY]").attr("selected","selected").val();
	if(IS_MARRY == "1Marriage" || IS_MARRY == "4Marriage" || IS_MARRY == "1" || IS_MARRY == "4"){
		$(".warmMarry").each(function(){
			if($(this).val() == null || $(this).val() ==''){
				$(this).addClass('error');
			}else{
				$(this).removeClass('error');
			}
		});
		
		$(".warmlegalDateMarry").each(function() {
			if ($(this).datebox('getValue') == '' || $(this).datebox('getValue') == null ) {
				$(this).parent().find("input").addClass('error');
			}else{
				$(this).parent().find("input").removeClass('error');
			}
			
		});
	}

}

//配偶信息 提交校验
function isNotNullSubmitMarry(){
	var flagM = true;
	var IS_MARRY = $("select[name=IS_MARRY]").attr("selected","selected").val();
	if(IS_MARRY == "1Marriage" || IS_MARRY == "4Marriage" || IS_MARRY == "1" || IS_MARRY == "4"){
		$(".warmMarry").each(function(){
			if($(this).val() == null || $(this).val() ==''){
				var title = $(this).attr('title');
				alert('【'+title+'】不能为空！');
				$(this).focus();
				flagM = false;
				return flagM;
			}
		});
		
		if(!flagM){
			;
		}else{
			$(".warmlegalDateMarry").each(function() {
				if ($(this).datebox('getValue') == '' || $(this).datebox('getValue') == null ) {
					var title = $(this).attr('title');
					alert('【'+title+'】不能为空！');
					$(this).focus();
					flagM = false;
					return flagM;
				}
			});
		}
		return flagM;
	}
	return flagM;
}
//单位名称  来判断单位地址\职务是否必填
function workUnitBT(val){
	if(val!=null && val!=""){//填写单位名称 
		$("#COMPANY_ADDR_PROVINCE").attr("class","warm");
		$("#COMPANY_ADDR_CITY").attr("class","warm");
		$("#COMPANY_ADDR_COUNTY").attr("class","warm");
		$("#COMPANY_ADDR").attr("class","warm");
		$("input[name='POSITION']").attr("class","warm");
	}else{
		$("#COMPANY_ADDR_PROVINCE").attr("class","");
		$("#COMPANY_ADDR_CITY").attr("class","");
		$("#COMPANY_ADDR_COUNTY").attr("class","");
		$("#COMPANY_ADDR").attr("class","");
		$("input[name='POSITION']").attr("class","");
	}
}
