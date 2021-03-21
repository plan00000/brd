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
<script	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/vendor/load-image.all.min.js"></script>
<script	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/vendor/canvas-to-blob.min.js"></script>
<script	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.iframe-transport.js"></script>
<script	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload.js"></script>
<script	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload-process.js"></script>
<script	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload-image.js"></script>
<script	src="${ctx}/static/js/jquery-fileupload-9.8.1/js/jquery.fileupload-validate.js"></script>
<link href="${ctx}/static/brd/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
<script src="${ctx}/static/js/sweetalert/sweetalert.min.js"  ></script>
<script>
	document.title="个人中心";
	$(function(){
		$("#nothotline").click(function(){
			alert("客服热线暂时关闭,请等待开启");	
		});
		

		
		$('#userimage_input').fileupload({
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
						hideLoading();
						$("#userimage_img").attr('src', '${ctx}'+file.url); 
						$('#userimage_url').attr("value", '${ctx}'+file.url);
						
						//上传成功后进行提交到后台
						$.post('${ctx}/weixin/usercenter/personal/modifyUserImage;JSESSIONID=<%=request.getSession().getId()%>',$('#submit_form').serialize(),function(data){
							if(data.code==1){
								alert("头像修改成功");
							}else{
								alert(data.mes);
							}
						})
					});
				}else{
					hideLoading();
					alert(data.files[0].error);					
				}
		    }
	    });
		//上传文件按钮点击事件
		$('#userimage_img').click(function(){
			$('#userimage_input').click();
		});
		//失败处理
		$('#userimage_input').bind('fileuploadprocessfail', function (e, data) {
			hideLoading();
			alert(data.files[0].error);	
		});
		
		//进度设置
		$('#userimage_input').bind('fileuploadprogress', function (e, data) {
			 showLoading();
		}); 
		
		$("#modifyusername").click(function(){
			$("#modifynicknam").attr("class","finan");
		});
		$("#confirmNickname").click(function(){
			var inputValue = $("#username").val();
			if(inputValue.length==0){
				alert("昵称不能为空");
				return ;
			}
			if(inputValue.length>10){
				alert("昵称不能大于10个字符");
				return ;
			}
			var re = /^[\u4e00-\u9fa5a-zA-Z0-9]+$/gi
			if(!re.test(inputValue)){
				alert("昵称不能存在特殊字符");
				return ;
			}
			$.post("${ctx}/weixin/usercenter/personal/modifyusername;JSESSIONID=<%=request.getSession().getId()%>",{username:inputValue},function(data){
				if(data.code==0){
					var usertype = '${user.userType}';
					if(usertype=='SALESMAN' || usertype=='CONTROLMANAGER' || usertype=='ADMIN'){
						$('#userName').html("<span>"+inputValue+"(${user.realname} )"+"</span>");
					}else{
						$('#userName').html("<span>"+inputValue+"</span>");
					}					
					$('#username').val(inputValue);
					layer.open({content: '修改成功', time: 2});
					$(".finan").attr("class","finan hidden"); 
				}else{
					alert(data.mes);
				}
			});	
		});
		
		$("#callhotline").click(function(){
			$(".kefu").attr("class","kefu");			
		});
		
		$("#cancelKefu").click(function(){
			$(".kefu").attr("class","kefu hidden");
		});
		
		$("#cancelfinan").click(function(){
			$("#modifynicknam").attr("class","finan hidden");
		})
		
		$("#loginout").click(function(){
			$("#loginoutAlert").attr("class","finan");
		});
		
		$("#cancelloginout").click(function(){
			$("#loginoutAlert").attr("class","finan hidden");
		})

		$("#confirmloginout").click(function(){
			location.href="${ctx}/weixin/user/logout"
		})
		
	});
	
</script>
</head>
<body>
	<div class="content">
			<form id="submit_form">
				<input type="file" id="userimage_input"  style="display: none;" accept="image/*"/>
				<input type="hidden" name="headimgurl" id="userimage_url" />
			</form>
        <div class="cent">
        	<c:choose>
			<c:when test="${empty user.getHeadimgurl()}">
				<p id="userimage_img" ><%-- <img id="userimage_img" src="${ctx }/static/images/w.png"  />  --%></p>      			
			</c:when>
		<c:otherwise>
			 <p><img id="userimage_img"  src="${ctx}/files/displayProThumbTemp?filePath=${user.getHeadimgurl()}&thumbWidth=120&thumbHeight=120"/> </p>     			     		
		</c:otherwise>
		</c:choose>
			<c:choose>
				<c:when test="${user.userType ne 'USER' }">
					<span id="userName" >${user.username} (${user.realname })</span>
				</c:when>
				<c:otherwise>
					<span id="userName" >${user.username}</span>
				</c:otherwise>
			</c:choose>
            <font>可提现金额(元)</font>
            <c:choose>
            <c:when test="${user.getUserType() eq 'ADMIN' || user.getUserType() eq 'CONTROLMANAGER' }" >
            	<samp>0.00</samp>
            </c:when>
            <c:otherwise>
            	<samp>${util:showMoneyWithoutUnit(user.userInfoBoth.brokerageCanWithdraw) }</samp>
            </c:otherwise>
            </c:choose>
        </div>
        <div class="cent_t hidden">
             <p>取消</p>
             <span>从手机相册选择</span>
             <samp>拍照</samp>
        </div>
        <div class="infor">
            <dl>
            <c:choose>
            <c:when test="${flage eq 'true' }" >
                <dt>累计佣金(元)</dt>
                <dd>${totalBrokerage}</dd>
            </c:when> 
            <c:otherwise>
            	<dt>累计佣金(万元)</dt>
                <dd>${totalBrokerage}</dd>
            </c:otherwise>
            </c:choose>   
            </dl>
            <dl>
            	<c:choose>
            	<c:when test="${otherFalge eq 'true' }" >            	
                	<dt>已提现金额(元)</dt>
                </c:when>
                <c:otherwise>            	
                	<dt>已提现金额(万元)</dt>
                </c:otherwise>
                </c:choose>
                <c:choose>
                <c:when test="${user.getUserType() eq 'ADMIN' || user.getUserType() eq 'CONTROLMANAGER' }" >
                	<dd>0.00</dd>
                </c:when>
                <c:otherwise>
                	<dd>${util:showPriceWithoutUnit(user.userInfoBoth.brokerageHaveWithdraw)}</dd>
                </c:otherwise>
                </c:choose>
            </dl>
            <dl>
                <dt>我的徒弟数</dt>
                <c:choose>
                <c:when test="${user.getUserType() eq 'ADMIN' || user.getUserType() eq 'CONTROLMANAGER' }" >
                	<dd>0</dd>
                </c:when>
                <c:otherwise>
                	<dd>${user.userInfoBoth.sonSum}</dd>
                </c:otherwise>
                </c:choose>
            </dl>
         </div>
         <div class="schedule">
        	 <p><a href="${ctx }/weixin/loan/myloan;JSESSIONID=<%=request.getSession().getId()%>">贷款进度</a></p>
             <p><a href="${ctx}/weixin/withdraw/main;JSESSIONID=<%=request.getSession().getId()%>">提现</a></p>
         </div>
         <div class="cent_text">
        	 <p><a href="${ctx}/weixin/brokerageDistribution/toBrokerageDistribution;JSESSIONID=<%=request.getSession().getId()%>"><span>佣金明细</span></a></p>
             <p style="margin-bottom:10px;" ><a href="${ctx }/weixin/withdraw/detail;JSESSIONID=<%=request.getSession().getId()%>"><span>提现明细</span></a></p>
             
             <p><a href="${ctx}/weixin/bankinfo/myBankinfo;JSESSIONID=<%=request.getSession().getId()%>"><span>我的银行卡</span></a></p>
            
             <p><a href="${ctx}/weixin/withdraw/toModifyPassword;JSESSIONID=<%=request.getSession().getId()%>"><span>修改提现密码</span></a></p>
             <p style="margin-bottom:10px;" ><a id="modifyusername" href="#"><span >修改昵称</span></a></p>
             <p style="margin-bottom:10px;"><a href="${ctx}/weixin/sharefriend/main/${user.getId() };JSESSIONID=<%=request.getSession().getId()%>"><span>收徒二维码</span></a></p>
           	 <c:if test="${not empty hotline}">
             	<p style="margin-bottom:0;">
             	 <a href="javascript:void(0)" id="callhotline" ><span>客服热线</span></a></p>
             	<%-- <a href="tel:${hotline}"><span>客服热线</span></a></p> --%>
             </c:if>
             <p style="margin-bottom:10px;" ><a href="${ctx}/weixin/information/toHelpConter"><span>帮助中心</span></a></p>
             <p style="margin-bottom:auto;" ><a href="#" id="loginout" ><span>退出登录</span></a>
             <p></p>
        </div>
        <div class="kefu hidden">
             <p id="cancelKefu" >取消</p>
             <span><img src="${ctx }/static/brd-mobile/images/hrz.png"><a href="tel:${parterline}" >合作热线：${parterline }</a></span>
             <samp><img src="${ctx }/static/brd-mobile/images/iphone.png"><a href="tel:${hotline }">客服热线：${hotline}</a></samp>
        </div>
        
        <div  id="modifynicknam"  class="finan hidden">
             <div class="nickname" >
            	 <p ><input type="text" value="${user.username}" id="username"  class="n_name">
            	 </p>
            	 <samp id="cancelfinan" >取消</samp>
            	 <samp id="confirmNickname" >确定</samp>
             </div>
         </div>
           <c:if test="${subscribe==0 }">
	         <div id="qrcode" class="layer">
	             <div class="layer_er">
	                 <p>请先长按扫描二维码，关注公众号</p>
	                 <span><img src="${sysInfo.qrCodeUrl }"></span>
	             </div>
	          </div> 
         </c:if>
         <div id="loginoutAlert" class="finan hidden">
             <div class="nickname" >
            	 <p style="border:none"><%-- <input type="text" value="${user.username}" id="username"  class="n_name"> --%>
            	 	确定退出？
            	 </p>
            	 <samp id="cancelloginout" >取消</samp>
                 <samp id="confirmloginout">确定</samp>
             </div>
         </div>
         
		<jsp:include  page="../common/footermenu.jsp?mod=my"/> 
        </div>
</body>
</html>