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
<title>产品详情</title>
<script type="text/javascript">
document.title="${product.info.productName }";

$(function(){
	$("#show_productDesc").click(function(){
		if($(".productDesc").hasClass('hidden')){
			$("#show_productDesc").addClass('jiantou');
			$(".productDesc").removeClass('hidden');
		}else{
			$("#show_productDesc").removeClass('jiantou');
			$(".productDesc").addClass('hidden');
		}
	});
	$("#show_require").click(function(){
		if($(".require").hasClass('hidden')){
			$("#show_require").addClass('jiantou');
			$(".require").removeClass('hidden');
		}else{
			$("#show_require").removeClass('jiantou');
			$(".require").addClass('hidden');
		}
	});
	$("#show_ready").click(function(){
		if($(".ready").hasClass('hidden')){
			$("#show_ready").addClass('jiantou');
			$(".ready").removeClass('hidden');
		}else{
			$("#show_ready").removeClass('jiantou');
			$(".ready").addClass('hidden');
		}
	});
})
</script>
</head>
<body>
<div class="product">
    	<p>${product.info.productName }（${product.type.billType.getDes() }）</p>
        <p>贷款利息</p>
        <c:if test = "${product.interestType.ordinal() != '1'}">
        	<p><font>${product.info.loanMinRate } <c:if test = "${not empty product.info.loanMaxRate }">~ ${product.info.loanMaxRate }</c:if></font>%/月</p>
        </c:if>
        <c:if test = "${product.interestType.ordinal() eq '1' }">
        	<p><font>${product.info.loanMinRate } <c:if test = "${not empty product.info.loanMaxRate }">~ ${product.info.loanMaxRate }</c:if></font>‰/日</p>
        </c:if>
        <%-- <c:if test = "${product.interestType.ordinal() eq '2' || product.interestType.ordinal() eq '3'}">
        	<p><font>${product.info.loanRate } </font>%/月</p>
        </c:if> --%>
    </div>
    <div class="product_mon">
        <dl>
             <dt>贷款金额(万)</dt>
             <dd>${util:showTenThousandPrice(product.info.loanMinAmount) } ~ ${util:showTenThousandPrice(product.info.loanMaxAmount) }</dd>
        </dl>
        <dl>
        <c:if test = "${product.interestType.ordinal() != '1'}">
             <dt>贷款期限(月)</dt>
             <dd>${product.info.loanMinTime } <c:if test ="${product.info.loanMinTime != product.info.loanMaxTime }">~ ${product.info.loanMaxTime }</c:if></dd>
        </c:if>
        <c:if test = "${product.interestType.ordinal() == '1'}">
             <dt>贷款期限(日)</dt>
             <dd>${product.info.loanMinTime } <c:if test ="${product.info.loanMinTime != product.info.loanMaxTime }">~ ${product.info.loanMaxTime }</c:if></dd>
        </c:if>
        </dl>
        <dl>
             <dt>还款方式</dt>
             <dd>${product.info.repayment }</dd>
        </dl>
    </div>
    <c:if test = "${product.type.billType.ordinal()=='1' }">
	    <div class="ation">
	   		 <p>佣金比例</p>
	         <!-- <span>师父补贴=底价收息额*1%按月结算<br/>加价范围0.1%/月-0.2%/月，佣金按月结算<br/>加价范围0.1%/月-0.2%/月，佣金按月结算</span> -->
	         <span>${product.info.userRateDesc }</span>
	         <span>${product.info.fatherRateDesc }</span>
	    </div>
    </c:if>
     <c:if test = "${product.type.billType.ordinal()=='2' }">
     	<%-- <div class="ation">
	   		 <p>佣金比例</p>
	         <!-- <span>师父补贴=底价收息额*1%按月结算<br/>加价范围0.1%/月-0.2%/月，佣金按月结算<br/>加价范围0.1%/月-0.2%/月，佣金按月结算</span> -->
	         <span>${product.info.userRateDesc }</span>
	    </div> --%>
	    <div class="ation">
    	<p>佣金比例</p>
   		 <dl class = "line">
             <dt>会员收佣说明：</dt>
             <dd>${product.info.userRateDesc }</dd>
         </dl>
         <dl>
             <dt>会员收佣比例：</dt>
             <dd>
            	<div class="progress_bar">
					<div class="pro-bar">
						<small class="progress_bar_title">
							<span class="progress_number">${util:showThousandRateWithoutUnit(product.info.userRate) }</span>
						</small>
						<span class="progress-bar-inner" style="background-color:#f86355; width:${util:showThousandRateWithoutUnit(product.info.userRate) }%; margin-left:0; float:none;" data-value="100" data-percentage-value="89"></span>
					</div>
				</div>
             </dd>
         </dl>
         <dl>
             <dt>师傅补贴比例：</dt>
             <dd>
             <div class="progress_bar">
					<div class="pro-bar">
						<small class="progress_bar_title">
							<span class="progress_number p_bule">${util:showRateWithoutUnit(product.info.fatherRate) }</span>
						</small>
						<span class="progress-bar-inner" style="background-color:#4baaf7; width:${util:showRateWithoutUnit(product.info.fatherRate) }%; margin-left:0; margin-left:0;" data-value="50" data-percentage-value="89"></span>
					</div>
				</div>
             </dd>
         </dl>
    </div>
     </c:if>
    <div class="inform">
        <ul>
            <li>
            	<a id="show_productDesc">产品信息</a>
                <div class="i_ion productDesc hidden" >
                <!-- 1.抵押率低于50%利息超低，不看负债，负债不影响额度<br/>2.抵押率低于50%利息超低，不看负债，负债不影响额度<br/>3.抵押率低于50%利息超低，不看负债，负债不影响额度<br/>4.抵押率低于50%利息超低，不看负债，负债不影响额度<br/>5.抵押率低于50%利息超低，不看负债，负债不影响额度<br/>6.抵押率低于50%利息超低，不看负债，负债不影响额度<br/>7.抵押率低于50%利息超低，不看负债，负债不影响额度<br/>8.抵押率低于50%利息超低，不看负债，负债不影响额度 -->
                	${util:htmlUnescape(product.info.productDesc)}
                </div>
            </li>
            <li>
            	<a id = "show_require" >申请条件</a>
                <div class="i_ion require hidden" >
                <!-- 1.抵押率低于50%利息超低，不看负债，负债不影响额度<br/>2.抵押率低于50%利息超低，不看负债，负债不影响额度<br/>3.抵押率低于50%利息超低，不看负债，负债不影响额度<br/>4.抵押率低于50%利息超低，不看负债，负债不影响额度<br/>5.抵押率低于50%利息超低，不看负债，负债不影响额度<br/>6.抵押率低于50%利息超低，不看负债，负债不影响额度<br/>7.抵押率低于50%利息超低，不看负债，负债不影响额度<br/>8.抵押率低于50%利息超低，不看负债，负债不影响额度 -->
                	${util:htmlUnescape(product.info.requirment)}
                </div>
            </li>
            <li>
            	<a id="show_ready">准备材料</a>
                <div class="i_ion ready hidden" >
                <!-- 1.抵押率低于50%利息超低，不看负债，负债不影响额度<br/>2.抵押率低于50%利息超低，不看负债，负债不影响额度<br/>3.抵押率低于50%利息超低，不看负债，负债不影响额度<br/>4.抵押率低于50%利息超低，不看负债，负债不影响额度<br/>5.抵押率低于50%利息超低，不看负债，负债不影响额度<br/>6.抵押率低于50%利息超低，不看负债，负债不影响额度<br/>7.抵押率低于50%利息超低，不看负债，负债不影响额度<br/>8.抵押率低于50%利息超低，不看负债，负债不影响额度 -->
                	${product.info.materials }
                </div>
            </li>
          </ul>
    </div>
    <a href="${ctx }/weixin/orderform/toApplyPage/${product.id }"><div class="shenq">立即申请</div></a>

</body>
</html>