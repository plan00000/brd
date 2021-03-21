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
<title>新增会员</title>
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
			var userType=$('input[name="userType"]:checked ').val();
			var password =$("#password").val();
			var mangername =$("#mangername").val();
			var sellername =$("#sellername").val();
			var company = $("#name").val();
			var idcard =$("#idcard").val();
			var phone =$("#phone").val();
			var recommonedPhone =$("#recommonedPhone").val();
			var nickname = $("#nickname").val();
			var company = $("#company").val();
			var sellerAddress = $("#sellerAddress").val();
			if(userType==null || userType=='undefined'){
				alert("请选择会员身份");
				return ;
			}
			if(password.length==0){
				alert("请输入密码");
				return ;
			}
			
			if(password.length<6 || password.length>15){
				alert("密码只能在6-15位之间");
				return ;
			}
			if(userType=='0'){
				if(nickname.trim().length==0){
					alert("请输入昵称");
					return ;
				}
			}
			if(phone.trim().length!=11){
				alert("请输入11位手机号码");
				return ;
			}
			
			
			var name ="";
			if(userType =='1'){
				name = mangername;
				if(mangername.trim().length==0 || mangername==" "){
					alert("请输入姓名")
					return ;
				}
				if(mangername.trim().length>5){
					alert("姓名最多5个字符")
					return ;
				}
			}else if(userType =='2') {
				name= sellername;
				if(sellername.trim().length==0 || sellername==" "){
					alert("请输入姓名")
					return ;
				}
				if(sellername.trim().length>5){
					alert("姓名最多5个字符")
					return ;
				}
				 if(sellerAddress.trim().length==0){
					alert("请输入商家详细地址");
					return ;					
				}	
				 if(company.trim().length==0){
					 alert("请输入公司名");
					 return ;
				 }
				 if(recommonedPhone.trim().length==0){
						alert("请输入推荐人");
						return ;
				} 
			}
			
			if(userType =='1'){
				if(idcard.trim().length!=6){
					alert("请输入身份证后6位");
					return ;
				}
			}
			
			$("#submitform").unbind("click");
			password =hex_md5($("#password").val());
			$.post("${ctx}/admin/user/addUser",{userType:userType,password:password,name:name,idcard:idcard,phone:phone
					,recommonedPhone:recommonedPhone,company:company,nickname:nickname,address:sellerAddress},function(data){
					if(data.code==0){
						showCallBackDialog("添加成功",function(){
							location.href = "${ctx}/admin/user/list";
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
        <p>会员管理</p>
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a href="#" >会员管理</a>><a><strong>新增会员</strong></a></span>
    </div>
    </div>
	<div class="new_emp animated fadeInRight">
            <div class="new_xinxi"><p><font>会员信息</font></p></div>
            <form id="myform" action="${ctx }/admin/user/addUser" >
	            <div class="new_text">
	                 <dl>
	                    <dt>会员身份：</dt>
	                    <dd>
	                    <ul>
		                    <li class="us" id="r_was1" ><input name="userType" checked="checked" value="0" type="radio" class="n_rad" >普通会员</li>
                            <li id="r_was2" ><input type="radio" name="userType" value="1" class="n_rad">融资经理</li>
                            <li id="r_was3" ><input type="radio" name="userType" value="2" class="n_rad">商家</li>
	                   	</ul>
	                   	</dd>
	                </dl> 	                
	                <div class="new_batt" >
	                <dl>
	                    <dt>手机号码：</dt>
	                    <dd><input type="text" name="phone" id="phone" maxlength="11"  class="n_la" ></dd>
	                </dl>
	                 <dl>
	                    <dt>密码：</dt>
	                    <dd><input type="password" name="password" maxlength="15" id="password" placeholder="最多15位的密码"  class="n_la" ></dd>
	                </dl>
	                 <dl>
	                    <dt>推荐人：</dt>
	                    <dd><input type="text" name="recommonedPhone" id="recommonedPhone" class="n_la" placeholder="推荐人手机号码"></dd>
	                </dl>
	                <div id="user" >
	                	<dl >
	                		<dt>昵称：</dt>
	                		<dd><input type="text" name="nickname" id="nickname" class="n_la" /> </dd>
	                	</dl>
	                </div>
	                <div id= "manger" style="display:none" >
	                <dl>
	                    <dt>姓名：</dt>
	                    <dd><input type="text" name="mangername" maxlength="5" id="mangername" class="n_la" 
	                    	onKeyDown="gbcount1(this.form.mangername,this.form.userd2,5,'姓名不能超过5个字符');"  onKeyUp="gbcount1(this.form.mangername,this.form.userd2,5,'姓名不能超过5个字符');" >
		                   	<input style="width: 40px;border:none;background:#fff;" name="userd2" value="0/5" readonly="readonly" /> 
	                    </dd>
	                </dl>
	                <dl>
	                    <dt>身份证后6位：</dt>
	                    <dd><input type="text" name="idcard"  id="idcard" maxlength="6" class="n_la" >
	                    </dd>
	                </dl>
	                </div>
	                <div id="seller" style="display:none" >
	                	<dl>
		                    <dt>姓名：</dt>
		                    <dd><input type="text" name="sellername"  id="sellername" class="n_la" maxlength="5"
		                     onKeyDown="gbcount1(this.form.sellername,this.form.used1,5,'姓名不能超过5个字符');"  onKeyUp="gbcount1(this.form.sellername,this.form.used1,5,'姓名不能超过5个字符');">
	                      	<input style="width: 40px;border:none;background:#fff;" name="used1" value="0/5" readonly="readonly" />
		                    </dd>
		                </dl>
		                <dl>
		                     <dt>公司名称：</dt>
		                    <dd><input type="text" name="company"  id="company" class="n_la" maxlength="15"
		                     onKeyDown="gbcount1(this.form.company,this.form.used,15,'公司名不能超过15个字符');"  onKeyUp="gbcount1(this.form.company,this.form.used,15,'公司名字不能超过15个字符');" >
		                   	<input style="width: 40px;border:none;background:#fff;" name="used" value="0/15" readonly="readonly" /> 
		                    </dd>
		                </dl>
		                 <dl>
		                	<dt>详细地址：</dt>
		                	<dd>
		                		<input type="text" class="n_lt" id="sellerAddress" placeholder="请输入完整的地址" >
		                	</dd>
		                </dl> 
	                </div>
	            </div>
	            </div>
            </form>
            <div class="submit" align="center">
                     <div class="submit_trr">
                        <a href="javascript:void(0)" id="submitform" ><p>提交</p></a>
                        <a href="${ctx}/admin/user/list"><p>返回</p></a>
                     </div>
                 </div>
        </div> 

</body>
</html>