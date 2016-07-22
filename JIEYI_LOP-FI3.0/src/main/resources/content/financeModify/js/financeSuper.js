$(function(){
	$("#table1").datagrid({
		url : 'FinanceModify!goSuperPage.action',
		columns : [[
			{field:'SUPERID',align:'center',width:30,title: '操作',formatter:function(value,rowData,rowIndex){
			    
			    
				return "<a href='javascript:void(0);' onclick=\"dialogMod('"+rowData.ID+"','"+rowData.NAME+"','"+rowData.PROVE_ID+"','"+rowData.PROVE_ID_SPLIT+"')\") >修改</a>&nbsp;&nbsp;&nbsp;"; 
						  
				}
			},
			{field:'ID',checkbox:true,width:100,align:'center'},
			{field:'NAME',title:'供应商名称',width:100,align:'center'},
			{field:'PROVE_ID',title:'财务接口编号',width:100,align:'center'},
			
			{field:'PROVE_ID_SPLIT',title:'财务接口编号(分期)',width:100,align:'center'}
			
			
		]],
		toolbar : '#toolbar',//工具条
		pagination : true ,//分页
		idField : 'ID',
		fitColumns : true,
		autoRowHeight : true,
		//singleSelect : true,
		checkOnSelect : true,
		nowrap : true,
		rownumbers : true,
		
		pageSize : 20,
		pageList : [10,20,50,100,200,300]
	});
	
	$('#form01').form({
        url:""
    });
   
});
//查询
function se(){
	var searchParams = getFromData('#toolbar');
	$('#table1').datagrid('load',{"searchParams":searchParams});
}

function dialogMod(ID,NAME,PROVE_ID,PROVE_ID_SPLIT){
	if(PROVE_ID=='undefined')
		PROVE_ID="";
	if(PROVE_ID_SPLIT=='undefined')
		PROVE_ID_SPLIT="";
	$("#ID").val(ID);
	$("#SuperName").val(NAME);
	$("#provId").val(PROVE_ID);
	$("#provIdSplit").val(PROVE_ID_SPLIT);
	$("#dialog").dialog({
		//modal: true,
		autoOpen: false,
		height:220,
		width:350,
		title:'修改区域'
	 });
	$("#dialog").dialog("open");
}	


$(document).ready(function(){
    //保存添加区域信息
    $("#save").click(function(){
    	var super_id=$("#ID").val();
    	
    	var prov_id=$("#provId").val();
    	var prov_split_id=$("#provIdSplit").val();
            if(super_id=='' || super_id=='undefined')
            {
             	alert('请联系管理员!');
             	return; 
            }
            if(prov_id=='' && prov_split_id=='')
            {
             	alert('请不要提交空表单 o(╯□╰)o!');
             	return; 
            } 
            jQuery.ajax({
             	url:  "FinanceModify!modifySuperPage.action",
             	data: "ID="+super_id+"&PROV_ID="+prov_id+"&PROV_SPLIT_ID="+prov_split_id,
             	type: "post",
     			dataType:"json",
             	success: function (json){
     			//alert(json.data);
             	    if( json.data>0)
             	    {
             			alert("保存成功");
             			$("#dialog").dialog("close");
             			window.location.reload();
             	    }else{
             			alert("保存失败");
             	    }
                }				
            });
            
    });
     
     			
     			
});  

function closeDialog(name){
 $('#'+name).dialog('close');
}   







//导出
function exportExcel(flag){
	
	//data
	var datagridList=$('#table1').datagrid('getChecked');
	var sqlData = [];	
	
	for(var i = 0; i < datagridList.length; i++)
	{
		sqlData.push("'"+datagridList[i].ID+"'");
	}
	
	//params
	var searchParams = getFromData('#toolbar');
	
	//url
	var url = "FinanceModify!explorExcleSuper.action";
	if(flag == 'all')
	{
		url += "?exportAll=true";
	}else
	{
		url += "?exportAll=false";
		if(datagridList.length == 0){
			alert("请选择要导出的数据");
			return;
		}
	}
	//submit
	$('#form01').form('submit',{
        url:url,
        onSubmit: function(){
			//查询参数
			if($('#searchParams').length<=0){
				$('#form01').append('<input name=\"searchParams\" id=\"searchParams\" type=\"hidden\" />');
			}
			$('#searchParams').val(searchParams);
			//导出标识
			if($('#sqlData').length<=0){
				$('#form01').append('<input name=\"sqlData\"  id=\"sqlData\" type=\"hidden\" />');
			}
			$('#sqlData').val(sqlData.join(','));
        }
    });
	//remove
	$('#sqlData').remove();
	$('#searchParams').remove();
}
//清空
function clearQuery(form01){
	$('#form01').form('clear');
	
}

