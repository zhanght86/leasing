var currIndex = '';
var table1;
var table2;
var col = eval(fields);
var rowsss = JSON.parse(rowss);
$(function(){
	table2 = $('#table2');
	table1 = $('#table1');
	table1.datagrid({
		url : 'InvoiceMg!doSelectConfigPageData.action',
		columns : col,
		toolbar : '#toolbar',//工具条
		idField : 'ID',
		fitColumns : true,
		autoRowHeight : true,
		singleSelect:true,
		collapsible:true,
//		checkOnSelect : true,
		nowrap : true,
		rownumbers : true,
	    method: 'get',
		pagination : true ,//分页
		pageSize : 300,
		pageList : [300],
		onClickCell : function(index,field,value){
//			console.info(index+field+value);
			// function onClickRow(index){
	        if (editIndex != index){
	            if (endEditing()){
	            	table1.datagrid('beginEdit', index);
	            	currIndex = editIndex;
	                editIndex = index;
	            } else {
	            	table1.datagrid('selectRow', editIndex);
	            	currIndex = editIndex;
	            }
	        }else{
	        	//if(table1.datagrid('getEditors',index)){
	        		//return;
	        	//}
	        	currIndex = editIndex;
	        	table1.datagrid('beginEdit', index);
	        }
	        //}
		},
		onAfterEdit : function(rowIndex, rowData, changes){
			var ID = table1.datagrid('getRows')[rowIndex]['ID'];
			//修改
			if(!ID){
				ID="";
			}
			//console.info(changes);
			var SQL_KEYS = "," ;
			var SQL_VALUES =",";
			var SQL_SET = ""; 
			var temp = "";
			for ( var p in changes ){ // 方法 
				if ( typeof ( changes [ p ]) == " function " ){ 
//					/changes [ p ]() ; 
				} else { // p 为属性名称，changes[p]为对应属性的值 
					SQL_KEYS += p +",";
					SQL_VALUES += "'"+changes [ p ]+"'," ; 
					SQL_SET += p +"='"+ changes [ p ]+"'," ;
					//temp += changes [ p ];
				} 
			} // 最后显示所有的属性 
			
			if(changes&&SQL_SET.length>0){
//				$.messager.confirm('确认','Are you sure you want to update this param?',function(flag){
				$.ajax({
					url : "InvoiceMg!doUpdateRulerHead.action?SQL_SET="+ SQL_SET.substring(0, SQL_SET.length-1)+"&SQL_KEYS="+SQL_KEYS.substring(0, SQL_KEYS.length-1)+"&SQL_VALUES="+SQL_VALUES.substring(0, SQL_VALUES.length-1),
					type : "POST",
					dataType:"json",
					data : "ID="+ID,
					success : function(msg){
						var res = "修改失败";
						if(msg.flag){
							res="修改成功";
						}
						$.messager.show({
			                title:'操作反馈',
			                msg:res,
			                showType:'show'
			            });
						if(ID==""){
							table1.datagrid('reload');
							table1.datagrid('beginEdit', currIndex);
						}
					}
				});
			}
		}
	});
});
var editIndex = undefined;
function endEditing(){
    if (editIndex == undefined){
    	return true;
	}
    if (table1.datagrid('validateRow', editIndex)){
        table1.datagrid('endEdit', editIndex);
        editIndex = undefined;
        return true;
    } else {
    	$.messager.show({
			title:'提示',
			msg:'请填写必填项',
			timeout:5000,
			showType:'slide'
		});
        return false;
    }
}
function add()
{
	if (endEditing()){
		table1.datagrid('appendRow',rowss);
        editIndex = table1.datagrid('getRows').length-1;
        table1.datagrid('selectRow', editIndex)
                .datagrid('beginEdit', editIndex);
        currIndex = editIndex;
    }
//	table1.datagrid('showColumn',{field:'RULER_NAME',title:'规则名称',width:100});
}
function save()
{	
	if(!hasSelect()){
		return;
	}
	if (endEditing()){
		table1.datagrid('acceptChanges');
	}
//	var cRow = hasSelect();
//	if(!cRow){
//		return;
//	}
//	 if (endEditing()){
//		 table1.datagrid('acceptChanges');
//     }
//	console.info(cRow);
//	table1.datagrid('endEdit', cRow);
}
function rem()
{
	var sel = hasSelect();
	if(!sel){
		return;
	}
	var ID=sel['ID'];
	//存在id
	if(ID){
		$.messager.confirm('确认','确定要删除?',function(flag){
			if(flag){
				$.ajax({
					url : "InvoiceMg!doDeleteRulerHead.action",
					type : "POST",
					dataType:"json",
					data : "ID="+ID,
					success : function(msg){
						var res = "修改失败";
						if(msg.flag){
							res="修改成功";
						}
						$.messager.show({
			                title:'操作反馈',
			                msg:res,
			                showType:'show'
			            });
						table1.datagrid('reload');
					}
				});
			}else{
				return;
			}
		});
	}else{
		table1.datagrid('reload');
	}
}
function edit(){
	var sel = hasSelect();
	if(!sel)return;
	var id = sel['ID'];
	init(id);
	$('#dialog1').dialog({
	    title: sel['RULER_NAME']+"（增值税类型：0-专用发票，1-纳税人资质，2-普通发票）",
	    width: 520,
	    height: 460,
	    closed: false,
	    cache: false,
//	    href: 'InvoiceMg!toInvoiceConfigDetailMg.action?id='+id,
	    modal: true
	});
	
//	var panel1 = $("#panel1");
//	panel1.panel({
//		width:500,
//	    height:150,
//	    title:'My Panel',
//	    tools:[{
//	        iconCls:'icon-add',
//	        handler:function(){alert('new');}
//	    },{
//	        iconCls:'icon-save',
//	        handler:function(){alert('save');}
//	    }]
//	});
//	panel1.panel('open');
}
function hasSelect(){
	var sel = table1.datagrid('getSelected');
	if(!sel)
	{
		$.messager.show({
			title:'提示',
			msg:'请选中要操作的数据',
			timeout:5000,
			showType:'slide'
		});
		return false;
	}
	return sel;
}
var table2 ;
function init(id){
	table2 = $('#table2');
	table2.datagrid({
		url : 'InvoiceMg!doSelectConfigDetailData.action?id='+id,
		columns : [[
		            {field:'FLAG',title:'资金类型',width:100,align:'right'},
		            {field:'IF_INVOICE',title:'发票',width:40,editor:'text',align:'center',
	            		editor:{type:'checkbox',options:{on:'开具',off:''}}
		            },
		            {field:'IF_RECEIPT',title:'收据',width:40,editor:'text',align:'center',
	            		editor:{type:'checkbox',options:{on:'开具',off:''}}
		            },
		            {field:'MERGE',title:'合并标志',width:100,editor:'text'},
		            {field:'MERGE_NAME',title:'合并名称',width:100,editor:'text'},
		            {field:'SHOW_NODE',title:'发票节点',width:100,editor:'text'},
		            {field:'RECEIPT_NODE',title:'收据节点',width:100,editor:'text'},
		            {field:'TAX_TYPE',title:'增值税类型',width:100,editor:'text'},
		            {field:'TAX_RATE',title:'税率',width:100,editor:'text'}
		            ]],
//		toolbar : '#toolbar',//工具条
		idField : 'ID',
		fitColumns : true,
		autoRowHeight : true,
		singleSelect:true,
		collapsible:true,
	//		checkOnSelect : true,
		nowrap : true,
		rownumbers : true,
	    method: 'get',
			pagination : true ,//分页
			pageSize : 300,
			pageList : [300],
		onClickCell : function(index,field,value){
	//			console.info(index+field+value);
			// function onClickRow(index){
	        if (editIndex2 != index){
	            if (endEditing2()){
	            	table2.datagrid('beginEdit', index);
	                editIndex2 = index;
	            } else {
	            	table2.datagrid('selectRow', editIndex2);
	            }
	        }else{
	        	if(table2.datagrid('getEditors',index)){
	        		return;
	        	}
	        	table2.datagrid('beginEdit', index);
	        }
	        //}
		},
		onAfterEdit : function(rowIndex, rowData, changes){
//			console.info(rowData);
//			console.info(changes);
			var SQL_KEYS = "" ;
			for ( var p in rowData ){ // 方法 
				if ( typeof ( rowData [ p ]) == " function " ){ 
	//					/changes [ p ]() ; 
				} else { // p 为属性名称，changes[p]为对应属性的值 
					SQL_KEYS +="&"+ p +"="+rowData [ p ];
				} 
			} // 最后显示所有的属性 
			
			if(changes){
	//				$.messager.confirm('确认','Are you sure you want to update this param?',function(flag){
					$.ajax({
						url : "InvoiceMg!doUpdateConfigDetail.action?RULER_ID="+id+SQL_KEYS,
						type : "POST",
						dataType:"json",
						data : "",
						success : function(msg){
							var res = "修改失败";
							if(msg.flag){
								res="修改成功";
							}
							$.messager.show({
				                title:'操作反馈',
				                msg:res,
				                showType:'show'
				            });
							//table2.datagrid('reload');
						}
					});
			}
		}
	});
}
var editIndex2 = undefined;
function endEditing2(){
    if (editIndex2 == undefined){
    	return true;
	}
    if (table2.datagrid('validateRow', editIndex2)){
        table2.datagrid('endEdit', editIndex2);
        editIndex2 = undefined;
        return true;
    } else {
    	$.messager.show({
			title:'提示',
			msg:'请填写必填项',
			timeout:5000,
			showType:'slide'
		});
        return false;
    }
}
function refresh(){
	$.ajax({
		url : "InvoiceMg!doRefreshConfigFinal.action",
		type : "POST",
		dataType:"json",
		data : "",
		success : function(msg){
			var res = "启用失败";
			if(msg.flag){
				res="启用成功";
			}
			$.messager.show({
                title:'操作反馈',
                msg:res,
                showType:'show'
            });
			//table2.datagrid('reload');
		}
	});
}