$(function(){
	var paytype = $("#PAY_TYPE").val();
    var num = $("#TERM").val();
    if(paytype=='4'){
        paytimeReady('REN_PAY_TIME');
		paytimeReady('PAY_DATE');
   }else{
        paytimeReady('PAY_DATE');
   }
});

//还款日递增
function paytimeReady(id){
	//设定还款日
    var paytype = $("#PAY_TYPE").val();
	var num = $("#TERM").val();
	var strDate = $("#PAY_DATE").val();//还款日
	var now = new Date(strDate.replace(/\-/g,"/"));
	var perid = $("#PERIOD").val();//周期
	for(var i=1;i<=num;i++){
			if(i>1){
				//周期为月
				if(perid=='1'){
					now = new Date( now.setMonth(now.getMonth() + 1));
					now = getYestoday(now);
					$("#"+id+i).datebox('setValue',now);
					now = new Date(now.replace(/\-/g,"/"));
				}
				//周期为季度
				else if(perid=='2'){
					now = new Date( now.setMonth(now.getMonth() + 4));
					now = getYestoday(now);
					$("#"+id+i).val(now);					
					now = new Date(now.replace(/\-/g,"/"));
				}
				//周期为年
				else{
					now = getLastYear(now);
					$("#"+id+i).val(now);
					now = new Date(now.replace(/\-/g,"/"));
				}
			}
			else{
				$("#"+id+i).datebox('setValue',strDate);
			}
	}
}
//转换格式
function getYestoday(date) {
	var yesterday_milliseconds = date.getTime();
	var yesterday = new Date();
	yesterday.setTime(yesterday_milliseconds);
	var strYear = yesterday.getFullYear();
	var strDay = yesterday.getDate();
	var strMonth = yesterday.getMonth() + 1;
	if (strMonth < 10) {
		strMonth = "0" + strMonth;
	}
	if(strDay < 10) {
		strDay = "0" + strDay;
	}
	datastr = strYear + "-" + strMonth + "-" + strDay;
	return datastr;
}
//获得上一年在这一天的日期      
function getLastYear(date){      
    var strYear = date.getFullYear() - 1;        
    var strDay = date.getDate();        
    var strMonth = date.getMonth()+1;      
    if(strMonth<10)        
    {        
       strMonth="0"+strMonth;        
    }      
    if(strDay<10)        
    {        
       strDay="0"+strDay;        
    }      
    datastr = strYear+"-"+strMonth+"-"+strDay;      
    return datastr;      
}   
//租金计算
function countRent(id){
	var paytype =  $("#PAY_TYPE").val();
	var num = $("#TERM").val();
	var value = $("#"+id).val();
	value=value.replace(/[^\d\.]/g,'');
	$("#"+id).val(value);
	//等额本息
	if(paytype=='1'){
		var value = $("#"+id).val();
		value=value.replace(/[^\d\.]/g,'');
		$("#"+id).val(value);
		for(var i=2;i<=num;i++){
			$("#MONTH_PRICE"+i).val(value);
		}
	}
}
//本金计算
function countOwn(id,k){
    	var paytype =  $("#PAY_TYPE").val();
		var num = $("#TERM").val();
		var value = $("#"+id).val();
		value=value.replace(/[^\d\.]/g,'');
		$("#"+id).val(value);
		for(var i=1;i<=num;i++){
			if(paytype=='2'){
				$("#OWN_PRICE"+i).val(value);
			}
			if(i==k){
				if(paytype!='1'){
					if($("#REN_PRICE"+i).val()==null||$("#REN_PRICE"+i).val()==''){
        				$("#MONTH_PRICE"+i).val(parseFloat(value));
					}
					else{
						$("#MONTH_PRICE"+i).val(parseFloat(value)+parseFloat($("#REN_PRICE"+i).val()));
					}
				}
			}
		}
}
//利息计算
function countRenPrice(id,k){
   	 	var paytype =  $("#PAY_TYPE").val();
		var num =  $("#TERM").val();
		var value = $("#"+id).val();
		value=value.replace(/[^\d\.]/g,'');
		$("#"+id).val(value);
		for(var i=1;i<=num;i++){
				if(i==k){
					if(paytype!='1'){
						if($("#OWN_PRICE"+i).val()==null||$("#OWN_PRICE"+i).val()==''){
        					$("#MONTH_PRICE"+i).val(parseFloat(value));
						}
						else{
							$("#MONTH_PRICE"+i).val(parseFloat(value)+parseFloat($("#OWN_PRICE"+i).val()));
						}
					}
				}
		}
}