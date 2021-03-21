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
<script type="text/javascript" src="${ctx }/static/js/common.js"></script>
<script type="text/javascript" src="${ctx }/static/js/md5-min.js"></script>
<link href="${ctx}/static/brd/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<script src="${ctx}/static/js/sweetalert/sweetalert.min.js"  ></script>
<title>修改提现密码</title>
<script>
	document.title="修改提现密码";
	var getcode;
	var showtime;
$(function(){
	
	var tel = '${phone}';
	if(tel.length!=11){
		swal({
			title:"您的号码不是正确的手机号码不需要修改提现密码",
			type:"warning",
			showCancelButton:false,
			confirmButtonColor:"#DD6B55",
			confirmButtonText: "是",
			closeOnConfirm:false					
		},
		 function(){
			location.href = "${ctx}/weixin/usercenter/main;JSESSIONID=<%=request.getSession().getId()%>";
		}
		)	
	}
	
	
	
	
	
	
	
	
	
	 $("#sendCode").click(function(){
		 var $this = $(this);
		$.get("${ctx}/weixin/withdraw/getPhoneAuthcode",{auth:0},function(data){
			if(data.code==0){
				$this.data("enable", false);
				getcode = $this;
				showtime=$("#sendCode");
				time = 60;
				getcode.css("background-color","#f4f4f4");
				window.setTimeout(countdown,1000);
			}else{
				swal(data.mes);
			}
		
		});		
		
	});
	
	
	
	$("#sub").click(function(){
		var newPassword=$("#newPassword").val().trim();
		var confirmPassword=$("#confirmPassword").val().trim();
	    
		if(newPassword.length<6 || newPassword.length>12 || confirmPassword.length<6 || confirmPassword.length>12){
			swal("请输入6-12位提现密码");
			return ;
		}
		
		if(newPassword!=confirmPassword){
	    	swal("两次输入的密码必须相同");
	    	return ;
	    }
		if(newPassword.length==0){
			swal("请输入密码")
			return ;
		}
		if(confirmPassword.length==0){
			swal("请输入再次确认密码")
			return ;
		} 
		var rep=/^[0-9a-zA-z]{6,12}$/gi;
		if(!rep.test(newPassword)){
			swal("密码由6-12位数字、英文字符组成");
			return ;
		}
		newPassword=hex_md5($("#newPassword").val());
		confirmPassword= hex_md5($("#confirmPassword").val());
	    var code = $("#authCode").val();
		if(code.length==0){
			swal("请输入验证码");
			return ;
		}
		
		$.post("${ctx}/weixin/withdraw/modifyPassword",{newPassword:newPassword,confirmPassword:confirmPassword,code:code},function(data){
			if(data.code==0){
				swal("修改成功"); 
				setTimeout(function(){
					 location.href = "${ctx}/weixin/usercenter/main;JSESSIONID=<%=request.getSession().getId()%>";
				},2000);
			}else{
				swal(data.mes);
			}
		});
		
		
		
		
	});
	
	
	
	
}); 

function countdown() {
	time--;
	if (time <= 0) {
		getcode.data("enable", true);
		showtime.text("获取验证码");
		getcode.css("background", "#fb8044");
		getcode.css("color", "#fff");
	} else {
		showtime.text(time + "秒后可重发");
		getcode.css("color", "#a5411f");
		window.setTimeout(countdown, 1000);
	}

}


</script>
</head>
<body>
	 <div class="m_mus">
	    <div class="modify">
	        <dl>
	            <dt>验证码</dt>
	            <dd>
	            <input type="text" class="m_txt" id="authCode" placeholder="请输入验证码" name="username">
	                 <button type="button" value="获取验证码" class="m_btn" id="sendCode" style="-webkit-appearance:none;" >获取验证码</button>
	                 </dd>
	        </dl>
	        <dl>
	            <dt>输入新密码</dt>
	            <dd><input type="password" id="newPassword" maxlength="12" placeholder="请输入新密码" class="m_fy"></dd>
	        </dl>
	        <dl>
	            <dt>确认新密码</dt>
	            <dd><input type="password" id="confirmPassword" maxlength="12"  placeholder="请确认新密码" class="m_fy"></dd>
	        </dl> 
	   </div>
	   		<span>密码由6-12位数字、英文字符组成</span>
	       <p id="sub">确定</p>
	  </div>
</body>
</html>