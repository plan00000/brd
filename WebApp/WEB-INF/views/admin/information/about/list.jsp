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
<script>window.UEDITOR_HOME_URL = "${ctx}/"</script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/lang/zh-cn/zh-cn.js"> </script>
<title>关于我们</title>
<script type="text/javascript">
var detailEditor;
	$(function() {
		activeNav2("10","10_3");
		detailEditor =  UE.getEditor('container');
		detailEditor.addListener("ready", function () {
			detailEditor.setContent($("#detail_").html());
		});
		
		$("#submit").click(function(){
			var content =  detailEditor.getContentTxt();
			 $.post("${ctx}/admin/information/about/modify",$("#myform").serialize(),function(data){
				if(data.code==0){
					showCallBackDialog("修改成功",function() {
						location.href = "${ctx}/admin/information/about";
						})
				}else{
					alert(data.mes);
				}
			})  
			 
		});
		
		
		
	});
	
</script>
</head>
<body>
<div class="row  border-bottom">
	<div class="basic">
        <p>官网文章</p>
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a href="#" >官网文章</a>><a><strong>关于我们</strong></a></span>
    </div>
    </div>
	<div class="username-text animated fadeInRight">
            <div class="guide">
            	<form id="myform" >
                 <dl>
                    <dt>正文内容：</dt>
                    <dd>
                    	<div id="detail_" style="display: none;">${util:htmlUnescape(information.getContent())}</div>
						<script id="container" name="content" type="text/plain"  style="width:450px;height:300px;"></script>
                    </dd>
                 </dl>
                </form>
    			<p><span id="submit" >保存</span></p>
            </div>    
    </div>    
</body>
</html>