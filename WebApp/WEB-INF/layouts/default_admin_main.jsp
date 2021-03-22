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
 					
 					<shiro:hasAnyPermissions name="ORGANIZATION_MANAGER_DEPART,ORGANIZATION_MANAGER_USER,ORGANIZATION_MANAGER_ROLE">
					<li id="na_li1"><a href="#"><i class="fa fa-edit"></i> <span
							class="nav-label">企业管理</span><span class="fa arrow"></span></a>
						<ul class="nav nav-second-level collapse">
							<shiro:hasPermission name="ORGANIZATION_MANAGER_DEPART"><li id="na_li1_1" >
							<a href="${ctx}/admin/department/list">部门管理</a></li></shiro:hasPermission>
							<shiro:hasPermission name="ORGANIZATION_MANAGER_ROLE"><li id="na_li1_2">
							<a href="${ctx}/admin/role/list">角色管理</a></li></shiro:hasPermission>
							<shiro:hasPermission name="ORGANIZATION_MANAGER_USER"><li id="na_li1_3">
							<a href="${ctx}/admin/employee/list">员工管理</a></li>	</shiro:hasPermission>
						</ul></li>
					</shiro:hasAnyPermissions>

					<shiro:hasPermission name="USER_MANAGER">
						<li id="na_li2"><a href="${ctx}/admin/user/list"><i class="fa fa-user"></i> <span
								class="nav-label">会员管理</span></a>
						</li>
					</shiro:hasPermission>
				 <shiro:hasAnyPermissions name="ORDER_MANAGER,ORDER_MANAGER_PC,ORDER_MANAGER_BROKERAGE,ORDER_MANAGER_WITHDRAW">
					<li id="na_li3"><a href="#"><i class="fa fa-table"></i> <span
							class="nav-label">订单管理</span><span class="fa arrow"></span></a>
						<ul class="nav nav-second-level collapse">
							<shiro:hasPermission name="ORDER_MANAGER">
								<li id="na_li3_1" ><a href="${ctx}/admin/orderform/weixin/list">贷款订单</a></li>
							</shiro:hasPermission>
							
							<shiro:hasPermission name="ORDER_MANAGER_PC">
								<li id="na_li3_2"><a href="${ctx}/admin/orderform/pc/list">官网订单</a></li>		
							</shiro:hasPermission>
							
							<shiro:hasPermission name="ORDER_MANAGER_BROKERAGE">
								<li id="na_li3_3"><a href="${ctx}/admin/orderform/brokerage/list">佣金订单</a></li>
							</shiro:hasPermission> 
							
							<shiro:hasPermission name="ORDER_MANAGER_WITHDRAW">
								<li id="na_li3_4"><a href="${ctx}/admin/orderform/flowwithdraw/list">提现订单</a></li>
							</shiro:hasPermission>
						</ul></li>
 
					</shiro:hasAnyPermissions>
					<shiro:hasAnyPermissions name="PRODUCT_MANAGER_TYPE,PRODUCT_MANAGER">
					<li id="na_li4"><a href="#"><i class="fa fa-table"></i> <span
							class="nav-label">产品管理</span><span class="fa arrow"></span></a>
						<ul class="nav nav-second-level collapse">
							<shiro:hasPermission name="PRODUCT_MANAGER_TYPE">
								<li id="na_li4_1" ><a href="${ctx}/admin/product/productType/list">产品类型</a></li>
							</shiro:hasPermission>
							
							<shiro:hasPermission name="PRODUCT_MANAGER">
								<li id="na_li4_2"><a href="${ctx}/admin/product/productlist">产品管理</a></li>		
							</shiro:hasPermission>
							
						</ul></li>
 
					</shiro:hasAnyPermissions>  
					 <shiro:hasAnyPermissions name="ACTIVITY_MANAGER_STAR,ACTIVITY_MANAGER_APPRENTICE,ACTIVITY_MANAGER_REGISTER,ACTIVITY_MANAGER_QRCODE">
						<li id="na_li5"><a href="#"><i class="fa fa-edit"></i> <span
								class="nav-label">活动管理</span><span class="fa arrow"></span></a>
							<ul class="nav nav-second-level collapse">
								<shiro:hasPermission name="ACTIVITY_MANAGER_STAR">
									<li id="na_li5_1"><a href="<c:url value='/admin/activity/starOrderList'/>">星级订单</a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="ACTIVITY_MANAGER_APPRENTICE">
									<li id="na_li5_2"><a href="<c:url value='/admin/activity/apprenticeReward'/>">收徒奖励</a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="ACTIVITY_MANAGER_REGISTER">
									<li id="na_li5_3"><a href="<c:url value='/admin/activity/recommendRegister'/>">推荐注册</a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="ACTIVITY_MANAGER_QRCODE">
									<li id="na_li5_4"><a href="<c:url value='/admin/qrCodeActivity/list'/>">二维码活动</a></li>
								</shiro:hasPermission>
							</ul>
						</li>
					</shiro:hasAnyPermissions>
					
					<shiro:hasAnyPermissions name="TEXT_MANAGER_ACTIVITY,TEXT_MANAGER_HELP">
						<li id="na_li6"><a href="#"><i class="fa fa-file-o"></i> <span
								class="nav-label">微信文章</span><span class="fa arrow"></span></a>
							<ul class="nav nav-second-level collapse">
								<shiro:hasPermission name="TEXT_MANAGER_ACTIVITY">
									<li id="na_li6_1"><a href="${ctx}/admin/information/activity/list">活动资讯</a></li>	
								</shiro:hasPermission>
								<shiro:hasPermission name="TEXT_MANAGER_ACTIVITY">
									<li id="na_li6_2"><a href="${ctx}/admin/information/help/list">帮助中心</a></li>	
								</shiro:hasPermission>
							</ul>
						</li>
					</shiro:hasAnyPermissions>
					
					<shiro:hasAnyPermissions name="TEXT_MANGER_PC_ACTIVITY,TEXT_MANAGER_APPRENTICE,TEXT_MANAGER_ABOUTUS">
						<li id="na_li10"><a href="#"><i class="fa fa-file-o"></i> <span
								class="nav-label">官网文章</span><span class="fa arrow"></span></a>
							<ul class="nav nav-second-level collapse">
								<shiro:hasPermission name="TEXT_MANGER_PC_ACTIVITY">
									<li id="na_li10_1" ><a href="${ctx}/admin/information/pcactivity/list">精彩资讯</a></li>	
								</shiro:hasPermission>
								<shiro:hasPermission name="TEXT_MANAGER_APPRENTICE">
									<li id="na_li10_2" ><a href="${ctx}/admin/information/apprentice">收徒指南</a></li>	
								</shiro:hasPermission>
								<shiro:hasPermission name="TEXT_MANAGER_ABOUTUS">
									<li id="na_li10_3" ><a href="${ctx}/admin/information/about">关于我们</a></li>	
								</shiro:hasPermission>
							</ul>
						</li>
					</shiro:hasAnyPermissions>
					
					 <shiro:hasAnyPermissions name="AD_MANAGER_PC,AD_MANAGER_WEIXIN">
						<li id="na_li7"><a href="#"><i class="fa fa-picture-o"></i> <span
								class="nav-label">广告管理</span><span class="fa arrow"></span></a>
							<ul class="nav nav-second-level collapse">
								<shiro:hasPermission name="AD_MANAGER_PC">
									<li id="na_li7_1"><a href="${ctx }/admin/advertisement/pc/ad/list">PC官网</a></li>	
								</shiro:hasPermission>
								<shiro:hasPermission name="AD_MANAGER_WEIXIN">
									<li id="na_li7_2" ><a href="${ctx}/admin/advertisement/weixin/ad/list">微信站</a></li>	
								</shiro:hasPermission>
							</ul>
						</li>
					</shiro:hasAnyPermissions> 


				    <shiro:hasAnyPermissions name="SET_MANAGER_BASE,SET_MANAGER_INFO,SET_MANAGER_AGGREMENT,SET_WECHAT_NOTIFY,SET_ORDERFORM_NOTIFY,SET_SEO_SETTING">
						<li id="na_li8"><a href="#"><i class="fa fa-laptop"></i> <span
								class="nav-label">平台设置</span><span class="fa arrow"></span></a>
							<ul class="nav nav-second-level collapse">
								<shiro:hasPermission name="SET_MANAGER_BASE">
									<li id="na_li8_1"><a href="<c:url value='/admin/platformSetting/baseSetting'/>">基础设置</a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="SET_MANAGER_INFO">
									<li id="na_li8_2"><a href="<c:url value='/admin/platformSetting/toAddMessage'/>">短信通知</a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="SET_ORDERFORM_NOTIFY">
									<li id="na_li8_3"><a href="<c:url value='/admin/platformSetting/orderMessage'/>">订单通知</a></li>
								</shiro:hasPermission> 
								<shiro:hasPermission name="SET_MANAGER_AGGREMENT">
									<li id="na_li8_4"><a href="<c:url value='/admin/platformSetting/toServiceAgreement'/>">服务协议</a></li>
								</shiro:hasPermission> 
								<shiro:hasPermission name="SET_WECHAT_NOTIFY">
									<li id="na_li8_5"><a href="<c:url value='/admin/weixinPost/weixinPost'/>">微信推送</a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="SET_SEO_SETTING">
									<li id="na_li8_6"><a href="<c:url value='/admin/platformSetting/seoSetting'/>">SEO设置</a></li>
								</shiro:hasPermission>
								<shiro:hasPermission name="SET_FRIENDSHIPLINK">
									<li id="na_li8_7"><a href="<c:url value='/admin/platformSetting/friendshiplink/list'/>">友情链接</a></li>
								</shiro:hasPermission>
							</ul>
						</li>
					</shiro:hasAnyPermissions>
						 <shiro:hasAnyPermissions name="STATISTICS_MANAGER_USER,STATISTICS_REPORT_USER,STATISTICS_MANAGER_ORDER,STATISTICS_MANAGER_PRODUCT,STATISTICS_MANAGER_DEPART,STATISTICS_MANAGER_BROKERAGE">
						<li id="na_li9"><a href="#"><i class="fa fa-pie-chart"></i> <span
								class="nav-label">报表统计</span><span class="fa arrow"></span></a>
							<ul class="nav nav-second-level collapse">
									<shiro:hasPermission name="STATISTICS_MANAGER_USER">
										<li id="na_li9_1" ><a href="<c:url value='/admin/reportStatistics/userStatistics/userStatistics'/>">会员统计</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="STATISTICS_REPORT_USER">
										<li id="na_li9_2" ><a href="<c:url value='/admin/reportStatistics/userReport/userReport'/>">会员报表</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="STATISTICS_MANAGER_ORDER">
										<li id="na_li9_3" ><a href="<c:url value='/admin/reportStatistics/orderStatistics/orderStatistics'/>">订单统计</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="STATISTICS_MANAGER_PRODUCT">
										<li id="na_li9_4"><a href="<c:url value='/admin/reportStatistics/productStatistics/productStatistics'/>">产品统计</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="STATISTICS_MANAGER_DEPART">
										<li id="na_li9_5"><a href="<c:url value='/admin/reportStatistics/departmentStatistics/departmentStatistics'/>">部门统计</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="STATISTICS_MANAGER_BROKERAGE">
										<li id="na_li9_6"><a href="<c:url value='/admin/reportStatistics/brokerageStatistics/brokerageStatistics'/>">佣金统计</a></li>
									</shiro:hasPermission>
									<shiro:hasPermission name="STATISTICS_SELLER">
										<li id="na_li9_7"><a href="<c:url value='/admin/reportStatistics/sellerStatistics'/>">商家统计</a></li>
									</shiro:hasPermission>
										<li id="na_li9_8"><a href="<c:url value='/admin/reportStatistics/pv'/>">访问量统计</a></li>
							</ul>
						</li>
					</shiro:hasAnyPermissions>
					
					<shiro:hasPermission name="SYS_LOG">
						<li id="na_li11"><a href="${ctx}/admin/operlog/list"><i class="fa fa-magic"></i> <span class="nav-label">操作日志</span></a>
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