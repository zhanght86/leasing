//导出
function exportRefundMess(flag){
	//url
	var url = "";
	var RE_TYPE = $("#RE_TYPE").val();
	if(RE_TYPE ==""){
	   $.messager.alert('提示','请选择要导出的退款类型！');
	   return;
	}
	if(flag =='main'){
	    url += "RefundDBApply!exportExcelData.action";
	}else{
	    url += "RefundDBApply!exportExcelDetail.action";
	}
	var datagridList=$("#pageTable").datagrid('getChecked');
	var sqlData = [];		
	for(var i = 0; i < datagridList.length; i++)
	{
		sqlData.push("'"+datagridList[i].RE_ID+"'");
	}

	if($("#pageTable").datagrid('getRows').length <= 0){
		$.messager.alert('提示','无数据请勿点击导出','info',null);
		return;
	}
	if(datagridList.length == 0){
		$.messager.alert('提示','请选择要申请的数据');
		return;
	}
	$('#form01').form('submit',{
        url:url,
        onSubmit: function(){
			//导出标识
			if($('#sqlData').length<=0){
				$('#form01').append('<input name=\"sqlData\"  id=\"sqlData\" type=\"hidden\" />');
			}
			$('#sqlData').val(sqlData.join(','));
        },
        success : function(){
        	search0();
        }
    });
	$('#sqlData').remove();
}
 
function submitRefundApp(value){
    jQuery.ajax({
		url:"RefundDBHeXiao!heXiaoMethod.action?RE_ID="+value,
		type:"post", 
		dataType:"json",
		success:function(res){
			if (res.flag==true){
               jQuery.messager.alert("提示",res.msg);
			   $('#pageTable2').datagrid('reload');
            }else{
			   jQuery.messager.alert("提示",res.msg);
    		} 
		}
	 });
}

function revRefundApp(value){
	    var url = "RefundDBApply!submitMethod.action";
	    var datagridList=$("#pageTable").datagrid('getChecked');
	    if($("#pageTable").datagrid('getRows').length <= 0){
		  $.messager.alert('提示','无数据请勿点击提交','info',null);
		  return;
	    }
  	    var sqlSubmitData = [];		
  	    for(var i = 0; i < datagridList.length; i++){
		   var pa = {"RE_ID":datagridList[i].RE_ID,"RE_STATUS":'10'};
  		   sqlSubmitData.push(pa);
  	    }
		if(datagridList.length>0){
			$('#form01').form('submit',{
	          url:url,
	          onSubmit: function(){
	  			//导出标识
	  			if($('#sqlSubmitData').length<=0){
	  				$('#form01').append('<input name=\"sqlSubmitData\"  id=\"sqlSubmitData\" type=\"hidden\" />');
	  			}
	  			$('#sqlSubmitData').val(JSON.stringify(sqlSubmitData));
	          },
	           success : function(data){
	          	alert(JSON.parse(data).msg);
	          	search0();
	          }
	      });
	  	$('#sqlSubmitData').remove();
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