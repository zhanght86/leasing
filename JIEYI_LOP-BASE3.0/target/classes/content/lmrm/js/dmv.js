function conditionsSelect(){ 
//	var jsonData = $("#my_form").serialize();
//	jQuery.ajax({
//	   type: "POST",
//	   dataType:"json",
//	   url: _basePath+"/lmrm/Dmv!toMgDmvData.action",
//	   data: jsonData,
//	   success: function(msg){
//		 $('#pageTable').datagrid("loadData",msg);
//	   }
//	});
	$('#pageTable').datagrid('load', {"param":getFromData("#my_form")});
}

function clearSelect(formId) {
	$("#"+formId).form('clear');
}

function selectProvince(text,value,selectId,selectId1,addName,addName1,addName2){
	$("#"+addName).val('');
	$("#"+addName1).val('');
	$("#"+addName2).val('');
	$("#"+addName).val(text);
	$('#'+selectId).combobox('clear');
	$('#'+selectId1).combobox('clear');
	jQuery.ajax({
	   type: "POST",
	   dataType:"json",
	   url: _basePath+"/lmrm/Dmv!selectCity.action?PARENT_ID="+encodeURI(value),
	   success: function(json){
			var data = json.data;
			var temp = new Array();
			if(data != null && data.length > 0){
				for(var i = 0; i < data.length; i++){
					temp.push({"value":data[i].ID,"text":data[i].NAME});
				}
				$('#'+selectId).combobox('loadData', temp);
			}
	   }
	});
}

function setAreaName(text,addName){
	$("#"+addName).val(text);
}

function clearInput(){
	$("#ADD_PROVINCE_NAME").val('');
	$("#").val('');
	$("#").val('');
}

function addDMV(divId){
	jQuery.ajax({
	   type: "POST",
	   dataType:"json",
	   url: _basePath+"/lmrm/Dmv!selectProvince.action",
	   success: function(json){
			var data = json.data;
			var temp = new Array();
			if(data != null && data.length > 0){
				for(var i = 0; i < data.length; i++){
					temp.push({"value":data[i].ID,"text":data[i].NAME});
				}
				$('#ADD_PROVINCE_ID').combobox('loadData', temp);
			}
	   }
	});
	$("#"+divId).show();
	$("#"+divId).dialog();
}

function close(divId,formId) {
	$("#"+divId).hide();
	$("#"+divId).dialog('close');
	$("#"+formId).form('clear');
}

function setHouserDel(val,row) {
	return "<a href='#' onclick='toView(" + JSON.stringify(row) + ")'>查看</a> |" +
		   "<a href='#' onclick='updateDmv(" + JSON.stringify(row) + ")'>修改</a> |" +
		   "<a href='#' onclick='deleteDmv(" + JSON.stringify(row) + ")'>删除</a>";
}

function toView(row){
	$("#SHOW_NAME").val(row.NAME);
	$("#SHOW_AREA_NAME").val(row.AREA_NAME);
	$('#SHOW_RECORD_DATE').val(row.RECORD_DATE);
	$("#SHOW_PROVINCE_NAME").val(row.PROVINCE_NAME);
	$("#SHOW_RECORD_NAME").val(row.RECORD_NAME);
	$("#SHOW_CITY_NAME").val(row.CITY_NAME);
	$("#SHOW_CONTACT").val(row.CONTACT);
	$("#SHOW_COUNTY_NAME").val(row.COUNTY_NAME);
	$("#SHOW_PHONE").val(row.PHONE);
	$("#SHOW_ADDRESS").val(row.ADDRESS);
	$("#SHOW_ID").val(row.ID);
	
	$("#pageTableDMV").datagrid({
		url:_basePath+"/lmrm/LeaseMortgage!toMgLeaseMortgageData.action?CMV_ID="+encodeURI(row.ID)
    });
	
	$("#showDmvDiv").show();
	$("#showDmvDiv").dialog();
}

function selectLM(){
	var E_NAME = $("#E_NAME").val();
	var E_TYPE = $("#E_TYPE").val();
	var CMV_ID = $("#SHOW_ID").val();
	jQuery.ajax({
	   type: "POST",
	   dataType:"json",
	   url: _basePath+"/lmrm/LeaseMortgage!toMgLeaseMortgageData.action?E_NAME="+encodeURI(E_NAME)+"&E_TYPE="+encodeURI(E_TYPE)+"&CMV_ID="+encodeURI(CMV_ID),
	   success: function(msg){
		 $('#pageTableDMV').datagrid("loadData",msg);
	   }
	});
}

function addSave(formId){
	var jsonData = $("#"+formId).serialize();
	jQuery.ajax({
	   type: "POST",
	   dataType:"json",
	   url: _basePath+"/lmrm/Dmv!toAddDmv.action",
	   data: jsonData,
	   success: function(json){
	   		if(json.flag){
	   			$("#addDmvDiv").hide();
				$("#addDmvDiv").dialog('close');
	   			 $('#pageTable').datagrid('load');
//				 window.location.href=_basePath+"/lmrm/Dmv!execute.action";
			}else{
				alert(json.msg);
			}
	   }
	});
}

function updateSave(formId){
	var pq = $('#UPDATE_AREA_ID').combobox('getText');
	if(pq == null || pq == ''){
		$("#UPDATE_AREA_NAME").val('');	
	}
	var sheng = $('#UPDATE_PROVINCE_ID').combobox('getText');
	if(sheng == null || sheng == ''){
		$("#UPDATE_PROVINCE_NAME").val('');
	}
	var shi = $('#UPDATE_CITY_ID').combobox('getText');
	if(shi == null || shi == ''){
		$("#UPDATE_CITY_NAME").val('');
	}
	var xian = $('#UPDATE_COUNTY_ID').combobox('getText');
	if(xian == null || xian == ''){
		$("#UPDATE_COUNTY_NAME").val('');
	}
	var jsonData = $("#"+formId).serialize();
	jQuery.ajax({
		type: "POST",
		dataType:"json",
		url: _basePath+"/lmrm/Dmv!toUpdateDmv.action",
		data: jsonData,
		success: function(json){
			if(json.flag){
				$("#updateDmvDiv").hide();
				$("#updateDmvDiv").dialog('close');
				$('#pageTable').datagrid('load');
//				window.location.href=_basePath+"/lmrm/Dmv!execute.action";
			}else{
				alert(json.msg);
			}
		}
	});
}

function updateDmv(row){
	jQuery.ajax({
		type: "POST",
		dataType:"json",
		url: _basePath+"/lmrm/Dmv!selectArea.action",
		success: function(json){
			var data = json.data;
			var temp = new Array();
			if(data != null && data.length > 0){
				for(var i = 0; i < data.length; i++){
					if(data[i].ID == row.AREA_ID){
						temp.push({"value":data[i].ID,"text":data[i].NAME,"selected":true});
					}else{
						temp.push({"value":data[i].ID,"text":data[i].NAME});
					}
				}
				$('#UPDATE_AREA_ID').combobox('loadData', temp);
			}
			jQuery.ajax({
			   type: "POST",
			   dataType:"json",
			   url: _basePath+"/lmrm/Dmv!selectProvince.action",
			   success: function(json){
					var data = json.data;
					var temp = new Array();
					if(data != null && data.length > 0){
						for(var i = 0; i < data.length; i++){
							if(data[i].ID == row.PROVINCE_ID){
								temp.push({"value":data[i].ID,"text":data[i].NAME,"selected":true});
							}else{
								temp.push({"value":data[i].ID,"text":data[i].NAME});
							}
						}
						$('#UPDATE_PROVINCE_ID').combobox('loadData', temp);
					}
					jQuery.ajax({
						type: "POST",
						dataType:"json",
						url: _basePath+"/lmrm/Dmv!selectCity.action?PARENT_ID="+encodeURI(row.PROVINCE_ID),
						success: function(json){
							var data = json.data;
							var temp = new Array();
							if(data != null && data.length > 0){
								for(var i = 0; i < data.length; i++){
									if(data[i].ID == row.CITY_ID){
										temp.push({"value":data[i].ID,"text":data[i].NAME,"selected":true});
									}else{
										temp.push({"value":data[i].ID,"text":data[i].NAME});
									}
								}
								$('#UPDATE_CITY_ID').combobox('loadData', temp);
							}
							jQuery.ajax({
								type: "POST",
								dataType:"json",
								url: _basePath+"/lmrm/Dmv!selectCity.action?PARENT_ID="+encodeURI(row.CITY_ID),
								success: function(json){
									var data = json.data;
									var temp = new Array();
									if(data != null && data.length > 0){
										for(var i = 0; i < data.length; i++){
											if(data[i].ID == row.COUNTY_ID){
												temp.push({"value":data[i].ID,"text":data[i].NAME,"selected":true});
											}else{
												temp.push({"value":data[i].ID,"text":data[i].NAME});
											}
										}
										$('#UPDATE_COUNTY_ID').combobox('loadData', temp);
									}
									$("#UPDATE_NAME").val(row.NAME);
									$("#UPDATE_AREA_NAME").val(row.AREA_NAME);
									$('#UPDATE_RECORD_DATE').datebox('setValue', row.RECORD_DATE);
									$("#UPDATE_PROVINCE_NAME").val(row.PROVINCE_NAME);
									$("#UPDATE_RECORD_NAME").val(row.RECORD_NAME);
									$("#UPDATE_CITY_NAME").val(row.CITY_NAME);
									$("#UPDATE_CONTACT").val(row.CONTACT);
									$("#UPDATE_COUNTY_NAME").val(row.COUNTY_NAME);
									$("#UPDATE_PHONE").val(row.PHONE);
									$("#UPDATE_ADDRESS").val(row.ADDRESS);
									$("#UPDATE_ID").val(row.ID);
									$("#updateDmvDiv").show();
									$("#updateDmvDiv").dialog();
								}
							});
						}
					});
			   }
			});
		}
	});
}

function deleteDmv(row){
	$.messager.confirm('提示框', '确定要删除吗?',function(flag){
		if(flag){
			jQuery.ajax({
				url : _basePath+"/lmrm/Dmv!toDeleteDmv.action?ID="+encodeURI(row.ID),
				dataType : "json",
				success : function(json){
					if(json.flag){
						$('#pageTable').datagrid('load');
//						window.location.href=_basePath+"/lmrm/Dmv!execute.action";
					}else{
						alert(json.msg);
					}	
				}
			});
		}
	});
}