<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>window.UEDITOR_HOME_URL = "${ctx}/"</script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/lang/zh-cn/zh-cn.js"> </script>

<script src="${ctx}/static/js/input-number-change.js"></script>
<title>修改资讯</title>
<script type="text/javascript">
var detailEditor;
	$(function() {
		activeNav2("10","10_1");
		detailEditor =  UE.getEditor('container');
		detailEditor.addListener("ready", function () {
			detailEditor.setContent($("#detail_").html());
		});
		
		/* var summaryy = '${information.summary}';
		$("#summary").val(summaryy); */
		
		
		$("#backPage").click(function(){
			location.href="${ctx}/admin/information/pcactivity/list?sortType=${sortType}&page=${page}";
		})
		
		$("#submit").click(function(){
			var title = $("#title").val();
			if(title.length==0){
				alert("标题不能为空");
				return ;
			}
			var content =  detailEditor.getContentTxt();
			 if(content.length ==0){
				 alert("内容不能为空");
			 	return ;
			 }	
			 
			 var sortId = $("#sortId").val();
			 var re = /^[0-9]+$/gi
			 
			 if(!re.test(sortId)){
				 alert("请输入正确的文章排序");
			 	return ;
			 }
			 var summary = $("#summary").val();
			 
			 if(summary.trim().length==0){
				 alert("请输入文章摘要");
				 return ;
			 }
			 
			 
			 
			 $.post("${ctx}/admin/information/pcactivity/editActivityInformation",$("#myform").serialize(),function(data){
				if(data.code==0){
					showCallBackDialog("修改成功",function() {
						location.href = "${ctx}/admin/information/pcactivity/list?sortType=${sortType}&page=${page}";
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
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a href="#" >官网文章</a>><a href="#" >精彩资讯</a>><a><strong>修改资讯</strong></a></span>
    	</div>
	</div>
	<div class="username-text animated fadeInRight">
            <div class="reward">
            	
               	 <form name="myform" id="myform" >
               	 	<input type="hidden" name="id" value="${information.getId()}">
               	 <dl>
                    <dt>资讯标题：</dt>
                    <dd><input type="text" name="title" id="title" value="${information.title }" maxlength="22"  class="r_dt"
                    onKeyDown="gbcount(this.form.title,this.form.userd,20);"  onKeyUp="gbcount(this.form.title,this.form.userd,20);" >
		                <input style="border:none;background:#fff;margin-top:5px;margin-left:2px" name="userd" value="${fn:length(util:htmlUnescape(information.title)) }/20" readonly="readonly" />
                    <font></font></dd>
                 </dl>
                 <dl>
                    <dt>资讯摘要：</dt>
                    <dd>
                    <textarea name="summary" id="summary" maxlength="500"  class="r_zixun"
                     onKeyDown="gbcount(this.form.summary,this.form.userd2,500);"  onKeyUp="gbcount(this.form.summary,this.form.userd2,500);" >${util:htmlUnescape(information.summary) }</textarea>
                     <input style="border:none;background:#fff;margin-top:55px;margin-left:2px" name="userd2" value="${fn:length(util:htmlUnescape(information.summary))}/500" readonly="readonly" />
                    <font></font></dd>
                 </dl>
                 <dl>
                    <dt>正文内容：</dt>
                    
                    <dd>
                    	<div id="detail_" style="display: none;">${util:htmlUnescape(information.getContent())}</div>
						<script id="container" name="content" type="text/plain"  style="width:450px;height:300px;"></script>
                    </dd>
                 </dl>
                 <dl>
                    <dt>是否启用：</dt>
                    <dd>
	                    <c:if test="${information.status eq 'YES' }" >
	                    	<span><input type="radio" name="status" checked="checked" value="1" class="r_us">启用</span>
                        	<span><input type="radio" name="status" value="0" class="r_us">停用</span>
	                    </c:if>
	                    <c:if test="${information.status eq 'NO' }" >
	                    	<span><input type="radio" name="status"  value="1" class="r_us">启用</span>
                        	<span><input type="radio" name="status" checked="checked" value="0" class="r_us">停用</span>
	                    </c:if>
                        
                    </dd>
                 </dl>
                 <dl>
                    <dt>文章排序：</dt>
                    <dd>
                        <input type="text" name="sortId" id="sortId" value="${information.sortid }" class="r_zhi">
                        <font>排序值越大，显示越靠后</font>
                    </dd>
                 </dl>
                 </form>
                 <p><samp id="submit" >修改</samp><samp id="backPage" >返回</samp></p>
            </div>    
    </div>    

</body>
</html>