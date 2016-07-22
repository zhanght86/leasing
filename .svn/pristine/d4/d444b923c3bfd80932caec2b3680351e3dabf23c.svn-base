<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>融资租赁管理平台</title>
	<link href="favicon.ico" type="image/x-icon" rel="shortcut icon">
	<script type="text/javascript" src="res/jquery/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="res/jquery/jquery-ui-1.8.1.min.js"></script>
	<script type="text/javascript" src="res/enc/jquery.md5.js"></script>
	<script type="text/javascript" src="res/jquery/jquery.cookie.js"></script>
	<script type="text/javascript" src="js/login.js"></script>
	<link href="login.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div id="bg"><img src="./Sys!download.action?fileName=sys_img_loginbg.png"></div>
<div id="msg" name="msg"/></div>
<script type="text/javascript">

	$(function(){
		if (top.location != self.location) {
			top.location = self.location;
		}
	});
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
<div id="logo"><img src="./Sys!download.action?fileName=sys_img_loginLogo.png" width="350" height="48" /></div>
<div class="login">
<form name="frmLogin" action="./" method="post">
	<input type="hidden" id="request" value="${param.REQUEST }" >
    <input id="USERCODE" name="Input" class="input_01" type="text" autocomplete="off" tabindex="1" placeholder="用户名"/>
    <input id="PASSWORD" name="PASSWORD" type="password" class="input_02" autocomplete="off" tabindex="2" placeholder="密码"/>
    <input id="submit" name="input"  type="button"   class="button" />
    </form>
</div>
</body>
</html>
