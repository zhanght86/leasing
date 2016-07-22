
var context = require.config({
	urlArgs: "___=" +  (new Date()).getTime()
	,enforceDefine : false
    ,baseUrl: _basePath +'/js'
    
    ,paths: {
//    	jquery	 			: 'module/jquery-1.10.2.min'
//    	,'jquery.mobile' 	: 'module/jquery.mobile-1.4.5/jquery.mobile-1.4.5',
        ejs 				: 'module/ejs_0.9_alpha_1_production'
        ,angular 			: 'module/angular'
        ,'wx'				: 'module/jweixin-1.0.0'
//        ,'bootstrap'		: 'module/bootstrap-3.3.4-dist/js/bootstrap.min'
        
        // user file
    	,WeiXinOpenAction 	: 'module/WeiXinOpenAction'
		,WeiXinComm 		: 'module/WeiXinComm1'
		,'jquery.layout' 	: 'module/jquery.layout'
    }
	,map : {
		'*' : {
			css : 'module/css.min'
		}
	}
	,shim: {  
	    "WeiXinComm": {  
//	        deps: ["jquery"] ,
			exports: 'WeiXinComm'
	    }  
		/*,bootstrap : {  
            deps : [ 'jquery' ],  
            exports : 'bootstrap'  
       }  */
		,angular:{
            exports:'angular'
        }
		,ejs :{
			exports:'ejs'
		}
		,WeiXinOpenAction:{
			exports:'WeiXinOpenAction'
		}
//		,'jquery.mobile': ['jquery']
	}  
});
// 有关jquery 插件加载说明 http://www.requirejs.cn/docs/api.html#config-shim
/*context(['jquery'], function() {
	
	jQuery.noConflict(true);
    console.log("jquery 版本："+ jQuery.fn.jquery ) ;
});*/
console.log("jquery 版本："+ jQuery.fn.jquery ) ;
