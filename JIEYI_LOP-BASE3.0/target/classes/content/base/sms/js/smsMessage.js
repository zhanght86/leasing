/*
 * 模板发送
 */
function uploadExcel(){
	var url = "SmsMessage!uploadExcel.action";
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
}

/*
 *在线发送 
 */
function sendOnline(){
	var PERSONS = $('#PERSONS').val();
	var CONTENTS = $('#CONTENTS').val();
	var DEPARTMENT = $('#DEPARTMENT').val();
	var data = "?PERSONS="+PERSONS+"&CONTENTS="+CONTENTS+"&DEPARTMENT="+DEPARTMENT;
	var url = "SmsMessage!sendOnline.action"+data;
	
	$('#smsOnlineForm').form('submit',
		{
            url:url,
            onSubmit: function()
			{
				if(PERSONS.length==0)
				{
					$.messager.alert('提示','请添加发送人员！','');
					return false;
				}
				if(CONTENTS.length==0)
				{
					$.messager.alert('提示','请填写要发送的内容！','');
					return false;
				}
            },
			success:function(data)
			{
			 	var data = eval('(' + data + ')');
				$.messager.alert('',data.msg,'');
				$("#PERSONS").val("");
			}
     });
}

function putAll(){
	var p = $('#personsAll').val();
	$('#PERSONS').val(p);
}

function addPerson(){
	var p1 = $('#personList').combobox('getValues');
	var p2 = $('#PERSONS').val();
	if(p2==""){
		p2 = p1;
	}else{
		p2 += ","+p1;
	}
	$('#PERSONS').val(p2);
}
function clean(){
	$("#PERSONS").val("");
}

