<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
<c:if test = "${not empty apprenticeUsers }">
	<c:forEach items = "${apprenticeUsers.content }" var = "apprenticeUser">
      		 <dl>
              <dt>
              	<p>${apprenticeUser.username }</p>
                <p>${apprenticeUser.mobileno}</p>
                <c:if test = "${apprenticeUser.userType.ordinal() == '0'}">
                	<span>普</span>
                </c:if>
                <c:if test = "${apprenticeUser.userType.ordinal() == '1'}">
                	<span>融</span>
                </c:if>
                <c:if test = "${apprenticeUser.userType.ordinal() == '2'}">
                	<span>商</span>
                </c:if>
                  
              </dt>
              <dd>
                  <p><c:if test="${user.userType.ordinal() != '1'}">他收徒<font>${apprenticeUser.userInfoBoth.sonSum }</font>个，</c:if>为您赚佣金
                  	<font><c:if test = "${empty apprenticeUser.brokerage  }">0</c:if><c:if test = "${not empty apprenticeUser.brokerage }">${apprenticeUser.brokerage }</c:if></font>元</p>
                  <span>注册时间：${util:formatNormalDate(apprenticeUser.createdate) }</span>
              </dd>
           </dl>
      	</c:forEach>
</c:if>