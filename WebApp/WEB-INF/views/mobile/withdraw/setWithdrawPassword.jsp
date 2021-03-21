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

<script>
var getcode;
$(function(){
	document.title="设置提现密码";
	var tel = '${phone}';
	if(tel.length!=11){
		swal({
			title:"您的号码不是正确的手机号码不需要设置提现密码",
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
		var realpass = $("#withdrawPassword").val().trim();
	    var password=hex_md5($("#withdrawPassword").val()).trim();
		var code = $("#authCode").val().trim();
		 if($("#withdrawPassword").val().length==0){
			 swal({
				 title:"提现密码不能为空",
			 	 timer:2000,
			 	 showConfirmButton:false
			 })
			 return ;
		 }
		 if(realpass.length<6 || realpass.length>12 ){
			 swal({
				 title:"请输入6-12位提现密码",
				 timer:2000,
				 showConfirmButton:false
			 })
			 return;
		 }
		 
		 if(code.length!=6){
			 swal({
				 title:"请输入6位的验证码",
			 	 timer:2000,
			 	 showConfirmButton:false
			 })
			 return ;
		 }	
		 if(code.length==0){ 
			swal({
				title:"验证码不能为空",
				timer:2000,
				showConfirmButton:false
			})
			return ;
		}	
		
		$.post("${ctx}/weixin/withdraw/setPassword",{password:password,code:code},function(data){
			if(data.code==0){
				swal("设置成功");
				setTimeout(function(){
					location.href = "${ctx}/weixin/withdraw/main;JSESSIONID=<%=request.getSession().getId()%>";
				},2000);
			}else{
				swal(data.mes);
			}
		})
		
		
		
		
		
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
	<div class="content">
	     <div class="with">
            <dl>
                <dt></dt>
                <dd>绑定的手机号码为：${phone }<br/>请先设置提现密码，再进行银行卡绑定</dd>
            </dl>
        </div>
        <div class="w_yan">
            <dl>
                <dt>验证码</dt>
                <dd>
                <input type="text" id="authCode" class="w_txt" placeholder="	请输入验证码">        
                <button type="button" value="获取验证码" class="w_btn" style = "color:#a5411f" id="sendCode" >获取验证码</button>
                </dd>
            </dl>
            <dl>
                <dt>提现密码</dt>
                <dd>
                <input type="password" id="withdrawPassword"  class="w_txt1" placeholder="	请输入取现密码">
            </dl>
            <p id="sub" >提交</p>
        </div> 
        </div>
</body>
</html>