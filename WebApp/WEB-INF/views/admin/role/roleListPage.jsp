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
<title>角色管理</title>
<script type="text/javascript">
	$(function() {
		activeNav2("1","1_2");
		
		
		$("#allcheck").click(function(){
			var ischecked = $(this).is(":checked")?"check":"uncheck"; 
			 $("input[type='checkbox']").iCheck(ischecked);
		})
		
		//选择
		$(".childcheckbox").click(function(){
			var childNum = $(".childcheckbox").length ;
			if($('input[name="roleId"]:checked').length < childNum){
				$("#allcheck").iCheck("uncheck");
			}
			if($('input[name="roleId"]:checked').length == childNum){
				$("#allcheck").iCheck("check");
			}
		});
		
		$("#deleteRole").click(function(){
			var ids ="";
			var items = $('input[name="roleId"]:checked');
		
			if(items.length==0){
				alert("请选择要删除的角色");
				return ;
			} 
			$('input[name="roleId"]:checked').each(function(){
				ids += $(this).val()+",";
			});
			
			ids = ids.substring(0,ids.length-1);
			showCallBackDialog("确定删除?",function(){
				$.post("${ctx}/admin/role/delRoles",{ids:ids},function(data){
					if(data.code==1){
						showCallBackDialog(data.mes,function(){
		      				location.href="${ctx}/admin/role/list";
		      			});
					}else{
						alert(data.mes);
					} 
				});  
			})
		})
		
		
	});
	
	function delRole(obj){
		showCallBackDialog("确定删除?",function(){
			$.post("${ctx}/admin/role/delRoles",{ids:obj},function(data){
				if(data.code==1){
					showCallBackDialog(data.mes,function(){
	      				location.href="${ctx}/admin/role/list?page=${roles.getNumber()+1}";
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
<div class="row  border-bottom">
	<div class="basic">
        <p>企业管理</p>
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a href="#" >企业管理</a>><a><strong>角色管理</strong></a></span>
    </div>
	</div>
	<div class="department">
				           	<a href="${ctx}/admin/role/toAddRolePage"><p>新增角色</p></a>
				            <span id="deleteRole" >批量删除</span>
	</div>
	
	<div class="username-text animated fadeInRight ">
			<div class="level" style="border:none">
            <table class="table table-striped" >
                 <thead>
                 	<tr>
                     	<td class="l_in2">
                        <input style="height:13px;width:13px;margin-left:25px;" type="checkbox" id="allcheck" class="l_in">
                        </td>
                        <td><strong>角色名称</strong></td>
                        <td><strong>账号个数</strong></td>
                        <td><strong>操作</strong></td> 
  					</tr>
                 </thead>
                 <tbody>        
                     <c:forEach var="list" items="${roles.getContent()}">
                     		<tr >
                     			<td>
		                     	<c:if test="${list.rolename ne 'Admin' && list.rolename ne '业务员' && list.rolename ne '风控经理' && list.rolename ne '财务'}" >
		                     		<input type="checkbox" name="roleId" value="${list.id}" class = "childcheckbox">
		                     	</c:if>
		                     	</td>
		                        <td>
		                        <c:choose>
			                        <c:when test="${list.rolename eq  'Admin' }" >
			                        CEO
			                        </c:when>
			                        <c:otherwise>
			                        	${list.rolename }
			                        </c:otherwise>
		                        </c:choose>
		                        </td>
		                        <td >${list.number }</td>
		                        <td>
			                        <div class="level_img">
			                        <c:if test="${list.rolename ne 'Admin' && list.rolename ne '业务员' }" >
			                            	<a href="${ctx}/admin/role/modifyRole/${list.id}?page=${roles.getNumber()+1}" ><span><img src="${ctx}/static/brd/img/bjt1.png"></span></a>
			                           	<c:if test="${list.rolename ne '风控经理' && list.rolename ne '财务' }" >
			                           		<a href="#" onclick="delRole('${list.id}')" ><span><img src="${ctx}/static/brd/img/bjt3.png"></span></a>
			                        	</c:if>
			                        </c:if>
			                        </div>
		                        </td>
		                     </tr>
		                 
                     </c:forEach>
                   </tbody>
            </table>
        
        </div> 
						<div class="text-right">

							<div class="btn-group">
								<div class="dataTables_paginate paging_simple_numbers"
									id="DataTables_Table_0_paginate">
									<tags:pagination page="${roles}" paginationSize="10"
										hrefSubfix=""
										hrefPrefix="${ctx}/admin/role/list" />
								</div>
							</div>
						</div>
					</div>
</body>
</html>