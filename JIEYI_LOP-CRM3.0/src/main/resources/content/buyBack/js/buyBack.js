
function clear_(){
	 $('#queryForm').form('clear');
}

function se(){
	$("#exportEXCEL").linkbutton('enable');
	var content = {};
	$("#queryForm :input").each(function() {
		if ($(this).attr("name") == undefined)
			return;
		content[$(this).attr("name")] = $(this).val();
	});
	$('#pageTable').datagrid('load', content);
//	var url_=_basePath+"/buyBack/BuyBack!buyBackListing.action";
//	var jsonData = $("#queryForm").serialize();
//	var jsonData = "?page="+$(".pagination-num").val()+"&rows="+$(".pagination-page-list  option:selected").val()+"&"+$("#queryForm").serialize();
//	var parserUrl = _parserUrl(jsonData);
//	jQuery.ajax({
//	   type: "POST",
//	   dataType:"json",
//	   url: url_,
//	   data: jsonData,
//	   success: function(msg){
//	     //alert( "Data Saved: ");
//		 $('#pageTable').datagrid("loadData",msg);
//	   }
//	});
}
function buyBackApply(){
	var selectAll = $('#pageTable').datagrid("getSelections");
	if(selectAll.length!=1){
		$.messager.show({
			title:'操作错误提示',
			msg:'回购申请只允许选择一行',
			showType:'show'
		});
		return false;
	}
	$("#dialog").show();
	$('#dialog').dialog({
		modal:true
	});  
}
function batchBuyBackApply(){
	var date_b = $('#pageTable').datagrid("getRows");
	date_b = date_b[0];
	$("#batchBuyBackParam").find("input[name='SUP_NAME']").val(date_b.SUP_NAME);
	$("#batchBuyBackParam").find("input[name='SUP_ID']").val(date_b.SUP_ID);
	$("#batchBuyBackParam").show();
	$('#batchBuyBackParam').dialog({
		modal:true
	});  
}

function getStatus(value,rowData,rowIndex){
	if(rowData.BUY_BACK_STATUS==0){
		return '预警';
	}else if(rowData.BUY_BACK_STATUS==null||rowData.BUY_BACK_STATUS==""){
		return '正常';
	}else if(rowData.BUY_BACK_STATUS==1){
		return '回购(正常)';
	}
}

function warning(){
	$('#dialog').dialog('close');
	var date_b = $('#pageTable').datagrid("getSelections");
	date_b = date_b[0];
	$("#buyBackParam").find("input[name='PAY_ID']").val(date_b.PAY_ID);
	$("#buyBackParam").find("input[name='BUY_BACK_STATUS']").val(0);
	$("#buyBackParam").find("input[name='NAME']").val(date_b.NAME);
	$("#buyBackParam").find("input[name='PRO_CODE']").val(date_b.PRO_CODE);
	$("#buyBackParam").find("input[name='PAY_CODE']").val(date_b.PAY_CODE);
	$("#buyBackParam").find("input[name='COMPANY_NAME']").val(date_b.COMPANY_NAME);
	$("#buyBackParam").find("input[name='SUP_NAME']").val(date_b.SUP_NAME);
	$("#buyBackParam").find("input[name='PRODUCT_NAME']").val(date_b.PRODUCT_NAME);
	$("#buyBackParam").find("input[name='LEASE_TERM']").val(date_b.LEASE_TERM);
	$("#buyBackParam").find("input[name='SUM_ZJ']").val(date_b.SUM_ZJ);
	$("#buyBackParam").find("input[name='COUNT_']").val(date_b.COUNT_);
	$("#buyBackParam").find("input[name='ACCOUNT_DATE']").val(date_b.ACCOUNT_DATE);
	
	if(date_b.BUY_BACK_STATUS!=null){
		$.messager.show({
			title:'操作错误提示',
			msg:'只有正常的数据才能发起预警',
			showType:'show'
		});
		return false;
	}
	$("#buyBackParam").show();
	$('#buyBackParam').dialog({
		modal:true
	});  
}
function buyBackSubmit(){
	var BUY_BACK_STATUS = 0;//回购的状态
	if(BUY_BACK_STATUS==0){//当状态为0表示回购预警操作
		//var ACCOUNT_DATE = $("#buyBackParam_1").find("input[name='ACCOUNT_DATE']").val();
		var ACCOUNT_DATE = $("#buyBackParam").find("input[name='ACCOUNT_DATE']");
		ACCOUNT_DATE.each(function(){
			if($(this).val()!=null&&$(this).val()!=""){
				ACCOUNT_DATE = $(this).val();
			}
			
		});
		var re=/^\d{4}-\d{2}-\d{2}$/g ;// 
		if(!re.test(ACCOUNT_DATE)){
			$.messager.show({
				title:'操作错误提示',
				msg:'请正确填写结算日',
				showType:'show'
			});
			return false;
		}
		var data_ = $("#buyBackForm").serialize();
		jQuery.ajax({
		   type: "POST",
		   dataType:"json",
		   url: _basePath+"/buyBack/BuyBack!warning.action",
		   data:data_,
		   success: function(msg){
			alert (msg.data);
			window.location.href=_basePath+"/buyBack/BuyBack.action";
		   }
		});
	}
}
function batchBuyBackSubmit(){
	var BUY_BACK_STATUS = 0;//回购的状态
	if(BUY_BACK_STATUS==0){//当状态为0表示回购预警操作
		//var ACCOUNT_DATE = $("#buyBackParam_1").find("input[name='ACCOUNT_DATE']").val();
		var ACCOUNT_DATE = $("#batchBuyBackParam").find("input[name='ACCOUNT_DATE']");
		ACCOUNT_DATE.each(function(){
			if($(this).val()!=null&&$(this).val()!=""){
				ACCOUNT_DATE = $(this).val();
			}
			
		});
		var re=/^\d{4}-\d{2}-\d{2}$/g ;// 
		if(!re.test(ACCOUNT_DATE)){
			$.messager.show({
				title:'操作错误提示',
				msg:'请正确填写结算日',
				showType:'show'
			});
			return false;
		}
		var data_ = $("#batchBuyBackForm").serialize();
		jQuery.ajax({
			type: "POST",
			dataType:"json",
			async:false,
			url: _basePath+"/buyBack/BuyBack!batchWarning.action",
			data:data_,
			success: function(msg){
			alert (msg.data);
			window.location.href=_basePath+"/buyBack/BuyBack.action";
		}
		});
	}
}

//function exportExel(){
//	var data = $('#pageTable').datagrid("getSelections");
//	var PAY_ID = new Array();
//	var flag = true;
//	$(data).each(function(){
//		PAY_ID.push(this.PAY_ID);
//		if(this.BUY_BACK_STATUS==null){
//			flag = false;
//		}
//	});
//	if(!flag){
//		$.messager.show({
//				title:'操作错误提示',
//				msg:'导出的数据必须事先都要有回购操作',
//				showType:'show'
//		});
//		return false;
//	}
//	window.location.href=_basePath+"/buyBack/BuyBack!exportExcel.action?PAY_ID="+PAY_ID.toString();
//}
function exportExel(param){
	
	var data = $('#pageTable').datagrid("getSelections");
	var PAY_CODE = new Array();
	var flag = true;
	$(data).each(function(){
		PAY_CODE.push(this.PAY_CODE);
		if(this.BUY_BACK_STATUS==null){
			flag = false;
		}
	});
	if(!flag){
		$.messager.show({
				title:'操作错误提示',
				msg:'导出的数据必须事先都要有回购操作',
				showType:'show'
		});
		return false;
	}
	//console.info(PAY_CODE.toString());
	if(param=="B19"){
		window.location.href=_basePath+"/buyBack/BuyBack!ExcelFile11.action?PAYLIST_CODE="+PAY_CODE.toString()+"&FILE_NAME=回购通知书.xls&PATH=B19.xls";
	}else if(param=="B30"){
		window.location.href=_basePath+"/buyBack/BuyBack!ExcelFile.action?PAYLIST_CODE="+PAY_CODE.toString()+"&FILE_NAME=通知书（致承租人）.xls&PATH=B30.xls";
	}else if(param=="B31"){
		window.location.href=_basePath+"/buyBack/BuyBack!ExcelFile.action?PAYLIST_CODE="+PAY_CODE.toString()+"&FILE_NAME=债权转让协议书.xls&PATH=B31.xls";
	}else if(param=="B50"){
		window.location.href=_basePath+"/buyBack/BuyBack!ExcelFile.action?PAYLIST_CODE="+PAY_CODE.toString()+"&FILE_NAME=所有权转移证书.xls&PATH=B50.xls";
	}
	
}
function batchExportExel(param){
	var date_b = $('#pageTable').datagrid("getRows");
	date_b = date_b[0];
	window.location.href=_basePath+"/buyBack/BuyBack!ExcelFileBatch.action?SUP_ID="+date_b.SUP_ID+"&SUP_NAME="+date_b.SUP_NAME+"&FILE_NAME=回购通知书.zip&PATH=B19.xls";
}
function refreshWarning(){
	jQuery.ajax({
	   type: "POST",
	   dataType:"json",
	   url: _basePath+"/buyBack/BuyBack!refreshWarning.action",
	   success: function(msg){
		alert (msg.data);
		window.location.href=_basePath+"/buyBack/BuyBack.action";
	   }
	});
}

////正常回购（合计值计算）
//function heji(){
//	var LILV = parseFloat($("#buyBackParam_1").find("input[name=LILV]").val());//利率减免比例
//	var HG_SXF = parseFloat($("#buyBackParam_1").find("input[name=HG_SXF]").val());//回购手续费
//	var liugoujia = parseFloat($("#buyBackParam_1").find("input[name=liugoujia]").val());//留购价
//	var WDQLX_MONEY = parseFloat($("#buyBackParam_1").find("input[name=WDQLX_MONEY]").val());//未到期利息
//	
//	var hx_lv =  Math.round(WDQLX_MONEY * LILV / 100);//可冲抵利率
//	
//	var sy_money = $("#buyBackParam_1").find("input[name='sy_money']").val();//剩余租金
//	var sy_money_ = parseFloat(0);//抵扣收剩余租金
//	
//	
//	//保证金
//	var bzj = parseFloat(0);//可使用保证金
//	var bzj_ = parseFloat(0);//已使用保证金
//	if($("#buyBackParam_1").find("select[name='BZJ']").attr("selected",true).val()==0){//当保证金选择为承租人保证金时， 使用承租人保证金进行租金抵扣
//		bzj = parseFloat($("#buyBackParam_1").find("input[name='BZJ_MONEY']").val());
//	}else {
//		bzj = parseFloat(0);
//	}
//	
//	if(bzj<=sy_money){//当保证金小于等于剩余租金时， 保证金全部使用， 有剩余租金
//		bzj_ = bzj;  
//		sy_money_ = sy_money - bzj;
//	}else if(bzj>sy_money){ //当保证金大于剩余租金时， 保证金有剩余， 租金全部核销 ， 核销收剩余租金为0
//		bzj_ = sy_money ;
//		sy_money_ = parseFloat(0);
//	}else if(bzj==0){//当保证金为0 时， 保证金不做抵扣
//		bzj_ = parseFloat(0);
//		sy_money_ = sy_money;
//	} 
//	
//	$("#BZJDk").val(bzj_);
//	var bzj_1 = $("#BZJDk").val();//保证金抵扣金额	
//	
//	//留购价低租金
//	var lgj = parseFloat(0);//可使用留购价
//	var lgj_ = parseFloat(0);//已使用留购价
//	if($("#buyBackParam_1").find("select[name='isLiugou']").attr("selected",true).val()==0){//是   留购价抵扣
//		lgj = parseFloat($("#buyBackParam_1").find("input[name='LIUGOUJIA']").val());
//	}else { //否留购价不抵扣
//		lgj = parseFloat(0);
//	}
//	
//	if(sy_money_>0){
//		if(lgj==0){//当留购价<=0， 留购价全部使用， 抵扣后的租金有剩余
//			lgj_ = 0;
//			sy_money_ = sy_money_;
//		}else if(lgj <= sy_money_){//当留购价<=剩余租金时， 留购价全部使用， 抵扣后的租金有剩余
//			lgj_ = lgj;
//			sy_money_ -= lgj;
//		}else if(lgj > sy_money_){
//			lgj_ = sy_money_;
//			sy_money_ = 0;
//		}
//	}else if(sy_money_ == 0){//剩余租金 == 0， 留购价抵扣金额为0
//		lgj_ = parseFloat(0);
//	}
//	$("#LG_DKJE").val(lgj_);
//	var lgj_1 = $("#LG_DKJE").val();//留购价抵扣金额
//	
//	//DB保证金
//	var dbbzj = parseFloat(0);//可使用DB保证金
//	var dbbzj_ = parseFloat(0);//已使用DB保证金
//	if($("#buyBackParam_1").find("select[name='DBBZJ']").attr("selected",true).val()==0){//当DB保证金选择为承租人保证金时， 使用承租人保证金进行租金抵扣
//		dbbzj = parseFloat($("#buyBackParam_1").find("input[name='dbBZJ_MONEY']").val());
//	}else {
//		dbbzj = parseFloat(0);
//	}
//	
//	if(sy_money_>0){
//		if(dbbzj<=sy_money){//当DB保证金小于等于剩余租金时， 保证金全部使用， 有剩余租金
//			dbbzj_ = dbbzj;  
//			sy_money_ = sy_money - bzj;
//		}else if(bzj>sy_money){ //当DB保证金大于剩余租金时， 保证金有剩余， 租金全部核销 ， 核销收剩余租金为0
//			dbbzj_ = sy_money ;
//			sy_money_ = parseFloat(0);
//		}else if(bzj==0){//当DB保证金为0 时， 保证金不做抵扣
//			dbbzj_ = parseFloat(0);
//			sy_money_ = sy_money;
//		} 
//	}else {
//		dbbzj_ = parseFloat(0);
//	}
//	$("#dbBZJDk").val(dbbzj_);
//	var dbbzj_1 = $("#dbBZJDk").val();//保证金抵扣金额
//	
////	alert("sy-money == "+parseFloat($("#buyBackParam_1").find("input[name='sy_money']").val())
////			+"    WS_YQ == "
////			+parseFloat($("#buyBackParam_1").find("input[name='WS_YQ']").val())
////			+"    hx_lv == "+hx_lv
////			+"    liugoujia=="+parseFloat($("#buyBackParam_1").find("input[name='liugoujia']").val())
////			+"   HG_SXF === "+parseFloat($("#buyBackParam_1").find("input[name='HG_SXF']").val())
////			+"   taxes === "+parseFloat($("#buyBackParam_1").find("input[name='taxes']").val())
////			+"    bzj_1 === "+bzj_1
////			+"   lgj_1 === "+lgj_1
////			+"   dbbzj_ === "+dbbzj_
////			+(parseFloat($("#buyBackParam_1").find("input[name='sy_money']").val())
////					  +parseFloat($("#buyBackParam_1").find("input[name='WS_YQ']").val())
////					  -parseFloat(hx_lv)
////					  +parseFloat($("#buyBackParam_1").find("input[name='liugoujia']").val())
////					  +parseFloat($("#buyBackParam_1").find("input[name='HG_SXF']").val())
////					  +parseFloat($("#buyBackParam_1").find("input[name='taxes']").val())
////					  -parseFloat(bzj_1)
////					  -parseFloat(lgj_1)
////					  -parseFloat(dbbzj_)));
//	//合计值：
//	var heji = parseFloat(0);
//	heji = parseFloat($("#buyBackParam_1").find("input[name='sy_money']").val())
//		  +parseFloat($("#buyBackParam_1").find("input[name='WS_YQ']").val())
//		  -parseFloat(hx_lv)
//		  +parseFloat($("#buyBackParam_1").find("input[name='liugoujia']").val())
//		  +parseFloat($("#buyBackParam_1").find("input[name='HG_SXF']").val())
//		  +parseFloat($("#buyBackParam_1").find("input[name='taxes']").val())
//		  -parseFloat(bzj_1)
//		  -parseFloat(lgj_1)
//		  -parseFloat(dbbzj_);
//	$("#HEJI").val(heji);
//}
