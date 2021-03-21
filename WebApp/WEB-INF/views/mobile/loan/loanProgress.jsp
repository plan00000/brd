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
<title>贷款进度</title>
<script>
	document.title="贷款进度";
</script>
</head>
<body>
	  		 <div class="check">
                <dl style="border:none;">
                    <dt>
                        <p>${orderform.productInfo.productName}（${orderform.product.type.billType.getDes()}）</p>
                        <a href="#"><span style="background:none;text-align:right;">${orderform.status.getDes()}</span></a>
                    </dt>
                    <dd>
                        <span>贷款金额：<font>${money }</font>万</span>
                        <span>贷款人：${name}</span>
                        <span>
                        	 <c:choose>
                        		<c:when test="${orderform.getProduct().getType().getBillType().getDes() =='自助贷' }" >
                        			<span></span>
                        		</c:when>
                        		<c:otherwise>
                        			佣金：<font>
                        			<c:choose>
                        			      <c:when test="${orderform.status eq 'UNCONTACTED' || orderform.status eq 'UNTOLKWITH' || orderform.status eq 'UNCHECKED' }" >    
                        			           	${orderform.getSelfBrokerageNum() }
                        			      </c:when> 
                        			      <c:when test="${orderform.status eq 'INVALID' || orderform.status eq 'CHECKFAIL'  }" >
                        			      </c:when>
                        			      <c:otherwise>
                        			      		${orderform.getBrokerageRateNum() }
                        			      </c:otherwise>   			
                        			</c:choose>
                        			</font>元
                        		</c:otherwise>
                        	</c:choose>
                        </span>
                    </dd>
                </dl>
            </div>
            <div class="c_01" ></div>
            <div class="order">
				<c:if test="${orderform.status eq 'INVALID' }" >
					<dl class="o_bj1">
	                   <dt>无效订单</dt>
	                   <dd>
	                   		<span></span>
	                   		<font>${util:formatNormalDate(failtTime)}</font>
	                   </dd>
	                </dl>  
					<dl class="o_bj1">
	                   <dt>订单已审核，待放款</dt>
	                   <dd>
	                   		<span></span>
	                   		<font>${util:formatNormalDate(uncheckTime)}</font>
	                   </dd>
	                </dl>  
				</c:if>
				<c:if test="${orderform.status eq 'LOANED'}" >	
					<dl class="o_bj1">
	                   <dt>订单已放款</dt>
	                   <dd>
	                   		<span></span>
	                   		<font>${util:formatNormalDate(loanTime)}</font>
	                   </dd>
	                </dl>  
					<dl class="o_bj1">
	                   <dt>订单已审核，待放款</dt>
	                   <dd>
	                   		<span></span>
	                   		<font>${util:formatNormalDate(uncheckTime)}</font>
	                   </dd>
	                </dl>  				
				</c:if>
            	 <c:if test="${orderform.status eq 'UNLOAN'}" >
            		<dl class="o_bj1">
	                   <dt>订单已审核，待放款</dt>
	                   <dd>
	                   		<span></span>
	                   		<font>${util:formatNormalDate(uncheckTime)}</font>
	                   </dd>
	                </dl>  
            	</c:if> 
            	<c:if test="${orderform.status eq 'CHECKFAIL'}" >
            		<dl class="o_bj1">
	                   <dt>订单审核失败</dt>
	                   <dd>
	                   		<span>备注:
	                   			<c:choose>
	                   				<c:when test="${empty orderform.invalidReason  }">
	                   					无
	                   				</c:when>
	                   				<c:otherwise>
	                   					${orderform.invalidReason }
	                   				</c:otherwise>
	                   			</c:choose>
	                   		</span>
	                   		<font>${util:formatNormalDate(uncheckTime)}</font>
	                   </dd>
	                </dl>  
            	</c:if>             	
            	<dl class="o_bj1">
                   <dt>订单已提交,待审核</dt>
                   <dd>
                   		<span>备注:
                   		 <c:choose>
                   		 	<c:when test="${empty orderform.remark  }">
                   		 		无
                   		 	</c:when>
                   		 	<c:otherwise>
                   		 		${orderform.remark}
                   		 	</c:otherwise>
                   		 </c:choose>
                   		</span>                   		
                   		<font>${util:formatNormalDate(orderform.createTime)}</font>
                   </dd>
                </dl>  
               	</div>
</body>
</html>