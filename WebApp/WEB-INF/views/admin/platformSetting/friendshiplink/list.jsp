<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>友情链接</title>
<script type="text/javascript">
	$(function(){
		activeNav2("8","8_7");
		
		$("#allcheck").click(function(){
			var ischecked = $(this).is(":checked")?"check":"uncheck"; 
			 $("input[type='checkbox']").iCheck(ischecked);
		})
		
		//选择
		$(".childcheckbox").click(function(){
			var childNum = $(".childcheckbox").length ;
			if($('input[name="friendlinkId"]:checked').length < childNum){
				$("#allcheck").iCheck("uncheck");
			}
			if($('input[name="friendlinkId"]:checked').length == childNum){
				$("#allcheck").iCheck("check");
			}
		});
		
		$("#deleteFriendlinlk").click(function(){
		var ids ="";
		var items = $('input[name="friendlinkId"]:checked');
	
		if(items.length==0){
			alert("请选择要删除的友情链接");
			return ;
		} 
		$('input[name="friendlinkId"]:checked').each(function(){
			ids += $(this).val()+",";
		});
		ids = ids.substring(0,ids.length-1);
		showCallBackDialog("确定删除?",function(){
			$.post("${ctx}/admin/platformSetting/friendshiplink/delFriendship",{ids:ids},function(data){
				if(data.code==0){
					showCallBackDialog(data.mes,function(){
						location.href="${ctx}/admin/platformSetting/friendshiplink/list";
	      			});
				}else{
					alert(data.mes);
				} 
			});  
		})
	});
		
		//jq 结束
	});
	
	/*style="word-wrap:break-word; white-space:normal; word-break:break-all; width:150px;"  */
	/* .level table p{ width:320px; height:auto; overflow:hidden; margin:0 auto;}  */
	function delFriend(id){
		showCallBackDialog("确定删除?",function(){
			$.post("${ctx}/admin/platformSetting/friendshiplink/delFriendship",{ids:id},function(data){
				if(data.code==0){
					showCallBackDialog(data.mes,function(){
	      				location.href="${ctx}/admin/platformSetting/friendshiplink/list";
	      			});
				}else{
					alert(data.mes);
				} 
			});
		})
	}
	
</script>
</head>
<body>
	<div class="row border-bottom">
	<div class="basic">
        <p>平台设置</p>
        <span><a href="<c:url value='/admin/main'/>" style="margin-left:0;">首页</a>><a href="#" >平台设置</a>><a><strong>友情链接</strong></a></span>
    </div>
    </div>
	<div class="employee animated fadeInRight">
           <dl>
           	<dt style="width:0;"></dt>
             <dd>
                <a href="${ctx}/admin/platformSetting/friendshiplink/toAddEditFriendship" ><samp>新增链接</samp> </a>
                <a href="javascript:void(0)"><samp id="deleteFriendlinlk"  class="e_suo">批量删除</samp> </a>
              </dd>
           </dl>
    </div>
    
    <div class="username-text animated fadeInRight  n_mar">
		 <div class="level">
            <table width="100%" class="table table-striped"  border="0" align="center" cellpadding="0" cellspacing="0">
                 <thead>
                 	<tr>
                 		<td><input type="checkbox" id="allcheck"   ></td>
                 		<td><strong>链接标题</strong></td>
                 		<td><strong>链接地址</strong></td>
                 		<td><strong>操作</strong></td>
                 	</tr> 
                 </thead>
                 <tbody>
                 	<c:forEach var="list" items="${page.getContent() }" >
                 		<tr>
                 			<td><input type="checkbox" name="friendlinkId" value="${list.id }" class = "childcheckbox"></td>
                 			<td>${list.title }</td>
                 			<td>
                 				<p style="width:320px; height:auto; overflow:hidden; margin:0 auto;word-break:break-all;" >
	                 				<c:choose>
	                 					<c:when test="${fn:startsWith(list.linkUrl,'http://') || fn:startsWith(list.linkUrl,'https://')}" >
		                 					<a href="${list.linkUrl }" target="_blank" > ${list.linkUrl } </a> 
		                 				</c:when>
		                 				<c:otherwise>
		                 					 <a href="http://${list.linkUrl }" target="_blank" > ${list.linkUrl } </a> 
		                 				</c:otherwise>
	                 				</c:choose>
                 				</p>
                 			</td>
                 			<td>
	                			<a href="${ctx }/admin/platformSetting/friendshiplink/toEditFriendship/${list.id}?page=${page.getNumber()+1}" class="l_a"><img src="${ctx }/static/brd/img/bjt1.png"></a>
	                			<a href="javascript:void(0)" class="l_a"><img onclick="delFriend('${list.id}')" src="${ctx }/static/brd/img/bjt3.png"></a>
                		</td>
                 		</tr>
                 	</c:forEach>
                 </tbody>
            </table>
            <div class=" m_right">
				<tags:pagination page="${page}" paginationSize="10" hrefSubfix=""
						hrefPrefix="${ctx}/admin/platformSetting/friendshiplink/list" />
			</div>
            </div>
             
	</div>
</body>
</html>