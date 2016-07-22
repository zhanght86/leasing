$(function(){
	$('[_type]').css('text-align','right');
	$('[_type]').change(function(){
		var tmptxt = $(this).val();
		if(isNaN(tmptxt)){
			alert('输入数字！');
			$(this).val('');
			$(this).focus();
			return;
		}
		
		//借款人月平均工资
		var avgS1 = avgSalary('SALARY_11','SALARY_12','SALARY_13').toFixed(2);
		$('#AVG_SALARY_1').val(avgS1);
		//共同借款人月平均工资
		var avgS2 = avgSalary('SALARY_21','SALARY_22','SALARY_23').toFixed(2);
		$('#AVG_SALARY_2').val(avgS2);
		//Guarantor 月平均工资
		var avgS3 = avgSalary('SALARY_31','SALARY_32','SALARY_33').toFixed(2);
		$('#AVG_SALARY_3').val(avgS3);
		
		//借款人其他月收入
		var os1 = $('#OTHER_SALARY_1').val();
		//共同借款人其他月收入
		var os2 = $('#OTHER_SALARY_2').val();
		//Guarantor其他月收入
		var os3 = $('#OTHER_SALARY_3').val();
		
		//月收入
		var salary1 = FloatAdd(avgS1,os1).toFixed(2);
		$('#SALARY_1').val(salary1);
		var salary2 = FloatAdd(avgS2,os2).toFixed(2);
		$('#SALARY_2').val(salary2);
		var salary3 = FloatAdd(avgS3,os3).toFixed(2);
		$('#SALARY_3').val(salary3);
		
		//家庭月收入
		var salary0 = FloatAdd(salary1,salary2).toFixed(2);
		$('#SALARY_0').val(salary0);
		
		//入账
		var na1 = avgSalary('NET_AMOUNT_11','NET_AMOUNT_12','NET_AMOUNT_13').toFixed(2);
		$('#AVG_NET_AMOUNT_1').val(na1);
		var na2 = avgSalary('NET_AMOUNT_21','NET_AMOUNT_22','NET_AMOUNT_23').toFixed(2);
		$('#AVG_NET_AMOUNT_2').val(na2);
		var na3 = avgSalary('NET_AMOUNT_31','NET_AMOUNT_32','NET_AMOUNT_33').toFixed(2);
		$('#AVG_NET_AMOUNT_3').val(na3);
		
		
		//月收入
		var amount1 = monthlyIncome('AVG_NET_AMOUNT_1','NET_AMOUNT_A1','NET_AMOUNT_B1','OTHER_AMOUNT_1').toFixed(2);
		$('#AMOUNT_1').val(amount1);
		var amount2 = monthlyIncome('AVG_NET_AMOUNT_2','NET_AMOUNT_A2','NET_AMOUNT_B2','OTHER_AMOUNT_2').toFixed(2);
		$('#AMOUNT_2').val(amount2);
		var amount3 = monthlyIncome('AVG_NET_AMOUNT_3','NET_AMOUNT_A3','NET_AMOUNT_B3','OTHER_AMOUNT_3').toFixed(2);
		$('#AMOUNT_3').val(amount3);
		
		//家庭月收入
		var amount0 = FloatAdd(amount1,amount2).toFixed(2);
		$('#AMOUNT_0').val(amount0);
		
		//可核实总月收入
		var sa1 = FloatAdd(salary1,amount1).toFixed(2);
		$('#SUM_AMOUNT_1').val(sa1);
		var sa2 = FloatAdd(salary2,amount2).toFixed(2);
		$('#SUM_AMOUNT_2').val(sa2);
		var sa3 = FloatAdd(salary3,amount3).toFixed(2);
		$('#SUM_AMOUNT_3').val(sa3);
		
		//家庭可核实总月收入
		var sa0 = FloatAdd(sa1,sa2).toFixed(2);
		$('#SUM_AMOUNT_0').val(sa0);
		
		//偏差
		var d1 = FloatSub($('#ZC_AMOUNT_1').val(),sa1);
		$('#DEVIATION_1').val(d1);
		var d2 = FloatSub($('#ZC_AMOUNT_2').val(),sa2);
		$('#DEVIATION_2').val(d2);
		var d3 = FloatSub($('#ZC_AMOUNT_3').val(),sa3);
		$('#DEVIATION_3').val(d3);
		
		//总月供款
		var sm1 = sumMonthly('CAR_MONTHLY_1','HOUSE_MONTHLY_1','OTHER_MONTHLY_1').toFixed(2);
		$('#SUM_MONTHLY_1').val(sm1);
		var sm2 = sumMonthly('CAR_MONTHLY_2','HOUSE_MONTHLY_2','OTHER_MONTHLY_2').toFixed(2);
		$('#SUM_MONTHLY_2').val(sm2);
		var sm3 = sumMonthly('CAR_MONTHLY_3','HOUSE_MONTHLY_3','OTHER_MONTHLY_3').toFixed(2);
		$('#SUM_MONTHLY_3').val(sm3);
		var sm0 = sumMonthly('CAR_MONTHLY_0','HOUSE_MONTHLY_0','OTHER_MONTHLY_0').toFixed(2);
		$('#SUM_MONTHLY_0').val(sm0);
		
		//负债收入比
		if(sa1!=0){
			var dti1 = FloatDiv(sm1,sa1).toFixed(2);
			$('#DTI_1').val(dti1);
		}else{
			$('#DTI_1').val('');
		}
		if(sa2!=0){
			var dti2 = FloatDiv(sm2,sa2).toFixed(2);
			$('#DTI_2').val(dti2);
		}else{
			$('#DTI_2').val('');
		}
		if(sa3!=0){
			var dti3 = FloatDiv(sm3,sa3).toFixed(2);
			$('#DTI_3').val(dti3);
		}else{
			$('#DTI_3').val('');
		}
		if(sa0!=0){
			var dti0 = FloatDiv(sm0,sa0).toFixed(2);
			$('#DTI_0').val(dti0);
		}else{
			$('#DTI_0').val('');
		}
		
		//最后一次结息金额
		var a2 = FloatMul($('#ACCRUAL_1').val(),1100).toFixed(2);
		$('#ACCRUAL_2').val(a2);
		
	});
});

function noCount(checkbox){
	if($(checkbox).is(':checked')){
		$('#table1').hide();
		$('.tr0').show();
	}else{
		$('#table1').show();
		$('.tr0').hide();
	}
}

//工资
function avgSalary(s1,s2,s3){
	var a = $('#'+s1).val();
	var b = $('#'+s2).val();
	var c = $('#'+s3).val();
	
	//月平均工资
	var avgS = FloatDiv(FloatAdd(FloatAdd(a,b),c),3);
	return avgS;
}

//入账月收入
function monthlyIncome(s1,s2,s3,s4){
	var a = $('#'+s1).val();
	var b = $('#'+s2).val();
	var c = $('#'+s3).val();
	var d = $('#'+s4).val();
	
	var sumM = FloatAdd( FloatMul( FloatMul(a, FloatDiv(b,100)),FloatDiv(c,100)), d);
	return sumM;
}

//总月供款
function sumMonthly(s1,s2,s3){
	var a = $('#'+s1).val();
	var b = $('#'+s2).val();
	var c = $('#'+s3).val();
	
	var sumM = FloatAdd(FloatAdd(a,b),c);
	return sumM;
}

function save(){
	if(!$('#count').is(':checked')&&
			($('#DTI_1').val().trim()==''
				&&$('#DTI_2').val().trim()==''
				&&$('#DTI_3').val().trim()==''
				&&$('#DTI_0').val().trim()=='')){
		return alert('请进行计算或勾选不计算！');
	}
	var params = "PROJECT_ID="+$('#PROJECT_ID').val()+"&IS_COUNT="+$('#count').is(':checked');
	params += "&TPCL="+$('#TPCL').val()+"&REMARK="+$('#REMARK').val()+"&REJECT="+$('#REJECT').val()
			+"&REPULSE_CAUSE="+$('#REPULSE_CAUSE').val()+"&CUST_TYPE="+$('#CUST_TYPE').val();
	
	if(!$('#count').is(':checked')){
		var json1 = {};
		$('[_type="借款人"]').each(function(){
			json1[this.name] = this.value;
		});
		var json2 = {};
		$('[_type="共同借款人"]').each(function(){
			json2[this.name] = this.value;
		});
		var json3 = {};
		$('[_type="家庭"]').each(function(){
			json3[this.name] = this.value;
		});
		var json4 = {};
		$('[_type="担保人"]').each(function(){
			json4[this.name] = this.value;
		});
		params += "&json1="+JSON.stringify(json1);
		params += "&json2="+JSON.stringify(json2);
		params += "&json3="+JSON.stringify(json3);
		params += "&json4="+JSON.stringify(json4);
	}
	
	$.ajax({
		url:_basePath+'/leeds/DTI/DTI!save.action',
		data:params,
		type:'post',
		dataType:'json',
		success:function(result){
			alert(result.msg);
		}
	});
}

function clean(){
	if(confirm('确定要重置？')){
		$.ajax({
			url:_basePath+'/leeds/DTI/DTI!clean.action',
			data:'PROJECT_ID='+$('#PROJECT_ID').val(),
			type:'post',
			dataType:'json',
			success:function(result){
				if(result.flag){
					alert(result.msg);
				}
				location.replace(location);
			}
		});
	}
}









//浮点数加法运算
function FloatAdd(arg1, arg2) {
    var r1, r2, m;
    try {
        r1 = arg1.toString().split(".")[1].length;
    } catch(e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    } catch(e) {
        r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2)) ;
    return (arg1 * m + arg2 * m) / m;
}


//浮点数减法运算
function FloatSub(arg1, arg2) {
    var r1, r2, m, n;
    try {
        r1 = arg1.toString().split(".")[1].length;
    } catch(e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    } catch(e) {
        r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2));
    //动态控制精度长度
    n = (r1 >= r2) ? r1: r2;
    return ((arg1 * m - arg2 * m) / m).toFixed(n);
}


//浮点数乘法运算
function FloatMul(arg1, arg2) {
    var m = 0,
    s1 = arg1.toString(),
    s2 = arg2.toString();
    try {
        m += s1.split(".")[1].length;
    } catch(e) {}
    try {
        m += s2.split(".")[1].length;
    } catch(e) {}
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}


//浮点数除法运算
function FloatDiv(arg1, arg2) {
    var t1 = 0,
    t2 = 0,
    r1, r2;
    try {
        t1 = arg1.toString().split(".")[1].length;
    } catch(e) {}
    try {
        t2 = arg2.toString().split(".")[1].length;
    } catch(e) {}
    with(Math) {
        r1 = Number(arg1.toString().replace(".", "")) ;
        r2 = Number(arg2.toString().replace(".", "")) ;
        return (r1 / r2) * pow(10, t2 - t1);
    };
};
