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
<title>编辑员工</title>
<script type="text/javascript">
	$(function() {
		activeNav2("1","1_3");
		
		var type = '${type}';
		var e_was1 = $("#e_was1");
		var e_was2 = $("#e_was2");
		var e_was3 = $("#e_was3");
		var e_was4 = $("#e_was4");
		if(type=='brokerage'){
			e_was1.attr("class","r_bj");
		}else if (type=='withdraw'){
			e_was2.attr("class","r_bj");
		}else if (type=='apprentice'){
			e_was3.attr("class","r_bj");
		}else if(type=='loginlog'){
			e_was4.attr("class","r_bj");
		}
		
		$("#e_was1").click(function(){
			location.href="${ctx}/admin/employee/toEditEmployee/${employeeId}?type=brokerage&lastPage=${lastPage}"
		});
		$("#e_was2").click(function(){
			location.href="${ctx}/admin/employee/toEditEmployee/${employeeId}?type=withdraw&lastPage=${lastPage}"
		});
		$("#e_was3").click(function(){
			location.href="${ctx}/admin/employee/toEditEmployee/${employeeId}?type=apprentice&lastPage=${lastPage}"
		});
		$("#e_was4").click(function(){
			location.href="${ctx}/admin/employee/toEditEmployee/${employeeId}?type=loginlog&lastPage=${lastPage}"
		});
		
		
		$("#downgrade").click(function(){
			var id ='${employee.id}';
			var phone ='${employee.mobileno}';
			if(phone.trim().length!=11){
				alert("该业务员不是正确手机号码请去修改");
				return ;
			}
			$.post("${ctx}/admin/employee/isRightPhone",{phone:phone},function(data){
				if(data.code==1){
					alert("该业务员不是正确手机号码请去修改");
					return ;
				} else {
					showCallBackDialog("是否确定降级?",function(){
						$.post("${ctx}/admin/employee/drowngrade",{id:id},function(data){
							if(data.code==0){
								showCallBackDialog("降级成功",function(){
									location.href = "${ctx}/admin/employee/list?page=${lastPage}";
								})
							}else{
								alert(data.mes);	
							}				
						});		
					});
								
				}				
			});		
			
		});
		
		
		$("#submit").click(function(){
			functionSumbmit();
		});
		
		function  functionSumbmit(){
			var mobileno = $("#mobileno").val();
			var departmentId;
			var firstDepartmentId = $("#firstDepartmentId").val();
			var secondDepartmentId=	$("#secondDepartmentId").val();
			var thirdDepartmentId =	$("#thirdDepartmentId").val();
			var departmentId=firstDepartmentId;
			
			if(secondDepartmentId!='undefined' && secondDepartmentId>0 ){
				departmentId = secondDepartmentId;
			}
			if(thirdDepartmentId!='undefined' && thirdDepartmentId>0 ){
				departmentId = thirdDepartmentId;
			}
			
			var roleId = $("#roleId").val();
			var state=$('input[name="state"]:checked ').val();
			var resetPassword=$('input[name="resetPassword"]:checked ').val(); 
			var password =$("#password").val();
			var id ='${employee.id}';			
			if(resetPassword==0){
				if(password.trim().length<6|| password.trim().length>15){
					alert("请输入6-15位密码")
					return ;
				}
				password=hex_md5(password);
			}
			$("#submit").unbind("click");
			$.post("${ctx}/admin/employee/editEmployee",{mobileno:mobileno,departmentId:departmentId
					,roleId:roleId,state:state,resetPassword:resetPassword,password:password,id:id},function(data){
				if(data.code==0){
					showCallBackDialog("修改成功",function(){
						location.href = "${ctx}/admin/employee/list?page=${lastPage}";
					})
				}else{
						alert(data.mes);
						$("#submit").bind("click",function(){
							functionSumbmit();
						});
				}						
			}) 
			
		}
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
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a href="#" >企业管理</a>><a href="#" >员工管理</a>><a><strong>${employee.realname }</strong></a></span>
    </div>
</div>	
	
	<div class="new_emp animated fadeInRight">
		<div class="row">
			<div class="col-lg-12">
				<div class="ibox float-e-margins">
			<div class="ibox-content">
					 <div class="new_xinxi"><p><font>员工信息</font></p></div>		 			 
            <div class="new_batt n_one">
            	<dl>
                    <dt>账号：</dt>
                    <dd>${employee.userInfoEmployee.userno}</dd>
                </dl>
                <dl>
                    <dt>员工姓名：</dt>
                    <dd>${employee.realname }</dd>
                </dl>
                <dl>
                    <dt>注册时间：</dt>
                    <dd>${util:formatNormalDate(employee.createdate) }</dd>
                </dl>
                <dl>
                    <dt>手机号码：</dt>
                    <dd><input type="text" class="n_la" id="mobileno" maxlength="11" value="${employee.mobileno}"></dd>
                </dl>
                <dl>
                    <dt>所属部门：</dt>
                    <dd>
                    <form id ="myform" action="${ctx}/admin/employee/toEditEmployee/${employee.id}" >
                    <input type="hidden" name="type" value="${type }" />
                    <input type="hidden" name="lastPage" value="${lastPage }" >
                    <c:choose >
                   	<c:when test="${firstDepartmentId ne '-1' ||  secondDepartmentId ne '-1' || thirdDepartmentId ne '-1' }" >
				                    <c:if test="${  empty secondDepartments  && empty thirdDepartments }"  >
				                     <select  class="n_la"  id="firstDepartmentId"  name="firstDepartmentId" onchange="submitform()">
				                    	<c:forEach var="department"  items="${firstDepartments}" > 
				                    		<option <c:if test="${department.id eq firstDepartmentId }">selected</c:if> value='${department.id }'>${department.name }</option> 
				                    	</c:forEach>
				                    </select> 
				                    </c:if>
				                    <c:if test="${not  empty secondDepartments  && empty thirdDepartments }"  >
				                    	<select  class="n_la" name="firstDepartmentId" id="firstDepartmentId" onchange="submitform()">
				                    	<c:forEach var="department"  items="${firstDepartments}" > 
				                    		<option <c:if test="${department.id eq firstDepartmentId }">selected</c:if> value='${department.id }'>${department.name }</option> 
				                    	</c:forEach>
				                    	</select>
				                    	<select name="secondDepartmentId" id="secondDepartmentId" class="n_la" id="departmentId" onchange="submitform()" >
				                    		<option value="" >请选择</option>
				                    		<c:forEach var ="department" items="${secondDepartments }">
				                    			<option <c:if test="${department.id eq secondDepartmentId }">selected</c:if> value='${department.id }'>${department.name }</option> 
				                    		</c:forEach>
				                    	</select>
				                    </c:if>
				                    <c:if test="${not  empty thirdDepartments && not  empty secondDepartments }"  >
					                    	<select  class="n_la" id="firstDepartmentId"  name="firstDepartmentId" onchange="submitform()">
					                    	<c:forEach var="department"  items="${firstDepartments}" > 
					                    		<option <c:if test="${department.id eq firstDepartmentId }">selected</c:if> value='${department.id }'>${department.name }</option> 
					                    	</c:forEach>
				                   			 </select>
					                    	<select name="secondDepartmentId" id="secondDepartmentId"  class="n_la" onchange="submitform()" >
					                    		<option value="" >请选择</option>
					                    		<c:forEach var ="department" items="${secondDepartments }">
					                    			<option <c:if test="${department.id eq secondDepartmentId }">selected</c:if> value='${department.id }'>${department.name }</option> 
					                    		</c:forEach>
					                    	</select>
								              <select name="thirdDepartmentId" id="thirdDepartmentId"  class="n_la" >
								                  	<option value="">请选择</option>
								                  	<c:forEach var="department" items="${thirdDepartments }">
								                  	<option <c:if test="${department.id eq thirdDepartmentId }">selected</c:if> value='${department.id }'>${department.name }</option> 
								                  </c:forEach>
								                  </select>
								        </c:if>
                   	</c:when>
					<c:otherwise>
                    	<c:choose>
							<c:when test="${employee.userInfoEmployee.department.level==1   }" >
								<select class="n_la" id="firstDepartmentId"  name="firstDepartmentId" onchange="submitform()" >
									<c:forEach var ="department" items="${firstDepartments}" >
										<option <c:if test="${department.id eq employee.userInfoEmployee.department.id }">selected</c:if> value='${department.id }'>${department.name }</option> 
									</c:forEach>
								</select>
								<c:if test="${not empty employee.userInfoEmployee.department.sons }" >
								<select name="secondDepartmentId" id="secondDepartmentId" class="n_la" onchange="submitform()" >	
									<option value="" >请选择</option>
									<c:forEach var = "department" items="${employee.userInfoEmployee.department.sons}" >
										<option  value="${department.id }" >${department.name } </option>
									</c:forEach>
									</select>
								</c:if>
							</c:when>
							<c:when test="${employee.userInfoEmployee.department.level==2 }" >
								<select name="firstDepartmentId" id="firstDepartmentId" onchange="submitform()" class="n_la">
									<c:forEach var ="department"  items="${firstDepartments}" >
										<option <c:if test="${department.id eq employee.userInfoEmployee.department.parent.id }">selected</c:if> value='${department.id }'>${department.name }</option> 
									</c:forEach>
								</select>
								<select name="secondDepartmentId" id="secondDepartmentId"  class="n_la">
									<option value="" >请选择</option>
									<c:forEach var ="department" items="${employee.userInfoEmployee.department.parent.sons}" >
										<option <c:if test="${department.id eq employee.userInfoEmployee.department.id }">selected</c:if> value='${department.id }'>${department.name }</option> 
									</c:forEach>
								</select>
								<c:if test="${not empty employee.userInfoEmployee.department.sons }" >
								<select name="thirdDepartmentId" id="thirdDepartmentId"  id="departmentId" class="n_la" onchange="submitform()" >	
									<option value="" >请选择</option>
									<c:forEach var = "department" items="${employee.userInfoEmployee.department.sons}" >
										<option  value="${department.id }" >${department.name } </option>
									</c:forEach>
									</select>
								</c:if>		
							</c:when>
							<c:otherwise>						
								<select name="firstDepartmentId" id="firstDepartmentId" onchange="submitform()" class="n_la">
									<c:forEach var ="department"  items="${firstDepartments}" >
										<option <c:if test="${department.id eq employee.userInfoEmployee.department.parent.parent.id }">selected</c:if> value='${department.id }'>${department.name }</option> 
									</c:forEach>
								</select>
								<select name="secondDepartmentId" id="secondDepartmentId" onchange="submitform()" class="n_la">
									<option value="" >请选择</option>
									<c:forEach var ="department" items="${secondDepartments}" >
										<option <c:if test="${department.id eq employee.userInfoEmployee.department.parent.id }">selected</c:if> value='${department.id}'>${department.name }</option> 
									</c:forEach>
								</select>
								<select name="thirdDepartmentId"  class="n_la" id="thirdDepartmentId" >
									<option value="" >请选择</option>
									<c:forEach var ="department" items="${thirdDepartments}" >
										<option <c:if test="${department.id eq employee.userInfoEmployee.department.id }">selected</c:if> value='${department.id }'>${department.name }</option> 
									</c:forEach>
								</select> 		
							</c:otherwise>
						</c:choose>
					</c:otherwise>
					</c:choose>
					</form>
                   </dd>
                </dl>
                <dl>
                    <dt>所属角色：</dt>
                    <dd>
                    <c:choose>
                    <c:when test="${employee.userType ne 'SALESMAN' }" >
                    <select id="roleId" name="roleId" class="n_la">
                    	 <c:forEach var="role" items="${roles}" >
							<option <c:if test="${role.id eq employee.role.id }">selected</c:if> value='${role.id }'>${role.rolename }</option> 
                    	</c:forEach> 	
                    </select>
                    </c:when>
                    <c:otherwise>
                    	业务员
                        <input id="roleId" name="roleId" type="hidden" value="${employee.role.id }" >
                    </c:otherwise>
                    </c:choose>
                   </dd>
                </dl>
            </div>
            <div class="new_xinxi"><p><font>操作</font></p></div>
                <div class="new_batt n_two" style="height:auto;">
                    <c:if test="${employee.userType ne 'SALESMAN' }" >
                     <dl>
                        <dt>账号状态：</dt>
                        <dd>
                        	<c:if test="${employee.state.ordinal() eq 0 }" >
                        		<span><input type="radio" name="state" value="0" class="n_zt">启用</span>
                            	<span><input type="radio" name="state" value="1" checked="checked" class="n_zt">冻结</span>
                            </c:if>
                            <c:if test="${employee.state.ordinal() eq 1 }" >
                        		<span><input type="radio" name="state" value="0" checked="checked" class="n_zt">启用</span>
                            	<span><input type="radio" name="state" value="1" class="n_zt">冻结</span>
                            </c:if>
                        </dd>
                    </dl>
                    </c:if>
                   
                    <c:if test="${employee.userType eq 'SALESMAN' }" >
                     <div style="hidden" >
                     <dl>
                        <dt>账号状态：</dt>
                        <dd>
                        	<c:if test="${employee.state.ordinal() eq 0 }" >
                        		<span><input type="radio" name="state" value="0" class="n_zt">启用</span>
                            	<span><input type="radio" name="state" value="1" checked="checked" class="n_zt">冻结</span>
                            </c:if>
                            <c:if test="${employee.state.ordinal() eq 1 }" >
                        		<span><input type="radio" name="state" value="0" checked="checked" class="n_zt">启用</span>
                            	<span><input type="radio" name="state" value="1" class="n_zt">冻结</span>
                            </c:if>
                        </dd>
                    </dl>
                    </div>
                    </c:if>
                    <dl>
                        <dt>密码重置：</dt>
                        <dd>
                        	<span><input type="radio" value="0"  name="resetPassword" class="n_zt">是</span>
                            <span><input type="radio" value="1" checked="checked" name="resetPassword" class="n_zt">否</span>
                        </dd>
                    </dl>
                    <dl>
                        <dt>输入密码：</dt>
                        <dd><input type="password" id="password" maxlength="15" class="n_la" value=""></dd>
                    </dl>
                    <c:if test="${employee.userType eq 'SALESMAN' }" >
                    <dl>
	                    <dt>业务员更改身份：</dt>
	                   	 <dd><a href="#javascript:void(0)"><samp id="downgrade" >降级为融资经理</samp></a></dd>
		                </dl>
	                </c:if>
                </div>
            <div class="submit">
                     <div class="submit_trr">
                        <a href="javascript:void(0)" id="submit" ><p>保存修改</p></a>
                        <a href="${ctx}/admin/employee/list?page=${lastPage}" ><p>返回</p></a>
                     </div>
                 </div>
                 <div class="record">
                  <div class="new_xinxi"><p><font>员工信息</font></p></div>
                      <div class="record_table">
                      	<div class="record_age">
                            <ul >
                            	<c:if test="${employee.userType eq 'SALESMAN' }" >
	                                <li id="e_was1" value="brokerage" >获取佣金记录</li>
	                                <li id="e_was2" value="withdraw" >提现记录</li>
	                                <li id="e_was3" value="apprentice" >收徒记录</li>
                                </c:if>
                                <li id="e_was4" value="loginlog"  >登录日志</li>
                            </ul>
                        </div>
                        <c:choose>
                        <c:when test="${type eq 'brokerage' }" >
	                        <div class="record_mex" >
	                        	<table width="100%" cellpadding="1" cellspacing="1" align="center" >
	                        		<tbody>
	                        			<tr>
	                        				<td>订单号</td>
	                        				<td>确认人</td>
	                        				<td>与会员关系</td>
	                        				<td>抽佣比例</td>
	                        				<td>获得佣金</td>
	                        				<td>佣金发放时间</td>
	                        			</tr>
	                        			<c:forEach var="list" items="${page.getContent() }" >
	                        				<tr>
	                        					<td>${list.order.orderNo }</td>
	                        					<td>${list.confirmName }</td>
	                        					<td>${list.relate.getDes() }</td>
	                        					<td>${list.haveBrokerageRate }</td>
	                        					<td>${list.haveBrokerage }</td>
	                        					<td>${list.sendBrokerage }</td>
	                        				</tr>
	                        			</c:forEach>	
	                        		</tbody>
	                        	</table>
	                        		<div class="btn-group"style="float:right" >
												<div class="dataTables_paginate paging_simple_numbers"
													id="DataTables_Table_0_paginate">
													<tags:pagination page="${page}" paginationSize="10"
														hrefSubfix="type=brokerage&lastPage=${lastPage}"
														hrefPrefix="${ctx}/admin/employee/toEditEmployee/${employee.id }" />
												</div>
								    </div>
	                        </div>
                        </c:when>
                        <c:when test="${type eq 'withdraw' }" >
                        	 <div class="record_mex" >
	                        	<table width="100%" cellpadding="1" cellspacing="1" align="center" >
	                        		<tbody>
	                        			<tr>
	                        				<td>申请时间</td>
	                        				<td>完成时间</td>
	                        				<td>提现金额</td>
	                        				<td>到账方式</td>
	                        			</tr>
	                        			<c:forEach var="list" items="${page.getContent() }" >
	                        				<tr>
	                        					<td>${list.createTime }</td>
	                        					<td>${list.sendDate }</td>
	                        					<td>￥${list.money }</td>
	                        					<td>银行卡(${list.bankaccount })</td>
	                        				</tr>
	                        			</c:forEach>	
	                        		</tbody>
	                        	</table>
	                        		<div class="btn-group"style="float:right" >
												<div class="dataTables_paginate paging_simple_numbers"
													id="DataTables_Table_0_paginate">
													<tags:pagination page="${page}" paginationSize="10"
														hrefSubfix="type=withdraw&lastPage=${lastPage}"
														hrefPrefix="${ctx}/admin/employee/toEditEmployee/${employee.id }" />
												</div>
								    </div>
	                        </div>
                        </c:when>
                          <c:when test="${type eq 'apprentice' }" >
                        	 <div class="record_mex" >
	                        	<table width="100%" cellpadding="1" cellspacing="1" align="center" >
	                        		<tbody>
	                        			<tr>
	                        				<td>注册时间</td>
	                        				<td>会员帐号</td>
	                        				<td>姓名</td>
	                        				<td>订单</td>
	                        				<td>徒弟</td>
	                        			</tr>
	                        			<c:forEach items="${page.getContent()}" var="list">	
	                        			<tr>
	                        				<td>${util:formatNormalDate(list.registerTime) }</td>
	                        				<td>${list.account }</td>
	                        				<td>${list.name } </td>
	                        				<td>${list.orderSum }</td>
	                        				<td>${list.sonsSum }</td>
	                        			</tr>
	                        			</c:forEach>	
	                        		</tbody>
	                        	</table>
	                        		 <div class="btn-group"style="float:right" >
												<div class="dataTables_paginate paging_simple_numbers"
													id="DataTables_Table_0_paginate">
													<tags:pagination page="${page}" paginationSize="10"
														hrefSubfix="type=apprentice&lastPage=${lastPage}"
														hrefPrefix="${ctx}/admin/employee/toEditEmployee/${employee.id }" />
												</div>
								    </div> 
	                        </div>
                        </c:when>
                        	    
                        	    
                        <c:when test="${type eq 'loginlog' }" >
                        	 <div class="record_mex" >
	                        	<table width="100%" cellpadding="1" cellspacing="1" align="center" >
	                        		<tbody>
	                        			<tr>
	                        				<td>登录时间</td>
	                        				<td>登录IP</td>
	                        				<td>登录地址</td>
	                        				<td>累计登录次数</td>
	                        			</tr>
	                        			<c:forEach var="list" items="${page.getContent() }" >
	                        				<tr>
	                        					<td>${util:formatNormalDate(list.loginDate)}</td>
	                        					<td>${list.userLoginIp }</td>
	                        					<td>${list.loginAddress}</td>
	                        					<td>${list.loginTimes }</td>
	                        				</tr>
	                        			</c:forEach>	
	                        		</tbody>
	                        	</table>
	                        		<div class="btn-group"style="float:right" >
												<div class="dataTables_paginate paging_simple_numbers"
													id="DataTables_Table_0_paginate">
													<tags:pagination page="${page}" paginationSize="10"
														hrefSubfix="type=loginlog&lastPage=${lastPage}"
														hrefPrefix="${ctx}/admin/employee/toEditEmployee/${employee.id }" />
												</div>
								    </div>
	                        </div>
                        </c:when>         
                        </c:choose>
                     </div>
                     </div>
                 </div>
					</div>
				</div>
			</div>
		</div>
</body>
</html>