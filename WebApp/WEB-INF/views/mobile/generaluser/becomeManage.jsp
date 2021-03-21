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
<title>成为融资经理</title>
<script type="text/javascript">
$(document).ready(function(){
	var isManager = ${isManager}
	if(isManager>0){
		layer.open({
			content : "用户需成为融资经理才可赚取奖励",
			time : 3
		});	
	}
	$(".m_bottom").click(function(){
		location.href = "${ctx}/weixin/generalUserCenter/main?isManager=1";
	})
})
</script>
</head>
<body>
     <div class="manager"><p><img src="${advertisement.picurl }" ></p></div>
     <div class="m_bottom">成为融资经理</div>
      </body>
</html>