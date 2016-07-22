/**
 * 险种管理js
 * @author 韩晓龙
 */
var url = "";

/**
 * 清空按钮
 */
function emptyData(){
	//清空input
	$(".paramData").each(function(){
		$(this).val("");
	});
}

function dosearch(){
	var INSURE_NAME = $("#INSURE_NAME").val();
	var INSURE_TYPE = $("#INSURE_TYPE").val();
	var EQUIP_TYPE = $("#EQUIP_TYPE").val();
	$('#pageTable').datagrid('load', {"INSURE_NAME":INSURE_NAME,"INSURE_TYPE":INSURE_TYPE,"EQUIP_TYPE":EQUIP_TYPE});
}

function setOperation(val,row,index) {
	return "<a href='#'  onclick='showInsureType(" + index + ")'>查看</a>|<a href='#'  onclick='modifyInsureType(" + index + ")'>修改</a>|<a href='#'  onclick='deleteInsureType(" + val + ")'>删除</a>";
}

//增加
function newOne(){
    $('#dlg').dialog('open').dialog('setTitle','添加险种');
    $('#fm').form('clear');
    url = "";
    url = "InsureTypeManagement!doAddInsureType.action";
}

//修改
function modifyInsureType(index){
	$('#pageTable').datagrid('selectRow',index);//根据index选中row
	var row = $('#pageTable').datagrid('getSelected');
	if(row){
		$('#dlg').dialog('open').dialog('setTitle','修改险种信息');
		$('#fm').form('load',row);
		url = "";
		url = "InsureTypeManagement!doUpdateInsureType.action";
	}
}

//查看
function showInsureType(index){
	//样式控制
	$('div').remove("#dlg-buttons");
	$("#i1").attr({ readonly : "readonly"});
	$("#i2").attr({ readonly : "readonly"});
	$("#s1").attr({disabled : "disabled"});
	$("#s2").attr({disabled : "disabled"});
	$("#REMARK").attr({ readonly : "readonly"});
	//数据控制
	$('#pageTable').datagrid('selectRow',index);//根据index选中row
	var row = $('#pageTable').datagrid('getSelected');
	if(row){
		$('#dlg').dialog('open').dialog('setTitle','查看');
		$('#fm').form('load',row);
	}
}

//删除
function deleteInsureType(ID){
	$.messager.confirm("删除","确定要删除此险种吗？",function(r){
		if(r){
			jQuery.ajax({
				url : _basePath + "/insure/InsureTypeManagement!doDeleteInsureType.action",
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