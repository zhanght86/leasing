var cur_row;
var table1;
var rows={} ;
$(function(){
	/*if(!$('#target_type').combobox('getText')){
		$('#target_type').combobox('setValue', '供应商');
	}*/
	table1 = $("#table1");
	table1.datagrid({
		url : 'EmsMg!selectEmsMgPageData.action',
		columns : [[
				{field:'modify',title:'操作',align:'center',width:100,formatter:function(
				    	value, row, index){
				    	var id = row['FIA_ID'] ;
				    	//alert(id) ;
				    	rows[id]=row;
				    	var a = '&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="modify('+row['ID']+","+row['FIA_ID']+",'"+row['EMS_FLAG']+"'"+')">修改</a>&nbsp;' ;
				    	if(row['ZHENGJIFAPIAO']=='第三方') return a;
				    	var b = '<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="synchronizationEms('+row['ID']+","+row['FIA_ID']+')">同步</a>' ;
				    	return a+b;} },
		            {field:'ID',title:'邮寄地址ID',width:100,align:'center',hidden:true},
		            {field:'FIA_ID',title:'开票协议ID',width:100,align:'center',hidden:true},
		            {field:'PROJECT_ID',title:'项目ID',align:'center',width:100,align:'center',hidden:true},
		            {field:'W_NAME',title:'客户名称',width:100,align:'center',editor:'text'},
			        {field:'ZHENGJIFAPIAO',title:'类型',align:'center',width:100,editor:'text'},
			        
			        {field:'NO',title:'开票协议编号',width:100,editor:'text',align:'center'},
			        {field:'CODE',title:'融资租赁合同编号',width:100,editor:'text',align:'center'},
			        {field:'PAYLIST_CODE',title:'支付表号',width:100,editor:'text',align:'center'},
			        
			        {field:'EMS_FLAG',title:'邮寄地址编号',width:100,editor:'text',align:'center'},
			        {field:'EMS_TO_NAME',title:'收件人',width:100,editor:'text',align:'center'},
			        {field:'EMS_TO_PHONE',title:'收件人电话',width:100,editor:'text',align:'center'},
			        {field:'EMS_TO_DW',title:'邮寄单位',width:100,editor:'text',align:'center'},
			        {field:'EMS_TO_ADDR',title:'邮寄地址',width:100,editor:'text',align:'center'},
			        {field:'EMS_TO_CITY',title:'邮寄城市',width:100,editor:'text',align:'center'},
			        {field:'EMS_TO_CODE',title:'邮编',width:100,editor:'text',align:'center'} 
			        
		            ]],
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
		pageSize : 20,
		pageList : [20,50,100,200]
	});
	
});
function seachInfo(){
	var searchParams = getFromData('#toolbar');
	table1.datagrid('load',{"searchParams":searchParams});
}

//导出
function exportExcel(){
	//url
	var url = "EmsMg!doExportApplyExcel.action";
	//submit
	$('#form01').form('submit',{
      url:url,
      success : function(){
    	  seachInfo();
      }
  });
	
}

function synchronizationEms(id,fia_id){
	
	if(id==undefined || typeof(id)==undefined){
		$.messager.alert('警告','请写填入邮寄地址相关信息再进行同步操作!');
		return ;
	}
	var row = rows[fia_id] ;
	var paylist_code = row['PAYLIST_CODE'] ;
	//alert(paylist_code) ;
	jQuery.ajax({
        type: "POST",
        dataType: "json",
		async:false,
        url: "EmsMg!synchronizationEms.action",
        data: {
			ID:id,			
			PAYLIST_CODE:paylist_code
		},
        success: function(data){
			//debugger;
			alert(data.msg);
        }
	    });
} 

function modify(id,fia_id,ems_flag){
	var row = rows[fia_id];
	/*if(!row){
		$.messager.show({
			title:'提示',
			msg:'请选中要修改的数据',
			timeout:5000,
			showType:'slide'
		});
		return;
	}*/
	var target_id = id;
	//var target_type = target_type;
	//var target_full_name = target_full_name;
	var ems_flag = ems_flag;
	var ems_id=id ;
	var fia_id=fia_id ;
	var NO = row['NO'] ;
	if(NO==null || NO == undefined || typeof(NO)==undefined){
		NO='';
	}
	//alert(NO) ;
	
	$('#dialog1').dialog({
	    title: '开票协议编号: '+ NO,
	    width: 550,
	    height: 450,
	    closed: false,
	    cache: false,
//	    href: 'InvoiceMg!toInvoiceConfigDetailMg.action?id='+id,
	    modal: true,
	    toolbar:[{
            text:'新地址',
            iconCls:'icon-add',
            handler:function(){
		    	$("#EMS_FLAG").val('');
		    	$("#EMS_TO_NAME").val('');
		    	$("#EMS_TO_PHONE").val('');
		    	$("#EMS_TO_DW").val('');
		    	$("#EMS_TO_CITY").val('');
		    	$("#EMS_TO_ADDR").val('');
		    	$("#EMS_TO_CODE").val('');
		    	$("#combobox01").combobox("clear");
            }
        },'-',{
            text:'保存',
            iconCls:'icon-save',
            handler:function(){
	        	var searchParams = getFromData('#dialog1');
	        	$.ajax({
		    		url : "EmsMg!updateOrAddEmsInfo.action?TARGET_TYPE="+target_type+"&TARGET_ID="+target_id+"&EMS_ID="+ems_id+"&FIA_ID="+fia_id,
		    		type : "POST",
		    		dataType:"json",
		    		data : "searchParams="+searchParams,
		    		success : function(msg){
	        			var message = msg.msg;
	        			if(msg.flag){
	        				if(message.indexOf("请检查录")==-1){
	        					$('#dialog1').dialog("close");
	        					table1.datagrid('reload');
	        				}
	        			}else{
	        				message = '保存失败，请检查填写数据';
	        			}
		        		$.messager.show({
		        			title:'提示',
		        			msg: message ,
		        			timeout:5000,
		        			showType:'slide'
		        		});
	    			}
	        	});
            }
        }]
	});
	//存在addr
	if(ems_flag){
		$("#EMS_FLAG").val(row['EMS_FLAG']);
		$("#EMS_TO_NAME").val(row['EMS_TO_NAME']);
		$("#EMS_TO_PHONE").val(row['EMS_TO_PHONE']);
		$("#EMS_TO_DW").val(row['EMS_TO_DW']);
		$("#EMS_TO_CITY").val(row['EMS_TO_CITY']);
		$("#EMS_TO_ADDR").val(row['EMS_TO_ADDR']);
		$("#EMS_TO_CODE").val(row['EMS_TO_CODE']);
	}

}
function formatItem(row){
    var s = '<span style="font-weight:bold">' + row.text + '</span><br/>' +
            '<span style="color:#888">' + row.desc + '</span>';
    return s;
}

function alterDialog(row){
	cur_row = row;
	var ems = cur_row.desc;
	var id = cur_row.id;
	var text = cur_row.text;
	var strs=ems.split(";"); //字符分割      
	$("#EMS_FLAG").val(text);
	$("#EMS_TO_NAME").val(strs['0']);
	$("#EMS_TO_PHONE").val(strs['1']);
	$("#EMS_TO_DW").val(strs['2']);
	$("#EMS_TO_CITY").val(strs['3']);
	$("#EMS_TO_ADDR").val(strs['4']);
	$("#EMS_TO_CODE").val(strs['5']);
}

//上传回执
function uploadExcel(){
	//url
	var url = "EmsMg!uploadInvoiceEmsInfo.action";
	$('#uploadDialog').dialog({
		title : '上传回执',
		width : 400,
		height : 200,
		closed : true,
		cache : false,
		modal : true,
		closed : false,
		buttons : 
			[{
				text : '上传',
				iconCls : 'icon-up',
				handler : function()
					{
						var flag = false;
						$.messager.confirm('Confirm','确定上传文件：'+$('#uploadFile').val(),function(r)
						{
							if(!r)
							{
								return;
							}
							$('#fileUploadForm').form('submit',
								{
                                    url:url,
                                    onSubmit: function()
									{
										var filename = $('#uploadFile').val();
										if(filename.indexOf('xls') == -1)
										{
											$.messager.alert('提示','请勿上传非xls结尾的文件','');
											return false;
										}
                                    },
                					success:function(data)
									{
                					 	var data = eval('(' + data + ')');
                						$.messager.alert('',data.msg,'');
										$('#uploadDialog').dialog('close');
                					}
                            });
                        });
        			}
			},
			{
				text : '关闭',
				iconCls : 'icon-cancel',
				handler : function()
				{
					$('#uploadDialog').dialog('close');
				}
			}]
	});
}

//上传 
function uploadSave(){
   jQuery.ajaxFileUpload({
    	url:"EmsMg!uploadInvoiceEmsInfo.action",
    	secureuri:false,
    	fileElementId:"verificInvoice",	 
    	dataType: "json",
		success: function (data,status)  //服务器成功响应处理函数
        {    
			var d = JSON.parse(data);
		    $("#importReceipt").dialog('close');
			 if(d.flag){
			    jQuery.messager.alert("提示",d.msg);
			 }else{
			    jQuery.messager.alert("提示",d.msg);
			 }
        }

    });	
}

function importReceipt(){
    $("#verificInvoice").val("");
    $("#importReceipt").dialog('open').dialog('setTitle','增值税发票核销');
}
