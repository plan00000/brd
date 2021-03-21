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
<title>推荐注册</title>
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
<script>window.UEDITOR_HOME_URL = "${ctx}/"</script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/ueditor.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctx}/static/js/ueditor/lang/zh-cn/zh-cn.js"> </script>
<script type="text/javascript">
var detailEditor;
$(function() {
	activeNav2("5","5_3");
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
	
	var activityCopy = $("#activityCopy").val();
	var b = activityCopy.length;
	$("#tcopy").val(b+"/80");
	
	$("#canfirm1").click(function(){
   		if (!check()){
			return ;
		} 
  	    obj = document.getElementsByName("userType");
  	    userType_val = [];
  	    for(k in obj){
  	        if(obj[k].checked)
  	            userType_val.push(obj[k].value);
  	    }
  	 	var activitySet= $('input:radio:checked').val();
  		var bounsAmount = $("#bounsAmount").val();
		var maxNum = $("#maxNum").val();
		var picurl = $("#picurl").val();
		var activityType = 2;
		var activityCopy = $("#activityCopy").val();
		var activityRule = detailEditor.getContent();
		var activityId = "${activity.id}";
		var activityObject = userType_val;
		$.post("${ctx}/admin/activity/addActivity",
				{bounsAmount:bounsAmount,maxNum:maxNum,activityRule:activityRule,activitySet:activitySet,
			activityId:activityId,picurl:picurl,activityCopy:activityCopy,activityType:activityType,activityObject:activityObject},
				function(data){
			if(data.code==0){
				showCallBackDialog("设置成功",function(){
					location.reload();
				})
			}
			
		})
		
	});
	$(".b_right").click(function(){
		window.history.go(-1);
	})
	function check(){
		if (!checkBounsAmount()){
			return false;
		}
		return true;
	}
	function checkBounsAmount(){
		var bounsAmount =  $("#bounsAmount").val();
		if (bounsAmount<1&& bounsAmount.length!=0){
			alert("活动奖励最小值是1");
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
	
	$(".radio>input").each(function(index){ 
		$(this).click(function(){ 
		$(".inf_box>.inf_con:eq("+index+")").addClass("dis_block").siblings().removeClass("dis_block"); 
		});
		});
	
});


</script>
</head>
<body>
<div class="row border-bottom">
	<div class="basic">
        <p>活动管理</p>
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a href="#" >活动管理</a>><a><strong>推荐注册</strong></a></span>
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
            		<%-- <p><img id="baner_img"  src="${ctx}/files/displayProThumbTemp?filePath=${activity.picurl } "></p> --%>
            	</c:otherwise>
            </c:choose>
            	<span id ="a_fileupload">重新上传</span>
                <samp>建议尺寸：1080*420</samp>
            </div>
            <div class="new_xinxi n_top"><p><font style="width:85px;">活动banner</font></p></div>
            <form method="post" >
            <div id="activityRule" style="display: none;">${util:htmlUnescape(activity.activityRule)}</div>
            <div class="rule">
            	<p>活动文案：</p>
                <textarea name="Memo1"  class="r_rank r_wenan" id="activityCopy" 
				  onKeyDown="gbcount(this.form.Memo1,this.form.used1,80);" onKeyUp="gbcount(this.form.Memo1,this.form.used1,80);"
				  >${activity.activityCopy }</textarea>
               	<input class="inputtext inputtextn" name="used1"  id="tcopy" readonly="readonly"> 
                 <p>活动规则说明：</p>
                  <span> <script id="editor1" name="content" type="text/plain"  style="width:640px;height:240px;"></script></span>
                 <ul>
                     <li>活动设置：</li>
                     <li>
                     <input type="radio" name="activitySet1" onclick="radioShow();"<c:if test="${activitySetInt==1 }"> checked="checked"</c:if> value="1" class="r_lt">注册就送红包</li>
                     <li style="margin-left:60px;">
                     <input type="radio" name="activitySet1" onclick="radioShow();" value="2" <c:if test="${activitySetInt==2 }"> checked="checked"</c:if> class="r_lt">填写推荐码注册送红包</li>
                 </ul>
                 <p>奖励设置：&nbsp;&nbsp; &nbsp;&nbsp; <font>

                 <input type="text" class="r_shu"  placeholder ="最小为1" value="${activity.bonusAmount }" id="bounsAmount">&nbsp;&nbsp;元奖励。自动发放至用户账户。</p>
                <input type="hidden" name="picurl" id="picurl"  />
                 <ul>
                     <li>活动对象：</li>
                     <li><input type="checkbox" name="userType" <c:if test="${is0 }"> checked="checked"</c:if> class="r_lt" value="0">普通会员</li>
                     <li><input type="checkbox" name="userType" <c:if test="${is1 }"> checked="checked"</c:if> class="r_lt" value="1">融资经理</li>
                     <li><input type="checkbox" name="userType" <c:if test="${is2 }"> checked="checked"</c:if> class="r_lt" value="2">商家</li>
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
    <!--page-wrapper-->
</body>
</html>