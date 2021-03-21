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
<script type="text/javascript" src="${ctx }/static/js/common.js"></script>
<script type="text/javascript" src="${ctx }/static/js/md5-min.js"></script>
<title>新增员工</title>
<script type="text/javascript">
	$(function() {
		activeNav2("1","1_3");
		
		$("#submit").click(function(){
			var firstDepartmentId = $("#firstDepartmentId").val();
			var secondDepartmentId = $("#secondDepartmentId").val();
			var thirdDepartmentId = $("#thirdDepartmentId").val();
			if(thirdDepartmentId=="undefined" || thirdDepartmentId ==null ){
				thirdDepartmentId=-1;
			}
			if(secondDepartmentId=="undefined" || thirdDepartmentId==null ){
				secondDepartmentId =-1;
			}
			
			var roleId = $("#roleId").val();
			var userno = '${userno}';
			var name = $("#name").val();
			var password =$("#password").val();
			var phone = $("#phone").val();
			if(name.trim().length==0){
				alert("请输入姓名");
				return ;
			}
			
			if(name.trim().length>=6){
				alert("名字最多5个字符")
				return ;
			}
			
			if(password.length==0){
				alert("请输入密码");
				return;
			}
			
			if(password.length<6 || password.length>15){
				alert("密码只能在6-15位之间");
				return ;
			}

			password =hex_md5($("#password").val());
			var dto = {
				firstDepartmentId:firstDepartmentId,
				secondDepartmentId:secondDepartmentId,
				thirdDepartmentId:thirdDepartmentId,
				roleId:roleId,
				userno:userno,
				name:name,
				phone:phone,
				password:password
			}
			
			 $.post('${ctx}/admin/employee/addEmployee',dto,function(data){
					if(data.code==0){
						showCallBackDialog("添加成功",function(){
							location.href = "${ctx}/admin/employee/list";
						})
					} else{
						alert(data.mes);
					}			
			}); 
			
			
			
		});
		
		
		$("#backPage").click(function(){
			location.href="${ctx}/admin/employee/list";
		})
		
		
	});
	
	function submitform(){
		$("#myform").submit();
	}
</script>
</head>
<body>
<div class="row  border-bottom">
	<div class="basic">
        <p>企业管理</p>
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a href="#" >企业管理</a>><a href="#" >员工管理</a>><a><strong>新增员工</strong></a></span>
    </div>
	</div>
	<div class="new_emp animated fadeInRight">
		<div class="row">
			<div class="col-lg-12">
				<div class="ibox float-e-margins">
					
					<div class="ibox-content">
						<div class="new_xinxi"><p><font>角色信息</font></p></div>
				            <form id="myform" name="myform" action="${ctx }/admin/employee/toAddEmployee" >
				            <div class="new_batt t_top" >
				                <dl>
				                    <dt>所属部门：</dt>
				                    <dd>
				                    <select  class="n_la"  id="firstDepartmentId"  name="firstDepartmentId" onchange="submitform()">
				                    	<c:forEach var="department" items="${firstDepartmens}" >
				                    		<option <c:if test="${department.id eq firstDepartmentId }">selected</c:if> value='${department.id }'>${department.name }</option> 
				                    	</c:forEach>
				                    </select>
				                    <c:if test="${not  empty  secondDepartments}" >
				                   		
				                    	 <select name="secondDepartmentId" id="secondDepartmentId" class="n_la" onchange="submitform()" >
				                    		 <option value="-1" >请选择</option>
				                    		<c:forEach var ="department" items="${secondDepartments }">
				                    			<option <c:if test="${department.id eq secondDepartmentId }">selected</c:if> value='${department.id }'>${department.name }</option> 
				                    		</c:forEach>
				                    	</select>                   	
				                    </c:if>
				                    <c:if test="${not  empty thirdDepartments && not  empty  secondDepartments }"  >
				                    	<select name="thirdDepartmentId" id="thirdDepartmentId" class="n_la" >
				                    		<option value="-1" >请选择</option>
				                    		<c:forEach var="department" items="${thirdDepartments }">
				                    			<option <c:if test="${department.id eq thirdDepartmentId }">selected</c:if> value='${department.id }'>${department.name }</option> 
				                    		</c:forEach>
				                    	</select>
				                    </c:if>
				                   </dd>
				                </dl>
				                <dl>
				                    <dt>所属角色：</dt>
				                    <dd>
				                    <select name="roleId" id="roleId" class="n_la" >
				                        <c:forEach var="role" items="${roles}">
				                        	<option <c:if test="${role.id eq roleId }">selected</c:if> value='${role.id }'>${role.rolename }</option> 
				                        </c:forEach>
				                    </select>
				                   </dd>
				                </dl>
				                 <dl>
				                    <dt>账号：</dt>
				                    <dd>${userno }</dd>
				                </dl>
				                 <dl>
				                    <dt>员工姓名：</dt>
				                    <dd><input type="text" id="name"   class="n_la" ></dd>
				                </dl>
				                <dl>
				                    <dt>密码：</dt>
				                    <dd><input type="password" value="" placeholder="最多15位的密码"  maxlength="15" id="password"  class="n_la" ></dd>
				                </dl>
				                <dl>
				                    <dt>手机号：</dt>
				                    <dd><input type="text"  id="phone" maxlength="11"   class="n_la" ></dd>
				                </dl>
				            </div>
				            <div class="submit s_left" align="center">
				                     <div class="submit_trr">
				                        <a href="javascript:void(0)" id="submit" ><p>提交</p></a>
				                        <a href="#" id="backPage"><p>返回</p></a>
				                     </div>
				                 </div>
						
						</form>
					
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>