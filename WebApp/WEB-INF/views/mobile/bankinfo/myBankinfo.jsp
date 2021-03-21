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
<title>我的银行卡</title>
<script>
	document.title="我的银行卡"
</script>
</head>
<body>
	
	 <div class="bank">
           <c:forEach var="banckinfo" items="${list}">
           		<dl>
           			<dt>
           			<p>
           				<c:choose>
	           				<c:when test="${banckinfo.bankname eq '中国建设银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/jh.png"> 
	           				</c:when>
	           				<c:when test="${banckinfo.bankname eq '中国工商银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/gsyh.png"> 
	           				</c:when>
	           				<c:when test="${banckinfo.bankname eq '中国农业银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/ry.png"> 
	           				</c:when>
	           				<c:when test="${banckinfo.bankname eq '中国交通银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/jtyh.png"> 
	           				</c:when>
	           				<c:when test="${banckinfo.bankname eq '中国兴业银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/xy.png"> 
	           				</c:when>
	           				<c:when test="${banckinfo.bankname eq '中信银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/zx.png"> 
	           				</c:when>
	           				<c:when test="${banckinfo.bankname eq '中国银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/zg.png"> 
	           				</c:when>
	           				<c:when test="${banckinfo.bankname eq '中国招商银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/zs.png"> 
	           				</c:when>
	           				<c:when test="${banckinfo.bankname eq '中国邮政储蓄银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/yz.png"> 
	           				</c:when>
	           				<c:when test="${banckinfo.bankname eq '中国民生银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/ms.png"> 
	           				</c:when>
	           				<c:when test="${banckinfo.bankname eq '中国光大银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/gd.png"> 
	           				</c:when>
	           				<c:when test="${banckinfo.bankname eq '中国华夏银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/hx.png"> 
	           				</c:when>
	           				<c:when test="${banckinfo.bankname eq '中国广发银行' }" >
	           				 <img src="${ctx }/static/brd-mobile/images/gf.png"> 
	           				</c:when>
           				</c:choose>
           				${banckinfo.bankname }
           				</p>
           				<span>手机尾号:${mobileno}</span>
           			</dt>
           			<dd>	
           				<span></span>
           				<span></span>
           				<span></span>
           				<span>${banckinfo.bankaccount }</span>
           			</dd>	
           		</dl>         		
           </c:forEach>
           
               <div class="add">
              	 <a href="${ctx }/weixin/bankinfo/toAddBankinfo/1;JSESSIONID=<%=request.getSession().getId()%>">
                  <samp></samp>
                  <span>添加银行卡</span>
                  </a>
              </div>
          </div>     
          
          
          
</body>
</html>