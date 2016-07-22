$(function(){
	$("#saveBtn").hide();
	$("#startBtn").show();
	$("textarea").attr("readonly",true); // readonly="readonly"
	$("textarea").css("background-color","#D5D5D5");
	$("#addFun").attr("disabled",true);	
});


function changeFun(){
	var f=$("#flag", window.parent.parent.document);
	//alert(f.val());
	f.val("startVal");

	$("#saveBtn").show();
	$("#startBtn").hide();
	$("textarea").attr("readonly",false);
	$("textarea").css("background-color","#FFFFFF");
	$("#addFun").attr("disabled",false);	
	
//	alert($("#saveBtn").is(":hidden"));
	
}
	function seachByBD(telNo){
//		alert(telNo);
		var url="http://www.baidu.com/s?ie=utf-8&tn=site8_dg&wd="+telNo;
		
		window.open(url);
		/*$("#searchBD").dialog({
			width:500,
			height:400,
			content:"<iframe src='"+url+"' style='width: 480px;height: 326px;' scrolling=auto></iframe>",
			title:"百度",
			buttons: [{
				id:"btnbc",
		        text:'取消',
		     iconCls:'icon-cancel',
		     handler:function(){
		    	 $("#searchBD").dialog('close');
			 	}
			}]
		  });
		
			$("#searchBD").dialog('open');*/
	}
	function getCustTelList(){
		
	}
	
	//追加紧急联系人
	function appendEmergencyContact(){
		
		if(!($("#saveBtn").is(":hidden"))) {
			$.messager.alert("提示","请先保存数据！"); 
			return false;
			//console.log($("#saveBtn").is(":hidden"));
		} 
//		else {
//			console.log($("#saveBtn").is(":hidden"));
//		}
		
		$("#controlCustEmergency").dialog({
			width:530,
			height:260,
			title:"保存",
			buttons: [{
				id:"btnsave",
		        text:'保存',
		     iconCls:'icon-save',		     
		     handler:function(){
//		    	 if($("#EMERGENCY_TYPE").find("option:selected").val()==""){
//		    		
//		 			return false;
//		 		}
		 		if($("#addForm input[name='EMERGENCY_NAME']").val()==""){
		 			$("#errmsgTR").find(":first-child").html("请填写姓名！");
		    		$("#errmsgTR").show();
		 			return false;
		 		}
		 		var phone=$("#addForm input[name='EMERGENCY_PHONE']").val();
		 		if($.trim(phone).length != 11 || phone ==""){
		 			$("#errmsgTR").find(":first-child").html("请填写正确的手机号！");
		    		$("#errmsgTR").show();
		 			return false;
		 		}
		 		if($("#EMERGENCY_RELATIONSHIP").find("option:selected").val()==""){
		 			$("#errmsgTR").find(":first-child").html("请选择申请人之间关系！");
		    		$("#errmsgTR").show();
		 			return false;
		 		}
		    	
				jQuery.ajax({
					type:"post",
					url : _basePath + "/customers/CustTel!doAddEmergencyInfo.action",
					data : "param="+getFromData("#addForm"),
					dataType : "json",
					success : function(result) {
						if (result) {
							if($("#saveBtn").is(":hidden")){
								$.messager.alert("提示","保存成功");
								location.reload();
								
							}else{
								saveTelContext();
							}
						} else {
							$.messager.alert("提示","保存失败，请联系管理员！"); 
						}
					}
				});
			 	}
			},{
				id:"btnbc",
		        text:'取消',
		     iconCls:'icon-cancel',
		     handler:function(){
		    	 $("#controlCustEmergency").dialog('close');
			 	}
			}]
		  });
		$("#addCustEmergency").show();
		$("#controlCustEmergency").dialog('open');
	}
	
//判定	
	function saveTelContext(){
		var project_id= $("#project_id").val();
		var TEL_PHONE= $("#TEL_PHONE").val();
		var PHONE= $("#PHONE").val();
		var TELEPHONE_UNIT= $("#TELEPHONE_UNIT").val();
		var EMERGENCY_PHONE = $("#EMERGENCY_PHONE").val();
		
		// 替换所有textarea输入框中的%为百分比
		$("textarea").each(function(){
			$(this).val($(this).val().replace(/%/g,"百分比"));
		});
		
		var num = 0;

		$("#emergencyInfo textarea").each(function(){
			if($(this).val()!=null && $(this).val()!=''){
				num++;
				return false;
			}
			// 循环遍历每一个联系人的电话调查备注里的非空文本值：alert($(this).val());
			//var textArr=$(this).val();
			//alert(textArr.length);
		});
		if(num > 0){
			jQuery.ajax({
				type:"post",
				url : _basePath + "/customers/CustTel!saveTelContext.action",
				data : "project_id="+project_id+"&param="+getFromData("#telContext")+"&param2="+getFromData("#emergencyInfoForm") + 
				"&TEL_PHONE=" + TEL_PHONE + "&PHONE=" + PHONE+ "&TELEPHONE_UNIT=" + TELEPHONE_UNIT + "&EMERGENCY_PHONE=" + EMERGENCY_PHONE,
				dataType : "json",
				success : function(result) {
					$("#emergency_context textarea").each(function(){
						
//						if($(this).text()==null || $(this).text()==''){
//							console.log($(this).text() + "error");
//						}else{
//							console.log($(this).text() + "ok");
//						}
						// 循环遍历每一个联系人的电话调查备注里的非空文本值：alert($(this).text());
					});
					if (result) {
						$.messager.alert("提示","保存成功");
						location.reload();
					} else {
						$.messager.alert("提示","保存失败，请联系管理员！"); 
					}
				}
		 
			});
			
			//	alert("saveTelContext="+project_id+"tbody="+tbody);
			// 当保存成功后，将所有输入框置为不可填写并弹出提示框
			var t = $('textarea');
			var f = $("#flag", window.parent.parent.document);
			f.val("saveVal");
			$("#saveBtn").hide();
			$("#startBtn").show();
			$("#addFun").attr("disabled",true);
			$("textarea").attr("readonly",true);
			$("textarea").css("background-color","#D5D5D5");
		}else{
			$.messager.alert("提示","请输入一个联系人的电话备注！"); 
		}
					
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
//					if(val=="100009998" || val=="100010003" || val=="100439000" || val=="100246608"){
//						if(json.length==1){//二级市为一个选项
//							getChildArea(obj,json[0].ID,childId2);
//							$(tr).find("option[value="+json[0].ID+"]").attr("selected",true);
//						}else{
//							getChildArea(obj,val,childId2);
//						}	
//						$(tr).find("#"+childId1).attr("class","");
//						$(tr).find("#"+childId1).hide();
//					}else{
//						$(tr).find("#"+childId1).attr("class","warm");
//						$(tr).find("#"+childId1).show();
//					}
			}
		});
	}
	
	/**
	 * 根据ID删除追加的联系人
	 * @param itemID	紧急联系人表ID
	 */
	function delByID(itemID){
		
		$.messager.confirm('提示框', '确定要删除此联系人信息？', function(r){
			if(r){
				jQuery.ajax({
					url:_basePath+"/customers/CustTel!delByID.action",
					data : "itemID="+itemID,
					type:"post",
					dataType:"json",
					success:function(result){
						if (result) {
							$.messager.alert("提示","删除成功"); 
							location.reload();
						} else {
							$.messager.alert("提示","删除失败，请联系管理员！"); 
						}
					}
				});
	        }
		});
		
	}
	