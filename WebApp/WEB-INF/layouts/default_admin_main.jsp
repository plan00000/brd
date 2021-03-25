<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="shiro" uri="http://www.springside.org.cn/tags/shiro"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + path;
%>
<!DOCTYPE html>
<html>
<head>
<%-- 从被装饰页面获取title标签内容 --%>
<title><sitemesh:title /></title>

<link href="${ctx }/static/brd/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${ctx }/static/brd/font-awesome/css/font-awesome.css"
	rel="stylesheet">

<!-- Toastr style -->
<link href="${ctx }/static/brd/css/plugins/toastr/toastr.min.css"
	rel="stylesheet">

<!-- Gritter -->
<link
	href="${ctx }/static/brd/js/plugins/gritter/jquery.gritter.css"
	rel="stylesheet">

<link href="${ctx }/static/brd/css/animate.css" rel="stylesheet">
<link href="${ctx }/static/brd/css/style.css" rel="stylesheet">
<link href="${ctx }/static/brd/css/style.ban.css" rel="stylesheet">
<link
	href="${ctx }/static/js/jquery-ui-1.11.4.custom/jquery-ui-2.min.css"
	rel="stylesheet">
<!-- iCheck -->
<link href="${ctx }/static/brd/css/plugins/iCheck/custom.css" rel="stylesheet">

<link href="${ctx}/static/brd/css/style.ban.css" rel="stylesheet" >

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Local style only for demo purpose -->
<style>
.directive-list {
	list-style: none;
}

.directive-list li {
	background: #f3f3f4;
	padding: 10px 20px;
	margin: 4px;
}
</style>
<!-- baguettbox -->
<link rel="stylesheet"
	href="${ctx }/static/js/baguettebox/css/baguetteBox.css">

<!-- Mainly scripts -->
<script src="${ctx}/static/js/jquery-1.9.1.min.js"></script>
<script src="${ctx}/static/brd/js/bootstrap.min.js"></script>
<script
	src="${ctx }/static/brd/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script
	src="${ctx }/static/brd/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

<!-- Flot -->
<script src="${ctx }/static/brd/js/plugins/flot/jquery.flot.js"></script>
<script
	src="${ctx }/static/brd/js/plugins/flot/jquery.flot.tooltip.min.js"></script>
<script
	src="${ctx }/static/brd/js/plugins/flot/jquery.flot.spline.js"></script>
<script
	src="${ctx }/static/brd/js/plugins/flot/jquery.flot.resize.js"></script>
<script src="${ctx }/static/brd/js/plugins/flot/jquery.flot.pie.js"></script>

<!-- Peity -->
<script
	src="${ctx }/static/brd/js/plugins/peity/jquery.peity.min.js"></script>
<script src="${ctx }/static/brd/js/demo/peity-demo.js"></script>

<!-- Custom and plugin javascript -->
<script src="${ctx }/static/brd/js/inspinia.js"></script>
<script src="${ctx }/static/brd/js/plugins/pace/pace.min.js"></script>

<!-- jQuery UI -->
<script src="${ctx }/static/js/jquery-ui-1.11.4.custom/jquery-ui.min.js"></script>

<!-- Sparkline -->
<script
	src="${ctx }/static/brd/js/plugins/sparkline/jquery.sparkline.min.js"></script>

<!-- Sparkline demo data  -->
<script src="${ctx }/static/brd/js/demo/sparkline-demo.js"></script>
<!-- ChartJS-->
<script src="${ctx }/static/brd/js/plugins/chartJs/Chart.min.js"></script>
<!-- Toastr -->
<script src="${ctx }/static/brd/js/plugins/toastr/toastr.min.js"></script>
<!-- baguettbox -->
<script src="${ctx}/static/js/baguettebox.min.js"></script>
<!-- iCheck -->
<script src="${ctx}/static/brd/js/plugins/iCheck/icheck.min.js"></script>

<script src="${ctx }/static/brd/js/bootstrap-transition.js" ></script>

<script src="${ctx }/static/brd/js/bootstrap-modal.js" ></script>

<link href="${ctx}/static/brd/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">

<script src="${ctx}/static/js/sweetalert/sweetalert.min.js"  ></script>


<script type="text/javascript">
	//激活菜单栏
	function activeNav(no) {
		$("#na_li"+no).siblings().removeClass("active");
  		$("#na_li"+no).addClass("active");  
  		var $navLi = $("#na_li" + no + " a");
  		$navLi.click();
	}
	//双下拉菜单栏
	function activeNav2(no,no_) {
		var $navLi = $("#na_li" + no + " a");
		$navLi.click();
		$("#na_li" + no_).addClass("active");
		$("#na_li"+ no_ +" ul").collapse('toggle');
	}
	$(function(){
		$("#to_edit_li").click(function(){
			/* location.href = "${ctx}/admin/personal/info"; */
		});
	});
	
	
</script>
<sitemesh:head />
</head>
<body>
	<div id="wrapper">
		<nav class="navbar-default navbar-static-side" role="navigation">
			<div class="sidebar-collapse">
				<ul class="nav metismenu" id="side-menu">
					<li id="to_edit_li" class="nav-header">
						<div class="dropdown profile-element">
							<span>
						<%-- <c:choose>
                   			<c:when test="${empty u_headUrl }">
                   				<dt><img alt="image" class="img-circle"src="${ctx }/static/brd/img/profile_small.jpg" /></dt>
                   			</c:when>
                   			<c:otherwise>
								<dt><img id="headimg_url"  class="img-circle" src="${ctx}/files/displayProThumb?filePath=${u_headUrl}&thumbWidth=120&thumbHeight=80" /></dt>
                   			</c:otherwise>
                   		</c:choose> --%>
								
							</span> <a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<span class="clear"> <span class="block m-t-xs"> <strong
										class="font-bold">${u_name}</strong>
								</span> <span class="text-muted text-xs block"></span>
							</span>
							</a>
							<ul class="dropdown-menu animated fadeInRight m-t-xs">
								<li><a href="${ctx }/admin/main/toChangePassword">修改密码</a></li>
								<li><a href="${ctx }/admin/main/toBaseInfo">基本信息</a></li>
								<li class="divider"></li>
								<li><a href="${ctx}/admin/login/logout">安全退出</a></li>
							</ul>
						</div>
						<div class="logo-element">城际车</div>
					</li>
 					<li id="na_li0"><a href="${ctx}/admin/main"><i class="fa fa-table"></i> <span
								class="nav-label">首页</span></a>
					</li>

					<shiro:hasPermission name="USER_MANAGER">
						<li id="na_li2"><a href="${ctx}/admin/user/list"><i class="fa fa-user"></i> <span
								class="nav-label">后台用户管理</span></a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="SYS_LOG">
						<li id="na_li3"><a href="${ctx}/admin/orderform/list"><i class="fa fa-magic"></i> <span class="nav-label">订单管理</span></a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="SYS_LOG">
						<li id="na_li4"><a href="${ctx}/admin/line/list"><i class="fa fa-magic"></i> <span class="nav-label">线路管理</span></a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="SYS_LOG">
						<li id="na_li5"><a href="${ctx}/admin/driver/list"><i class="fa fa-magic"></i> <span class="nav-label">司机管理</span></a>
						</li>
					</shiro:hasPermission>
					<shiro:hasPermission name="SYS_LOG">
						<li id="na_li6"><a href="${ctx}//admin/passenger/list"><i class="fa fa-magic"></i> <span class="nav-label">乘客管理</span></a>
						</li>
					</shiro:hasPermission>
	
				 </ul>
			</div>
		</nav>
		<div id="page-wrapper" class="gray-bg">
			<div class="row border-bottom">
				<nav class="navbar navbar-static-top white-bg" role="navigation"
					style="margin-bottom: 0">
					<div class="navbar-header">
						<a class="navbar-minimalize minimalize-styl-2 btn btn-primary "
							href="#"><i class="fa fa-bars"></i> </a>
						<!--<form role="search" class="navbar-form-custom" action="search_results.html">
                <div class="form-group">
                    <input type="text" placeholder="Search for something..." class="form-control" name="top-search" id="top-search">
                </div>
            </form>-->
					</div>
					<ul class="nav navbar-top-links navbar-right">
						<li><span class="m-r-sm text-muted welcome-message">欢迎使用城际车管理系统！</span>
						</li>
						<li><a href="${ctx}/admin/login/logout"> <i
								class="fa fa-sign-out"></i> 安全退出
						</a></li>
					</ul>

				</nav>
			</div>
			<sitemesh:body />
			<!-- <div class="footer">
				<div class="pull-right">
					<strong>Copyright</strong> 帮人贷APP &copy; 2015-2016.
				</div>
			</div> -->
		</div>
	</div>
	<tags:commonDialog></tags:commonDialog>
	<tags:loading></tags:loading>
</body>
</html>