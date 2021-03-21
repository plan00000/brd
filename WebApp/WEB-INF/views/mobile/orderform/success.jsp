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
<title>贷款成功</title>
<script type="text/javascript">
document.title="申请成功";
$(function(){
	$("#btn_backIndex").click(function(){
		location.href = "${ctx }/weixin/index/toIndex;JSESSIONID=<%=request.getSession().getId()%>";
	})
})
</script>
</head>
<body>
	<div class="success">
        <dl>
            <dt><img src="${ctx }/static/brd-mobile/images/success.png"></dt>
            <dd>
                <p>订单已提交！</p>
                <span>稍后工作人员将和您确认。</span>
            </dd>
         </dl>
         <samp id = "btn_backIndex">回到首页</samp>
         <a href="${ctx }/weixin/loan/${orderformId }/detail;JSESSIONID=<%=request.getSession().getId()%>"><samp>查看订单</samp></a>
    </div>
    <div class="share hidden">
        <div class="share_s">
            <p>分享订单</p>
            <span>好友看不见佣金的哟~</span>
            <span>点击右上角按钮，发送给朋友或群聊</span>
        </div>
    </div>

</body>
</html>