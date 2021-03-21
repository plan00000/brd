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
<title>星级订单</title>
<script type="text/javascript">
function issueBonus(userId){
	var a = $("#userId"+userId).attr("name");
	if(a=='NO'){
		$("#userId_post").val(userId);
		$(".withdraw1").css("display","block");
	}
}
$(function() {
	activeNav2("5","5_1");
    var item = $("tr"); 
    for(var i=0;i<item.length;i++){ 
        if(i%2==1){ 
            item[i].style.backgroundColor="#f3f3f4"; 
        } 
    } 
	$("#canfirm").click(function(){
		var userId= $("#userId_post").val();
		 $("#bonusFrame").hide();
			$.ajax({
				type : 'post',
				url : '${ctx}/admin/activity/rewardState?userId='+userId,
				success : function(res) {
					if (res.code == 1) {
						$("#userId"+userId).text("奖励已发");
						$("#userId"+userId).attr("name","YES");
						return false;
					}
				}
			})
	});
	$(".w_right").click(function(){
		 $("#bonusFrame").hide();
	});
	$(".withdraw1 p font").click(function(){
		 $("#bonusFrame").hide();
	});
	$("#starOrderSetting").click(function(){
		location.href = "${ctx}/admin/activity/starOrderSetting?activityType=1"; 
	}); 
	
	$("#starNum").click(function(){
		var id=$(this).data('id');
		if(id == 1){
			$("#ipt_sortName").val("orderSum");
			$("#ipt_sortType").val("asc");
			requestLoad();
		}else{
			$("#ipt_sortName").val("orderSum");
			$("#ipt_sortType").val("desc");
			requestLoad();
		}
	});

})

	function changeValue(object){
		var $this = $(object);
		var type = $this.data("type");
		var value = $this.data("value");
		if(type== 'ordersStatus'){
			$(".orderstatus").removeClass('e_class');
			$this.addClass('e_class');
			$("#ipt_status").val(value);
			requestLoad();
		}
	}
	function requestLoad(){
		$("#request_form").submit();
	}

</script>
</head>
<body>
	<form id = "request_form" action ="${ctx}/admin/activity/starOrderList">
		<input type ="hidden" id ="ipt_status" value ="${status}" name = "status"/>
		<input type ="hidden" id ="ipt_sortName" value ="${sortName }" name = "sortName"/>
		<input type ="hidden" id = "ipt_sortType" value = "${sortType}" name = "sortType"/>
		<input type = "hidden" id ="ipt_page" name = "page"/>
	</form>
	<div class="row border-bottom">
	<div class="basic">
        <p>活动管理</p>
        <span><a href="<c:url value='/admin/main'/>"  style="margin-left:0;">首页</a>><a href="#" >活动管理</a>><a><strong>星级订单</strong></a></span>
    </div>
    </div>
       <div class="employee animated fadeInRight e_chan">
           <dl>
           	 <dt>完成状态：</dt>
	         <dd>
         	<p class="orderstatus <c:if test = "${status =='' }"> e_class</c:if>" data-type = "ordersStatus" data-value = "" onclick = "changeValue(this)">全部状态</p>
            <p class="orderstatus <c:if test = "${status =='COMPLETE' }"> e_class</c:if>" data-type = "ordersStatus" data-value = "COMPLETE" onclick = "changeValue(this)">已完成</p>
            <p class="orderstatus <c:if test = "${status =='UNCOMPLETE' }"> e_class</c:if>" data-type = "ordersStatus" data-value = "UNCOMPLETE" onclick = "changeValue(this)">进行中</p>
         </dd>
           </dl>
           <dl>
           	 <dt>操作：</dt>
             <dd><samp id ="starOrderSetting">活动设置</samp></dd>
           </dl>
            </div>
            <div class="username-text animated fadeInRight">
                      <div class="row">
			<div class="col-lg-12">
				<div class="ibox float-e-margins">
				<div class="table-responsive">
            <div class="level l_repost">
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                 <tbody>
                	 <tr>
                        <td><strong>会员名</strong></td>
                        <td><strong>手机号码</strong></td>
                      <td>
                 		   <div class="level_triangle">
                    		<p><strong>星级贷订单数</strong></p>
                      	 <c:choose>
                    		<c:when test="${sortName eq 'orderSum' && sortType eq 'desc' }">
                    			<span class="l_sanj2" id ="starNum" data-id = 1></span>
                    		</c:when>
                    		<c:when test="${sortName eq 'orderSum' && sortType eq 'asc' }">
                    			<span class="l_sanj1" id ="starNum" data-id = 2></span>
                    		</c:when>
                    		<c:otherwise>
                    			<span class="l_sanj3" id ="starNum" data-id = 1></span>
                    		</c:otherwise>
                    		</c:choose>
              	      </div></td>
                        
                         <td><strong>状态</strong></td>
                        <td><strong>操作</strong></td>
                        <td><input type="hidden" value="" id="userId_post"/>  </td>
                     </tr>
                      <c:forEach items="${page.getContent()}" var="item">
                  	 	 <tr>
                  	    	  <td>${item.user.username}</td>
                      		  <td>${item.user.mobileno}</td>
                      		  <td class="m_ls">${item.orderSum}</td>
                      		<c:if test="${item.status == 'COMPLETE'}">
                        		<td>已完成</td>
                      		  <td>
                      		  	<a href="javascript:void(0)" id="userId${item.user.id }" 
                      		  			onclick="issueBonus(${item.user.id })" style="color:#00bb9c;"
                      		  			name = "${item.user.userInfoBoth.starOrderAwardFlag}">
                      		    	<c:if test="${item.user.userInfoBoth.starOrderAwardFlag=='NO'}">发放奖励</c:if>
                      		 	    <c:if test="${item.user.userInfoBoth.starOrderAwardFlag=='YES'}">奖励已发</c:if>
                      		 	 </a>
                      		 	</td>
                      		  </c:if>
                      		  <c:if test="${item.status == 'UNCOMPLETE'}">
                        		<td>进行中</td>
                        		<td></td>
                        		</c:if>
                     	 </tr> 
                      	</c:forEach>
                     </tbody>
                     </table>
						<div class="text-right">

							<div class="btn-group">
								<div class="dataTables_paginate paging_simple_numbers"
									id="DataTables_Table_0_paginate">
									<tags:pagination page="${page}" paginationSize="10"
										hrefSubfix=""
										hrefPrefix="${ctx}/admin/activity/starOrderList" />
								</div>
							</div>
						</div>
                     <div id="bonusFrame" class="withdraw1"  style="display: none;">
                           <p><font></font></p>
                           <div class="withdraw_text">
                               <span>是否已线下联系并发放了奖励？</span>
                               <samp id="canfirm">是的，已发奖品</samp>
                               <samp class="w_right">还未发放</samp>
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
