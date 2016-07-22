//$(function(){
//		   $('#leftmenu').tree('collapse', node.target);
//		    $('#leftmenu').tree('collapseAll', node.target);
//			$('#leftmenu').tree({
//				animate:true,
//				collapsabled:false
////				}
//			});
//		});

function addTab(name,url){
	if($('#tabBox').tabs('exists',name)){
	}else{
	$('#tabBox').tabs('add',{
		title: name,
		content: '<iframe src="'+url+'" width="99%" height="99%" frameborder="0" border="0"></iframe>',
		closable: true
	});
	}
}
$(function(){
	$('#layout').layout('collapse','west');
});
function removeTab(){
	var tab = $('#tabBox').tabs('getSelected');
	if (tab){
		var index = $('#tabBox').tabs('getTabIndex', tab);
		$('#tt').tabs('close', index);
	}
}


//�����Ȼ�߶�
function fixWidth(percent)  
   {  
    return (document.body.clientWidth-40) * percent ; //����������Լ������  
   }

function fixHeight(percent)  
{  
    return (document.body.clientHeight-40) * percent ; //����������Լ������  
}


$(function(){
            var lastIndex;
            $('#tt').datagrid({
                toolbar:[{
                    text:'append',
                    iconCls:'icon-add',
                    handler:function(){
                        $('#tt').datagrid('endEdit', lastIndex);
                        $('#tt').datagrid('appendRow',{
                            itemid:'',
                            productid:'',
                            listprice:'',
                            unitprice:'',
                            attr1:'',
                            status:'P'
                        });
                        lastIndex = $('#tt').datagrid('getRows').length-1;
                        $('#tt').datagrid('selectRow', lastIndex);
                        $('#tt').datagrid('beginEdit', lastIndex);
                    }
                },'-',{
                    text:'delete',
                    iconCls:'icon-remove',
                    handler:function(){
                        var row = $('#tt').datagrid('getSelected');
                        if (row){
                            var index = $('#tt').datagrid('getRowIndex', row);
                            $('#tt').datagrid('deleteRow', index);
                        }
                    }
                },'-',{
                    text:'GetChanges',
                    iconCls:'icon-search',
                    handler:function(){
                        var rows = $('#tt').datagrid('getChanges');
                        alert('changed rows: ' + rows.length + ' lines');
                    }
                }],
                onBeforeLoad:function(){
                    $(this).datagrid('rejectChanges');
                },
                onClickRow:function(rowIndex){
                    if (lastIndex != rowIndex){
                        $('#tt').datagrid('endEdit', lastIndex);
                        $('#tt').datagrid('beginEdit', rowIndex);
                    }
                    lastIndex = rowIndex;
                }
            });
        });