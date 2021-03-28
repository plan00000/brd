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
<link href="${ctx }/static/brd/css/plugins/datapicker/datepicker3.css" rel="stylesheet">
<script src="${ctx }/static/brd/js/plugins/datapicker/bootstrap-datepicker.js" type="text/javascript"></script>
<title>会员列表</title>
<script type="text/javascript">
	$(function() {
		activeNav("2");
		
		var step = '${step}';
		if(step>0){
			document.title ='第'+step+'步'
		}
		
		
		
		var orRangetime = '${timeRange}';
		if(orRangetime=='custom'){
			$("#showCustom").css("display","block");			
		}		
		
		//日期控件初始化
		$.fn.datepicker.dates['zh-cn'] = {
			    days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"],
			    daysShort: ["日", "一", "二", "三", "四", "五", "六"],
			    daysMin: ["日", "一", "二", "三", "四", "五", "六"],
			    months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
			    monthsShort: ["一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"],
			    today: "今天",
			    clear: "清除",
			    format: "yyyy-mm-dd",
			    titleFormat: "yyyy MM", /* Leverages same syntax as 'format' */
			    weekStart: 1
		};
		$(".datepicker").datepicker({
		    language: 'zh-cn'
		});
		
		
		//徒弟
		$("#sortsonSum").click(function(){
			var id=$(this).data('id');
			if(id == 1){
				$("#formsortName").val("sonSum");
				$("#formsortType").val("asc");
				submit();
			}else{
				$("#formsortName").val("sonSum");
				$("#formsortType").val("desc");
				submit();
			}
		});
		//徒孙数
		$("#sortgrandsonSum").click(function(){
			var id = $(this).data('id');
			if(id == 1){
				$("#formsortName").val("grandsonSum");
				$("#formsortType").val("asc");
				submit();
			}else{
				$("#formsortName").val("grandsonSum");
				$("#formsortType").val("desc");
				submit();
			}
		});
		
		//徒孙孙
		$("#sortggsonsSum").click(function(){
			var id = $(this).data('id');
			if(id == 1){
				$("#formsortName").val("ggsonsSum");
				$("#formsortType").val("asc");
				submit();
			}else{
				$("#formsortName").val("ggsonsSum");
				$("#formsortType").val("desc");
				submit();
			}
		});
		//登录次数
		$("#sortloginTimes").click(function(){
			var id =$(this).data('id');
			if(id == 1){
				$("#formsortName").val("loginTimes");
				$("#formsortType").val("asc");
				submit();
			}else{
				$("#formsortName").val("loginTimes");
				$("#formsortType").val("desc");
				submit();
			}
		})
		
		
		//订单
		
		$("#sortorderSum").click(function(){
			var id = $(this).data('id');
			if(id == 1){
				$("#formsortName").val("orderSum");
				$("#formsortType").val("asc");
				submit();
			}else{
				$("#formsortName").val("orderSum");
				$("#formsortType").val("desc");
				submit();
			}
		})
		
		//成功订单
		$("#sortorderSuccessSum").click(function(){
			var id = $(this).data('id');
			if(id == 1){
				$("#formsortName").val("orderSuccessSum");
				$("#formsortType").val("asc");
				submit();
			}else{
				$("#formsortName").val("orderSuccessSum");
				$("#formsortType").val("desc");
				submit();
			}
		});
		
		$("#query").click(function(){
			var state= $("#formstate").val($("#state").val());
			var loginTimes =$("#loginTimes").val();
			var grandsonSum =$("#grandsonSum").val();
			var orderSum=$("#orderSum").val();
			var orderSuccessSum=$("#orderSuccessSum").val(); 
			var keywordType=$("#formkeywordType").val($("#keywordType").val());
			var keyword = $("#formkeyword").val($("#keyword").val());
			var timeRange=$("#formtimeRange").val($("#timeRange").val());
			var startTimestr = $("#startTimestr").val();
			var endTimestr=$("#endTimestr").val();
			if(startTimestr!=""&& endTimestr==""){
				alert("请填写结束时间")
				return ;
			}
			if(startTimestr==""&& endTimestr!=""){
				alert("请填写开始时间")
				return ;
			}
			/***判断时间区间*/
			var startarr =startTimestr.split("-");
			var endarr = endTimestr.split("-");
			var start = Date.UTC(startarr[0],startarr[1],startarr[2]);
			var end = Date.UTC(endarr[0],endarr[1],endarr[2]);
			if(startTimestr.length > 0 &&endTimestr.length > 0 && start > end){
				alert("起始时间必须小于截止时间");
				return ;
			}

			$("#formggrandsonSum").val($("#ggrandsonSum").val());
			$("#formstartTimestr").val($("#startTimestr").val());
			$("#formendTimestr").val($("#endTimestr").val());		
			$("#formloginTimes").val($("#loginTimes").val());
			$("#formsonSum").val($("#sonSum").val());
			$("#formgrandsonSum").val($("#grandsonSum").val());
			$("#formorderSum").val($("#orderSum").val());
			$("#formorderSuccessSum").val($("#orderSuccessSum").val());
			var userType =$("#userType").val();
			if(userType!="-1"){
				$("#formuserType").val($("#userType").val());
			}
			
			$("#myform").attr("target","_blank");
			$("#myform").submit();
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
			$.post("${ctx}/admin/user/changeState",{id:userId,state:poststate},function(data){
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
		
		$("#export_btn").click(function(){
			window.open("${ctx}/admin/user/export;JSESSIONID=<%=request.getSession().getId()%>?sortName=${sortName}&sortType=${sortType}&state=${state}&userType=${userType}&loginTimes=${loginTimes}&sonSum=${sonSum}&grandsonSum=${grandsonSum}&orderSum=${orderSum}&orderSuccessSum=${orderSuccessSum}&keywordType=${keywordType}&keyword=${keyword}&timeRang=${timeRang}&startTimestr=${startTimestr}&endTimestr=${endTimestr}&userId=${userId}&getType=${getType}&ggrandsonSum=${ggrandsonSum}&lastLoginDate=${lastLoginDate}&departid=${departid}&contacted=${contacted}&registerStarttime=${registerStarttime}&registerEndtime=${registerEndtime}");
		}); 
	
		
	});
	
	
	
	function submit(){
		$("#myform").submit();
	}
	
	function showCustomContent(){
		var value = $("#timeRange").val();
		if(value=="custom"){
			document.getElementById("showCustom").style.display="block";
		}else{
			document.getElementById("showCustom").style.display="none";
		}
	}
	
	
	
</script>
</head>
<body>
	<div class="row border-bottom ">
		<div class="basic">
	        <p>会员管理</p>
	        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a><strong>会员管理</strong></a></span>
	    </div>
	</div>
		<div class="member animated fadeInRight">
			
            <form id = "myform" action ="${ctx}/admin/user/list" >
				<input type ="hidden" id="formsortName" name = "sortName"/>
				<input type ="hidden" id="formsortType" name = "sortType"/>
				<input type ="hidden" id="formstate" name = "state" />
				<input type ="hidden" id="formuserType" name="userType" />
				<input type ="hidden" id="formloginTimes" name="loginTimes" />
				<input type ="hidden" id="formsonSum" name="sonSum" />
				<input type ="hidden" id="formgrandsonSum" name="grandsonSum" />
				<input type ="hidden" id="formorderSum" name="orderSum" />
				<input type ="hidden" id="formorderSuccessSum" name="orderSuccessSum" />
				<input type ="hidden" id="formkeywordType" name="keywordType" />
				<input type ="hidden" id="formkeyword" name="keyword" />
				<input type ="hidden" id="formtimeRange" name="timeRange" />
				<input type ="hidden" id="formstartTimestr" name="startTimestr" />
				<input type ="hidden" id="formendTimestr" name="endTimestr" />
				<input type ="hidden" value="${userId}" name="userId" />
				<input type ="hidden" value="${getType}" name="getType" />
				<input type ="hidden" id="formggrandsonSum" name="ggrandsonSum" />
				<input type ="hidden" value="${lastLoginDate}" name="lastLoginDate" /><!-- 登录时间 -->
				<input type ="hidden" value="${departid}" name="departid" /><!-- 所属业务员部门id-->
				<input type ="hidden" value="${contacted }" name="contacted" />
				<input type ="hidden" value="${step+1 }" name="step" />
				<input type ="hidden" value="${registerStarttime }" name="registerStarttime" />
				<input type ="hidden" value="${registerEndtime }" name="registerEndtime" />
  			</form>
            
            <div class="member_text">
                <dl>
                    <dt>会员状态：</dt>
                    <dd>
                        <select class="m_ter" name="state" id="state">
	                        <option value="-1" <c:if test="${state eq '-1' }">selected</c:if> >全部</option>
	                        <option value="1" <c:if test="${state eq '1' }">selected</c:if> >正常</option>
	                       <option <c:if test="${state eq '0' }">selected</c:if> value='0'>禁用</option> 
                        </select>
                    </dd>
                </dl>
                <dl>
                    <dt>会员身份：</dt>
                    <dd>
                        <select class="m_ter" name="userType" id="userType" >
	                        <option value="-1" >全部</option>
	                        <option <c:if test="${userType eq 'USER' }">selected</c:if> value='USER'>普通用户</option> 
	                       	<option <c:if test="${userType eq 'MANAGER' }">selected</c:if> value='MANAGER'>融资经理</option> 
	                       	<option <c:if test="${userType eq 'SELLER' }">selected</c:if> value='SELLER'>商家</option>
                       		<option <c:if test="${userType eq 'SALESMAN' }">selected</c:if> value='SALESMAN'>业务员</option>
                        </select>
                    </dd>
                </dl>
                <dl>
                    <dt>登录次数：</dt>
                    <c:choose>
                    	<c:when test="${loginTimes ne '0' }">
                    		<dd><input type="text" id="loginTimes" value="${loginTimes }" class="m_ter">≥次</dd>
                    	</c:when>
                    	<c:otherwise>
                    		<dd><input type="text" id="loginTimes" class="m_ter">≥次</dd>
                    	</c:otherwise>
                    </c:choose>
                    
                </dl>
                <dl>
                    <dt>徒弟数量：</dt>
	                   <c:choose>
	                    	<c:when test="${sonSum ne '0' }">
	                    		 <dd><input type="text" id="sonSum" value="${sonSum }" class="m_ter">≥人</dd>
	                    	</c:when>
	                    	<c:otherwise>
	                    		<dd><input type="text" id="sonSum" class="m_ter">≥人</dd>
	                    	</c:otherwise>
	                    </c:choose>  
                </dl>
                <dl>
                    <dt>徒孙数量：</dt>
                    <c:choose>
                    	<c:when test="${ggrandsonSum ne '0' }">
                    		<dd><input type="text" id="grandsonSum" value="${grandsonSum }" class="m_ter">≥人</dd>
                    	</c:when>
                    	<c:otherwise>
                    		<dd><input type="text" id="grandsonSum"  class="m_ter">≥人</dd>
                    	</c:otherwise>
                    </c:choose>
                </dl>
                <dl>
                	<dt>徒孙孙数：</dt>
                	 <c:choose>
                    	<c:when test="${ggrandsonSum ne '0' }">
                    		<dd><input type="text" id="ggrandsonSum" value="${ggrandsonSum }" class="m_ter">≥人</dd>
                    	</c:when>
                    	<c:otherwise>
                    		<dd><input type="text" id="ggrandsonSum" class="m_ter">≥人</dd>
                    	</c:otherwise>
                    </c:choose>
                </dl>
                
                <dl>
                    <dt>提交订单：</dt>
                    <c:choose>
                    	<c:when test="${orderSum ne '0' }">
                    		 <dd><input type="text" id="orderSum" value="${orderSum }" class="m_ter">≥单</dd>
                    	</c:when>
                    	<c:otherwise>
                    		 <dd><input type="text" id="orderSum" class="m_ter">≥单</dd>
                    	</c:otherwise>
                    </c:choose>
                </dl>
                <dl>
                    <dt>订单成交：</dt>
                    <c:choose>
                    	<c:when test="${orderSuccessSum ne '0'}">
                    		<dd><input type="text" id="orderSuccessSum" value="${orderSuccessSum}" class="m_ter">≥单</dd>
                    	</c:when>
                    	<c:otherwise>
                    		<dd><input type="text" id="orderSuccessSum" class="m_ter">≥单</dd>
                    	</c:otherwise>
                    </c:choose>
                    
                </dl>
                <dl class="m_time">
                    <dt>注册时间：</dt>
                    <dd>
                        <select class="m_ter" name="timeRange" id="timeRange" onchange="showCustomContent()">
	                        <option <c:if test="${timeRange eq 'all' }">selected</c:if> value='all'>全部时间</option>
	                        <option <c:if test="${timeRange eq 'today'}" >selected</c:if> value="today"  >当日注册</option>
	                        <option <c:if test="${timeRange eq 'three'}" >selected</c:if>  value="three" >三天内</option>
	                        <option <c:if test="${timeRange eq 'week'}">selected</c:if> value="week">七天内</option>
	                        <option <c:if test="${timeRange eq 'month'}">selected</c:if> value="month" >一个月内</option>
	                        <option <c:if test="${timeRange eq 'custom' }" >selected</c:if> value="custom" >自定义时间</option>
                        </select>
                        <div id="showCustom" style="display:none">
	                         <span>自定义<input type="text" class="m_dy datepicker" id="startTimestr" value="${startTimestr }">
	                         —
	                         <input type="text" class="m_dy datepicker" id="endTimestr" value="${endTimestr }"></span>
						</div>
                    </dd>
                </dl>
                <dl class="m_time">
                    <dt>会员查询：</dt>
                    <dd>
                        <select class="m_ter" name="keywordType" id="keywordType" >
	                       <option <c:if test="${keywordType eq '0' }">selected</c:if> value='0'>会员名</option>
	                       <option <c:if test="${keywordType eq '1' }">selected</c:if> value='1'>手机号码</option>
                        </select> 
                        <span class="m_ct"><input type="text" id="keyword" class="m_cx" value="${keyword }" placeholder="输入要搜索的关键字"></span>
                    </dd>
                </dl>
                <div class="member_bottom">
                    <p>操作：</p>
                    <a href="#" id="query" >查询</a>
                    <a href="${ctx}/admin/user/toAddUser" >新增会员</a>
                    <shiro:hasPermission name="USER_EXPORT">
                        <a href="#" id="export_btn" >导出EXCEL</a>
                    </shiro:hasPermission>
                </div> 
               </div>
        </div>
        	
        <div class="username-text animated fadeInRight">
			<div class="row">
			<div class="col-lg-12">
				<div class="ibox float-e-margins">

						<div class="table-responsive">
            <div class="level m_bottom" style="padding:0px;">
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table table-striped" >
                 <thead>
                 	<tr>
                        <td><strong>会员名</strong></td>
                        <td><strong>身份</strong></td>
                        <td><strong>师父</strong></td>
                        <td><strong>所属商家</strong></td>
                        <td><strong>所属业务员</strong></td>
                        <td>
                        <div class="level_triangle" >
                        	<p style="padding-top:0px" ><strong>徒弟数</strong></p>
                            <c:choose>
                            	<c:when test="${sortName eq 'sonSum' && sortType eq 'desc' }" >
                            		<span class="l_sanj2" style="margin-top:6px" id="sortsonSum" data-id =1 ></span>
                            	</c:when>
                            	<c:when test="${sortName eq 'sonSum' && sortType eq 'asc' }" >
                            		<span class="l_sanj1"  style="margin-top:6px" id="sortsonSum" data-id =2 ></span>
                            	</c:when>
                            	<c:otherwise>
                            		<span class="l_sanj3"  style="margin-top:3px" id="sortsonSum" data-id=1 ></span>
                            	</c:otherwise>
                            </c:choose>
                        </div>
                        </td>
                        <td>
                        <div class="level_triangle">
                        	<p style="padding-top:0px" ><strong>徒孙数</strong></p>
                            <c:choose>
                            	<c:when test="${sortName eq 'grandsonSum' && sortType eq 'desc' }" >
                            		<span class="l_sanj2"  style="margin-top:6px" id="sortgrandsonSum" data-id =1 ></span>
                            	</c:when>
                            	<c:when test="${sortName eq 'grandsonSum' && sortType eq 'asc' }" >
                            		<span class="l_sanj1"  style="margin-top:6px" id="sortgrandsonSum" data-id =2 ></span>
                            	</c:when>
                            	<c:otherwise>
                            		<span class="l_sanj3"  style="margin-top:3px" id="sortgrandsonSum" data-id=1 ></span>
                            	</c:otherwise>
                            </c:choose>
                        </div>
                        </td>
                        <td>
                        <div class="level_triangle">
                        	<p style="padding-top:0px" ><strong>徒孙孙数</strong></p>
                            <c:choose>
                            	<c:when test="${sortName eq 'ggsonsSum' && sortType eq 'desc' }" >
                            		<span class="l_sanj2"  style="margin-top:6px" id="sortggsonsSum" data-id =1 ></span>
                            	</c:when>
                            	<c:when test="${sortName eq 'ggsonsSum' && sortType eq 'asc' }" >
                            		<span class="l_sanj1"  style="margin-top:6px" id="sortggsonsSum" data-id =2 ></span>
                            	</c:when>
                            	<c:otherwise>
                            		<span class="l_sanj3"  style="margin-top:3px" id="sortggsonsSum" data-id=1 ></span>
                            	</c:otherwise>
                            </c:choose>
                        </div>
                        </td>
                        <td>
                        <div class="level_triangle">
                        	<p style="padding-top:0px" ><strong>登录数</strong></p>
                            <c:choose>
                            	<c:when test="${sortName eq 'loginTimes' && sortType eq 'desc' }" >
                            		<span class="l_sanj2"  style="margin-top:6px" id="sortloginTimes" data-id =1 ></span>
                            	</c:when>
                            	<c:when test="${sortName eq 'loginTimes' && sortType eq 'asc' }" >
                            		<span class="l_sanj1"  style="margin-top:6px" id="sortloginTimes" data-id =2 ></span>
                            	</c:when>
                            	<c:otherwise>
                            		<span class="l_sanj3"  style="margin-top:3px" id="sortloginTimes" data-id=1 ></span>
                            	</c:otherwise>
                            </c:choose>
                        </div>
                        </td>
                        <td>
                        <div class="level_triangle">
                        	<p style="padding-top:0px" ><strong>订单数</strong></p>
                            <c:choose>
                            	<c:when test="${sortName eq 'orderSum' && sortType eq 'desc' }" >
                            		<span class="l_sanj2"  style="margin-top:6px" id="sortorderSum" data-id =1 ></span>
                            	</c:when>
                            	<c:when test="${sortName eq 'orderSum' && sortType eq 'asc' }" >
                            		<span class="l_sanj1"  style="margin-top:6px" id="sortorderSum" data-id =2 ></span>
                            	</c:when>
                            	<c:otherwise>
                            		<span class="l_sanj3"  style="margin-top:3px" id="sortorderSum" data-id=1 ></span>
                            	</c:otherwise>
                            </c:choose>
                        </div>
                        </td>
                        <td>
                        <div class="level_triangle">
                        	<p style="padding-top:0px"><strong>成功订单</strong></p>
                            <c:choose>
                            	<c:when test="${sortName eq 'orderSuccessSum' && sortType eq 'desc' }" >
                            		<span class="l_sanj2"  style="margin-top:6px" id="sortorderSuccessSum" data-id =1 ></span>
                            	</c:when>
                            	<c:when test="${sortName eq 'orderSuccessSum' && sortType eq 'asc' }" >
                            		<span class="l_sanj1"  style="margin-top:6px" id="sortorderSuccessSum" data-id =2 ></span>
                            	</c:when>
                            	<c:otherwise>
                            		<span class="l_sanj3"  style="margin-top:3px" id="sortorderSuccessSum" data-id=1 ></span>
                            	</c:otherwise>
                            </c:choose>
                        </div></td>
                        <td><strong>状态</strong></td>
                        <td><strong>操作</strong></td>
                     </tr>
                 </thead>
                 <tbody>
                    <c:forEach var="user" items="${users.getContent()}" >	
                    	<tr bgcolor="f3f3f4" >
                    		<td><a href="${ctx}/admin/user/toEditUser/${user.id}">${user.username }</a></td>
                    		<td class="m_yew" >${user.userType.getStr()}</td>
                    		<c:choose>
                    		<c:when test="${empty user.userInfoBoth.parent.username }" >
                    			<td>无</td>
                    		</c:when>
                    		<c:otherwise>
                    			<td><a target="_blank" href="${ctx}/admin/user/list?userId=${user.userInfoBoth.parent.id}&getType=getSons" >${user.userInfoBoth.parent.username }</a></td>
                    		</c:otherwise>
                    		</c:choose>
                    		<c:choose>
                    			<c:when test="${empty user.userInfoBoth.seller.username }">
                    				<td>无</td>
                    			</c:when>
                    			<c:otherwise>
                    				<td><a target="_blank" href="${ctx}/admin/user/list?userId=${user.userInfoBoth.seller.id}&getType=getSeller" >${user.userInfoBoth.seller.username }</a></td>
                    			</c:otherwise>
                    		</c:choose>
                    		<c:choose>
                    			<c:when test="${empty user.userInfoBoth.salesman.username }">
                    				<td>无</td>
                    			</c:when>
                    			<c:otherwise>
                    				<td><a target="_blank" href="${ctx}/admin/user/list?userId=${user.userInfoBoth.salesman.id}&getType=getSales" >${user.userInfoBoth.salesman.username }</a></td>
                    			</c:otherwise>
                    		</c:choose>
                    		
                    		<td class="l_in3" ><a target="_blank" href="${ctx }/admin/user/list?userId=${user.id}&&getType=getSons" >${user.userInfoBoth.sonSum}</a></td>
                    		<td class="l_in3" ><a target="_blank" href="${ctx }/admin/user/list?userId=${user.id}&&getType=getGrandSons" >${user.userInfoBoth.grandsonSum}</a></td>
                    		<td class="l_in3" ><a target="_blank" href="${ctx }/admin/user/list?userId=${user.id}&&getType=getGGrandSons" >${user.userInfoBoth.ggsonsSum}</a></td>
                    		<td class="l_in3" ><a href="javascript:void(0)" style= "cursor:default" >${user.loginTimes}</a></td>
                    		<td class="l_in3" ><a target="_blank" href="${ctx }/admin/orderform/weixin/list?userId=${user.id}" >${user.userInfoBoth.orderSum}</a></td>
                    		<td class="l_in3" ><a target="_blank" href="${ctx }/admin/orderform/weixin/list?userId=${user.id}&&getType=successOrder" >${user.userInfoBoth.orderSuccessSum}</a></td>
                    		<td class="l_color">
			                        	<c:choose>
											 <c:when test="${user.state.ordinal() eq 0 }">
												<button
														class="changeState btn  btn btn-danger btn-sm demo4"
														changeId="${user.id }" state="close">禁用</button>
											</c:when>
											<c:otherwise>
												<button
														class="changeState btn btn-primary btn-sm demo4"
														changeId="${user.id }" state="open">启用</button>
											</c:otherwise> 
										</c:choose>
			                </td>
                        	<td><a href="${ctx}/admin/user/toEditUser/${user.id}"><span><img src="${ctx }/static/brd/img/bjt1.png"></span></a></td>
                    	</tr>
                    </c:forEach>                      
                      </tbody>
                     </table>
						  <div class="text-right">
							<div class="btn-group">
								<div class="dataTables_paginate paging_simple_numbers"
									id="DataTables_Table_0_paginate">
									<tags:pagination page="${users}" paginationSize="10"
										hrefSubfix="contacted=${contacted }&sortName=${sortName }&&sortType=${sortType }&state=${state }&userType=${userType}&loginTimes=${loginTimes }&sonSum=${sonSum }&grandsonSum=${grandsonSum}&orderSum=${orderSum }&orderSuccessSum=${orderSuccessSum }&keywordType=${keywordType }&keyword=${keyword}&timeRange=${timeRange }&startTimestr=${startTimestr }&endTimstr=${endTimestr}&userId=${userId }&getType=${getType }&ggrandsonSum=${ggrandsonSum}&registerStarttime=${registerStarttime}&registerEndtime=${registerEndtime}"
										hrefPrefix="${ctx}/admin/user/list" />
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