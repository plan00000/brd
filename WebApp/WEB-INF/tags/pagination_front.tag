<%-- controller 那边必须设置  跳页参数 page --%>
<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="org.springframework.data.domain.Page" required="true"%>
<%@ attribute name="paginationSize" type="java.lang.Integer" required="true"%>
<%-- 跳转链接前缀 --%>
<%@ attribute name="hrefPrefix" type="java.lang.String" required="true"%>
<%-- 跳转链接后缀 --%>
<%@ attribute name="hrefSubfix" type="java.lang.String" required="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="util" uri="functions" %>
<%
int pagination_size = 5; 
int current =  page.getNumber() + 1;
int begin = Math.max(1, current - pagination_size/2);
int end = Math.min(begin + (pagination_size - 1), page.getTotalPages());
int totalPages=page.getTotalPages();

request.setAttribute("current", current);
request.setAttribute("begin", begin);
request.setAttribute("end", end);
request.setAttribute("totalPages", totalPages);
%>
<div style="float:right;">
<div id="pageT" class="page">
				<% if (page.hasPrevious()){%>
					<span><a href="${hrefPrefix}?page=${current-1}&${hrefSubfix}">上一页</a></span>
	         	<%}else{%>
	         		<span><a href="javascript:void(0);">上一页</a></span>
	         	<%} %>
	         	
	         	<c:choose>
	         		<c:when test="${totalPages <= 6}">
	         			<c:forEach var="i" begin="1" end="${totalPages}">
			            	<c:choose>
				                <c:when test="${i == current}">
				                	<span><a href="javascript:void(0);" class="current">${i}</a></span>
				                </c:when>
				                <c:otherwise>
				                	<span><a href="${hrefPrefix}?page=${i}&${hrefSubfix}">${i}</a></span>
				                </c:otherwise>
			            	</c:choose>
		        		</c:forEach>
	         		</c:when>
	         		<c:otherwise>
			         	<c:if test="${current > 4}">
			         		<span><a href="${hrefPrefix}?page=1&${hrefSubfix}">1</a></span>
			         		<span><a href="javascript:void(0);">...</a></span>
						</c:if>
						<c:choose>
							<c:when test="${current+2 < totalPages && current < 4}">
					         	<c:forEach var="i" begin="${begin}" end="${end+1}">
					            	<c:choose>
						                <c:when test="${i == current}">
						               			<span><a href="javascript:void(0);" class="current">${i}</a></span>
						                </c:when>
						                <c:otherwise>
						                	<span><a href="${hrefPrefix}?page=${i}&${hrefSubfix}">${i}</a></span>
						                </c:otherwise>
					            	</c:choose>
				        		</c:forEach>
							</c:when>
							<c:when test="${current+2 < totalPages && current == 4}">
								<span><a href="${hrefPrefix}?page=1&${hrefSubfix}" >1</a></span>
					         	<c:forEach var="i" begin="${begin}" end="${end}">
					            	<c:choose>
						                <c:when test="${i == current}">
						                	<span><a href="javascript:void(0);" class="current">${i}</a></span>
						                </c:when>
						                <c:otherwise>
						                	<span><a href="${hrefPrefix}?page=${i}&${hrefSubfix}">${i}</a></span>
						                </c:otherwise>
					            	</c:choose>
				        		</c:forEach>
							</c:when>
							<c:when test="${current+2 < totalPages && current > 4}">
					         	<c:forEach var="i" begin="${begin}" end="${end}">
					            	<c:choose>
						                <c:when test="${i == current}">
						                	<span><a href="javascript:void(0);" class="current">${i}</a></span>
						                </c:when>
						                <c:otherwise>
						                	<span><a href="${hrefPrefix}?page=${i}&${hrefSubfix}">${i}</a></span>
						                </c:otherwise>
					            	</c:choose>
				        		</c:forEach>
							</c:when>
							<c:otherwise>
								<c:forEach var="i" begin="${end-4}" end="${end}">
					            	<c:choose>
						                <c:when test="${i == current}">
						                	<span><a href="javascript:void(0);" class="current">${i}</a></span>
						                </c:when>
						                <c:otherwise>
						                	<span><a href="${hrefPrefix}?page=${i}&${hrefSubfix}">${i}</a></span>
						                </c:otherwise>
					            	</c:choose>
				        		</c:forEach>
							</c:otherwise>
						</c:choose>
	         		</c:otherwise>
	         	</c:choose>
        		<c:if test="${current+2 < totalPages && end < totalPages && totalPages > 6}">
        			<span><a href="javascript:void(0);">...</a></span>
        		</c:if>
	         	
				<% if (page.hasNext()){%>
					<span><a href="${hrefPrefix}?page=${current+1}&${hrefSubfix}">下一页</a></span>
		        <%}else{%>
		       		<span><a href="javascript:void(0);">下一页</a></span>
		        <%} %>
<span>共<%=totalPages%>页，当前第${current}页</span>
</div>
</div>
<script type="text/javascript">
	$("#pageT span").attr("style","float: left;");
</script>
