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
<title>修改密码</title>
<script type="text/javascript" src="${ctx }/static/js/common.js"></script>
<script type="text/javascript" src="${ctx }/static/js/md5-min.js"></script>
<script type="text/javascript">
$(function(){
	activeNav("1");
	$("#changePasswordForm").submit(function(){
		return false;
	});
	$("#ipt_save").click(function(){
		if(checkPassword()){
			var data={};
			data.newPassword = hex_md5($("#ipt_newPass").val());
			data.oldPassword = hex_md5($("#ipt_oldPass").val());
			$.post("${ctx}/admin/main/changePassword", data, function(ret) {
				if (ret.code == 1) {
					alert("修改密码成功");
				 location.href = "${ctx}/admin/main"; 
				} else if (ret.code == 0) {
					alert(ret.mes);
				}else if(ret.code == 2){
					alert(ret.mes);
					location.href ="${ctx}/brd/admin/login";
				}
				
			});
		}
	});
	$("#ipt_back").click(function(){
		location.href="${ctx}/admin/main"
	});
});
//检查密码输入是否正确
function checkPassword() {
	var oldPass= $("#ipt_oldPass").val();
	if (oldPass.trim().length == 0) {
		//密码为空
		alert("请输入旧密码");
		return false;
	}
	if (!CommValidate.isPassword(oldPass)) {
		//不符合密码格式
		alert("旧密码的格式错误");
		return false;
	}
	if (oldPass.length < 6) {
		alert("旧密码至少为6个字符");
		return false;
	}
	var newPass = $("#ipt_newPass").val();
	var confirmPass=$("#ipt_resetPass").val();
	if (newPass.trim().length == 0) {
		//密码为空
		alert("请输入新密码");
		return false;
	}
	if (!CommValidate.isPassword(newPass)) {
		//不符合密码格式
		alert("新密码的格式错误");
		return false;
	}
	if (newPass.length < 6) {
		alert("新密码至少为6个字符");
		return false;
	}
	if (newPass !== confirmPass){
		alert("你输入的新密码与确认密码不一致");
		return false;
	}
	return true;
}
</script>
</head>
<body>
	<div class="row border-bottom">
		<div class="basic">
	        	<p>修改密码</p>
	            <span><a href="${ctx }/admin/main;JSESSIONID=<%=request.getSession().getId()%>" style="margin-left:0;">首页</a>><a><strong>修改密码</strong></a></span>
	     </div>
     </div>
     <form method="get" class="form-horizontal" id="changePasswordForm">
		<input type="hidden" value="" id="ipt_oldPassword" name="oldPassword"/>
		<input type="hidden" value="" id="ipt_newPassword" name="newPassword"/>
     <div class="username-text animated fadeInRight">
          <div class="modify">
              <dl>
                  <dt>旧密码</dt>
                  <dd><input type="password" class="m_box" id="ipt_oldPass"></dd>
              </dl>
              <dl>
                  <dt>新密码</dt>
                  <dd><input type="password" class="m_box" id="ipt_newPass"></dd>
              </dl>
              <dl>
                  <dt>确认密码</dt>
                  <dd><input type="password" class="m_box" id="ipt_resetPass"></dd>
              </dl>
              <span id="ipt_save">保存</span>
              <samp id="ipt_back">取消</samp>
           </div>
    </div>
    </form>
</body>
</html>