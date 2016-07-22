$(document).ready(function(){
	$("#dialogProduct").dialog('close');
	$("#pageTable").datagrid({
		url:_basePath+"/project/project!toDepositConfirm.action",
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		singleSelect:false,
		fit : true,
		pageSize:20,
		toolbar:'#pageForm',
        frozenColumns:[[
					{field:'D_ID',title:'操作',width:220,align:'center',checkbox:true}
                ]],
		columns:[[
		    {field:'TOTHEPEOPLE',title:'来款人',width:150,align:'center'},
		   
	      	{field:'TOTHETYPE',title:'来款类型',width:100,align:'center'},
	      	
	      	{field:'TOTHEMONEY',title:'金额',width:110,align:'center'},
	      	
	      	{field:'MONEYMODE',title:'来款方式',width:130,align:'center'},

	      	{field:'STATUS',title:'来款状态',width:130,align:'center'},	
	      	
	      	{field:'TOTHETIME',title:'来款日期',width:100,align:'center'},
	      	
	      	{field:'VOUCHER',title:'凭证编号',width:100,align:'center'},
	      	
	           {field : 'PICTNAME',
				  title : '图片',
				  width : 150,
					formatter : function(value,
							rowData, rowIndex) {
						if(rowData.PICTNAME != null && rowData.PICTNAME != '') {
							var picStr = _basePath
							+ "/leeds/cust_info_input/CustInfoInput!readPic.action?path="
							+ rowData.PICTPATH;
							
							return '<a href="'+picStr+'" class="lightbox" title=""><img src="'+picStr+'" onclick="document.getElementById(\"div'+rowData.D_ID+'\").style.display=\"\""  width="100" height="40" alt="" /></a>';
			      		 } else {
			      			 return "";
			      		 }
					}
				},
	      	
	      	{field:'CUST_NAME',align:'center',title:'客户名称',width:130,formatter:function(value,rowData,rowIndex){
	        	  return "<a href='#' onclick='toViewCust("+ JSON.stringify(rowData) +")'>"+rowData.CUST_NAME+"</a>";
	          }},
	          {field:'CUST_TYPE',hidden:true},
		      	{field:'CUST_ID',hidden:true},
	      	{field:'LEASE_CODE',title:'合同编号',width:150,align:'center'},
	      	
	      	{field:'PRO_CODE',title:'项目编号',align:'center',width:130},
	      	
	      	{field:'SHOP_NAME',title:'门店名称',width:200,align:'center'},
	      	
	      	{field:'POSNO',title:'POS机终端号',width:130,align:'center'},
	      	
	      	{field:'FB_NAME',title:'分部',width:200,align:'center'},
	      	
	      	{field:'SHOP_CODE',title:'门店编号',width:90,align:'center'}
	      	] ],
			//zheba
			onLoadSuccess: function (data) {
				$(".lightbox").lightbox({
					fitToScreen: true,
					imageClickClose: false
				});
			}
	});
});



function toViewCust(row) {
	var value=row.CUST_ID;
	var type=row.CUST_TYPE;
	top.addTab("查看客户明细", _basePath
			+ "/customers/Customers!toViewCustInfoMain.action?CLIENT_ID=" + value
			+ "&TYPE=" + type + "&tab=view");

}

function tabRightsShow(row,value){
	if (row){
		 var TTT_ID=row.TTT_ID;
		 var JM_ID  =row.JM_ID;
		 var projectId = row.ID;
		 var title = row.CUST_NAME+"."+projectId;
		 top.addTab(title,_basePath+"/bpm/Task!tabRightsShow.action?MEMO_ID="+JM_ID+"&JBPM_ID="+encodeURI(TTT_ID)
				 +"&STATUS="+encodeURI(row.STATUS)+"&PROJECT_ID="+encodeURI(projectId));
	}
}

function rollback(){
	var checkedList=$('#pageTable').datagrid('getChecked');
	if(checkedList.length<=0)
	{
		alert("请选择认款的数据！");
		return false;
	}
	
	var IDS="";
	var selectData = [];
	for(var i = 0; i < checkedList.length; i++)
	{
		if(i==0)
		{
			IDS=checkedList[i].D_ID;
		}
		else
		{
			IDS=IDS+","+checkedList[i].D_ID;
		}
	}
	$("#IDS").val(IDS);
	$("#confirm").submit();
}

function confirm(STATUS){
	var checkedList=$('#pageTable').datagrid('getChecked');
	var IDS="";
	var selectData = [];
	for(var i = 0; i < checkedList.length; i++)
	{
		if(i==0)
		{
			IDS=checkedList[i].D_ID;
		}
		else
		{
			IDS=IDS+","+checkedList[i].D_ID;
		}
		
	}
	jQuery.ajax({
		url: "project!confirmMoney.action",
		data: "IDS="+encodeURI(IDS)+"&STATUS="+STATUS,
		dataType: "json",
		success: function(result){
			if (result.flag==false){
                jQuery.messager.alert("提示",result.msg);
            }else{
            	seach();
			} 
		}
	});
}

//下载资料文件
function downloadFile(row) {
	var PICTPATH = row.PICTPATH;	
	window.location.href = _basePath
			+ "/project/project!downPaperFile.action?PICTPATH="
			+ PICTPATH;
}
/**
 * 清空按钮
 * @return
 */
function emptyData(){
	$('#pageForm').form('clear');
	$(".paramData").each(function(){
		$(this).val("");
	});
}