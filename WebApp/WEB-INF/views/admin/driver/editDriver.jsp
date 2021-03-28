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
<script src="${ctx}/static/js/input-number-change.js"></script>
<title>编辑司机</title>
<script type="text/javascript">
	$(function() {
		activeNav("2");
		
		$(":radio").click(function(){
			var value = $(this).val();
			if(value==0){
				$("#user").css("display","block")
				$("#manger").css("display","none");
				$("#seller").css("display","none")
			}else if(value==1){
				$("#user").css("display","none")
				$("#manger").css("display","block");
				$("#seller").css("display","none")
			}else if(value==2){
				$("#user").css("display","none")
				$("#manger").css("display","none");
				$("#seller").css("display","block")
			}
		})
		
		
		
		$("#submitform").click(function(){
			submitAdd();
			
		});
		
		function submitAdd(){
            var sex=$('input[name="userType"]:checked ').val();
			var mangername =$("#mangername").val();
            var phone =$("#phone").val();
            var password =$("#password").val();
            var idCard =$("#idCard").val();
            var carNo = $("#carNo").val();
			var driverNo = $("#driverNo").val();
			var carMark = $("#carMark").val();
            var carColor = $("#carColor").val();
			if(password.length==0){
				alert("请输入密码");
				return ;
			}
            if(sex==null || sex=='undefined'){
                alert("请选择性别");
                return ;
            }
			if(password.length<6 || password.length>15){
				alert("密码只能在6-15位之间");
				return ;
			}
			if(phone.trim().length!=11){
				alert("请输入11位手机号码");
				return ;
			}
            if(mangername.trim().length==0 || mangername==" "){
                alert("请输入姓名")
                return ;
            }
            if(idCard.trim().length==0 || idCard==" "){
                alert("请输入身份证")
                return ;
            }
            if(carNo.trim().length==0 || carNo==" "){
                alert("请输入车牌号")
                return ;
            }
            if(driverNo.trim().length==0 || driverNo==" "){
                alert("请输入驾驶证")
                return ;
            }
            if(carMark.trim().length==0 || carMark==" "){
                alert("请输入车辆型号")
                return ;
            }
            if(carColor.trim().length==0 || carColor==" "){
                alert("请输入车辆颜色")
                return ;
            }

			$("#submitform").unbind("click");
			password =hex_md5($("#password").val());
			$.post("${ctx}/admin/driver/editDriver",{sex:sex,password:password,name:mangername,id:'${tbDriver.id}',phone:phone,idCard:idCard
					,carNo:carNo,driverNo:driverNo,carMark:carMark,carColor:carColor},function(data){
					if(data.code==0){
						showCallBackDialog("添加成功",function(){
							location.href = "${ctx}/admin/driver/list";
						})
					}else{
						alert(data.mes);
						$("#submitform").bind("click",function(){
							submitAdd();
						});
					}
			}); 
		}
	});

	
</script>
</head>
<body>
<div class="row  border-bottom">
	<div class="basic">
        <p>编辑司机</p>
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a href="#" >司机管理</a>><a><strong>编辑司机</strong></a></span>
    </div>
    </div>
	<div class="new_emp animated fadeInRight">
            <div class="new_xinxi"><p><font>司机信息</font></p></div>
            <form id="myform" action="${ctx }/admin/driver/addDriver" >
	            <div class="new_text">
					<dl>
						<dt>性别：</dt>
						<dd>
							<ul>
								<c:choose>
								<c:when test="${driverforms.state eq 1 }">
								<li class="us" id="r_was1" ><input name="userType" checked="checked" value="1" type="radio" class="n_rad" >男</li>
								<li id="r_was2" ><input type="radio" name="userType" value="2" class="n_rad">女</li>
								</c:when>
									<c:otherwise>
										<li class="us" id="r_was1" ><input name="userType"  value="1" type="radio" class="n_rad" >男</li>
										<li id="r_was2" ><input type="radio" name="userType" checked="checked" value="2" class="n_rad">女</li>
									</c:otherwise>
								</c:choose>
							</ul>
						</dd>
					</dl>
	                <div class="new_batt" >
						<dl>
							<dt>姓名：</dt>
							<dd><input type="text" name="mangername" maxlength="5" id="mangername" value="${tbDriver.userName}" class="n_la"
									   onKeyDown="gbcount1(this.form.mangername,this.form.userd2,5,'姓名不能超过5个字符');"  onKeyUp="gbcount1(this.form.mangername,this.form.userd2,5,'姓名不能超过5个字符');" >
								<input style="width: 40px;border:none;background:#fff;" name="userd2" value="0/5" readonly="readonly" />
							</dd>
						</dl>
						<dl>
							<dt>手机号码：</dt>
							<dd><input type="text" name="phone" id="phone" value="${tbDriver.mobileno}" maxlength="11"  class="n_la" ></dd>
						</dl>
	                 <dl>
	                    <dt>密码：</dt>
	                    <dd><input type="password" name="password" maxlength="15" id="password" placeholder="最多15位的密码"  class="n_la" ></dd>
	                </dl>
						<dl>
							<dt>身份证号：</dt>
							<dd><input type="text" name="idCard" id="idCard" value="${tbDriver.idCard}" maxlength="11"  class="n_la" ></dd>
						</dl>
						<dl>
							<dt>车牌号：</dt>
							<dd><input type="text" name="carNo" id="carNo" value="${tbDriver.carNo}" maxlength="11"  class="n_la" ></dd>
						</dl>
	                <div id="user" >
	                	<dl >
	                		<dt>驾驶证：</dt>
	                		<dd><input type="text" name="driverNo" id="driverNo" value="${tbDriver.driverNo}" class="n_la" /> </dd>
	                	</dl>
	                </div>
						<dl>
							<dt>车辆型号：</dt>
							<dd><input type="text" name="carMark" id="carMark" value="${tbDriver.carMark}" maxlength="11"  class="n_la" ></dd>
						</dl>
						<dl>
							<dt>车辆颜色：</dt>
							<dd><input type="text" name="carColor" id="carColor" value="${tbDriver.carColor}" maxlength="11"  class="n_la" ></dd>
						</dl>
	            </div>
	            </div>
            </form>
            <div class="submit" align="center">
                     <div class="submit_trr">
                        <a href="javascript:void(0)" id="submitform" ><p>提交</p></a>
                        <a href="${ctx}/admin/driver/list"><p>返回</p></a>
                     </div>
                 </div>
        </div> 

</body>
</html>