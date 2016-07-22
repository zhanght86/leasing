function updateMoneyOrder(){
	 $("#dlgUp").dialog("open");
}

/**
 * 加法
 * @param arg1
 * @param arg2
 * @return
 */
function accAdd(arg1, arg2) {
    var r1, r2, m;
    try { r1 = arg1.toString().split(".")[1].length; } catch (e) { r1 = 0; }
    try { r2 = arg2.toString().split(".")[1].length; } catch (e) { r2 = 0; }
    m = Math.pow(10, Math.max(r1, r2));
    return (arg1 * m + arg2 * m) / m;
}

function fomatFloat(src,pos){       
    return Math.round(src*Math.pow(10, pos))/Math.pow(10, pos);       
} 


function toSave(){
	if($("#FI_REALITY_BANK").attr("selected",true).val()==''){
		return alert("请选择银行");
	}
	if($("#FI_ACCOUNT_DATE").datebox("getValue")==''){
		return alert("请选择核销时间");
	}
	if($("#FI_REALITY_MONEY").val()==''){
		alert("请选择实际金额");
	}else {
		if(parseFloat($("#HEJI_").val())>parseFloat($("#FI_REALITY_MONEY").val())){
			return alert("实际核销金额不应小于申请金额");
		}
	}
	 $("#SaveData").linkbutton("disable"); 
	$("#CheckedData").form('submit',{
		dataType:"json",
		success:function(json){
			var data = $.parseJSON(json);
			if(data.flag==true){
				alert("核销成功");
			}else {
				alert("核销失败");
			}
		}
	});
}