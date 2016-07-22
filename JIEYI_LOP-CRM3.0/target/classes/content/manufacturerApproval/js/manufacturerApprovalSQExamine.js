/**
 * 陕汽厂商项目业务审批单页面 js
 * 作者:韩晓龙
 */
/**
 * 导出Excel
 */
function exportExcel(PROJECT_ID){
	//发送导出请求
	window.location.href=_basePath+"/manufacturerApproval/ManufacturerApproval!exportExcel.action?PROJECT_ID=" + PROJECT_ID;
}
