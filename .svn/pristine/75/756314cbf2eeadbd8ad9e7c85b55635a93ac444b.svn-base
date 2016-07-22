$(function(){
	$(".dialog").dialog();
	$(".linkbutton").linkbutton();
	
     $('#pageTable').datagrid({
    	 view: detailview,
    	 detailFormatter:function(index,row){
    	 return '<div style="padding:2px"><table id="ddv-'+index+'"></table></div>';
     },
     onExpandRow: function(index,row){
    	 jQuery.ajax({
     		url:_basePath+"/renterpool/ResidualMoney!getDetailList.action?CLIENT_ID="+row.CLIENT_ID,  
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
                            {field:'CLIENT_ID',checkbox:true}, 
							{field:'BASE_MONEY',title:'基础额度金额',width:130,align:'right'},
							{field:'CANUSE_MONEY',title:'可用余款金额',width:130,align:'right'},
							{field:'PAY_TIME',title:'来款时间',width:130,align:'right'},
							{field:'THAW_TIME',title:'解冻时间',width:130,align:'right'},
							{field:'SOURCE_ID',title:'付款单号',width:130,align:'right'},
							{field:'STATUS',title:'状态',width:130,align:'right'},
							{field:'POOL_ID',title: '操作',width:130,align:'center',formatter:function(value,rowData,rowIndex){
							            var html = "";
							            if(rowData.STATUS=='解冻'){
										   html += "<a href='javascript:void(0);' onclick='freezeMethod("+value+")'>冻结</a>&nbsp;&nbsp;";
										}else{
										   html += "<a href='javascript:void(0);' onclick='doFreezeMethod("+value+")'>解冻</a>";
										}
                                       return html;  
                               	}
       						}
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

//查詢
function se(){
	var CUST_NAME=$("#CUST_NAME").val(); 
    $('#pageTable').datagrid('load', {"CUST_NAME":CUST_NAME});
}

//操作
function operate(val, row, indext){
	return "<a href=javascript:void(0) onclick=returnMoney("+indext+","+JSON.stringify(row)+")>退款申请</a>";
}

//冻结
function freezeMethod(val) {
	 if(confirm("确定要冻结此资金吗？")){
			$.ajax({
				url:_basePath+"/renterpool/ResidualMoney!doUnfreezeMethod.action?POOL_ID="+val,  
				type:"post",
				dataType:"json",
				success:function(json){
					if(json.flag == true){
						alert("余款资金冻结成功！");
						window.location.href=_basePath+"/renterpool/ResidualMoney.action";
					}else {
						alert("余款资金冻结失败！");
						window.location.href=_basePath+"/renterpool/ResidualMoney.action";
					}
				}
			}); 
	 }
}

//解冻
function doFreezeMethod(val){
	if(confirm("确定要解冻此资金吗？")){
		$.ajax({
			url:_basePath+"/renterpool/ResidualMoney!doFreezeMethod.action?POOL_ID="+val,  
			type:"post",
			dataType:"json",
			success:function(json){
				if(json.flag == true){
					alert("余款资金解冻成功！");
					window.location.href=_basePath+"/renterpool/ResidualMoney.action";
				}else {
					alert("余款资金解冻失败！");
					window.location.href=_basePath+"/renterpool/ResidualMoney.action";
				}
			}
		});
	}
}

//退款
function returnMoney(index,row){
    //获取选中的退款信息
	
	 var checkedItems = $("#ddv-"+index).datagrid('getChecked');
	 if(checkedItems.length > 0){
		 var names = [];
		 var refundMoney = 0;
		
        $.ajax({
        	url:_basePath+"/renterpool/ResidualMoney!getOpenBank.action?CLIENT_ID="+row.CLIENT_ID,
        	type:"post",
        	dataType:"json",
        	success:function(json){
        	jQuery.each(checkedItems, function(index, item){
                refundMoney +=parseFloat(item.CANUSE_MONEY);
    			 names.push(item.POOL_ID);
            }); 
        	
        	 var pool_ids = names.join(",");
    		 $("#POOL_ID_ITEMS").val(pool_ids);
    		 $("#CUST_NAME_1").text(row.CUST_NAME);
    		 $("#RE_PAYEE_UNIT").val(row.CUST_NAME);
    		 for(var i=0;i<json.data.length;i++){
					$("#RE_PAYEE_ACCOUNT").append($("<option value='"+json.data[i].BANK_ACCOUNT+"'>"+json.data[i].BANK_ACCOUNT+"</option>"));
					$("#RE_PAYEE_BANK").append($("<option value='"+json.data[i].BANK_NAME+"'>"+json.data[i].BANK_NAME+"</option>"));				
					$("#RE_PAYEE_BANK_ADDR").append($("<option value='"+json.data[i].BANK_ADDRESS+"'>"+json.data[i].BANK_ADDRESS+"</option>"));				
			}
    		 
//    		 $("#RE_PAYEE_ACCOUNT").val(json.data.BANK_ACCOUNT);
//    		 $("#RE_PAYEE_BANK").val(json.data.BANK_NAME);
//    		 $("#RE_PAYEE_BANK_ADDR").val(json.data.BANK_ADDRESS);
    		 $("#PAY_TIME").datebox("setValue","");
    		 $("#returnMoney input[name='CLIENT_ID']").val(row.CLIENT_ID);
    		 $("#CUST_NAME_0").val(row.CUST_NAME);
    		 $("#returnMoney input[name='REFUND_MONEY']").val(refundMoney);
    		 $("#returnMoney input[name='PROJECT_COUNT']").val(checkedItems.length);
    		 $('#returnMoney').dialog('open').dialog('setTitle','新建承租人余款池退款单');
        	}
        });		
	 }else{
	     jQuery.messager.alert("提示","请选中要退款项！");
	 }
}

function saveRefund(){
	   var CUST_ID = $("#returnMoney input[name='CLIENT_ID']").val();
	   var CUST_NAME = $("#CUST_NAME_1").text();
	   var REFUND_MONEY = $("#returnMoney input[name='REFUND_MONEY']").val();
	   var PROJECT_COUNT = $("#returnMoney input[name='PROJECT_COUNT']").val();
	   var POOL_ID_ITEMS = $("#returnMoney input[name='POOL_ID_ITEMS']").val();
	   var PAY_TIME = $("#PAY_TIME").datebox("getValue");
	   var RE_PAYEE_UNIT = $("#returnMoney input[name='RE_PAYEE_UNIT']").val();
	   var RE_PAYEE_ACCOUNT = $("#returnMoney select[name='RE_PAYEE_ACCOUNT']").attr("selected",true).val();
	   var RE_PAYEE_BANK = $("#returnMoney select[name='RE_PAYEE_BANK']").attr("selected",true).val();
	   var RE_PAYEE_BANK_ADDR = $("#returnMoney select[name='RE_PAYEE_BANK_ADDR']").attr("selected",true).val();
	   if(PAY_TIME==''){
	       jQuery.messager.alert("提示","请填写退款时间!");
	   }else{
	       jQuery.ajax({
				url: _basePath+"/renterpool/ResidualMoney!refundApply.action",
				data: "CUST_ID="+CUST_ID+"&CUST_NAME="+CUST_NAME+"&REFUND_MONEY="+REFUND_MONEY+
					  "&PROJECT_COUNT="+PROJECT_COUNT+"&POOL_ID_ITEMS="+POOL_ID_ITEMS+"&PAY_TIME="
					  +PAY_TIME+"&RE_PAYEE_ACCOUNT="+RE_PAYEE_ACCOUNT+"&RE_PAYEE_BANK="
					  +RE_PAYEE_BANK+"&RE_PAYEE_BANK_ADDR="+RE_PAYEE_BANK_ADDR+"&RE_PAYEE_UNIT="+RE_PAYEE_UNIT,			
				dataType:"json",
				success: function(res){
					if(res.flag==true){
						jQuery.messager.alert("提示",res.msg);
						$('#returnMoney').dialog('close');
						$('#pageTable').datagrid('reload');
				   }
				   else{
					   jQuery.messager.alert("提示",res.msg);
				   }
				}
					 
			 });
	   }
}