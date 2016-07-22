$(document).ready(function(){
	getDataList();
});
/**初始化页面**/
function getDataList(){
	//alert('blackCustManage.vm');
	$("#pageTable").datagrid({
		url:_basePath+"/blackcust/BlackCustManage!getBlackCustManageData.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:true,
		fit : true,
		pageSize:20,
		toolbar:'#pageForm',
		frozenColumns:[[
					{field:'ID',title:'操作',width:180,align:'center',formatter:function(value,rowData,rowIndex){
						var doWork="";
							doWork = doWork+"<a href='javascript:void(0)' onclick='queryBlackCustById("+value+")'>查看</a>";
							doWork = doWork+" | <a href='javascript:void(0)' onclick='updateBlackCust("+value+")'>修改</a>";
							doWork = doWork+" | <a href='javascript:void(0)' onclick='getOutBlackCust("+value+")'>移除</a>";
						return doWork;
					}}
					]], 
		columns:[[
		        
	      	{field:'CUST_NAME',title:'姓名',width:180,align:'center',formatter:function(value,rowData,rowIndex){
	      		//addby muqi 2016年4月6日18:37:48 原因预览中有客户名称，则同步姓名到姓名列中。 start
	      		if(null!=value){
	      			return rowData.CUST_NAME;
	      		}else if(rowData.CASE_RECORDS!=null && rowData.CASE_RECORDS.length<10){
	      				return rowData.CASE_RECORDS;
	      		} 
	      		//addby muqi 2016年4月6日18:37:48 原因预览中有客户名称，则同步姓名到姓名列中。 end
	      	}},
	      	{field:'ID_CARD_NO',title:'身份证号',width:180,align:'center'},
	      	{field:'SETIN_DATE',title:'进入时间',align:'center',width:180},
	      	{field:'GETOUT_DATE',title:'移除时间',width:180,align:'center'},
	      	{field:'CASE_RECORDS',title:'原因预览',width:400,align:'center',formatter:function(value,rowData,rowIndex){
	      		//alert(value.substring(0,"10"));
	      		
	      		var strValue="";
	      		if(null!=value){
	      		   strValue = value.substring(0,"20")+"...";
	      		}
	      		
	      		return strValue;
	      	}}
	     ]]
	});
}

function getOperation(value,rowData,index){
	
	//var srin=JSON.stringify(rowData);
	return  "<a href='javascript:void(0)' onclick='queryContractProjectById("+value+","+index+","+rowData.CLIENT_ID+")'>查看</a>";
	        //+
			//"&nbsp;|&nbsp;" +
			//"<a href='javascript:void(0)' onclick='deleteSql("+index+")'>删除</a>" ;
}
/**
 * 增加标签页
 * @param name
 * @param url
 * @return
 */
function addTab(name,url){
	//alert("addTab");
	//alert(url);
	url=url.replace("undefined","");
	//alert(url);
	if(url.lastIndexOf("?")==-1){
		url=url+"?_datetime="+new Date().getTime();
	}else{
		url=url+"&_datetime="+new Date().getTime();
	}
	if($('#tabBox').tabs('exists',name)){
		//alert(1);
		$('#tabBox').tabs('select',name);
		$('#tabBox').tabs('update',{
			tab:$("#tabBox").tabs("getSelected"),
			options:{
				content: '<iframe src="'+url+'" width="100%" align="center" style="padding:0px;margin-bottom:-5px;" height="100%" frameborder="0" border="0"></iframe>'
			}
		});
	}else{
		//alert(2);
		$('#tabBox').tabs('add',{
			title: name,
			content: '<iframe src="'+url+'" width="100%" height="100%" style="padding:0px;margin-bottom:-5px;" frameborder="0" border="0"></iframe>',
			closable: true,
			tools:[{
		        iconCls:'icon-mini-refresh',
		        handler:function(){
					$('#tabBox').tabs('update',{
						tab:$("#tabBox").tabs("getSelected"),
						options:{
							content: '<iframe src="'+url+'" width="100%" align="center" style="padding:0px;margin-bottom:-5px;" height="100%" frameborder="0" border="0"></iframe>'
						}
					});
		        }
		    }]
		});
	}
}

function clearInput(){
	$("#pageForm input").val("");
}
function validate(){
	if(!checkName()){
		
		return false;
	}
	if(!checkIdCard($("#formData input[name='ID_CARD_NO']").val())){
		return false;
	}
	return true;
	
	
}
/**添加黑名单**/
function addBlackCust(){
	//$("#addBlackCustTable").load(_basePath+"/blackcust/BlackCustManage!toAddBlackCustVm.action");
	$("#addBlackCust").dialog({
	title:"添加",
	buttons: [{
		id:"btnsave",
        text:'保存',
     iconCls:'icon-save',
     handler:function(){
     	if($("#formData").form('validate')&&validate()){
     		$("#btnsave").attr("disabled",false);
     		jQuery.ajax({
     		url: _basePath+"/blackcust/BlackCustManage!addBlackCust.action?_dateTime="+new Date(),
		    data: "param="+getFromData("#formData"),
		    type: "post",
		    dataType: "json",
		    success: function(json){
				   if(json.flag){
				   	$.messager.alert('提示','保存成功！');
				   	location.reload();
				   }else{
				   	$.messager.alert('提示','保存失败，请与管理员联系！');
				   	$("#btnsave").attr("disabled",true);
				   }     	
			  }
     	 });
     	}
     }},{
		id:"btncacel",
        text:'取消',
     iconCls:'icon-cancel',
     handler:function(){
    	 $("#addBlackCust").dialog('close');
	 	}
	}]
  });
	$("#addBlackCust").dialog('open');
}
/**查看单个**/
function queryBlackCustById(id){
	 $("#queryBlackCust").dialog({
		 title:"查看",
		 buttons: [{
	     id:"btnclose",
		 text:'关闭',
		 iconCls:'icon-cancel',
		 handler:function(){
			 $("#queryBlackCust").dialog('close');
			 }
			 }]
		 });
	  $("#queryBlackCustTable").load(_basePath+"/blackcust/BlackCustManage!queryBlackCustById.action?id="+id);
	  $("#queryBlackCust").dialog('open');
}

/**修改黑名单**/
function updateBlackCust(id){
	    jQuery.ajax({
	     		url: _basePath+"/blackcust/BlackCustManage!queryBlackCustById2.action?_dateTime="+new Date(),
			    data: "id="+id,
			    type: "post",
			    dataType: "json",
			    success: function(json){
					   $("#formData input[name='CUST_NAME']").val(json.data.CUST_NAME);
  	                   $("#formData input[name='ID_CARD_NO']").val(json.data.ID_CARD_NO);
  	                   $("#SETIN_DATE").datebox('setValue', json.data.SETIN_DATE);
  	                   $("#GETOUT_DATE").datebox('setValue', json.data.GETOUT_DATE);
  	                   $("#formData textarea[name='CASE_RECORDS']").text(json.data.CASE_RECORDS);
  	                   $("#formData input[name='STATUS']").val(json.data.STATUS);
  	                   $("#formData input[name='ID']").val(json.data.ID);
  	                   $("#addBlackCust").dialog({
							title:"添加",
							buttons: [{
								id:"btnsave",
						        text:'保存',
						     iconCls:'icon-save',
						     handler:function(){
						     	if($("#formData").form('validate')&&validate()){
						     		jQuery.ajax({
						     		url: _basePath+"/blackcust/BlackCustManage!updateBlackCust.action?_dateTime="+new Date(),
								    data: "param="+getFromData("#formData"),
								    type: "post",
								    dataType: "json",
								    success: function(json){
										   if(json.flag){
									   	$.messager.alert('提示','保存成功！');
									   	location.reload();
									   }else{
									   	$.messager.alert('提示','保存失败，请与管理员联系！');
									   }     	
								  }
								     	 });
								     	}
								     }},{
										id:"btnbc",
								        text:'取消',
								     iconCls:'icon-cancel',
								     handler:function(){
								    	 $("#addBlackCust").dialog('close');
									 	}
								}]
							  });
								$("#addBlackCust").dialog('open');
							  }
				     	 });
}
/**移除黑名单**/
function getOutBlackCust(id){
	if(confirm('确定要移除黑名单吗?')){
		jQuery.ajax({
	     		url: _basePath+"/blackcust/BlackCustManage!cancelBlackCust.action?_dateTime="+new Date(),
			    data: "id="+id,
			    type: "post",
			    dataType: "json",
			    success: function(json){
			    	if(json){
			    		$.messager.alert('提示','移除成功！');
			    		location.reload();
			    	}else{
			    		$.messager.alert('提示','移除失败，请与管理员联系！');
			    	}
			    }
	});
	}
	
}


//搜索
function conditionsSelect(){
	$('#pageTable').datagrid('load', {
		CUST_NAME:$("#pageForm input[name='CUST_NAME']").val(),
		ID_CARD_NO:$("#pageForm input[name='ID_CARD_NO']").val()
	});
}

/**身份证**/
function checkIdCard(sId){
	if(!isCardID(sId)){
    	$.messager.alert('提示','身份证输入错误，请重新输入！');
    	 return false;
    };
    return true;

}

/**用户名**/
function checkName(){
//	alert(sId);
	var i=$("#CUST_NAME").val();
    if(i.length==0){
    	$.messager.alert('提示','必须填写姓名！');
    	return false;
    	};
    return true;
}


function isCardID(sId){
    var iSum=0 ;  
    var info="" ;  
    if(!/^\d{17}(\d|x)$/i.test(sId)) return false;   
    sId=sId.replace(/x$/i,"a");   
//    if(aCity[parseInt(sId.substr(0,2))]==null) return "你的身份证地区非法";   
    sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2));   
    var d=new Date(sBirthday.replace(/-/g,"/")) ;  	
    if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))return false;   
    for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;  
    if(iSum%11!=1) return false;   
    return true;//aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"男":"女")   
}   
