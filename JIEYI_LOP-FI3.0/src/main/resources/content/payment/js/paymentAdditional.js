$(document).ready(function(){
	var num=0;
	$(".eqnu").each(function(){
		if($(this).children().eq(1).text()=="挂车" && parseFloat($(this).children().eq(4).text())<150000){
		}else{
			num=num+1;
		}
	});
	$("#EQNUM").val(num);
	getPaymentType();
	
	dealMoney();
	
	srche();
});

/**
 * 初始化时将应付金额值写入，如果初始化时实际金额为空，则默认为0
 */
function dealMoney(){
	
	$("input[name='APPLY_MONEY']").each(function(i, n) {
		
		var m = $(this).val();
		
		//如果初始化时实际金额为空，则默认为0
		if(null == m || "" == m){
			$(this).val(0);
		}
		
		//初始化时将应付金额值写入
		var first = $(this).parent().next().find("input[name='FIRST_PAYALL']").val();
		//mod gengchangbao JZZL-115 2016年3月16日 start
		//var money =  $(this).parent().next().next().find("input[name='MONEY']").val();
		//if(null == money || "" == money){
//			if(!isNaN(first) && first>0){
//				
//				var payble = 0.0;
//				 
//				if(null != m && "" != m){
//					payble = m - first;
//				}
//				
//				$(this).parent().next().next().find("input[name='MONEY']").val(payble.toFixed(2));
//			}else{
				var money = $(this).parent().next().next().find("input[name='MONEY']").val();
				
				if(null == money || "" == money){
					$(this).parent().next().next().find("input[name='MONEY']").val(m);
				}
//			}
		//}
		//mod gengchangbao JZZL-115 2016年3月16日 end
	});
	
}

/**
 * 验证名称，名称不能重复
 */
function repeatNameForArray(){
	
	// 验证名称，名称不能重复
	var mcs = $("select[name='MC']").map(function()
		{return $(this).val();}).get().join(",");
		
	var mvArray = mcs.split(",");
	
	var nary = mvArray.sort();
	for(var i = 0; i < nary.length - 1; i++)
	{
	   if (nary[i] == nary[i+1])
	    {
	       alert("名称不能重复，重复名称：" + nary[i]);
	       return true;
	    }
	}
	
}
