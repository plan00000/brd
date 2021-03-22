<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<meta charset="utf-8">
<title>城际车后台管理</title>
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;">
</head>
<body>
<div class="container">
	<div class="Warning-box clearfix p40">
		<h1 class="tit_warning t_error">SORRY</h1>
		<div class="prompt">
			<p align="center"><span class="h3 fb">抱歉！您所要访问的页面不存在。</span></p>
			<p align="center">您要访问的页面已删除或者暂时不能用。</p>
			<p>&nbsp;</p>
			<p align="center"><a href="#" target=main onclick="javascript:history.go(-1);">&raquo; 返回上一级页面</a>　　<a href=<c:url value="/"/>>&raquo; 返回网站首页</a></p>										
		</div>
	</div>
</div>
</body>
</html>