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
<title>星级奖励</title>
<script type="text/javascript">
document.title="下单得iPhone";
$(document).ready(function(){
	$('#receiveReward').click(function(){
		$.ajax({
			type : 'post',
			url : '${ctx}/weixin/activity/receiveReward?apprenticeType=2',
			success : function(res) {
				if (res.code == 0) {
					$('#cancelBT').css("display","block");
				} 		
				return false;
			}
		})
	});
	$('#confirm').click(function(){
		$("#cancelBT").hide();
		location.reload();
	})
	$('#gotoOrder').click(function(){
		location.href = "${ctx}/weixin/product/list?type=星级贷"; 
	})
})

</script>
</head>
<body>
	<div class="reward">
		<%-- <img src="${activity.picurl }" style="width:100%;height:200px"> --%>
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
		<div class="cash_big">
			<div class="cash">
				<p>下星级订单得iPhone</p>
				<span>截止至${time } 您的任务进度如下：</span>
			</div>
			<div class="star">
				<p>
					<c:forEach var="i" begin="1" end="${orderSum1 }" varStatus="status">
						<samp>
							<img src="${ctx }/static/brd-mobile/images/star1.png">
						</samp>
					</c:forEach>
					<c:forEach var="i" begin="1" end="${remainNum }" varStatus="status">
						<samp>
							<img src="${ctx }/static/brd-mobile/images/star.png">
						</samp>
					</c:forEach>
				</p>
				<span>还差<font>${remainNum }</font>单
				</span>
			</div>
				<c:if test="${completeStatus==1 }">
					<div class="sou" id = "gotoOrder">去下单</div>
				</c:if>
<%--         		<c:if test="${completeStatus==2 }">
					<div class="sou" id = "receiveReward">领取奖励</div>
				</c:if> --%>
				<c:if test="${completeStatus==3 }">
					<div class="sou_su">任务完成</div>
				</c:if>
		</div>
		<div class="cash_text">
			<span>活动规则：</span>
			${util:htmlUnescape(activity.activityRule)}
		</div>
			<div class="finan" id="cancelBT" style="display: none">
             <div class="confirmBt">
              <span>奖励已发放，请注意查收。</span>
                 <samp id ="confirm">确定</samp>
             </div>
        	</div>  
		
	</div>
</body>
</html>