function PERIODChange(obj){
	var period=$(obj).val();
	if (period == "" || period == null || period == undefined) {
		var DUE_RECE_CURR=$("input[name='DUE_RECE_CURR']").val();
		$("input[name='SY_MONEY']").val(0);
		$("input[name='NOT_INTEREST']").val(0);
		$("input[name='PENALTY_MONEYED']").val(0);
		$("input[name='BENJIN_AFTER']").val(0);
		
		$("input[name='DUE_RECE_YS']").val(DUE_RECE_CURR);
		$("input[name='DUE_RECE_JM']").val(0);
		$("input[name='DUE_RECE']").val(DUE_RECE_CURR);
		TOTALMONEY();
	}
	else{
		var PAYLIST_CODE=$("input[name='PAYLIST_CODE']").val();
		$.ajax({
			url:_basePath+"/pay/PayTask!queryInfoByPeriod.action?PAYLIST_CODE="+PAYLIST_CODE+"&PERIOD="+period,
			type:"post",
			dataType:"json",
			success:function (data){
				$("input[name='SY_MONEY']").val(data[0].NO_RENT_MEONY);
				$("input[name='NOT_INTEREST']").val(data[0].NOT_INTEREST);
				$("input[name='ACCOUNT_DATE']").val(data[0].RENT_DATE);
				$("input[name='BENJIN_AFTER']").val(data[0].BENJIN_AFTER);
				
				$("input[name='DUE_RECE_YS']").val(data[0].DUNMONEY);
				var DUNMONEY=data[0].DUNMONEY;
				var JM=$("input[name='DUE_RECE_JM']").val();
				if(DUNMONEY >=JM){
					$("input[name='DUE_RECE']").val(DUNMONEY-JM);
				}
				else{
					$("input[name='DUE_RECE_JM']").val(0);
					$("input[name='DUE_RECE']").val(data[0].DUNMONEY);
				}
				exemptMoney();
			}
		});
	}
}

function changeDunMoney(obj){
	var JM=parseFloat($(obj).val());
	var YS=parseFloat($("input[name='DUE_RECE_YS']").val());
	if(JM>YS){
		$(obj).val(0);
		$("input[name='DUE_RECE']").val(YS);
	}
	else{
		$("input[name='DUE_RECE']").val(YS-JM);
	}
	TOTALMONEY();
}

function exemptMoney(){
	var exemptInterest=$("input[name='exemptInterest']").val();
	if (exemptInterest == "" || exemptInterest == null || exemptInterest == undefined) {
		exemptInterest=0;
		$("input[name='exemptInterest']").val(0);
	}
	
	if(exemptInterest>100){
		exemptInterest=100;
		$("input[name='exemptInterest']").val(100);
	}
	
	if(exemptInterest<0){
		exemptInterest=0;
		$("input[name='exemptInterest']").val(0);
	}
	
	var NOT_INTEREST=$("input[name='NOT_INTEREST']").val();
	if (NOT_INTEREST == "" || NOT_INTEREST == null || NOT_INTEREST == undefined) {
		NOT_INTEREST=0;
	}
	
	var PENALTY_MONEYED=parseFloat(NOT_INTEREST)*(100- parseFloat(exemptInterest))/100;
	$("input[name='PENALTY_MONEYED']").val(PENALTY_MONEYED.toFixed(2));
	TOTALMONEY();
}

function TOTALMONEY(){
	var SY_MONEY=parseFloat($("input[name='SY_MONEY']").val());//(1+)回购前未收租金合计
	if (SY_MONEY == "" || SY_MONEY == null || SY_MONEY == undefined || SY_MONEY == "NaN") {
		SY_MONEY=parseFloat(0);
	}
	
	var BENJIN_AFTER=parseFloat($("input[name='BENJIN_AFTER']").val());//(2+)回购期次后本金合计
	if (BENJIN_AFTER == "" || BENJIN_AFTER == null || BENJIN_AFTER == undefined || BENJIN_AFTER == "NaN") {
		BENJIN_AFTER=parseFloat(0);
	}
	
	var DUE_RECE=parseFloat($("input[name='DUE_RECE']").val());//(3+)罚息金额
	if (DUE_RECE == "" || DUE_RECE == null || DUE_RECE == undefined || DUE_RECE == "NaN") {
		DUE_RECE=parseFloat(0);
	}
	
	var BZJDk=parseFloat($("input[name='BZJDk']").val());//(4-)保证金抵扣金额
	if (BZJDk == "" || BZJDk == null || BZJDk == undefined || BZJDk == "NaN") {
		BZJDk=parseFloat(0);
	}
	
	var HG_SXF=parseFloat($("input[name='HG_SXF']").val());//(5+)回购手续费
	if (HG_SXF == "" || HG_SXF == null || HG_SXF == undefined || HG_SXF == "NaN") {
		HG_SXF=parseFloat(0);
	}
	
	var DBBZJDK=parseFloat($("input[name='DBBZJDK']").val());//(6-)DB保证金抵扣金额
	if (DBBZJDK == "" || DBBZJDK == null || DBBZJDK == undefined || DBBZJDK == "NaN") {
		DBBZJDK=parseFloat(0);
	}
	
	var taxes=parseFloat($("input[name='taxes']").val());//(7+)税金
	if (taxes == "" || taxes == null || taxes == undefined || taxes == "NaN") {
		taxes=parseFloat(0);
	}
	
	var PENALTY_MONEYED=parseFloat($("input[name='PENALTY_MONEYED']").val());//(8+)减免后金额
	if (PENALTY_MONEYED == "" || PENALTY_MONEYED == null || PENALTY_MONEYED == undefined || PENALTY_MONEYED == "NaN") {
		PENALTY_MONEYED=parseFloat(0);
	}
	
	var LGJTOTAL=parseFloat($("input[name='LGJTOTAL']").val());//(9-)留购价
	if (LGJTOTAL == "" || LGJTOTAL == null || LGJTOTAL == undefined || LGJTOTAL == "NaN") {
		LGJTOTAL=parseFloat(0);
	}
//	alert("SY_MONEY="+SY_MONEY+"---BENJIN_AFTER="+BENJIN_AFTER+"---DUE_RECE="+DUE_RECE+"---BZJDk="+BZJDk+"---HG_SXF="+HG_SXF+"---DBBZJDK="+DBBZJDK+"---taxes="+taxes+"---PENALTY_MONEYED="+PENALTY_MONEYED+"---LGJTOTAL="+LGJTOTAL);
	var TOTAL_MONEY=parseFloat(SY_MONEY+BENJIN_AFTER+DUE_RECE-BZJDk+HG_SXF-DBBZJDK+taxes+PENALTY_MONEYED-LGJTOTAL).toFixed(2);
//	alert(TOTAL_MONEY);
	$("input[name='TOTAL_MONEY']").val(TOTAL_MONEY);
}


function CUSTDKCHANGE(){
	var STATUS=$("select[name='BZJSTATUS']").val();
	if(STATUS==0){
		$("input[name='BZJDk']").removeAttr("readonly");
		var BZJTOTAL=$("input[name='BZJTOTAL']").val();
		if (BZJTOTAL == "" || BZJTOTAL == null || BZJTOTAL == undefined) {
			BZJTOTAL=0;
		}
		$("input[name='BZJDk']").val(BZJTOTAL);
	}
	else{
		$("input[name='BZJDk']").val(0);
		$("input[name='BZJDk']").attr("readonly","readonly");
	}
	TOTALMONEY();
}

function changeCustMoney(){
	var BZJTOTAL=$("input[name='BZJTOTAL']").val();
	if (BZJTOTAL == "" || BZJTOTAL == null || BZJTOTAL == undefined) {
		BZJTOTAL=0;
	}
	
	var BZJDk=$("input[name='BZJDk']").val();
	if (BZJDk == "" || BZJDk == null || BZJDk == undefined) {
		$("input[name='BZJDk']").val(0);
		BZJDk=0;
	}
	
	if(parseFloat(BZJDk)>parseFloat(BZJTOTAL)){
		$("input[name='BZJDk']").val(BZJTOTAL);
	}
	
	if(parseFloat(BZJDk)<0){
		$("input[name='BZJDk']").val(0);
	}
	
	TOTALMONEY();
}

function DBDKCHANGE(){
	var STATUS=$("select[name='DBBZJSTATUS']").val();
	if(STATUS==0){
		$("input[name='DBBZJDK']").removeAttr("readonly");
		var DBBZJTOTAL=$("input[name='DBBZJTOTAL']").val();
		if (DBBZJTOTAL == "" || DBBZJTOTAL == null || DBBZJTOTAL == undefined) {
			DBBZJTOTAL=0;
		}
		$("input[name='DBBZJDK']").val(DBBZJTOTAL);
	}
	else{
		$("input[name='DBBZJDK']").val(0);
		$("input[name='DBBZJDK']").attr("readonly","readonly");
	}
	TOTALMONEY();
}

function changeDBMoney(obj){
	var DBBZJTOTAL=$("input[name='DBBZJTOTAL']").val();
	if (DBBZJTOTAL == "" || DBBZJTOTAL == null || DBBZJTOTAL == undefined) {
		DBBZJTOTAL=0;
	}
	
	var DBBZJDK=$("input[name='DBBZJDK']").val();
	if (DBBZJDK == "" || DBBZJDK == null || DBBZJDK == undefined) {
		$("input[name='DBBZJDK']").val(0);
		DBBZJDK=0;
	}
	
	if(parseFloat(DBBZJDK)>parseFloat(DBBZJTOTAL)){
		$("input[name='DBBZJDK']").val(DBBZJTOTAL);
	}
	
	if(parseFloat(DBBZJDK)<0){
		$("input[name='DBBZJDK']").val(0);
	}
	
	TOTALMONEY();
}

function get_custName(param){
	var pv = $(param).val();
	if(pv.length<2){
		return null;
	}
	//去后台验证是否有供应商
	jQuery.ajax({
        type: "POST",
        dataType: "json",
		async:false,
        url: _basePath + "/buyBack/BuyBack!getCustName.action",
        data: "pv="+pv,
        success: function(msg){
			//console.info(msg.data);
			var persons = msg.data;
			if(persons.length>=2){
				var str="";
				for(var i=0;i<persons.length;i++){
					var person = persons[i];
					str = str+"<li><input id=\""+person.ID+"\" type=\"radio\" name =\"buyCust\" value=\""+person.ID+"\" ><label for=\""+person.ID+"\">"+person.NAME+"</label></li>";
				}
				$("#dialog_").append(str);
				$("#dialog_").dialog({  
					modal:true,
					buttons: [{
						text:'Ok',
						iconCls:'icon-ok',
						handler:function(){
							var select = $("input[name='buyCust']:checked");
							//$("input[name='PAYEE_NAME']").val($("label[for='"+select.val()+"']").text());
							//$("input[name='PAYEE_NAME']").val($("label[for='"+select.val()+"']").text()+"||"+select.val());
							$("input[name='PAYEE_NAME']").val(select.val());
							$("#PAYEE_NAME_SPAN").text($("label[for='"+select.val()+"']").text());
							$("input[name='PAYEE_NAME_TEXT']").val($("label[for='"+select.val()+"']").text());
							$("#dialog_").dialog('close',{forceClose:true});
							$("#dialog_").empty();
						}
					}]
				})
			}else if(persons.length==1){
				//$("input[name='PAYEE_NAME']").val(persons[0].NAME+"||"+persons[0].ID);
				$("input[name='PAYEE_NAME']").val(persons[0].ID);
				$("#PAYEE_NAME_SPAN").text(persons[0].NAME);
				$("input[name='PAYEE_NAME_TEXT']").val(persons[0].NAME);
			}else {
				$("input[name='PAYEE_NAME']").val("");
				$("#PAYEE_NAME_SPAN").text("");
				$("input[name='PAYEE_NAME_TEXT']").val("");
			}
        }
    });
}

//点击下一步（保存测试结果）
function nex_New() {
	var JQ_PERIOD=$("select[name='JQ_PERIOD']").val();
	if (JQ_PERIOD == "" || JQ_PERIOD == null || JQ_PERIOD == undefined) {
		alert("请选择结清期次!") ;
		return ;
	}
	
	var TOTAL_MONEY=$("input[name='TOTAL_MONEY']").val();
	if(parseFloat(TOTAL_MONEY)<0){
		alert("请修改保证金抵扣金额，合计不能小于零!") ;
		return ;
	}
	
	var PAYEE_NAME=$("input[name='PAYEE_NAME']").val();
	if(PAYEE_NAME == "" || PAYEE_NAME == null || PAYEE_NAME == undefined){
		alert("请输入回购方!") ;
		return ;
	}
	
	
	var settleInfo = getFormParamFormat("changePay");
	var data_ ="HG_SXF="+ $("input[name='HG_SXF']").val()+ "&taxes="
			+ $("input[name='taxes']").val()  + "&PAYLIST_CODE="
			+ $("#PAYLIST_CODE").val() + "&code_=" + $("#code_").val()
			 + "&settleInfo=" + JSON.stringify(settleInfo);
	jQuery.ajax( {
		type : "POST",
		dataType : "json",
		async : false,
		url : _basePath + "/buyBack/BuyBack!buyBackSave.action",
		data : data_,
		success : function(json) {
			$.messager.alert("提示","发起流程成功！",'info',function(){
						$.messager.alert("提示",json.msg+json.data,"info",function(){
						//点击保存后下一步和测算按钮不可用
						$('#nex_').linkbutton('disable');
						$('#calculate_').linkbutton('disable');
						window.location.href = _basePath
								+ "/buyBack/BuyBack!buyBackManagement.action";
						});
					});		
		}
	});
}