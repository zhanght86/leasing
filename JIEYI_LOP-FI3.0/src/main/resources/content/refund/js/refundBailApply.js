function refundMoney(index,row){
     //获取选中的退款信息
	 var checkedItems = $('#ddv-'+index).datagrid('getChecked');
	 if(checkedItems.length > 0){
		 var names = [];
		 var refundMoney = 0;
         jQuery.each(checkedItems, function(index, item){
             refundMoney +=parseFloat(item.CANUSE_MONEY);
			 names.push(item.POOL_ID);
         }); 
		 var pool_ids = names.join(",");
		 $("#POOL_ID_ITEMS").val(pool_ids);
		 $("#SUPPLIER_NAME").text(row.SUP_SHORTNAME);
		 $("#PAY_TIME").datebox("setValue","");
		 $("#dlgRefund input[name='SUP_ID']").val(row.SUP_ID);
		 $("#dlgRefund input[name='REFUND_MONEY']").val(refundMoney);
		 $("#dlgRefund input[name='PROJECT_COUNT']").val(checkedItems.length);
		 $('#dlgRefund').dialog('open').dialog('setTitle','新建DB保证金退款单');
	 }else{
	     jQuery.messager.alert("提示","请选中要退DB保证金！");
	 }
}

function saveRefund(){
   var SUP_ID = $("#dlgRefund input[name='SUP_ID']").val();
   var REFUND_MONEY = $("#dlgRefund input[name='REFUND_MONEY']").val();
   var PROJECT_COUNT = $("#dlgRefund input[name='PROJECT_COUNT']").val();
   var POOL_ID_ITEMS = $("#dlgRefund input[name='POOL_ID_ITEMS']").val();
   var PAY_TIME = $("#PAY_TIME").datebox("getValue");
   if(PAY_TIME==''){
       jQuery.messager.alert("提示","请填写退款时间!");
   }else{
	   if(confirm("确定保存退款单吗？")){
	       jQuery.ajax({
				url: "RefundDBApply!refundApply.action",
				data: "RE_TYPE=2&SUP_ID="+SUP_ID+"&REFUND_MONEY="+REFUND_MONEY+"&PROJECT_COUNT="+PROJECT_COUNT+"&POOL_ID_ITEMS="+POOL_ID_ITEMS+"&PAY_TIME="+PAY_TIME,			
				dataType:"json",
				success: function(res){
					if(res.flag==true){
						jQuery.messager.alert("提示",res.msg);
						$('#dlgRefund').dialog('close');
						$('#pageTable').datagrid('reload');
				   }
				   else{
					   jQuery.messager.alert("提示",res.msg);
				   }
				}
					 
			 });
	     }
   }
}

$(function(){
    $('#pageTable').datagrid({
        view: detailview,
        detailFormatter:function(index,row){
            return '<div style="padding:2px"><table id="ddv-'+index+'"></table></div>';
        },
        onExpandRow: function(index,row){
            $('#ddv-'+index).datagrid({
                url:'RefundBailApply!getRefundBailDropList.action?SUP_ID='+row.SUP_ID+'&STATUS=1&POOL_TYPE=4',
                singleSelect: false,
                selectOnCheck: true,
                checkOnSelect: true,
                rownumbers:true,
                loadMsg:'',
                height:'auto',
                columns:[[
				    {field:'ck',checkbox:true },
                    {field:'CUST_NAME',title:'承租人',width:100,align:'right'},
                    {field:'PRO_CODE',title:'项目编号',width:100,align:'right'},
					{field:'PRO_NAME',title:'项目名称',width:200,align:'right'},
					{field:'BASE_MONEY',title:'保证金额',width:100,align:'right'},
					{field:'CANUSE_MONEY',title:'余款金额',width:100,align:'right'},
					{field:'PAY_TIME',title:'来款时间',width:100,align:'right'},
					{field:'STATUS',title:'状态',width:100,align:'right'},
					{field:'REMARK',title:'备注',width:100,align:'right'},
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
        }
    });
});