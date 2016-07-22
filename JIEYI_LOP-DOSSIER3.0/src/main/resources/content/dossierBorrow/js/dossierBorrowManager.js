$(function(){
	var DOSSIER_TYPE = $(".tab01-nav_active").attr("BStype");
    $('#pageTable').datagrid({
        view: detailview,
        columns:[[
		          {field:'PROJECT_CODE',align:'left',width:10,title:'项目编号',align:'center'},
		          {field:'CLIENT_NAME',align:'left',width:10,title:'客户名称',align:'center'},
		          {field:'BORROWNAME',align:'left',width:10,title:'借档人姓名',align:'center'},
		          {field:'BORROWIDCARD',align:'left',width:10,title:'借档人身份证号',align:'center'},
		          {field:'BORROWPHONE',align:'left',width:10,title:'借阅人联系电话',align:'center'},
		          {field:'RECIEVETYPE_NAME',align:'left',width:10,title:'接收方式',align:'center'},
		          {field:'BORROWADDRESS',align:'left',width:10,title:'借阅人地址',align:'center'},
		          {field:'POSTCODE',align:'left',width:10,title:'邮编',align:'center'},
		          {field:'FILEPATH_NAME',align:'left',width:10,align:'center',title:'借还承诺函',formatter:function(value,rowData,rowIndex){
		        	  return "<a href='javascript:void(0);' onclick=downLoad('"+rowData.DOSSIER_BORROWAPPID+"')>"+value+"</a>";
		          }},
		          {field:'RESTOREPURPOSE',align:'left',width:10,title:'借出用途',align:'center'}
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
	        href:_basePath+"/borrow/DossierBorrowManager!doShowBorrowDetailList.action?BORROW_APP_ID="+row.DOSSIER_BORROWAPPID,
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
	$("#BORROWDATE_BEGIN").datebox('setValue','');
	$("#BORROWDATE_END").datebox('setValue','');
	$("#BORROWNAME").val('');
	$("#BORROWIDCARD").val('');
	$("#FACTRESTOREDATE_BEGIN").datebox('setValue','');
	$("#FACTRESTOREDATE_END").datebox('setValue','');
}
function downLoad(id){
	window.location.href=_basePath+"/borrow/DossierBorrowManager!downLoad.action?BORROW_APP_ID="+id;
}