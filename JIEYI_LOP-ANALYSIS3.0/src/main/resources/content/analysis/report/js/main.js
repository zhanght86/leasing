
/**
 * iframe自适应高度
 */
function iframeLoad() {
	var iframeNode = $("#tabbody iframe");
	iframeNode.each(function(){
		if($(this).css("display")!="none"){
			var url=$(this).attr("src");
			if(url==null||url==""){
				$(this).height(0);
			}else{
				var height = ($(this).contents().find("body").height());
				height = height < 300 ? 300 : height;
				$(this).height(height);
			}
		}
	});
}

window.setInterval("iframeLoad()", 50);

$(document).ready(function(){

	$("#tabhead .Label_act").click(function(){
		var t = $(this);
		if (t.attr("url") == undefined || t.attr("url") =="" || t.attr("url") == null ) {
			return;
		}
		var isload = t.data("isload");
		var tabid = t.data("tabid");
		var url = _basePath + t.attr("url");
		if (!isload) {
			tabid = "tabid"+new Date().getTime();
			var iframe = $("<iframe id='"+tabid+"' src=''>")
				.attr("width","100%")
				.attr("frameborder","0")
				.attr("border","0")
				.attr("scrolling","no")
				.attr("src",url);
			$("#tabbody").append(iframe);
			t.data("tabid",tabid);
			t.data("isload",true);
		}
		$("#tabbody iframe").hide();
		$("#"+tabid).show().contents().find("#searchTxt").focus();
		$("#tabhead .mytab").addClass("Label_act").removeClass("Label");
		t.removeClass("Label_act");
		t.addClass("Label");
	});
	
	window.changeTab=function(){
		
	}
	
});
$(document).ready(function(){
    $(".tab01-nav_active:first").click();
});
function showDiv(name,tabThis){
	$(".tab01-nav_active").attr("class","tab01-nav");
	$(tabThis).attr("class","tab01-nav_active");
	try{
    	$('[id*="chartFrame"]').hide();
	}catch(e){}
    $('#' + name).show();
}