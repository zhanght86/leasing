
/**导出全部	START **/
function exportExcelAll(){
	// 首次还款日
	var REPAYMENT_DATE = $("input[name='REPAYMENT_DATE']").val();

	// 客户姓名	
	var CNAME = $("#CNAME").val();
	jQuery.messager.confirm("导出","确定要导出查询记录吗?",function(r){
		pdata = "REPAYMENT_DATE=" + REPAYMENT_DATE + "&CNAME=" + CNAME;
		if(r){
			window.location.href = _basePath + "/white/WhiteListManage!exportExcelAll.action?" +pdata;
		}
	});
}
/**导出全部	END **/
	

/**导出选中 START
**/
function exportExcelCheck(){
	// 首次还款日
	var REPAYMENT_DATE = $("input[name='REPAYMENT_DATE']").val();
	// 客户姓名	
	var CNAME = $("#CNAME").val();
	// 选中数量
	var CHECKNUM = 0;
	// 选中项
	var LID;
	$("input[name='PID']:checked").each(function(){
		
		var data = $(this).val();
		if(CHECKNUM != 0) {
			LID = LID+","+data;
		} else {
			LID = data;
		}
		CHECKNUM++;
	}); 
	// 
	if(CHECKNUM == 0) {
		$.messager.alert("提示","没有选中项");
		return false;
	}
	
	jQuery.messager.confirm("导出","确定要导出选中的记录吗?",function(r){
		pdata = "REPAYMENT_DATE="+REPAYMENT_DATE+ "&CNAME="+CNAME+"&LID="+LID;
		if(r){
			window.location.href = _basePath + "/white/WhiteListManage!exportExcelCheck.action?" + pdata;
		}
	});
}
/**导出选中 END
**/


/**下载
 * @prama CNAME 名称
 * @prama PAHT 路径
 * START	
 */
function toDownload(CNAME, PATH) {
	var v = "CNAME="+CNAME + "&PATH="+PATH;
	window.location.href = _basePath + "/white/WhiteListManage!exportExcelCheckPic.action?" + v;
}
/**下载	END	*/


/**
 * 预览查看
 * @prama picId
 * @prama picPath
 * START
 **/
function preView(picId,picPath) {
//	alert(11);
var picId = picId;
var path = new Array(); 
path = picPath.split(",");
var p = path.length;

if(p != 1) {
	for(var i = 1; i <= p; i++) {
		var k = i-1;
		var a='test'+i;
		var v ='<a href="#" id='+a+' data-pid='+picId+' data-url='+path[k]+' ><img src="'+_basePath + "/white/WhiteListManage!preViewPic.action?path="+ path[k] + '"  style="cursor:hand;padding:10px;margin:10px;max-width:150px;max-height:300px;"  alt=""/></a>'
		$('#showDiv').append(v);
	}
//	alert($('#showDiv').html());
	$('#showDiv').show();
	$('#showDiv').dialog({
		title: "图文列表",
		noheader: true,
		modal: true,
		toolbar: [
			{text: '关闭',
			  handler:function(){
			  	$('#showDiv').dialog('close');
			  }
			}
		]
	});
	
	for(var i = 1; i <= p; i++) {
		var a='test'+i;
		$("#"+a).bind('click',function(){
//			alert($(this).attr("data-pid")+"/"+$(this).attr("data-url"));
			preView($(this).attr("data-pid"),$(this).attr("data-url"));
		});
	}
} 
else {
	var url = _basePath+'/white/WhiteListManage!preViewPic.action?path='+path;
	$('#showPic').show();
	$(window).keydown(function(event){
		switch(event.which) {
			case 38:
			case 40:
				break;
			default:
				$('#showPic').dialog('close');
		}
	});
	$('#showPic').dialog({
		noheader: true,
		modal: true,
		content: '<div algin="center"  id="block1" onmouseout="drag=0;" onmouseover="dragObj=block1; drag=1;" class="dragAble"><img border="0" name="imageshow" picId="'+picId+'" id="images1" src="'+url+'"/></div>',
		fit: true,
		toolbar: [
			{text: '关闭',
			  handler:function(){
			  	$('#showPic').dialog('close');
			  }
			}, /**'-', {
				text: '下载',
				handler:function() {
					downloadPic(obj);	
				}
			},*/
			'-',{//还原
				text:'<a href="#"><img src="'+_basePath+'/leeds/imgViewer/images/zoom.gif" width="20" height="20" style="cursor:hand" id="rotReal" title="还原" alt=""/></a>',
				handler:function(){
				
				}
			},'-',{//放大
				text:'<a href="#"><img src="'+_basePath+'/leeds/imgViewer/images/zoom_in.gif" width="20" height="20" style="cursor:hand" onClick="bigit();" title="放大" alt=""/></a>',
				handler:function(){
				
				}
			},'-',{//缩小
				text:'<a href="#"><img src="'+_basePath+'/leeds/imgViewer/images/zoom_out.gif" width="20" height="20" style="cursor:hand" onClick="smallit();" title="缩小" alt=""/></a>',
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
		]
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
}

}
/**预览查看	END	**/

var bitemp = 0;
//Enlarge image 图片放大
/**
 * 图片放大	start
 */
function bigit() {
	if (bitemp < 10) {
		var w = parseInt(document.images["imageshow"].width);
		var h = parseInt(document.images["imageshow"].height);
		document.images["imageshow"].width = w * 1.2;
		document.images["imageshow"].height = h * 1.2;
		bitemp++;
	}
}
/**	END	*/

//Shrink image  图片缩小
/***
 * 图片缩小	start
 */
function smallit() {
	if (bitemp > -5) {
		var w = parseInt(document.images["imageshow"].width);
		var h = parseInt(document.images["imageshow"].height);
		document.images["imageshow"].width = w / 1.2;
		document.images["imageshow"].height = h / 1.2;
		bitemp--;
	}
}
/** 图片缩小	end	*/

//rotate&restore image
window.imgReadInit = function() {
	// rotate image
	window.param = {
		right : document.getElementById("rotRight"),
		left : document.getElementById("rotLeft"),
		real : document.getElementById("rotReal"),
		img : document.getElementById("images1"),
		rot : 0
	};
	window.fun = {
		right : function() {
			param.rot = window.remRotate.getRot();
			param.rot += 1;
			param.img.className = "rot" + param.rot;
			if (param.rot === 4) {
				param.rot = 0;
			}
			window.remRotate.writeRot(param.rot);
		},
		left : function() {
			param.rot = window.remRotate.getRot();
			param.rot -= 1;
			if (param.rot === -1) {
				param.rot = 3;
			}
			param.img.className = "rot" + param.rot;
			window.remRotate.writeRot(param.rot);
		},
		// restore image
		real : function() {
			param.rot = window.remRotate.getRot();
			param.img.className = "rot" + param.rot;
			document.images["imageshow"].height = (document.images["imageshow"].height / document.images["imageshow"].width)
					* window._WIDTH;
			document.images["imageshow"].width = window._WIDTH;
			$('#block1').css('top', '10px');
			$('#block1').css('left', '30px');
			bitemp = 0;
		}
	};

	param.right.onclick = function() {
		fun.right();
	};
	param.left.onclick = function() {
		fun.left();
	};
	param.real.onclick = function() {
		fun.real();
	};
	window._WIDTH = 500;
//	checkShow($('.left').find('a:first')[0]);
//	statusShow();
	window.remRotate.init();
};

//-------------记忆旋转--------------------------------------------------------begin-----
window.remRotate = {
	cookieId : function() {
		return 'img_' + $('#images1').attr('picId');
	},
	init : function() {
		var imageShow = $('#images1');
		imageShow.addClass('rot' + this.getRot());
	},
	writeRot : function(rot) {
		$.cookie(this.cookieId(), rot, {
					expires : 1000,
					path : _basePath
				});
		console.log($.cookie(this.cookieId()));
	},
	getRot : function() {
		var rot = $.cookie(this.cookieId());
		if (rot == undefined)
			return 0;
		else
			return parseInt(rot);
	}
};
// -------------记忆旋转--------------------------------------------------------end-----
