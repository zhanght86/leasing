/*$(function(){
	var uploader = WebUploader.create({
	    // swf文件路径
	    swf: '/js/webuploader/Uploader.swf',
	    // 文件接收服务端。
	    server: 'creditReports/CreditReports!uploadFile.action',
	    // 选择文件的按钮。可选。
	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	    pick: '#picker',
	    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
	    resize: false
	});
	
	// 当有文件被添加进队列的时候
	uploader.on( 'fileQueued', function( file ) {
	    $('#thelist').append( '<div id="' + file.id + '" class="item">' +
	        '<h4 class="info">' + file.name + '</h4>' +
	        '<p class="state">等待上传...</p>' +
	    '</div>' );
	});
	// 文件上传过程中创建进度条实时显示。
	uploader.on( 'uploadProgress', function( file, percentage ) {
	    var $li = $( '#'+file.id ),
	        $percent = $li.find('.progress .progress-bar');
	
	    // 避免重复创建
	    if ( !$percent.length ) {
	        $percent = $('<div class="progress progress-striped active">' +
	          '<div class="progress-bar" role="progressbar" style="width: 0%">' +
	          '</div>' +
	        '</div>').appendTo( $li ).find('.progress-bar');
	    }
	
	    $li.find('p.state').text('上传中');
	
	    $percent.css( 'width', percentage * 100 + '%' );
	});
	
	uploader.on( 'uploadSuccess', function( file ) {
	    $( '#'+file.id ).find('p.state').text('已上传');
	});

	uploader.on( 'uploadError', function( file ) {
	    $( '#'+file.id ).find('p.state').text('上传出错');
	});

	uploader.on( 'uploadComplete', function( file ) {
	    $( '#'+file.id ).find('.progress').fadeOut();
	});
});*/










function openUpload(pro_id){
	$('#PROJECT_ID').val(pro_id);
	$('#uploadFileDialog').show();
	$('#uploadFileDialog').dialog({
		title:'上传文件',
		width:600,
		height:300,
		closed:false,
		cache: false,
		buttons:[{
			text:'上传',
			iconCls:'icon-up',
			handler:function(){
//				$('#uploadFileForm').submit();
				saveUpLoad();
			}
		},{
            text:'关 闭',
			iconCls:'icon-cancel',
            handler:function(){
               $("#uploadFileDialog").dialog('close');
            }
        }]
	});
}

function saveUpLoad(){
	var file=$("#file_upload").val();
	var project_id=$("#PROJECT_ID").val();
	if(file==null||file==""){
		alert("请选择要上传的文件");
		return false;
	}else{
		jQuery.ajaxFileUpload({
        	    url:"CreditReports!uploadFile.action?PROJECT_ID="+project_id,
        	    secureuri:false,
        	    fileElementId:"file_upload",	 
        	    dataType: "json",
				success: function (data,status)  //服务器成功响应处理函数
                {
					var obj = JSON.parse(data);
					alert(obj.msg);
					$('#pageTable').datagrid('reload');
					$('.ddv').datagrid("reload");
                },
       	});	
		$("#uploadFileDialog").dialog('close');
	}
}

function deleteFile(id){
	if(confirm("确定要删除吗？")){
		$.ajax({
			url:'CreditReports!deleteFile.action?ID='+id,
			dataType:'json',
			success:function(data){
				if(data){
					alert('删除成功');
					$('.ddv').datagrid("reload");
				}
			}
		});
	}
}

function showPhoto(row){
	console.debug(row);
	if(row){
		var PROJECT_ID = row.ID;
		top.addTab("资料",_basePath+"/crm/Customer!toMgElectronicPhotoAlbum11.action?PROJECT_ID="+PROJECT_ID+"&CUST_TYPE=承租人&YHBC=1");
	}else{
		$.messager.alert("请选择一个项目!");
	}
}
