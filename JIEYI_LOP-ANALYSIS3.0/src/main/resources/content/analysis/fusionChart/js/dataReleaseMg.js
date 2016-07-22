var url="";
$(function(){  
	//二级联动
	$('#MODULE_CODE').combobox({  
	    editable:false,  
	    onSelect:function(record){  
	        //重新载入节点，并清空当前输入的值  
		$('#CARD_CODE').combobox({  
	            disabled:false,  
	            url:_basePath+'/fusionChart/DataRelease!getTypeByModule.action?typeName='+encodeURI(record.value),  
	            valueField:'FLAG',  
	            textField:'FLAG'  
	        }).combobox('clear');  
	    }  
	}); 
});  
function se(){
	var NAME=$("#NAME").val();
	$('#pageTable').datagrid('load', {"NAME":NAME});
}

function getUtil(value,row,index){
	var str = "";
	if(row.FLAG == "0"){
		str = "启用";
	}else{
		str = "停用";
	}
    return "<a href=javascript:void(0) onclick=del("+row.ID+")>删除</a>&nbsp;|&nbsp;<a href=javascript:void(0) onclick=update("+row.ID+")>"+str+"</a>";
}
function getStatus(value,row,index){
	var str = "";
	if(value == "0"){
		str = "发布中";
	}else if(value == "1"){
		str = "发布成功";
	}else{
		str = "发布失败";
	}
    return str;
}

function update(ID){
	if (ID) {
		jQuery.ajax( {
			url : _basePath + "/fusionChart/DataRelease!updateFlagByID.action",
			data : "ID=" + ID,
			dataType : "json",
			success : function(res) {
				if (res.flag == true) {
					$('#pageTable').datagrid('reload');
				} else {
					$.messager.alert("提示", "操作失败,请稍后再试!");
				}
			}
		});
	}
}

function newOne(){
    $('#dlg').dialog('open').dialog('setTitle','添加数据发布');
    $('#fm').form('clear');
    url = "DataRelease!addDataRelease.action";
}

function del(ID){
	if (ID) {
		if (confirm("确定要删除该数据版本吗？")) {
			jQuery.ajax( {
				url : _basePath
						+ "/fusionChart/DataRelease!delDataRelByID.action",
				data : "ID=" + ID,
				dataType : "json",
				success : function(res) {
					if (res.flag == true) {
//						$.messager.alert("提示", res.msg);
						$('#pageTable').datagrid('reload');
					} else {
						$.messager.alert("提示", res.msg);
					}
				}

			});
		}
	}
}


function save() {
	jQuery.ajax( {
		url : url,
		data : $("#fm").serialize(),
		dataType : "json",
		success : function(res) {
			if (res.flag == true) {
				$('#dlg').dialog('close');
				$('#pageTable').datagrid('reload');
			} else {
				$.messager.alert("提示", res.msg);
			}
		}
	});
}

