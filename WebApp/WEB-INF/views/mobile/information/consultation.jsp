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
<title>活动资讯</title>
<script type="text/javascript">
document.title="资讯";
</script>
</head>
<body>
	<div class="advisory">
		<ul>
			<c:forEach items="${informationList }" var="information">
				<a
					href="${ctx}/weixin/information/toArticleDetails/${information.id}"><li>${information.title }</li></a>
			</c:forEach>
		</ul>
	</div>
</body>
</html>