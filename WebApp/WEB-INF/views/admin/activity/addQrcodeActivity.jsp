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
<title>新增二维码</title>
<script src="${ctx}/static/js/input-number-change.js"></script>
<script type="text/javascript">
$(function(){
	activeNav2("5","5_4");
	//初始化字符串长度
	var state = ${state};
	if(state == '1'){
		var name = $("#ipt_name").val();
		var a = name.length;
		$("#tname").val(a+"/30");
	}
	/** 添加*/
	$("#btn_save").click(function(){
		if(check()){
			var data = {};
			data.name = $("#ipt_name").val().trim();
			$.post("${ctx}/admin/qrCodeActivity/add;JSESSIONID=<%=request.getSession().getId()%>",data,function(res){
				if(res.code == 1){
					location.href= "${ctx}/admin/qrCodeActivity/list;JSESSIONID=<%=request.getSession().getId()%>";
				}else{
					alert(res.mes);
				}
			})
		}
	});
	/**编辑　*/
	$("#btn_edit").click(function(){
		if(check()){
			var data = {};
			data.name = $("#ipt_name").val().trim();
			data.id = $("#ipt_id").val().trim();
			$.post("${ctx}/admin/qrCodeActivity/edit;JSESSIONID=<%=request.getSession().getId()%>",data,function(res){
				if(res.code == 1){
					location.href= "${ctx}/admin/qrCodeActivity/list;JSESSIONID=<%=request.getSession().getId()%>";
				}else{
					alert(res.mes);
				}
			})
		}
	});
})
function check(){
	var name = $("#ipt_name").val().trim();
	if(name.length == 0){
		alert("名称不能为空");
		return false;
	}
	return true;
}
</script>
</head>
<body>
	<div class="row border-bottom">
		<div class="basic">
	     <p>活动管理</p>
	     <span><a href="<c:url value='/admin/main'/> style="margin-left:0;">首页</a>>
			 <a href="#">活动管理</a>>
			 <a href="#">二维码活动</a>>
			 <a><strong>${state eq '0' ? '新增二维码' : '编辑二维码'}</strong></a>
		 </span>
	    </div>
    </div>
    <div class="new_emp animated fadeInRight">
    	<div class="compute">
    		<form id = "submit_form">
	    		<dl>
	                  <dt>名称：</dt>
	                  <dd>
	                  	<input type = "hidden" id = "ipt_id" value="${ qrcodeActivity.id}">
	                  	<input type="text" class="c_scet" id = "ipt_name" name = "name" <c:if test = "${state eq '1' }">value = "${qrcodeActivity.name }"</c:if>
	                  	onKeyDown="gbcount(this.form.name,this.form.used1,30);"  onKeyUp="gbcount(this.form.name,this.form.used1,30);" onblur = "gbcount(this.form.name,this.form.used1,30);">
	                 	<input class="inputtext2 inputtextn" name="used1"  id="tname" value="0/30" readonly="readonly"> 
	                 </dd>
	            </dl>
            </form>
            <p>
            	<a href="${ctx}/admin/qrCodeActivity/list" id = "btn_back" class="cut">返回</a>
            	<c:if test = "${state eq '0' }">
                 	<a href="javascript:void(0)" id = "btn_save" >生成</a>
                </c:if>
                <c:if test = "${state eq '1' }">
                	<a href="javascript:void(0)" id = "btn_edit" >修改</a>
                </c:if>
                 
            </p>
        </div>
    </div>
</body>
</html>