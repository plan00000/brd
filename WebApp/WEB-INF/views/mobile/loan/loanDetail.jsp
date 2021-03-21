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
<title>贷款详情</title>
<script>
	document.title="贷款详情"
	$(function(){
		$("#shareOrder").click(function(){
			$("#shareButtom").attr("class","share");			
		});
		
		$("#shareButtom").click(function(){
			$("#shareButtom").attr("class","hidden");	
		});
		
		$("body").css("background","#f2f2f2");
		
	});
</script>
</head>
<body>
    	  <div class="detail">
            <dl>
                <dt><p></p>
                <c:choose>
                	<c:when test="${orderform.status eq 'UNCHECKED' || orderform.status eq 'UNLOAN' || orderform.status eq 'LOANED'}">
                		<span ></span>
                	</c:when>
                	<c:otherwise>
                		 <span class="d_huijt"></span>
                	</c:otherwise>
                </c:choose>
               </dt>
                <dd>提交订单</dd>
            </dl>
            <c:choose>
            <c:when test="${orderform.status eq 'UNCHECKED' || orderform.status eq 'UNLOAN' || orderform.status eq 'LOANED' }">
            	<dl>
	                <dt><p></p>
	                <c:choose>
		                <c:when test="${orderform.status eq 'LOANED' || orderform.status eq 'UNLOAN'  }">
		                	<span></span>
		                </c:when>
		                <c:otherwise>
		                	<span class="d_huijt" ></span>
		                </c:otherwise>
	                </c:choose>
	                </dt>
	                <dd>待审核</dd>
	            </dl>
            </c:when>
            <c:otherwise>
	            <dl>
	                <dt><p class="d_hui"></p><span class="d_huijt" ></span></dt>
	                <dd>待审核</dd>
	            </dl>
	        </c:otherwise>    
			</c:choose>
			<c:choose>
				<c:when test="${orderform.status eq 'UNLOAN' || orderform.status eq 'LOANED'}">
					<dl>
		            	<dt><p></p>
		            	<c:choose>
		            		<c:when test="${orderform.status eq 'LOANED'}">
		            			<span ></span>
		            		</c:when>
		            		<c:otherwise>
		            			<span class="d_huijt" ></span>
		            		</c:otherwise>
		            	</c:choose>
		            	</dt>
		            	<dd>待放款</dd>
	            	</dl>
				</c:when>
				<c:otherwise>
					<dl>
	            	<dt><p class="d_hui"></p><span class="d_huijt"></span></dt>
	            	<dd>待放款</dd>
	            	</dl>
				</c:otherwise>
			</c:choose>
			<c:choose>
				<c:when test="${orderform.status eq 'LOANED'}">
					<dl class="d_tall">
		           		<dt style="background:url(${ctx}/static/brd-mobile/images/money.png) no-repeat center; background-size: 32px 32px"  ></dt>
		           		<dd>已放款</dd>
	           		</dl>
				</c:when>
				<c:otherwise>
					<dl class="d_tall">
		           		<dt ></dt>
		           		<c:choose>
		           			<c:when test="${orderform.status eq 'CHECKFAIL' }">
		           				<dd>审核失败</dd>
		           			</c:when>
		           			<c:when test="${orderform.status eq 'INVALID' }">
		           				<dd>无效订单</dd>
		           			</c:when>
		           			<c:otherwise>
		           				<dd>已放款</dd>
		           			</c:otherwise>
		           		</c:choose>
		           		
	           		</dl>
				</c:otherwise>
			</c:choose>			
          </div>
             <div class="d_bottom">
                 <div class="d_bot">
                     <p><samp>
                     ${orderform.productInfo.productName}（${orderform.getProduct().getType().getBillType().getDes()}）</samp>
                     <font>${orderform.status.getDes()}</font></p>
                     <span>订单编号：${orderform.orderNo }</span>
                     <span>订单类型：${orderform.getProduct().getType().getProductName()}</span>
                     <span>贷款人：${name }</span>
                     <span>手机号码：${phone}</span>
                     <span>申请金额：${util:showMoneyWithoutUnit(orderform.money) }元</span>
                     <c:if test="${orderform.status eq 'UNLOAN' || orderform.status eq 'LOANED' }" >
                     	<span>成功放款金额：${util:showMoneyWithoutUnit(orderform.actualMoney)}元</span>  
                     </c:if>
                     	<span>贷款周期：
                     		<c:choose>
                     			<c:when test="${orderform.product.interestType eq 'INTERESTMODELDAY' }" >
                     				${orderform.loanTime}天
                     			</c:when>
                     			<c:otherwise>
                     				${orderform.loanTime}个月
                     			</c:otherwise>
                     		</c:choose>
                     	</span>
                     	 <c:choose>
                        		<c:when test="${orderform.getProduct().getType().getBillType().getDes() =='自助贷' }" >
                        			
                        		</c:when>
                        		<c:otherwise>
                        			<span>预计佣金：${orderform.getSelfBrokerageNum()}元</span>
                        		</c:otherwise>
                         </c:choose>
                     	<c:choose>
                     		<c:when test="${orderform.status eq 'LOANED' && orderform.getProduct().getType().getBillType().getDes() ne '自助贷'  }" >
                     			<c:choose>
                     				<c:when test="${apply.sendStatus eq 'SINGAL'  }" >
                     					<span>实际佣金：${util:showMoneyWithoutUnit(realBrokerage) }元</span>
                     				</c:when>
                     				<c:otherwise>
	                     				<span>实际佣金：预计分${apply.sendTimes }次发放；</span>
	                     			 <c:forEach var="record" items="${records}">
	                     				<span>${util:formatShortDate(record.createTime)}   发放第${record.number }次      ${util:showMoneyWithoutUnit(record.selfPaymentBrokerage)}元
	                     					<c:choose>
	                     						<c:when test="${not empty record.remark  }">
	                     							(${record.remark})
	                     						</c:when>
	                     						<c:otherwise>
	                     							
	                     						</c:otherwise>
	                     					</c:choose>
	                     				 </span>
	                     			</c:forEach> 
                     		</c:otherwise>
                     			</c:choose>
                     		</c:when>
                     		<c:otherwise>
                     			
                     			
                     		</c:otherwise>
                     	</c:choose>
                     	<span>申请时间：${util:formatNormalDate(orderform.createTime)}</span>
                 </div>
                 <div class="j_du">
                     <a href="#" id="shareOrder" class="use">分享订单</a>
                     <a href="${ctx}/weixin/loan/${orderform.id }/progress;JSESSIONID=<%=request.getSession().getId()%>">查看进度</a>
                 </div>
              </div>
              <div id="shareButtom" class="share hidden">
                  <div class="share_s">
                      <p>分享订单</p>
                      <span></span>
                      <span>点击右上角按钮，发送给朋友或群聊</span>
                  </div>
              </div>      
	
</body>
</html>