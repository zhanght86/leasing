//start by wuyanfei
function status(value,rowData){
	var showInfo ;
	if (value=='0') 
		showInfo='已签订' ;
	else if(value=='3')
		showInfo='作废' ;
	else if(value=='4')
		showInfo='复核通过' ;
	else showInfo='正常';
	return showInfo;
}

function check(value,rowData){
	var param = {} ;
	var COUNT2 = rowData.COUNT2 ;
	var BUY_ID = rowData.BUY_ID ;
	var LEASE_CODE =rowData.LEASE_CODE;
	var CUST_SIGN_NAME = rowData.CUST_SIGN_NAME ;
	var PRODUCT_CODE = rowData.LEASE_ORDER_CODE ;
	var PAY_ID = rowData.PAY_ID;
	var PAYLIST_CODE = rowData.PAYLIST_CODE ;
	
	param.BUY_ID=BUY_ID;
	param.LEASE_CODE=LEASE_CODE;
	param.CUST_SIGN_NAME=CUST_SIGN_NAME;
	param.PRODUCT_CODE=PRODUCT_CODE;
	param.PAY_ID=PAY_ID;
	param.PAYLIST_CODE=PAYLIST_CODE ;
	var paramJson = JSON.stringify(param);
	var array = new Array() ;
	array.push(paramJson) ;
	var event1="";
	var event2="";
	if(COUNT2!='0'){
		event1 = "<a href='BuyCertificate!gocertificate_eqiupChoose.action?paramJson="+paramJson+"'>新建</a><br>" ;
	}
	//var arr=JSON.stringify(rowData.Certificate);
	var arr = rowData.Certificate ;
	if(arr != null && arr!="[]"){
		$.each(arr,function(n,value){
			//PAYLIST_CODE&PAY_ID&no&LEASE_CODEE&RENTER_NAME
			event2 += "<a href='javascript:void(0)' onClick=showBuyCertificate('合格证查看','" + value.NO+"','" + rowData.PAYLIST_CODE + "','" + rowData.CUST_SIGN_NAME +"','" + rowData.PAY_ID + "','" + rowData.LEASE_CODE +"')>"+value.NO+"</a><br>" ;
		}) ;
	}	
	return event1+event2 ;
	
}

function checkUndefined(str){
	if(str=="undefined" || str==undefined) return "" ;
	return str ;
}

function showBuyCertificate(tabname,NO,PAYLIST_CODE,CUST_SIGN_NAME,PAY_ID,LEASE_CODE){
	var param = "?NO="+checkUndefined(NO)+"&PAYLIST_CODE="+checkUndefined(PAYLIST_CODE)+"&CUST_SIGN_NAME="+checkUndefined(CUST_SIGN_NAME) + "&PAY_ID="+checkUndefined(PAY_ID)+"&LEASE_CODE="+ checkUndefined(LEASE_CODE);			
	top.addTab(tabname,_basePath+'/buyCertificate/BuyCertificate!certificateCheck.action'+param);
}

function modify(value,rowData){
	var param = {} ;
	var COUNT2 = rowData.COUNT2 ;
	var BUY_ID = rowData.BUY_ID ;
	var LEASE_CODE =rowData.LEASE_CODE;
	var CUST_SIGN_NAME = rowData.CUST_SIGN_NAME ;
	var PRODUCT_CODE = rowData.LEASE_ORDER_CODE ;
	var PAY_ID = rowData.PAY_ID;
	var PAYLIST_CODE = rowData.PAYLIST_CODE ;
	
	param.BUY_ID=BUY_ID;
	param.LEASE_CODE=LEASE_CODE;
	param.CUST_SIGN_NAME=CUST_SIGN_NAME;
	param.PRODUCT_CODE=PRODUCT_CODE;
	param.PAY_ID=PAY_ID;
	param.PAYLIST_CODE=PAYLIST_CODE ;
	var paramJson = JSON.stringify(param);
	var event="" ;
	var arr = rowData.Certificate ;
	//var arr=JSON.stringify(cerList);
	//debugger ;
	if(arr!=null && arr!="[]"){
		$(arr).each(function(index){
			var url = 'BuyCertificate!certificateUpdate.action' ;
			event += "<a href='"+url+"?paramJson="+paramJson+"&NO="+arr[index].NO+"'>"+arr[index].NO+"</a><br>";
		});
	}
	return event ;
}

function saveCertificateMenthod(v){
	
	
	var flag = true;
	$(".uploadfile").each(function(){
	
		var ru = $(this).val().split(".");
		var typeName = ru[1].toUpperCase();
		var type = ["PNG","JPG","GIF"];
		if (typeName=="PNG"||typeName=="JPG"||typeName=="GIF"){

		} else {
			alert("只能上传"+type+"文件");
			flag = false;
			return false;
		} 
	});
	if(flag){
		if(!$("#certificateEntry").validationEngine({returnIsValid:true})){
			return false;
		}
		$("#doSave").attr("disabled","disabled");
		var cd=$("#code").val();
		var model=$("#model").val();
		if(cd==null||cd==''){
			alert("合格证编号不能为空");
			$("#doSave").removeAttr("disabled");
			return false;
		}
		if(confirm("确定保存吗？")){
			   $("#status").val(v);
			   var temp={}; 
			  // debugger ;
			   $("#certificateEntry input").each(function(){
				   var name = $(this).attr("name") ;
				   var value = $(this).val() ;
				   if(name!=undefined){
					   temp[name]=value;
				   }
			   });
			   var  paramJson = JSON.stringify(temp);
			   var url='BuyCertificate!insertCertificate.action' ;
			   jQuery.ajax({
				 	type:"post",
					url: url ,
					data:"paramJson="+paramJson,
					dataType:"json",
					success:function(s){
					 if(s.flag==true){
						 alert(s.msg);
						 window.location.href="BuyCertificate!execute.action";
					 }else{
						 alert(s.msg);
					 }
						
					}
				 });
		 }else{
			 $("#doSave").removeAttr("disabled");
			 return false;
		 }
	}
}

function updateCertificateMenthod(v){
	

	if($("#certificateEntry").form('validate'))
		
	
	var cd=$("#CERTIFICATE_NO").val();
	if(cd==null||cd==''){
		alert("合格证编号不能为空！");
		return false;
	}
	
	var bool=true;
	$('[name=uploadfile]').each(function(){
		alert(this).val();
		var ru = $(this).val().split(".");
    	var typeName = ru[1].toUpperCase();
    	var type2 = ["PNG","JPG","GIF"];
    	if (typeName=="PNG"||typeName=="JPG"||typeName=="GIF"){
    		
    	} else {
    		alert("只能上传"+type2+"文件");
    		bool=false;
            return false;
    	}
	});
	
	if(bool)
	{
		if(confirm("确定保存吗？")){
			
			$("#status").val(v);
			String.prototype.trim = function () {  
		        return this.replace(/^\s*((.|\n)*\S)?\s*$/, '$1' );  
		    }  
			var cn=$("#CERTIFICATE_NO").val();
			var cn1=$("#CERTIFICATE_NO").val(cn.trim());
			var temp = {} ;
			  $("#certificateUpdate input").each(function(){
				   var name = $(this).attr("name") ;
				   var value = $(this).val() ;
				 
				   if(name!=undefined){
					   temp[name]=value;
				   }
			   });
			   var  paramJson = JSON.stringify(temp);
			   var url='BuyCertificate!subcertificateInfo.action' ;
			   jQuery.ajax({
				 	type:"post",
					url: url ,
					data:"paramJson="+paramJson,
					dataType:"json",
					success:function(s){
					 if(s.flag==true){
						 alert(s.msg);
						 window.location.href="BuyCertificate!execute.action";;
					 }else{
						 alert(s.msg);
					 }
						
					}
			});
		}else{
			return false;
		}
	}
	
}


$.extend($.fn.validatebox.defaults.rules, {

    checkCode: {
        validator: function(value){
            return CheckCode(value) ;
        },
        message: '合格证编号重复'
    },
    checkCarSysmle:{
        validator: function(value){
            return CheckCarSysmle(value);
        },
        message: '车架号重复'
    },
    checkEngineType:{
    	validator: function(value){
            return CheckEngineType(value);
        },
        message: '发动机型号重复'
    }
});

function CheckCode(code){
	var f = false ;
	var handler = $("#handler").val() ;
	
	if(handler=="edit"){
		var initCertificateNo = $("#initCertificateNo").val() ;	
		if(code==initCertificateNo){
			return true ;
		}
	}
	
	jQuery.ajax({
	 	type:"post",
		url: "BuyCertificate!checkCode.action",
		data:"code="+code,
		dataType:"json",
		async : false,
		success:function(s){
				f = s.flag ;
			}
		});
		return f ;
}
function CheckCarSysmle(code){
	var f = false ;
	var handler = $("#handler").val() ;
	
	if(handler=="edit"){
		var initCarSymbol = $("#initCarSymbol").val() ;	
		if(code==initCarSymbol){
			return true ;
		}
	}
	
	jQuery.ajax({
		type:"post",
		url:"BuyCertificate!CheckCarSysmle.action",
		data:"code="+code,
		dataType:"json",
		async : false,
		success:function(s){
			 f = s.flag ;
	  	}
	});
	
	return f ;
}
function CheckEngineType(code){
	var f = false ;
	var handler = $("#handler").val() ;
	
	if(handler=="edit"){
		var initEngineType = $("#initEngineType").val() ;	
		if(code==initEngineType){
			return true ;
		}
	}
	
	jQuery.ajax({
		type:"post",
		url:"BuyCertificate!CheckEngineType.action",
		data:"code="+code,
		dataType:"json",
		async : false,
		success:function(s){
		 f=s.flag;
	  }
	});
	return f ;
}


$(function(){
	 $("#equipcertificate").click(function(){
		 var checked = 0;
		 var EQUIPMENT_ID="";
		 var code=null;
		 $('[name=equipments]:checkbox:checked').each(function(){
			    EQUIPMENT_ID+=$(this).val()+",";
			    code = $(this).attr("buycode");
			 checked = checked+1;		 
		 });
		 if(checked>1){
			 alert("只能选择一条设备");
			 return false;
		 }
		 if(checked>=1){
			 var payPro=[];
			 var dataJson ={
				EQUIPMENT_ID:EQUIPMENT_ID
			 };
			 $("#equipmentids").val(JSON.stringify(dataJson));
			 $("#buyleasecode").val(code);
			 $("#certificate").submit();
		 }else{
			alert("请选择设备！"); 
		 }
	 });
	 
	 $("#qianshoudanAddDiv").dialog('close');
	});

function addFilesDoc(){
	var temp=$("<div class='addfile'><input type='file' name='uploadfile' class='uploadfile' id='upfile' size='10' />&nbsp;&nbsp;<a href='javascript:void(0)' class='deletes'>删除</a></div>");
	$("#file").append(temp);
}
delFileDiv = function(obj){
	$j(obj).parent().remove();
}
$(function(){
	$("#file").click(function(e){
		if($(e.target).is(".deletes")){
			$(e.target).parents(".addfile").remove();
		}
	});
});

function clear_(){
	 $('#queryForm').form('clear');
}
function se(){
	var content = {};
	$("#queryForm :input").each(function() {
		if ($(this).attr("name") == undefined)
			return;
		content[$(this).attr("name")] = $(this).val();
	});
	$('#pageTable').datagrid('load', content);
}

function hidtext(){
	$("#dateinfo").hide();
	$("#certifi").show();
}

function  goCertificateManagerPage(){
	var url = _basePath+ "/buyCertificate/BuyCertificate!execute.action";
	jQuery.ajax(url);
	
}

function closetab() {          
            var p = $(window.parent.document.getElementsByClassName("tabs-selected"));
            var tagName = $($(p).find('span')[0]).text(); 
           
           top.closeTab(tagName);
        }
//end by wuyanfei 

/** -- yipan modified 2016-01-26 14:43:03 start -- **/
function qianshou(value,rowData){
	//modify by lishuo 03-07-2016 添加签收单验证 Start
	if(rowData.HANDOVER_FILE==null || rowData.HANDOVER_FILE==''){
		return "<a href='javascript:void(0);' onclick=openAddQianshouDan('"+rowData.PAYLIST_CODE+"')>添加签收单</a>&nbsp;|&nbsp;" +
		 "<a href='javascript:void(0);' onclick=\"downLoadPayList('"+rowData.SCHEME_ROW_NUM+"','"+rowData.PROJECT_ID+"','"+rowData.SCHEME_ID+"','"+rowData.PAY_ID+"')\">导出还款计划</a>";
	}else{
		return "<a href='javascript:void(0);' onclick=alterAddQianshouDan()>添加签收单</a>&nbsp;|&nbsp;" +
		 "<a href='javascript:void(0);' onclick=\"downLoadPayList('"+rowData.SCHEME_ROW_NUM+"','"+rowData.PROJECT_ID+"','"+rowData.SCHEME_ID+"','"+rowData.PAY_ID+"')\">导出还款计划</a>";
	}
	//modify by lishuo 03-07-2016 添加签收单验证 END
}

//add by lishuo 03-07-2016
function alterAddQianshouDan(){
	$.messager.alert("提示","此单已存在签收单，不可再次添加!");
}
/**
 * 开启添加签收单弹出框
 * @param PAYLIST_CODE	还款计划编号
 */
function openAddQianshouDan(PAYLIST_CODE){
	$("#qianshoudanAddDiv").dialog("open");
	$("#PAYLIST_CODE_ADD").val(PAYLIST_CODE);
}

/**
 * 下载还款计划表Excel
 * @param SCHEME_ROW_NUM	同一项目不同方案下包含的设备
 * @param PROJECT_ID		项目ID
 * @param SCHEME_ID			方案
 * @param PAY_ID			支付标号
 */
function downLoadPayList(SCHEME_ROW_NUM, PROJECT_ID, SCHEME_ID, PAY_ID){
	location.href = _basePath + "/project/project!downLoadPayList.action?SCHEME_ROW_NUM="+SCHEME_ROW_NUM+"&PROJECT_ID="+PROJECT_ID+"&SCHEME_ID="+SCHEME_ID+"&PAY_ID="+PAY_ID;
}

/**
 * 添加/保存签收单
 * @returns {Boolean}
 */
function AddQianshouDan(){
	var PAYLIST_CODE=$("#PAYLIST_CODE_ADD").val();
	// 签收日期必填
	var HANDOVER_DATE=$("#HANDOVER_DATE").datebox('getValue');
	if(null == HANDOVER_DATE || "" == HANDOVER_DATE){
		alert("请填写签收日期！");
		return false;
	}
	// 签收单需要上传
//	var HANDOVER_FILE = $("#HANDOVER_FILE").val();
//	if(null == HANDOVER_FILE || "" == HANDOVER_FILE){
//		alert("请上传签收单！");
//		return false;
//	}
	 jQuery.ajaxFileUpload({
 	    url: _basePath+ "/buyCertificate/BuyCertificate!addQianshoudan.action?PAYLIST_CODE="+PAYLIST_CODE+"&HANDOVER_DATE="+HANDOVER_DATE,
 	    secureuri:false,
 	    fileElementId:"HANDOVER_FILE",
 	    dataType: "json",
 	    success: function (s){
				 if(s.flag){
					 alert("操作成功！");
					 $("#qianshoudanAddDiv").dialog("close");
					 $("#pageTable").datagrid('reload');
				 }else{
					 alert(s.msg);
				 }
 	    },
     	error: function (callback){
 	    	alert("上传失败,请重新选择");
 	    }
	 });
}
/** -- yipan modified 2016-01-26 14:43:03 end -- **/
function qianshoufile(value,rowData){
	if(rowData.HANDOVER_FILE==null||rowData.HANDOVER_FILE=='')
		return "";
	else
		//modify by lishuo 04-19-2016 签收单可修改 Start  
		return "<a href='"+_basePath+"/buyCertificate/BuyCertificate!downqianshoufile.action?HAND_FILE="+rowData.HANDOVER_FILE+"'>签收单查看</a>&nbsp;|&nbsp;" +
				"<a href='javascript:void(0);' onclick=openAddQianshouDan('"+rowData.PAYLIST_CODE+"')>修改签收单</a>";
	    //modify by lishuo 04-19-2016 签收单可修改 End
}