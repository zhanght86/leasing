var url="";
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
 				var temp = "<br/><a href='javascript:void(0)' style='color:blue;' class='easyui-linkbutton' onclick='downFile(" + json[i].ID + ")'>" + tempVal.substring(13, tempVal.length) + "</a>";//此处文件名进行了优化
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
