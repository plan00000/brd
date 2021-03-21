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
<script>
	document.title="帮人贷"
	var getcode;
	var showtime;
	$(function(){
		$("#sendCode").click(function(){
			 var $this = $(this);
			var $password = $("#ipt_password");
			var password = $password.val();
			var phone = $("#phone").val();
			if (phone.trim().length == 0){
				layer.open({content: '请填写手机号码', time: 2});
				return false;
			}
			if (phone.trim().length != 11){
				layer.open({content: '手机号码必须为11位', time: 2});
				return false;
			}
			if (!CommValidate.checkPhone(phone)){
				layer.open({content: '手机号码格式错误', time: 2});
				return false
			}
			var data = {};
			data.phone= phone;
			
			$.post("${ctx}/weixin/user/register/isPhoneRegister",{phone:phone},function(data){
					if(data.code==1){
						$.post("${ctx}/weixin/user/register/getPhoneAuthcode/register;JSESSIONID=<%=request.getSession().getId()%>",{phone:phone},function(res){
							if(res.code==1){
								$this.data("enable", false);
								getcode = $this;
								showtime=$("#sendCode");
								time = 60;
								window.setTimeout(countdown,1000);
							}else{
								alert(res.mes);								
							}		
						})	
					} else{
						alert(data.mes);
					}		
				
			});	
		
		});
		
		
		$("#btn_register").click(function(){
			var password = $("#ipt_password").val();
			var phone =  $("#phone").val();
			var nickname = $("#nickname").val();	
			/* var ischecked = $("#fuwu").is(":checked")?"check":"uncheck";
			if (ischecked=="uncheck"){
				layer.open({content: '请先阅读服务协议', time: 2});
				return false;
			} */
			if (password.length==0){
				//密码为空
				layer.open({content: '请输入密码', time: 2});
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
			
			if (nickname.length==0){
				layer.open({content:'请输入昵称',time:2 });	
				return false;	
			}	
			
			if (nickname.length>11){
				layer.open({content:'昵称最多10个字符',time:2});
				return false;
			}
			
			var recommoned = '${recommended}';
			var phoneAuthCode = $("#phoneAuthCode").val();
			if(phoneAuthCode.length==0){
				layer.open({content: '请填写验证码', time: 2});
				return false;
			}
			
			
			password = hex_md5($("#ipt_password").val());
			var dto = {
				mobileno:phone,
				phoneAuthcode:phoneAuthCode,
				password:password,
				recommoned:recommoned,				
				 nickname:nickname
			}
			
			$.post("${ctx}/weixin/sharefriend/register",dto,function(data){
				if(data.code==1){
					$("#ipt_password").val(password);
					alert("注册成功")
					setTimeout(function(){		
					$.post('${ctx}/weixin/user/login',$('#form_submit').serialize(),function(data){
						if (data.code == 1){
							//登录成功
							location.href = "${ctx}/weixin/usercenter/main;JSESSIONID=<%=request.getSession().getId()%>";
							
						}else if (data.code == 0){
							//登录失败
							alert(data.mes);
						}	
					});
					},2000);
				}else{
					alert(data.mes)
				}
				})
		});
		$("body").css("background","#f52c3c")
	});
	
	function countdown() {
		time--;
		if (time <= 0) {
			getcode.data("enable", true);
			showtime.text("获取验证码");
			getcode.css("background", "#fff");
			getcode.css("color", "red");
		} else {
			showtime.text( "重新发送"+time+"s" );
			getcode.css("color", "#fff");
			getcode.css("background", "#cdcdcd");
			window.setTimeout(countdown, 1000);
		}

	}
</script>
<title>分享</title>
</head>
<body style="background:#f52c3c;">
    <div class="erwei"><img src="${ctx }/static/brd-mobile/images/bjer.png"></div>
        <div class="e_clid">
        <form id="form_submit" >
       	 <p><input type="text" id="phone" name="username"  placeholder="请输入手机号码" class="e_shou"></p>
         <p><input type="password" id="ipt_password" name="password" placeholder="设置登录密码（不少于6位）" class="e_shou"></p>
          <p><input type="text" id="nickname" name="nickname" class="e_shou"  placeholder="请输入昵称" ></p>
         </form>
         <p>
             <input type="text" id="phoneAuthCode" placeholder="手机验证码" class="e_shou1">
           	 <button type="button" value="获取验证码" class="e_shou2" id="sendCode" style="-webkit-appearance:none;background:#fff" >获取验证码</button>
           </p>
          
          <p><input type="text"  value="推荐码:${recommended}" readOnly="readOnly" class="e_shou"></p>
          <span id="btn_register" >点击领取红包</span>
          <%-- <samp><input name="" id="fuwu" type="checkbox" checked="checked"  class="e_gou" />我已阅读并同意<a style="color: #666" href="${ctx}/weixin/user/register/toServiceAgreement">《帮人贷服务协议》</a></samp> --%>
        </div>
        <div class="about">
        	<h1>您的好友为何会向你推荐“帮人贷”？</h1>
            <img src="${ctx }/static/brd-mobile/images/zhi.png">
            <p>公司介绍</p>
            <span>帮人贷”是厦门帮人贷信息服务有限公司就互联网金融领域推出的一个O2O贷款模式综合服务平台，兼具贷款和收徒赚佣金两大功能。</span>
            <span>帮人贷”是通过线上的商业机会与线下的商业服务相结合，让互联网作为线下交易的前台，扩展线下服务渠道和覆盖面，汇集信贷精英、借款人，贷款供应信息的金融平台，旨在让社会所有阶层均能随心享受做单、赚佣借款于一体的现代金融服务。</span>
            <p>业务介绍</p>
            <span>“帮人贷”作为互联网金融线上服务平台，采用“互联网+金融+佣金”相结合的模式，给传统金融插上了翅膀，并同商家相互合作致力于给伙伴创造财富，给借款人打造最靠谱的O2O贷款平台。</span>
        	<samp></samp>
        </div>
       <%--  <div class="e_foot"><img src="${ctx }/static/brd-mobile/images/foot.png"></div>    --%>
</body>


</html>