$(function(){
	$("input[name='WDQLX_MONEY']").val(RoundNumber(($("input[name='sy_money']").val()-$("input[name='WH_BJ']").val()),2));
});
function RoundNumber(num, pos)  
{  
    return Math.round(num * Math.pow(10, pos)) / Math.pow(10, pos);  
}  
/**
 * 添加分期回购单
 * @return
 */
function toSaveBuyBack(){
	var param = getFormParamFormat("buyBackForm");
	var ACCOUNT_DATE = param.ACCOUNT_DATE;
	var re= /^\d{4}-\d{2}-\d{2}$/g ;// 
	if(!re.test(ACCOUNT_DATE)){
		$.messager.show({
			title:'操作错误提示',
			msg:'请正确填写结算日',	
			showType:'show'
		});
		return false;
	}
	jQuery.ajax({
        type: "POST",
        dataType: "json",
		async:false,
	    url: _basePath + "/buyBack/BuyBack!toSaveBuyBack.action",
	    data: "json="+JSON.stringify(param),
	    success: function(json){				
			alert(json.msg);
			window.location.href=_basePath + "/buyBack/BuyBack!buyBackManagement.action"
	    }
	});
}

/**
 * 修改回購申請
 * @return
 */
function toSaveBuyBackApp(){
	var param = getFormParamFormat("buyBackForm");
	var ACCOUNT_DATE = param.ACCOUNT_DATE;
	var re= /^\d{4}-\d{2}-\d{2}$/g ;// 
	if(!re.test(ACCOUNT_DATE)){
		$.messager.show({
			title:'操作错误提示',
			msg:'请正确填写结算日',	
			showType:'show'
		});
		return false;
	}
	jQuery.ajax({
        type: "POST",
        dataType: "json",
		async:false,
	    url: _basePath + "/buyBack/BuyBack!doSavebuyBackReturnApp.action",
	    data: "json="+JSON.stringify(param),
	    success: function(json){				
			alert(json.msg);	
	    }
	});
}

/**
 * 分期回购测算
 * @return
 */
function calculate(){
	var param = getFormParamFormat("buyBackForm");
	var num = param.lease_term;
	var re =  /^[0-9]*[1-9][0-9]*$/;
	if(!re.test(num)){
		$.messager.show({
			title:'操作错误提示',
			msg:'请正确填写分期回购期次',	
			showType:'show'
		});
		return false;
	}
	jQuery.ajax({
        type: "POST",
        dataType: "json",
		async:false,
        url: _basePath + "/buyBack/BuyBack!buyBackStagesCalculate.action",
        data: param,
        success: function(msg){
			 //点击保存后下一步和测算按钮不可用
            $('#nex_').linkbutton('enable');
			$("#dataDiv").show();
	        $('#pageTable').datagrid();
	            var data = {
	                "flag": true,
	                "total": msg.data.ln.length,
	                "rows": msg.data.ln
	            };
	        $('#pageTable').datagrid("loadData", data);
			//console.info(msg);
			$("input[name='WS_YQ']").val(msg.data.fine);
			$("input[name='LG_DKJE']").val(msg.data.Leave);
			$("input[name='BZJDk']").val(msg.data.Deposit);
			$("input[name='dbBZJDk']").val(msg.data.DBDeposit);			
        }
    });
	
}

function buyBackSubmit(){
	var param = getFormParamFormat("buyBackForm");
	var data_ = $('#pageTable').datagrid("getData");
	param.data_ = data_.rows;
	jQuery.ajax({
        type: "POST",
        dataType: "json",
		async:false,
        url: _basePath + "/buyBack/BuyBack!buyBackStagesSave.action",
        data: "json="+JSON.stringify(param),
        success: function(msg){
			//console.info(msg.msg);
			alert(msg.data);
        }
    });
}

//选择判断是否抵扣留购价， 保证金， DB保证金
function tochoseDk(val1, val2){
	if(val2 == 'lgj'){	
		if(val1=='0'){
			return $("#LG_DKJE").val($("#LIUGOUJIA").val());
		}else {
			return $("#LG_DKJE").val("0.00");
		}
	}else if(val2 == 'bzj'){
		if(val1=='0'){
			$("#BZJ").val($("#BZJ_MONEY").val());
			return $("#BZJ").val($("#BZJ_MONEY").val());
		}else {
			return $("#BZJ").val("0.00");
		}
	}else if(val2 == 'db_bzj'){
		if(val1=='0'){
			return $("#dbBZJDk").val($("#dbBZJ_MONEY").val());
		}else {
			return $("#dbBZJDk").val("0.00");
		}
	}
}
function get_custName(param){
	var pv = $(param).val();
	if(pv.length<2){
		return null;
	}
	//去后台验证是否有供应商
	jQuery.ajax({
        type: "POST",
        dataType: "json",
		async:false,
        url: _basePath + "/buyBack/BuyBack!getCustName.action",
        data: "pv="+pv,
        success: function(msg){
			//console.info(msg.data);
			var persons = msg.data;
			if(persons.length>=2){
				var str="";
				for(var i=0;i<persons.length;i++){
					var person = persons[i];
					str = str+"<li><input id=\""+person.ID+"\" type=\"radio\" name =\"buyCust\" value=\""+person.ID+"\" ><label for=\""+person.ID+"\">"+person.NAME+"</label></li>";
				}
				$("#dialog_").append(str);
				$("#dialog_").dialog({  
					modal:true,
					buttons: [{
						text:'Ok',
						iconCls:'icon-ok',
						handler:function(){
							var select = $("input[name='buyCust']:checked");
							//$("input[name='PAYEE_NAME']").val($("label[for='"+select.val()+"']").text());
							//$("input[name='PAYEE_NAME']").val($("label[for='"+select.val()+"']").text()+"||"+select.val());
							$("input[name='PAYEE_NAME']").val(select.val());
							$("input[name='PAYEE_NAME']").after($("label[for='"+select.val()+"']").text());
							$("#dialog_").dialog('close',{forceClose:true});
							$("#dialog_").empty();
						}
					}]
				})
			}else if(persons.length==1){
				//$("input[name='PAYEE_NAME']").val(persons[0].NAME+"||"+persons[0].ID);
				$("input[name='PAYEE_NAME']").val(persons[0].ID);
				$("input[name='PAYEE_NAME']").after(persons[0].NAME);
			}else {
				$("input[name='PAYEE_NAME']").val("");
			}
        }
    });
}