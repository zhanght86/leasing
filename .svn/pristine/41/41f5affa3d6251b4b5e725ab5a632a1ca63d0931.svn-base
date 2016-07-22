var url="";

$.ajaxSetup ({
   			 cache: false //关闭AJAX相应的缓存
});
		
//修改利率配置
function update(index){
    $('#pageTable').datagrid('selectRow',index);//根据index选中row
    var row = $('#pageTable').datagrid('getSelected');
    if (row){
        $('#dlg').dialog('open').dialog('setTitle','修改信息');
        $('#fm').form('load',row);
        url ="InterestRateConfig!doUpdateInterestRate.action";
    }
}
//增加一条利率配置
function newOne(){
    $('#dlg').dialog('open').dialog('setTitle','添加');
    $('#fm').form('reset');
	$("#dateBoxId").datebox("setValue", "");  
    url = "InterestRateConfig!doAddInterestRate.action";
}
//删除一条利率配置
function del(index){
	$('#pageTable').datagrid('selectRow',index);//根据index选中row
    var row = $('#pageTable').datagrid('getSelected');
    if (row){
        //$('#dlg').dialog('open').dialog('setTitle','编辑信息');
        $('#fm').form('load',row);
        if(!confirm("是否确认删除?")){
        	return false;
        }
        url ="InterestRateConfig!doDeleteInterestRate.action";
        $('#pageTable').datagrid('reload');
        
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

