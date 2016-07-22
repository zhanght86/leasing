$(function(){
	$(".menugroup").click(function(){
		$(".main").removeClass("check").hide();
		$(".check").removeClass("check");
		$(this).find("img").addClass("check");
		$("#"+$(this).attr("groupid")).show();
	}).eq(1).click();
	$(".exp").hide();
	$(".menugroup").hover(
	  function () {
		  $(this).next().show();
	  },
	  function () {
		  $(this).next().hide();
	  }
	);
	$(".menuCount").each(function(){
		var menuCountUrl = $(this).attr("url");
		if(menuCountUrl != null && menuCountUrl != ""){
			var url = _basePath + menuCountUrl;
			var t = $(this);
			$.get(url,function(count){
				t.addClass("tip").find("p").text(count);
			});
		}
	});
});