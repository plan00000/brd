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
<title>部门管理</title>
<script type="text/javascript">
	$(function() {
		activeNav2("1","1_1");
		
		/**全选与反选*/
		$("#allcheck").click(function(){
			var ischecked = $(this).is(":checked")?"check":"uncheck"; 
			 $("input[type='checkbox']").iCheck(ischecked);
		})
		
		//选择
		$(".childcheckbox").click(function(){
			var childNum = $(".childcheckbox").length ;
			if($('input[name="departmentId"]:checked').length < childNum){
				$("#allcheck").iCheck("uncheck");
			}
			if($('input[name="departmentId"]:checked').length == childNum){
				$("#allcheck").iCheck("check");
			}
		});
		
		$("#deleteDepartment").click(function(){
			var ids ="";
			var items = $('input[name="departmentId"]:checked');
		
			if(items.length==0){
				alert("请选择要删除的部门");
				return ;
			} 
			$('input[name="departmentId"]:checked').each(function(){
				ids += $(this).val()+",";
			});
			ids = ids.substring(0,ids.length-1);
			showCallBackDialog("确定删除?",function(){
				$.post("${ctx}/admin/department/delDepartments",{ids:ids},function(data){
					if(data.code==0){
						showCallBackDialog(data.mes,function(){
							location.href="${ctx}/admin/department/list?page=${departments.getNumber()+1}";
		      			});
					}else{
						alert(data.mes);
					} 
				});  
			})
			
		})
		
		
		
	});
	
	function deletedtn(obj){
		showCallBackDialog("确定删除？",function(){
			$.post("${ctx}/admin/department/delete",{id:obj},function(res){
				if(res.code==0){
					showCallBackDialog(res.mes,function(){
	      				location.href="${ctx}/admin/department/list?page=${departments.getNumber()+1}";
	      			});
					 
				}else{
						alert(res.mes)
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
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a href="#" >企业管理</a>><a><strong>部门管理</strong></a></span>
    </div>	
    </div>
	<div class="department">
			<a href="${ctx}/admin/department/toAdddepartment"><p>新增部门</p></a>
			<span id="deleteDepartment" >批量删除</span>
	</div>
	
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-12">
				<div class="ibox float-e-margins">
					<div class="ibox-content">

						<div class="table-responsive">
						
						
			<div class="level" style="border:none" >
	            <table class="table table-striped" width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	                <thead>
	                	<tr>
	                     	<td class="l_in2">
	                        <input type="checkbox" id="allcheck" class="l_in">
	                        </td>
	                        <td  class="l_in1"><strong>级别</strong></td>
	                        <td><strong>部门名称</strong></td>
	                        <td><strong>部门人数</strong></td>
	                        <td><strong>操作</strong></td>
	                     </tr>
	                </thead>
	                 <tbody>
	                     	 <c:forEach var ="list" items="${departmentList }">
	                     	 		<tr>
	                     	 			<td><input type="checkbox" name="departmentId" value="${list.id }" class="l_in childcheckbox" > </td>
	                     	 			<c:choose>
	                     	 			<c:when test="${list.level eq '1' }" >
	                     	 				<td class="l_in1" style="height:auto">${list.level}级 </td>
	                     	 			</c:when>
	                     	 			<c:when test="${list.level eq '2' }" >
	                     	 				<td class="l_in4" >${list.level }级</td>
	                     	 			</c:when>
	                     	 			<c:otherwise>
	                     	 				<td class="l_in5" >${list.level}级</td>
	                     	 			</c:otherwise>
	                     	 			</c:choose>
	                     	 			<td>${list.name}</td>
	                     	 			<td class="l_in3">${list.departmentNum}</td>
	                     	 			<td>
								             <div class="level_img">
								                   <a href="${ctx}/admin/department/toEditDepartment/${list.id}?grandParentId=${grandson.parent.parent.id}&page=${departments.getNumber()+1}"><span><img src="${ctx}/static/brd/img/bjt1.png"></span></a>
								                   <a href="#" onclick="deletedtn('${list.id}')" ><span><img src="${ctx}/static/brd/img/bjt3.png"></span></a>
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
									<tags:pagination page="${departments}" paginationSize="10"
										hrefSubfix=""
										hrefPrefix="${ctx}/admin/department/list" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
  </div>
</body>
</html>