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
<link href="${ctx}/static/brd-mobile/css/swiper.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx }/static/brd-mobile/js/swiper.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var sUserAgent = navigator.userAgent.toLowerCase();  
	var isMobile = sUserAgent.match("mobile") == "mobile";  
	if(isMobile){
		location.href="${ctx}/weixin/index/toIndex";
	}
	
	
	$('#qq').click(function(){
		var qq = ${sysInfo.qq}
		location.href = "tencent://message/?uin="+qq;
	});
	$("#top").click(function() {
	    $("html,body").animate({scrollTop:0}, 500);
	}); 
	setTimeout(function(){
		$("MARQUEE").attr("behavior","scroll");	
	},1000);
	
	
	
	
	
	
})
</script>

<title>帮人贷-专注厦门本地的金融贷款服务平台</title>
</head>
<body>
	 <div class="war">
	<div class="banner">
		<div class="swiper-container" id="bannerSwiper" style="cursor: -webkit-grab;">
        <c:choose>
		<c:when test="${empty banners}">
      	<div class="swiper-wrapper">
            <div class="swiper-slide"><a href="#"><img src="${ctx }/static/brd-mobile/images/ban6.jpg" style="width:100%;height:375px"/> </a></div>
            <div class="swiper-slide"><a href="#"><img src="${ctx }/static/brd-mobile/images/ban5.jpg" style="width:100%;height:375px"/> </a></div>
            <div class="swiper-slide"><a href="#"><img src="${ctx }/static/brd-mobile/images/ban4.jpg" style="width:100%;height:375px"/> </a></div>
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
			       	  		<img src="${banner.picurl}" style="width:100%;height:375px"/> </a></div>
			        	</div>
			        	</c:forEach>
		   			</div>
				</c:otherwise>
			
			</c:choose>
         
      		</c:otherwise>
		</c:choose>
</div>

	     <script type="text/javascript">
	var swiper = new Swiper('.swiper-container',{
	    loop: true,
		autoplay:5000,
		speed:1000,
		pagination: '#bannerpagination',
		paginationClickable: true,
		grabCursor : true,
		nextButton: '.arrow-right',
	    prevButton: '.arrow-left',
		parallax:true,
	  });
</script>
    <div class="home_text">
    	<div class="home_box1">
    	<c:choose>
    		<c:when test="${empty middle.address }" >
    			<img src = "${middle.picurl }" style="margin-top: -40px;"/>
    		</c:when>
    		<c:otherwise>
    			<img src = "${middle.picurl }" onclick="window.open('${middle.address}')" style="margin-top: -40px;" />
    		</c:otherwise>
    	</c:choose>
    	
    	
    	</div> 
        <div class="home_box2" style="font-size: 15px;">	   
	        <c:choose>
			<c:when test="${empty sysInfo.scrollBall}">
			<MARQUEE   scrollAmount=3>默认广播</MARQUEE>
			   	</c:when>
			<c:otherwise>
			<MARQUEE behavior="alternate" direction="left" scrollAmount=2>
			<div >${scrollBall }</div>
			</MARQUEE>
			  </c:otherwise>
			</c:choose>
		</div> 
        <div class="home_box5"><img src = "${scrollblow.picurl}"/></div> 
        <div class="home_box4"><img src = "${leftBottom.picurl}"/></div>  
        <div class ="home_box6"><img src = "${rightBottom.picurl }"/></div>
    
    </div>
    <!--war-->
    </div>
    	<div class="share">
            <div class="share">
              <p id="qq"></p>
              <span id="top"></span></div>
          </div>
     <jsp:include page="./footer.jsp"/> 
	
</body>
</html>