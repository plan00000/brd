<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
<%@ taglib prefix="shiro" uri="http://www.springside.org.cn/tags/shiro"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=${baidu_map_api_for_web}"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/TextIconOverlay/1.2/src/TextIconOverlay_min.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/library/MarkerClusterer/1.2/src/MarkerClusterer_min.js"></script>
<title>商家统计</title>
<script type="text/javascript">
var list;	
	$(function(){
		activeNav2("9","9_7");
		
		$.get("${ctx}/admin/reportStatistics/sellerStatistics/data",{},function(rep){
			list = rep.data;
			var map = new BMap.Map("mymap");
			map.centerAndZoom(new BMap.Point(118.141365,24.502271), 13);
			map.enableScrollWheelZoom();
			map.setCurrentCity("福建");
			var markers = [];
			var i = 0;
			for (; i < list.length; i++) {
			   var point = new BMap.Point(list[i].longitude,list[i].latitude);
			   var marker = new BMap.Marker(point);
			   var userId = list[i].userId;
		       showInfo(marker,userId);
			   markers.push(marker);
			}
			var markerClusterer = new BMapLib.MarkerClusterer(map, {markers:markers}); 
		});
		
		function showInfo(marker,userId){
			marker.addEventListener("click",function(){  
				location.href="${ctx}/admin/user/toEditUser/"+userId;
            });
		}
		
		//jq结束
	});
	
	
	
</script>
</head>
<body>
 	<div class="row  border-bottom">
		<div class="basic">
        <p>报表统计</p>
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a>报表统计</a>><a><strong>商家报表</strong></a></span>
    </div>
		<div class="col-lg-2"></div>
	</div>	
	
	<div id="mymap" class="member animated fadeInRight" style="height:650px" >	
	</div>
</body>
</html>