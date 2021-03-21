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
<title>热门贷款</title>
<script type="text/javascript">
$(function(){
	
});
function changeValue(object){
	var $this = $(object);
	var type = $this.data("type");
	var value = $this.data("value");
	if(type=='mortgageType'){
		$(".mortgagetype").removeClass('l_on');
		$this.addClass('l_on');
		$("#ipt_mortgagetype").val(value);
		requestLoad();
	}
	if(type== 'productType'){
		$(".producttype").removeClass('l_on');
		$this.addClass('l_on');
		$("#ipt_type").val(value);
		requestLoad();
	}
	if(type == 'sortType'){
		$(".sorttype").removeClass('l_on');
		$this.addClass('l_on');
		$("#ipt_sortby").val(value);
		requestLoad();
	}
}
/** 条件查询*/
function requestLoad(){
	$("#search_form").submit();
}

</script>
</head>
<body>
	<div class="war">
		<form id = "search_form" action ="${ctx }/pc/product/list">
			<input type ="hidden" id ="ipt_mortgagetype" value ="${mortgageType }" name = "mortgageType"/>
			<input type ="hidden" id ="ipt_type" value ="${type }" name = "type"/>
			<input type ="hidden" id ="ipt_sortby" value ="${sortBy }" name = "sortBy"/>
		</form>
	       <div class="loan">
	           <p><span class="dai1">贷款类型：</span>
	           		<a href="javascript:void(0);" class = "mortgagetype <c:if test = "${mortgageType eq '' }">l_on</c:if>" data-type = "mortgageType" data-value= ""  onclick = "changeValue(this)">不限</a>
	           		<a href="javascript:void(0);" class = "mortgagetype <c:if test = "${mortgageType eq 'CREDITLOAN' }">l_on</c:if>" data-type = "mortgageType" data-value= "CREDITLOAN"  onclick = "changeValue(this)">信用贷</a>
	           		<a href="javascript:void(0);" class = "mortgagetype <c:if test = "${mortgageType eq 'MORTGAGELOAN' }">l_on</c:if>" data-type = "mortgageType" data-value= "MORTGAGELOAN"  onclick = "changeValue(this)">抵押贷</a>
	           		<a href="javascript:void(0);" class = "mortgagetype <c:if test = "${mortgageType eq 'NULLLOAN' }">l_on</c:if>" data-type = "mortgageType" data-value= "NULLLOAN"  onclick = "changeValue(this)">无抵押贷</a>
	           </p>
	           <%-- <p style="height:auto;"><span class="dai2">贷款分类：</span>
	           		<a href="#" class = "producttype <c:if test = "${type eq '' }">l_on</c:if>" data-type ="productType" data-value ="" onclick = "changeValue(this)">不限</a>
	           		<c:forEach items= "${ productTypes}" var = "productType">
	                 	<a href="#" class = "producttype <c:if test = "${productType.productName eq type }">l_on</c:if>" 
	                 					data-type = "productType" data-value ="${productType.productName }"  onclick = "changeValue(this)">${productType.productName }</a>
                    </c:forEach>
	           </p> --%>
	           <p><span class="dai3">排序：</span>
	           		<a href="#" class = "sorttype <c:if test = "${sortBy eq '' }">l_on</c:if>"  data-type = "sortType" data-value = "" onclick = "changeValue(this)">默认</a>
	           		<a href="#" class = "sorttype <c:if test = "${sortBy eq 'loanMinAmount' }">l_on</c:if>" data-type = "sortType" data-value = "loanMinAmount" onclick = "changeValue(this)">金额优先</a>
	           </p>
	       </div>
       <div class="loan_txt">
           <div class="loan_t">
           		<c:forEach items ="${products.content }" var = "product">
           			<c:if test="${product.interestType.ordinal() eq '0' }">
		           		<dl>
		                   <dt>
		                       <p>${product.info.productName }</p>
		                       <span>
		                           <samp>每月贷款利率</samp>
		                           <font>${ product.info.loanMinRate }%<c:if test = "${not empty product.info.loanMaxRate }">~${product.info.loanMaxRate }%</c:if></font>
		                       </span>
		                       <span>
		                           <samp>贷款期限</samp>
		                           <font>${product.info.loanMinTime } <c:if test ="${product.info.loanMinTime == product.info.loanMaxTime }">- ${product.info.loanMaxTime }</c:if><b>个月</b></font>
		                       </span>
		                       <span>
		                           <samp>贷款金额</samp>
		                           <font>${util:showTenThousandPrice(product.info.loanMinAmount) }-${util:showTenThousandPrice(product.info.loanMaxAmount)}<b>万元</b></font>
		                       </span>
		                   </dt>
		                   <a href="${ctx }/pc/product/productDesc/${product.id}"><dd>贷款</dd></a>
		               </dl>
	               </c:if>
	               <c:if test="${product.interestType.ordinal() eq '1' }">
	               		<dl>
		                   <dt>
		                       <p>${product.info.productName }</p>
		                       <span>
		                           <samp>每日贷款利率</samp>
		                           <font>${product.info.loanMinRate }‰<c:if test = "${ not empty product.info.loanMaxRate }">~${product.info.loanMaxRate }‰</c:if></font>
		                       </span>
		                       <span>
		                           <samp>贷款期限</samp>
		                           <font>${product.info.loanMinTime } <c:if test ="${product.info.loanMinTime == product.info.loanMaxTime }">- ${product.info.loanMaxTime }</c:if><b>天</b></font>
		                       </span>
		                       <span>
		                           <samp>贷款金额</samp>
		                           <font>${util:showTenThousandPrice(product.info.loanMinAmount) }-${util:showTenThousandPrice(product.info.loanMaxAmount)}<b>万元</b></font>
		                       </span>
		                   </dt>
		                   <a href="${ctx }/pc/product/productDesc/${product.id}"><dd>贷款</dd></a>
		               </dl>
	               </c:if>
           		</c:forEach>
    		</div>
    		<div class=" m_right">
                <tags:pagination paginationSize="10" page="${products}" hrefPrefix="${ctx }/pc/product/list" hrefSubfix="${queryStr}"></tags:pagination> 
            </div>
  		</div>
  		<jsp:include page="../footer.jsp"/>
 </div>
 
</body>
</html>