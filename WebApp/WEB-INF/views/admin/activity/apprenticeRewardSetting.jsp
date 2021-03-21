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
<title>收徒奖励设置</title>
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
	activeNav2("5","5_2");
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
		var is1 = "${is1}";
		var is2 = "${is2}";
		var is4 = "${is4}";
 		if (!check()){
			return ;
		}  
  	    obj = document.getElementsByName("userType");
  	    userType_val = [];
  	    for(k in obj){
  	        if(obj[k].checked)
  	            userType_val.push(obj[k].value);
  	    }
  	    var activityObject = userType_val;
  		var isa1 = $.inArray("1", activityObject);  
  		var isa2 = $.inArray("2", activityObject);  
  		var isa4 = $.inArray("4", activityObject);  
  	    if((is1=="true")&&(isa1<0)){
  	    	showCallBackDialog("确认取消该活动对象？一旦取消，该活动对象之前的收徒数将清零。",function(){
  	    		 sub(activityObject);	
  	    	})	
  	    }else if((is2=="true")&&(isa2<0)){
  	    	showCallBackDialog("确认取消该活动对象？一旦取消，该活动对象之前的收徒数将清零。",function(){
 	    		 sub(activityObject);	
 	    	})		
  	    }else if((is4=="true")&&(isa4<0)){
  	    	showCallBackDialog("确认取消该活动对象？一旦取消，该活动对象之前的收徒数将清零。",function(){
 	    		 sub(activityObject);	
 	    	})		
  	    }else{
  	    	sub(activityObject);
  	    }
	});
	
  	function sub(activityObject){
		var bounsAmount = $("#bounsAmount").val();
		var maxNum = $("#maxNum").val();
		var picurl = $("#picurl").val();
		var activityType = 1;
		var activityRule = detailEditor.getContent();
		var activityId = "${activity.id}";
		var activityObjectStr = "${activityObject}";
		
		$.post("${ctx}/admin/activity/addActivity",
				{bounsAmount:bounsAmount,maxNum:maxNum,activityRule:activityRule,activityObjectStr:activityObjectStr,
			activityId:activityId,picurl:picurl,activityObject:activityObject,activityType:activityType},
				function(data){
			if(data.code==0){
				showCallBackDialog("设置成功",function(){
					location.reload();
				})
			}else{
				alert(data.mes);
			}
		})
  	}	
	
	
	$(".b_right").click(function(){
		location.href = "${ctx}/admin/activity/apprenticeReward";
	})
	function check(){
		if (!checkBounsAmount()){
			return false;
		}
		if (!checkMaxNum()){
			return false;
		}
		return true;
	}
	function checkBounsAmount(){
		var bounsAmount =  $("#bounsAmount").val();
		if (bounsAmount<1&& bounsAmount.length!=0){
			alert("人可获得最小值是1");
			return false;
			}
		return true;
	}
	function checkMaxNum(){
		var maxNum =  $("#maxNum").val();
		if (maxNum==0){
			alert("累计收徒不能为空");
			return false;
			}
		if (!(/(^[0-9]\d*$)/.test(maxNum))){
			alert("输入的不是正整数");
			return false;
		}
		if (maxNum>12){
			alert("累计收徒最大值为12");
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
        <a href="#">收徒奖励</a>>
<%--         <a href="<c:url value='/admin/activity/apprenticeReward'/>"target="_blank">收徒奖励</a>> --%>
        <a><strong>活动设置</strong></a></span>
    </div>
    </div>
        <div class="new_emp animated fadeInRight">
                  <div class="row">
			<div class="col-lg-12">
				<div class="ibox float-e-margins">
				<div class="table-responsive">
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
                 <span>
				   <script id="editor1" name="content" type="text/plain"  style="width:640px;height:240px;"></script>
				  </span>
         		<p style="margin-top:30px; width: 100%;" >活动设置：&nbsp;累计收徒&nbsp;<font>
         		<input type="text" class="r_shu"  placeholder ="最大为12" value="${activity.maxNum }" id="maxNum" ></font>&nbsp;人可获得&nbsp;
         		<input type="text" class="r_shu"  placeholder ="最小为1" value="${activity.bonusAmount }" id="bounsAmount" >&nbsp;元奖励。完成后自动发放至用户账户。</p>
                 <input type="hidden" name="picurl" id="picurl"  />
                 <ul>
                     <li>活动对象：</li>
                     <li><input type="checkbox" name="userType"  <c:if test="${is1 }"> checked="checked"</c:if> class="r_lt" value="1">融资经理</li>
                     <li><input type="checkbox" name="userType"  <c:if test="${is2 }"> checked="checked"</c:if> class="r_lt" value="2">商家</li>
                     <li><input type="checkbox" name="userType"  <c:if test="${is4 }"> checked="checked"</c:if> class="r_lt" value="4">业务员</li>
                 </ul>
         	   <div class="rule_bao">
            	<p id="canfirm1">保存</p>
                <p class="b_right">返回</p>
            </div>
            </div>
            </form>
         <!--new_emp-->    
        </div>
        </div>
        </div>
        </div>
        </div>
</body>
</html>
</html>