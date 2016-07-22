$(function() {
	leeds.keyer.winEnter(query);
	
	// 新增记录弹窗
	var recordDivOptions = leeds.dialogInitOp('recordDiv', 'recordForm',
			addRecords, 850, {
				top : 10
			});
	$('#recordDiv').dialog(recordDivOptions);

	// 记录列表
	$('#recordList')
			.datagrid(
					{
						// title : '资料管理',
						// iconCls : 'icon-edit',
						url : _basePath
								+ '/api/datalist/DataTemplet!toRecordMainPage.action',
						queryParams : {

						},
						columns : [ [
								{
									field : 'PHASE_V',
									title : '阶段',
									width : 70,
									align : 'center'
								},
								{
									field : 'BUSINESS_TYPE',
									title : '业务类型',
									width : 70,
									align : 'center'
								},
								{
									field : 'CUST_TYPE',
									title : '客户类型',
									width : 70,
									align : 'center'
								},
								{
									field : 'SYB_TYPE',
									title : '事业部',
									width : 70,
									align : 'center'
								},
								{
									field : 'FACTORS',
									title : '条件',
									width : 200,
									align : 'center'
								},
								{
									field : 'FILES',
									title : '资料',
									width : 200,
									align : 'center'
								},					
								{
									field : 'ID',
									title : '操作',
									width : 70,
									align : 'center',
									formatter : function(value, row, index) {
										var res = '';
										res += '<a href="javascript:void(0)" onclick="toUpdate('
												+ value
												+ ')">修改</a>'
												+ ' | <a href="javascript:void(0)" onclick="delRecord('
												+ row.ID + ')">删除</a>';
										return res;
									}
								} ] ],
						fitColumns : true,
						striped : true,
						nowrap : false,
						pagination : true,
						pageSize : 20,
						rownumbers : true,
						singleSelect : true,
						toolbar : '#tbRecord'
					});
	// 客户类型加点击事件
	$('[DICT_TITLE="客户类型"]').each(function() {
		$(this).attr('onclick', 'changeCustType()');
	});

});


function query() {
	$('#recordList').datagrid("load",{"CUSTOMER_TYPE" : $("#CUSTOMER_TYPE").combobox("getValue"),"BUSINESS_TYPE": $("#BUSINESS_TYPE").combobox("getValue"),"BUSINESS_UNIT":$("#BUSINESS_UNIT").combobox("getValue")});
}


function clearInput() {
	$("#queryForm input").val("");

}


// ----------------------------记录，增删改查---------------------begin--------

// toUpdate
function toUpdate(ids) {
	loadRecord(ids);
	// fileRecord(id);
	var recordDivOptions1 = leeds.dialogInitOp('recordDiv', 'recordForm',
			updRecord, 850, {
				top : 10
			});
	$('#recordDiv').dialog(recordDivOptions1);
	$('#recordDiv').dialog('open');
}

// delRecord
function delRecord(id) {
	leeds.del(id, _basePath + '/api/datalist/DataTemplet!delRecord.action',
			'recordList');
}

// loadRecord
function loadRecord(ids) {
	var url = _basePath + '/api/datalist/DataTemplet!loadRecord.action';
	$.ajax({
		type : "post",
		url : url,
		data : 'ID=' + ids + "&date_=" + new Date(),
		dataType : 'json',
		success : function(res) {
			console.debug(res);
			$("#recordForm").form("clear");
			$('#recordForm').form('load', res.data);

			
			$("#PHASE").combobox('select',res.data.PHASE_V );	
			
			$.each(res.data.factors, function(i, n) {

				$('#' + n.FACTOR_SYS + '_' + n.FACTOR_DICT_ID).attr('checked',
						true);
			});
		  
				$('#pageTable1').datagrid({
					onLoadSuccess:function(row){//当表格成功加载时执行               
		                var rowData = row.rows;
		                $.each(rowData,function(idx,val){//遍历JSON
		                	$.each(res.data.files, function(i, n) {
		        				var c = i + 1;
		        				var arr1 = [ {
		        					c : n.TYPE
		        				} ];
		        				
		        				var arr2 = [ {
		        					c : n.ISCHOICE
		        				} ];
		        				
		                      if(val.ID==n.FILE_DICT_ID){
		                        $("#pageTable1").datagrid("selectRow", idx);//如果数据行为已选中则选中改行
                                $("input[id='ck" + n.FILE_DICT_ID + "']").each(function() {
                                	if ($(this).val() == n.FILE_DICT_ID ) {
			    						    $(this).attr("checked", true);
			    					}
		                    	});
		                        $("input[id='TYPE" + n.FILE_DICT_ID + "']").each(function() {
			    					if ($(this).val() == n.TYPE) {
			    						$(this).attr("checked", true);
			    					}
			    					
			    				});
		                      
			                   $("input[id='ISCHOICE" + n.FILE_DICT_ID + "']").each(function() {
				    					if ($(this).val() == n.ISCHOICE) {
				    						
				    						$(this).attr("checked", true);
				    					}
			                    });

			    				$("input[id='FENSHU" + n.FILE_DICT_ID + "']").each(function() {
			    					$(this).val(n.FENSHU);
			    				});
			    				$("input[id='FILE_DICT_ID" + n.FILE_DICT_ID + "']").each(
			    						function() {
			    							$(this).val(n.FILE_DICT_ID);
			    				});
		                      }
		                }); 
		                	

		    			});	
		            }});
				
				
		//	changeCustType();
		}
	});

}

// 原件
function getOperation1(value, rowDate, index) {
	var mmid = $("#ID").val();
	var html = "<div style='width:60px; float:center; '  class='filedom'>"
			+ "<input type='hidden' value='" + mmid
			+ "' width='30' height='30' name='ID' id='ID" + rowDate.ID + "' />"
			+ "<input type='hidden' value='" + rowDate.ID
			+ "' width='30' height='30' name='FILE_DICT_ID' id='FILE_DICT_ID"
			+ rowDate.ID + "' />" +

			"<input type='radio' name='TYPE" + rowDate.ID + "' id='TYPE"
			+ rowDate.ID + "' value='0' ";
	if (rowDate.TYPE == 0) {
		html += " checked ";
	}
	html += "/>原件" + "&nbsp&nbsp" + "<input type='radio' name='TYPE"
			+ rowDate.ID + "' id='TYPE" + rowDate.ID + "' value='1' ";
	if (rowDate.TYPE != 0) {
		html += " checked ";
	}
	html += " />复印件";

	html += "</div>";

	return html;
}

// 原件/复印件份数
function getOperation2(value, rowDate, index) {
	var mmid = $("#ID").val();
	var fenshu = "0";//若份数为零则默认显示3份
	if (rowDate.FENSHU != null) {
		fenshu = rowDate.FENSHU;
	}
	return "<div style='width:60px; float:center; '  class='filedom1'>"
			+ "<input type='hidden' value='" + mmid
			+ "' width='30' height='30' name='ID' id='ID" + rowDate.ID + "' />"
			+ "<input type='hidden' value='" + rowDate.ID
			+ "' width='30' height='30' name='FILE_DICT_ID' id='FILE_DICT_ID"
			+ rowDate.ID + "' />" + "<input type='text' id='FENSHU"
			+ rowDate.ID + "' name='FENSHU'  value='" + fenshu
			+ "' style='width:40px'  />份</div>";
}

function getOperation3(value, rowDate, index){
	var mmid = $("#ID").val();
	var html = "<div style='width:60px; float:center; '  class='filedom2'>"
			+ "<input type='hidden' value='" + mmid 
			+ "' width='30' height='30' name='ID' id='ID" + rowDate.ID + "' />"
			+ "<input type='hidden' value='" + rowDate.ID
			+ "' width='30' height='30' name='FILE_DICT_ID' id='FILE_DICT_ID"
			+ rowDate.ID + "' />" +

			"<input type='radio' name='ISCHOICE" + rowDate.ID + "' id='ISCHOICE"
			+ rowDate.ID + "' value='0' ";
	if (rowDate.ISCHOICE == 0) {
		html += " checked ";
	}
	html += "/>必选" + "&nbsp&nbsp" + "<input type='radio' name='ISCHOICE"
			+ rowDate.ID + "' id='ISCHOICE" + rowDate.ID + "' value='1' ";
	if (rowDate.ISCHOICE != 0) {
		html += " checked ";
	}
	html += " />非必选";

	html += "</div>";

	return html;
} 



// addRecords
function addRecords() {
	
	if ($('#recordForm input[name=FACTOR]:checked').size() == 0) {
		leeds.msg('请最少选择一项【条件】！');
		return;
	}
	// 条件详细
	var data1 = [];
	$(".factor").each(
			function() {
				var temp2 = {};
				temp2.FACTOR_DICT_ID = $(this).find("input:checkbox:checked")
						.val();
				temp2.FACTOR_SYS = $(this).find("input:checkbox:checked").attr(
						"sysFactorValue");
				data1.push(temp2);
			});
	
    //资料清单 	
	var data3 = [];
	$(".check").each(function() {
		var temp= {};
		temp.ID=$(this).find("input:checkbox:checked").val();
	    temp.FILE_DICT_ID = $(this).find("input:checkbox:checked").val();
		temp.TYPE = $("input[name=TYPE" + temp.ID+ "]:checked").val();
		temp.FENSHU = $("#FENSHU"+ temp.ID).val();
		temp.ISCHOICE=$("input[name=ISCHOICE" +temp.ID+ "]:checked").val();
		data3.push(temp);       
	 });


 

	// // 原件复印件
	// var data3 = [];
	// $(".filedom").each(function() {
	// var temp4 = {};
	// temp4.TYPE = $(this).find("input:radio:checked").val();
	// temp4.FILE_DICT_ID = $(this).find("input[name=FILE_DICT_ID]").val();
	// data3.push(temp4);
	// });
	// // 原件复印件份数
	// var data4 = [];
	// $(".filedom1").each(function() {
	// var temp5 = {};
	// temp5.FILE_DICT_ID = $(this).find("input[name=FILE_DICT_ID]").val();
	// temp5.FENSHU = $(this).find("input[name=FENSHU]").val();
	// data4.push(temp5);
	// });

	var resutlData = {
		data1 : data1,
		data3 : data3,
		PHASE : $("#PHASE").combobox('getValue'),
		MEMO : $("#MEMO").val()
	};
	console.debug(resutlData);
	jQuery.ajax({
		type : "post",
		url : _basePath + '/api/datalist/DataTemplet!addRecords.action',
		data : "param=" + encodeURI(JSON.stringify(resutlData)),
		dataType : "json",
		success : function(data) {
			if (data.flag == false) {
				alert("失败!");
				$('#recordDiv').dialog('close');
			} else {
				alert("成功");
				$('#recordDiv').dialog('close');
				window.location.href = _basePath
						+ '/api/datalist/DataTemplet.action';
			}
		}
	});

	// $('#recordForm').form('submit', {
	// url : _basePath + '/leeds/materialMgt/MaterialMgt!addRecords.action',
	// onSubmit : function(param) {
	//			
	// },
	// success : function(data) {
	// $('#recordList').datagrid('load');
	// $('#recordDiv').dialog('close');
	// }
	// });
}
// updRecord
function updRecord() {
	if ($('#recordForm input[name=FACTOR]:checked').size() == 0) {
		leeds.msg('请最少选择一项【条件】！');
		return;
	}

	var data1 = [];
	$(".factor").each(
			function() {
				var temp2 = {};
				temp2.FACTOR_DICT_ID = $(this).find("input:checkbox:checked")
						.val();
				temp2.FACTOR_SYS = $(this).find("input:checkbox:checked").attr(
						"sysFactorValue");
				data1.push(temp2);
			});
	
	
    //资料清单 	
	var data3 = [];
	$(".check").each(function() {
		var temp= {};
		temp.ID=$(this).find("input:checkbox:checked").val();
	    temp.FILE_DICT_ID = $(this).find("input:checkbox:checked").val();
		temp.TYPE = $("input[name=TYPE" + temp.ID+ "]:checked").val();
		temp.FENSHU = $("#FENSHU"+ temp.ID).val();
		temp.ISCHOICE=$("input[name=ISCHOICE" +temp.ID+ "]:checked").val();
		data3.push(temp);       
	 });

	
	
//	var form = $("#pageTable1").datagrid('getSelections');
//	var data3 = [];
//	for (var i = 0; i < form.length; i++) {
//		var temp = {};
//		temp.FILE_DICT_ID = form[i].ID;
//		temp.TYPE = $("input[name=TYPE" + form[i].ID + "]:checked").val();
//		temp.FENSHU = $("#FENSHU"+ form[i].ID).val();
//		temp.ISCHOICE=$("input[name=ISCHOICE" + form[i].ID + "]:checked").val();
//		data3.push(temp);
//	}



	var resutlData = {
		data1 : data1,
		data3 : data3,
//		data4 : data4,
		PHASE : $("#PHASE").combobox('getValue'),
		MEMO : $("#MEMO").val(),
		ID : $("#ID").val()
	};

	jQuery.ajax({
		type : "post",
		url : _basePath + '/api/datalist/DataTemplet!updRecord.action',
		data : "param=" + encodeURI(JSON.stringify(resutlData)),
		dataType : "json",
		success : function(data) {
			if (data.flag == false) {
				alert("失败!");
				$('#recordDiv').dialog('close');
			} else {
				alert("成功");
				$('#recordDiv').dialog('close');
				window.location.href = _basePath
						+ '/api/datalist/DataTemplet.action';
			}
		}
	});

	// if($('#recordForm input[name=FILE_NAME]:checked').size()==0) {
	// leeds.msg('请最少选择一项【资料清单】！');
	// return;
	// }
	// $('#recordForm').form('submit', {
	// url : _basePath + '/leeds/materialMgt/MaterialMgt!updRecord.action',
	// onSubmit : function(param) {
	//			
	// },
	// success : function(data) {
	// $('#recordList').datagrid('load');
	// $('#recordDiv').dialog('close');
	// }
	// });
}

// 按客户类型刷新资料清单
function changeCustType() {
	var cust_type = [];
	$('[DICT_TITLE="客户类型"]:checked').each(function() {
		cust_type.push($(this).attr('DATA_CODE'));
	});
	//$('#pageTable1').datagrid('load', {
		//CUST_TYPE : cust_type.join(",")
		
		//MM_ID : $('#ID').val()
//	});
}

function checkboxDoo(value,row,index){
  if(row.checked){
       return "<div class='check'><input type='checkbox'  id='ck"+row.ID+"' name='ck' checked='checked' value='"+row.ID+"' onclick='checkboxNumber("+row.ID+")'/></div>";
   }else{
	   return "<div class='check'><input type='checkbox' id='ck"+row.ID+"' name='ck' value='"+row.ID+"' onclick='checkboxNumber("+row.ID+")'/></div>";
  }
         
}


function checkboxNumber(index){
	$("input[id='ck"+index+"']").click(function(){ 
		  $(".check").each(function() {
		    var len = $("input[name='ck']:checked").length; 
		    var lenAll=$("input[name='ck']").length; 
		    if(len==lenAll){ 
			    $("input[id='ButonGetCheck']").attr("checked",'true');//全选	
		    }else if(number<lenAll){
		    	 $("input[id='ButonGetCheck']").removeAttr("checked");//全选	
	        }
		 });	 
	});
}
/**
 * 选中
 * @param node
 * @author King 2014年8月10日
 */
function choseAllFile(node){
      $("#ButonGetCheck").click(function(){	
    	  if($(this).attr("checked")){
    		   $(".check").each(function() {
    		       $("input[name='ck']").attr("checked",'true');//全选		
    			});	  
    	  }else{
    		  $(".check").each(function() {
    		       $("input[name='ck']").removeAttr("checked");//全选	
    		  });	     
    	  }		     
	  });
}


//添加打开dialog
function openRecordDiv(){

	$('#recordForm').form('clear');	
	$('#recordDiv').dialog('open','cache: false').dialog('setTitle','添加用户');	
	$('.filedom [value=1]').each(function(){
		$(this).attr("checked",true);
	});
	$('.filedom2 [value=1]').each(function(){
		$(this).attr("checked",true);
	});
}
//导出资料配置项目
function exportExcel(){
	//"CUSTOMER_TYPE" : $("#CUSTOMER_TYPE").combobox("getValue"),"BUSINESS_TYPE": $("#BUSINESS_TYPE").combobox("getValue"),"BUSINESS_UNIT":$("#BUSINESS_UNIT").combobox("getValue")
	window.location.href =_basePath + '/api/datalist/DataTemplet!exportMoni.action?CUSTOMER_TYPE='+ $("#CUSTOMER_TYPE").combobox("getValue")+"&BUSINESS_TYPE="+$("#BUSINESS_TYPE").combobox("getValue")+"&BUSINESS_UNIT="+$("#BUSINESS_UNIT").combobox("getValue")
}


