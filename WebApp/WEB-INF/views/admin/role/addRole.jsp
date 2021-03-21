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
<title>新增角色</title>
<script type="text/javascript">
	$(function() {
		activeNav2("1","1_2");
		
		$("#submit").click(function(){
			var permissions = "";
			$('input[name="permissions"]:checked').each(function(){
				permissions += $(this).val() + ";";
			});
			var roleName =$("#roleName").val();
			
			if(roleName.trim().length==0){
				alert("角色名不能为空");
				return;
			}
			 var dto ={
					rolename:roleName,
					permissions:permissions
			 } 
			
			 $.post('${ctx}/admin/role/addRole',dto,function(data){
				if(data.code==0){
					showCallBackDialog("添加成功",function(){
						location.href = "${ctx}/admin/role/list";
					})
				} else{
					alert(data.mes);
				}			
			}); 
		});
		
		
		$(":checkbox").click(function(){
			
			
			var ischecked = $(this).is(":checked")?"check":"uncheck";
			var name = $(this).attr('name').split(" ");
			var checkclass = $(this).attr('class').split(" ");
			if(name=="allcheck"){
				var login_font =$("#login_font").is(":checked")?"check":"uncheck";
				var login_admin = $("#login_admin").is(":checked")?"check":"uncheck";
				$(":checkbox").iCheck(ischecked);
				$("#login_font").iCheck(login_font);
				$("#login_admin").iCheck(login_admin);
			}else{
				var permissionsS=$("#permissionsS").is(":checked")?"check":"uncheck";
				if((name=="permissions")&&(checkclass=="c_tr1")){
					permissionsS = ischecked;
				}
				if(!(name=="permissions"&& (checkclass=="c_box"))){
					$("."+name).iCheck(ischecked);
				}
				$(".permissions1").iCheck(permissionsS);
			}
			
		});
		
		
	});
	
</script>
</head>
<body>
<div class="row  border-bottom">
	<div class="basic">
        <p>企业管理</p>
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a href="#" >企业管理</a>><a href="#" >角色管理</a>><a><strong>新增角色</strong></a></span>
    </div>
</div>	
	
	<div class="wrapper wrapper-content animated fadeInRight">
		<div class="row">
			<div class="col-lg-12">
				<div class="ibox float-e-margins">
					
					<div class="ibox-content">
						<div class="character">
            	<div class="character01">
            	
            		<p><font>角色信息</font></p>
            		<form name="myform">
                    <dl>
                        <dt>角色名称：</dt>
                        <dd><input type="text" value="" name="roleName" id="roleName" maxlength="8" class="c_right"
                        	 onKeyDown="gbcount(this.form.roleName,this.form.used,8);"  onKeyUp="gbcount(this.form.roleName,this.form.used,8);" >
		                   					<input style="width:25px;border:none;background:#fff;" name="used" value="0/8" readonly="readonly" /> 
                        </dd>
                    </dl>
                    </form>
                 </div>
                  <div class="character01 hidden">
            		<p><font>登录权限</font></p>
                    <dl>
                        <dt class="c_ter"><input type="checkbox" name="permissions" id="login_font" value="LOGIN_MANAGER_FRONT" class="c_box">登录前台</dt>
                        <dd class="c_ter"><input type="checkbox" name="permissions" id="login_admin" checked="checked" value="LOGIN_MANAGER_BACK" checked="checked"  class="c_box">登录后台</dd>
                    </dl>
                 </div> 
                 <div class="character02">
                 	<p><font>菜单权限</font></p>
                    <div class="character-table">
                    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                        <tbody>
                            <tr>
                            	<td class="c_tr"><input type="checkbox"  name="allcheck" class="c_tr1"></td>
                            	<td class="c_td">一级菜单</td>
                                <td><strong>二级菜单</strong></td>
                             </tr>
                              <tr bgcolor="f3f3f4">
                            	<td class="c_tr"><input type="checkbox"  name="ORGANIZATION_MANAGER" class="c_tr1"></td>
                            	<td class="c_td">企业管理</td>
                                <td>
                                <span><input type="checkbox"  name="permissions" value="ORGANIZATION_MANAGER_DEPART" style="margin-top:17px;width:15px;height:15px;float:left" class="ORGANIZATION_MANAGER">部门管理</span>
                                <span><input type="checkbox"  name="permissions" value="ORGANIZATION_MANAGER_ROLE" style="margin-top:17px;width:15px;height:15px;float:left" class="ORGANIZATION_MANAGER">角色管理</span>
                                <span><input type="checkbox"   name="permissions" value="ORGANIZATION_MANAGER_USER" style="margin-top:17px;width:15px;height:15px;float:left" class="ORGANIZATION_MANAGER" >员工管理</span>
                                </td>
                             </tr>
                             <tr>
                            	<td class="c_tr"><input type="checkbox"  name="USER" class="c_tr1"></td>
                            	<td class="c_td">会员管理</td>
                                <td>
                                <span><input type="checkbox" value="USER_MANAGER" name="permissions"  style="margin-top:17px;width:15px;height:15px;float:left" class="USER">会员管理  </span>
                                <span><input type="checkbox"  value="USER_UPGRADE" name="permissions" style="margin-top:17px;width:15px;height:15px;float:left" class="USER">升级会员身份</span>
                               	
                               </td>
                             </tr>
                             <tr bgcolor="f3f3f4">
                            	<td class="c_tr"><input type="checkbox"  name="ORDER_MANAGER" class="c_tr1"></td>
                            	<td class="c_td">订单管理</td>
                                <td>
                                <span><input type="checkbox" name="permissions" value="ORDER_MANAGER" style="margin-top:17px;width:15px;height:15px;float:left" class="ORDER_MANAGER">贷款订单</span>
                                <span><input type="checkbox" name="permissions" value="ORDER_MANAGER_PC" style="margin-top:17px;width:15px;height:15px;float:left" class="ORDER_MANAGER" >官网订单</span>
                                 <span><input type="checkbox"  name="permissions" value="ORDER_MANAGER_BROKERAGE" style="margin-top:17px;width:15px;height:15px;float:left" class="ORDER_MANAGER">佣金订单</span>
                                  <span><input type="checkbox"  name="permissions" value="ORDER_MANAGER_WITHDRAW" style="margin-top:17px;width:15px;height:15px;float:left" class="ORDER_MANAGER">提现订单</span>
                               </td>
                             </tr>
                             <tr>
                            	<td class="c_tr"><input type="checkbox"  name="PRODUCT_MANAGER" class="c_tr1"></td>
                            	<td class="c_td">产品管理</td>
                                <td>
                                <span><input type="checkbox"  name="permissions" value="PRODUCT_MANAGER_TYPE" style="margin-top:17px;width:15px;height:15px;float:left" class="PRODUCT_MANAGER">产品类型</span>
                                 <span><input type="checkbox"  name="permissions" value="PRODUCT_MANAGER" style="margin-top:17px;width:15px;height:15px;float:left" class="PRODUCT_MANAGER">产品管理</span>
                               </td>
                             </tr>
                             <tr bgcolor="f3f3f4">
                            	<td class="c_tr"><input type="checkbox"  name="ACTIVITY_MANAGER" class="c_tr1"></td>
                            	<td class="c_td">活动管理</td>
                                <td>
                                <span><input type="checkbox"   name="permissions" value="ACTIVITY_MANAGER_STAR" style="margin-top:17px;width:15px;height:15px;float:left" class="ACTIVITY_MANAGER">星级订单</span>
                                 <span><input type="checkbox"  name="permissions" value="ACTIVITY_MANAGER_APPRENTICE" style="margin-top:17px;width:15px;height:15px;float:left" class="ACTIVITY_MANAGER">收徒奖励</span>
                                  <span><input type="checkbox"  name="permissions" value="ACTIVITY_MANAGER_REGISTER" style="margin-top:17px;width:15px;height:15px;float:left" class="ACTIVITY_MANAGER">推荐注册</span>
                                    <span><input type="checkbox"  name="permissions" value="ACTIVITY_MANAGER_QRCODE" style="margin-top:17px;width:15px;height:15px;float:left" class="ACTIVITY_MANAGER">二维码活动</span>
                               </td>
                             </tr>
                             <tr>
                             	<td class="c_tr" ><input type="checkbox" name="TEXT_MANGER" class="c_tr1" ></td>
                             	<td class="c_td">微信文章</td>
                             	<td>
                             	 <span><input type="checkbox"  name="permissions" value="TEXT_MANAGER_ACTIVITY" style="margin-top:17px;width:15px;height:15px;float:left" class="TEXT_MANGER">活动资讯</span>
                                 <span><input type="checkbox"  name="permissions" value="TEXT_MANAGER_HELP" style="margin-top:17px;width:15px;height:15px;float:left" class="TEXT_MANGER">新闻中心</span>
                             	</td>
                             </tr>
                             <tr bgcolor="f3f3f4">
                             	<td class="c_tr" ><input type="checkbox" name="TEXT_MANGER_PC" class="c_tr1" ></td>
                             	<td class="c_td">官网文章</td>
                             	<td>
                             	 <span><input type="checkbox"  name="permissions" value="TEXT_MANGER_PC_ACTIVITY" style="margin-top:17px;width:15px;height:15px;float:left" class="TEXT_MANGER_PC">精彩资讯</span>
                                 <span><input type="checkbox"  name="permissions" value="TEXT_MANAGER_APPRENTICE" style="margin-top:17px;width:15px;height:15px;float:left"  class="TEXT_MANGER_PC">收徒指南</span>
                                 <span><input type="checkbox"  name="permissions" value="TEXT_MANAGER_ABOUTUS" style="margin-top:17px;width:15px;height:15px;float:left" class="TEXT_MANGER_PC">关于我们</span>
                             	</td>
                             </tr>
                             <!-- <tr>
                            	<td class="c_tr"><input type="checkbox"  name="TEXT_MANAGER" class="c_tr1"></td>
                            	<td class="c_td">文章管理</td>
                                <td>
                                <span><input type="checkbox"  name="permissions" value="TEXT_MANAGER_ACTIVITY" style="margin-top:17px;width:15px;height:15px;float:left" class="TEXT_MANAGER">活动资讯</span>
                                 <span><input type="checkbox"  name="permissions" value="TEXT_MANAGER_HELP" style="margin-top:17px;width:15px;height:15px;float:left" class="TEXT_MANAGER">新闻中心</span>
                                 <span><input type="checkbox"  name="permissions" value="TEXT_MANAGER_APPRENTICE" style="margin-top:17px;width:15px;height:15px;float:left"  class="TEXT_MANAGER">收徒指南</span>
                                 <span><input type="checkbox"  name="permissions" value="TEXT_MANAGER_ABOUTUS" style="margin-top:17px;width:15px;height:15px;float:left" class="TEXT_MANAGER">关于我们</span>
                               </td>
                             </tr> -->
                             <tr >
                            	<td class="c_tr"><input type="checkbox"  name="AD_MANAGER" class="c_tr1"></td>
                            	<td class="c_td">广告管理</td>
                                <td>
                                <span><input type="checkbox"  name="permissions" value="AD_MANAGER_PC" style="margin-top:17px;width:15px;height:15px;float:left" class="AD_MANAGER">PC官网</span>
                                 <span><input type="checkbox"  name="permissions" value="AD_MANAGER_WEIXIN" style="margin-top:17px;width:15px;height:15px;float:left" class="AD_MANAGER">微信站</span>
                               </td>
                             </tr>
                             <tr bgcolor="f3f3f4">
                            	<td class="c_tr"><input type="checkbox"  name="SET_MANAGER" class="c_tr1"></td>
                            	<td class="c_td">平台设置</td>
                                <td>
                                 <span><input type="checkbox"   name="permissions" value="SET_MANAGER_BASE" style="margin-top:17px;width:15px;height:15px;float:left" class="SET_MANAGER">基础设置</span>
                                 <span><input type="checkbox"  name="permissions" value="SET_MANAGER_INFO" style="margin-top:17px;width:15px;height:15px;float:left" class="SET_MANAGER">短信通知</span>
                                 <span><input type="checkbox"  name="permissions" value="SET_ORDERFORM_NOTIFY" style="margin-top:17px;width:15px;height:15px;float:left" class="SET_MANAGER">订单通知</span>
                                 <span><input type="checkbox"  name="permissions" value="SET_MANAGER_AGGREMENT" style="margin-top:17px;width:15px;height:15px;float:left" class="SET_MANAGER">服务协议</span>
                              	 <span><input type="checkbox"  name="permissions" value="SET_WECHAT_NOTIFY" style="margin-top:17px;width:15px;height:15px;float:left" class="SET_MANAGER">微信推送</span>
                               	 <span><input type="checkbox"  name="permissions" value="SET_SEO_SETTING" style="margin-top:17px;width:15px;height:15px;float:left" class="SET_MANAGER">SEO设置</span>
                               	 <span><input type="checkbox" name="permissions" value="SET_FRIENDSHIPLINK" style="margin-top:17px;width:15px;height:15px;float:left" class="SET_MANAGER">友情链接</span>
                               </td>
                             </tr>
                             <tr >
                            	<td class="c_tr"><input type="checkbox"  name="STATISTICS_MANAGER" class="c_tr1"></td>
                            	<td class="c_td">统计报表</td>
                                <td>
                                 <span><input type="checkbox"  name="permissions" value="STATISTICS_MANAGER_USER" style="margin-top:17px;width:15px;height:15px;float:left" class="STATISTICS_MANAGER">会员统计</span>
                                 <span><input type="checkbox"  name="permissions" value="STATISTICS_REPORT_USER" style="margin-top:17px;width:15px;height:15px;float:left" class="STATISTICS_MANAGER">会员报表</span>
                                 <span><input type="checkbox"  name="permissions" value="STATISTICS_MANAGER_ORDER" style="margin-top:17px;width:15px;height:15px;float:left" class="STATISTICS_MANAGER">订单统计</span>
                                 <span><input type="checkbox"  name="permissions" value="STATISTICS_MANAGER_PRODUCT" style="margin-top:17px;width:15px;height:15px;float:left" class="STATISTICS_MANAGER">产品统计</span>
                                 <span><input type="checkbox"  name="permissions" value="STATISTICS_MANAGER_DEPART" style="margin-top:17px;width:15px;height:15px;float:left" class="STATISTICS_MANAGER">部门统计</span>
                                 <span><input type="checkbox"  name="permissions" value="STATISTICS_MANAGER_BROKERAGE" style="margin-top:17px;width:15px;height:15px;float:left" class="STATISTICS_MANAGER">佣金统计</span>
                              	 <span><input type="checkbox"  name="permissions" value="STATISTICS_SELLER" style="margin-top:17px;width:15px;height:15px;float:left" class="STATISTICS_MANAGER">商家统计</span>
                               </td>
                             </tr>
                             <tr bgcolor="f3f3f4">
                            	<td class="c_tr"><input type="checkbox" value="USER_EXPORT"  name="permissions" class="c_tr1"></td>
                            	<td class="c_td">导出excel</td>
                                <td></td>
                             </tr>
                             <tr >
                            	<td class="c_tr"><input type="checkbox" value="SYS_LOG"  name="permissions" class="c_tr1"></td>
                            	<td class="c_td">操作日志</td>
                                <td></td>
                             </tr>
                        </tbody>
                    </table>
                    </div>
                 </div>
                 
                 <div class="submit" align="center">
                     <div class="submit_trr">
                        <a href="javascript:void(0)" id="submit"><p>提交</p></a>
                        <a href="${ctx}/admin/role/list"><p>返回</p></a>
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