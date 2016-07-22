/**
 * 查询模糊
 * @author 杨雪
 * @return
 */
function toSeacher() {
	var ORG_NAME = $("input[name='ORG_NAME']").val();
	var PRO_CODE = $("input[name='PRO_CODE']").val();
	var EQUIPMENINFOS = $("input[name='EQUIPMENINFOS']").val();
	var COMPANY_NAMES = $("input[name='COMPANY_NAMES']").val();
	var CLIENT_NAME = $("input[name='CLIENT_NAME']").val();
	$('#pageTable').datagrid('load', {
		"ORG_NAME" : ORG_NAME,
		"PRO_CODE" : PRO_CODE,
		"EQUIPMENINFOS" : EQUIPMENINFOS,
		"COMPANY_NAMES" : COMPANY_NAMES,
		"CLIENT_NAME" : CLIENT_NAME
	});
}

/**
 * 清空查询数据
 * @author 杨雪
 * @return
 */
function clearQuery(){
	$(".paramData").each(function(){
		$(this).val("");
	});
}

/***********************************************************以下为申请操作**********************************************************************************/

/**
 * 保存申请数据
 */
function toSubmtit(){
	var detailData = $("#pageTable").datagrid('getSelections');
	$.messager.confirm('提示','确定保存数据?',function(r) {
			if(r){
				if($("#RE_DATE").datebox('getValue')==null||$("#RE_DATE").datebox('getValue')==""){
					return alert("请填写付款日期");
				}else if(detailData.length<=0){
					return alert("请填写退款项目");
				}else {
					var data = {};
					data["getBaseData"] = getBaseData();
					data["getDetailData"] = getDetailData();
					$.ajax({
						url:_basePath+"/retentionRefund/RetentionRefund!doSaveApp.action",
					    type:'post',
					    data:'data='+JSON.stringify(data),
					    dataType:'json',
					    success:function(json){
							if(json.flag==true){
								$.messager.alert("留购价退款申请","留购价退款申请成功！！");
							}else{
								$.messager.alert("留购价退款申请","留购价退款申请失败！！");
							}
							//页面刷新
					    	window.location.href = _basePath+"/retentionRefund/RetentionRefund!toMgRetention.action";
						}
					});
				}				
			}
		});
}

/**
 * 计算应收金额， 项目数量
 * @auth yx
 * @date 2013-10-11
 * @return
 */
function onChangeSelect()
{
	var datagridList=$("#pageTable").datagrid('getSelections');
	var RE_MONEYAll=0;
	var NUM=0;
	for(var i = 0; i < datagridList.length; i++)
	{
		datagridList[i].PROJECT_ID.checkbox = true;
	
		var RE_MONEY=datagridList[i].BEGINNING_MONEY;
		RE_MONEYAll=accAdd(RE_MONEYAll,RE_MONEY);
		NUM++;
	}
	$("#RE_PROJECT_COUNT").val(NUM);//项目数量
	$("#RE_MONEY").val(RE_MONEYAll);//应收
}

/**
 * 获取申请单基础数据
 * @auth yx
 * @date 2013-09-28
 * @return
 */
function getBaseData(){
	var getBaseData = {};
	var RE_DATE = $("#RE_DATE").datebox('getValue');
	getBaseData["RE_DATE"]  = RE_DATE;
	$("#baseData tr").each(function(){
		getBaseData["RE_MONEY"] = $(this).find($("input[name=RE_MONEY]")).val();		
		getBaseData["RE_PROJECT_COUNT"]  = $(this).find($("input[name=RE_PROJECT_COUNT]")).val();
		getBaseData["ORG_ID"]  = $(this).find($("input[name=ORG_ID]")).val();
		getBaseData["ORG_NAME"]  = $(this).find($("input[name=ORG_NAME]")).val();
	});

	return getBaseData;
}

/**
 * 获取明细信息
 * @return
 */
function getDetailData(){
	var getDetailData = [];
	var detailData = $("#pageTable").datagrid('getSelections');
	for(var i = 0; i<detailData.length; i++) {
		var temp = {};
		temp.PRO_ID = detailData[i].PROJECT_ID;
		temp.PRO_CODE = detailData[i].PRO_CODE;
		temp.CLIENT_ID = detailData[i].CLIENT_CODE;
		temp.CLIENT_NAME = detailData[i].CLIENT_NAME;
		temp.REFUND_REALITY_MONEY = detailData[i].BEGINNING_MONEY;
		getDetailData.push(temp);
	}
	return getDetailData;//JSON.stringify(getDetailData);//encodeURI(JSON.stringify(getDetailData));
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