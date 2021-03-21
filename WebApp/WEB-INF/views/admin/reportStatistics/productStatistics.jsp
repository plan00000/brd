<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品统计</title>
<script type="text/javascript">
$(function() {
	activeNav2("9","9_4");

})
	function toEdit(object) {
		var $this = $(object);
		var id = $this.data("id");
		location.href = "${ctx }/admin/product/toEditProduct/"+id;
	}
</script>
</head>
<body>
<div class="row border-bottom">
	<div class="basic">
        <p>报表统计</p>
        <span><a href="<c:url value='/admin/main'/>" style="margin-left:0;">首页</a>><a href="#" >报表统计</a>><a><strong>产品统计</strong></a></span>
    </div>
    </div>
      <div class="details animated fadeInRight">
        	<div class="products">
                <div class="products_title">
               		<p>当前产品总数：${productCount }个</p>
                    <p>当前产品类别：${productTypeCount }种</p>
                </div>
                <div class="distributed">
                <table width="100%" cellpadding="0" cellspacing="0">
                    <tbody>
                    
                        <tr><td colspan="6"  class="d_but">产品提单类型分布</td></tr>
                        <tr>
                            <td><strong>提单类型</strong></td>
                            <td><strong>产品数量</strong></td>
                            <td><strong>所占比例</strong></td>
                            <td><strong>被申请次数</strong></td>
                            <td><strong>已放款订单数</strong></td>
                            <td><strong>放款金额</strong></td>
                        </tr>
                        <c:forEach items="${repList }" var="product">
                       	 <tr>
                            <td>${product.billType }</td>
                            <td>${product.productCount }</td>
                            <td>${product.productPercent }</td>
                            <td>${product.applyTimes }</td>
                            <td>${product.loanCount }</td>
                             <td>${util:showTenThousandPrice(product.loanMoney)}万</td> 
                       	 </tr>  
                       	 </c:forEach>                  
                    </tbody>
                  </table>
                </div>
                <div class="distributed">
                <table width="100%" cellpadding="0" cellspacing="0">
                    <tbody>
                    
                        <tr><td colspan="9"  class="d_but01">最受欢迎产品TOP10</td></tr>
                        <tr>
                        	<td><strong>序号</strong></td>
                            <td><strong>产品名称</strong></td>
                            <td><strong>提单类型</strong></td>
                            <td><strong>产品分类</strong></td>
                            <td><strong>贷款金额</strong></td>
                            <td><strong>贷款期限</strong></td>
                            <td><strong>贷款利率</strong></td>
                            <td><strong>累计被申请次数</strong></td>
                            <td><strong>已放款总额</strong></td>
                        </tr>
                        <c:forEach items="${page.getContent()}" var="pro" varStatus="status">
                       	 <tr>
                        	<td>${status.index + 1}</td>
                            <td><a href="javascript:void(0)" onclick="toEdit(this)" data-id = "${pro.id }">${pro.paoductName }</a></td>
                            <td>${pro.billType }</td>
                            <td>${pro.typeName }</td>
                            <td>
                            ${util:showTenThousandPrice(pro.loanMinAmount)}-
                            ${util:showTenThousandPrice(pro.loanMaxAmount)}万</td>
                            
                            <c:if test="${pro.interestType==2 }">
                            	<td>${pro.loanMinTime }-${pro.loanMaxTime }月</td>
                           		<td>${util:showRateWithoutUnit(pro.loanRate) }%</td>
                            </c:if>
                            <c:if test="${pro.interestType==1 }">
                            	<td>${pro.loanMinTime }-${pro.loanMaxTime }日</td>
                           		<td>${util:showThousandRateWithoutUnit(pro.loanRate) }‰</td>
                            </c:if>
                            
                            <td>${pro.applyTimes }</td>
				 			<td>${util:showTenThousandPrice(pro.loanMoney)}万</td> 
                        	</tr>
                        </c:forEach>
                    </tbody>
                  </table>
               <%--    <tags:pagination paginationSize="6" page="${page}" hrefPrefix="" hrefSubfix="loginAccount=${loginAccount }&name=${name }&userType=${userType }"></tags:pagination> --%>
                </div>
            </div>
          </div>
          <div style="height:40px;"></div>
</body>
</html>
             