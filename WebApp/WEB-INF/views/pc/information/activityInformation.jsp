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
<title></title>
<script type="text/javascript" >
	$(function(){
		
		//
	})
	
	function toDetail(id){
		location.href="${ctx}/pc/content/"+id;		
	}
	
</script>
</head>
<body>
	<div class="war">
        <div class="wonderful">
        	<div class="wonderful-ul">
        		<c:forEach var="list" items="${page.getContent() }" >
        			<dl >
        				<dt style="cursor:pointer" onclick="toDetail('${list.id}')" >${list.title }</dt>
        				<dd>摘要内容：${util:htmlUnescape(list.summary)}</dd>
           			</dl>
        		</c:forEach>
        		 <div class="text-right">
							<div class="btn-group">
								<div class="dataTables_paginate paging_simple_numbers"
									id="DataTables_Table_0_paginate">
									<tags:pagination page="${page}" paginationSize="10"
										hrefSubfix=""
										hrefPrefix="${ctx}/brd/pc/infomation" />
								</div>
							</div>
				</div>
            </div>
           	
        </div> 
        <jsp:include page="../footer.jsp" />
	</div>
</body>
</html>