/**
 * 款项上传
 * 
 * @author hanxl
 */
function upload(){
	// 检查是否有上传文件
	if (document.all['upload_file'].value == "") {
		alert("请选择上传文件");
		return false;
	}
	var insureFeeFile = document.all['upload_file'].value;
	var extType = insureFeeFile.substr(insureFeeFile.lastIndexOf(".") + 1);
	if (!(extType == 'xls')) {
		alert("对不起，只允许上传xls文件！");
		return false;
	}
	//上传文件
	$('#fm_file').form('submit',{
        url: _basePath+"/insureSettlement/InsureSettlementUpload!uploadTemplate.action",
        success: function(result){
			var result = eval('('+result+')');//解析result
			alert(result.msg);
			$('#uploadbtn').linkbutton('disable');//上传按钮置为不可用
        	$('#dg').datagrid('reload');    //刷新
        }
    });
}

function downloadTemplate(){
	$('#fm_template').form('submit',{
        url: _basePath+"/insureSettlement/InsureSettlementUpload!downloadTemplate.action",
        onSubmit: function(){
        },
        success: function(result){
        }
    });
}
