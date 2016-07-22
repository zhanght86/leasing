$(document).ready(function(){
        	$('#pageTable').datagrid({
        		url:_basePath+"/renterpool/CashDeposit!toMgCashDepositData_New.action",
        		pagination:true,//是否分页 true为是
        		rownumbers:true,//左侧自动显示行数
        		singleSelect:true,
        		fit : true,
        		pageSize:20,
        		toolbar:'#pageForm',
        		 frozenColumns:[[
						{field:'REMIND_FLAG',width:40,formatter:function(value,rowData,rowIndex){
							var CANUSE_MONEY=rowData.CANUSE_MONEY;
							var STATUS=rowData.STATUS;
							if((value == 1 || STATUS==1) && CANUSE_MONEY>0){
							   return "<div class='icon-red'>&nbsp;</div>";
						    }else{
							   return "<div class='icon-green'>&nbsp;</div>";
							}
						}},
        		       {field:'aa',title:'操作',width:180,align:'center',formatter:function(value,rowData,rowIndex){
        		    		var approval = "";
        		    		var REMIND_FLAG=rowData.REMIND_FLAG;
        		    		var POUNDAGE_WAY=rowData.POUNDAGE_WAY;
        		    		var STATUS=rowData.STATUS;
        		    		
        		    		var CANUSE_MONEY=rowData.CANUSE_MONEY;
        		    		
        		    		if(POUNDAGE_WAY=="1" ){//平均冲抵
        		    			if(REMIND_FLAG==1 && CANUSE_MONEY >0 && STATUS==0){
        		    				approval="<a href='javascript:void(0)' onclick=changePJCD("+ JSON.stringify(rowData) +")>转平均冲抵</a>&nbsp;";
        		    			}
        		    			else{
        		    				approval="<font color='#BEBEBE'>转平均冲抵</font>";
        		    			}
        		    			
        		    			if(STATUS==1 && CANUSE_MONEY >0){
        		    				approval=approval +" | "+"<a href='javascript:void(0)' onclick=returnMoney("+JSON.stringify(rowData)+")>退款</a>"+" | "+"<a href='javascript:void(0)' onclick=changeBackMoney("+JSON.stringify(rowData)+")>转来款</a>";
        		    			}
        		    			else{
        		    				approval=approval +" | "+"<font color='#BEBEBE'>退款</font>";
        		    			}
//        		    			approval =approval + "<a href='javascript:void(0)' onclick=showzqx('"+rowData.ID+"','"+rowData.PROJECT_ID+"')>租前息</a>&nbsp;";
        		    		}else if(POUNDAGE_WAY=="2" ){//期末冲抵
        		    			if(REMIND_FLAG==1 && CANUSE_MONEY >0  && STATUS==0){
        		    				approval="<a href='javascript:void(0)' onclick=changeQMCD("+ JSON.stringify(rowData) +")>转期末冲抵</a>&nbsp;";
        		    			}
        		    			else{
        		    				approval="<font color='#BEBEBE'>转期末冲抵</font>";
        		    			}
        		    			
        		    			if(STATUS==1 && CANUSE_MONEY >0){
        		    				approval=approval +" | "+"<a href='javascript:void(0)' onclick=returnMoney("+JSON.stringify(rowData)+")>退款</a>"+" | "+"<a href='javascript:void(0)' onclick=changeBackMoney("+JSON.stringify(rowData)+")>转来款</a>";
        		    			}
        		    			else{
        		    				
        		    				approval=approval +" | "+"<font color='#BEBEBE'>退款</font>";
        		    			}
        		    		}else{//期末退回
        		    			if(((REMIND_FLAG==1 && STATUS==0) || STATUS==1) && CANUSE_MONEY >0){
        		    				approval="<a href='javascript:void(0)' onclick=returnMoney("+JSON.stringify(rowData)+")>退款</a>&nbsp;"+" | "+"<a href='javascript:void(0)' onclick=changeBackMoney("+JSON.stringify(rowData)+")>转来款</a>";
        		    			}
        		    			else{
        		    				approval="<font color='#BEBEBE'>退款</font>";
        		    			}
        		    		}
        		    		
        		    		return approval;
        		    	}
        		       }
        		 ]],view:detailview,
					detailFormatter : function(index, row) {
						return '<div id="ddv-' + row.POOL_ID + '" style="padding:5px 0;"></div>';
					},
					onExpandRow : function(index, row) {
						jQuery.ajax({
							url:_basePath+"/renterpool/CashDeposit!queryDetailByPoolId.action?POOL_ID="+row.POOL_ID,  
							type:'post',
							dataType:'json',
						    success: function(json){
								var data = {flag:json.flag,total:json.data.length,rows:json.data};
								var pRowIndex = "ddv-"+row.POOL_ID;
								$('#ddv-'+row.POOL_ID).datagrid({
									fitColumns:true,
									columns:[[
												{field:'TYPE',title:'类型',width:70,formatter:function(value,rowData,rowIndex){
												    if(value == 1){//平均冲抵
														   return "抵扣";
													    }else if(value == 2){
													    	return "退回";
													    }else{
													    	return "转来款";
													    }
												}},
					                            {field:'CREDIT_TIME',align:'center',width:100,title:'操作时间'},
					                            {field:'USERMONEY',align:'center',width:120,title:'操作金额'},
					                            {field:'FUND_CODE',align:'center',width:150,title:'单据编号'}
									         ]],
									onResize:function(){
				                        $('#pageTable').datagrid('fixDetailRowHeight',index);
				                    },
				                    onLoadSuccess:function(){
				                        setTimeout(function(){
				                            $('#pageTable').datagrid('fixDetailRowHeight',index);
				                        },0);
				                    }
								});
								 $('#pageTable').datagrid('fixDetailRowHeight',index);
									$('#ddv-'+row.POOL_ID).datagrid("loadData",data);
						}
					});
					},
        		 columns:[[
        		         {field:'CLIENT_ID',hidden:true},
        		         {field:'SUP_ID',hidden:true},
        		         {field:'POOL_ID',hidden:true},
        		         {field:'PAY_STATUSFLAG',title:'还款计划状态',width:100,align:'center'},
        		         {field:'STATUSFLAG',title:'资金池状态',width:100,align:'center'},
        		         {field:'POUNDAGE_WAY',title:'类型',width:70,formatter:function(value,rowData,rowIndex){
        		        	    if(value == 1){//平均冲抵
	  							   return "平均冲抵";
	  						    }else if(value == 2){//期末冲抵
	  							   return "期末冲抵";
	  							}
	  						    else{
	  						    	return "期末退回";
	  						    }
        		         }},
        		         {field:'SUP_NAME',title:'供应商',width:170},
        		         {field:'CUST_NAME',title:'承租人名称',width:170,formatter:function(value,rowData,rowIndex){
        		        	 return "<a href='#' onclick='toViewCust("+ JSON.stringify(rowData) +")'>"+value+"</a>";
        		         }},
        		         {field:'PRO_CODE',title:'项目编号',width:150}, 
        		         {field:'LEASE_CODE',title:'合同编号',width:150}, 
        		          {field:'PAYLIST_CODE',title:'还款计划编号',width:150,formatter:function(value,rowData,rowIndex){
        		        	 return "<a href='javascript:void(0)' onclick=showDetail('"+rowData.PAY_ID+"','"+rowData.PROJECT_ID+"','"+rowData.PAYLIST_CODE+"')>"+ value +"</a>　";
        		         }},
        		         {field:'PAY_TIME',title:'付款时间',width:100}, 
        		         {field:'BASE_MONEY',title:'保证金总额',width:120}, 
        		         {field:'CANUSE_MONEY',title:'保证金余款额度',width:120}
        		 ]]
        	});
        });

//查詢
function se() {
	var CUST_NAME = $("#CUST_NAME").val();
	var SUP_NAME = $("#SUP_NAME").val();
	var STATUS1 = $("#STATUS1").val();

	var PRO_CODE = $("#PRO_CODE").val();
	var LEASE_CODE = $("#LEASE_CODE").val();
	var PAYLIST_CODE = $("#PAYLIST_CODE").val();
	var POUNDAGE_WAY = $("#POUNDAGE_WAY").val();
	$('#pageTable').datagrid('load', {
		"CUST_NAME" : CUST_NAME,
		"SUP_NAME" : SUP_NAME,
		"STATUS" : STATUS1,
		"PRO_CODE" : PRO_CODE,
		"LEASE_CODE" : LEASE_CODE,
		"PAYLIST_CODE" : PAYLIST_CODE,
		"POUNDAGE_WAY" : POUNDAGE_WAY
	});
}


function toViewCust(row) {
	var value=row.CLIENT_ID;
	var type=row.CUST_TYPE;
	top.addTab("查看客户明细", _basePath
			+ "/customers/Customers!toViewCustInfoMain.action?CLIENT_ID=" + value
			+ "&TYPE=" + type + "&tab=view");

}


function showDetail(ID,PROJECT_ID,PAYLIST_CODE){
	top.addTab(PAYLIST_CODE+"还款计划明细",_basePath+"/pay/PayTask!toMgshowDetail.action?ID="+ID+"&PROJECT_ID="+PROJECT_ID);
}


function changePJCD(row){
	var POOL_ID=row.POOL_ID;
	var PAYLIST_CODE=row.PAYLIST_CODE;
	var PROJECT_ID=row.PROJECT_ID;
	var CLIENT_ID=row.CLIENT_ID;
	var PAY_STATUS=row.PAY_STATUS;
	if(PAY_STATUS == 0 || PAY_STATUS == 4 || PAY_STATUS == 7 || PAY_STATUS == 8){
		$.ajax({
			url:_basePath+"/renterpool/CashDeposit!changePJCDJBPM.action?PAYLIST_CODE="+PAYLIST_CODE+"&POOL_ID="+POOL_ID+"&PROJECT_ID="+PROJECT_ID+"&CLIENT_ID="+CLIENT_ID,
			type:"post",
			dataType:"json",
			success:function (json){
				if(json.flag){
					$.messager.alert("提示","发起流程成功！",'info',function(){
						$.messager.alert("提示",json.msg+json.data,"info",function(){
							$('#pageTable').datagrid('load');
						});
					});	
				}else{
					alert(PAYLIST_CODE+json.msg);
				}
			}
		});
	}
	else{
		alert("还款计划"+PAYLIST_CODE+"正在被占用，请关注还款计划状态。");
	}
	
}

function changeQMCD(row){
	var POOL_ID=row.POOL_ID;
	var PAYLIST_CODE=row.PAYLIST_CODE;
	var PROJECT_ID=row.PROJECT_ID;
	var CLIENT_ID=row.CLIENT_ID;
	var PAY_STATUS=row.PAY_STATUS;
	if(PAY_STATUS == 0 || PAY_STATUS == 4 || PAY_STATUS == 7 || PAY_STATUS == 8){
		$.ajax({
			url:_basePath+"/renterpool/CashDeposit!changeQMCDJBPM.action?PAYLIST_CODE="+PAYLIST_CODE+"&POOL_ID="+POOL_ID+"&PROJECT_ID="+PROJECT_ID+"&CLIENT_ID="+CLIENT_ID,
			type:"post",
			dataType:"json",
			success:function (json){
				if(json.flag){
					$.messager.alert("提示","发起流程成功！",'info',function(){
						$.messager.alert("提示",json.msg+json.data,"info",function(){
							$('#pageTable').datagrid('load');
						});
					});	
				}else{
					alert(PAYLIST_CODE+json.msg);
				}
			}
		});
	}else{
		alert("还款计划"+PAYLIST_CODE+"正在被占用，请关注还款计划状态。");
	}
}


//退款
function returnMoney(row) {
	var PAYLIST_CODE=row.PAYLIST_CODE;
	var PAY_STATUS=row.PAY_STATUS;
	if(PAY_STATUS == 0 || PAY_STATUS == 4 || PAY_STATUS == 7 || PAY_STATUS == 8){
		$.ajax({
			url:_basePath+"/renterpool/CashDeposit!getCust_Bank_Info.action?CUST_ID="+row.CLIENT_ID+"&CUST_NAME="+encodeURI(row.CUST_NAME)+"&TYPE=2&PAYLIST_CODE="+PAYLIST_CODE,
			type:"post",
			dataType:"json",
			success:function (json){
				if(json.flag){
					$("input[name='PRO_CODE_D']").val(row.PRO_CODE);
					$("input[name='LEASE_CODE_D']").val(row.LEASE_CODE);
					$("input[name='PAYLIST_CODE_D']").val(row.PAYLIST_CODE);
					$("input[name='CUST_ID_D']").val(row.CLIENT_ID);
					$("input[name='CUST_NAME_D']").val(row.CUST_NAME);
					$("input[name='SUP_ID_D']").val(row.SUP_ID);
					$("input[name='SUPP_NAME_D']").val(row.SUP_NAME);
					$("input[name='POOL_ID_D']").val(row.POOL_ID);
					$("input[name='REFUND_MONEY']").val(row.CANUSE_MONEY);
					$("input[name='PROJECT_ID_D']").val(row.PROJECT_ID);
					
					if (json.data != null) {
						$("input[name='RE_PAYEE_UNIT']").val(json.data.PAYEE_NAME);
						$("input[name='RE_PAYEE_ACCOUNT']").val(json.data.PAY_BANK_ACCOUNT);
						$("input[name='RE_PAYEE_BANK']").val(json.data.PAY_BANK_NAME);
						$("input[name='RE_PAYEE_BANK_ADDR']").val(json.data.PAY_BANK_ADDRESS);
					}
					$('#returnMoney').dialog('open').dialog('setTitle',PAYLIST_CODE+'客户保证金退款单');
				}else{
					alert(PAYLIST_CODE+json.msg);
				}
			}
		});
	}else{
		alert("还款计划"+PAYLIST_CODE+"正在被占用，请关注还款计划状态。");
	}
	
	
}

function REPANYEECHANGE(){
	var RE_PAYEE_TYPE=$("select[name='RE_PAYEE_TYPE']").val();
	var url="";
	if(RE_PAYEE_TYPE == '2'){
		var CUST_ID=$("input[name='CUST_ID_D']").val();
		var CUST_NAME=$("input[name='CUST_NAME_D']").val();
		var PAYLIST_CODE=$("input[name='PAYLIST_CODE_D']").val();
		url=_basePath+"/renterpool/CashDeposit!getCust_Bank_Info.action?CUST_ID="+CUST_ID+"&CUST_NAME="+encodeURI(CUST_NAME)+"&TYPE=2&PAYLIST_CODE="+PAYLIST_CODE;
	}else{
		var SUP_ID=$("input[name='SUP_ID_D']").val();
		var SUP_NAME=$("input[name='SUPP_NAME_D']").val();
		var PAYLIST_CODE=$("input[name='PAYLIST_CODE_D']").val();
		url=_basePath+"/renterpool/CashDeposit!getCust_Bank_Info.action?SUP_ID="+SUP_ID+"&SUP_NAME="+encodeURI(SUP_NAME)+"&TYPE=1&PAYLIST_CODE="+PAYLIST_CODE;
	}
	$.ajax({
		url:url,
		type:"post",
		dataType:"json",
		success:function (json){
			if(json.flag){
				if (json.data != null) {
					$("input[name='RE_PAYEE_UNIT']").val(json.data.PAYEE_NAME);
					$("input[name='RE_PAYEE_ACCOUNT']").val(json.data.PAY_BANK_ACCOUNT);
					$("input[name='RE_PAYEE_BANK']").val(json.data.PAY_BANK_NAME);
					$("input[name='RE_PAYEE_BANK_ADDR']").val(json.data.PAY_BANK_ADDRESS);
				}
			}else{
				alert(PAYLIST_CODE+json.msg);
			}
		}
	});
}

function getMoneyJY(){
	var USER_MONEY=parseFloat($("input[name='USER_MONEY']").val());
	var REFUND_MONEY=parseFloat($("input[name='REFUND_MONEY']").val());
	if(REFUND_MONEY<0){
		$("input[name='REFUND_MONEY']").val(0);
	}
	else if(REFUND_MONEY>USER_MONEY){
		$("input[name='REFUND_MONEY']").val(USER_MONEY);
	}
}

function getChangeMoneyJY(){
	var USER_MONEY=parseFloat($("input[name='USER_MONEY_C']").val());
	var REFUND_MONEY=parseFloat($("input[name='REFUND_MONEY_C']").val());
	if(REFUND_MONEY<0){
		$("input[name='REFUND_MONEY_C']").val(0);
	}
	else if(REFUND_MONEY>USER_MONEY){
		$("input[name='REFUND_MONEY_C']").val(USER_MONEY);
	}
}

// 保存
function saveRefund() {

	var POOL_ID_D=$("input[name='POOL_ID_D']").val();
	var RE_MONEY=$("input[name='REFUND_MONEY']").val();
	var RE_DATE=$("input[name='PAY_TIME']").val();
	var CUST_ID=$("input[name='CUST_ID_D']").val();
	var CUST_NAME=$("input[name='CUST_NAME_D']").val();
	var SUP_ID=$("input[name='SUP_ID_D']").val();
	var SUP_NAME=$("input[name='SUPP_NAME_D']").val();
	var RE_PAYEE_UNIT=$("input[name='RE_PAYEE_UNIT']").val();
	var RE_PAYEE_ACCOUNT=$("input[name='RE_PAYEE_ACCOUNT']").val();
	var RE_PAYEE_BANK=$("input[name='RE_PAYEE_BANK']").val();
	var RE_PAYEE_BANK_ADDR=$("input[name='RE_PAYEE_BANK_ADDR']").val();
	var RE_PAYEE_TYPE=$("select[name='RE_PAYEE_TYPE']").val();
	var PROJECT_ID=$("input[name='PROJECT_ID_D']").val();
	var PAYLIST_CODE=$("input[name='PAYLIST_CODE_D']").val();
	
	if(RE_PAYEE_UNIT == "" || RE_PAYEE_UNIT == undefined || RE_PAYEE_UNIT==null) {
		jQuery.messager.alert("提示", "请填写收款单位!");
	}
	
	if(RE_PAYEE_BANK == "" || RE_PAYEE_BANK == undefined || RE_PAYEE_BANK==null) {
		jQuery.messager.alert("提示", "请填写收款账户开户行!");
	}
	
	if(RE_PAYEE_ACCOUNT == "" || RE_PAYEE_ACCOUNT == undefined || RE_PAYEE_ACCOUNT==null) {
		jQuery.messager.alert("提示", "请填写收款账户!");
	}
	
	if(RE_PAYEE_BANK_ADDR == "" || RE_PAYEE_BANK_ADDR == undefined || RE_PAYEE_BANK_ADDR==null) {
		jQuery.messager.alert("提示", "请填写收款账户开户行地址!");
	}
	
	if (RE_DATE == '') {
		jQuery.messager.alert("提示", "请填写退款时间!");
	} else {
		jQuery.ajax({
			url : _basePath + "/renterpool/CashDeposit!refundBackSave.action",
			type : "post",
			data : "POOL_ID=" + POOL_ID_D + "&RE_MONEY="
					+ RE_MONEY + "&RE_DATE=" + RE_DATE
					+ "&CUST_ID=" + CUST_ID + "&CUST_NAME=" + encodeURI(CUST_NAME)
					+ "&SUP_ID=" + SUP_ID + "&SUP_NAME="
					+ encodeURI(SUP_NAME) + "&RE_PAYEE_UNIT=" + encodeURI(RE_PAYEE_UNIT)
					+ "&RE_PAYEE_TYPE=" + RE_PAYEE_TYPE
					+ "&PROJECT_ID=" + PROJECT_ID
					+ "&PAYLIST_CODE=" + PAYLIST_CODE
					+ "&RE_PAYEE_ACCOUNT=" + encodeURI(RE_PAYEE_ACCOUNT)
					+ "&RE_PAYEE_BANK=" + encodeURI(RE_PAYEE_BANK)
					+ "&RE_PAYEE_BANK_ADDR=" + encodeURI(RE_PAYEE_BANK_ADDR),
			dataType : "json",
			success : function(res) {
				if (res.flag == true) {
					jQuery.messager.alert("提示", res.msg);
					$('#returnMoney').dialog('close');
					$('#pageTable').datagrid('reload');
				} else {
					jQuery.messager.alert("提示", res.msg);
				}
			}

		});
	}
}


//转来款
function changeBackMoney(row) {
	var PAYLIST_CODE=row.PAYLIST_CODE;
	var PAY_STATUS=row.PAY_STATUS;
	if(PAY_STATUS == 0 || PAY_STATUS == 4 || PAY_STATUS == 7 || PAY_STATUS == 8){
		$.ajax({
			url:_basePath+"/renterpool/CashDeposit!getCust_Change_Info.action?PAYLIST_CODE="+PAYLIST_CODE,
			type:"post",
			dataType:"json",
			success:function (json){
				if(json.flag){
					$("input[name='PRO_CODE_C']").val(row.PRO_CODE);
					$("input[name='LEASE_CODE_C']").val(row.LEASE_CODE);
					$("input[name='PAYLIST_CODE_C']").val(row.PAYLIST_CODE);
					$("input[name='CUST_ID_C']").val(row.CLIENT_ID);
					$("input[name='CUST_NAME_C']").val(row.CUST_NAME);
					$("input[name='SUP_ID_C']").val(row.SUP_ID);
					$("input[name='SUPP_NAME_C']").val(row.SUP_NAME);
					$("input[name='POOL_ID_C']").val(row.POOL_ID);
					$("input[name='USER_MONEY_C']").val(row.CANUSE_MONEY);
					$("input[name='REFUND_MONEY_C']").val(row.CANUSE_MONEY);
					$("input[name='PROJECT_ID_C']").val(row.PROJECT_ID);
					
					
					$('#changeBackMoney').dialog('open').dialog('setTitle',PAYLIST_CODE+'客户保证金转来款');
				}else{
					alert(PAYLIST_CODE+json.msg);
				}
			}
		});
	}else{
		alert("还款计划"+PAYLIST_CODE+"正在被占用，请关注还款计划状态。");
	}
	
	
}

function changeMoneyCome(){
	var PAYLIST_CODE=$("input[name='PAYLIST_CODE_C']").val();
	if(confirm("确定转来款吗？")){
		var POOL_ID_C=$("input[name='POOL_ID_C']").val();
		var RE_MONEY=$("input[name='REFUND_MONEY_C']").val();
		var CUST_ID=$("input[name='CUST_ID_C']").val();
		var CUST_NAME=$("input[name='CUST_NAME_C']").val();
		var SUP_ID=$("input[name='SUP_ID_C']").val();
		var SUP_NAME=$("input[name='SUPP_NAME_C']").val();
		
		var RE_PAYEE_TYPE=$("select[name='RE_PAYEE_TYPE_C']").val();
		var PROJECT_ID=$("input[name='PROJECT_ID_C']").val();
		var PAYLIST_CODE=$("input[name='PAYLIST_CODE_C']").val();
		
		jQuery.ajax({
			url : _basePath + "/renterpool/CashDeposit!changeMoneyCome.action",
			type : "post",
			data : "POOL_ID=" + POOL_ID_C 
					+ "&RE_MONEY="+ RE_MONEY 
					+ "&CUST_ID=" + CUST_ID 
					+ "&CUST_NAME=" + encodeURI(CUST_NAME)
					+ "&SUP_ID=" + SUP_ID 
					+ "&SUP_NAME="+ encodeURI(SUP_NAME) 
					+ "&RE_PAYEE_TYPE=" + RE_PAYEE_TYPE
					+ "&PROJECT_ID=" + PROJECT_ID
					+ "&PAYLIST_CODE=" + PAYLIST_CODE,
			dataType : "json",
			success : function(res) {
				if (res.flag == true) {
					jQuery.messager.alert("提示", res.msg);
					$('#changeBackMoney').dialog('close');
					$('#pageTable').datagrid('reload');
				} else {
					jQuery.messager.alert("提示", res.msg);
				}
			}

		});
	}
}