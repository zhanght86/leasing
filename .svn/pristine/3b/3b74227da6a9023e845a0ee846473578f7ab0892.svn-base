
function se(){
	var SUP_SHORTNAME=$("#SUP_SHORTNAME").val();
	var COMPANY_NAME=$("#COMPANY_NAME").val();
	var STATUS = $("#STATUS").val();
	var BUSINESS_SECTOR = $("#BUSINESS_SECTOR").val();
	$('#pageTable').datagrid('load',{"SUP_SHORTNAME":SUP_SHORTNAME,"COMPANY_NAME":COMPANY_NAME,"STATUS":STATUS,"BUSINESS_SECTOR":BUSINESS_SECTOR});
}
function rowformater(value,row,index){
    return "<a href=javascript:void(0) onclick=addParentTabs('供应商明细','"+_basePath+"/base/suppliers/Suppliers!getShowDetail.action?SUP_ID="+row.SUP_ID+"')>"+value+"</a>";
}

function clearMess(){
    $("#form01 input").val("");
	$("#STATUS").val("");
	$("#BUSINESS_SECTOR").val("");
}

function addConfig(value,row,index){
    if(row.CREDIT_SWITCH==1){
       return "<a href='javascript:void(0)' onclick='standardChange("+JSON.stringify(row)+")'>标准额度修改</a>&nbsp;|&nbsp;<a href='javascript:void(0)' onclick='singleChange("+JSON.stringify(row)+")'>一单一议额度修改</a>";
	}else{
	   if(row.STATUS=='未授信'){
	   	  if(row.APPLY_STATUS==1){
			 return "<a href='javascript:void(0)' style='text-decoration: none; color:red;'>已发起初始额度申请流程</a>"; 				  
		  }else{
		      return "<a href='javascript:void(0)' onclick='changeRequest1("+JSON.stringify(row)+",1)' >初始额度申请</a>";
		  }
	   }else if(row.STATUS=='初始授信'){
	   	  if(row.APPLY_STATUS==1){
			 return "<a href='javascript:void(0)' style='text-decoration: none; color:red;'>已发起标准额度申请流程</a>"; 				  
		  }else{
             return "<a href='javascript:void(0)' onclick='changeRequest1("+JSON.stringify(row)+",2)' >标准额度申请</a>";
		  }
	   }else{
	   	  if(row.APPLY_STATUS==1){
			 return "<a href='javascript:void(0)' style='text-decoration: none; color:red;'>已发起标准额度变更流程</a>"; 				  
		  }else{
             return "<a href='javascript:void(0)' onclick='changeRequest1("+JSON.stringify(row)+",3)'  >标准额度变更</a>";
		  }
	   }
	}
}
//发起流程
function changeRequest1(row,str){
	//String projectId, String clientId, String payCode CREDIT_ID, APP_ID, SUP_ID
	var url = _basePath+"/base/channel/Channel!doStartProcess.action";
	var param = "CREDIT_ID="+row.CREDIT_ID+"&SUP_ID="+row.SUP_ID+"&STATUS="+row.STATUS+"&SUPPLIER_ID="+row.SCODE+
	"&APP_TYPE="+str+
	//标准参数start
	"&SUP_NAME="+row.SUP_SHORTNAME+
	"&projectId="+row.CREDIT_ID+
	"&clientId="+
	"&payCode="+row.SUP_ID+
	"&MODEL_NAME=授信_初始(标准)额度申请专决书"+
	"&random="+Math.random();
	//标准参数end
//	console.info(url);
	//top.addTab("授信_初始(标准)额度申请专决书【"+row.SUP_ID+"】",url);
	jQuery.ajax({
		url : url,
		data : param,
		type : "POST",
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		dataType : "json",
		success:function(json){
			if(json.flag){
				$.messager.alert("提示","发起流程成功！",'info',function(){
						$.messager.alert("提示",json.data.msg+json.data.nextCode,"info",function(){
							top.addTab(json.data.jbpmId,_basePath+"/bpm/Task!toShow.action?taskId="+json.msg);
						});
					});
				
				
			}
		}
   });
	se();
}

function standardChange(v){
   $("#SUP_SHORTNAME_").text(v.SUP_SHORTNAME);
   $("#INITIAL_AMOUNT").text(v.INITIAL_AMOUNT);
   $("#STANDARD_AMOUNT").text(v.STANDARD_AMOUNT);
   $("#CANUSE_MONEY").text(v.CANUSE_MONEY);
   $("#USER_MONEY").text(v.BASE_USEMONEY);
   $("#CREDIT_ID").val(v.CREDIT_ID);
   $("#SUP_ID").val(v.SUP_ID);
   $('#dlg').dialog('open').dialog('setTitle','修改标准额度');
}

function singleChange(v){
   $("#SUP_SHORTNAME_SINGLE").text(v.SUP_SHORTNAME);
   $("#SINGLE_AMOUNT").text(v.SINGLE_AMOUNT);
   $("#USESINGLE_MONEY").text(v.SINGLE_CANUSEMONEY);
   $("#CREDIT_ID").val(v.CREDIT_ID);
   $("#SUP_ID").val(v.SUP_ID);
   $('#dlgSingle').dialog('open').dialog('setTitle','修改一单一议额度');
}

function save(){
   var CREDIT_ID = $("#CREDIT_ID").val();
   var SUP_ID = $("#SUP_ID").val();
   var INCREASE_AMOUNT = $("#INCREASE_AMOUNT").val();
   var UPSINGLE_AMOUNT = $("#UPSINGLE_AMOUNT").val();
   jQuery.ajax({
		url : _basePath+"/base/channel/CreditAmountManager!upCreditAmount.action",
		data : "CREDIT_ID="+CREDIT_ID+"&SUP_ID="+SUP_ID+"&INCREASE_AMOUNT="+INCREASE_AMOUNT+"&UPSINGLE_AMOUNT="+UPSINGLE_AMOUNT,
		dataType:"json",
		success:function(data){
		    if(data.flag==true){
			   jQuery.messager.alert("提示",data.msg);
			   clear();
			   $('#dlgSingle').dialog('close');
			   $('#dlg').dialog('close');
			}else{
			   jQuery.messager.alert("提示",data.msg);
			}
			$('#pageTable').datagrid('reload');
		}
   });
}

function clear(){
   $("#SUP_SHORTNAME").text("");
   $("#INITIAL_AMOUNT").text("");
   $("#STANDARD_AMOUNT").text("");
   $("#CANUSE_MONEY").text("");
   $("#USER_MONEY").text("");
   $("#SUP_SHORTNAME_SINGLE").text("");
   $("#SINGLE_AMOUNT").text("");
   $("#USESINGLE_MONEY").text("");
   $("#INCREASE_AMOUNT").val("");
   $("#UPSINGLE_AMOUNT").val("");
   $("#CREDIT_ID").val("");
   $("#SUP_ID").val(""); 
}