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
<title>操作日志</title>
<script type="text/javascript">
	$(function() {
		activeNav("11");
		
		
		
		
		
	});
	
</script>
</head>
<body>
<div class="row  border-bottom">
		<div class="basic">
        <p>操作日志</p>
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a><strong>操作日志</strong></a></span>
    </div>
		<div class="col-lg-2"></div>
	</div>
	
	<div class="username-text animated fadeInRight">
            <div class="level">
            <table class="table table-striped" width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                 <thead>
                 	<tr>
                        <td><strong>员工账号</strong></td>
                        <td><strong>角色</strong></td>
                        <td><strong>操作时间</strong></td>
                        <td><strong>操作内容</strong></td>
                     </tr>
                 </thead>
                 <tbody>
                     <c:forEach var="log" items="${logs.getContent() }">
                     	<tr>
                     		<td>${log.user.username }</td>
                     		<td>${log.user.role.rolename }</td>
                     		<td>${util:formatNormalDate(log.operTime)}</td>
                     		<td>${log.operContent}</td>
                		</tr>
                     </c:forEach>
                     </tbody>
                     </table>
				<div class="text-right">
						<div class="btn-group">
								<div class="dataTables_paginate paging_simple_numbers"
									id="DataTables_Table_0_paginate">
									<tags:pagination page="${logs}" paginationSize="10"
										hrefSubfix=""
										hrefPrefix="${ctx}/admin/operlog/list" />
								</div>
						</div>
				</div>
            </div>
	</div>
</body>
</html>