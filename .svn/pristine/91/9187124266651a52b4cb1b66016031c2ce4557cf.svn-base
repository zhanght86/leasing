$(document).ready(function(){
	var LEASE_CODE = $("#LEASE_CODE").val();
	$("#getCarsByLeaseCode").datagrid({
//		rownumbers:true,//左侧自动显示行数
		fitColumns:true,
		fit:false,
		singleSelect: true,
		url:'pickCarStock!getCarsByContractNo.action?LEASE_CODE='+LEASE_CODE,
		/*width:"1000px",
		height:"150px",*/
		columns:[[
		    
		          	{field:'id',title:'资产ID',checkbox:"true"},
		          	{field:'orderItemId',title:'订单子项',hidden:true},
		          	{field:'orderNo',title:'主订单编码',hidden:true},
		          	{field:'orderId',title:'主订单ID',hidden:true},
		          	{field:'baseWareId',title:'所属仓库ID',hidden:true},
		          	{field:'wareName',title:'所属仓库名称',width:120},
		          	{field:'brandId',title:'品牌ID',hidden:true},
		          	{field:'brandName',title:'品牌名称',width:120},
		          	{field:'seriesId',title:'系列ID',hidden:true},
		          	{field:'seriesName',title:'系列名称',width:120},
		          	{field:'modelId',title:'型号ID',hidden:true},
		          	{field:'modelName',title:'型号名称',width:120},
		          	{field:'colorId',title:'颜色ID',hidden:true},
		          	{field:'colorName',title:'颜色名称',width:120},
		          	{field:'vin',title:'车架号',width:120},
		          	{field:'ein',title:'发动机号',width:120},
		          	{field:'licenseAddress',title:'车牌号地域',width:120},
		          	{field:'licenseNo',title:'车牌号码',width:120},
		          	{field:'status',title:'资产状态',hidden:true},
		          	{field:'licensed',title:'是否上牌',hidden:true},
		          	{field:'registered',title:'是否已登记费用',hidden:true},
		          	{field:'customer',title:'客户名称',width:120},
		          	{field:'mobile',title:'手机号',width:120},
		          	{field:'registerStatus',title:'注册状态',width:120},
		          	{field:'paylistCode',title:'支付表号',width:120},
		        	{field:'projectCode',title:'合同号',width:120},
		        	{field:'registerStatusName',title:'交车状态',width:120}
		         ]]
	,
	onCheck: function (index, row) {  //easyui封装好的时间（被单机行的索引，被单击行的值）

	                    //需要传递的值
	                    var customer = row["customer"];
	                    var registerStatus = row["registerStatus"];
	                    var paylistCode =row["paylistCode"];
	                    var projectCode = row["projectCode"];
	                    var LEASE_CODE = $("#LEASE_CODE").val();
	                    var baseWareId = row["baseWareId"];
//	                    if (AssessStatus=="尚未评估") //如果该项尚未评估，则支持跳转到评估页面
//	                    {
//	                        var url = "../EvaluationStudentAssess/StudentAssess?CourseName=" + CourseName + "&TeacherCourseID=" + TeacherCourseID
//	                    }
//	                    else {  //如果该项以经评估则不需要跳转
//	                        var url = "../EvaluationStudentAssess/AssessStatus"
//	                    }
	                   
	                    //通过Ajax传值
	                    $.ajax({
	                        url: 'pickCarStock!selectCar.action?PRO_CODE='+projectCode+"&customer="+customer+"&registerStatus="+registerStatus+"&paylistCode="+paylistCode+"&LEASE_CODE="+LEASE_CODE+"&baseWareId="+baseWareId,
	                        type: 'POST',
	                        async:false,
	                        dataType:"json",
	                        success: function (data) {
//	                        	alert(data.fag +""+data.msg);
	                        	if(data.flag==true){
	                        		alert("OK"+data.msg);
//	                        		$("#getCarsByLeaseCode").datagrid("selectRow", index);
	                        	}else{
	                        		alert(data.msg);
	                        		$("#getCarsByLeaseCode").datagrid('clearSelections')
	                        	}
	                        }
	                    });    
	                }
	});
});

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
	 
	 $("#qianshoudanUpdDiv").dialog('close');
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
	if(rowData.HANDOVER_FILE==undefined && rowData.HANDOVER_DATE==undefined){
		return "<a href='javascript:void(0);' onclick=openAddQianshouDan('"+rowData.PAYLIST_CODE+"')>添加签收单</a>&nbsp;|&nbsp;" +
		 "<a href='javascript:void(0);' onclick=\"downLoadPayList('"+rowData.SCHEME_ROW_NUM+"','"+rowData.PROJECT_ID+"','"+rowData.SCHEME_ID+"','"+rowData.PAY_ID+"')\">导出还款计划</a>";
	}
	//modify by lishuo 03-07-2016 添加签收单验证 Start
	if(rowData.HANDOVER_FILE!=null || rowData.HANDOVER_FILE!='' || rowData.HANDOVER_DATE!=null || row.Data.HANDOVER_DATE !=''){
		return "<a href='javascript:void(0);' onclick=alterAddQianshouDan()>添加签收单</a>&nbsp;|&nbsp;" +
		 "<a href='javascript:void(0);' onclick=\"downLoadPayList('"+rowData.SCHEME_ROW_NUM+"','"+rowData.PROJECT_ID+"','"+rowData.SCHEME_ID+"','"+rowData.PAY_ID+"')\">导出还款计划</a>";
	}else{
		return "<a href='javascript:void(0);' onclick=openAddQianshouDan('"+rowData.PAYLIST_CODE+"')>添加签收单</a>&nbsp;|&nbsp;" +
		 "<a href='javascript:void(0);' onclick=\"downLoadPayList('"+rowData.SCHEME_ROW_NUM+"','"+rowData.PROJECT_ID+"','"+rowData.SCHEME_ID+"','"+rowData.PAY_ID+"')\">导出还款计划</a>";
	}
	//modify by lishuo 03-07-2016 添加签收单验证 END
}

//add by lishuo 03-07-2016
function alterAddQianshouDan(){
	$.messager.alert("提示","不可以再次添加，不可再次添加!");
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
 * 更新签收单和签收时间
 * @param PAYLIST_CODE
 */
function openUpdQianshouDan(PAYLIST_CODE){
	$("#qianshoudanUpdDiv").dialog("open");
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
 * 添加/保存签收单 刘丽
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
	//必须要选车
	//资产ID
	var id = "";
	var cjh="";
	var fdjh="";
	var ysmc="";
	var baseWareId="";
    var row = $('#getCarsByLeaseCode').datagrid('getSelected');
    if (row){
    	//资产ID
    	if(row.id == null || row.id ==""){
    		alert("资产ID为空");
    		return false;
    	}else{
    		id = row.id;
    	}
    	//所属仓库ID  当仓库ID=1 的时候 代表该车辆已经出库，不再调用出库接口   add gengchangbao JZZL-new0707
    	baseWareId = row.baseWareId;
    	//车架号
    	cjh =row.vin;
    	//发动机号
    	fdjh=row.ein;
    	//颜色名称
    	ysmc=row.colorName;
    	
    }else{
    	alert('请选择车,只能选择一辆');
    	return false;
    }
    //获取合同编号
    var LEASE_CODE = $("#LEASE_CODE").val();
    if(LEASE_CODE==null || LEASE_CODE ==""){
    	alert("没有合同编号，请联系技术人员,页面隐藏问题");
    	return false;
    }
//	// 签收单需要上传
//	var HANDOVER_FILE = $("#HANDOVER_FILE").val();
//	if(null == HANDOVER_FILE || "" == HANDOVER_FILE){
//		alert("请上传签收单！");
//		return false;
//	}
	 jQuery.ajaxFileUpload({
 	    url: _basePath+ "/pickCarStock/pickCarStock!addQianshoudan.action?PAYLIST_CODE="+PAYLIST_CODE+"&HANDOVER_DATE="+HANDOVER_DATE+"&LEASE_CODE="+LEASE_CODE
 	    +"&id="+id+"&cjh="+cjh+"&fdjh="+fdjh+"&ysmc="+ysmc+"&baseWareId="+baseWareId,
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

/**
 * 修改签收单
 * @returns {Boolean}
 */
function UpdQianshouDan(){

	var PAYLIST_CODE=$("#PAYLIST_CODE_ADD").val();
	// 签收日期必填
	var HANDOVER_DATE=$("#HANDOVER_DATE1").datebox('getValue');
	if(null == HANDOVER_DATE || "" == HANDOVER_DATE){
		alert("请填写签收日期！");
		return false;
	}
	
    //获取合同编号
    var LEASE_CODE = $("#LEASE_CODE").val();
    if(LEASE_CODE==null || LEASE_CODE ==""){
    	alert("没有合同编号，请联系技术人员,页面隐藏问题");
    	return false;
    }
//	// 签收单需要上传
//	var HANDOVER_FILE = $("#HANDOVER_FILE").val();
//	if(null == HANDOVER_FILE || "" == HANDOVER_FILE){
//		alert("请上传签收单！");
//		return false;
//	}
	 jQuery.ajaxFileUpload({
 	    url: _basePath+ "/pickCarStock/pickCarStock!addQianshoudan.action?PAYLIST_CODE="+PAYLIST_CODE+"&HANDOVER_DATE="+HANDOVER_DATE+"&LEASE_CODE="+LEASE_CODE+"&flag=1",
 	    secureuri:false,
 	    fileElementId:"HANDOVER_FILE",
 	    dataType: "json",
 	    success: function (s){
				 if(s.flag){
					 alert("操作成功！");
					 $("#qianshoudanUpdDiv").dialog("close");
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
	if(rowData.HANDOVER_FILE==undefined && rowData.HANDOVER_DATE==undefined){
		return "";
	}

	if((rowData.HANDOVER_FILE!=undefined&&rowData.HANDOVER_FILE!=null&&rowData.HANDOVER_FILE!='') || (rowData.HANDOVER_DATE!=undefined&&rowData.HANDOVER_DATE!=null &&rowData.HANDOVER_DATE!=''))
		//modify by lishuo 04-19-2016 签收单可修改 Start  
		return "<a href='"+_basePath+"/buyCertificate/BuyCertificate!downqianshoufile.action?HAND_FILE="+rowData.HANDOVER_FILE+"'>签收单查看</a>&nbsp;|&nbsp;" +
				"<a href='javascript:void(0);' onclick=openUpdQianshouDan('"+rowData.PAYLIST_CODE+"')>修改签收单</a>";
	    //modify by lishuo 04-19-2016 签收单可修改 End
	else
		return "";
}