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
<title>SEO设置</title>
<script type="text/javascript">
	$(function(){
		activeNav2("8","8_6");
		
		
		$("#submit").click(function(){
			var seoTitle =$("#seoTitle").val();
			var seoKeyword = $("#seoKeyword").val();			
			var seoDescribe = $("#seoDescribe").val();
			console.log(seoTitle);
			if(seoTitle.trim().length==0){
				alert("请输入标题");
				return ;
			}			
			if(seoKeyword.trim().length==0){
				alert("请输入关键词");
				return ;
			}	
			if(seoDescribe.trim().length==0){
				alert("请输入描述");
				return ;
			}	
			
			$.post("${ctx}/admin/platformSetting/seoSetting/set",$("#myform").serialize(),function(data){
				if(data.code==0){
					showCallBackDialog("保存成功",function() {
						//location.href = "${ctx}/admin/platformSetting/seoSetting";
						window.location.reload();
					})
				}
			})
			
		});
		
		
		//jq 结束
	});
</script>
</head>
<body>
	<div class="row border-bottom">
	<div class="basic">
        <p>平台设置</p>
        <span><a href="<c:url value='/admin/main'/>" style="margin-left:0;">首页</a>><a href="#" >平台设置</a>><a><strong>SEO设置</strong></a></span>
    </div>
    </div>
	<div class="details animated fadeInRight">
          <div class="seo">
          	<form name="myform" id="myform"  >
	            <dl>
	              <dt>标题：</dt>
	              <dd>
	                  <input type="text" class="s_in" name="seoTitle" id="seoTitle" value="${sysInfo.seoTitle }" >
	                  <span>设置网页标题可便于搜索引擎收录该页面</span>
	              </dd>
	            </dl>
	            <dl>
	              <dt>关键词：</dt>
	              <dd>
	                  <input type="text" class="s_in" name="seoKeyword" id="seoKeyword" value="${sysInfo.seoKeyword }" >
	                  <span>设置页面关键词可便于搜索引擎收录该页面，添加多个关键词时使用英文逗号,隔开</span>
	              </dd>
	            </dl>
	            <dl>
	              <dt>描述：</dt>
	              <dd>
	                  <input type="text" class="s_in" name="seoDescribe" id="seoDescribe" value="${sysInfo.seoDescribe }" >
	                  <span>设置页面描述可便于搜索引擎收录页面中的重要内容</span>
	              </dd>
	            </dl>
            </form>
            <p id="submit">保存</p>
          </div>
			</div>
</body>
</html>