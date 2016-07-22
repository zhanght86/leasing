//交换分屏---------------------------------------begin
var ScreenSwitcher = {
	layoutSelctor: '#cc',
	initPos: 'east',
	
	init: function(layoutSelctor, initPos) {
		this.layoutSelctor = layoutSelctor || this.layoutSelctor;
		this.initPos = initPos || this.initPos;
	},
	
	poss: {west: 'east', east: 'west'},
	doSwitch: function(callback) {
		var curPos = this.getCurPos();
		var layoutSelctor = this.layoutSelctor;
		var p = $(layoutSelctor).layout('panel', curPos);
		var opts = $.extend({}, p.panel('options'));
		opts.region = this.poss[curPos];
		opts.collapsed = true;
		$(layoutSelctor).layout('collapse', curPos);
		setTimeout(function(){
			$(layoutSelctor).layout('remove', curPos);
			$(layoutSelctor).layout('add', opts);
			$(layoutSelctor).layout('expand', ScreenSwitcher.poss[curPos]);
			$.cookie(ScreenSwitcher.cookieId(), ScreenSwitcher.poss[curPos], {expires:1000});
			if(typeof(callback)=='function') callback();
		},500);
	},
	cookieId: function() {
		return 'fp_pos';
	},
	getCurPos: function() {
		var pos = $.cookie(this.cookieId());
		if(pos==undefined) return this.initPos;
		else return pos;
	}
}
//交换分屏---------------------------------------end