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
<title>收徒</title>
<script type="text/javascript">
document.title="收徒";
$(function() {
	$('.shoutu').click(function(){
		location.href = "${ctx }/weixin/usercenter/main;JSESSIONID=<%=request.getSession().getId()%>";
	})
})
</script>
</head>
<body>
	<div class="shoutu">
            <dl>
                <dt>
                	<%-- <c:if test = "${empty user.headimgurl }">
                		<img src="${ctx}/static/brd-mobile/images/u1157.png">
                	</c:if> --%>
                	<c:choose>
               			<c:when test="${empty user.headimgurl }">
               				<img src="${ctx}/static/brd-mobile/images/u1157.png">
               			</c:when>
               			<c:otherwise>
							<img src="${ctx}/files/displayProThumb?filePath=${user.headimgurl}&thumbWidth=120&thumbHeight=80" />
               			</c:otherwise>
                   </c:choose>
                </dt>
                <dd>
                     <p>${user.username }</p>
                     <p>身份：${user.userType.getStr() }</p>
                </dd>
             </dl>
        </div>
        <!--  商家-->
        <c:if test="${user.userType =='SELLER' }">
	        <div class="shoutu_sun">
	        	<a href="${ctx }/weixin/user/apprentice/apprenticeList">
	       		 <div class="shoutu_jzt">
	            <dl>
	                <dt>徒弟</dt>
	                <dd>
	                    <span></span>
	                    <samp><font>${user.userInfoBoth.sonSum }</font>人</samp>
	                </dd>
	            </dl>
	            <dl class="s_bott">
	                <dt>徒孙</dt>
	                <dd>
	                    <span></span>
	                    <samp><font>${user.userInfoBoth.grandsonSum}</font>人</samp>
	                </dd>
	            </dl>
	        </div>
	        <p></p>
	        	</a>
	        </div>
        </c:if>
        <!-- 业务员 -->
        <c:if test="${user.userType =='SALESMAN' }">
        	 <div class="clerk_sun">
        		<a href="${ctx }/weixin/user/apprentice/apprenticeList">
		       	 <div class="clerk">
		            <dl>
		                <dt>
		                <span>${manager }</span>
		                <samp></samp>
		                </dt>
		                <dd>融资经理&nbsp;</dd>
		            </dl>
		            <dl class="c_right">
		                <dt>
		                <span>${seller }</span>
		                <samp></samp>
		                </dt>
		                <dd>&nbsp;&nbsp;&nbsp;商家</dd>
		            </dl>
		            <dl class="c_left">
		                <dt>
		                <span>${managersons }</span>
		                <samp></samp>
		                </dt>
		                <dd>徒孙&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</dd>
		            </dl>
		            <dl class="c_l">
		                <dt>
		                <span>${sellersons}</span>
		                <samp></samp>
		                </dt>
		                <dd>&nbsp;&nbsp;&nbsp;徒孙</dd>
		            </dl>
		        </div>
		       <p></p>
		        	</a>	
		   </div>
        </c:if>
        <!-- 融资经理 -->
        <c:if test="${user.userType =='MANAGER' }">
        	<div class="ager">
	        	<a href="${ctx }/weixin/user/apprentice/apprenticeList">
	       			<div class="ager_l">
			            <dl>
			                <dt>徒弟</dt>
			                <dd>
			                    <span></span>
			                    <samp><font>${user.userInfoBoth.sonSum }</font>人</samp>
			                </dd>
			            </dl>
	        		</div>
        			<p></p>
        		</a>
        	</div>
        </c:if>
        <a href="${ctx}/weixin/sharefriend/main/${user.getId()};JSESSIONID=<%=request.getSession().getId()%>"><div class="mine">我的收徒二维码</div></a>
        <div class="recom">
        	<a href="${ctx}/weixin/product/list?isIndex=1">
            <dl>
                <dt>
                    <p>推单赚佣</p>
                    <span>帮人贷 赚佣金</span>
                </dt>
                <dd><img src="${ctx}/static/brd-mobile/images/u404.png"></dd>
             </dl>
             </a>
             <a href="${ctx }/weixin/user/apprentice/apprenticeGift;JSESSIONID=<%=request.getSession().getId()%>">
            <dl>
                <dt>
                    <p>收徒送礼</p>
                    <span>红包送好友</span>
                </dt>
                <dd><img src="${ctx}/static/brd-mobile/images/u1179.png"></dd>
             </dl>
             </a>
        </div>
        <a href="${ctx}/weixin/sharefriend/main/${user.getId()};JSESSIONID=<%=request.getSession().getId()%>"><div class="shouyj">收徒赚佣金</div></a>
        <jsp:include page="../common/footermenu.jsp?mod=st"/>  
</body>
</html>