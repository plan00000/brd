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
<title>忘记密码</title>
<script type="text/javascript" src="${ctx }/static/js/common.js"></script>
<script type="text/javascript" src="${ctx }/static/js/md5-min.js"></script>
<script type="text/javascript">
document.title="忘记密码"
var time;
var getcode;
var showtime;
$(function(){
	//点击获取手机验证码
	$("#ipt_get_phonecode").click(function(){
		var $this = $(this);
		if ($this.data("enable")==false){
			return ;
		}else{
			$this.data("enable", false);
		}
		if (!checkMobileno()){
			$this.data("enable", true);
			return ;
		}
		var phone = $("#ipt_mobileno").val();
		var data = {};
		data.phone= phone;
		showLoading();
		$.post("${ctx}/weixin/user/forgetPassword/isPhoneRegister",{phone:phone},function(res){
			if (res.code == 1){
				$.post("${ctx}/weixin/user/forgetPassword/getPhoneAuthcode/forgetPassword;JSESSIONID=<%=request.getSession().getId()%>",data,function(res){
					if (res.code == 1){
						//获取手机验证码成功
						$this.data("enable", false);
						getcode = $this;
						showtime=$("#showtime");
						time = 60;
						getcode.css("background-color","#f4f4f4");
						window.setTimeout(countdown,1000);
					}else{
						alert(res.mes);
					}
				});
			}else{
				alert(res.mes);
			}
			$this.data("enable", true);
			hideLoading();
		});
	});
	//点击修改密码
	$("#btn_resetpw").click(function(){
		if (!check()){
			return ;
		}
		showLoading();
		$("#ipt_password2").val(hex_md5($("#ipt_password").val()));
		$.post("${ctx}/weixin/user/forgetPassword/ResetPassword;JSESSIONID=<%=request.getSession().getId()%>",$("#form_register").serialize(),function(res){
			if (res.code == 1){
				location.href = "${ctx}/weixin/user/toLogin;JSESSIONID=<%=request.getSession().getId()%>";
			}else{
				//alert(res.mes);
				layer.open({content: res.mes, time: 2});
			}
			hideLoading();
		})
	});
	
});
function countdown() {
	time--;
	if (time <= 0) {
		getcode.data("enable", true);
		showtime.text("获取短信验证码");
		getcode.css("background", "#fb8044");
		getcode.css("color", "#fff");
	} else {
		showtime.text(time + "秒后可重发");
		window.setTimeout(countdown, 1000);
	}

}
//检测参数
function check(){
	if (!checkMobileno()){
		return false;
	}
	if (!checkPhonecode()){
		return false;
	}
	if (!checkPassword()){
		return false;
	}
	return true;
}
//检测手机验证码
function checkPhonecode(){
	var $phonecode = $("#ipt_phoneAuthcode");
	var phonecode = $phonecode.val();
	if (phonecode.trim().length == 0){
		layer.open({content: '请填写手机验证码', time: 2});
		return false;
	}else if (phonecode.length != 6){
		layer.open({content: '请正确填写手机验证码', time: 2});
		return false;
	}
	return true;
}
//检查手机号码
function checkMobileno(){
	var $mobileno = $("#ipt_mobileno");
	var mobileno = $mobileno.val();
	if (mobileno.trim().length == 0){
		layer.open({content: '请填写手机号码', time: 2});
		return false;
	}
	if (mobileno.length != 11){
		layer.open({content: '您的号码不是正确的手机号，请联系公司人员前往系统后台修改号码', time: 2});
		return false;
	}
	if (!CommValidate.checkPhone(mobileno)){
		layer.open({content: '手机号码格式错误', time: 2});
		return false
	}
	return true;
}
//检测密码
function checkPassword(){
	var $password = $("#ipt_password");
	
	var password = $password.val();
	
	var password1 = $("#ipt_password1").val();
	
	if (password.trim().length==0){
		//密码为空
		layer.open({content: '密码为空', time: 2});
		return false;
	}
	if (password1.trim().length == 0){
		//确认密码不能为空
		layer.open({content:'确认密码为空',time:2});
		return false;
	}
	if (!CommValidate.isPassword(password)){
		//不符合密码格式
		layer.open({content: '密码格式错误', time: 2});
		return false;
	}
	if (password.length <6){
		layer.open({content: '密码至少为6个字符', time: 2});
		return false;
	}
	if (password.length > 12){
		layer.open({content: '密码至多为12个字符', time: 2});
		return false;
	}
	//判断密码是否一致
	if(password != password1){
		layer.open({content:'新密码和确认密码不一致',time:2});
		return false;
	}
	
	
	return true;
}
</script>
</head>
<body>
	<div class="login">
         <div class="login_in">
         	<form id="form_register">
         	<input type = "hidden" name="password" id="ipt_password2"/>
             <dl>
                 <dt>手机号码</dt>
                 <dd><input type="text" class="l_txt" placeholder="请输入手机号码" name="mobileno" id="ipt_mobileno" maxlength = "11"></dd>
            </dl>
            <dl>
                 <dt>验证码</dt>
                 <dd>
                 <input type="text" class="d_txt" placeholder="请输入验证码" id = "ipt_phoneAuthcode" name="phoneAuthcode">
                 <button class="d_btn" type="button" id = "ipt_get_phonecode"><div id = "showtime" style = "color:#a5411f">获取验证码</div></button> 
                 </dd>
            </dl>
            <dl>
                 <dt>输入新密码</dt>
                 <dd><input type="password" class="l_txt" placeholder="输入新密码" id="ipt_password"></dd>
            </dl>
            <dl style="width:100%; margin-left:0; padding-left:2%;">
                 <dt>确认新密码</dt>
                 <dd><input type="password" class="l_txt" placeholder="确认新密码" id = "ipt_password1"></dd>
            </dl>
            <span style="color:#a5411f;">密码由6-12位数字、英文字符组成</span>
            <p><button type="button" class="btn" id = "btn_resetpw">确定</button></p>
            </form>
           
         </div>
   </div>
</body>
</html>