var code_ = 0;

jQuery(function(){
	$("#FIRSTMONEYHELP").dialog('close');
	calculate();
});

function startJbpmQzSq(){
	var Rows = $('#pageTable').datagrid('getRows');
	$("input[name='ROWS']").val(JSON.stringify(Rows));
	$("#mainForm").submit();
}



function startJbpmCrdieSq(){
	$.messager.confirm("询问","确定要生成支付表，生成支付表后不能在修改？",function(r){
		if(r){
			var Rows = $('#pageTable').datagrid('getRows');
			$("input[name='ROWS']").val(JSON.stringify(Rows));
			var Eq=[];
			$("#eqIDTR > tr").each(function (){
					var temp={};
					temp.PRODUCT_NAME=$(this).find("input[name='PRODUCT_NAME']").val();
					temp.CATENA_NAME=$(this).find("input[name='CATENA_NAME']").val();
					temp.SPEC_NAME=$(this).find("input[name='SPEC_NAME']").val();
					temp.AMOUNT=$(this).find("input[name='AMOUNT']").val();
					temp.EQ_ID=$(this).find("input[name='EQ_ID']").val();
					temp.UNIT_PRICE=$(this).find("input[name='UNIT_PRICE']").val();
					Eq.push(temp);
			 });
			var dataJson ={
					EqList:Eq
				};
			$("input[name='EQLIST']").val(JSON.stringify(dataJson));
			$("#mainForm").submit();
		}
	});
	
}

function calculate(){
	var PRO_ID = $("#PROJECT_ID").val();

	var YEAR_INTEREST=$("input[name='YEAR_INTEREST']").val();
	
	var FINANCE_TOPRIC=$("input[name='FINANCE_TOPRIC']").val();//融资额
	
	var LEASE_TERM=$("input[name='LEASE_TERM']").val();
	
	var PAYCOUNTOFYEAR=$("input[name='PAYCOUNTOFYEAR']").val();
	
	var PAY_WAY=$("input[name='PAY_WAY']").val();
	
	var START_DATE_CHANGE=$("input[name='START_DATE_CHANGE']").val();
	
	var REPAYMENT_DATE_CHANGE=$("input[name='REPAYMENT_DATE_CHANGE']").val();
	
	var LXTQSQ=$("input[name='LXTQSQ']").val();
	
	var DISCOUNT_MONEY=$("input[name='DISCOUNT_MONEY']").val();
	
	var DISCOUNT_TYPE=$("input[name='DISCOUNT_TYPE']").val();

	var data_ = "PRO_ID="+PRO_ID+"&annualRate="+YEAR_INTEREST+"&_leaseTerm="+LEASE_TERM+"&&residualPrincipal="+FINANCE_TOPRIC+"&_payCountOfYear="+PAYCOUNTOFYEAR+"&pay_way="+PAY_WAY+"&date="+START_DATE_CHANGE+"&date1="+REPAYMENT_DATE_CHANGE+"&LXTQSQ="+LXTQSQ+"&DISCOUNT_MONEY="+DISCOUNT_MONEY+"&DISCOUNT_TYPE="+DISCOUNT_TYPE;
	if (code_ == 8) {
		var editData = getEditRows();
		if(!editData){//如果确认框选择了false则不让往下走
			return false;
		}
		data_ = data_ + "&EditRows="+JSON.stringify(editData);	
	}
	$.ajax({
        type: "post",
        dataType: "json",
        url: _basePath + "/pay/PayTask!quoteCalculateTest.action",
        data: data_,
        success: function(msg){
            var data = msg.data.ln;
//            alert(totalColumn($(data), "zj"));
            var footer_ = [{
                PAY_DATE: "合计：",
                zj: totalColumn($(data), "zj"),
                bj: totalColumn($(data), "bj"),
                lx: totalColumn($(data), "lx")
            }];
            document.getElementById("total_month_price").innerHTML="￥" + formatMoney(totalColumn($(data), "zj"));
            $("#MONTH_PRICE").val(totalColumn($(data), "zj"));
            
            if(LXTQSQ=='2'){
            	 document.getElementById("total_gdlx_price").innerHTML="￥" + formatMoney(msg.data.GDLX_PRICE);
            	 document.getElementById("total_gdlx_price1").innerHTML="￥" + formatMoney(msg.data.GDLX_PRICE);
            	 $("#GDLX").val(msg.data.GDLX_PRICE);
            }
            else{
            	 $("#GDLX").val(0);
            }
            var data = {
                flag: msg.flag,
                total: data.length,
                rows: data,
                footer: footer_
            }
			
			$(".dataDiv").show();
            
            if(msg.data.code==8){
				code_ = 8;
				$('#pageTable').datagrid({fit:true,fitColumns:true,rownumbers:true,singleSelect:true,showFooter:true,
					onClickRow: function(rowIndex, rowData){
							onClickRow_(rowIndex, rowData);
		                }
				});
			}else{
				$('#pageTable').datagrid({fit:true,fitColumns:true,rownumbers:true,singleSelect:true,showFooter:true});
			}
			$('#pageTable').datagrid("loadData", data);
			
		
			$("#pageTable").datagrid('selectRow',0);
			var row=$("#pageTable").datagrid('getSelected');
			var foot=$("#pageTable").datagrid('getFooterRows');
			
			$("#pageTable").datagrid('unselectRow',0);
			
        }
    })
}
//不等额修改行
var editIndex = undefined;
function onClickRow_(index, rowData){
	var grid_data = $('#pageTable').datagrid('getData');
    if (editIndex != index) {
        if (endEditing()) {
            $('#pageTable').datagrid('selectRow', index).datagrid('beginEdit', index);
            editIndex = index;
        }
        else {
            $('#pageTable').datagrid('selectRow', editIndex);
        }
    }
    
}
function endEditing(){
    if (editIndex == undefined) {
        return true
    }
    if ($('#pageTable').datagrid('validateRow', editIndex)) {
        $('#pageTable').datagrid('endEdit', editIndex);
		/*手动修改了本金和利息之后自动改变相应的租金值
		var data_ = $('#pageTable').datagrid('getRows');
        var rows_ =data_[editIndex];
		if($.trim(rows_.bj).length<=0){
			rows_.bj = 0;
		}
		if($.trim(rows_.lx).length<=0){
			rows_.lx = 0;
		}
        //var rows_last =data_[editIndex-1];
		rows_.zj = formatNumber(parseFloat(rows_.bj)+parseFloat(rows_.lx),'0.00');
		rows_.PMTzj = formatNumber(parseFloat(rows_.bj)+parseFloat(rows_.lx),'0.00');
		//rows_.sybj = formatNumber(parseFloat(rows_last.sybj) - parseFloat(rows_.bj),'0.00');
		$('#pageTable').datagrid('updateRow',{index:editIndex,row:rows_});
		*/
		var pay_project_model=$("#pay_project_model").val();
		var data_ = $('#pageTable').datagrid('getRows');
        var rows_ =data_[editIndex];
        //资产包
        if(pay_project_model=='6'){
        	//周期
        	var lease_period=$("#lease_period").val();
        	//还款政策
        	var sybj;
        	if(editIndex!=0){
        		sybj=data_[editIndex-1].sybj;
        	}else{
        		sybj=$("#FINANCE_TOPRIC").val();
        	}
        	var YEAR_INTEREST=$("#YEAR_INTEREST").val();
        	if(rows_.zcbxjl==null||rows_.zcbxjl==''){
        		rows_.zcbxjl=rows_.bj;
        	}
        	rows_.bj=formatNumber(parseFloat(rows_.zcbxjl)*parseFloat($("#ZKL").val())/100,'0.00');
        	if(editIndex!=0){
        		rows_.lx=formatNumber(parseFloat(sybj)*parseFloat(YEAR_INTEREST)*parseFloat(lease_period)/1200,'0.00');
        	}else{
        		if($("#PAY_WAY").val()=='2'){
        			rows_.lx='0';
        		}else{
	        		var UPDATE_DELIVER_DATE=$("#UPDATE_DELIVER_DATE").datebox('getValue');
	        		var PAY_DATE=rows_.PAY_DATE;
	        		var day=(new Date(PAY_DATE).getTime()-new Date(UPDATE_DELIVER_DATE).getTime())/(60*60*24*1000);
	        		rows_.lx=formatNumber(parseFloat(sybj)*parseFloat(YEAR_INTEREST)*parseFloat(day)/36500,'0.00');
        		}
        	}
        	rows_.sybj=formatNumber(parseFloat(sybj)-parseFloat(rows_.bj),'0.00');
        	rows_.zj=formatNumber(parseFloat(rows_.bj)+parseFloat(rows_.lx),'0.00');
        	$('#pageTable').datagrid('updateRow',{index:editIndex,row:rows_});
        }else{
			if($.trim(rows_.zj).length<=0){
				rows_.zj = 0;
			}
	        //var rows_last =data_[editIndex-1];
			rows_.bj = formatNumber(parseFloat(rows_.zj) - parseFloat(rows_.lx),'0.00');
			//rows_.sybj = formatNumber(parseFloat(rows_last.sybj) - parseFloat(rows_.bj),'0.00');
			$('#pageTable').datagrid('updateRow',{index:editIndex,row:rows_});
        }
        editIndex = undefined;
        return true;
    }
    else {
        return false;
    }
}

//不等额的时候修改了的行数
function getEditRows(){
	//var rows = $('#pageTable').datagrid('getChanges');
	var resultData = new Array();
	var rows2 = $('#pageTable').datagrid('getChanges');
	var allData = $('#pageTable').datagrid("getRows");
	$(allData).each(function(){
		var flag = false;
		if(this.lock=="yes"){
			for(var i=0;i<rows2.length;i++){
				if(this.qc == rows2[i].qc){
					flag = true;
				}
			}
			if(!flag){
				resultData.push(this);
			}
		}
	})
	$(rows2).each(function(){
		resultData.push(this);
	})
	//console.info(resultData);
	if(!confirm("您共修改了"+resultData.length+"期租金,确认往下执行吗?")){
		return false;
	}
	//alert(rows2.length+' rows are changed!'); 
	return resultData;
}

function addDot(){
	$('#pageTable').datagrid("resize");
}

$(window).resize(function() {
	setTimeout('addDot()',500);
})

/**
 * 除法
 * @param arg1
 * @param arg2
 * @return
 */
function accDiv(arg1, arg2) {
    var t1 = 0, t2 = 0, r1, r2;
    try { t1 = arg1.toString().split(".")[1].length ;} catch (e) { }
    try { t2 = arg2.toString().split(".")[1].length ;} catch (e) { }
    with (Math) {
        r1 = Number(arg1.toString().replace(".", ""));
        r2 = Number(arg2.toString().replace(".", ""));
        return (r1 / r2) * pow(10, t2 - t1);
    }
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

//季度不规则批量修改
function batchUpdate(){
	var resultData = getEditRows();
	var allData = $('#pageTable').datagrid("getRows");
	var counter = 1;
	for (var i = 0; i < allData.length; i++) {
		var data_ = allData[i];
		for (var j = 0; j < resultData.length; j++) {
			var qc = parseInt(resultData[j].qc) + counter * 3;
			if (parseInt(data_.qc) == qc) {
				data_.lock = 'yes';
				data_.zj = resultData[j].zj;
				break;
			}
			var qc = parseInt(resultData[j].qc);
			if (parseInt(data_.qc) == qc) {
				data_.lock = 'yes';
				break;
			}
			
		}
		if ((i+1) % 3 == 0&&i!=2) {
			counter = counter + 1;
		}
	}
	console.info(allData);
	var data2 = {
		total: allData.length,
		rows: allData
	}
	$('#pageTable').datagrid("loadData", data2);
	calculate();
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

function QUERYZCBModel(){
	var PROJECT_NAME=$("#PROJECT_NAME").val();
	top.addTab("资产包明细",_basePath+"/project/project!QUERYZCBModel.action?PROJECT_NAME="+encodeURI(PROJECT_NAME));
}
