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
<title>首页</title>
<link href="${ctx}/static/brd-mobile/css/swiper.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/static/brd-mobile/js/swiper.min.js"></script>
<script type="text/javascript">
document.title="帮人贷";
$(document).ready(function(){
	$("#apprenticeAward").click(function(){
		judgment() ;
	})
	$("#iproduct").click(function(){
		judgment1() ;
	})
	$("#starOrder").click(function(){
		location.href="${ctx}/weixin/activity/toStarOrder";	
	})
	$("#toActivityInformation").click(function(){
		location.href="${ctx}/weixin/information/toActivityInformation";	
	})
	setTimeout(function(){
		$("MARQUEE").attr("behavior","scroll");	
	},1000);
	
	$("#cancelKefu").click(function(){
		$(".kefu").attr("class","kefu hidden");
	});
	$(".kefurx").click(function(){
		$(".kefu").attr("class","kefu");
	});
	
	
})
	function judgment() {
		$.ajax({
			type : 'post',
			url : '${ctx}/weixin/judgment/judgment',
			success : function(res) {
				if (res.code == 0) {
					layer.open({
						content : res.mes,
						time : 3
					});
					return false;
				} else if (res.code == 1) {
					location.href = "${ctx}/weixin/generalUserCenter/becomeManager";
					return false;
				} else if(res.code == -1){
					//location.href = "${ctx}/weixin/user/toLogin"; 
					location.href = "${ctx}/wechat/weixinLogin"; 
				}else{
					location.href = "${ctx}/weixin/activity/toApprenticeAward";
					return false;
				}
			}
		})
	}
	function judgment1() {
		$.ajax({
			type : 'post',
			url : '${ctx}/weixin/judgment/judgment1',
			success : function(res) {
				if (res.code == 0) {
					layer.open({
						content : res.mes,
						time : 3
					});
					return false;
				} else if (res.code == 1) {
						location.href = "${ctx}/weixin/generalUserCenter/becomeManager";
				} else if (res.code == 2){
					location.href = "${ctx}/weixin/product/list?isIndex=1";
					return false;
				} else {
					//location.href = "${ctx}/weixin/user/toLogin"; 
					location.href = "${ctx}/wechat/weixinLogin"; 
					return false;
				}
			}
		})
	}


</script>
</head>
<body>
	<div class="swiper-container" id="bannerSwiper" style="cursor: -webkit-grab;">
        <c:choose>
		<c:when test="${empty banners}">
      	<div class="swiper-wrapper">
            <div class="swiper-slide"><a href="#"><img src="${ctx }/static/brd-mobile/images/ban6.jpg" style="width:100%;height:auto"/> </a></div>
            <div class="swiper-slide"><a href="#"><img src="${ctx }/static/brd-mobile/images/ban5.jpg" style="width:100%;height:auto"/> </a></div>
            <div class="swiper-slide"><a href="#"><img src="${ctx }/static/brd-mobile/images/ban4.jpg" style="width:100%;height:auto"/> </a></div>
        </div>
       	</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${banners.size() == 1}">
					<c:forEach items = "${banners}" var="banner" varStatus="status">
						<div>
							<c:choose>
								<c:when test="${empty banner.address}">
				       	  			<a href="#">
				       	  		</c:when>
				       	  		<c:otherwise>
				       	  			<a href="${banner.address}">
				       	  		</c:otherwise>
							</c:choose>
							<img src="${banner.picurl}" style="width:100%;height:auto"/> </a></div>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
				<div class="swiper-wrapper">
            <c:forEach items = "${banners}" var="banner" varStatus="status">
       		 <div class="swiper-slide" id="swiper-slide${status.index+1}">
       	  		<div class="inner">
       	  		<c:choose>
				<c:when test="${empty banner.address}">
       	  			<a href="#">
       	  		</c:when>
       	  		<c:otherwise>
       	  			<a href="${banner.address}">
       	  		</c:otherwise>
				</c:choose>
       	  		<img src="${banner.picurl}" style="width:100%;height:auto"/> </a></div>
        	</div>
        	</c:forEach>
		   </div>
			</c:otherwise>
			</c:choose>
			
         
      		</c:otherwise>
		</c:choose>
</div>

	<div class="h_gund">
	   <c:choose>
		<c:when test="${empty sysInfo.scrollBall}">
		<MARQUEE scrollAmount=2>默认广播</MARQUEE>
		   	</c:when>
		<c:otherwise>
		<MARQUEE behavior="alternate" loop="-1" scrollAmount=2>${scrollBall }</MARQUEE>
		  </c:otherwise>
		</c:choose>
	</div>
	<div class="content">
		<div class="home_txt">
			<dl id="apprenticeAward">
				<dt>
					<p>
						<a href="javascript:void(0)" >收徒奖励</a>
					</p>
					<span><a href="javascript:void(0)" >轻松赚红包</a></span>
				</dt>
				<dd>
					<a href="javascript:void(0)"><img
						src="${ctx }/static/brd-mobile/images/u395.png"></a>
				</dd>
			</dl>
			<dl id="starOrder">
				<dt>
					<p>
						<a >星级订单</a>
					</p>
					<span><a >下单得iPhone</a></span>
				</dt>
				<dd>
					<a ><img
						src="${ctx }/static/brd-mobile/images/u397.png"></a>
				</dd>
			</dl>
			<dl id="iproduct">
				<dt>
					<p>
						<a href="javascript:void(0)" >推单赚佣</a>
					</p>
					<span><a href="javascript:void(0)" >帮人贷 赚佣金</a></span>
				</dt>
				<dd>
					<a href="javascript:void(0)" ><img
						src="${ctx }/static/brd-mobile/images/u404.png"></a>
				</dd>
			</dl>
			<dl id="toActivityInformation">
				<dt>
					<p>
						<a >活动资讯</a>
					</p>
					<span><a>速报抢先看</a></span>
				</dt>
				<dd>
					<a><img
						src="${ctx }/static/brd-mobile/images/u406.png"></a>
				</dd>
			</dl>
		</div>
		      <div class="m_text" style="margin-top:10px;">
		    		<p class="m_textp">热门贷款</p>
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
                  
                  <!-- 自助贷-按月 /收益金-->
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
                   
                   
                 </div>
		<div class="help">
			<p>
				<a href="${ctx}/weixin/information/toHelpConter">帮助中心</a>
			</p>
			<ul>
				<c:forEach items="${helpList.getContent() }" var="help">
					<li><a
						href="${ctx}/weixin/information/toArticleDetails/${help.id}">${help.title }</a></li>
				</c:forEach>
			</ul>
		</div>
		
		<div class="kefurx">
		 	<p>客服热线</p>
		 </div>
		
	</div>
	
	 
		
	<div class="kefu hidden">
             <p id="cancelKefu" >取消</p>
             <span><img src="${ctx }/static/brd-mobile/images/hrz.png"><a href="tel:${sysInfo.getCooperatePhone()}" >合作热线：${sysInfo.getCooperatePhone() }</a></span>
             <samp><img src="${ctx }/static/brd-mobile/images/iphone.png"><a href="tel:${sysInfo.getHotline()}">客服热线：${sysInfo.getHotline()}</a></samp>
    </div>
	
	
	<jsp:include  page="../common/footermenu.jsp?mod=sy"/> 
		    <script>
	    var swiper = new Swiper('.swiper-container',{
		    loop: true,
			autoplay:4000,
			speed:300,
			pagination: '#bannerpagination',
			paginationClickable: true,
			grabCursor : true,
			nextButton: '.arrow-right',
		    prevButton: '.arrow-left',
			parallax:true,
		  });
	    </script>
</body>
</html>