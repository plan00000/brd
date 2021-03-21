<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${ctx }/static/js/common.js">
</script>
<script	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/vendor/load-image.all.min.js"></script>
<script	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/vendor/canvas-to-blob.min.js"></script>
<script	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload.js"></script>
<script	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload-process.js"></script>
<script	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload-image.js"></script>
<script	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload-validate.js"></script>
<script src="${ctx}/static/js/baguettebox.min.js"></script>
<title>编辑图片</title>
<script type="text/javascript">
	$(function() {
		activeNav2("7","7_2");

		baguetteBox.run('.baguetteBoxOne', {
			animation : 'fadeIn',
			captions : false,
		});
		//上传文件按钮点击事件
		$('#a_fileupload').click(function() {
			$('#file_upload').click();
		});
		//失败处理
		$('#fileupload_input').bind('fileuploadprocessfail', function(e, data) {
			alert("图片上传失败");
		});
		$("#backPage").click(function(){
			location.href="${ctx}/admin/advertisement/weixin/ad/list";			
		})
		$("#file_upload").fileupload({
			url : '${ctx}/admin/advertisement/weixin/ad/uploadimage',
			dataType : 'json',
			formData : {
				type : 3
			},
			previewMaxWidth : 160,
			previewMaxHeight : 80,
			messages : {
				acceptFileTypes : '文件格式错误',
			},
			done : function(e, data) {
				//done方法就是上传完毕的回调函数，其他回调函数可以自行查看api
				//注意result要和jquery的ajax的data参数区分，这个对象包含了整个请求信息
				//返回的数据在result.result中，假设我们服务器返回了一个json对象
				if (data.result.files[0].error == null) {
					$.each(data.result.files, function(index, file) {
						$("#ipt_photourl").val(file.url);
						$("#img_photourl").attr("src", file.url);

					});
				}
			},

		});
		//点击添加banner
		$("#ipt_addBanner").click(function() {
			var id ='${advertisement.id}'
			var picurl =$("#ipt_photourl").val();
			///var position = $("#position").val();
			var position = '${advertisement.position}';
			var isouturl = $('input[name="isouturl"]:checked ').val();
			var outurlAddress  =$("#outurlAddress").val();
			if(isouturl=="0"){
				if(picurl.length==0){
					alert("请上传图片");
					return ;
				}
			}
			
			
			$.post("${ctx}/admin/advertisement/weixin/ad/editAdvertisement",
				{id:id,picurl:picurl,position:position,isouturl:isouturl,outurlAddress:outurlAddress},function(data) {
					if(data.code==0){
						showCallBackDialog("修改成功",function() {
							location.href = "${ctx}/admin/advertisement/weixin/ad/list";
							})
					}else{
						alert(data.mes);						
					}
		  });
	   })
	});
</script>
</head>
<body>
	<div class="basic">
        <p>广告管理</p>
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a href="#" >广告管理</a>><a href="#" >微信站</a>><a><strong>编辑图片</strong></a></span>
    </div>	
	
	<div class="username-text animated fadeInRight">
            <div class="reward">
               <form id="form_submit" >
               <input type="hidden" name="picurl" value="${advertisement.picurl }" id="ipt_photourl"> 
       		   <input style="display: none" id="file_upload" type="file" accept="image/*">
               	 <dl>
                    <dt>图片位置：</dt>
                    <dd>
	                    <span>
	                    ${advertisement.position.getDes() }
	                    </span>
                    </dd>
                 </dl>
                 <dl>
                    <dt>图片上传：</dt>
                    <dd> 
                    	<p>
                    	<c:choose>
                    		<c:when test="${not empty advertisement.picurl}" >
                    			<img id="img_photourl" name="" src="${advertisement.picurl }">
                    		</c:when>
                    		<c:otherwise>
                    			<img id ="img_photourl" name="" src="${ctx }/static/brd/img/4.png" >
                    		</c:otherwise>
                    	</c:choose>
                    	</p>
                        <span id="a_fileupload" class="r_file">选择图片</span>
                        <font class="r_size">建议尺寸： 1080*420</font>
                    </dd>
                 </dl>
                 <dl>
                    <dt>是否添加链接：</dt>
                    <dd>
	                    <c:choose>
	                    	<c:when test="${advertisement.isouturl eq 'YES' }" >
	                    		<span><input type="radio" name="isouturl" checked="checked" value="1" class="r_us">是</span>
	                        	<span><input type="radio" name="isouturl" value="0" class="r_us">否</span>
	                    	</c:when>
	                    	<c:otherwise>
	                        	<span><input type="radio" name="isouturl" value="1" class="r_us">是</span>
	                        	<span><input type="radio" name="isouturl" value="0" checked="checked" class="r_us">否</span>
	                        </c:otherwise>
	                     </c:choose>
                    </dd>
                 </dl>
                 <dl>
                    <dt>链接地址：</dt>
                    <dd>
                   	<c:choose>
                   		<c:when test="${advertisement.isouturl eq 'YES' }">
                   			<input type="text" id="outurlAddress" value="${advertisement.address }"   class="r_dt">
                   		</c:when>
                   		<c:otherwise>
                   			<input type="text" id="outurlAddress" class="r_dt">
                   		</c:otherwise>
                   	</c:choose>
                        
                    </dd>
                 </dl>   
           	</form>
           		<p><samp id="ipt_addBanner" >保存</samp><samp id="backPage">返回</samp></p>
            </div>   
    </div>    
</body>
</html>