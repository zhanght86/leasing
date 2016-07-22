
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
					{field:'THAW_TIME',title:'解冻时间',width:100,align:'right'},
					{field:'STATUS',title:'状态',width:100,align:'right'},
					{field:'POOL_ID',title: '操作',width:80,align:'center',formatter:function(value,rowData,rowIndex){
			            var html = "";
			            if(rowData.STATUS=='预退款' && rowData.RE_STATUS=='0'){
						   html += "<a href='javascript:void(0);' onclick='removeMoney("+value+","+rowData.RE_ID+")'>移除款项</a>&nbsp;&nbsp;";
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
        }
    });
    $('.linkbutton').linkbutton();
    $('.datebox').datebox();
});

function revRefundApp(value){
    if(confirm("确定撤销此次退款，删除此退款单吗？")){
    	jQuery.ajax({
    		url:"RefundDBApply!delRefundDan.action?RE_ID="+value,
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

function removeMoney(value,re_id){
    if(confirm("确定从退款单中移除该款项吗？")){
	    jQuery.ajax({
    		url:"RefundDBApply!removeMoney.action?POOL_ID="+value+"&RE_ID="+re_id,
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

function delMethod(){
	var url = "RefundDBApply!delmainMethod.action";
    var datagridList=$("#pageTable").datagrid('getChecked');
	if($("#pageTable").datagrid('getRows').length <= 0){
		$.messager.alert('提示','无数据请勿点击删除','info',null);
		return;
	}
	var sqldelData = [];		
	for(var i = 0; i < datagridList.length; i++){
		var pa = {"RE_ID":datagridList[i].RE_ID}
		sqldelData.push(pa);
	}
	
	if(datagridList.length>0){
		$('#form01').form('submit',{
            url:url,
            onSubmit: function(){
    			//导出标识
    			if($('#sqldelData').length<=0){
    				$('#form01').append('<input name=\"sqldelData\"  id=\"sqldelData\" type=\"hidden\" />');
    			}
    			$('#sqldelData').val(JSON.stringify(sqldelData));
            },
            success : function(data){
            	alert(JSON.parse(data).msg);
            	search0();
            }
        });
    	$('#sqldelData').remove();
	}
	
}
