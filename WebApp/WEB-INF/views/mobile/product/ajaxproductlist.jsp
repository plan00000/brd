<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
<c:if test = "${not empty products}">
	<c:forEach items = "${products }" var = "product">
   		<c:if test="${product.type.billType.ordinal() != '0'}">
         		 <a href="${ctx }/weixin/product/productDetail/${product.id }">
	                 <dl>
	                     <dt>
		                     <c:if test = "${product.type.id =='1'||product.type.id =='2'|| product.type.id == '3' }">
		                     	<font><img src="${ctx }/static/brd-mobile/images/start.png"></font>
		                     </c:if>
	                     	${product.info.productName }
	                     </dt>
	                     <dd>
	                     	<c:if test = "${product.interestType.ordinal() != '1' }">
		                         <p>
		                             <font>${product.info.loanMinRate}%<c:if test = "${ not empty product.info.loanMaxRate }">~${product.info.loanMaxRate }%</c:if></font>
		                             <span>每月贷款利率</span>
		                         </p>
	                         </c:if>
	                         <c:if test = "${product.interestType.ordinal() == '1' }">
		                         <p>
		                             <font>${product.info.loanMinRate}‰<c:if test = "${ not empty product.info.loanMaxRate }">~${product.info.loanMaxRate }‰</c:if></font>
		                             <span>每日贷款利率</span>
		                         </p>
	                         </c:if>
	                         
	                          <%-- <p>
	                          	<c:if test = "${product.type.billType.ordinal() == '1' && product.interestType.ordinal() != '1'}">
	                          		 <samp><font>${util:showRateWithoutUnit(product.info.spreadMin) }-${util:showRateWithoutUnit(product.info.spreadMax) }%</font></samp>
	                          	</c:if>
	                          	<c:if test = "${product.type.billType.ordinal() == '1' && product.interestType.ordinal() == '1'}">
	                          		 <samp><font>${util:showThousandRateWithoutUnit(product.info.spreadMin) }-${util:showThousandRateWithoutUnit(product.info.spreadMax) }‰</font></samp>
	                          	</c:if>
	                          	<c:if test = "${product.type.billType.ordinal() == '2' && product.interestType.ordinal() != '3'}">
	                          		 <samp><font>${util:showRateWithoutUnit(product.info.percentageRate) }%</font></samp>
	                          	</c:if>
	                          	<c:if test = "${product.type.billType.ordinal() == '2' && product.interestType.ordinal() == '3'}">
	                          		 <samp><font>${util:showRateWithoutUnit(product.info.algoParamB) }%</font></samp>
	                          	</c:if>
	                            <c:if test = "${product.type.billType.ordinal() =='1'&& product.interestType.ordinal() != '2' }">
	                            	<samp>加价息差</samp>
	                            </c:if>
	                            <c:if test = "${product.type.billType.ordinal() =='1'&& product.interestType.ordinal() == '2' }">
	                            	<samp>约定费用价差</samp>
	                            </c:if>
	                            <c:if test = "${product.type.billType.ordinal() =='2'&& product.interestType.ordinal() != '3' }">
	                            	<samp>提成比例</samp>
	                            </c:if>
	                            <c:if test = "${product.type.billType.ordinal() =='2'&& product.interestType.ordinal() == '3' }">
	                            	<samp>佣金比例</samp>
	                            </c:if>
	                             
                         	 </p> --%>
                         	  <p>
                         	 	<samp><font>${product.info.fontBrokerageNum }</font></samp>
                         	 	<samp>${product.info.fontBrokerageDesc }</samp>
                         	 </p>
                         	 <c:if test = "${product.interestType.ordinal() != '1' }">
	                         	 <p>
	                             	<samp>${util:showTenThousandPrice(product.info.loanMinAmount) }-${util:showTenThousandPrice(product.info.loanMaxAmount)}万元</samp>
	                             	<samp>${product.info.loanMinTime }<c:if test ="${product.info.loanMinTime != product.info.loanMaxTime }">-${product.info.loanMaxTime }</c:if>个月</samp>
	                         	</p>
	                         </c:if>
	                         <c:if test = "${product.interestType.ordinal() == '1' }">
	                         	 <p>
	                             	<samp>${util:showTenThousandPrice(product.info.loanMinAmount) }-${util:showTenThousandPrice(product.info.loanMaxAmount)}万元</samp>
	                             	<samp>${product.info.loanMinTime }<c:if test ="${product.info.loanMinTime != product.info.loanMaxTime }">-${product.info.loanMaxTime }</c:if>天</samp>
	                         	</p>
	                         </c:if>
	                     </dd>
	                 </dl>
                   </a>
                  </c:if>
                  
                  <!-- 自助贷-按月/收益金 -->
                  <c:if test = "${product.interestType.ordinal() != '1' && product.type.billType.ordinal() eq '0'}">
            		<a href="${ctx }/weixin/product/productDetail/${product.id }">
	                 <dl class="m_ziz">
	                     <dt>
	                     	<c:if test = "${product.type.id =='1'||product.type.id =='2'|| product.type.id == '3' }">
		                     	<font><img src="${ctx }/static/brd-mobile/images/start.png"></font>
		                     </c:if>
	                     	${product.info.productName }
	                     </dt>
	                     <dd>
	                         <p>
		                         <font>${product.info.loanMinRate}%<c:if test = "${ not empty product.info.loanMaxRate }">~${product.info.loanMaxRate }%</c:if></font>
		                         <span>每月贷款利率</span>
		                     </p>
	                         <p>
                             	<samp>${util:showTenThousandPrice(product.info.loanMinAmount) }-${util:showTenThousandPrice(product.info.loanMaxAmount)}万元</samp>
                             	<samp>${product.info.loanMinTime }<c:if test ="${product.info.loanMinTime != product.info.loanMaxTime }">-${product.info.loanMaxTime }</c:if>个月</samp>
                         	 </p>
	                     </dd>
	                   </dl>
                   </a>
                  </c:if>
                  <!-- 自助贷-按日 -->
                  <c:if test = "${product.interestType.ordinal() eq '1' && product.type.billType.ordinal() eq '0'}">
                  	<a href="${ctx }/weixin/product/productDetail/${product.id }">
	                 <dl class="m_ziz">
	                     <dt>
	                     	<c:if test = "${product.type.id =='1'||product.type.id =='2'|| product.type.id == '3' }">
		                     	<font><img src="${ctx }/static/brd-mobile/images/start.png"></font>
		                     </c:if>
	                     	${product.info.productName }
	                     </dt>
	                     <dd>
	                         <p>
		                         <font>${product.info.loanMinRate}‰<c:if test = "${ not empty product.info.loanMaxRate }">~${product.info.loanMaxRate }‰</c:if></font>
		                         <span>每日贷款利率</span>
		                     </p>
	                          <p>
                             	<samp>${util:showTenThousandPrice(product.info.loanMinAmount) }-${util:showTenThousandPrice(product.info.loanMaxAmount)}万元</samp>
                             	<samp>${product.info.loanMinTime }<c:if test ="${product.info.loanMinTime != product.info.loanMaxTime }">-${product.info.loanMaxTime }</c:if>天</samp>
                         	 </p>
	                     </dd>
	                   </dl>
                   </a>
                  </c:if>
                  
       	</c:forEach>
</c:if>