$(function(){
     $('#pageTable').datagrid({
    	 view: detailview,
    	 detailFormatter:function(index,row){
    	 return '<div style="padding:2px"><table id="ddv-'+index+'"></table></div>';
     },
     onExpandRow: function(index,row){
    	 jQuery.ajax({
     		url:_basePath+"/renterpool/CashDeposit!getCDDetainlByFundId.action?SOURCE_ID="+row.SOURCE_ID,  
				type:'post',
				dataType:'json',
			    success: function(json){
    		 	var data = {flag:json.flag,total:json.data.length,rows:json.data};
				var pRowIndex = "ddv-"+index;
				 $('#ddv-'+index).datagrid({
					 singleSelect: false,
                     selectOnCheck: true,
                     checkOnSelect: true,
                     rownumbers:true,
                     loadMsg:'',
                     height:'auto',
                     columns:[[
							{field:'FUND_ID',title:'付款单号',width:130,align:'right'},
							{field:'FI_ACCOUNT_DATE',title:'付款日期',width:130,align:'right'},
							{field:'SUP_SHORTNAME',title:'供应商',width:130,align:'right'},
							{field:'PRO_CODE',title:'项目编号',width:130,align:'right'},
							{field:'CUST_NAME',title:'客户名称',width:130,align:'right'},
							{field:'PRODUCT_NAME',title:'租赁物类型',width:130,align:'right'},
							{field:'LEASE_TERM',title:'租赁期次',width:130,align:'right'},
							{field:'D_RECEIVE_DATE',title:'计划日期',width:90,align:'right'},
							{field:'FA_CAN_USE_MONEY',title:'保证金',width:130,align:'right'},
							{field:'ZJ_MONEY',title:'租金',width:130,align:'right'},
							{field:'BJ_MONEY',title:'本金',width:100,align:'right'},
							{field:'LX_MONEY',title:'利息',width:100,align:'right'},
							{field:'WS_YQ',title:'违约金',width:100,align:'right'},
							{field:'XJZJ_MONEY',title:'需交租金',width:100,align:'right'}, 
							{field:'CANUSE_MONEY',title:'剩余保证金',width:100,align:'right'},
							{field:'ZYBZJ',title:'本次抵扣资金',width:100,align:'right'}
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
                   $('#ddv-'+index).datagrid("loadData",data);
     			}
    	 });
     }
     });
});

/**
 * EXCEL导出
 * @return
 */
function excelCDDetail(){
	var SUP_NAME = $("#SUP_NAME").val(); 
	var CUST_NAME = $("#CUST_NAME").val();
	var PRO_NAME = $("#PRO_NAME").val();
	var PRO_CODE = $("#PRO_CODE").val();
	window.location.href = _basePath+"/renterpool/CashDeposit!excelCDDetail.action?SUP_NAME="
							+encodeURI(SUP_NAME)+"&CUST_NAME="+encodeURI(CUST_NAME)+"&PRO_NAME="
							+encodeURI(PRO_NAME)+"&PRO_CODE="+encodeURI(PRO_CODE);
}