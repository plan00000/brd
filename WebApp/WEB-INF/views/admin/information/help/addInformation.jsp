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
<script src="${ctx}/static/js/input-number-change.js"></script>
<title>添加帮助</title>
<script type="text/javascript">
var detailEditor;
	$(function() {
		activeNav2("6","6_2");
		detailEditor =  UE.getEditor('container');

		$("#backPage").click(function(){
			var previewId = $("#informationId").val();
			if(previewId!=-1){
				$.post("${ctx}/admin/information/activity/delete/preview",{id:previewId},function(data){					
				});			
			}	
			location.href="${ctx}/admin/information/help/list";
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
			 
			
			 $.post("${ctx}/admin/information/help/addActivityInformation",$("#myform").serialize(),function(data){
				if(data.code==0){
					showCallBackDialog("增加成功",function() {
						location.href = "${ctx}/admin/information/help/list";
						})
				}else{
					alert(data.mes);
				}
			}) 
			 
			
			
		});
		
		$("#preview").click(function(){
			/*  $("#myIframe").attr("src","${ctx }/weixin/information/toArticleDetails/3");
			 $(".tank_text").removeClass("hidden"); */
			 /*  $("#myIframe").attr("srcdoc",detailEditor.getContentTxt()); */
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
			$(".tank_text").removeClass("hidden");
			$.post("${ctx}/admin/information/activity/preview",$("#myform").serialize(),function(data){
				$("#myIframe").attr("src","${ctx }/weixin/information/toArticleDetails/"+data.id);
				$("#informationId").val(data.id);
			}); 
		});
		
		$(".t_01").click(function(){
			$(".tank_text").addClass("hidden");
			
		});
		
	});
	
</script>
</head>
<body>
<div class="row  border-bottom">
	<div class="basic">
        <p>文章管理</p>
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a href="#" >文章管理</a>><a href="#" >帮助中心</a>><a><strong>新增帮助</strong></a></span>
    </div>
	</div>
	<div class="username-text animated fadeInRight">
            <div class="reward">
            <input type="hidden" id="informationId" name="informationId" value="-1"  />
               	 <form name="myform" id="myform" >
               	 <dl>
                    <dt>帮助标题：</dt>
                    <dd><input type="text" name="title" id="title" maxlength="20"  class="r_dt"
                    onKeyDown="gbcount(this.form.title,this.form.userd,20);"  onKeyUp="gbcount(this.form.title,this.form.userd,20);" >
		                <input style="border:none;background:#fff;margin-top:5px;margin-left:2px" name="userd" value="0/20" readonly="readonly" />
                    <font></font></dd>
                 </dl>
                 <dl>
                    <dt>正文内容：</dt>
                    
                    <dd>
                    	
						<script id="container" name="content" type="text/plain"  style="width:450px;height:300px;"></script>
                    </dd>
                 </dl>
                 <dl>
                    <dt>是否启用：</dt>
                    <dd>
                        <span><input type="radio" name="status" checked="checked" value="1" class="r_us">启用</span>
                        <span><input type="radio" name="status" value="0" class="r_us">停用</span>
                    </dd>
                 </dl>
                 <dl>
                    <dt>文章排序：</dt>
                    <dd>
                        <input type="text" name="sortId" id="sortId" value="${sortId }" class="r_zhi">
                        <font>排序值越大，显示越靠后</font>
                    </dd>
                 </dl>
                 </form>
                 <p><samp id="submit" >新增</samp><samp id="backPage" >返回</samp>
                 <samp id="preview">预览</samp></p>
            </div>    
            
            <div class="tank_text hidden">
		    	<div class="tank">
		            <div class="t_02">
		            <iframe id="myIframe" width="330px" height="470px" >
		            
		            </iframe> 
		             </div>
		        </div>
		        <div class="t_01"></div>
		    </div>   
    </div>    
			
</body>
</html>