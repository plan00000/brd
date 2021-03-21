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
<title>收徒送礼</title>
<script type="text/javascript">
document.title="收徒送礼";
</script>
</head>
<body>

	<div class="reward">
	             <c:choose>
               <c:when test="${empty activity.picurl }">
                   	<img src="${ctx }/static/brd-mobile/images/ban4.jpg" style="width:100%;height:200px">
                   	</c:when>
                   	<c:otherwise>
					<img src="${activity.picurl }" style="width:100%;height:200px">
                   	</c:otherwise>
             </c:choose>
	</div>
		<div class="content">
            <div class="stered">
            <p>${activity.activityCopy }</p>
            </div>
            <div class="s_reg">
           	 <p>朋友将收到来自您的礼包：</p>
             <dl>
                 <dt>
                    <c:choose>
               			<c:when test="${empty user.headimgurl }">
               				<img src="${ctx}/static/brd-mobile/images/u1157.png">
               			</c:when>
               			<c:otherwise>
							<img src="${ctx}/files/displayProThumb?filePath=${user.headimgurl}&thumbWidth=120&thumbHeight=80" />
               			</c:otherwise>
                   </c:choose>
                   </dt>
                 <dd>${user.username }</dd>
              </dl>
              <a href="${ctx}/weixin/sharefriend/main/${user.getId()};JSESSIONID=<%=request.getSession().getId()%>"><span>立即发礼包给好友</span></a>
            </div>
            <div class="cash_text">
        	<span>活动规则：</span>
			${util:htmlUnescape(activity.activityRule)}
         </div>       
         </div>       
</body>
</html>