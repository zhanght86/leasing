$(function() {
	$("#pageTable").datagrid(
					{
						url : _basePath + "/shangpai/Shangpai!findshangpai.action",
						pagination : true,// 分页
						rownumbers : true,// 行数
						singleSelect : true,
						fitColumns : true,
						queryParams: {
							LEASE_CODE: $("input[name='LEASE_CODE']").val()
						},
						toolbar : '#pageForm',
						columns : [ [
								{
									field : 'ID',
									align : 'center',
									title : '操作',
									width : 15,
									formatter : function(value, rowData,rowIndex) 
									{
										var rehtml = "<a href='javascript:void(0);' onclick='uploadPdfTemplate("
													+ value
													+ ",\""+rowData.CUST_NAME+"\")'>添加</a>";
										rehtml+= "|&nbsp;<a href='javascript:void(0);' onclick='lookshangpai("
													+ value
													+ ",\""+rowData.CUST_NAME+"\")'>查看</a>";
										if(rowData.ADDRESS!= null && rowData.ADDRESS != "")
										{
										rehtml+= "|&nbsp;<a href='javascript:void(0);' onclick='updates("
											+ value
											+ ",\""+rowData.CUST_NAME+"\")'>修改</a>";
										}else
										{
											rehtml+= "| <a href='javascript:void(0);' style='color:#CCCCCC'>修改</a>";
										}
										return rehtml;
									}
								}, 
								{
									field : 'LEASE_CODE',
									align : 'center',
									width : 10,
									title : '融资租赁合同号'
								},
								{
									field : 'CUST_NAME',
									align : 'center',
									width : 10,
									title : '客户名称'
								},

								{
									field : 'CLERK_NAME',
									align : 'center',
									width : 10,
									title : '客户经理'
								},
				
								{
									field : 'SPSTUTE',
									align : 'center',
									width : 10,
									title : '上牌状态'
								},
								/*{
									field : 'COMPANY_NAME',
									align : 'center',
									width : 10,
									title : '融指标'
								},*/
								{
									field : 'PLATFORM_TYPE',
									align : 'center',
									width : 10,
									title : '业务类型'
								},

								{
									field : 'SUPPLIERS_NAME',
									align : 'center',
									width : 10,
									title : '经销商'
								},
								/*{
									field : 'NAME',
									align : 'center',
									width : 10,
									title : '产品名称'
								},*/
								{
									field : 'CAR_SYMBOL',
									align : 'center',
									width : 10,
									title : '车架号'
								},
								{
									field : 'SHANGPAIDATE',
									align : 'center',
									width : 10,
									title : '上牌时间'
								},
								{
									field : 'ADDRESS',
									align : 'center',
									width : 10,
									title : '上牌地点'
								}
								
					] ]
						
});
});
function uploadPdfTemplate(ID,CUST_NAME)
{
		    	$("#disDiv #ID").val(ID);
		    	$("#disDiv #CUST_NAME").val(CUST_NAME);
		    	$('#disDiv').dialog({
		    		title: '添加',
		    		width: 400,
		    		height: 450,
		    		modal:true,
		    		cache: false
		    	});
}
function lookshangpai(ID,CUST_NAME)
{
			$('#dislook').dialog({
				title: '查看',
				width: 400,
				height: 450,
				modal:true,
				closed: false,
				href:'Shangpai!findone.action?ID='+ID 
			});

}
function baopei(ID,CUST_NAME,SHANGPAIDATE,ADDRESS,DANWEI,FACHEDATE,CARNUM,BEIZHU)
{
		if(confirm("确定要添加吗？")) {
			jQuery.ajax( {
				url : _basePath + "/shangpai/Shangpai!saveshangpai.action",
				data : "ID="+ID+"&CUST_NAME="+CUST_NAME+"&SHANGPAIDATE="+SHANGPAIDATE+"&ADDRESS="+ADDRESS+"&DANWEI="+DANWEI+"&FACHEDATE="+FACHEDATE+"&CARNUM="+CARNUM+"&BEIZHU="+BEIZHU, 
				dataType : "json",
				success : function(json) {																
						if(json.flag==true){
							jQuery.messager.alert("success",json.msg);
							$('#pageTable').datagrid('reload');	
							$("#disDiv").dialog('close');
																}								
										}
			});							
		}
}
function updates(ID,CUST_NAME)
{
			$('#disupdate').dialog({
				title: '修改',
				width: 400,
				height: 450,
				modal:true,
				closed: false,
				href:'Shangpai!findbyid.action?ID='+ID 
			});

}
function updateshang(ID,CUST_NAME,SHANGPAIDATE,ADDRESS,DANWEI,FACHEDATE,CARNUM,BEIZHU,XIUGAI,XIUGAIDATE)
{
		if(confirm("确定要修改吗？")) {
			jQuery.ajax( {
				url : _basePath + "/shangpai/Shangpai!updateshangpai.action",
				data : "ID="+ID+"&CUST_NAME="+CUST_NAME+"&SHANGPAIDATE="+SHANGPAIDATE+"&ADDRESS="+ADDRESS+"&DANWEI="+DANWEI+"&FACHEDATE="+FACHEDATE+"&CARNUM="+CARNUM+"&BEIZHU="+BEIZHU+"&XIUGAI="+XIUGAI+"&XIUGAIDATE="+XIUGAIDATE, 
				dataType : "json",
				success : function(json) {																
						if(json.flag==true){
							jQuery.messager.alert("success",json.msg);
							$('#pageTable').datagrid('reload');	
							$("#disupdate").dialog('close');
																}								
										}
			});							
		}
}