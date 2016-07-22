<%@page import="com.pqsoft.skyeye.CentralController"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%if (CentralController.getM() < 0) response.sendRedirect(request.getContextPath());%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="res/jquery/jquery-1.4.2.min.js"></script>
<script type="text/javascript">
	$(function() {
		if (top.location != self.location) {
			top.location = self.location;
		}
	});
	window.onresize = window.onload = function() {
		var w, h
		if (!!(window.attachEvent && !window.opera)) {
			h = document.documentElement.clientHeight;
			w = document.documentElement.clientWidth;
		} else {
			h = window.innerHeight;
			w = window.innerWidth;
		}
		var bgImg = document.getElementById('bg').getElementsByTagName('img')[0];
		bgImg.width = (w - 1);
		bgImg.height = (h - 1);
	}
</script>
</head>
<body style="margin: 0; padding: 0;">
	<div id="bg">
		<img src="restart.jpg" alt="HIGH">
	</div>
	<div id="msg" name="msg" />
	</div>
</body>
</html>
