$(function(){
     $('#pageTable_tk').datagrid({
    	 view: detailview,
    	 detailFormatter:function(index,row){
    	 return '<div style="padding:2px"><table id="ddv-'+index+'"></table></div>';
     },
     onExpandRow: function(index,row){
    	 jQuery.ajax({
     		url:_basePath+"/renterpool/CashDeposit!getRefundDetailData.action?RE_ID="+row.RE_ID,  
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
                            {field:'CLIENT_ID',hidden:true}, 
							{field:'PRO_CODE',title:'项目编号',width:130,align:'right'},
							{field:'CUST_NAME',title:'客户名称',width:130,align:'right'},
							{field:'PRODUCT_NAME',title:'租赁物名称',width:130,align:'right'},
							{field:'LEASE_TOPRIC',title:'租赁物购买价款',width:130,align:'right'},
							{field:'FINANCE_TOPRIC',title:'融资金额',width:130,align:'right'},
							{field:'COMPANY_NAME',title:'厂商',width:130,align:'right'},
							{field:'TYPE_NAME',title:'机型',width:90,align:'right'},
							{field:'WHOLE_ENGINE_CODE',title:'出厂编号',width:130,align:'right'},
							{field:'AMOUNT',title:'台量',width:130,align:'right'},
							{field:'START_CONFIRM_DATE',title:'起租确认日期',width:100,align:'right'},
							{field:'RE_DATE',title:'预退款日期',width:100,align:'right'},
							{field:'BZJ',title:'款项名称',width:100,align:'right'}, 
							{field:'RE_MONEY',title:'退款金额',width:100,align:'right'},
							{field:'PAY_DATE',title:'租赁到期日',width:100,align:'right'},
							{field:'STATUS',title:'结束方式',width:100,align:'right',formatter:function(value,row,index){
								if(value=="0"){
									return "正常";
								}else if(value=="3"){
									return "正常结清";
								}else if(value=="9"){
									return "提前还款";
								}else if(value=="5"){
									return "回购";
								}
							}}
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

function search0(){
    var SUP_SHORTNAME = $("#SUP_SHORTNAME").val(); 
   	var RE_PAYEE_UNIT = $("#RE_PAYEE_UNIT").val();
   	var RE_DATE = $("#RE_DATE").datebox("getValue");
   	var STATUS = $("#STATUS2").val();
   	$('#pageTable_tk').datagrid('load', {"SUP_SHORTNAME":SUP_SHORTNAME,"RE_PAYEE_UNIT":RE_PAYEE_UNIT,"RE_DATE":RE_DATE,"RE_STATUS":STATUS});
}

function clearQuery(){
	$("#RE_DATE").datebox("clear");
	$(".paramData").each(function() {
		$(this).val("");
	});
}
		
function addConfig(value,row,index){
	return "<a href=javascript:void(0) onclick=submitRefundApp("+value+") >提交评审</a>&nbsp;" +
			"<a href=javascript:void(0) onclick=revRefundApp("+value
			+",\"" +row.POOL_ID+"\") >撤销退款</a>";
}


function daochu(){
	var SUP_NAME = $("#SUP_NAME").val(); 
   	var RE_PAYEE_UNIT = $("#RE_PAYEE_UNIT").val();
   	var RE_DATE = $("#RE_DATE").datebox("getValue");
   	window.location.href= _basePath+"//renterpool/CashDeposit!excelDetail.action?SUP_NAME="+encodeURI(SUP_NAME)+"&RE_PAYEE_UNIT="+encodeURI(RE_PAYEE_UNIT)+"&RE_DATE="+encodeURI(RE_DATE);	
}


function submitRefundApp(value){
	if(confirm("确定要发起退款评审流程吗？")){
		jQuery.ajax({
			url:_basePath+"/renterpool/CashDeposit!startRefundApp.action?RE_ID="+value,
			type:"post", 
			dataType:"json",
			success:function(res){
			if (res.flag==true){
				jQuery.messager.alert("提示",res.msg);
				$('#pageTable').datagrid('reload');
			}else{
				jQuery.messager.alert("提示",res.msg);
			} 
			}
		});
	 }
}
		
function revRefundApp(value,POOL_ID){
    if(confirm("确定删除此退款单吗？")){
        jQuery.ajax({
        	url:_basePath+"/renterpool/CashDeposit!delRefundDan.action?RE_ID="+value+"&POOL_ID="+POOL_ID,
        	type:"post", 
        	dataType:"json",
        	success:function(res){
        	if (res.flag==true){
        		jQuery.messager.alert("提示",res.msg);
        		$('#pageTable').datagrid('reload');
        	}else{
        		jQuery.messager.alert("提示",res.msg);
        	} 
        }
        });
	}
}