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
<title>星级订单设置</title>
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
<script>window.UEDITOR_HOME_URL = "${ctx}/"</script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/ueditor.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/lang/zh-cn/zh-cn.js"> </script>
<script type="text/javascript">
var detailEditor;
$(function() {
	activeNav2("5","5_1");
	detailEditor =  UE.getEditor(
			'editor1',
			{ toolbars: [["bold","italic","underline","justifyleft","justifycenter","justifyright","fontfamily","fontsize","forecolor"]],
			wordCount:true,
			maximumWords:800,
			elementPathEnabled:false,
	        autoHeightEnabled: false,
	        enableAutoSave: false
	});
	detailEditor.addListener("ready", function () {
		detailEditor.setContent($("#activityRule").html());
	});

	$("#canfirm1").click(function(){
 	 if (!check()){
			return ;
		}   
		var startOrdernum = $("#startOrdernum").val();
		var picurl = $("#picurl").val();
		var maxNum = $("#maxNum").val();
		var activityType = 0;
		var activityRule = detailEditor.getContent();
		var activityId = "${activity.id}";
		//alert(activityId);
		$.post("${ctx}/admin/activity/addActivity",{
			startOrdernum:startOrdernum,maxNum:maxNum,activityRule:activityRule,activityId:activityId,picurl:picurl,activityType:activityType},
				function(data){
			if(data.code==0){
				showCallBackDialog("设置成功",function(){
					location.reload();
				})
			}
			
		})
		
	});
	$(".b_right").click(function(){
		location.href = "${ctx}/admin/activity/starOrderList";
	})
	function check(){
		if (!checkStartOrdernum()){
			return false;
		}
		if (!checkMaxNum()){
			return false;
		}
		return true;
	}
	function checkStartOrdernum(){
		var startOrdernum =  $("#startOrdernum").val();
		if (!(/(^[0-9]\d*$)/.test(startOrdernum))){
			alert("输入的不是正整数");
			return false;
		}
		if (startOrdernum>6){
			alert("累计1年下星级订单最大值为6");
			return false;
			}
		return true;
	}
	function checkMaxNum(){
		if (maxNum==0){
			alert("不计时间下星级订单不能为空");
			return false;
			}
		var maxNum =  $("#maxNum").val();
		if (!(/(^[0-9]\d*$)/.test(maxNum))){
			alert("输入的不是整数");
			return false;
		}
		if (maxNum>6){
			alert("不计时间下星级订单最大值为6");
			return false;
			}
		return true;
	}
	//上传文件按钮点击事件
	$('#a_fileupload').click(function(){
		$('#file_upload').click();
	});
	//失败处理
	$('#file_upload').bind('fileuploadprocessfail', function (e, data) {
		hideLoading();
		alert(data.files[0].error);	
	});

	//进度设置
	$('#file_upload').bind('fileuploadprogress', function (e, data) {
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
					$("#baner_img").attr('src', '${ctx}'+file.url); 
					$("#picurl").val('${ctx}'+file.url);
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
        <p>活动管理</p>
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>>
        <a href="#" >活动管理</a>>
        <a href="#">星级订单</a>>
        <a><strong>活动设置</strong></a></span>
    </div>
    </div>
        <div class="new_emp animated fadeInRight">
            <div class="new_xinxi"><p><font style="width:100px;">活动banner</font></p></div>
            <div class="activity">
          		<input type="file" id="file_upload"  style="display: none;" accept="image/*"/>
            <c:choose>
            	<c:when test="${empty activity.picurl}">
            		<p><img id="baner_img" src="${ctx }/static/brd/img/tu.jpg"></p>
            	</c:when>
            	<c:otherwise>
            		<p><img id="baner_img" src="${activity.picurl }"></p>
            	</c:otherwise>
            </c:choose>
            	<span id ="a_fileupload">重新上传</span>
                <samp>建议尺寸：1080*420</samp>
            </div>
            <div class="new_xinxi n_top"><p><font style="width:85px;">活动规则</font></p></div>
             <form method="post" >
               <div id="activityRule" style="display: none;">${util:htmlUnescape(activity.activityRule)}</div>
           		 <div class="rule">
                 <p>活动规则说明：</p>
                  <span> <script id="editor1" name="content" type="text/plain"  style="width:640px;height:240px;"></script></span>
                 <p style="margin-top:30px;">活动设置：&nbsp;&nbsp;累计1年下星级贷&nbsp;&nbsp;<font>
                 <input type="text" class="r_shu" placeholder ="最大为6" value="${activity.maxNum }" id="maxNum">
                 </font>&nbsp;&nbsp;单或不计时间累计下&nbsp;&nbsp;
                 <input type="text" class="r_shu" placeholder ="最大为6" value="${activity.starOrdernum }"   id="startOrdernum">&nbsp;&nbsp;单可获得奖励。</p>
         		 <input type="hidden" name="picurl" id="picurl"  />
         	   <div class="rule_bao">
            	<p id="canfirm1">保存</p>
                <p class="b_right">返回</p>
            </div>
            </div>
            </form>
         <!--new_emp-->    
        </div>
</body>
</html>
</html>