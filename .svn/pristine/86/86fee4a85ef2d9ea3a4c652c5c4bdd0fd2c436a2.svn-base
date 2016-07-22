/**
 * 编码管理js
 * @author 徐广明
 */
var url = "";

//查询
function se(){
	var TYPE = $("#pageForm input[name='TYPE']").val();
    $('#pageTable').datagrid('load', {"TYPE":TYPE});
}
function emptyData(){
	//清空input
	$(".paramData").each(function(){
		$(this).val("");
	});
}
//添加
function newOne(){
    $('#dlg').dialog('open').dialog('setTitle','添加编码');
    $('#fm').form('clear');
    url = "";
    url = "Code!addCode.action";
}
//save按钮功能
function save(){

	$('#name').val( $('#TYPE').val()+'_'+$('#ID').val()) ;
	$('#className').val("com.pqsoft.quartzjobs.jobs.SysCodeSetJob");
	$('#info').val("dfdf");
	if($('#RESET_TYPE').val() == 1){
		$('#cron_expression').val("0 0 0 * * ? *");
	}else if($('#RESET_TYPE').val() == 2){
		$('#cron_expression').val("0 0 0 1 * ? *");
	}else{
		$('#cron_expression').val("0 0 0 1 1 ? *");
		//$('#cron_expression').val("0 * * * * ? *");
	}
	
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

function setOperation(val,row,index) {
	//return "<a href='#'  onclick='modifyInsureType(" + index + ")'>修改</a>|<a href='#'  onclick='deleteInsureType(" + val + ")'>删除</a>";
	return "<a href='#'  onclick='modifyInsureType(" + index + ")'>修改</a>|<a href='#'  onclick='deleteInsureType(" + val + ")'>删除</a>";
}
function deleteInsureType(ID)
{
	$.messager.confirm("删除","确定要删除此编码吗？",function(r){
		if(r){
			jQuery.ajax({
				url : _basePath + "/Code/Code!doDeleteCode.action",
				data : { "ID": ID},
				dataType:'json',
				success:function(data){
					$.messager.alert("结果","删除成功！");
					//刷新页面
					$('#pageTable').datagrid('reload');
				}
			});
		}
	});
}
function modifyInsureType(index){
	$('#name').val( $('#TYPE').val()+'_'+$('#ID').val()) ;
	$('#className').val("com.pqsoft.quartzjobs.jobs.SysCodeSetJob");
	$('#info').val("dfdf");
	if($('#RESET_TYPE').val() == 1){
		$('#cron_expression').val("0 0 0 * * ? *");
	}else if($('#RESET_TYPE').val() == 2){
		$('#cron_expression').val("0 0 0 1 * ? *");
	}else{
		$('#cron_expression').val("0 0 0 1 1 ? *");
		//$('#cron_expression').val("0 * * * * ? *");
		 
	}
	$('#pageTable').datagrid('selectRow',index);//根据index选中row
	var row = $('#pageTable').datagrid('getSelected');
	if(row){
		$('#dlg').dialog('open').dialog('setTitle','修改编码信息');
		$('#fm').form('load',row);
		url = "";
		url = "Code!doUpdateCode.action";
	}
}