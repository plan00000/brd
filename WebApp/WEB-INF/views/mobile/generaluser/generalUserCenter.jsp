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
<title>普通会员个人中心</title>
<script	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/vendor/load-image.all.min.js"></script>
<script	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/vendor/canvas-to-blob.min.js"></script>
<script	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.iframe-transport.js"></script>
<script	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload.js"></script>
<script	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload-process.js"></script>
<script	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload-image.js"></script>
<script	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload-validate.js"></script>
<link href="${ctx}/static/brd/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<script src="${ctx}/static/js/sweetalert/sweetalert.min.js"  ></script>
<script type="text/javascript">
document.title="个人中心"
$(document).ready(function(){
	$("#submit").click(function(){
		if (!check()){
			return ;
		}
 		var realname =  $("#realname").val();
		var idcard =  $("#idcard").val(); 
		$.ajax({
			type : 'post',
			url : "${ctx}/weixin/generalUserCenter/perfectInformation?realname="+realname+"&idcard="+idcard,
			success : function(res) {
				if (res.code == 1) {
					layer.open({
						content : res.mes,
						time : 3,
					});
				}else{
					location.href = "${ctx}/weixin/index/toIndex"; //进入首页
				}		
				return false;
			}
		})
	})
//检查真实姓名
function checkRealname(){
	var realname =  $("#realname").val();
	if (realname.trim().length == 0){
		layer.open({content: '请填写您的真实姓名', time: 2});
		return false;
		}
	return true;
}
//检查身份证后6位
function checkIdcard(){
	var idcard =  $("#idcard").val(); 
	if (idcard.trim().length == 0){
		layer.open({content: '请填写身份证号后6位', time: 2});
			return false;
		}else if (idcard.length != 6){
			layer.open({content: '请正确填写身份证号后6位', time: 2});
			return false;
		}
	return true;
}

//检测参数
function check(){
	if (!checkRealname()){
		return false;
	}
	if (!checkIdcard()){
		return false;
	}
	return true;
}
//检查昵称
function checkNickname(){
	var nickname = $("#nicknameVal").val(); 
	if (nickname.trim().length == 0){
		layer.open({content: '请填写昵称', time: 2});
			return false;
		}else if (nickname.length > 10){
			layer.open({content: '昵称限制10个字符以内', time: 2});
			return false;
		}
	var re = /^[\u4e00-\u9fa5a-zA-Z0-9]+$/gi
		if(!re.test(nickname)){
			alert("昵称不能存在特殊字符");
			return ;
		}
	return true;
}
//让昵称显示
$("#updatenickname").click(function(){
	 $("#nickname").css("display","block");
})
//让客服显示
$("#service").click(function(){
	 $(".kefu").css("display","block");
})

	$("#confirm").click(function(){
		if (!checkNickname()){
			return ;
		}
 		var nickname =  $("#nicknameVal").val();
		$.post("${ctx}/weixin/usercenter/personal/modifyusername;JSESSIONID=<%=request.getSession().getId()%>",{username:nickname},function(data){
			if(data.code==0){
				$('#userName').html("<dd>"+nickname+"</dd>");
				$('#nicknameVal').val(nickname);
				layer.open({content: '修改成功', time: 2});
				$("#nickname").hide();  
			}else{
				alert(data.mes);
			}
		});
		
	})
$("#cancel2").click(function(){
	 $("#nickname").css("display","none");
})
$("#cancel1").click(function(){
	 location.href = "${ctx}/weixin/generalUserCenter/main";
})
$("#cancel3").click(function(){
	$(".kefu").hide();
})



$('#userimage_input').fileupload({
	acceptFileTypes : /(\.|\/)(jpg|jpe?g|png|bmp)$/i,
	url : '${ctx}/files/uploadimage;JSESSIONID=<%=request.getSession().getId()%>',
	dataType : 'json',
	formData : {
		type : 3
	},
    previewMaxWidth: 160,
    previewMaxHeight: 80,
	messages: {
        acceptFileTypes: '文件格式错误',
 	},
   	done:function(e,data){
    	if(data.result.files[0].error==null){
			$.each(data.result.files, function(index, file) {
				hideLoading();
				$("#userimage_img").attr('src', '${ctx}'+file.url); 
				$('#userimage_url').attr("value", '${ctx}'+file.url);
				
				//上传成功后进行提交到后台
				$.post('${ctx}/weixin/usercenter/personal/modifyUserImage;JSESSIONID=<%=request.getSession().getId()%>',$('#submit_form').serialize(),function(data){
					if(data.code==1){
						alert("头像修改成功");
					}else{
						alert(data.mes);
					}
				})
			});
		}else{
			hideLoading();
			alert(data.files[0].error);					
		}
    }
});
//上传文件按钮点击事件
$('#touxiang').click(function(){
	$('#userimage_input').click();
});
//失败处理
$('#userimage_input').bind('fileuploadprocessfail', function (e, data) {
	hideLoading();
	alert(data.files[0].error);	
});

//进度设置
$('#userimage_input').bind('fileuploadprogress', function (e, data) {
	 showLoading();
}); 

$('#loginout').click(function(){
	$("#loginoutAlert").attr("class","finan");
})
$("#cancelloginout").click(function(){
	$("#loginoutAlert").attr("class","finan hidden");
})

$("#confirmloginout").click(function(){
	location.href="${ctx}/weixin/user/logout"
})

})
//检查是否有关注公众号
/* function checkWeixinUserId(){
 	var weixinUserId = ${weixinUser.id};
		$.ajax({
			type : 'post',
			url : "${ctx}/weixin/generalUserCenter/isFollow?weixinUserId="+weixinUserId,
			success : function(res) {
				if (res.code == 1) {
					$("#flag").val(true);
				}else{
					$("#qrcode").css("display","block");
					$("#flag").val(false);
				}	
			}
		})
		var flag = $('#flag').val();
		return flag;
} */

//检验是否关注后跳到成为融资经理
function becomeManager() {
/* 	 
 	if (!checkWeixinUserId()){
		return ;
	}  */
	location.href = "${ctx}/weixin/generalUserCenter/becomeManager?isManager=0";
}
//检验是否关注后跳到帮助中心
function judgment(){
/*  	if (!checkWeixinUserId()){
		return ;
	}  */
	location.href = "${ctx}/weixin/information/toHelpConter";
}
//检验是否关注后跳到成为贷款进度
function progressOfLoad() {
/*  	if (!checkWeixinUserId()){
		return ;
	}  */
	var SessionId = $("#SessionId").val(); 
	location.href = "${ctx }/weixin/loan/myloan;JSESSIONID="+SessionId;
}
//检验是否关注后跳到提现
function withdrawals(){
/*  	if (!checkWeixinUserId()){
		return ;
	}  */
	var SessionId = $("#SessionId").val(); 
	location.href = "${ctx}/weixin/withdraw/main;JSESSIONID="+SessionId;
}
function detail(){
/*  	if (!checkWeixinUserId()){
		return ;
	} */ 
	var SessionId = $("#SessionId").val(); 
	location.href = "${ctx}/weixin/withdraw/detail;JSESSIONID="+SessionId;
}
function myBankinfo(){
/*  	if (!checkWeixinUserId()){
		return ;
	}  */
	var SessionId = $("#SessionId").val(); 
	location.href = "${ctx}/weixin/bankinfo/myBankinfo;JSESSIONID="+SessionId;
}
function toModifyPassword(){
/*  	if (!checkWeixinUserId()){
		return ;
	}  */
	var SessionId = $("#SessionId").val(); 
	location.href = "${ctx}/weixin/withdraw/toModifyPassword;JSESSIONID="+SessionId;
}



</script>
</head>     
<body>
	<div class="c_ent">
        <div class="member">
           	<form id="submit_form">
				<input type="file" id="userimage_input"  style="display: none;" accept="image/*"/>
				<input type="hidden" name="headimgurl" id="userimage_url" />
				</form>
            <dl>
                <dt id="touxiang">
                	<c:if test="${not empty user.getHeadimgurl()  }">
	                <img id="userimage_img" height="32"  width="32" src="${ctx}/files/displayProThumbTemp?filePath=${user.getHeadimgurl()}&thumbWidth=120&thumbHeight=120"/> 
                	</c:if>
                	<c:if test="${empty user.getHeadimgurl() }" >
                		<img id="userimage_img" height="32" width="32" src="${ctx }/static/images/w.png"  />  
                	</c:if>
                </dt>
                <dd id="userName">${user.username }</dd>
            </dl>
            <span>账户余额（元）<font>${brokerageCanWithdraw }</font></span>
            <div class="m_ber">
                <a href="javascript:void(0)" onclick="progressOfLoad()">贷款进度</a>
                <a href="javascript:void(0)" onclick="withdrawals()">提现</a>
                <input id = "SessionId" type = "text" value="<%=request.getSession().getId()%>" hidden="hidden">
                <input id = "flag" type = "text"  hidden="hidden"  value="" >
             </div>
               <ul>
                <li><a href="javascript:void(0)" onclick="becomeManager()"><p>成为融资经理</p></a></li>
                <li  id ="updatenickname"><p>修改昵称</p></li>
               <li style="margin-top:12px;"> <a href="#"  onclick="detail()"> <p class="jind">提现明细</p></a></li>
                <li><a href="#"  onclick="myBankinfo()"><p class="jind">我的银行卡</p></a></li>
                <li style="margin-bottom:12px;"><a href="#"  onclick="toModifyPassword()"><p class="m_bert">修改提现密码</p></a></li>
                <li><p class="jind" id="service">客服热线</p></li>
                <li><p class="jind"  onclick="judgment()" >帮助中心</p></li>
                <li><p id="loginout"  class="m_bert">退出登录</p></li>
          </ul>
          <jsp:include  page="../common/footermenu.jsp?mod=my"/>
         </div>
         
         <div class="finan" id ="nickname" style="display: none" >
             <div class="nickname">
            	 <p><input id ="nicknameVal" placeholder="原来的昵称" type="text" value="${user.username }" class="n_name"></p>
                 <samp id ="cancel2">取消</samp>
                 <samp id="confirm">确定</samp>
             </div>
         </div>
         <c:if test="${subscribe==0 }">
	         <div id="qrcode" class="layer">
	             <div class="layer_er">
	                 <p>请先长按扫描二维码，关注公众号</p>
	                 <span><img src="${sysInfo.qrCodeUrl }"></span>
	             </div>
	          </div> 
         </c:if> 
          
         <div class="kefu" style="display: none">
             <p id ="cancel3">取消</p>
             <span><img src="${ctx }/static/brd-mobile/images/hrz.png"><a style="color:#3F6DCF;" href="tel:${sysInfo.hotline }">合作热线：${sysInfo.hotline }</a></span>
              <samp><img src="${ctx }/static/brd-mobile/images/iphone.png"><a style="color:#3F6DCF;" href="tel:${sysInfo.cooperatePhone }">客服热线：${sysInfo.cooperatePhone }</a></samp>
        </div>
         <div id="loginoutAlert" class="finan hidden">
             <div class="nickname" >
            	 <p style="border:none"><%-- <input type="text" value="${user.username}" id="username"  class="n_name"> --%>
            	 	确定退出？
            	 </p>
                 <samp id="cancelloginout" >取消</samp>
                 <samp id="confirmloginout">确定</samp>
             </div>
         </div>
        
          <c:if test="${isManager==1 }">
            <div class="finan">
             <div class="finan_text">
            	 <p>完善以下信息即可成为融资经理收徒赚佣金~</p>
                 <dl>
                     <dt>姓名</dt>
                     <dd><input id="realname" type="text" value="" placeholder="请输入您的姓名" class="f_shu"></dd>
                 </dl>
                 <dl>
                     <dt>身份证号</dt>
                     <dd><input  id="idcard"  type="text" value="" placeholder="身份证号后6位" class="f_shu"></dd>
                 </dl>
                 <span>为保证后续您能顺利提取所赚佣金，避免损失，请您正确填写身份信息。一旦填写无法修改。</span>
                 <samp id ="submit">确定</samp>
                 <samp id ="cancel1">取消</samp>
            	 </div>
        	 </div>
        </c:if>
        </div>
      </body>
</html>