$(function(){
    $('#pageTable').datagrid({
        view: detailview,
        columns:[[
                  {field:'CLIENT_NAME',align:'left',width:10,title:'客户名称',align:'center'},
		          {field:'PROJECT_CODE',align:'left',width:10,title:'项目编号',align:'center'},
		          {field:'RECIEVEMAN',align:'left',width:10,title:'接收人姓名',align:'center'},
		          {field:'RECIEVECODE',align:'left',width:10,title:'接收人身份证号',align:'center'},
		          {field:'ACCEPTPHONE',align:'left',width:10,title:'接收人联系电话',align:'center'},
		          {field:'POSTWAY',align:'left',width:10,title:'接收方式',align:'center'},
		          {field:'HANDOVERDATE',align:'left',width:10,title:'移交时间',align:'center'},
//		          {field:'BORROWADDRESS',align:'left',width:10,title:'接收人地址'},
//		          {field:'POSTCODE',align:'left',width:10,title:'邮编'},
//		          {field:'FILEPATH_NAME',align:'left',width:10,title:'所有权证书',formatter:function(value,rowData,rowIndex){
//		        	  return "<a href='javascript:void(0);' onclick=downLoad('"+rowData.DOSSIER_BORROWAPPID+"')>"+value+"</a>";
//		          }},
		          {field:'REMARKS',align:'left',width:10,title:'备注',align:'center'}
//		          formatter:function(value,rowData,rowInex){
//		        	  return "<font title='"+value.substring(0,10)+"'>"+value+"</font>";
//		          }}
		          ]],
        detailFormatter:function(index,row){
    	return '<table class="table_02" cellpadding="0" cellspacing="0" id="ddv-' + index + '" style="padding:5px;"></table>';
        },
        //展开层
        onExpandRow: function(index,row){
	        $('#ddv-'+index).panel({
	        	width:'100%',
			border:false,
			cache:false,
	        height:'auto',
	        href:_basePath+"/Transfer/DossierTransferManager!doShowTransferDetailList.action?TRANSFER_APP_ID="+row.TRANSFER_APP_ID+"&PROJECT_CODE="+row.PROJECT_CODE+"&PAYLIST_CODE="+row.PAYLIST_CODE,
	        onLoad:function(){
		        $('#pageTable').datagrid('fixDetailRowHeight',index);
	        }
	        });
        }
        });
});

function se(){
	$('#pageTable').datagrid('load', {"param":getFromData("#pageForm1")});
}
function clean(){
	$("#CLIENT_NAME").val('');
	$("#PROJECT_CODE").val('');
	$("#HANDOVERDATE_START").datebox('setValue','');
	$("#HANDOVERDATE_END").datebox('setValue','');
	$("#RECIEVEMAN").val('');
	$("#RECIEVECODE").val('');
}
//function downLoad(id){
//	window.location.href=_basePath+"/borrow/DossierBorrowManager!downLoad.action?BORROW_APP_ID="+id;
//}