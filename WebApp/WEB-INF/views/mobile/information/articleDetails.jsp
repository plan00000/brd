<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>文章详情</title>
<script type="text/javascript">
var a = "${util:htmlUnescape(information.title)}";
	document.title=a;
	
/* 	var $iframe = $('<iframe src="/favicon.ico"></iframe>').on('load', function() {
		setTimeout(function() {
		$iframe.off('load').remove()
		}, 0)
	}).appendTo($body) */
</script>
</head>
<body>
	<div class="article">
		<p style="height: auto;">${information.title }</p>
		<samp>${addDate }</samp>
	</div>
	<div class="article_text" id = "cont">
		<p style="line-height:normal;">${util:htmlUnescape(information.content)}</p>
	</div>
</body>
</html>