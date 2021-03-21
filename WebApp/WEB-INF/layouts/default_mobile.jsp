<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="shiro" uri="http://www.springside.org.cn/tags/shiro"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + path;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta name="MobileOptimized" content="400">
<meta name="format-detection" content="telephone=no">
<title> </title>
<link href="${ctx }/static/brd-mobile/css/style.css" rel="stylesheet"
	type="text/css" />
<script src="${ctx }/static/js/jquery-1.11.1.min.js"></script>
<script src="${ctx }/static/js/jquery-ui-1.11.4.custom/jquery-ui.min.js"></script>
<script src="${ctx}/static/brd-mobile/js/layer.m.js"></script>
<script src="${ctx }/static/brd-mobile/js/global.js"></script>

<sitemesh:head />
</head>
<body>
	<sitemesh:body />
	<tags:mobile-common-dialog></tags:mobile-common-dialog>
	<tags:mobile-loading></tags:mobile-loading>

</body>
</html>
