<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ page import="org.apache.shiro.SecurityUtils"%>
<%@ page import="com.zzy.brd.constant.Constant"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<%
Integer times = 0;
Object obj = SecurityUtils.getSubject().getSession().getAttribute(Constant.SYS_ERROR_LOGIN_TIMES);
if(null!=obj)
	times = (Integer)obj;
%>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>登录</title>
    
<%-- <link href="${ctx}/static/brd/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${ctx}/static/brd/font-awesome/css/font-awesome.css"
	rel="stylesheet">

<link href="${ctx}/static/brd/css/animate.css" rel="stylesheet">
<link href="${ctx}/static/brd/css/style.css" rel="stylesheet"> --%>
<link href="${ctx }/static/brd/css/style.ban.css" rel="stylesheet">
<!-- Mainly scripts -->
<script src="${ctx}/static/js/jquery-1.11.1.js"></script>
<script src="${ctx}/static/brd/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx }/static/js/md5-min.js"></script>
<script type="text/javascript">
var t1=<%=times%>;
var isYzm = false;
<%if(times>=Constant.SYS_ERROR_LOGIN_TIMES_VALUE){%>
	isYzm = true;
<%}%>
	$(function(){
		
		$("#ipt_username").val("");
		$("#ipt_password").val("");
		//点击验证码
		$("#img_captcha").click(function(){
			var $this = $(this);
			var time = new Date().getTime();
			$this.attr("src","${ctx }/captcha?type=1&time="+time);
		});
		$("#form_login input").keypress(function(event) {
			if (event.keyCode == 13) {
				$("#ipt_submit").click();						
			}
		});
		
		$("#ipt_submit").click(function(){
			if (!check()) {
				return;
			} 
			if ($("#ipt_password").val().length != 32)
				$("#ipt_password").val(hex_md5($("#ipt_password").val()));
			$("#form_login").submit();
		});
	});
	
	//检查参数
	function check() {
		if (!checkUsername()) {
			return false;
		}
		if (!checkPassword()) {
			return false;
		}
		return true;
	}
	//检测用户名
	function checkUsername() {
		var username = $("#ipt_username").val();
		if (username.trim().length == 0) {
			//用户名为空
			showMsg("用户名为空");
			return false;
		}
		return true;
	}
	//检查密码
	function checkPassword() {
		var password = $("#ipt_password").val();
		if (password.trim().length == 0) {
			//密码为空
			showMsg("密码为空");
			return false;
		}
		return true;
	}
	function showMsg(msg){
		$("#loginMsg").html("");
		$("#msgSpan").html(msg);
		$("#msgSpan").attr("style","display:inline-block;color:#B94A48");
	}
</script>
</head>
 <%-- <body class="gray-bg">

    <div class="middle-box text-center loginscreen animated fadeInDown" style="position:absolute;
            left:50%; 
            bottom:25%; 
            margin:-150px 0 0 -150px;">
		<div>
			<img src="${ctx}/static/brd/img/edaibao_logo.png" />
				<!--帮人贷LOGO-->
			<h3 >欢迎使用帮人贷管理系统！</h3>
			<form class="m-t" id="form_login" role="form" action="${ctx}/admin/login" method="post">
				<div class="form-group">
					<input type="text" class="form-control" placeholder="用户名"  name="username" id="ipt_username">
				</div>
				<div class="form-group">
					<input type="password" class="form-control" placeholder="密码" name="password" id="ipt_password">
				</div>
				<div class="form-group" style="display:<%=times>=Constant.SYS_ERROR_LOGIN_TIMES_VALUE?"block":"none"%>">
					<label>验证码</label>
					<input type="text" name="captcha" autocomplete="off" id="ipt_captcha">
					<img style="cursor:pointer;" id="img_captcha" src="${ctx }/captcha?type=1"></img>
				</div>
				<input type="hidden" name="rememberMe"  value="false">
				<button id="ipt_submit" type="button" class="btn btn-primary block full-width m-b">登录</button>
				<!--<a href="#"><small>忘记密码?</small></a>-->
			</form>
			<span id="msgSpan" style="display: none;color:#B94A48"></span>
			<%
				if(request.getAttribute("errMsg") != null){
			%> 					
			<span id="loginMsg" style="display: inline-block;color:#B94A48">${errMsg}</span>
			<%}else{%>
			<span id="loginMsg" style="display: none;">验证码错误</span>
			<%} %>
			
			<p class="m-t">
				<small>Copyright 帮人贷APP © 2015-2016.</small>
			</p>
		</div>
	</div>
</body>  --%>
<body style="background:#f9f9f9;">
	<div class="login_in">
        <div class="login">
            <p>城际车后台管理系统</p>
        </div>
        </div>
        <div class="login_admin">
        	<form class="m-t" id="form_login" role="form" action="${ctx}/admin/login" method="post">
	            <div class="login_use">
	                <h2>登录<font>SIGN IN</font></h2>
	                <span><input type="text" class="l_input" placeholder="用户名" name="username" id="ipt_username"></span>
	                <span><input type="password" class="l_input" placeholder="密码" name="password" id="ipt_password"></span>
	                
	                <span class="form-group" style="display:<%=times>=Constant.SYS_ERROR_LOGIN_TIMES_VALUE?"block":"none"%>">
	                    <!-- <input type="text" class="l_input l_hot" value="验证码"> -->
	                    <input type="text" name="captcha" autocomplete="off" class="l_input l_hot" id="ipt_captcha">
	                    <samp><img style="cursor:pointer;" id="img_captcha" src="${ctx }/captcha?type=1"></img></samp>
	                </span>
	                <p id="ipt_submit">登录</p>
	                <span id="msgSpan" style="display: none;color:#B94A48"></span>
	                <%
					if(request.getAttribute("errMsg") != null){
					%> 					
					<span id="loginMsg" style="display: inline-block;color:#B94A48">${errMsg}</span>
					<%}else{%>
					<span id="loginMsg" style="display: none;">验证码错误</span>
					<%} %>
	            </div>
	        </form>
          </div>
          <div class="foot">© Copyright 2021 王品睿 086</div>
    </body>
</html>