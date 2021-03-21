<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>短信通知</title>
<script type="text/javascript">
$(function() {
	activeNav2("8","8_3");
	$("#canfirm").click(function(){
 
		var notifyPhone = $("#notifyPhone").val();
		var sysInfoId = "${sysInfo.id}";
		$.post(
				"${ctx}/admin/platformSetting/editOrderMessage",
				{sysInfoId:sysInfoId,notifyPhone:notifyPhone},
				function(data){
			if(data.code==0){
				showCallBackDialog("设置成功",function(){
					location.reload();
				})
			}else{
				alert(data.mes);
			}
			
		})
		
	});
	$(".b_list").click(function(){
		//location.reload();
		window.history.go(-1);
	})
});
</script>
</head>
<body>
<div class="row border-bottom">
	<div class="basic">
        <p>平台设置</p>
        <span><a href="<c:url value='/admin/main'/>" style="margin-left:0;">首页</a>><a href="#" >平台设置</a>><a><strong>订单通知</strong></a></span>
    </div>	
    </div>	
        <div class="details animated fadeInRight">
            <div class="new_xinxi"><p><font>订单短信通知设置</font></p></div>
           	 <div class="basis_text03">
                	<dl>
                        <dt>添加发送对象手机：</dt>
                        <dd>
                        	<input type="text" id="notifyPhone" class="b_shur" value = "${sysInfo.notifyPhone }">
                            <span>注：手机号码之间用";"隔开且不能以";"结尾</span>
                        </dd>
                    </dl>
                    <p>
                        <span id = "canfirm">保存</span>
                        <span class="b_list">返回</span>
                    </p>
                </div>
	  </div>
</body>
</html>
