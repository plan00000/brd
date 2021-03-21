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
<title>佣金详情</title>
<script type="text/javascript">
document.title="佣金明细";
$(document).ready(function(){
	$(".grant").click(function(){
	    var relate = $(this).find(".itemRelate").val();
	    var orderId = $(this).find(".itemId").val();
		if(relate!="本人"){
			layer.open({content: '只能查看自己的订单详情', time: 2});
		}else{
			{
				location.href = "${ctx}/weixin/loan/"+orderId+"/detail;JSESSIONID=<%=request.getSession().getId()%>";
			}
			return false;
		}
	})
})
</script>


</head>
<body>
	<div class="content">
		<div class="commission">
			<div class="c_ion">
				<p>
					本月佣金<span>${thisMouthBrokerage } <c:if test="${flage3 eq 'false' }">万</c:if>元</span>
				</p>
				<p>
					上月佣金<span>${lastMouthBrokerage }<c:if test="${flage2 eq 'false' }">万</c:if>元</span>
				</p>
				<p>
					累计佣金<span><font>${totalBrokerage }</font><c:if test="${flage1 eq 'false' }">万</c:if>元</span>

				</p>
			</div>
		</div>
		<c:forEach items="${items }" var="item">
		<div class="grant">
			<p>
				订单编号：${item.orderNo }<span>佣金发放时间：${item.sendBrokerage }</span>
			</p>
			<a href="#">
				<dl>
					<%-- <dt>${item.productName }</dt> --%>
					<dt>${item.productName }<c:if test = "${item.relate == '本人'}"><font><img src="${ctx }/static/brd-mobile/images/arrow-r.png"></font></c:if></dt>
					<dd>
						<span>佣金：${item.haveBrokerage } 元</span> <span>和我的关系：${item.relate }</span>
					</dd>
					<dd>
						<span>贷款金额：
						${util:showTenThousandPrice(item.money)}
						万</span> <span >下单人：${item.name }</span>
						<input class="itemRelate" type="text" value="${item.relate }" hidden="hidden"/>
						<input class="itemId" type="text" value="${item.orderId }"  hidden="hidden" />
					</dd>
				</dl>
			</a>
		</div>
		</c:forEach>
	</div>
</body>
</html>