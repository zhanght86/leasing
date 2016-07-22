$(document).ready(function() {
    $("#ADDDBR").click(function(){
     $("#DBRLB").hide();
     $("#CDLXX").show();
     $("#DBRTJ").show();
  
    });
    $("#TODBR").click(function(){
        $("#DBRLB").show();
        $("#CDLXX").hide();
        $("#DBRTJ").hide();
        $("#ZRRXX").hide();
     
    });
    $("#DBRTYPE").change(function(){
        
        if($("#DBRTYPE").val()=='法人'){
        	$("#DBRTJ").show();
        	$("#ZRRXX").hide();
        }else if($("#DBRTYPE").val()=='个人'){
        	$("#DBRTJ").hide();
        	$("#ZRRXX").show();
        }
        
        
     
    });
    
});
function tofindData() {
	var NAME = $("input[name='NAME']").val();
	var PRO_NAME = $("input[name='PRO_NAME']").val();
	var PRO_CODE = $("input[name='PRO_CODE']").val();
	var CREATE_TIME1 = $("input[name='CREATE_TIME1']").val();
	var CREATE_TIME2 = $("input[name='CREATE_TIME2']").val();
	var PLATFORM_TYPE = $("select[name='PLATFORM_TYPE']").attr("selected",true).val();
	var STATUS = $("select[name='STATUS']").attr("selected",true).val();
	$('#CreditTab').datagrid('load', {
		"NAME" : NAME,
		"PRO_NAME" : PRO_NAME,
		"PRO_CODE" : PRO_CODE,
		"CREATE_TIME1" : CREATE_TIME1,
		"CREATE_TIME2" : CREATE_TIME2,
		"PLATFORM_TYPE" : PLATFORM_TYPE,
		"STATUS":STATUS
	});
}
function getValue(val, row) {
	var  code = row.PRO_CODE;
	var html="<a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-edit' onclick=toQueryCredit("
	+ row.ID+","
	+ row.CLIENT_ID+","
	+ "'"+code+"'"
	+ ")>查看</a>";
	if(row.STATUS!='项目签约'){
		html+="|  <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-remove' plain='true' onclick=toUpdateCredit("
	+ row.ID+","
	+ row.CLIENT_ID+","
	+ "'"+code+"')>修改</a>";
	}
	return  html;
//	+"| <a href='javascript:void(0)' class='easyui-linkbutton' iconCls='icon-add' plain='true' onclick='appendScheme(" + JSON.stringify(row) + ")'>追加方案</a>";

}

function appendScheme(row){
	if (row){
		 var PROJECT_ID=row.ID;
		 var PRO_CODE=row.PRO_CODE;
		 var PLATFORM_TYPE=row.PLATFORM_TYPE_TYPE;
		 top.addTab(PRO_CODE+"追加设备方案",_basePath+"/project/project!appendScheme.action?PROJECT_ID="+PROJECT_ID+"&PLATFORM_TYPE="+PLATFORM_TYPE);
	}else{
		$.messager.alert("请选择一个项目!");
	}
}

function toQueryCredit(value, type,code) {
	top.addTab(code+"资信查看", _basePath
			+ "/credit/Credit!queryCreditPage.action?PROJECT_ID="+value+"&CLIENT_ID="+type+"&tab=view"+"&date="+new Date().getTime());
}
function toUpdateCredit(value, type,code) {
	top.addTab(code+"资信修改", _basePath
			+ "/credit/Credit!updateCreditPage.action?PROJECT_ID="+value+"&CLIENT_ID="+type+"&tab=update"+"&date="+new Date().getTime());
}
function toUpdateGuarautorInfo(DBR_ID,GUARANTORTYPE) {
	top.addTab("资信担保人信息", _basePath
			+ "/credit/CreditGuara!toGuarantorUpdatePage.action?DBR_ID="+DBR_ID+"&GUARANTORTYPE="+GUARANTORTYPE+"&tab=update"+"&date="+new Date().getTime());
}
function toGuarantorViewInfo(DBR_ID,GUARANTORTYPE) {
	top.addTab("资信担保人信息", _basePath
			+ "/credit/CreditGuara!toGuarantorViewInfo.action?DBR_ID="+DBR_ID+"&GUARANTORTYPE="+GUARANTORTYPE+"&tab=update"+"&date="+new Date().getTime());
}
function submitForm(){
	//if(checking()){
		$("#doSaveGuara").attr("disabled","true");
		$(".FRLX").val($("#DBRTYPE").val());
		$('#FADATA').submit();
	//}else {
	//	alert("请填写必填项目");
	//}
		
}
function submitForm1(){
	//if(checking()){
		$("#doSaveGuaraZRR").attr("disabled","true");
		$(".ZRRLX").val($("#DBRTYPE").val());
		$('#ZRRDATA').submit();
	//}else {
	//	alert("请填写必填项目");
	//}
	
		
}
function submitUpdateForm(){
	//if(checking()){
		$("#doUpdateGuara").attr("disabled","true");
		$('#FADATA').submit();
	//}else {
	//	alert("请填写必填项目");
	//}
}
function submitUpdateForm1(){
	//if(checking()){
		$("#doUpdateGuaraZRR").attr("disabled","true");
		$('#ZRRDATA').submit();
	//}else {
	//	alert("请填写必填项目");
	//}
}
function checking() {
	var flag = true;
	
		$(".warmlegal").each(function() {
			if ($(this).val() == '' || $(this).val() == null ) {
				$(this).addClass("red");
				flag = false;
			} else {
				$(this).removeClass("red");
			}
		});
		$(".warmlegalDate").each(function() {
			if ($(this).datebox('getValue') == '' || $(this).datebox('getValue') == null ) {
				$(this).addClass("red");
				flag = false;
			}else {
				$(this).removeClass("red");
			}
			
		});
	
	return flag;
}


//start by wuyanfei 信誉评分操作
function scoreForXY(value,row,index){
	var ID=row.ID;
	var SUP_SHORTNAME=row.SUP_SHORTNAME;
	return "<a href=javascript:void(0) onclick=scoreAddTabForXY('"+ID+"','"+SUP_SHORTNAME+"')>"+value+"</a>";
}

function scoreAddTabForXY(ID,SUP_SHORTNAME){
	top.addTab("打分",_basePath+"/secuEvaluate/SecuEvaluate!doGradeScore.action?TYPE_ID="+ID+"&TYPE=ZXXY&SUP_SHORTNAME="+SUP_SHORTNAME);
}



