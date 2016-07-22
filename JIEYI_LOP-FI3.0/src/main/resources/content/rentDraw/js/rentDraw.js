$(document).ready(function(){
				$("#pageTable").datagrid({
					url:_basePath+"/rentDraw/RentDraw!getRentDrawPage.action",
					pageSize:20,
					fit : true,
					pagination : true,
					rownumbers : true,
					singleSelect : true,
					toolbar : "#pageForm",
                    columns:[[
						{field:'detail',width:150,title:'批次',align:'center',formatter:detail},
						{field:'PERSONTOTAL',width:100,title:'代扣客户总数',align:'center'},
						{field:'TOTALPRICE',width:100,title:'代扣总金额',align:'center'},
						{field:'NORENTCOUNT',width:100,title:'未扣数量',align:'center'},
						{field:'SUCCCOUNT',width:100,title:'成功数量',align:'center'},
						{field:'FAILCOUNT',width:100,title:'失败数量',align:'center'},
						{field:'DRAWINGCOUNT',width:100,title:'扣款中数量',align:'center'}
                    ]]
                });
			});

function detail(value,rowData){
	return "<a href='javascript:void(0)' onclick='rentDetail("+rowData.BATCH+")'>"+rowData.BATCH+"</a>";
}


function rentDetail(batch){
	top.addTab("租金划扣明细",_basePath+"/rentDraw/RentDraw!toMgshowDetail.action?BATCH="+batch);
}