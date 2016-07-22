$(function() {
	$('#pageTable').datagrid({
		url:_basePath+"/cashDeposit/CashDeposit!toMgCashDeposit.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,		
		fit:true,
		fitColumns:true,
		toolbar:'#pageForm',
		pageSize:20,
		columns:[[
		          {field:'ID',align:'center',width:50,title:'操作',formatter:function(value, rows, index){
		        	  if(rows.DEPOSIT_STATUS==null||rows.DEPOSIT_STATUS==0||rows.DEPOSIT_STATUS==3){
		        		  return "<a href='#' onclick='toTuiKuanPage("+rows.ID+"\,"+rows.POUNDAGE_WAY+"\,"+rows.BAOZHENGJIN+"\,"
		        		  		+rows.SCHEME_ID+"\,\""+rows.PRO_CODE+"\"\,\""+rows.PAYLIST_CODE+"\");'>退款</a>";
		        		  		//+"|| <a href='#' onclick='toViewApp("+rows.ID+"\,"+rows.FRF_ID+"\,"+rows.POUNDAGE_WAY+"\,"+rows.BAOZHENGJIN+"\,"+rows.SCHEME_ID+"\,\""+rows.PRO_CODE+"\"\,\""+rows.PAYLIST_CODE+"\");'>退款查看</a>";
		        	  }else if(rows.DEPOSIT_STATUS==1||rows.DEPOSIT_STATUS==2){
		        	  	  return "<a href='#' onclick='toViewApp("+rows.ID+"\,"+rows.FRF_ID+"\,"+rows.POUNDAGE_WAY+"\,"+rows.BAOZHENGJIN+"\,"
		        	  	  		+rows.SCHEME_ID+"\,\""+rows.PRO_CODE+"\"\,\""+rows.PAYLIST_CODE+"\");'>退款查看</a>";
		        	  }
				   }},
		          {field:'DEPOSIT_STATUS',align:'center',width:40,title:'申请状态', formatter:function(value,rows,index){
		        	  if(value==3){
		        		  return "退款失败";
		        	  }else if(value==1){
		        		  return "已申请退款"; 
		        	  }else if(value==2){
		        		  return "退款成功";
		        	  }else {
		        		  return "未申请退款";
		        	  }
		          }},
		          {field:'PRO_CODE',align:'center',width:40,title:'项目编号'},
		          {field:'LEASE_CODE',align:'center',width:40,title:'融资租赁合同号'},
		          //{field:'PAYLIST_CODE',align:'center',width:40,title:'支付表编号'},
		          {field:'CLIENT_NAME',align:'center',width:40,title:'客户名称'},
		          {field:'CREATE_TIME',align:'center',width:40,title:'项目创建日期'},
		          {field:'START_DATE',align:'center',width:40,title:'起租日期'},
		          {field:'PAY_MONEY',align:'center',width:40,title:'未还款金额'},
		          {field:'BAOZHENGJIN',align:'center',width:40,title:'客户保证金金额'},
		          {field:'POUNDAGE_WAY',align:'center',width:40,title:'保证金处理方式',formatter:function(value, rows, index){
		        	  if(value==1){
		        		return "平均冲抵";  
		        	  }else if(value==2){
		        		  return "期末冲抵";
		        	  }else {
		        		  return "期末退回";
		        	  }
		          }},
		  
		          {field:'TX',align:'center',width:10,title:'',formatter:function(value,rows,index){
		        	  if(value==0){
		        		  return "<div class='icon-green'>&nbsp;</div>";
		        	  }else {
		        		  return "";
		        	  }
		          }}
		          ]]

		//以下为”+“号展看显示内容。
//		view : detailview,
//		detailFormatter : function(index, row) {
//			return '<div id="ddv-' + index + '" style="padding:5px 0;"></div>';
//		}
//		onExpandRow : function(index, row) {
//			jQuery.ajax({
//				url:_basePath+"/fundNotEBank/FundNotEBank!getFundDetailData.action?FUND_ID="+row.HEAD_ID,  
//				type:'post',
//				dataType:'json',
//			    success: function(json){
//				var data = {flag:json.flag,total:json.data.length,rows:json.data};
//				var pRowIndex = "ddv-"+index;
//				$('#ddv-'+index).datagrid({
//					fitColumns:true,
//                    singleSelect:true,
//					rownumbers:true,
//                    loadMsg:'加载中...',
//                    height:'auto',
//                    columns:[[                     
//                            {field:'D_PROJECT_CODE',align:'center',width:40,title:'项编号'},
//  						    {field:'D_CLIENT_CODE',align:'center',hidden:true,width:50},
//                            {field:'D_CLIENT_NAME',align:'center',width:50,title:'客户名称'},
//                            {field:'PAYMENT_STATUS',align:'center',width:35,title:'放款方式',formatter:function(value,row,index){
//                            	if(value=="1"){
//                            		return "全额放款";
//                            	}else if(value=="3"){
//                            		return "部分差额放款";
//                            	}else if(value=="4"){
//                            		return "DB差额放款";
//                            	}
//                            }},
//                            {field:'PRODUCT_NAME',align:'center',width:45,title:'租赁物类型'},
//                            {field:'COMPANY_NAME',align:'center',width:50,title:'厂商'},
//                            
//                            {field:'LEASE_TOPRIC',align:'center',width:60,title:'租赁物购买价款'},
//  						    {field:'D_PAY_CODE',align:'center',width:50,hidden:true},
//                            {field:'FIRST_MONEY',align:'center',width:50,title:'首期款合计'},
//                            {field:'OTHER_MONEY',align:'center',width:50,title:'其他费用合计'},
//                            {field:'DB_MONEY',align:'center',width:50,title:'DB保证金'},
//                            {field:'CS_MONEY',align:'center',width:50,title:'厂商保证金'},
//                            {field:'YS_MONEY',align:'center',width:50,title:'应收金额',hidden:true},
//                            {field:'RECEIVABLE',align:'center',width:50,title:'本次应收金额',hidden:true},
//  						  {field:'VERITABLE_MONEY',align:'center',width:50,title:'本次实收金额'}
//                     ]],
//                     
//                     onResize:function(){
//                        $('#pageTable').datagrid('fixDetailRowHeight',index);
//                    },
//                    onLoadSuccess:function(){
//                        setTimeout(function(){
//                            $('#pageTable').datagrid('fixDetailRowHeight',index);
//                        },0);
//                    }
//				});
//
//                $('#pageTable').datagrid('fixDetailRowHeight',index);
//				$('#ddv-'+index).datagrid("loadData",data);
//				}
//			});
//		}
	});
});

/**
 * 查询模糊
 * @author 杨雪
 * @return
 */
function toSe() {
	var LEASE_CODE = $("input[name='LEASE_CODE']").val();
	var CLIENT_NAME = $("input[name='CLIENT_NAME']").val();
	var PRO_CODE = $("input[name='PRO_CODE']").val();
	//var PAYLIST_CODE = $("input[name='PAYLIST_CODE']").val();
	$('#pageTable').datagrid('load', {
		"LEASE_CODE" : LEASE_CODE,
		"CLIENT_NAME" : CLIENT_NAME,
		"PRO_CODE" : PRO_CODE
	//	"PAYLIST_CODE" : PAYLIST_CODE
	});
}


/**
 * 清空查询数据
 * @author 杨雪
 * @return
 */
function clearQuery(){
	$(".paramData").each(function(){
		$(this).val("");
	});
}

/**
 * 加载客户保证金退款页面
 * @param id  项目id
 * @param POUNDAGE_WAY 保证金处理方式
 * @param BAOZHENGJIN 保证金金额
 * @param SCHEME_ID 方案id
 * @return
 */
function toTuiKuanPage(id,POUNDAGE_WAY,BAOZHENGJIN,SCHEME_ID,PRO_CODE,PAYLIST_CODE){
	top.addTab("保证金退款", _basePath+ "/cashDeposit/CashDeposit!toAddReturnApp.action?ID="+id+"&POUNDAGE_WAY="
				+POUNDAGE_WAY+"&BAOZHENGJIN="+BAOZHENGJIN+"&SCHEME_ID="+SCHEME_ID+"&PRO_CODE="+PRO_CODE+"&PAYLIST_CODE="
				+PAYLIST_CODE+"&date="+new Date().getTime());
}

/**
 * 保存保证金退款
 * @return
 */
function doSaveReturn(){
	$('#saveForm').form('submit',{
		type: "post",
		 url: _basePath+ "/cashDeposit/CashDeposit!doSaveReturn.action",
		success:function(json){
			date = $.parseJSON(json);
			if (json.flag) {
				alert("提示 ： "+ encodeURI(json.msg));
				top.addTab("客户保证金管理",_basePath + '/cashDeposit/CashDeposit.action');
				top.closeTab("保证金退款");
			} else {
				$.messager.alert("提示", date.msg);
			}
		},
		error : function(e) {
			alert(e.message);
		}
	});
}

/**
 * 查看保证金退款
 * @return
 */
function toViewApp(id,FRF_ID,POUNDAGE_WAY,BAOZHENGJIN,SCHEME_ID,PRO_CODE,PAYLIST_CODE){
	top.addTab("保证金退款", _basePath+ "/cashDeposit/CashDeposit!toViewReturnApp.action?ID="+id+"&POUNDAGE_WAY="
			+POUNDAGE_WAY+"&BAOZHENGJIN="+BAOZHENGJIN+"&SCHEME_ID="+SCHEME_ID+"&PRO_CODE="+PRO_CODE+"&PAYLIST_CODE="
			+PAYLIST_CODE+"&rf_id="+FRF_ID+"&date="+new Date().getTime());
}

function ceshi(){
	window.location.href=_basePath+"/fiReport/FiReport!fiFinaReportMain.action";
}