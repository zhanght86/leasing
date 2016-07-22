function thewMethod(value,RE_ID){
    if(confirm("确定要解冻此DB保证金吗？")){
	   jQuery.ajax({
			url: "RefundProcess!refundFreeze.action",
			data: "POOL_ID="+value+"&STATUS=1"+"&RE_ID="+RE_ID,
			dataType:"json",
			success: function(res){
				if(res.flag==true){
					jQuery.messager.alert("提示",res.msg);
					$('#pageTable').datagrid('reload');
			   }
			   else{
				   jQuery.messager.alert("提示",res.msg);
			   }
			}
		 });
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
                url:'RefundDBApply!getDebtDropDownList.action?RE_ID='+row.RE_ID,
                singleSelect:true,
                rownumbers:true,
                loadMsg:'',
                height:'auto',
                columns:[[
                    {field:'CUST_NAME',title:'承租人',width:100,align:'right'},
                    {field:'PRO_CODE',title:'项目编号',width:100,align:'right'},
					{field:'PRO_NAME',title:'项目名称',width:200,align:'right'},
					{field:'BASE_MONEY',title:'保证金额',width:100,align:'right'},
					{field:'CANUSE_MONEY',title:'退款金额',width:100,align:'right'},
					{field:'PAY_TIME',title:'来款时间',width:100,align:'right'},
					{field:'STATUS',title:'状态',width:100,align:'right'},
					{field:'POOL_ID',title: '操作',width:80,align:'center',formatter:function(value,rowData,rowIndex){
				            var html = "<a href='javascript:void(0);' onclick='thewMethod("+value+","+rowData.RE_ID+")'>解冻</a>";
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
        }
    });
    $('.linkbutton').linkbutton();
    $('.datebox').datebox();
});