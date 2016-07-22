$(function(){
	var TPM_BUSINESS_PLATE = $(".tab01-nav_active").attr("BStype");
	jQuery.ajax({
	    type: "POST",
	    dataType:"json",
	    url: _basePath+'/pdfTemplate/BusinessSector!getDate.action?TPM_BUSINESS_PLATE='+encodeURI(TPM_BUSINESS_PLATE),
	    //data: jsonData,
	    success: function(json){
		 	var data = {flag:json.flag,total:json.data.length,rows:json.data};
			$("#pageTable").datagrid({
                singleSelect:true,
				rownumbers:true,
                height:'auto',
                fitColumns:true,
                columns:[[
//              			{field:'TPM_CODE',align:'center',width:100,title:'文本编号'},
              			{field:'TPM_TYPE',align:'center',width:100,title:'模版类型'},
                        {field:'NAME',align:'center',width:100,title:'模版名称'},
                        {field:'TPM_CUSTOMER_TYPE',align:'center',width:100,title:'客户类型'},
                        {field:'TPM_BUSINESS_TYPE',align:'center',width:100,title:'业务类型'},// Add By YangJ 2014年5月20日 16:48:42
                        {field:'TPM_VERSION',align:'center',width:100,title:'版本号'},
                        {field:'TPM_UPLOAD_DATE',align:'center',width:100,title:'上传日期'},
                        {field:'TPM_STATUS',align:'center',width:100,title:'使用状态'},
//                        {field:'TPM_SALES_WAY',align:'center',width:100,title:'销售模式'},
//                        {field:'TPM_LEASE_WAY',align:'center',width:100,title:'租赁方式'},
//                        {field:'TPM_APPLY_AGENT',align:'center',width:100,title:'适用代理商'},
//                        {field:'TPM_USE_DEPARTMENT',align:'center',width:100,title:'使用部门'},
//                        {field:'TPM_SIGNATURE',align:'center',width:100,title:'签字方'},
//                        {field:'TPM_SIGNATURE_NAME',align:'center',width:100,title:'签字方名称'},
//                        {field:'TPM_SIGNED_OCCASION',align:'center',width:100,title:'签订场合'},
//                        {field:'TPM_MANUFACTURERS',align:'center',width:100,title:'使用制造商'},
//                        {field:'TPM_BREACH_MONEY',align:'center',width:100,title:'是否涉及违约金'},
//                        {field:'TPM_FUNCTION_DESCRIPTION',align:'center',width:100,title:'功能描述'}

                ]]
            });
			$("#pageTable").datagrid("loadData",data);
   		}
	});
});

function clearMess(){ //Add Buy YangJ 2014年5月6日19:09:13
   	$("#search").form("clear");// Add By YangJ 2014年5月21日 10:18:20
   	
	$("#BUSINESS_TYPE").get(0).selectedIndex=0;
	$("#MUST_SELECT").get(0).selectedIndex=0;
//	alert($("input[name='TPM_UPLOAD_DATE1']").val());
//	$(".combo-text validatebox-text").each(function(){$(this).val("");});//清空上传日期查询条件 Add By YangJ 2014年5月21日 09:59:38
//	alert($("input[name='TPM_UPLOAD_DATE1']").val());
}

// Add By YangJ 2014年5月6日18:39:47
function Query(){
	//alert($("#MUST_SELECT").val()+"--"+$("#BUSINESS_TYPE").val());
	var TPM_BUSINESS_PLATE = $(".tab01-nav_active").attr("BStype");
	//alert(TPM_BUSINESS_PLATE);
	jQuery.ajax({	
	    type: "POST",
	    dataType:"json",
	    url: _basePath+'/pdfTemplate/BusinessSector!getDate.action?MUST_SELECT='+$("#MUST_SELECT").val()+'&BUSINESS_TYPE='+$("#BUSINESS_TYPE").val()+'&TPM_BUSINESS_PLATE='+encodeURI(TPM_BUSINESS_PLATE)+'&TPM_UPLOAD_DATE1='+$("input[name='TPM_UPLOAD_DATE1']").val(),
	    success: function(json){
		//alert("查询成功");
		 	var data = {flag:json.flag,total:json.data.length,rows:json.data};
			$("#pageTable").datagrid("loadData",data);
   		}
	});
}




function changeTab(tabThis){
	clearMess();
	//alert();
	$(".tab01-nav_active").attr("class","tab01-nav");
	$(tabThis).attr("class","tab01-nav_active");
	var TPM_BUSINESS_PLATE = $(".tab01-nav_active").attr("BStype");
	jQuery.ajax({
	    type: "POST",
	    dataType:"json",
	    url: _basePath+'/pdfTemplate/BusinessSector!getDate.action?TPM_BUSINESS_PLATE='+encodeURI(TPM_BUSINESS_PLATE),
	    success: function(json){
		 	var data = {flag:json.flag,total:json.data.length,rows:json.data};
			$("#pageTable").datagrid("loadData",data);
   		}
	});
}