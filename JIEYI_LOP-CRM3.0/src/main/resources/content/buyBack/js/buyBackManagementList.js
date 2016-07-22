
//如果为数字返回当前数字,否则返回0
function isNum(obj){
	var reg = new RegExp("^[0-9]*$");
	if(reg.test(obj)) return obj;
	return 0;
}

function getBeginningNum1(value,rowData,rowIndex){
	var LEASE_TERM = isNum(rowData.LEASE_TERM) ;
	var BEGINNING_NUM = isNum(rowData.BEGINNING_NUM) ;
	return LEASE_TERM-BEGINNING_NUM;
}

function getBeginningNum2(value,rowData,rowIndex){
	var SUM_ZJ = isNum(rowData.SUM_ZJ) ;
	var BEGINNING_PAID = isNum(rowData.BEGINNING_PAID) ;
	return SUM_ZJ-BEGINNING_PAID;
}

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
	
	var date_b = $('#pageTable').datagrid("getSelections");
	date_b = date_b[0];	
	if(date_b.STATUS=='5'||date_b.STATUS=='55'){
		$.messager.show({
			title:'操作错误提示',
			msg:'该项目已经发起回购流程， 请选择为发起正常回购流程的数据',
			showType:'show'
		});
		return false;
	}else{
		//判断是否在有数据在核销流程中或者在虚拟核销中，不能提前结清
		$.ajax({
    		url:_basePath+"/pay/PayTask!doPayListCodeIng.action?PAYLIST_CODE="+date_b.PAY_CODE,
    		type:"post",
    		dataType:"json",
    		success:function (json){
    			if(json.flag){
    				window.location.href=_basePath + "/buyBack/BuyBack!buyBackShow.action?PAYLIST_CODE="+date_b.PAY_CODE+"&ID="+date_b.PAY_ID;
    			}else{
    				alert("该还款计划正在核销中或者虚拟核销未退款，不能发起回购！");
    			}
    		}
    	});
		
	}
}

/**
 * 项目状态
 * @param value
 * @param rowData
 * @param rowIndex
 * @return
 */
function getStatus(value,rowData,rowIndex){
	if(rowData.STATUS=='5'){
		return '回购';
	}else if(rowData.STATUS=='55'){
		return '回购中';
	}else{
		return '正常';
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

/**
 * 正常回购
 * @return
 */
function buyBackNormal(){
	$('#dialog').dialog('close');
	var date_b = $('#pageTable').datagrid("getSelections");
	date_b = date_b[0];	
	if(date_b.STATUS==5||date_b.BUY_BACK_STATUS==55){
		$.messager.show({
			title:'操作错误提示',
			msg:'该项目已经发起回购流程， 请选择为发起正常回购流程的数据',
			showType:'show'
		});
		return false;
	}else{
		$.ajax({
			url:_basePath+"/buyBack/BuyBack!toFindHGData.action?PAYLIST_ID="+date_b.PAY_ID+"&PRO_CODE="+date_b.PRO_CODE,
			type:"post",
			dataType:"json",
			success:function(json){
				//debugger ;
				$("#buyBackParam_1").find("input[name='PROJECT_ID']").val("");
				$("#buyBackParam_1").find("input[name='PAY_ID']").val("");
				$("#buyBackParam_1").find("input[name='PAYLIST_CODE']").val("");
				$("#buyBackParam_1").find("input[name='SUPPLIER_ID']").val("");
				$("#buyBackParam_1").find("input[name='CLIENT_ID']").val("");
				$("#buyBackParam_1").find("input[name='CLIENT_TYPE']").val("");
				
				$("#buyBackParam_1").find("input[name='RENT_PAID_AMOUNT']").val(0);
				$("#buyBackParam_1").find("input[name='BUY_BACK_STATUS']").val(0);
				$("#buyBackParam_1").find("input[name='NAME']").val("");
				$("#buyBackParam_1").find("input[name='LEASE_CODE']").val("");
				$("#buyBackParam_1").find("input[name='COMPANY_NAME']").val("");
				$("#buyBackParam_1").find("input[name='SUP_NAME']").val("");
				$("#buyBackParam_1").find("input[name='PRODUCT_NAME']").val("");
				$("#buyBackParam_1").find("input[name='LEASE_TERM']").val("");
				$("#buyBackParam_1").find("input[name='SUM_ZJ']").val(0);
				$("#buyBackParam_1").find("input[name='START_DATE']").val("");
				$("#buyBackParam_1").find("input[name='COUNT_']").val(0);
				$("#buyBackParam_1").find("input[name='ACCOUNT_DATE']").val("");
				$("#buyBackParam_1").find("input[name='TYPE_NAME']").val("");//型号
				$("#buyBackParam_1").find("input[name='WHOLE_ENGINE_CODE']").val("");//出厂编号
				$("#buyBackParam_1").find("input[name='first_money']").val(0);//首期租金
				$("#buyBackParam_1").find("input[name='MQ_MONEY']").val(0);//每期租金
				$("#buyBackParam_1").find("input[name='LEASE_TOPRIC']").val(0);//租赁物总价值
				$("#buyBackParam_1").find("input[name='YS_MONEY']").val(0);//已交租金
				$("#buyBackParam_1").find("input[name='sy_money']").val(0);//剩余租金
				
				$("#buyBackParam_1").find("input[name='PROJECT_ID']").val(date_b.PROJECT_ID);
				$("#buyBackParam_1").find("input[name='PAY_ID']").val(date_b.PAY_ID);
				$("#buyBackParam_1").find("input[name='PAYLIST_CODE']").val(date_b.PAY_CODE);
				$("#buyBackParam_1").find("input[name='SUPPLIER_ID']").val(date_b.SUP_ID);
				$("#buyBackParam_1").find("input[name='CLIENT_ID']").val(date_b.CLIENT_ID);
				$("#buyBackParam_1").find("input[name='CLIENT_TYPE']").val(date_b.TYPE);
				
				$("#buyBackParam_1").find("input[name='RENT_PAID_AMOUNT']").val(date_b.BEGINNING_NUM);
				$("#buyBackParam_1").find("input[name='BUY_BACK_STATUS']").val(0);
				$("#buyBackParam_1").find("input[name='NAME']").val(date_b.NAME);
				$("#buyBackParam_1").find("input[name='LEASE_CODE']").val(date_b.LEASE_CODE);
				$("#buyBackParam_1").find("input[name='COMPANY_NAME']").val(date_b.COMPANY_NAME);
				$("#buyBackParam_1").find("input[name='SUP_NAME']").val(date_b.SUP_NAME);
				$("#buyBackParam_1").find("input[name='PRODUCT_NAME']").val(date_b.PRODUCT_NAME);
				$("#buyBackParam_1").find("input[name='LEASE_TERM']").val(date_b.LEASE_TERM);
				$("#buyBackParam_1").find("input[name='SUM_ZJ']").val(date_b.SUM_ZJ);
				$("#buyBackParam_1").find("input[name='START_DATE']").val(date_b.START_DATE);
				$("#buyBackParam_1").find("input[name='COUNT_']").val(date_b.COUNT_);
				$("#buyBackParam_1").find("input[name='ACCOUNT_DATE']").val(date_b.ACCOUNT_DATE);
				$("#buyBackParam_1").find("input[name='TYPE_NAME']").val(validValue(json.data.project.SPEC_NAME));//型号
				$("#buyBackParam_1").find("input[name='WHOLE_ENGINE_CODE']").val(validValue(json.data.project.WHOLE_ENGINE_CODE));//出厂编号
				$("#buyBackParam_1").find("input[name='first_money']").val(validValue(json.data.rentData.first_money));//首期租金
				$("#buyBackParam_1").find("input[name='MQ_MONEY']").val(validValue(json.data.rentData.MQ_MONEY));//每期租金
				$("#buyBackParam_1").find("input[name='LEASE_TOPRIC']").val(validValue(json.data.project.LEASE_TOPRIC));//租赁物总价值
				$("#buyBackParam_1").find("input[name='YS_MONEY']").val(date_b.BEGINNING_PAID);//已交租金
				$("#buyBackParam_1").find("input[name='sy_money']").val(date_b.WH_BJ);//剩余租金
				
				
				$("#buyBackParam_1").find("input[name='WS_YQ']").val(date_b.PENALTY_RECE);//违约金
				
				//未收利息
				var wh_lx  = sy_money - parseFloatFormat(isNum(date_b.WH_BJ));
				
				$("#buyBackParam_1").find("input[name='WDQLX_MONEY']").val(wh_lx);//未到期利息
				$("#buyBackParam_1").find("input[name='BZJ_MONEY']").val(validValue(json.data.bzj.BZJ));//保证金
				$("#buyBackParam_1").find("input[name='dbBZJ_MONEY']").val(validValue(json.data.bzj.DBBZJ));//DB保证金
				$("#buyBackParam_1").find("input[id='LIUGOUJIA_']").val(validValue(json.data.project.STAYBUY_PRICE));//留购价
				
				//保证金抵扣
				var bzjdk = parseFloatFormat(isNum(json.data.bzj.BZJ));
				var bzjdk_ = parseFloatFormat(0);//实际抵扣金额(使用)
				
				var sy_money_ = parseFloatFormat(0);//剩余租金（保证金抵扣后剩余租金）
				
				if(bzjdk<=sy_money){//当保证金小于等于剩余租金时， 保证金全部使用， 有剩余租金
					bzjdk_ = bzjdk;
					sy_money_ = sy_money - bzjdk;
				}else {  //当保证金大于剩余租金时， 保证金有剩余， 租金全部核销
					bzjdk_ = sy_money ;
					sy_money_ = parseFloatFormat(0);
				}
				$("#buyBackParam_1").find("input[name='BZJDk']").val(bzjdk_);//保证金抵扣
				
				//留购价抵扣
				var lgjdk = parseFloatFormat(isNum(json.data.bzj.LIUGOUJIA));
				var lgjdk_ = parseFloatFormat(0);//实际抵扣金额
				
				if(sy_money_>0){//当剩余租金>0时， 调用留购价款
					if(lgjdk<=sy_money_){//当留购价<=剩余租金时， 留购价全部使用， 抵扣后的租金有剩余
						lgjdk_ = lgjdk;
						sy_money_ -=lgjdk;
					}else {//当留购价>剩余租金时，剩余租金全部核销
						lgjdk_ = sy_money_;
						sy_money_ = parseFloatFormat(0);
					}
				}
				
				$("#buyBackParam_1").find("input[name='LG_DKJE']").val(lgjdk_);//留购价抵扣
				
				//DB保证金
				var DB_BZJDK = parseFloatFormat(isNum(json.data.bzj.DB_BZJ)); 
				var DB_BZJDK_ = parseFloatFormat(0);//实际抵扣金额
				
				if(sy_money_>0){//当剩余租金>0时， 调用留购价款
					if(DB_BZJDK<=sy_money_){//当留购价<=剩余租金时， 留购价全部使用， 抵扣后的租金有剩余
						DB_BZJDK_ = DB_BZJDK;
						sy_money_ -=DB_BZJDK;
					}else {//当留购价>剩余租金时，剩余租金全部核销
						DB_BZJDK_ = sy_money_;
						sy_money_ = parseFloatFormat(0);
					}
				}
				
				$("#buyBackParam_1").find("input[name='dbBZJDk']").val(DB_BZJDK_);//DB保证金
				
				//合计值：
				var heji = parseFloatFormat(0);
				var SY_MONEY_FORMAT= parseFloatFormat($("#buyBackParam_1").find("input[name='sy_money']").val()) ;
				var WS_YQ_FORMAT = parseFloatFormat($("#buyBackParam_1").find("input[name='WS_YQ']").val()) ;
				var WDQLX_MONEY_FORMAT = parseFloatFormat($("#buyBackParam_1").find("input[name='WDQLX_MONEY']").val()) ;
				var LIUGOUJIA_FORMAT = parseFloatFormat($("#buyBackParam_1").find("input[name='liugoujia']").val()) ;
				var HG_SXF_FORMAT = parseFloatFormat($("#buyBackParam_1").find("input[name='HG_SXF']").val()) ;
				var TAXES_FORMAT = parseFloatFormat($("#buyBackParam_1").find("input[name='taxes']").val()) ;
				var BZJDK_FORMAT = parseFloatFormat($("#buyBackParam_1").find("input[name='BZJDk']").val()) ;
				var LG_DKJE_FORMAT = parseFloatFormat($("#buyBackParam_1").find("input[name='LG_DKJE']").val()) ;
				var DBBZJDK_FORMAT =  parseFloatFormat($("#buyBackParam_1").find("input[name='dbBZJDk']").val()) ;
				var testInfo = "SY_MONEY_FORMAT: " + SY_MONEY_FORMAT + "WS_YQ_FORMAT: " + WS_YQ_FORMAT + "WS_YQ_FORMAT: " + WS_YQ_FORMAT + "WDQLX_MONEY_FORMAT: " +  WDQLX_MONEY_FORMAT 
					+ "LIUGOUJIA_FORMAT: " + LIUGOUJIA_FORMAT + "HG_SXF_FORMAT: " + HG_SXF_FORMAT + "TAXES_FORMAT: " + TAXES_FORMAT + "BZJDK_FORMAT: " + BZJDK_FORMAT 
					+ "LG_DKJE_FORMAT: " + LG_DKJE_FORMAT + "DBBZJDK_FORMAT: " + DBBZJDK_FORMAT ; 
				//alert(testInfo) ;
				heji = SY_MONEY_FORMAT
					  + WS_YQ_FORMAT
					  - WDQLX_MONEY_FORMAT
					  + LIUGOUJIA_FORMAT
					  + HG_SXF_FORMAT
					  + TAXES_FORMAT
					  - BZJDK_FORMAT
					  - LG_DKJE_FORMAT
					  - DBBZJDK_FORMAT;
				$("#HEJI").val(heji); 
			} 
		});
		}
		$("#buyBackParam_1").show();
		$('#buyBackParam_1').dialog({
		modal:true
	});  
}

function buyBackSubmit(){
	var BUY_BACK_STATUS = $("#buyBackParam").find("input[name='BUY_BACK_STATUS']").val();//回购的状态
	if(BUY_BACK_STATUS==0){//当状态为0表示回购预警操作
		var ACCOUNT_DATE = $("#buyBackParam").find("input[name='ACCOUNT_DATE']").val();
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

/**
 * 分期回购[页面]
 * @return
 */
function buyBackStages(){
	$('#dialog').dialog('close');
	var date_b = $('#pageTable').datagrid("getSelections");
	if(date_b[0].BUY_BACK_STATUS==1||date_b[0].BUY_BACK_STATUS==2){
		$.messager.show({
			title:'操作错误提示',
			msg:'该项目已经发起回购流程， 请选择为发起正常回购流程的数据',
			showType:'show'
		});
		return false;
	}
	//console.info(date_b);
	window.location.href=_basePath+"/buyBack/BuyBack!buyBackStages.action?param="+JSON.stringify(date_b[0]);
}

function exportExel(param){
	
	var data = $('#pageTable').datagrid("getSelections");
	
	if(data.length!=1){
		$.messager.show({
			title:'操作错误提示',
			msg:'只允许选择一行导出',
			showType:'show'
		});
		return false;
	}
	
	var PAY_CODE = new Array();
	var PAY_ID = new Array();
	var flag = true;
	$(data).each(function(){
		PAY_CODE.push(this.PAY_CODE);
		PAY_ID.push(this.PAY_ID);
		if(this.STATUS==null){
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
		window.location.href=_basePath+"/buyBack/BuyBack!ExcelFile3.action?PAY_ID="+PAY_ID+"&PAYLIST_CODE="+PAY_CODE.toString()+"&FILE_NAME=回购通知书.xls&PATH=B19.xls";
	}else if(param=="B30"){
		window.location.href=_basePath+"/buyBack/BuyBack!ExcelFile3.action?PAY_ID="+PAY_ID+"&PAYLIST_CODE="+PAY_CODE.toString()+"&FILE_NAME=通知书（致承租人）.xls&PATH=B30.xls";
	}else if(param=="B31"){
		window.location.href=_basePath+"/buyBack/BuyBack!ExcelFile3.action?PAY_ID="+PAY_ID+"&PAYLIST_CODE="+PAY_CODE.toString()+"&FILE_NAME=债权转让协议书.xls&PATH=B31.xls";
	}else if(param=="B50"){
		window.location.href=_basePath+"/buyBack/BuyBack!ExcelFile3.action?PAY_ID="+PAY_ID+"&PAYLIST_CODE="+PAY_CODE.toString()+"&FILE_NAME=所有权转移证书.xls&PATH=B50.xls";
	}else if(param=="B59"){
		window.location.href=_basePath+"/buyBack/BuyBack!ExcelFile3.action?PAY_ID="+PAY_ID+"&PAYLIST_CODE="+PAY_CODE.toString()+"&FILE_NAME=(分期)债权转让协议书.xls&PATH=B59.xls";
	}
	
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


/**
 * 正常回购
 * @return
 */
function buyBackSave(){
	var ACCOUNT_DATE = $("input[name=ACCOUNT_DATE]").val();
	if( ACCOUNT_DATE ==null || ACCOUNT_DATE ==""){
		alert("请选择结算日！");
		return;
		}
	var data_ = $("#buyBackForm_1").serialize();
	 $("#buyBackSave").linkbutton("disable");  
	jQuery.ajax({
	   type: "POST",
	   dataType:"json",
	   url: _basePath+"/buyBack/BuyBack!buyBackNormal.action",
	   data:data_,
	   success: function(json){
	   	if(json.flag){
	   		$.messager.alert("提示",json.msg,"info",function(){
	   			$.messager.alert("提示","下一步操作人为: "+json.data,"info",function(){
						window.location.href=_basePath+"/buyBack/BuyBack!buyBackManagement.action";
				});
			});
	   	}else{
	   		alert(json.msg) ;
	   	}
	   	
		//alert (json.msg);		
	   }
	});
}

//正常回购（合计值计算）
function heji(){
	var LILV = parseFloatFormat($("#buyBackParam_1").find("input[name=LILV]").val());//利率减免比例
	var HG_SXF = parseFloatFormat($("#buyBackParam_1").find("input[name=HG_SXF]").val());//回购手续费
	var liugoujia = parseFloatFormat($("#buyBackParam_1").find("input[name=liugoujia]").val());//留购价
	var WDQLX_MONEY = parseFloatFormat($("#buyBackParam_1").find("input[name=WDQLX_MONEY]").val());//未到期利息
	
	var hx_lv =  Math.round(WDQLX_MONEY * LILV / 100);//可冲抵利率
	
	var sy_money = $("#buyBackParam_1").find("input[name='sy_money']").val();//剩余租金
	var sy_money_ = parseFloatFormat(0);//抵扣收剩余租金
	
	
	//保证金
	var bzj = parseFloatFormat(0);//可使用保证金
	var bzj_ = parseFloatFormat(0);//已使用保证金
	if($("#buyBackParam_1").find("select[name='BZJ']").attr("selected",true).val()==0){//当保证金选择为承租人保证金时， 使用承租人保证金进行租金抵扣
		bzj = parseFloatFormat($("#buyBackParam_1").find("input[name='BZJ_MONEY']").val());
	}else {
		bzj = parseFloatFormat(0);
	}
	
	if(bzj<=sy_money){//当保证金小于等于剩余租金时， 保证金全部使用， 有剩余租金
		bzj_ = bzj;  
		sy_money_ = sy_money - bzj;
	}else if(bzj>sy_money){ //当保证金大于剩余租金时， 保证金有剩余， 租金全部核销 ， 核销收剩余租金为0
		bzj_ = sy_money ;
		sy_money_ = parseFloatFormat(0);
	}else if(bzj==0){//当保证金为0 时， 保证金不做抵扣
		bzj_ = parseFloatFormat(0);
		sy_money_ = sy_money;
	} 
	
	$("#BZJDk").val(bzj_);
	var bzj_1 = $("#BZJDk").val();//保证金抵扣金额	
	
	//留购价低租金
	var lgj = parseFloatFormat(0);//可使用留购价
	var lgj_ = parseFloatFormat(0);//已使用留购价
	if($("#buyBackParam_1").find("select[name='isLiugou']").attr("selected",true).val()==0){//是   留购价抵扣
		lgj = parseFloatFormat($("#buyBackParam_1").find("input[name='LIUGOUJIA']").val());
	}else { //否留购价不抵扣
		lgj = parseFloatFormat(0);
	}
	
	if(sy_money_>0){
		if(lgj==0){//当留购价<=0， 留购价全部使用， 抵扣后的租金有剩余
			lgj_ = 0;
			sy_money_ = sy_money_;
		}else if(lgj <= sy_money_){//当留购价<=剩余租金时， 留购价全部使用， 抵扣后的租金有剩余
			lgj_ = lgj;
			sy_money_ -= lgj;
		}else if(lgj > sy_money_){
			lgj_ = sy_money_;
			sy_money_ = 0;
		}
	}else if(sy_money_ == 0){//剩余租金 == 0， 留购价抵扣金额为0
		lgj_ = parseFloatFormat(0);
	}
	$("#LG_DKJE").val(lgj_);
	var lgj_1 = $("#LG_DKJE").val();//留购价抵扣金额
	
	//DB保证金
	var dbbzj = parseFloatFormat(0);//可使用DB保证金
	var dbbzj_ = parseFloatFormat(0);//已使用DB保证金
	if($("#buyBackParam_1").find("select[name='DBBZJ']").attr("selected",true).val()==0){//当DB保证金选择为承租人保证金时， 使用承租人保证金进行租金抵扣
		dbbzj = parseFloatFormat($("#buyBackParam_1").find("input[name='dbBZJ_MONEY']").val());
	}else {
		dbbzj = parseFloatFormat(0);
	}
	
	if(sy_money_>0){
		if(dbbzj<=sy_money){//当DB保证金小于等于剩余租金时， 保证金全部使用， 有剩余租金
			dbbzj_ = dbbzj;  
			sy_money_ = sy_money - bzj;
		}else if(bzj>sy_money){ //当DB保证金大于剩余租金时， 保证金有剩余， 租金全部核销 ， 核销收剩余租金为0
			dbbzj_ = sy_money ;
			sy_money_ = parseFloatFormat(0);
		}else if(bzj==0){//当DB保证金为0 时， 保证金不做抵扣
			dbbzj_ = parseFloatFormat(0);
			sy_money_ = sy_money;
		} 
	}else {
		dbbzj_ = parseFloatFormat(0);
	}
	$("#dbBZJDk").val(dbbzj_);
	var dbbzj_1 = $("#dbBZJDk").val();//保证金抵扣金额
	
	//合计值：
	var heji = parseFloatFormat(0);
	
	var v1 = parseFloatFormat($("#buyBackParam_1").find("input[name='sy_money']").val());
	var v2 = parseFloatFormat($("#buyBackParam_1").find("input[name='WS_YQ']").val()) ;
	var v3 = parseFloatFormat(hx_lv) ;
	var v4 = parseFloatFormat($("#buyBackParam_1").find("input[name='liugoujia']").val()) ;
	var v5 = parseFloatFormat($("#buyBackParam_1").find("input[name='HG_SXF']").val()) ;
	var v6 = parseFloatFormat($("#buyBackParam_1").find("input[name='taxes']").val()) ;
	var v7 = parseFloatFormat(bzj_1) ;
	var v8 = parseFloatFormat(lgj_1) ;
	var v9 = parseFloatFormat(dbbzj_); 
	
	var testinfo = "v1: " + v1+" v2: " + v2+"v3: " + v3+"v4: " + v4+"v5: " + v5+"v6: " + v6+"v7: " + v7+"v8: " + v8+"v9: " + v9 ;
	//alert(testinfo) ;
	
	heji = parseFloatFormat($("#buyBackParam_1").find("input[name='sy_money']").val())
		  +parseFloatFormat($("#buyBackParam_1").find("input[name='WS_YQ']").val())
		  -parseFloatFormat(hx_lv)
		  +parseFloatFormat($("#buyBackParam_1").find("input[name='liugoujia']").val())
		  +parseFloatFormat($("#buyBackParam_1").find("input[name='HG_SXF']").val())
		  +parseFloatFormat($("#buyBackParam_1").find("input[name='taxes']").val())
		  -parseFloatFormat(bzj_1)
		  -parseFloatFormat(lgj_1)
		  -parseFloatFormat(dbbzj_);
	$("#HEJI").val(heji);
}
function get_custName(param){
	var pv = $(param).val();
	if(pv.length<2){
		return null;
	}
	//去后台验证是否有供应商
	jQuery.ajax({
        type: "POST",
        dataType: "json",
		async:false,
        url: _basePath + "/buyBack/BuyBack!getCustName.action",
        data: "pv="+pv,
        success: function(msg){
			//console.info(msg.data);
			var persons = msg.data;
			if(persons.length>=2){
				var str="";
				for(var i=0;i<persons.length;i++){
					var person = persons[i];
					str = str+"<li><input id=\""+person.ID+"\" type=\"radio\" name =\"buyCust\" value=\""+person.ID+"\" ><label for=\""+person.ID+"\">"+person.NAME+"</label></li>";
				}
				$("#dialog_").append(str);
				$("#dialog_").dialog({  
					modal:true,
					buttons: [{
						text:'Ok',
						iconCls:'icon-ok',
						handler:function(){
							var select = $("input[name='buyCust']:checked");
							//$("input[name='PAYEE_NAME']").val($("label[for='"+select.val()+"']").text());
							//$("input[name='PAYEE_NAME']").val($("label[for='"+select.val()+"']").text()+"||"+select.val());
							$("input[name='PAYEE_NAME']").val(select.val());
							$("input[name='PAYEE_NAME']").after($("label[for='"+select.val()+"']").text());
							$("#dialog_").dialog('close',{forceClose:true});
							$("#dialog_").empty();
						}
					}]
				})
			}else if(persons.length==1){
				//$("input[name='PAYEE_NAME']").val(persons[0].NAME+"||"+persons[0].ID);
				$("input[name='PAYEE_NAME']").val(persons[0].ID);
				$("input[name='PAYEE_NAME']").after(persons[0].NAME);
			}else {
				$("input[name='PAYEE_NAME']").val("");
			}
        }
    });
}

function validValue(value){
	var result = value ;
	if(value==undefined || typeof(value)==undefined)
		 result='';
		// alert("reslut: " + result + "value: " + value) ;
	return result ; 
}

function parseFloatFormat(value){
	var tempValue=value;
	if(value==null || value=='' || value == undefined){
		tempValue = 0;
	}
	var result = parseFloat(tempValue) ;
	//alert("result :" + result + "value: " +value) ;
	return  result;
}


function compure2(value, rowData){
	
	if(rowData.STATUS=='5' || rowData.STATUS=='55'){
		return "<a href='javascript:void(0)' onclick=showDetailXMHG('" + rowData.PAY_ID + "','" + rowData.PAY_CODE + "')>查看</a>";
	}
	else{
		return " ";
	}
	
}

function showDetailXMHG(ID,PAYLIST_CODE){
	top.addTab(PAYLIST_CODE+"回购明细",_basePath+"/buyBack/BuyBack!toMgshowDetailHG.action?PAY_ID="+ID);
}



