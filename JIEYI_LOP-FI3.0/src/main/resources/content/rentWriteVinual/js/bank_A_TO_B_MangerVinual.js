function dd(){
$("#bank_A_TO_B_PageTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		fit:true,
		pageSize:50,
		toolbar:'#pageForm',
		url:'rentWriteVinual!query_Bank_A_TO_B_Page.action',
		columns:[[
		          	{field:'ID',checkbox:true,width:100},
		          	{field:'LOCKNAME',title:'锁状态',align:'center',width:50},
		          	{field:'BEGINNING_STATUS',align:'center',title:'申请状态',width:70,formatter:function(value,rowData,rowIndex){
		        	  	if(value=='0')
		          		{
		          			return "未申请";
		          		}
		        	  	else if(value=='1')
		          		{
		          			return "已申请";
		          		}
	                  }},
		          	{field:'LEASE_CODE',title:'融资租赁合同号',width:100,align:'center'},
	                {field:'CUSTNAME',title:'客户名称',width:150,align:'center'},
		          	{field:'COMPANY_NAME',title:'厂商',width:150,align:'center'},
		          	{field:'SUPPLIERS_NAME',title:'经销商',width:150,align:'center'},
		          	{field:'PRODUCT_NAME',title:'品牌',width:150,align:'center'},
		          	{field:'PAYLIST_CODE',title:'支付表号',width:150,align:'center'},
		          	{field:'BEGINNING_NAME',title:'款项名称',width:50,align:'center'},
		          	{field:'BEGINNING_NUM',title:'期次',width:50,align:'center'},
		          	{field:'BEGINNING_RECEIVE_DATA',title:'计划收取日期',width:100,align:'center'},
		          	{field:'BEGINNING_MONEY',title:'应收金额',width:100,align:'center'},
		          	{field:'BEGINNING_PAID',title:'实收金额',width:100,align:'center'},
		          	{field:'VINUAL_MONEY',title:'虚拟收入金额',width:100,align:'center'},
		          	{field:'ITEM_FLAG',hidden:true},
		          	{field:'CUST_ID',hidden:true},
		          	{field:'SUP_ID',hidden:true},
		          	{field:'MONEY_FLAG',hidden:true}
		         ]]
	});

}


//非网银-创建结算单
function ISA_TO_B()
{
	var datagridList=$('#bank_A_TO_B_PageTable').datagrid('getChecked');
	if(datagridList.length<=0)
	{
		alert("请先选择要数据在继续申请操作！");
		return false;
	}
	
	var IDS="";
	var IDS1="";
	var IDS2="";
	var IDS3="";
	var IDS4="";
	var IDS5="";
	var IDS6="";
	var hh=0;
	var aa=0;
	for(var i = 0; i < datagridList.length; i++){
		hh++;
		if(hh==1){
			if(aa==0){
				IDS=datagridList[i].ID;
			}
			else if(aa==1){
				IDS1=datagridList[i].ID;
			}
			else if(aa==2){
				IDS2=datagridList[i].ID;
			}
			else if(aa==3){
				IDS3=datagridList[i].ID;
			}
			else if(aa==4){
				IDS4=datagridList[i].ID;
			}
			else if(aa==5){
				IDS5=datagridList[i].ID;
			}
			else if(aa==6){
				IDS6=datagridList[i].ID;
			}
		}
		else{
			if(aa==0){
				IDS=IDS+","+datagridList[i].ID;
			}
			else if(aa==1){
				IDS1=IDS1+","+datagridList[i].ID;
			}
			else if(aa==2){
				IDS2=IDS2+","+datagridList[i].ID;
			}
			else if(aa==3){
				IDS3=IDS3+","+datagridList[i].ID;
			}
			else if(aa==4){
				IDS4=IDS4+","+datagridList[i].ID;
			}
			else if(aa==5){
				IDS5=IDS5+","+datagridList[i].ID;
			}
			else if(aa==6){
				IDS6=IDS6+","+datagridList[i].ID;
			}
		}
		if(hh==300){
			aa++;
			hh=0;
		}
	}
	
	$.ajax({
		url:_basePath+"/rentWrite/rentWriteNew!LockTypeIsF.action?IDS="+IDS,
	    type:'post',
	    dataType:'json',
	    success:function(json){
		    if(json.data == '2'){
		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
		    }
		    else if(json.data == '3'){
		    	return $.messager.alert("锁定提示","您所选择的数据有已申请的数据,请刷新页面重新选择！");
		    }
		    else{
		    	if(IDS1.length>0){
		    		$.ajax({
			    		url:_basePath+"/rentWrite/rentWriteNew!LockTypeIsF.action?IDS="+IDS1,
			    	    type:'post',
			    	    dataType:'json',
			    	    success:function(json){
			    		    if(json.data == '2'){
			    		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
			    		    }
			    		    else if(json.data == '3'){
			    		    	return $.messager.alert("锁定提示","您所选择的数据有已申请的数据,请刷新页面重新选择！");
			    		    }
			    		    else{
			    		    	if(IDS2.length>0){
			    		    		$.ajax({
			    			    		url:_basePath+"/rentWrite/rentWriteNew!LockTypeIsF.action?IDS="+IDS2,
			    			    	    type:'post',
			    			    	    dataType:'json',
			    			    	    success:function(json){
			    			    		    if(json.data == '2'){
			    			    		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
			    			    		    }
			    			    		    else if(json.data == '3'){
			    			    		    	return $.messager.alert("锁定提示","您所选择的数据有已申请的数据,请刷新页面重新选择！");
			    			    		    }
			    			    		    else{
			    			    		    	if(IDS3.length>0){
			    			    		    		$.ajax({
			    			    			    		url:_basePath+"/rentWrite/rentWriteNew!LockTypeIsF.action?IDS="+IDS3,
			    			    			    	    type:'post',
			    			    			    	    dataType:'json',
			    			    			    	    success:function(json){
			    			    			    		    if(json.data == '2'){
			    			    			    		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
			    			    			    		    }
			    			    			    		    else if(json.data == '3'){
			    			    			    		    	return $.messager.alert("锁定提示","您所选择的数据有已申请的数据,请刷新页面重新选择！");
			    			    			    		    }
			    			    			    		    else{
			    			    			    		    	if(IDS4.length>0){
			    			    			    		    		$.ajax({
			    			    			    			    		url:_basePath+"/rentWrite/rentWriteNew!LockTypeIsF.action?IDS="+IDS4,
			    			    			    			    	    type:'post',
			    			    			    			    	    dataType:'json',
			    			    			    			    	    success:function(json){
			    			    			    			    		    if(json.data == '2'){
			    			    			    			    		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
			    			    			    			    		    }
			    			    			    			    		    else if(json.data == '3'){
			    			    			    			    		    	return $.messager.alert("锁定提示","您所选择的数据有已申请的数据,请刷新页面重新选择！");
			    			    			    			    		    }
			    			    			    			    		    else{
			    			    			    			    		    	if(IDS5.length>0){
			    			    			    			    		    		$.ajax({
			    			    			    			    			    		url:_basePath+"/rentWrite/rentWriteNew!LockTypeIsF.action?IDS="+IDS5,
			    			    			    			    			    	    type:'post',
			    			    			    			    			    	    dataType:'json',
			    			    			    			    			    	    success:function(json){
			    			    			    			    			    		    if(json.data == '2'){
			    			    			    			    			    		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
			    			    			    			    			    		    }
			    			    			    			    			    		    else if(json.data == '3'){
			    			    			    			    			    		    	return $.messager.alert("锁定提示","您所选择的数据有已申请的数据,请刷新页面重新选择！");
			    			    			    			    			    		    }
			    			    			    			    			    		    else{
			    			    			    			    			    		    	if(IDS6.length>0){
			    			    			    			    			    		    		$.ajax({
			    			    			    			    			    			    		url:_basePath+"/rentWrite/rentWriteNew!LockTypeIsF.action?IDS="+IDS6,
			    			    			    			    			    			    	    type:'post',
			    			    			    			    			    			    	    dataType:'json',
			    			    			    			    			    			    	    success:function(json){
			    			    			    			    			    			    		    if(json.data == '2'){
			    			    			    			    			    			    		    	return $.messager.alert("锁定提示","数据已锁定，请等待解锁后在操作！");
			    			    			    			    			    			    		    }
			    			    			    			    			    			    		    else if(json.data == '3'){
			    			    			    			    			    			    		    	return $.messager.alert("锁定提示","您所选择的数据有已申请的数据,请刷新页面重新选择！");
			    			    			    			    			    			    		    }
			    			    			    			    			    			    		    else{
			    			    			    			    			    			    		    	method();
			    			    			    			    			    			    		    }
			    			    			    			    			    			    	    }
			    			    			    			    			    			    	});
			    			    			    			    			    		    	}
			    			    			    			    			    		    	else{
			    			    			    			    			    		    		method();
			    			    			    			    			    		    	}
			    			    			    			    			    		    	
			    			    			    			    			    		    }
			    			    			    			    			    	    }
			    			    			    			    			    	});
			    			    			    			    		    	}
			    			    			    			    		    	else{
			    			    			    			    		    		method();
			    			    			    			    		    	}
			    			    			    			    		    	
			    			    			    			    		    }
			    			    			    			    	    }
			    			    			    			    	});
			    			    			    		    	}
			    			    			    		    	else{
			    			    			    		    		method();
			    			    			    		    	}
			    			    			    		    	
			    			    			    		    }
			    			    			    	    }
			    			    			    	});
			    			    		    	}
			    			    		    	else{
			    			    		    		method();
			    			    		    	}
			    			    		    	
			    			    		    }
			    			    	    }
			    			    	});
			    		    	}
			    		    	else{
			    		    		method();
			    		    	}
			    		    	
			    		    }
			    	    }
			    	});
		    	}
		    	else{
		    		method();
		    	}
		    	
		    	
		    }
	    }
	})
	
}

function method(){
	
	$("#divFrom").empty();
	var datagridList=$('#bank_A_TO_B_PageTable').datagrid('getChecked');
	if(datagridList.length<=0)
	{
		alert("请先选择要数据在继续操作！");
		return false;
	}
	
	$.messager.confirm("提示","您确定对选中的数据进行虚拟转实收？",function(flag){
		if(flag){
			var selectData = [];
			for(var i = 0; i < datagridList.length; i++)
			{
				var temp={};
				temp.PAYLIST_CODE=datagridList[i].PAYLIST_CODE;
				temp.BEGINNING_NUM=datagridList[i].BEGINNING_NUM;
				temp.ITEM_FLAG=datagridList[i].ITEM_FLAG;
				selectData.push(temp);
			}
			var dataJson ={selectData:selectData};
			var url = _basePath+"/rentWrite/rentWriteVinual!fund_A_TO_B_Submit.action?selectDateHidden="+JSON.stringify(dataJson);
			$("#divFrom").append("<form id='formRoll' method='post' action='"+url+"'></form>");
			$("#formRoll").submit();
		}
	});
	
}