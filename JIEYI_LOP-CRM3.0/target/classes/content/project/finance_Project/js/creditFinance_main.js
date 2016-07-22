

/**
 * iframe自适应高度
 */
function iframeLoad() {
	var iframeNode = $("#tasktabcontent iframe");
	iframeNode.each(function(){
		if($(this).css("display")!="none"){
			var url=$(this).attr("src");
			if(url==null||url==""){
				$(this).height(0);
			}else{
				var height = ($(this).contents().find("body").height() + 45);
				height = height < 300 ? 300 : height;
				$(this).height(height);
			}
		}
	});
}

window.setInterval("iframeLoad()", 50);

$(document).ready(function(){

	$("#tasktab .Label_act").click(function(){
		
		var t = $(this);
		var isload = t.data("isload");
		var tabid = t.data("tabid");
		var CREDIT_ID=$("input[name=CREDIT_ID]").val();
		var MODEL=$("input[name=MODEL]").val();
		
		
			var url = "CreditFinanceMain!searchTask.action?SORT="+t.attr("sort")+"&CREDIT_ID="+CREDIT_ID+"&MODEL="+MODEL;
			tabid = "tabid"+new Date().getTime();
			var iframe = $("<iframe id='"+tabid+"' src=''>")
				.attr("width","100%")
				.attr("frameborder","0")
				.attr("border","0")
				.attr("scrolling","no")
				.attr("src",url);
			$("#tasktabcontent").append(iframe);
			t.data("tabid",tabid);
			t.data("isload",true);
		
		$("#tasktabcontent iframe").hide();
		$("#"+tabid).show().contents().find("#searchTxt").focus();
		$("#tasktab .mytab").addClass("Label_act").removeClass("Label");
		t.removeClass("Label_act");
		t.addClass("Label");
	});
	
	$(".btntabfirst").click(function(){
		$("#tasktab .mytab").first().click();
	});
	
	$(".btntablast").click(function(){
		$("#tasktab .mytab").last().click();
	});
	
	$(".btntabprev").click(function(){
		$("#tasktab .mytab.Label").prev().click();
	});
	
	$(".btntabnext").click(function(){
		$("#tasktab .mytab.Label").next().click();
	});
	
	$(window).keypress(function(event){
		switch(event.keyCode) {
			case 34 : $(".btntablast").click();
				break;
			case 33 : $(".btntabfirst").click();
				break;
			case 37 : $(".btntabprev").click();
				break;
			case 39 : $(".btntabnext").click();
				break;
		}
	});
	
	$(".btntabfirst").click();
});
