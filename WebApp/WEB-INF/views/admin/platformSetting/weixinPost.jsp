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
<title>微信推送</title>
<script type="text/javascript">
$(function() {
	activeNav2("8","8_5");
	$("#back").click(function(){
		window.history.go(-1);
	})
	
	$("#save").click(function(){
/*  		if (!check()){
			return ;
		}   */ 
			$.post(
				"${ctx}/admin/weixinPost/editWeixinPost",
				$("#form_weixinPost").serialize(),function(res){		
					//"${ctx}/admin/weixinPost/editWeixinPost",
				if(res.code==0){
					showCallBackDialog("设置成功",function(){
						location.reload();
					})
				}else{
					alert(res.mes);
				}
				
			})
	});
});
/* function check(){
	if (!checkT0()){
		return false;
	}
	if (!checkT1()){
		return false;
	}
	if (!checkT2()){
		return false;
	}
	if (!checkT3()){
		return false;
	}
	if (!checkT4()){
		return false;
	}
	if (!checkT5()){
		return false;
	}
	if (!checkT6()){
		return false;
	}
	return true;
}

function checkT0(){
	var templateId0 = $("#templateId0").val().trim();
	if(templateId0.length == 0){
		alert("贷款成功提交通知模板ID不能为空");
		return false;
	}
	return true;
}
function checkT1(){
	var templateId1 = $("#templateId1").val().trim();
	if(templateId1.length == 0){
		alert("贷款审核成功通知模板ID不能为空");
		return false;
	}
	return true;
}
function checkT2(){
	var templateId2 = $("#templateId2").val().trim();
	if(templateId2.length == 0){
		alert("贷款审核失败通知模板ID不能为空");
		return false;
	}
	return true;
}
function checkT3(){
	var templateId3 = $("#templateId3").val().trim();
	if(templateId3.length == 0){
		alert("贷款放款成功通知模板ID不能为空");
		return false;
	}
	return true;
}
function checkT4(){
	var templateId4 = $("#templateId4").val().trim();
	if(templateId4.length == 0){
		alert("佣金生成通知模板ID不能为空");
		return false;
	}
	return true;
}
function checkT5(){
	var templateId5 = $("#templateId5").val().trim();
	if(templateId5.length == 0){
		alert("佣金放款通知模板ID不能为空");
		return false;
	}
	return true;
}
function checkT6(){
	var templateId6 = $("#templateId6").val().trim();
	if(templateId6.length == 0){
		alert("提现到账通知模板ID不能为空");
		return false;
	}
	return true;
}
 */
</script>
</head>
<body>
<div class="row border-bottom">
	<div class="basic">
        <p>平台设置</p>
        <span><a href="<c:url value='/admin/main'/>" style="margin-left:0;">首页</a>><a href="#" >平台设置</a>><a><strong>微信推送</strong></a></span>
    </div>
    </div>
		 <div class="details animated fadeInRight">
		<div class="new_xinxi"><p><font>微信通知设置</font></p></div>
		<form id="form_weixinPost">
           	<div class="push">
           		<input type="hidden" name ="sysInfoId" value="${sysInfo.id }">
                	<div class="push_01">
                    	<p>贷款成功提交通知</p>
                    	<dl>
                            <dt>通知状态：</dt>
                            <dd>
                            	<c:if test="${weixinPost0.state.ordinal() eq 0 || empty weixinPost0.state }" >
	                                <span><input type="radio" class="d_radus" name="state0" value="0" checked="checked">开</span>
	                                <span><input type="radio" class="d_radus" name="state0" value="1">关</span> 
	                             </c:if>
                            	<c:if test="${weixinPost0.state.ordinal() eq 1 }" >
	                                <span><input type="radio" class="d_radus" name="state0" value="0">开</span>
	                                <span><input type="radio" class="d_radus" name="state0" value="1" checked="checked">关</span> 
	                             </c:if>
                            </dd>
                        </dl>
                        <dl>
                            <dt>模板ID：</dt>
                            <dd><input id="templateId0" name="templateId0" type="text" style="width: 400px;" class="d_inpt" value="${weixinPost0.templateId }" ></dd>
                        </dl>
                        <dl>
                            <dt>通知首段：</dt>
                            <dd><input id="first0" name="first0" type="text" class="d_inpt" value="${weixinPost0.first }" readonly= "true"></dd>
                        </dl>
                        <dl>
                            <dt>通知尾段：</dt>
                            <dd><input id="remark0" name="remark0" type="text" class="d_inpt" value="${weixinPost0.remark }" readonly= "true"></dd>
                        </dl>
                     </div>
                	<div class="push_01">
                    	<p>贷款审核成功通知</p>
                    	<dl>
                            <dt>通知状态：</dt>
                            <dd>
                            	<c:if test="${weixinPost1.state.ordinal() eq 0 || empty weixinPost1.state }" >
	                                <span><input type="radio" class="d_radus" name="state1" value="0" checked="checked">开</span>
	                                <span><input type="radio" class="d_radus" name="state1" value="1">关</span> 
	                             </c:if>
                            	<c:if test="${weixinPost1.state.ordinal() eq 1 }" >
	                                <span><input type="radio" class="d_radus" name="state1" value="0">开</span>
	                                <span><input type="radio" class="d_radus" name="state1" value="1" checked="checked">关</span> 
	                             </c:if>
                            </dd>
                        </dl>
                        <dl>
                            <dt>模板ID：</dt>
                            <dd><input id="templateId1" name="templateId1" type="text" style="width: 400px;" class="d_inpt" value="${weixinPost1.templateId }" ></dd>
                        </dl>
                        <dl>
                            <dt>通知首段：</dt>
                            <dd><input id="first1" name="first1" type="text" class="d_inpt" value="${weixinPost1.first }" readonly= "true"></dd>
                        </dl>
                        <dl>
                            <dt>通知尾段：</dt>
                            <dd><input id="remark1" name="remark1" type="text" class="d_inpt" value="${weixinPost1.remark }" readonly= "true"></dd>
                        </dl>
                     </div>
                	<div class="push_01">
                    	<p>贷款审核失败通知</p>
                    	<dl>
                            <dt>通知状态：</dt>
                            <dd>
                            	<c:if test="${weixinPost2.state.ordinal() eq 0 || empty weixinPost2.state }" >
	                                <span><input type="radio" class="d_radus" name="state2" value="0" checked="checked">开</span>
	                                <span><input type="radio" class="d_radus" name="state2" value="1">关</span> 
	                             </c:if>
                            	<c:if test="${weixinPost2.state.ordinal() eq 1 }" >
	                                <span><input type="radio" class="d_radus" name="state2" value="0">开</span>
	                                <span><input type="radio" class="d_radus" name="state2" value="1" checked="checked">关</span> 
	                             </c:if>
                            </dd>
                        </dl>
                        <dl>
                            <dt>模板ID：</dt>
                            <dd><input id="templateId2" name="templateId2" type="text" style="width: 400px;" class="d_inpt" value="${weixinPost2.templateId }" ></dd>
                        </dl>
                        <dl>
                            <dt>通知首段：</dt>
                            <dd><input id="first2" name="first2" type="text" class="d_inpt" value="${weixinPost2.first }" readonly= "true"></dd>
                        </dl>
                        <dl>
                            <dt>通知尾段：</dt>
                            <dd><input id="remark2" name="remark2" type="text" class="d_inpt" value="${weixinPost2.remark }" readonly= "true"></dd>
                        </dl>
                     </div>
                	<div class="push_01">
                    	<p>贷款放款成功通知</p>
                    	<dl>
                            <dt>通知状态：</dt>
                            <dd>
                            	<c:if test="${weixinPost3.state.ordinal() eq 0 || empty weixinPost3.state }" >
	                                <span><input type="radio" class="d_radus" name="state3" value="0" checked="checked">开</span>
	                                <span><input type="radio" class="d_radus" name="state3" value="1">关</span> 
	                             </c:if>
                            	<c:if test="${weixinPost3.state.ordinal() eq 1 }" >
	                                <span><input type="radio" class="d_radus" name="state3" value="0">开</span>
	                                <span><input type="radio" class="d_radus" name="state3" value="1" checked="checked">关</span> 
	                             </c:if>
                            </dd>
                        </dl>
                        <dl>
                            <dt>模板ID：</dt>
                            <dd><input id="templateId3" name="templateId3" type="text" style="width: 400px;" class="d_inpt" value="${weixinPost3.templateId }" ></dd>
                        </dl>
                        <dl>
                            <dt>通知首段：</dt>
                            <dd><input id="first3" name="first3" type="text" class="d_inpt" value="${weixinPost3.first }" readonly= "true"></dd>
                        </dl>
                        <dl>
                            <dt>通知尾段：</dt>
                            <dd><input id="remark3" name="remark3" type="text" class="d_inpt" value="${weixinPost3.remark }" readonly= "true"></dd>
                        </dl>
                     </div>
                	<div class="push_01">
                    	<p>佣金生成通知</p>
                    	<dl>
                            <dt>通知状态：</dt>
                            <dd>
                            	<c:if test="${weixinPost4.state.ordinal() eq 0 || empty weixinPost4.state }" >
	                                <span><input type="radio" class="d_radus" name="state4" value="0" checked="checked">开</span>
	                                <span><input type="radio" class="d_radus" name="state4" value="1">关</span> 
	                             </c:if>
                            	<c:if test="${weixinPost4.state.ordinal() eq 1 }" >
	                                <span><input type="radio" class="d_radus" name="state4" value="0">开</span>
	                                <span><input type="radio" class="d_radus" name="state4" value="1" checked="checked">关</span> 
	                             </c:if>
                            </dd>
                        </dl>
                        <dl>
                            <dt>模板ID：</dt>
                            <dd><input id="templateId4" name="templateId4" type="text" style="width: 400px;" class="d_inpt" value="${weixinPost4.templateId }" ></dd>
                        </dl>
                        <dl>
                            <dt>通知首段：</dt>
                            <dd><input id="first4" name="first4" type="text" class="d_inpt" value="${weixinPost4.first }" readonly= "true"></dd>
                        </dl>
                        <dl>
                            <dt>通知尾段：</dt>
                            <dd><input id="remark4" name="remark4" type="text" class="d_inpt" value="${weixinPost4.remark }" readonly= "true"></dd>
                        </dl>
                     </div>
                	<div class="push_01">
                    	<p>佣金放款通知</p>
                    	<dl>
                            <dt>通知状态：</dt>
                            <dd>
                            	<c:if test="${weixinPost5.state.ordinal() eq 0 || empty weixinPost5.state }" >
	                                <span><input type="radio" class="d_radus" name="state5" value="0" checked="checked">开</span>
	                                <span><input type="radio" class="d_radus" name="state5" value="1">关</span> 
	                             </c:if>
                            	<c:if test="${weixinPost5.state.ordinal() eq 1 }" >
	                                <span><input type="radio" class="d_radus" name="state5" value="0">开</span>
	                                <span><input type="radio" class="d_radus" name="state5" value="1" checked="checked">关</span> 
	                             </c:if>
                            </dd>
                        </dl>
                        <dl>
                            <dt>模板ID：</dt>
                            <dd><input id="templateId5" name="templateId5" type="text" style="width: 400px;" class="d_inpt" value="${weixinPost5.templateId }" ></dd>
                        </dl>
                        <dl>
                            <dt>通知首段：</dt>
                            <dd><input id="first5" name="first5" type="text" class="d_inpt" value="${weixinPost5.first }" readonly= "true"></dd>
                        </dl>
                        <dl>
                            <dt>通知尾段：</dt>
                            <dd><input id="remark5" name="remark5" type="text" class="d_inpt" value="${weixinPost5.remark }" readonly= "true"></dd>
                        </dl>
                     </div>
                	<div class="push_01">
                    	<p>提现到账通知</p>
                    	<dl>
                            <dt>通知状态：</dt>
                            <dd>
                            	<c:if test="${weixinPost6.state.ordinal() eq 0 || empty weixinPost6.state }" >
	                                <span><input type="radio" class="d_radus" name="state6" value="0" checked="checked">开</span>
	                                <span><input type="radio" class="d_radus" name="state6" value="1">关</span> 
	                             </c:if>
                            	<c:if test="${weixinPost6.state.ordinal() eq 1 }" >
	                                <span><input type="radio" class="d_radus" name="state6" value="0">开</span>
	                                <span><input type="radio" class="d_radus" name="state6" value="1" checked="checked">关</span> 
	                             </c:if>
                            </dd>
                        </dl>
                        <dl>
                            <dt>模板ID：</dt>
                            <dd><input id="templateId6" name="templateId6" type="text" style="width: 400px;" class="d_inpt" value="${weixinPost6.templateId }" ></dd>
                        </dl>
                        <dl>
                            <dt>通知首段：</dt>
                            <dd><input id="first6" name="first6" type="text" class="d_inpt" value="${weixinPost6.first }" readonly= "true"></dd>
                        </dl>
                        <dl>
                            <dt>通知尾段：</dt>
                            <dd><input id="remark6" name="remark6" type="text" class="d_inpt" value="${weixinPost6.remark }" readonly= "true"></dd>
                        </dl>
                     </div>
                     <div class="push_03">
                    	<p>开发者ID设置</p>
                        <dl>
                            <dt>AppID（应用ID)：</dt>
                            <dd><input type="text" class="d_04" id="appid"  name="appid" value="${sysInfo.appid }">
                            </dd>
                        </dl>
                        <dl>
                            <dt>AppSecret(应用密钥)：</dt>
                            <dd><input type="text" class="d_04" id="appsecret" name="appsecret" value="${sysInfo.appsecret }"></dd>
                        </dl>
                     </div>
                     <div class="push_02">
                     	<span id ="save">保存</span>
                        <span id ="back">返回</span>
                     </div>
                </div> 
                </form>
		</div>    
		    <div style="height:40px;"></div>
</body>
</html>
