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
<title>收徒赚佣</title>
</head>
<body>
	<div class="war">
        <div class="advisory">
<%--             <div class="advisory_t">
                <h1>${about.title }</h1>
            </div> --%>
            <div class="advisory_text">
                <p>${util:htmlUnescape(about.content) }</p>
        </div>
	</div>
	<jsp:include page="./footer.jsp"/>
	</div>
	 
</body>
</html>