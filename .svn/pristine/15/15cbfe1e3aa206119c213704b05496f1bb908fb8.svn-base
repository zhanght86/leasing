
function exportData(flag){
	$("#divFrom").empty();
	var type=1;
	var IDS="";
	if(flag == 'all')
	{
		uploadType="all";
	}
	else{
		type=2;
		var datagridList=$('#pageTable').datagrid('getChecked');
		if(datagridList.length<=0)
		{
			alert("请先选择要导出的数据在继续导出操作！");
			return false;
		}
		else{
			
			for(var i = 0; i < datagridList.length; i++)
			{
				if(i==0){
					IDS=datagridList[i].ID;
				}
				else{
					IDS=IDS+","+datagridList[i].ID;
				}
			}
		}
	}
	
	var SUPPER_NAME=$("input[name='SUPPER_NAME']").val();
	var INVOICE_DATE1=$("input[name='INVOICE_DATE1']").val();
	var INVOICE_DATE2=$("input[name='INVOICE_DATE2']").val();
	var PROJECT_CODE=$("input[name='PROJECT_CODE']").val();
	var PRODUCT_NAME=$("select[name='PRODUCT_NAME']").find("option:selected").val();
	var COMP_NAME=$("input[name='COMP_NAME']").val();
	var RESERVE_DATE1=$("input[name='RESERVE_DATE1']").val();
	var RESERVE_DATE2=$("input[name='RESERVE_DATE2']").val();
	var CUST_NAME=$("input[name='CUST_NAME']").val();
	var PAYEE_NAME=$("input[name='PAYEE_NAME']").val();
	var USER_STATUS=$("select[name='USER_STATUS']").val();
	var url="payment!EQ_Upload.action?IDS="+IDS+"&SUPPER_NAME="+SUPPER_NAME+"&INVOICE_DATE1="+INVOICE_DATE1+"&INVOICE_DATE2="+INVOICE_DATE2+"&PROJECT_CODE="+PROJECT_CODE+"&PRODUCT_NAME="+PRODUCT_NAME+"&COMP_NAME="+COMP_NAME+"&RESERVE_DATE1="+RESERVE_DATE1+"&RESERVE_DATE2="+RESERVE_DATE2+"&CUST_NAME="+CUST_NAME+"&PAYEE_NAME="+PAYEE_NAME+"&USER_STATUS="+USER_STATUS+"&type="+type;
	
	$("#divFrom").append("<form id='formSubmit' method='post' action='"+url+"'></form>");
	$("#formSubmit").submit();
}