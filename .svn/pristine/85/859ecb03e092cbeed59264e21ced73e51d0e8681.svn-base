<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="Cache-Control" content="no-cache" />
	<title>平强软件 融资租赁信息系统</title>
	<script type="text/javascript" src="res/jquery/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="res/jquery/jquery-ui-1.8.1.min.js"></script>
	<script type="text/javascript" src="res/enc/jquery.md5.js"></script>
	<script type="text/javascript" src="res/jquery/jquery.cookie.js"></script>
	<script type="text/javascript" src="js/login.js"></script>
	<script type="text/javascript">
		$(function(){
			if (top.location != self.location) {
				top.location = self.location;
			}
		});
	</script>
	<link rel="stylesheet" href="res/jquery/themes/redmond/jquery-ui-1.8.1.custom.css" type="text/css"></link>
	<link href="index/css/login.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="bg"><img src="index/images/login-bg.jpg"></div>
<div id="msg" name="msg"/></div>
<script type="text/javascript">
 window.onresize = window.onload = function(){
     var w,h
     if(!!(window.attachEvent && !window.opera))
     {
      h = document.documentElement.clientHeight;
      w = document.documentElement.clientWidth;
     }else{
      h = window.innerHeight;
      w = window.innerWidth;
     }
  document.getElementById('msg').value  ='窗口大小：' + 'width:' + w + '; height:'+h;
    var bgImg = document.getElementById('bg').getElementsByTagName('img')[0];
    bgImg.width = (w - 1);
    bgImg.height= (h-1) ;  
          
   }   
</script>
<div id="logo"><img src="index/images/login-logo.png" width="350" height="48" /></div>
<div class="login">
<form name="frmLogin" action="./" method="post">
    <input type="hidden" id="request" value="${param.REQUEST }">
    <input  name="USERCODE" id="USERCODE" class="input_01" type="text" autocomplete="off" tabindex="1"  onclick="this.value=''; focus()"/>
    <input name="PASSWORD" id="PASSWORD" type="password" class="input_02" autocomplete="off" tabindex="1"  onclick="this.value=''; focus()"/>
    <input  id="submit"  type="button"  class="button" />
    </form>
</div>
	<div id="tokenDialog">
		<input type="text" id="captcha">
	</div>
</body>
</html>
