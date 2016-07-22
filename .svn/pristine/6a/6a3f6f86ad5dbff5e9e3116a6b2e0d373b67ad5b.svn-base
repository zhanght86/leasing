var url="";

function update(row){
	//var row = $('#pageTable').datagrid('getSelected');
    if (row){
        $('#dlg').dialog('open').dialog('setTitle','修改');
        $('#fm').form('load',row);
        $(".eqProv").each(function (){
        	if($(this).is("select")){
        		$(this).empty();
        		$(this).append($("<option>").val(row.SFIDDATA).text(row.SFNAME));
        	}
        });
        $(".cityMess").each(function (){
        	if($(this).is("select")){
        		$(this).empty();
        		$(this).append($("<option>").val(row.CSDATA).text(row.CSNAME));
        	}
        });
        url ="AssureCompany!saveCompany.action";
    }else{
    	alert("请选择要编辑的记录！");
    }
}

function newOne(){
    $('#dlg').dialog('open').dialog('setTitle','添加');
    $('#fm').form('clear');
    url = "AssureCompany!addCompany.action";
}

function del(value){
	//var row = $('#pageTable').datagrid('getSelected');
	url = "AssureCompany!delCompany.action";
	if(confirm("您确定要删除这条记录吗？")){		
		$.ajax({ 
			url: url, 
			data: "ID="+value,
			dataType:"json",
			success: function(result){
			if (result.flag==false){
                $.messager.show({
                    title: '出错',
                    msg: result.msg
                });
            }else{
				alert("记录已删除！");
				$('#pageTable').datagrid('reload');
            }
	    }});
	}
}

function save(){
	jQuery.ajax({
		url: url,
		data:$("#fm").serialize(),
		dataType:"json",
		success: function(res){
			 if (res.flag==false){
	             $.messager.show({
	                 title: '出错',
	                 msg: res.msg
	             });
	         } else {
	             $('#dlg').dialog('close');        
	             $('#pageTable').datagrid('reload');    
	         }
        }
 });
}

