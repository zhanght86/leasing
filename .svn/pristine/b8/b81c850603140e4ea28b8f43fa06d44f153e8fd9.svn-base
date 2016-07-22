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
		onSelect: function(rowIndex, rowData){
			if(!$("input[type='checkbox']")[rowIndex+1].disabled){
				changeOne(rowIndex, rowData);
			}
			onChangeSelect();
		},
		onUnselect: function(rowIndex, rowData){
			if(!$("input[type='checkbox']")[rowIndex+1].disabled){
				changeNotOne(rowIndex, rowData);
			}
			onChangeSelect();
		},
		onAfterEdit:function(rowIndex, rowData, changes){
			var PAID_MONEY=changes.PAID_MONEY;
			var BEGINNING_MONEY=rowData.BEGINNING_MONEY;
			if(parseFloat(PAID_MONEY)-parseFloat(BEGINNING_MONEY)>0)
			{
				$('#bank_C_PageTable').datagrid('updateRow',{
					index: rowIndex,
					row: {
						PAID_MONEY: BEGINNING_MONEY
					}
				});
			}
		},
		onLoadSuccess: function(data){//加载完毕后获取所有的checkbox遍历
            if (data.rows.length > 0) {
            	var payCode="";
                //循环判断操作为新增的不能选择
                for (var i = 0; i < data.rows.length; i++) {
                	if(i==0){
                		payCode=data.rows[i].PAYLIST_CODE;
                	}
                	else{
                		var payCode1=data.rows[i].PAYLIST_CODE;
                		if(payCode==payCode1){
                			//根据operate让某些行不可选
                            $("input[type='checkbox']")[i + 1].disabled = "disabled";
                		}
                		else{
                			payCode=data.rows[i].PAYLIST_CODE;
                		}
                        
                	}
                	
                }
            }
            $(".datagrid-header-check").children("input[type='checkbox']").eq(0).attr("disabled", true);
        },
        onClickRow: function(rowIndex, rowData){
            //加载完毕后获取所有的checkbox遍历
            $("input[type='checkbox']").each(function(index, el){
                //如果当前的复选框不可选，则不让其选中
                if (el.disabled == true) {
                	 $('#bank_C_PageTable').datagrid('unselectRow', index - 1);
                }
            })
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
		          	{field:'PAID_MONEY',title:'本次实付金额',width:15},
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

