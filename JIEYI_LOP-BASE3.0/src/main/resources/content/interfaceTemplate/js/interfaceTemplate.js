$(function(){
    $('#dialogDivDs').dialog({
        onClose: function(){
            $("#fromDateDs [name='NAME']").val('');
            $("#fromDateDs [name='ID']").val('');
            $("#fromDateDs [name='NOTE']").val('');
            $("#fromDateDs [name='SQL_TEXT']").val('');
            $("#fromDateDs [name='SQL_NOTE']").val('');
            $("#fromDateDs [name='OLDNAME']").val('');
            $("#fromDateDs [name='CONTENT']").val('');
            $("#SEND_TYPE2").combobox('clear');
            $("#fromDateDs [name='year']").val('');
            $("#fromDateDs [name='month']").val('');
            $("#fromDateDs [name='day']").val('');
            $("#fromDateDs [name='hour']").val('');
            $("#fromDateDs [name='minute']").val('');
            $("#fromDateDs [name='SEND_TIME']").val('');
            $("#old_time_zw").empty();
            $("#old_time").empty();
        }
    });
    $('#dialogDivJd').dialog({
        onClose: function(){
            $(".dxData:not(.hiddenTrClass)").remove();
            $("#SEND_TYPE4").combobox('clear');
            $("#SEND_TYPE4").combobox('select', 'SMS');
            $("#NODE_NAME1").combobox('clear');
            $("#NODE_NAME1").combobox('loadData', {});
            $("#OLD_NODE_NAME").val('');
        }
    });
    getJd();
});

function getJd(){
    $("#pageTable").datagrid({
        url: _basePath + "/base/interfaceTemplate/InterfaceTemplate!getJdData.action",
        pagination: true,// 分页
        rownumbers: true,//行数
        singleSelect: true,//单选模式
        nowrap:false,
        toolbar: '#pageFormJd',
        columns: [[{
            field: 'CODE',
            align: 'center',
            title: '操作',
            width: 4,
            formatter: function(value, rowData, rowIndex){
                if (rowData.TYPE == 2) {
                    return "<a href='javascript:void(0);' onclick=\"updateJd('" + rowIndex + "')\">修改</a>&nbsp;|&nbsp;" +
                    "<a href='javascript:void(0);' onclick=\"deleteJd('" +
                    value +
                    "')\">删除</a>";
                }
                else {
                    return "<a href='javascript:void(0);' onclick=\"updateJd('" + rowIndex + "')\">修改</a>"
                }
            }
        },{
            field: 'NODE_NAME',
            align: 'center',
            width: 6,
            title: '发送节点'
        }, {
            field: 'TYPE_NAME',
            align: 'center',
            width: 3,
            title: '节点类型'
        }, {
            field: 'SEND_TYPE',
            align: 'center',
            width: 9,
            title: '发送方式'
        }, {
            field: 'DX_NAME',
            align: 'center',
            width: 3,
            title: '发送对象'
        }, {
            field: 'CONTENT',
            align: 'center',
            width: 25,
            title: '内容'
        }, {
            field: 'NOTE',
            align: 'center',
            width: 5,
            title: '备注'
        }]],
        onLoadSuccess: function(data){
            if (data && data.rows.length > 0) {
                mergeCellsByField("pageTable", "NODE_NAME");
            }
			LK();
        }
    });
}

function LK(){
    var n = parseInt($("#n").val()) + 1;
    $("#n").val(n);
    $(".datagrid-cell-c" + n + "-NODE_NAME").attr('style', 'text-align: center; height: auto;');
    $(".datagrid-cell-c" + n + "-TYPE_NAME").attr('style', 'text-align: center; height: auto;');
    $(".datagrid-cell-c" + n + "-SEND_TYPE").attr('style', 'text-align: center; height: auto;');
    $(".datagrid-cell-c" + n + "-DX_NAME").attr('style', 'text-align: center; height: auto;');
    $(".datagrid-cell-c" + n + "-CONTENT").attr('style', 'text-align: center; height: auto;');
    $(".datagrid-cell-c" + n + "-NOTE").attr('style', 'text-align: center; height: auto;');
    $(".datagrid-cell-c" + n + "-CODE").attr('style', 'text-align: center; height: auto;');
}

function mergeCellsByField(tableID, colList){
    var ColArray = colList.split(",");
    var tTable = $('#' + tableID);
    var TableRowCnts = tTable.datagrid("getRows").length;
    var tmpA;
    var tmpB;
    var PerTxt = "";
    var CurTxt = "";
    var alertStr = "";
    for (var j = ColArray.length - 1; j >= 0; j--) {
        // 当循环至某新的列时，变量复位。
        PerTxt = "";
        tmpA = 1;
        tmpB = 0;
        // 从第一行（表头为第0行）开始循环，循环至行尾(溢出一位)
        for (var i = 0; i <= TableRowCnts; i++) {
            if (i == TableRowCnts) {
                CurTxt = "";
            }
            else {
                CurTxt = tTable.datagrid("getRows")[i][ColArray[j]];
            }
            if (PerTxt == CurTxt) {
                tmpA += 1;
            }
            else {
                tmpB += tmpA;
                tTable.datagrid('mergeCells', {
                    index: i - tmpA,
                    field: 'NODE_NAME',
                    rowspan: tmpA,
                    colspan: null
                });
                tTable.datagrid('mergeCells', {
                    index: i - tmpA,
                    field: 'TYPE_NAME',
                    rowspan: tmpA,
                    colspan: null
                });
                tTable.datagrid('mergeCells', {
                    index: i - tmpA,
                    field: 'SEND_TYPE',
                    rowspan: tmpA,
                    colspan: null
                });
                tTable.datagrid('mergeCells', {
                    index: i - tmpA,
                    field: 'CODE',
                    rowspan: tmpA,
                    colspan: null
                });
                tmpA = 1;
            }
            PerTxt = CurTxt;
        }
    }
}

function changeTab(tabThis){
    $(".tab01-nav_active").attr("class", "tab01-nav");
    $(tabThis).attr("class", "tab01-nav_active");
    var pageType = $(".tab01-nav_active").attr("page");
    if (pageType == "jddy") {
        $("#pageFormDs").hide();
        $("#pageFormJd").show();
        getJd();
    }
    else 
        if (pageType == "dspl") {
            $("#pageFormDs").show();
            $("#pageFormJd").hide();
            $("#pageTable").datagrid({
                url: _basePath + "/base/interfaceTemplate/InterfaceTemplate!getDsData.action",
                pagination: true,// 分页
                rownumbers: true,//行数
                singleSelect: true,//单选模式
                nowrap:false,
                toolbar: '#pageFormDs',
                columns: [[{
                    field: 'ID',
                    align: 'center',
                    title: '操作',
                    width: 1,
                    formatter: function(value, rowData, rowIndex){
                        return "<a href='javascript:void(0);' onclick=\"updateDs(" + rowIndex + ")\">修改</a>&nbsp;|&nbsp;" +
                        "<a href='javascript:void(0);' onclick=\"deleteDs('" +
                        value +
                        "','" +
                        rowData.NAME +
                        "')\">删除</a>";
                    }
                },{
                    field: 'NAME',
                    align: 'center',
                    width: 1,
                    title: '模版名称'
                }, {
                    field: 'SEND_TYPE',
                    align: 'center',
                    width: 1,
                    title: '发送方式',
					nowrap:'false'
                }, {
                    field: 'CONTENT',
                    align: 'center',
                    width: 1,
                    title: '内容',
					nowrap:'false'
                }, {
                    field: 'SQL_NOTE',
                    align: 'center',
                    width: 1,
                    title: 'SQL说明'
                }, {
                    field: 'NOTE',
                    align: 'center',
                    width: 1,
                    title: '备注',
					nowrap:'false'
                }, {
                    field: 'SEND_TIME',
                    align: 'center',
                    width: 1,
                    title: '发送时间'
                }]],
                onLoadSuccess: function(data){
                    LK();
                }
            });
        }
}

function clearJd(){
    $("#NODE_NAME").combobox('select', '');
    $("#DX_NAMES").combobox('select', '');
    $("#SEND_TYPE").combobox('select', '');
    $("#pageFormJd [name='CONTENTS']").val('');
}


//搜索JD
function conditionsSelectJd(){
    $("#pageTable").datagrid({
        onLoadSuccess: function(data){
            if (data && data.rows.length > 0) {
                mergeCellsByField("pageTable", "NODE_NAME");
            }
            LK();
        }
    });
    $('#pageTable').datagrid('load', {
        NODE_NAME: $("#NODE_NAME").combobox("getValue"),
        DX_NAMES: $("#DX_NAMES").combobox("getValue"),
        SEND_TYPE: $("#SEND_TYPE").combobox("getValue"),
        CONTENTS: $("#pageFormJd [name='CONTENTS']").val()
    });
}

//搜索ds
function conditionsSelectDs(){
    $('#pageTable').datagrid('load', {
        NAME: $("#pageFormDs [name='NAME']").val(),
        SEND_TYPE: $("#SEND_TYPE1").combobox("getValue"),
        CONTENT: $("#pageFormDs [name='CONTENT']").val(),
        NOTE: $("#pageFormDs [name='NOTE']").val()
    });
}

function clearDs(){
    $("#pageFormDs [name='NAME']").val('');
    $("#pageFormDs [name='NOTE']").val('');
    $("#pageFormDs [name='CONTENT']").val('');
    $("#SEND_TYPE1").combobox('select', '');
}

function deleteJd(CODE){
    $.messager.confirm('提 示', '确定删除发送节点为【' + CODE + '】的模版吗?', function(r){
        if (r) {
            jQuery.ajax({
                async: true,
                url: "InterfaceTemplate!deleteJd.action",
                data: "NODE_NAME=" + CODE + "&_dateTime=" + new Date(),
                type: "post",
                dataType: "json",
                success: function(json){
                    if (json.flag) {
                        $('#pageTable').datagrid('load');
                    }
                    else {
                        $.messager.alert('提示', '删除失败！', 'warning');
                    }
                },
                error: function(e){
                    $.messager.alert('提示', '删除失败！', 'warning');
                }
            })
        }
    })
}

function deleteDs(ID, NAME){
    $.messager.confirm('提 示', '确定删除模版【' + NAME + '】吗?', function(r){
        if (r) {
            jQuery.ajax({
                async: true,
                url: "InterfaceTemplate!deleteDs.action",
                data: "ID=" + ID + "&_dateTime=" + new Date(),
                type: "post",
                dataType: "json",
                success: function(json){
                    if (json.flag) {
                        $('#pageTable').datagrid('load');
                    }
                    else {
                        $.messager.alert('提示', '删除失败！', 'warning');
                    }
                },
                error: function(e){
                    $.messager.alert('提示', '删除失败！', 'warning');
                }
            })
        }
    })
}

//添加页面
function addJd(){
    var temp = new Array();
    jQuery.ajax({
        url: _basePath + "/base/interfaceTemplate/InterfaceTemplate!getNodeNameList.action?_dateTime=" + new Date(),
        dataType: "json",
        success: function(json){
            var data = json.data;
            if (data != null && data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    if (i == 0) {
                        temp.push({
                            "value": data[i].FLAG,
                            "text": data[i].FLAG,
                            "selected": true
                        });
                    }
                    else {
                        temp.push({
                            "value": data[i].FLAG,
                            "text": data[i].FLAG
                        });
                    }
                }
                $('#NODE_NAME1').combobox('loadData', temp);
            }
        }
    });
    $("#dialogDivJd").show();
    $("#dialogDivJd").dialog({
        title: '添 加'
    });
    $("#dialogDivJd").dialog('open');
}

//添加页面
function addDs(){
    $("#dialogDivDs").show();
    $("#dialogDivDs").dialog({
        title: '添 加'
    });
    $("#dialogDivDs").dialog('open');
    $("#SEND_TYPE2").combobox('clear');
    $("#SEND_TYPE2").combobox('select', 'SMS');
}

//修改页面
function updateDs(rowIndex){
    $('#pageTable').datagrid('selectRow', rowIndex);
    var rowData = $('#pageTable').datagrid('getSelected');
    $("#fromDateDs [name='ID']").val(rowData.ID);
    $("#fromDateDs [name='OLDNAME']").val(rowData.NAME);
    $("#fromDateDs [name='NAME']").val(rowData.NAME);
    $("#fromDateDs [name='NOTE']").val(rowData.NOTE);
    $("#fromDateDs [name='SQL_TEXT']").val(rowData.SQL_TEXT);
    $("#fromDateDs [name='SQL_NOTE']").val(rowData.SQL_NOTE);
    $("#fromDateDs [name='CONTENT']").val(rowData.CONTENT);
    $("#old_time_zw").append('当前发送时间为：');
    $("#old_time").append(rowData.SEND_TIME);
    $("#SEND_TYPE2").combobox('clear');
    if (rowData.WXQ == '1') {
        $("#SEND_TYPE2").combobox('select', 'WXQ');
    }
    if (rowData.WXF == '1') {
        $("#SEND_TYPE2").combobox('select', 'WXF');
    }
    if (rowData.SMS == '1') {
        $("#SEND_TYPE2").combobox('select', 'SMS');
    }
    if (rowData.EMAIL == '1') {
        $("#SEND_TYPE2").combobox('select', 'EMAIL');
    }
    $("#dialogDivDs").show();
    $("#dialogDivDs").dialog({
        title: '修 改'
    });
    $("#dialogDivDs").dialog('open');
}

function closeDialogDs(){
    $('#dialogDivDs').dialog('close');
}

function closeDialogJd(){
    $('#dialogDivJd').dialog('close');
}

//添加and修改保存Ds
function saveDs(){
    $('#btnbc_saveDs').linkbutton('disable');
	if(!$("#fromDateDs [name='year']").val() && !$("#fromDateDs [name='month']").val() && !$("#fromDateDs [name='day']").val() && !$("#fromDateDs [name='hour']").val() && !$("#fromDateDs [name='minute']").val()){
		$("#fromDateDs [name='SEND_TIME']").val($("#old_time").html());
	}else{
	    $("#fromDateDs [name='SEND_TIME']").val($("#fromDateDs [name='year']").val()+'-'+$("#fromDateDs [name='month']").val()+'-'+$("#fromDateDs [name='day']").val()+' '+$("#fromDateDs [name='hour']").val()+':'+$("#fromDateDs [name='minute']").val());
	}
    var data = '';
    $("#fromDateDs [name='SEND_TYPE']").each(function(){
        data = data + '&' + $(this).val() + '=1';
    })
    var url = '';
    if ($("#fromDateDs [name='ID']").val()) {
        url = 'InterfaceTemplate!doUpdateDs.action?_dateTime=' + new Date();
    }
    else {
        url = 'InterfaceTemplate!doAddDs.action?_dateTime=' + new Date();
    }
    if ($("#fromDateDs [name='NAME']").validatebox('isValid')) {
        var NAME = $("#fromDateDs [name='NAME']").val();
        var url1 = '';
        var OLDNAME = '';
        if ($("#fromDateDs [name='ID']").val()) {
            url1 = 'InterfaceTemplate!checkNameUpdate.action?_dateTime=' + new Date();
            OLDNAME = $("#fromDateDs [name='OLDNAME']").val();
        }
        else {
            url1 = 'InterfaceTemplate!checkNameAdd.action?_dateTime=' + new Date();
        }
        jQuery.ajax({
            url: url1,
            data: "NAME=" + NAME + "&OLDNAME=" + OLDNAME,
            type: "post",
            dataType: "json",
            success: function(json){
                console.info(json);
                if (json.flag) {
                    $.messager.alert('提示', '该名称已被占用！', 'warning');
                    $('#btnbc_saveDs').linkbutton('enable');
                }
                else {
                    jQuery.ajax({
                        url: url,
                        data: "param=" + getFromData("#fromDateDs") + data,
                        type: "post",
                        dataType: "json",
                        success: function(json){
                            if (json.flag) {
                                $('#pageTable').datagrid('load', {
                                    ID: json.data.ID
                                });
                                closeDialogDs();
                            }
                            else {
                                $.messager.alert('提示', '保存失败，请与管理员联系！', 'warning');
                            }
                            $('#btnbc_saveDs').linkbutton('enable');
                        }
                    });
                }
            }
        });
    }
    else {
        $.messager.alert('提示', '请填写必填项！', 'warning');
        $('#btnbc_saveDs').linkbutton('enable');
    }
}

function addTr(){
    $("#table").append($(".hiddenTrClass").clone().removeClass("hiddenTrClass"));
}

//删除Tr
function deleteTr(e){
    $(e).parents("tr").remove();
}

function saveJd(){
    $('#btnbc_saveJd').linkbutton('disable');
	if(!$("#NODE_NAME1").combobox('getValue')){
		$.messager.alert('提示', '请选择发送节点！');
		$('#btnbc_saveJd').linkbutton('enable');
		return false;
	}
    var saveRecord = new Array();
	var dxFlag = false;
    $(".dxData:not(.hiddenTrClass)").each(function(){
		dxFlag = true;
        var temp = {};
        temp.SQL_ID = $(this).find("[name=SQL_ID]").val();
        temp.CONTENT = $(this).find("[name=CONTENT]").val();
        temp.NOTE = $(this).find("[name=NOTE]").val();
        saveRecord.push(temp);
    });
	if(!dxFlag){
		$.messager.alert('提示', '请添加发送对象！');
		$('#btnbc_saveJd').linkbutton('enable');
		return false;
	}
    var SMS = '2';
    var WXQ = '2';
    var WXF = '2';
    var EMAIL = '2';
    $("#fromDateJd [name=SEND_TYPE]").each(function(){
        if ($(this).val() == 'SMS') {
            SMS = '1';
        }
        if ($(this).val() == 'WXQ') {
            WXQ = '1';
        }
        if ($(this).val() == 'WXF') {
            WXF = '1';
        }
        if ($(this).val() == 'EMAIL') {
            EMAIL = '1';
        }
    })
    var addData = {
        OLD_NODE_NAME: $("#OLD_NODE_NAME").val(),
        NODE_NAME: $("#NODE_NAME1").combobox('getValue'),
        SMS: SMS,
        WXQ: WXQ,
        WXF: WXF,
        EMAIL: EMAIL,
        dxList: saveRecord
    };
    var alldata = JSON.stringify(addData);
    jQuery.ajax({
        url: _basePath + "/base/interfaceTemplate/InterfaceTemplate!doAddJd.action",
        type: "post",
        data: {
            "alldata": alldata
        },
        dataType: "json",
        success: function(json){
            if (json.flag) {
                $('#pageTable').datagrid('load');
                $.messager.alert('提示', '保存成功！');
                $('#btnbc_saveJd').linkbutton('enable');
                closeDialogJd();
                
            }
            else {
                $.messager.alert('提示', '保存失败，请与管理员联系！');
                $('#btnbc_saveJd').linkbutton('enable');
            }
        }
    });
}

//修改页面
function updateJd(rowIndex){
    $('#pageTable').datagrid('selectRow', rowIndex);
    var rowData = $('#pageTable').datagrid('getSelected');
    var NODE_NAME = rowData.NODE_NAME;
    $("#OLD_NODE_NAME").val(NODE_NAME);
    var temp = new Array();
    jQuery.ajax({
        url: _basePath + "/base/interfaceTemplate/InterfaceTemplate!getNodeNameList.action?_dateTime=" + new Date(),
        dataType: "json",
        async: false,
        success: function(json){
            var data = json.data;
            temp.push({
                "value": NODE_NAME,
                "text": NODE_NAME,
                "selected": true
            });
            if (data != null && data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    if (i == 0) {
                        temp.push({
                            "value": data[i].FLAG,
                            "text": data[i].FLAG
                        });
                    }
                    else {
                        temp.push({
                            "value": data[i].FLAG,
                            "text": data[i].FLAG
                        });
                    }
                }
            }
            $('#NODE_NAME1').combobox('loadData', temp);
        }
    });
    $("#SEND_TYPE4").combobox('clear');
    if (rowData.WXQ == '1') {
        $("#SEND_TYPE4").combobox('select', 'WXQ');
    }
    if (rowData.WXF == '1') {
        $("#SEND_TYPE4").combobox('select', 'WXF');
    }
    if (rowData.SMS == '1') {
        $("#SEND_TYPE4").combobox('select', 'SMS');
    }
    if (rowData.EMAIL == '1') {
        $("#SEND_TYPE4").combobox('select', 'EMAIL');
    }
    jQuery.ajax({
        url: _basePath + "/base/interfaceTemplate/InterfaceTemplate!getJdUpdateData.action",
        type: "post",
        data: "NODE_NAME=" + NODE_NAME,
        dataType: "json",
        success: function(json){
            var dxData = json.data;
            if (json.flag) {
                for (var j = 0; j < dxData.length; j++) {
					if(dxData[j].SQL_ID){
	                    var tr = $(".hiddenTrClass").clone().removeClass("hiddenTrClass");
	                    $(tr).find("[name=SQL_ID]").val(dxData[j].SQL_ID);
	                    $(tr).find("[name=CONTENT]").val(dxData[j].CONTENT);
	                    $(tr).find("[name=NOTE]").val(dxData[j].NOTE);
	                    $("#table").append(tr);
					}
                }
            }
        }
    });
    $("#dialogDivJd").show();
    $("#dialogDivJd").dialog({
        title: '修 改'
    });
    $("#dialogDivJd").dialog('open');
}
