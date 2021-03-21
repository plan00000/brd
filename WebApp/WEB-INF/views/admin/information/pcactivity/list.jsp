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
<title>精彩资讯</title>
<script type="text/javascript">
	$(function() {
		activeNav2("10","10_1");
		
		$("#sortAddDate").click(function(){
			var id =$(this).data('id');
			if(id==1) {
				$("#formsortType").val("asc");
				submitform();
			}else {
				$("#formsortType").val("desc");
				submitform();
			}
			
		});
		
		
		$("#allcheck").click(function(){
			var ischecked = $(this).is(":checked")?"check":"uncheck"; 
			 $("input[type='checkbox']").iCheck(ischecked);
		})
		
		//选择
		$(".childcheckbox").click(function(){
			var childNum = $(".childcheckbox").length ;
			if($('input[name="information"]:checked').length < childNum){
				$("#allcheck").iCheck("uncheck");
			}
			if($('input[name="information"]:checked').length == childNum){
				$("#allcheck").iCheck("check");
			}
		});
		$("#deleteInformation").click(function(){
			var ids ="";
			var items = $('input[name="information"]:checked');
			
			if(items.length==0){
				alert("请选择要删除的活动资讯");
				return ;
			} 
			 	$('input[name="information"]:checked').each(function(){
				ids += $(this).val()+",";
			});
			
			ids = ids.substring(0,ids.length-1);
			showCallBackDialog("确定删除?",function(){
				$.post("${ctx}/admin/information/pcactivity/delInformations",{ids:ids},function(data){
					if(data.code==0){
						showCallBackDialog("删除成功",function(){
							location.href = "${ctx}/admin/information/pcactivity/list";
						})
					}else{
						alert(data.mes);	
					}			
				}) 	
			})
		})
		
		
		
		$("#addInformation").click(function(){
			location.href="${ctx}/admin/information/pcactivity/toAddPage";
		});
		
		
	});
	
	function delInformation(id){
		showCallBackDialog("确定删除?",function(){
			$.post("${ctx}/admin/information/pcactivity/delInformation",{id:id},function(data){
				if(data.code==0){
					showCallBackDialog("删除成功",function(){
						location.href = "${ctx}/admin/information/pcactivity/list";
					})
				}else{
					alert(data.mes);	
				}			
			})
		})
	}
	
	function submitform(){
		$("#myform").submit();
	}
	
	
	function closeEdit(id){
		var sortid = $("#sortId"+id).val();
		
 		var re = /^[0-9]+$/gi
			 
		if(!re.test(sortid)){
			alert("请输入正确的文章排序");
			return ;
	    }
 		if(sortid > 2147483647){
			alert("超过排序允许的最大值")
			return ;
		}
		
		$.post("${ctx}/admin/information/pcactivity/changeSortid",{id:id,sortid:sortid},function(data){
			if(data.code==0){
				showCallBackDialog("修改成功",function(){
					location.href = "${ctx}/admin/information/pcactivity/list?page=${pageNumber}&sortType=${sortType}";
				})
			}else{
				alert(data.mes);
			}
		})		
	}
	
	function edit(id){
		$("#edit"+id).css("display","none");
		$("#canEdit"+id).css("display","");
	}
	
	
</script>
</head>
<body>
<div class="row  border-bottom">
	<div class="basic">
        <p>官网文章</p>
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a href="#" >官网文章</a>><a><strong>精彩资讯</strong></a></span>
    </div>
</div>	
	<form id="myform" action="${ctx }/admin/information/activity/list" >
            	<input type="hidden" name="page" value="${pageNumber}" />
            	<input type="hidden" name="sortType" id="formsortType" value="${sortType }" />
    </form>
	<div class="employee animated fadeInRight">
           <dl>
           	<dt style="width:0;"></dt>
             <dd>
                <a href="#" ><samp id="addInformation" >添加资讯</samp> </a>
                <a href="#"><samp id="deleteInformation" class="e_suo">批量删除</samp> </a>
              </dd>
           </dl>
    </div>
		 <div class="username-text animated fadeInRight  n_mar">
		 <div class="level">
            <table width="100%" class="table table-striped"  border="0" align="center" cellpadding="0" cellspacing="0">
                 <thead>
                 	 <tr>
                     	<td class="l_in2"><input id="allcheck" type="checkbox" class="l_in"></td>
                        <td>
                        <div class="level_triangle">
                        	<p style="padding-top:0px" ><strong>标题</strong></p>
                        </div>
                        </td>
                        <td>
                        <div class="level_triangle">
                        	<p style="padding-top:0px" ><strong>添加时间</strong></p>
                           <c:choose >
	                           	<c:when test="${sortType eq 'desc' }">
	                           		<span class="l_sanj2" style="margin-top:6px" id="sortAddDate" data-id =1 ></span>
	                           	</c:when>
	                           	<c:when test="${sortType eq 'asc' }">
	                           		<span class="l_sanj1" style="margin-top:6px" id="sortAddDate" data-id =2 ></span>
	                           	</c:when>
	                           	<c:otherwise>
	                           		<span class="l_sanj3" style="margin-top:3px" id="sortAddDate" data-id=1 ></span>
	                           	</c:otherwise>
                           </c:choose>
                        </div>
                        </td>
                        <td>
                        <div class="level_triangle">
                        	<p style="padding-top:0px" ><strong>状态</strong></p>
                            
                        </div>
                        </td>
                        <td><strong>排序</strong></td>
                        <td><strong>操作</strong></td>
                     </tr>
                 </thead>
                 
                 <tbody>
                	
                     <c:forEach var="information" items="${page.getContent() }" >
                       <tr >
	                     	<td class="l_in2"><input type="checkbox" name="information" value="${information.id }" class="l_in childcheckbox"></td>
	                     	<td>${information.title }</td>
	                        <td>${util:formatNormalDate(information.addDate)}</td>
	                        <c:if test="${information.status eq 'YES'}" >
	                        	<td class="l_color" >启用</td>
	                        </c:if>
	                        <c:if test="${information.status eq 'NO'}" >
	                        	<td style="color:red" class="l_color" >停用</td>
	                        </c:if>
	                      
	                        <td> 
	                        <div id="edit${information.id }"  >
	                        	<font >${information.sortid }</font><img onclick="edit('${information.id}')" src="${ctx }/static/brd/img/sr.png">
	                        </div>
	                        <div id="canEdit${information.id }" style="display:none"  >
	                        <input type="text" value="${information.sortid }" id="sortId${information.id }"  class="l_tuo"><img onclick="closeEdit('${information.id}')"  src="${ctx }/static/brd/img/gx.png">
	                        </div>
	                        </td>
	                        <td>
	                        	<a href="${ctx}/admin/information/pcactivity/toEditInformation/${information.id}?sortType=${sortType}&page=${page.getNumber() + 1}"  class="l_a"><img src="${ctx }/static/brd/img/bjt1.png"></a>
	                        	<a href="#"  class="l_a"><img onclick="delInformation('${information.id}')" src="${ctx }/static/brd/img/bjt3.png"></a>
	                            <a href="${ctx }/pc/content/${information.id}" target="_blanl"  class="l_a"><img src="${ctx }/static/brd/img/bjt2.png"></a>
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
									<tags:pagination page="${page}" paginationSize="10"
										hrefSubfix="sortType=${sortType }"
										hrefPrefix="${ctx}/admin/information/activity/list" />
								</div>
							</div>
						</div>
</div>						
</body>
</html>