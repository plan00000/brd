<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
<%@ taglib prefix="shiro" uri="http://www.springside.org.cn/tags/shiro"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${ctx}/static/js/input-number-change.js"></script>
<title>添加链接</title>
<script type="text/javascript">
	$(function(){
		activeNav2("8","8_7");
			
		var linkUrlO = '${friendshipLink.linkUrl}';
		$("#linkUrl").val(linkUrlO);
		
		
		$("#submit").click(function(){
			var title = $("#title").val();
			var linkUrl = $("#linkUrl").val();
			var friendshipId = '${friendshipLink.id}';
			if(title.trim().length==0){
				alert("请输入链接名称");
				return ;
			}			
			if(linkUrl.trim().length==0){
				alert("请输入链接地址");
				return;
			}
			
			if(linkUrl.trim().length>255) {
				alert("链接长度不能超过255")
				return ;
			}
			
			$.post("${ctx}/admin/platformSetting/friendshiplink/editFriendship",{friendshipId:friendshipId,title:title,linkUrl:linkUrl},function(data){
				if(data.code==0){
					showCallBackDialog(data.mes,function(){
						location.href="${ctx}/admin/platformSetting/friendshiplink/list?page=${page}";
	      			});
				}else {
					alert(data.mes)
				}
			});
		});
		
		$("#backPage").click(function(){
			location.href="${ctx}/admin/platformSetting/friendshiplink/list?page=${page}";
		}); 
		
		
		//jq结束
	});
</script>
</head>
<body>
	<div class="basic">
        <p>平台设置</p>
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a>平台设置</a>><a>友情链接</a>><a><strong>添加链接</strong></a></span>
    </div>	
 <div class="details animated fadeInRight">
	  	<div class="new_xinxi"><p><font>新增链接</font></p></div>
	    	<form id="myform" >
		    	<div class="connection">
			        	<dl>
			                <dt>链接名称：</dt>
			                <dd><input type="text" maxlength="10" value="${friendshipLink.title }" id="title" name="title" class="c_net" 
			                onKeyDown="gbcount(this.form.title,this.form.userd2,10);"  onKeyUp="gbcount(this.form.title,this.form.userd2,10);" >
			                   	<input style="margin-left:5px;width: 40px;height:30px;border:none;background:#fff;" name="userd2" value="${fn:length(util:htmlUnescape(friendshipLink.title))}/10" readonly="readonly" /></dd>
			            </dl>
			            <dl>
			                <dt>链接地址：</dt>
			                <dd><textarea id="linkUrl" name="linkUrl" class="c_nect"></textarea></dd>
	
			            </dl>
		            
		           <p style="cursor:pointer" id="submit" >提交</p>
		           <p style="cursor:pointer" id="backPage" >返回</p>
		       </div>
	       </form>
	</div>
</body>
</html>