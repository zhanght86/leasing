// move image
drag = 0;
move = 0;
var ie = document.all;
var nn6 = document.getElementById && !document.all;
var isdrag = false;
var y, x;
var oDragObj;

function moveMouse(e) {
	if (isdrag) {
		oDragObj.style.top = (nn6 ? nTY + e.clientY - y : nTY + event.clientY
				- y)
				+ "px";
		oDragObj.style.left = (nn6 ? nTX + e.clientX - x : nTX + event.clientX
				- x)
				+ "px";
		return false;
	}
}

function initDrag(e) {
	var oDragHandle = nn6 ? e.target : event.srcElement;
	var topElement = "HTML";
	while (oDragHandle.tagName != topElement
			&& oDragHandle.className != "dragAble") {
		oDragHandle = nn6 ? oDragHandle.parentNode : oDragHandle.parentElement;
	}
	if (oDragHandle.className == "dragAble") {
		isdrag = true;
		oDragObj = oDragHandle;
		nTY = parseInt(oDragObj.style.top + 0);
		y = nn6 ? e.clientY : event.clientY;
		nTX = parseInt(oDragObj.style.left + 0);
		x = nn6 ? e.clientX : event.clientX;
		document.onmousemove = moveMouse;
		return false;
	}
}
document.onmousedown = initDrag;
document.onmouseup = new Function("isdrag=false");

// Shrink image  图片缩小
var bitemp = 0;
function smallit() {
	//if (bitemp > -5) {
		var w = parseInt(document.images["imageshow"].width);
		var h = parseInt(document.images["imageshow"].height);
		document.images["imageshow"].width = w / 1.2;
		document.images["imageshow"].height = h / 1.2;
		bitemp--;
	//}
}

// Enlarge image 图片放大
function bigit() {
	//if (bitemp < 10) {
		var w = parseInt(document.images["imageshow"].width);
		var h = parseInt(document.images["imageshow"].height);
		document.images["imageshow"].width = w * 1.2;
		document.images["imageshow"].height = h * 1.2;
		bitemp++;
	//}
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

// rotate&restore image
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
			//document.images["imageshow"].height = (document.images["imageshow"].height / document.images["imageshow"].width)
			//		* window._WIDTH;
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
	checkShow($('.left').find('a:first')[0]);
	statusShow();
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

// -------------审核-------------------------------------------------------------begin-----
var statusV = "";
var pictureId = "";
var projectId = "";

function checkPicture(type) {
	var statusV = $("#statusV").val();
	if(type=="1"){
		$.messager.confirm('审核确认', '确定审核通过该资料？', function(r) {
			if (r) {
				$.ajax({
					url : _basePath
							+ "/leeds/cust_info_input/CustInfoInput!updatePictureStatus.action",
					data : {
						ID : pictureId,
						CHECK_STATUS : 2
					},
					dataType : "json",
					success : function(json) {
						if (json.flag) {
							alert("审核成功！");
							window.location.href = _basePath
									+ "/leeds/cust_info_input/CustInfoInput!seeImg.action?PROJECT_ID="
									+ projectId+"&CUST_TYPE="+$("#CUST_TYPE").val()+"&PHASE="+$("#PHASE").val();
						} else {
							alert("审核失败！");
						}
					}
				});
			}
		});
	}else {
				$.messager.prompt('意见', '请输入驳回意见', function(r) {
					if (r) {
						var checkRemark = r;
						$.ajax({
							url : _basePath
									+ "/leeds/cust_info_input/CustInfoInput!updatePictureStatus.action",
							data : {
								ID : pictureId,
								CHECK_STATUS : 3,
								CHECK_REMARK : checkRemark
							},
							dataType : "json",
							success : function(json) {
								if (json.flag) {
									alert("驳回成功！");
									window.location.href = _basePath
											+ "/leeds/cust_info_input/CustInfoInput!seeImg.action?PROJECT_ID="
											+ projectId+"&CUST_TYPE="+$("#CUST_TYPE").val()+"&PHASE="+$("#PHASE").val();
								} else {
									alert("驳回失败！");
								}
							}
						});
					}
				});
	}
	
}

function statusHide() {
	$("#statusDiv").hide();
}
function statusShow() {
	$("#statusDiv").show();
}
function checkShow(_this) {

	statusV = $(_this).attr("statusV");
	pictureId = $(_this).attr("pictureId");
	var checkName = $(_this).attr("checkName");
	var checkRemark = $(_this).attr("checkRemark");
	projectId = $(_this).attr("projectId");

	if (statusV == "1") {
		$("#statusText").text("待审核");
		$("#statusDiv").show();
		$('#statusDiv').css('background-color', 'yellow');
		$("#statusA").show();
		$("#statusB").show();
		$("#tgText").hide();
	} else if (statusV == "2") {
		$("#statusText").text("审核通过");
		$("#tgText").text("审核人： " + checkName);
		$("#tgText").show();
		$('#statusDiv').css('background-color', '#99FF66');
		$("#statusA").hide();
		$("#statusB").hide();
	} else if (statusV == "3") {
		$("#statusText").text("驳回");
		$("#tgText").text("驳回人： " + checkName + " 驳回备注: " + checkRemark);
		$('#statusDiv').css('background-color', 'red');
		$("#tgText").show();
		$("#statusA").hide();
	} 
}
// -------------审核-------------------------------------------------------------end-----
