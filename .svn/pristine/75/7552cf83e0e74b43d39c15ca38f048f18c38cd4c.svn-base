/**
 * 选择可视用户
 */
var url = "";
var arrChk = "";
//保存
function save2(){
	url = "";
	url = "PolicyPublish!chooseReader.action";
	arrChk = "";//先清空
	$(":checkbox[checked]").each(function(){arrChk+=this.value +',';});//遍历被选中CheckBox元素的集合 得到Value值
	if(arrChk.length>0){
		//如果有选中项,去掉末尾的 ","
		arrChk = arrChk.substr(0,arrChk.length-1);
	}
	url += "?arrOrg=" + arrChk + "&ID=" + document.getElementById("policy_id_forOrg").value; //传递参数arrOrg
	alert("请耐心等待程序后台执行完毕,执行完毕后会有相应提示!");
	$('#fm2').form('submit',{
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
            }else{
            	alert("执行完毕");
            }
        }
    });
}

//保存
function saveByGroup(){
	url = "";
	url = "PolicyPublish!chooseReader.action";
	arrChk = "";//先清空
	$(":checkbox[checked]").each(function(){arrChk+=this.value +',';});//遍历被选中CheckBox元素的集合 得到Value值
	if(arrChk.length>0){
		//如果有选中项,去掉末尾的 ","
		arrChk = arrChk.substr(0,arrChk.length-1);
	}
	url += "?arrOrg=" + arrChk + "&IFORG=IFORG" + "&ID=" + document.getElementById("policy_id_forOrg").value; //传递参数arrOrg
	alert("请耐心等待程序后台执行完毕,执行完毕后会有相应提示!");
	$('#fm2').form('submit',{
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
            }else{
            	alert("执行完毕");
            }
        }
    });
}

//清空clear2
function clear2(){
	$(":checkbox").removeAttr("checked");
	arrChk = "";
		alert("清空了所有选中效果");
}