
function toMgEdossier(){
	var row = $('#customersTab').datagrid('getSelected');
	if (row){
		top.addTab("电子档案",_basePath+"/crm/Customer!toMgEdossier.action");
	}else{
		$.messager.alert("提示","请选择一个客户!");
	}
}

function toMgCallLog(){
	var row = $('#customersTab').datagrid('getSelected');
	if (row){
		top.addTab(row.NAME+"沟通记录",_basePath+"/crm/Customer!toMgCallLog.action?CLIENT_ID="+row.CLIENT_ID);
	}else{
		$.messager.alert("提示","请选择一个客户!");
	}
}
function toMgCredit(){
	var row = $('#customersTab').datagrid('getSelected');
	if (row){
		top.addTab("信用档案",_basePath+"/crm/CreditDossier.action?CLIENT_ID="+row.CLIENT_ID);
	}else{
		$.messager.alert("提示","请选择一个客户!");
	}
}

function goAssetsMg(id){
	var row = $('#customersTab').datagrid('getSelected');
	if(row){
		top.addTab("资产档案",_basePath+"/crm/AssetsDossier.action?CLIENT_ID="+row.CLIENT_ID);
	}else{
		$.messager.alert("提示","请选择一个客户!");
	}
}


function toProjectCreatd(val1, val2, val3,val4){
//	var row = $('#customersTab').datagrid('getSelected');
//	if (row){
//		 var CUSTOMER_ID=row.CLIENT_ID;
//		 var CUSTOMER_TYPE=row.TYPE_1;
//		 var CUSTOMER_NAME=row.NAME;
		 top.addTab("项目立项",_basePath+"/project/project!lodingDiv.action?CUSTOMER_ID="+val1+"&CUSTOMER_TYPE="+val2+"&CUSTOMER_NAME="+val3+"&SUPP_ID="+val4);
//		 top.addTab("项目立项",_basePath+"/project/project!StartProject.action?CUSTOMER_ID="+val1+"&CUSTOMER_TYPE="+val2+"&CUSTOMER_NAME="+val3+"&SUPP_ID="+val4);
//	}else{
//		$.messager.alert("提示","请选择一个客户!");
//	}
}

function toProjectAll(){
	var row = $('#customersTab').datagrid('getSelected');
	if (row){
		 var CUSTOMER_NAME=row.NAME;
		 top.closeTab("项目一览");
		 top.addTab("项目一览",_basePath+"/project/project!getDataList.action?CUST_NAME="+CUSTOMER_NAME);
	}else{
		$.messager.alert("提示","请选择一个客户!");
	}
}


//电子档案
function goElectronicPhotoAlbumMg(){
	var row = $('#customersTab').datagrid('getSelected');
	if (row){
		var CUSTOMER_ID=row.CLIENT_CODE;//客户编号
		var CUSTOMER_NAME=row.NAME;//客户名称
		var CUSTOMER_TYPE=row.TYPE;//客户类型
		top.addTab("电子档案",_basePath+"/crm/Customer!toMgElectronicPhotoAlbum.action?RENTER_CODE="+CUSTOMER_ID+"&RENTER_NAME="+CUSTOMER_NAME + "&CUSTOMER_TYPE=" + CUSTOMER_TYPE);
	}else{
		$.messager.alert("提示","请选择一个客户!");
	}
}
