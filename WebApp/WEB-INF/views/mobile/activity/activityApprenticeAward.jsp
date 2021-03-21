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
<title>收徒奖现金</title>
<script type="text/javascript">
document.title="收徒奖现金";
$(document).ready(function(){
	$('#receiveReward').click(function(){
		$.ajax({
			type : 'post',
			url : '${ctx}/weixin/activity/receiveReward',
			success : function(res) {
				if (res.code == 1) {
					/* $("#receiveReward").removeClass('sou');
					$("#receiveReward").addClass('sou');
					$("#receiveReward").text('收徒送礼'); */
					location.reload();
				}else{
					location.reload();
				} 		
				return false;
			}
		})
	});
	$('#toApprentice').click(function(){
		//location.href = "${ctx }/weixin/user/apprentice/toApprentice";
		location.href="${ctx}/weixin/sharefriend/main/${userId}";
	});
	
	
	$('#confirm').click(function(){
		$("#cancelBT").hide();
		location.reload();
	})
	/* 任务完成，收徒送礼 */
	$("#apprenticeGift").click(function(){
		location.href = "${ctx}/weixin/user/apprentice/apprenticeGift;JSESSIONID=<%=request.getSession().getId()%>";
	});
})

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
    	<div class="cash_big">
            <div class="cash">
                <p>收徒奖现金</p>
                <span>截止至${time } 您的任务进度如下：</span>
            </div>
        <div class="cash_man">
        	<p>
        			<c:forEach var="i" begin="1" end="${sonNum }" varStatus="status">
						<samp>
							<img src="${ctx }/static/brd-mobile/images/biaoq1.png">
						</samp>
					</c:forEach>
					<c:forEach var="i" begin="1" end="${remainNum }" varStatus="status">
						<samp>
							<img src="${ctx }/static/brd-mobile/images/biaoq.png">
						</samp>
					</c:forEach>
            </p>
            <c:if test = "${remainNum != '0'}">
            	<span>还差<font>${remainNum }</font>人</span>
            </c:if>
            <c:if test = "${remainNum == '0' }">
            	<span>完成任务</span>
            </c:if>
            
        </div>
        	<c:if test="${completeStatus==1 }">
				<div class="sou" id = "toApprentice">去收徒</div>
			</c:if>
        	<c:if test="${completeStatus==2 }">
				<div class="sou" id = "receiveReward">领取奖励</div>
			</c:if>
			<c:if test="${completeStatus==3 }">
				<div class="sou" id = "apprenticeGift">收徒送礼</div>
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