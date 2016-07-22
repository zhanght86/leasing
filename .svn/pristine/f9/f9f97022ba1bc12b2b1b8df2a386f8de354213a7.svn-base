function subNum(){
			var BAILOUTWAY_NSFR = $("#BAILOUTWAY_NSFR").val();//融资比例
			var BAILOUTWAY = $("#BAILOUTWAY").val();//融资额
			if(BAILOUTWAY_NSFR==''&&BAILOUTWAY==''){
				alert("融资比例和融资额不能都为空");
				$("#BAILOUTWAY_NSFR").focus();
				return false;
			}
			var MARGIN_SCALE = $("#MARGIN_SCALE").val();//保证金比例
			var MARGIN = $("#MARGIN").val();//保证金
			if(MARGIN_SCALE==''&&MARGIN==''){
				alert("保证金比例和保证金不能都为空");
				$("#MARGIN_SCALE").focus();
				return false;
			}
			var PREMIUM_SCALE = $("#PREMIUM_SCALE").val();//保险费比例
			var PREMIUM = $("#PREMIUM").val();//保险费
			if(PREMIUM_SCALE==''&&PREMIUM==''){
				alert("保险费比例和保险费不能都为空");
				$("#PREMIUM_SCALE").focus();
				return false;
			}
			var POUNDAGE_SCALE = $("#POUNDAGE_SCALE").val();//手续费比例
			var POUNDAGE = $("#POUNDAGE").val();//手续费
			if(POUNDAGE_SCALE==''&&POUNDAGE==''){
				alert("手续费比例和手续费不能都为空");
				$("#POUNDAGE_SCALE").focus();
				return false;
			}
			var POUNDAGE_RATE = $("#POUNDAGE_RATE").val();//管理费比例
			var MANAGER_COST = $("#MANAGER_COST").val();//管理费
			if(POUNDAGE_RATE==''&&MANAGER_COST==''){
				alert("管理费比例和管理费不能都为空");
				$("#POUNDAGE_RATE").focus();
				return false;
			}
			var REST_SCALE = $("#REST_SCALE").val();//其他费用比例
			var REST = $("#REST").val();//其他费用
			if(REST_SCALE==''&&REST==''){
				alert("其他费用比例和其他费用不能都为空");
				$("#REST_SCALE").focus();
				return false;
			}
			var PAY_DATE = $("#PAY_DATE").datebox('getValue');
			var TERM = $("#TERM").val();
			var POUNDAGE_RATE = $("#POUNDAGE_RATE").val();
			var INTEREST = $("#INTEREST").val();
			var PAY_TIME = $("#PAY_TIME").datebox('getValue');
			if(PAY_DATE==''){
				alert("还款日不能为空");
				$("#PAY_DATE").focus();
				return false;
			}
			if(PAY_DATE<PAY_TIME){
				alert("还款日不能小于放款日");
				$("#PAY_DATE").focus();
				return false;
			}
			if(TERM==''){
				alert("期数不能为空");
				$("#TERM").focus();
				return false;
			}
			var RATE_TYPE = $("#RATE_TYPE").val();//利率类型
			var RATE_SCALE = $("#RATE_SCALE").val();//浮动比例
			if(INTEREST==''){
				alert("利率不能为空");
				$("#INTEREST").focus();
				return false;
			}
			if(RATE_TYPE!='1'){
    			if(RATE_SCALE==''){
    				alert("利率不是固定类型,浮动比例不能为空");
    				$("#RATE_SCALE").focus();
    				return false;
    			}else{//如果利率不固定，则计算基准利率
					if(RATE_TYPE=='2'){
						var inster = parseFloat(INTEREST)+(parseFloat(INTEREST)*parseFloat(RATE_SCALE));
						$("#INTEREST").val(inster);
					}
					if(RATE_TYPE=='3'){
						var inster = parseFloat(INTEREST)-(parseFloat(INTEREST)*parseFloat(RATE_SCALE));
						$("#INTEREST").val(inster);
					}
					return true;
				}
			}
			if(RATE_TYPE=='1'){
				$("#RATE_SCALE").val("");
			}
			return true;
}

/**
 * 保存方案
 * @return
 */
function toSaveShame(){
	if(subNum()){
		$("#formaddupd").form('submit',{
			dataType:'json',
			success:function(json){
				var obj = eval('('+json+')');
				if(obj.flag==true){
					alert("融资方案录入成功");
					window.location.href = _basePath+"/rePayment/RePayment.action";
				}else {
					alert("融资方案录入失败");
					window.location.href =  _basePath+"/rePayment/RePayment.action";
				}
		}
		});
	}
}