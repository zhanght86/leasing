////设置默认可放款金额总计
//$(function(){
////	$('#pageTable').datagrid("loadData");
////	var detailData = $("#pageTable").datagrid('getSelections');
////	var money=0;
////	for(var i = 0; i<detailData.length; i++) {
////		money+=accAdd($(this).attr("SUM_MONEY"),money);
////		checked= checked+1;
////	}
////	$("#ActualMoney").text(money);
////	$("#PRODUCT_PROJECT_MONEY").val(money);
//	var money=0;
//	$("#getScreened tr[name='payid']").click(function(){
//		money+=accAdd($(this).attr("SUM_MONEY"),money);
//	});
//	$("#ActualMoney").text(money);
//	alert($("#ActualMoney").val());
//	$("#PRODUCT_PROJECT_MONEY").val(money);
//});

/**
 * 搜索
 */
function toSeacher(){
	jQuery.ajax({
		url: _basePath + '/screened/Screened!payConformScreened.action',
		type:'post',
		data:"param="+getFromData("#pageForm"),
		dataType:'json',
		success:function(msg){
			//融资筛选条件数据
		   var data = msg.data.getScreened;
		   
		   //融资机构数据
		   var agency = msg.data.agency;		   
		   $("#AGENCY_ID").val(agency.ID);//融资机构id
		   $("#AGENCY_NAME").val(agency.ORGAN_NAME);//融资机构名称
		   $("#AGENCY_MONEY").val(agency.BALANCE);//余额
		   $("#AGENCY_TOTAL_CREDIT").val(agency.TOTAL_CREDIT);//授信总额度
		   
		   //融资方式相关信息
		   var param = msg.data.param;
		   $("#PRODUCT_PAY_WAY").val(param.PAY_WAY);//
		   $("#PRODUCT_PAY_BASE").val(param.PAY_BASE);//融资机构名称
		   $("#PRODUCT_PROJECT_MONEY").val(param.ActualMoney);//余额
		   $("#PRODUCT_CLIENT_NAME").val(param.CLIENT_NAME);//客户名称		   
		   $("#PRODUCT_FIRST_SCALE").val(param.PAY_LISTCODE);//支付表	   
		   
		   var data = {
	                flag: msg.flag,
	                total: data.length,
	                rows: data
	            };
		   $('#pageTable').datagrid("loadData", data);
		}
	});
}

/**
 * 列表-操作
 * @param val
 * @param row
 * @return
 */
function operattion(val,row){
	if(row){
		return "<a href='javascript:void(0)' class='easyui-linkbutton' onclick='checkCondition("
			+ "\""+ row.PAYLIST_CODE
			+ "\")'>查看条件</a>  |  <a href='javascript:void(0)' class='easyui-linkbutton' onclick='doAddPayIdToSession("
			+ row.RENT_ID+ ",\""+ row.SUM_MONEY+ "\")'>加入购物车</a>";
	}
}

//判断资料是否齐全
function judgeData(val,row){
	if(val=="1"){
		return "资料齐全";
	}else {
		return "资料不齐全";
	}
}


/**
 * 清空查询数据
 * @author 杨雪
 * @return
 */
function cleanse(){
	$("#START_DATE").datebox('clear');
	$("#PLAN_DATE").datebox('clear');
	$("#PLAN_DATE1").datebox('clear');
	$("#TICKET_DATE").datebox('clear');
	$("#TICKET_DATE1").datebox('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}

/**
 * 选择项目并计算金额
 * @return
 */
function onChangeSelect(){
	var datagridList=$("#pageTable").datagrid('getSelections');
	var SUM_MONEYAll=0;
	var NUM=0;
	for(var i = 0; i < datagridList.length; i++)
	{
		datagridList[i].RENT_ID.checkbox = true;
		
		var SUM_MONEY=datagridList[i].SUM_MONEY;
		SUM_MONEYAll=accAdd(SUM_MONEYAll,SUM_MONEY);		
		NUM++;
	}
	$("#ActualMoney").text(SUM_MONEYAll);//可放放款总金额
	$("#PRODUCT_PROJECT_MONEY").val(SUM_MONEYAll);//余额
}

//创建项目-弹出框
function addallProject(dialogid){
	var product_id = $("#PAY_WAY").attr("selected",true).val();
	var PROJECT_MONEY = $("#PROJECT_MONEY").val();
	var AGENCY_MONEY = $("#AGENCY_MONEY").val();
	if(product_id==null || product_id==''){
		alert("请选择融资产品");
		return ;
	}else if(PROJECT_MONEY>AGENCY_MONEY){
		alert("融资机构授信额度不够！");
		return ;
	}else{
		$.ajax({
			   url: _basePath+"/screened/Screened!projectCode.action",
			  type: "post",			   
			  dataType:"json",
			  error: function(){
			   alert("操作失败！");
			  },
			  success: function(json){
				  $("#PROJECT_CODE").val(json.msg);
			  }
			 });
		$("#toAddRePro").dialog("open");
	}
}

//创建项目操作-融资筛选
function doAddRePro(){
	var checked=0;
	var payid="";
	var proid="";
	var detailData = $("#pageTable").datagrid('getSelections');
	for(var i = 0; i<detailData.length; i++) {
		payid+=detailData[i].RENT_ID+",";
		proid+=detailData[i].FPH_ID+",";
		checked= checked+1;
	}
	var  PROJECT_NAME = $("#PROJECT_NAME").val();
	var  PROJECT_CODE = $("#PROJECT_CODE").val();
	if(PROJECT_NAME==""||PROJECT_NAME==null){
		alert("请填写必填项！");
	}
	if(checked>=1){
	    var dataJson={
		   payid:payid,
		   proid:proid,
		   PROJECT_NAME:PROJECT_NAME,
		   PROJECT_CODE:PROJECT_CODE,
		   PRODUCT_PAY_WAY :$("#PRODUCT_PAY_WAY").val(),
		   PRODUCT_PPAY_BASE : $("#PRODUCT_PPAY_BASE").val(),
		   PRODUCT_PROJECT_MONEY: $("#PRODUCT_PROJECT_MONEY").val(),
		   PRODUCT_CLIENT_NAME : $("#PRODUCT_CLIENT_NAME").val(),
		   PRODUCT_FIRST_SCALE : $("#PRODUCT_FIRST_SCALE").val(),
		   AGENCY_ID : $("#AGENCY_ID").val(),
		   AGENCY_NAME : $("#AGENCY_NAME").val(),
		   AGENCY_MONEY : $("#AGENCY_MONEY").val(),
		   AGENCY_TOTAL_CREDIT : $("#AGENCY_TOTAL_CREDIT").val()
		};
		$.ajax({
			  type: "post",
			  url: _basePath+"/screened/Screened!createProject.action",
			  data:"dataJson="+JSON.stringify(dataJson),
			  dataType:"json",
			  error: function(){
			   alert("操作失败！");
			  },
			  success: function(json){
				  if(json.flag==true){
					  alert("创建成功");
					  window.location.href = _basePath+"/refundProject/RefundProject.action";
				  }else{
					  alert("创建失败");
					  $("#toAddRePro").dialog("close");
				  }
			  }
	     });
	 }else{
	   alert("请选择支付表");
	 }
}

//创建项目操作-购物车
function doAddRePro1(){
	var checked=0;
	var payid="";
	$('[name=payid]:checkbox:checked').each(function(){
	    payid+=$(this).val()+",";
		checked= checked+1;
	});
	var  PROJECT_NAME = $("#PROJECT_NAME").val();
	var  PROJECT_CODE = $("#PROJECT_CODE").val();
	if(PROJECT_NAME==""||PROJECT_NAME==null){
		alert("请填写必填项！");
	}
	if(checked>=1){
	    var dataJson={
		   payid:payid,
		   PROJECT_NAME:PROJECT_NAME,
		   PROJECT_CODE:PROJECT_CODE,
		   PRODUCT_PAY_WAY :$("#PRODUCT_PAY_WAY").val(),
		   PRODUCT_PPAY_BASE : $("#PRODUCT_PPAY_BASE").val(),
		   PRODUCT_PROJECT_MONEY: $("#PRODUCT_PROJECT_MONEY").val(),
		   PRODUCT_CLIENT_NAME : $("#PRODUCT_CLIENT_NAME").val(),
		   PRODUCT_FIRST_SCALE : $("#PRODUCT_FIRST_SCALE").val(),
		   AGENCY_ID : $("#AGENCY_ID").val(),
		   AGENCY_NAME : $("#AGENCY_NAME").val(),
		   AGENCY_MONEY : $("#AGENCY_MONEY").val(),
		   AGENCY_TOTAL_CREDIT : $("#AGENCY_TOTAL_CREDIT").val()
		};
		$.ajax({
			  type: "post",
			  url: _basePath+"/screened/Screened!createProject.action",
			  data:"dataJson="+JSON.stringify(dataJson),
			  dataType:"json",
			  error: function(){
			   alert("操作失败！");
			  },
			  success: function(json){
				  if(json.flag==true){
					  alert("创建成功");
					  $("#toAddRePro").dialog("close");
				  }else{
					  alert("创建失败");
					  $("#toAddRePro").dialog("close");
				  }
			  }
	     });
	 }else{
	   alert("请选择支付表");
	 }
}

//上传清单
function excel1(div){
	var product_id = $("#PAY_WAY").attr("selected",true).val();
	if(product_id!=null && product_id!=''){
		  $("#FILE_PAY_WAY").val(product_id);
		  $("#FILE_PAY_BASE").val($("#PAY_BASE").attr("selected",true).val());
		  $("#FILE_PROJECT_MONEY").val($("#PROJECT_MONEY").val());
		  $("#FILE_START_DATE").val($("#START_DATE").val());
		  $("#"+div).dialog("open");
	}else{
		  alert("请选择融资产品！");
	}
}

//操作-上传清单
function upload(p,s){
  	var nameq = $("#file").val();
  	if(nameq.substring(nameq.lastIndexOf("."))=='.'+s){
  		$('#frmUploadWebBank').form({
  			url:_basePath+"/screened/Screened!analyticalExcel.action",
  			data:"FILE_PAY_WAY="+$("#FILE_PAY_WAY").val()+"&FILE_PAY_BASE="+$("#FILE_PAY_BASE").val()+"&FILE_PROJECT_MONEY="+$("#FILE_PROJECT_MONEY").val()+"&FILE_START_DATE="+$("#FILE_START_DATE").val(),
  			type:"post",
  			dataType:"json",
  			success:function(json){
  			 //alert(data.getExcelData);
  			 //   alert(msg.data);
  				//融资筛选条件数据
  			  var data_ = eval('(' + json + ')');
  			   var data = data_.data.getExcelData;
  			   
  			   //融资机构数据
  			   var agency = data_.data.agency;		   
  			   $("#AGENCY_ID").val(agency.ID);//融资机构id
  			   $("#AGENCY_NAME").val(agency.ORGAN_NAME);//融资机构名称
  			   $("#AGENCY_MONEY").val(agency.BALANCE);//余额
  			   $("#AGENCY_TOTAL_CREDIT").val(agency.TOTAL_CREDIT);//授信总额度
  			   
  			   //融资方式相关信息
  			   var param = data_.data.param;
  			   $("#PRODUCT_PAY_WAY").val(param.PAY_WAY);//
  			   $("#PRODUCT_PAY_BASE").val(param.PAY_BASE);//融资机构名称
  			   $("#PRODUCT_PROJECT_MONEY").val(param.ActualMoney);//余额
  			   $("#PRODUCT_CLIENT_NAME").val(param.CLIENT_NAME);//客户名称		   
  			   $("#PRODUCT_FIRST_SCALE").val(param.PAY_LISTCODE);//支付表	   
  			   
  			   var data = {
  		            flag: data_.flag,
  		           total: data.length,
  		            rows: data
  		        };
  			   
  			   $('#pageTable').datagrid("loadData", data);
  			   
  			   $("#toAddInventory").dialog('close');
  			}
  			});  		
		$("#frmUploadWebBank").submit();
  	}else{
		alert("请选择"+s+"文件");
	}
}

//查看条件
function checkCondition(PAYLIST_CODE){
	var PAY_WAY = $("#PAY_WAY").attr("selected",true).val();
	jQuery.ajax({
		type:"post",
		url:_basePath+"/screened/Screened!checkCondition.action",
		data:"PAYLIST_CODE="+PAYLIST_CODE+"&PAY_WAY="+PAY_WAY,
		success:function(s){
			$("#toSeacherCondition").empty();
			$("#toSeacherCondition").append(s);
			$("#toSeacherCondition").dialog("open");
		}
	});
}

//添加payid到session
function doAddPayIdToSession(payId,money){	
	jQuery.ajax({
		url:_basePath+"/screened/Screened!doAddPayIdToSession.action?payId="+payId+"&Money="+money,
		type:"post", 
		dataType:"json",
		success:function(data){
			if(data.data.FLAG != "1"){
				alert("重复添加");
			}else{
				$("#payMoney").text("已选数量："+data.data.SIZE+" , 已选金额："+data.data.SUM).css("font-size","12");
			}
		}
	});
}

//清空session 中的 payid
function doClearPayIdToSession(){	
	jQuery.ajax({
		url:_basePath+"/screened/Screened!doClearPayIdToSession.action",
		type:"post", 
		dataType:"json",
		success:function(data){
			$("#payMoney").text("已选数量："+data.data.SIZE+" , 已选金额："+data.data.SUM).css("font-size","12");
		}
	});
}

//弹出已选列表
function goSelectPay(){
	var PAY_WAY=$("#PAY_WAY").val();
	var PAY_BASE=$("#PAY_BASE").val();
	window.open("Screened!goSelectPay.action?PAY_WAY="+PAY_WAY+"&PAY_BASE="+PAY_BASE,'newwindow','height=500,width=1000,top=200,left=200,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no, status=no');
}

//删除session 中的 payid
function doDelPayIdToSession(payId,money){
	var PAY_WAY=$("#PAY_WAY").val();
	var PAY_BASE=$("#PAY_BASE").val();
	
	jQuery.ajax({
		url:"../screened/Screened!doDelPayIdToSession.action?payId="+payId+"&Money="+money,
		type:"post", 
		dataType:"json",
		success:function(data){
			window.open("Screened!goSelectPay.action?PAY_WAY="+PAY_WAY+"&PAY_BASE="+PAY_BASE,'_self','height=500,width=1000,top=200,left=200,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no, status=no');
		}
	});
}

/**
 * 加法
 * @param arg1
 * @param arg2
 * @return
 */
function accAdd(arg1,arg2){
	var r1,r2,m;
	try{r1=arg1.toString().split(".")[1].length;}catch(e){r1=0;}
	try{r2=arg2.toString().split(".")[1].length;}catch(e){r2=0;}
	m=Math.pow(10,Math.max(r1,r2));
	return (arg1*m+arg2*m)/m;
}

/**
 * 关闭弹出窗口
 * @param div
 * @return
 */
function closeAddRePro(div){
	$("#"+div).dialog('close');
}