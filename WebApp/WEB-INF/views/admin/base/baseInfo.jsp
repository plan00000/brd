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
<script src="${ctx }/static/js/common.js"></script>
<!--  The Load Image plugin is included for the preview images and image resizing functionality  -->
<script
	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/vendor/load-image.all.min.js"></script>
<!-- The Canvas to Blob plugin is included for image resizing functionality -->
<script
	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/vendor/canvas-to-blob.min.js"></script>
<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->

<script
	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload.js"></script>
<!-- The File Upload processing plugin -->
<script
	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload-process.js"></script>
<!-- The File Upload image preview & resize plugin -->
<script
	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload-image.js"></script>
<script
	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload-validate.js"></script>

<title>基本信息</title>
<script type="text/javascript">
$(function(){
	

//上传文件按钮点击事件
	$('#headimg_btn').click(function(){
		$('#headimg_input').click();
	});
	//失败处理
	$('#headimg_input').bind('fileuploadprocessfail', function (e, data) {
		/* hideLoading(); */
		alert(data.files[0].error);	
	});
	//进度设置
	$('#headimg_input').bind('fileuploadprogress', function (e, data) {
		 /* showLoading(); */
	}); 
	//上传头像
	$('#headimg_input').fileupload({
    	acceptFileTypes : /(\.|\/)(jpg|jpe?g|png|bmp)$/i,
    	url : '${ctx}/files/uploadimage;JSESSIONID=<%=request.getSession().getId()%>',
    	dataType : 'json',
    	formData : {
			type : 0
		},
    	
        previewMaxWidth: 160,
        previewMaxHeight: 80,
		messages: {
	        acceptFileTypes: '文件格式错误',
     	},
	   	done:function(e,data){
	    	if(data.result.files[0].error==null){
				$.each(data.result.files, function(index, file) {
					/* hideLoading(); */
					$("#headimg_url1").attr('src', '${ctx}'+file.newurl);
					$("#headimg_url").attr('src', '${ctx}'+file.newurl);
					/* $("#idcardReversePhoto_url").attr('src', '${ctx}'+file.url);  */
					$('#headimgPhoto').attr("value", '${ctx}'+file.url);

					//上传成功后进行提交到后台
					var data = {};
					data.headImage = "${ctx}"+file.url;
					$.post('${ctx}/admin/main/modifyHeadImage;JSESSIONID=<%=request.getSession().getId()%>',data,function(data){
						if(data.code==1){
							alert("头像修改成功");
						}else{
							alert(data.mes);
						}
					})
				});
			}else{
				/* hideLoading(); */
				alert(data.files[0].error);					
			}
	    }
    });
});
</script>
</head>
<body>
	<div class="row border-bottom">
		<div class="basic">
      	<p>基本信息</p>
          <span><a href="${ctx }/admin/main;JSESSIONID=<%=request.getSession().getId()%>" style="margin-left:0;">首页</a>><a><strong>基本信息</strong></a></span>
       </div>
     </div>
       <div class="username-text animated fadeInRight">
            <div class="basic_jib">
                <div class="basic_jib01">
                	<p>基本信息</p>
                </div>
                <div class="basic_age">
                	<div class="b_images">
                        <dl>
<!--                             <dt><img src="img/bj.png"></dt>
                            <dd>选择图片</dd> -->
                           <%--  <c:choose>
                          <c:when test="${empty userdesc.headimgurl }">
                   				<dt><img id = "headimg_url1"  src="${ctx }/static/brd/img/bj.png"></dt>
                   			</c:when>
                   			<c:otherwise>
                   				<img id="headimg_url1"
										src="${ctx}/files/displayProThumb?filePath=${userdesc.headimgurl}&thumbWidth=120&thumbHeight=120" />
                   			</c:otherwise>
                   		</c:choose>
                       <dd id="headimg_btn">选择图片</dd>
                       <input type="file" id="headimg_input"  style="display: none;" accept="image/*"/>
             					<input type="hidden" name="headimgPhoto" id="headimgPhoto" /> --%>
                        </dl>
                    </div>
                    <div class="basic_right">
                    	<dl>
                            <dt>姓名：</dt>
                            <dd>${userdesc.username}</dd>
                        </dl>
                        <dl>
                            <dt>角色：</dt>
                            
                            	 <c:choose>
			                        <c:when test="${userdesc.role.rolename eq  'Admin' }" >
			                       <dd> CEO </dd>
			                        </c:when>
			                        <c:otherwise>
			                        	<dd>${userdesc.role.rolename }</dd>
			                        </c:otherwise>
		                        </c:choose>
                        </dl>
                        <dl>
                            <dt>手机号码：</dt>
                            <dd>${userdesc.mobileno }</dd>
                        </dl>
                        <dl>
                            <dt>注册时间：</dt>
                            <dd>${util:formatNormalDate(userdesc.createdate) }</dd>
                        </dl>
                        <dl>
                            <dt>部门：</dt>
                            <dd>
                                <samp><c:if test ="${firstD != null}">${firstD.name }</c:if>&nbsp;&nbsp;<c:if test ="${secondD != null}">${secondD.name }</c:if>&nbsp;&nbsp;<c:if test ="${threeD != null}">${threeD.name }</c:if></samp>
<!--                                 <samp>销售一</samp>
                                <samp>销售一组</samp>
                                <samp>销售部</samp>
                                <samp>销售一</samp>
                                <samp>销售111组</samp>
                                <samp>销售111组</samp> -->
                            </dd>
                        </dl>
                        <dl>
                            <dt>上次登录时间：</dt>
                            <dd>${util:formatNormalDate(userdesc.lastlogindate) }</dd>
                        </dl>
                        <dl>
                            <dt>上次登录IP：</dt>
                            <dd>${userdesc.userInfoEmployee.lastloginIp }</dd>
                        </dl>
                        <dl>
                            <dt>登录地点：</dt>
                            <dd>
                                <samp>${userdesc.userInfoEmployee.lastloginCity }</samp>
                              <!--   <samp>厦门市</samp> -->
                            </dd>
                        </dl>
                    </div>
                 </div>
              </div>
        <!--username-text-->
        </div>
       
       
</body>
</html>