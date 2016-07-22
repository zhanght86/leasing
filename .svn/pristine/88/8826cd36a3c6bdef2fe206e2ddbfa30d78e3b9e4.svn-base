//点击测算
function calculate(){
	//点击测算以后开始期次不能改变
	$("#changeIssue").removeAttr("onchange");
	$("#changeIssue").attr("readonly","readonly");
	var flag_ = check_();
	if(!flag_) return false;//调用验证方法
	var data_ = $("#changePay").serialize();
	if(getUrl_().code==8){//如果是不等额的需要添加些参数
		var editData = getEditRows();
		if(!editData){//如果确认框选择了false则不让往下走
			return false;
		}
		data_ = data_ + "&EditRows="+JSON.stringify(editData);	
	}
    jQuery.ajax({
        type: "POST",
        dataType: "json",
        url: _basePath + "/pay/PayTask!calculateTest.action",
        data: data_,
        success: function(msg){
            var data = msg.data.ln;
            //把变更后的年利率赋值给input
            //var END_YEAR_INTEREST = $("#END_YEAR_INTEREST").val(formatNumber(msg.data.END_YEAR_INTEREST * 100, '0.00'));
            var END_YEAR_INTEREST = $("#END_YEAR_INTEREST").val(formatNumber(msg.data.END_YEAR_INTEREST, '0.0000'));
            //点击测算后下一步按钮可用
            $('#nex_').linkbutton('enable');
            var footer_ = [{
                PAY_DATE: "合计：",
                PMTzj: totalColumn($(data), "PMTzj"),
                zj: totalColumn($(data), "zj"),
                bj: totalColumn($(data), "bj"),
                lx: totalColumn($(data), "lx")
            }];
            var data = {
                flag: msg.flag,
                total: data.length,
                rows: data,
                footer: footer_
            }
            //alert( "Data Saved: ");
            $('#pageTable').datagrid("loadData", data);
            $('#pageTable').datagrid({
                onClickRow: function(rowIndex, rowData){
					if(getUrl_().code==8) onClickRow_(rowIndex, rowData);
                }
            });
        }
    })
}

//点击下一步（保存测试结果）
function nex_(){
	if(getUrl_().status==6){
		var editData = getEditRows();
		if(!editData){//如果确认框选择了false则不让往下走
			return false;
		}
	}
	var myData = $('#pageTable').datagrid('getRows');
	var data_ = "myData="+JSON.stringify(myData)+"&otherInsure="+$("#otherInsure").val()+"&otherAssure="+$("#otherAssure").val()
		+"&otherPoundage="+$("#otherPoundage").val()+"&END_YEAR_INTEREST="+$("#END_YEAR_INTEREST").val()
		+"&PAYLIST_CODE="+$("#PAYLIST_CODE").val()
		+"&changeIssue="+$("#changeIssue").val()
		+"&lease_term="+$("#shortLengthenIssue").val()
		+"&code_="+$("#code_").val()
		+"&status_="+$("#status_").val()
	    +"&ID="+$("#ID_").val();
	    jQuery.ajax({
        type: "POST",
        dataType: "json",
		async:false,
        url: _basePath + "/pay/PayTask!calculateSave.action",
        data: data_,
        success: function(msg){
			$.messager.alert("提示","发起流程成功！",'info',function(){
				$.messager.alert("提示",msg.msg+msg.data,"info",function(){
					 //点击保存后下一步和测算按钮不可用
		            $('#nex_').linkbutton('disable');
					$('#calculate_').linkbutton('disable');
					window.location.href=_basePath+"/pay/PayTask!changePayRent.action";
			});	
        });
	    }
    })
}

//不等额修改行
var editIndex = undefined;
function onClickRow_(index, rowData){
	var pv=$("#changeIssue").val();
	pv = parseInt(pv)-2;
	if(index>pv){//只有在开始变更期次以后的数据可以修改
		;
	}
	else{
		return false;
	}
	var grid_data = $('#pageTable').datagrid('getData');
	//如果是前三行或者后三行都不让修改(status!='6'提前结清的话所有期次都可以改)
	//if((index<3||index>grid_data.rows.length-4)&&getUrl_().status!='6') return false;
	//status!='6'正常结清结清的话所有期次都不能改
	if(getUrl_().status==3) return false;
    if (editIndex != index) {
        if (endEditing()) {
            $('#pageTable').datagrid('selectRow', index).datagrid('beginEdit', index);
            editIndex = index;
        }
        else {
            $('#pageTable').datagrid('selectRow', editIndex);
        }
    }
    
}
function endEditing(){
    if (editIndex == undefined) {
        return true;
    }
    if ($('#pageTable').datagrid('validateRow', editIndex)) {
        $('#pageTable').datagrid('endEdit', editIndex);
		/*手动修改了本金和利息之后自动改变相应的租金值*/
		var data_ = $('#pageTable').datagrid('getRows');
        var rows_ =data_[editIndex];
        //var rows_last =data_[editIndex-1];
		if($.trim(rows_.bj).length<=0){
			rows_.bj = 0;
		}
		if($.trim(rows_.lx).length<=0){
			rows_.lx = 0;
		}
		rows_.bj = formatNumber(parseFloat(rows_.zj)-parseFloat(rows_.lx),'0.00');
		//rows_.PMTzj = formatNumber(parseFloat(rows_.bj)+parseFloat(rows_.lx),'0.00');
		//rows_.sybj = formatNumber(parseFloat(rows_last.sybj) - parseFloat(rows_.bj),'0.00');
		$('#pageTable').datagrid('updateRow',{index:editIndex,row:rows_});
		
        editIndex = undefined;
        return true;
    }
    else {
        return false;
    }
}

//不等额的时候修改了的行数
function getEditRows(){
	//var rows = $('#pageTable').datagrid('getChanges');
	var resultData = new Array();
	var rows2 = $('#pageTable').datagrid('getChanges');
	var allData = $('#pageTable').datagrid("getRows");
	$(allData).each(function(){
		var flag = false;
		if(this.lock=="yes"){
			for(var i=0;i<rows2.length;i++){
				if(this.qc == rows2[i].qc){
					flag = true;
				}
			}
			if(!flag){
				resultData.push(this);
			}
		}
	})
	$(rows2).each(function(){
		resultData.push(this);
	})
	//console.info(resultData);
	if(!confirm("您共修改了"+resultData.length+"期租金,确认往下执行吗?")){
		return false;
	}
	//alert(rows2.length+' rows are changed!'); 
	return resultData;
}
//测算的时候验证操作
function check_(){
	if(!$("#changePay").form('validate')){
		$.messager.show({
			title:'操作错误提示',
			msg:'请填写带*必填项',
			showType:'show'
		});
		return false;
	}
	if($("#changeIssue").val().length<=0){
		$.messager.show({
			title:'操作错误提示',
			msg:'请选择"开始变更期次"',
			showType:'show'
		});
		return false;
	}
	return true;
}

//租前息预览测算
function calculate_rent(){
	var flag_ = rent_check_();
	if(!flag_) return false;//调用验证方法
	var data_ = $("#changePay").serialize();
    jQuery.ajax({
        type: "POST",
        dataType: "json",
        url: _basePath + "/pay/PayTask!rentBeforeCalculation.action",//calculateTest
        data: data_,
        success: function(msg){
            var data = msg.data.ln;
            //把变更后的年利率赋值给input
              $("#LEASE_TERM").val(msg.data.LEASE_TERM);
            //点击测算后下一步按钮可用
            $('#rent_nex_').linkbutton('enable');
            var footer_ = [{
                PAY_DATE: "合计：",
                PMTzj: totalColumn($(data), "PMTzj"),
                zj: totalColumn($(data), "zj"),
                bj: totalColumn($(data), "bj"),
                lx: totalColumn($(data), "lx")
            }];
            var data = {
                flag: msg.flag,
                total: data.length,
                rows: data,
                footer: footer_
            }
            //alert( "Data Saved: ");
            $('#pageTable').datagrid("loadData", data);
            $('#pageTable').datagrid({
                onClickRow: function(rowIndex, rowData){
					if(getUrl_().code==8) onClickRow_(rowIndex, rowData);
                }
            });
        }
    })
}
//租前息点击下一步
function rent_nex_(){
	var myData = $('#pageTable').datagrid('getRows');
	var data_ = "myData="+JSON.stringify(myData)
		+"&TIAN_YEAR_INTEREST="+$("#TIAN_YEAR_INTEREST").val()
		+"&YEAR_INTEREST="+$("#YEAR_INTEREST").val()
		+"&PAYLIST_CODE="+$("#PAYLIST_CODE").val()
		+"&PAYLIST_CODE_ZQX="+$("#PAYLIST_CODE_ZQX").val()
		+"&LEASE_TERM="+$("#LEASE_TERM").val()
		+"&LEASE_PERIOD="+$("#changePay").find($("select[name=LEASE_PERIOD]")).attr("selected",true).val()
		+"&START_DATE="+$("#changePay").find($("input[name=START_DATE]")).val()
		+"&END_DATE="+$("#changePay").find($("input[name=END_DATE]")).val()
		+"&H_START_DATE="+$("#changePay").find($("input[name=H_START_DATE]")).val()
		+"&LEASE_TOPRIC="+$("#LEASE_TOPRIC").val()
		+"&ID="+$("#ID_").val();
	    jQuery.ajax({
        type: "POST",
        dataType: "json",
		async:false,
        url: _basePath + "/pay/PayTask!rentBeforeSave.action",
        data: data_,
        success: function(msg){
			alert(msg.data);
			 //点击保存后下一步和测算按钮不可用
            $('#nex_').linkbutton('disable');
			$('#calculate_').linkbutton('disable');
			window.location.href=_basePath+"/pay/PayTask!changePayRent.action";
        }
    });
}
function rent_check_(){
	if(!$("#START_DATE").form('validate')){
		$.messager.show({
			title:'操作错误提示',
			msg:'请填写带*必填项',
			showType:'show'
		});
		return false;
	}
	if(!$("#END_DATE").form('validate')){
		$.messager.show({
			title:'操作错误提示',
			msg:'请填写带*必填项',
			showType:'show'
		});
		return false;
	}
	if(!$("#H_START_DATE").form('validate')){
		$.messager.show({
			title:'操作错误提示',
			msg:'请填写带*必填项',
			showType:'show'
		});
		return false;
	}
	if(!$("#LEASE_TOPRIC").form('validate')){
		$.messager.show({
			title:'操作错误提示',
			msg:'请填写带*必填项',
			showType:'show'
		});
		return false;
	}
	if(!$("#TIAN_YEAR_INTEREST").form('validate')){
		$.messager.show({
			title:'操作错误提示',
			msg:'请填写带*必填项',
			showType:'show'
		});
		return false;
	}
	return true;
}


