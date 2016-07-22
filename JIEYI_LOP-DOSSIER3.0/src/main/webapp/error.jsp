<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String basePath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>error页面</title>
<style type="text/css">
body {
    color: #656565;
    font-family: Arial,Helvetica,sans-serif;
    font-size: 14px;
    font-weight: bold;
    line-height: 22px;
    margin: 0;
    padding: 0;
}
ul, li {
    list-style: none outside none;
    margin: 0;
    padding: 0;
}
p {
    list-style: none outside none;
    margin: 0;
    padding: 0;
}
.main {
    top: 120px;
    width: 800px;
}
.img {
    background: url("<%=basePath %>/img/404.png") repeat scroll 0 0 transparent;
    float: left;
    height: 326px;
    width: 288px;
}
.img p {
    color: #FFFFFF;
    font-size: 58px;
    font-weight: bold;
    line-height: 58px;
    padding-top: 120px;
    text-align: center;
}
.img b{ color:#FFF;}
.text {
    float: right;
    padding-top: 8%;
    width: 50%;
}
.text b {
    font-family: "微软雅黑";
    font-size: 20px;
    line-height: 40px;
}
.text a {
    color: #656565;
    text-decoration: none;
}


</style>

</head>
<body>
<center>
<div style="">
<div style="height:50px;"></div>
<div class="main">
<div class="img"><p>error</p>
<b>错误页面</b>
</div>
<div style=" width:80px; float:left;"><img width="80" height="326" src="<%=basePath %>/img/shu_06.png"></div>
<div class="text" style="text-align:left">
<ul>
<li><img width="146" height="60" src="<%=basePath %>/img/baoqian_09.png">
	<hr /><b>Oops:<%=request.getAttribute("msg") %></b></li>
</ul>
</div>
<div style="clear: both;text-align: left;"><%=request.getAttribute("memo") %></div>
</div>
</div>
</center>
</body>
</html>
