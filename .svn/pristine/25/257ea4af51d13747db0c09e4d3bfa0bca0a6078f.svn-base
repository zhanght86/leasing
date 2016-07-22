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

var count = 0;
//资料清单列表-原件复印件选择
function choeseFile(value,row,index){
	++count;
	if(value==0){
		var str = "<input type='hidden' name='TYPE"+count+"' id='TYPE"+count+"' class='TYPE' value='1' />原件";
	}else{
		var str = "<input type='hidden' name='TYPE"+count+"' id='TYPE"+count+"' class='TYPE' value='2' />复印件";
	}
	
//    if(value==0){
//    	var str = "<input type='radio' name='DOSSIER_TEMP"+row.DATA_ID+"' id='DOSSIER_TEMP"+row.DATA_ID+"' class='DOSSIER_TEMP' value='1' checked='true'/>原件" +
//		   "<input type='radio' name='DOSSIER_TEMP"+row.DATA_ID+"' class='DOSSIER_TEMP' value='2'/>复印件";
//    }else{
//    	var str = "<input type='radio' name='DOSSIER_TEMP"+row.DATA_ID+"' id='DOSSIER_TEMP"+row.DATA_ID+"' class='DOSSIER_TEMP' value='1' />原件" +
//		   "<input type='radio' name='DOSSIER_TEMP"+row.DATA_ID+"' class='DOSSIER_TEMP' value='2' checked='true'/>复印件";
//    }

	return str;
	
}

//资料清单列表-文件分数设置
function choseNum(value,row,index){
	++count;
	if(value!=undefined){
		return "<input type='text' name='FENSHU"+count+"' id='FENSHU"+count+"' class='FENSHU' value='"+value+"' />";
	}else {
		return "<input type='text' name='FENSHU"+count+"' id='FENSHU"+count+"' class='FENSHU' value='0' />";
	}	
}

//选择支付表
function chosePaylist(value,rows,index){
	++count;
	if(rows.WARRANT_TYPE=="权证资料"){//权证资料
		var str = rows.payList;
		var arr = str.split(';');
		var str_ = "<select name='PAY_CODE"+count+"' width='180px;' id='PAY_CODE"+rows.LEASE_CODE+""+rows.DATA_ID+"' class='paycode'>";
		for(var i=0;i<arr.length;i++){
			if(arr[i]!=""){
				str_ += "<option value='"+arr[i]+"'>"+arr[i]+"</option>";
			}
		}
		str_ += "</select>";
		return str_;
	}
	
}
//按合同显示的类别数
function countVel(index){
	return index;
}
/**
 * 申请归档, 保存(单合同归档)X
 * @auther yangxue 
 * @date 2015-06-05
 * @returns
 */
function save(node){

	$("#save").attr("disabled",true);	
	var SEND_NUM = "";//邮寄单位
	var RECIPIENT = "";//接受人
	var SEND_PORSON = ""; //递送人
	var SEND_COMPANY = ""; //邮寄公司
	if($("input[name='SEND_TYPE']:checked").val()=="1"){
		if($("#SEND_COMPANY:selected").val()==""){
			return alert("请选择邮寄公司");
		}else {
			SEND_COMPANY = $("#SEND_COMPANY").val();
		}
		
		if($("#SEND_NUM").val()==""){
			return alert("请选择邮寄单号");
		}else{
			SEND_NUM = $("#SEND_NUM").val();
		}
		
		if($("#RECIPIENT").val()==""){
			return alert("请选择收件人");
		}else{
			RECIPIENT = $("#RECIPIENT").val();
		}
		
		if($("#SEND_PORSON").val()==""){
			return alert("请选择寄件人");
		}else{
			SEND_PORSON = $("#SEND_PORSON").val();
		}
	}
	
	$('.table_01 tr').each(function(){
		$(this).find('td').each(function(index){
			
		});
	});

	
	var flag = false;
	var saveRecord = [];
	var i = 1; 
 
	
	$(".dataid").each(
				function() {
						if ($(this).attr("checked") == "checked") {
							flag = true;
							var temp = {};
							var td = $(this).parent("td").parent("tr");
							temp.DATA_ID = $(this).val();
							temp.CLIENT_NAME = $("#CLIENT_NAME").val();
							temp.CLIENT_CODE = $("#CLIENT_CODE").val();					
							temp.LEASE_CODE = $("#LEASE_CODE").val();
							temp.PLATFORM_TYPE = $("#PLATFORM_TYPE").val();
							temp.INDUSTRY_TYPE = $("#INDUSTRY_TYPE").val();						
							temp.PAYLIST_CODE = $(td).find("input[class=PAY_CODE]").val();	
						    //计算支付表的个数
							if(temp.PAYLIST_CODE ==undefined||temp.PAYLIST_CODE==null){//无支付编号
								temp.FILE_NAME = $(td).find("input[name=FILE_NAME]").val();
								temp.FILF_FLAG = $(td).find("input[name=FILF_FLAG]").val();
								temp.DOSSIER_TEMP = $(td).find("input[name=DOSSIER_TEMP"+i+"]:checked").val();
								temp.DOSSIER_COUNT = $(td).find("input[name=THIS_COUNT]").val();
							}else{		//有支付编号		
									temp.NUM= $(td).find("input[class=NUM]").val();//不是权证资料的个数
									temp.JUM= $(td).find("input[class=JUM]").val();//权证资料的个数
									temp.payCum= $(td).find("input[class=payCum]").val();//支付表的个数
									temp.FILE_NAME = $(td).find("input[name=FILE_NAME"+temp.PAYLIST_CODE+"]").val();	
									temp.FILF_FLAG = $(td).find("input[name=FILF_FLAG"+temp.PAYLIST_CODE+"]").val();
									var NJUM=Number(temp.JUM)+Number(temp.NUM);//不带支付编号这个合同下的资料个数
									var j=0;
									if(i<=NJUM){//权证资料部分
										  temp.DOSSIER_TEMP = $(td).find("input[name=DOSSIER_TEMP"+i+""+temp.PAYLIST_CODE+"]:checked").val();
									      //alert("有支付编号："+temp.DOSSIER_TEMP+":inum=:"+i);
									}else{//因为支付表编号不同，权证资料重复部分
									   for(j=1;j<Number(temp.payCum);j++){
										 var inum=i-(j*Number(temp.JUM));
										 temp.DOSSIER_TEMP = $(td).find("input[name=DOSSIER_TEMP"+inum+""+temp.PAYLIST_CODE+"]:checked").val();
										// alert("有支付编号："+temp.DOSSIER_TEMP+":inum=:"+inum);								
									    }	
								    }
									temp.DOSSIER_COUNT = $(td).find("input[name=THIS_COUNT"+temp.PAYLIST_CODE+"]").val();								
							}						
							saveRecord.push(temp);
						}
					i++;
			});						

	
	var data = {
			saveRecord:saveRecord,
			SEND_TYPE:$("input[name='SEND_TYPE']:checked").val(),
	        SEND_COMPANY:SEND_COMPANY,
	        SEND_NUM:SEND_NUM,
	        RECIPIENT:RECIPIENT,
	        SEND_PORSON:SEND_PORSON,
	        LEASE_CODE:$("#LEASE_CODE").val(),
	        CLIENT_CODE:$("#CLIENT_CODE").val()
	};
	
	jQuery.ajax({
		url:_basePath+"/documentApp/ApplyDossier!doAddpDossierApp.action",
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

/**
 * 批量与单个档案柜审批保存
 */
 function saveFiles(node){
		var saveRecord = [];
		$(".client").each(
	      function(k) { ++k;
		   var dataList = $("#its"+k).val();//获取选中信息
			alert(DATAID);
	      for(var i = 0; i<++k; i++){
	    	  var temp = {};
				temp.DATA_ID = $("input[class='.dataid']:checked")[i].val();
				alert(temp.DATA_ID);
	      }
		});
 }


/**
 * 申请归档, 保存(多合同归档)
 * @auther yangxue 
 * @date 2015-06-10
 * @returns
 */
function save1(){
    $("#save1").attr("disabled",true);
	
	var SEND_NUM = "";//邮寄单位
	var RECIPIENT = "";//接受人
	var SEND_PORSON = ""; //递送人
	var SEND_COMPANY = ""; //邮寄公司
	if($("input[name='SEND_TYPE']:checked").val()=="1"){
		if($("#SEND_COMPANY:selected").val()==""){
			return alert("请选择邮寄公司");
		}else {
			SEND_COMPANY = $("#SEND_COMPANY").val();
		}
		
		if($("#SEND_NUM").val()==""){
			return alert("请选择邮寄单号");
		}else{
			SEND_NUM = $("#SEND_NUM").val();
		}
		
		if($("#RECIPIENT").val()==""){
			return alert("请选择收件人");
		}else{
			RECIPIENT = $("#RECIPIENT").val();
		}
		
		if($("#SEND_PORSON").val()==""){
			return alert("请选择寄件人");
		}else{
			SEND_PORSON = $("#SEND_PORSON").val();
		}
	}
	
	var saveRecord = [];
	$(".ziliaoliebiao").each(
			function(k) {
				++k;
				var dataList = $("#ziliao"+k).datagrid('getSelections');//获取选中信息
				for(var i = 0; i<dataList.length; i++){
					var temp = {};
					temp.DATA_ID = dataList[i].DATA_ID;
					temp.FILE_NAME = dataList[i].WARRANT_NAME;
					temp.FILF_FLAG = dataList[i].WARRANT_TYPE;
					temp.CLIENT_NAME = $("#CLIENT_NAME"+k).val();
					temp.CLIENT_CODE = $("#CLIENT_CODE"+k).val();
					temp.LEASE_CODE = $("#LEASE_CODE"+k).val();
					temp.PLATFORM_TYPE = $("#PLATFORM_TYPE"+k).val();
				//	var PAYLIST_CODE = "";					
					//$().each(function(i, n){
				//	    if($(this).attr("selected", true)){
					//		PAYLIST_CODE = $(this).val();
				//		}
				//	});
				//	temp.PAYLIST_CODE = PAYLIST_CODE;


			//		temp.code=dataList[i].payList;
			//		temp.arr=temp.PAYLIST_CODE.split(';');
			//		alert(temp.arr);
					temp.PAYLIST_CODE=$(this).find("select[id=PAY_CODE"+temp.LEASE_CODE+temp.DATA_ID+"] option:selected").val();
					
				//	var DOSSIER_TEMP = "";
				//	$(".TYPE"+temp.DATA_ID).each(function(){
						//if($(this).attr("checked", true)){
						//	DOSSIER_TEMP = $(this).val();							
					    //	}
				//	});
					
					if(dataList[i].TYPE==0){
						temp.DOSSIER_TEMP = 1;
					}else if(dataList[i].TYPE==1){
						temp.DOSSIER_TEMP = 2;
					}
					temp.DOSSIER_COUNT=dataList[i].FENSHU;
					temp.PORTFOLIO_NUMBER = $(this).val();
					saveRecord.push(temp);
				}
			});
	
	var data = {
			saveRecord:saveRecord,
			SEND_TYPE:$("input[name='SEND_TYPE']:checked").val(),
	        SEND_COMPANY:SEND_COMPANY,
	        SEND_NUM:SEND_NUM,
	        RECIPIENT:RECIPIENT,
	        SEND_PORSON:SEND_PORSON,
	        LEASE_CODE:$("#LEASE_CODE").val(),
	        CLIENT_CODE:$("#CLIENT_CODE").val()
	};
	
	jQuery.ajax({
		url:_basePath+"/documentApp/ApplyDossier!doAddpDossierApp.action",
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


function saveFile2(ele1, ele2,ele3){
	var CLIENT_NAME=$("input[name='CLIENT_NAME']").val();
	var LEASE_CODE=$("input[name='LEASE_CODE']").val();
	top.addTab(CLIENT_NAME+LEASE_CODE+"归档明细",_basePath+"/documentApp/ApplyDossier!toAddDossierData.action?CLIENT_NAME="+CLIENT_NAME+"&LEASE_CODE="+LEASE_CODE);
}

/**
 * 档案入柜操作
 * @param ele1
 * @param ele2
 * @param ele3
 * @returns
 */
function saveFile1(ele1, ele2,ele3){
	var CABINET_NUMBER = "";
	var PORTFOLIO_HEAD = $("#PORTFOLIO_HEAD"+ele2).attr("selected",true).val();
	var PORTFOLIO_ROW = $("#PORTFOLIO_ROW"+ele2).attr("selected",true).val();
	var PORTFOLIO_LINE = $("#PORTFOLIO_LINE"+ele2).attr("selected",true).val();
	var CLIENT_NAME= $("#CLIENT_NAME"+ele2).val();
	var LEASE_CODE=$("#LEASE_CODE"+ele2).val();
	
	
	
	if(PORTFOLIO_HEAD==""||PORTFOLIO_LINE==""){
		return $.messager.alert("提示", "请选择档案柜编号");
	}
	
	CABINET_NUMBER = PORTFOLIO_HEAD+"-"+PORTFOLIO_ROW+"-"+PORTFOLIO_LINE;
	var saveData = [];

	
	$(".dataid").each(function() {
		var temp = {};
		temp[$(this).attr("name")] = $.trim($(this).val());
		temp.RETURN_REASON= $("#RETURN_REASON"+temp[$(this).attr("name")]).attr("selected",true).val();	
		temp.PAYLIST_CODE=$("input[id='PAYLIST_CODE"+$(this).val()+"']").val();
		alert(temp.PAYLIST_CODE);
		saveData.push(temp);
	});


	
	if(saveData==""){
		return $.messager.alert("警告", "请选择入柜资料");
	}
	
	var save = {
			saveData:saveData,
			CABINET_NUMBER:CABINET_NUMBER,
			CLIENT_NAME:CLIENT_NAME,
			LEASE_CODE:LEASE_CODE,
			PORTFOLIO_NUMBER:$("#PORTFOLIO_NUMBER"+ele2).val()
	};
	
	$.messager.confirm('警告','确认提交档案入柜?',function(r){
	    if (r){
			$.ajax({
				type:"post",
				//url:_basePath+"/documentApp/ApplyDossier!doUpdateDossierFile.action",
				url:_basePath+"/documentApp/ApplyDossier!doUpdateApply.action",
				data:"DOSSIER_APPLY_ID="+$("#DOSSIER_APPLY_ID").val()+"&REMARK="+$("#REMARK").val()+"&saveData="+encodeURI(JSON.stringify(save)),
				dataType:"json",
				success:function(json){
					if(json.flag){
						$.messager.alert("提示", json.msg);
						$("#"+ele3+" [name='CABINET_NUMBER']").each(function() {
							$(this).val(CABINET_NUMBER);
						});
						
						$("#"+ele3+" [name='PORTFOLIO_NUMBER']").each(function() {
							$(this).val($("#PORTFOLIO_NUMBER"+ele2).val());
						});
					}else{
						$.messager.alert("提示", json.msg);
					}
				}
			});
	    }});
}

/**
 * 档案入柜提交
 * @auther yangxue 
 * @date 2015-06-09
 */
function saveFile(ele1, ele2,ele3){
	var CABINET_NUMBER = "";
	var PORTFOLIO_HEAD = $("#PORTFOLIO_HEAD"+ele2).combobox('getValue');
	var PORTFOLIO_ROW = $("#PORTFOLIO_ROW"+ele2).combobox('getValue');
	var PORTFOLIO_LINE = $("#PORTFOLIO_LINE"+ele2).combobox('getValue');
	
	if(PORTFOLIO_HEAD==""||PORTFOLIO_LINE==""){
		return $.messager.alert("提示", "请选择档案柜编号");
	}
	
	CABINET_NUMBER = PORTFOLIO_HEAD+"-"+PORTFOLIO_ROW+"-"+PORTFOLIO_LINE;
	var saveData = [];
	$("#"+ele3+" [name='DATA_ID']").each(function() {
		var temp = {};
		temp[$(this).attr("name")] = $.trim($(this).val());
		temp.RETURN_REASON=$("#RETURN_REASON"+$.trim($(this).val())).combobox('getValue');
		alert(temp.RETURN_REASON);
		saveData.push(temp);
	});
	
	if(saveData==""){
		return $.messager.alert("警告", "请选择入柜资料");
	}
	
	var save = {
			saveData:saveData,
			CABINET_NUMBER:CABINET_NUMBER,
			PORTFOLIO_NUMBER:$("#PORTFOLIO_NUMBER"+ele2).val()
	};
	
	$.messager.confirm('警告','确认提交档案入柜?',function(r){
	    if (r){
	    	$.ajax({
	    		type:"post",
	    		url:_basePath+"/documentApp/ApplyDossier!doUpdateApply.action",
	    		data:"DOSSIER_APPLY_ID="+$("#DOSSIER_APPLY_ID").val()+"&REMARK="+$("#REMARK").val()+"&saveData="+encodeURI(JSON.stringify(save)),
	    		dataType:"json",
	    		success:function(json){
	    			if(json.flag){
	    				$.messager.alert("提示", json.msg);
	    			}else{
	    				$.messager.alert("提示", json.msg);
	    			}
	    		}
	    	});
	    }
	});
}

/**
 * 选择档案柜
 * @param ele
 * @param val
 * @param val1
 */
function chooseCabine(ele, val,val1){
	var PORTFOLIO_HEAD=$("#"+ele).val();
	var hline=$("#"+val1).val();
	$.ajax({
		type:"post",
		url:_basePath+"/documentApp/ApplyDossier!doShowCabRowLine.action",
		data:"PORTFOLIO_HEAD_1="+PORTFOLIO_HEAD,
		dataType:"json",
		success:function(json){
			if(json.flag){
				 var rows=json.data.ROW_NUM;
				 var lines=json.data.LINENUM;
				// var PORTFOLIO_ROW=$("#PORTFOLIO_ROW");
				 var PORTFOLIO_LINE=$("#"+val1);
				 PORTFOLIO_LINE.empty();				 
				 for(var i=1;i<=lines;i++){
					var op1=$("<option>");
					op1.attr("value","00"+i);
					if(hline=="00"+i){
						op1.attr("selected",true);
					}
					op1.text("00"+i);
					PORTFOLIO_LINE.append(op1);					 
				 }
				 
			}else{
				$.messager.alert("提示","获取档案柜行列数据失败");
			}
		}
	});
}

/**
 * 退回退回选中项目
 * @auther yangxue 
 * @date 2015-06-11
 */
function returnMaterial(){
	var saveRecord = [];
	$(".dataid").each(
			function() {
				if ($(this).attr("checked") == "checked") {
					flag = true;
					var temp = {};
					var td = $(this).parent("td").parent("tr");
					temp.DATA_ID = $(this).val();
					temp.PAYLIST_CODE=$(td).find("input[class=PAYLIST_CODE]").val();
					if(temp.PAYLIST_CODE ==undefined||temp.PAYLIST_CODE==null){//无支付编号
					  if($(td).find("select[id=IS_RETURN"+temp.DATA_ID+"] option:selected").val()==''){
							return alert("请选择是否退回!").length=1;
					  }else{
							temp.IS_RETURN=$(td).find("select[id=IS_RETURN"+temp.DATA_ID+"] option:selected").val();
					  }
					  if($(td).find("select[id=RETURN_REASON"+temp.DATA_ID +"] option:selected").val()==''){
							return alert("请选择错误原因!").length=1;
					  }else{
							temp.RETURN_REASON=$(td).find("select[id=RETURN_REASON"+temp.DATA_ID +"] option:selected").val();	
					  }			
					}else{
					  if($(td).find("select[id=IS_RETURN"+temp.DATA_ID+temp.PAYLIST_CODE+"] option:selected").val()==''){
							return alert("请选择是否退回!").length=1;
					  }else{
							temp.IS_RETURN=$(td).find("select[id=IS_RETURN"+temp.DATA_ID+temp.PAYLIST_CODE+"] option:selected").val();		
					  }
					  if($(td).find("select[id=RETURN_REASON"+temp.DATA_ID+temp.PAYLIST_CODE +"] option:selected").val()==''){
							return alert("请选择错误原因!").length=1;
					  }else{
							temp.RETURN_REASON=$(td).find("select[id=RETURN_REASON"+temp.DATA_ID+temp.PAYLIST_CODE +"] option:selected").val();				
					  }			  
					}
					saveRecord.push(temp);
				}
			});
	
	//判断是否勾选需要退回的资料
	if(saveRecord == "") return $.messager.alert("警告","请选择退回资料!!");
	
	//若存在退回资料, 则执行操作
	var save = {saveRecord : saveRecord};
	jQuery.ajax({
		type:'post',
		url:_basePath+"/documentApp/ApplyDossier!doUpdateReturnMaterial.action", 
		data:"param="+encodeURI(JSON.stringify(save)),
		dataType:'json',
		success:function(json){
			if(json.flag){
				$.messager.alert("提示",json.msg);
			}else {
				$.messager.alert("提示",json.msg);
			}
		}
	});
}

//checkbox制灰限制
function togrey(node){
	
	var THIS_COUNT=$(node).parent("td").children("#THIS_COUNT").val();
	$(".myfile").each(function(i){
	    var index=0;    
	    if(THIS_COUNT==0){
	        index=i;
	        var ID=$(this).parent("td").parent("tr").children("td").eq(index).text();
	 	    $("input[name='DATA_ID']").each(function() {  
             if($(this).val()==ID){  
               $(this).attr("disabled", true);  
              }  
             }); 
	     }
	 });	
}