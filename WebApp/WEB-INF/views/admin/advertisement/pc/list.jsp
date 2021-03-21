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
<title>PC官网</title>
<script type="text/javascript">
	$(function() {
		activeNav2("7","7_1");
		$("samp").click(function(){
			location.href="${ctx}/admin/advertisement/pc/ad/toAddAdvertise" ;
		});
	});
	
	function delAd(id){
		showCallBackDialog("是否确定要删除该条滚播图",function(){
			$.post("${ctx}/admin/advertisement/pc/ad/delAd",{id:id},function(data){
				if(data.code==0){
					showCallBackDialog("删除成功",function() {
						location.href = "${ctx}/admin/advertisement/pc/ad/list";
					})
				}else{
					alert(data.mes);
				}
			});		
		})
	}
	
	
</script>
</head>
<body>
		<div class="basic">
        <p>广告管理</p>
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a href="#" >广告管理</a>><a><strong>PC官网</strong></a></span>
    </div>	
	
	<div class="employee animated fadeInRight e_cut">
           <dl>
           	<dt style="width:0;"></dt>
             <dd><a href="#"><samp>增加滚播图</samp></a></dd>
           </dl>
          </div>
		 <div class="username-text animated fadeInRight n_mar">
            <div class="level" >
            <table class="table table-striped" width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                 <thead>
                 	<tr>
                        <td><strong>图片</strong></td>
                        <td><strong>位置</strong></td>
                        <td><strong>添加链接</strong></td>
                        <td><strong>添加时间</strong></td>
                        <td><strong>操作</strong></td>
                     </tr>
                 </thead>
                 <tbody>
                     <c:forEach var="advertisement" items="${page.getContent() }">
                     	<tr  >
							<td><c:choose>
							<c:when test="${not empty advertisement.picurl }" >
								<img src="${advertisement.picurl }" width="190" height="55" > 
							</c:when>
							<c:otherwise>
								<img src="${ctx }/static/brd/img/4.png" width="190" height="55" > 
							</c:otherwise>
							</c:choose> </td>                     	
                     		<td>${advertisement.position.getDes() }</td>
                     		<c:choose>
	                     		<c:when test="${advertisement.isouturl =='YES' }" >
	                     		<td><a href='${advertisement.address}' target="_blank" >${advertisement.address}</a></td>
	                     		</c:when>
	                     		<c:otherwise>
	                     			<td>无</td>
	                     		</c:otherwise>
                     		</c:choose>
                     		<td>${util:formatNormalDate(advertisement.createTime) }</td>
                     		<td>
	                            <div class="level_img">
	                                <span><a href="${ctx }/admin/advertisement/pc/ad/toEditAd/${advertisement.id}" ><img src="${ctx}/static/brd/img/bjt1.png"></a></span>
	                                <c:if test="${advertisement.position eq 'BANNER'  }" >
                              			<span><img onclick="delAd(${advertisement.id })" src="${ctx }/static/brd/img/bjt3.png"></span>
	                                </c:if>
	                            </div>
                        	</td>
                     	</tr>
             		</c:forEach>
                   </tbody>
            </table>
            	<div class="text-right">
						</div>
						</div>
            </div>
</body>
</html>