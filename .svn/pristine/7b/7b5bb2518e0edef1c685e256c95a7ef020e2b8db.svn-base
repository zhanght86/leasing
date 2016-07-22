/**
 * 留购申请
 * @param 
 * @return
 */
function purchaseapp(row){
	if (row){
		//alert(_basePath+"/project/purchase/Purchase!applyPurchasePage.action?PRO_CODE="+row.PRO_CODE+"&PROJECT_ID="+row.ID);
		top.addTab("留购申请界面",_basePath+"/project/purchase/Purchase!applyPurchasePage.action?PRO_CODE="+row.PRO_CODE+"&PROJECT_ID="+row.ID+"&SUP_ID="+row.SUP_ID+"&viewType=1");
    }
}
/**
 * 批量发起留购流程
 * @param 
 * @return
 */
function moreAppWF(){
	var datagridList=$('#pageTable').datagrid('getChecked');
	if(confirm("是否已经导出excel？批量发起后将无法导出！")){
		for(var i = 0; i < datagridList.length; i++){
			var PROJECT_ID=datagridList[i].ID;
			var PRO_CODE=datagridList[i].PRO_CODE;
			var CLIENT_ID=datagridList[i].CLIENT_ID;
			var SUP_ID = datagridList[i].SUP_ID;
			var REMARK='批量发起';
			
			var data="PRO_CODE="+PRO_CODE+"&PROJECT_ID="+PROJECT_ID+"&CLIENT_ID="+CLIENT_ID+"&SUP_ID="+SUP_ID+"&REMARK="+REMARK;
			//alert(data);
			jQuery.ajax({
		        type: "POST",
		        dataType: "json",
				url:_basePath+"/project/purchase/Purchase!appPurchaseWF.action?"+data,
				async:false,
				success:function(json){
					if(json.flag){
					}else{
						$.messager.alert("提示",json.msg);
					}
				}
			});		
			if(i==(datagridList.length-1)){
				alert("流程批量发起成功！");
				top.closeTab("留购申请");
			}
		}
	}
}

/**
 * 留购申请--数据全导出 
 * @param 
 * @return
 */
function exportExcel(){
	var datagridList=$('#pageTable').datagrid('getChecked');
	var sqlData = [];	
	
	for(var i = 0; i < datagridList.length; i++)
	{
		sqlData.push("'"+datagridList[i].ID+"'");
	}
	
	//params
	var searchParams = getFromData('#toolbar');
	
	//url
	var url = "Purchase!exportExcel.action";
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

/**
 * 留购查询--数据全导出 
 * @param 
 * @return
 */
function exportSearchExcel(){
	var datagridList=$('#pageTable').datagrid('getChecked');
	var sqlData = [];	
	
	for(var i = 0; i < datagridList.length; i++)
	{
		sqlData.push("'"+datagridList[i].ID+"'");
	}
	
	//params
	var searchParams = getFromData('#toolbar');
	
	//url
	var url = "Purchase!exportSearchExcel.action";
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
/**
 * 所有权转移证书导出
 * @param param
 * @return
 */
function exportFile(){
	
	var data = $('#pageTable').datagrid("getSelections");
	var PRO_CODE = new Array();
	var flag = true;
	$(data).each(function(){
		PRO_CODE.push(this.PRO_CODE);
	});
	//window.location.href=_basePath+"/buyBack/BuyBack!ExcelFile.action?PAYLIST_CODE="+PAY_CODE.toString()+"&FILE_NAME=所有权转移证书.xls&PATH=B50.xls";
	window.location.href=_basePath+"/project/purchase/Purchase!ExcelFile.action?PRO_CODE="+PRO_CODE.toString()+"&FILE_NAME=所有权转移证书.xls&PATH=B50.xls";
	
}

/**
 * 发起留购流程
 * @param 
 * @return
 */
function appWorkflow(){
	var PRO_CODE=$("#PRO_CODE").val();
	var PROJECT_ID=$("#PROJECT_ID").val();
	var CLIENT_ID=$("#CLIENT_ID").val();
	var SUP_CODE=$("#SUP_CODE").val();
	var REMARK=$("#REMARK").val();
	var sup_id = $("#SUP_ID").val();
	var data="PRO_CODE="+PRO_CODE+"&PROJECT_ID="+PROJECT_ID+"&CLIENT_ID="+CLIENT_ID+"&REMARK="+REMARK+"&SUP_CODE="+SUP_CODE+"&SUP_ID="+sup_id;
	jQuery.ajax({
        type: "POST",
        dataType: "json",
		url:_basePath+"/project/purchase/Purchase!appPurchaseWF.action?"+data,
		success:function(json){
			if(json.flag){
				alert("留购发起成功,已流转到合同档案上传！");
				top.closeTab("留购申请界面");
				top.closeTab("留购申请");
				
			}else{
				$.messager.alert("提示",json.msg);
			}
		}
	});		
	
}

/**
 * 小写金额转换大写
 * @param numberValue
 * @return
 */
function atoc(numberValue) {
	var numberValue = new String(Math.round(numberValue * 100)); // 数字金额
	var chineseValue = ""; // 转换后的汉字金额
	var String1 = "零壹贰叁肆伍陆柒捌玖"; // 汉字数字
	var String2 = "万仟佰拾亿仟佰拾万仟佰拾元角分"; // 对应单位
	var len = numberValue.length; // numberValue 的字符串长度
	var Ch1; // 数字的汉语读法
	var Ch2; // 数字位的汉字读法
	var nZero = 0; // 用来计算连续的零值的个数
	var String3; // 指定位置的数值
	if (len > 15) {
		alert("超出计算范围");
		return "";
	}
	if (numberValue == 0) {
		chineseValue = "零元整";
		return chineseValue;
	}
	String2 = String2.substr(String2.length - len, len); // 取出对应位数的STRING2的值
	for ( var i = 0; i < len; i++) {
		String3 = parseInt(numberValue.substr(i, 1), 10); // 取出需转换的某一位的值
		if (i != (len - 3) && i != (len - 7) && i != (len - 11)
				&& i != (len - 15)) {
			if (String3 == 0) {
				Ch1 = "";
				Ch2 = "";
				nZero = nZero + 1;
			} else if (String3 != 0 && nZero != 0) {
				Ch1 = "零" + String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			} else {
				Ch1 = String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			}
		} else { // 该位是万亿，亿，万，元位等关键位
			if (String3 != 0 && nZero != 0) {
				Ch1 = "零" + String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			} else if (String3 != 0 && nZero == 0) {
				Ch1 = String1.substr(String3, 1);
				Ch2 = String2.substr(i, 1);
				nZero = 0;
			} else if (String3 == 0 && nZero >= 3) {
				Ch1 = "";
				Ch2 = "";
				nZero = nZero + 1;
			} else {
				Ch1 = "";
				Ch2 = String2.substr(i, 1);
				nZero = nZero + 1;
			}
			if (i == (len - 11) || i == (len - 3)) { // 如果该位是亿位或元位，则必须写上
				Ch2 = String2.substr(i, 1);
			}
		}
		chineseValue = chineseValue + Ch1 + Ch2;
	}
	if (String3 == 0) { // 最后一位（分）为0时，加上“整”
		chineseValue = chineseValue + "整";
	}
	return chineseValue;
}
