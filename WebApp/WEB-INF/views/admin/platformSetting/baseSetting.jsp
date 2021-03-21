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
<title>基础设置</title>
<script src="${ctx}/static/js/jquery-fileupload-9.8.1/js/vendor/load-image.all.min.js"></script>
<!-- The Canvas to Blob plugin is included for image resizing functionality -->
<script src="${ctx}/static/js/jquery-fileupload-9.8.1/js/vendor/canvas-to-blob.min.js"></script>
<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
<script src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload.js"></script>
<!-- The File Upload processing plugin -->
<script src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload-process.js"></script>
<!-- The File Upload image preview & resize plugin -->
<script src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload-image.js"></script>
<script src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload-validate.js"></script>
<script src="${ctx}/static/js/baguettebox.min.js"></script>
<script src="${ctx}/static/js/input-number-change.js"></script>
<script type="text/javascript">
$(function() {
	activeNav2("8","8_1");
	
	var scrollBall = $("#scrollBall").val();
	var a = scrollBall.length;
	$("#tscrollBall").val(a+"/500");
	
	var shareNotify = $("#shareNotify").val();
	var b = shareNotify.length;
	$("#tshareNotify").val(b+"/35");
	
	$("#canfirm").click(function(){
  
		var icpNumber = $("#icpNumber").val();
		var qq = $("#qq").val();
		var picurl = $("#picurl").val();
		var picurl1 = $("#picurl1").val();
		var hotline = $("#hotline").val();
		var cooperatePhone = $("#cooperatePhone").val();
		var shareNotify = $("#shareNotify").val();
		var scrollBall = $("#scrollBall").val();
		var sysInfoId = "${sysInfo.id}";
		$.post(
				"${ctx}/admin/platformSetting/editBaseSetting",
				{icpNumber:icpNumber,qq:qq,picurl:picurl,picurl1:picurl1,hotline:hotline,
					sysInfoId:sysInfoId,cooperatePhone:cooperatePhone,shareNotify:shareNotify,scrollBall:scrollBall},
				function(data){
			if(data.code==0){
				showCallBackDialog("设置成功",function(){
					location.reload();
				})
			}
			
		})
		
	});
	$(".b_rib").click(function(){
		//location.reload();
		window.history.go(-1);
	})
	
	//上传文件按钮点击事件
	$('#a_fileupload').click(function(){
		$('#file_upload').click();
	});
	//上传文件按钮点击事件
	$('#a_fileupload1').click(function(){
		$('#file_upload1').click();
	});
	//失败处理
	$('#file_upload').bind('fileuploadprocessfail', function (e, data) {
		hideLoading();
		alert(data.files[0].error);	
	});
	//失败处理
	$('#file_upload1').bind('fileuploadprocessfail', function (e, data) {
		hideLoading();
		alert(data.files[0].error);	
	});

	//进度设置
	$('#file_upload').bind('fileuploadprogress', function (e, data) {
		 showLoading();
	}); 
	//进度设置
	$('#file_upload1').bind('fileuploadprogress', function (e, data) {
		 showLoading();
	}); 
	
	
	$('#file_upload').fileupload({
		acceptFileTypes : /(\.|\/)(jpg|jpe?g|png|bmp)$/i,
		url : '${ctx}/files/uploadimage;JSESSIONID=<%=request.getSession().getId()%>',
		dataType : 'json',
		formData : {
			type : 3
		},
	    previewMaxWidth: 160,
	    previewMaxHeight: 80,
		messages: {
	        acceptFileTypes: '文件格式错误',
	 	},
	   	done:function(e,data){
	    	if(data.result.files[0].error==null){
				$.each(data.result.files, function(index, file) {
					hideLoading();
					$("#qrcode_img").attr('src', '${ctx}'+file.url); 
					$("#picurl").val('${ctx}'+file.url);
				});
			}else{
				hideLoading();
				alert(data.files[0].error);					
			}
	    }
	});
	
	$('#file_upload1').fileupload({
		acceptFileTypes : /(\.|\/)(jpg|jpe?g|png|bmp)$/i,
		url : '${ctx}/admin/platformSetting/uploadimage;JSESSIONID=<%=request.getSession().getId()%>',
		dataType : 'json',
		formData : {
			type : 6
		},
	    previewMaxWidth: 160,
	    previewMaxHeight: 80,
		messages: {
	        acceptFileTypes: '文件格式错误',
	 	},
	   	done:function(e,data){
	    	if(data.result.files[0].error==null){
	    		$.each(data.result.files, function(index, file) {
					hideLoading();
					$("#qrcode_img1").attr('src', '${ctx}'+file.newurl); 
					$("#picurl1").val('${ctx}'+file.url);
				});
			}else{
				hideLoading();
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
        <p>平台设置</p>
        <span><a href="<c:url value='/admin/main'/>" style="margin-left:0;">首页</a>><a href="#" >平台设置</a>><a><strong>基础设置</strong></a></span>
    </div>
    </div>
		<div class="username-text animated fadeInRight">
		<form action="">
             <div class="basis">
             	<div class="basis_text01">
                	<dl>
                        <dt>ICP证书号：</dt>
                        <dd>
                        	<input type="text" class="b_shur" value="${sysInfo.icpNumber }"id ="icpNumber"  /> 
                            <span>前台页面底部可以显示 ICP 备案信息，如果网站已备案，在此输入你的授权码，它将显示在前台页面底部，如果没有请留空</span>
                        </dd>
                    </dl>
                </div>
                <div class="basis_text01">
                	<dl>
                        <dt>客服QQ账号：</dt>
                        <dd>
                        	<input type="text" class="b_shur"  value="${sysInfo.qq }" id ="qq"  />
                            <span>前台提供QQ客服接入</span>
                        </dd>
                    </dl>
                </div>
                
                
                <div class="basis_text01">
                	<dl class="b_logo">
                        <dt>官网二维码：</dt>
                        <dd>
                      <input type="file" id="file_upload"  style="display: none;" accept="image/*"/>
          			 <c:choose>
            			<c:when test="${empty sysInfo.qrCodeUrl}">
            				<img id="qrcode_img"  src="${ctx }/static/brd/img/5.png">
            			</c:when>
            			<c:otherwise>
            				<img id="qrcode_img"  src="${sysInfo.qrCodeUrl }">
            			</c:otherwise>
           		 	</c:choose>
                            <samp id ="a_fileupload">选择图片</samp>
                        </dd>
                    </dl>
                	<dl class="b_logo">
                        <dt>收徒二维码：</dt>
                        <dd>
                      <input type="file" id="file_upload1"  style="display: none;" accept="image/*"/>
          			 <c:choose>
            			<c:when test="${empty sysInfo.apprenticeQrCodeUrl}">
            				<img id="qrcode_img1"  src="${ctx }/static/brd/img/5.png">
            			</c:when>
            			<c:otherwise>
            				<img id="qrcode_img1"  src="${sysInfo.apprenticeQrCodeUrl }">
            			</c:otherwise>
           		 	</c:choose>
                            <samp id ="a_fileupload1">选择图片</samp>
                        </dd>
                    </dl>
                      <input type="hidden" name="picurl" id="picurl"  />
                      <input type="hidden" name="picurl1" id="picurl1"  />
                </div>
                
                <div class="basis_text01">
                	<dl>
                        <dt>服务热线：</dt>
                        <dd><input type="text" class="b_shur" value="${sysInfo.hotline }" id="hotline"  ></dd>
                    </dl>
                    <dl>
                        <dt>渠道合作：</dt>
                        <dd><input type="text" class="b_shur"  value="${sysInfo.cooperatePhone }" id="cooperatePhone" ></dd>
                    </dl>
                </div>
                <div class="basis_text01">
                	<dl>
                        <dt>分享词语：</dt>
                        <dd>
                        	<input type="text" class="b_shur" name="Memo1" 
                        	 
                        	<c:choose>
							<c:when test="${empty sysInfo.shareNotify}">
			       	  			value="一起来帮人贷定个小目标，比如先赚一个亿~" 
			       	  		</c:when>
			       	  		<c:otherwise>
			       	  			value="${sysInfo.shareNotify }" 
			       	  		</c:otherwise>
							</c:choose>
			                        	 
                        	 id="shareNotify"
                        	 onKeyDown="gbcount(this.form.Memo1,this.form.used1,35);" onKeyUp="gbcount(this.form.Memo1,this.form.used1,35);">
                            <!-- <font>21/35</font> -->
                            <input class="inputtext1 inputtextn" name="used1" id="tshareNotify" readonly="readonly"> 
                            <span>微信站收徒二维码提示语。</span>
                        </dd>
                    </dl>
                </div>
                <div class="basis_text01 b_line">
                	<dl>
                        <dt>滚动文字设置：</dt>
                        <dd>
                        	<textarea name="Memo" class="b_like" id="scrollBall" 
                        	onKeyDown="gbcount(this.form.Memo,this.form.used,500);" 
				 			onKeyUp="gbcount(this.form.Memo,this.form.used,500);" >${sysInfo.scrollBall }</textarea>
                           	<input class="inputtext1 inputtextn b_hui" name="used"  id="tscrollBall" readonly="readonly"> 
                        	 <span style="word-break:break-all;width:450px;height:auto" >每句话以“；”区分，前端会以“；”区分的句子段落随机滚动播放。（前端会屏蔽输入的“；”，可正常输入段落的其他标点符号）
                        </span>	
                        </dd>
                    </dl>
                    <div class="basis_text02">
                        <p id="canfirm">提交</p>
                        <p class="b_rib">返回</p>
                	</div>
                </div>
                </div>
                </form>
           <!--page-wrapper-->
		</div>    
		    <div style="height:40px;"></div>
</body>
</html>
