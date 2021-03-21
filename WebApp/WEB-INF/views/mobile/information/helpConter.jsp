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
<title>帮助中心</title>
<script type="text/javascript">
document.title="帮助中心";
</script>
</head>
<body>
	<div class="advisory">
		<ul>
			<c:forEach items="${helpList }" var="help">
				<a href="${ctx}/weixin/information/toArticleDetails/${help.id}"><li>${help.title }</li></a>
			</c:forEach>
		</ul>
	</div>
</body>
</html>