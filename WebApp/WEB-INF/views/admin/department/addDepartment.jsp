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
<script src="${ctx}/static/js/input-number-change.js"></script>
<title>添加部门</title>
<script type="text/javascript">
	$(function() {
		activeNav2("1","1_1");
		
		var departmentlevel = '${departmentlevel}';
		if(departmentlevel== 0){
			$("#department1").attr("checked","checked");
		}else if (departmentlevel == 1){
			$("#department2").attr("checked","checked");
		}else if(departmentlevel== 2){
			$("#department3").attr("checked","checked");
		}		
		$(":radio").click(function(){
			 $('#searchForm').submit();	
			
		}); 
		 
		$(".submitDepart").click(function(){
			submitDepart();
		});
		
		function submitDepart(){
			$(".submitDepart").attr("disabled","disabled");
			var departname = $("#departname").val();
			var parentDeparmentId = $("#parentDeparmentId").val();
			var departmentlevel = "${departmentlevel}";
			 if(departmentlevel==2){
				if( parentDeparmentId==null){
					alert("未选择二级部门");
					return ;
				}
			}
			if(departname.trim().length==0){
				alert("部门名不能为空");
				return ;
			} 
			 $(".submitDepart").unbind("click");
			 $.post("${ctx}/admin/department/addDepartment",{departname:departname,departmentlevel:departmentlevel,parentDeparmentId:parentDeparmentId},
					function(data){
				if(data.code==0){
					showCallBackDialog("添加成功",function(){
						location.href = "${ctx}/admin/department/list";
					})
				}else{
					alert(data.mes);
					$(".submitDepart").bind("click",function(){
						submitDepart()
					});
				}
				
			}) 
		}
		
		
		$(".pageBack").click(function(){
			location.href="${ctx}/admin/department/list";
		})
		
 		
		
	});
	function submitform() {
		$('#searchForm').submit();	
		
	}
</script>
</head>
<body>
<div class="row  border-bottom">
	<div class="basic">
        <p>企业管理</p>
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a href="#" >企业管理</a>><a href="#" >部门管理</a>><a><strong>添加部门</strong></a></span>
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
			                <div class="edit_text">
			                    <div class="edit_top">
			                    	<span>部门级别：</span>
			                        <ul>
			                            <li> <input type="radio" value="0" id="department1" class="e_radio"  name="departmentlevel" >1级 </li>
										<li> <input type="radio" value="1" id="department2" class="e_radio" name="departmentlevel" >2级  </li>
										<li> <input type="radio" value="2" id="department3" class="e_radio" name="departmentlevel" >3级  </li>
			                        </ul>
			                    </div>
			                    <c:if test="${departmentlevel==0}" >
				                    <div class="edit_box" >
				                        <dl>
				                            <dt>部门名称：</dt>
				                            <dd><input type="text" maxlength="8" name="departname" id="departname" class="e_box"
				                             onKeyDown="gbcount(this.form.departname,this.form.used,8);"  onKeyUp="gbcount(this.form.departname,this.form.used,8);" >
		                   					<input style="width:25px;border:none;background:#fff;" name="used" value="0/8" readonly="readonly" /> 
				                            </dd>
				                        </dl>
				                        <samp class="submitDepart" >提交</samp>
				                        <span class="pageBack" >返回</span>
				                    </div>	
			                    </c:if>
			                    <c:if test="${departmentlevel== 1}">
			                    	<div class="edit_box" >
			                    		<dl>
			                    			<dt>一级部门: </dt>
			                    			<dd>
												 <select class="e_box" name="firstDepartId" id="parentDeparmentId" >
			                                		<c:forEach var='item' items='${fitstDepartList }' >
			                                		  <option <c:if test="${item.id eq firstDepartId }">selected</c:if> value='${item.id }'>${item.name }</option>                        	
			                                		</c:forEach> 
                                				</select>
                                			</dd> 
					                    </dl>
					                        <dl>
					                            <dt>二级部门名称：</dt>
					                            <dd><input type="text" maxlength="8" name="departname" id="departname" class="e_box"
					                            onKeyDown="gbcount(this.form.departname,this.form.used,8);"  onKeyUp="gbcount(this.form.departname,this.form.used,8);" >
		                   					<input style="width:25px;border:none;background:#fff;" name="used" value="0/8" readonly="readonly" /> </dd>
					                        </dl>
					                        <samp class="submitDepart" >提交</samp>
				                        	<span class="pageBack" >返回</span>
			                    	</div>
			                    </c:if> 
			                   	<c:if test="${departmentlevel==2 }"> 
					                   	<div class="edit_box" >
					                   		<dl>
					                            <dt>一级部门：</dt>
					                            <dd>
					                                <select class="e_box" name="firstDepartId" onchange='submitform()' >
				                                		<c:forEach var='item' items='${fitstDepartList }' >
				                                		  <option <c:if test="${item.id eq firstDepartId }">selected</c:if> value='${item.id }'>${item.name }</option>                        	
				                                		</c:forEach> 
                                					</select>
					                            </dd>
					                        </dl>
					                        <dl>
					                            <dt>二级部门：</dt>
					                            <dd>
					                                <select class="e_box"  id="parentDeparmentId" >
				                                		<c:forEach var='item' items='${nextDepartment }' >
				                                		 	<option value='${item.id }'>${item.name }</option>                       	
				                                		</c:forEach> 
                                					</select>
					                            </dd>
					                        </dl>
					                        <dl>
					                            <dt>三级部门名称：</dt>
					                            <dd><input type="text" maxlength="8" name="departname" id="departname" class="e_box"
					                            onKeyDown="gbcount(this.form.departname,this.form.used,8);"  onKeyUp="gbcount(this.form.departname,this.form.used,8);" >
		                   					<input style="width:25px;border:none;background:#fff;" name="used" value="0/8" readonly="readonly" /></dd>
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