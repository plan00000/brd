<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="${sysInfo.seoKeyword }"  />
<meta name="description" content="${sysInfo.seoDescribe }" />
<title> <sitemesh:title/> </title>
<link href="${ctx }/static/brd/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${ctx }/static/js/baguettebox/css/baguetteBox.css">
<link href="${ctx }/static/brd-front/css/style.css" rel="stylesheet" type="text/css" />
<link
	href="${ctx }/static/js/jquery-ui-1.11.4.custom/jquery-ui-2.min.css"
	rel="stylesheet" />
<script src="${ctx }/static/js/jquery-1.11.1.min.js"></script>
<script src="${ctx }/static/js/jquery-ui-1.11.4.custom/jquery-ui.min.js"></script>
<sitemesh:head />
</head>
<script >
	$(function(){
		
		var title = '${sysInfo.seoTitle}';
		if(title==null){
			title="城际车官网";
		}
		document.title=title;
		
		
	})
</script>
<body>
	<!-- <div class="head">
        <a href="#" target="_blank"><p></p></a>
        <span>400-800-1234</span>
    </div> -->
    <div class="fdjuhu">
	    <div class="head">
	        <a href="#" target="_blank"><p></p></a>
	        <samp>指尖上的贷款神器，口袋里的赚佣法宝</samp>
	        <span></span>
	    </div>
    </div>
    <div class="top">
        <div class="top_t">
            <ul>
            	<a href="${ctx }/pc/main"><li <c:if test = "${pcSelect eq '1' }">class='bj'</c:if> >首页</li></a>
                <a href="${ctx }/pc/product/list" ><li <c:if test = "${pcSelect eq '2' }">class='bj'</c:if> >热门贷款</li></a>
                <a href="${ctx }/pc/apprentice" ><li <c:if test = "${pcSelect eq '3' }">class='bj'</c:if> >收徒赚佣</li></a>
                <a href="${ctx }/pc/about"><li <c:if test = "${pcSelect eq '4' }">class='bj'</c:if> >关于我们</li></a>
                <a href="${ctx }/pc/infomation"><li <c:if test = "${pcSelect eq '5' }">class='bj'</c:if> >精彩资讯</li></a>
            </ul>
        </div>
    </div>
	<sitemesh:body />
<tags:commonDialog></tags:commonDialog>
<tags:mobile-loading></tags:mobile-loading>
</body>
</html>