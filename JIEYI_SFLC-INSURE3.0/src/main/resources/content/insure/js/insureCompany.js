/**
 * 保险公司管理js
 * @author 韩晓龙
 */
var url = "";

//显示可配置险种
function configInsureType(index){
    $('#pageTable').datagrid('selectRow',index);//根据index选中row
	var row = $('#pageTable').datagrid('getSelected');
	if(row){
		$('#dlg').dialog('open').dialog('setTitle','配置险种信息');
		$('#fm').form('load',row);
		url = "";
		url = "InsureCompany!configInsureType.action";
	}
}

//得到所有checkbox的值
function getAllCheckBoxValue(){
	var arrChk = "";
	$($("input:checked")).each(function(){arrChk+=this.value +',';});//遍历被选中CheckBox元素的集合 得到Value值
	arrChk = arrChk.substr(0,arrChk.length-1);
	return arrChk;
}

//save按钮功能
function save(){
	url = url + "?ALLINSURETYPE=" + getAllCheckBoxValue();
    $('#fm').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            var result = eval('('+result+')');
            if (result.flag==false){
                $.messager.show({
                    title: '出错',
                    msg: result.msg
                });
            } else {
                $('#dlg').dialog('close');        // close the dialog
                $('#pageTable').datagrid('reload');    // reload the user data
            }
        }
    });
}

//清空查询条件
function emptyData(){
	//清空input
	$(".paramData").each(function(){
		$(this).val("");
	});
}

//查询
function dosearch(){
	var COMPANY_NAME = $("#COMPANY_NAME").val();
	var COMPANY_ADDR = $("#COMPANY_ADDR").val();
	var CONTACTS = $("#CONTACTS").val();
	var CONTACT_NUM = $("#CONTACT_NUM").val();
	$('#pageTable').datagrid('load', {"COMPANY_NAME":COMPANY_NAME,"COMPANY_ADDR":COMPANY_ADDR,"CONTACTS":CONTACTS,"CONTACT_NUM":CONTACT_NUM});
}

//操作
function setOperation(val,row,index) {
	return "<a href='#'  onclick='showInsureCompanyDetail(" + val + ")'>查看</a>|<a href='#'  onclick='showInsureCompanyAlter(" + val + ")'>修改</a>|<a href='#'  onclick='doDeleteInsureCompany(" + val + ")'>删除</a>|<a href='#'  onclick='configInsureType(" + index + ")'>配置险种</a>";
}
//显示 添加保险公司信息 页面
function showInsureCompanyAdd(ID){
	top.addTab("添加保险公司信息", _basePath + "/insure/InsureCompany!toShowInsureCompanyAdd.action");
}
//显示 修改保险公司信息 页面
function showInsureCompanyAlter(ID){
	top.addTab("修改保险公司信息", _basePath + "/insure/InsureCompany!toShowInsureCompanyAlter.action?ID=" + ID);
}
//查看保险公司
function showInsureCompanyDetail(ID){
	top.addTab("查看保险公司信息", _basePath + "/insure/InsureCompany!toShowInsureCompanyDetail.action?ID=" + ID);
}
//刷新保险公司展示页面
function showInsureCompany(){
	top.closeTab("保险公司管理");
	top.addTab("保险公司管理", _basePath + "/insure/InsureCompany.action");
}
//增加 保险公司
function doAddInsureCompany(){
	url = _basePath + "/insure/InsureCompany!doAddInsureCompany.action";
	$('#fm_add').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            var result = eval('('+result+')');
            if (result.flag==false){
                $.messager.show({
                    title: '出错',
                    msg: result.msg
                });
            } else {
            	$.messager.alert('结果','添加成功!','info',function(){
            				//刷新页面
            				top.closeTab("添加保险公司信息");
            				showInsureCompany();
            		}
                );
            }
        }
    });
}
//修改 保险公司
function doUpdateInsureCompany(ID){
	url = _basePath + "/insure/InsureCompany!doUpdateInsureCompany.action?ID=" + ID;
	$('#fm_update').form('submit',{
        url: url,
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            var result = eval('('+result+')');
            if (result.flag==false){
                $.messager.show({
                    title: '出错',
                    msg: result.msg
                });
            } else {
            	$.messager.alert('结果','修改成功!','info',function(){
            				//刷新页面
            				top.closeTab("修改保险公司信息");
            				showInsureCompany();
            		}
                );
            }
        }
    });
}
//删除保险公司
function doDeleteInsureCompany(ID){
	$.messager.confirm("删除","确定要删除该保险公司吗?",function(r){
		if(r){
			jQuery.ajax({
				url : _basePath + "/insure/InsureCompany!doDeleteInsureCompany.action",
				data : { "ID": ID},
				dataType:'json',
				success:function(data){
					$.messager.alert("结果","删除成功！");
					//刷新页面
					showInsureCompany();
				}
			});
		}
	});
}
/*
 	$.messager.confirm("添加","确定要保存此保险公司信息吗？",function(r){
		if(r){
			jQuery.ajax({
				url : _basePath + "/insure/InsureCompany!doDeleteMessage.action",
				data : { "ID": ID},
				dataType:'json',
				success:function(data){
					$.messager.alert("结果","保存成功！");
					//刷新页面
					showReasonRecord(Record_ID);//这个是逾期记录的ID
				}
			});
		}
	});
 */
