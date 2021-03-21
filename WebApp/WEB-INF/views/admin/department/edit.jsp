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
<script src="${ctx}/static/js/input-number-change.js"></script>
<title>编辑部门</title>
<script type="text/javascript">
	$(function() {
		activeNav2("1","1_1");		
		
		var departmentlevel = '${level}'-1;
		
		if(departmentlevel== 0){
			$("#department1").attr("checked","checked");
		}else if (departmentlevel == 1){
			$("#department2").attr("checked","checked");
		}else if(departmentlevel== 2){

			$("#department3").attr("checked","checked");
		}
		
		$(".submitDepart").click(function(){
			var departname = $("#departname").val();
			var departmentId = '${department.id}';
			
			if(departname.length==0){
				alert("部门名不能为空");
				return ;
			} 
		   $.post("${ctx}/admin/department/editDepartment",{departmentName:departname,departmentId:departmentId},function(data){
				if(data.code==0){
					showCallBackDialog("修改成功",function(){
						location.href = "${ctx}/admin/department/list?page=${page}";
					})
				}else{
					alert(data.mes);
				}
			}); 
			
		});
		
		$(".pageBack").click(function(){
			location.href="${ctx}/admin/department/list?page=${page}";
		})
	});
</script>
</head>
<body>
<div class="row  border-bottom">
     <div class="basic">
        <p>企业管理</p>
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a href="#" >企业管理</a>><a href="#" >部门管理</a>><a><strong>编辑部门</strong></a></span>
    </div>
    </div>
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-12">
				<div class="ibox float-e-margins">
					
					<div class="ibox-content">
				<div class="edit" style="border:none" >
            	<div class="edit_title"><p>部门信息</p></div>
		                <form id='searchForm' method='get' action="${ctx}/admin/department/toAdddepartment">
			                <input type="hidden" value="${page }" >
			                <div class="edit_text">
			                    <div class="edit_top">
			                    	<span>部门级别：</span>
			                        <ul>
			                            <li> <input type="radio" value="1" id="department1" name="departmentlevel" onclick="return false;" >1级 </li>
										<li> <input type="radio" value="2" id="department2"  name="departmentlevel"  onclick="return false;">2级  </li>
										<li> <input type="radio" value="3" id="department3" name="departmentlevel" onclick="return false" >3级  </li>
			                        </ul>
			                    </div>
			                    <c:if test="${level==1}" >
			                    	<div class="edit_box" >
				                        <dl>
				                            <dt>部门名称：</dt>
				                            <dd><input type="text" maxlength="8" value="${department.name}" name="departname" id="departname" class="e_box"
				                             onKeyDown="gbcount(this.form.departname,this.form.used,8);"  onKeyUp="gbcount(this.form.departname,this.form.used,8);" >
		                   					<input style="width:25px;border:none;background:#fff;" id="valueDepartment" name="used" value="${fn:length(department.name) }/8" readonly="readonly" /> 
				                            </dd>
				                        </dl>
				                        <samp class="submitDepart" >提交</samp>
				                        <span class="pageBack" >返回</span>
				                    </div>
			                    </c:if>
			                    <c:if test="${level==2}" >
			                    	<div class="edit_box" >
			                    		<dl>
			                    			<dt>一级部门: </dt>
			                    			<dd>
                                				${department.parent.name }
                                			</dd> 
					                    </dl>
					                        <dl>
					                            <dt>二级部门名称：</dt>
					                            <dd><input type="text" maxlength="8" name="departname" value="${department.name }" id="departname" class="e_box"
					                             onKeyDown="gbcount(this.form.departname,this.form.used,8);"  onKeyUp="gbcount(this.form.departname,this.form.used,8);" >
		                   					<input style="width:25px;border:none;background:#fff;" id="valueDepartment" name="used" value="${fn:length(department.name) }/8" readonly="readonly" /> 
					                            </dd>
					                        </dl>
					                        <samp class="submitDepart" >提交</samp>
				                        	<span class="pageBack" >返回</span>
			                    	</div>          	
			                   	</c:if>
			                   	<c:if test="${level==3}">
			                   		<div class="edit_box" >
					                   		<dl>
					                            <dt>一级部门：</dt>
					                            <dd>
                                					${department.parent.parent.name }
					                            </dd>
					                        </dl>
					                        <dl>
					                            <dt>二级部门：</dt>
					                            <dd>
                                					${department.parent.name }
					                            </dd>
					                        </dl>
					                        <dl>
					                            <dt>三级部门名称：</dt>
					                            <dd><input type="text" maxlength="8" name="departname" value="${department.name}" id="departname" class="e_box"
					                             onKeyDown="gbcount(this.form.departname,this.form.used,8);"  onKeyUp="gbcount(this.form.departname,this.form.used,8);" >
		                   					<input style="width:25px;border:none;background:#fff;" id="valueDepartment" name="used" value="${fn:length(department.name) }/8" readonly="readonly" /> 
					                            </dd>
					                        </dl>
					                       	<samp class="submitDepart" >提交</samp>
				                        	<span class="pageBack" >返回</span>
					                       </div>
			                   	</c:if>
			                 </div>
			                 </form>
		           		 </div>		
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>