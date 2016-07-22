var tg='<a id="statusA" href="javascript:void(0)" style="padding-right:0.25cm; margin-top:5px;">合格</a>';
var btg='<a id="statusB" href="javascript:void(0)" style="padding-right:0.25cm; margin-top:5px;">不合格</a>';
var deleteFlag=false;
var fileTypeFlag=false;
$(function(){
	//全局参数
	var PRO_CODE = $("#PRO_CODE").val();
	var PROJECT_ID = $("#PROJECT_ID").val();
	var FK_ID = $("#FK_ID").val();
	var PHASE = $("#PHASE").val();
	var YHBC=$("#YHBC").val();
//	var HTSH_FLAG=$("#HTSH").val();
//	if (HTSH_FLAG == 1) {
//		YHBC = 'YHBC'; 
//	} else if (HTSH_FLAG == 2){
//		YHBC = '';
//	} else if (HTSH_FLAG == 3) {
//		YHBC = 'SCSC';
//	}
	var JFBG=$("#JFBG").val();
	var qbtg="<a href=\"javascript:void(0)\" onclick=\"toTongGuo(\'"+PROJECT_ID+"\',\'"+PHASE+"\')\">全部通过</a>";
	var plsc="<a href='javascript:void(0)' onclick='uploadFile1("+PROJECT_ID+")'>批量上传</a>";
	var plxz="<a href=\"javascript:void(0)\" onclick=\"downloadFile1(\'"+PROJECT_ID+"\',\'"+PHASE+"\')\">批量下载</a>";
	if(JFBG!='')
	{
		qbtg="";
		tg="";
		btg="";
	}
	if(YHBC!=''){
		plsc="";
	}
	deleteFlag=getStace(PROJECT_ID);
	//生成文件列表
	$.each($('.pageTable12345'), function(i0, n0){
		var that = $(n0);
		that.datagrid({
			url: _basePath+"/crm/Customer!toMgElectronicPhotoAlbumData11.action",  //获取列表列值方法
	        rownumbers:true,//行数
	        onSelect: function(rowIndex) {
	        	that.datagrid('unselectRow', rowIndex);
	        },
	        toolbar:i0==0?'#ptBar':'',
	        fit: true,
	        fitColumns: true,
			onLoadSuccess : function(data){	 
	        	if($('.ismust:not(:parent)').size()>=1) {
					$('.startBpmBtn').linkbutton('disable');
					$('.startBpmBtn').attr('title', '上传完所有文件，才可以发起流程');
				}else {
					$('.startBpmBtn').linkbutton('enable');
					$('.startBpmBtn').attr('title', '');
				}
				
				$('.scBtn').linkbutton();
				//拖拽模块，初始化
				$('.drag').draggable({
					deltaX: 100,
					deltaY: 35,
					proxy: function(source){
						var s = $(source).clone();
						s.appendTo('#leedsTab');
						return s;
					},
					revert:true,
					cursor:'move',
					onStartDrag:function(){
						$(this).draggable('proxy').addClass('dp');
					},
					onStopDrag:function(){
					}
				});
				$('.target').droppable({
					accept:'.drag',
					onDragEnter:function(e,source){
						$(source).draggable('proxy').css('border','1px solid red');
						$(this).addClass('over');
					},
					onDragLeave:function(e,source){
						$(source).draggable('proxy').css('border','1px solid #ccc');
						$(this).removeClass('over');
					},
					onDrop:function(e,source){
						$(this).append(source);
						$(this).removeClass('over');
						var that = $(this);
						var curParentId = that.attr('parentId');
						var oldParentId = $(source).attr('oldParentId');
						if(curParentId!=oldParentId)
//							$.get(_basePath+'/crm/Customer!changePicDir.action',{
//								ID: $(source).attr('subId'),
//								PARENT_ID: curParentId,
//								TPM_TYPE: that.attr('tpmType')
//							},function(json){
//								alert(json.flag);
//							});
							jQuery.ajax({
								url:_basePath+'/crm/Customer!changePicDir.action', //根据ID编号修改合同附件表一系列的值 
								type:'post',
								data:"ID="+$(source).attr('subId')+"&PARENT_ID="+curParentId+"&TPM_TYPE="+that.attr('tpmType'),
								dataType:'json',
								success:function(json){
									if(json.flag){
										conditionsSelect();
									}
								}
							});
					}
				});
			},
			queryParams :{
				PRO_CODE:PRO_CODE,
				PHASE:PHASE,
				PROJECT_ID: PROJECT_ID,
				FK_ID: FK_ID,
				mainType:that.attr('mainTypeTitle')
			},
			nowrap: false,
	        columns:[[
				{field:'TPM_TYPE',align:'center',width:80,title:'资料类型',formatter:function(value, rowData, rowIndex) {
					var str = "";
					if(rowData.REQUIRE_TYPE=="0"){
						str += "(必选)";
					}else {
						str += "(非必选)";
					}
					return value +"<font color='red'>"+str+"</font>";
				}},
	            {field:'ID',align:'left',width:400,title:'文件列表&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; '+qbtg+'  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+plsc+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+plxz+'</br> <font color="red">注： 图片蓝色框为审核通过；  黄色框为待审核； 红色框为驳回图片； 黑色框为不可操作图片</font>  ',formatter:function(value,rowData, rowIndex) {
					// 保真图
					var paths = rowData.PATHS;
					// 缩略图
					var path_scales = rowData.PATH_SCALES;
					var str = rowData.REQUIRE_TYPE;
					var html = "";
					html = '<div class="target" parentId="'+rowData.ID+'" tpmType="'+rowData.TPM_TYPE+'" style="width:100%; height:92px; border:1px solid #99FF99">';
					
					
				    if(str=="0"){
				    	html = '<div class="target ismust" parentId="'+rowData.ID+'" ismust="必选" tpmType="'+rowData.TPM_TYPE+'" style="width:100%; height:92px; border:1px solid #99FF99">';
				    }else{
				    	html = '<div class="target" parentId="'+rowData.ID+'" ismust="非必选" tpmType="'+rowData.TPM_TYPE+'" style="width:100%; height:92px; border:1px solid #99FF99">';
					}
					if(paths) {
						var subIds = rowData.SUB_IDS.split(",");
						var names = rowData.NAMES.split(",");
						var status = rowData.CHECK_STATUS_.split(",");
						$.each(paths.split(","), function(i, n){
							
							// 页面直接显示
							var paths_temp = path_scales.split(",");
							
							var id = subIds[i];
							var name = names[i];
							var statu = status[i];
							var param1 = {};
							param1.path = n;
							param1.id = id;
							// 图片保真图，大图显示时使用
							param1.picSrc = _basePath+'/leeds/cust_info_input/CustInfoInput!readPic.action?path='+n;
							param1.name = name;
							param1.statu = statu;
							
							// 图片略缩图，直接显示使用
							var path = _basePath+'/leeds/cust_info_input/CustInfoInput!readPic.action?path='+paths_temp[i];
							
							if(name.indexOf(".PDF") > 0 || name.indexOf(".pdf") > 0){
								path=_basePath+"/img/pdf.jpg";
							}else if(name.indexOf(".doc") > 0 || name.indexOf(".DOC") > 0){
								path=_basePath+"/img/word.jpg";
							}else if(name.indexOf(".xls") > 0 || name.indexOf(".XLS") > 0){
								path=_basePath+"/img/excel.jpg";
							}
							html += '<div class="drag" oldParentId="'+rowData.ID+'" subId="'+id+'"data-options="handle:\'#title_'+id+'\'" style="border:1px solid #ccc; padding:0px 5px 5px 5px; float:left; margin-right:20px">' +
								    '<div id="title_'+id+'" style="height:15px"></div>' ;
							if(statu==1){
								html += '<img border="0"  src="'+path+'" height="70" style="cursor:pointer;border:2px solid yellow" onclick=showPic('+JSON.stringify(param1)+') />';
							}else if (statu==2){
								html += '<img border="0"  src="'+path+'" height="70" style="cursor:pointer;border:2px solid blue" onclick=showPic('+JSON.stringify(param1)+') />';
							}else if (statu==3){
								html += '<img border="0"  src="'+path+'" height="70" style="cursor:pointer;border:2px solid red" onclick=showPic('+JSON.stringify(param1)+') />';
							}else if (statu==4){
								html += '<img border="0"  src="'+path+'" height="70" style="cursor:pointer;border:2px solid black" onclick=showPic('+JSON.stringify(param1)+') />';
							}
								
							html +='</div>';
						});
					}
					html += '</div>';
					return html;
				}
			},
				{field:'TPM_BUSINESS_PLATE',align:'center',title: '操作',width:50,formatter:function setHouserDel(value,rowData,rowIndex) {
						 if(YHBC!=null && YHBC !=''){
							 if(YHBC=="SCSC"){
								 return "<a href='#' class='scBtn' data-options='plain:true' onclick='uploadFile(" + JSON.stringify(rowData)+ ',' + rowIndex + ")'>上传</a>" +
								 		"&nbsp;&nbsp;<a href='#' class='scBtn' data-options='plain:true' onclick='deletePic11("+JSON.stringify(rowData)+")'>删除</a>";
							 }else{
								 return "";
							 }
							
						 }else{
							 $("#batchData").val(JSON.stringify(rowData));
							 return "<a href='#' class='scBtn' data-options='plain:true' onclick='uploadFile(" + JSON.stringify(rowData)+ ',' + rowIndex + ")'>上传</a>";
						 }
					}
				}
	        ]]
		});
	});
	//上传文件
	$("#uploadify").uploadify({
        'auto'            	  : false,     //选定文件后是否自动上传，默认false 
    	'buttonText'      : '添加文件',  
    	//buttonImg ： 浏览按钮的图片的路径 。 
    	'fileObjName'      : 'myFile', //文件对象名称。用于在服务器端获取文件  该属性的缺省值为：‘Filedata’
    	'fileSizeLimit'   	  :'50MB',  //上传文件大小限制，默认单位是KB,上传文件大小设置 单位可以是B、KB、MB、GB 
    	'fileTypeDesc'    :'仅支持格式：gif/jpg/png',//文件类型的说明
    	'fileTypeExts'     :'*.JPG;*.PNG;*.GIF;*.DOCX;*.XLSX;*.PDF;*.jpg;*.png;*.gif;*.docx;*.xlsx;*.pdf;',//指定允许上传的文件类型。默认 *.*。比如只支持gif , jpg , png 类型的图像，那么该属性设置为: ‘*.gif; *.jpg ; *.png’
    	'multi'               : true,     //是否支持多文件上传，默认为true
    	'method'            :'post',//默认是’post’,也可以设置为’get’
        'progressData'    :'speed',//设置文件上传时显示的数据，有两个选择：‘上传速度‘或者’百分比‘，分别对应’speed’和’percentage’
        'queueID'           :'fileQueue',   //文件队列的ID，该ID与存放文件队列的div的ID一致。
        'uploadLimit'       :200,     //最多上传文件数量，默认999
        'queueSizeLimit'  :200,      //队列长度限制，缺省值999
        'swf'             : _basePath+'/crm/js/uploadify3.2/uploadify.swf',    //swf文件路径，‘uploader.swf’ 
        'uploader'        : _basePath+'/crm/Customer!doUploadPic.action',                    //服务器端脚本文件路径
        'onQueueComplete': function(queueData) {//在队列中的文件上传完成后触发
         	$("#uploadFileDiv").dialog('close');
			conditionsSelect(); 
		},
		onSelect : function(fileObj){
			var name = fileObj.name;
			var size = fileObj.size;
			var fileCat = $('#FILE_TYPE1').val();
			var id = fileObj.id;
			var ttttt="";
			var aaa="";
			var i=0;
			var flag = false;
			fileTypeFlag = false;
			upPicture(name);
			if (!fileTypeFlag){
				 //leeds.msg('只能上传图片！');
				 $('#uploadify').uploadify('cancel', id);
				 alert('只能上传图片！');
			}
/*			$("[field=TPM_TYPE]").each(function(){
				 ttttt= $(this).text().split("(");
				 if(name.indexOf(ttttt[0])>= 0){
					 flag = true;
                     i++;
				 }
			});
			if (i > 1) {
                flag = false;
            }
			 if (!flag) {
				 leeds.msg('文件【'+name+'】名称不符合要求！');
				 $('#uploadify').uploadify('cancel', id);
			 }*/
//			if(name.indexOf(fileCat)!=0) {
//				leeds.msg('文件【'+name+'】名称不符合要求！')
//				$('#uploadify').uploadify('cancel', id);
//			}
		}
    });
	
	//批量上传文件
	$("#bulkUploadify").uploadify({
        'auto'            	  : false,     //选定文件后是否自动上传，默认false 
    	'buttonText'      : '批量添加文件',  
    	//buttonImg ： 浏览按钮的图片的路径 。 
    	'fileObjName'      : 'myFile', //文件对象名称。用于在服务器端获取文件  该属性的缺省值为：‘Filedata’
    	'fileSizeLimit'   	  :'50MB',  //上传文件大小限制，默认单位是KB,上传文件大小设置 单位可以是B、KB、MB、GB 
    	'fileTypeDesc'    :'仅支持格式：gif/jpg/png',//文件类型的说明
    	'fileTypeExts'     :'*.JPG;*.PNG;*.GIF;*.DOCX;*.XLSX;*.PDF;*.jpg;*.png;*.gif;*.docx;*.xlsx;*.pdf;',//指定允许上传的文件类型。默认 *.*。比如只支持gif , jpg , png 类型的图像，那么该属性设置为: ‘*.gif; *.jpg ; *.png’
    	'multi'               : true,     //是否支持多文件上传，默认为true
    	'method'            :'post',//默认是’post’,也可以设置为’get’
        'progressData'    :'speed',//设置文件上传时显示的数据，有两个选择：‘上传速度‘或者’百分比‘，分别对应’speed’和’percentage’
        'queueID'           :'bulkFileQueue',   //文件队列的ID，该ID与存放文件队列的div的ID一致。
        'uploadLimit'       :200,     //最多上传文件数量，默认999
        'queueSizeLimit'  :200,      //队列长度限制，缺省值999
        'swf'             : _basePath+'/crm/js/uploadify3.2/uploadify.swf',    //swf文件路径，‘uploader.swf’ 
        'uploader'        : _basePath+'/crm/Customer!doUploadPic.action',                    //服务器端脚本文件路径
        'onQueueComplete': function(queueData) {//在队列中的文件上传完成后触发
         	$("#bulkUploadFileDiv").dialog('close');
			conditionsSelect(); 
		},
		onSelect : function(fileObj){
			var name = fileObj.name;
			var subName = name.substr(0,name.indexOf(fileObj.type));
			var size = fileObj.size;
			var fileCat = $('#FILE_TYPE1').val();
			var id = fileObj.id;
			var ttttt="";
			var aaa="";
			var i=0;
			var flag = false;
			$("[field=TPM_TYPE]").each(function(){
				 ttttt= $(this).text().split("(");
				 var temp = ttttt[0].split("_");
				 if(subName.indexOf(temp[0]) >= 0){					
					 flag = true;
                     i++;
				 }
			});
			if (i > 1) {
                flag = false;
            }
			 if (!flag) {
				 leeds.msg('文件【'+name+'】名称不符合要求！');
				 $('#bulkUploadify').uploadify('cancel', id);
			 }
		}
    });
});

function tabSelect(tabIndex) {
	if($('.dp').size())
		$('#leedsTab').tabs('select', tabIndex);
}
function showPic(param1) {
	var picSrc = param1.picSrc;
	var picId = param1.id;
	var name = param1.name;
	var statu = param1.statu;
	var path = param1.path;

	var task_ = $("#task_").val();
	var updateStart = $("#updateStart").val();
	
	$('#showPic').show();
	if(name.indexOf(".PDF") > 0 || name.indexOf(".pdf") > 0){
		picSrc=_basePath+"/img/pdf.jpg";
	}else if(name.indexOf(".doc") > 0 || name.indexOf(".DOC") > 0){
		picSrc=_basePath+"/img/word.jpg";
	}else if(name.indexOf(".xls") > 0 || name.indexOf(".XLS") > 0){
		picSrc=_basePath+'/img/excel.jpg';
	}
//	$(window).keydown(function(event){
//		switch(event.which) {
//			case 38:
//			case 40:
//				break;
//			default:
//				$('#showPic').dialog('close');
//		}
//	});
	var toolbar ;
	
	if(toLowerCase(path.split(".")[1])!="jpg"&&toLowerCase(path.split(".")[1])!="png"&&toLowerCase(path.split(".")[1])!="gif"){
		toolbar= [
				{text: '关闭',
					  handler:function(){
					  	$('#showPic').dialog('close');
					  }
				},
				'-',{text: '下载',
					  handler:function(){
						downloadPic(param1);
					  }
				},
				'-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/left.gif" width="20" height="20" style="cursor:hand" title="上一页" alt=""/></a>',
					  handler:function(){
						  turnThePage(param1,1);
					  }
				},
				'-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/right.gif" width="20" height="20" style="cursor:hand" title="下一页" alt=""/></a>',
					  handler:function(){
						  turnThePage(param1,2);
					  }
				}
			  ];
	}else if(toLowerCase(path.split(".")[1])=="jpg"||toLowerCase(path.split(".")[1])=="png"||toLowerCase(path.split(".")[1])=="gif"){
		if(statu == 1){
			toolbar= [
						{text: '关闭',
							  handler:function(){
							  	$('#showPic').dialog('close');
							  }
						}, '-',{text: '删除',
							  handler:function(){
								  deletePic(param1);
								  }
						}, '-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/left.gif" width="20" height="20" style="cursor:hand" title="上一页" alt=""/></a>',
							  handler:function(){
								  turnThePage(param1,1);
							  }
						},
						'-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/right.gif" width="20" height="20" style="cursor:hand" title="下一页" alt=""/></a>',
							  handler:function(){
								  turnThePage(param1,2);
							  }
						},'-', {//还原
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom.gif" width="20" height="20" style="cursor:hand" id="rotReal" title="还原" alt=""/></a>',
							handler:function(){
							
							}
						},'-',{//放大
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom_in.gif" width="20" height="20" style="cursor:hand" onClick="bigit();" title="放大" alt=""/></a>',
							handler:function(){
							
							}
						},'-',{//缩小
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom_out.gif" width="20" height="20" style="cursor:hand" onClick="smallit();" title="缩小" alt=""/></a>',
							handler:function(){
							
							}
						},'-',{//左转
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/Turnleft.png" width="20" height="20" style="cursor:hand" id="rotLeft" title="左转" alt=""/></a>',
							handler:function(){
							
							}
						},'-',{//右转
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/Turnright.png" width="20" height="20" style="cursor:hand" id="rotRight" title="右转" alt=""/></a>',
							handler:function(){
							}
						}
					  ];
		}else{
			toolbar= [
						{text: '关闭',
							  handler:function(){
							  	$('#showPic').dialog('close');
							  }
						}, '-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/left.gif" width="20" height="20" style="cursor:hand" title="上一页" alt=""/></a>',
							  handler:function(){
								  turnThePage(param1,1);
							  }
						},
						'-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/right.gif" width="20" height="20" style="cursor:hand" title="下一页" alt=""/></a>',
							  handler:function(){
								  turnThePage(param1,2);
							  }
						},'-', {//还原
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom.gif" width="20" height="20" style="cursor:hand" id="rotReal" title="还原" alt=""/></a>',
							handler:function(){
							
							}
						},'-',{//放大
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom_in.gif" width="20" height="20" style="cursor:hand" onClick="bigit();" title="放大" alt=""/></a>',
							handler:function(){
							
							}
						},'-',{//缩小
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom_out.gif" width="20" height="20" style="cursor:hand" onClick="smallit();" title="缩小" alt=""/></a>',
							handler:function(){
							
							}
						},'-',{//左转
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/Turnleft.png" width="20" height="20" style="cursor:hand" id="rotLeft" title="左转" alt=""/></a>',
							handler:function(){
							
							}
						},'-',{//右转
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/Turnright.png" width="20" height="20" style="cursor:hand" id="rotRight" title="右转" alt=""/></a>',
							handler:function(){
							}
						}
					  ];
		}
	}
	        
	/*if(task_=="jf"){
		if(toLowerCase(path.split(".")[1])!="jpg"&&toLowerCase(path.split(".")[1])!="png"&&toLowerCase(path.split(".")[1])!="gif"){
			toolbar= [
					{text: '关闭',
						  handler:function(){
						  	$('#showPic').dialog('close');
						  }
					},
					'-',{text: '下载',
						  handler:function(){
							downloadPic(param1);
						  }
					},
					'-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/left.gif" width="20" height="20" style="cursor:hand" title="上一页" alt=""/></a>',
						  handler:function(){
							  turnThePage(param1,1);
						  }
					},
					'-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/right.gif" width="20" height="20" style="cursor:hand" title="下一页" alt=""/></a>',
						  handler:function(){
							  turnThePage(param1,2);
						  }
					}
				  ];
		}else if(toLowerCase(path.split(".")[1])=="jpg"||toLowerCase(path.split(".")[1])=="png"||toLowerCase(path.split(".")[1])=="gif"){
			toolbar= [
					{text: '关闭',
						  handler:function(){
						  	$('#showPic').dialog('close');
						  }
					}, '-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/left.gif" width="20" height="20" style="cursor:hand" title="上一页" alt=""/></a>',
						  handler:function(){
							  turnThePage(param1,1);
						  }
					},
					'-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/right.gif" width="20" height="20" style="cursor:hand" title="下一页" alt=""/></a>',
						  handler:function(){
							  turnThePage(param1,2);
						  }
					},'-', {//还原
						text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom.gif" width="20" height="20" style="cursor:hand" id="rotReal" title="还原" alt=""/></a>',
						handler:function(){
						
						}
					},'-',{//放大
						text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom_in.gif" width="20" height="20" style="cursor:hand" onClick="bigit();" title="放大" alt=""/></a>',
						handler:function(){
						
						}
					},'-',{//缩小
						text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom_out.gif" width="20" height="20" style="cursor:hand" onClick="smallit();" title="缩小" alt=""/></a>',
						handler:function(){
						
						}
					},'-',{//左转
						text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/Turnleft.png" width="20" height="20" style="cursor:hand" id="rotLeft" title="左转" alt=""/></a>',
						handler:function(){
						
						}
					},'-',{//右转
						text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/Turnright.png" width="20" height="20" style="cursor:hand" id="rotRight" title="右转" alt=""/></a>',
						handler:function(){
						}
					}
				  ];
		}
	}else {
		if(updateStart=="jdbpm"){
			toolbar= [
						{text: '关闭',
							  handler:function(){
							  	$('#showPic').dialog('close');
							  }
						},
						'-',{text: '下载',
							  handler:function(){
								downloadPic(param1);
							  }
							}, 
						'-',{text: '删除',
								  handler:function(){
									  deletePic(param1);
									  }
									},
						'-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/left.gif" width="20" height="20" style="cursor:hand" title="上一页" alt=""/></a>',
								  handler:function(){
									  turnThePage(param1,1);
								  }
							},
							'-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/right.gif" width="20" height="20" style="cursor:hand" title="下一页" alt=""/></a>',
								  handler:function(){
									  turnThePage(param1,2);
								  }
							},
							 '-','-', {//还原
								text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom.gif" width="20" height="20" style="cursor:hand" id="rotReal" title="还原" alt=""/></a>',
								handler:function(){
								
								}
							},'-',{//放大
								text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom_in.gif" width="20" height="20" style="cursor:hand" onClick="bigit();" title="放大" alt=""/></a>',
								handler:function(){
								
								}
							},'-',{//缩小
								text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom_out.gif" width="20" height="20" style="cursor:hand" onClick="smallit();" title="缩小" alt=""/></a>',
								handler:function(){
								
								}
							},'-',{//左转
								text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/Turnleft.png" width="20" height="20" style="cursor:hand" id="rotLeft" title="左转" alt=""/></a>',
								handler:function(){
								
								}
							},'-',{//右转
								text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/Turnright.png" width="20" height="20" style="cursor:hand" id="rotRight" title="右转" alt=""/></a>',
								handler:function(){
								}
							}
					  ];
		}else {
			if(toLowerCase(path.split(".")[1])!="jpg"&&toLowerCase(path.split(".")[1])!="png"&&toLowerCase(path.split(".")[1])!="gif"&&statu==1){
				toolbar= [{text: '关闭',
							  handler:function(){
							  	$('#showPic').dialog('close');
							  }
							},'-',{text: '下载',
							  handler:function(){
								downloadPic(param1);
							  }
							}, '-',{text: '删除',
							  handler:function(){
								  deletePic(param1);
							  }
							} , '-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/left.gif" width="20" height="20" style="cursor:hand" title="上一页" alt=""/></a>',
								  handler:function(){
									  turnThePage(param1,1);
								  }
							},'-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/right.gif" width="20" height="20" style="cursor:hand" title="下一页" alt=""/></a>',
								  handler:function(){
									  turnThePage(param1,2);
								  }
							},'-',{//通过
								text:tg,
								iconCls:'icon-multitasking',
								handler:function(){
									checkPic(1);
								}
							},'-',{//驳回
								text:btg,
								iconCls:'icon-multitasking',
								handler:function(){
									checkPic(2);
								}//
						}
					  ];
			} else if(toLowerCase(path.split(".")[1])!="jpg"&&toLowerCase(path.split(".")[1])!="png"&&toLowerCase(path.split(".")[1])!="gif"&&statu==2){
				toolbar= [
						{text: '关闭',
							  handler:function(){
							  	$('#showPic').dialog('close');
							  }
						},
						'-',{text: '下载',
							  handler:function(){
								downloadPic(param1);
							  }
						} , '-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/left.gif" width="20" height="20" style="cursor:hand" title="上一页" alt=""/></a>',
							  handler:function(){
								  turnThePage(param1,1);
							  }
						},
						'-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/right.gif" width="20" height="20" style="cursor:hand" title="下一页" alt=""/></a>',
							  handler:function(){
								  turnThePage(param1,2);
							  }
						},
						'-',{//驳回
								text:btg,
								iconCls:'icon-multitasking',
								handler:function(){
									checkPic(2);
								}//
						}
					  ];
			}else if(toLowerCase(path.split(".")[1])!="jpg"&&toLowerCase(path.split(".")[1])!="png"&&toLowerCase(path.split(".")[1])!="gif"&&statu==3){
				toolbar= [
						{text: '关闭',
							  handler:function(){
							  	$('#showPic').dialog('close');
							  }
						},
						'-',{text: '下载',
							  handler:function(){
								downloadPic(param1);
							  }
						}, '-',{text: '删除',
							  handler:function(){
								  deletePic(param1);
							  }
							} , '-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/left.gif" width="20" height="20" style="cursor:hand" title="上一页" alt=""/></a>',
							  handler:function(){
								  turnThePage(param1,1);
							  }
						},
						'-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/right.gif" width="20" height="20" style="cursor:hand" title="下一页" alt=""/></a>',
							  handler:function(){
								  turnThePage(param1,2);
							  }
						}
					  ];
			}else if((toLowerCase(path.split(".")[1])=="jpg"||toLowerCase(path.split(".")[1])=="png"||toLowerCase(path.split(".")[1])=="gif")&&statu==3){
				toolbar= [
						{text: '关闭',
							  handler:function(){
							  	$('#showPic').dialog('close');
							  }
						}, '-',{text: '删除',
							  handler:function(){
								  deletePic(param1);
							  }
							}, '-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/left.gif" width="20" height="20" style="cursor:hand" title="上一页" alt=""/></a>',
							  handler:function(){
								  turnThePage(param1,1);
							  }
						},'-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/right.gif" width="20" height="20" style="cursor:hand" title="下一页" alt=""/></a>',
							  handler:function(){
								  turnThePage(param1,2);
							  }
						}, '-','-', {//还原
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom.gif" width="20" height="20" style="cursor:hand" id="rotReal" title="还原" alt=""/></a>',
							handler:function(){
							
							}
						},'-',{//放大
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom_in.gif" width="20" height="20" style="cursor:hand" onClick="bigit();" title="放大" alt=""/></a>',
							handler:function(){
							
							}
						},'-',{//缩小
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom_out.gif" width="20" height="20" style="cursor:hand" onClick="smallit();" title="缩小" alt=""/></a>',
							handler:function(){
							
							}
						},'-',{//左转
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/Turnleft.png" width="20" height="20" style="cursor:hand" id="rotLeft" title="左转" alt=""/></a>',
							handler:function(){
							
							}
						},'-',{//右转
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/Turnright.png" width="20" height="20" style="cursor:hand" id="rotRight" title="右转" alt=""/></a>',
							handler:function(){
							}
						}
					  ];
			}else if((toLowerCase(path.split(".")[1])=="jpg"||toLowerCase(path.split(".")[1])=="png"||toLowerCase(path.split(".")[1])=="gif")&&statu==2){
				toolbar= [
						{text: '关闭',
							  handler:function(){
							  	$('#showPic').dialog('close');
							  }
						}, '-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/left.gif" width="20" height="20" style="cursor:hand" title="上一页" alt=""/></a>',
							  handler:function(){
								  turnThePage(param1,1);
							  }
						},
						'-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/right.gif" width="20" height="20" style="cursor:hand" title="下一页" alt=""/></a>',
							  handler:function(){
								  turnThePage(param1,2);
							  }
						}, '-','-', {//还原
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom.gif" width="20" height="20" style="cursor:hand" id="rotReal" title="还原" alt=""/></a>',
							handler:function(){
							
							}
						},'-',{//放大
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom_in.gif" width="20" height="20" style="cursor:hand" onClick="bigit();" title="放大" alt=""/></a>',
							handler:function(){
							
							}
						},'-',{//缩小
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom_out.gif" width="20" height="20" style="cursor:hand" onClick="smallit();" title="缩小" alt=""/></a>',
							handler:function(){
							
							}
						},'-',{//左转
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/Turnleft.png" width="20" height="20" style="cursor:hand" id="rotLeft" title="左转" alt=""/></a>',
							handler:function(){
							
							}
						},'-',{//右转
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/Turnright.png" width="20" height="20" style="cursor:hand" id="rotRight" title="右转" alt=""/></a>',
							handler:function(){
							}
						},'-',{//驳回
							text:btg,
							iconCls:'icon-multitasking',
							handler:function(){
								checkPic(2);
							}//
						}
					  ];
			}else {
				if (deleteFlag){
				toolbar= [
						{text: '关闭',
						  handler:function(){
						  	$('#showPic').dialog('close');
						  }
						}, '-',{text: '删除',
							  handler:function(){
								  deletePic(param1);
							  }
							},'-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/left.gif" width="20" height="20" style="cursor:hand" title="上一页" alt=""/></a>',
							  handler:function(){
								  turnThePage(param1,1);
							  }
						},'-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/right.gif" width="20" height="20" style="cursor:hand" title="下一页" alt=""/></a>',
							  handler:function(){
								  turnThePage(param1,2);
							  }
						}, '-','-', {//还原
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom.gif" width="20" height="20" style="cursor:hand" id="rotReal" title="还原" alt=""/></a>',
							handler:function(){
							
							}
						},'-',{//放大
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom_in.gif" width="20" height="20" style="cursor:hand" onClick="bigit();" title="放大" alt=""/></a>',
							handler:function(){
							
							}
						},'-',{//缩小
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom_out.gif" width="20" height="20" style="cursor:hand" onClick="smallit();" title="缩小" alt=""/></a>',
							handler:function(){
							
							}
						},'-',{//左转
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/Turnleft.png" width="20" height="20" style="cursor:hand" id="rotLeft" title="左转" alt=""/></a>',
							handler:function(){
							
							}
						},'-',{//右转
							text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/Turnright.png" width="20" height="20" style="cursor:hand" id="rotRight" title="右转" alt=""/></a>',
							handler:function(){
							}
						},'-',{//通过
							text:tg,
							iconCls:'icon-multitasking',
							handler:function(){
								checkPic(1);
							}
						},'-',{//驳回
							text:btg,
							iconCls:'icon-multitasking',
							handler:function(){
								checkPic(2);
							}//
						}
					];
				}
				else{
					toolbar= [
								{text: '关闭',
								  handler:function(){
								  	$('#showPic').dialog('close');
								  }
								}, '-',{text: '删除',
									  handler:function(){
										  deletePic(param1);
										  }
								},'-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/left.gif" width="20" height="20" style="cursor:hand" title="上一页" alt=""/></a>',
									  handler:function(){
										  turnThePage(param1,1);
									  }
								},'-',{text: '<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/right.gif" width="20" height="20" style="cursor:hand" title="下一页" alt=""/></a>',
									  handler:function(){
										  turnThePage(param1,2);
									  }
								}, '-','-', {//还原
									text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom.gif" width="20" height="20" style="cursor:hand" id="rotReal" title="还原" alt=""/></a>',
									handler:function(){
									
									}
								},'-',{//放大
									text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom_in.gif" width="20" height="20" style="cursor:hand" onClick="bigit();" title="放大" alt=""/></a>',
									handler:function(){
									
									}
								},'-',{//缩小
									text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/zoom_out.gif" width="20" height="20" style="cursor:hand" onClick="smallit();" title="缩小" alt=""/></a>',
									handler:function(){
									
									}
								},'-',{//左转
									text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/Turnleft.png" width="20" height="20" style="cursor:hand" id="rotLeft" title="左转" alt=""/></a>',
									handler:function(){
									
									}
								},'-',{//右转
									text:'<a href="javascript:void(0)"><img src="../leeds/imgViewer/images/Turnright.png" width="20" height="20" style="cursor:hand" id="rotRight" title="右转" alt=""/></a>',
									handler:function(){
									}
								},'-',{//通过
									text:tg,
									iconCls:'icon-multitasking',
									handler:function(){
										checkPic(1);
									}
								},'-',{//驳回
									text:btg,
									iconCls:'icon-multitasking',
									handler:function(){
										checkPic(2);
									}//
								}
							];
				}
			}
		}
	}*/
	$('#showPic').dialog({
		noheader: true,
		modal: true,
		content: '<div algin="center"  id="block1" onmouseout="drag=0;" onmouseover="dragObj=block1; drag=1;" class="dragAble"><img border="0" name="imageshow" title="'+name+'" picId="'+picId+'" id="images1" src="'+picSrc+'" ondblclick="showDetail('+picId+')";/></div>',
		fit: true,
		toolbar : toolbar
	});
	imgReadInit();
	$('#images1').bind('mousewheel', function(event, delta) {
        if(delta>0){
        	bigit();
        }else{
        	smallit();
        }
        return false;
    });
	window.fun.real();
}

function conditionsSelect(){
	$('.pageTable12345').datagrid("reload");
}
//删除图片
function deletePic(row) {
	$.messager.confirm('提示框', '确定要删除吗?',function(flag){
		if(flag){
			jQuery.ajax({
				url : _basePath+"/crm/Customer!deleteFile11.action?path="+encodeURI(row.path) + "&ID=" + row.id,
				dataType : "json",
				success : function(json){
					$('#showPic').dialog('close');
					if(json.flag){
						conditionsSelect();
					}else{
						alert(json.msg);
					}	
				}
			});
		}
	});
}

//----------------------上传资料-----------------beign
function uploadFile(row,index){
	var divId = 'uploadFileDiv';
	var formId = 'uploadFileForm';
	$("#"+divId).show();
	$("#"+divId).dialog({   
		 title:'上传【'+row.TPM_TYPE+'】',
		 iconCls: 'icon-application',
     	 modal:true,
		 resizable:true,
		 buttons: [{
			id:"btnbc",
            text:'点击上传',
            iconCls:'icon-up',
            handler:function(){
			 	$('#btnbc').linkbutton('disable');
			 	row.PARENT_ID=row.ID;
			 	row.ID = '';
			 	row.REMARK = $("#uploadFileForm").find("#REMARK").val();
			 	row.FK_ID = row.FK_ID;
			 	var data_ = row;
				$("#uploadify").uploadify("settings", "formData", data_); 
				$('#uploadify').uploadify('upload','*');
            }
        },{
            text:'关闭',
			iconCls:'icon-cancel',
            handler:function(){
            	$('#uploadify').uploadify('cancel','*');
               $("#"+divId).dialog('close');
            }
        }]
	}); 
	$("[name='upload']").each(function(){
		$(this).val('');
	});
	$('#FILE_TYPE1').val(row.TPM_TYPE);
	$("#uploadFileForm").find("#REMARK").val('');
}

//批量上传
function uploadFile1(project){
	var divId = 'bulkUploadFileDiv';
	var formId = 'bulkUploadFileForm';
	var TPM_BUSINESS_PLATE=$("#PHASE").val();
	var aa=$("#pageTable12345").attr("mainTypeTitle");
	$("#"+divId).show();
	$("#"+divId).dialog({   
		 title:'批量上传',
		 iconCls: 'icon-application',
     	 modal:true,
		 resizable:true,
		 buttons: [{
			id:"btnbc",
            text:'点击上传',
            iconCls:'icon-up',
            handler:function(){
			 	$('#btnbc').linkbutton('disable');
			 	var batchData = $.parseJSON($("#batchData").val());
			 	var PROJECT_ID=project;
			 	var REMARK = "批量上传";
			 	var data_ = {
			 			PROJECT_ID : PROJECT_ID,
			 			TPM_BUSINESS_PLATE : TPM_BUSINESS_PLATE,
			 			REMARK : REMARK, 
			 			PARENT_ID : batchData.ID,
			 			CREATE_CODE : batchData.CREATE_CODE,
			 			TPM_CUSTOMER_TYPE : batchData.TPM_CUSTOMER_TYPE
			 	};
				$("#bulkUploadify").uploadify("settings", "formData", data_); 
				$('#bulkUploadify').uploadify('upload','*');
            }
        },{
            text:'关闭',
			iconCls:'icon-cancel',
            handler:function(){
            	$('#bulkUploadify').uploadify('cancel','*');
               $("#"+divId).dialog('close');
            }
        }]
	}); 
	$("[name='upload']").each(function(){
		$(this).val('');
	});
	$("#bulkUploadFileForm").find("#BULKREMARK").val('');
}
//----------------------上传资料-----------------end

//下载图片
function downloadPic(row){
	window.location.href=_basePath+"/crm/Customer!downFile1.action?path="+encodeURI(row.path)+"&filename="+row.name; 
}

//图片审核
function checkPic(type){
	if(type=="1"){
		$.messager.confirm('审核确认', '确定审核通过该资料？', function(r) {
			if (r) {
				$.ajax({
					url : _basePath
							+ "/leeds/cust_info_input/CustInfoInput!updatePictureStatus.action",
					data : {
						ID : $("#images1").attr("picId"),
						CHECK_STATUS : 2
					},
					dataType : "json",
					success : function(json) {
						if (json.flag) {
							alert("审核成功！");
							$('#showPic').dialog('close');
							conditionsSelect();
						} else {
							alert("审核失败！");
						}
					}
				});
			}
		});
	}else {
				$.messager.prompt('意见', '请输入不合格意见', function(r) {
					if(r=='')
					{
						alert("请输入不合格意见!");
						return;
					}
					if (r) {
						var checkRemark = r;
						$.ajax({
							url : _basePath
									+ "/leeds/cust_info_input/CustInfoInput!updatePictureStatus.action",
							data : {
								ID : $("#images1").attr("picId"),
								CHECK_STATUS : 3,
								CHECK_REMARK : checkRemark
							},
							dataType : "json",
							success : function(json) {
								if (json.flag) {
									alert("操作成功！");
									$('#showPic').dialog('close');
									conditionsSelect();
								} else {
									alert("操作失败！");
								}
							}
						});
					}
				});
	}
}


//图片批量通过
function toTongGuo(obj,PHASE){
	$.messager.confirm('提示框', '确定要通过全部资料吗?',function(flag){
		if(flag){
			jQuery.ajax({
				url: _basePath+"/leeds/cust_info_input/CustInfoInput!updatePictureStatusPiLiang.action?PROJECT_ID="+obj+"&TPM_BUSINESS_PLATE="+PHASE+"&CHECK_STATUS=2",
				dataType:'json',
				success:function(json){
					if (json.flag) {
						alert("成功");
						conditionsSelect();
					} else {
						alert("失败！未找到还未通过的资料！");
					}
				}
			});
		}
	});
}

//下一张图片
function turnThePage(param,type){
	var id=param.id;
	jQuery.ajax({
		url: _basePath+"/crm/Customer!turnThePage.action?ID="+id+"&TYPE="+type,
		dataType:'json',
		success:function(res){
			if (res.flag) {
				showPic(res.data);
			} else {
				$.messager.alert("提示",res.msg);
			}
		}
	});
}

//显示明细
function showDetail(picId){
	window.open(_basePath+"/crm/Customer!getImg.action?ID="+picId);
}

function downloadFile1(picId,phase){
	window.location.href=_basePath+"/crm/Customer!expPdf.action?PROJECT_ID="+picId+"&PHASE="+phase;
}

//add by luoxianhong 2015-11-13
function getStace(projectID){
	var flag=false;
	jQuery.ajax({
		url: _basePath+"/payment/PaymentApply!getProjectHeadStatus.action",
		data: "projectId="+projectID,
		dataType:"json",
		async:false,
		success: function(res){
			if(res.flag){
				flag=true;
				
		   }
		}
	});
	return flag;
}


function toLowerCase(str){
	return str.toLowerCase();
}
	

//删除图片111
function deletePic11(row) {
	if(row.PATHS==undefined||row.PATHS==""){
		alert("请选择要删除的图片！");
		return;
	}
	$.messager.confirm('提示框', '确定要删除吗?',function(flag){
		if(flag){
			jQuery.ajax({
				url : _basePath+"/crm/Customer!deleteFile11.action?path="+encodeURI(row.PATHS) + "&PARENT_ID=" + row.ID,
				dataType : "json",
				success : function(json){
					if(json.flag){
						alert("图片删除成功！");
						conditionsSelect();
					}else{
						alert(json.msg);
					}	
				}
			});
		}
	});
}
	
function upPicture(fileName)
{
    var ImageFileExtend = ".gif,.png,.jpg,.ico,.bmp";
    if(fileName.length>0) {
    //判断后缀
    var fileExtend=fileName.substring(fileName.lastIndexOf('.')).toLowerCase();
    	//可以对fileExtend（文件后缀<.xxx>） 进行判断 处理
        if(ImageFileExtend.indexOf(fileExtend)>-1)
        {
            fileTypeFlag = true;
        }
    }
}	
