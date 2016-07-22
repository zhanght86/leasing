var url="";
//////////////////////////////////////搜索功能////////////////////////////////////////////////
function dosearch(){
	var PUBLISHER = $('#PUBLISHER').val();
	var TITLE = $("#TITLE").val();
	var LAST_MODIFY_TIME_BEGIN = $("#LAST_MODIFY_TIME_BEGIN").datebox("getValue");
	var LAST_MODIFY_TIME_END = $("#LAST_MODIFY_TIME_END").datebox("getValue");
	$('#pageTable').datagrid('load', {"PUBLISHER":PUBLISHER,"TITLE":TITLE,"LAST_MODIFY_TIME_BEGIN":LAST_MODIFY_TIME_BEGIN,"LAST_MODIFY_TIME_END":LAST_MODIFY_TIME_END});
}
//////////////////////////////以下为dlg4功能/////////////////////////////////////////////////
//显示下载附件
function showdownload(index,policy_id){
	//先清空  拼上 label
	$("#myLabel").empty().append("<label>附件列表:</label>");
	//使用ajax 查询该政策下的所有的附件	
	var urltemp = _basePath +'/base/policyPublish/PolicyPublish!selectAllFiles.action?ID=' + policy_id;
	//利用ajax	查询当前政策的所有附件
	jQuery.ajax({
		type: "POST",
	    dataType:"json",
	    url: urltemp,
	    //data: jsonData,
	    success: function(json){
			var tempVal = "";
 			for(var i in json){
 				tempVal = json[i].FILE_NAME;
 				var temp = "<br/><a href='javascript:void(0)' class='easyui-linkbutton' onclick='delFile(" + json[i].ID + ")'>删除</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0)' class='easyui-linkbutton' onclick='downFile(" + json[i].ID + ")'>" + tempVal + "</a>";
 				$("#myLabel").append(temp); 
			}
	}});
	//////////////////////////////ajax结束//////////////////////////////////////////
	$('#pageTable').datagrid('selectRow',index);//根据index选中row
    var row = $('#pageTable').datagrid('getSelected');
    if (row){
        $('#dlg4').dialog('open').dialog('setTitle','下载附件');
        $('#fm4').form('load',row);
    }
}
//下载附件
function downFile(fileId){
    url ="PolicyPublish!downloadAttachment.action";
	url += "?ID=" + fileId;
	$('#fm4').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            var result = eval('('+result+')');
            if (result.flag==false){
                $.messager.show({
                    title: '出错',
                    msg: result.msg
                });
            } else {
                $('#pageTable').datagrid('reload');    // reload the user data
            }
        }
    });
}
//删除附件
function delFile(fileId){
    url ="PolicyPublish!deleteOneFile.action";
	url += "?ID=" + fileId;
	$('#fm4').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            var result = eval('('+result+')');
            if (result.flag==false){
                $.messager.show({
                    title: '出错',
                    msg: result.msg
                });
            } else {
            	$.messager.show({
                    title: '结果',
                    msg: result.msg
                });
            	$('#dlg4').dialog('close');
                $('#pageTable').datagrid('reload');    // reload the user data
            }
        }
    });
	//样式
}
//////////////////////////////以下为dlg3功能/////////////////////////////////////////////////
//显示上传附件窗口
function showUpload(index){
	$('#pageTable').datagrid('selectRow',index);//根据index选中row
	document.getElementById("upload_file").value = "";//先清空
    var row = $('#pageTable').datagrid('getSelected');
    if (row){
        $('#dlg3').dialog('open').dialog('setTitle','上传附件');
        $('#fm3').form('load',row);
        url = "PolicyPublish!uploadAttachment.action?ID=";
        //将ID拼进去
        url += document.getElementById("policy_id").value;
    }
}
//上传附件
function upload(index){
	$('#fm3').form('submit',{
        url: url,
        onSubmit: function(){
            //return $(this).form('validate');
			if(document.getElementById("upload_file").value == ""){
				alert("没有选择附件哟！");
				return false;
			}
        },
        success: function(result){
            var result = eval('('+result+')');
            if (result.flag==false){
                $.messager.show({
                    title: '出错',
                    msg: result.msg
                });
            } else {
                $('#dlg3').dialog('close');        // close the dialog
                $('#pageTable').datagrid('reload');    // reload the user data
            }
        }
    });
}
//选择政策的可视用户
function chooseUser(index,policy_id){
	top.addTab("政策发布可视化用户选择", _basePath +'/base/policyPublish/PolicyPublish!toChooserPage.action?POLICY_ID=' + policy_id);
}
//修改政策
function update(index){
    $('#pageTable').datagrid('selectRow',index);//根据index选中row
    var row = $('#pageTable').datagrid('getSelected');
    if (row){
        $('#dlg').dialog('open').dialog('setTitle','修改信息');
        $('#fm').form('load',row);
        url ="PolicyPublish!doUpdatePolicy.action";
    }
}
//增加一条政策
function newOne(){
    $('#dlg').dialog('open').dialog('setTitle','添加');
    $('#fm').form('clear');
    url = "PolicyPublish!doAddPolicy.action";
}
//删除一条政策
function del(index){
	$('#pageTable').datagrid('selectRow',index);//根据index选中row
    var row = $('#pageTable').datagrid('getSelected');
    if (row){
        if(!confirm("是否确认删除?")){
        	return false;
        }
        url ="PolicyPublish!doDeletePolicy.action?ID=" + row.ID;
        $.ajax({
			type:"post",
			url:url,
			dataType:"json",
    		success: function(flag){
				$.messager.alert('提示','删除成功!','info',null);
				$('#pageTable').datagrid('reload');    //刷新
			}
        });
    }
}
//save按钮功能
function save(){
	
    $('#fm').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            var result = eval('('+result+')');
            if (result.flag==false){
                $.messager.show({
                    title: '出错',
                    msg: result.msg
                });
            } else {
                $('#dlg').dialog('close');        // close the dialog
                $('#pageTable').datagrid('reload');    // reload the user data
            }
        }
    });
}
//////////////////////////////以下为dlg5功能/////////////////////////////////////////////////
