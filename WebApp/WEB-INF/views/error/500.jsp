<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>
<%	
	//设置返回码200，避免浏览器自带的错误页面
	response.setStatus(200);
	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(exception.getMessage(), exception);
%>

<html>
	<head>
		<title>500 - 系统内部错误</title>
		<meta name="description" content="">
		<meta name="keywords" content="">
		<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;">
	</head>

	<body>
		<div class="container">
			<div class="Warning-box clearfix p40">
				<h1 class="tit_warning t_error">SORRY</h1>
				<div class="prompt">
					<p align="center"><span class="h3 fb">系统繁忙，请稍后重试！</span></p>
					<p align="center">很抱歉，帮人贷暂时无法处理您的访问请求！</p>					
					<p>&nbsp;</p>
					<p align="center"><a href="#" target=main onclick="javascript:history.go(-1);">&raquo; 返回上一级页面</a>　　<a href=<c:url value="/"/>>&raquo; 返回网站首页</a></p>				
				</div>
			</div>
		</div>
	</body>
</html>
