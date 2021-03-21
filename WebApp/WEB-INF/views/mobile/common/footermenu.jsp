<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" /> 
<%String mod=request.getParameter("mod"); %>
<footer>
	<div class="mune ">
    	<a href="${ctx }/weixin/index/toIndex;JSESSIONID=<%=request.getSession().getId()%>"><img src="${ctx }/static/brd-mobile/images/home<% if("sy".equals(mod)){%>1<%}%>.png">
        <p>首页</p>
        </a>
    </div>
	<div class="mune ">
    	<a href="${ctx }/weixin/product/list;JSESSIONID=<%=request.getSession().getId()%>"><img src="${ctx }/static/brd-mobile/images/m<% if("dk".equals(mod)){%>1<%}%>.png">
        <p>贷款</p>
        </a>
    </div>
	<div class="mune ">
    	<a href="${ctx }/weixin/user/apprentice/toApprentice;JSESSIONID=<%=request.getSession().getId()%>"><img src="${ctx }/static/brd-mobile/images/share<% if("st".equals(mod)){%>1<%}%>.png">
        <p>收徒</p>
        </a>
    </div>
	<div class="mune">
    	<a href="${ctx }/weixin/usercenter/main;JSESSIONID=<%=request.getSession().getId()%>"><img src="${ctx }/static/brd-mobile/images/w<% if("my".equals(mod)){%>1<%}%>.png">
        <p>我的</p>
        </a>
    </div>    
</footer>