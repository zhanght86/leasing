
function toVehicleLocation(){
	var vehs = $("#pageTable").datagrid('getSelections');
	if(vehs.length<1){
		return alert('请选择数据');
	}
	var transData = {clientCodes:[],
//			contractCodes:[],
//			payCodes:[],
//			supplierCodes:[],
			vehEquipmentIds:[]};
	
	for(var i=0;i<vehs.length;i++){
		transData.clientCodes.push(vehs[i].CUST_ID);
//		transData.contractCodes.push(vehs[i].LEASE_CODE);
//		transData.payCodes.push(vehs[i].PAYLIST_CODE);
//		transData.supplierCodes.push(vehs[i].SUP_ID);
		transData.vehEquipmentIds.push(vehs[i].ID);
	}
	console.debug(JSON.stringify(transData));
	top.addTab("查看车辆位置","http://jt808.che08.com/rzzl/indexpingqiang.jsp" +
			"?mid=1699&transData="+encodeURIComponent(JSON.stringify(transData)));
}
