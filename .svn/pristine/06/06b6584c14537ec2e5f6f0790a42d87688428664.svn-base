$(function(){
    $('#toMgProject').datagrid({
        view: detailview,
        columns:[[{field:'PROJECT_STATUS',align:'center',width:5,formatter:function (value,rowData,rowIndex){
        				if(value==0){
        					return "<div class='icon-yellow'>&nbsp;</div>";
        				}else if(value>=1||value==-1){
        					return "<div class='icon-green'>&nbsp;</div>";
        				}else{
        					return "<div class='icon-red'>&nbsp;</div>";
        				}
        			}},
//		          {field:'PROJECT_STATUS',align:'left',width:10,title:'项目状态'},
		          {field:'ORGAN_NAME',align:'left',width:15,title:'融资机构'},
		          {field:'BAILOUTWAY_NAME',align:'left',width:15,title:'融资方式'},
		          {field:'PROJECT_NAME',align:'left',width:15,title:'项目名称'},
		          {field:'PROJECT_CODE',align:'left',width:10,title:'项目编号'},
		          {field:'CREATE_TIME',align:'left',width:10,title:'创建时间'}
		          ]],
        detailFormatter:function(index,row){
    	return '<table class="table_02" cellpadding="0" cellspacing="0" id="ddv-' + index + '" style="padding:5px;"></table>';
        },
        // 展开层
        onExpandRow: function(index,row){
	        $('#ddv-'+index).panel({
	        	width:'100%',
				border:false,
				cache:false,
		        height:'auto',
		        href:_basePath+"/finance/DossierFinance!doShowFinanceDossierDetailList.action?itemid="
		        +row.itemid+"&PROJECT_ID="+row.ID+"&FID="+row.JG_ID+"&PAY_WAY="+row.BAILOUTWAY_ID,
		        onLoad:function(){
			        $('#toMgProject').datagrid('fixDetailRowHeight',index);
		        }
	        });
        }
        });
});

// 搜索
function toSeacher(){
	var BAILOUTWAY_NAME = $("selected[name='BAILOUTWAY_NAME']").attr("selected",true).val();
	var ORGAN_NAME = $("input[name='ORGAN_NAME']").val();
	var PROJECT_NAME = $("input[name='PROJECT_NAME']").val();
	$("#toMgProject").datagrid("load",{
		"PROJECT_NAME":PROJECT_NAME,
		"ORGAN_NAME":ORGAN_NAME,
		"BAILOUTWAY_NAME":BAILOUTWAY_NAME
	});
}

// 清空
function clearQuery(){
	$(".paramData").each(function(){
		$(this).val("");
	});
}

// 操作
function operation(val, row){
	return "<a href='javascript:void(0)' onclick=toShowPro('"+row.PRO_ID+"','"+row.PROJECT_NAME+"')>"+val+"</a> ";
}

// 查看
function toShowPro(PRO_ID,PROJECT_NAME){
	top.addTab(PROJECT_NAME+"融资项目查看",_basePath+"/refundProject/RefundProject!toShowPro.action?PROJECT_ID="+PRO_ID+"&data="+new Date());
}
