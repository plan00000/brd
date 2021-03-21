<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="util" uri="functions"%>
<%@ taglib prefix="shiro" uri="http://www.springside.org.cn/tags/shiro"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
	.caozuo {
		width: 85px;
	    height: 30px;
	    overflow: hidden;
	    float: left;
	    font: 14px/30px "microsoft yahei";
	    color: #333;
	    text-align: right;
	    margin-right: 10px;
	 }
</style>
<title>员工列表</title>
<script type="text/javascript">
	$(function() {
		activeNav2("1","1_3");
		firstDepartmentId = '${firstDepartmentId}';
		secondDepartmentId=	'${secondDepartmentId}';
		thirdDepartmentId =	'${thirdDepartmentId}';
		loadDepartment();
		state = '${state}';
		if(state==0){
			$("#normal").attr("class","e_class");
			$("#frooze").attr("class"," ");
		}else if (state==1){
			$("#normal").attr("class","");
			$("#frooze").attr("class","e_class");
		}else{
			$("#allstate").attr("class","e_class");			
		}
		
		$("#normal").click(function(){
			$("#normal").attr("class","e_class");
			$("#frooze").attr("class"," ");
			$("#allstate").attr("class","");	
			state=0;
		})
		
		$("#allstate").click(function(){
			$("#normal").attr("class","");
			$("#frooze").attr("class"," ");
			$("#allstate").attr("class","e_class");	
			state=-1;
		})
		
		$("#frooze").click(function(){
			$("#normal").attr("class","");
			$("#frooze").attr("class","e_class");
			$("#allstate").attr("class","");	
			state=1;
		})
		
		$("#allcheck").click(function(){
			var ischecked = $(this).is(":checked")?"check":"uncheck"; 
			 $("input[type='checkbox']").iCheck(ischecked);
		})
		
		//选择
		$(".childcheckbox").click(function(){
			var childNum = $(".childcheckbox").length ;
			var a = $('input[name="employee"]:checked').length;
			if($('input[name="employee"]:checked').length < childNum){
				$("#allcheck").iCheck("uncheck");
			}
			if($('input[name="employee"]:checked').length == childNum){
				$("#allcheck").iCheck("check");
			}
		});
		
		$("#query").click(function(){
			var roleId =$("#roleId").val();
			$("#formkeyword").val($("#keyword").val());
			var keyword = $("#formkeyword").val();
			var postfirstDepartmentId = $("#firstDepartmentId").val();
			var postsecondDepartmentId = $("#secondDepartmentId").val();
			var postthirdDepartmentId = $("#thirdDepartmentId").val();;
			if(postsecondDepartmentId=="undefined"|| postsecondDepartmentId==null){
				postsecondDepartmentId=-1;
			}
			if(postthirdDepartmentId=="undefined"|| postthirdDepartmentId==null){
				postthirdDepartmentId=-1;
			}
			 location.href="${ctx}/admin/employee/list?state="+state+"&roleId="+roleId+"&keyword="+keyword+
					"&firstDepartmentId="+postfirstDepartmentId+"&secondDepartmentId="+postsecondDepartmentId+
					"&thirdDepartmentId="+postthirdDepartmentId; 
		});
		
		$(".changeState").click(function() {
			var $this = $(this);
			var state = $(this).attr("state");
			var operation ="open";
			var userId = $(this).attr("changeId");
			var poststate = "ON";
			if(state==operation){
				operation="close";
				poststate ="OFF"
			}
			$.post("${ctx}/admin/employee/changeState",{id:userId,state:poststate},function(data){
				if(data==0){
					if (state == 'open') {
						$this.html("禁用");
						$this.removeClass("btn-primary");
						$this.addClass("btn-danger");
						$this.attr("state", "close");
					} else {
						$this.html("启用");
						$this.removeClass("btn-danger");
						$this.addClass("btn-primary");
						$this.attr("state", "open");
					}
				}
			
			})
		});
		
		$("#delemployee").click(function(){
			var ids ="";
			var items = $('input[name="employee"]:checked');
		
			if(items.length==0){
				alert("请选择要禁用的员工");
				return ;
			} 
			$('input[name="employee"]:checked').each(function(){
				ids += $(this).val()+",";
			});
			
			ids = ids.substring(0,ids.length-1);
			showCallBackDialog("确定禁用?",function(){
				$.post("${ctx}/admin/employee/delEmployee",{ids:ids},function(data){
					if(data.code==0){
						showCallBackDialog("禁用成功",function(){
							location.href = "${ctx}/admin/employee/list";
						})
					}else{
						alert(data.mes);	
					}			
				})   
			})
		});
		
		$("#sortorderNum").click(function(){
			var id=$(this).data('id');
			if(id == 1){
				$("#ipt_sortName").val("orderNum");
				$("#ipt_sortType").val("asc");
				submit();
			}else{
				$("#ipt_sortName").val("orderNum");
				$("#ipt_sortType").val("desc");
				submit();
			}
		});
		
		$("#sortsonNum").click(function(){
			var id=$(this).data('id');
			if(id == 1){
				$("#ipt_sortName").val("sonNum");
				$("#ipt_sortType").val("asc");
				submit();
			}else{
				$("#ipt_sortName").val("sonNum");
				$("#ipt_sortType").val("desc");
				submit();
			}
		});
		
		$("#sortgrandSonNum").click(function(){
			var id=$(this).data('id');
			if(id == 1){
				$("#ipt_sortName").val("grandSonNum");
				$("#ipt_sortType").val("asc");
				submit();
			}else{
				$("#ipt_sortName").val("grandSonNum");
				$("#ipt_sortType").val("desc");
				submit();
			}
		});
		
		$("#export_btn").click(function(){
			window.open("${ctx}/admin/employee/export;JSESSIONID=<%=request.getSession().getId()%>?state=${state}&roleId=${roleId}&keyword=${keyword}&firstDepartmentId=${firstDepartmentId}&secondDepartmentId=${secondDepartmentId}&thirdDepartmentId=${thirdDepartmentId}");
		}); 
		
		
		
	});
	
	function loadDepartment(){
		if(secondDepartmentId=="undefined"|| secondDepartmentId==null){
			secondDepartmentId=-1;
		}
		if(thirdDepartmentId=="undefined"|| thirdDepartmentId==null){
			thirdDepartmentId=-1;
		}
		$.post("${ctx}/admin/employee/getDepartmentList",{firstDepartmentId:firstDepartmentId,secondDepartmentId:secondDepartmentId,thirdDepartmentId:thirdDepartmentId},
				function(data){
			var showDepartment = document.getElementById("showDepartment");
			var firstDepartments =data.firstDepartments;
			var secondDepartments = data.secondDepartments;
			var thirdDepartments = data.thirdDepartment;
			var html="";	
			html = "<select name='firstDepartmentId' id='firstDepartmentId' class='e_inpt' onchange='changeDepartment()'>";
				html += "<option value='-1'>全部</option> " ;
			for(var i=0;i<firstDepartments.length;i++){
				if(firstDepartments[i].id == firstDepartmentId ){
					html += "<option selected='selected' value='"+firstDepartments[i].id+"'>"+firstDepartments[i].name+"</option>";
				}else{
					html += "<option value='"+firstDepartments[i].id+"'>"+firstDepartments[i].name+"</option>";
				}
			}	
			html +="</select>";
			 if(secondDepartments.length>0  && firstDepartmentId>-1 ){
				html +="<select name='secondDepartmentId' id='secondDepartmentId' class='e_inpt' onchange='changeDepartment()'> ";
				html += "<option value='-1'>全部</option> " ;
				for(var i=0;i<secondDepartments.length;i++){
					if(secondDepartments[i].id == secondDepartmentId){
						html += "<option selected='selected' value='"+secondDepartments[i].id+"'>"+secondDepartments[i].name+"</option>";
					}else{				
						html += "<option value='"+secondDepartments[i].id+"'>"+secondDepartments[i].name+"</option>";
					}
				}	
				html +="</select>"; 
				
			}
			if(thirdDepartments.length>0 && firstDepartmentId>-1 && secondDepartmentId>-1  ){
				html +="<select name='thirdDepartmentId' id='thirdDepartmentId' class='e_inpt' > ";
				html += "<option value=''>全部</option> " ;
				for(var i=0;i<thirdDepartments.length;i++){
					if(thirdDepartments[i].id==thirdDepartmentId){
						html += "<option selected='selected' value='"+thirdDepartments[i].id+"'>"+thirdDepartments[i].name+"</option>";
					}else{
						html += "<option value='"+thirdDepartments[i].id+"'>"+thirdDepartments[i].name+"</option>";
					}
				}
				html += "<select>";
			} 		
			showDepartment.innerHTML=html;
		});
	}
	
	function changeDepartment(){
		firstDepartmentId=$("#firstDepartmentId").val();
		secondDepartmentId=$("#secondDepartmentId").val();
		thirdDepartmentId = $("#thirdDepartmentId").val();		
		if(secondDepartmentId=="undefined"|| secondDepartmentId==null){
			secondDepartmentId=-1;
		}
		if(thirdDepartmentId=="undefined"|| thirdDepartmentId==null){
			thirdDepartmentId=-1;
		}
		$.post("${ctx}/admin/employee/getDepartmentList",{firstDepartmentId:firstDepartmentId,secondDepartmentId:secondDepartmentId,thirdDepartmentId:thirdDepartmentId},
				function(data){
			var showDepartment = document.getElementById("showDepartment");
			var firstDepartments =data.firstDepartments;
			var secondDepartments = data.secondDepartments;
			var thirdDepartments = data.thirdDepartment;
			var html="";
			if(firstDepartmentId==-1|| firstDepartmentId.trim().length==0){
				html = "<select name='firstDepartmentId' id='firstDepartmentId' class='e_inpt' onchange='changeDepartment()'>";
					html += "<option selected='selected' value=''>全部</option> " ;
				for(var i=0;i<firstDepartments.length;i++){
					if(firstDepartments[i].id == firstDepartmentId ){
						html += "<option selected='selected' value='"+firstDepartments[i].id+"'>"+firstDepartments[i].name+"</option>";
					}else{
						html += "<option value='"+firstDepartments[i].id+"'>"+firstDepartments[i].name+"</option>";
					}
				}	
				html +="</select>";
			}else{	
				html = "<select name='firstDepartmentId' id='firstDepartmentId' class='e_inpt' onchange='changeDepartment()'>";
				html += "<option selected='selected' value='-1'>全部</option> " ;
				for(var i=0;i<firstDepartments.length;i++){
					if(firstDepartments[i].id == firstDepartmentId ){
						html += "<option selected='selected' value='"+firstDepartments[i].id+"'>"+firstDepartments[i].name+"</option>";
					}else{
						html += "<option value='"+firstDepartments[i].id+"'>"+firstDepartments[i].name+"</option>";
					}
				}	
				html +="</select>";
				if(secondDepartments.length>0){
					html +="<select name='secondDepartmentId' id='secondDepartmentId' class='e_inpt' onchange='changeDepartment()'> ";
					html += "<option value='-1'>全部</option> " ;
					for(var i=0;i<secondDepartments.length;i++){
						if(secondDepartments[i].id == secondDepartmentId){
							html += "<option selected='selected' value='"+secondDepartments[i].id+"'>"+secondDepartments[i].name+"</option>";
						}else{				
							html += "<option value='"+secondDepartments[i].id+"'>"+secondDepartments[i].name+"</option>";
						}
					}	
					html +="</select>"; 
				}
				if(thirdDepartments.length>0 && secondDepartmentId>0 ){
					html +="<select name='thirdDepartmentId' id='thirdDepartmentId' class='e_inpt' > ";
					html += "<option value='-1'>全部</option> " ;
					for(var i=0;i<thirdDepartments.length;i++){
						html += "<option value='"+thirdDepartments[i].id+"'>"+thirdDepartments[i].name+"</option>";
					}
					html += "<select>";
				}	
			}
			showDepartment.innerHTML=html;
		});
	}
	
	
	function submit(){
		$("#myform").submit();
	}
	
	
</script>
</head>
<body>
	<div class="row  border-bottom">
		<div class="basic">
        	<p>企业管理</p>
            <span><a href="<c:url value='/admin/main'/>">首页</a>>
            <a href="#" >企业管理</a>><a><strong>员工管理</strong></a></span>
        </div>
		<div class="col-lg-2"></div>
	</div>
	<form id ="myform" action ="${ctx}/admin/employee/list">
		<input type ="hidden" id ="ipt_sortName" value ="${sortName }" name = "sortName"/>
		<input type ="hidden" id ="ipt_sortType" value = "${sortType}" name = "sortType"/>
		<input type ="hidden" id="ipt_department" name="departmentId" />
		<input type ="hidden" id="ipt_roleId" value="${roleId}" name="roleId" />
		<input type ="hidden" id="ipt_state" value="${state}" name="state" />
		<input type ="hidden" id="formkeyword" value="${keyword }" name="keyword" />
 	</form>
	<div class="animated fadeInRight employee">
             <form name="searchform" id="searchform" action="${ctx }/admin/employee/list">
             <dl>
           	 <dt>所属部门：</dt>
             <dd>
             	<!-- <p class="e_class" id="alldepartment" >全部部门</p> -->
                 <div id="showDepartment"></div>
             </dd>
           </dl> 
            <dl>
           	 <dt>所属角色：</dt>
             <dd>
	                 <select name="roleId" class="e_inpt" id="roleId" >
	                 		<option value="" >全部</option>
	                 	  <c:forEach  var="role" items="${roles}">
                    		<option <c:if test="${role.id eq roleId }">selected</c:if> value='${role.id }'>${role.rolename }</option> 
                    	  </c:forEach> 
	                </select> 
             </dd>
           </dl>
           <dl>
           	 <dt>账号状态：</dt>
             <dd>
             	<p id="allstate">全部状态</p>
                	<p id="normal" >正常</p>
                	<p id="frooze" >冻结</p>
             </dd>
           </dl> 
           <dl>
           	 <dt>搜索：</dt>
             <dd>
             	<input type="text" id="keyword" value="${keyword }" placeholder="输入要搜索的关键词" class="e_sou">
             </dd>
           </dl>
           <dl>
           	 <dt>操作：</dt>
             <dd>
             	<a href="#"><samp id="query" >查询</samp> </a>
                <a href="${ctx}/admin/employee/toAddEmployee" ><samp>新增员工</samp></a>
                <shiro:hasPermission name="USER_EXPORT">
                         		<a href="#" ><samp id="export_btn" >导出EXCEL</samp> </a> 
               </shiro:hasPermission>
               <a href="javascript:void(0)"><samp class="e_suo" id="delemployee" >批量禁用</samp></a>
             </dd>
           </dl>
           </form>
           </div>
            <div class="username-text animated fadeInRight">
            	<div class="row">
			<div class="col-lg-12">
				<div class="ibox float-e-margins">

						<div class="table-responsive">
            <div class="level" style="border:none" >
	            <table width="100%" border="0" align="center" class="table table-striped" cellpadding="0" cellspacing="0">
		                 <thead>
		                 	 <tr>
		                     	<td class="l_width">
		                        <input type="checkbox" id="allcheck" class="l_in">
		                        </td>
		                        <td><strong>员工姓名</strong></td>
		                        <td><strong>所属部门</strong></td>
		                        <td><strong>角色</strong></td>
		                        <td><strong>状态</strong></td>
		                        <td><strong>操作</strong></td>
		                     </tr>
		                 </thead>
		                 <tbody>
		                	
		                     <c:forEach var="employee" items="${employees.getContent()}">
			                     	<tr bgcolor="f3f3f4">
			                     	<td><input type="checkbox" name="employee" value="${employee.id }" class="l_in childcheckbox"></td>
			                        <td>${employee.realname }</td>
			                        <td>${employee.userInfoEmployee.department.name }</td>
			                        <td>${employee.role.rolename }</td>       
			                        <td class="l_color">
			                        	<c:choose>
			                        	<c:when test="${employee.userType ne 'SALESMAN' }">
				                        	<c:choose>
												 <c:when test="${employee.state.ordinal() eq 0 }">
													<button
															class="btn changeState btn btn-danger btn-sm demo4"
															changeId="${employee.id }" state="close">禁用</button>
												</c:when>
												<c:otherwise>
													<button
															class="changeState btn btn-primary btn-sm demo4"
															changeId="${employee.id }" state="open">启用</button>
												</c:otherwise> 
											</c:choose>
										</c:when>
										<c:otherwise>
											<%-- <c:choose>
												 <c:when test="${employee.state.ordinal() eq 0 }">
													<button	class="btn  btn btn-danger btn-sm demo4"
															 state="close">冻结</button>
												</c:when>
												<c:otherwise>
													<button class="btn btn-primary btn-sm demo4"
															 state="open">启用</button>
												</c:otherwise> 
											</c:choose> --%>
										</c:otherwise>
										</c:choose>
			                        </td>
			                        <td><a href="${ctx }/admin/employee/toEditEmployee/${employee.id}?lastPage=${employees.getNumber()+1}"><span><img src="${ctx }/static/brd/img/bjt1.png"></span></a></td>
		                      </tr> 
		                     </c:forEach>
		                     </tbody>
		                     </table>
		                      <div class="text-right">

							<div class="btn-group">
								<div class="dataTables_paginate paging_simple_numbers"
									id="DataTables_Table_0_paginate">
									<tags:pagination page="${employees}" paginationSize="10"
										hrefSubfix="firstDepartmentId=${firstDepartmentId }&secondDepartmentId=${secondDepartmentId }&thirdDepartmentId=${thirdDepartmentId }&sortName=${sortName}&sortType=${sortType }&keyword=${keyword}&roleId=${roleId}&state=${state}	"
										hrefPrefix="${ctx}/admin/employee/list" />
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