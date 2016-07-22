function showPic(obj) {
	var picId = $(obj).attr("picId");
	var path = $(obj).attr("picPath");
	var url = _basePath+'/leeds/cust_info_input/CustInfoInput!readPic.action?path='+path;
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
			}, '-', {
				text: '下载',
				handler:function() {
					downloadPic(obj);	
				}
			},'-',{//还原
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
				text:'<a href="#"><img src="'+_basePath+'/leeds/imgViewer/images/left.gif" width="20" height="20" style="cursor:hand" id="rotLeft" title="左转" alt=""/></a>',
				handler:function(){
				
				}
			},'-',{//右转
				text:'<a href="#"><img src="'+_basePath+'/leeds/imgViewer/images/right.gif" width="20" height="20" style="cursor:hand" id="rotRight" title="右转" alt=""/></a>',
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

//下载图片
function downloadPic(obj){
	window.location.href=_basePath+"/crm/Customer!downFile1.action?path="+encodeURI($(obj).attr("picPath"))+"&filename="+$(obj).attr("picName"); 
}

//Shrink image  图片缩小
var bitemp = 0;
function smallit() {
	if (bitemp > -5) {
		var w = parseInt(document.images["imageshow"].width);
		var h = parseInt(document.images["imageshow"].height);
		document.images["imageshow"].width = w / 1.2;
		document.images["imageshow"].height = h / 1.2;
		bitemp--;
	}
}

// Enlarge image 图片放大
function bigit() {
	if (bitemp < 10) {
		var w = parseInt(document.images["imageshow"].width);
		var h = parseInt(document.images["imageshow"].height);
		document.images["imageshow"].width = w * 1.2;
		document.images["imageshow"].height = h * 1.2;
		bitemp++;
	}
}

// switch image
function seeBig(_this) {
	var imageShow = $('#images1')[0];
	var imgIcon = $(_this).find('img')[0];
	imageShow.src = imgIcon.src;
	imageShow.height = imgIcon.height * 10;
	imageShow.width = imgIcon.width * 10;
	$(imageShow).attr('picId', $(_this).attr('pictureId'));
	window.fun.real();
}

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
// -------------记忆旋转--------------------------------------------------------begin-----
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
