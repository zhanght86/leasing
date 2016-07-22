var switchPortal = function(){
	var cookie_portal ={};
	$("#left .win").each(function() {
		var portal = $(this).data("portal");
		portal.side = "left";
		cookie_portal[portal.sid]=portal;
	});
	$("#center .win").each(function() {
		var portal = $(this).data("portal");
		portal.side = "center";
		cookie_portal[portal.sid]=portal;
	});
	$("#right .win").each(function() {
		var portal = $(this).data("portal");
		portal.side = "right";
		cookie_portal[portal.sid]=portal;
	});
	//$.JSONCookie("portal", cookie_portal, {});
}


var Panels = {
	base:function(json){
		var title = json.title;
		var noClose = json.noClose;
		var menu = json.menu;
		var ico = json.ico;
		var sid = json.sid;
		var side = json.side;
		var html = json.html;
		
		var divContent = $("<div>");//<div class='content'></div>
		if(html)divContent.append(html);
		var win = $("<div class='win'></div>")
			.data("portal",json)
			.attr("sid",sid)
			.append(
				function(){
					var caption = $("<div class='caption'></div>")
						.append(
							$("<b></b>")
						).append(
							$("<label></label>").html(title)
						);
					if(!noClose){
						caption.append(
							$("<a></a>").click(function(){win.remove();switchPortal();})
						);
					}
					if(menu&&menu.length!=0){
						var ul = $("<ul></ul>");
						for(var i=0;i<menu.length;i+=1){
							ul.append(
								$("<li></li>").html(menu[i][1]).attr("title",menu[i][2]||"").data("id",menu[i][0]).data("action",menu[i][3]||"").click(function(){
									var _this = $(this);
									openTab(_this.data("action"));
								})
							);
						}
						$("<i></i>").append(ul).appendTo(caption);
					}
					return caption;
				}()
			).append(divContent).data("content",divContent);
		
		$("#"+side).append(win);
		return win;
	},
	col2:function(json){
		var win = this.base(json);
		var divContent = win.data("content");
	
		var list = json.list;
		var field1 = json.field1;
		var field2 = json.field2;
		var field1action = json.field1action;
		var field2action = json.field2action;
		var field1defaultValue = json.field1defaultValue;
		var field2defaultValue = json.field2defaultValue;
		
		var ul = $("<ul>").addClass("col2").appendTo(divContent);
		
		for(var i=0;i<list.length;i+=1){
			
			$("<li>").append(
				$("<span>").css({width:"80%"}).html(list[i][field1]||field1defaultValue)
			).append(
				$("<span>").css({width:"20%","text-align":"right"}).html(list[i][field2]||field2defaultValue)
			).mouseover(function(e){
				$(this).css("background-color","#ddd");
			}).mouseout(function(e){
				$(this).css("background-color","#fff");
			}).appendTo(ul);
		}
		return win;
	},
	iframe:function(json){
		var win = this.base(json);
		var divContent = win.data("content");
		$("<iframe>")
			.attr("frameborder",0)
			.attr("border",0)
			.attr("src",json.src)
			.css({width:"100%",height:json.height||"250px"})
			.appendTo(divContent);
		return win;
	},
	ajax:function(json){
		var title = json.title;
		var noClose = json.noClose;
		var menu = json.menu;
		var ico = json.ico;
		var sid = json.sid;
		var side = json.side;
		var divContent = $("<div>");//<div class='content'></div>
		$.get(json.src,function(html){
			divContent.append(html);
		});
		var win = $("<div class='win'></div>")
			.data("portal",json)
			.attr("sid",sid)
			.append(
				function(){
					var caption = $("<div class='caption'></div>")
						.append(
							$("<b></b>")
						).append(
							$("<label></label>").html(title)
						);
					if(!noClose){
						caption.append(
							$("<a></a>").click(function(){win.remove();switchPortal();})
						);
					}
					if(menu&&menu.length!=0){
						var ul = $("<ul></ul>");
						for(var i=0;i<menu.length;i+=1){
							ul.append(
								$("<li></li>").html(menu[i][1]).attr("title",menu[i][2]||"").data("id",menu[i][0]).data("action",menu[i][3]||"").click(function(){
									var _this = $(this);
									openTab(_this.data("action"));
								})
							);
						}
						$("<i></i>").append(ul).appendTo(caption);
					}
					return caption;
				}()
			).append(divContent).data("content",divContent);
		
		$("#"+side).append(win);
		return win;
	},
	load:function(json){
		var win = this.base(json);
		var divContent = win.data("content");
		$.get(json.src,function(html){
			divContent.append(html);
		});
		return win;
	}
};

$(document).ready(function() {

// 设置每个小面板可以拖动
	$(".sortable").sortable( {
		handle : '.caption',
		opacity : 0.8,
		connectWith : '.sortable',
		placeholder : 'ui-widget-content win',
		start : function(e, ui) {
			ui.item.find("div.content").hide();
		},
		stop : function(e, ui) {
			ui.item.find("div.content").show();
			ui.item.css("z-index", 1);
			switchPortal();
		}
	});
});
