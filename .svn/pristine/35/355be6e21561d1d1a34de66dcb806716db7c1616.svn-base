function se(){
    var SUP_SHORTNAME = $("#SUP_SHORTNAME").val(); 
	var START_TIME = $("#START_TIME").datebox("getValue");
	var END_TIME = $("#END_TIME").datebox("getValue");
	var PRO_CODE = $("#PRO_CODE").val();
	var PRO_NAME = $("#PRO_NAME").val();
	var CLIENT_NAME = $("#CLIENT_NAME").val();
	var ON_CARD = $("#ON_CARD").val();
    $('#pageTable').datagrid('load', {"SUP_SHORTNAME":SUP_SHORTNAME,"START_TIME":START_TIME,"END_TIME":END_TIME,"PRO_CODE":PRO_CODE,"PRO_NAME":PRO_NAME,"CLIENT_NAME":CLIENT_NAME,"ON_CARD":ON_CARD});
}

function clearMess(){
    $("#form01").form('clear');
}
   
		//导出
function exportExcel(flag){
	//url
	var url = "VehicleInvoice!exportExcelInvoiceMess.action";
	var datagridList=$("#pageTable").datagrid('getChecked');
	var sqlData = [];		
	for(var i = 0; i < datagridList.length; i++)
	{
		sqlData.push("'"+datagridList[i].ID+"'");
	}
	if(flag == "all"){
		if($("#pageTable").datagrid('getRows').length <= 0){
			$.messager.alert('提示','无数据请勿点击导出','info',null);
			return;
		}
	}else{
		if(datagridList.length == 0){
			alert("请选择要导出的数据");
			return;
		}
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
        	se();
        }
    });
	
	$('#sqlData').remove();
}
 
		
function exportXML(flag){
   //url
	var url = "VehicleInvoice!exportInvoiceXML.action";
	var datagridList=$("#pageTable").datagrid('getChecked');
	var XMLData = [];
	for(var i = 0; i < datagridList.length; i++)
	{
		XMLData.push("'"+datagridList[i].ID+"'");
	}
	if(flag == "all"){
		if($("#pageTable").datagrid('getRows').length <= 0){
			$.messager.alert('提示','无数据请勿点击导出','info',null);
			return;
		}
	}else{
		if(datagridList.length == 0){
			alert("请选择要导出的数据");
			return;
		}
	}
	$('#form01').form('submit',{
        url:url,
        onSubmit: function(){
			//导出标识
			if($('#XMLData').length<=0){
				$('#form01').append('<input name=\"XMLData\"  id=\"XMLData\" type=\"hidden\" />');
			}
			$('#XMLData').val(XMLData.join(','));
        },
        success : function(data){
		    var json = JSON.parse(data);
		    if(json.flag){
            	se();
			}else{
				jQuery.messager.alert("错误",json.msg);
			}
        }
    });
	$('#XMLData').remove();
}
function reasion1(){
	 //url
	var url = "VehicleInvoice!invoiceFalseReasion.action";
	var proj_id = $('#PRO_CODE').val();
	if(proj_id.length<11){
		alert("请录入项目编号");
		return ;
	}
	$.ajax({
		url : 	url + "?PROJ_ID='"+proj_id+"'",
		type : "POST",
		dataType:"json",
		success : function(msg){
			$.messager.show({
                title:'操作反馈',
                msg:msg.msg,
                showType:'show'
            });
		}
	});
}

