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
<title>帮人贷登录</title>
<script type="text/javascript" src="${ctx }/static/js/common.js"></script>
<script type="text/javascript" src="${ctx }/static/js/md5-min.js"></script>
<script>
document.title="帮人贷登录"
$(function(){
	//点击登录
	$("#ipt_login").click(function(){
		if(!check()){
			return;
		}
		showLoading();
		$("#ipt_password2").val(hex_md5($("#ipt_password").val()));
		$.post('${ctx}/weixin/user/login',$('#form_submit').serialize(),function(data){
			$("#ipt_password").val("");
			if (data.code == 1){
				//登录成功
				location.href = "${ctx}/weixin/index/toIndex;JSESSIONID=<%=request.getSession().getId()%>";

			}else if (data.code == 0){
				//登录失败
				alert(data.mes);
			}
			hideLoading();
			
		})
	});
	
});
//检查参数
function check(){
	if (!checkUsername()){
		return false;
	}
	if (!checkPassword()){
		return false;
	}
	return true;
}
//检测用户名
function checkUsername(){
	var username = $("#ipt_username").val();
	if (username.trim().length==0){
		//用户名为空
		alert("用户名为空");
		return false;
	}
	return true;
}
//检查密码
function checkPassword(){
	var password = $("#ipt_password").val();
	if (password.trim().length==0){
		//密码为空
		alert("密码为空");
		return false;
	}
	if (!CommValidate.isPassword(password)){
		//不符合密码格式
		alert("密码格式错误");
		return false;
	} 
	if (password.length <6){
		alert("密码至少为6个字符");
		return false;
	}
	if (password.length > 12){
		alert("密码至多为12个字符");
		return false;
	}
	return true;
}
function toRegister() {
	var openId = "${openId}";
	location.href = "${ctx }/weixin/user/register/toRegister?openId="+openId;
}

</script>
</head>
 
<body>
<div class="login">
   <div class="login_in">
   	<form id="form_submit">
   		<input type="hidden" id="ipt_password2" name="password"/>
       <dl>
           
           <dt>账户</dt>
           <dd><input type="text" class="l_txt" id="ipt_username" maxlength="11"  placeholder="手机号" name="username"></dd>
      </dl>
      <dl style="width:100%; margin-left:0;padding-left:2%;">
           <dt style="width:24.5%;">登录密码 <input type="hidden" value="${openId }" name="openId" ></dt>
           <dd><input type="password" class="l_txt" id="ipt_password" maxlength="12"  placeholder="密码" ></dd>
      </dl>
      <p><button type="button" class="btn" id="ipt_login">登录</button></p>
    </form>
      <samp><a href="${ctx }/weixin/user/forgetPassword/toResetPassword">忘记密码</a></samp> 
   	<samp style=" text-align:right;"><a href="javascript:void(0)" onclick="toRegister()">免费注册</a></samp>
<%--    	<samp style=" text-align:right;"><a href="${ctx }/weixin/user/register/toRegister/1"${openId }>免费注册</a></samp> --%>
   </div>
 </div>
</body>
</html>
