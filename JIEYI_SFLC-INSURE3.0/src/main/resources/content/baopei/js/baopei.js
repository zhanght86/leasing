function setOperation(val,rowData) {
	return "<a href='#'  onclick=\"selPolicy(\'"+rowData.PAYLIST_CODE+"\',\'"+rowData.INSU_PRICE+"\',\'"+rowData.INCU_ID+"\',\'"+rowData.ID+"\',\'"+rowData.ORG_ID+"\')\">理赔</a>";
}
function selPolicy(PAYLIST_CODE,INSU_PRICE,INCU_ID,ID,ORG_ID)
{
		    	$("#disDiv #PAYLIST_CODE").val(PAYLIST_CODE);
		    	$("#disDiv #INSU_PRICE").val(INSU_PRICE);
		    	$("#disDiv #INCU_ID").val(INCU_ID);
		    	$("#disDiv #ID").val(ID);
		    	$("#disDiv #PRO_CODE").val(ORG_ID);
		    	$('#disDiv').dialog({
		    		title: '理赔',
		    		width: 300,
		    		height: 150,
		    		modal:true,
		    		cache: false
		    	});
}
function baopei(PAYLIST_CODE,INSU_PRICE,baofei,ID,ORG_ID)
{
		if(confirm("确定要设置理赔吗？")) {
			var INSU_PRICE=$("#INSU_PRICE").val();
			var PAYLIST_CODE=$("#PAYLIST_CODE").val();
			var INCU_ID=$("#INCU_ID").val();
			var baofei=$("#baofei").val();
			var ID=$("#ID").val();
			var ORG_ID=$("#ORG_ID").val();
			jQuery.ajax( {
				url : _basePath + "/baopei/Baopei!lipei.action",
				data : "INSU_PRICE="+INSU_PRICE+"&baofei="+baofei+"&PAYLIST_CODE="+PAYLIST_CODE+"&ID="+ID+"&ORG_ID="+ORG_ID, 
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