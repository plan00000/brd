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
<title>服务协议</title>
<script>window.UEDITOR_HOME_URL = "${ctx}/"</script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/lang/zh-cn/zh-cn.js"> </script>
<script type="text/javascript">
var detailEditor;
$(function(){
	activeNav2("8","8_4");
		detailEditor =  UE.getEditor('editor1');
		detailEditor.addListener("ready", function () {
			detailEditor.setContent($("#detail_").html());
		});
		$("#tosubmit").click(function(){
			$.post("${ctx}/admin/platformSetting/addServiceAgreement;JSESSIONID=<%=request.getSession().getId()%>",$("#form_submit").serialize(),
				function(data){
					if(data == 0){
						alert("修改失败");
						return;
					}else if(data == 2){
						alert("修改失败，请输入内容。")
					}else{
						showCallBackDialog("修改成功",function(){
							location.reload();
						});
					}
				});
	});
});
</script>
</head>
<body>
<div class="row border-bottom">
	<div class="basic">
        <p>平台设置</p>
        <span><a href="<c:url value='/admin/main'/>" style="margin-left:0;">首页</a>><a href="#" >平台设置</a>><a><strong>服务协议</strong></a></span>
    </div>
    </div>
        <div class="username-text animated fadeInRight">
        <div id="detail_" style="display: none;">${util:htmlUnescape(information.content)}</div>
        	<form id="form_submit" class="form-horizontal" method="post">
        	<input type="hidden" name="id" value="${information.id}">
            <div class="guide">
               	 <dl>
                    <dt>帮人贷服务协议：</dt>
                    <dd>
                   		 <script id="editor1" name="content" type="text/plain"  style="width:545px;height:260px;"></script>
					</dd>
                 </dl>
                <p><span id="tosubmit">保存</span></p>
            </div>
            </form>
       <!--username-text-->     
    </div>
</body>
</html>
