var table1;
var editIndex = undefined;
function endEditing(field){
    if (editIndex == undefined){return true;}
    if (table1.datagrid('validateRow', editIndex)){
        table1.datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
        return false;
    }
}

$(function(){
	table1 = $("#table1");
	table1.datagrid({
		url : 'Bank!doSelectPropertiesPageData.action',
		columns : [[
			{field:'ID',hidden:true,width:100},
			{field:'BANK_FLAG',title:'银行标识',width:100},
			{field:'VAL_NAME_CN',title:'参数名',width:100},
			{field:'VAL_VALUE',title:'参数值',width:100,editor:'text'},
			{field:'DESCRIPTION',title:'详细描述',width:100},
		]],
		//toolbar : '#toolbar',//工具条
		pagination : true ,//分页
		idField : 'ID',
		fitColumns : true,
		autoRowHeight : true,
		singleSelect : true,
//		checkOnSelect : true,
		nowrap : true,
		rownumbers : true,
		pageSize : 300,
		pageList : [300],
		onClickCell : function(index,field,value){
			// function onClickRow(index){
            if (editIndex != index){
                if (endEditing(field)){
                	table1.datagrid('beginEdit', index);
                    editIndex = index;
                } else {
                	table1.datagrid('selectRow', editIndex);
                }
            }
	        //}
		},
		onAfterEdit : function(rowIndex, rowData, changes){
			if(changes.VAL_VALUE != undefined){
				$.messager.confirm('确认','Are you sure you want to update this param?',function(flag){
					if(flag){
						$.ajax({
							url : "Bank!doUpdateProperties.action",
							type : "POST",
							dataType:"json",
							data : "id="+ rowData.ID+"&val_value="+changes.VAL_VALUE,
							success : function(msg){
								$.messager.show({
					                title:'操作反馈',
					                msg:msg.msg,
					                showType:'show'
					            });
								table1.datagrid('reload');
							}
						});
					}else{
						table1.datagrid('reload');
					}
				});
			}
		}
	});
	
	
});



/*
$.extend($.fn.datagrid.methods, {
    editCell: function(jq,param){
        return jq.each(function(){
            var opts = $(this).datagrid('options');
            var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor1 = col.editor;
                if (fields[i] != param.field){
                    col.editor = null;
                }
            }
            $(this).datagrid('beginEdit', param.index);
            for(var i=0; i<fields.length; i++){
                var col = $(this).datagrid('getColumnOption', fields[i]);
                col.editor = col.editor1;
            }
        });
    }
});
*/