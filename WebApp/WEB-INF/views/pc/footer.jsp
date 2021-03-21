<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" /> 
<div class="footer bton" >
   <div class="foot">
   	<div class="f_left">
       	<h1><img src="${ctx }/static/brd-front/images/u2781.png"></h1>
        <p>关注微信：帮人贷<br/>客服热线：${sysInfo.hotline}<br/><samp>指尖上的贷款神器<br/>口袋里的赚佣法宝</samp><br/>“贷贷相传，坐享其佣”</p>
        <dl>
        <c:choose>
			<c:when test="${empty sysInfo.qrCodeUrl}">
				<dt><img src="${ctx }/static/brd-front/images/ewn.png"></dt>
  			</c:when>
			<c:otherwise>
				<dt><img src="${sysInfo.qrCodeUrl}"></dt>	
 			</c:otherwise>
		</c:choose>
        <dd>扫一扫关注我</dd>
        </dl>
     </div>
     <span><a href="${ctx }/pc/agreement">《帮人贷服务协议》</a><br/>ICP证：闽B2-20100257&nbsp;&nbsp;&nbsp;&nbsp;Copyright © 帮人贷信息服务有限公司</span>
   </div>
  <%--  <div class="f_left">
       <h1><img src="images/u2781.png"></h1>
        <p>关注微信：帮人贷<br/>客服热线：${sysInfo.hotline}<br/><samp>指尖上的贷款神器<br/>口袋里的赚佣法宝</samp><br/>“贷贷相传，坐享其佣”</p>
       <dl>
           <dt>
           	 <c:choose>
			<c:when test="${empty sysInfo.qrCodeUrl}">
				<img src="${ctx }/static/brd-front/images/weix.png">
			   	</c:when>
			<c:otherwise>
				<img src="${sysInfo.qrCodeUrl}">	
			  </c:otherwise>
			</c:choose>
           </dt>
           <dd>帮人贷微信公众号<br/>开启掌上赚佣模式</dd>
       </dl>
       <p>客服热线<br/>${sysInfo.hotline}</p>
       <span><a href="${ctx }/pc/agreement">《帮人贷服务协议》</a><br/>ICP证：${sysInfo.icpNumber}&nbsp;&nbsp;&nbsp;&nbsp;Copyright © 帮人贷信息服务有限公司</span> --%>
  	   <div class="foot-link ">
                <dl>
                    <dt>友情链接：</dt>
                    <dd>
                    	<c:forEach var="list" items="${friendLinks}" >
                    		<c:choose>
                 					<c:when test="${fn:startsWith(list.linkUrl,'http://') || fn:startsWith(list.linkUrl,'https://')}" >
	                 					<a href="${list.linkUrl }" target="_blank" >${list.title } </a> |
	                 				</c:when>
	                 				<c:otherwise>
	                 					<a href="http://${list.linkUrl }" target="_blank" >${list.title} </a> |
	                 				</c:otherwise>
                 				</c:choose>
                    	</c:forEach>
                    </dd>
                </dl>
       </div>
   <!-- </div> -->
</div> 
