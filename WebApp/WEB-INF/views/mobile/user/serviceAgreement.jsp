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
<title>帮人贷服务协议</title>
<script type="text/javascript">
	document.title="帮人贷服务协议";
</script>
</head>
<body>
<%-- 	<div class="article">
		<p>${information.title }</p>
		<samp>${addDate }</samp>
	</div> --%>
	<div class="article_text" id = "cont">
		<p>${util:htmlUnescape(agreement.content)}</p>
	</div>
</body>
</html>