var _isNotLoad = true;
function toSeacher() {
	var dataUrl = 'Funds_Whole!queryFundsWholeProjectDate.action';
	var searchParams = getFromData('#pageForm');
	if(_isNotLoad){
		$('#table1').datagrid({
			url : dataUrl,
			queryParams : {"searchParams" : searchParams}
		});
		_isNotLoad = false;
	}else{
		$('#table1').datagrid('load', {"searchParams" : searchParams});
	}
}


//操作
function operation(value, rowData,index){
	if(value=="0"){
		return "<a href='javascript:void(0);'  onclick='updateProjectFlag(0,"+index+")'>剔除</a>";
	}else if(value=="1"){
		return "<a href='javascript:void(0);'  onclick='updateProjectFlag(1,"+index+")'>恢复</a>";
	}
}

//记录剔除数据ID
var IDS = {};

//参数：prop = 属性，val = 值
function createJson(key, val) {
 if(typeof val === "undefined") {// 如果 val 被忽略
     delete IDS[key];// 剔除属性
 }
 else {
	 IDS[key] = val;// 添加 或 修改
 }
}

function updateProjectFlag(FLAG,index){
	$('#table1').datagrid('selectRow', index);
	var row = $('#table1').datagrid('getSelected');
	if(FLAG=="1"){//恢复 加 
		row.FLAG = 0;
		if(row.STATUS == "已签订"){
			$('#LEASE_TOPRIC').val(jia($('#LEASE_TOPRIC').val(),row.LEASE_TOPRIC));
		}else{
			$('#FINANCE_TOPRIC').val(jia($('#FINANCE_TOPRIC').val(),row.FINANCE_TOPRIC));
		}
		createJson(row.PRO_CODE);
	}else{//剔除 减
		row.FLAG =1;
		if(row.STATUS == "已签订"){
			$('#LEASE_TOPRIC').val(jian($('#LEASE_TOPRIC').val(),row.LEASE_TOPRIC));
		}else{
			$('#FINANCE_TOPRIC').val(jian($('#FINANCE_TOPRIC').val(),row.FINANCE_TOPRIC));
		}
		createJson(row.PRO_CODE,row.PRO_CODE);
	}
	$('#table1').datagrid('refreshRow', index);
	wholeMoney();
}
function wholeMoney(){
	var arr = ["RENT_MONEY","RENT_SCALE","FIRST_MONEY","FIRST_SCALE","FINANCE_TOPRIC","FINANCE_SCALE","LEASE_TOPRIC","LEASE_SCALE","SELF_MONEY"] ;
	for(var i=0;i<arr.length;i++){
		var exp = $('#'+arr[i]).val();
		if(exp == null || exp =="undefined" || exp == ""){
			alert("金额不能为空！");
			$('#'+arr[i]).val("0");
			return ;
		}
		if(isNaN(exp)){
			alert("请填写正确数字！");
			return ;
		}
	}
	var RENT_MONEY = ceng($('#RENT_MONEY').val(),cu($('#RENT_SCALE').val(),100));
	var FIRST_MONEY = ceng($('#FIRST_MONEY').val(),cu($('#FIRST_SCALE').val(),100));
	var FINANCE_TOPRIC = ceng($('#FINANCE_TOPRIC').val(),cu($('#FINANCE_SCALE').val(),100));
	var LEASE_TOPRIC = ceng($('#LEASE_TOPRIC').val(),cu($('#LEASE_SCALE').val(),100));
	var SELF_MONEY = jia($('#SELF_MONEY').val(),0);
	
	$('#WHOLE_MONEY').val(jia((RENT_MONEY+FIRST_MONEY+SELF_MONEY-FINANCE_TOPRIC-LEASE_TOPRIC),0));
	
}

// 统计应收 应付 逾期数据
function editWholeInfo(){
	jQuery.ajax({
		type:"post",
		async: true,
		url:_basePath+"/Funds_Whole/Funds_Whole!queryFundsWholeInfo.action",
		data:"searchParams=" + getFromData('#pageForm'),
		dataType:"json",
		success:function(json){
			$('#RENT_MONEY').val(json.data.RENT_MONEY);
			$('#FIRST_MONEY').val(json.data.FIRST_MONRY);
			$('#FINANCE_TOPRIC').val(json.data.FINANCE_TOPRIC);
			$('#LEASE_TOPRIC').val(json.data.LEASE_TOPRIC);
			$('#WHOLE_MONEY').val(json.data.WHOLE_MONEY);
			$('#OVERDUE').val(json.data.OVERDUE);
			wholeMoney();
		}
	});
}
//保存统筹数据
function saveWhole (){
	
	var arr = ["RENT_MONEY","RENT_SCALE","FIRST_MONEY","FIRST_SCALE","FINANCE_TOPRIC","FINANCE_SCALE","LEASE_TOPRIC","LEASE_SCALE","SELF_MONEY"] ;
	for(var i=0;i<arr.length;i++){
		var exp = $('#'+arr[i]).val();
		if(exp == null || exp =="undefined" || exp == ""){
			alert("金额不能为空！");
			$('#'+arr[i]).val("0");
			return ;
		}
		if(isNaN(exp)){
			alert("请填写正确数字！");
			return ;
		}
	}
	var PRO_IDS = "'0'";
	for(var key in IDS){
		PRO_IDS += ",'"+key+"'";
	}
	jQuery.ajax({
		type:"post",
		async: true,
		url:_basePath+"/Funds_Whole/Funds_Whole!saveFundsWholeInfo.action",
		data:"searchParams=" + getFromData('#pageForm')+"&PRO_IDS="+PRO_IDS,
		dataType:"json",
		success:function(json){
			if(json.flag){
				alert("保存成功！");
				top.updateWhole();
				top.closeTab("统筹测算");
			}else{
				alert("保存失败，请稍后重试！");
			}
		}
	});
	
}
