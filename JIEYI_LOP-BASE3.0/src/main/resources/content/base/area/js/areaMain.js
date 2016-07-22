
$(document).ready(function(){
	$("#fromDate").form('clear');
	$("#pageTable").datagrid({
		url:_basePath+"/base/area/Area!pageData.action",
        pagination:true,// 分页
        rownumbers:true,//行数
        singleSelect:true,//单选模式
		fitColumns:true,
		toolbar:'#pageForm',
        columns:[[
			{field:'ID',align:'center',width:20,title:'区域编号'},
            {field:'NAME',align:'center',width:30,title:'区域名称'},
            {field:'LESSEE_AREA',align:'center',width:20,title:'区号'},
			{field:'AREAID',align:'center',width:30,title: '操作',formatter:function(value,rowData,rowIndex){
                    //return "<a href='javascript:void(0);' onclick=dialogMod('"+rowData.ID+"','"+rowDate.NAME+"','"+rowDate.LESSEE_AREA+"') >修改</a>&nbsp;&nbsp;&nbsp;"; 
                    
            	return "<a href='javascript:void(0);' onclick=\"dialogMod('"+rowData.ID+"','"+rowData.NAME+"','"+rowData.LESSEE_AREA+"')\") >修改</a>&nbsp;&nbsp;&nbsp;"; 
						  
            	}
			}
        ]]
    });
});


//修改省份
function dialogMod(ID,NAME,LESSEE_AREA){
	$("#ID").val(ID);
	$("#AreaName").val(NAME);
	$("#LESSEE_AREA").val(LESSEE_AREA);
	
	$("#dialog").dialog({
		//modal: true,
		autoOpen: false,
		height:220,
		width:350,
		title:'修改区域'
	 });
	$("#dialog").dialog("open");
}	

//添加省份
function dialogAdd(){
	$("#dialog").dialog({
		//modal: true,
		autoOpen: false,
		height:220,
		width:350,
		title:'添加区域'
	 });
	$("#ID").val('');
	$("#AreaName").val('');
	$("#LESSEE_AREA").val('');			 
	$("#dialog").dialog("open");
}


$(document).ready(function(){
       //保存添加区域信息
       $("#saveSheng").click(function(){
               var NAME=$("input[name='AreaName']").val();
               var LESSEE_AREA=$("input[name='LESSEE_AREA']").val();
               var ID=$("input[name='ID']").val();
               if(NAME=='' || LESSEE_AREA=='')
               {
                	alert('请填写必填项!');
                	return; 
               }
               jQuery.ajax({
                	url:  "Area!doaddProvince.action",
                	data: "NAME="+NAME+"&LESSEE_AREA="+LESSEE_AREA+"&ID="+ID,
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