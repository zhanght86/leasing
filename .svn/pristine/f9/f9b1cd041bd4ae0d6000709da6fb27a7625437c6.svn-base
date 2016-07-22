$(document).ready(function(){
	 $('#bank_C_PageTable').datagrid({ 
         onLoadSuccess: function (data) {
		 	onChangeSelect();
         }
	 });
	 
	 $('#bank_C_PageTable').datagrid({ onClickRow:
         function () {
		 	onChangeSelect();
         }
     });
});


function dd(){
$("#bank_C_PageTable").datagrid({
		pagination:true,//是否分页 true为是
		rownumbers:true,//左侧自动显示行数
		fitColumns:true,
		fit:true,
		pageSize:50,
		pageList:[10,20,50,100,200,500,1000,1500],
		toolbar:'#pageForm',
		onClickCell:onClickCell,
		url:'rentWrite!Bank_C_PageAjax.action',
		onSelect: function(rowIndex, rowData){
			if(!$("input[type='checkbox']")[rowIndex+1].disabled){
				changeOne(rowIndex, rowData);
			}
			onChangeSelect();
		},
		onUnselect: function(rowIndex, rowData){
			onChangeSelect();
		},
		onSelectAll: function(rowIndex, rowData){
			onChangeSelect();
		},
		onUnselectAll: function(rowIndex, rowData){
			onChangeSelect();
		},
		onAfterEdit:function(rowIndex, rowData, changes){
			var MONEY_FLAG=rowData.MONEY_FLAG;
			var PAID_MONEY=changes.PAID_MONEY;
			var BEGINNING_MONEY=rowData.BEGINNING_MONEY;
			if(MONEY_FLAG=='1'){
				alert("该期租金本金为负值，不能不足额支付！");
				$('#bank_C_PageTable').datagrid('updateRow',{
					index: rowIndex,
					row: {
						PAID_MONEY: BEGINNING_MONEY
					}
				});
			}
			else{
				if(parseFloat(PAID_MONEY)-parseFloat(BEGINNING_MONEY)>0)
				{
					$('#bank_C_PageTable').datagrid('updateRow',{
						index: rowIndex,
						row: {
							PAID_MONEY: BEGINNING_MONEY
						}
					});
				}
			}
		},
		columns:[[
		          	{field:'ID',checkbox:true,width:100},
		          	{field:'LOCKNAME',title:'锁状态',width:15},
		          	{field:'PRO_CODE',title:'项目编号',width:15},
	                {field:'CUSTNAME',title:'客户名称',width:15},
		          	{field:'COMPANY_NAME',title:'厂商',width:15},
		          	{field:'SUP_NAME',title:'供应商',width:15},
		          	{field:'EQUIPMENINFOS',title:'租赁物',width:15},
		          	{field:'PAYLIST_CODE',title:'还款计划',width:15},
		          	{field:'BEGINNING_NAME',title:'款项名称',width:10},
		          	{field:'BEGINNING_NUM',title:'期次',width:5},
		          	{field:'BEGINNING_RECEIVE_DATA',title:'计划收取日期',width:10},
		          	{field:'BEGINNING_MONEY',title:'本次应付金额',width:15},
		          	{field:'PAID_MONEY',title:'本次实付金额',width:15, editor: {type:'text'}},
		          	{field:'ITEM_FLAG',hidden:true},
		          	{field:'CUST_ID',hidden:true},
		          	{field:'SUP_ID',hidden:true},
		          	{field:'MONEY_FLAG',hidden:true}
		         ]]
	});

}





function onChangeSelect()
{
	var datagridList=$("#bank_C_PageTable").datagrid('getSelections');
	
	var pages=$(".pagination-num").val();
	var rowss=$(".pagination-page-list").val();
	
	var BEGINNING_MONEYAll=0;
	var PAID_MONEYAll=0;
	var NUM=0;
	
	for(var i = 0; i < datagridList.length; i++)
	{
		var jj=datagridList[i].ROWNO-(pages-1)*rowss;
		if(!$("input[type='checkbox']")[jj].disabled){
			var BEGINNING_MONEY=datagridList[i].BEGINNING_MONEY;
			BEGINNING_MONEYAll=fomatFloat(accAdd(BEGINNING_MONEYAll,BEGINNING_MONEY),2);
			
			var PAID_MONEY=datagridList[i].PAID_MONEY;
			PAID_MONEYAll=fomatFloat(accAdd(PAID_MONEYAll,PAID_MONEY),2);
			NUM++;
		}
	}
	$("#FI_REALITY_MONEY").val(BEGINNING_MONEYAll);
	$("#FI_PROJECT_NUM").val(NUM);
	$("#FI_PAY_MONEY").val(PAID_MONEYAll);
}

function onChangeSelectAll(rows){
	var BEGINNING_MONEYAll=0;
	var PAID_MONEYAll=0;
	var NUM=0;
	
	for(var i = 0; i < rows.length; i++)
	{
		if(!$("input[type='checkbox']")[i].disabled){
			var BEGINNING_MONEY=rows[i].BEGINNING_MONEY;
			BEGINNING_MONEYAll=fomatFloat(accAdd(BEGINNING_MONEYAll,BEGINNING_MONEY),2);
			
			var PAID_MONEY=rows[i].PAID_MONEY;
			PAID_MONEYAll=fomatFloat(accAdd(PAID_MONEYAll,PAID_MONEY),2);
			NUM++;
		}
		else{
			$('#bank_C_PageTable').datagrid('unselectRow', i);
		}
	}
	$("#FI_REALITY_MONEY").val(BEGINNING_MONEYAll);
	$("#FI_PROJECT_NUM").val(NUM);
	$("#FI_PAY_MONEY").val(PAID_MONEYAll);
}

function changeOne(rowIndex, rowData){
	var DataList=$('#bank_C_PageTable').datagrid('getRows');
	if(rowIndex==DataList.length-1){
		;
	}
	else{
		var PAYLIST_CODE=rowData.PAYLIST_CODE;
		var payList_code1=DataList[rowIndex+1].PAYLIST_CODE;
		if(PAYLIST_CODE==payList_code1){
			$("input[type='checkbox']")[rowIndex+2].disabled = false;
		}
	}
}

function changeNotOne(rowIndex, rowData){
	var DataList=$('#bank_C_PageTable').datagrid('getRows');
	if(rowIndex==DataList.length-1){
		;
	}
	else{
		var PAYLIST_CODE=rowData.PAYLIST_CODE;
		for(var num=rowIndex+1;num<DataList.length;num++){
			var payList_code1=DataList[num].PAYLIST_CODE;
			if(PAYLIST_CODE==payList_code1){
				
				$("input[type='checkbox']")[num+1].disabled = true;
				 $('#bank_C_PageTable').datagrid('unselectRow', num);
			}
		}
	}
}

var disNum=0;


/********************************************************以下为设置单元格可修改***齐江龙********************************************************************************/
$.extend($.fn.datagrid.methods, {
	editCell : function(jq, param) {
		if(disNum==1){
			disNum=0;
			return jq.each(function() {
					var opts = $(this).datagrid('options');
					var fields = $(this).datagrid('getColumnFields', true).concat($(this).datagrid('getColumnFields'));
					for ( var i = 0; i < fields.length; i++) {
						var col = $(this).datagrid('getColumnOption', fields[i]);
						col.editor1 = col.editor;
						if (fields[i] != param.field) {
							col.editor = null;
						}
					}
					$(this).datagrid('beginEdit', param.index);
					for ( var i = 0; i < fields.length; i++) {
						var col = $(this).datagrid('getColumnOption', fields[i]);
						col.editor = col.editor1;
					}
			});
		}
	}
});

var editIndex = undefined;

function onClickCell(index, field) {
	if(!$("input[type='checkbox']")[index+1].disabled){
		disNum=1;
		$('#bank_C_PageTable').datagrid('endEdit', index);
		$('#bank_C_PageTable').datagrid('editCell', {index : index,field : field});
		editIndex=index;
		onChangeSelect();
	}
	else{

		if(editIndex != undefined){
			$('#bank_C_PageTable').datagrid('endEdit', editIndex);
			editIndex=undefined;
		}
	}
}


function toRefer(){
	$.ajax({		
		url:_basePath+"/rentWrite/rentWrite!CreateJoinFundDate.action",
	    type:'post',
	    dataType:'json',
	    success:function(json){
		 if(json.flag == true){
			 $.messager.alert("提示","同步成功！！");	
			//页面刷新
		 }else{
			 $.messager.alert("提示","同步失败！！");			 
		 }
		
		}
	});
}

/********************************************************以上为设置单元格可修改***齐江龙********************************************************************************/


