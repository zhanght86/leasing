var code_ = 0;
function calculate(){
	//首期租金按融资额计算
	changeStartMoney();
//	changeEqumentMoney();
	//add by lishuo 01-26-2016 固定月租产品超5.6W提醒 Start
	var UNIT_PRICE =$("input[name='UNIT_PRICE']").val();
	var SCHEME_CODE1 =$("#SCHEME_CODE1").find("option:selected").text();
	if(SCHEME_CODE1!=null && SCHEME_CODE1.indexOf("月租") > 0){
		if(UNIT_PRICE <= 40000 || UNIT_PRICE >= 56000){
			$.messager.alert("提示","五菱固定月租方案限于车价4万元~5.6万元范围内使用!");
		}
	}
	//add by lishuo 01-26-2016 固定月租产品超5.6W提醒  End
	changeEqumentMoneyCS();//点击测算时,不进行比例推算
	try{
		var VALUE_DOWN=$("#ZBZJBL_PERCENT").attr("VALUE_DOWN");
		var VALUE_UP=$("#ZBZJBL_PERCENT").attr("VALUE_UP");
		var ZBZJBL_PERCENT=$("#ZBZJBL_PERCENT").val();
		if(ZBZJBL_PERCENT!=''&& down!='' && up!=''){
			if(parseInt(ZBZJBL_PERCENT)-parseInt(VALUE_DOWN)<0||parseInt(ZBZJBL_PERCENT)-parseInt(VALUE_UP)>0){
				$.messager.alert("提示","保证金总比例超出取值区间【"+VALUE_DOWN+"--"+VALUE_UP+"】,请重新设置各保证金比例");
				return;
			}
		}
	}catch(Exception){}	
	var PRO_ID = $("#PRO_ID").val();
	var LXTQSQ=$("#LXTQSQ").val();
	var LAST_MONEY=$("#LAST_MONEY").val();
	if(LAST_MONEY==''||LAST_MONEY==null||LAST_MONEY=='undefined')
		LAST_MONEY=0;
	if(LXTQSQ==''||LXTQSQ==null){
		//利息不提前收取
		LXTQSQ='1';
	}
	var SQFS=$("#SQFS").val();
	if(SQFS==''||SQFS==null){
		//管理费一次性收取
		SQFS='1';
	}
	var FEES_PRICE=$("#FEES_PRICE").val();
	if(FEES_PRICE==null||FEES_PRICE==''){
		//手续费
		FEES_PRICE=0;
	}
	var CALCULATE_TYPE="pmt";
	if($("#CALCULATE_TYPE").val()!=null&&$("#CALCULATE_TYPE").val()!=''){
		CALCULATE_TYPE=$("#CALCULATE_TYPE").val();
	}
	var MANAGEMENT_FEETYPE=$("#MANAGEMENT_FEETYPE").val();
	if(MANAGEMENT_FEETYPE==null||MANAGEMENT_FEETYPE==''){
		//手续费收取方式（1：一次性收取  3：按期收取）
		MANAGEMENT_FEETYPE=1;//默认为一次性收取
	}
	
	var MQGLF=$("#MQGLF").val();
	if(MQGLF==null||MQGLF==''){
		//每期管理费
		MQGLF=0;//
	}
	
	var DISCOUNT_TYPE=$("#DISCOUNT_TYPE").val();
	if(DISCOUNT_TYPE==null||DISCOUNT_TYPE==''){
		//不贴息
		DISCOUNT_TYPE="1";
	}
	
	var DISCOUNT_MONEY=$("#DISCOUNT_MONEY").val();
	if(DISCOUNT_MONEY==''||DISCOUNT_MONEY==null){
		//贴息金额
		DISCOUNT_MONEY=0;
	}
	
	if($.trim($("#START_PERCENT").val())==null || $.trim($("#START_PERCENT").val())==''){
		$.messager.alert("提示","请输入首期租金比例");
		return;
	}
	var YEAR_INTEREST=0;
	if($.trim($("#YEAR_INTEREST").val())!=''&& $.trim($("#YEAR_INTEREST").val())!=null )
	{
		YEAR_INTEREST=$("#YEAR_INTEREST").val();
	}
	else
	{
		$.messager.alert("提示","请输入年利率!");
		return;
	}
	var YEAR_INTEREST_Pay=accDiv(YEAR_INTEREST,100);
	
	/*var lease_term=0;
	if($.trim($("#lease_term").val())!=null&& $.trim($("#lease_term").val())!='')
	{
		lease_term=$("#lease_term").val();
	}
	else
	{
		$.messager.alert("提示","请输入期次!");
		return;
	}*/
	var lease_term = document.getElementById("lease_term").value;
	
	var lease_period=0;
	if($.trim($("#lease_period").val())!=null && $.trim($("#lease_period").val())!='' )
	{
		lease_period=$("#lease_period").find("option:selected").val();
	}
	else
	{
		$.messager.alert("提示","请输入周期!");
		return;
	}
	var _payCountOfYear=accDiv(12,lease_period);
	
	var LEASE_TOPRIC=0;
	if($.trim($("#LEASE_TOPRIC").val())!=null && $.trim($("#LEASE_TOPRIC").val())!='')
	{
		LEASE_TOPRIC=$("#LEASE_TOPRIC").val();
	}
	else
	{
		$.messager.alert("提示","请输入设备金额!");
		return;
	}

	FIRSTPAYALL();// TODO ............
	var FINANCE_TOPRIC=$("input[name='FINANCE_TOPRIC']").val();//融资额
	
	var SCALE=2;
	var beforeThreePercent=1;
	var ROUND_UP_TYPE=0;
	var isNoPayForNewYear=false;
	var AMOUNT=1;
	var BEFORETERM=3;
	var YGQZR_DATE="";
	var RenewalMargin_MONEY=0;
	var ISTAX=1;
	try{
		AMOUNT=$("#AMOUNT_H").val();
	}catch(Exception){};
	try{
		SCALE=$("#SCALE").val();
		if(SCALE==null||SCALE==''||SCALE==undefined||SCALE=='undefined')
			SCALE=2;
		ROUND_UP_TYPE=$("#ROUND_UP_TYPE").val();
		if(ROUND_UP_TYPE==null||ROUND_UP_TYPE==''||ROUND_UP_TYPE==undefined||ROUND_UP_TYPE=='undefined')
			ROUND_UP_TYPE=0;
		var NOPAYFORNEWYEAR=$("#ISNOPAYFORNEWYEAR").val();
		if(NOPAYFORNEWYEAR=="1")
			isNoPayForNewYear=true;
		var BEFORETHREE=$("#BEFORETHREE_PERCENT").val();
		if(BEFORETHREE!=null&&BEFORETHREE!=''&&BEFORETHREE!='undefined'&&BEFORETHREE!=undefined)
			beforeThreePercent=accDiv(BEFORETHREE,100);
		
		if(AMOUNT==null||AMOUNT==''||AMOUNT==undefined||AMOUNT=='undefined')
			AMOUNT=$("[name='AMOUNT']").val();
		if(AMOUNT==null||AMOUNT==''||AMOUNT==undefined||AMOUNT=='undefined')
			AMOUNT=1;
		
		BEFORETERM=$("#BEFORETERM").val();
		if(BEFORETERM==null||BEFORETERM==''||BEFORETERM==undefined||BEFORETERM=='undefined')
			BEFORETERM=3;
		RenewalMargin_MONEY=$("#RenewalMargin_MONEY").val();
		if(RenewalMargin_MONEY==null||RenewalMargin_MONEY==''||RenewalMargin_MONEY==undefined||RenewalMargin_MONEY=='undefined')
			RenewalMargin_MONEY=0;
		
		ISTAX=$("#ISTAX").val();
		if(ISTAX==null||ISTAX==''||ISTAX==undefined||ISTAX=='undefined')
			ISTAX=0;
		
		YGQZR_DATE = $("input[name='YGQZR_DATE']").val();
	}catch(Exception){}
	
	//客户保证金
	var DEPOSIT_MONEY=$("#DEPOSIT_MONEY").val();
	if(DEPOSIT_MONEY==null||DEPOSIT_MONEY==""||DEPOSIT_MONEY=='undefined'||DEPOSIT_MONEY==undefined)
		DEPOSIT_MONEY=0;
	
	var POUNDAGE_WAY=3;
	try{
		POUNDAGE_WAY=$("#POUNDAGE_WAY").val();
	}catch(Exception){}
	
	var FIRSTPAYALL_val = $("#FIRSTPAYALL").val();
	
	var PAY_WAY_=$("[name='PAY_WAY']").val();
	if(PAY_WAY_==''||PAY_WAY_==null){
		$.messager.alert("提示","请选择支付方式！");
		return null;
	}
	//已经测算的标识
//	var data_ = "PRO_ID="+PRO_ID+"&UPDATE_DELIVER_ADDRESS="+UPDATE_DELIVER_ADDRESS+"&UPDATE_DELIVER_DATE="+UPDATE_DELIVER_DATE+"&annualRate="+YEAR_INTEREST_Pay+"&_leaseTerm="+lease_term+"&&residualPrincipal="+FINANCE_TOPRIC+"&_payCountOfYear="+_payCountOfYear+"&pay_way="+PAY_WAY_+"&date="+UPDATE_DELIVER_DATE;
	var data_ = "LXTQSQ="+LXTQSQ+"&PRO_ID="+PRO_ID+"&annualRate="+YEAR_INTEREST_Pay+"&_leaseTerm="
				+lease_term+"&residualPrincipal="+FINANCE_TOPRIC+"&_payCountOfYear="
				+_payCountOfYear+"&pay_way="+PAY_WAY_+"&SQFS="+SQFS
//				+"&QUOTA_ID="+QUOTA_ID
				+"&DISCOUNT_TYPE="+DISCOUNT_TYPE+"&DISCOUNT_MONEY="+DISCOUNT_MONEY+"&FEES_PRICE="+FEES_PRICE
				+"&MANAGEMENT_FEETYPE="+MANAGEMENT_FEETYPE+"&MQGLF="+MQGLF+"&LAST_MONEY="+LAST_MONEY
				+"&CALCULATE_TYPE="+CALCULATE_TYPE+"&DEPOSIT_MONEY="+DEPOSIT_MONEY+"&FIRSTPAYALL="+FIRSTPAYALL_val+"&LEASE_TOPRIC="+LEASE_TOPRIC+"&POUNDAGE_WAY="+POUNDAGE_WAY
				+"&SCALE="+SCALE+"&ROUND_UP_TYPE="+ROUND_UP_TYPE+"&ISNOPAYFORNEWYEAR="+isNoPayForNewYear+"&BEFORETHREEPERCENT="+beforeThreePercent
				+"&AMOUNT="+AMOUNT+"&BEFORETERM="+BEFORETERM+"&YGQZR_DATE="+YGQZR_DATE+"&RenewalMargin_MONEY="+RenewalMargin_MONEY+"&ISTAX="+ISTAX;
	
	if (code_ == 8) {
		var editData = getEditRows();
		if(!editData){//如果确认框选择了false则不让往下走
			return false;
		}
		data_ = data_ + "&EditRows="+JSON.stringify(editData);	
	}
	$.ajax({
        type: "post",
        dataType: "json",
        url: _basePath + "/pay/PayTask!quoteCalculateTest.action",
        data: data_,
        success: function(msg){
        	if(msg.flag==false){
        		alert(msg.msg);
        	}else{
			if($("#LXTQSQ").val()=='2'){
				$("#GDLX_PRICE").val(msg.data.GDLX_PRICE);
			}
            var data = msg.data.ln;
            var footer_ = [{
                PAY_DATE: "合计：",
//                PMTzj: totalColumn($(data), "PMTzj"),
                zj: totalColumn($(data), "zj"),
                bj: totalColumn($(data), "bj"),
                lx: totalColumn($(data), "lx"),
                lxzzs: totalColumn($(data), "lxzzs"),
                sxf: totalColumn($(data), "sxf"),
                glf: totalColumn($(data), "glf")
            }];
            var data__ = {
                flag: msg.flag,
                total: data.length,
                rows: data,
                footer: footer_
            };
            data = data__;
            //-----------------start-----------计算 首期租金预付月数*预付资金 =加入首期付款合计
            var MYZJ=parseFloat(msg.data.MYZJ);//获取每个月的租金
            var YSZJQC = $("input[name='YSZJQC']").val();//月
            var YSZJQC = $("#YSZJQC").val();
            if(YSZJQC!=null){
            var JUST_MONEYZH = parseFloat(YSZJQC*MYZJ);
            $("input[name='JUST_MONEY']").val(JUST_MONEYZH.toFixed(2));
            }
            //-----------------end-----------计算 首期租金预付月数*预付资金 =加入首期付款合计
            
			var INTERNAL_RATE=parseFloat(msg.data.INTERNAL_RATE);
			var SYL_BZ_VALUE=parseFloat($("[name='SCHEME_SYL_BZ_VALUE']").val());
			$("#showIrrSpan").html(msg.data.INTERNAL_RATE);
			$("#INTERNAL_RATE").val(msg.data.INTERNAL_RATE);
            
            //根据收益率标准判断是否可以报价
			if(SYL_BZ_VALUE!=null&&SYL_BZ_VALUE!=''&&SYL_BZ_VALUE!='undefined'&&SYL_BZ_VALUE!=undefined&&parseFloat(INTERNAL_RATE)-parseFloat(SYL_BZ_VALUE)<=0){
				$.messager.confirm("警告","该报价不合规，是否继续报价",function(r){
					if(r){
						$(".dataDiv").show();
							if(msg.data.code==8){
								code_ = 8;
								$('#pageTable').datagrid({fit:true,fitColumns:true,rownumbers:true,singleSelect:true,showFooter:true,
									onClickRow: function(rowIndex, rowData){
											onClickRow_(rowIndex, rowData);
						                }
								});
							}else{
								$('#pageTable').datagrid({fit:true,fitColumns:true,rownumbers:true,singleSelect:true,showFooter:true});
							}
							$('#pageTable').datagrid("loadData", data);
							
							//算之后可以发起流程做保存
							$("#submitAddScheme_").linkbutton('enable');
	
							var PAY_WAY=$("#PAY_WAY").find("option:selected").text();
							$("#pageTable").datagrid('selectRow',0);
							var row=$("#pageTable").datagrid('getSelected');
							var foot=$("#pageTable").datagrid('getFooterRows');
							var data1=msg.data.ln;
							if(PAY_WAY.indexOf("期初")>=0){
								$("#FIRSTMONTHPRICE").val(row.zj);
							}
							var START_MONEY=$("input[name=START_MONEY]").val();
							if(START_MONEY==null||START_MONEY==''||START_MONEY==undefined||START_MONEY=='undefined')
								START_MONEY=0;
							$("#MONEYALL").val(foot[0].zj);
							$("#pageTable").datagrid('unselectRow',0);
							FIRSTPAYALL();
					}
				});
			}else{
				$(".dataDiv").show();
				if(msg.data.code==8){
					code_ = 8;
					$('#pageTable').datagrid({fit:true,fitColumns:true,rownumbers:true,singleSelect:true,showFooter:true,
						onClickRow: function(rowIndex, rowData){
								onClickRow_(rowIndex, rowData);
			                }
					});
				}else{
					$('#pageTable').datagrid({fit:true,fitColumns:true,rownumbers:true,singleSelect:true,showFooter:true});
				}
				$('#pageTable').datagrid("loadData", data);
				
				//算之后可以发起流程做保存
				$("#submitAddScheme_").linkbutton('enable');

				var PAY_WAY=$("#PAY_WAY").find("option:selected").text();
				$("#pageTable").datagrid('selectRow',0);
				var row=$("#pageTable").datagrid('getSelected');
				var foot=$("#pageTable").datagrid('getFooterRows');
				var data1=msg.data.ln;
				if(PAY_WAY.indexOf("期初")>=0){
					$("#FIRSTMONTHPRICE").val(row.zj);
				}
				var START_MONEY=$("input[name=START_MONEY]").val();
				if(START_MONEY==null||START_MONEY==''||START_MONEY==undefined||START_MONEY=='undefined')
					START_MONEY=0;
				$("#MONEYALL").val(foot[0].zj);
				$("#pageTable").datagrid('unselectRow',0);
				FIRSTPAYALL();
			}
        }
        }
    });
}
/*2016年4月13日 15:33:25 P2P金融新算法 吴国伟*/
function p2p_calculate(){
	//融资额
	var TOTAL_FINACING = $("#FINANCE_TOPRIC").val();
	//年利率
	var YEAR_INTEREST = $("#YEAR_INTEREST").val();
	//周期
	var  LEASE_TERM = $("#lease_term").val();
	//内部收益率
	var SCHEME_SYL_BZ_VALUE = $("#SCHEME_SYL_BZ_VALUE").val();
	/******2016年5月11日 14:04:52 吴国伟修改*******/
	//车价
	var LEASE_TOPRIC = $("#LEASE_TOPRIC").val();
	//采购价格 
	var CC_PRICE = $("#CC_PRICE").val();
	
	if(CC_PRICE==0|| CC_PRICE==null||CC_PRICE==''||CC_PRICE==undefined||CC_PRICE=='undefined'){
		CC_PRICE  = LEASE_TOPRIC;
	}
	/******************************************end*/	
	var SCALE=0;
	SCALE=$("#SCALE").val();
	
	if(SCALE==null||SCALE==''||SCALE==undefined||SCALE=='undefined')
		SCALE=0;
	//start每期管理费 2016年5月26日 12:27:19
	var MQGLF = 0;
	MQGLF=$("#MQGLF").val();
	if(MQGLF==null||MQGLF==''||MQGLF==undefined||MQGLF=='undefined')
		MQGLF=0;
	//end
	
	//start每期管理费 2016年7月1日 17:29:19
	var HTZCF = 0;
	HTZCF=$("#HTZCF").val();
	if(HTZCF==null||HTZCF==''||HTZCF==undefined||HTZCF=='undefined')
		HTZCF=0;
	//end
	
	//start 财客元素  2016年7月11日 14:01:12
	var CKYS = 1;
	CKYS=$("#CKYS").val();
	if(CKYS==null||CKYS==''||CKYS==undefined||CKYS=='undefined')
		CKYS=1;
	//end
	
	var ROUND_UP_TYPE =2;
	ROUND_UP_TYPE=$("#ROUND_UP_TYPE").val();
	if(ROUND_UP_TYPE==null||ROUND_UP_TYPE==''||ROUND_UP_TYPE==undefined||ROUND_UP_TYPE=='undefined')
		ROUND_UP_TYPE=2;
	
	var data_ = "TOTAL_FINACING="+TOTAL_FINACING+"&SCHEME_SYL_BZ_VALUE="+SCHEME_SYL_BZ_VALUE
	            +"&YEAR_INTEREST="+YEAR_INTEREST+"&LEASE_TERM="+LEASE_TERM
	            +"&SCALE="+SCALE+"&ROUND_UP_TYPE="+ROUND_UP_TYPE+"&CC_PRICE="+CC_PRICE
	            +"&LEASE_TOPRIC="+LEASE_TOPRIC+"&MQGLF="+MQGLF+"&HTZCF="+HTZCF+"&CKYS="+CKYS;
	$.ajax({
        type: "post",
        dataType: "json",
        url: _basePath + "/pay/PayTask!quoteP2PCalculate.action",
        data: data_,
        success: function(msg){
        	if(msg.flag==false){
        		alert(msg.msg);
        	}else{
	            var data = msg.data.ln;
	            var footer_ = [{
	                PAY_DATE: "合计：",
	//                PMTzj: totalColumn($(data), "PMTzj"),
	                zj: totalColumn($(data), "zj"),
	                bj: totalColumn($(data), "bj"),
	                lx: totalColumn($(data), "lx"),
	                lxzzs: totalColumn($(data), "lxzzs"),
	                sxf: totalColumn($(data), "sxf"),
	                glf: totalColumn($(data), "glf")
	            }];
	            var data__ = {
	                flag: msg.flag,
	                total: data.length,
	                rows: data,
	                footer: footer_
	            };
	            
	            data = data__;
	            
	            //-----------------start-----------计算 首期租金预付月数*预付资金 =加入首期付款合计
	            var MYZJ=parseFloat(msg.data.MYZJ);//获取每个月的租金
	            var YSZJQC = $("input[name='YSZJQC']").val();//月
	            var YSZJQC = $("#YSZJQC").val();
	            if(YSZJQC!=null){
	            var JUST_MONEYZH = parseFloat(YSZJQC*MYZJ);
	            $("input[name='JUST_MONEY']").val(JUST_MONEYZH.toFixed(2));
	            }
	            //-----------------end-----------计算 首期租金预付月数*预付资金 =加入首期付款合计
	            
	            
	            
	            var zzcfwf = msg.data.zzcfwf;
	            var zhtje = msg.data.zhtje;
	            $("#P2P_FINANCING").val(zhtje);
	            $("#P2P_SERVICE_FEE").val(zzcfwf);
	            var YSZJQC = $("#YSZJQC").val();
		        if(YSZJQC!=null){
		            var JUST_MONEYZH = parseFloat(YSZJQC*MYZJ);
		            $("input[name='JUST_MONEY']").val(JUST_MONEYZH.toFixed(2));
		            
		          } 
				$(".dataDiv").show();
				$('#pageTable').datagrid({fit:true,fitColumns:true,rownumbers:true,singleSelect:true,showFooter:true});
				$('#pageTable').datagrid("loadData", data);
				
				//算之后可以发起流程做保存
				$("#submitAddScheme_").linkbutton('enable');

				$("#pageTable").datagrid('selectRow',0);
				var foot=$("#pageTable").datagrid('getFooterRows');
				
				var START_MONEY=$("input[name=START_MONEY]").val();
				if(START_MONEY==null||START_MONEY==''||START_MONEY==undefined||START_MONEY=='undefined')
					START_MONEY=0;
				$("#MONEYALL").val(foot[0].zj);
				$("#pageTable").datagrid('unselectRow',0);
				//暂时留着
				FIRSTPAYALL();
	
		         
	            
	            
	        	}
             }
        });
}
//不等额修改行
var editIndex = undefined;
function onClickRow_(index, rowData){
	var grid_data = $('#pageTable').datagrid('getData');
    if (editIndex != index) {
        if (endEditing()) {
            $('#pageTable').datagrid('selectRow', index).datagrid('beginEdit', index);
            editIndex = index;
        }
        else {
            $('#pageTable').datagrid('selectRow', editIndex);
        }
    }
    
}
function endEditing(){
    if (editIndex == undefined) {
        return true;
    }
    if ($('#pageTable').datagrid('validateRow', editIndex)) {
        $('#pageTable').datagrid('endEdit', editIndex);
		/*手动修改了本金和利息之后自动改变相应的租金值
		var data_ = $('#pageTable').datagrid('getRows');
        var rows_ =data_[editIndex];
		if($.trim(rows_.bj).length<=0){
			rows_.bj = 0;
		}
		if($.trim(rows_.lx).length<=0){
			rows_.lx = 0;
		}
        //var rows_last =data_[editIndex-1];
		rows_.zj = formatNumber(parseFloat(rows_.bj)+parseFloat(rows_.lx),'0.00');
		rows_.PMTzj = formatNumber(parseFloat(rows_.bj)+parseFloat(rows_.lx),'0.00');
		//rows_.sybj = formatNumber(parseFloat(rows_last.sybj) - parseFloat(rows_.bj),'0.00');
		$('#pageTable').datagrid('updateRow',{index:editIndex,row:rows_});
		*/
		var pay_project_model=$("#pay_project_model").val();
		var data_ = $('#pageTable').datagrid('getRows');
        var rows_ =data_[editIndex];
        //资产包
        if(pay_project_model=='6'){
        	//周期
        	var lease_period=$("#lease_period").val();
        	//还款政策
        	var sybj;
        	if(editIndex!=0){
        		sybj=data_[editIndex-1].sybj;
        	}else{
        		sybj=$("#FINANCE_TOPRIC").val();
        	}
        	var YEAR_INTEREST=$("#YEAR_INTEREST").val();
        	if(rows_.zcbxjl==null||rows_.zcbxjl==''){
        		rows_.zcbxjl=rows_.bj;
        	}
        	rows_.bj=formatNumber(parseFloat(rows_.zcbxjl)*parseFloat($("#ZKL").val())/100,'0.00');
        	if(editIndex!=0){
        		rows_.lx=formatNumber(parseFloat(sybj)*parseFloat(YEAR_INTEREST)*parseFloat(lease_period)/1200,'0.00');
        	}else{
        		if($("#PAY_WAY").val()=='2'){
        			rows_.lx='0';
        		}else{
	        		var UPDATE_DELIVER_DATE=$("#UPDATE_DELIVER_DATE").datebox('getValue');
	        		var PAY_DATE=rows_.PAY_DATE;
	        		var day=(new Date(PAY_DATE).getTime()-new Date(UPDATE_DELIVER_DATE).getTime())/(60*60*24*1000);
	        		rows_.lx=formatNumber(parseFloat(sybj)*parseFloat(YEAR_INTEREST)*parseFloat(day)/36500,'0.00');
        		}
        	}
        	rows_.sybj=formatNumber(parseFloat(sybj)-parseFloat(rows_.bj),'0.00');
        	rows_.zj=formatNumber(parseFloat(rows_.bj)+parseFloat(rows_.lx),'0.00');
        	$('#pageTable').datagrid('updateRow',{index:editIndex,row:rows_});
        }else{
			if($.trim(rows_.zj).length<=0){
				rows_.zj = 0;
			}
	        //var rows_last =data_[editIndex-1];
			rows_.bj = formatNumber(parseFloat(rows_.zj) - parseFloat(rows_.lx),'0.00');
			//rows_.sybj = formatNumber(parseFloat(rows_last.sybj) - parseFloat(rows_.bj),'0.00');
			$('#pageTable').datagrid('updateRow',{index:editIndex,row:rows_});
        }
        editIndex = undefined;
        return true;
    }
    else {
        return false;
    }
}

//不等额的时候修改了的行数
function getEditRows(){
	//var rows = $('#pageTable').datagrid('getChanges');
	var resultData = new Array();
	var rows2 = $('#pageTable').datagrid('getChanges');
	var allData = $('#pageTable').datagrid("getRows");
	$(allData).each(function(){
		var flag = false;
		if(this.lock=="yes"){
			for(var i=0;i<rows2.length;i++){
				if(this.qc == rows2[i].qc){
					flag = true;
				}
			}
			if(!flag){
				resultData.push(this);
			}
		}
	})
	$(rows2).each(function(){
		resultData.push(this);
	})
	//console.info(resultData);
	if(!confirm("您共修改了"+resultData.length+"期租金,确认往下执行吗?")){
		return false;
	}
	//alert(rows2.length+' rows are changed!'); 
	return resultData;
}

//function addDot(){
//	$('#pageTable').datagrid("resize");
//}
//
//$(window).resize(function() {
//	setTimeout('addDot()',500);
//});

/**
 * 除法
 * @param arg1
 * @param arg2
 * @return
 */
function accDiv(arg1, arg2) {
    var t1 = 0, t2 = 0, r1, r2;
    try { t1 = arg1.toString().split(".")[1].length ;} catch (e) { }
    try { t2 = arg2.toString().split(".")[1].length ;} catch (e) { }
    with (Math) {
        r1 = Number(arg1.toString().replace(".", ""));
        r2 = Number(arg2.toString().replace(".", ""));
        return (r1 / r2) * pow(10, t2 - t1);
    }
}

/**
 * 加法
 * @param arg1
 * @param arg2
 * @return
 */
function accAdd(arg1, arg2) {
    var r1, r2, m;
    try { r1 = arg1.toString().split(".")[1].length; } catch (e) { r1 = 0; }
    try { r2 = arg2.toString().split(".")[1].length; } catch (e) { r2 = 0; }
    m = Math.pow(10, Math.max(r1, r2));
    return (arg1 * m + arg2 * m) / m;
}

////季度不规则批量修改
//function batchUpdate(){
//	var resultData = getEditRows();
//	var allData = $('#pageTable').datagrid("getRows");
//	var counter = 1;
//	for (var i = 0; i < allData.length; i++) {
//		var data_ = allData[i];
//		for (var j = 0; j < resultData.length; j++) {
//			var qc = parseInt(resultData[j].qc) + counter * 3;
//			if (parseInt(data_.qc) == qc) {
//				data_.lock = 'yes';
//				data_.zj = resultData[j].zj;
//				break;
//			}
//			var qc = parseInt(resultData[j].qc);
//			if (parseInt(data_.qc) == qc) {
//				data_.lock = 'yes';
//				break;
//			}
//			
//		}
//		if ((i+1) % 3 == 0&&i!=2) {
//			counter = counter + 1;
//		}
//	}
//	console.info(allData);
//	var data2 = {
//		total: allData.length,
//		rows: allData
//	}
//	$('#pageTable').datagrid("loadData", data2);
//	calculate();
//}




